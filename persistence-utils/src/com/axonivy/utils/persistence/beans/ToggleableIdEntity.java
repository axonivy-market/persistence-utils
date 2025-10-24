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
 * Base for all entities which can be enabled and disabled.
 *
 */
@MappedSuperclass
public abstract class ToggleableIdEntity extends ToggleableEntity<String> {

	private static final long serialVersionUID = -8780884685375253549L;

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
