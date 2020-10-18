package com.lzc.crowd.util;
import javax.servlet.http.HttpServletRequest;

public  class CrowdUtil {
    /**
     * 判断请求是否为Ajax
     * @param request
     * @return true:是 false:不是
     *
     */
    public static boolean judgeRequestType (HttpServletRequest request){
        //获取请求头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-requested-With");

        //判断
        return ((acceptHeader!=null&&acceptHeader.contains("application/json"))
                ||
                xRequestHeader!=null&&xRequestHeader.equals("XMLHttpRequest"));
    }
}
