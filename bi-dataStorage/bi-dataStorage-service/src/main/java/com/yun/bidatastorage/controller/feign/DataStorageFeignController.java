package com.yun.bidatastorage.controller.feign;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.yun.bidataconnmon.util.Regular;
import com.yun.bidataconnmon.vo.Result;
import com.yun.bidatastorage.api.DataStorageApiFeign;
import com.yun.bidatastorage.dto.QuerySourceDto;
import com.yun.bidatastorage.entity.DataSourceEntity;
import com.yun.bidatastorage.enums.DataSourceFactory;
import com.yun.bidatastorage.service.DataSourceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author Yun
 */
@RestController
@RequestMapping("/open/storage/api")
@Api(tags = "Data feign服务接口")
@Slf4j
public class DataStorageFeignController implements DataStorageApiFeign {
    @Autowired
    DataSourceService dataSourceService;


    @Override
    public Result<Object> querySourceList() {
        return null;
    }

    @Override
    public Result<Object> saveData() {
        return null;
    }

    @Override
    public Result<Object> querySql(QuerySourceDto querySourceDto) {
        DataSourceEntity dataSourceEntity = dataSourceService.getById(querySourceDto.getSourceId());
        //数据源不存在 直接返回
        if (dataSourceEntity == null) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_DOES_NOT_EXIST);
        }
        Connection connection = null;
        try {
            DataSourceFactory dataSourceFactory;
            //判断是否是 druid 支持的数据源类型 支持使用默认的 不支持 查询是否有自行扩展的
            if (DataSourceFactory.isDruid(dataSourceEntity.getType())) {
                dataSourceFactory = DataSourceFactory.DRUID;
            } else {
                dataSourceFactory = DataSourceFactory.valueOf(dataSourceEntity.getType().toUpperCase());
            }
            //获取链接
            connection = dataSourceFactory.creationConnection(dataSourceEntity);
            //进行匹配 SQL 是否有敏感操作 避免数据问题
            Matcher matcher = Regular.PATTERN_SENSITIVE_SQL.matcher(querySourceDto.getSql());
            if (matcher.find()) {
                return Result.ERROR(Result.ResultEnum.PROHIBIT_SENSITIVE_OPERATIONS);
            }
            //执行SQL 查询结果
            List<Entity> query = SqlExecutor.query(connection, querySourceDto.getSql(), new EntityListHandler());
            return Result.OK(query);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_TYPE_DOES_NOT_EXIST);
        } catch (SQLException sqlException) {
            return Result.ERROR(Result.ResultEnum.FAILED_TO_CREATE_DATA_SOURCE_LINK);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

}
