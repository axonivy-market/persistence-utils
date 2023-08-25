package com.axonivy.utils.persistence.daos;

import com.axonivy.utils.persistence.dao.ToggleableDAO;
import com.axonivy.utils.persistence.demo.daos.BaseDAO;
import com.axonivy.utils.persistence.entities.Option;
import com.axonivy.utils.persistence.entities.Option_;

public class OptionDAO extends ToggleableDAO<Option_, Option> implements BaseDAO {

	private static final OptionDAO INSTANCE = new OptionDAO();
	
	public static OptionDAO get() {
		return INSTANCE;
	}
	
	@Override
	protected Class<Option> getType() {
		return Option.class;
	}

}
