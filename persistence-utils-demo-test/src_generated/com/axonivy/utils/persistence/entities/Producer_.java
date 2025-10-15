package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans.ToggleableIdEntity_;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.Producer}
 **/
@StaticMetamodel(Producer.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Producer_ extends ToggleableIdEntity_ {

	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #address
	 **/
	public static final String ADDRESS = "address";
	
	/**
	 * @see #phone
	 **/
	public static final String PHONE = "phone";
	
	/**
	 * @see #email
	 **/
	public static final String EMAIL = "email";
	
	/**
	 * @see #products
	 **/
	public static final String PRODUCTS = "products";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.Producer}
	 **/
	public static volatile EntityType<Producer> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Producer#name}
	 **/
	public static volatile SingularAttribute<Producer, String> name;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Producer#address}
	 **/
	public static volatile SingularAttribute<Producer, String> address;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Producer#phone}
	 **/
	public static volatile SingularAttribute<Producer, String> phone;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Producer#email}
	 **/
	public static volatile SingularAttribute<Producer, String> email;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Producer#products}
	 **/
	public static volatile SetAttribute<Producer, Product> products;

}

