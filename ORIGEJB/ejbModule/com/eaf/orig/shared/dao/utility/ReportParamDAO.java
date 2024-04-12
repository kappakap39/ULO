package com.eaf.orig.shared.dao.utility;

import java.sql.Blob;
import java.util.Vector;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.inf.log.model.INFExportDataM;
import com.eaf.orig.master.shared.model.ReportParam;

public interface ReportParamDAO {
	public Vector<ReportParam> getReportParamM(String param_type);
	public ReportParam getReportParamM(String param_type,String param_code);
	public Vector<CacheDataM> getDATE();
	public Vector<INFExportDataM> getINFExport(String moduleID,String date);
	public Blob getBlob(String moduleID,String ID);
}
