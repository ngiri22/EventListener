package com.opentext.dmg.target.eventlistener.utils;

import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.*;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.artesia.asset.Asset;
import com.artesia.asset.AssetIdentifier;
import com.artesia.asset.services.AssetDataLoadRequest;
import com.artesia.asset.services.AssetServices;
import com.artesia.common.NameValue;
import com.artesia.common.exception.BaseTeamsException;
import com.artesia.container.Container;
import com.artesia.container.ContainerPath;
import com.artesia.container.LightContainer;
import com.artesia.container.services.ContainerServices;
import com.artesia.event.Event;
import com.artesia.security.SecuritySession;
import com.artesia.security.session.services.AuthenticationServices;
import com.artesia.security.session.services.LocalAuthenticationServices;
import com.artesia.user.TeamsUser;
import com.artesia.user.UserIdentifier;
import com.artesia.user.services.UserServices;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opentext.dmg.target.eventlistener.exceptions.EventListenerProcessorException;
import com.opentext.dmg.target.eventlistener.model.HipChatMessage;
import com.opentext.dmg.target.eventlistener.repository.DataRepository;
import com.opentext.dmg.target.eventlistener.repository.EventPublisher;
import com.opentext.dmg.target.eventlistener.service.HipChatService;

@Slf4j
public class TargetEventListenerUtils 
{

	private EventAttributes eventAttributesForExtensions;
	private EventAttributes eventAttributesForCGI;

	public TargetEventListenerUtils() {
	}

	/**
	 * Create and return a local session.
	 * 
	 * @return SecuritySession
	 */
	public static SecuritySession getLocalSession()
	{
		SecuritySession localSession = null;
		try 
		{
			localSession = LocalAuthenticationServices.getInstance().createLocalSession(TargetEventListenerConstants.TEAMS_ADMIN_USER);
		} 
		catch (BaseTeamsException e) 
		{
			log.error("An exception occured while trying to get the login session", e);
			return null;
		}
		return localSession;
	}

	public static String getUserEmail(String teamsUserId, SecuritySession securitySession) throws BaseTeamsException
	{
		UserServices userServices = UserServices.getInstance();
		TeamsUser retrievedTeamsUser = userServices.retrieveTeamsUser(new UserIdentifier(teamsUserId), securitySession);

		if (retrievedTeamsUser != null)
		{
			return retrievedTeamsUser.getEmailAddress();
		}
		return null;
	}

	/**
	 * Create and return a local session.
	 * 
	 * @return SecuritySession
	 */
	public static SecuritySession getLocalSession(String aUser)
	{
		SecuritySession localSession = null;
		try 
		{
			localSession = LocalAuthenticationServices.getInstance().createLocalSession(aUser);
		} 
		catch (BaseTeamsException e) 
		{
			log.error("An exception occured while trying to get the login session", e);
			return null;
		}
		return localSession;
	}
	
	/**
	 * Closes a security session.
	 * @throws BaseTeamsException 
	 * 
	 */
	public static void logoutSecuritySession(SecuritySession session) throws BaseTeamsException
	   {
		if (session != null)
	      {
	         try
	         {
	        	log.info("TargetEventListenerUtils : LogoutSecuritySession : Closing security session with Session_Id : " + session.getId());
	            AuthenticationServices.getInstance().logout(session);
	         }
	         catch (BaseTeamsException e)
	         {
	            log.error("Logout failed. Message is [" + e.getDebugMessage() + "].", e);
	            if(session != null){
	            	AuthenticationServices.getInstance().logout(session);
	            }
	         }
	      }
	   }
	
	public void processEvent(Event theEvent, EventPublisher eventPublisher, DataRepository dataRepository, HipChatService hipChatService) {
		log.info("********************KAFKA publish for event...{} and for the Asset: {}", theEvent.getEventId().getId(), theEvent.getObjectId());
		Boolean eventPublishedToExtnTopic = false;
		Boolean eventPublishedToCgiTopic = false;
		String eventId = theEvent.getEventId().getId();
		SecuritySession securitySession = getLocalSession();
		String assetId= theEvent.getObjectId();
		Asset asset = getAsset(assetId, securitySession);
		String assetName = asset.getName();
		String userId = asset.getImportUserName();
		String assetVersion = Integer.toString(asset.getVersion());
		Date eventTime = theEvent.getTime();
		Container container = getProjectContainerInfo(assetId, securitySession);
		boolean cgiAsset = false;
		if(container != null && CGI_FOLDER_TYPE.contains(container.getContainerTypeId().getId())){
			cgiAsset = true;
		}
		
		String srcAssetId = null;
		if (EVENTID_30026.equalsIgnoreCase(theEvent.getEventId().getId()))
		{
			NameValue[] data = theEvent.getData();
			NameValue aNameValue = data[0];
			String value = aNameValue.getValue();
			if (value != null && value.trim().length() != 0)
			{
				srcAssetId = value;
				log.info("It's a duplicate event. Source Asset Id is: {}", srcAssetId);
			}
		}	
		try {
			logoutSecuritySession(securitySession);
		} catch (BaseTeamsException e) {
			log.error("Logout failed. Message is [" + e.getDebugMessage() + "].", e);
		}
		
		//Now publishing to Kafka and updating Cassandra
		if (cgiAsset) {
			log.debug("Publishing to CGI topic. Asset Id is: {}", assetId);
			eventAttributesForCGI = new EventAttributes(eventId, assetId,
					assetName, assetVersion, userId, eventTime);
			eventPublishedToCgiTopic = eventPublisher.sendMessage(eventAttributesForCGI, KAFKA_TOPIC_NAME_FOR_CGI, hipChatService);
		}

		log.info("Publishing to Extensions topic. Asset Id is: {}", assetId);
		eventAttributesForExtensions = new EventAttributes(eventId, assetId,
				userId, assetName, srcAssetId, eventTime, cgiAsset,
				SHOULD_XMPMM_EXTENSION_RUN, SHOULD_AMC_EXTENSION_RUN, SHOULD_PPI_EXTENSION_RUN,
				SHOULD_ES_EXTENSION_RUN);
		eventPublishedToExtnTopic = eventPublisher.sendMessage(eventAttributesForExtensions, KAFKA_TOPIC_NAME_FOR_EXTENSIONS, hipChatService);
		eventAttributesForExtensions.setPublisedToCgiTopic(eventPublishedToCgiTopic);
		eventAttributesForExtensions.setPublishedToExtnTopic(eventPublishedToExtnTopic);

		updateEventStatusInDataBase(eventAttributesForExtensions, dataRepository, hipChatService);
		

	}
	
