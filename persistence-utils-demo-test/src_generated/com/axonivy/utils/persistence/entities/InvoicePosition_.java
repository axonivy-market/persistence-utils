package com.axonivy.utils.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InvoicePosition.class)
public abstract class InvoicePosition_ extends com.axonivy.utils.persistence.beans.GenericIdEntity_ {

	public static volatile SingularAttribute<InvoicePosition, String> description;
	public static volatile SingularAttribute<InvoicePosition, Invoice> invoice;

	public static final String DESCRIPTION = "description";
	public static final String INVOICE = "invoice";

}

