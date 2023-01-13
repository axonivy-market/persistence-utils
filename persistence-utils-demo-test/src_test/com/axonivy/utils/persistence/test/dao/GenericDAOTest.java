package com.axonivy.utils.persistence.test.dao;

import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.daos.ConstrainedDAO;
import com.axonivy.utils.persistence.entities.Constrained;
import com.axonivy.utils.persistence.test.DemoTestBase;

import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class GenericDAOTest extends DemoTestBase {

	@Test
	public void testBasicFunctionality() {
		ConstrainedDAO dao = ConstrainedDAO.getInstance();
		
		Constrained entity = new Constrained();
		entity = dao.save(entity);
		
		LOG.info("Saved Entity Id: {0}", entity.getId());
	}
}
