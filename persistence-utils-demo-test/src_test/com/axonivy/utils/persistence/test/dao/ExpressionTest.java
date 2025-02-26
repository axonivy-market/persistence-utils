package com.axonivy.utils.persistence.test.dao;

import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.dao.ExpressionMap;
import com.axonivy.utils.persistence.dao.GenericIdEntityDAO;
import com.axonivy.utils.persistence.daos.BaseDAO;
import com.axonivy.utils.persistence.entities.family.ChildTestEntity;
import com.axonivy.utils.persistence.entities.family.ChildTestEntity_;
import com.axonivy.utils.persistence.entities.family.GrandParentTestEntity_;
import com.axonivy.utils.persistence.entities.family.ParentTestEntity_;

import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class ExpressionTest {
	private ChildTestEntityDAO dao = new ChildTestEntityDAO();

	@Test
	public void testIssue82() {
		dao.testIssue82();
	}

	protected class ChildTestEntityDAO extends GenericIdEntityDAO<ChildTestEntity_, ChildTestEntity> implements BaseDAO {

		@Override
		protected Class<ChildTestEntity> getType() {
			return ChildTestEntity.class;
		}

		public void testIssue82() {
			try (var ctx = initializeQuery()) {
				var map = ExpressionMap.createNewExpressionMap();
				getExpressionGeneral(map, ctx.r, ChildTestEntity_.parent);
				getExpressionGeneral(map, ctx.r, ChildTestEntity_.parent, ParentTestEntity_.grandParent, GrandParentTestEntity_.name);
			}
		}
	}
}

