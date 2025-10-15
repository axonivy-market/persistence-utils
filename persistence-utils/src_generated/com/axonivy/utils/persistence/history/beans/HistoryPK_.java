package com.axonivy.utils.persistence.history.beans;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.sql.Timestamp;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.history.beans.HistoryPK}
 **/
@StaticMetamodel(HistoryPK.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class HistoryPK_ {

	
	/**
	 * @see #entityType
	 **/
	public static final String ENTITY_TYPE = "entityType";
	
	/**
	 * @see #timestamp
	 **/
	public static final String TIMESTAMP = "timestamp";
	
	/**
	 * @see #entityId
	 **/
	public static final String ENTITY_ID = "entityId";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.history.beans.HistoryPK}
	 **/
	public static volatile EmbeddableType<HistoryPK> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.history.beans.HistoryPK#entityType}
	 **/
	public static volatile SingularAttribute<HistoryPK, String> entityType;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.history.beans.HistoryPK#timestamp}
	 **/
	public static volatile SingularAttribute<HistoryPK, Timestamp> timestamp;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.history.beans.HistoryPK#entityId}
	 **/
	public static volatile SingularAttribute<HistoryPK, String> entityId;

}

