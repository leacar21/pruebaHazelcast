package com.despegar.example.hazelcast.repository.dao.event;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.repository.dao.AbstractHibernateEntityDAO;
import com.despegar.example.hazelcast.repository.model.old.SubTeamEvent;
import com.despegar.example.hazelcast.repository.model.old.SubTeamTaskTuple;

@Component
public class SubTeamEventDAO{
	
}

//@Component
//public class SubTeamEventDAO
//    extends AbstractHibernateEntityDAO<SubTeamEvent, Long> {
//	
//	@SuppressWarnings("unchecked")
//    public List<SubTeamEvent> readBySubTeamIdAndTaskId(Long subTeamId, Long taskId) {
//
//        Criteria criteria = this.join("task", "t", JoinType.INNER_JOIN);
//        criteria.add(Restrictions.eq("subTeamId", subTeamId));
//        criteria.add(Restrictions.eq("t.id", taskId));
//
//        List<SubTeamEvent> list = criteria.list();
//        return list;
//    }
//	
//	@SuppressWarnings("unchecked")
//    public SubTeamEvent readBySubTeamIdAndEventId(Long subTeamId, Long eventId) {
//
//        Criteria criteria = this.join("event", "e", JoinType.INNER_JOIN);
//        criteria.add(Restrictions.eq("subTeamId", subTeamId));
//        criteria.add(Restrictions.eq("e.id", eventId));
//
//        List<SubTeamEvent> list = criteria.list();
//        if (CollectionUtils.isNotEmpty(list)) {
//            return list.iterator().next();
//        }
//        
//        return null;
//    }
//
//	
////	@SuppressWarnings("unchecked")
////    public List<SubTeamTaskTuple> loadAllKeys(Long subTeamId) {
////		List<SubTeamTaskTuple> keys = new LinkedList<SubTeamTaskTuple>();
////		
////		Criteria criteria = this.getSession();
////        criteria.add(Restrictions.eq("subTeamId", subTeamId));
////        
////        List<SubTeamEvent> listSubTeamEvent = criteria.list();
////		for (SubTeamEvent subTeamEvent : listSubTeamEvent) {
////			SubTeamTaskTuple subTeamTaskTuple = new SubTeamTaskTuple(subTeamEvent.getSubTeamId(), subTeamEvent.getTask().getId());
////			keys.add(subTeamTaskTuple);
////		}
////        return keys; 
////    }
//	
//	
//	
////	@SuppressWarnings("unchecked")
////    public List<SubTeamTaskTuple> loadAllKeys() {
////		List<SubTeamTaskTuple> keys = new LinkedList<SubTeamTaskTuple>();
////        
////        List<SubTeamEvent> listSubTeamEvent = this.readAll();
////		for (SubTeamEvent subTeamEvent : listSubTeamEvent) {
////			SubTeamTaskTuple subTeamTaskTuple = new SubTeamTaskTuple(subTeamEvent.getSubTeamId(), subTeamEvent.getTask().getId());
////			keys.add(subTeamTaskTuple);
////		}
////        return keys; 
////    }
//	
//}
