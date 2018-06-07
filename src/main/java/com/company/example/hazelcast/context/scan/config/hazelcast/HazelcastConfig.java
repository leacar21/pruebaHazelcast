package com.company.example.hazelcast.context.scan.config.hazelcast;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.company.example.hazelcast.constants.Constants;
import com.company.example.hazelcast.context.scan.config.hazelcast.listeners.EventListener;
import com.company.example.hazelcast.context.scan.config.hazelcast.storage.EventsMapStore;
import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.MapStoreConfig.InitialLoadMode;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapStore;

@Configuration
public class HazelcastConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastConfig.class);

	private int port = 6701;
	private String heartbeatInterval = "3";
	private String heartbeatSeconds = "30";
	private String firstRunDelay = "15";
	private String nextRunDelay = "10";
	private String hazelcastInterface = "10.59.24.204";
	private String hazelcastJmx = "false";

	// ---------------------------
	private int backupCount = 1;
	private boolean statisticsEnabled = true;
	private int mapStoreDelay = 0;
	// ---------------------------

	@Autowired
	private EventsMapStore eventsMapStore;

	@Bean(destroyMethod = "shutdown")
	public HazelcastInstance getHazelcast() {
		List<String> joinTcpIpMembers;
		String publicAddress = null;
		//joinTcpIpMembers = Arrays.asList("10.59.24.204", "10.59.24.140");
		joinTcpIpMembers = Arrays.asList("10.59.24.204");

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

		LOGGER.info("Create Map Configuration...");
		this.mapConfiguration(config, backupCount, statisticsEnabled, mapStoreDelay, eventsMapStore);

		HazelcastInstance newHazelcastInstance = Hazelcast.newHazelcastInstance(config);
		return newHazelcastInstance;
	}

	// -----------------------------
	// -----------------------------

	public void mapConfiguration(Config config, int backupCount, boolean statisticsEnabled, int mapStoreDelay, MapStore implementationMapStore) {
		MapConfig mapConfiEvents = mapConfigurationAux(config, Constants.EVENTS_MAP, backupCount, statisticsEnabled, mapStoreDelay, implementationMapStore);
		this.addEvictionConfiguration(mapConfiEvents);
		this.addEventListener(mapConfiEvents);
	}

	// -----------------------------

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

	// -----------------------------

	private void addEventListener(MapConfig mapConfig) {
		EntryListenerConfig listenerConfig = new EntryListenerConfig();
		listenerConfig.setImplementation(new EventListener());
		mapConfig.addEntryListenerConfig(listenerConfig);
	}

	// -----------------------------

	private void addEvictionConfiguration(MapConfig mapConfig) {
		mapConfig.setEvictionPolicy(EvictionPolicy.LRU);
		MaxSizeConfig sizeConfig = new MaxSizeConfig(997, MaxSizeConfig.MaxSizePolicy.PER_NODE);
		mapConfig.setMaxSizeConfig(sizeConfig);
		mapConfig.setMaxIdleSeconds(3600);
	}

	// -----------------------------
}
