package com.axonivy.utils.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

import com.axonivy.utils.persistence.logging.Logger;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.runtime.IvyRuntime;
import ch.ivyteam.ivy.security.IRole;
import ch.ivyteam.ivy.security.ISecurityContext;
import ch.ivyteam.ivy.security.exec.Sudo;

/**
 * Some Ivy Utilities for the process.
 *
 */
public class IvyUtilities {
	private static final Logger LOG = Logger.getLogger(IvyUtilities.class);
	private static boolean testMode;

	/**
	 *
	 */
	public static final String BYTES_SYMBOL = "B"; // NO_UCD (use private)

	/**
	 * Get the name of the currently active process model.
	 *
	 * @return the name of the currently active process model
	 */
	public static String getProcessModelName() {
		String result = null;
		try {
			result = asSystem(() -> Ivy.request().getProcessModel().getName());
		} catch (Exception e) {
			LOG.warn("Could not get process model name, returning {0}", e, result);
		}
		return result;
	}

	/**
	 * Execute a call via the system context, where more permissions are allowed
	 *
	 * @param callable any callable or a lambda
	 * @param <R> the type of the represented object
	 * @return result value, can be of any type
	 * @throws Exception if something unexpected happens in ivy wf security context
	 *                   call executeAsSystem
	 */
	public static <R> R asSystem(Callable<R> callable) throws Exception {
		if (testMode) {
			LOG.debug("Runnning in Test Mode, system permission should not be required");
			return callable.call();
		} else {
			return Sudo.call(callable);
		}
	}

	protected static <R> R asSystem(Function<ISecurityContext, R> function) throws Exception {
		return asSystem(() -> function.apply(Ivy.wf().getSecurityContext()));
	}

	/**
	 * Get all direct and indirect role members owning a role.
	 *
	 * @param roleName the name of a role
	 * @return list of role members
	 */
	public static List<IRole> getAllRoleMembers(String roleName) {
		List<IRole> result = null;
		try {
			result = asSystem(secCtx -> {
				IRole role = secCtx.roles().find(roleName);
				return role != null ? role.getRoleMembers() : null;
			});
		} catch (Exception e) {
			LOG.error("Error while getting role members owning role {0}.", e, roleName);
		}
		return result != null ? result : new ArrayList<>();
	}

	/**
	 * Get all direct and indirect role members owning a role.
	 *
	 * @param role the object of a role
	 * @return list of role members
	 */
	public static List<IRole> getAllRoleMembers(IRole role) {
		List<IRole> result = null;
		try {
			result = asSystem(() -> role != null ? role.getRoleMembers() : null);
		} catch (Exception e) {
			LOG.error("Error while getting role members owning role {0}.", e, role.getName());
		}
		return result != null ? result : new ArrayList<>();
	}

	/**
	 * Set test mode.
	 *
	 * Test mode is used in JUnit tests and avoids certain Ivy calls (like
	 * executeAsSystem) which are unnecessary outside the engine but difficult to
	 * implement.
	 *
	 * @param testMode set test mode
	 */
	public static void setTestMode(boolean testMode) {
		IvyUtilities.testMode = testMode;
	}

	/**
	 * Check if this program is running in Axon Ivy Designer or not
	 *
	 * @deprecated use {@link IvyRuntime#isDesigner()}
	 * @return true if this program is running in Axon Ivy Designer
	 */
	@Deprecated
	public static boolean isDesigner() {
		return IvyRuntime.isDesigner();
	}

	/**
	 * Checks if given {@link IApplication} is designer.
	 *
	 * @param application the application
	 * @return true if the application is running in Axon Ivy Designer
	 */
	public static boolean isDesigner(IApplication application) {
		try {
			return asSystem(() -> IApplication.DESIGNER_APPLICATION_NAME.equalsIgnoreCase(application.getName()));
		} catch (Exception e) {
			LOG.warn("Could not check whether app is designer, returning false", e);
		}
		return false;
	}

}
