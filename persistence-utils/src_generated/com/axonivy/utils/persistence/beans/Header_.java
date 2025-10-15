package com.axonivy.utils.persistence.beans;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.beans.Header}
 **/
@StaticMetamodel(Header.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Header_ {

	
	/**
	 * @see #HEADERINITIALIZER
	 **/
	public static final String _HEADERINITIALIZER = "HEADERINITIALIZER";
	
	/**
	 * @see #createdDate
	 **/
	public static final String CREATED_DATE = "createdDate";
	
	/**
	 * @see #modifiedDate
	 **/
	public static final String MODIFIED_DATE = "modifiedDate";
	
	/**
	 * @see #flaggedDeletedDate
	 **/
	public static final String FLAGGED_DELETED_DATE = "flaggedDeletedDate";
	
	/**
	 * @see #createdByUserName
	 **/
	public static final String CREATED_BY_USER_NAME = "createdByUserName";
	
	/**
	 * @see #modifiedByUserName
	 **/
	public static final String MODIFIED_BY_USER_NAME = "modifiedByUserName";
	
	/**
	 * @see #flaggedDeletedByUserName
	 **/
	public static final String FLAGGED_DELETED_BY_USER_NAME = "flaggedDeletedByUserName";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.beans.Header}
	 **/
	public static volatile EmbeddableType<Header> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.Header#HEADERINITIALIZER}
	 **/
	public static volatile SingularAttribute<Header, Integer> HEADERINITIALIZER;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.Header#createdDate}
	 **/
	public static volatile SingularAttribute<Header, Instant> createdDate;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.Header#modifiedDate}
	 **/
	public static volatile SingularAttribute<Header, Instant> modifiedDate;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.Header#flaggedDeletedDate}
	 **/
	public static volatile SingularAttribute<Header, Instant> flaggedDeletedDate;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.Header#createdByUserName}
	 **/
	public static volatile SingularAttribute<Header, String> createdByUserName;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.Header#modifiedByUserName}
	 **/
	public static volatile SingularAttribute<Header, String> modifiedByUserName;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.beans.Header#flaggedDeletedByUserName}
	 **/
	public static volatile SingularAttribute<Header, String> flaggedDeletedByUserName;

}

