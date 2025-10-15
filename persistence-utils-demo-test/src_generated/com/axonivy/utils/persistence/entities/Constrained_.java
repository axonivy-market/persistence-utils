package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans.GenericEntity_;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.Constrained}
 **/
@StaticMetamodel(Constrained.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Constrained_ extends GenericEntity_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";
	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.Constrained}
	 **/
	public static volatile EntityType<Constrained> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Constrained#id}
	 **/
	public static volatile SingularAttribute<Constrained, UUID> id;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Constrained#name}
	 **/
	public static volatile SingularAttribute<Constrained, String> name;

}

