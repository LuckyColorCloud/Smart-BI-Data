package com.yun.biapimanage.apiFilter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yun.biapimanage.entity.ApiManageEntity;
import com.yun.biapimanage.service.ApiManageService;
import com.yun.bidataconnmon.constant.CommonConstant;
import com.yun.bidataconnmon.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
            if (out != null)
                out.close();
        }
    }

    public Result<Object> process(String path, HttpServletRequest request, HttpServletResponse response) {

        // 校验接口是否存在
        ApiManageEntity apiManageEntity = apiManageService.getBaseMapper().selectOne(new QueryWrapper<ApiManageEntity>().lambda().eq(ApiManageEntity::getPath, path));
        if (apiManageEntity == null) {
            return Result.ERROR(Result.ResultEnum.INTERFACE_DOES_NOT_EXIST);
        }
        Object o;
        if (apiManageEntity.isAuth()) {
            //权限处理逻辑
            o = null;
        } else {
            //判断是否需要从请求头获取参数 减少不必要操作
            if (StrUtil.isEmpty(apiManageEntity.getJson()) || JSONUtil.parseObj(apiManageEntity.getJson()).isEmpty()) {
                o = getData(apiManageEntity, null);
            } else {
                //从请求获取数据库配置需要的 请求参数
                JSONObject params = getParams(request, JSONUtil.parseArray(apiManageEntity.getJson()));
                o = getData(apiManageEntity, params);
            }
        }
        return Result.OK(o);
    }

    /**
     * 获取接口需要返回的数据
     *
     * @param apiManageEntity
     * @return
     */
    private Object getData(ApiManageEntity apiManageEntity, JSONObject params) {
        switch (apiManageEntity.getType()) {
            case 0:

                break;
            case 1:

                break;
        }
        return null;
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
