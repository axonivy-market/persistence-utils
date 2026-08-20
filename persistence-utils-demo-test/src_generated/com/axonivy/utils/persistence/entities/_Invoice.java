package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans._GenericIdEntity;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.Invoice}
 **/
@StaticMetamodel(Invoice.class)
public interface _Invoice extends _GenericIdEntity {

	
	/**
	 * @see #name
	 **/
	String NAME = "name";

	
	/**
	 * Static metamodel for attribute {@link Invoice#name}
	 **/
	TextAttribute<Invoice> name = new TextAttributeRecord<>(NAME);

}

