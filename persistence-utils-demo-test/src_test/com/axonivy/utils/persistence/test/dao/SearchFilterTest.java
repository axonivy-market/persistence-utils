package com.axonivy.utils.persistence.test.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.Constants;
import com.axonivy.utils.persistence.demo.daos.PersonDAO;
import com.axonivy.utils.persistence.demo.enums.MaritalStatus;
import com.axonivy.utils.persistence.demo.enums.PersonSearchField;
import com.axonivy.utils.persistence.enums.HasCmsName;
import com.axonivy.utils.persistence.helper.SessionLocale;
import com.axonivy.utils.persistence.search.SearchFilter;
import com.axonivy.utils.persistence.test.DemoTestBase;
import com.axonivy.utils.persistence.test.service.LogService;
import com.axonivy.utils.persistence.test.service.LogService.LoggerLevel;

import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.environment.IvyTest;


@IvyTest
public class SearchFilterTest extends DemoTestBase {
	private static final PersonDAO personDAO = PersonDAO.getInstance();

	@BeforeEach
	public void prepare(AppFixture fixture) throws Exception {
		prepareTestDataAndMocking(true);
	}

	@Test
	public void testSearch() {
		var filter = new SearchFilter();

		filter
		.add(PersonSearchField.ID)
		.add(PersonSearchField.IVY_USER_NAME)
		.add(PersonSearchField.FIRST_NAME)
		.add(PersonSearchField.LAST_NAME)
		.add(PersonSearchField.BIRTHDATE)
		.add(PersonSearchField.MARITAL_STATUS)
		.add(PersonSearchField.SALARY)
		.add(PersonSearchField.DEPARTMENT_NAME);

		filter.addSort(PersonSearchField.LAST_NAME, true).addSort(PersonSearchField.FIRST_NAME, true);

		var persons = personDAO.findBySearchFilter(filter);

		logTuples("Persons", persons, -30);

		assertThat(persons).as("Find tuples").hasSizeGreaterThan(300);
	}

	@Test
	public void testSearchIndividual() {
		LogService.get().consoleLog(Level.INFO, LoggerLevel.forPackage(Constants.class, 0, Level.INFO), LoggerLevel.HIBERNATE_SQL_STATEMENTS);

		var filter = new SearchFilter();

		filter.add(PersonSearchField.ID)
		.add(PersonSearchField.IVY_USER_NAME, "er");

		var persons = personDAO.findBySearchFilter(filter);

		logTuples("Persons", persons, -30);

		assertThat(persons).as("Find tuples").hasSizeGreaterThan(100);
	}

	private List<String> cmsName(Collection<Tuple> tuples, int index) {
		return tuples.stream()
				.map(t -> ((HasCmsName)t.get(index)).getCmsName())
				.toList();
	}

	@Test
	public void testSearchEnum(AppFixture fixture) {
		LogService.get().consoleLog(Level.INFO, LoggerLevel.forPackage(Constants.class, 0, Level.INFO), LoggerLevel.HIBERNATE_SQL_STATEMENTS);

		LOG.info("Test ascending sort of enum");
		var filter = new SearchFilter()
				.add(PersonSearchField.ID)
				.add(PersonSearchField.MARITAL_STATUS, List.of(MaritalStatus.MARRIED, MaritalStatus.PARTNERSHIP));


		LOG.info("Test in German");
		try (SessionLocale sessionLocale = SessionLocale.get(Locale.GERMAN)) {
			var tuples = personDAO.findBySearchFilter(filter);

			Set<Object> set = tuples.stream().map(t -> t.get(1)).collect(Collectors.toSet());
			assertThat(set).containsOnly(MaritalStatus.MARRIED, MaritalStatus.PARTNERSHIP);
		}
	}

	@Test
	public void testSearchEnumContains(AppFixture fixture) {
		LogService.get().consoleLog(Level.INFO, LoggerLevel.forPackage(Constants.class, 0, Level.INFO), LoggerLevel.HIBERNATE_SQL_STATEMENTS);

		LOG.info("Test ascending sort of enum");
		var filter = new SearchFilter()
				.add(PersonSearchField.ID)
				.add(PersonSearchField.MARITAL_STATUS_CONTAINS, "Partner");

		LOG.info("Test in German");
		try (SessionLocale sessionLocale = SessionLocale.get(Locale.GERMAN)) {
			var tuples = personDAO.findBySearchFilter(filter);

			Set<Object> set = tuples.stream().map(t -> t.get(1)).collect(Collectors.toSet());
			assertThat(set).containsOnly(MaritalStatus.PARTNER_PASSED_AWAY, MaritalStatus.PARTNERSHIP, MaritalStatus.PARTNERSHIP_CANCELED);
		}
	}

