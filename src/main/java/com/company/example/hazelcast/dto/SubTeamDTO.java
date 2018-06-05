package com.company.example.hazelcast.dto;

import java.io.Serializable;
import java.util.List;

public class SubTeamDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String code;
	private String name;
	private List<PermisionDTO> permisions;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PermisionDTO> getPermisions() {
		return permisions;
	}
	public void setPermisions(List<PermisionDTO> permisions) {
		this.permisions = permisions;
	}
}
