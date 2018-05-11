package com.despegar.example.hazelcast.repository.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.despegar.example.hazelcast.repository.model.generic.AbstractHibernateEntity;


@Entity
@Table(name = "Z_EVENTS_TO_PROCESS")
public class EventToProcess
    extends AbstractHibernateEntity<Long>
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "native")
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "EVENT_ID")
    private Event event;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TASK_EVENTS_TO_PROCESS_ID", nullable = false)
    private TaskEventsToProcess taskEventsToProcess;
    
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

	public TaskEventsToProcess getTaskEventsToProcess() {
		return taskEventsToProcess;
	}

	public void setTaskEventsToProcess(TaskEventsToProcess taskEventsToProcess) {
		this.taskEventsToProcess = taskEventsToProcess;
	}

}
