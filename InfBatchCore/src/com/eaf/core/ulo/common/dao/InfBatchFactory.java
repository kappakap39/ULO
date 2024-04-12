package com.eaf.core.ulo.common.dao;

public class InfBatchFactory {
	public static InfBatchDAO getInfBatchDAO(){
		return new InfBatchDAOImpl();
	}
}
