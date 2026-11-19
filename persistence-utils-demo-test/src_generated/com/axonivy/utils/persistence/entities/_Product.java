package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans._AuditableIdEntity;
import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.SortableAttributeRecord;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.Product}
 **/
@StaticMetamodel(Product.class)
public interface _Product extends _AuditableIdEntity {

	
	/**
	 * @see #name
	 **/
	String NAME = "name";
	
	/**
	 * @see #price
	 **/
	String PRICE = "price";
	
	/**
	 * @see #producer
	 **/
	String PRODUCER = "producer";

	
	/**
	 * Static metamodel for attribute {@link Product#name}
	 **/
	TextAttribute<Product> name = new TextAttributeRecord<>(NAME);
	
	/**
	 * Static metamodel for attribute {@link Product#price}
	 **/
	SortableAttribute<Product> price = new SortableAttributeRecord<>(PRICE);
	
	/**
	 * Static metamodel for attribute {@link Product#producer}
	 **/
	SortableAttribute<Product> producer = new SortableAttributeRecord<>(PRODUCER);

}

