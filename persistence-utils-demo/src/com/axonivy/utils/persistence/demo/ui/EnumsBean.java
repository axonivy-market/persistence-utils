package com.axonivy.utils.persistence.demo.ui;

import com.axonivy.utils.persistence.demo.enums.MaritalStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named("enums")
@ApplicationScoped
public class EnumsBean {

	public MaritalStatus[] getMaritalStatus() {
		return MaritalStatus.values();
	}
}
