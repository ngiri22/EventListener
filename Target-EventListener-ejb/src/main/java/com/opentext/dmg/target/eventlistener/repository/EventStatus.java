package com.opentext.dmg.target.eventlistener.repository;

import java.util.Date;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "extensions_events")
public class EventStatus {

    @PartitionKey(0)
    @Column(name = "asset_id")
    private String	asset_id;

    @ClusteringColumn(0)
    @Column(name = "event_id")
    private String	event_id;

    @ClusteringColumn(1)
    @Column(name = "event_time")
    private Date event_time;

    private	String	user_id;
    private	String	asset_name;
    private	String	source_id;
    private boolean cgi_asset;
    private boolean published_to_cgi_topic;
    private boolean published_to_extn_topic;
   
}