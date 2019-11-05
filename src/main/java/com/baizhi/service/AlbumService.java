package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AlbumService {
    //分页查询
    Map<String,Object>selectAll(Integer page,Integer rows);
    //保存
    String save(Album album);
    //更改
    void update(Album album);
    //删除
    void delete(String id, HttpServletRequest request);
    //修改专辑中的数量
    Album selectOne(String id);

}
