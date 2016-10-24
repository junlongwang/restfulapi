package com.joybike.server.api.thirdparty.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * Created by 58 on 2016/10/23.
 */

/**
 * 阿里云 OSS文件类
 *
 * @author YuanDuDu
 */
public class OSSClientUtil {

    //Log log = LogFactory.getLog(OSSClientUtil.class);
    // endpoint以杭州为例，其它region请按实际情况填写
    private String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    // accessKey
    private String accessKeyId = "LTAIRYcpxEtj4Jo2";
    private String accessKeySecret = "OhG9MVQzO092ZIif6kJk3pzl3bdvx9";
    //空间
    private String bucketName = "joybike-user";
    //文件存储目录
    private String filedir = "data/";

    private OSSClient ossClient;

    public OSSClientUtil() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    public static void main(String[] args) {

        OSSClientUtil util=new OSSClientUtil();
        String url=util.uploadImg2Oss();
        System.out.println(url);
        System.out.println(util.getImgUrl(url));
        util.destory();
    }

    public static String updateHead() throws IOException{
//        if (file == null || file.getSize() <= 0) {
//            //throw new ImgException("头像不能为空");
//        }

        String pic_path = "C:\\Users\\58\\Desktop\\图片1.jpg";//图片路径
        FileInputStream is;
        try {
            is = new FileInputStream(pic_path);

            int i = is.available(); // 得到文件大小
            byte data[] = new byte[i];
            is.read(data); // 读数据
            is.close();
            //response.setContentType("image/*"); // 设置返回的文件类型
            //OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
            //toClient.write(data); // 输出数据
            //toClient.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 上传图片
     *
     */
//    public void uploadImg2Oss(String url) {
//        File fileOnServer = new File(url);
//        FileInputStream fin;
//        try {
//            fin = new FileInputStream(fileOnServer);
//            String[] split = url.split("/");
//            this.uploadFile2OSS(fin, split[split.length - 1]);
//        } catch (FileNotFoundException e) {
//            throw new ImgException("图片上传失败");
//        }
//    }


    public String uploadImg2Oss() {
//        if (file.getSize() > 1024 * 1024) {
//            throw new ImgException("上传图片大小不能超过1M！");
//        }

        String pic_path = "C:\\Users\\58\\Desktop\\图片1.jpg";//图片路径
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(pic_path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String originalFilename = "图片1.jpg";
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = fileInputStream;
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            //throw new ImgException("图片上传失败");
        }
        return  null;
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        if (!fileUrl.equals("") && fileUrl!=null) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            e.printStackTrace();
            //log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") ||
                FilenameExtension.equalsIgnoreCase("jpg") ||
                FilenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase("pptx") ||
                FilenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase("docx") ||
                FilenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        System.out.println(url);
        if (url != null) {
            return url.toString();
        }
        return null;
    }
}
