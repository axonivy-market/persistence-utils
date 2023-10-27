package com.axonivy.utils.persistence.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.daos.InvoiceDAO;
import com.axonivy.utils.persistence.entities.Invoice;
import com.axonivy.utils.persistence.entities.InvoicePosition;

import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class Aps156Test {

	private Invoice invoice;

	@BeforeEach
	public void prepare() {
		invoice = new Invoice();
		invoice.setName("Invoice 1");

		ArrayList<InvoicePosition> positions = new ArrayList<>();

		InvoicePosition ip1 = new InvoicePosition();
		ip1.setDescription("Invoice Position 1");
		ip1.setInvoice(invoice);
		positions.add(ip1);

		invoice.setPositions(positions);

		invoice = InvoiceDAO.get().save(invoice);
	}

	@Test
	public void test1() {
		assertThat(invoice).as("Prepared test").isNotNull();

		int count = InvoiceDAO.get().countPositions(invoice.getId());

		assertThat(count).as("Found one").isEqualTo(1);
	}
}

