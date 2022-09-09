package com.yun.bidata.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.yun.bidata.entity.UserRoleEntity;

import java.util.Map;

/**
 * HTTP 请求工具封装
 *
 * @author Yun
 */
public class HttpUtil {

    @SuppressWarnings("unchecked")
    public static String get(UserRoleEntity userRoleEntity, Integer timeOut) {
      return  HttpRequest.get(userRoleEntity.getDomain() + userRoleEntity.getUrl())
                .headerMap(JSONUtil.toBean(userRoleEntity.getHeader(), Map.class), true)
                .timeout(timeOut)
                .body(userRoleEntity.getBody())
                .execute().body();
    }

    @SuppressWarnings("unchecked")
    public static String post(UserRoleEntity userRoleEntity, Integer timeOut) {
        return  HttpRequest.post(userRoleEntity.getDomain() + userRoleEntity.getUrl())
                .headerMap(JSONUtil.toBean(userRoleEntity.getHeader(), Map.class), true)
                .timeout(timeOut)
                .body(userRoleEntity.getBody())
                .execute().body();
    }
}
