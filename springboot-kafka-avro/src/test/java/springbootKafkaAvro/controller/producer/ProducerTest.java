package springbootKafkaAvro.controller.producer;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import springbootKafkaAvro.TestMain;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.UUID;

public class ProducerTest extends TestMain {

    @Test
    public void test02() throws Exception {


        while(true){
            HashMap<String, String> map = new HashMap<>();
            map.put("userID", String.valueOf(new SecureRandom().nextInt()));
            map.put("ip", "111.123.321.123");
            map.put("sysName", "oa");
            map.put("url", "/oa/login/toPage");
            map.put("date", String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()));
            map.put("uuid", UUID.randomUUID().toString());
            map.put("deviceType","web");

            System.out.println("发送请求>>>" );
            buildGet("/userLogsSend/send", map);
            Thread.sleep(1000);
        }




    }
}
