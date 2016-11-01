package doc;

import com.alibaba.fastjson.JSON;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dto.LoginData;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.dto.VehicleOrderSubscribeDto;
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
import org.springframework.http.ResponseEntity;
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


        VehicleOrderSubscribeDto dto = new VehicleOrderSubscribeDto();

        VehicleOrderDto dto1 = new VehicleOrderDto();
        dto1.setBeforePrice(BigDecimal.valueOf(1));
        dto1.setCyclingTime(100);
        dto1.setBeginAt(1344508271);
        dto1.setBeginDimension(BigDecimal.valueOf(123.345));

        dto1.setBeginLongitude(BigDecimal.valueOf(40.345));
        dto1.setUserId(Long.valueOf(1));
        dto1.setOrderCode("20161011");
        dto1.setStatus(1);
        dto1.setVehicleId("JOY001");
        dto1.setEndAt(0);
        dto1.setPayId(Long.valueOf(0));
        dto1.setId(Long.valueOf(1));


        subscribeInfo info = new subscribeInfo();

        info.setCreateAt(1344508271);
        info.setSubscribeCode("test");
        info.setVehicleId("JOY001");
        info.setStatus(1);
        info.setStartAt(1344508271);
        info.setEndAt(1344518271);
        info.setId(Long.valueOf(1));
        info.setUserId(Long.valueOf(1));
        dto.setInfo(info);

        System.out.println("1000:::" + JSON.toJSON(ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 0, null, dto))));

//        System.out.println(new Message<List<product>>(false, ReturnEnum.Product_Error.getErrorCode(), ReturnEnum.Product_Error.getErrorDesc() + "-" + "error", null));
    }
}

