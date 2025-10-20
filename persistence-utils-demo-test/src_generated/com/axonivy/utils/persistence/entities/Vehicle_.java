package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans.AuditableIdEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.Vehicle}
 **/
@StaticMetamodel(Vehicle.class)
public abstract class Vehicle_ extends AuditableIdEntity_ {

	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #color
	 **/
	public static final String COLOR = "color";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.Vehicle}
	 **/
	public static volatile EntityType<Vehicle> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Vehicle#name}
	 **/
	public static volatile SingularAttribute<Vehicle, String> name;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Vehicle#color}
	 **/
	public static volatile SingularAttribute<Vehicle, String> color;

}

