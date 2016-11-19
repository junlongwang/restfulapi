package restfulUnitTest;

import com.alibaba.fastjson.JSON;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.dto.UserPayIngDto;
import com.joybike.server.api.model.Message;
import com.joybike.server.api.model.vehicle;
import com.joybike.server.api.restful.BicycleRestfulApi;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.thirdparty.amap.AMapUtil;
import com.joybike.server.api.util.UnixTimeUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 58 on 2016/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class BicycleRestfulApiUnitTest {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private OrderRestfulService orderRestfulService;

    private final Logger logger = Logger.getLogger(BicycleRestfulApi.class);

    @Test
    public void fun()
    {
        vehicle vehicle = null;
        try {
            vehicle = vehicleDao.getVehicleBylockId("200000010");
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserPayIngDto dto = null;
        try {
            dto = orderRestfulService.userPayOrder(vehicle.getVehicleId(), UnixTimeUtils.now(), Double.valueOf("40.043"), Double.valueOf("40.043"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //消息推送
        logger.info("--------------------消息推送--------------------");
        logger.info(dto);
        logger.info("--------------------消息推送--------------------");
        logger.info(JSON.toJSON(dto));
        System.out.println(JSON.toJSON(dto));

        Message<UserPayIngDto> message = new Message<>();
        message.setData(dto);
        message.setIsSuccess(true);
        message.setErrorCode(0);
        logger.info("--------------------消息推送--------------------");
        System.out.println(JSON.toJSON(message));
    }

    @Test
    public void test()
    {
        String address= AMapUtil.getAddress("40.043,40.043");
        System.out.println(address);
    }
}
