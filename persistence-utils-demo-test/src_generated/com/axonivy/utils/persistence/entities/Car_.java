package com.axonivy.utils.persistence.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.Car}
 **/
@StaticMetamodel(Car.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Car_ extends Vehicle_ {

	
	/**
	 * @see #fuel
	 **/
	public static final String FUEL = "fuel";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.Car}
	 **/
	public static volatile EntityType<Car> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Car#fuel}
	 **/
	public static volatile SingularAttribute<Car, Double> fuel;

}

