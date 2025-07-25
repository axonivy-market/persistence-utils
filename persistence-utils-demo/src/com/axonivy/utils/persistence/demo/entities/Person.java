package com.axonivy.utils.persistence.demo.entities;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.axonivy.utils.persistence.beans.AuditableIdEntity;
import com.axonivy.utils.persistence.demo.enums.MaritalStatus;
import com.axonivy.utils.persistence.demo.validation.ValidIvyUserName;
import com.axonivy.utils.persistence.demo.validation.ValidUniqueIvyUserName;
import com.axonivy.utils.persistence.validation.IvyNotBlank;
import com.axonivy.utils.persistence.validation.IvyNotNull;
import com.axonivy.utils.persistence.validation.IvySizeMax;
import com.axonivy.utils.persistence.validation.groups.SaveErrorGroup;
import com.axonivy.utils.persistence.validation.groups.SaveWarnGroup;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UC_Person_ivyUserName", columnNames = {"ivyUserName"}))
@ValidIvyUserName(groups = {SaveWarnGroup.class})
@ValidUniqueIvyUserName(groups = {SaveErrorGroup.class})
public class Person extends AuditableIdEntity {
	private static final long serialVersionUID = 1L;

	@Column(length = 32)
	@IvySizeMax(max = 32, groups = {SaveErrorGroup.class})
	@IvyNotBlank(groups = {SaveWarnGroup.class})
	private String firstName;

	@Column(length = 64)
	@IvySizeMax(max = 64, groups = {SaveErrorGroup.class})
	@IvyNotBlank(groups = {SaveWarnGroup.class})
	private String lastName;

	@Column(length = 32)
	@IvySizeMax(max = 32, groups = {SaveErrorGroup.class})
	@IvyNotBlank(groups = {SaveErrorGroup.class})
	private String ivyUserName;

	private boolean syncToIvy = false;

	/**
	 * Note use of java.sql.Date which causes database date-only type and Java time 00:00:00.000.
	 * Could also use Temporal annotation.
	 */
	@Column
	@IvyNotNull(groups = {SaveWarnGroup.class})
	private Date birthdate;

	@Column(length = 32)
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;

	@Column(precision = 19, scale = 2)
	private BigDecimal salary;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_person_department"))
	private Department department;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the ivyUserName
	 */
	public String getIvyUserName() {
		return ivyUserName;
	}
	/**
	 * @param ivyUserName the ivyUserName to set
	 */
	public void setIvyUserName(String ivyUserName) {
		this.ivyUserName = ivyUserName;
	}
	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}
	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}
	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	/**
	 * @return the birthdate as a {@link java.util.Date}
	 */
	public java.util.Date getBirthdateAsJavaUtilDate() {
		return birthdate;
	}
	/**
	 * @param date the birthdate as a {@link java.util.Date}
	 */
	public void setBirthdateAsJavaUtilDate(java.util.Date date) {
		this.birthdate = date != null ? new Date(date.getTime()) : null;
	}
	/**
	 * @return the maritalStatus
	 */
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}
	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	/**
	 * @return the salary
	 */
	public BigDecimal getSalary() {
		return salary;
	}
	/**
	 * @param salary the salary to set
	 */
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	/**
	 * @return the syncToIvy
	 */
	public boolean isSyncToIvy() {
		return syncToIvy;
	}
	/**
	 * @param syncToIvy the syncToIvy to set
	 */
	public void setSyncToIvy(boolean syncToIvy) {
		this.syncToIvy = syncToIvy;
	}
}
