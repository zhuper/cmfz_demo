package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    //查看
    Map<String,Object> selectAll(Integer page, Integer rows, String albumId);

    //添加
    String add(Chapter chapter);

    //修改
    void edit(Chapter chapter);

}