	public static Asset getAsset(String assetId, SecuritySession securitySession)
	{
		log.debug("Fetch Asset Object for assetId : {}", assetId);
		Asset returnValue = null;
		try 
		{
			if (securitySession != null)
			{
				AssetDataLoadRequest loadRequest = new AssetDataLoadRequest();
				loadRequest.setLoadPath(true);
				loadRequest.setLoadMetadata(true);
				returnValue = AssetServices.getInstance().retrieveAsset(new AssetIdentifier(assetId), loadRequest, securitySession);
			}
		} 
		catch (BaseTeamsException e) 
		{
			log.error("An exception occured while handling the event.{}", e.getMessage());
		}
		catch(Exception e)
		{
			log.error("An exception occured while handling the event.{}", e.getMessage());
		}
		return returnValue;
	}
	
	public void updateEventStatusInDataBase(EventAttributes eventAttributes, DataRepository dataRepository, HipChatService hipChatService) {
		if(StringUtils.isNotBlank(eventAttributes.getEventId()) && StringUtils.isNotBlank(eventAttributes.getAssetId())
				&& eventAttributes.getEventTime() != null){

			try {
				dataRepository.updateEventDetails(eventAttributes);
				log.debug("Upsert successful for asset: " + eventAttributes.getAssetId());
			} catch (EventListenerProcessorException e) {
				log.error(
						"Upsert failed for asset due to NULL value for either EventId : {} OR AssetId : {}  OR EventTriggerTime : {}",
						eventAttributes.getEventId(),
						eventAttributes.getAssetId(),
						eventAttributes.getEventTime());
				ObjectMapper mapper = new ObjectMapper();
				JsonNode objNode = mapper.valueToTree(eventAttributes);
				hipChatService.sendHipChatMessage("Upsert failed for Event: " + objNode.toString());
			}
		}
	}
	
	public Container getProjectContainerInfo(String assetId, SecuritySession securitySession)
	{
		log.debug("Fetching the Container Info for the Asset : " + assetId);
		Container projectFolder = null;
		try
		{
			AssetDataLoadRequest assetDataLoadRequest = new AssetDataLoadRequest();
			assetDataLoadRequest.setLoadSecurityPolicies(true);
			assetDataLoadRequest.setLoadMetadataByModel(true);
			assetDataLoadRequest.setLoadMetadata(true);
			assetDataLoadRequest.setLoadPath(true);

			Asset asset = AssetServices.getInstance().retrieveAsset(new AssetIdentifier(assetId),
					assetDataLoadRequest, securitySession);

			if (asset instanceof Asset)
			{
				log.debug("assetid:"+ assetId +" is an Asset");
				List<ContainerPath> containerPaths = asset.getPathList();
				if(containerPaths != null)
				{
					projectFolder = getProjectFolderInHierarchy(containerPaths, assetDataLoadRequest, securitySession);
				}
			}
			else
			{
				log.debug("assetid:"+ assetId +" is not an Asset");
			}
		}
		catch (BaseTeamsException e)
		{
			log.error("Error while retrieving container details: {}", e);

		}
		catch (Exception e)
		{
			log.error("Error while retrieving container details: {}", e);

		}
		if (projectFolder != null)
		{
			log.debug("Asset with id : {} is imported into the folder: {} ", assetId, projectFolder.getName());
		}
		else
		{
			log.error("Asset {} was not imported into a folder.", assetId);
		}
		return projectFolder;
	}

	public Container getProjectFolderInHierarchy(List<ContainerPath> containerPaths, AssetDataLoadRequest assetDataLoadRequest, SecuritySession securitySession) throws BaseTeamsException
	{
		Container parentContainer = null;
		Container projectFolder = null;
		log.debug("Looking for project folder among the container paths");
		if (containerPaths != null)
		{
			for (ContainerPath containerPath : containerPaths)
			{
				List<LightContainer> containers = containerPath.getParents();
				if (containers.size() > 0)
				{
					LightContainer lightContainer = containers.get(0);
					if(lightContainer != null)
					{
						log.debug("Container Name={}", lightContainer.getName());

						AssetIdentifier containerId = lightContainer.getId();
						parentContainer = ContainerServices.getInstance().getContainer(containerId, assetDataLoadRequest,
								securitySession);

						String parentContainerTypeId = parentContainer.getContainerTypeId().getId();
						log.debug("Container Folder Type Id is :{}", parentContainerTypeId);

						log.debug("Project Folder Type from container paths: {}", parentContainer.getAssetId().getId());
						projectFolder = parentContainer;
						break;
					}
				}
			}
		}
		return projectFolder;
	}
}
