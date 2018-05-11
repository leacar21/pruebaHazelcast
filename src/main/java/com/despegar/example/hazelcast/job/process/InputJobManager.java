package com.despegar.example.hazelcast.job.process;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.despegar.example.hazelcast.constants.Constants;
import com.despegar.example.hazelcast.context.scan.config.hazelcast.process.input.InputDistributor;
import com.despegar.example.hazelcast.repository.model.Event;
import com.despegar.example.hazelcast.repository.model.TaskEventsToProcess;
import com.despegar.example.hazelcast.repository.model.old.SubTeamTaskTuple;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Service
public class InputJobManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputJobManager.class);

    @Autowired
	private HazelcastInstance hazelcastInstance;

    @Autowired
    private InputDistributor distributor;

    @Scheduled(initialDelay = 20000, fixedDelay = 5000)
    public void jobsStart() {
    		Map<Long, TaskEventsToProcess> eventMap = hazelcastInstance.getMap(Constants.EVENTS_MAP);
//        map.entrySet().stream().forEach(item -> this.run(item.getValue()));

    }

    public void run(InputJobDTO inputJob) {
//        this.distributor.filterAndProcess(inputJob);
    }


}
