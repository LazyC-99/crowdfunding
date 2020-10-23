package com.lzc.crowd.service.impl;

import com.lzc.crowd.constant.CrowdConstant;
import com.lzc.crowd.entity.Admin;
import com.lzc.crowd.entity.AdminExample;
import com.lzc.crowd.mapper.AdminMapper;
import com.lzc.crowd.service.AdminService;
import com.lzc.crowd.util.CrowdUtil;
import com.lzc.crowd.exception.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);


    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPwd) {
        //1.查询
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        //2.判断是否存在
        if (admins.size()==0||admins==null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        //3.不唯一检查(数据库唯一约束)
        if (admins.size()>1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        //4.存在 从数据库取出密码
        Admin admin = admins.get(0);
        String userPwdDB =admin.getUserPswd();

        //5.加密并比较
        String userPwdForm = CrowdUtil.md5(userPwd);
        logger.info("登录密码比较:"+userPwdForm+":"+userPwdDB);
        if (!Objects.equals(userPwdDB,userPwdForm)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        return admin;
    }
}
