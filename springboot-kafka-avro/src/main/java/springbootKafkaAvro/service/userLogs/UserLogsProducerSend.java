package springbootKafkaAvro.service.userLogs;

import springbootKafkaAvro.avro.UserLogs;
import springbootKafkaAvro.result.KafkaResult;

public interface UserLogsProducerSend {

    public KafkaResult send(UserLogs userLogs);


}
