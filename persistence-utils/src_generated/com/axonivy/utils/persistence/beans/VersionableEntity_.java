package com.axonivy.utils.persistence.beans;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.beans.VersionableEntity}
 **/
@StaticMetamodel(VersionableEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class VersionableEntity_ extends GenericEntity_ {

	
	/**
	 * @see #version
	 **/
	public static final String VERSION = "version";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.beans.VersionableEntity}
	 **/
	public static volatile MappedSuperclassType<VersionableEntity> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.VersionableEntity#version}
	 **/
	public static volatile SingularAttribute<VersionableEntity, Integer> version;

}

