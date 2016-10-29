package serviceUnitTest;

import com.joybike.server.api.ThirdPayService.ThirdPayService;
import com.joybike.server.api.ThirdPayService.WxPublicConstructUrlInter;
import com.joybike.server.api.model.ThirdPayBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Created by 58 on 2016/10/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mvc.xml")
public class wxPublicConstrucUnitTest {

//    @Autowired
//    private WxPublicConstructUrlInter wxPublicConstructUrlInter;

    @Autowired
    private ThirdPayService ThirdPayService;
    @Test
    public void test()
    {
        ThirdPayBean payOrder = new ThirdPayBean();
        payOrder.setPruductDesc("longziyuan");
        payOrder.setId(Long.valueOf("1415651231"));
        payOrder.setOrderMoney(BigDecimal.valueOf(0.01));
        payOrder.setOperIP("192.168.0.1");
        payOrder.setOpenid("123");
        payOrder.setChannelId(0);
        ThirdPayService.execute(payOrder);
    }
}
