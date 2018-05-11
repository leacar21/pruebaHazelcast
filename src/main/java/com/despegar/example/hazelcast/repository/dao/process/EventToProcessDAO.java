package com.despegar.example.hazelcast.repository.dao.process;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.repository.dao.AbstractHibernateEntityDAO;
import com.despegar.example.hazelcast.repository.model.EventToProcess;
import com.despegar.example.hazelcast.repository.model.old.SubTeamEvent;

@Component
public class EventToProcessDAO
    extends AbstractHibernateEntityDAO<EventToProcess, Long> {

	public List<Long> loadAllKeys() {
		List<Long> keys = new LinkedList<Long>();
		List<EventToProcess> listEventToProcess = this.readAll();
		for (EventToProcess eventToProcess : listEventToProcess) {
			keys.add(eventToProcess.getId());
		}
        return keys; 
    }
	
}
