package com.axonivy.utils.persistence.beans;

import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.impl.SortableAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.beans.VersionableEntity}
 **/
@StaticMetamodel(VersionableEntity.class)
public interface _VersionableEntity extends _GenericEntity {

	
	/**
	 * @see #version
	 **/
	String VERSION = "version";

	
	/**
	 * Static metamodel for attribute {@link VersionableEntity#version}
	 **/
	SortableAttribute<VersionableEntity> version = new SortableAttributeRecord<>(VERSION);

}

