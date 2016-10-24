import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.util.XStreamUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 58 on 2016/10/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class xstreamUnitTest {

    @Test
    public void toXmltest()
    {
        Book book = new Book("gjf","gengjf","23.3");
        String xml = XStreamUtils.toXml(book);
        System.out.println(xml);
    }


    @Test
    public void toObject()
    {
        String xml = "<book price=\"23.3\">\n" +
                "  <name>gjf</name>\n" +
                "  <author>gengjf</author>\n" +
                "</book>";
        Book book= XStreamUtils.toBean(xml,Book.class);
        System.out.println(book);
    }



}
