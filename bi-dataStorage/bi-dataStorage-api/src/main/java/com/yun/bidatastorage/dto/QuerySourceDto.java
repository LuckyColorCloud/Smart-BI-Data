package com.yun.bidatastorage.dto;

import lombok.Data;

/**
 * 查询数据库 DTO 对象
 *
 * @author Yun
 */
@Data
public class QuerySourceDto {
    /**
     * 数据源ID
     */
    private Integer sourceId;
    /**
     * 查询 sql
     */
    private String sql;
    /**
     * 扩展JSON
     */
    private String json;
}
