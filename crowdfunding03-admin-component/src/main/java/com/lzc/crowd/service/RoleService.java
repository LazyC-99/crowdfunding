package com.lzc.crowd.service;

import com.github.pagehelper.PageInfo;
import com.lzc.crowd.entity.Role;

import java.util.List;

public interface RoleService {
    /**
     * 分页查询Role
     * @param keyword 查询关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize);
    /**
     * 增加Role
     * @param role
     * @return
     */
    void saveRole(Role role);
    /**
     * 修改Role
     * @param role
     * @return
     */
    void editRole(Role role);
    /**
     * 删除Role
     * @param roleIdList 要删除的RoleId集合
     * @return
     */
    void removeRole(List<Integer> roleIdList);
    /**
     * 根据用户id查询已拥有的角色
     * @param adminId 要查询角色的用户id
     * @return
     */
    List<Role> getAssignedRole(Integer adminId);
    /**
     * 根据用户id查询未拥有的角色
     * @param adminId 要查询角色的用户id
     * @return
     */
    List<Role> getUnAssignedRole(Integer adminId);
}
