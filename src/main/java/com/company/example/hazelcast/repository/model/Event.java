package com.company.example.hazelcast.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.example.hazelcast.repository.model.base.AbstractEntity;

@Entity
@Table(name = "ZZ_EVENT")
public class Event extends AbstractEntity {

    @Column(name = "EVENT_TYPE", nullable = false)
	private String type;
	
    @Column(name = "EVENT_CODE", nullable = false)
	private String code;
    
    @Column(name = "EVENT_KEY", nullable = false)
	private Long key;

    //------
    
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

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

}
