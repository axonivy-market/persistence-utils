package com.axonivy.utils.persistence.history.beans;

import com.axonivy.utils.persistence.beans.GenericEntity_;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.axonivy.utils.persistence.history.beans.History}
 **/
@StaticMetamodel(History.class)
public abstract class History_ extends GenericEntity_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";
	
	/**
	 * @see #userName
	 **/
	public static final String USER_NAME = "userName";
	
	/**
	 * @see #jsonData
	 **/
	public static final String JSON_DATA = "jsonData";
	
	/**
	 * @see #updateType
	 **/
	public static final String UPDATE_TYPE = "updateType";

	
	/**
	 * Static metamodel type for {@link com.axonivy.utils.persistence.history.beans.History}
	 **/
	public static volatile EntityType<History> class_;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.history.beans.History#id}
	 **/
	public static volatile SingularAttribute<History, HistoryPK> id;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.history.beans.History#userName}
	 **/
	public static volatile SingularAttribute<History, String> userName;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.history.beans.History#jsonData}
	 **/
	public static volatile SingularAttribute<History, String> jsonData;
	
	/**
	 * Static metamodel for attribute {@link com.axonivy.utils.persistence.history.beans.History#updateType}
	 **/
	public static volatile SingularAttribute<History, String> updateType;

}

