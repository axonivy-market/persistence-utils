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
 * Generic entity used as a base for all persistent objects with a technical key.
 *
 * The technical key is a random UUID and is auto generated.
 */
@MappedSuperclass
public abstract class GenericIdEntity extends VersionableEntity<String> implements GetterIdString {
	private static final long serialVersionUID = 1L;

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
