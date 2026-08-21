package com.axonivy.utils.persistence.beans;

import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.beans.GenericIdEntity}
 **/
@StaticMetamodel(GenericIdEntity.class)
public interface _GenericIdEntity extends _VersionableEntity {

	
	/**
	 * @see #id
	 **/
	String ID = "id";

	
	/**
	 * Static metamodel for attribute {@link GenericIdEntity#id}
	 **/
	TextAttribute<GenericIdEntity> id = new TextAttributeRecord<>(ID);

}

