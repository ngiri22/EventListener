package com.opentext.dmg.target.eventlistener.utils;

import java.util.Properties;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.*;

@Slf4j
public class KafkaInstanceProvider {
	
	public static KafkaInstanceProvider kafkaInstanceProvider = null;
	public static KafkaProducer<String, String> producer = null;
	
	private KafkaInstanceProvider(){
	}
	
	@PostConstruct
	public void buildProducer()
	{
		Properties props = new Properties();
		props.put(BOOTSTARP_SERVERS_KEY, BOOTSTARP_SERVERS_VALUE);
		props.put(KAFKA_KEY_SERIALIZER_KEY, KAFKA_KEY_SERIALIZER_VALUE);
		props.put(KAFKA_VALUE_SERIALIZER_KEY, KAFKA_VALUE_SERIALIZER_VALUE);
		props.put(KAFKA_SECURITY_PROTOCOL_KEY, KAFKA_SECURITY_PROTOCOL_VALUE);
		props.put(KAFKA_KEYSTORE_LOCATION_KEY, KAFKA_KEYSTORE_LOCATION_VALUE);
		props.put(KAFKA_KEYSTORE_PASSWORD_KEY, KAFKA_KEYSTORE_PASSWORD_VLAUE);
		props.put(KAFKA_TRUSTSTORE_LOCATION_KEY, KAFKA_TRUSTSTORE_LOCATION_VALUE);
		props.put(KAFKA_TRUSTSTORE_PASSWORD_KEY, KAFKA_TRUSTSTORE_PASSWORD_VALUE);
		props.put(KAFKA_CONNECTION_TIME_OUT_KEY, KAFKA_CONNECTION_TIME_OUT_VALUE);
		
		producer = new KafkaProducer<>(props);
	}
	public static KafkaInstanceProvider getInstance(){
		if(kafkaInstanceProvider == null){
			kafkaInstanceProvider = new KafkaInstanceProvider();
		}
		return kafkaInstanceProvider;
	}
	
	public KafkaProducer<String, String> getProducer()
	{
		if(null==producer){
			buildProducer();
		}
		return producer;
	}
	
	public void closeKafka(){
		if (producer != null) {
			producer.close();
			log.debug("Closing the producer.");
		}
	}
}
