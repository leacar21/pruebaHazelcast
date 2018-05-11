package com.despegar.example.hazelcast.job;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.constants.Constants;
import com.despegar.example.hazelcast.repository.dao.process.TaskEventsToProcessDAO;
import com.despegar.example.hazelcast.repository.model.EventToProcess;
import com.despegar.example.hazelcast.repository.model.SubTeam;
import com.despegar.example.hazelcast.repository.model.TaskEventsToProcess;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Component
public class ProcessMapJob {

	private static final String PROCESS_MAP_JOB_NAME = "PROCESS_MAP";

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@Autowired
    private TaskEventsToProcessDAO taskEventsToProcessDAO;
	

	public ProcessMapJob() {
	}

	@Scheduled(initialDelay = 10000, fixedDelay = 5000)
	public void run() {

		try {
			String ip = hazelcastInstance.getCluster().getLocalMember().getAddress().getHost();
			System.out.println("Ejecutando MAP JOB en: " + ip);
			// run job
			this.execute();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			e.printStackTrace();
		} finally {
		}		

	}
	
	//-----

	protected void execute() {
		// LOGICA DE NEGOCIO
		IMap<Long, TaskEventsToProcess> mapTaskEventsToProcess = hazelcastInstance.getMap(Constants.EVENTS_MAP);
		IMap<Long, SubTeam> mapSubteams = hazelcastInstance.getMap(Constants.SUBTEAMS_MAP);
		
		// Se obtiene Evento
		Set<Long> localKeySet = mapTaskEventsToProcess.localKeySet();
		List<Long> localKeyList = new LinkedList<>(localKeySet);
		if (CollectionUtils.isNotEmpty(localKeyList)) {
			Long key = Collections.min(localKeyList); // De momento procesamos uno por vez en el Job
			//Long key = localKeyList.get(0); // De momento procesamos uno por vez en el Job
			
			mapTaskEventsToProcess.lock(key); // Bloqueo pesimista
            try {
            	
	            	TaskEventsToProcess taskEventsToProcess = mapTaskEventsToProcess.get(key);
	
	    			for (SubTeam subTeam : mapSubteams.values()) { // TODO: Ver si no bloquear tambien los subequipos
	    				processEvent(subTeam, taskEventsToProcess);
	    			} 
            	
            } finally {
            		mapTaskEventsToProcess.delete(key);
            		mapTaskEventsToProcess.unlock(key);
            }

		}
		
	}

	//------------------------
	
	private void processEvent(SubTeam subTeam, TaskEventsToProcess taskEventsToProcess) {
		List<EventToProcess> eventsToProcess = taskEventsToProcess.getEventsToProcess();
		for (EventToProcess eventToProcess : eventsToProcess) {
			try {
				System.out.println("--------> Subteam: " + subTeam.getCode() + " | Event: " + eventToProcess.getEvent().getId() + "-" + eventToProcess.getEvent().getType());
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}

	//-----

}

