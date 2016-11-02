package myTest; /**
 * Created by 58 on 2016/10/27.
 */
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {


        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            String str = "";
            String str1 = "";
            fis = new FileInputStream("D:\\Tencent\\QQ\\Users\\744360792\\FileRecv\\58摊销异常数据.txt");// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            int i=0;
            while ((str = br.readLine()) != null) {
                str1 += " "+str +" "+httpPost(str)+"\n";
            }
            // 当读取的一行不为空时,把读到的str的值赋给str1
            //System.out.println(str1);// 打印出str1
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static RestTemplate restTemplate = new RestTemplate();
    public static String httpPost(String line)
    {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        //String url="http://amortize.web.58dns.org/consume?payId=178538428072626884&consumeTime=1472525116000&consumeAmount=4.00&totalAmount=54.00&isLastTime=1&flowId=947671856&productId=null&cateOne=2&cateTwo=null&cateThree=null&cityId=-1&userId=1050848302&sourceSys='gj'";
        String contents = line;
        String[] kv=contents.split(",");


        Amortize amortize = new Amortize();
        for (String value : kv)
        {
            if(value.equals("payId=") ||value.equals("payId=0") )
            {
                break;
            }
            String[] filed=value.split("=");


            try {
                String aa=filed[1];
            }
            catch (Exception e)
            {
                System.out.println(line+" ++++++++++++++++++");
                //break;
            }


            if(filed[0].equals("payId"))
            {
                amortize.setPayId(Long.valueOf(filed[1]));
            }
            if(filed[0].equals("consumeTime"))
            {
                amortize.setConsumeTime(Long.valueOf(filed[1])*1000);
            }
            if(filed[0].equals("flowId"))
            {
                amortize.setFlowId(Integer.valueOf(filed[1]));
            }
            if(filed[0].equals("cateOne"))
            {
                amortize.setCateOne(Integer.valueOf(filed[1]));
            }
            if(filed[0].equals("cityId"))
            {
                amortize.setCityId(Integer.valueOf(filed[1]));
            }
            if(filed[0].equals("userId"))
            {
                amortize.setUserId(Long.valueOf(filed[1]));
            }
            if(filed[0].equals("consumeAmount"))
            {
                if(filed[1]=="-1.00" || filed[1].equals("-1.00"))
                {
                    amortize.setConsumeAmount(BigDecimal.ONE);
                }
                else {
                    Double n = Math.abs(Double.valueOf(filed[1]));
                    if(n<0)
                    {
                        n=0-n;
                    }
                    amortize.setConsumeAmount(BigDecimal.valueOf(n));
                }
            }
            if(filed[0].equals("totalAmount"))
            {
                amortize.setTotalAmount(BigDecimal.valueOf(Double.valueOf(filed[1])));
            }


        }
        amortize.setIsLastTime(0);
        amortize.setSourceSys("pmc");
        if(amortize!=null && amortize.getPayId()!=null && amortize.getPayId()>0) {
//            HttpEntity<Amortize> formEntity = new HttpEntity<Amortize>(amortize,headers);
//            String result = restTemplate.postForObject("http://amortize.web.58dns.org/consume", formEntity, String.class);
//            return result;

            String json= JSON.toJSONString(amortize);
            //HttpEntity<String> formEntity = new HttpEntity<String>(json,headers);
            //String result = restTemplate.postForObject("http://amortize.web.58dns.org/consume", formEntity, String.class);

            String result = HttpRequest.sendPost("http://amortize.web.58dns.org/consume",json);
            System.out.println(json + " "+result);
        }
        return "手工补偿库存，无需摊销";
    }
}
