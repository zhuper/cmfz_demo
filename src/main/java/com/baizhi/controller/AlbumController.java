package com.baizhi.controller;


import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("album")
@RestController
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page, Integer rows){
        Map<String,Object>map = albumService.selectAll(page,rows);
        return map;
    }

    //图片文件上传
    @RequestMapping("upload")
    public  Map<String,Object> upload(MultipartFile cover, String id, HttpServletRequest request){
        Map<String,Object>map =new HashMap<>();
        try{
            String realPath = request.getServletContext().getRealPath("/album/img");
            String filename = cover.getOriginalFilename();
            //文件上传
            cover.transferTo(new File(realPath,filename));
            //修改baaner对象中cover属性
            Album album = new Album();
            album.setId(id);
            album.setCover(cover.getOriginalFilename());
            albumService.update(album);
            map.put("status",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }

    //增删改
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Album album, String oper , HttpServletRequest request){
        Map<String,Object> map =new HashMap<>();
        try {
            if (oper.equals("add")){
                String id = albumService.save(album);
                map.put("status",true);
                map.put("message",id);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;

    }

}
