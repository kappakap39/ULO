package com.eaf.inf.batch.ulo.report.core;

import java.io.File;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.inf.GenerateFileInf;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.excel.MapperReR005;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;

public class ReR005Excel implements GenerateFileInf {
	private static transient Logger logger = Logger.getLogger(ReR005Excel.class);
	
	@Override
	public InfResultDataM create(GenerateFileDataM generateFile)throws Exception{
		logger.debug("start create excel");
		
		InfResultDataM infResult = new InfResultDataM();
		try{
			String REPORT_ID = generateFile.getUniqueId();
			String TEMPLATE = generateFile.getFileTemplate();
			String REPORT_PATH = generateFile.getFileOutputPath();
			String REPORT_NAME = generateFile.getFileOutputName();
			InfReportJobDataM infReportJob = (InfReportJobDataM)generateFile.getObject();
	//			String YYYYMMDD = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.YYYYMMDD);
	//			REPORT_PATH += YYYYMMDD+File.separator;
			File FOLDER = new File(REPORT_PATH);
			if(!FOLDER.exists()){
				FOLDER.mkdirs();
			}
			
			DecimalFormat format = new DecimalFormat("00");
			REPORT_NAME = REPORT_NAME.replace("_xx", "_"+format.format(generateFile.getOrderNo()));
			logger.debug("REPORT_ID >> "+REPORT_ID);
			logger.debug("REPORT_PATH >> "+REPORT_PATH);
			logger.debug("REPORT_NAME >> "+REPORT_NAME);
			export(REPORT_ID, TEMPLATE, REPORT_PATH, REPORT_NAME, InfBatchProperty.getDate());
			infResult.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infResult.setResultDesc(e.getLocalizedMessage());
		}
		logger.debug("end create excel");
		return infResult;
	}
	
	public void export(String REPORT_ID, String TEMPLATE, String REPORT_PATH, String REPORT_NAME, Date asOfDate) throws Exception{
		MapperReR005 xlsMapper = new MapperReR005(TEMPLATE, REPORT_PATH+File.separator+REPORT_NAME, REPORT_ID, asOfDate, null);
		xlsMapper.export();
	}

	@Override
	public InfResultDataM create(GenerateFileDataM generateFile, Connection con)throws Exception{
		return null;
	}
	

}
