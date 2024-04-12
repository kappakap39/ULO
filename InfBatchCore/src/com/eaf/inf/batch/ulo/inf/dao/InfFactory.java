package com.eaf.inf.batch.ulo.inf.dao;

public class InfFactory {
	public static InfDAO getInfDAO(){
		return new InfDAOImpl();
	}

}
