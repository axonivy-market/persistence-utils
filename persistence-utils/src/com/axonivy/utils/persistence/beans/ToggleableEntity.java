package com.axonivy.utils.persistence.beans;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Base for all entities which can be enabled and disabled.
 *
 */
@MappedSuperclass
public abstract class ToggleableEntity<ID extends Serializable> extends AuditableEntity<ID> {

	private static final long serialVersionUID = 5872211233738039349L;

	@Column(nullable = false)
	private boolean enabled;

	@Column
	private Instant expiry;

	
	/**
	 * default constructor
	 */
	public ToggleableEntity() {
		enabled = true;
	}

	/**
	 * Should not be used for querying if toggleable is really allowed to be used, there is also a expiry date to be considered, thats why you should usually use isActive.
	 *
	 * @return true if enabled flag is set to true
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the checks if is enabled.
	 *
	 * @param isEnabled the new checks if is enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the expiry date.
	 *
	 * @return the expiry date
	 */
	public Instant getExpiry() {
		return expiry;
	}

	/**
	 * Sets the expire date.
	 *
	 * @param expiryDate the new expire date
	 */
	public void setExpiry(Instant expiryDate) {
		this.expiry = expiryDate;
	}

	/**
	 * Checks if is expired.
	 *
	 * @return true if expiryDate is set and is before
	 */
	public boolean isExpired() {
		Instant now = Instant.now();
		return expiry != null && expiry.compareTo(now) < 0;
	}

	/**
	 * Combines the active state and the expire date.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return isEnabled() && !isExpired();
	}
	
	/**
	 * Gets the expiry as {@link Date}.
	 *
	 * @return expiry date
	 * @deprecated added only for easy conversion to old data type
	 * @see #getFlaggedDeletedDate
	 */
	public Date getExpiryAsDate() {
		return Date.from(this.expiry);
	}
	
	/**
	 * Sets the expiry from {@link Date}
	 *
	 * @param expiry expiry date
	 * @deprecated added only for easy conversion to old data type
	 * @see #setFlaggedDeletedDate(Instant)
	 */
	public void setExpiryAsDate(Date expiry) {
		if(Objects.nonNull(expiry)) {
			this.expiry = expiry.toInstant();
		} else {
			this.expiry = null;
		}
	}
	
	/**
	 * Gets the expiry as {@link LocalDateTime}.
	 * 
	 * The conversion is done using the systems default ZoneOffset.
	 *
	 * @return expiry date.
	 */
	public LocalDateTime getExpiryAsLocalDateTime() {
		return LocalDateTime.ofInstant(this.expiry, ZoneOffset.systemDefault());
	}
	
	/**
	 * Sets the expiry from {@link LocalDateTime}
	 *
	 * The conversion is done using the systems default ZoneOffset.
	 * 
	 * @param expiry expiry date
	 */
	public void setExpiryAsLocalDateTime(LocalDateTime expiry) {
		if(Objects.nonNull(expiry)) {
			this.expiry = expiry.atZone(ZoneOffset.systemDefault()).toInstant();
		} else {
			this.expiry = null;
		}
	}

}
