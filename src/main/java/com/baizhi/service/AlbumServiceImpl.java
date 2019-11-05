package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
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
public class AlbumServiceImpl implements AlbumService{

    @Autowired
    private AlbumDao  albumDao;

    @Autowired
    private StarDao starDao;


    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Album album = new Album();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Album> list = albumDao.selectByRowBounds(album,rowBounds);
        //查询明星的id
        for (Album a : list) {
            Star star = starDao.selectByPrimaryKey(a.getStar_id());
            a.setStar(star);
        }
        int count = albumDao.selectCount(album);

        Map<String,Object>map =new HashMap<>();
        map.put("page",page);
        //每页显示多少行
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);  //总共有几页
        map.put("records",count);  //总共有多少条数据
        return  map;
    }


    @Override
    public String save(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCreate_date(new Date());
        int i = albumDao.insert(album);

        //判断
        if (i==1){
            //获得指定id上传
           return album.getId();
        }
        throw  new RuntimeException("添加失败");
    }

    @Override
    public void update(Album album) {
        if ("".equals(album.getCover())){
            album.setCover(null);
        }

        try {
            albumDao.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }

    }

    @Override
    public void delete(String id, HttpServletRequest request) {
        Album album = albumDao.selectByPrimaryKey(id);
        int i = albumDao.deleteByPrimaryKey(id);
        if (i==0){
            throw new RuntimeException("删除失败");
        }else {
            //删除成功
            String cover = album.getCover();
            File file = new File(request.getServletContext().getRealPath("/album/img/"),cover);
            boolean b = file.delete();
            if (b==false){
                throw new RuntimeException("删除封面失败");
            }
        }
    }

    @Override
    public Album selectOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }
}
