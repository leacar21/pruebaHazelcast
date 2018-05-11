package com.despegar.example.hazelcast.repository.model.old;

import java.io.Serializable;

public class SubTeamTaskTuple implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long subTeamId;
	private Long taskId;
	
	public SubTeamTaskTuple(Long subTeamId, Long taskId) {
		this.subTeamId = subTeamId;
		this.taskId = taskId;
	}
	
	public Long getSubTeamId() {
		return subTeamId;
	}
	public void setSubTeamId(Long subTeamId) {
		this.subTeamId = subTeamId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
}
