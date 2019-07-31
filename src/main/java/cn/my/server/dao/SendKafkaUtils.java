package cn.my.server.dao;
import java.util.Properties;

import cn.my.server.common.ResultUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import cn.my.server.MyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendKafkaUtils {
//	private static final Log kafka = LogFactory.getLog("kafka");
	private static Logger logger = LoggerFactory.getLogger(SendKafkaUtils.class);

	private static final Properties props = new Properties();
	private static Producer<String, String> procuder;
	private static ProducerRecord<String, String> msg;
	static{
		 props.put("bootstrap.servers", MyConfig.kafka_brokers);//you should write specific ip address rather than localhost
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");//StringSerializer/IntegerSerializer/or other self-defined Serializer.
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("partitioner.class","cn.my.server.dao.HashPartitioner");
		 procuder = new KafkaProducer<String,String>(props);

	}

	public static void toKafka(){

	}

	public static String toKafka(String event_code,String json) {
		try {
//			ProducerRecord(String topic, Integer partition, Long timestamp, K key, V value, Iterable< Header > headers)
			long startTime = System.currentTimeMillis();
			msg = new ProducerRecord<String, String>(event_code, event_code, json);
			procuder.send(msg);
			long time = System.currentTimeMillis() - startTime;
			logger.info("kafka存储耗时：" + time);
            return ResultUtils.SUCCSESS;
		}catch (Exception e ){
			e.printStackTrace();
            return ResultUtils.ERRORS;
		}

//	     procuder.close(100,TimeUnit.MILLISECONDS);
	}

	public static void main(String[] args) {
	SendKafkaUtils.toKafka("test2","value");
	}
}
