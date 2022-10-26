package com.yun.bidata.enums;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jayway.jsonpath.JsonPath;
import com.yun.bidatacommon.constant.CommonConstant;
import com.yun.bidatacommon.vo.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 格式转换工厂
 *
 * @author Yun
 */
@SuppressWarnings({"AlibabaEnumConstantsMustHaveComment", "unchecked"})
public enum FormatConversion {

    /**
     * 基本类型图表   {"a":"a","c":"c","b":"b"}===>{"name":"a","value":"b"} 集合类型同理  params===>{"a":"name","b":"value"}
     */
    CHART {
        @Override
        public Result<Object> conversion(String data, String params) {
            //判断是否为 对象
            if (JSONUtil.isTypeJSONObject(data)) {
                JSONObject jsonObject = JSONUtil.parseObj(data);
                HashMap<String, Object> hashMap = JSONUtil.toBean(data, HashMap.class);
                Set<String> keys = hashMap.keySet();
                HashMap<String, Object> resultMap = new HashMap<>();
                //进行转换key 并组装
                keys.forEach(key -> resultMap.put(hashMap.get(key).toString(), jsonObject.get(key)));
                return Result.OK(resultMap);
            } else if (JSONUtil.isTypeJSONArray(data)) {
                ArrayList<HashMap<String, Object>> resultMapList = new ArrayList<>();
                JSONArray jsonArray = JSONUtil.parseArray(data);
                HashMap<String, Object> hashMap = JSONUtil.toBean(data, HashMap.class);
                Set<String> keys = hashMap.keySet();
                jsonArray.parallelStream().map(JSONObject::new).forEach(t -> {
                    HashMap<String, Object> tempMap = new HashMap<>();
                    keys.forEach(key -> tempMap.put(hashMap.get(key).toString(), t.get(key)));
                    resultMapList.add(tempMap);
                });
                return Result.OK(resultMapList);
            } else {
                return Result.ERROR(Result.ResultEnum.NO_SUCH_DATA_PROCESSING_TYPE);
            }
        }
    },
    /**
     * 对象转集合
     * join支持多个参数
     * 但对象转list集合并添加元素,{"fireProbability":"10%","flood":"5%"} ===> "[{"name":"火灾","value":"10%"},{"name":"洪水","value":"5%"}]"      params===> "[{"fireProbability":"value","join":{"name":"火灾"}},{"flood":"value","join":{"name":"洪水"}}]"
     */
    OBJECT_TO_COLLECTION {
        @Override
        public Result<Object> conversion(String data, String params) {
            //判断是否为 对象
            if (JSONUtil.isTypeJSONObject(data)) {
                JSONObject jsonObject = JSONUtil.parseObj(data);
                ArrayList<String> keys = new ArrayList<>(jsonObject.keySet());
                //参数转换成集合  这里肯定是集合
                JSONArray jsonArray = JSONUtil.parseArray(params);
                //结果
               Object collect = jsonArray.parallelStream().map(JSONObject::new).map(t ->
                {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    keys.forEach(key -> {
                        hashMap.put(t.getStr(key), jsonObject.get(key));
                    });
//                    //判断是否有额外补充参数
                    if (t.get(CommonConstant.JOIN) != null) {
                        hashMap.putAll(JSONUtil.toBean(t.getStr(CommonConstant.JOIN), HashMap.class));
                    }
                    return hashMap;
                }).collect(Collectors.toList());
                return Result.OK(collect);
            } else {
                return Result.ERROR(Result.ResultEnum.NO_SUCH_DATA_PROCESSING_TYPE);
            }
        }
    },
    /**
     * 柱状图  不存在非集合情况!!!
     * 将一个集合 转为前端柱状图形式,
     * {"total":123,"list":[{"name":张三,"age":18,"value":1},{"name":李四,"age":23,"value":2},{"name":王五,"age":18,"value":3}]}===>{"total":123,"data":[18,23,18],"name":["张三","李四","王五"]}===>
     * params===>{"ppr":[{"key":"total","jsonPath":"$..total"}],"name":"name","data":"data"} 理论上只能有一个key{"key":"total","jsonPath":"$..total"} 这里面除了jsonPath只会有一个
     * 可同时做字段转换名称
     */
    HISTOGRAM {
        @Override
        public Result<Object> conversion(String data, String params) {
            //判断是否为集合类型
            if (JSONUtil.isTypeJSONArray(data)) {
                //数据
                JSONArray jsonArray = JSONUtil.parseArray(data);
                //转换参数
                JSONObject jsonObject = JSONUtil.parseObj(params);
                //公共参数
                JSONArray common = jsonObject.getJSONArray(CommonConstant.PARENT_PARAMETER);
                jsonObject.remove(CommonConstant.PARENT_PARAMETER);
                ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
                List<String> keys = jsonObject.keySet().stream().map(String::valueOf).collect(Collectors.toList());
                jsonArray.parallelStream().map(JSONObject::new).forEach(t -> {
                    keys.forEach(key -> map.put(jsonObject.getStr(key), t.get(key)));
                });
                if (!common.isEmpty()){
                    common.parallelStream().map(JSONObject ::new).forEach(t->{
                        map.put(t.getStr(CommonConstant.KEY), JsonPath.read(data,t.getStr(CommonConstant.JSON_PATH)));
                    });
                }
                return Result.OK(map);
            } else {
                return Result.ERROR(Result.ResultEnum.DATA_FORMAT_ERROR);
            }
        }
    }
    ;

    /**
     * 转换工厂
     *
     * @param data   数据
     * @param params 参数
     * @return 结果
     */
    public abstract Result<Object> conversion(String data, String params);
}
