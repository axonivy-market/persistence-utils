package com.axonivy.utils.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.axonivy.utils.persistence.beans.GenericIdEntity;

@Entity
public class InvoicePosition extends GenericIdEntity {
	private static final long serialVersionUID = 1L;

	@Column
	private String description;

	@ManyToOne
	private Invoice invoice;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
}
