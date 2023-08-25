package com.axonivy.utils.persistence.daos;

import com.axonivy.utils.persistence.dao.AuditableIdDAO;
import com.axonivy.utils.persistence.entities.Car;
import com.axonivy.utils.persistence.entities.Car_;


public class CarDAO extends AuditableIdDAO<Car_, Car> implements BaseDAO {
	private static final CarDAO instance = new CarDAO();

	
	private CarDAO() {
	}

	public static CarDAO getInstance() {
		return instance;
	}

	@Override
	protected Class<Car> getType() {
		return Car.class;
	}
}
