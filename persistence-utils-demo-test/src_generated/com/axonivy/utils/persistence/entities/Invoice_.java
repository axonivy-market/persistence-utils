package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans.GenericIdEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.entities.Invoice}
 **/
@StaticMetamodel(Invoice.class)
public abstract class Invoice_ extends GenericIdEntity_ {

	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #positions
	 **/
	public static final String POSITIONS = "positions";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.entities.Invoice}
	 **/
	public static volatile EntityType<Invoice> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Invoice#name}
	 **/
	public static volatile SingularAttribute<Invoice, String> name;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.entities.Invoice#positions}
	 **/
	public static volatile ListAttribute<Invoice, InvoicePosition> positions;

}

