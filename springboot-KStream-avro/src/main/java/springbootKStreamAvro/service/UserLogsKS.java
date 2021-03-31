package springbootKStreamAvro.service;

import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.confluent.shaded.serializers.StringSerde;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import springbootKStreamAvro.avro.UserLogs;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
@EnableKafkaStreams
public class UserLogsKS {

    @Value("${k-topic.name}")
    private String TOPIC;
    @Autowired
    private PrometheusMeterRegistry meterRegistry;

    private AtomicLong gauge;

    @PostConstruct
    private void init(){
        gauge = meterRegistry.gauge("logs",Tags.of("sysName","oa"), new AtomicLong(0));
    }

    @Bean
    public KStream<String, UserLogs> statisticsLogs(StreamsBuilder streamBuilder){

        KStream<String, UserLogs> stream = streamBuilder.stream(TOPIC);

//        GenericAvroSerde

//        stream.print(Printed.toSysOut());
//        stream.foreach((k, v) -> System.out.println("系统:" + k + "次数:" + v));
        // 访问次数
        KTable<String, Long> visitNumber = stream.groupByKey().count();

        // 访问人次
        KTable<Integer, Long> persons = stream.map((k, v) -> {
            return new KeyValue<Integer, UserLogs>(Integer.valueOf(v.getUserID()), v);
        }).groupByKey().count();

        // ip访问次数
//        KTable<String, Long> ipCount = stream.map((k, v) -> {
//            return new KeyValue<String, UserLogs>(v.getIp(), v);
//        }).groupByKey().count();

//        visitNumber.toStream().print(Printed.<String,Long>toSysOut().withLabel("+++++++++++++++++++++++"));
        visitNumber.toStream().peek((key, value) -> System.out.println("visitNumber: key=" + key + " , value=" + value + " , date=" + LocalDateTime.now()));
        visitNumber.toStream().peek((key, value) -> gauge.set(value));

//        visitNumber.toStream().foreach((k, v) -> System.out.println("总次数:" + k + "次数:" + v));
//        persons.toStream().foreach((k, v) -> System.out.println("总人数:" + k + "次数:" + v));
        persons.toStream().peek((key, value) -> System.out.println("persons :  key=" + key + ", value=" + value + " , date=" + LocalDateTime.now()));
//        ipCount.toStream().foreach((k, v) -> System.out.println("ip:" + k + "次数:" + v));


//        meterRegistry.gauge("logs", Tags.of("ip",system), count);

//        meterRegistry.counter("logs", Tags.of("ip",ipNumber)).increment(2);

        return stream;
    }


}
