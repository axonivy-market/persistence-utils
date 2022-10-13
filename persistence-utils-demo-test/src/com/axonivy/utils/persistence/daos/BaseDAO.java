package com.axonivy.utils.persistence.daos;

import com.axonivy.utils.persistence.Constants;

/**
 * Define the persistence unit name to use in DAOs implementing this one.
 */
public interface BaseDAO extends com.axonivy.utils.persistence.dao.BaseDAO {
	@Override
	default public String getPersistenceUnitName() {
		return Constants.PERSISTENCE_UNIT;
	}
}
