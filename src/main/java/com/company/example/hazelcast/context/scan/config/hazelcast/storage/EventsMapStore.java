package com.company.example.hazelcast.context.scan.config.hazelcast.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.example.hazelcast.repository.dao.event.EventDAO;
import com.company.example.hazelcast.repository.model.Event;
import com.hazelcast.core.MapStore;

@Component
public class EventsMapStore implements MapStore<Long, Event> {

//	@Autowired
//	private EventDAO eventDAO;

	// ----------------

	public synchronized void delete(Long key) {
	}

	public synchronized void store(Long key, Event value) {
	}

	public synchronized void storeAll(Map<Long, Event> map) {
	}

	public synchronized void deleteAll(Collection<Long> keys) {
	}

	public synchronized Event load(Long key) {
		return null;
	}

	public synchronized Map<Long, Event> loadAll(Collection<Long> keys) {
		Map<Long, Event> result = new HashMap<Long, Event>();
		return result;
	}

	public Iterable<Long> loadAllKeys() {
		List<Long> list = new LinkedList<Long>();
		return list;
	}

	// public synchronized void delete(Long key) {
	// eventDAO.deleteBykey(key);
	// }
	//
	// public synchronized void store(Long key, Event value) {
	// value.setKey(key);
	// eventDAO.save(value);
	// }
	//
	// public synchronized void storeAll(Map<Long, Event> map) {
	// for (Map.Entry<Long, Event> entry : map.entrySet())
	// store(entry.getKey(), entry.getValue());
	// }
	//
	// public synchronized void deleteAll(Collection<Long> keys) {
	// for (Long key : keys) delete(key);
	// }
	//
	// public synchronized Event load(Long key) {
	// Event event = eventDAO.findBykey(key);
	// return event;
	// }
	//
	// public synchronized Map<Long, Event> loadAll(Collection<Long> keys) {
	// Map<Long, Event> result = new HashMap<Long, Event>();
	// for (Long key : keys) {
	// result.put(key, load(key));
	// }
	// return result;
	// }
	//
	// public Iterable<Long> loadAllKeys() {
	// List<Long> list = eventDAO.loadAllKeys();
	// Set<Long> result = new HashSet<Long>(list);
	// return result;
	// }
}
