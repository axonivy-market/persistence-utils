package com.axonivy.utils.persistence.entities.family;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import com.axonivy.utils.persistence.beans.GenericIdEntity;

@Entity
public class ChildTestEntity extends GenericIdEntity {
	private static final long serialVersionUID = 1L;

	@Column
	private String name;
	
	@ManyToOne()
	private ParentTestEntity parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParentTestEntity getParent() {
		return parent;
	}

	public void setParent(ParentTestEntity parent) {
		this.parent = parent;
	}
}
