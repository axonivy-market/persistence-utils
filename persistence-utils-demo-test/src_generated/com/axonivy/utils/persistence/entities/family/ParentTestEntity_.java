package com.axonivy.utils.persistence.entities.family;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ParentTestEntity.class)
public abstract class ParentTestEntity_ extends com.axonivy.utils.persistence.beans.GenericIdEntity_ {

	public static volatile SingularAttribute<ParentTestEntity, GrandParentTestEntity> grandParent;
	public static volatile SingularAttribute<ParentTestEntity, String> name;

	public static final String GRAND_PARENT = "grandParent";
	public static final String NAME = "name";

}

