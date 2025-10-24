package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans.AuditableIdEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.Product}
 **/
@StaticMetamodel(Product.class)
public abstract class Product_ extends AuditableIdEntity_ {

	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #price
	 **/
	public static final String PRICE = "price";
	
	/**
	 * @see #producer
	 **/
	public static final String PRODUCER = "producer";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.Product}
	 **/
	public static volatile EntityType<Product> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Product#name}
	 **/
	public static volatile SingularAttribute<Product, String> name;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Product#price}
	 **/
	public static volatile SingularAttribute<Product, Double> price;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Product#producer}
	 **/
	public static volatile SingularAttribute<Product, Producer> producer;

}

