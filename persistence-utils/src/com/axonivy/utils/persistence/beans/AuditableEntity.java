package com.axonivy.utils.persistence.beans;

import java.io.Serializable;
import java.time.Instant;
import java.util.Comparator;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;

import com.axonivy.utils.persistence.annotations.CascadeCopy;

/**
 * Auditable entity class which allows header manipulation
 * 
 */
@MappedSuperclass
public abstract class AuditableEntity<ID extends Serializable> extends VersionableEntity<ID> {

	private static final long serialVersionUID = 758520274735110178L;

	@Embedded
	@CascadeCopy(ignore=true)
	private final Header header = new Header();

	@Transient
	private boolean auditingDisabled = false;

	@PrePersist
	protected void prePersist() {
		Instant now = Instant.now();
		String userName = getSessionUsername();

		if(header.getCreatedDate() == null){
			header.setCreatedDate(now);
		}

		if(header.getCreatedByUserName() == null){
			header.setCreatedByUserName(userName);
		}

		header.setModifiedDate(now);
		header.setModifiedByUserName(userName);
	}

	@PreUpdate
	protected void preUpdate() {
		if(!isAuditingDisabled()) {
			Instant now = Instant.now();
			header.setModifiedDate(now);
			header.setModifiedByUserName(getSessionUsername());
		}
	}

	/**
	 * Modify header before calling remove - set dates and user names
	 */
	@PreRemove
	public void preRemove()  {
		if(!isAuditingDisabled()) {
			Instant now = Instant.now();
			String userName = getSessionUsername();
			header.setFlaggedDeletedDate(now);
			header.setFlaggedDeletedByUserName(userName);
			header.setModifiedDate(now);
			header.setModifiedByUserName(userName);
		}
	}

	/**
	 * Modify header to undelete the bean.
	 */
	public void preUndelete() {
		Instant now = Instant.now();
		String userName = getSessionUsername();
		header.setFlaggedDeletedDate(null);
		header.setFlaggedDeletedByUserName(null);
		header.setModifiedDate(now);
		header.setModifiedByUserName(userName);
	}

	/**
	 * Checks if is deleted.
	 *
	 * @return true, if is deleted
	 */
	public boolean isDeleted() {
		return header.getFlaggedDeletedDate() != null;
	}

	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public Header getHeader() {
		return header;
	}

	/**
	 * Checks if is auditing disabled.
	 *
	 * @return true, if is auditing disabled
	 */
	public boolean isAuditingDisabled() {
		return auditingDisabled;
	}

	/**
	 * Sets the auditing disabled.
	 *
	 * @param auditingDisabled the new auditing disabled
	 */
	public void setAuditingDisabled(boolean auditingDisabled) {
		this.auditingDisabled = auditingDisabled;
	}

	/**
	 * @return Comparator comparing only  according to createdDate field
	 */
	public static <ID extends Serializable> Comparator<AuditableEntity<ID>> createCreatedOnComparator() {
		return (entity1, entity2) -> {
			Instant createdOn1 = entity1.getHeader() != null ? entity1.getHeader().getCreatedDate() : null;
			Instant createdOn2 = entity2.getHeader() != null ? entity2.getHeader().getCreatedDate() : null;

			if (createdOn1 != null) {
				return createdOn2 == null ? -1 : createdOn1.compareTo(createdOn2);
			} else {
				return createdOn2 == null ? 0 : 1;
			}
		};
	}
}
