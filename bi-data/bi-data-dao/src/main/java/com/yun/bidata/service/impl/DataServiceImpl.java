package com.yun.bidata.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jayway.jsonpath.JsonPath;
import com.yun.apimanage.api.ApiManageFeign;
import com.yun.apimanage.dto.ProjectDto;
import com.yun.bidata.dto.FormatDto;
import com.yun.bidata.dto.QueryDataDto;
import com.yun.bidata.dto.RequestDto;
import com.yun.bidata.entity.IndexConfigEntity;
import com.yun.bidata.entity.UserRoleEntity;
import com.yun.bidata.enums.FormatConversion;
import com.yun.bidata.enums.RequestEnum;
import com.yun.bidata.exception.DataException;
import com.yun.bidata.service.DataService;
import com.yun.bidata.service.IndexConfigService;
import com.yun.bidata.service.UserRoleService;
import com.yun.bidata.util.RequestUtil;
import com.yun.bidatacommon.constant.CommonConstant;
import com.yun.bidatacommon.util.JavaFormat;
import com.yun.bidatacommon.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 数据清洗接口
 *
 * @author Yun
 */
@Slf4j
@Service("dataServiceImpl")
public class DataServiceImpl implements DataService {
    @Autowired
    IndexConfigService indexConfigService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    ApiManageFeign apiManageFeign;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Value("${smart.data.timeOut:3000}")
    private Long timeOut;

    /**
     * 远程调用获取实时数据
     *
     * @param dto 获取数据类
     * @return 数据结果
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public Result<Object> getData(QueryDataDto dto) {
        IndexConfigEntity indexConfigEntity = indexConfigService.getById(dto.getIndexId());
        //指标是否存在
        if (indexConfigEntity == null) {
            return Result.ERROR(Result.ResultEnum.INTERFACE_DOES_NOT_EXIST);
        }
        //获取需要的token 角色配置信息
        UserRoleEntity userRoleEntity = userRoleService.getById(indexConfigEntity.getTokenId());
        //数据库无此角色 抛出异常
        if (userRoleEntity == null && indexConfigEntity.getTokenId() != null) {
            return Result.ERROR(Result.ResultEnum.ROLE_TOKEN_DOES_NOT_EXIST);
        }
        //获取上级项目 公共信息
        Result<ProjectDto> result = apiManageFeign.queryProjectById(indexConfigEntity.getProjectId());
        if (result == null || !result.isSuccess()) {
            return Result.ERROR(result.getCode(), result.getMessage());
        }
        ProjectDto project = result.getResult();
        try {
            //根据角色获取Token
            String token = userRoleEntity == null ? "" : queryToken(userRoleEntity);
            //获取请求信息
            RequestDto requestDto = new RequestDto();
            requestDto.setUrl(project.getDomain() + indexConfigEntity.getUrl());
            requestDto.setHeaders(RequestUtil.apply(project.getHeaders(), indexConfigEntity.getPrivateHeader(), new HashMap<String, Object>(1) {{
                put(project.getTokenKey(), token);
            }}));
            //TODO 缺少动态参数
            //请求返回结果
            String resultBody = RequestEnum.valueOf(indexConfigEntity.getRequestType().toUpperCase()).conversion(requestDto);
            //根据jsonPath 得到想要的数据集 初步处理
            String processResult = StrUtil.isEmpty(indexConfigEntity.getJsonPath()) ? resultBody : JsonPath.read(resultBody, indexConfigEntity.getJsonPath()).toString();
            //判断是否有需要拦截的字段
            JSONArray excludes = StrUtil.isEmpty(indexConfigEntity.getExclude()) ? null : JSONUtil.parseArray(indexConfigEntity.getExclude());
            //判断返回数据类型 拦截字段为空直接返回
            switch (indexConfigEntity.getType()) {
                case 0:
                    return commonType(excludes, processResult);
                case 1:
                    return grouping(excludes, processResult, indexConfigEntity.getMapKey());
                default:
                    return Result.ERROR(Result.ResultEnum.METRIC_TYPE_DOES_NOT_EXIST);
            }
        } catch (DataException e) {
            return Result.ERROR(e.getMessage());
        } catch (Exception e) {
            return Result.ERROR(Result.ResultEnum.ROLE_TOKEN_DOES_NOT_EXIST);
        }
    }

    /**
     * 分组类型
     *
     * @param excludes      过滤 需要字段
     * @param processResult jsonPath初步处理值
     * @param mapKey        分组参数
     * @return 结果
     */
    private Result<Object> grouping(JSONArray excludes, String processResult, String mapKey) {
        //分组的情况肯定是集合,如不是集合 或 不存在mapKey参数 即不合规范 直接返回
        if (JSONUtil.isTypeJSONArray(processResult) && JSONUtil.isTypeJSONObject(mapKey)) {
            JSONArray jsonArray = JSONUtil.parseArray(processResult);
            JSONObject jsonObject = JSONUtil.parseObj(mapKey);
            ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>(jsonArray.size());
            //拦截需要的key
            jsonArray.parallelStream().map(JSONObject::new).forEach(json -> {
                HashMap<String, Object> hashMap = new HashMap<>(excludes.size());
                excludes.parallelStream().map(String::valueOf).forEach(t -> hashMap.put(t, json.get(t)));
                hashMaps.add(hashMap);
            });
            return Result.OK(hashMaps.stream().collect(Collectors.groupingBy(t->t.get(jsonObject.get(CommonConstant.GROUPING)))));
        } else {
            //非JSON 情况无法落库!!! 可以由人工扩展选择存到redis等
            return Result.OK(processResult);
        }
    }

