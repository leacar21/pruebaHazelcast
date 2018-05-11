package com.despegar.example.hazelcast.context.scan.config.hazelcast.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.constants.Constants;
import com.despegar.example.hazelcast.context.scan.config.hazelcast.listeners.EventListener;
import com.despegar.example.hazelcast.repository.model.SubTeam;
import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.MapStoreConfig.InitialLoadMode;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.config.QueueStoreConfig;
import com.hazelcast.core.MapStore;
import com.hazelcast.core.QueueStore;

@Component
public class HazelcastUtil {
	
	public void queueConfiguration(Config config, QueueStore queueStore) {
		QueueStoreConfig storeConfig = new QueueStoreConfig();
		storeConfig.setEnabled(true);
		storeConfig.setProperty("binary", "false");
		storeConfig.setProperty("memory-limit", "1000");
		storeConfig.setProperty("bulk-load", "500");
		storeConfig.setStoreImplementation(queueStore);
		
		//QueueConfig queueConfig = config.getQueueConfig(EVENTS_QUEUE);
		QueueConfig queueConfig = new QueueConfig();
		queueConfig.setName(Constants.EVENTS_QUEUE);
		queueConfig.setQueueStoreConfig(storeConfig);
//        queueConfig.setBackupCount(1)
//                   .setMaxSize(0)
//                   .setStatisticsEnabled(true)
//                   .setQuorumName("quorum-name");
//        queueConfig.setQueueStoreConfig(storeConfig); 
        config.addQueueConfig(queueConfig);
	}
	
	//-----------------------------
	
	public void mapConfigurationSubTeams(Config config, int backupCount, boolean statisticsEnabled, int mapStoreDelay, MapStore implementationMapStore) {
		MapConfig mapConfiEvents = mapConfigurationAux(config, Constants.SUBTEAMS_MAP, backupCount, statisticsEnabled, mapStoreDelay, implementationMapStore);
		this.addEvictionConfiguration(mapConfiEvents);
        //this.addEventListener(mapConfiEvents);
	}
	
	//-----------------------------
	
	public void mapConfiguration(Config config, int backupCount, boolean statisticsEnabled, int mapStoreDelay, MapStore implementationMapStore) {
		MapConfig mapConfiEvents = mapConfigurationAux(config, Constants.EVENTS_MAP, backupCount, statisticsEnabled, mapStoreDelay, implementationMapStore);
		this.addEvictionConfiguration(mapConfiEvents);
        this.addEventListener(mapConfiEvents);
	}
    
    private MapConfig mapConfigurationAux(Config config, String name, int backupCount, boolean statisticsEnabled, int mapStoreDelay, MapStore implementationMapStore) {
        MapConfig mapConfig = config.getMapConfig(name);
        mapConfig.setBackupCount(backupCount);
        mapConfig.setInMemoryFormat(InMemoryFormat.OBJECT);
        mapConfig.setStatisticsEnabled(statisticsEnabled);


        if (implementationMapStore != null) {
            MapStoreConfig mapStoreConfig = new MapStoreConfig();
            mapStoreConfig.setEnabled(true);
            mapStoreConfig.setImplementation(implementationMapStore);
            mapStoreConfig.setWriteDelaySeconds(mapStoreDelay);
            mapStoreConfig.setWriteBatchSize(100);
            mapStoreConfig.setInitialLoadMode(InitialLoadMode.LAZY);
            mapConfig.setMapStoreConfig(mapStoreConfig);
        }
        return mapConfig;
    }
    
    //-----------------------------
    
    private void addEventListener(MapConfig mapConfig) {
        EntryListenerConfig listenerConfig = new EntryListenerConfig();
        listenerConfig.setImplementation(new EventListener());
        mapConfig.addEntryListenerConfig(listenerConfig);
    }

    private void addEvictionConfiguration(MapConfig mapConfig) {
        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);
        MaxSizeConfig sizeConfig = new MaxSizeConfig(997, MaxSizeConfig.MaxSizePolicy.PER_NODE);
        mapConfig.setMaxSizeConfig(sizeConfig);
        mapConfig.setMaxIdleSeconds(3600);
    }
    
    //-----------------------------
}
