package daoUnitTest;

import com.joybike.server.api.dao.UserCouponDao;
import com.joybike.server.api.model.userCoupon;
import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.util.UnixTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


/**
 * Created by lishaoyong on 16/10/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class userCouponTest {

    @Autowired
    UserCouponDao userCouponDao;

    @Test
    public void insertTest(){
        userCoupon userCoupon = new userCoupon(Long.valueOf(3),Long.valueOf(1), UnixTimeUtils.now(),0,UnixTimeUtils.now());
        long id = userCouponDao.save(userCoupon);
        System.out.println(id + "：");
    }

    @Test
    public void updateTest(){
        Map map = new HashMap();
        map.put("status",1);
        map.put("userId",1);
        map.put("couponId", 1);
        long i = userCouponDao.updateCoupon(map);
        System.out.println(i + ":");
    }

    @Test
    public void getTest(){
        List<userCoupon> list = userCouponDao.getValidList(1,0);


        System.out.println(list.size());

    }

    @Test
    public void getCountTest(){
        int vaCount = userCouponDao.getValidCount(1,0);
        System.out.println(vaCount + "个");
    }
}
