package com.fyp.service.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class DruidDataSourceFactory implements DataSourceFactory {

    private Properties props;

    public void setProperties(Properties properties) {
        this.props = properties;
    }

    private String getProperty(String name){
        return this.props.getProperty(name);
    }

    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName(getProperty("driver"));
        dataSource.setUrl(getProperty("url"));
        dataSource.setUsername(getProperty("username"));
        dataSource.setPassword(getProperty("password"));

        try {
            dataSource.init();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return dataSource;
    }
}
