package com.axonivy.utils.persistence.test;

import com.axonivy.utils.persistence.demo.tool.test.service.TestService;
import com.axonivy.utils.persistence.test.dao.TestDemoDAO;

public class DemoTestBase extends IvyTestBase {
	protected final TestDemoDAO testDemoDao = TestDemoDAO.getInstance();

	/**
	 * Prepare the test environment.
	 *
	 */
	public void prepareTestDataAndMocking(boolean clean) {
		TestService.prepareTestDataAndIvy(clean, true);
	}

}
