package com.fyp.service.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.InputStream;

public class MyBatisUtil {
    private static final String configFile = "mybatis-config.xml";

    private static SqlSessionFactory sqlSessionFactory = null;

    private static SqlSessionFactory createSqlSessionFactory(){
        try{
            if(sqlSessionFactory == null) {
                InputStream inputStream = Resources.getResourceAsStream(configFile);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
            return sqlSessionFactory;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static SqlSession getSession() {
        SqlSessionFactory factory = createSqlSessionFactory();
        return factory.openSession();
    }

}
