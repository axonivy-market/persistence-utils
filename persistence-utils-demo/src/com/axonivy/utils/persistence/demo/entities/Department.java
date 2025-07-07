package com.axonivy.utils.persistence.demo.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.axonivy.utils.persistence.beans.AuditableIdEntity;
import com.axonivy.utils.persistence.validation.IvyNotBlank;
import com.axonivy.utils.persistence.validation.IvySizeMax;
import com.axonivy.utils.persistence.validation.groups.SaveErrorGroup;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UC_Department_name", columnNames = {"name"}))
public class Department extends AuditableIdEntity {
	private static final long serialVersionUID = 1L;

	@Column(length = 64)
	@IvySizeMax(max = 32, clientId="departmentName", groups = {SaveErrorGroup.class})
	@IvyNotBlank(clientId="departmentName", groups = {SaveErrorGroup.class})
	private String name;

	@OneToMany(mappedBy = "department")
	private List<Person> persons;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


}
