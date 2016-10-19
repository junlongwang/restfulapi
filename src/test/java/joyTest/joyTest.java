package joyTest;

import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.service.UserInfoService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by lishaoyong on 16/10/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class joyTest {


    @Autowired
    UserInfoService userInfoService;

    @Test
    public void joy() {
        userInfo userInfo = new userInfo();
        userInfo.setId(Long.valueOf(1));
        userInfo.setRealName("龙子渊");
        userInfo.setIdNumber("1234567890");
        int u = userInfoService.updateUserInfo(userInfo);
        System.out.println(u + ":");
    }

}
