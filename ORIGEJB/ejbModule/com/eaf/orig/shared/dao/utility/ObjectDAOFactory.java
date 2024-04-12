package com.eaf.orig.shared.dao.utility;

public class ObjectDAOFactory {
	public static ReportParamDAO getReportParamDAO(){
		return new ReportParamDAOImpl();
	}
	public static UtilityDAO getUtilityDAO(){
		return new UtilityDAOImpl();
	}
}
