package daoUnitTest;

import com.joybike.server.api.dao.BankAcountDao;
import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.model.User;
import com.joybike.server.api.model.userInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 58 on 2016/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class UserInfoDaoUnitTest {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private BankAcountDao bankAcountDao;

    @Test
    public void save()
    {

//        userInfoDao.test();


        //User user= new User("lpf",321,32);
        //userDao.save(user);
    }

    @Test
    public void update()
    {
//        User user= new User("GJF",32,32);
//        user.setId(1);
//        System.out.println("update");
//        System.out.println(userInfoDao);
//        userInfoDao.update();
    }

    @Test
    public void delete()
    {
        userInfoDao.delete(1L);
    }
    @Test
    public void findById() throws Exception {
        User model = new User();
        model.setId(8);
//        User user= userInfoDao.findById(model);
//        System.out.println(user);

//        User model=userDao.findById(8L);
//        System.out.println(model);

        double amount=bankAcountDao.getUserAmount(22);
        System.out.println(amount);

    }


    @Test
    public void find()
    {
////        List<User> list= userInfoDao.query("select * from user", null);
//        System.out.println(list);
//
//        System.out.println("++++++++++++++++++++++++");
//
//        int count= userInfoDao.getCount(null);
//        System.out.println(count);
//
//
//        List<User> users= userInfoDao.getList("select * from user", new HashMap<String,Object>());
//        System.out.println(users);


        userInfo userInfo = null;// userInfoDao.getInfoByPhone("15110184829");
        System.out.println("+++++++++++++++++++++++++");
        System.out.println(userInfo);


        try {
            userInfo= userInfoDao.getUserInfo(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            userInfo = userInfoDao.getInfoByPhone("13910991532");
        } catch (Exception e) {
            e.printStackTrace();
        }

        userInfo.setIphone("15110184829");
        userInfo.setRealName("GJF");

        userInfoDao.save(userInfo);

        System.out.println(userInfo);



    }

}
