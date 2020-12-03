package com.lzc.crowd.controller;

import com.github.pagehelper.PageInfo;
import com.lzc.crowd.entity.Role;
import com.lzc.crowd.service.RoleService;
import com.lzc.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SuppressWarnings("all")
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping("/role/remove.json")
    public ResultEntity removeRole(@RequestBody List<Integer> roleIdList) {
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/edit.json")
    public ResultEntity editRole(Role role) {
        roleService.editRole(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/save.json")
    public ResultEntity saveRole(Role role) {
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }


    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/role/get/role.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {
        PageInfo<Role> pageInfo = roleService.getPageInfo(keyword,pageNum,pageSize);
        return ResultEntity.successWithData(pageInfo);
    }
}
