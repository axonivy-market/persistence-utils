package com.axonivy.utils.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HistorizedPerson.class)
public abstract class HistorizedPerson_ extends com.axonivy.utils.persistence.beans.AuditableIdEntity_ {

	public static volatile SingularAttribute<HistorizedPerson, String> firstName;
	public static volatile SingularAttribute<HistorizedPerson, String> LastName;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "LastName";

}

