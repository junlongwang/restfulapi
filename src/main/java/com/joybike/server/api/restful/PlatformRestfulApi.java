package com.joybike.server.api.restful;

import com.joybike.server.api.dto.Token;
import com.joybike.server.api.model.Message;
import com.joybike.server.api.thirdparty.aliyun.redix.RedixUtil;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 服务平台
 * Created by 58 on 2016/10/27.
 */
@RequestMapping("/platform")
@RestController()
public class PlatformRestfulApi {

    static List<String> appKeys= new ArrayList<String>();

    static
    {
        appKeys.add("6cf6ead412df6ad88880c4742be3d2f6"); //安卓
        appKeys.add("6566567b50be0ac7b85076ae76acbaa7");//IOS
        appKeys.add("f3c75e05f57b4385bdf5f0a0812d996a");//h5
    }

    /**
     * 根据用户appkey获取Token
     * @param appKey
     * @return
     */
    @RequestMapping(value = "getToken", method = RequestMethod.GET)
    public ResponseEntity<Message<Token>> getToken(String appKey)
    {
        //如果appKey非法，返回失败
        if(!appKeys.contains(appKey))
        {
            return  ResponseEntity.ok(new Message<Token>(false,1000,"非法请求",null));
        }
        String access_token = null;
        Token token = null;
        try {
            if(RedixUtil.exits(appKey))
            {
                access_token = RedixUtil.getString(appKey);
            }
            else
            {
                access_token = UUID.randomUUID().toString();
            }
            token = new Token(access_token,300);
        }
        catch (Exception e)
        {
            Logger logger = Logger.getLogger(PayRestfulApi.class);
            logger.fatal("**************************************");
            logger.fatal(appKey+"获取认证Token失败！",e);
            logger.fatal("**************************************");
        }
        return  ResponseEntity.ok(new Message<Token>(true,0,null,token));
    }
}
