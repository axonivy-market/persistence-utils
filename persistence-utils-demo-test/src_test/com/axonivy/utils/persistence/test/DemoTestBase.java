package com.axonivy.utils.persistence.test;

import com.axonivy.utils.persistence.demo.tool.test.service.TestService;

public class DemoTestBase extends IvyTestBase {

	/**
	 * Prepare the test environment.
	 *
	 */
	public void prepareTestDataAndMocking(boolean clean) {
		TestService.prepareTestDataAndIvy(clean, true);
	}

}
