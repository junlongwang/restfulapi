//package myTest; /**
// * Created by 58 on 2016/10/27.
// */
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.math.BigDecimal;
//
//public class Main {
//    public static void main(String[] args) {
//        FileInputStream fis = null;
//        InputStreamReader isr = null;
//        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
//        try {
//            String str = "";
//            String str1 = "";
//            fis = new FileInputStream("D:\\Tencent\\QQ\\Users\\744360792\\FileRecv\\调用日志.log");// FileInputStream
//            // 从文件系统中的某个文件中获取字节
//            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
//            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
//            while ((str = br.readLine()) != null) {
//                str1 += str +" "+httpPost(str)+"\n";
//            }
//            // 当读取的一行不为空时,把读到的str的值赋给str1
//            System.out.println(str1);// 打印出str1
//        } catch (FileNotFoundException e) {
//            System.out.println("找不到指定文件");
//        } catch (IOException e) {
//            System.out.println("读取文件失败");
//        } finally {
//            try {
//                br.close();
//                isr.close();
//                fis.close();
//                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    static RestTemplate restTemplate = new RestTemplate();
//    public static String httpPost(String url)
//    {
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        headers.add("MyRequestHeader","MyValue");
//        //String url="http://amortize.web.58dns.org/consume?payId=178538428072626884&consumeTime=1472525116000&consumeAmount=4.00&totalAmount=54.00&isLastTime=1&flowId=947671856&productId=null&cateOne=2&cateTwo=null&cateThree=null&cityId=-1&userId=1050848302&sourceSys='gj'";
//        String contents = url.substring(38);
//        String[] kv=contents.split("&");
//
//        Amortize amortize = new Amortize();
//        int i=0;
//        for (String item : kv)
//        {
//            String k=item.split("=")[0];
//            String v=item.split("=")[1];
//
//            System.out.println(k+"="+v);
//
//            ++i;
//            switch (i)
//            {
//                case 1:
//                    amortize.setPayId(Long.valueOf(v));
//                    break;
//
//                case 2:
//                    amortize.setConsumeTime(Long.valueOf(v));
//                    break;
//                case 3:
//                    amortize.setConsumeAmount(BigDecimal.valueOf(Double.valueOf(v)));
//                    break;
//                case 4:
//                    amortize.setTotalAmount(BigDecimal.valueOf(Double.valueOf(v)));
//                    break;
//                case 5:
//                    amortize.setIsLastTime(Integer.valueOf(v));
//                    break;
//                case 6:
//                    amortize.setFlowId(Integer.valueOf(v));
//                    break;
//                case 7:
//                    //amortize.setProductId(Integer.valueOf(v));
//                    break;
//                case 8:
//                    amortize.setCateOne(Integer.valueOf(v));
//                    break;
//                case 9:
//                case 10:
//                    break;
//                case 11:
//                    amortize.setCityId(Integer.valueOf(v));
//                    break;
//                case 12:
//                    amortize.setUserId(Long.valueOf(v));
//                    break;
//                case 13:
//                    amortize.setSourceSys("gj");
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        System.out.println(amortize);
//
//
//        HttpEntity<Amortize> formEntity = new HttpEntity<Amortize>(amortize,headers);
//        String result = restTemplate.postForObject("http://amortize.web.58dns.org/consume", formEntity, String.class);
//        return result;
//    }
//}
