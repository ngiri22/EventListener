package com.opentext.dmg.target.eventlistener.utils;

import com.artesia.common.prefs.Preferences;

public final class TargetEventListenerConstants 
{

	private TargetEventListenerConstants()
	{
	   
	}
	
	public static final String PUBLIC_FOLDER_ID = "ARTESIA.PUBLIC.TREEN";
	
	public static final String MY_FOLDER_ID = "1001N";
	
	public static final String EVENT_XML_KEY = "eventXML";
	
	public static final String SRC_ASSET_ID = "srcAssetId";

	public static final String ASSET_ID_KEY = "assetId";
	
	public static final String USER_ID_KEY = "userId";

	public static final String EVENT_ID_KEY = "eventId";
	
	public static final String EVENT_CREATION_TIME = "eventCreationTime";
	
	public static final String PROCESS_COUNT_KEY = "processCount";

	public static final String EVENTID_30022 = "30022";

	public static final String EVENTID_30026 = "30026";

	public static final String EVENTID_80014 = "80014";

	public static final String EVENTID_40014 = "40014";

	public static final String EVENTID_10001 = "10001";
	
	public static final String EVENTID_30023 = "30023";

	public static final String COMPONENT_NAME = "EVENTLISTENER";

	public static final String CONFIG_COMPONENT_KEY = "CONFIG";
	
	public static final String FOLDER_NAME= "FOLDER";

	public static final String FOLDER_NAME_PLACE_HOLDER = "%folder-name%";
	
	public static final String DLQ_PLACE_HOLDER = "%dlq-name%";
	
	public static final String FAILURE_STEP_PLACE_HOLDER = "%failure-step%";

	public static final String EMAIL_SUBJECT_MIMETYPE = "text/html";

