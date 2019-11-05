package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("article")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("findAll")

    public Map<String,Object> findAll(Integer page, Integer rows){
        Map<String,Object>map = articleService.findAll(page,rows);
        return map;
    }

    /***
     * 图片上传功能
     * @param articleImg
     * @param request
     * @return
     */
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile articleImg, HttpServletRequest request){
//        {"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}
        Map<String, Object> map = new HashMap<>();
        File file = new File(request.getServletContext().getRealPath("article/img"), articleImg.getOriginalFilename());
        try {
            articleImg.transferTo(file);
            map.put("error",0);
            map.put("url","http://localhost:8989/star/article/img/"+articleImg.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error",1);
        }
        return map;
    }



    /**
     * 编辑器
     * @param request
     * @return
     */
    @RequestMapping("browse")
    public Map<String,Object> browse(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        File file = new File(request.getServletContext().getRealPath("article/img"));
        File[] files = file.listFiles();
        List<Object> list = new ArrayList<>();
        for (File img : files) {
            Map<String, Object> imgObject = new HashMap<>();
            imgObject.put("is_dir",false);
            imgObject.put("has_file",false);
            imgObject.put("filesize",img.length());
            imgObject.put("is_photo",true);
            imgObject.put("filetype", FilenameUtils.getExtension(img.getName()));
            imgObject.put("filename",img.getName());
            imgObject.put("datetime","2018-06-06 00:36:39");
            list.add(imgObject);
        }
        map.put("file_list",list);
        map.put("total_count",list.size());
        map.put("current_url","http://localhost:8989/star/article/img/");
        return map;
    }

    /***
     * 修改功能
     */
    @RequestMapping("update")
    public Map<String,Object> update(Article article){
        Map<String, Object> map = new HashMap<>();
        try {
            articleService.update(article);
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 添加功能
     */
    @RequestMapping("add")
public Map<String,Object>add(Article article){

    Map<String,Object>map = new HashMap<>();

    try {
        String id = articleService.save(article);
        map.put("status",true);
        map.put("message",id);
    } catch (Exception e) {
        map.put("status",false);
        map.put("message",e.getMessage());
        e.printStackTrace();
    }
    return map;
}

    /***
     * 删除功能
     * @param article
     * @param request
     * @return
     */
    @RequestMapping("delete")
    public Map<String,Object>delete(Article article){
    Map<String,Object>map = new HashMap<>();

    try {
        articleService.delete(article.getId());

        map.put("status",true);
    } catch (Exception e) {
        map.put("status",false);
        e.printStackTrace();
    }
    return map;
    }
}
