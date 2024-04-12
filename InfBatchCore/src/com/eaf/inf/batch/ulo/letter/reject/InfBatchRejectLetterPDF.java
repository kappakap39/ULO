package com.eaf.inf.batch.ulo.letter.reject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.file.FileControl;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchRejectLetterPDF extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchRejectLetterPDF.class);
	String REJECT_LETTER_INTERFACE_STATUS_ERROR = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_STATUS_ERROR");
	String REJECT_LETTER_PDF_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_OUTPUT_NAME");
	String PDF_FIELD_DELIMETER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_FIELD_DELIMETER");
	String REJECT_LETTER_PDF_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_FILE_ENCODING");
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try {
			//ProcessTaskDataM processTask = new ProcessTaskDataM("REJECT_LETTER_PDF_TASK");
			ProcessTaskDataM processTask = new ProcessTaskDataM("REJECT_LETTER_PDF_ALL_APP_TASK");
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			ArrayList<TaskExecuteDataM> taskExecuteDataMList =	processTask.getTaskExecutes();
			
			int total = taskExecuteDataMList.size();
			int success = 0;
			int error = 0;
			int warning = 0;
			writeTextFiles(taskExecuteDataMList,infBatchResponse);
			if(InfBatchConstant.ResultCode.FAIL.equals(infBatchResponse.getResultCode())){
				insertInfBatchLog(taskExecuteDataMList);
			}
			
			for(TaskExecuteDataM taskExecute : taskExecuteDataMList) {
				if(InfBatchConstant.ResultCode.FAIL.equals(taskExecute.getResultCode())) {
					++error;
				}else if(InfBatchConstant.ResultCode.WARNING.equals(taskExecute.getResultCode())){
					warning++;
				}else {
					++success;
				}
			}
			
			infBatchResponse.setTotalSuccess(success);
			infBatchResponse.setTotalFail(error);
			infBatchResponse.setTotalTask(total);
			infBatchResponse.setTotalWarning(warning);
			infBatchResponse.setTotalValid(success+error);
			processTask.setConfigClassName(InfBatchProperty.getInfBatchConfig("REJECT_LETTER_RESULT"));	
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	private void writeTextFiles(ArrayList<TaskExecuteDataM>  taskExecuteDataMList,InfBatchResponseDataM infBatchResponse){
		OutputStreamWriter writer = null;
		try {
			String REJECT_LETTER_PDF_OUTPUT_PATH = PathUtil.getPath("REJECT_LETTER_PDF_OUTPUT_PATH");
			FileControl.mkdir(REJECT_LETTER_PDF_OUTPUT_PATH);
			String outputPath = REJECT_LETTER_PDF_OUTPUT_PATH +File.separator+RejectLetterUtil.generateRejectLetterParamFileName(REJECT_LETTER_PDF_OUTPUT_NAME);
			writer = new OutputStreamWriter(new FileOutputStream(outputPath), REJECT_LETTER_PDF_FILE_ENCODING);
//			Date headerDate = InfBatchProperty.getDateGeneralParam();
//				headerDate = (!InfBatchUtil.empty(headerDate))?headerDate:InfBatchProperty.getDate();
			Date headerDate = InfBatchProperty.getDate();
			if(!Util.empty(SystemConfig.getGeneralParam("CTE_DATE"))){
				headerDate =  InfBatchProperty.getDateGeneralParam();
			}
			writer.write("0"+PDF_FIELD_DELIMETER+Formatter.display(headerDate,Formatter.EN,Formatter.Format.YYYYMMDD));
			int totalRecord = 0;
			if(!InfBatchUtil.empty(taskExecuteDataMList)){
				for(TaskExecuteDataM taskExecute : taskExecuteDataMList){
					// KPL: if sub interface code is EDOC, skip writing text file
					String REJECT_LETTER_PDF_EDOC_SUBINTERFACE_CODE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_EDOC_SUBINTERFACE_CODE");
					RejectLetterInterfaceResponse  interfaceResponse =(RejectLetterInterfaceResponse)taskExecute.getResponseObject();
					if (InfBatchUtil.empty(interfaceResponse) || REJECT_LETTER_PDF_EDOC_SUBINTERFACE_CODE.equals(interfaceResponse.getSubInterfaceCode()) == false) {
						try{
							if(!InfBatchUtil.empty(taskExecute.getResultCode()) && taskExecute.getResultCode().equals(InfBatchConstant.ResultCode.SUCCESS)){
								totalRecord++;
							}
							RejectLetterInterfaceResponse  rejectLetterInterfaceResponse =(RejectLetterInterfaceResponse)taskExecute.getResponseObject();
							//logger.debug("rejectLetterInterfaceResponse : "+rejectLetterInterfaceResponse);					
							if(!InfBatchUtil.empty(rejectLetterInterfaceResponse)){
								if(!InfBatchUtil.empty(rejectLetterInterfaceResponse.getContentPDF())){
									writer.write(RejectLetterUtil.getNewLine()+rejectLetterInterfaceResponse.getContentPDF());
								}	
							}
						}catch(Exception e1){
							taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
							taskExecute.setResultDesc(e1.getLocalizedMessage());
						}
					}
				}
				//totalRecord = taskExecuteDataMList.size();
			}
			writer.write(RejectLetterUtil.getNewLineNuberTag("9",0));
			writer.write(PDF_FIELD_DELIMETER+RejectLetterUtil.getIndent(0)+String.valueOf(totalRecord));
		}catch(Exception e){
			logger.fatal("ERROR ",e);			
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());			
		}finally{
			if(null != writer){
				try{
					writer.close();
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
		}
	}
    private void insertInfBatchLog(ArrayList<TaskExecuteDataM>  taskExecuteDataMList){
      	 Connection conn = InfBatchObjectDAO.getConnection();	
      	 try {
      		InfDAO dao = InfFactory.getInfDAO();
      		conn.setAutoCommit(false);
      		if(!InfBatchUtil.empty(taskExecuteDataMList)){
      			for(TaskExecuteDataM taskExecute :taskExecuteDataMList){
					// KPL: if sub interface code is EDOC, skip stamp fail on INF_BATCH_LOG
					String REJECT_LETTER_PDF_EDOC_SUBINTERFACE_CODE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_EDOC_SUBINTERFACE_CODE");
					RejectLetterInterfaceResponse  interfaceResponse =(RejectLetterInterfaceResponse)taskExecute.getResponseObject();
					if (REJECT_LETTER_PDF_EDOC_SUBINTERFACE_CODE.equals(interfaceResponse.getSubInterfaceCode()) == false) {
						RejectLetterInterfaceResponse  rejectLetterInterfaceResponse = (RejectLetterInterfaceResponse)taskExecute.getResponseObject();
		   				if(!InfBatchUtil.empty(rejectLetterInterfaceResponse)){
	   						RejectLetterPDFDataM rejectLetetrPDFDataM =(RejectLetterPDFDataM)rejectLetterInterfaceResponse.getInterfaceObject();
	   						ArrayList<String> aplicationRecordIds = rejectLetetrPDFDataM.getApplicationRecordIds();
	   				   		 if(!InfBatchUtil.empty(aplicationRecordIds)){
	   				   			 for(String applicationRecordId : aplicationRecordIds){
	   				   				 InfBatchLogDataM infBatchLogDataM = new InfBatchLogDataM();
	   				   				 infBatchLogDataM.setApplicationRecordId(applicationRecordId);
	   				   				 infBatchLogDataM.setInterfaceCode(rejectLetterInterfaceResponse.getInterfaceCode());
	   				   				 infBatchLogDataM.setInterfaceStatus(REJECT_LETTER_INTERFACE_STATUS_ERROR);
	   				   				 infBatchLogDataM.setRefId(rejectLetterInterfaceResponse.getLetterNo());
	   				   				 infBatchLogDataM.setInterfaceDate(InfBatchProperty.getTimestamp());
	   				   				 infBatchLogDataM.setCreateDate(InfBatchProperty.getTimestamp());
	   				   				 dao.insertInfBatchLog(infBatchLogDataM, conn);
	   				   			 }
	   				   		 }
		   				}
					}
   				}		
      		}
      		conn.commit();
   		}catch (Exception e) {
   			logger.fatal("ERROR ",e);
   			try {
   				conn.rollback();
   			} catch (SQLException e1) {
   				logger.fatal("ERROR ",e1);
   			}			
   		}finally{
   			try{
   				InfBatchObjectDAO.closeConnection(conn);
   			}catch(Exception e){
   				logger.fatal("ERROR ",e);
   			}
   			
   		}
       }
}
