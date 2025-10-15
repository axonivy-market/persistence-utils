package com.axonivy.utils.persistence.entities.family;

import com.axonivy.utils.persistence.beans.GenericIdEntity_;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.family.ChildTestEntity}
 **/
@StaticMetamodel(ChildTestEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class ChildTestEntity_ extends GenericIdEntity_ {

	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #parent
	 **/
	public static final String PARENT = "parent";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.family.ChildTestEntity}
	 **/
	public static volatile EntityType<ChildTestEntity> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.family.ChildTestEntity#name}
	 **/
	public static volatile SingularAttribute<ChildTestEntity, String> name;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.family.ChildTestEntity#parent}
	 **/
	public static volatile SingularAttribute<ChildTestEntity, ParentTestEntity> parent;

}

