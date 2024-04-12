package com.eaf.inf.batch.ulo.orsum.dao;

public class ORSummaryDAOFactory {
	public static ORSummaryDAO getORSummaryDAO(){
		return new ORSummaryDAOImpl();
	}
}
