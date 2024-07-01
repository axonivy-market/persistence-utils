package com.axonivy.utils.persistence.enums;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.persistence.logging.Logger;
import com.axonivy.utils.persistence.service.EnumService;

import ch.ivyteam.ivy.environment.Ivy;

/**
 * Interface to use with class which have their instance names defined in the CMS.
 *
 * These are typically enumerations.
 */
public interface HasCmsName {
	static final String SLASH = "/";
	static final String ENUMS_CMS_BASE = SLASH + "Enums";

	/**
	 * Get the name of this instance.
	 *
	 * @return name of this instance.
	 */
	String name();

	/**
	 * Return the name entry of the instance.
	 *
	 * @return
	 */
	default String getCmsName() {
		return getCms("name");
	}

	/**
	 * Lookup the entry for an instance in the Ivy Cms.
	 *
	 * If the entry is not found, then return the name of the entry.
	 *
	 * @param entry
	 * @return CMS entry
	 */
	default String getCms(String entry) {
		var cmsPath = getEntryPath(entry);
		var result = Ivy.cms().co(cmsPath);
		if(StringUtils.isEmpty(result)) {
			Logger.getLogger(HasCmsName.class).warn("Missing CMS entry for '" + cmsPath + "'");
			result = name();
		}
		return result;
	}

	/**
	 * Lookup the URL of an entry in the Ivy Cms.
	 * 
	 * If the entry is not found, then the name of the entry is returned.
	 * 
	 * @param entry
	 * @return
	 */
	default String getCmsUrl(String entry) {
		var cmsPath = getEntryPath(entry);
		var result = Ivy.cms().cr(cmsPath);
		if(StringUtils.isEmpty(result)) {
			Logger.getLogger(HasCmsName.class).warn("Missing CMS entry for '" + cmsPath + "'");
			result = name();
		}
		return result;
	}

	/**
	 * Get the base path of the entry.
	 * 
	 * @param entry
	 * @return
	 */
	default String getEntryPath(String entry) {
		return getBasePath() + SLASH + name() + SLASH + entry;
	}

	/**
	 * Get the CMS base path of enums.
	 * 
	 * @return
	 */
	default String getBasePath() {
		return ENUMS_CMS_BASE + SLASH + getClass().getCanonicalName().replaceAll("[.]", SLASH);
	}

	/**
	 * Get the comparator to use for sorting the CMS entries.
	 * 
	 * <p>
	 * Things to consider: case-sensitivity, special characters,... See {@link EnumService} for some predefined comparators.
	 * </p>
	 * @return
	 */
	default Comparator<String> getCmsComparator() {
		return EnumService.localeAwareCIComparator();
	}
}
