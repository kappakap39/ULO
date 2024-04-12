package com.eaf.inf.batch.ulo.report.dao;

public class ReportFileFactory {
	
	public static ReportFileDAO getReportFileDAO(){
		return new ReportFileDAOImp();
	}
}
