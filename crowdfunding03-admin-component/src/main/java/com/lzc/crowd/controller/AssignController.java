package com.lzc.crowd.controller;

import com.lzc.crowd.entity.Role;
import com.lzc.crowd.service.AdminService;
import com.lzc.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AssignController {

    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/assign/do/assign/role.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId, @RequestParam(value = "roleIdList",required = false) List<Integer> roleIdList, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword){
        adminService.saveAdminRoleRelationship(adminId,roleIdList);
        return "redirect:/admin/get/user.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap){
        //1.查询已分配的角色
        List<Role> assignedRole = roleService.getAssignedRole(adminId);
        //2.查询未分配的角色
        List<Role> unassignedRole = roleService.getUnAssignedRole(adminId);
        //3.存入模型
        modelMap.addAttribute("assignedRole",assignedRole);
        modelMap.addAttribute("unassignedRole",unassignedRole);
        return "assign-role";
    }
}
