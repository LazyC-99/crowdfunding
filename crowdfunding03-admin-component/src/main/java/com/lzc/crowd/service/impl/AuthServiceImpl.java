package com.lzc.crowd.service.impl;

import com.lzc.crowd.entity.Auth;
import com.lzc.crowd.entity.AuthExample;
import com.lzc.crowd.mapper.AuthMapper;
import com.lzc.crowd.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthMapper authMapper;

    @Override
    public List<Auth> getAllAuth() {
        return authMapper.selectByExample(null);
    }

    @Override
    public List<Integer> getAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAuthIdByRoleId(roleId);
    }

    @Override
    public List<String> getAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAuthNameByAdminId(adminId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        //1.获取roleId的值
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        //2.删除旧关联数据
        authMapper.deleteOldRelationship(roleId);
        //3.获取分配的权限
        List<Integer> authIdArray = map.get("authIdArray");
        if (authIdArray!=null&&authIdArray.size()>0){
            authMapper.insertNewRelationship(roleId,authIdArray);
        }
    }
}
