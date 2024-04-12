package com.eaf.inf.batch.ulo.inf.text;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.inf.GenerateFileInf;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.util.FileUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;

public class TextFileCore extends InfBatchObjectDAO implements GenerateFileInf{
	private static transient Logger logger = Logger.getLogger(TextFileCore.class);
	String USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	String KM_ALERT_PERFORMANCE = InfBatchProperty.getInfBatchConfig(InfBatchConstant.KM_ALERT_PERFORMANCE+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	String KVI_CLOSE_APPLICATION = InfBatchProperty.getInfBatchConfig(InfBatchConstant.KVI_CLOSE_APPLICATION+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	String MAKE_AFP_PRINT_REJECT_NON_NCB = InfBatchProperty.getInfBatchConfig(InfBatchConstant.MAKE_AFP_PRINT.REJECT_NON_NCB+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	String MAKE_AFP_PRINT_REJECT_NCB = InfBatchProperty.getInfBatchConfig(InfBatchConstant.MAKE_AFP_PRINT.REJECT_NCB+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	String MAKE_AFP_PRINT_APPROVE = InfBatchProperty.getInfBatchConfig(InfBatchConstant.MAKE_AFP_PRINT.APPROVE+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	String MAKE_PDF = InfBatchProperty.getInfBatchConfig(InfBatchConstant.MAKE_PDF+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	String SETUP_LOAN = InfBatchProperty.getInfBatchConfig(InfBatchConstant.SETUP_LOAN+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	
	public String executeData(String MODULE_ID, String OUTPUT_NAME) throws Exception{
		String executeResult = "";
		if(KM_ALERT_PERFORMANCE.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getKmAlertService(USER_ID, OUTPUT_NAME);
		}else if(SETUP_LOAN.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getSetupLoan(USER_ID, OUTPUT_NAME);
		}else if(KVI_CLOSE_APPLICATION.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getKVICloseApplication(USER_ID, OUTPUT_NAME);
		}else if(MAKE_AFP_PRINT_REJECT_NON_NCB.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getAFPPrintRejectNonNCB(USER_ID, OUTPUT_NAME);
		}else if(MAKE_AFP_PRINT_REJECT_NCB.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getAFPPrintRejectNCB(USER_ID, OUTPUT_NAME);
		}else if(MAKE_AFP_PRINT_APPROVE.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getAFPPrintApprove(USER_ID, OUTPUT_NAME);
		}else if(MAKE_PDF.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getPDFRejectLetter(USER_ID, OUTPUT_NAME);
		}

		return executeResult;
	}
	
	@Override
	public InfResultDataM create(GenerateFileDataM generateFile)throws Exception{
		InfResultDataM infResult = new InfResultDataM();
		try{
			String MODULE_ID = generateFile.getUniqueId();
			String FILE_PATH = generateFile.getFileOutputPath();
			String FILE_NAME = generateFile.getFileOutputName();
			String ENCODE = generateFile.getEncode();
			String executeResult = executeData(MODULE_ID,FILE_NAME);
			String contentText = "";
			if(KM_ALERT_PERFORMANCE.equals(MODULE_ID))
			{
				contentText = executeResult;
			}
			else
			{
				logger.debug("executeResult >> "+executeResult);
				contentText = InfFactory.getInfDAO().getInfExport(MODULE_ID);
			}
			
			FileUtil.generateFile(FILE_PATH, FILE_NAME, contentText, ENCODE);
			infResult.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			
			if(SETUP_LOAN.equals(MODULE_ID))
			{
				if(Util.empty(executeResult))
				{
					infResult.setResultDesc("Not found any KPL application eligible for setupLoan process.");
				}
				if(!Util.empty(executeResult) && executeResult.contains("[ERROR]") )
				{
					infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infResult.setResultDesc(executeResult);
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infResult.setResultDesc(e.getLocalizedMessage());
			infResult.setErrorMsg(e.getLocalizedMessage());
		}
		return infResult;
	}

	@Override
	public InfResultDataM create(GenerateFileDataM generateFile, Connection conn)throws Exception{
		return null;
	}

}
