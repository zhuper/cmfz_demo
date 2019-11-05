package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ArticleService {

     Map<String,Object>findAll(Integer page,Integer rows);
    //保存
    String save(Article article);
    //更改
    void update(Article article);
    //删除
    void delete(String id);
}