    /**
     * 普通类型
     *
     * @param excludes      过滤 需要的字段
     * @param processResult jsonPath初步处理的值
     * @return 结果
     */
    private Result<Object> commonType(JSONArray excludes, String processResult) {
        if (excludes != null && excludes.size() > 0) {
            if (JSONUtil.isTypeJSONObject(processResult)) {
                JSONObject jsonObject = JSONUtil.parseObj(processResult);
                HashMap<String, Object> hashMap = new HashMap<>(excludes.size());
                if (!jsonObject.isEmpty()) {
                    //拦截需要的key
                    excludes.parallelStream().map(String::valueOf).forEach(t -> hashMap.put(t, jsonObject.get(t)));
                }
                return Result.OK(hashMap);
            } else if (JSONUtil.isTypeJSONArray(processResult)) {
                JSONArray jsonArray = JSONUtil.parseArray(processResult);
                ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>(jsonArray.size());
                //拦截需要的key
                jsonArray.parallelStream().map(JSONObject::new).forEach(json -> {
                    HashMap<String, Object> hashMap = new HashMap<>(excludes.size());
                    excludes.parallelStream().map(String::valueOf).forEach(t -> hashMap.put(t, json.get(t)));
                    hashMaps.add(hashMap);
                });
                return Result.OK(hashMaps);
            } else {
                //非JSON 情况无法落库!!! 可以由人工扩展选择存到redis等
                return Result.OK(processResult);
            }
        } else {
            return Result.OK(JSONUtil.parse(processResult));
        }
    }

    /**
     * 格式转换
     *
     * @param dto 数据
     * @return 结果数据
     */
    @Override
    public Result<Object> formatConversion(FormatDto dto) {
        FormatConversion formatConversion;
        try {
            try {
                formatConversion = FormatConversion.valueOf(dto.getChartType().toUpperCase());
            } catch (IllegalArgumentException e) {
                formatConversion = FormatConversion.valueOf(JavaFormat.javaToMysql(dto.getChartType()).toUpperCase());
            }
            return formatConversion.conversion(dto.getData(), dto.getParams());
        } catch (Exception e) {
            return Result.ERROR(Result.ResultEnum.FORMAT_CONVERSION_ERROR);
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
                    //请求对象
                    RequestDto requestDto = new RequestDto();
                    requestDto.setTimeOut(timeOut);
                    requestDto.setHeaders(userRoleEntity.getHeader());
                    requestDto.setBody(userRoleEntity.getBody());
                    requestDto.setUrl(userRoleEntity.getUrl());
                    String temp;
                    try {
                        temp = RequestEnum.valueOf(userRoleEntity.getRequestType().toUpperCase()).conversion(requestDto);
                    } catch (IllegalArgumentException e) {
                        log.info("该类型请求类型不存在:{}", userRoleEntity.getRequestType());
                        return null;
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
