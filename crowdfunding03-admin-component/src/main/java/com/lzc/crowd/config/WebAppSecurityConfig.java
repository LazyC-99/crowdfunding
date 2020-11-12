package com.lzc.crowd.config;

import com.lzc.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SpringSecurity配置类
 * @author Administrator
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailService userDetailService;
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            //放行"/"和静态资源
            .antMatchers("/")
            .permitAll()
            .antMatchers("/bootstrap/**")
            .permitAll()
            .antMatchers("/css/**")
            .permitAll()
            .antMatchers("/fonts/**")
            .permitAll()
            .antMatchers("/img/**")
            .permitAll()
            .antMatchers("/jquery/**")
            .permitAll()
            .antMatchers("/layer-v3.1.1/**")
            .permitAll()
            .antMatchers("/script/**")
            .permitAll()
            .antMatchers("/ztree/**")
            .permitAll()
            //分配权限
            .antMatchers("/admin/get/user.html")
            .access("hasRole('经理') or hasAuthority('user:get')")
            //而其他的请求都需要认证
            .anyRequest()
            .authenticated()
            .and()
            //修改Spring Security默认的登陆界面
            .formLogin()
            .loginPage("/admin/to/login/page.html")
            .loginProcessingUrl("/security/do/login.html")
            .defaultSuccessUrl("/admin/to/main/page.html")
            .permitAll()
            .usernameParameter("loginAcct")
            .passwordParameter("userPwd")
            .and()
            .logout()
            .logoutUrl("/security/do/logout.html")
            .logoutSuccessUrl("/admin/to/main/page.html")
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new AccessDeniedHandler() {
                @Override
                public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                    request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                    request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request,response);
                }
            })
            .and()
            .rememberMe()
            .and()
            .csrf().disable();   //默认参数remember-me
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
            .passwordEncoder(getPasswordEncoder());
    }


}
