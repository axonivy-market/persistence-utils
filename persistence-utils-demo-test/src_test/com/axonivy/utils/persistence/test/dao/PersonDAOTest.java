package com.axonivy.utils.persistence.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.TransactionRolledbackException;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.Constants;
import com.axonivy.utils.persistence.demo.daos.PersonDAO;
import com.axonivy.utils.persistence.demo.entities.Department;
import com.axonivy.utils.persistence.demo.entities.Person;
import com.axonivy.utils.persistence.test.DemoTestBase;
import com.axonivy.utils.persistence.test.service.LogService;
import com.axonivy.utils.persistence.test.service.LogService.LoggerLevel;

import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.environment.IvyTest;


@IvyTest
public class PersonDAOTest extends DemoTestBase {
	private static final PersonDAO personDAO = PersonDAO.getInstance();
	private static final String userLeitung = "gera.dewegs";


	@BeforeEach
	public void prepare(AppFixture fixture) throws Exception {
		prepareTestDataAndMocking(true);
	}


	@Test
	public void testLoadTestdata() {
		LogService.get().consoleLog(Level.INFO);

		List<Person> all = personDAO.findAll();
		assertThat(all).as("Found entries").hasSizeGreaterThan(300);

		for (Person person : all) {
			LOG.info(String.format("Person: %-20s %-20s %tF %-20s %9.2f",
					person.getFirstName(), person.getLastName(), person.getBirthdate(),
					person.getMaritalStatus(), person.getSalary()));
		}
	}

	@Test
	public void testLoadPermissions(AppFixture fixture) {
		LogService.get().consoleLog(Level.INFO, LoggerLevel.forPackage(Constants.class, 0, Level.INFO), LoggerLevel.HIBERNATE_SQL_STATEMENTS);
		createUser(userLeitung, "Hans", "Huber", "password");
		fixture.loginUser(userLeitung);
		List<Person> all = personDAO.findAll();
		assertThat(all).as("Found entries").hasSizeGreaterThan(5);

		for (Person person : all) {
			LOG.info(String.format("Person: %-20s %-20s %tF %-20s %9.2f",
					person.getFirstName(), person.getLastName(), person.getBirthdate(),
					person.getMaritalStatus(), person.getSalary()));
			assertThat(person.getDepartment().getName()).as("Correct department").isEqualTo("Leitung");
		}
	}

	//@Test
	public void testData() throws Exception {
		LOG.info("Writig Excel file");
		TestDemoDAO testDemoDao = TestDemoDAO.getInstance();
		testDemoDao.exportTablesToExcel("C:/Temp/exported.xls", Person.class.getSimpleName(), Department.class.getSimpleName());
		LOG.info("Wrote Excel file");
	}

	@Test
	public void testTransactions() throws TransactionRolledbackException {
		Person person = personDAO.findByIvyUserName(userLeitung, null);
		assertThat(person).as("Find person").isNotNull();
		Department leitung = person.getDepartment();
		assertThat(leitung).as("Find department").isNotNull();

		personDAO.beginSession();
		personDAO.beginTransaction();

		person = new Person();
		person.setIvyUserName("ivyname1");
		person.setDepartment(leitung);
		personDAO.save(person);

		person = new Person();
		person.setIvyUserName("ivyname2");
		person.setDepartment(leitung);
		personDAO.save(person);

		assertThat(personDAO.findByIvyUserName("ivyname1", null)).as("Find person").isNotNull();
		assertThat(personDAO.findByIvyUserName("ivyname2", null)).as("Find person").isNotNull();

		personDAO.rollbackTransaction();
		personDAO.closeSession();

		assertThat(personDAO.findByIvyUserName("ivyname1", null)).as("Rolledback person").isNull();
		assertThat(personDAO.findByIvyUserName("ivyname2", null)).as("Rolledback person").isNull();
	}

	//@Test
	public void testConstraintViolation() {
		Person person = personDAO.findByIvyUserName(userLeitung, null);
		assertThat(person).as("Find person").isNotNull();
		Department leitung = person.getDepartment();
		assertThat(leitung).as("Find department").isNotNull();

		person = new Person();
		person.setIvyUserName("duplicatename");
		person.setDepartment(leitung);
		personDAO.save(person);

		person = new Person();
		person.setIvyUserName("duplicatename");
		person.setDepartment(leitung);

		try {
			personDAO.save(person);
			Assertions.fail("Expected " + PersistenceException.class.getSimpleName() + " while saving person with duplicate ivy username");
		} catch(PersistenceException e) {
			LOG.info("Found expected Exception: {0}", e.getMessage());
		}

		person.setId(null);
		person.setVersion(null);
		person.setIvyUserName("uniquename");
		person = personDAO.save(person);

		try {
			person.setIvyUserName("duplicatename");
			person = personDAO.save(person);
			Assertions.fail("Expected " + PersistenceException.class.getSimpleName() + " while updating person with duplicate ivy username");
		} catch(PersistenceException e) {
			LOG.info("Found expected Exception: {0}", e.getMessage());
		}
	}
}
