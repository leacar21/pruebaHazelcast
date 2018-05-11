package com.despegar.example.hazelcast.context.scan.config.hazelcast.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.repository.dao.process.EventToProcessDAO;
import com.despegar.example.hazelcast.repository.dao.process.TaskEventsToProcessDAO;
import com.despegar.example.hazelcast.repository.dao.task.TaskDAO;
import com.despegar.example.hazelcast.repository.model.EventToProcess;
import com.despegar.example.hazelcast.repository.model.TaskEventsToProcess;
import com.hazelcast.core.MapStore;

@Component
public class EventsMapStore implements MapStore<Long, TaskEventsToProcess>{

    
    @Autowired
    private TaskEventsToProcessDAO taskEventsToProcessDAO;
    
    @Autowired
    private EventToProcessDAO eventToProcessDAO;
    
    @Autowired
    private TaskDAO taskDAO;
    
    //----------------

    public synchronized void delete(Long key) {
    		System.out.println(">>>>> MAP delete");
    		taskEventsToProcessDAO.delete(key);
    }

    public synchronized void store(Long key, TaskEventsToProcess value) {
		
		TaskEventsToProcess taskEventsToProcess = this.load(key);
		if (taskEventsToProcess == null) {
        		taskEventsToProcessDAO.save(value);
        } else {
	    		for (EventToProcess eventToProcess : value.getEventsToProcess()) {
				if (eventToProcess.getId() == null) {
					eventToProcessDAO.save(eventToProcess);
				}
			}
        	
        		taskEventsToProcess.setEventsToProcess(value.getEventsToProcess());
        		taskEventsToProcessDAO.update(taskEventsToProcess);
        }
		System.out.println(">>>>> MAP store");
		
    }

    public synchronized void storeAll(Map<Long, TaskEventsToProcess> map) {
        for (Map.Entry<Long, TaskEventsToProcess> entry : map.entrySet())
            store(entry.getKey(), entry.getValue());
    }

    public synchronized void deleteAll(Collection<Long> keys) {
        for (Long key : keys) delete(key);
    }

    public synchronized TaskEventsToProcess load(Long key) {
    		System.out.println(">>>>> MAP load");
    		TaskEventsToProcess taskEventsToProcess = taskEventsToProcessDAO.read(key);
    		return taskEventsToProcess;
    }

    public synchronized Map<Long, TaskEventsToProcess> loadAll(Collection<Long> keys) {
    		System.out.println(">>>>> MAP loadAll");
        Map<Long, TaskEventsToProcess> result = new HashMap<Long, TaskEventsToProcess>();
        for (Long key : keys) { 
        		result.put(key, load(key));
        }
        return result;
    }

    public Iterable<Long> loadAllKeys() {
    		System.out.println(">>>>> MAP loadAllKeys");
    		List<Long> list = taskEventsToProcessDAO.loadAllKeys();
        Set<Long> result = new HashSet<Long>(list);
        return result;
    }
}
