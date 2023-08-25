package com.axonivy.utils.persistence.beans;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Formula;


/**
 * Header to use for auditing persistent entities.
 *
 * The header can be embedded into a bean and stores information about creation, modification and deletion of a bean.
 */
@Embeddable
public class Header implements Serializable {

	private static final long serialVersionUID = -6330206771644279896L;

	@Formula("0")//any value as initialization of header is accepted
	private final int HEADERINITIALIZER = 0;//JPA nulls the header instance in auditable, if all properties in header are null, we have to create a dummy property so that header stays initialized all the time http://www.coderanch.com/t/629485/ORM/databases/columns-Embedded-field-NULL-JPA

	@Column(insertable = true, updatable = false)
	private Instant createdDate;

	@Column
	private Instant modifiedDate;

	@Column
	private Instant flaggedDeletedDate;

	@Column(length = 255)
	private String createdByUserName;

	@Column(length = 255)
	private String modifiedByUserName;

	@Column(length = 255)
	private String flaggedDeletedByUserName;

	/**
	 * {@link Instant} of creation.
	 *
	 * @return date of creation.
	 */
	public Instant getCreatedDate() {
		return createdDate;
	}

	/**
	 * Set the creation date.
	 *
	 * @param createdDate creation date
	 */
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * {@link Instant} of last modification.
	 *
	 * @return last modification date
	 */
	public Instant getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * Set last modification date.
	 *
	 * @param modifiedDate last modification date
	 */
	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * {@link Instant} of deletion.
	 *
	 * @return deletion date
	 */
	public Instant getFlaggedDeletedDate() {
		return flaggedDeletedDate;
	}

	/**
	 * Set deletion date.
	 *
	 * @param flaggedDeletedDate deletion date
	 */
	public void setFlaggedDeletedDate(Instant flaggedDeletedDate) {
		this.flaggedDeletedDate = flaggedDeletedDate;
	}

	/**
	 * Name of user who created the bean.
	 *
	 * @return name of bean creator
	 */
	public String getCreatedByUserName() {
		return createdByUserName;
	}

