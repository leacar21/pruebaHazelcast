package com.company.example.hazelcast.context.scan.config.hazelcast;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.example.hazelcast.constants.Constants;
import com.company.example.hazelcast.context.scan.config.hazelcast.storage.EventsMapStore;
import com.company.example.hazelcast.context.scan.config.hazelcast.storage.EventsQueueStore;
import com.company.example.hazelcast.context.scan.config.hazelcast.storage.SubTeamMapStore;
import com.company.example.hazelcast.context.scan.config.hazelcast.util.HazelcastUtil;
import com.company.example.hazelcast.repository.model.SubTeam;
import com.company.example.hazelcast.repository.model.TaskEventsToProcess;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastConfig.class);
	
    private int port = 6701;
    private String heartbeatInterval = "3";
    private String heartbeatSeconds = "30";
    private String firstRunDelay = "15";
    private String nextRunDelay = "10";
    private String hazelcastInterface = "10.59.32.20";
    private String hazelcastJmx = "false";
    
    //---------------------------
    private int backupCount = 1;
    private boolean statisticsEnabled = true;
    private int mapStoreDelay = 0;
    //---------------------------

    @Autowired
    private HazelcastUtil hazelcastUtil;
    
    @Autowired
    private EventsQueueStore eventsQueueStore;
    
    @Autowired
    private EventsMapStore eventsMapStore;
    
    @Autowired
    private SubTeamMapStore subTeamMapStore;
    
    
    @Bean(destroyMethod = "shutdown")
    public HazelcastInstance getHazelcast() {

        List<String> joinTcpIpMembers;
        String publicAddress = null;
        //joinTcpIpMembers = Arrays.asList("10.59.25.122", "10.59.25.75");
        joinTcpIpMembers = Arrays.asList("10.59.32.20");

        // Configure Hazelcast Properties
        Config config = new Config();
        config.setProperty("hazelcast.heartbeat.interval.seconds", this.heartbeatInterval);
        config.setProperty("hazelcast.max.no.heartbeat.seconds", this.heartbeatSeconds);
        config.setProperty("hazelcast.merge.first.run.delay.seconds", this.firstRunDelay);
        config.setProperty("hazelcast.merge.next.run.delay.seconds", this.nextRunDelay);
        config.setProperty("hazelcast.logging.type", "slf4j");
        config.setProperty("hazelcast.jmx", this.hazelcastJmx);
        config.setProperty("hazelcast.jmx.detailed", this.hazelcastJmx);
        
        // Configure Hazelcast Network

        NetworkConfig network = config.getNetworkConfig();
        network.setPort(this.port).setPortAutoIncrement(true);
        if (publicAddress != null) {
            network.setPublicAddress(publicAddress);
        }

        network.getInterfaces().addInterface(this.hazelcastInterface).setEnabled(true);

        JoinConfig join = network.getJoin();

        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig().setRequiredMember(null).setMembers(joinTcpIpMembers).setEnabled(true);

        //=================
        LOGGER.info("Creando configuración colas y mapas...");
        hazelcastUtil.mapConfigurationSubTeams(config, backupCount, statisticsEnabled, mapStoreDelay, subTeamMapStore);
        hazelcastUtil.queueConfiguration(config, eventsQueueStore);
        hazelcastUtil.mapConfiguration(config, backupCount, statisticsEnabled, mapStoreDelay, eventsMapStore);
        //for (Integer i = 0; i < Constants.COUNT_SUB_TEAMS; i++) {
        		//Long idSubTeam = i.longValue();
        		//EventsMapStore eventsMapStore = new EventsMapStore(idSubTeam, taskDAO, eventDAO, subTeamEventDAO);
			//hazelcastUtil.mapConfiguration(config, Constants.SUB_TEAM_PREFIX + i, backupCount, statisticsEnabled, mapStoreDelay, eventsMapStore);
		//}
        //=================
        
        
        HazelcastInstance newHazelcastInstance = Hazelcast.newHazelcastInstance(config);
        
        
     // Esto es para que carge todos los mapas al arrancar la app (y no esperar a que pase un job para que se cargue)
//	    for (int i=0; i < Constants.COUNT_SUB_TEAMS; i++) {
	    		Map<Long, TaskEventsToProcess> eventMap = newHazelcastInstance.getMap(Constants.EVENTS_MAP);
	    		if (eventMap.isEmpty()) { // esto
	    			System.out.println(">>>>>> Mapa Vacio");
	    		} else {
	    			System.out.println(">>>>>> Mapa Lleno");
	    		}
//		}
        
	    	// Cargamos algunos subteams
    		for (long i = 0; i < Constants.COUNT_SUB_TEAMS; i++) {
    			Map<Long, SubTeam> subteamMap = newHazelcastInstance.getMap(Constants.SUBTEAMS_MAP);
    			SubTeam subTeam = new SubTeam();
    			subTeam.setId(i);
    			subTeam.setCode("ST" + i);
    			subteamMap.put(i, subTeam);
		}	
	    		
		return newHazelcastInstance;
    }
    
    //-----------------------------
}
