package com.axonivy.utils.persistence.entities.family;

import com.axonivy.utils.persistence.beans.GenericIdEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.family.GrandParentTestEntity}
 **/
@StaticMetamodel(GrandParentTestEntity.class)
public abstract class GrandParentTestEntity_ extends GenericIdEntity_ {

	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.family.GrandParentTestEntity}
	 **/
	public static volatile EntityType<GrandParentTestEntity> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.family.GrandParentTestEntity#name}
	 **/
	public static volatile SingularAttribute<GrandParentTestEntity, String> name;

}

