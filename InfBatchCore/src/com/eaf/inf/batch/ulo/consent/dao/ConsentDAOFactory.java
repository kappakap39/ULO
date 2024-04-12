package com.eaf.inf.batch.ulo.consent.dao;

public class ConsentDAOFactory {
	public static ConsentDAO getConsentDAO(){
		return new ConsentDAOImpl();
	}
}
