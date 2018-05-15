package com.opentext.dmg.target.eventlistener.utils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class EventAttributes {
	
	private String eventId;
	private String assetId;
	private String assetName;
	private String assetVersion;
	private String userId;
	private Date eventTime;
	
	
	private String srcAssetId;
	
	private String shouldXmpmmRun;
	private String shouldAmcRun;
	private String shouldPpiRun;
	private String shouldEsRun;
	private Boolean cgiAsset;
	private Boolean publisedToCgiTopic;
	private Boolean publishedToExtnTopic;
	
	
	public EventAttributes(String eventId, String assetId, String assetName, String assetVersion,
			String userId, Date time) {
		this.eventId = eventId;
		this.assetId = assetId;
		this.assetName = assetName;
		this.assetVersion = assetVersion;
		this.userId = userId;
		this.eventTime = time;
	}
	
	public EventAttributes(String eventId, String assetId, String userId, String assetName,
			String srcAssetId, Date time, Boolean cgiAsset, String shouldXmpmmRun, String shouldAmcRun,
			String shouldPpiRun, String shouldEsRun) {
		this.eventId = eventId;
		this.assetId = assetId;
		this.userId = userId;
		this.assetName = assetName;
		this.srcAssetId = srcAssetId;
		this.eventTime = time;
		this.cgiAsset = cgiAsset;
		this.shouldXmpmmRun = shouldXmpmmRun;
		this.shouldAmcRun = shouldAmcRun;
		this.shouldPpiRun = shouldPpiRun;
		this.shouldEsRun = shouldEsRun;
	}

}
