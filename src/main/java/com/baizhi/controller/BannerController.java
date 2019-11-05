package com.baizhi.controller;


import com.baizhi.entity.Banner;

import com.baizhi.service.BannerService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    //增删改
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Banner banner,String oper ,HttpServletRequest request){
        Map<String,Object> map =new HashMap<>();
        try {
            if (oper.equals("add")){
                String id = bannerService.save(banner);
                map.put("status",true);
                map.put("message",id);
            }
            if ("edit".equals(oper)){
                bannerService.update(banner);

            }
            if ("del".equals(oper)){
                bannerService.delete(banner.getId(),request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;

    }

    //分页查询
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page,Integer rows){
        Map<String,Object>map = bannerService.selectAll(page,rows);
        return map;
    }

    //图片文件上传
    @RequestMapping("upload")
    public  Map<String,Object> upload(MultipartFile cover, String id, HttpServletRequest request){
        Map<String,Object>map =new HashMap<>();
        try{
            String realPath = request.getServletContext().getRealPath("/banner/img");
            String filename = cover.getOriginalFilename();
            //文件上传
            cover.transferTo(new File(realPath,filename));
            //修改baaner对象中cover属性
            Banner banner = new Banner();
            banner.setId(id);
            banner.setCover(cover.getOriginalFilename());
            bannerService.update(banner);
            map.put("status",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}