	/**
	 * Set name of user who created the bean.
	 *
	 * @param createdByUserName name of bean creator
	 */
	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}

	/**
	 * Name of user who last modified the bean.
	 *
	 * @return name of last bean modifier
	 */
	public String getModifiedByUserName() {
		return modifiedByUserName;
	}

	/**
	 * Set name of user who last modified the bean.
	 *
	 * @param modifiedByUserName name of last bean modifier
	 */
	public void setModifiedByUserName(String modifiedByUserName) {
		this.modifiedByUserName = modifiedByUserName;
	}

	/**
	 * Name of user who deleted the bean.
	 *
	 * @return name of user who deleted the bean
	 */
	public String getFlaggedDeletedByUserName() {
		return flaggedDeletedByUserName;
	}

	/**
	 * Set name of user who deleted the bean.
	 *
	 * @param flaggedDeletedByUserName name of user who deleted the bean
	 */
	public void setFlaggedDeletedByUserName(String flaggedDeletedByUserName) {
		this.flaggedDeletedByUserName = flaggedDeletedByUserName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return " [createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", flaggedDeletedDate=" + flaggedDeletedDate
				+ ", createdByUserName=" + createdByUserName
				+ ", modifiedByUserName=" + modifiedByUserName
				+ ", flaggedDeletedByUserName=" + flaggedDeletedByUserName
				+ "]";
	}

	/**
	 * {@link Instant} of creation as {@link Date}.
	 *
	 * @return date of creation.
	 * @deprecated added only for easy conversion to old data type
	 * @see #getCreatedDate()
	 */
	public Date getCreatedDateAsDate() {
		return Objects.nonNull(this.createdDate) ? Date.from(this.createdDate) : null;
	}
	
	/**
	 * Set the creation date from {@link Date}
	 *
	 * @param createdDate creation date
	 * @deprecated added only for easy conversion to old data type
	 * @see #setCreatedDate(Instant)
	 */
	public void setCreateDateAsDate(Date createdDate) {
		this.createdDate = Objects.nonNull(createdDate) ? createdDate.toInstant() : null;
	}
	
	/**
	 * {@link Instant} of creation as {@link LocalDateTime}.
	 * 
	 * The conversion is done using the systems default ZoneOffset.
	 *
	 * @return date of creation.
	 */
	public LocalDateTime getCreatedDateAsLocalDateTime() {
		return Objects.nonNull(this.createdDate) ? LocalDateTime.ofInstant(this.createdDate, ZoneOffset.systemDefault()) : null;
	}
	
	/**
	 * Set the creation date from {@link LocalDateTime}
	 * 
	 * The conversion is done using the systems default ZoneOffset.
	 *
	 * @param createDate creation date
	 */
	public void setCreateDateAsLocalDateTime(LocalDateTime createDate) {
		this.createdDate = Objects.nonNull(createDate) ? createDate.atZone(ZoneOffset.systemDefault()).toInstant() : null;
	}
	
	/**
	 * {@link Instant} of last modification as {@link Date}.
	 *
	 * @return date of last modification.
	 * @deprecated added only for easy conversion to old data type
	 * @see #getModifiedDate
	 */
	public Date getModifiedDateAsDate() {
		return Objects.nonNull(this.modifiedDate) ? Date.from(this.modifiedDate) : null;
	}
	
	/**
	 * Set the date of last modification from {@link Date}
	 *
	 * @param modifiedDate last modification date
	 * @deprecated added only for easy conversion to old data type
	 * @see #setModifiedDate(Instant)
	 */
	public void setModifiedDateAsDate(Date modifiedDate) {
		this.modifiedDate = Objects.nonNull(modifiedDate) ? modifiedDate.toInstant() : null;
	}
	
	/**
	 * {@link Instant} of last modification as {@link LocalDateTime}.
	 * 
	 * The conversion is done using the systems default ZoneOffset.
	 *
	 * @return date of last modification.
	 */
	public LocalDateTime getModifiedDateAsLocalDateTime() {
		return Objects.nonNull(this.modifiedDate) ? LocalDateTime.ofInstant(this.modifiedDate, ZoneOffset.systemDefault()) : null;
	}
	
	/**
	 * Set the date of last modification from {@link LocalDateTime}
	 *
	 * The conversion is done using the systems default ZoneOffset.
	 * 
	 * @param modifiedDate last modification date
	 */
	public void setModifiedDateAsLocalDateTime(LocalDateTime modifiedDate) {
		this.modifiedDate = Objects.nonNull(modifiedDate) ? modifiedDate.atZone(ZoneOffset.systemDefault()).toInstant() : null;
	}
	
	/**
	 * {@link Instant} of deletion as {@link Date}.
	 *
	 * @return date of deletion
	 * @deprecated added only for easy conversion to old data type
	 * @see #getFlaggedDeletedDate
	 */
	public Date getFlaggedDeletedDateAsDate() {
		return Objects.nonNull(this.flaggedDeletedDate) ? Date.from(this.flaggedDeletedDate) : null;
	}
	
	/**
	 * Set the date of deletion from {@link Date}
	 *
	 * @param flaggedDeletedDate deletion date
	 * @deprecated added only for easy conversion to old data type
	 * @see #setFlaggedDeletedDate(Instant)
	 */
	public void setFlaggedDeletedDateAsDate(Date flaggedDeletedDate) {
		this.flaggedDeletedDate = Objects.nonNull(flaggedDeletedDate) ? flaggedDeletedDate.toInstant() : null;
	}
	
	/**
	 * {@link Instant} of deletion as {@link LocalDateTime}.
	 * 
	 * The conversion is done using the systems default ZoneOffset.
	 *
	 * @return date of deletion.
	 */
	public LocalDateTime getFlaggedDeletedDateAsLocalDateTime() {
		return Objects.nonNull(this.flaggedDeletedDate) ? LocalDateTime.ofInstant(this.flaggedDeletedDate, ZoneOffset.systemDefault()) : null;
	}
	
	/**
	 * Set the date of deletion from {@link LocalDateTime}
	 *
	 * The conversion is done using the systems default ZoneOffset.
	 * 
	 * @param flaggedDeletedDate deletion date
	 */
	public void setFlaggedDeletedDateAsLocalDateTime(LocalDateTime flaggedDeletedDate) {
		this.flaggedDeletedDate = Objects.nonNull(flaggedDeletedDate) ? flaggedDeletedDate.atZone(ZoneOffset.systemDefault()).toInstant() : null;
	}
	
}
