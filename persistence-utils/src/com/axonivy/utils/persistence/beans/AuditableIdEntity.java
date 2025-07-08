package com.axonivy.utils.persistence.beans;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import ch.ivyteam.ivy.environment.Ivy;

/**
 * Auditable entity class which allows header manipulation
 * 
 */
@MappedSuperclass
public abstract class AuditableIdEntity extends AuditableEntity<String> {

	private static final long serialVersionUID = 8298601078993069192L;

	@Id
	@Column(length = 32, nullable = false)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Access(AccessType.PROPERTY)
	protected String id;

	/* (non-Javadoc)
	 * @see com.axonivy.persistence.beans.GenericEntity#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.axonivy.persistence.beans.GenericEntity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get Ivy.session().getSessionUserName() as default session username, or override it later. 
	 */
	@Override
	public String getSessionUsername() {
		return Ivy.session().getSessionUserName();
	}
	
}
