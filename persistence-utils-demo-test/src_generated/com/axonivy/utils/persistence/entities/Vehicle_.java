package com.axonivy.utils.persistence.entities;

import javax.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Vehicle.class)
public abstract class Vehicle_ extends com.axonivy.utils.persistence.beans.AuditableIdEntity_ {

	public static volatile SingularAttribute<Vehicle, String> color;
	public static volatile SingularAttribute<Vehicle, String> name;

	public static final String COLOR = "color";
	public static final String NAME = "name";

}

