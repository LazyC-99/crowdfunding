package com.lzc.crowd.service;

import com.lzc.crowd.entity.Admin;

import java.util.List;

public interface AdminService {

    List<Admin> getAll();

    void saveAdmin(Admin admin);
}
