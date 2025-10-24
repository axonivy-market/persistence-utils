package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans.GenericIdEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.InvoicePosition}
 **/
@StaticMetamodel(InvoicePosition.class)
public abstract class InvoicePosition_ extends GenericIdEntity_ {

	
	/**
	 * @see #description
	 **/
	public static final String DESCRIPTION = "description";
	
	/**
	 * @see #invoice
	 **/
	public static final String INVOICE = "invoice";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.InvoicePosition}
	 **/
	public static volatile EntityType<InvoicePosition> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.InvoicePosition#description}
	 **/
	public static volatile SingularAttribute<InvoicePosition, String> description;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.InvoicePosition#invoice}
	 **/
	public static volatile SingularAttribute<InvoicePosition, Invoice> invoice;

}

