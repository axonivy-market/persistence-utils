package com.axonivy.utils.persistence.history.beans;

import com.axonivy.utils.persistence.beans._GenericEntity;
import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.SortableAttributeRecord;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.history.beans.History}
 **/
@StaticMetamodel(History.class)
public interface _History extends _GenericEntity {

	
	/**
	 * @see #id
	 **/
	String ID = "id";
	
	/**
	 * @see #id_entityType
	 **/
	String ID_ENTITY_TYPE = "id.entityType";
	
	/**
	 * @see #id_timestamp
	 **/
	String ID_TIMESTAMP = "id.timestamp";
	
	/**
	 * @see #id_entityId
	 **/
	String ID_ENTITY_ID = "id.entityId";
	
	/**
	 * @see #userName
	 **/
	String USER_NAME = "userName";
	
	/**
	 * @see #jsonData
	 **/
	String JSON_DATA = "jsonData";
	
	/**
	 * @see #updateType
	 **/
	String UPDATE_TYPE = "updateType";

	
	/**
	 * Static metamodel for attribute {@link History#id}
	 **/
	SortableAttribute<History> id = new SortableAttributeRecord<>(ID);
	
	/**
	 * Static metamodel for attribute {@link History#id.entityType}
	 **/
	TextAttribute<History> id_entityType = new TextAttributeRecord<>(ID_ENTITY_TYPE);
	
	/**
	 * Static metamodel for attribute {@link History#id.timestamp}
	 **/
	SortableAttribute<History> id_timestamp = new SortableAttributeRecord<>(ID_TIMESTAMP);
	
	/**
	 * Static metamodel for attribute {@link History#id.entityId}
	 **/
	TextAttribute<History> id_entityId = new TextAttributeRecord<>(ID_ENTITY_ID);
	
	/**
	 * Static metamodel for attribute {@link History#userName}
	 **/
	TextAttribute<History> userName = new TextAttributeRecord<>(USER_NAME);
	
	/**
	 * Static metamodel for attribute {@link History#jsonData}
	 **/
	TextAttribute<History> jsonData = new TextAttributeRecord<>(JSON_DATA);
	
	/**
	 * Static metamodel for attribute {@link History#updateType}
	 **/
	TextAttribute<History> updateType = new TextAttributeRecord<>(UPDATE_TYPE);

}

