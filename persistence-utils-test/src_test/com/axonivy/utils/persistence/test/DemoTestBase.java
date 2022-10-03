package com.axonivy.utils.persistence.test;

import com.axonivy.utils.persistence.demo.enums.Role;
import com.axonivy.utils.persistence.demo.tool.test.service.TestService;
import com.axonivy.utils.persistence.test.dao.TestDemoDAO;
import com.axonivy.utils.persistence.test.mock.Mocked;

public class DemoTestBase extends IvyTestBase {
	protected final TestDemoDAO testDemoDao = TestDemoDAO.getInstance();

	/**
	 * Create the static roles.
	 */
	public void createRoles() {
		for(Role role : Role.values()) {
			Mocked.securityContext.addSimpleRole(
					role.getIvyRoleName(), role.getIvyRoleName(), role.getIvyRoleName(),
					false, Mocked.securityContext.getTopLevelRole());
		}
	}

	/**
	 * Prepare the test environment.
	 * 
	 */
	public void prepareTestDataAndMocking(boolean clean) {
		createRoles();
		TestService.prepareTestDataAndIvy(clean, true);
	}

}
