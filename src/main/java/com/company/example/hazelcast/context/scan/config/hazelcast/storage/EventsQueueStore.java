package com.company.example.hazelcast.context.scan.config.hazelcast.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.example.hazelcast.repository.dao.event.QueuedEventDAO;
import com.company.example.hazelcast.repository.model.QueuedEvent;
import com.hazelcast.core.QueueStore;

@Component
public class EventsQueueStore implements QueueStore<QueuedEvent> {	


	@Autowired
	private QueuedEventDAO queuedEventDAO;
	
    @Override
    public void delete(Long key) {
        System.out.println(">>>>> delete " + key);
        queuedEventDAO.delete(key);
        System.out.println(">>>>> FIN delete ");
    }

    @Override
    public void store(Long key, QueuedEvent value) {
        System.out.println(">>>>> store " + key);
        value.setId(key);
        queuedEventDAO.save(value);
    }

    @Override
    public void storeAll(Map<Long, QueuedEvent> map) {
        System.out.println(">>>>> store all");
        for (Long key : map.keySet()) {
        		QueuedEvent queuedEvent = map.get(key);
        		store(key, queuedEvent);
        }
    }

    @Override
    public void deleteAll(Collection<Long> keys) {
        System.out.println(">>>>> deleteAll");
        for (Long key : keys) {
        		queuedEventDAO.deleteByKey(key);
		}
    }

    @Override
    public QueuedEvent load(Long key) {
        System.out.println(">>>>> load " + key);
        QueuedEvent queuedEvent = queuedEventDAO.load(key);
        return queuedEvent;
    }

    @Override
    public Map<Long, QueuedEvent> loadAll(Collection<Long> keys) {
        System.out.println(">>>>> loadAll");
        Map<Long, QueuedEvent> mapQueuedEvent = new HashMap<Long, QueuedEvent>();
        for (Long key : keys) {
        		QueuedEvent queuedEvent = queuedEventDAO.load(key);
        		mapQueuedEvent.put(key, queuedEvent);
		}
        return mapQueuedEvent;
    }

    @Override
    public Set<Long> loadAllKeys() {
        System.out.println(">>>>> loadAllKeys");
        List<Long> list = queuedEventDAO.loadAllKeys();
        Set<Long> result = new HashSet<Long>(list);
        return result;
    }
   
}