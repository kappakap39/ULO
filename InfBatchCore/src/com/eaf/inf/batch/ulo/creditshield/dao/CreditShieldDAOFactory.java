package com.eaf.inf.batch.ulo.creditshield.dao;

public class CreditShieldDAOFactory {
	public static CreditShieldDAO getCreditShieldDAO(){
		return new CreditShieldDAOImpl();
	}
}
