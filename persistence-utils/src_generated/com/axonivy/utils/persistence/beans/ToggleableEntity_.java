package com.axonivy.utils.persistence.beans;

import java.time.Instant;
import javax.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ToggleableEntity.class)
public abstract class ToggleableEntity_ extends com.axonivy.utils.persistence.beans.AuditableEntity_ {

	public static volatile SingularAttribute<ToggleableEntity, Instant> expiry;
	public static volatile SingularAttribute<ToggleableEntity, Boolean> enabled;

	public static final String EXPIRY = "expiry";
	public static final String ENABLED = "enabled";

}

