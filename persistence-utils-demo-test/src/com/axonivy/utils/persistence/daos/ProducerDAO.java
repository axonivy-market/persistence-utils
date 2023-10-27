package com.axonivy.utils.persistence.daos;

import com.axonivy.utils.persistence.dao.ToggleableIdDAO;
import com.axonivy.utils.persistence.entities.Producer;
import com.axonivy.utils.persistence.entities.Producer_;

public class ProducerDAO extends ToggleableIdDAO<Producer_, Producer> implements BaseDAO {

	@Override
	protected Class<Producer> getType() {
		return Producer.class;
	}
}
