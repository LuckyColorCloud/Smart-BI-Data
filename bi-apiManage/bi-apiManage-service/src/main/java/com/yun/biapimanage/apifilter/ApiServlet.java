package com.yun.biapimanage.apifilter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yun.biapimanage.entity.ApiManageEntity;
import com.yun.biapimanage.service.ApiManageService;
import com.yun.bidata.api.DataApiFeign;
import com.yun.bidata.dto.FormatDto;
import com.yun.bidata.dto.QueryDataDto;
import com.yun.bidatacommon.constant.CommonConstant;
import com.yun.bidatacommon.vo.Result;
import com.yun.bidatastorage.api.DataStorageApiFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Yun
 */
@Slf4j
@Component
public class ApiServlet extends HttpServlet {

    @Autowired
    private ApiManageService apiManageService;
    @Autowired
    private DataApiFeign dataApiFeign;
    @Autowired
    private DataStorageApiFeign dataStorageApiFeign;

    /**
     * 拦截 post 请求 作为动态api接口输出 仅拦截 smart.api.context
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    @SuppressWarnings({"rawtypes", "ConstantConditions"})
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求路径
        String servletPath = req.getRequestURI();
        //切割获取末尾路由 /web/path ====> path
        servletPath = servletPath.substring(servletPath.lastIndexOf("/") + 1);
        PrintWriter out = null;
        Result result = new Result();
        try {
            out = resp.getWriter();
            resp.setContentType("application/json;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            result = process(servletPath, req, resp);
            resp.setStatus(result.getCode());
            out.append(JSONUtil.toJsonStr(result));
        } catch (Exception e) {
            result.setCode(Result.ResultEnum.ERROR.getRespCode());
            result.setMessage(e.getMessage());
            out.append(JSONUtil.toJsonStr(result));
            log.error(e.toString(), e);
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * 处理过程
     *
     * @param path     路径
     * @param request  请求
     * @param response 返回
     * @return
     */
    public Result<Object> process(String path, HttpServletRequest request, HttpServletResponse response) {
        // 校验接口是否存在
        ApiManageEntity apiManageEntity = apiManageService.getBaseMapper().selectOne(new QueryWrapper<ApiManageEntity>().lambda().eq(ApiManageEntity::getApiPath, path));
        if (apiManageEntity == null) {
            return Result.ERROR(Result.ResultEnum.INTERFACE_DOES_NOT_EXIST);
        }
        Result<Object> result;
        if (apiManageEntity.getAuto()) {
            //权限处理逻辑
            result = null;
        } else {
            //从请求获取 请求参数
            JSONObject params = getParams(request);
            result = getData(apiManageEntity, params);
        }
        return result;
    }

    /**
     * 获取接口需要返回的数据
     *
     * @param apiManageEntity
     * @return
     */
    private Result<Object> getData(ApiManageEntity apiManageEntity, JSONObject params) {
        Result<Object> result;
        //0.接口转发1.查询数据库 2.静态数据直接返回result 3.数据融合(根据id) 4.数据融合(数组合并)
        switch (apiManageEntity.getType()) {
            case 0:
                QueryDataDto queryDataDto = new QueryDataDto();
                queryDataDto.setParams(params.isEmpty() ? null : JSONUtil.toJsonStr(params));
                queryDataDto.setIndexId(JSONUtil.parseArray(apiManageEntity.getIndexId()).getInt(0));
                result = dataApiFeign.getData(queryDataDto);
                break;
            case 1:
                result = dataStorageApiFeign.querySql(JSONUtil.parseArray(apiManageEntity.getIndexId()).getInt(0));
                break;
            case 2:
                result = Result.OK(JSONUtil.isTypeJSON(apiManageEntity.getStaticData()) ? JSONUtil.parse(apiManageEntity.getStaticData()) : apiManageEntity.getStaticData());
                break;
            case 3:
            case 4:
                result = dataFusion(apiManageEntity);
                break;
            default:
                result = Result.ERROR(Result.ResultEnum.NO_SUCH_DATA_PROCESSING_TYPE);
                break;
        }
        //判断是否有需要在次转换为特定格式
        if (StrUtil.isNotEmpty(apiManageEntity.getParams()) && StrUtil.isNotEmpty(apiManageEntity.getChartType())) {
            FormatDto formatDto = new FormatDto();
            formatDto.setChartType(apiManageEntity.getChartType());
            formatDto.setData(JSONUtil.toJsonStr(result.getResult()));
            formatDto.setParams(apiManageEntity.getParams());
            Result<Object> format = dataApiFeign.format(formatDto);
            //判断转换是否成功
            if (format.isSuccess()) {
                return format;
            }
        }
        return result;
    }

