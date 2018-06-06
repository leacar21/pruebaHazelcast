package com.company.example.hazelcast.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.example.hazelcast.repository.dao.event.EventDAO;
import com.company.example.hazelcast.repository.model.Event;

@Service
public class EventService {

	@Autowired
	private EventDAO eventDAO;
	
	@Transactional(readOnly = false)
	public void delete(Long key) {
		eventDAO.deleteByKey(key);
	}

	@Transactional(readOnly = false)
	public void save(Long key, Event value) {
		value.setKey(key);
		eventDAO.save(value);
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

}
