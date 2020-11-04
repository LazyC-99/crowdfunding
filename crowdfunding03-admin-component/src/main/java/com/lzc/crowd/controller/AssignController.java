package com.lzc.crowd.controller;

import com.lzc.crowd.entity.Auth;
import com.lzc.crowd.entity.Role;
import com.lzc.crowd.service.AdminService;
import com.lzc.crowd.service.AuthService;
import com.lzc.crowd.service.RoleService;
import com.lzc.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 角色权限分配
 * @author Administrator
 */
@Controller
public class AssignController {

    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;
    @Autowired
    AuthService authService;



    /**
     * 执行角色的权限分配
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/do/auth/assign.json")
    public ResultEntity<List<Integer>> saveRoleAuthRelationship(@RequestBody Map<String,List<Integer>> map){
        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successWithoutData();
    }

    /**
     * 获取角色已拥有配的权限
     * @param roleId 角色id
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/get/role/auth.json")
    public ResultEntity<List<Integer>> getAssignedAuth(@RequestParam("roleId") Integer roleId){
        List<Integer> roleAuthList = authService.getAuthByRoleId(roleId);
        return ResultEntity.successWithData(roleAuthList);
    }

    @ResponseBody
    @RequestMapping("/assign/get/auth/tree.json")
    public ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> authList = authService.getAllAuth();
        return ResultEntity.successWithData(authList);
    }

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
