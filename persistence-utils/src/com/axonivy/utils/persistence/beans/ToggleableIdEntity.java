package com.axonivy.utils.persistence.beans;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import ch.ivyteam.ivy.environment.Ivy;

/**
 * Base for all entities which can be enabled and disabled.
 *
 */
@MappedSuperclass
public abstract class ToggleableIdEntity extends ToggleableEntity<String> {

	private static final long serialVersionUID = -8780884685375253549L;

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
