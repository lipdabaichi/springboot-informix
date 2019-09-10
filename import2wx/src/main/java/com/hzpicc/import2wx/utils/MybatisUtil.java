package com.hzpicc.import2wx.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

//创建SqlSesison 关闭SqlSesison
public class MybatisUtil {
    //创建SqlSessionFacroty工厂
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //读取mybatis-config.xml输入流
//            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            InputStream inputStream = Resources.getResourceAsStream("application.yml");
            //工厂读取配置文件
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建SqlSession对象
    public static SqlSession createSqlSession() {
        return sqlSessionFactory.openSession();
    }

    //关闭SqlSession  释放资源
    public static void closeSqlSession(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
