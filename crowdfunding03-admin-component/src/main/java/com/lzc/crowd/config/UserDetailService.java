package com.lzc.crowd.config;

import com.lzc.crowd.entity.Admin;
import com.lzc.crowd.entity.Auth;
import com.lzc.crowd.entity.Role;
import com.lzc.crowd.service.AdminService;
import com.lzc.crowd.service.AuthService;
import com.lzc.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据账号名称查询Admin对象
        Admin admin = adminService.getAdminByLoginAcct(username);
        //2.获取adminId
        Integer adminId = admin.getId();
        //3.根据adminId查询所拥有的角色信息
        List<Role> assignedRole = roleService.getAssignedRole(adminId);
        //4.根据adminId查询权限信息
        List<String> authNameList = authService.getAuthNameByAdminId(adminId);
        //5.创建集合对象存储GrantedAuthority
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //6.遍历assignedRole存入角色信息
        for (Role role:assignedRole) {
            //加前缀
            String roleName = "ROLE_"+role.getRoleName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            grantedAuthorities.add(simpleGrantedAuthority);
        }
        //7.遍历authNameList存入权限信息
        for (String authName:authNameList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            grantedAuthorities.add(simpleGrantedAuthority);
        }
        //8.封装SecurityAdmin对象
        SecurityAdmin securityAdmin =new SecurityAdmin(admin,grantedAuthorities);
        return securityAdmin;
    }
}
