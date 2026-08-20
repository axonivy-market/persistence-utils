package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans._AuditableIdEntity;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.HistorizedPerson}
 **/
@StaticMetamodel(HistorizedPerson.class)
public interface _HistorizedPerson extends _AuditableIdEntity {

	
	/**
	 * @see #firstName
	 **/
	String FIRST_NAME = "firstName";
	
	/**
	 * @see #LastName
	 **/
	String LAST_NAME = "LastName";

	
	/**
	 * Static metamodel for attribute {@link HistorizedPerson#firstName}
	 **/
	TextAttribute<HistorizedPerson> firstName = new TextAttributeRecord<>(FIRST_NAME);
	
	/**
	 * Static metamodel for attribute {@link HistorizedPerson#LastName}
	 **/
	TextAttribute<HistorizedPerson> LastName = new TextAttributeRecord<>(LAST_NAME);

}