    /**
     * 数据融合
     *
     * @param apiManageEntity api配置类
     * @return 融合结果
     */
    private Result<Object> dataFusion(ApiManageEntity apiManageEntity) {
        try {
            //必须是jsonList类型 单个融合锤子🔨
            JSONArray jsonArray = JSONUtil.parseArray(apiManageEntity.getIndexId());
            List<Object> data;
            switch (apiManageEntity.getFusion()) {
                case 0:
                    //接口类型
                    data = jsonArray.parallelStream().map(String::valueOf).map(Integer::valueOf).map(t -> new QueryDataDto() {
                        {
                            setIndexId(t);
                        }
                    }).map(dataApiFeign::getData).map(Result::getResult).collect(Collectors.toList());
                    break;
                case 1:
                    //查询数据库类型 多种不同类型时可从这里做数据关联内存搞⛏
                    data = jsonArray.parallelStream().map(String::valueOf).map(Integer::valueOf).map(dataStorageApiFeign::querySql).map(Result::getResult).collect(Collectors.toList());
                    break;
                default:
                    return Result.ERROR(Result.ResultEnum.DATA_FUSION_ERROR);
            }
            //判断融合类型  3.数据融合(根据id)  4.数据融合(数组合并)
            switch (apiManageEntity.getType()) {
                case 4:
                    //判断是否有融合参数 没有融合🔨
                    if (StrUtil.isEmpty(apiManageEntity.getFusionParams()) || JSONUtil.parseObj(apiManageEntity.getFusionParams()).isEmpty()) {
                        return Result.ERROR(Result.ResultEnum.DATA_FUSION_ERROR);
                    }
                    //匹配键
                    String key = apiManageEntity.getFusionParams();
                    //将所有对象转换成list集合
                    List<? extends List<Object>> listTemp = data.stream().map(t -> {
                        if (t instanceof Map) {
                            ArrayList<Object> temp = new ArrayList<>();
                            temp.add(t);
                            return temp;
                        } else if (t instanceof Collection) {
                            return JSONUtil.parseArray(t);
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                    //数据匹配 多变一  经过上层数据处理这里不存在 多种key 情况 如果有请转换成一致的key
                    ArrayList<JSONObject> jsonObjects = new ArrayList<>();
                    //拍平整个集合
                    listTemp.forEach(t -> t.stream().map(JSONObject::new).forEach(jsonObjects::add));
                    //合并集合
                    return Result.OK(jsonObjects.stream().collect(Collectors.groupingBy(t -> t.getStr(key)))
                            .values().parallelStream().map(list ->
                            {
                                HashMap<String, Object> hashMap1 = new HashMap<>(16);
                                list.forEach(hashMap1::putAll);
                                return hashMap1;
                            }).collect(Collectors.toList()));
                case 5:
                    //数据库查询出来都是集合类型====> 具体看代码
                    List<JSONArray> collect = data.parallelStream().map(JSONArray::new).collect(Collectors.toList());
                    //查询出来最少的集合数 多余会舍弃
                    int min = collect.stream().map(List::size).min(Integer::compareTo).get();
                    //结果
                    ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>();
                    for (int i = 0; i < min; i++) {
                        HashMap<String, Object> hashMap = new HashMap<>(16);
                        for (JSONArray objects : collect) {
                            hashMap.putAll(objects.getJSONObject(i));
                        }
                        hashMaps.add(hashMap);
                    }
                    return Result.OK(hashMaps);
                default:
                    return Result.ERROR(Result.ResultEnum.DATA_FUSION_ERROR);
            }
        } catch (Exception e) {
            return Result.ERROR(Result.ResultEnum.DATA_FUSION_ERROR);
        }
    }

    /**
     * 匹配请求参数
     *
     * @param request
     * @return
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    private JSONObject getParams(HttpServletRequest request) {
        String contentType = request.getContentType();
        //必须是application/json请求
        if (contentType.equalsIgnoreCase(CommonConstant.APP_JSON)) {
            JSONObject jsonObject = new JSONObject();
            JSONObject httpJsonBody = getHttpJsonBody(request);
            if (Objects.isNull(httpJsonBody)) {
                return new JSONObject();
            }
            return jsonObject;
        } else {
            throw new RuntimeException("请求方式不规范!");
        }
    }

    /**
     * 从请求获取请求参数
     *
     * @param request
     * @return
     */
    private JSONObject getHttpJsonBody(HttpServletRequest request) {
        try {
            InputStreamReader in = new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(in);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return JSONUtil.isTypeJSON(br.toString()) ? JSONUtil.parseObj(sb.toString()) : null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
