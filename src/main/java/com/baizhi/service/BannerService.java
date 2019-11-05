package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BannerService {
    //分页查询
    Map<String,Object>selectAll(Integer pade,Integer rows);
    //保存
    String save(Banner banner);
    //更改
    void update(Banner banner);
    //删除
    void delete(String id, HttpServletRequest request);

}
