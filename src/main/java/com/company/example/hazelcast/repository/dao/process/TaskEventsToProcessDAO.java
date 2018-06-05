package com.company.example.hazelcast.repository.dao.process;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Component;

import com.company.example.hazelcast.repository.dao.AbstractHibernateEntityDAO;
import com.company.example.hazelcast.repository.model.TaskEventsToProcess;

@Component
public class TaskEventsToProcessDAO
    extends AbstractHibernateEntityDAO<TaskEventsToProcess, Long> {

	public List<Long> loadAllKeys() {
		List<Long> keys = new LinkedList<Long>();
		List<TaskEventsToProcess> listTaskEventsToProcess = this.readAll();
		for (TaskEventsToProcess taskEventsToProcess : listTaskEventsToProcess) {
			keys.add(taskEventsToProcess.getId());
		}
        return keys; 
    }
	
	public TaskEventsToProcess loadByTaskId(Long taskId) {
        Criteria criteria = this.join("task", "t", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("t.id", taskId));

        
        List<TaskEventsToProcess> list = criteria.list();
	    if (CollectionUtils.isNotEmpty(list)) {
	        return list.iterator().next();
	    }
	      
	    return null;
	}
	
}
