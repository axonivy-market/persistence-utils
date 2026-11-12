package com.axonivy.utils.persistence.entities.family;

import com.axonivy.utils.persistence.beans._GenericIdEntity;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.family.GrandParentTestEntity}
 **/
@StaticMetamodel(GrandParentTestEntity.class)
public interface _GrandParentTestEntity extends _GenericIdEntity {

	
	/**
	 * @see #name
	 **/
	String NAME = "name";

	
	/**
	 * Static metamodel for attribute {@link GrandParentTestEntity#name}
	 **/
	TextAttribute<GrandParentTestEntity> name = new TextAttributeRecord<>(NAME);

}

