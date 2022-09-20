package com.yun.bidata.enums;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yun.bidataconnmon.constant.CommonConstant;
import com.yun.bidataconnmon.vo.Result;

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
        public Result<Object> conversion(String data, String type, String params) {
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
                jsonArray.parallelStream().map(JSONObject::new).forEach(t->{
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
    OBJECT_TO_COLLECTION{
        @Override
        public Result<Object> conversion(String data, String type, String params) {
            //判断是否为 对象
            if (JSONUtil.isTypeJSONObject(data)) {
                JSONObject jsonObject = JSONUtil.parseObj(data);
                ArrayList<String> keys = new ArrayList<>(jsonObject.keySet());
                //参数转换成集合  这里肯定是集合
                JSONArray jsonArray = JSONUtil.parseArray(params);
                //结果
                List<HashMap<String, Object>> collect = jsonArray.parallelStream().map(JSONObject::new).map(t ->
                {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    keys.forEach(key -> {
                        hashMap.put(t.getStr(key), jsonObject.get(key));
                    });
                    //判断是否有额外补充参数
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
    }

    ;


    public abstract Result<Object> conversion(String data, String type, String params);
}
