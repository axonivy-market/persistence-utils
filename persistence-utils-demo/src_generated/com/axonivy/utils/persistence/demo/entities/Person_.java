package com.axonivy.utils.persistence.demo.entities;

import com.axonivy.utils.persistence.beans.AuditableIdEntity_;
import com.axonivy.utils.persistence.demo.enums.MaritalStatus;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.demo.entities.Person}
 **/
@StaticMetamodel(Person.class)
public abstract class Person_ extends AuditableIdEntity_ {

	
	/**
	 * @see #firstName
	 **/
	public static final String FIRST_NAME = "firstName";
	
	/**
	 * @see #lastName
	 **/
	public static final String LAST_NAME = "lastName";
	
	/**
	 * @see #ivyUserName
	 **/
	public static final String IVY_USER_NAME = "ivyUserName";
	
	/**
	 * @see #syncToIvy
	 **/
	public static final String SYNC_TO_IVY = "syncToIvy";
	
	/**
	 * @see #birthdate
	 **/
	public static final String BIRTHDATE = "birthdate";
	
	/**
	 * @see #maritalStatus
	 **/
	public static final String MARITAL_STATUS = "maritalStatus";
	
	/**
	 * @see #salary
	 **/
	public static final String SALARY = "salary";
	
	/**
	 * @see #department
	 **/
	public static final String DEPARTMENT = "department";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.demo.entities.Person}
	 **/
	public static volatile EntityType<Person> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Person#firstName}
	 **/
	public static volatile SingularAttribute<Person, String> firstName;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Person#lastName}
	 **/
	public static volatile SingularAttribute<Person, String> lastName;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Person#ivyUserName}
	 **/
	public static volatile SingularAttribute<Person, String> ivyUserName;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Person#syncToIvy}
	 **/
	public static volatile SingularAttribute<Person, Boolean> syncToIvy;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Person#birthdate}
	 **/
	public static volatile SingularAttribute<Person, Date> birthdate;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Person#maritalStatus}
	 **/
	public static volatile SingularAttribute<Person, MaritalStatus> maritalStatus;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Person#salary}
	 **/
	public static volatile SingularAttribute<Person, BigDecimal> salary;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.demo.entities.Person#department}
	 **/
	public static volatile SingularAttribute<Person, Department> department;

}

