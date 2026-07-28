package com.axonivy.utils.persistence.demo.ui;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import com.axonivy.utils.persistence.demo.enums.MaritalStatus;

@Named(value = "enums")
@ApplicationScoped
public class EnumsBean {

	public MaritalStatus[] getMaritalStatus() {
		return MaritalStatus.values();
	}
}
