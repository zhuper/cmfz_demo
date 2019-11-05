package com.baizhi.service;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Album;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


public interface AdminService {

    //登陆
    public void login(HttpServletRequest request, Admin admin, String inputCode) ;
    //注册
    public void registered(HttpServletRequest request,Admin admin);



    }
