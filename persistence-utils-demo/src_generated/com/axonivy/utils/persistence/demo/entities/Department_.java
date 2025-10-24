package com.axonivy.utils.persistence.demo.entities;

import com.axonivy.utils.persistence.beans.AuditableIdEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.demo.entities.Department}
 **/
@StaticMetamodel(Department.class)
public abstract class Department_ extends AuditableIdEntity_ {

	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #persons
	 **/
	public static final String PERSONS = "persons";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.demo.entities.Department}
	 **/
	public static volatile EntityType<Department> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Department#name}
	 **/
	public static volatile SingularAttribute<Department, String> name;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Department#persons}
	 **/
	public static volatile ListAttribute<Department, Person> persons;

}