	@Test
	public void testSearchEnumOrder(AppFixture fixture) {
		LogService.get().consoleLog(Level.INFO, LoggerLevel.forPackage(Constants.class, 0, Level.INFO), LoggerLevel.HIBERNATE_SQL_STATEMENTS);

		LOG.info("Test ascending sort of enum");
		var filter = new SearchFilter()
				.add(PersonSearchField.ID)
				.add(PersonSearchField.MARITAL_STATUS);
		filter.addSort(PersonSearchField.MARITAL_STATUS, true);

		LOG.info("Test in German");
		try (SessionLocale sessionLocale = SessionLocale.get(Locale.GERMAN)) {
			var tuples = personDAO.findBySearchFilter(filter);
			logTuples("All in german", tuples, -30);
			assertThat(assertOrder(cmsName(tuples, 1), true).size()).isGreaterThan(1);
		}
		LOG.info("Test in English");
		try (SessionLocale sessionLocale = SessionLocale.get(Locale.ENGLISH)) {
			var tuples = personDAO.findBySearchFilter(filter);
			assertThat(assertOrder(cmsName(tuples, 1), true).size()).isGreaterThan(1);
		}

		LOG.info("Test descending sort of enum");
		filter = new SearchFilter()
				.add(PersonSearchField.ID)
				.add(PersonSearchField.MARITAL_STATUS);
		filter.addSort(PersonSearchField.MARITAL_STATUS, false);

		LOG.info("Test in German");
		try (SessionLocale sessionLocale = SessionLocale.get(Locale.GERMAN)) {
			var tuples = personDAO.findBySearchFilter(filter);
			assertThat(assertOrder(cmsName(tuples, 1), false).size()).isGreaterThan(1);
		}
		LOG.info("Test in English");
		try (SessionLocale sessionLocale = SessionLocale.get(Locale.ENGLISH)) {
			var tuples = personDAO.findBySearchFilter(filter);
			assertThat(assertOrder(cmsName(tuples, 1), false).size()).isGreaterThan(1);
		}
	}

	/**
	 * Check order, assuming that there are might be two values being the same.
	 *
	 * @param values
	 * @param ascending
	 * @return distinct values
	 */
	protected Set<String> assertOrder(Collection<String> values, boolean ascending) {
		var distinct = new HashSet<String>();
		String last = null;
		for (var cur : values) {
			distinct.add(cur);
			if(last != null) {
				final var assertion = assertThat(last.compareToIgnoreCase(cur));
				if(ascending) {
					assertion.isNotPositive();
				}
				else {
					assertion.isNotNegative();
				}
			}
			last = cur;
		}
		return distinct;
	}

	/**
	 * Test TG-871: SearchFilter: Sorting does not work for fields which are not part of the result tuple.
	 */
	@Test
	public void testSearchSortingWithoutResultField() {
		// first check for ascending sort order
		// search including the sort field, sort ascending
		var filter = new SearchFilter()
				.add(PersonSearchField.BIRTHDATE)
				.add(PersonSearchField.SALARY)
				.add(PersonSearchField.IVY_USER_NAME);

		filter.addSort(PersonSearchField.IVY_USER_NAME, true);

		var personsInclUserNameAsc = personDAO.findBySearchFilter(filter);
		assertThat(personsInclUserNameAsc).as("Find tuples, asc").hasSizeGreaterThan(300);
		// search excluding the sort field, sort ascending
		filter = new SearchFilter()
				.add(PersonSearchField.BIRTHDATE)
				.add(PersonSearchField.SALARY);

		filter.addSort(PersonSearchField.IVY_USER_NAME, true);

		var personsExclUserNameAsc = personDAO.findBySearchFilter(filter);
		assertThat(personsExclUserNameAsc).as("Find tuples, asc").hasSizeGreaterThan(300);

		// sort order should match
		assertThat(personsExclUserNameAsc.size()).as("Same result size, asc").isEqualTo(personsInclUserNameAsc.size());
		assertArrayEquals(personsInclUserNameAsc.stream().map(t -> t.get(0)).toArray(Date[]::new),
				personsExclUserNameAsc.stream().map(t -> t.get(0)).toArray(Date[]::new),
				"Same birthdates in same order, asc");
		assertArrayEquals(personsInclUserNameAsc.stream().map(t -> t.get(1)).toArray(BigDecimal[]::new),
				personsExclUserNameAsc.stream().map(t -> t.get(1)).toArray(BigDecimal[]::new),
				"Same salaries in same order, asc");
		// now check for descending sort order

		// search including the sort field, sort descending
		filter = new SearchFilter()
				.add(PersonSearchField.BIRTHDATE)
				.add(PersonSearchField.SALARY)
				.add(PersonSearchField.IVY_USER_NAME);

		filter.addSort(PersonSearchField.IVY_USER_NAME, false);

		var personsInclUserNameDesc = personDAO.findBySearchFilter(filter);
		assertThat(personsInclUserNameDesc).as("Find tuples, desc").hasSizeGreaterThan(300);

		// search excluding the sort field, sort descending
		filter = new SearchFilter()
				.add(PersonSearchField.BIRTHDATE)
				.add(PersonSearchField.SALARY);

		filter.addSort(PersonSearchField.IVY_USER_NAME, false);

		var personsExclUserNameDesc = personDAO.findBySearchFilter(filter);
		assertThat(personsExclUserNameDesc).as("Find tuples, desc").hasSizeGreaterThan(300);
		// sort order should match
		assertThat(personsExclUserNameDesc.size()).as("Same result size, desc").isEqualTo(personsInclUserNameDesc.size());
		assertArrayEquals(personsInclUserNameDesc.stream().map(t -> t.get(0)).toArray(Date[]::new),
				personsExclUserNameDesc.stream().map(t -> t.get(0)).toArray(Date[]::new),
				"Same birthdates in same order, desc");
		assertArrayEquals(personsInclUserNameDesc.stream().map(t -> t.get(1)).toArray(BigDecimal[]::new),
				personsExclUserNameDesc.stream().map(t -> t.get(1)).toArray(BigDecimal[]::new),
				"Same salaries in same order, desc");
		// now make sure, that sorting was performed at all, asc should not match desc
		assertThat(personsInclUserNameDesc.get(0))
		.as("Ascending and descending should differ")
		.isNotEqualTo(personsInclUserNameAsc.get(0));
	}
}
