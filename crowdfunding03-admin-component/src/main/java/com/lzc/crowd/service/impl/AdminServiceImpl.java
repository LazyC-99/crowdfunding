package com.lzc.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzc.crowd.constant.CrowdConstant;
import com.lzc.crowd.entity.Admin;
import com.lzc.crowd.entity.AdminExample;
import com.lzc.crowd.exception.LoginAcctNotUniqueException;
import com.lzc.crowd.mapper.AdminMapper;
import com.lzc.crowd.service.AdminService;
import com.lzc.crowd.util.CrowdUtil;
import com.lzc.crowd.exception.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    @Override
    public void saveAdmin(Admin admin) {
        //1.密码加密
        //String pwd = CrowdUtil.md5(admin.getUserPswd());
        String pwd = bCryptPasswordEncoder.encode(admin.getUserPswd());
        admin.setUserPswd(pwd);
        //2.创建时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(new Date());
        admin.setCreateTime(createTime);
        try {
            adminMapper.insert(admin);
        }catch (Exception e) {
            logger.info("异常全类名:"+e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctNotUniqueException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
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

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return admins.get(0);
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        //1.开启PageHelper分页功能
        PageHelper.startPage(pageNum,pageSize);
        //2.查询
        List<Admin> admins = adminMapper.selectAdminByKeyWord(keyword);
        //3.封装到PageInfo中
        return new PageInfo<>(admins);
    }

    @Override
    public void removeAdmin(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void editAdmin(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e) {
            logger.info("异常全类名:"+e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctNotUniqueException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }

    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        adminMapper.deleteOldRelationship(adminId);
        if(roleIdList!=null && roleIdList.size()>0){
            adminMapper.insertNewRelationship(adminId,roleIdList);
        }
    }
}
