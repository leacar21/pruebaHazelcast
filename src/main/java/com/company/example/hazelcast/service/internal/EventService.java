package com.company.example.hazelcast.service.internal;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.example.hazelcast.constants.Constants;
import com.company.example.hazelcast.dto.EventDTO;
import com.company.example.hazelcast.dto.EventType;
import com.company.example.hazelcast.repository.dao.event.EventDAO;
import com.company.example.hazelcast.repository.dao.task.TaskDAO;
import com.company.example.hazelcast.repository.model.Event;
import com.company.example.hazelcast.repository.model.EventToProcess;
import com.company.example.hazelcast.repository.model.QueuedEvent;
import com.company.example.hazelcast.repository.model.Task;
import com.company.example.hazelcast.repository.model.TaskEventsToProcess;
import com.company.example.hazelcast.repository.model.old.SubTeamTaskTuple;
import com.hazelcast.core.HazelcastInstance;

@Service
public class EventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
	
	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private EventDAO eventDAO;

	@PostConstruct
	public void init() {
	}
	
	public Long publishEvent(EventDTO eventDTO) throws InterruptedException {
		
		BlockingQueue<QueuedEvent> queue = hazelcastInstance.getQueue(Constants.EVENTS_QUEUE);
		eventDTO.setId(this.generateId());
		
		Task task = null;
		if (eventDTO.getType().equals(EventType.CREATION)) {
			task = new Task();
			task.setCheckIn(eventDTO.getTask().getCheckIn());
			task.setCountry(eventDTO.getTask().getCountry());
			task.setCreation(eventDTO.getTask().getCreation());
			task.setRequestId(eventDTO.getTask().getRequestId());
			task.setTransactionId(eventDTO.getTask().getTransactionId());
			taskDAO.save(task);
		} else {
			task = taskDAO.load(eventDTO.getTask().getId());
		}

		Event event = new Event();
		event.setCode(eventDTO.getCode());
		event.setType(eventDTO.getType().name());
		event.setTask(task);
		eventDAO.save(event);
		
		QueuedEvent queuedEvent = new QueuedEvent();
		queuedEvent.setEvent(event);
		
		queue.offer(queuedEvent);
		
		System.out.println("===> Event received with Id = " + event.getId() + " and TaskId " + task.getId());
		return task.getId();
		
	}

	//-------
	
	private Long generateId() {
		Date now = new Date();
		Random random = new Random();
		Long id = now.getTime()*100000 + (random.nextLong()%100000);
		return id;
	}

	//----
	
	public void printEvents() {
		//for (int i = 0; i < Constants.COUNT_SUB_TEAMS; i++) {
			System.out.println("--------------------------------------------------------------");
			//System.out.println("SubTeamId: " + i);
			//Map<SubTeamTaskTuple, Map<Long, Event>> eventMap = hazelcastInstance.getMap(Constants.EVENTS_MAP + Constants.SUB_TEAM_PREFIX + i);
			Map<Long, TaskEventsToProcess> eventMap = hazelcastInstance.getMap(Constants.EVENTS_MAP);
			printMap(eventMap);	
			System.out.println("--------------------------------------------------------------");
			System.out.println("");
		//}
	}

	private void printMap(Map<Long, TaskEventsToProcess> eventMap) {
		if (eventMap != null) {
			System.out.println("--------------------------------------------------------------");
			for (Long key : eventMap.keySet()) {
				TaskEventsToProcess taskEventsToProcess = eventMap.get(key);
				System.out.println("TaskId: " + taskEventsToProcess.getTask().getId());
				for (EventToProcess eventToProcess : taskEventsToProcess.getEventsToProcess()) {
					System.out.println("       EventId: " + eventToProcess.getEvent().getId() + " | " + eventToProcess.getEvent().getType());
				}
			}
			System.out.println("--------------------------------------------------------------");
			System.out.println("");
		}
	}
	
}
