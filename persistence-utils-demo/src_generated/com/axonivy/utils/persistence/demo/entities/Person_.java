package com.axonivy.utils.persistence.demo.entities;

import com.axonivy.utils.persistence.demo.enums.MaritalStatus;
import java.math.BigDecimal;
import java.sql.Date;
import javax.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ extends com.axonivy.utils.persistence.beans.AuditableIdEntity_ {

	public static volatile SingularAttribute<Person, String> firstName;
	public static volatile SingularAttribute<Person, String> lastName;
	public static volatile SingularAttribute<Person, Date> birthdate;
	public static volatile SingularAttribute<Person, String> ivyUserName;
	public static volatile SingularAttribute<Person, Boolean> syncToIvy;
	public static volatile SingularAttribute<Person, BigDecimal> salary;
	public static volatile SingularAttribute<Person, Department> department;
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

