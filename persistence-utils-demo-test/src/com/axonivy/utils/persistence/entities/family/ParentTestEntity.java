package com.axonivy.utils.persistence.entities.family;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import com.axonivy.utils.persistence.beans.GenericIdEntity;

@Entity
public class ParentTestEntity extends GenericIdEntity {
	private static final long serialVersionUID = 1L;

	@Column
	private String name;

	@ManyToOne()
	private GrandParentTestEntity grandParent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GrandParentTestEntity getGrandParent() {
		return grandParent;
	}

	public void setGrandParent(GrandParentTestEntity grandParent) {
		this.grandParent = grandParent;
	}
}
