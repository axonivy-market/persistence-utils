package com.axonivy.utils.persistence.beans;

import javax.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VersionableEntity.class)
public abstract class VersionableEntity_ extends com.axonivy.utils.persistence.beans.GenericEntity_ {

	public static volatile SingularAttribute<VersionableEntity, Integer> version;

	public static final String VERSION = "version";

}

