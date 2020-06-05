import com.casco.opgw.kafkatoredis.KafkaToRedisApplication;
import com.casco.opgw.kafkatoredis.redis.AnalogRedisUtils;
import com.casco.opgw.kafkatoredis.redis.DigitalRedisUtils;
import com.casco.opgw.kafkatoredis.redis.EnumRedisUtils;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = KafkaToRedisApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class RedusUtilsTest {

    @Autowired
    private DigitalRedisUtils digitalRedisUtils;

    @Autowired
    private EnumRedisUtils enumRedisUtils;

    @Autowired
    private AnalogRedisUtils analogRedisUtils;



    //@Test
    public void testDigitalRedis(){
        digitalRedisUtils.set("test", "tset_digit");

    }

    @Test
    public void testEnumRedis(){
        enumRedisUtils.set("test", "tset_enum");

    }

    //@Test
    public void testAnalogRedis(){
        analogRedisUtils.set("test", "tset_analog");

    }
}
