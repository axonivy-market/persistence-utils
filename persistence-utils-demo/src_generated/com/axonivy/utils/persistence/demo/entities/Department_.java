package com.axonivy.utils.persistence.demo.entities;

import javax.annotation.Generated;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ extends com.axonivy.utils.persistence.beans.AuditableIdEntity_ {

	public static volatile ListAttribute<Department, Person> persons;
	public static volatile SingularAttribute<Department, String> name;

	public static final String PERSONS = "persons";
	public static final String NAME = "name";

}

