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

        product p = new product(Long.valueOf(1),"pd_recharge","充90送10块",BigDecimal.valueOf(90),BigDecimal.valueOf(10),Long.valueOf(1),UnixTimeUtils.now(),Long.valueOf(0),0);
        product p1 = new product(Long.valueOf(1),"pd_recharge","充85送5块",BigDecimal.valueOf(90),BigDecimal.valueOf(10),Long.valueOf(1),UnixTimeUtils.now(),Long.valueOf(0),0);

        List<product> l = new ArrayList<product>();
        l.add(p);
        l.add(p1);


        System.out.println(JSON.toJSON(new Message<List<product>>(true, 0, null, l)));

        System.out.println(new Message<List<product>>(false, ReturnEnum.Product_Error.getErrorCode(), ReturnEnum.Product_Error.getErrorDesc() + "-" + "error", null));
    }
}

