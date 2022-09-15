package com.yun.bidatastorage.util;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yun.bidataconnmon.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

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

    public static boolean createTable(String tableName, Connection connection, JSONObject jsonObject) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Set<String> validators = jsonObject.keySet();
            validators.forEach(t -> {
                String className = ClassUtil.getClassName(jsonObject.get(t), true);
                stringBuilder.append(" ").append(t).append(" ").append(className).append(" ").append(",");
            });
            return SqlExecutor.execute(connection, CREATE_TABLE.replace("#{tableName}", tableName).replace("${createSql}", stringBuilder)) > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }

    /**
     * 创建表 唯一索引方式
     */
    private static final String CREATE_TABLE_UNIQUE = "CREATE TABLE ${tableName}( ${createSql}  UNIQUE ( ${unique} ) )  character set utf8mb4;";

    public static boolean createTable(String tableName, Connection connection, JSONObject jsonObject, String unique) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<String> errors = new ArrayList<>();
            Set<String> validators = jsonObject.keySet();
            JSONArray jsonArray = JSONUtil.parseArray(unique);
            validators.forEach(t -> {
                String className = ClassUtil.getClassName(jsonObject.get(t), true);
                if (CommonConstant.TEXT.equalsIgnoreCase(javaToMysql(className))) {
                    errors.add(t);
                }
                stringBuilder.append(" ").append(t).append(" ").append(javaToMysql(className)).append(" ").append(",");
            });
            StringBuilder uniqueStr = new StringBuilder();
            jsonArray.forEach(t -> {
                if (!errors.contains(t.toString())) {
                    uniqueStr.append(t).append(",");
                }
            });
            System.out.println(CREATE_TABLE_UNIQUE.replace("${tableName}", tableName).replace("${createSql}", stringBuilder).replace("${unique}", uniqueStr.substring(0, uniqueStr.length() - 1)));
            return SqlExecutor.execute(connection, CREATE_TABLE_UNIQUE.replace("#{tableName}", tableName).replace("${createSql}", stringBuilder).replace("${unique}", uniqueStr.substring(0, uniqueStr.length() - 1))) > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }

    /**
     * 插入数据
     */
    private static final String INSERT_TABLE = "INSERT  INTO ${tableName}  ${key} VALUES ${insertSql};";

    public static boolean insetTable(String tableName, Connection connection, JSONArray jsonArray) {
        try {
            return SqlExecutor.execute(connection, INSERT_TABLE.replace("#{tableName}", tableName).replace("${insertSql}", infoSql(jsonArray, tableName, INSERT_TABLE))) > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }

    /**
     * 插入数据 忽略异常 用作主键更新
     */
    private static final String INSERT_TABLE_IGNORE = "INSERT IGNORE INTO ${table}  ${key} VALUES ${value};";

    public static boolean insetTableIgnore(String tableName, Connection connection, JSONArray jsonArray) {
        try {
            return SqlExecutor.execute(connection, INSERT_TABLE.replace("#{tableName}", tableName).replace("${insertSql}", infoSql(jsonArray, tableName, INSERT_TABLE_IGNORE))) > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }

    /**
     * sql 拼接方法
     *
     * @param jsonArray
     * @param tableName
     * @param initSql
     * @return
     */
    private static String infoSql(JSONArray jsonArray, String tableName, String initSql) {
        String field = StringUtils.join(jsonArray.getJSONObject(0).keySet().toArray(), ",");
        String temp = INSERT_TABLE_IGNORE.replace("${table}", tableName).replace("${key}", "(" + field + ")");
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            ArrayList<Object> strings = new ArrayList<Object>(jsonArray.getJSONObject(i).values());
            value.append("(");
            for (int j = 0; j < strings.size(); j++) {
                value.append("'").append(strings.get(j)).append("'").append(j + 1 == strings.size() ? "" : ",");
            }
            value.append(")").append(i + 1 != jsonArray.size() ? "," : "");
        }
        return temp.replace("${value}", value.toString());
    }

    private static String javaToMysql(String type) {
        switch (type) {
            case "Integer":
                return "int(11)";
            case "Boolean":
                return "bit(1)";
            case "Double":
                return "double";
            case "float":
                return "float";
            case "BigDecimal":
                return "decimal";
            case "Timestamp":
                return "timestamp";
            case "Date":
                return "date";
            case "String":
            default:
                return "Text";
        }
    }
}
