package com.eaf.inf.batch.ulo.eapp.fraudresult.dao;


public class EFraudDAOFactory {
	public static EFraudDAO getEFraudDAO(){
		return new EFraudDAOImpl();
	}
}
