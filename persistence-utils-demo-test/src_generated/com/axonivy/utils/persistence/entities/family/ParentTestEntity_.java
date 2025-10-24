package com.axonivy.utils.persistence.entities.family;

import com.axonivy.utils.persistence.beans.GenericIdEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.family.ParentTestEntity}
 **/
@StaticMetamodel(ParentTestEntity.class)
public abstract class ParentTestEntity_ extends GenericIdEntity_ {

	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #grandParent
	 **/
	public static final String GRAND_PARENT = "grandParent";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.family.ParentTestEntity}
	 **/
	public static volatile EntityType<ParentTestEntity> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.family.ParentTestEntity#name}
	 **/
	public static volatile SingularAttribute<ParentTestEntity, String> name;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.family.ParentTestEntity#grandParent}
	 **/
	public static volatile SingularAttribute<ParentTestEntity, GrandParentTestEntity> grandParent;

}

