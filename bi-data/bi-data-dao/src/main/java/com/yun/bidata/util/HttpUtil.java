package com.yun.bidata.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.yun.bidata.entity.ApiPathEntity;
import com.yun.bidata.entity.UserRoleEntity;

import java.util.Map;

/**
 * HTTP 请求工具封装
 *
 * @author Yun
 */
@SuppressWarnings("unchecked")
public class HttpUtil {

    /**
     * get 方式获取角色token
     * @param userRoleEntity
     * @param timeOut
     * @return
     */
    public static String get(UserRoleEntity userRoleEntity, Integer timeOut) {
      return  HttpRequest.get(userRoleEntity.getDomain() + userRoleEntity.getUrl())
                .headerMap(JSONUtil.toBean(userRoleEntity.getHeader(), Map.class), true)
                .timeout(timeOut)
                .body(userRoleEntity.getBody())
                .execute().body();
    }

    /**
     * post 方式获取角色token
     * @param userRoleEntity
     * @param timeOut
     * @return
     */
    public static String post(UserRoleEntity userRoleEntity, Integer timeOut) {
        return  HttpRequest.post(userRoleEntity.getDomain() + userRoleEntity.getUrl())
                .headerMap(JSONUtil.toBean(userRoleEntity.getHeader(), Map.class), true)
                .timeout(timeOut)
                .body(userRoleEntity.getBody())
                .execute().body();
    }

    /**
     * get方式获取接口信息
     * @param userRoleEntity
     * @param timeOut
     * @return
     */
    public static String get(UserRoleEntity userRoleEntity, Integer timeOut, ApiPathEntity apiPathEntity) {
        return  HttpRequest.get(userRoleEntity.getDomain() + apiPathEntity.getUrl())
                .headerMap(JSONUtil.toBean(apiPathEntity.getPrivateHeader(), Map.class), true)
                .timeout(timeOut)
                .body(apiPathEntity.getBody())
                .execute().body();
    }

    /**
     * post方式获取接口信息
     * @param userRoleEntity
     * @param timeOut
     * @return
     */
    public static String post(UserRoleEntity userRoleEntity, Integer timeOut,ApiPathEntity apiPathEntity) {
        return  HttpRequest.get(userRoleEntity.getDomain() + userRoleEntity.getUrl())
                .headerMap(JSONUtil.toBean(apiPathEntity.getPrivateHeader(), Map.class), true)
                .timeout(timeOut)
                .body(apiPathEntity.getBody())
                .execute().body();
    }
}
