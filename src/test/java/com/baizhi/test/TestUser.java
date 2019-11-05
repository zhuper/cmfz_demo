package com.baizhi.test;

import com.baizhi.dao.BannerDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.UserTrend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestUser {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BannerDao bannerDao;

    @Test
    public void test(){
        System.out.println(userDao);
        List<UserTrend> man = userDao.getDbType("男");
        /*for (UserTrend u : man) {
            System.out.println(u);
        }*/
    }

    @Test
    public void test1(){
        bannerDao.selectAll().forEach(u -> System.out.println(u));
    }
}
