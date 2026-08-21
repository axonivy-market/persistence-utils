package com.axonivy.utils.persistence.demo.entities;

import com.axonivy.utils.persistence.beans._AuditableIdEntity;
import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.SortableAttributeRecord;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.demo.entities.Person}
 **/
@StaticMetamodel(Person.class)
public interface _Person extends _AuditableIdEntity {

	
	/**
	 * @see #firstName
	 **/
	String FIRST_NAME = "firstName";
	
	/**
	 * @see #lastName
	 **/
	String LAST_NAME = "lastName";
	
	/**
	 * @see #ivyUserName
	 **/
	String IVY_USER_NAME = "ivyUserName";
	
	/**
	 * @see #syncToIvy
	 **/
	String SYNC_TO_IVY = "syncToIvy";
	
	/**
	 * @see #birthdate
	 **/
	String BIRTHDATE = "birthdate";
	
	/**
	 * @see #maritalStatus
	 **/
	String MARITAL_STATUS = "maritalStatus";
	
	/**
	 * @see #salary
	 **/
	String SALARY = "salary";
	
	/**
	 * @see #department
	 **/
	String DEPARTMENT = "department";

	
	/**
	 * Static metamodel for attribute {@link Person#firstName}
	 **/
	TextAttribute<Person> firstName = new TextAttributeRecord<>(FIRST_NAME);
	
	/**
	 * Static metamodel for attribute {@link Person#lastName}
	 **/
	TextAttribute<Person> lastName = new TextAttributeRecord<>(LAST_NAME);
	
	/**
	 * Static metamodel for attribute {@link Person#ivyUserName}
	 **/
	TextAttribute<Person> ivyUserName = new TextAttributeRecord<>(IVY_USER_NAME);
	
	/**
	 * Static metamodel for attribute {@link Person#syncToIvy}
	 **/
	SortableAttribute<Person> syncToIvy = new SortableAttributeRecord<>(SYNC_TO_IVY);
	
	/**
	 * Static metamodel for attribute {@link Person#birthdate}
	 **/
	SortableAttribute<Person> birthdate = new SortableAttributeRecord<>(BIRTHDATE);
	
	/**
	 * Static metamodel for attribute {@link Person#maritalStatus}
	 **/
	SortableAttribute<Person> maritalStatus = new SortableAttributeRecord<>(MARITAL_STATUS);
	
	/**
	 * Static metamodel for attribute {@link Person#salary}
	 **/
	SortableAttribute<Person> salary = new SortableAttributeRecord<>(SALARY);
	
	/**
	 * Static metamodel for attribute {@link Person#department}
	 **/
	SortableAttribute<Person> department = new SortableAttributeRecord<>(DEPARTMENT);

}

