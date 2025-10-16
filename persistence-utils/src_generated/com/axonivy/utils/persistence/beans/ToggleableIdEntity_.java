package com.axonivy.utils.persistence.beans;

import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.beans.ToggleableIdEntity}
 **/
@StaticMetamodel(ToggleableIdEntity.class)
public abstract class ToggleableIdEntity_ extends ToggleableEntity_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.beans.ToggleableIdEntity}
	 **/
	public static volatile MappedSuperclassType<ToggleableIdEntity> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.ToggleableIdEntity#id}
	 **/
	public static volatile SingularAttribute<ToggleableIdEntity, String> id;

}

