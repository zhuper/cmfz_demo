package com.baizhi.service;


import com.baizhi.entity.Star;
import com.baizhi.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    //带分页展示
    Map<String,Object> selectUsersByStarId(Integer pade, Integer rows, String starId);
   //添加
    String save(User user);
    //删除
    void delete(String id, HttpServletRequest request);
    //修改
    void update(User user);
    //文件表格导出
    public List<User> export(HttpServletRequest request);
    //查询所有
    Map<String,Object> selectAll(Integer page, Integer rows);
    //查趋势图
    public Integer[] getTrend(String sex);


}
