package daoUnitTest;

import com.joybike.server.api.dao.UserDao;
import com.joybike.server.api.model.User;
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
public class UserDaoUnitTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void save()
    {
        User user= new User("lpf",321,32);
        userDao.save(user);
    }

    @Test
    public void update()
    {
        User user= new User("GJF",32,32);
        user.setId(1);
        System.out.println("update");
        System.out.println(userDao);
        userDao.update(user);
    }

    @Test
    public void delete()
    {
        userDao.delete(1L);
    }
    @Test
    public void findById()
    {
        User model = new User();
        model.setId(8);
        User user=userDao.findById(model);
        System.out.println(user);

//        User model=userDao.findById(8L);
//        System.out.println(model);
    }


    @Test
    public void find()
    {
        List<User> list=userDao.query("select * from user", null);
        System.out.println(list);

        System.out.println("++++++++++++++++++++++++");

        int count=userDao.getCount(null);
        System.out.println(count);


        List<User> users=userDao.getList("select * from user", new HashMap<String,Object>());
        System.out.println(users);


    }

}
