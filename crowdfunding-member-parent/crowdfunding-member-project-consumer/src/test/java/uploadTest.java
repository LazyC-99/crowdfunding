import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.lzc.crowd.util.ResultEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class uploadTest {

    public static void main(String[] args) throws FileNotFoundException {
        ResultEntity<String> resultEntity = uploadFileToOss("http://oss-cn-chengdu.aliyuncs.com", "LTAI4G3dA7qmTqHcfynuBd9y", "WJbp5YX7jEZoA8a0gGzUTXIBvwbwQl", "lzc-oss", "http://lzc-oss.oss-cn-chengdu.aliyuncs.com", "C:/Users/Administrator/Desktop/课堂文件/pic/img(1).jpg");
        System.out.println(resultEntity);
    }


    public static ResultEntity<String> uploadFileToOss(String endpoint,String accessKeyId,String accessKeySecret,
                                                       String bucketName,String bucketDomain,String filePath) throws FileNotFoundException {


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 根据日期生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //生成文件名
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        // 从原始文件名中获取文件扩展名
        String extensionName = filePath.substring(filePath.lastIndexOf("."));
        //拼接文件名
        String objectName = folderName + "/" + fileMainName + extensionName;



        // 创建PutObjectRequest对象。
        File file= new File(filePath);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file);
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
