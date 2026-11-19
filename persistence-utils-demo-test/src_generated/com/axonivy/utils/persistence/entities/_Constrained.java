package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans._GenericEntity;
import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.SortableAttributeRecord;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.Constrained}
 **/
@StaticMetamodel(Constrained.class)
public interface _Constrained extends _GenericEntity {

	
	/**
	 * @see #id
	 **/
	String ID = "id";
	
	/**
	 * @see #name
	 **/
	String NAME = "name";

	
	/**
	 * Static metamodel for attribute {@link Constrained#id}
	 **/
	SortableAttribute<Constrained> id = new SortableAttributeRecord<>(ID);
	
	/**
	 * Static metamodel for attribute {@link Constrained#name}
	 **/
	TextAttribute<Constrained> name = new TextAttributeRecord<>(NAME);

}

