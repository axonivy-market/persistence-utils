package com.axonivy.utils.persistence.demo.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Department.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Department_ extends com.axonivy.utils.persistence.beans.AuditableIdEntity_ {

	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Department#persons
	 **/
	public static volatile ListAttribute<Department, Person> persons;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Department#name
	 **/
	public static volatile SingularAttribute<Department, String> name;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Department
	 **/
	public static volatile EntityType<Department> class_;

	public static final String PERSONS = "persons";
	public static final String NAME = "name";

}

