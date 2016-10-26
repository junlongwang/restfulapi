package doc;

import com.alibaba.fastjson.JSON;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dto.LoginData;
import com.joybike.server.api.dto.vehicleRepairDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.util.UnixTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishaoyong on 16/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class test {

    @Autowired
    PayRestfulService payRestfulService;
    @Autowired
    UserRestfulService userRestfulService;
    @Autowired
    OrderRestfulService orderRestfulService;
    @Autowired
    BicycleRestfulService bicycleRestfulService;

    @Test
    public void test(){


            bankDepositOrder b = new bankDepositOrder(Long.valueOf(1),Long.valueOf(1),BigDecimal.valueOf(8),BigDecimal.valueOf(2),0,BigDecimal.valueOf(8),BigDecimal.valueOf(2),2,0,"","d_f_a1234","","",123,0,123);

        bankDepositOrder b1 = new bankDepositOrder(Long.valueOf(2),Long.valueOf(0),BigDecimal.valueOf(8),BigDecimal.valueOf(2),0,BigDecimal.valueOf(8),BigDecimal.valueOf(2),2,0,"","d_f_a1234","","",123,0,123);

        List<bankDepositOrder> l = new ArrayList<bankDepositOrder>();
        l.add(b);
        l.add(b1);
        String mo = "13910991532";

        userInfo userInfo = null;
        try {
            userInfo = userRestfulService.getUserInfoByMobile(mo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginData loginData = new LoginData(String.valueOf(1234), userInfo);

        System.out.println(JSON.toJSON(new Message<LoginData>(false, ReturnEnum.UseRregister_Error.getErrorCode(),ReturnEnum.UseRregister_Error.getErrorDesc(), null)));

        System.out.println(JSON.toJSON(new Message<LoginData>(true, 0,null, loginData)));
    }
}

