package com.eaf.inf.batch.ulo.debt.dao;

public class DebtDAOFactory {
	public static DebtDAO getDebtDAO(){
		return new DebtDAOImpl();
	}
}
