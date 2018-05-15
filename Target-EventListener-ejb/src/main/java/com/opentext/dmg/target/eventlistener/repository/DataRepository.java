package com.opentext.dmg.target.eventlistener.repository;

import lombok.extern.slf4j.Slf4j;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opentext.dmg.target.eventlistener.exceptions.EventListenerProcessorException;
import com.opentext.dmg.target.eventlistener.utils.CassandraSessionProvider;
import com.opentext.dmg.target.eventlistener.utils.EventAttributes;

@Slf4j
public class DataRepository {

	private static Mapper<EventStatus> mapper = null;
	private CassandraSessionProvider provider = null;

	public DataRepository(CassandraSessionProvider provider) {

		this.provider = provider;
		updateMapper(this.provider);

	}

	private void updateMapper(CassandraSessionProvider provider) {
		mapper = new MappingManager(provider.getSession())
				.mapper(EventStatus.class);
	}

	public void updateEventDetails(EventAttributes eventAttributes)
			throws EventListenerProcessorException {

		log.info(
				"Update Event details: EventId: {} Asset: {} EventTime: {} UserID: {} AssetNAme: {} SrcAssetID: {} isCGIAsset: {}",
				eventAttributes.getEventId(), eventAttributes.getAssetId(),
				eventAttributes.getEventTime(), eventAttributes.getUserId(),
				eventAttributes.getAssetName(),
				eventAttributes.getSrcAssetId(), eventAttributes.getCgiAsset());
		EventStatus eventStatus = new EventStatus();
		eventStatus.setEvent_id(eventAttributes.getEventId());
		eventStatus.setAsset_id(eventAttributes.getAssetId());
		eventStatus.setEvent_time(eventAttributes.getEventTime());
		eventStatus.setUser_id(eventAttributes.getUserId());
		eventStatus.setAsset_name(eventAttributes.getAssetName());
		if (eventAttributes.getSrcAssetId() != null)
			eventStatus.setSource_id(eventAttributes.getSrcAssetId());
		if (eventAttributes.getCgiAsset() != null)
			eventStatus.setCgi_asset(eventAttributes.getCgiAsset());
		if (eventAttributes.getPublisedToCgiTopic() != null)
			eventStatus.setPublished_to_cgi_topic(eventAttributes
					.getPublisedToCgiTopic());
		if (eventAttributes.getPublishedToExtnTopic() != null)
			eventStatus.setPublished_to_extn_topic(eventAttributes
					.getPublishedToExtnTopic());
		try {
			mapper.saveAsync(eventStatus);
		} catch (Exception e) {
			log.warn(
					"Cassandra saveAsync failed for {} with {}, retrying C* builder",
					eventStatus, e.getMessage(), e);
			try {
				updateMapper(this.provider);
				mapper.saveAsync(eventStatus);
			} catch (Exception ex) {
				log.warn("Final Failure : Cassandra saveAsync failed for {} with {}", eventStatus, ex.getMessage(), ex);
				throw new EventListenerProcessorException(ex.getMessage());
			}

		}
	}

}
