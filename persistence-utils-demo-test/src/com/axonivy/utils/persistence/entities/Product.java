package com.axonivy.utils.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.axonivy.utils.persistence.beans.AuditableIdEntity;

@Entity
@Table(name = "product")
public class Product extends AuditableIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private double price;

	@ManyToOne
	private Producer producer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

}
