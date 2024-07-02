package com.axonivy.utils.persistence.test;

import java.util.Collection;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Tuple;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;
import org.hsqldb.util.DatabaseManagerSwing;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import com.axonivy.utils.persistence.IvyEntityManager;
import com.axonivy.utils.persistence.demo.Logger;
import com.axonivy.utils.persistence.demo.service.IvyService;
import com.axonivy.utils.persistence.test.service.LogService;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.db.IExternalDatabase;
import ch.ivyteam.ivy.db.IExternalDatabaseManager;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.security.IRole;
import ch.ivyteam.ivy.security.ISecurityContext;
import ch.ivyteam.ivy.security.IUser;
import ch.ivyteam.ivy.security.user.NewUser;


public class IvyTestBase {
	protected final Logger LOG = Logger.getLogger(getClass());

	@BeforeEach
	public void setup(AppFixture fixture) {
		// start with system user
		fixture.loginUser(IvyService.getSystemUser());
	}

	@AfterAll
	public static void down() {
		IvyEntityManager.getInstance().clearThreadlocals();
		LogService.get().consoleLog(Level.INFO);
	}

	/**
	 * Get a configured database from Ivy.
	 *
	 * @param name
	 * @return
	 */
	public static IExternalDatabase getExternalDatabase(String name) {
		return IExternalDatabaseManager.instance()
				.getExternalDatabaseApplicationContext(IApplication.current())
				.getExternalDatabase(name);
	}

	/**
	 * Start the database manager.
	 */
	public static void startDBManager()  {
		startDBManager("jpa-demo-test");
	}

	/**
	 * Start the database manager.
	 */
	public static void startDBManager(String databaseName)  {
		IExternalDatabase database = getExternalDatabase(databaseName);
		String url = database.getConfiguration().url();
		DatabaseManagerSwing.main(new String[] {"--url", url, "--noexit" });
	}

	/**
	 * Log a collection of tuples intelligently.
	 *
	 * @param tupleName name which is printed as a headline
	 * @param tuples
	 * @param max max length per column, if < 0 then entries will be abbreviated to fit
	 */
	public void logTuples(String tupleName, Collection<Tuple> tuples, int max) {
		LOG.info("Begin of tuples ''{0}'' ({1} entries)", tupleName, tuples.size());
		boolean abbreviate = max < 0;
		int maxWidth = max < 0 ? -max : max;

		boolean first = true;
		int widths[] = new int[0];
		for (Tuple tuple : tuples) {
			if(first) {
				// initialize list of max column widths
				first = false;
				widths = new int[tuple.getElements().size()];
			}
			// check lengths of all columns, if bigger, remember
			for (int i = 0; i < widths.length; i++) {
				String value = "" + tuple.get(i);
				int length = value.toString().length();
				if(widths[i] < length) {
					widths[i] = length;
				}

				widths[i] = Math.min(widths[i], maxWidth);
			}
		}

		if(!first) {
			int row = 0;
			for (Tuple tuple : tuples) {
				row++;
				StringBuilder lineBuilder = new StringBuilder(String.format("%3d", row));

				for (int i = 0; i < widths.length; i++) {
					String value = "" + tuple.get(i);
					if(value.length() > maxWidth && abbreviate) {
						value = StringUtils.abbreviate(value, maxWidth);
					}
					lineBuilder.append(String.format(" %-" + widths[i] + "s", value));
				}
				LOG.info(lineBuilder.toString());
			}

		}
		LOG.info("End of tuples ''{0}'' ({1} entries)", tupleName, tuples.size());
	}

	/**
	 * Grant a role to a user.
	 *
	 * @param username
	 */
	public void grantRoleToUser(String rolename, String username) {
		IUser user = IvyService.findUser(username);
		if(user == null) {
			throw new RuntimeException("Could not find user " + username);
		}

		IRole role = IvyService.findRole(rolename);
		if(role == null) {
			throw new RuntimeException("Could not find role " + rolename);
		}
		user.addRole(role);
	}

	/**
	 * Revoke a role from a user.
	 *
	 * @param username
	 */
	public void revokeRoleFromUser(String rolename, String username) {
		IUser user = IvyService.findUser(username);
		if(user == null) {
			throw new RuntimeException("Could not find user " + username);
		}

		IRole role = IvyService.findRole(rolename);
		if(role == null) {
			throw new RuntimeException("Could not find role " + rolename);
		}
		user.removeRole(role);
	}

	private boolean safeCompare(Object l, Object r) {
		if(l == r) {
			return true;
		}
		if(l == null || r == null) {
			return false;
		}

		return l.equals(r);
	}

	private String toString(FacesMessage m) {
		return "" + m.getSeverity() + ":" + m.getSummary() + ":"	+ m.getDetail();
	}

	public void assertFacesMessages(FacesContext facesContext, String message, FacesMessage...expected) {
		boolean differ = false;

		List<FacesMessage> faces = FacesContext.getCurrentInstance().getMessageList();


		StringBuilder sb = new StringBuilder();
		int max = faces.size();
		if(expected.length > max) {
			max = expected.length;
		}
		for (int i = 0; i < max; i++) {
			String ex = null;
			String got = null;
			if(i < expected.length) {
				ex = toString(expected[i]);
			}
			if(i < faces.size()) {
				got = toString(faces.get(i));
			}
			if(!safeCompare(ex, got)) {
				differ = true;
				sb.append(String.format("diff at index %d: expected: '%s' got: '%s'%n", i, ex, got));
			}
		}

		if(differ) {
			String assertError = String.format(
					"%s%nFaces Messages differ, size: expected: %d got: %d%n%s",
					message,
					expected.length,
					faces.size(),
					sb.toString());
			LOG.error(assertError);
			throw new AssertionError(
					assertError);
		}
	}

	public void createUser(String ivyUserName, String firstName, String lastName, String password) {
		ISecurityContext securityContext = IApplication.current().getSecurityContext();

		LOG.info("Creating user {0}", ivyUserName);
		securityContext.users().create(NewUser.create(ivyUserName)
				.fullName(String.format("%s %s", firstName, lastName))
				.password(password)
				.mailAddress(String.format("%s.%s@demo.axonivy.com", firstName, lastName))
				.toNewUser());
	}

}