package com.eaf.inf.batch.ulo.report.core;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.excel.MapperReR021;
import com.eaf.inf.batch.ulo.report.model.AuditTrailDataM;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;

public class ReR021Excel extends ReR004Excel{
	private static transient Logger logger = Logger.getLogger(ReR021Excel.class);
	private ArrayList<AuditTrailDataM> reR021 = null;
	@Override
	public InfResultDataM create(GenerateFileDataM generateFile)throws Exception{
		logger.debug("start create excel");
		
		InfResultDataM infResult = new InfResultDataM();
		try{
			String REPORT_ID = generateFile.getUniqueId();
			String TEMPLATE = generateFile.getFileTemplate();
			String REPORT_PATH = generateFile.getFileOutputPath();
			String REPORT_NAME = generateFile.getFileOutputName();
			Date systemDate = generateFile.getSystemDate();
			InfReportJobDataM infReportJob = (InfReportJobDataM)generateFile.getObject();
			Date appDate = InfBatchProperty.getDate();
			File FOLDER = new File(REPORT_PATH);
			String asOfDate = null;
			if(!FOLDER.exists()){
				FOLDER.mkdirs();
			}
			
			logger.debug("REPORT_ID >> "+REPORT_ID);
			logger.debug("REPORT_PATH >> "+REPORT_PATH);
			logger.debug("REPORT_NAME >> "+REPORT_NAME);
			logger.debug("SYSTEM_DATE >> "+systemDate);
			logger.debug("appDate >> "+appDate);
			if(!InfBatchUtil.empty(systemDate)){
				asOfDate = InfBatchUtil.convertDate(systemDate, InfBatchConstant.SYSTEM_DATE_FORMAT);
		 	}else{
		 		asOfDate = InfBatchUtil.convertDate(appDate, InfBatchConstant.SYSTEM_DATE_FORMAT);
		 	}
			if(InfBatchConstant.RE_R021.APPROVE.equals(REPORT_ID)){
				reR021 = ReportFileFactory.getReportFileDAO().getReportR021(asOfDate,"NM9901");
			}else if(InfBatchConstant.RE_R021.REJECT.equals(REPORT_ID)){
				reR021 = ReportFileFactory.getReportFileDAO().getReportR021(asOfDate,"NM9902");
			}
			
			export(asOfDate, TEMPLATE, REPORT_PATH, REPORT_NAME, infReportJob, reR021);
			infResult.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infResult.setResultDesc(e.getLocalizedMessage());
		}
		logger.debug("end create excel");
		return infResult;
	}
	
	public void export(String systemDate, String TEMPLATE, String REPORT_PATH, String REPORT_NAME, InfReportJobDataM infReportJob,ArrayList<AuditTrailDataM> reR021) throws Exception{
		if(infReportJob==null){
			MapperReR021 xlsMapper = new MapperReR021(TEMPLATE, REPORT_PATH+File.separator+REPORT_NAME, systemDate,reR021);
			xlsMapper.export();
		}else{
			MapperReR021 xlsMapper = new MapperReR021(TEMPLATE, REPORT_PATH+File.separator+REPORT_NAME, systemDate, infReportJob,reR021);
			xlsMapper.export();
			ReportFileFactory.getReportFileDAO().updateInfReportJobFlag(infReportJob.getReportJobId());
		}
	}
	
	@Override
	public InfResultDataM create(GenerateFileDataM generateFile, Connection con)throws Exception{
		return null;
	}
}
