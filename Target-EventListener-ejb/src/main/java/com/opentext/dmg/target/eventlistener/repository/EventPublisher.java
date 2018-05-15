package com.opentext.dmg.target.eventlistener.repository;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opentext.dmg.target.eventlistener.service.HipChatService;
import com.opentext.dmg.target.eventlistener.utils.EventAttributes;
import com.opentext.dmg.target.eventlistener.utils.KafkaInstanceProvider;

@Slf4j
public class EventPublisher {
	
	private KafkaInstanceProvider kafkaProvider = null;
	
	public EventPublisher(KafkaInstanceProvider kafkaProvider){
		this.kafkaProvider = kafkaProvider;
	}

	public Boolean sendMessage(EventAttributes eventAttributes, String topicName, HipChatService hipChatService){
		Boolean isMessagePublished = true;
		JsonNode objNode = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			objNode = mapper.valueToTree(eventAttributes);
			log.info("JSON to be published...{}", objNode.toString());
			KafkaProducer<String, String> producer = kafkaProvider.getProducer();
			producer.send(new ProducerRecord<String, String>(topicName, "cgiAssetEvent", objNode.toString()));
			
		} catch (Exception e) {
			log.error("Failed to publish message to Kafka for asset: {} in the topic {} for Event : {} at time : {}",
					eventAttributes.getAssetId(), topicName, eventAttributes.getEventId(), eventAttributes.getEventTime());
			isMessagePublished = false;
			hipChatService.sendHipChatMessage("Failed to publish message to Kafka for Event: " + objNode.toString());
		}
		
		return isMessagePublished;
	}
}
