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

import java.util.HashMap;
import java.util.Map;

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

    //@Test
    public void testEnumRedis(){
        enumRedisUtils.set("test", "tset_enum");

    }

    //@Test
    public void testAnalogRedis(){
        analogRedisUtils.set("test", "tset_analog");
    }

    @Test
    public void testMulitSet(){
        Map<String, String> map = new HashMap<>();
        map.put("test1", "1");
        map.put("test2", "2");
        map.put("test3", "3");
        map.put("test4", "4");

        analogRedisUtils.sets(map);
    }
}
