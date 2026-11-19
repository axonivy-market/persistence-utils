package com.axonivy.utils.persistence.entities.family;

import com.axonivy.utils.persistence.beans._GenericIdEntity;
import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.SortableAttributeRecord;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.family.ChildTestEntity}
 **/
@StaticMetamodel(ChildTestEntity.class)
public interface _ChildTestEntity extends _GenericIdEntity {

	
	/**
	 * @see #name
	 **/
	String NAME = "name";
	
	/**
	 * @see #parent
	 **/
	String PARENT = "parent";

	
	/**
	 * Static metamodel for attribute {@link ChildTestEntity#name}
	 **/
	TextAttribute<ChildTestEntity> name = new TextAttributeRecord<>(NAME);
	
	/**
	 * Static metamodel for attribute {@link ChildTestEntity#parent}
	 **/
	SortableAttribute<ChildTestEntity> parent = new SortableAttributeRecord<>(PARENT);

}

