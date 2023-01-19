package com.axonivy.utils.persistence.daos;

import com.axonivy.utils.persistence.dao.GenericDAO;
import com.axonivy.utils.persistence.entities.Constrained;
import com.axonivy.utils.persistence.entities.Constrained_;

public class ConstrainedDAO extends GenericDAO<Constrained_, Constrained> implements BaseDAO {

	private static ConstrainedDAO INSTANCE = new ConstrainedDAO();
	
	@Override
	protected Class<Constrained> getType() {
		return Constrained.class;
	}
	
	public static ConstrainedDAO getInstance() {
		return INSTANCE;
	}
}
