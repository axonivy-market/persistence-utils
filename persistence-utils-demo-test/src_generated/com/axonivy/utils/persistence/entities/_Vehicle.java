package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans._AuditableIdEntity;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.Vehicle}
 **/
@StaticMetamodel(Vehicle.class)
public interface _Vehicle extends _AuditableIdEntity {

	
	/**
	 * @see #name
	 **/
	String NAME = "name";
	
	/**
	 * @see #color
	 **/
	String COLOR = "color";

	
	/**
	 * Static metamodel for attribute {@link Vehicle#name}
	 **/
	TextAttribute<Vehicle> name = new TextAttributeRecord<>(NAME);
	
	/**
	 * Static metamodel for attribute {@link Vehicle#color}
	 **/
	TextAttribute<Vehicle> color = new TextAttributeRecord<>(COLOR);

}

