package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "用户名")
    private String username;
    private String password;
    private String salt;    //盐
    @Excel(name = "昵称")
    private String nickname;
    @Excel(name = "电话")
    private String phone; //电话
    @Excel(name = "省")
    private String province;    // 省
    @Excel(name = "市")
    private String city;    //市
    @Excel(name = "签名")
    private String sign;    //签名
    @Excel(name = "照片", type=2)
    private String photo;   //照片
    @Excel(name = "性别")
    private String sex;     //性别
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_date;
    private String star_id;

}
