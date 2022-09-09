package com.yun.bidata.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 数据清洗接口
 *
 * @author Yun
 */
public class DataServiceImpl implements DataService {
    @Autowired
    ApiPathService apiPathService;
    @Autowired
    UserRoleService userRoleService;

    @Autowired
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
    public Object getData(QueryDataDto dto) {
        List<ApiPathEntity> lists = apiPathService.getBaseMapper().selectList(new QueryWrapper<ApiPathEntity>().lambda().eq(ApiPathEntity::getUrl, dto.getPath()));
        //保证脏数据的情况下 不抛出异常 进行逻辑判断
        if (lists == null || lists.size() < 1) {
            return Result.ERROR(Result.ResultEnum.INTERFACE_DOES_NOT_EXIST);
        } else if (lists.size() != 1) {
            return Result.ERROR(Result.ResultEnum.DIRTY_DATA);
        }
        //理论为唯一数据 可以从数据库加唯一索引避免判断
        ApiPathEntity apiPathEntity = lists.get(0);
        //获取需要的token 角色配置信息
        UserRoleEntity userRoleEntity = userRoleService.getById(apiPathEntity.getUserRoleId());
        //数据库无此角色 抛出异常
        if (userRoleEntity == null) {
            return Result.ERROR(Result.ResultEnum.ROLE_TOKEN_DOES_NOT_EXIST);
        }
        try {
            //根据角色获取Token
            String token = queryToken(userRoleEntity);
            //通过JSONPATH 获取需要的token 不在进行转换json获取
            Object jsonPathResult = JsonPath.read(token, userRoleEntity.getJsonPath());
            //判断 token不为空 且 token需要缓存的情况
            if (!StrUtil.isEmptyIfStr(jsonPathResult) && userRoleEntity.isCache()) {
                //设置缓存到redis
                redisTemplate.opsForValue().set(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId(), jsonPathResult, userRoleEntity.getCacheExpiration(), TimeUnit.MINUTES);
            }


        } catch (DataException e) {
            return Result.ERROR(e.getMessage());
        } catch (Exception e) {
            return Result.ERROR(Result.ResultEnum.ROLE_TOKEN_DOES_NOT_EXIST);
        }
        return null;
    }

    /**
     * 获取角色token
     *
     * @param userRoleEntity 角色类
     * @return 返回请求结果
     */
    private String queryToken(UserRoleEntity userRoleEntity) {
        String body;
        if (redisTemplate.opsForValue().get(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId()) == null) {
            synchronized (DataServiceImpl.class) {
                if (redisTemplate.opsForValue().get(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId()) == null) {
                    // 0.GET 1.POST def:抛异常  可自己扩展其他方式
                    switch (userRoleEntity.getRequestType()) {
                        case 0:
                            body = HttpUtil.get(userRoleEntity, timeOut);
                            break;
                        case 1:
                            body = HttpUtil.post(userRoleEntity, timeOut);
                            break;
                        default:
                            throw new DataException();
                    }
                } else {
                    body = (String) redisTemplate.opsForValue().get(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId());
                }
            }
        } else {
            body = (String) redisTemplate.opsForValue().get(CommonConstant.CACHE_TOKEN_HEAD + userRoleEntity.getId());
        }
        return body;
    }

}
