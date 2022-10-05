package com.axonivy.utils.persistence.test.dao;

import com.axonivy.utils.persistence.demo.daos.BaseDAO;
import com.axonivy.utils.persistence.demo.tool.test.dao.DemoDAO;


public class TestDemoDAO extends DemoDAO implements BaseDAO {
	private static final TestDemoDAO instance = new TestDemoDAO();

	private TestDemoDAO() {
	}

	public static TestDemoDAO getInstance() {
		return instance;
	}
}
