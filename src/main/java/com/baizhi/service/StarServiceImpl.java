package com.baizhi.service;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Star;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class StarServiceImpl implements StarService {

    @Autowired
    private StarDao starDao;

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Star star =new Star();
        RowBounds rowbounds = new RowBounds((page-1)*rows,rows);
        List<Star> list = starDao.selectByRowBounds(star, rowbounds);
        //查询数量
        int count = starDao.selectCount(star);
        Map<String,Object>map =new HashMap<>();
        map.put("page",page);
        //每页显示多少行装进list
        map.put("rows",list);
        //总共有几页
        map.put("total",count%rows==0?count/rows:count/rows+1);
        //总共有多少条数据
        map.put("records",count);
        return map;
    }


    @Override
    public String save(Star star) {
        star.setId(UUID.randomUUID().toString());
        //star.setBir(new Date());
        int a = starDao.insert(star);
        //判断
        if (a == 1){
            //获得id 指定上传
            return star.getId();
        }
        throw  new RuntimeException("添加失败");
    }

    /***
     * 修改
     */
    @Override
    public void update(Star star) {

        if ("".equals(star.getPhoto())){
            star.setPhoto(null);
        }

        try {
            starDao.updateByPrimaryKeySelective(star);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    /***
     * 删除
     * @param id
     */
    @Override
    public void delete(String id, HttpServletRequest request) {
        Star star = starDao.selectByPrimaryKey(id);


        //根据id删除
        int i = starDao.deleteByPrimaryKey(id);
        if (i==0){
            throw  new RuntimeException("删除失败");
        }else {
            //删除成功

            String photo = star.getPhoto();
            File file =new File(request.getServletContext().getRealPath("/star/img/"),photo);
            boolean b = file.delete();
            if (b==false){
                throw new RuntimeException("删除封面失败");
            }

        }

    }

    //查询所有明星
    @Override
    public List<Star> getAllStarForSelect() {
        List<Star> list = starDao.selectAll();
        return list;
    }
}
