package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Article article =new Article();
        RowBounds rowBounds = new RowBounds(page-1*rows,rows);
        List<Article> list = articleDao.selectByRowBounds(article,rowBounds);

        int count = articleDao.selectCount(article);
        Map<String,Object>map =new HashMap<>();
        map.put("page",page);
        //每页显示多少行
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);  //总共有几页
        map.put("records",count);  //总共有多少条数据
        return  map;
    }

    /***
     * 添加功能
     * @param article
     * @return
     */
    @Override
    public String save(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreate_date(new Date());
        int i = articleDao.insert(article);

        if (i==1){
            //获得指定id上传
            return article.getId();
        }
        throw  new RuntimeException("添加失败");
    }

    /**
     * 修改
     * @param article
     */
    @Override
    public void update(Article article) {
        try {
            articleDao.updateByPrimaryKeySelective(article);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }

    }

    /**
     * 删除
     * @param id
     * @param request
     */
    @Override
    public void delete(String id) {

        int i = articleDao.deleteByPrimaryKey(id);
        if (i==0){
            throw new RuntimeException("删除失败");
        }
    }
}
