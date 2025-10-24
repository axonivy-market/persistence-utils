package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans.ToggleableIdEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.Option}
 **/
@StaticMetamodel(Option.class)
public abstract class Option_ extends ToggleableIdEntity_ {

	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #value
	 **/
	public static final String VALUE = "value";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.Option}
	 **/
	public static volatile EntityType<Option> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Option#name}
	 **/
	public static volatile SingularAttribute<Option, String> name;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Option#value}
	 **/
	public static volatile SingularAttribute<Option, String> value;

}

