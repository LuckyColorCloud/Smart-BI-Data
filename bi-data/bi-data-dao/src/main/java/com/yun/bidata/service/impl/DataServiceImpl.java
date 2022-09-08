package com.yun.bidata.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yun.bidata.api.dto.QueryDataDto;
import com.yun.bidata.entity.ApiPathEntity;
import com.yun.bidata.entity.UserRoleEntity;
import com.yun.bidata.exception.DataException;
import com.yun.bidata.service.ApiPathService;
import com.yun.bidata.service.DataService;
import com.yun.bidata.service.UserRoleService;
import com.yun.bidataconnmon.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

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


    @Override
    public Object getData(QueryDataDto dto) {
        List<ApiPathEntity> lists = apiPathService.getBaseMapper().selectList(new QueryWrapper<ApiPathEntity>().lambda().eq(ApiPathEntity::getUrl, dto.getPath()));
        if (lists == null || lists.size() < 1) {
            return Result.ERROR(Result.ResultEnum.INTERFACE_DOES_NOT_EXIST);
        } else if (lists.size() != 1) {
            return Result.ERROR(Result.ResultEnum.DIRTY_DATA);
        }
        ApiPathEntity apiPathEntity = lists.get(0);
        UserRoleEntity userRoleEntity = userRoleService.getById(apiPathEntity.getUserRoleId());

        return null;
    }


    private String queryToken(UserRoleEntity userRoleEntity) {
        String body;
        // 0.GET 1.POST def:抛异常  可自己扩展其他方式
        switch (userRoleEntity.getRequestType()) {
            case 0:
                body = HttpRequest.get(userRoleEntity.getDomain() + userRoleEntity.getUrl())
                        .headerMap(JSONUtil.toBean(userRoleEntity.getHeader(), Map.class), true)
                        .body(userRoleEntity.getBody())
                        .execute().body();
                break;
            case 1:
                body = HttpRequest.post(userRoleEntity.getDomain() + userRoleEntity.getUrl())
                        .headerMap(JSONUtil.toBean(userRoleEntity.getHeader(), Map.class), true)
                        .body(userRoleEntity.getBody())
                        .execute().body();
                break;
            default:
                throw new DataException();
        }
        return body;
    }

}
