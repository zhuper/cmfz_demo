package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    //查询

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Banner banner =new Banner();
        RowBounds rowbounds = new RowBounds((page-1)*rows,rows);
        List<Banner> list = bannerDao.selectByRowBounds(banner,rowbounds);
        int count = bannerDao.selectCount(banner);

        Map<String,Object>map =new HashMap<>();
        map.put("page",page);
        //每页显示多少行装进list
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);//总共有几页、
        map.put("records",count);//总共有多少条数据

        return map;
    }

    @Override
    public String save(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreate_date(new Date());
        int a = bannerDao.insert(banner);
        //判断
        if (a == 1){
            //获得id 指定上传
            return banner.getId();
        }
        throw  new RuntimeException("添加失败");
    }

    /***
     * 修改
     * @param banner
     */
    @Override
    public void update(Banner banner) {

        if ("".equals(banner.getCover())){
            banner.setCover(null);
        }

        try {
            bannerDao.updateByPrimaryKeySelective(banner);
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
        Banner banner = bannerDao.selectByPrimaryKey(id);


        //根据id删除
        int i = bannerDao.deleteByPrimaryKey(id);
        if (i==0){
            throw  new RuntimeException("删除失败");
        }else {
            //删除成功
            String cover = banner.getCover();
            File file =new File(request.getServletContext().getRealPath("/banner/img/"),cover);
            boolean b = file.delete();
            if (b==false){
                throw new RuntimeException("删除封面失败");
            }
        }


    }
}
