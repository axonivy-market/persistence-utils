package com.axonivy.utils.persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.axonivy.utils.persistence.beans.GenericIdEntity;

@Entity
public class Invoice extends GenericIdEntity {
	private static final long serialVersionUID = 1L;

	@Column
	private String name;

	@OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InvoicePosition> positions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<InvoicePosition> getPositions() {
		return positions;
	}

	public void setPositions(List<InvoicePosition> positions) {
		this.positions = positions;
	}



}
