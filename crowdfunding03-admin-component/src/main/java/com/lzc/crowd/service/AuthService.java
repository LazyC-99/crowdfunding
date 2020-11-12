package com.lzc.crowd.service;

import com.lzc.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getAuthIdByRoleId(Integer roleId);

    List<String> getAuthNameByAdminId(Integer adminId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);
}
