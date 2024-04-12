package com.eaf.inf.batch.ulo.report.core;

import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.inf.GenerateFileInf;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PerformanceController;

public class JasperCore extends InfBatchObjectDAO implements GenerateFileInf{
	private static transient Logger logger = Logger.getLogger(JasperCore.class);
	
	@Override
	public InfResultDataM create(GenerateFileDataM fileGenerate)throws Exception{
		InfResultDataM infResult = new InfResultDataM();
		
		String REPORT_ID = fileGenerate.getUniqueId();
		String TEMPLATE = fileGenerate.getFileTemplate();
		String REPORT_PATH = fileGenerate.getFileOutputPath();
		String REPORT_NAME = fileGenerate.getFileOutputName();
		String JASPER_REPORT_PATH = fileGenerate.getReportPath();
		Date systemDate = fileGenerate.getSystemDate();
//			String YYYYMMDD = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.YYYYMMDD);
//			REPORT_PATH += YYYYMMDD+File.separator;
		File FOLDER = new File(REPORT_PATH);
		if(!FOLDER.exists()){
			FOLDER.mkdirs();
		}
		logger.debug("REPORT ID >> "+REPORT_ID);
		logger.debug("TEMPLATE >> "+TEMPLATE);
		logger.debug("REPORT_PATH >> "+REPORT_PATH);
		logger.debug("REPORT_NAME >> "+REPORT_NAME);
		logger.debug("JASPER_REPORT_PATH >> "+JASPER_REPORT_PATH);
		logger.debug("SYSTEM_DATE >> "+systemDate);
		
		Connection conn  = null;
		Date appDate = InfBatchProperty.getDate();
		logger.debug("appDate >> "+appDate);
		try{
			Map<String, Object> jasperParameter = new HashMap<>();
			conn = getConnection();
		 	jasperParameter.put("SUBREPORT_DIR", JASPER_REPORT_PATH+File.separator);	
		 	if(!InfBatchUtil.empty(systemDate)){
		 		jasperParameter.put("P_ADD_DATE", systemDate);
		 		jasperParameter.put("P_APP_DATE", systemDate);
		 		jasperParameter.put("P_DATE_STRING", InfBatchUtil.convertDate(systemDate, InfBatchConstant.SYSTEM_DATE_FORMAT));
		 	}else{
		 		jasperParameter.put("P_ADD_DATE", appDate);
		 		jasperParameter.put("P_APP_DATE", appDate);
		 		jasperParameter.put("P_DATE_STRING", InfBatchUtil.convertDate(appDate, InfBatchConstant.SYSTEM_DATE_FORMAT));
		 	}
		 	
		 	
		 	JasperPrint jasperPrint = new JasperPrint();		 	
		 	try{
		 		PerformanceController performance = new PerformanceController();
		 		performance.create("JasperFillManager");
		 		jasperPrint = JasperFillManager.fillReport(TEMPLATE, jasperParameter,conn);
		 		performance.end("JasperFillManager");
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
				infResult.setErrorMsg(e.getLocalizedMessage());
			}finally{
				try{
//					if(null != conn){
//						conn.close();
//					}
					closeConnection(conn);
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					throw new Exception(e.getLocalizedMessage());
				}
			}
	
		 	JRXlsExporter exporter = new JRXlsExporter();
		 	File OUTPUT_PATH = new File(REPORT_PATH);
		 	File OUTPUT_FILE = new File(REPORT_PATH+File.separator+REPORT_NAME);
		 	if(!OUTPUT_PATH.exists()){
		 		OUTPUT_PATH.mkdirs();
		 	}
			exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(OUTPUT_FILE));

			try{
				exporter.exportReport();
			}catch  (JRException e){
				logger.fatal("ERROR",e);
				throw new Exception(e.getLocalizedMessage());
			}
			infResult.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infResult.setErrorMsg(e.getLocalizedMessage());
		}
		return infResult;
	}

	@Override
	public InfResultDataM create(GenerateFileDataM generateFile, Connection con)throws Exception{
		return null;
	}

}
