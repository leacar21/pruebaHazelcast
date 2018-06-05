package com.company.example.hazelcast.context.scan.config.hazelcast.listeners;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.example.hazelcast.constants.Constants;
import com.company.example.hazelcast.repository.dao.event.SubTeamEventDAO;
import com.company.example.hazelcast.repository.model.Event;
import com.company.example.hazelcast.repository.model.TaskEventsToProcess;
import com.company.example.hazelcast.repository.model.old.SubTeamTaskTuple;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapEvent;

@Component
public class EventListener extends AbstractEvictedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

    private Executor executor = Executors.newFixedThreadPool(5);
    
//    @Autowired
//	private HazelcastInstance hazelcastInstance;
    
    @Autowired
    private SubTeamEventDAO subTeamEventDAO;
    
    public EventListener() {
    }

	@Override
	Boolean isEnabled() {
		return true;
	}
    
    @Override
    public void entryEvicted(EntryEvent event) {
    		System.out.println( "-----> Entry Evicted:" + event );
    }

    @Override
    public void entryExpired(EntryEvent event) {
    		System.out.println( "-----> Entry Expired:" + event );
    }

	@Override
	public void entryAdded(EntryEvent event) {
		TaskEventsToProcess taskEventsToProcess = (TaskEventsToProcess)(event.getValue());
		System.out.println( "-----> Entry Added:" + event);
//		Map<Long, Event> mapEvent = (Map<Long, Event>)(event.getValue());
//		System.out.println( "-----> Entry Added:" + mapEvent.keySet() + " | " + event);
//		SubTeamTaskTuple subTeamTaskTuple = (SubTeamTaskTuple)(event.getKey());
//		
//		//Long subTeamId = subTeamTaskTuple.getSubTeamId();
//		//String subTeamMapName = Constants.EVENTS_MAP + Constants.SUB_TEAM_PREFIX + subTeamId;
//
//		Set<Long> keys = mapEvent.keySet();
//		Long minEventId = Collections.min(keys);
//		Event e = mapEvent.get(minEventId);
//		// TODO: Procesar evento para SubTeam
//		mapEvent.remove(minEventId);
//		Event e2 = mapEvent.get(minEventId);
	}
 
	@Override
	public void entryRemoved(EntryEvent event) {
		System.out.println( "-----> Entry Removed:" + event );
	}

	@Override
	public void entryUpdated(EntryEvent event) {
		TaskEventsToProcess taskEventsToProcess = (TaskEventsToProcess)(event.getValue());
		System.out.println( "-----> Entry Updated:" + event);
	}

	@Override
	public void mapEvicted(MapEvent event) {
		System.out.println( "-----> Map Evicted:" + event );
	}

	@Override
	public void mapCleared(MapEvent event) {
		System.out.println( "-----> Map Cleared:" + event );
	}
}
