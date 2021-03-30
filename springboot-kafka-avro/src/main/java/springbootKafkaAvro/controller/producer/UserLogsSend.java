package springbootKafkaAvro.controller.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootKafkaAvro.avro.UserLogs;
import springbootKafkaAvro.result.KafkaResult;
import springbootKafkaAvro.service.userLogs.UserLogsProducerSend;

@RestController
@RequestMapping("/userLogsSend")
public class UserLogsSend {
    @Autowired
    UserLogsProducerSend userLogsProducerSend;

    @GetMapping("/send")
    @CrossOrigin(value = "*", maxAge = 60)
    public void send(String userID,
                     String ip,
                     String sysName,
                     String url,
                     String date,
                     String uuid,
                     String deviceType) {

        UserLogs userLogs = new UserLogs();
        userLogs.setUserID(userID);
        userLogs.setIp(ip);
        userLogs.setSysName(sysName);
        userLogs.setUrl(url);
        userLogs.setDate(date);
        userLogs.setUuid(uuid);
        userLogs.setDeviceType(deviceType);


        KafkaResult result = userLogsProducerSend.send(userLogs);

    }
}
