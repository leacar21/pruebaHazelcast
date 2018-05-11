package com.despegar.example.hazelcast.dto;

import java.io.Serializable;
import java.util.List;

public class PermisionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String restrictionKey;
	private List<String> restrictionValues;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRestrictionKey() {
		return restrictionKey;
	}
	public void setRestrictionKey(String restrictionKey) {
		this.restrictionKey = restrictionKey;
	}
	public List<String> getRestrictionValues() {
		return restrictionValues;
	}
	public void setRestrictionValues(List<String> restrictionValues) {
		this.restrictionValues = restrictionValues;
	}
	
}
