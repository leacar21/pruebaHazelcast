package com.despegar.example.hazelcast.repository.dao.event;

import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.repository.dao.AbstractHibernateEntityDAO;
import com.despegar.example.hazelcast.repository.model.Event;

@Component
public class EventDAO
    extends AbstractHibernateEntityDAO<Event, Long> {

}
