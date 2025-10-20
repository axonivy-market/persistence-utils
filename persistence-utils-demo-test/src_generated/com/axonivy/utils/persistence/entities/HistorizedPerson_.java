package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans.AuditableIdEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.HistorizedPerson}
 **/
@StaticMetamodel(HistorizedPerson.class)
public abstract class HistorizedPerson_ extends AuditableIdEntity_ {

	
	/**
	 * @see #firstName
	 **/
	public static final String FIRST_NAME = "firstName";
	
	/**
	 * @see #LastName
	 **/
	public static final String LAST_NAME = "LastName";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.HistorizedPerson}
	 **/
	public static volatile EntityType<HistorizedPerson> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.HistorizedPerson#firstName}
	 **/
	public static volatile SingularAttribute<HistorizedPerson, String> firstName;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.HistorizedPerson#LastName}
	 **/
	public static volatile SingularAttribute<HistorizedPerson, String> LastName;

}

