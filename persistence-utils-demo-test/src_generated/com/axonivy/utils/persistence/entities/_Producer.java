package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans._ToggleableIdEntity;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.Producer}
 **/
@StaticMetamodel(Producer.class)
public interface _Producer extends _ToggleableIdEntity {

	
	/**
	 * @see #name
	 **/
	String NAME = "name";
	
	/**
	 * @see #address
	 **/
	String ADDRESS = "address";
	
	/**
	 * @see #phone
	 **/
	String PHONE = "phone";
	
	/**
	 * @see #email
	 **/
	String EMAIL = "email";

	
	/**
	 * Static metamodel for attribute {@link Producer#name}
	 **/
	TextAttribute<Producer> name = new TextAttributeRecord<>(NAME);
	
	/**
	 * Static metamodel for attribute {@link Producer#address}
	 **/
	TextAttribute<Producer> address = new TextAttributeRecord<>(ADDRESS);
	
	/**
	 * Static metamodel for attribute {@link Producer#phone}
	 **/
	TextAttribute<Producer> phone = new TextAttributeRecord<>(PHONE);
	
	/**
	 * Static metamodel for attribute {@link Producer#email}
	 **/
	TextAttribute<Producer> email = new TextAttributeRecord<>(EMAIL);

}

