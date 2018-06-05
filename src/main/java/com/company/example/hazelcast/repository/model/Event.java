package com.company.example.hazelcast.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.company.example.hazelcast.repository.model.generic.AbstractHibernateEntity;

@Entity
@Table(name = "Z_EVENT")
public class Event
    extends AbstractHibernateEntity<Long>
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "native")
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @Column(name = "EVENT_TYPE", nullable = false)
	private String type;
	
    @Column(name = "CODE", nullable = false)
	private String code;
    
    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private Task task;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	
	
}
