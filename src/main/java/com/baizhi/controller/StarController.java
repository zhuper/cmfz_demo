package com.baizhi.controller;


import com.baizhi.entity.Banner;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("star")
@Controller
public class StarController {

    @Autowired
    private StarService starService;

   //分页查询
    @RequestMapping("findAll")
    @ResponseBody
    public Map<String,Object>finALl(Integer page,Integer rows){
        Map<String, Object> map = starService.selectAll(page, rows);
        return map;
    }

    //增删改
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Star star, String oper , HttpServletRequest request){
        Map<String,Object> map =new HashMap<>();
        try {
            if (oper.equals("add")){
                String id = starService.save(star);
                map.put("status",true);
                map.put("message",id);
            }
            if ("edit".equals(oper)){
               starService.update(star);
            }
            if ("del".equals(oper)){
                starService.delete(star.getId(),request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;

    }



    //图片上传
    @RequestMapping("upload")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile photo, String id, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //获得图片路径
        try {
            String realPath = request.getServletContext().getRealPath("/star/img");
            String filename = photo.getOriginalFilename();
            //文件上传
            photo.transferTo(new File(realPath,filename));
            Star star = new Star();
            star.setId(id);
            star.setPhoto(photo.getOriginalFilename());
            starService.update(star);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;

    }


    @RequestMapping("getAllStarForSelect")

    public void getAllStarForSelect(HttpServletResponse response) throws Exception {
        List<Star> list = starService.getAllStarForSelect();
        String str = "<select>";
        for (Star star : list) {
            str += "<option value=" + star.getId() + ">" + star.getName()+ "</option>";
        }
        str += "</select>";
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(str);
    }



}
