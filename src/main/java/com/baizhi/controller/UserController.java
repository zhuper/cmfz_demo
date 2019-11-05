package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("selectUsersByStarId")
    public Map<String,Object> selectUsersByStarId(Integer page,Integer rows,String starId){
        return userService.selectUsersByStarId(page,rows,starId);
    }

    //增删改
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(User user, String oper , HttpServletRequest request){
        Map<String,Object> map =new HashMap<>();
        try {
            if (oper.equals("add")){
                String id = userService.save(user);
                map.put("status",true);
                map.put("message",id);
            }
            if ("edit".equals(oper)){
                userService.update(user);

            }
            if ("del".equals(oper)){
                userService.delete(user.getId(),request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;

    }

    //分页查询
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page,Integer rows){
        Map<String,Object>map = userService.selectAll(page,rows);
        return map;
    }

    //图片文件上传
    @RequestMapping("upload")
    public  Map<String,Object> upload(MultipartFile photo, String id, HttpServletRequest request){
        Map<String,Object>map =new HashMap<>();
        try{
            String realPath = request.getServletContext().getRealPath("/user/img");
            String filename = photo.getOriginalFilename();
            //文件上传
            photo.transferTo(new File(realPath,filename));
            User user = new User();
            user.setId(id);
            user.setPhoto(photo.getOriginalFilename());
            userService.update(user);

            map.put("status",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }

    @RequestMapping("export")
    public void export(HttpServletResponse response,HttpServletRequest request) {
        //准备数据
        List<User> list = userService.export(request);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("逐星球用户表","用户"), com.baizhi.entity.User.class, list);

        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        //处理中文下载名乱码
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition","attachment;filename="+fileName);

            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("getTrend")
          public Map<String,Object>getTrend(){
            Map<String,Object>map = new HashMap<>();
                  Integer[] man = userService.getTrend("男");
                  map.put("man",man);
                  Integer[] woman = userService.getTrend("女");
                  map.put("woman",woman);
                  return map;
          }
}


