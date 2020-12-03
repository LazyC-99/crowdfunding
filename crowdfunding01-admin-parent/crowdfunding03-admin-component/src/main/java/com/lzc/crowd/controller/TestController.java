package com.lzc.crowd.controller;

import com.lzc.crowd.entity.Admin;
import com.lzc.crowd.service.AdminService;
import com.lzc.crowd.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestController {
    @Autowired
    private AdminService adminService;

    private final Logger logger = LoggerFactory.getLogger(TestController.class);


    @RequestMapping("test/ssm.html")
    public String testSsm(Model model, HttpServletRequest request){
        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        logger.info("判断是否为Ajax请求:"+judgeResult);

        List<Admin> admins = adminService.getAll();

        model.addAttribute("admins",admins);

        System.out.println(10/0);
        return "target";
    }
}
