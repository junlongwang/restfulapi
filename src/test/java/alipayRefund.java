//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipayTradeAppPayRequest;
//import com.alipay.api.request.AlipayTradePayRequest;
//import com.alipay.api.request.AlipayTradeRefundRequest;
//import com.alipay.api.response.AlipayTradeAppPayResponse;
//import com.alipay.api.response.AlipayTradePayResponse;
//import com.alipay.api.response.AlipayTradeRefundResponse;
//import com.joybike.server.api.thirdparty.wxtenpay.util.AlipayAppUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**支付宝退款
// * Created by 58 on 2016/11/1.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
//public class alipayRefund {
//
//    String APP_ID = "2016102202289143";
//    //String APP_PRIVATE_KEY ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANkzPpaA1tYpcRXEq3l4ykaCeK5AZYj07n8EfdKkQxr6uo2Lzw8g5jwgFdkO330VtoVvClRnrsjF/d6WRiao/6slBIgDrvqEbWEXtudyqLzkl2DZGnmtQpC5q7q56P5bBAMfeli0ZmYW3rjdveGf1TRDGMYYR4x87V998XNA4UgNAgMBAAECgYAEAOXOcGGFYQ4skIt4mblgw1bmH1m/xIQA41xOXai+/pAhu8n9RWX5Bb5hWdzUuWm72+gc1ixqlvuu9qYkYEkWHcjZS4TqOANCqtSCWp4hlRGVCRfHtm1wDL72Z2AF7BZIRwPnRhS9apGm1kCSCH3iSYHJCijZS3T1ooPzWJ1dAQJBAP5A1PfmGvS2WbsCHy44Ib5Gd4JP82SuKLz/IqFu9tD23x7ZLc1zvQVkKfyBr5pFw6+lJ0t2IlSiPW68sdckyXUCQQDasT7V6ZCHxbgVrOb4C7CC4/ZVxUSLTYYKckjECrVKbov+DFaPojy2IGfoJGZaWJlk0FNeOBmQFKZouXJ1dBk5AkBYUlEo5GhMxeOZ0Pzf42PlYzk0rW1RdiZ0sPRou9FFedy8LJl6m0/4RXlIXAySPNXjeC2USy9V0x4gD7B/minZAkEAwQZS1NIrvHr6iT8sOeFvcYguI/RTFLVfSxcmPMrKyyCZtalEOdDTz1j4/YArSzEKa14pR28yuOZRHvwYF61amQJAA2GH8JB4p4rGx/yw0dY3ZDGJMpjOed3MZDnJ1blrDG3akdJM8qU8H/niH5uLm5zYjAk0VyUmg/9jXJyHFfjpsw==";
//    //String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
//    //"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZMz6WgNbWKXEVxKt5eMpGgniuQGWI9O5/BH3SpEMa+rqNi88PIOY8IBXZDt99FbaFbwpUZ67Ixf3elkYmqP+rJQSIA676hG1hF7bncqi85Jdg2Rp5rUKQuau6uej+WwQDH3pYtGZmFt643b3hn9U0QxjGGEeMfO1fffFzQOFIDQIDAQAB";
//
//
//    // 商户的私钥
//    public static String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALCjQgopUmAVdg8Ekie+7k/qu1PixS6OLJzoiYg2ZU/G90/pgHyNgCpPUNSRNOq3s2w2zOLtJMN1JN4x4aaGoTHYSgpghQr+QwOsuuogRKb0pLZkTnoh05NAfi1DX6dDnZ9/ooFXbWNhKeksE8eCiMKLoVasOwL6AN1RJ1fM748lAgMBAAECgYAItVPJKnZcNFKotOH7wacAG6N2pERyYiIC7lfxdjUSdM22i92Axn0eGOD0SeBg/gODf0Qkn+pjFIBnz++/BP4nYPY0CYoNOSO2S5f7v+xbtS4amC5nBWv5kxtcFvREhvsoPE9i7drUmKYkFRR/mmBklJIlFiBVHykPCO/bXcdBlQJBANx9EsigJK5r9PEQTq2z5GWA/66IEhicp1ldUq2k6uuowRJxzXQFLX3jzNg7lTA/eX0oxYjjL6sHCzZaqsygPmMCQQDNFi2SLq8SXF8M79RDuwUmpZfx/e6qc+5F+Yn7ChamGs3UdcZASVp0hxo//YpHgs/BKXDedPaba73ST3bqE07XAkBQifNJi426lL6lK6LBuntMRIGgvB14FgjfEMK5oQsax8q2tREqNxX17TcPKTyGojj7aeA1716jJ3CGCzpmgoYnAkEAspjNtq/Q5jxqyelRAGqtYapzV9m7LdUneuiEsIloj95nwM2PiAxZKYE96tvwv7W7FovwLsnMuCxrceqhs9Z8oQJAIcaccDaEhyZfJfTxOU4pIT0ctqDZ0rTZEWX4WwRdl83r60FoEbokqtrEoIGoa6Q//ClPkReDV7SljqvxYOgY9g==";
//    // 支付宝的公钥，无需修改该
//    public static String ALIPAY_PUBLIC_KEY  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
//    public static String Md5_key = "phetx8fnkr8obzc9gqfuxhbe4xj6no40";
//
//    @Test
//    public void fun()
//    {
//
//        //实例化客户端
//        AlipayClient client = new DefaultAlipayClient("https://mapi.alipay.com/gateway.do",APP_ID,APP_PRIVATE_KEY,"json","utf-8",ALIPAY_PUBLIC_KEY);
//        ////创建API对应的request类
//        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
//
//
//
//
//
////        request.setBizContent("\"{" +
////                "\"out_trade_no\":\"316\"," +
////                "\"trade_no\":\"2016111521001004640261548793\"," +
////                //"    \"out_request_no\":\"316\"\n" +
////                "\"refund_amount\":\"0.01\"" +
////                "}\"");//设置业务参数
//
//        request.setBizContent("{out_trade_no\":\"316\",trade_no\":\"2016111521001004640261548793\", \"refund_amount\":0.01,\"refund_reason\":\"正常退款\"}");
//
//
//
//
//        //通过alipayClient调用API，获得对应的response类
//        try {
//            AlipayTradeRefundResponse response = client.execute(request);
//            System.out.println(response);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void test()
//    {
//        AlipayClient client = new DefaultAlipayClient("https://mapi.alipay.com/gateway.do",APP_ID,APP_PRIVATE_KEY,"json","GBK",ALIPAY_PUBLIC_KEY);
//        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//        request.setNotifyUrl("");
//        AlipayTradeAppPayResponse response = null;
//        try {
//            response = client.execute(request);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        if(response.isSuccess()){
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
//    }
//}
