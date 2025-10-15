package com.axonivy.utils.persistence.beans;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.beans.AuditableIdEntity}
 **/
@StaticMetamodel(AuditableIdEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class AuditableIdEntity_ extends AuditableEntity_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.beans.AuditableIdEntity}
	 **/
	public static volatile MappedSuperclassType<AuditableIdEntity> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.AuditableIdEntity#id}
	 **/
	public static volatile SingularAttribute<AuditableIdEntity, String> id;

}

