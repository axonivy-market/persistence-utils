package com.axonivy.utils.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ extends com.axonivy.utils.persistence.beans.AuditableIdEntity_ {

	public static volatile SingularAttribute<Product, Double> price;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, Producer> producer;

	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String PRODUCER = "producer";

}

