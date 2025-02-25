package com.axonivy.utils.persistence.test.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import javax.persistence.PersistenceException;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.Constants;
import com.axonivy.utils.persistence.dao.CriteriaQueryGenericContext;
import com.axonivy.utils.persistence.daos.ConstrainedDAO;
import com.axonivy.utils.persistence.demo.daos.PersonDAO;
import com.axonivy.utils.persistence.demo.entities.Person;
import com.axonivy.utils.persistence.demo.entities.Person_;
import com.axonivy.utils.persistence.entities.Constrained;
import com.axonivy.utils.persistence.test.DemoTestBase;
import com.axonivy.utils.persistence.test.service.LogService;
import com.axonivy.utils.persistence.test.service.LogService.LoggerLevel;

import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class GenericDAOTest extends DemoTestBase {

	private class ConstrainedHolder {
		public Constrained constrained;
	}

	@Test
	public void testAPS148() {
		ConstrainedDAO dao = ConstrainedDAO.getInstance();

		Constrained entity1 = new Constrained();
		entity1.setName("Test");
		entity1 = dao.save(entity1);

		ConstrainedHolder holder = new ConstrainedHolder();
		holder.constrained = new Constrained();
		holder.constrained.setName("Test");

		assertThrows(PersistenceException.class, () -> {
			holder.constrained = dao.save(holder.constrained);
			fail("Expected exception");
		});

		Constrained entity3 = new Constrained();
		entity3.setName("Test3");
		entity3 = dao.save(entity3);

		LOG.info("Saved Entity Ids: {0} {1} {2}", entity1.getId(), holder.constrained.getId(), entity3.getId());
	}

	@Test
	public void testForceSingle() {
		// Dummy DAO which uses force of single result. 
		class TestDAO extends PersonDAO {
			public TestDAO() {
				super();
			}

			public Collection<TestEntity> findThemAll() {
				try (CriteriaQueryGenericContext<Person, TestEntity> ctx = initializeQuery(getType(), TestEntity.class)) {
					var idEx = getExpression(null, ctx.r, Person_.id);
					var ivyUserEx = getExpression(null, ctx.r, Person_.ivyUserName);
					ctx.q.select(ctx.c.construct(TestEntity.class, idEx, ivyUserEx));
					var results = findByCriteria(ctx);
					return results;
				}
			}

			public TestEntity findSingle() {
				return forceSingleResult(findThemAll());
			}
		}

		prepareTestDataAndMocking(true);

		LogService.get().consoleLog(Level.INFO, LoggerLevel.forPackage(Constants.class, 0, Level.INFO), LoggerLevel.HIBERNATE_SQL_STATEMENTS);

		TestDAO testDAO = new TestDAO();

		LOG.info("Find all.");
		var all = testDAO.findThemAll();
		assertThat(all).isNotNull();
		assertThat(all.size()).isGreaterThan(1);

		LOG.info("Find single.");
		var entity = testDAO.findSingle();
		assertThat(entity).isNotNull();
	}
	

	// Dummy class for testing
	public static class TestEntity {
		private String id;
		private String ivyUser;

		public TestEntity() {
			super();
		}

		public TestEntity(String id, String ivyUser) {
			this.id = id;
			this.ivyUser = ivyUser;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIvyUser() {
			return ivyUser;
		}

		public void setIvyUser(String ivyUser) {
			this.ivyUser = ivyUser;
		}
	}
}
