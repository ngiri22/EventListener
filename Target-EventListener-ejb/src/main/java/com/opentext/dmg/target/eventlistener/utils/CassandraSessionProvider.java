package com.opentext.dmg.target.eventlistener.utils;

import static com.opentext.dmg.target.eventlistener.utils.TargetEventListenerConstants.*;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.policies.DefaultRetryPolicy;

@Slf4j
public class CassandraSessionProvider {

    private static Session session = null;
    private static Cluster cluster = null;
    public static CassandraSessionProvider provider = null;

	private CassandraSessionProvider() {
	}
    
    @PostConstruct
    public void buildCluster()
    {
        try {
            cluster = Cluster.builder().withCredentials(CASSANDRA_USER_ID, CASSANDRA_USER_PSSWD)
                    .withPort(Integer.parseInt(CASSANDRA_PORT))
                    .addContactPoints(CASSANDRA_HOST.split(","))
                    .withSocketOptions(new SocketOptions().setKeepAlive(true))
                    .withRetryPolicy(DefaultRetryPolicy.INSTANCE)
                    .build()
                    .init();

            if ( cluster != null) session = cluster.connect(CASSANDRA_KEYSPACE);
            log.info("Cassandra data store ready for keyspace : " + CASSANDRA_KEYSPACE);
        } catch (NoHostAvailableException e) {
            log.error("Exception while creating cassandra session. {}", e.getMessage(),e);
        }
        catch (Exception e) {
            log.error("Could not build C* cluster. {}", e.getMessage(),e);
        }
    }
    
    public static CassandraSessionProvider getInstance()
    {
    	if(null==provider)
    	{
    		provider = new CassandraSessionProvider();	
    	}
    	return provider;
    }

    public Session getSession(){
        if(session==null) {
            buildCluster();
        }

        return session;
    }


    public void closeCluster (){
        if(cluster !=null) {
            cluster.close();
        }
    }

}