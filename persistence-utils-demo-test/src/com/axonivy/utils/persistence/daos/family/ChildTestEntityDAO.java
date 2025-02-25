package com.axonivy.utils.persistence.daos.family;

import com.axonivy.utils.persistence.dao.GenericIdEntityDAO;
import com.axonivy.utils.persistence.dao.ToggleableIdDAO;
import com.axonivy.utils.persistence.daos.BaseDAO;
import com.axonivy.utils.persistence.entities.Producer;
import com.axonivy.utils.persistence.entities.Producer_;
import com.axonivy.utils.persistence.entities.family.ChildTestEntity;
import com.axonivy.utils.persistence.entities.family.ChildTestEntity_;

public class ChildTestEntityDAO extends GenericIdEntityDAO<ChildTestEntity_, ChildTestEntity> implements BaseDAO {

	@Override
	protected Class<ChildTestEntity> getType() {
		return ChildTestEntity.class;
	}
}
