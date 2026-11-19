package com.axonivy.utils.persistence.entities;

import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.impl.SortableAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.Car}
 **/
@StaticMetamodel(Car.class)
public interface _Car extends _Vehicle {

	
	/**
	 * @see #fuel
	 **/
	String FUEL = "fuel";

	
	/**
	 * Static metamodel for attribute {@link Car#fuel}
	 **/
	SortableAttribute<Car> fuel = new SortableAttributeRecord<>(FUEL);

}

