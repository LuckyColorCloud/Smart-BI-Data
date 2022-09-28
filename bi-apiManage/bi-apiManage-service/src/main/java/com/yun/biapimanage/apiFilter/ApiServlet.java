package com.yun.biapimanage.apiFilter;

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
import com.yun.bidataconnmon.constant.CommonConstant;
import com.yun.bidataconnmon.vo.Result;
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
import java.util.Objects;

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
        ApiManageEntity apiManageEntity = apiManageService.getBaseMapper().selectOne(new QueryWrapper<ApiManageEntity>().lambda().eq(ApiManageEntity::getPath, path));
        if (apiManageEntity == null) {
            return Result.ERROR(Result.ResultEnum.INTERFACE_DOES_NOT_EXIST);
        }
        Result<Object> result;
        if (apiManageEntity.isAuth()) {
            //权限处理逻辑
            result = null;
        } else {
            //判断是否需要从请求头获取参数 减少不必要操作
            if (StrUtil.isEmpty(apiManageEntity.getJson()) || JSONUtil.parseObj(apiManageEntity.getJson()).isEmpty()) {
                result = getData(apiManageEntity, new JSONObject());
            } else {
                //从请求获取数据库配置需要的 请求参数
                JSONObject params = getParams(request, JSONUtil.parseArray(apiManageEntity.getJson()));
                result = getData(apiManageEntity, params);
            }
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
        //0.接口转发1.查询数据库 2.静态数据直接返回result
        switch (apiManageEntity.getType()) {
            case 0:
                QueryDataDto queryDataDto = new QueryDataDto();
                queryDataDto.setParams(params.isEmpty() ? null : JSONUtil.toJsonStr(params));
                queryDataDto.setApiId(apiManageEntity.getApiId());
                result = dataApiFeign.getData(queryDataDto);
                break;
            case 1:
                result = dataStorageApiFeign.querySql(apiManageEntity.getApiId());
                break;
            case 2:
                result = Result.OK((Object) apiManageEntity.getResult());
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
     * 匹配请求参数
     *
     * @param request
     * @param jsonArray
     * @return
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    private JSONObject getParams(HttpServletRequest request, JSONArray jsonArray) {
        String contentType = request.getContentType();
        //必须是application/json请求
        if (contentType.equalsIgnoreCase(CommonConstant.APP_JSON)) {
            JSONObject jsonObject = new JSONObject();
            JSONObject httpJsonBody = getHttpJsonBody(request);
            if (Objects.isNull(httpJsonBody)) {
                return new JSONObject();
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject.set(jsonArray.getStr(i), httpJsonBody.get(jsonArray.get(i)));
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
            return JSONUtil.parseObj(sb.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
