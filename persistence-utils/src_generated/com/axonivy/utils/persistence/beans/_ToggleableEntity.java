package com.axonivy.utils.persistence.beans;

import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.impl.SortableAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.beans.ToggleableEntity}
 **/
@StaticMetamodel(ToggleableEntity.class)
public interface _ToggleableEntity extends _AuditableEntity {

	
	/**
	 * @see #enabled
	 **/
	String ENABLED = "enabled";
	
	/**
	 * @see #expiry
	 **/
	String EXPIRY = "expiry";

	
	/**
	 * Static metamodel for attribute {@link ToggleableEntity#enabled}
	 **/
	SortableAttribute<ToggleableEntity> enabled = new SortableAttributeRecord<>(ENABLED);
	
	/**
	 * Static metamodel for attribute {@link ToggleableEntity#expiry}
	 **/
	SortableAttribute<ToggleableEntity> expiry = new SortableAttributeRecord<>(EXPIRY);

}

