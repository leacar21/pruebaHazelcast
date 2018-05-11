package com.despegar.example.hazelcast.job.process;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.context.scan.config.hazelcast.map.AbstractManagerMap;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Component
public class InputJobMapManager
                //extends AbstractManagerMap<InputJobDTO>
                implements Serializable {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
//    @Override
//    public IMap<String, InputJobDTO> getMap() {
//        return this.hazelcastInstance.getMap(""); // TODO: nombre?
//    }

    public Lock getLock(String item) {
        return this.hazelcastInstance.getLock(item);
    }

}
