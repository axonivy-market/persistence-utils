package com.axonivy.utils.persistence.entities;

import jakarta.persistence.Entity;

import com.axonivy.utils.persistence.beans.ToggleableIdEntity;

@Entity
public class Option extends ToggleableIdEntity {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String value;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
