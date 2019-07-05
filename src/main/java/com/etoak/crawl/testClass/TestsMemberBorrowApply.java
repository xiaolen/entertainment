package com.etoak.crawl.testClass;



import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.apache.ibatis.io.Resources;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.io.InputStream;

public class TestsMemberBorrowApply {


    private SysUserMapper sysUserMapper;

    /**
     * 根据主键ID查询数据
     */
    @Test
    public void selectByPrimaryKey()throws IOException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // Resources类中的getResourceAsStream方法是读取配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapperConfig.xml");
        SqlSessionFactory sqlSessionFactory = builder.build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        sysUserMapper = sqlSession.getMapper(SysUserMapper.class);

        SysUser sysUser = sysUserMapper.selectByPrimaryKey();
        System.out.println(sysUser.toString());
    }

}
