package com.company.example.hazelcast.repository.dao.event;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.company.example.hazelcast.repository.dao.base.BaseDaoHibernate;
import com.company.example.hazelcast.repository.model.Event;

//@Repository
public class EventDAO extends BaseDaoHibernate<Event, Long> {
	
	@Autowired
	protected EventDAO(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
	}

	@Override
	public List<Event> findAll() {
		return this.getHibernateTemplate().loadAll(Event.class);
	}

	@Override
	public Event findById(Long id) {
		return this.getHibernateTemplate().get(Event.class, id);
	}
	
	//---------
	
	public Event findBykey(Long key) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Event.class);
		if(key != null) criteria.add(Restrictions.eq("key", key));
		List<Event> list = (List<Event>) this.getHibernateTemplate().findByCriteria(criteria);
		Event event = null;
		if (!list.isEmpty()){
			event = list.get(0);
		}
		
		return event;
	}
	
	public void deleteBykey(Long key) {
		Event event = this.findBykey(key);
		this.delete(event);
	}
	
	public List<Long> loadAllKeys() {
        List<Long> keys = new LinkedList<Long>();
        List<Event> events = this.findAll();
        for (Event event : events) {
            keys.add(event.getKey());
        }
        return keys;
    }

	
}
