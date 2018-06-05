package com.company.example.hazelcast.repository.dao.event;

import org.springframework.stereotype.Component;

import com.company.example.hazelcast.repository.dao.AbstractHibernateEntityDAO;
import com.company.example.hazelcast.repository.model.Event;

@Component
public class EventDAO
    extends AbstractHibernateEntityDAO<Event, Long> {

}
