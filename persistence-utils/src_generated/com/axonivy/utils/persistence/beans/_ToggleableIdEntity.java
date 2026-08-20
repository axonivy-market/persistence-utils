package com.axonivy.utils.persistence.beans;

import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.beans.ToggleableIdEntity}
 **/
@StaticMetamodel(ToggleableIdEntity.class)
public interface _ToggleableIdEntity extends _ToggleableEntity {

	
	/**
	 * @see #id
	 **/
	String ID = "id";

	
	/**
	 * Static metamodel for attribute {@link ToggleableIdEntity#id}
	 **/
	TextAttribute<ToggleableIdEntity> id = new TextAttributeRecord<>(ID);

}

