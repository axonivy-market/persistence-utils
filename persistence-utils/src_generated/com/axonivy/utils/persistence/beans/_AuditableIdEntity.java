package com.axonivy.utils.persistence.beans;

import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.beans.AuditableIdEntity}
 **/
@StaticMetamodel(AuditableIdEntity.class)
public interface _AuditableIdEntity extends _AuditableEntity {

	
	/**
	 * @see #id
	 **/
	String ID = "id";

	
	/**
	 * Static metamodel for attribute {@link AuditableIdEntity#id}
	 **/
	TextAttribute<AuditableIdEntity> id = new TextAttributeRecord<>(ID);

}

