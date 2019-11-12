package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    /**
     * 登陆方法
     */

   /* @Override
    public void login(HttpServletRequest request, Admin admin,String inputCode) {
        //获得session
     HttpSession session= request.getSession();
     // 把验证码存进session
     String securityCode= (String) session.getAttribute("securityCode");
     //判断code是否为空，如果正确
        if(securityCode.equals(inputCode)){
            //把对象存进session
                Admin admin1 = adminDao.selectOne(admin);
            if (admin1!=null){
            session.setAttribute("loginAdmin",admin1);
            }else {
                throw new RuntimeException("用户名或密码错误");
            }
        }else {  //否则
            throw new RuntimeException("验证码错误");
        }
    }*/

    /**
     * 注册功能
     * @param admin
     * @return
     */
    @Override
    public void registered(HttpServletRequest request,Admin admin) {
        HttpSession session= request.getSession();
        //获得id
        try {
            String id = UUID.randomUUID().toString();
            admin.setId(id);
            adminDao.insert(admin);
            if (admin.getUsername().equals("")) throw new RuntimeException("昵称不能为空");
            //登陆信息存session
            session.setAttribute("loginAdmin",admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
