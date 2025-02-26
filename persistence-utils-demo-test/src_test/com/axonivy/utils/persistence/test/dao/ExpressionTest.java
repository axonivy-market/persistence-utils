package com.axonivy.utils.persistence.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.Tuple;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.dao.ExpressionMap;
import com.axonivy.utils.persistence.dao.GenericIdEntityDAO;
import com.axonivy.utils.persistence.dao.QuerySettings;
import com.axonivy.utils.persistence.daos.BaseDAO;
import com.axonivy.utils.persistence.demo.daos.DepartmentDAO;
import com.axonivy.utils.persistence.demo.daos.PersonDAO;
import com.axonivy.utils.persistence.demo.daos.markers.AccessMarker;
import com.axonivy.utils.persistence.demo.entities.Department;
import com.axonivy.utils.persistence.demo.entities.Person;
import com.axonivy.utils.persistence.demo.enums.PersonSearchField;
import com.axonivy.utils.persistence.entities.family.ChildTestEntity;
import com.axonivy.utils.persistence.entities.family.ChildTestEntity_;
import com.axonivy.utils.persistence.entities.family.GrandParentTestEntity_;
import com.axonivy.utils.persistence.entities.family.ParentTestEntity_;
import com.axonivy.utils.persistence.search.SearchFilter;
import com.axonivy.utils.persistence.test.service.LogService;
import com.axonivy.utils.persistence.test.service.LogService.LoggerLevel;

import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class ExpressionTest {
	private ChildTestEntityDAO dao = new ChildTestEntityDAO();

	/**
	 * Test fix of Issue 82.
	 * 
	 * <p>
	 * 	Before this fix, objects at the end of a getExpression list where always cached as a {@link Path} in the {@link ExpressionMap}.
	 * 	This caused a {@link ClassCastException}, when fetching associations of the object because they needed a {@link Join}.
	 *  After this fix, associations will always be fetched with {@link Join} and simple attributes will be fetched as {@link Path}.
	 * </p>
	 */
	@Test
	public void testIssue82_1() {
		dao.testIssue82();
	}

	/**
	 * Text fix of Issue 82.
	 * 
	 * <p>
	 * 	The first fix introduced another bug and joined objects with {@link JoinType#INNER} instead of
	 *  {@link JoinType#LEFT} which was the original implementation and is what is typically needed.
	 * </p>
	 */
	@Test
	public void test2Issue82_2() {
		var depDao = DepartmentDAO.getInstance();
		var persDao = PersonDAO.getInstance();

		// Create a department.
		var department = new Department();
		department.setName("Test Department");
		department = depDao.save(department);

		// Create a person without a department.
		var person1 = new Person();
		person1.setFirstName("Person");
		person1.setLastName("One");
		persDao.save(person1);

		// Create a person with a department.
		var person2 = new Person();
		person2.setFirstName("Person");
		person2.setLastName("Two");
		person2.setDepartment(department);
		persDao.save(person2);

		LogService.get().consoleLog(LoggerLevel.HIBERNATE_FULL);
		var persons = persDao.findBySearchFilter(SearchFilter.create()
				.add(PersonSearchField.LAST_NAME)
				.add(PersonSearchField.DEPARTMENT_NAME),
				new QuerySettings<Person>().withMarkers(AccessMarker.ALL));
		LogService.get().consoleLog(LoggerLevel.forObject(this, Level.WARN));

		// Find also person without department (because of left join).
		assertThat(persons).hasSize(2);

		assertThat(persons.stream().map(Tuple::toArray)).containsExactlyInAnyOrder(
				new Object[] {"One", null},
				new Object[] {"Two", "Test Department"}
				);
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

