package com.eaf.inf.batch.ulo.fraudresult.dao;

import com.eaf.inf.batch.ulo.fraudresult.dao.FraudResultDAO;
import com.eaf.inf.batch.ulo.fraudresult.dao.FraudResultDAOImpl;


public class FraudResultDAOFactory {
	public static FraudResultDAO getFraudResultDAO(){
		return new FraudResultDAOImpl();
	}
}
