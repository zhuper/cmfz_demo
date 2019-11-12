package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ArticleService {
        //查看分页查所有
     Map<String,Object>selectAll(Integer page,Integer rows);
    //保存
    String save(Article article);
    //更改
    void update(Article article);
    //删除
    void delete(String id);
    //查询检索
    List<Article>search(String content);
}
