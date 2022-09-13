package com.yun.bidatastorage.enums;

import com.alibaba.druid.pool.DruidDataSource;
import com.yun.bidatastorage.entity.DataSourceEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 数据源工厂模式
 *
 * @author Yun
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum DataSourceFactory {
    DRUID {
        @Override
        public Connection creationConnection(DataSourceEntity datasourceEntity) throws SQLException {
            DruidDataSource datasource = new DruidDataSource();
            datasource.setUrl(datasourceEntity.getUrl());
            datasource.setUsername(datasourceEntity.getUserName());
            datasource.setPassword(datasourceEntity.getPassWord());
            return datasource.getConnection();
        }
    };
    /**
     * 使用druid 支持的数据库 直接使用本方法  其他的自行扩展
     * mysql	支持，大规模使用
     * oracle	支持，大规模使用
     * sqlserver	支持
     * postgres	支持
     * db2	支持
     * h2	支持
     * derby	支持
     * sqlite	支持
     * sybase	支持
     */
    private static ArrayList<String> DRUID_LIST = new ArrayList<String>() {
        {
            add("mysql");
            add("oracle");
            add("sqlserver");
            add("postgres");
            add("db2");
            add("h2");
            add("derby");
            add("sqlite");
            add("sybase");
        }
    };

    public static boolean isDruid(String type) {
        return DRUID_LIST.contains(type.toLowerCase());
    }

    /**
     * 创建数据源链接
     *
     * @return
     */
    public abstract Connection creationConnection(DataSourceEntity datasourceEntity) throws SQLException;
}


