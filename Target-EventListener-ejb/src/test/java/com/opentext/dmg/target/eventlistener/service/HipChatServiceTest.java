package com.opentext.dmg.target.eventlistener.service;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.opentext.dmg.target.eventlistener.exceptions.EventListenerProcessorException;

public class HipChatServiceTest {

    private HipChatService hipChatService;
    private RestTemplate   restTemplate;

    @Before 
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        hipChatService = new HipChatService("testURL", "testToken", "testRoom");
    }
    
    
    @Test
    public void testSendMessage() throws IOException {
        EventListenerProcessorException testExcp = new EventListenerProcessorException("Test from Event Listener API");
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        hipChatService.setRestTemplate(restTemplate);
        when(restTemplate.exchange(Matchers.anyString(),
            eq(HttpMethod.POST), Matchers.any(HttpEntity.class), eq(String.class))).thenReturn(response);
        
        Assert.assertTrue(hipChatService.sendHipChatMessage(testExcp));
    }
}