package com.eaf.inf.batch.ulo.applicationdate;


public class ApplicationDateFactory {

	public static UpdateApplicationDateDAO getUpdateApplicationDateDAO() {
		return new UpdateApplicationDateDAOImpl();
	}
	
	public static InfChangeDateDAO getInfChangeDateDAO(){
		return new InfChangeDateDAOImpl();
	}
	
}
