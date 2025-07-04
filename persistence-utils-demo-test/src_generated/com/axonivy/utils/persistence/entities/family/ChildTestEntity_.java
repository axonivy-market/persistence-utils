package com.axonivy.utils.persistence.entities.family;

import javax.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChildTestEntity.class)
public abstract class ChildTestEntity_ extends com.axonivy.utils.persistence.beans.GenericIdEntity_ {

	public static volatile SingularAttribute<ChildTestEntity, ParentTestEntity> parent;
	public static volatile SingularAttribute<ChildTestEntity, String> name;

	public static final String PARENT = "parent";
	public static final String NAME = "name";

}

