package com.opentext.dmg.target.eventlistener.service;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opentext.dmg.target.eventlistener.exceptions.EventListenerProcessorException;
import com.opentext.dmg.target.eventlistener.model.HipChatMessage;

@Slf4j
@Setter
public class HipChatService {

    
    private RestTemplate restTemplate;
    private String baseUrl;
    private String hipChatToken;
    private String hipChatRoom;

	public HipChatService(String baseUrl, String hipChatToken, String hipChatRoom) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		this.restTemplate = new RestTemplate(requestFactory);
		this.baseUrl = baseUrl;
		this.hipChatToken = hipChatToken;
		this.hipChatRoom = hipChatRoom;
	}

    public void setRestTemplate(RestTemplate template){
    	this.restTemplate = template;
    }
    
    public boolean sendHipChatMessage(Exception excp) {
    
            HipChatMessage hipChatMessage = createHipChatMessage(excp);
            log.debug("HipChatService.sendHipChatMessage : Sending hipchat message {}", hipChatMessage);
           
            return postHipChatMessage(hipChatMessage);
    }
    
    public boolean sendHipChatMessage(String message) {
        
        HipChatMessage hipChatMessage = new HipChatMessage.HipChatMessageBuilder(message).description(message)
                .build();
        log.debug("HipChatService.sendHipChatMessage : Sending hipchat message {}", hipChatMessage);
        
        return postHipChatMessage(hipChatMessage);
        
}
    
    private HipChatMessage createHipChatMessage(Exception e) {
        HipChatMessage hipChatMessage;
        if (e instanceof EventListenerProcessorException) {
        	EventListenerProcessorException exc = (EventListenerProcessorException) e;
            hipChatMessage = new HipChatMessage.HipChatMessageBuilder(e.getMessage()).description(exc
                    .getMessage())
                    .build();
        } else {
            hipChatMessage = new HipChatMessage.HipChatMessageBuilder(e.getMessage()).description(e
                    .getMessage())
                    .build();
        }
        return hipChatMessage;
    }
    
    private boolean postHipChatMessage(HipChatMessage hipChatMessage){
    	ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug("Request :{}", mapper.writeValueAsString(hipChatMessage));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + hipChatToken);
            HttpEntity<HipChatMessage> entity = new HttpEntity<>(hipChatMessage, headers);
            ResponseEntity<String> response =
                    restTemplate.exchange(baseUrl + "/v2/room/" + hipChatRoom + "/notification",
                            HttpMethod.POST, entity, String.class);
            log.debug("Response from Hipchat Server {}", response);
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return true;
            } else {
                return false;
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            log.error("Error while sending the HipChad Message", e);
            return false;
        }
    }

}
