package com.lzc.crowd.filter;

import com.lzc.crowd.constant.AccessPassResource;
import com.lzc.crowd.constant.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CrowdAccessFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(CrowdAccessFilter.class);


    @Override
    public boolean shouldFilter() {
        //获取请求路径
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestPath = request.getServletPath();
        logger.info("请求:"+requestPath+"拦截?"+AccessPassResource.judgeInterceptRequest(requestPath));
        //判断请求是否拦截
        return AccessPassResource.judgeInterceptRequest(requestPath);
    }

    @Override
    public Object run() throws ZuulException {
        //获取当前登录的用户
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        Object loginMember = session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        //未登录状态时 重定向到登录页面
        if (loginMember==null){
            HttpServletResponse response = currentContext.getResponse();
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
            try {
                response.sendRedirect("/auth/member/to/login/page.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

}
