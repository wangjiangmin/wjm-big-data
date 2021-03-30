package springbootKafkaAvro.service.userLogs.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import springbootKafkaAvro.avro.UserLogs;
import springbootKafkaAvro.result.KafkaResult;
import springbootKafkaAvro.service.userLogs.UserLogsProducerSend;

@Service
public class UserLogsProducerSendImpl implements UserLogsProducerSend {

    @Value("${k-topic.name}")
    private String TOPIC;

    @Autowired
    KafkaTemplate<String, UserLogs> kafkaTemplate;


    public KafkaResult send(UserLogs userLogs) {

        this.kafkaTemplate.send(this.TOPIC, userLogs.getSysName(), userLogs);
        kafkaTemplate.flush();

        return KafkaResult.ok();
    }

}
