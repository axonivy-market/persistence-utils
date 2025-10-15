package com.axonivy.utils.persistence.demo.ui;

import java.util.ArrayList;
import java.util.List;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;

import org.apache.commons.lang.StringUtils;
import org.primefaces.PrimeFaces;

import com.axonivy.utils.persistence.dao.QuerySettings;
import com.axonivy.utils.persistence.dao.markers.AuditableMarker;
import com.axonivy.utils.persistence.demo.Logger;
import com.axonivy.utils.persistence.demo.daos.DepartmentDAO;
import com.axonivy.utils.persistence.demo.entities.Department;
import com.axonivy.utils.persistence.demo.service.ValidationService;
import com.axonivy.utils.persistence.validation.groups.SaveErrorGroup;
import com.axonivy.utils.persistence.validation.groups.SaveWarnGroup;
import com.axonivy.utils.persistence.demo.entities.Department_;


public class DepartmentSearchCtrl {
	private static final Logger LOG = Logger.getLogger(DepartmentSearchCtrl.class);
	private List<Department> departments = new ArrayList<>();
	private Department department = new Department();
	private Severity severity = null;

	public DepartmentSearchCtrl() {
		refreshDepartments();
	}

	protected void refreshDepartments() {
		try {
			List<Department> result = DepartmentDAO.getInstance()
					.findAll(new QuerySettings<Department>()
							.withMarkers(AuditableMarker.ALL)
							.withOrderAttributes(Department_.name));
			departments = (result != null) ? result : new ArrayList<>();
		} catch (Exception e) {
			LOG.error("Error when refreshing Departments", e);
			departments = new ArrayList<>();
		}
	}

	/**
	 * @return the departments
	 */
	public List<Department> getDepartments() {
		return departments;
	}

	/**
	 * @return the department
	 */
	public Department getDepartment() {
		if (department == null) {
			department = new Department();
		}
		return department;
	}

	public void setActiveDepartment(String departmentId) {
		if (StringUtils.isEmpty(departmentId)) {
			department = new Department();
		}
		else {
			Department found = DepartmentDAO.getInstance().findById(departmentId);
			department = (found != null) ? found : new Department();
		}
	}

	public void validateDepartmentForSave(boolean secondSave) {
		if(secondSave) {
			severity = ValidationService.validate2FacesMessages(department, true, SaveErrorGroup.class);
		}
		else {
			severity = ValidationService.validate2FacesMessages(department, true, SaveErrorGroup.class, SaveWarnGroup.class);
		}
	}

	public boolean isSaveViolationMaxWarning() {
		return severity != null && severity.getOrdinal() < FacesMessage.SEVERITY_ERROR.getOrdinal();
	}

	public boolean isNoSaveViolation() {
		return severity == null;
	}

	public Severity resetSaveViolation() {
		Severity help = severity;
		severity = null;
		return help;
	}

	public void saveDepartment() {
		DepartmentDAO.getInstance().save(department);
		refreshDepartments();
	}

	public void deleteDepartment(String departmentId) {
		Department toDelete = null;
		if (StringUtils.isNotEmpty(departmentId)) {
			toDelete = DepartmentDAO.getInstance().findById(departmentId);
			if(toDelete != null) {
				LOG.info("Deleting department ''{0}'' (id: {2})", toDelete.getName(), departmentId);
				DepartmentDAO.getInstance().delete(toDelete);
				refreshDepartments();
			}
		}

		if(toDelete == null) {
			LOG.error("Could not find and delete department with id ''{0}''", departmentId);
		}
	}

	public void hideDepartmentDialog() {
		PrimeFaces.current().executeScript("PF('departmentDialog').hide()");
		PrimeFaces.current().ajax().update("mainForm");
	}
}
