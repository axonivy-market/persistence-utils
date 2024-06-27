package com.axonivy.utils.persistence.beans;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Header.class)
public abstract class Header_ {

	public static volatile SingularAttribute<Header, String> createdByUserName;
	public static volatile SingularAttribute<Header, Instant> createdDate;
	public static volatile SingularAttribute<Header, String> modifiedByUserName;
	public static volatile SingularAttribute<Header, LocalDateTime> createdDateAsLocalDateTime;
	public static volatile SingularAttribute<Header, Date> modifiedDateAsDate;
	public static volatile SingularAttribute<Header, LocalDateTime> modifiedDateAsLocalDateTime;
	public static volatile SingularAttribute<Header, Date> flaggedDeletedDateAsDate;
	public static volatile SingularAttribute<Header, String> flaggedDeletedByUserName;
	public static volatile SingularAttribute<Header, Instant> modifiedDate;
	public static volatile SingularAttribute<Header, Instant> flaggedDeletedDate;
	public static volatile SingularAttribute<Header, LocalDateTime> flaggedDeletedDateAsLocalDateTime;
	public static volatile SingularAttribute<Header, Date> createdDateAsDate;

	public static final String CREATED_BY_USER_NAME = "createdByUserName";
	public static final String CREATED_DATE = "createdDate";
	public static final String MODIFIED_BY_USER_NAME = "modifiedByUserName";
	public static final String CREATED_DATE_AS_LOCAL_DATE_TIME = "createdDateAsLocalDateTime";
	public static final String MODIFIED_DATE_AS_DATE = "modifiedDateAsDate";
	public static final String MODIFIED_DATE_AS_LOCAL_DATE_TIME = "modifiedDateAsLocalDateTime";
	public static final String FLAGGED_DELETED_DATE_AS_DATE = "flaggedDeletedDateAsDate";
	public static final String FLAGGED_DELETED_BY_USER_NAME = "flaggedDeletedByUserName";
	public static final String MODIFIED_DATE = "modifiedDate";
	public static final String FLAGGED_DELETED_DATE = "flaggedDeletedDate";
	public static final String FLAGGED_DELETED_DATE_AS_LOCAL_DATE_TIME = "flaggedDeletedDateAsLocalDateTime";
	public static final String CREATED_DATE_AS_DATE = "createdDateAsDate";

}

