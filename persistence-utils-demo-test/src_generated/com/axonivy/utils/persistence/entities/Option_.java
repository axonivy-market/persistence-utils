package com.axonivy.utils.persistence.entities;

import javax.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Option.class)
public abstract class Option_ extends com.axonivy.utils.persistence.beans.ToggleableIdEntity_ {

	public static volatile SingularAttribute<Option, String> name;
	public static volatile SingularAttribute<Option, String> value;

	public static final String NAME = "name";
	public static final String VALUE = "value";

}

