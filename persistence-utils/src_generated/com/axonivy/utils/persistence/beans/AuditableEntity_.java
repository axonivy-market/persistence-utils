package com.axonivy.utils.persistence.beans;

import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.beans.AuditableEntity}
 **/
@StaticMetamodel(AuditableEntity.class)
public abstract class AuditableEntity_ extends VersionableEntity_ {

	
	/**
	 * @see #header
	 **/
	public static final String HEADER = "header";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.beans.AuditableEntity}
	 **/
	public static volatile MappedSuperclassType<AuditableEntity> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.AuditableEntity#header}
	 **/
	public static volatile SingularAttribute<AuditableEntity, Header> header;

}

