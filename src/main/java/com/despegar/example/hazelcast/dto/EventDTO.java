package com.despegar.example.hazelcast.dto;

import java.io.Serializable;

public class EventDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private EventType type;
	private String code;
	//private Date creation;
	private TaskDTO task;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
//	public Date getCreation() {
//		return creation;
//	}
//	public void setCreation(Date creation) {
//		this.creation = creation;
//	}
	public TaskDTO getTask() {
		return task;
	}
	public void setTask(TaskDTO task) {
		this.task = task;
	}
	
}
