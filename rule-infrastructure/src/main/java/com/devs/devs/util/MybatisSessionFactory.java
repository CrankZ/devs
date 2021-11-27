package com.devs.devs.util;

import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.io.Reader;

@Log
public class MybatisSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;

    private MybatisSessionFactory() {
        super();
    }

    public synchronized static SqlSessionFactory getSqlSessionFactory() {
        if (null == sqlSessionFactory) {
            InputStream inputStream = null;
            try {
                Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("create MybatisSessionFactory read mybatis-config.xml cause Exception....");
            }
            if (null != sqlSessionFactory) {
                log.info("get Mybatis sqlsession sucessed....");
            } else {
                log.info("get Mybatis sqlsession failed....");
            }
        }
        return sqlSessionFactory;
    }
}