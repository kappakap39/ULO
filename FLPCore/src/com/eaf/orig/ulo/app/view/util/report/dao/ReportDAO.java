package com.eaf.orig.ulo.app.view.util.report.dao;

import java.util.ArrayList;
import com.eaf.orig.ulo.app.view.util.report.model.InfReportJobDataM;

public interface ReportDAO {
	public void insertReportIntoTable(InfReportJobDataM infReportJob) throws Exception;
	public ArrayList<InfReportJobDataM> getReportData(String reportType) throws Exception;
}
