package com.yun.bidatastorage.controller.feign;

import cn.hutool.core.util.ZipUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yun.bidataconnmon.util.Regular;
import com.yun.bidataconnmon.vo.Result;
import com.yun.bidatastorage.api.DataStorageApiFeign;
import com.yun.bidatastorage.dto.SaveDataDto;
import com.yun.bidatastorage.entity.DataSourceEntity;
import com.yun.bidatastorage.entity.SqlScriptEntity;
import com.yun.bidatastorage.entity.StorageTableEntity;
import com.yun.bidatastorage.enums.DataSourceFactory;
import com.yun.bidatastorage.service.DataSourceService;
import com.yun.bidatastorage.service.SqlScriptService;
import com.yun.bidatastorage.service.StorageTableService;
import com.yun.bidatastorage.util.SqlUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
    @Autowired
    SqlScriptService sqlScriptService;
    @Autowired
    StorageTableService storageTableService;

    @Override
    public Result<Object> querySourceList() {
        return null;
    }

    private static final String SQL = "INSERT IGNORE INTO ${table}  ${key} VALUES ${value}";

    @Override
    public Result<Object> saveData(SaveDataDto saveDataDto) {
        StorageTableEntity storageTable = storageTableService.getById(saveDataDto.getStorageId());
        if (storageTable == null) {
            return Result.ERROR(Result.ResultEnum.STORAGE_TABLE_DOES_NOT_EXIST);
        }
        DataSourceEntity dataSourceEntity = dataSourceService.getById(storageTable.getSourceId());
        if (dataSourceEntity == null) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_DOES_NOT_EXIST);
        }
        try (Connection connection = getConnection(dataSourceEntity)) {
            boolean b = SqlUtil.doesTheTableExist(storageTable.getSaveName(), connection);
            String body = ZipUtil.unGzip(saveDataDto.getContext(), "UTF-8");
            JSONArray jsonArray = JSONUtil.parseArray(body);
            // 1.追加2.全量3.主键
            switch (storageTable.getSaveType()) {
                case 1:
                    if (b) {
                        if (jsonArray.size() > 0) {
                            String field = StringUtils.join(jsonArray.getJSONObject(0).keySet().toArray(), ",");
                            String sql = SQL.replace("${table}", storageTable.getSaveName()).replace("${key}", "(" + field + ")");
                            StringBuffer value = new StringBuffer();
                            for (int i = 0; i < jsonArray.size(); i++) {
                                ArrayList<Object> strings = new ArrayList<Object>(jsonArray.getJSONObject(i).values());
                                value.append("(");
                                for (int j = 0; j < strings.size(); j++) {
                                    value.append("'").append(strings.get(j)).append("'").append(j + 1 == strings.size() ? "" : ",");
                                }
                                value.append(")").append(i + 1 != jsonArray.size() ? "," : "");
                            }
                            String replace = sql.replace("${value}", value.toString());
                        }
                    }
                    break;
                case 2:

                    break;
                case 3:
                    break;
                default:
                    break;

            }
        } catch (SQLException e) {
            return Result.ERROR(Result.ResultEnum.FAILED_TO_CREATE_DATA_SOURCE_LINK);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_TYPE_DOES_NOT_EXIST);
        }

        return null;
    }

    @Override
    public Result<Object> querySql(Integer sqlId) {
        SqlScriptEntity sqlScriptEntity = sqlScriptService.getById(sqlId);
        //SQL 不存在 返回
        if (sqlScriptEntity == null) {
            return Result.ERROR(Result.ResultEnum.SCRIPT_DOES_NOT_EXIST);
        }
        DataSourceEntity dataSourceEntity = dataSourceService.getById(sqlScriptEntity.getSourceId());
        //数据源不存在 直接返回
        if (dataSourceEntity == null) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_DOES_NOT_EXIST);
        }
        try (Connection connection = getConnection(dataSourceEntity)) {
            //获取链接
            //进行匹配 SQL 是否有敏感操作 避免数据问题
            Matcher matcher = Regular.PATTERN_SENSITIVE_SQL.matcher(sqlScriptEntity.getSql());
            if (matcher.find()) {
                return Result.ERROR(Result.ResultEnum.PROHIBIT_SENSITIVE_OPERATIONS);
            }
            //执行SQL 查询结果
            List<Entity> query = SqlExecutor.query(connection, sqlScriptEntity.getSql(), new EntityListHandler());
            return Result.OK(query);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_TYPE_DOES_NOT_EXIST);
        } catch (SQLException sqlException) {
            return Result.ERROR(Result.ResultEnum.FAILED_TO_CREATE_DATA_SOURCE_LINK);
        } catch (Exception e) {
            return Result.ERROR(Result.ResultEnum.UNKNOWN_EXCEPTION);
        }
    }

    /**
     * 获取连接池方法
     *
     * @param dataSourceEntity
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    private Connection getConnection(DataSourceEntity dataSourceEntity) throws
            SQLException, IllegalArgumentException {
        DataSourceFactory dataSourceFactory;
        //判断是否是 druid 支持的数据源类型 支持使用默认的 不支持 查询是否有自行扩展的
        if (DataSourceFactory.isDruid(dataSourceEntity.getType())) {
            dataSourceFactory = DataSourceFactory.DRUID;
        } else {
            dataSourceFactory = DataSourceFactory.valueOf(dataSourceEntity.getType().toUpperCase());
        }
        //获取链接
        return dataSourceFactory.creationConnection(dataSourceEntity);
    }
}
