package com.lzc.crowd.config;

import com.lzc.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * 考虑SpringSecurity的User对象仅仅包含账号密码,为了能获取到原始的Admin对象.此类对User做出扩展
 * @author Administrator
 */
public class SecurityAdmin extends User {

    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities){
        super(originalAdmin.getUserName(),originalAdmin.getUserPswd(),authorities);
        this.originalAdmin=originalAdmin;
        //擦除密码
        this.originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
