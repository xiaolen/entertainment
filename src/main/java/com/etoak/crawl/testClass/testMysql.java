package com.etoak.crawl.testClass;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @Author: 秦渊渊
 * @Date: 2018/11/9 16:48
 */
public class testMysql {




    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据主键ID查询数据
     */
    @Test
    public void selectByPrimaryKey() {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey();
        System.out.println(sysUser.toString());
    }

}
