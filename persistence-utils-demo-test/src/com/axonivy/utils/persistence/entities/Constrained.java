package com.axonivy.utils.persistence.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.axonivy.utils.persistence.beans.GenericEntity;

@Entity
public class Constrained extends GenericEntity<UUID> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected UUID id;

	@Override
	public String getSessionUsername() {
		return "test";
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}
}
