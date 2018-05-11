package com.despegar.example.hazelcast.repository.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.despegar.example.hazelcast.repository.model.generic.AbstractHibernateEntity;


@Entity
@Table(name = "Z_TASK_EVENTS_TO_PROCESS")
public class TaskEventsToProcess
    extends AbstractHibernateEntity<Long>
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "native")
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private Task task;
    
    @OneToMany(mappedBy = "taskEventsToProcess", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<EventToProcess> eventsToProcess;
    

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<EventToProcess> getEventsToProcess() {
		return eventsToProcess;
	}

	public void setEventsToProcess(List<EventToProcess> eventsToProcess) {
		this.eventsToProcess = eventsToProcess;
	}

}
