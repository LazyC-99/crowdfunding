package com.lzc.crowd.controller;

import com.lzc.crowd.constant.CrowdConstant;
import com.lzc.crowd.entity.Admin;
import com.lzc.crowd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/do/logout.html")
    public String doLogout (HttpSession session){
        session.invalidate();
        return "redirect:/admin/to/login/page.html";


    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin (@RequestParam("loginAcct") String loginAcct, @RequestParam("userPwd") String userPwd, HttpSession session) {
        //返回admin对象则登录成功,账号密码不正确会抛出登录异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPwd);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }
}
