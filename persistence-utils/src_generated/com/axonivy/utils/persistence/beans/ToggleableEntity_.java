package com.axonivy.utils.persistence.beans;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.beans.ToggleableEntity}
 **/
@StaticMetamodel(ToggleableEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class ToggleableEntity_ extends AuditableEntity_ {

	
	/**
	 * @see #enabled
	 **/
	public static final String ENABLED = "enabled";
	
	/**
	 * @see #expiry
	 **/
	public static final String EXPIRY = "expiry";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.beans.ToggleableEntity}
	 **/
	public static volatile MappedSuperclassType<ToggleableEntity> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.ToggleableEntity#enabled}
	 **/
	public static volatile SingularAttribute<ToggleableEntity, Boolean> enabled;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.ToggleableEntity#expiry}
	 **/
	public static volatile SingularAttribute<ToggleableEntity, Instant> expiry;

}

