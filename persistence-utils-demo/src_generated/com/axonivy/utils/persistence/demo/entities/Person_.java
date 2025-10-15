package com.axonivy.utils.persistence.demo.entities;

import com.axonivy.utils.persistence.demo.enums.MaritalStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.sql.Date;

@StaticMetamodel(Person.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Person_ extends com.axonivy.utils.persistence.beans.AuditableIdEntity_ {

	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Person#firstName
	 **/
	public static volatile SingularAttribute<Person, String> firstName;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Person#lastName
	 **/
	public static volatile SingularAttribute<Person, String> lastName;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Person#birthdate
	 **/
	public static volatile SingularAttribute<Person, Date> birthdate;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Person#ivyUserName
	 **/
	public static volatile SingularAttribute<Person, String> ivyUserName;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Person#syncToIvy
	 **/
	public static volatile SingularAttribute<Person, Boolean> syncToIvy;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Person#salary
	 **/
	public static volatile SingularAttribute<Person, BigDecimal> salary;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Person#department
	 **/
	public static volatile SingularAttribute<Person, Department> department;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Person
	 **/
	public static volatile EntityType<Person> class_;
	
	/**
	 * @see com.axonivy.utils.persistence.demo.entities.Person#maritalStatus
	 **/
	public static volatile SingularAttribute<Person, MaritalStatus> maritalStatus;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String BIRTHDATE = "birthdate";
	public static final String IVY_USER_NAME = "ivyUserName";
	public static final String SYNC_TO_IVY = "syncToIvy";
	public static final String SALARY = "salary";
	public static final String DEPARTMENT = "department";
	public static final String MARITAL_STATUS = "maritalStatus";

}

