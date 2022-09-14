package com.yun.bidatastorage.util;

import cn.hutool.db.sql.SqlExecutor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * SQL 工具类
 *
 * @author Yun
 */
public class SqlUtil {

    /**
     * 判断表是否存在
     */
    private static final String TABLE_EXIST = "SELECT COUNT(*) FROM information_schema.TABLES WHERE table_name ='${tableName}';";

    public static boolean doesTheTableExist(String tableName, Connection connection) {
        try {
            return SqlExecutor.execute(connection, TABLE_EXIST.replace("#{tableName}", tableName)) > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }

    /**
     * 清空表数据
     */
    private static final String EMPTY_TABLE = "truncate table ${tableName}";

    public static boolean truncateTable(String tableName, Connection connection) {
        try {
            return SqlExecutor.execute(connection, EMPTY_TABLE.replace("#{tableName}", tableName)) > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }

    /**
     * 删除表
     */
    private static final String DROP_TABLE = "drop table if exists ${tableName};";

    public static boolean dropTable(String tableName, Connection connection) {
        try {
            return SqlExecutor.execute(connection, DROP_TABLE.replace("#{tableName}", tableName)) > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }

    /**
     * 创建表
     */
    private static final String CREATE_TABLE = "CREATE TABLE ${tableName}( ${createSql} )  character set utf8mb4;";

    public static boolean createTable(String tableName, Connection connection, String sql) {
        try {
            return SqlExecutor.execute(connection, CREATE_TABLE.replace("#{tableName}", tableName).replace("${createSql}", sql)) > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }

    /**
     * 插入数据
     */
    private static final String INSERT_TABLE = "CREATE TABLE ${tableName}( ${createSql} )  character set utf8mb4;";

    public static boolean insetTable(String tableName, Connection connection, String sql) {
        try {
            return SqlExecutor.execute(connection, INSERT_TABLE.replace("#{tableName}", tableName).replace("${createSql}", sql)) > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }
}
