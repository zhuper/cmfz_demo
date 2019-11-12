package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin")
@Controller

public class AdminController {

    @Autowired
    private AdminService adminService;
    @RequestMapping("login")
    @ResponseBody
    public Map<String, Object> login(Admin admin, HttpServletRequest request,String inputCode) {
        //创建一个集合
        Map<String, Object> map = new HashMap<>();
        //创建主体
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(),admin.getPassword());
        String code1 = (String) request.getSession().getAttribute("securityCode");
        if (code1.equalsIgnoreCase(inputCode)){
            try {

                subject.login(token);

               //adminService.login(request, admin, inputCode);
               // subject.login(admin,inputCode);
                map.put("status", true);
            } catch (Exception e) {
                map.put("status", false);
                //打印错误标记
                map.put("message", e.getMessage());
             }
        }else{
            throw new RuntimeException("hehe");
        }
        return map;
    }

    /**
     * 登出方法
     */

    @RequestMapping("exit")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/admin/login.jsp";
    }

    @RequestMapping("registered")
    @ResponseBody
    public Map<String, Object> registered(Admin admin, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            adminService.registered(request, admin);

            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            //打印错误标记
            map.put("message", e.getMessage());
        }
        return map;
    }
}
