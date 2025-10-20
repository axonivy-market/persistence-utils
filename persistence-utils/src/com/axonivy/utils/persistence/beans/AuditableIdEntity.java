package com.axonivy.utils.persistence.beans;

import org.hibernate.annotations.UuidGenerator;

import ch.ivyteam.ivy.environment.Ivy;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Auditable entity class which allows header manipulation
 * 
 */
@MappedSuperclass
public abstract class AuditableIdEntity extends AuditableEntity<String> {

	private static final long serialVersionUID = 8298601078993069192L;

	@Id
	@Column(length = 36, nullable = false)
	@GeneratedValue
	@UuidGenerator
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
