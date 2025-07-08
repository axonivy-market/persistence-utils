package com.axonivy.utils.persistence.entities.family;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import com.axonivy.utils.persistence.beans.GenericIdEntity;

@Entity
public class GrandParentTestEntity extends GenericIdEntity {
	private static final long serialVersionUID = 1L;

	@Column
	private String name;
}
