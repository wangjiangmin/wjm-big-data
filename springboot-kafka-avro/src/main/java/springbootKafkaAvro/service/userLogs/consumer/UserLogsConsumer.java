package springbootKafkaAvro.service.userLogs.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import springbootKafkaAvro.avro.UserLogs;

@Service
public class UserLogsConsumer {

   private static final Logger log = LoggerFactory.getLogger(UserLogsConsumer.class);

    @KafkaListener(topics = "#{'${k-topic.name}'}",groupId = "simple-consumer",id = "wjm-consumer")
    public void consumerPrint(@Payload(required = false)UserLogs userLogs){

        log.info(String.format("消费者 0 消费消息: %s",userLogs.toString()));

    }

//    @KafkaListener(topics = "#{'${k-topic.name}'}",groupId = "simple-consumer",id = "wjm-consumer1")
//    public void consumerPrint(String userLogs){
//
//        log.info(String.format("消费者 1 消费消息: %s",userLogs));
//
//    }
}
