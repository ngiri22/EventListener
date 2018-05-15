package com.opentext.dmg.target.eventlistener;

import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.EVENTS_TO_PROCESS_FOR;
import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.HIPCHAT_ROOM_NAME;
import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.HIPCHAT_SERVER_URL;
import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.HIPCHAT_TOKEN;
import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.MY_FOLDER_ID;
import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.PUBLIC_FOLDER_ID;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import com.artesia.event.Event;
import com.artesia.event.EventListener;
import com.opentext.dmg.target.eventlistener.repository.DataRepository;
import com.opentext.dmg.target.eventlistener.repository.EventPublisher;
import com.opentext.dmg.target.eventlistener.service.HipChatService;
import com.opentext.dmg.target.eventlistener.utils.CassandraSessionProvider;
import com.opentext.dmg.target.eventlistener.utils.KafkaInstanceProvider;
import com.opentext.dmg.target.eventlistener.utils.TargetEventListenerUtils;

@Slf4j
public class TargetEventListener implements EventListener
{
	private String eventsToRegister = "";
	private EventPublisher eventPublisher;
	private DataRepository dataRepository;
	private HipChatService hipChatService;
	private TargetEventListnerHealthCheckTimer targetEventListnerHealthCheckTimer;
	
	public TargetEventListener()
	{
	}
	
	public TargetEventListener(String events)
	{
		this.eventsToRegister = events;
		targetEventListnerHealthCheckTimer = new TargetEventListnerHealthCheckTimer();
		eventPublisher = new EventPublisher(KafkaInstanceProvider.getInstance());
		dataRepository = new DataRepository(CassandraSessionProvider.getInstance());
		hipChatService = new HipChatService(HIPCHAT_SERVER_URL, HIPCHAT_TOKEN, HIPCHAT_ROOM_NAME);
	}

	public void onEvent(Event theEvent) {
		if (log.isDebugEnabled()) {
			displayEventObject(theEvent);
		}
		String assetId = theEvent.getObjectId();
		String eventId = theEvent.getEventId().getId();
		log.debug("Events to Register.{}", eventsToRegister);
		log.info("A Valid event to capture : {} for the Asset: {}", eventId, assetId);
		if (EVENTS_TO_PROCESS_FOR.contains(eventId)
				&& (!PUBLIC_FOLDER_ID.equals(assetId) && !MY_FOLDER_ID.equals(assetId))) {
			log.debug("A Valid event to capture : {}", eventId);
			log.info("This a valid event to capture : {}, the message will be now posted to KAFKA Topic for processing.", eventId);
			TargetEventListenerUtils tgtEventListenerUtils = new TargetEventListenerUtils();
			tgtEventListenerUtils.processEvent(theEvent, eventPublisher, dataRepository, hipChatService);
		}
	}

	private void displayEventObject(Event theEvent) {
		String eventID = theEvent.getEventId().getId();
		String eventDescription = theEvent.getDescription();
		String objectId = theEvent.getObjectId();
		String objectName = theEvent.getObjectName();
		String objectType = theEvent.getObjectType();
		String xmlContent = theEvent.getXmlContent();
		String eventCreationTime = theEvent.getTime().toString();
		Date eventTimeinDateFormat = theEvent.getTime();

		log.debug("Event Captured == Event Details are == ");
		log.debug("Event ID : " + eventID);
		log.debug("Event Creation Time : " + eventCreationTime);
		log.debug("Event Creation Time in Date format : " + eventTimeinDateFormat);
		log.debug("Event Description : " + eventDescription);
		log.debug("Object ID : " + objectId);
		log.debug("Object Name : " + objectName);
		log.debug("Object Type : " + objectType);
		log.debug("XML Content : " + xmlContent);
		log.debug("== Event Details Ended ==");
		log.debug("Event To Process for : " + EVENTS_TO_PROCESS_FOR);

	}
}
