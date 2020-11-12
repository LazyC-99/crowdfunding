package com.lzc.crowd.service;

import com.github.pagehelper.PageInfo;
import com.lzc.crowd.entity.Admin;

import java.util.List;

public interface AdminService {

    /**
     * 查询所有Admin
     * @return
     */
    List<Admin> getAll();

    /**
     * 分页查询Admin
     * @param keyword 查询关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    /**
     * 根据用户名密码查询
     * @param loginAcct 用户名
     * @param userPwd 密码
     * @return Admin对象
     */
    Admin getAdminByLoginAcct(String loginAcct, String userPwd);
    /**
     * 根据用户名查询
     * @param username 用户名
     * @return Admin对象
     */
    Admin getAdminByLoginAcct(String username);
    /**
     * 新增Admin
     * @param admin admin对象
     */
    void saveAdmin(Admin admin);
    /**
     * 删除Admin
     * @param adminId 删除的ID
     */
    void removeAdmin(Integer adminId);
    /**
     * 根据ID查Admin
     * @param adminId
     */
    Admin getAdminById(Integer adminId);
    /**
     * 修改Admin
     * @param admin admin对象
     */
    void editAdmin(Admin admin);
    /**
     * 保存用户角色关系
     * @param adminId 用户id
     * @param roleIdList 用户说拥有的角色id集合
     */
    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
