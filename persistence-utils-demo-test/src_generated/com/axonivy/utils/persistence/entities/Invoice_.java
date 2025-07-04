package com.axonivy.utils.persistence.entities;

import javax.annotation.Generated;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Invoice.class)
public abstract class Invoice_ extends com.axonivy.utils.persistence.beans.GenericIdEntity_ {

	public static volatile SingularAttribute<Invoice, String> name;
	public static volatile ListAttribute<Invoice, InvoicePosition> positions;

	public static final String NAME = "name";
	public static final String POSITIONS = "positions";

}

