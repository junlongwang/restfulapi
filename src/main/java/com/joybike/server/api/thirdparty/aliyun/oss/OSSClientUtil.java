package com.joybike.server.api.thirdparty.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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

    {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    public static void main(String[] args) {

//        OSSClientUtil util=new OSSClientUtil();
//        String url=util.uploadImg2Oss();
//        System.out.println(url);
//        System.out.println(util.getImgUrl(url));
//        util.destory();

        String pic_path = "C:\\Users\\58\\Desktop\\WechatIMG23.jpeg";//图片路径
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(pic_path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String url=uploadRepairImg(fileInputStream);
            System.out.println(url);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String uploadUserImg(InputStream inputStream )
    {
        OSSClientUtil util=new OSSClientUtil();
        String name = UUID.randomUUID().toString()+".jpg";
        util.uploadFile2OSS(inputStream, "userImg/",name);
        util.destory();
        return OSSConsts.PRE_IMG_URL+"userImg/"+name;
    }

    public static String uploadRepairImg(InputStream inputStream)
    {
        OSSClientUtil util=new OSSClientUtil();
        String name = UUID.randomUUID().toString()+".jpg";
        util.uploadFile2OSS(inputStream, "RepairImg/",name);
        util.destory();
        return OSSConsts.PRE_IMG_URL+"RepairImg/"+name;
    }

    public static String uploadGPSImag(String pic_path)
    {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(pic_path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            OSSClientUtil util=new OSSClientUtil();
            String name = UUID.randomUUID().toString()+".jpg";
            util.uploadFile2OSS(fileInputStream, "GPSImage/", name);
            fileInputStream.close();
            util.destory();
            return OSSConsts.PRE_IMG_URL+"GPSImage/"+name;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
    public String uploadFile2OSS(InputStream instream,String dir, String fileName) {
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
            PutObjectResult putResult = ossClient.putObject(bucketName, dir + fileName, instream, objectMetadata);
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
        // 设置URL过期时间为50年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 50);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        System.out.println(url);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    public static String uploadUserImg(byte[] bytes)
    {
        OSSClientUtil util=new OSSClientUtil();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        String name = UUID.randomUUID().toString()+".jpg";
        util.uploadFile2OSS(inputStream,"userImg/", name);
        util.destory();
        return OSSConsts.PRE_IMG_URL+"userImg/"+name;
    }

    private static InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    private static byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    private String uploadImg2Oss() {
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
            this.uploadFile2OSS(inputStream,"temp", name);
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            //throw new ImgException("图片上传失败");
        }
        return  null;
    }
}
