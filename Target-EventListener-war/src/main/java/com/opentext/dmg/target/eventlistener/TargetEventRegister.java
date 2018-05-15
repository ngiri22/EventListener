package com.opentext.dmg.target.eventlistener;

import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.common.exception.BaseTeamsException;
import com.artesia.event.services.EventServices;
import com.artesia.security.SecuritySession;
import com.opentext.dmg.target.eventlistener.service.HipChatService;
import com.opentext.dmg.target.eventlistener.utils.TargetEventListenerUtils;

public class TargetEventRegister implements ServletContextListener
{

	private static final Log LOGGER = LogFactory.getLog(TargetEventRegister.class);
	
	private static final String EVENT_CLIENT_ID = "TARGET_EVENT_SEQUENCER"; 
	
	public void contextDestroyed(ServletContextEvent event) 
	{
		LOGGER.debug("Context Destroyed ...");
		try 
		{
			SecuritySession session = TargetEventListenerUtils.getLocalSession();
			EventServices.getInstance().removeEventListener(EVENT_CLIENT_ID,session);
			TargetEventListenerUtils.logoutSecuritySession(session);
			
			HipChatService hipChatService = new HipChatService(HIPCHAT_SERVER_URL, HIPCHAT_TOKEN, HIPCHAT_ROOM_NAME);
			hipChatService.sendHipChatMessage("Event Listener Context is detroyed. Please take immediate action!!!");	
		} 
		catch (BaseTeamsException e) 
		{
			LOGGER.error("An exception occured while destroying the servlet context", e);
		}
	}

	public void contextInitialized(ServletContextEvent event) 
	{
		
		LOGGER.info("TargetEventRegister >> contextInitialized() start ...");
		try 
		{
			String eventToProcess = EVENTS_TO_PROCESS_FOR;
			SecuritySession session = TargetEventListenerUtils.getLocalSession();
			
			TargetEventListener targetEventListener = new TargetEventListener(eventToProcess);
			EventServices.getInstance().addEventListener(EVENT_CLIENT_ID, targetEventListener, session);
		} 
		catch (BaseTeamsException e) 
		{
			LOGGER.error("An exception occurred while initializing the servlet context", e);
		}
		
		LOGGER.info("TargetEventRegister >> contextInitialized() end ...");
	}
}
