package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.UserTrend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = CmfzDemoApplication.class)

 public class CmfzDemoApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        userDao.getDbType("女").forEach(i-> System.out.println(i));


        //查询功能
       /* List<Admin> list = adminDao.selectAll();
        for (Admin admin : list) {
            System.out.println(admin);
        }
*/

        //根据id删除 对象（需要绑定）

       /* int i = adminDao.delete(admin);
        System.out.println(i);*/

       //修改
      //  adminDao.updateByExampleSelective(admin);







    }

}
