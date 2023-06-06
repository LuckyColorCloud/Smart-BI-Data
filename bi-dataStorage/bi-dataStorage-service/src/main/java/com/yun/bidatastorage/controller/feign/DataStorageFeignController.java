package com.yun.bidatastorage.controller.feign;

import cn.hutool.core.util.ZipUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yun.bidatacommon.util.Regular;
import com.yun.bidatacommon.model.vo.Result;
import com.yun.bidatastorage.api.DataStorageApiFeign;
import com.yun.bidatastorage.dto.DropTableDto;
import com.yun.bidatastorage.dto.SaveDataDto;
import com.yun.bidatastorage.dto.SaveFileDto;
import com.yun.bidatastorage.entity.DataSourceEntity;
import com.yun.bidatastorage.entity.SqlScriptEntity;
import com.yun.bidatastorage.entity.StorageTableEntity;
import com.yun.bidatastorage.enums.DataSourceFactory;
import com.yun.bidatastorage.service.DataSourceService;
import com.yun.bidatastorage.service.SqlScriptService;
import com.yun.bidatastorage.service.StorageTableService;
import com.yun.bidatastorage.util.SqlUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * @author Yun
 */
@RestController
@RequestMapping("/open/storage/api")
@Tag(name = "Data feign服务接口")
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


    @Override
    public Result<Object> saveData(SaveDataDto saveDataDto) {
        StorageTableEntity storageTable = storageTableService.getById(saveDataDto.getStorageId());
        //判断是否存在 存库实体
        if (storageTable == null) {
            return Result.ERROR(Result.ResultEnum.STORAGE_TABLE_DOES_NOT_EXIST);
        }
        //判断数据源是否存在
        DataSourceEntity dataSourceEntity = dataSourceService.getById(storageTable.getSourceId());
        if (dataSourceEntity == null) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_DOES_NOT_EXIST);
        }
        //获取数据源
        try (Connection connection = getConnection(dataSourceEntity)) {
            boolean b = SqlUtil.doesTheTableExist(storageTable.getSaveName(), connection);
            //解压缩 还原成string
            String body = ZipUtil.unGzip(saveDataDto.getContext(), saveDataDto.getCharset());
            //转换成list
            JSONArray jsonArray = JSONUtil.parseArray(body);
            //判断需要保存的 list
            JSONArray storageTableArray = JSONUtil.parseArray(storageTable.getStorageField().isEmpty() ? "[]" : storageTable.getStorageField());
            //进行过滤保存的list
            jsonArray = filter(jsonArray, storageTableArray);
            // 1.追加 2.全量 3.主键
            switch (storageTable.getSaveType()) {
                //1.追加方式
                case 1:
                    if (b) {
                        if (jsonArray.size() > 0) {
                            SqlUtil.insetTable(storageTable.getSaveName(), connection, jsonArray);
                        } else {
                            return Result.ERROR(Result.ResultEnum.DATA_NOT_OBTAINED);
                        }
                    } else {
                        if (SqlUtil.createTable(storageTable.getSaveName(), connection, jsonArray.getJSONObject(0))) {
                            SqlUtil.insetTable(storageTable.getSaveName(), connection, jsonArray);
                        } else {
                            return Result.ERROR(Result.ResultEnum.FAILED_TO_CREATE_TABLE);
                        }
                    }
                    break;
                // 全量方式
                case 2:
                    if (b) {
                        if (jsonArray.size() > 0 && SqlUtil.truncateTable(storageTable.getSaveName(), connection)) {
                            SqlUtil.insetTable(storageTable.getSaveName(), connection, jsonArray);
                        } else {
                            return Result.ERROR(Result.ResultEnum.FAILED_TO_CLEAR_TABLE_DATA);
                        }
                    } else {
                        if (SqlUtil.createTable(storageTable.getSaveName(), connection, jsonArray.getJSONObject(0)) && SqlUtil.truncateTable(storageTable.getSaveName(), connection)) {
                            SqlUtil.insetTable(storageTable.getSaveName(), connection, jsonArray);
                        } else {
                            return Result.ERROR(Result.ResultEnum.FAILED_TO_CREATE_TABLE);
                        }
                    }
                    break;
                //3.主键方式
                case 3:
                    if (b) {
                        if (jsonArray.size() > 0) {
                            SqlUtil.insetTableIgnore(storageTable.getSaveName(), connection, jsonArray);
                        } else {
                            return Result.ERROR(Result.ResultEnum.FAILED_TO_CLEAR_TABLE_DATA);
                        }
                    } else {
                        if (SqlUtil.createTable(storageTable.getSaveName(), connection, jsonArray.getJSONObject(0), storageTable.getPrimaryKey())) {
                            SqlUtil.insetTableIgnore(storageTable.getSaveName(), connection, jsonArray);
                        }
                    }
                    break;
                default:
                    return Result.ERROR(Result.ResultEnum.NO_SUCH_DATA_PROCESSING_TYPE);
            }
        } catch (SQLException e) {
            return Result.ERROR(Result.ResultEnum.FAILED_TO_CREATE_DATA_SOURCE_LINK);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_TYPE_DOES_NOT_EXIST);
        }

        return Result.ERROR(Result.ResultEnum.UNKNOWN_EXCEPTION);
    }

    /**
     * 查询SQL结果
     *
     * @param sqlId sqlid
     * @return 查询结果
     */
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

    @Override
    public Result<Object> saveFile(SaveFileDto saveFileDto) {
        //判断数据源是否存在
        DataSourceEntity dataSourceEntity = dataSourceService.getById(saveFileDto.getSourceId());
        if (dataSourceEntity == null) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_DOES_NOT_EXIST);
        }
        //获取数据源
        try (Connection connection = getConnection(dataSourceEntity)) {
            //判断表是否存在
            boolean b = SqlUtil.doesTheTableExist(saveFileDto.getSaveName(), connection);
            //解压缩 还原成string
            String body = ZipUtil.unGzip(saveFileDto.getContext(), saveFileDto.getCharset());
            //转换成list
            JSONArray jsonArray = JSONUtil.parseArray(body);
            if (b) {
                //表存在 直接返回异常
                return Result.ERROR(Result.ResultEnum.THE_FILE_ENTRY_TABLE_ALREADY_EXISTS);
            } else {
                //创建表 并插入数据
                if (SqlUtil.createTable(saveFileDto.getSaveName(), connection, jsonArray.getJSONObject(0)) && SqlUtil.truncateTable(saveFileDto.getSaveName(), connection)) {
                    SqlUtil.insetTable(saveFileDto.getSaveName(), connection, jsonArray);
                } else {
                    return Result.ERROR(Result.ResultEnum.FAILED_TO_CREATE_TABLE);
                }
            }
        } catch (SQLException e) {
            return Result.ERROR(Result.ResultEnum.FAILED_TO_CREATE_DATA_SOURCE_LINK);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_TYPE_DOES_NOT_EXIST);
        }
        return Result.ERROR(Result.ResultEnum.UNKNOWN_EXCEPTION);
    }

    @Override
    public Result<Object> dropTable(DropTableDto dropTable) {
        //判断数据源是否存在
        DataSourceEntity dataSourceEntity = dataSourceService.getById(dropTable.getSourceId());
        if (dataSourceEntity == null) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_DOES_NOT_EXIST);
        }
        //获取数据源
        try (Connection connection = getConnection(dataSourceEntity)) {
            boolean b = SqlUtil.dropTable(dropTable.getTableName(), connection);
            if (b) {
                return Result.OK();
            } else {
                return Result.ERROR(Result.ResultEnum.DROP_TABLE_ERROR);
            }
        } catch (SQLException e) {
            return Result.ERROR(Result.ResultEnum.FAILED_TO_CREATE_DATA_SOURCE_LINK);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Result.ERROR(Result.ResultEnum.DATA_SOURCE_TYPE_DOES_NOT_EXIST);
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

    /**
     * 拦截需要保存的字段
     *
     * @param jsonArray 初始数据
     * @param filter    需要的字段
     * @return 过滤结构
     */
    private JSONArray filter(JSONArray jsonArray, JSONArray filter) {
        if (jsonArray.isEmpty() || filter.isEmpty()) {
            return jsonArray;
        }
        JSONArray result = new JSONArray();
        Set<String> sets = filter.stream().map(String::valueOf).collect(Collectors.toSet());
        jsonArray.parallelStream().map(JSONObject::new).forEach(t -> {
            JSONObject jsonObject = new JSONObject();
            sets.parallelStream().forEach(t1 -> {
                jsonObject.set(t1, t.get(t1));
            });
            result.put(jsonObject);
        });
        return result;
    }
}