	public static final String OPENTEXT_UNIQUE_ID_FIELD_ID = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "OPENTEXT_UNIQUE_ID_FIELD_ID" , "");
	
	public static final String INITIAL_CONTEXT = Preferences.getPreference("JNDI", CONFIG_COMPONENT_KEY, "INITIAL_CONTEXT_FACTORY");

	public static final String URL_PACKAGES = Preferences.getPreference("JNDI", CONFIG_COMPONENT_KEY, "FACTORY_URL_PACKAGES");

	public static final String JNDI_PROVIDER_HOST = "jnp://" + Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "JNDI_PROVIDER_HOST");

	public static final String PROVIDER_HOST = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "JNDI_PROVIDER_HOST");

	public static final String QUEUE_NAME = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "QUEUE_NAME");

	public static final String EVENTS_TO_PROCESS_FOR = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "EVENTS_TO_PROCESS_FOR");

	public static final String QUEUE_CONNECTION_FACTORY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "QUEUE_CONNECTION_FACTORY");

	public static final String TEAMS_ADMIN_USER = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "TEAMS_ADMIN_USER");

	public static final String WEB_MAIL_HOST = Preferences.getPreference("COMMON", "SERVER", "EMAIL_SERVER");
	
	public static final String FROM_EMAIL_ADDRESS = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "FROM_EMAIL_ADDRESS");
	
	public static final String SHOULD_XMPMM_EXTENSION_RUN = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "SHOULD_XMPMM_EXTENSION_RUN");
	
	public static final String SHOULD_AMC_EXTENSION_RUN = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "SHOULD_AMC_EXTENSION_RUN");
	
	public static final String SHOULD_PPI_EXTENSION_RUN = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "SHOULD_PPI_EXTENSION_RUN");
	
	public static final String SHOULD_ES_EXTENSION_RUN = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "SHOULD_ES_EXTENSION_RUN");

	public static final String CGI_FOLDER_TYPE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CGI_FOLDER_TYPE");
	
	public static final String KAFKA_TOPIC_NAME_FOR_EXTENSIONS = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_TOPIC_NAME_FOR_EXTENSIONS");
	
	public static final String KAFKA_TOPIC_NAME_FOR_CGI = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_TOPIC_NAME_FOR_CGI");
	
	public static final String BOOTSTARP_SERVERS_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "BOOTSTARP_SERVERS_KEY");
	
	public static final String BOOTSTARP_SERVERS_VALUE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "BOOTSTARP_SERVERS_VALUE");
	
	public static final String KAFKA_RETRIES_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_RETRIES_KEY");
	
	public static final int KAFKA_RETRIES_COUNT_VALUE = 2;
	
	public static final String KAFKA_LINGER_MS_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_LINGER_MS_KEY");
	
	public static final int KAFKA_LINGER_MS_VALUE = 5;
	
	public static final String KAFKA_CONNECTION_TIME_OUT_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_CONNECTION_TIME_OUT_KEY");
	
	public static final int KAFKA_CONNECTION_TIME_OUT_VALUE = 2560000;
	
	public static final String KAFKA_KEY_SERIALIZER_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_KEY_SERIALIZER_KEY");
	
	public static final String KAFKA_KEY_SERIALIZER_VALUE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_KEY_SERIALIZER_VALUE");
	
	public static final String KAFKA_VALUE_SERIALIZER_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_VALUE_SERIALIZER_KEY");
	
	public static final String KAFKA_VALUE_SERIALIZER_VALUE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_VALUE_SERIALIZER_VALUE");
	
	public static final String KAFKA_SECURITY_PROTOCOL_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_SECURITY_PROTOCOL_KEY");
	
	public static final String KAFKA_SECURITY_PROTOCOL_VALUE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_SECURITY_PROTOCOL_VALUE");
	
	public static final String KAFKA_KEYSTORE_LOCATION_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_KEYSTORE_LOCATION_KEY");
	
	public static final String KAFKA_KEYSTORE_LOCATION_VALUE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_KEYSTORE_LOCATION_VALUE");
	
	public static final String KAFKA_KEYSTORE_PASSWORD_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_KEYSTORE_PASSWORD_KEY");
	
	public static final String KAFKA_KEYSTORE_PASSWORD_VLAUE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_KEYSTORE_PASSWORD_VLAUE");
	
	public static final String KAFKA_TRUSTSTORE_LOCATION_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_TRUSTSTORE_LOCATION_KEY");
	
	public static final String KAFKA_TRUSTSTORE_LOCATION_VALUE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_TRUSTSTORE_LOCATION_VALUE");
	
	public static final String KAFKA_TRUSTSTORE_PASSWORD_KEY = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_TRUSTSTORE_PASSWORD_KEY");
	
	public static final String KAFKA_TRUSTSTORE_PASSWORD_VALUE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "KAFKA_TRUSTSTORE_PASSWORD_VALUE");
	
	//Cassandra
	public static final String CASSANDRA_UPDATE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_UPDATE");
	
	public static final String CASSANDRA_HOST = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_HOST");
	public static final String CASSANDRA_KEYSPACE = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_KEYSPACE");
	public static final String CASSANDRA_LOCAL_DC = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_LOCAL_DC");
	public static final String CASSANDRA_USER_ID = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_USER_ID");
	public static final String CASSANDRA_USER_PSSWD = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_USER_PSSWD");
	public static final String CASSANDRA_PORT = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_PORT");
	
	public static final String HIPCHAT_TOKEN = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "HIPCHAT_TOKEN");
	public static final String HIPCHAT_SERVER_URL = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "HIPCHAT_SERVER_URL");
	public static final String HIPCHAT_ROOM_NAME = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "HIPCHAT_ROOM_NAME");
	//public static final String CASSANDRA_HOST1 = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_HOST1");
	//public static final String CASSANDRA_HOST2 = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_HOST2");
	//public static final String CASSANDRA_HOST3 = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "CASSANDRA_HOST3");
	
	//For Testing CGI TOPIC : TO BE REMOVED
	public static final String IS_CGI = Preferences.getPreference(COMPONENT_NAME, CONFIG_COMPONENT_KEY, "IS_CGI");
	
}
