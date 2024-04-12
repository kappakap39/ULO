package com.eaf.inf.batch.ulo.report.core;

import java.io.File;

import org.apache.log4j.Logger;

import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.excel.MapperReR012;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;

public class ReR012Excel extends ReR004Excel{
	private static transient Logger logger = Logger.getLogger(ReR012Excel.class);

	@Override
	public void export(String REPORT_ID, String TEMPLATE, String REPORT_PATH, String REPORT_NAME, InfReportJobDataM infReportJob) throws Exception{
		if(infReportJob==null){
			MapperReR012 xlsMapper = new MapperReR012(TEMPLATE, REPORT_PATH+File.separator+REPORT_NAME);
			xlsMapper.export();
		}else{
			MapperReR012 xlsMapper = new MapperReR012(TEMPLATE, REPORT_PATH+File.separator+REPORT_NAME, infReportJob);
			xlsMapper.export();
			ReportFileFactory.getReportFileDAO().updateInfReportJobFlag(infReportJob.getReportJobId());
		}
	}
}
