package com.baizhi.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDAO<T> {


    void save(T t);

    void update(T t);

    void remove(String id);

    T findById(String id);
        //查看所有并分页
    List<T> findAll(@Param("start") Integer start, @Param("rows") Integer rows);
    //分页的条数
    Long findTotalCounts();



}
