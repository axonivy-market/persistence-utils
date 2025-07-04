package com.axonivy.utils.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import com.axonivy.utils.persistence.beans.AuditableIdEntity;

@Entity
public class Vehicle extends AuditableIdEntity {
	private static final long serialVersionUID = 1L;

	@Column(length = 64)
	private String name;

	@Column(length = 64) 
	private String color;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
