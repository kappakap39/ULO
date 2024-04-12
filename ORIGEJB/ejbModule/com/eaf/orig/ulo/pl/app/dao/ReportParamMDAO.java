package com.eaf.orig.ulo.pl.app.dao;

import java.util.ArrayList;
import java.util.Vector;
import com.eaf.orig.master.shared.model.ReportParam;

public interface ReportParamMDAO {
	public Vector<ReportParam> getReportParamM();
	public Vector<ReportParam> getReportParamM(String paramType);
	public ReportParam getReportParamM(String paramType, String paramCode);
	public ArrayList<String> getRejectLetterTemplate();
	public void prepareRejectLetter(String reportDate);
	public int getMaxFileOfTemplate(String template);
	public void insertInterfaceLog(String moduleId, String logType, String logCode, String message, String createBy, String refId);
}
