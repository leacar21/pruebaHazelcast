package com.despegar.example.hazelcast.job;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.constants.Constants;
import com.despegar.example.hazelcast.repository.dao.process.TaskEventsToProcessDAO;
import com.despegar.example.hazelcast.repository.model.Event;
import com.despegar.example.hazelcast.repository.model.EventToProcess;
import com.despegar.example.hazelcast.repository.model.QueuedEvent;
import com.despegar.example.hazelcast.repository.model.Task;
import com.despegar.example.hazelcast.repository.model.TaskEventsToProcess;
import com.hazelcast.core.HazelcastInstance;

@Component
public class ProcessQueueJob extends AbstractJob {

	private static final String PROCESS_QUEUE_JOB_NAME = "PROCESS_QUEUE";

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@Autowired
    private TaskEventsToProcessDAO taskEventsToProcessDAO;
	

	public ProcessQueueJob() {
		super(PROCESS_QUEUE_JOB_NAME);
	}

	@Override
	@Scheduled(initialDelay = 10000, fixedDelay = 5000)
	public void run() {
		super.run();
	}
	
	//-----

	@Override
	protected void execute() {
		// LOGICA DE NEGOCIO
		BlockingQueue<QueuedEvent> queue = hazelcastInstance.getQueue(Constants.EVENTS_QUEUE);
		
		// Se obtiene Evento
		QueuedEvent queuedEvent = queue.peek();
		
		if (queuedEvent!=null) {
			Event event = queuedEvent.getEvent();
			// Se avisa a SubEquipos
			System.out.println("<== Event processed with Id = " + event.getId());
			
//			for (long i = 0; i < Constants.COUNT_SUB_TEAMS; i++) {
//				processEvent(event, i);
//			}
			processEvent(event);
			
			// Se remueve evento de la cola
			System.out.println("ANTES DE REMOVER...");
			queue.remove();	
			System.out.println("REMOVIDO...");
		} else {
			System.out.println("Queue vacia");
		}
	}

	//------------------------
	
	private void processEvent(Event event) {

		Map<Long, TaskEventsToProcess> eventMap = hazelcastInstance.getMap(Constants.EVENTS_MAP);
		Task task = event.getTask();
		Long taskId = task.getId();
		
		TaskEventsToProcess taskEventsToProcess = null;
		if (eventMap.containsKey(taskId)) {
			
			TaskEventsToProcess taskEventsToProcessLoaded = taskEventsToProcessDAO.loadByTaskId(taskId);
			
			EventToProcess eventToProcess = new EventToProcess();
			eventToProcess.setEvent(event);
			eventToProcess.setTaskEventsToProcess(taskEventsToProcessLoaded);

			taskEventsToProcessLoaded.getEventsToProcess().add(eventToProcess);
			eventMap.put(taskId, taskEventsToProcessLoaded);
		} else {
			taskEventsToProcess = new TaskEventsToProcess();
			taskEventsToProcess.setTask(task);
			
			EventToProcess eventToProcess = new EventToProcess();
			eventToProcess.setEvent(event);
			eventToProcess.setTaskEventsToProcess(taskEventsToProcess);
			
			List<EventToProcess> eventsToProcess = new LinkedList<EventToProcess>();
			eventsToProcess.add(eventToProcess);
			taskEventsToProcess.setEventsToProcess(eventsToProcess);
			eventMap.put(taskId, taskEventsToProcess);
		}
		
		
		printMap(eventMap);
	}

	
//	private void processEvent(Event event, long numSubTeam) {
//		String subTeamMapName = Constants.EVENTS_MAP + Constants.SUB_TEAM_PREFIX + numSubTeam;
//		Map<SubTeamTaskTuple, Map<Long, Event>> eventMap = hazelcastInstance.getMap(subTeamMapName);
//		Long idTask = event.getTask().getId();
//		Long subTeamId = Long.valueOf(numSubTeam);
//		SubTeamTaskTuple keySubteamTaskTuple = new SubTeamTaskTuple(subTeamId, idTask);
//		if (eventMap.containsKey(keySubteamTaskTuple)) {
//			Map<Long, Event> mapIdTaskEvent = eventMap.get(keySubteamTaskTuple);
//			mapIdTaskEvent.put(event.getId(), event);
//			eventMap.put(keySubteamTaskTuple, mapIdTaskEvent);
//		} else {
//			Map<Long, Event> mapIdTaskEvent = new HashMap<Long, Event>();
//			mapIdTaskEvent.put(event.getId(), event);
//			eventMap.put(keySubteamTaskTuple, mapIdTaskEvent);
//		}
//		printMap(eventMap);
//	}

	//-----
	
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
	
	//-----

}

