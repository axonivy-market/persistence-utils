package com.axonivy.utils.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Producer.class)
public abstract class Producer_ extends com.axonivy.utils.persistence.beans.ToggleableIdEntity_ {

	public static volatile SingularAttribute<Producer, String> address;
	public static volatile SingularAttribute<Producer, String> phone;
	public static volatile SingularAttribute<Producer, String> name;
	public static volatile SingularAttribute<Producer, String> email;
	public static volatile SetAttribute<Producer, Product> products;

	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String PRODUCTS = "products";

}

