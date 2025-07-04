package com.axonivy.utils.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import com.axonivy.utils.persistence.annotations.Audit;
import com.axonivy.utils.persistence.beans.AuditableIdEntity;
import com.axonivy.utils.persistence.daos.AuditHandler;

@Entity
@Audit(handler = AuditHandler.class)
public class HistorizedPerson extends AuditableIdEntity {

	/**
	 * auto generated id
	 */
	private static final long serialVersionUID = -8255457957691314129L;
	
	@Column(length = 32)
	private String firstName;
	
	@Column(length = 64)
	private String LastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
}
