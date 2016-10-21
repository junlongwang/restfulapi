package com.joybike.server.api.thirdparty;

/**
 * 短信验证码功能类
 * Created by 58 on 2016/10/21.
 */
public class SMSHelper {

    /**
     * 发送短信验证码
     * @param mobile 手机号
     * @param validateCode 验证码
     * @return
     */
    public static SMSResponse send(String mobile,String validateCode)
    {
        return SMSSender.sendMessage(mobile,validateCode);
    }
}
