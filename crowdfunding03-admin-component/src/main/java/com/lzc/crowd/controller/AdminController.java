package com.lzc.crowd.controller;

import com.github.pagehelper.PageInfo;
import com.lzc.crowd.constant.CrowdConstant;
import com.lzc.crowd.entity.Admin;
import com.lzc.crowd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@SuppressWarnings(value = "all")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/to/admin/edit/page/{adminId}.html")
    public String toAdmin (@PathVariable("adminId") Integer adminId,ModelMap modelMap){
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin",admin);
        return "admin-edit";
    }

    @RequestMapping("/admin/do/edit.html")
    public String editAdmin (Admin admin,@RequestParam("pageNum")Integer pageNum,@RequestParam("keyword")String keyword){
        adminService.editAdmin(admin);
        return "redirect:/admin/get/user.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/do/save.html")
    public String saveAdmin (Admin admin){
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/user.html?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String romoveAdmin (@PathVariable("adminId") Integer adminId,@PathVariable("pageNum")Integer pageNum,@PathVariable("keyword")String keyword) {
        adminService.removeAdmin(adminId);
        return "redirect:/admin/get/user.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/get/user.html")
    public String getPageInfo (@RequestParam(value = "keyword",defaultValue = "") String keyword, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize, ModelMap modelMap) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-user";
    }

    @Deprecated
    @RequestMapping("/admin/do/logout.html")
    public String doLogout (HttpSession session){
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    /**
     * 登录由SpringSecurity接管
     * @return
     */
    @Deprecated()
    @RequestMapping("/admin/do/login.html")
    public String doLogin (@RequestParam("loginAcct") String loginAcct, @RequestParam("userPwd") String userPwd, HttpSession session) {
        //返回admin对象则登录成功,账号密码不正确会抛出登录异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPwd);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }
}
