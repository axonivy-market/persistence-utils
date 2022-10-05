package com.axonivy.utils.persistence.daos;

import com.axonivy.utils.persistence.Constants;
import com.axonivy.utils.persistence.history.handler.DefaultAbstractAuditHandler;

public class AuditHandler extends DefaultAbstractAuditHandler {

	@Override
	public String getHandlerPersistenceUnitName() {
		return Constants.PERSISTENCE_UNIT;
	}

}
