package com.yun.bidata.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jayway.jsonpath.JsonPath;
import com.yun.bidata.api.dto.QueryDataDto;
import com.yun.bidata.entity.ApiPathEntity;
import com.yun.bidata.entity.UserRoleEntity;
import com.yun.bidata.exception.DataException;
import com.yun.bidata.service.ApiPathService;
import com.yun.bidata.service.DataService;
import com.yun.bidata.service.UserRoleService;
import com.yun.bidata.util.HttpUtil;
import com.yun.bidataconnmon.constant.CommonConstant;
import com.yun.bidataconnmon.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 数据清洗接口
 *
 * @author Yun
 */
@Service("dataServiceImpl")
public class DataServiceImpl implements DataService {
    @Autowired
    ApiPathService apiPathService;
    @Autowired
    UserRoleService userRoleService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Value("smart.data.timeOut:3000")
    private Integer timeOut;

    /**
     * 远程调用获取实时数据
     *
     * @param dto 获取数据类
     * @return 数据结果
     */
    @Override
    public Result<Object> getData(QueryDataDto dto) {
        ApiPathEntity apiPathEntity = apiPathService.getById(dto.getApiId());
        //保证脏数据的情况下 不抛出异常 进行逻辑判断
        if (apiPathEntity == null) {
            return Result.ERROR(Result.ResultEnum.INTERFACE_DOES_NOT_EXIST);
        }
        //获取需要的token 角色配置信息
        UserRoleEntity userRoleEntity = userRoleService.getById(apiPathEntity.getUserRoleId());
        //数据库无此角色 抛出异常
        if (userRoleEntity == null) {
            return Result.ERROR(Result.ResultEnum.ROLE_TOKEN_DOES_NOT_EXIST);
        }
        //TODO 存储还未做 需要远程调用形式 调用 dataStorage
        try {
            //根据角色获取Token
            String token = queryToken(userRoleEntity);
            //替换占位符 为 token
            apiPathEntity.setPrivateHeader(apiPathEntity.getPrivateHeader().replace("#{token}", token));
            //如果有传参body 以传参为准 如没有使用默认参数
            apiPathEntity.setBody(StrUtil.isEmpty(dto.getParams()) || JSONUtil.parseObj(dto.getParams()).isEmpty() ? apiPathEntity.getBody() : dto.getParams());
            //请求返回结果
            String body = apiPathEntity.getRequestType().equals(0) ? HttpUtil.get(userRoleEntity, timeOut, apiPathEntity) : HttpUtil.post(userRoleEntity, timeOut, apiPathEntity);
            //根据jsonPath 得到想要的数据集 初步处理
            String result = StrUtil.isEmpty(apiPathEntity.getJsonPath()) ? body : JsonPath.read(body, apiPathEntity.getJsonPath()).toString();
            //判断是否有需要拦截的字段
            JSONArray excludes = StrUtil.isEmpty(apiPathEntity.getExclude()) ? null : JSONUtil.parseArray(apiPathEntity.getExclude());
            //判断返回数据类型 拦截字段为空直接返回
            if (excludes != null && excludes.size() > 0) {
                if (JSONUtil.isTypeJSONObject(result)) {
                    JSONObject jsonObject = JSONUtil.parseObj(result);
                    HashMap<String, Object> hashMap = new HashMap<>(excludes.size());
                    if (!jsonObject.isEmpty()) {
                        //拦截需要的key
                        excludes.parallelStream().map(String::valueOf).forEach(t -> hashMap.put(t, jsonObject.get(t)));
                    }
                    return Result.OK(hashMap);
                } else if (JSONUtil.isTypeJSONArray(result)) {
                    JSONArray jsonArray = JSONUtil.parseArray(result);
                    ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>(jsonArray.size());
                    //拦截需要的key
                    jsonArray.parallelStream().map(JSONObject::new).forEach(json -> {
                        HashMap<String, Object> hashMap = new HashMap<>(excludes.size());
                        excludes.parallelStream().map(String::valueOf).forEach(t -> hashMap.put(t, json.get(t)));
                        hashMaps.add(hashMap);
                    });
                    return Result.OK(hashMaps);
                } else {
                    return Result.OK(result);
                }
            } else {
                return Result.OK(JSONUtil.parse(result));
            }
        } catch (DataException e) {
            return Result.ERROR(e.getMessage());
        } catch (Exception e) {
            return Result.ERROR(Result.ResultEnum.ROLE_TOKEN_DOES_NOT_EXIST);
        }
    }

    /**
     * 获取角色token
     *
     * @param userRoleEntity 角色类
     * @return 返回请求结果
     */
    private String queryToken(UserRoleEntity userRoleEntity) {
        String result;
        if (redisTemplate.opsForValue().get(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId()) == null) {
            synchronized (userRoleEntity.getId().toString().intern()) {
                if (redisTemplate.opsForValue().get(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId()) == null) {
                    // 0.GET 1.POST def:抛异常  可自己扩展其他方式
                    String temp;
                    switch (userRoleEntity.getRequestType()) {
                        case 0:
                            temp = HttpUtil.get(userRoleEntity, timeOut);
                            break;
                        case 1:
                            temp = HttpUtil.post(userRoleEntity, timeOut);
                            break;
                        default:
                            throw new DataException();
                    }
                    //通过 jsonPath 获取需要的token 不在进行转换json获取
                    result = JsonPath.read(temp, userRoleEntity.getJsonPath()).toString();
                    //判断 token不为空 且 token需要缓存的情况
                    if (!StrUtil.isEmptyIfStr(result) && userRoleEntity.isCache()) {
                        //设置缓存到redis
                        redisTemplate.opsForValue().set(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId(), result, userRoleEntity.getCacheExpiration(), TimeUnit.MINUTES);
                    }
                } else {
                    result = (String) redisTemplate.opsForValue().get(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId());
                }
            }
        } else {
            result = (String) redisTemplate.opsForValue().get(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId());
        }
        return result;
    }

}
