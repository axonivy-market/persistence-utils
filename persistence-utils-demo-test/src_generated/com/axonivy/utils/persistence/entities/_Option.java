package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans._ToggleableIdEntity;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.Option}
 **/
@StaticMetamodel(Option.class)
public interface _Option extends _ToggleableIdEntity {

	
	/**
	 * @see #name
	 **/
	String NAME = "name";
	
	/**
	 * @see #value
	 **/
	String VALUE = "value";

	
	/**
	 * Static metamodel for attribute {@link Option#name}
	 **/
	TextAttribute<Option> name = new TextAttributeRecord<>(NAME);
	
	/**
	 * Static metamodel for attribute {@link Option#value}
	 **/
	TextAttribute<Option> value = new TextAttributeRecord<>(VALUE);

}

