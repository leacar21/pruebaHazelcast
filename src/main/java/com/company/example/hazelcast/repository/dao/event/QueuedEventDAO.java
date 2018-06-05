package com.company.example.hazelcast.repository.dao.event;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.company.example.hazelcast.repository.dao.AbstractHibernateEntityDAO;
import com.company.example.hazelcast.repository.model.QueuedEvent;

@Component
public class QueuedEventDAO
    extends AbstractHibernateEntityDAO<QueuedEvent, Long> {
    
	//private String ALL_KEYS = "SELECT ID FROM Z_QUEUED_EVENT";
    private String DELETE_KEY = "DELETE FROM Z_QUEUED_EVENT WHERE ID=?";
	
	@SuppressWarnings("unchecked")
    public List<Long> loadAllKeys() {
		List<Long> keys = new LinkedList<Long>();
		List<QueuedEvent> listQueuedEvent = this.readAll();
		for (QueuedEvent queuedEvent : listQueuedEvent) {
			keys.add(queuedEvent.getId());
		}
        return keys; 
        	// return this.getCurrentSession().createSQLQuery(this.ALL_KEYS).list();
    }

    public void deleteByKey(Long key) {
        this.getCurrentSession().createSQLQuery(this.DELETE_KEY).executeUpdate();
    }

}
