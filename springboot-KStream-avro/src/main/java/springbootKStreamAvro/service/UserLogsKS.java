package springbootKStreamAvro.service;

import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.confluent.shaded.serializers.StringSerde;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import springbootKStreamAvro.avro.UserLogs;

@Service
@EnableKafkaStreams
public class UserLogsKS {

    @Value("${k-topic.name}")
    private String TOPIC;
    @Autowired
    private PrometheusMeterRegistry meterRegistry;

    @Bean
    public KStream<String, UserLogs> statisticsLogs(StreamsBuilder streamBuilder){

        KStream<String, UserLogs> stream = streamBuilder.stream(TOPIC);

//        GenericAvroSerde

//        stream.print(Printed.toSysOut());
        stream.foreach((k, v) -> System.out.println("系统:" + k + "次数:" + v));
        // 访问次数
        KTable<String, Long> visitNumber = stream.groupByKey().count();

        // 访问人次
        KTable<Integer, Long> persons = stream.map((k, v) -> {
            return new KeyValue<Integer, UserLogs>(Integer.valueOf(v.getUserID()), v);
        }).groupByKey().count();

        // ip访问次数
        KTable<String, Long> ipCount = stream.map((k, v) -> {
            return new KeyValue<String, UserLogs>(v.getIp(), v);
        }).groupByKey().count();

        visitNumber.toStream().foreach((k, v) -> System.out.println("总次数:" + k + "次数:" + v));
        persons.toStream().foreach((k, v) -> System.out.println("总人数:" + k + "次数:" + v));
        ipCount.toStream().foreach((k, v) -> System.out.println("ip:" + k + "次数:" + v));


//        meterRegistry.gauge("logs", Tags.of("ip",system), count);

//        meterRegistry.counter("logs", Tags.of("ip",ipNumber)).increment(2);

        return stream;
    }
}
