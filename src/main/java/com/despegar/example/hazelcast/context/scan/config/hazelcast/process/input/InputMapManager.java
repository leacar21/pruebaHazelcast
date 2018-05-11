package com.despegar.example.hazelcast.context.scan.config.hazelcast.process.input;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.context.scan.config.hazelcast.map.AbstractManagerMap;
import com.despegar.example.hazelcast.context.scan.config.hazelcast.map.IMapManager;
import com.despegar.example.hazelcast.repository.model.Event;
import com.despegar.example.hazelcast.repository.model.old.SubTeamTaskTuple;
import com.hazelcast.core.IMap;

@Component
public class InputMapManager 
		extends AbstractManagerMap<SubTeamTaskTuple, Map<Long, Event>> 
		implements IMapManager<SubTeamTaskTuple, Map<Long, Event>> {

	private String subTeamMapName;
	
//	public InputMapManager(Long subTeamId) {
//		this.subTeamMapName = Constants.EVENTS_MAP + Constants.SUB_TEAM_PREFIX + subTeamId;
//	}
	
    @Override
    public IMap<SubTeamTaskTuple, Map<Long, Event>> getMap() {
        return this.hazelcast.getMap(this.subTeamMapName);
    }

    public void update(SubTeamTaskTuple key, Map<Long, Event> value) {
        this.getMap().set(key, value);
    }

    public void update(SubTeamTaskTuple key, Map<Long, Event> value, int eviction_value, TimeUnit eviction_unit) {
        this.getMap().set(key, value, eviction_value, eviction_unit);
    }

}
