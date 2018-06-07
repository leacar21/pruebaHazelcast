package com.company.example.hazelcast.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.example.hazelcast.constants.Constants;
import com.company.example.hazelcast.repository.dao.event.EventDAO;
import com.company.example.hazelcast.repository.dao.event.EventToProcessDAO;
import com.company.example.hazelcast.repository.model.Event;
import com.company.example.hazelcast.repository.model.EventToProcess;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Service
public class EventService {

	@Autowired
    private HazelcastInstance hazelcastInstance;
	
	@Autowired
	private EventDAO eventDAO;
	
	@Autowired
	private EventToProcessDAO eventToProcessDAO;
	
	
	@Transactional(readOnly = false)
	public void delete(Long key) {
		eventDAO.deleteByKey(key);
	}

	@Transactional(readOnly = false)
	public void save(Long key, Event value) {
		value.setKey(key);
		Date d1 = new Date();
		eventDAO.save(value);
		Date d2 = new Date();
		Long time = d2.getTime() - d1.getTime();
		System.out.println(">>> " + time);
	}

	@Transactional(readOnly = false)
	public void saveAll(Map<Long, Event> map) {
		for (Map.Entry<Long, Event> entry : map.entrySet())
			this.save(entry.getKey(), entry.getValue());
	}

	@Transactional(readOnly = false)
	public void deleteAll(Collection<Long> keys) {
		for (Long key : keys) this.delete(key);
	}

	@Transactional(readOnly = true)
	public Event load(Long key) {
		Event event = eventDAO.findBykey(key);
		return event;
	}

	@Transactional(readOnly = true)
	public Map<Long, Event> loadAll(Collection<Long> keys) {
		Map<Long, Event> result = new HashMap<Long, Event>();
		for (Long key : keys) {
			result.put(key, this.load(key));
		}
		return result;
	}

	@Transactional(readOnly = true)
	public Iterable<Long> loadAllKeys() {
		List<Long> list = eventDAO.loadAllKeys();
		Set<Long> result = new HashSet<Long>(list);
		return result;
	}

	@Transactional(readOnly = false)
	public void addEventToProcess(Event event) {
		EventToProcess eventToProcess = new EventToProcess();
		eventToProcess.setCode(event.getCode());
		eventToProcess.setType(event.getType());
		eventToProcess.setKey(event.getKey());
        this.eventToProcessDAO.save(eventToProcess);
	}

	@Transactional(readOnly = false)
	public void processNextEventToProcess() {
		EventToProcess eventToProcess = this.eventToProcessDAO.loadFirst();
		if (eventToProcess != null) {
			Event event = new Event();
			event.setCode(eventToProcess.getCode());
			event.setType(eventToProcess.getType());
			event.setKey(eventToProcess.getKey());
			IMap<Long, Event> map = this.hazelcastInstance.getMap(Constants.EVENTS_MAP);
			map.put(event.getKey(), event);
			this.eventToProcessDAO.delete(eventToProcess);
		}
	}
	
}
