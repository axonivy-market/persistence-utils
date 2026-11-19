package com.axonivy.utils.persistence.demo.entities;

import com.axonivy.utils.persistence.beans._AuditableIdEntity;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.demo.entities.Department}
 **/
@StaticMetamodel(Department.class)
public interface _Department extends _AuditableIdEntity {

	
	/**
	 * @see #name
	 **/
	String NAME = "name";

	
	/**
	 * Static metamodel for attribute {@link Department#name}
	 **/
	TextAttribute<Department> name = new TextAttributeRecord<>(NAME);

}

