package com.baizhi.service;

import com.baizhi.entity.Banner;
import com.baizhi.entity.Star;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface StarService {

    //带分页展示
    Map<String,Object> selectAll(Integer page, Integer rows);
    //添加
    String save(Star star);
    //更改
    void update(Star star);
    //删除
    void delete(String id, HttpServletRequest request);


    List<Star> getAllStarForSelect();

}
