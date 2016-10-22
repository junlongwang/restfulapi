package com.joybike.server.api.thirdparty;

/**
 * 短信验证码功能类
 * Created by 58 on 2016/10/21.
 */
public class SMSHelper {

    /**
     * 发送短信控制车辆
     * @param mobile
     * @param content
     * @return
     */
    public static SMSResponse send(String mobile,String content)
    {
        return SMSSender.sendMessage(mobile,content);
    }

    /**
     * 发送短信验证码
     * @param mobile 手机号
     * @param validateCode 验证码
     * @return
     */
    public static SMSResponse sendValidateCode(String mobile,String validateCode)
    {
        return SMSSender.sendValidateCode(mobile, validateCode);
    }

    public static void main(String[] args) {

        //发送短信验证码
        SMSResponse response = sendValidateCode("15110184829","1221");

        //发送车辆控制信息
        //response = send("15110184829","00");
        System.out.println(response);
    }
}
