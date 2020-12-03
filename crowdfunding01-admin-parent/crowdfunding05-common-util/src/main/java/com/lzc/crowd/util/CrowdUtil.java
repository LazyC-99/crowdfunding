package com.lzc.crowd.util;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.lzc.crowd.constant.CrowdConstant;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    /**
     * 第三方短信接口
     * @param phoneNum  接受验证码的手机号
     * @return 成功返回验证码,失败返回信息
     */

    public static ResultEntity<String> sendShortMessage(String phoneNum){
        String host = "https://smssend.shumaidata.com";
        String path = "/sms/send";
        String method = "POST";
        String appcode = "15ac3d4d896e4716ac1282bcbd5d6146";
        StringBuilder code = new StringBuilder();
        for(int i =0;i < 4;i++){
            int random =  (int)(Math.random() * 10);
            code.append(random);
        }
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("receive", phoneNum);
        querys.put("tag", code.toString());
        querys.put("templateId", "M09DD535F4");
        Map<String, String> bodys = new HashMap<String, String>();



        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            StatusLine statusLine = response.getStatusLine();
            String reasonPhrase = statusLine.getReasonPhrase();
            int statusCode = statusLine.getStatusCode();
            if (statusCode==200) {
                //成功,返回验证码
                return ResultEntity.successWithData(code.toString());
            }
            return ResultEntity.failed(reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    /**
     * 专门负责上传文件到 OSS 服务器的工具方法
     * @param endpoint OSS 参数
     * @param accessKeyId OSS 参数
     * @param accessKeySecret OSS 参数
     * @param bucketName OSS 参数
     * @param bucketDomain OSS 参数
     * @param filePath 上传的文件路径
     * @return 包含上传结果以及上传的文件在 OSS 上的访问路径
     */
    public static ResultEntity<String> uploadFileToOss(String endpoint,String accessKeyId,String accessKeySecret,
                                                       String bucketName,String bucketDomain,String filePath) {

        String originalName = filePath.substring(filePath.lastIndexOf("/"));

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 根据日期生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //生成文件名
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));
        //拼接文件名
        String objectName = folderName + "/" + fileMainName + extensionName;



        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(filePath));
        // 上传文件。
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        // 从响应结果中获取具体响应消息
        ResponseMessage responseMessage = putObjectResult.getResponse();
        // 根据响应状态码判断请求是否成功
        try {

            if(responseMessage == null) {
                // 拼接上传文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;
                return ResultEntity.successWithData(ossFileAccessPath);
            } else {
                // 返回错误信息
                int statusCode = responseMessage.getStatusCode();
                String errorMessage = responseMessage.getErrorResponseAsString();
                return ResultEntity.failed(" 当 前 响 应 状 态 码 ="+statusCode+" 错 误 消 息="+errorMessage);
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

    }


}
