package com.axonivy.utils.persistence.beans;

import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.beans.GenericIdEntity}
 **/
@StaticMetamodel(GenericIdEntity.class)
public abstract class GenericIdEntity_ extends VersionableEntity_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.beans.GenericIdEntity}
	 **/
	public static volatile MappedSuperclassType<GenericIdEntity> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.GenericIdEntity#id}
	 **/
	public static volatile SingularAttribute<GenericIdEntity, String> id;

}

