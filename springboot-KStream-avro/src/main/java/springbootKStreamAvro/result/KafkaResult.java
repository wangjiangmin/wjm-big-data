package springbootKStreamAvro.result;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.SendResult;

public class KafkaResult {
	
	private Integer status;

	private String msg;
	
	private String topic;
	
	private Integer partition;
	
	private long offset;
	
	private String dataKey;
	
	private Object dataValue;
	
	KafkaResult(Integer status, String msg, String topic, Integer partition, long offset, String dataKey, Object dataValue){
		this.status = status;
		this.msg = msg;
		this.topic = topic;
		this.partition = partition;
		this.offset = offset;
		this.dataKey = dataKey;
		this.dataValue = dataValue;
	}
	
	public static KafkaResult build(Integer status,String msg,String topic,Integer partition,long offset,String dataKey,Object dataValue) {
		return new KafkaResult(status,msg,topic,partition,offset,dataKey,dataValue);
	}
	
	public static KafkaResult ok() {
		return KafkaResult.build(200, "操作成功!", null, null, 0, null, null);
	}
	
	public static KafkaResult ok(String msg) {
		return KafkaResult.ok(msg,null);
	}
	
	public static KafkaResult ok(SendResult<String, Object> result) {
		return KafkaResult.ok("操作成功!",result);
	}
	
	public static KafkaResult ok(ConsumerRecord<?, ?> record) {
		return KafkaResult.build(200,"操作成功!",record.topic(),
				record.partition(),
				record.offset(),record.key().toString(),record.value().toString());
	}
	
	public static KafkaResult ok(String msg,SendResult<String, Object> result) {
		return KafkaResult.build(200,msg,result.getRecordMetadata().topic(),
										result.getRecordMetadata().partition(),
										result.getRecordMetadata().offset(),null,null);
	}
	
	public static KafkaResult err() {
		return KafkaResult.ok("操作失败!");
	}
	
	
	public String json() {
		return JSONObject.toJSONString(this);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Integer getPartition() {
		return partition;
	}

	public void setPartition(Integer partition) {
		this.partition = partition;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public Object getDataValue() {
		return dataValue;
	}

	public void setDataValue(Object dataValue) {
		this.dataValue = dataValue;
	}
	
	
	
	
	
	
	
	
	
}
