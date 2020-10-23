package com.lzc.crowd.util;
import com.lzc.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 通用工具方法类
 * @author Administrator
 */
public  class CrowdUtil {

    /**
     * 对明文字符串进行加密
     * @param source 传入字符串
     * @return 加密字符串
     */
    public static String md5 (String source) {
        //1.判断source是否有效
        if (source == null || source.length() ==0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        String algorithm = "md5";
        try {
            //2.获取MessageDigest对象
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            //3.获取明文字符串对应的字节数组
            byte[] input = source.getBytes();

            //4.执行加密
            byte [] output = messageDigest.digest(input);

            //5.创建BigInteger对象
            BigInteger bigInteger = new BigInteger(1,output);

            //6.转化为16进制
            String encoded = bigInteger.toString(16).toUpperCase();

            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
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
        return ((acceptHeader!=null&&"application/json".contains(acceptHeader))
                ||
                xRequestHeader!=null&&"XMLHttpRequest".equals(xRequestHeader));
    }
}
