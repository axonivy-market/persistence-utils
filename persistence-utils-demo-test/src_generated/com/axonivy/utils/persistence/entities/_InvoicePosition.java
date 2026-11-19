package com.axonivy.utils.persistence.entities;

import com.axonivy.utils.persistence.beans._GenericIdEntity;
import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.SortableAttributeRecord;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.entities.InvoicePosition}
 **/
@StaticMetamodel(InvoicePosition.class)
public interface _InvoicePosition extends _GenericIdEntity {

	
	/**
	 * @see #description
	 **/
	String DESCRIPTION = "description";
	
	/**
	 * @see #invoice
	 **/
	String INVOICE = "invoice";

	
	/**
	 * Static metamodel for attribute {@link InvoicePosition#description}
	 **/
	TextAttribute<InvoicePosition> description = new TextAttributeRecord<>(DESCRIPTION);
	
	/**
	 * Static metamodel for attribute {@link InvoicePosition#invoice}
	 **/
	SortableAttribute<InvoicePosition> invoice = new SortableAttributeRecord<>(INVOICE);

}

