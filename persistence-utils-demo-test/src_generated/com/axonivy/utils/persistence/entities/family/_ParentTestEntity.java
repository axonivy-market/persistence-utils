package com.axonivy.utils.persistence.entities.family;

import com.axonivy.utils.persistence.beans._GenericIdEntity;
import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.SortableAttributeRecord;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.family.ParentTestEntity}
 **/
@StaticMetamodel(ParentTestEntity.class)
public interface _ParentTestEntity extends _GenericIdEntity {

	
	/**
	 * @see #name
	 **/
	String NAME = "name";
	
	/**
	 * @see #grandParent
	 **/
	String GRAND_PARENT = "grandParent";

	
	/**
	 * Static metamodel for attribute {@link ParentTestEntity#name}
	 **/
	TextAttribute<ParentTestEntity> name = new TextAttributeRecord<>(NAME);
	
	/**
	 * Static metamodel for attribute {@link ParentTestEntity#grandParent}
	 **/
	SortableAttribute<ParentTestEntity> grandParent = new SortableAttributeRecord<>(GRAND_PARENT);

}

