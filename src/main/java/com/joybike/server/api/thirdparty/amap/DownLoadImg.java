package com.joybike.server.api.thirdparty.amap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DownLoadImg{
    public static void main(String args[]){
        String  name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String path = "E:/image/";                      /*把要下载的图片存档在img文件夹下*/
        File dir = new File(path);
        if(!dir.exists())
            dir.mkdir();                                                /*如果目录不存在则创建目录*/
        path += name + ".jpg";
        //download("http://restapi.amap.com/v3/staticmap?zoom=12&scale=2&size=1024*1024&markers=mid,0xFF0000,起:116.215299,40.067835|mid,0xFF0000,终:116.305936,39.980616&paths=4,0x0000ff,1,,:116.215299,40.067835;116.215299,40.067835;116.227615,40.069182;116.23534,40.071054;116.243408,40.073156;116.248429,40.064617;116.250876,40.057949;116.254266,40.053744;116.257527,40.050361;116.258514,40.04471;116.26551,40.039651;116.271132,40.034459;116.272848,40.032258;116.286367,40.016286;116.287139,40.015004;116.287182,40.013295;116.28907,40.002152;116.294778,39.997418;116.295636,39.993933;116.298769,39.991138;116.300228,39.985022;116.305528,39.985121;116.305936,39.980616&key=ee95e52bf08006f63fd29bcfbcf21df0",path);
        download("http://restapi.amap.com/v3/staticmap?zoom=12&scale=1&size=1024*1024&markers=mid,0x26df3f,起:116.312588,39.955455|mid,0xFF0000,终:116.390908,39.92232&paths=8,0x0000ff,1,,:116.312588,39.955455;116.312588,39.955455;116.309412,39.955159;116.309927,39.946638;116.310185,39.942163;116.318381,39.939333;116.33293,39.938281;116.343144,39.938412;116.349838,39.939597;116.355718,39.940353;116.355889,39.932489;116.367949,39.932555;116.373227,39.932489;116.373098,39.924163;116.377304,39.92413;116.37739,39.922451;116.388119,39.922419;116.390908,39.92232&key=ee95e52bf08006f63fd29bcfbcf21df0",path);
        //download("http://restapi.amap.com/v3/staticmap?zoom=12&scale=1&size=1024*1024&markers=mid,0x49ee5e,%E8%B5%B7:116.335118,39.907442|mid,0xf13750,%E7%BB%88:116.399878,39.857122&paths=6,0x4c68eb,1,,:116.335118,39.907442;116.335118,39.907442;116.338938,39.907409;116.344817,39.907146;116.350911,39.907277;116.356748,39.907146;116.356791,39.902076;116.36267,39.899277;116.370438,39.899639;116.374257,39.899804;116.3746,39.894832;116.374643,39.891869;116.374128,39.889103;116.3743,39.886172;116.374472,39.883077;116.374386,39.878434;116.3743,39.872439;116.374515,39.870233;116.374214,39.86763;116.373699,39.863579;116.37224,39.860054;116.371425,39.856562;116.374858,39.856958;116.386788,39.857221;116.399878,39.857122&key=ee95e52bf08006f63fd29bcfbcf21df0",path);
    }
    public static void download(String strUrl,String path){
        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
            return;
        }


        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }


        OutputStream os = null;
        try{
            os = new FileOutputStream(path);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while((bytesRead = is.read(buffer,0,8192))!=-1){               /*buffer数组存放读取的字节，如果因为流位于文件末尾而没有可用的字节，则返回值-1，以整数形式返回实际读取的字节数*/
                os.write(buffer,0,bytesRead);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}