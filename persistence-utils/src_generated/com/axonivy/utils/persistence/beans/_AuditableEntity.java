package com.axonivy.utils.persistence.beans;

import jakarta.data.metamodel.SortableAttribute;
import jakarta.data.metamodel.StaticMetamodel;
import jakarta.data.metamodel.TextAttribute;
import jakarta.data.metamodel.impl.SortableAttributeRecord;
import jakarta.data.metamodel.impl.TextAttributeRecord;

/**
 * Jakarta Data static metamodel for {@link com.axonivy.utils.persistence.beans.AuditableEntity}
 **/
@StaticMetamodel(AuditableEntity.class)
public interface _AuditableEntity extends _VersionableEntity {

	
	/**
	 * @see #header
	 **/
	String HEADER = "header";
	
	/**
	 * @see #header_HEADERINITIALIZER
	 **/
	String HEADER__HEADERINITIALIZER = "header.HEADERINITIALIZER";
	
	/**
	 * @see #header_createdDate
	 **/
	String HEADER_CREATED_DATE = "header.createdDate";
	
	/**
	 * @see #header_modifiedDate
	 **/
	String HEADER_MODIFIED_DATE = "header.modifiedDate";
	
	/**
	 * @see #header_flaggedDeletedDate
	 **/
	String HEADER_FLAGGED_DELETED_DATE = "header.flaggedDeletedDate";
	
	/**
	 * @see #header_createdByUserName
	 **/
	String HEADER_CREATED_BY_USER_NAME = "header.createdByUserName";
	
	/**
	 * @see #header_modifiedByUserName
	 **/
	String HEADER_MODIFIED_BY_USER_NAME = "header.modifiedByUserName";
	
	/**
	 * @see #header_flaggedDeletedByUserName
	 **/
	String HEADER_FLAGGED_DELETED_BY_USER_NAME = "header.flaggedDeletedByUserName";

	
	/**
	 * Static metamodel for attribute {@link AuditableEntity#header}
	 **/
	SortableAttribute<AuditableEntity> header = new SortableAttributeRecord<>(HEADER);
	
	/**
	 * Static metamodel for attribute {@link AuditableEntity#header.HEADERINITIALIZER}
	 **/
	SortableAttribute<AuditableEntity> header_HEADERINITIALIZER = new SortableAttributeRecord<>(HEADER__HEADERINITIALIZER);
	
	/**
	 * Static metamodel for attribute {@link AuditableEntity#header.createdDate}
	 **/
	SortableAttribute<AuditableEntity> header_createdDate = new SortableAttributeRecord<>(HEADER_CREATED_DATE);
	
	/**
	 * Static metamodel for attribute {@link AuditableEntity#header.modifiedDate}
	 **/
	SortableAttribute<AuditableEntity> header_modifiedDate = new SortableAttributeRecord<>(HEADER_MODIFIED_DATE);
	
	/**
	 * Static metamodel for attribute {@link AuditableEntity#header.flaggedDeletedDate}
	 **/
	SortableAttribute<AuditableEntity> header_flaggedDeletedDate = new SortableAttributeRecord<>(HEADER_FLAGGED_DELETED_DATE);
	
	/**
	 * Static metamodel for attribute {@link AuditableEntity#header.createdByUserName}
	 **/
	TextAttribute<AuditableEntity> header_createdByUserName = new TextAttributeRecord<>(HEADER_CREATED_BY_USER_NAME);
	
	/**
	 * Static metamodel for attribute {@link AuditableEntity#header.modifiedByUserName}
	 **/
	TextAttribute<AuditableEntity> header_modifiedByUserName = new TextAttributeRecord<>(HEADER_MODIFIED_BY_USER_NAME);
	
	/**
	 * Static metamodel for attribute {@link AuditableEntity#header.flaggedDeletedByUserName}
	 **/
	TextAttribute<AuditableEntity> header_flaggedDeletedByUserName = new TextAttributeRecord<>(HEADER_FLAGGED_DELETED_BY_USER_NAME);

}

