package com.despegar.example.hazelcast.repository.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.despegar.example.hazelcast.repository.model.generic.AbstractHibernateEntity;

@Entity
@Table(name = "Z_QUEUED_EVENT")
public class QueuedEvent
    extends AbstractHibernateEntity<Long>
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "EVENT_ID")
    private Event event;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
