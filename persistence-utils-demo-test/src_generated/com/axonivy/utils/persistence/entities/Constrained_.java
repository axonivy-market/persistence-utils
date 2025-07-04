package com.axonivy.utils.persistence.entities;

import java.util.UUID;
import javax.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Constrained.class)
public abstract class Constrained_ extends com.axonivy.utils.persistence.beans.GenericEntity_ {

	public static volatile SingularAttribute<Constrained, String> name;
	public static volatile SingularAttribute<Constrained, UUID> id;

	public static final String NAME = "name";
	public static final String ID = "id";

}

