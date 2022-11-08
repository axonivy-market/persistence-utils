package com.axonivy.utils.persistence.test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Tuple;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.axonivy.utils.persistence.IvyEntityManager;
import com.axonivy.utils.persistence.demo.Logger;
import com.axonivy.utils.persistence.demo.service.IvyService;

import ch.ivyteam.ivy.application.IApplication;
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
	}

	public PackageLevel packageLevel(String packagePrefix, Level level) {
		PackageLevel pl = new PackageLevel();
		pl.setLevel(level);
		pl.setPackagePrefix(packagePrefix);
		return pl;
	}

	public static class PackageLevel {
		private Level level;
		private String packagePrefix;

		private PackageLevel() {
		}
		public Level getLevel() {
			return level;
		}
		public void setLevel(Level level) {
			this.level = level;
		}
		public String getPackagePrefix() {
			return packagePrefix;
		}
		public void setPackagePrefix(String packagePrefix) {
			this.packagePrefix = packagePrefix;
		}
	}

	/**
	 * Set the given categories to the given {@link Level}.
	 *
	 * If no packages are given, then all packages will match.
	 *
	 * @param message
	 * @param globalLevel
	 * @param packageLevels
	 */
	public void switchOnLogging(Level globalLevel, PackageLevel...packageLevels) {
		switchOnLogging("Configuring logging system:", globalLevel, packageLevels);
	}

	/**
	 * Automatically switch logging to a useful default after test.
	 */
	@AfterEach
	public void switchOffLogging() {
		switchOnLogging("Resetting logging system:", Level.WARN);
	}

	/**
	 * Set the given categories to the given {@link Level}.
	 *
	 * If no packages are given, then all packages will match.
	 *
	 * @param message
	 * @param globalLevel
	 * @param packageLevels
	 */
	private void switchOnLogging(String message, Level globalLevel, PackageLevel...packageLevels) {
		prepareSingleConsoleAppender(globalLevel);
		// add current class
		packageLevels = Stream.concat(Stream.of(packageLevel(getClass().getCanonicalName(), globalLevel)), Stream.of(packageLevels)).toArray(PackageLevel[]::new);
		for (PackageLevel packageLevel : packageLevels) {
			org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger)LogManager.getLogger(packageLevel.getPackagePrefix());
			if(logger != null) {
				logger.setLevel(packageLevel.getLevel());
			}
			else {
				LOG.error("Could not find logger {0}", packageLevel.getPackagePrefix());
			}
		}
	}

	private void prepareSingleConsoleAppender(Level globalLevel) {
		org.apache.logging.log4j.core.Logger root = (org.apache.logging.log4j.core.Logger)LogManager.getRootLogger();
		for (Appender appender : root.getAppenders().values()) {
			appender.stop();
			root.removeAppender(appender);
		}
		ConsoleAppender appender = ConsoleAppender
				.newBuilder()
				.setName("console")
				.setLayout(PatternLayout
						.newBuilder()
						.withPattern("%d{ABSOLUTE} [%c{1}] %level{WARN=WRN, DEBUG=DBG, TRACE=TRC, length=3}: %msg%n")
						.build())
				.setTarget(ConsoleAppender.Target.SYSTEM_OUT)
				.setFilter(ThresholdFilter.createFilter(globalLevel, Result.ACCEPT, Result.DENY))
				.build();
		appender.start();
		root.addAppender(appender);
	}

	/**
	 * Show Hibernate statements.
	 *
	 * @return
	 */
	public PackageLevel packageLevelHibernateSqlStatements() {
		return packageLevel("org.hibernate.SQL", Level.TRACE);
	}

	/**
	 * Show Hibernate parameters (except Enums).
	 *
	 * @return
	 */
	public PackageLevel packageLevelHibernateSqlParameters() {
		return packageLevel("org.hibernate.type.descriptor.sql.BasicBinder", Level.TRACE);
	}

	/**
	 * Show Hibernate enum parameters.
	 *
	 * @return
	 */
	public PackageLevel packageLevelHibernateSqlEnums() {
		return packageLevel("org.hibernate.type.EnumType", Level.TRACE);
	}

	/**
	 * Show Hibernate results.
	 * @return
	 */
	public PackageLevel packageLevelHibernateSqlResults() {
		return packageLevel("org.hibernate.type.descriptor.sql.BasicExtractor", Level.TRACE);
	}

	/**
	 * Show everything from Hibernate.
	 *
	 * @return
	 */
	public PackageLevel[] packageLevelHibernateFull() {
		return new PackageLevel[] {
				packageLevelHibernateSqlStatements(),
				packageLevelHibernateSqlParameters(),
				packageLevelHibernateSqlEnums(),
				packageLevelHibernateSqlResults()
		};
	}

	/**
	 * Combine package levels.
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	public PackageLevel[] packageLevelCombine(PackageLevel[] left, PackageLevel...right) {
		PackageLevel[] result = new PackageLevel[left.length + right.length];
		int currIndex = 0;
		for(int i=0; i<left.length; i++) {
			result[currIndex++] = left[i];
		}
		for(int i=0; i<right.length; i++) {
			result[currIndex++] = right[i];
		}
		return result;
	}

	/**
	 * Combine package levels.
	 *
	 * @param levels
	 * @return
	 */
	public PackageLevel[] packageLevelCombine(PackageLevel...levels) {
		return levels;
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