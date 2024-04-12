package com.eaf.inf.batch.ulo.letter.reject.allapp.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.letter.reject.inf.NotifyRejectLetterInf;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFConfigDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFProcessRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.GenerateRejectLetetrTemplate;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.inf.GenerateRejectLetterInf;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class RejectLetterPDFController{
	private static transient Logger logger = Logger.getLogger(RejectLetterPDFController.class);
	public TaskExecuteDataM create(RejectLetterPDFDataM rejectLetterPdf,RejectLetterPDFConfigDataM config){
		String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
		String INTERFACE_STATUS_ERROR = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_ERROR");
		TaskExecuteDataM taskExecuteDataM = new TaskExecuteDataM();
		try{
			if(validateRejectLetterPDFTransaction(taskExecuteDataM,rejectLetterPdf,config)){
				TemplateBuilderResponse templateBuilderResponse = this.getTemplateBuilderResponse(rejectLetterPdf);
				if(!InfBatchUtil.empty(templateBuilderResponse)){
					if(validateRejectLetterPDFInterface(taskExecuteDataM,templateBuilderResponse)){
						GenerateRejectLetetrTemplate generateRejectLetetrTemplate = new GenerateRejectLetetrTemplate();
						GenerateRejectLetterInf generateRejectLetterInf = generateRejectLetetrTemplate.getTemplateInterfaceFormat(RejectLetterUtil.PDF);
						RejectLetterInterfaceResponse rejectLetterInterfaceResponse= (RejectLetterInterfaceResponse)generateRejectLetterInf.generateTemplate(templateBuilderResponse,rejectLetterPdf);
						taskExecuteDataM.setResponseObject(rejectLetterInterfaceResponse);
						taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
					}else{
						createInfBatchLog(rejectLetterPdf,INTERFACE_STATUS_ERROR,taskExecuteDataM.getResultDesc());
						// KPL: Not required suppression as MakeAFP initial all send flag=N
					}
				}
			}else{
				createInfBatchLog(rejectLetterPdf,INTERFACE_STATUS_NOT_SEND,taskExecuteDataM.getResultDesc());
				// KPL: Not required suppression as MakeAFP initial all send flag=N
			}
		}catch (Exception e){
			logger.fatal("ERROR ",e);
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecuteDataM.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecuteDataM;
	}
	private boolean validateRejectLetterPDFTransaction(TaskExecuteDataM taskExecuteDataM,RejectLetterPDFDataM rejectLetterPdf,RejectLetterPDFConfigDataM config)throws Exception{
		try{
			String SEND_TO=rejectLetterPdf.getSendTo();
			String configId = "REJECT_LETTER_PDF_TRANSACTION_PROCESS_"+SEND_TO;
			String className = InfBatchProperty.getInfBatchConfig(configId);
			logger.debug("configId : "+configId);
			NotifyRejectLetterInf notify = (NotifyRejectLetterInf)Class.forName(className).newInstance();
			RejectLetterPDFProcessRequestDataM rejectLetterPDFProcessRequest = new RejectLetterPDFProcessRequestDataM();
				rejectLetterPDFProcessRequest.setRejectLetterPdf(rejectLetterPdf);
				rejectLetterPDFProcessRequest.setConfig(config);
			boolean result = notify.validateTaskTransaction(rejectLetterPDFProcessRequest);
			logger.debug("result : "+result);
			if(!result){
				taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.WARNING);
				taskExecuteDataM.setResultDesc("Email priority is not valid for MakePDF");
				return false;
			}
			return true;
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e);
		}
	}
	private TemplateBuilderResponse getTemplateBuilderResponse(RejectLetterPDFDataM rejectLetterPdf) throws Exception{
		String APPLICATION_TYPE_INCREASE = InfBatchProperty.getInfBatchConfig("APPLICATION_TYPE_INCREASE");
		String REJECT_LETTER_PDF_SENDTO_CUSTOMER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_SENDTO_CUSTOMER");
		String REJECT_LETTER_PDF_APP_OTHER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_APP_OTHER");
		TemplateBuilderResponse templateBuilderResponse  = new TemplateBuilderResponse();
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
		String LANGUAGE ="";
		String SEND_TO=rejectLetterPdf.getSendTo();
		String applicationType = Formatter.display(rejectLetterPdf.getApplicationType());
		if(!InfBatchUtil.empty(rejectLetterPdf.getLanguage())){
			LANGUAGE = "_"+rejectLetterPdf.getLanguage().toUpperCase();
		}
		logger.debug("SEND_TO : "+SEND_TO);
		String TEMPLATE_ID = "";
		String configId = "";
		if(SEND_TO.equals(REJECT_LETTER_PDF_SENDTO_CUSTOMER)){
			String app = (!InfBatchUtil.empty(applicationType) && applicationType.equals(APPLICATION_TYPE_INCREASE)) ? APPLICATION_TYPE_INCREASE : REJECT_LETTER_PDF_APP_OTHER ;
			configId = "REJECT_LETTER_PDF_"+SEND_TO+"_APP_"+app+"_TEMPLATE_ID"+LANGUAGE;
		}else{ // SELLER, RECOMMEND
			configId = "REJECT_LETTER_PDF_"+SEND_TO+"_NORMAL_TEMPLATE_ID"+LANGUAGE;
		}
		TEMPLATE_ID  = InfBatchProperty.getInfBatchConfig(configId);
		logger.debug("configId : "+configId);
		logger.debug("TEMPLATE_ID : "+TEMPLATE_ID);
		templateBuilderRequest.setTemplateId(TEMPLATE_ID);
		templateBuilderRequest.setTemplateType(TemplateBuilderConstant.TemplateType.REJECT_LETTER_PDF);
		templateBuilderRequest.setRequestObject(rejectLetterPdf);
		templateBuilderRequest.setUniqueId(rejectLetterPdf.getLetterNo());
		templateBuilderRequest.setLanguage(rejectLetterPdf.getLanguage().toUpperCase());
		templateBuilderResponse = templateController.build(templateBuilderRequest);
		return templateBuilderResponse;
	}
	private boolean validateRejectLetterPDFInterface(TaskExecuteDataM taskExecuteDataM,TemplateBuilderResponse templateBuilderResponse)throws Exception{
		TemplateVariableDataM templateVariable = templateBuilderResponse.getTemplateVariable();
		Map<String, Object> hMapping = templateVariable.getTemplateVariable();
		String email = (String)hMapping.get(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY);
		logger.debug("email : "+email);
		if(InfBatchUtil.empty(email)){
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.WARNING);
			taskExecuteDataM.setResultDesc("Email was not found");
			return false;
		}
		return true;
	}
	private void createInfBatchLog(RejectLetterPDFDataM rejectLetterPdf,String status,String message)throws InfBatchException{
		try{
			String REJECT_LETTER_PDF_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_INTERFACE_CODE");
			ArrayList<InfBatchLogDataM> infBatchLogs = new ArrayList<InfBatchLogDataM>();
			for(String applicationRecordId : rejectLetterPdf.getApplicationRecordIds()){
				InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
					infBatchLog.setApplicationGroupId(rejectLetterPdf.getApplicationGroupId());
					infBatchLog.setApplicationRecordId(applicationRecordId);
					infBatchLog.setLifeCycle(rejectLetterPdf.getLifeCycle());
					infBatchLog.setInterfaceCode(REJECT_LETTER_PDF_INTERFACE_CODE);
					infBatchLog.setInterfaceStatus(status); 
					infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
					infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
					infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
					infBatchLog.setLogMessage(message);
					infBatchLog.setSystem02(rejectLetterPdf.getLanguage());
					infBatchLog.setSystem03(rejectLetterPdf.getSendTo());
				infBatchLogs.add(infBatchLog);
			}
			if(!InfBatchUtil.empty(infBatchLogs)){
				insertInfBatchLog(infBatchLogs);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}
	private void insertInfBatchLog(ArrayList<InfBatchLogDataM> infBatchLogs)throws InfBatchException{
	   	 Connection conn = InfBatchObjectDAO.getConnection();	
	   	 try {
	   		 InfDAO infDAO = InfFactory.getInfDAO();
	   		 conn.setAutoCommit(false);
	   		 if(!InfBatchUtil.empty(infBatchLogs)){
	   			 for(InfBatchLogDataM infBatchLog : infBatchLogs){
	   				infDAO.insertInfBatchLog(infBatchLog, conn);
	   			 }
	   		 }
	   		 conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			try {
				conn.rollback();
			}catch (Exception e1){
				logger.fatal("ERROR",e1);
				throw new InfBatchException(e.getLocalizedMessage());
			}			
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
}
