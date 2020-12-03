package com.lzc.crowd.constant;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class AccessPassResource {



    public static final Set<String> PASS_RES_SET = new HashSet<>();

    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/reg/page.html");
        PASS_RES_SET.add("/auth/member/to/login/page.html");
        PASS_RES_SET.add("/auth/do/member/login/out");
        PASS_RES_SET.add("/auth/do/member/login");
        PASS_RES_SET.add("/auth/do/member/register");
        PASS_RES_SET.add("/auth/member/send/short/message.json");
    }

    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("script");
    }

    public static final Set<String> INTERCEPT_REQ_SET = new HashSet<>();

    static {
        INTERCEPT_REQ_SET.add("/auth/member/to/center/page.html");
    }

    public static boolean judgeInterceptRequest(String requestPath){
        return INTERCEPT_REQ_SET.contains(requestPath);
    }

    public static boolean judgePassRequest(String requestPath){
        if (requestPath==null||"".equals(requestPath)){
            throw new RuntimeException(CrowdConstant.MESSAGE_REQUEST_INVALIDATE);
        }
        //判断特定放行请求
        boolean requestContains = PASS_RES_SET.contains(requestPath);
        //判断静态资源请求
        String[] split = requestPath.split("/");
        String firstPath = "/";
        if(split.length>0){
            firstPath = split[1];
        }
        boolean staticContains = STATIC_RES_SET.contains(firstPath);
        return requestContains||staticContains;
    }

//    public static void main(String[] args) {
//        judgeCurrentRequest("/css/member/to/login/page.html");
//
//    }

}
