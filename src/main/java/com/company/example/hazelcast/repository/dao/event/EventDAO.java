package com.company.example.hazelcast.repository.dao.event;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.example.hazelcast.repository.model.Event;

@Repository
public class EventDAO {

	@Autowired
	private SessionFactory sessionFactory;

	
	public void deleteByKey(Long key) {
		Session session = sessionFactory.getCurrentSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		Event event = this.getByKey(key, em);
		session.delete(event);
	}

	public void save(Event value) {
		sessionFactory.getCurrentSession().saveOrUpdate(value);
	}

	public Event findBykey(Long key) {
		Session session = sessionFactory.getCurrentSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		Event event = this.getByKey(key, em);
		return event;
	}

	public List<Long> loadAllKeys() {
		Session session = sessionFactory.getCurrentSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		TypedQuery<Long> query = em.createQuery(
		        "SELECT e.key FROM Event e",
		        Long.class);
		List<Long> listEvent = query.getResultList();
		return listEvent;
	}
	
	//--------
	
	private Event getByKey(Long key, EntityManager em) {
		TypedQuery<Event> query = em.createQuery(
		        "SELECT e FROM Event e WHERE e.key = '" + key + "'",
		        Event.class);
		List<Event> listEvent = query.getResultList();
		if ((listEvent != null) && (listEvent.size() > 0)){
			return listEvent.get(0);
		}
		return null;
	}

}
