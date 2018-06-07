package com.company.example.hazelcast.repository.dao.event;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.example.hazelcast.repository.model.Event;
import com.company.example.hazelcast.repository.model.EventToProcess;

@Repository
public class EventToProcessDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	
	public void delete(EventToProcess value) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(value);
	}

	public void save(EventToProcess value) {
		sessionFactory.getCurrentSession().save(value);
	}

	public EventToProcess loadFirst() {
		
		Session session = sessionFactory.getCurrentSession();
//		EntityManager em = entityManagerFactory.createEntityManager();
//		
//		
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//
//		CriteriaQuery<EventToProcess> q = cb.createQuery(EventToProcess.class);
//		Root<EventToProcess> c = q.from(EventToProcess.class);
//		q.select(c);
//		Selection<EventToProcess> selection = q.getSelection();
		
//		TypedQuery<Long> query = session.createQuery(
//		        "SELECT e.key FROM EventToProcess e",
//		        Long.class);
//		List<Long> listEvent = query.getResultList();
		
		TypedQuery<EventToProcess> query = session.createQuery(
		        "SELECT e FROM EventToProcess e",
		        EventToProcess.class);
		List<EventToProcess> listEvent = query.getResultList();
		if ((listEvent != null) && (listEvent.size() > 0)){
			return listEvent.get(0);
		}
		return null;
	}
	
}
