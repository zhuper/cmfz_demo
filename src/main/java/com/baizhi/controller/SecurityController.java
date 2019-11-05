package com.baizhi.controller;

import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.font.ImageGraphicAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RequestMapping("code")
@RestController
public class SecurityController {

    @RequestMapping("getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String securityCode = SecurityCode.getSecurityCode();
        System.out.println(securityCode);
        HttpSession session = request.getSession();
        session.setAttribute("securityCode",securityCode);
        BufferedImage image = SecurityImage.createImage(securityCode);
        response.setContentType("image/png");
        ImageIO.write(image,"png",response.getOutputStream());
    }
}
