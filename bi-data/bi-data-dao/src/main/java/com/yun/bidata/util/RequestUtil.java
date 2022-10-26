package com.yun.bidata.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Map;

/**
 * 请求拼接工具
 *
 * @author Yun
 */
public class RequestUtil {
    /**
     * 追加方法
     *
     * @param source 源数据
     * @param target 目标数据
     * @return 都有返回合并结果, 没有返回有的
     */
    public static String apply(String source, String target) {
        //都空
        if (StrUtil.isEmpty(source) && StrUtil.isEmpty(target)) {
            return "{}";
        }
        if (StrUtil.isEmpty(source)) {
            return target;
        } else if (StrUtil.isEmpty(target)) {
            return source;
        } else {
            JSONObject jsonObject = JSONUtil.parseObj(target);
            jsonObject.putAll(JSONUtil.parseObj(source));
            return JSONUtil.toJsonStr(jsonObject);
        }
    }

    /**
     * 追加方法
     *
     * @param source 源数据
     * @param target 目标数据
     * @param add    添加数据
     * @return 都有返回合并结果, 没有返回有的
     */
    public static String apply(String source, String target, Map<String, Object> add) {
        //都空
        if (StrUtil.isEmpty(source) && StrUtil.isEmpty(target)) {
            return JSONUtil.toJsonStr(add);
        }
        if (StrUtil.isEmpty(source)) {
            JSONObject jsonObject = JSONUtil.parseObj(target);
            jsonObject.putAll(add);
            return target;
        } else if (StrUtil.isEmpty(target)) {
            JSONObject jsonObject = JSONUtil.parseObj(source);
            jsonObject.putAll(add);
            return source;
        } else {
            JSONObject jsonObject = JSONUtil.parseObj(target);
            jsonObject.putAll(JSONUtil.parseObj(source));
            jsonObject.putAll(add);
            return JSONUtil.toJsonStr(jsonObject);
        }
    }

    /**
     * 统一格式 避免重复造轮子
     *
     * @param json json
     * @return jsonArray
     */
    public static JSONArray toJsonArray(String json) {
        JSONArray result = new JSONArray();
        if (!JSONUtil.isTypeJSON(json)) {
            return null;
        } else if (JSONUtil.isTypeJSONObject(json)) {
            result.add(JSONUtil.parseObj(json));
        } else {
            result = JSONUtil.parseArray(json);
        }
        return result;
    }
}
