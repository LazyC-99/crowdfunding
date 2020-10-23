package com.lzc.crowd.service;

import com.lzc.crowd.entity.Admin;

import java.util.List;

public interface AdminService {

    /**
     * 查询所有Admin
     * @return
     */
    List<Admin> getAll();

    /**
     * 新增Admin
     * @param admin admin对象
     */
    void saveAdmin(Admin admin);

    /**
     * 根据用户名密码查询
     * @param loginAcct 用户名
     * @param userPwd 密码
     * @return Admin对象
     */
    Admin getAdminByLoginAcct(String loginAcct, String userPwd);
}
