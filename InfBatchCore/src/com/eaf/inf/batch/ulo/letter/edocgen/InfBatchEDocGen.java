package com.eaf.inf.batch.ulo.letter.edocgen;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
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
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryFactory;
import com.eaf.inf.batch.ulo.letter.history.model.LetterHistoryDataM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchEDocGen extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchEDocGen.class); 
	String APPROVE_LETTER_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_FILE_ENCODING");
	String REJECT_LETTER_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_FILE_ENCODING");
	String EDOCGEN_MODULE_ID = InfBatchProperty.getInfBatchConfig("EDOCGEN_MODULE_ID");
	String GENERIC_TEMPLATE = InfBatchProperty.getInfBatchConfig("GENERIC_TEMPLATE");
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try {
			 ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.EDOCGEN_TASK);
			 ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			ArrayList<TaskExecuteDataM> taskExecuteList = processTask.getTaskExecutes();
			
			writeTextFiles(taskExecuteList, infBatchResponse);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
	public void writeTextFiles(ArrayList<TaskExecuteDataM> taskExecuteList, InfBatchResponseDataM infBatchResponse) throws Exception{
		File file_AP = null;
		File file_RJ = null;
		Connection conn = InfBatchObjectDAO.getConnection();
		try{
			conn.setAutoCommit(false);
			
			String FILE_PATH = PathUtil.getPath("EDOCGEN_OUTPUT_PATH");
			String OUTPUT_FILE_NAME_AP = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_OUTPUT_NAME");
			String EMPTY_OUPUT_FILE_NAME_AP = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_OUTPUT_NAME_EMPTY");
			
			String OUTPUT_FILE_NAME_RJ = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_PAPER_T3");
			String EMPTY_OUPUT_FILE_NAME_RJ = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_PAPER_T3_EMPTY");
			
			String APPROVE_LETTER_HEADER_RECTYPE = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_HEADER_RECTYPE");
			String APPROVE_LETTER_DETAIL_RECTYPE = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_DETAIL_RECTYPE");
			String APPROVE_LETTER_TAILER_RECTYPE = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_TAILER_RECTYPE");
			
			String LETTER_CHANNEL_PAPER = InfBatchProperty.getInfBatchConfig("LETTER_CHANNEL_PAPER");
			String LETTER_CHANNEL_EMAIL = InfBatchProperty.getInfBatchConfig("LETTER_CHANNEL_EMAIL");
			String LETTER_HIST_RESEND_TYPE_EMAIL = InfBatchProperty.getInfBatchConfig("LETTER_HIST_RESEND_TYPE_EMAIL");
			String LETTER_HIST_RESEND_TYPE_PAPER = InfBatchProperty.getInfBatchConfig("LETTER_HIST_RESEND_TYPE_PAPER");
			
			String EDOCGEN_LETTER_TYPE_APPROVE = InfBatchProperty.getInfBatchConfig("EDOCGEN_LETTER_TYPE_APPROVE");
			String EDOCGEN_LETTER_TYPE_REJECT = InfBatchProperty.getInfBatchConfig("EDOCGEN_LETTER_TYPE_REJECT");
		
			String DATE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.yyyy_MM_dd);
			
			logger.debug("FILE_PATH >> "+FILE_PATH);
			FileControl.mkdir(FILE_PATH);
			
			String CONTEXT_AP = "";
			String CONTEXT_RJ = "";
			String header = "";
			String detail_AP = "";
			String detail_RJ = "";
			String tailer_AP = "";
			String tailer_RJ = "";
			int totalRecord_AP = 0;
			int totalRecord_RJ = 0;
			
			//Header
			header = APPROVE_LETTER_HEADER_RECTYPE + "|" + DATE;
			
			InfDAO dao = InfFactory.getInfDAO();
			for(TaskExecuteDataM taskExecute : taskExecuteList){
				LetterHistoryDataM letterHistory = (LetterHistoryDataM)taskExecute.getResponseObject();
				String letterNo = letterHistory.getLetterNo();
				String letetrType = letterHistory.getLetterType();
				logger.debug("letterNo : "+letterNo);
				if(!InfBatchUtil.empty(letterNo)){

					try{
						//TODO Build EDOCGEN text with data from LETTER_HISTORY
						
						//Customer
						String cusSendFlag = letterHistory.getCustomerSendFlag();
						if(MConstant.FLAG.YES.equals(cusSendFlag) || MConstant.FLAG_R.equals(cusSendFlag))
						{
							String cusSendMethod = letterHistory.getCustomerSendMethod();
							String cusReSendMethod = letterHistory.getCustomerResendSendMethod();
							
							//Convert resend method of 01-Email, 02-Paper to EDoc Gen format 3-Email, 1-Paper
							if(LETTER_HIST_RESEND_TYPE_EMAIL.equals(cusReSendMethod))
							{cusReSendMethod = LETTER_CHANNEL_EMAIL;}
							else if(LETTER_HIST_RESEND_TYPE_PAPER.equals(cusReSendMethod))
							{cusReSendMethod = LETTER_CHANNEL_PAPER;}
							
							boolean isEmail = false;
							String emailPrimary = "";
							
							if(MConstant.FLAG.YES.equals(cusSendFlag) && LETTER_CHANNEL_EMAIL.equals(cusSendMethod))
							{
								isEmail = true;
								emailPrimary = letterHistory.getCustomerEmailAddress();
							}
							else if(MConstant.FLAG_R.equals(cusSendFlag) && LETTER_CHANNEL_EMAIL.equals(cusReSendMethod))
							{
								isEmail = true;
								emailPrimary = letterHistory.getCustomerResendEmailAddress();
							}
							
							String detailTemp = letterHistory.getLetterContent();
							detailTemp = detailTemp.replace("{SEND_METHOD}", (MConstant.FLAG_R.equals(cusSendFlag)) ? cusReSendMethod : cusSendMethod);
							detailTemp = detailTemp.replace("{EMAIL_PRIMARY}", emailPrimary);
							detailTemp = detailTemp.replace("{EDOC_EMAIL_TEMPLATE}", (isEmail) ? letterHistory.getCustomerEmailTemplate() : "");
							detailTemp = detailTemp.replace("{LANGUAGE}", letterHistory.getCustomerLanguage());
							detailTemp = detailTemp.replace("{ATTACH_FILE}", (isEmail) ? "1" : "0");
							detailTemp = detailTemp.replace("{MASTER_OR_COPY}", (MConstant.FLAG_R.equals(cusSendFlag)) ? MConstant.FLAG_C : MConstant.FLAG_M);
							
							if(EDOCGEN_LETTER_TYPE_APPROVE.equals(letetrType))
							{
								detail_AP += "\n" + detailTemp;
								totalRecord_AP++;
							}
							else
							{
								detail_RJ += "\n" + detailTemp;
								totalRecord_RJ++;
							}
							
							//Change CustomerSendFlag of this letterNo to C
							LetterHistoryFactory.getLetterHistoryDAO().updateSendFlag(MConstant.FLAG_C, letterNo, MConstant.FLAG_C, conn);
						}
						
						//Seller
						String sellerSendFlag = letterHistory.getSellerSendFlag();
						if(MConstant.FLAG.YES.equals(sellerSendFlag))
						{
							String sellerSendMethod = letterHistory.getSellerSendMethod();
							boolean isEmailS = LETTER_CHANNEL_EMAIL.equals(sellerSendMethod);
							
							String detailTemp = letterHistory.getLetterContent();
							if(!Util.empty(detailTemp))
							{
								detailTemp = detailTemp.replace("{SEND_METHOD}", letterHistory.getSellerSendMethod());
								detailTemp = detailTemp.replace("{EMAIL_PRIMARY}", (isEmailS) ? letterHistory.getSellerEmailAddress() : "");
								detailTemp = detailTemp.replace("{EDOC_EMAIL_TEMPLATE}", (isEmailS) ? letterHistory.getSellerEmailTemplate() : "");
								detailTemp = detailTemp.replace("{LANGUAGE}", letterHistory.getSellerLanguage());
								detailTemp = detailTemp.replace("{ATTACH_FILE}", (isEmailS) ? "1" : "0");
								detailTemp = detailTemp.replace("{MASTER_OR_COPY}", MConstant.FLAG_M);
							}
							
							if(EDOCGEN_LETTER_TYPE_APPROVE.equals(letetrType))
							{
								detail_AP += "\n" + detailTemp;
								totalRecord_AP++;
							}
							else
							{
								detail_RJ += "\n" + detailTemp;
								totalRecord_RJ++;
							}
							
							//Change SellerSendFlag of this letterNo to C
							LetterHistoryFactory.getLetterHistoryDAO().updateSendFlag(MConstant.FLAG_S, letterNo, MConstant.FLAG_C, conn);
						}
						
						//Insert to batch log
						InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setInterfaceCode(EDOCGEN_MODULE_ID);
						infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
						infBatchLog.setRefId(letterNo);
						infBatchLog.setSystem01(letterHistory.getAppGroupNo());
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						dao.insertInfBatchLog(infBatchLog, conn);
						
					}catch(Exception e){
						logger.debug("ERROR ",e);
						InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
							infBatchLog.setSystem01(letterHistory.getAppGroupNo());
							infBatchLog.setInterfaceCode(EDOCGEN_MODULE_ID);
							infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_ERROR);
							infBatchLog.setRefId(letterNo);
							infBatchLog.setLogMessage(e.getMessage());
							infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
							infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						dao.insertInfBatchLog(infBatchLog, conn);
					}
				}
			}
			
			//Tailer
			tailer_AP = "\n" + APPROVE_LETTER_TAILER_RECTYPE + "|" + totalRecord_AP;
			tailer_RJ = "\n" + APPROVE_LETTER_TAILER_RECTYPE + "|" + totalRecord_RJ;
			
			CONTEXT_AP = header + detail_AP + tailer_AP;
			CONTEXT_RJ = header + detail_RJ + tailer_RJ;
			
			if(!InfBatchUtil.empty(CONTEXT_AP))
			{
				CONTEXT_AP = CONTEXT_AP.replaceAll("\n", System.lineSeparator());
				OUTPUT_FILE_NAME_AP = OUTPUT_FILE_NAME_AP.replace("YYYYMMDD", DATE);
				
				file_AP = new File(FILE_PATH+File.separator+OUTPUT_FILE_NAME_AP);
				FileUtils.writeStringToFile(file_AP, CONTEXT_AP,APPROVE_LETTER_FILE_ENCODING);
			}else
			{
				EMPTY_OUPUT_FILE_NAME_AP = EMPTY_OUPUT_FILE_NAME_AP.replace("YYYYMMDD", DATE);
				file_AP = new File(FILE_PATH+File.separator+EMPTY_OUPUT_FILE_NAME_AP);
				FileUtils.writeStringToFile(file_AP, CONTEXT_AP,APPROVE_LETTER_FILE_ENCODING);
			}
			
			if(!InfBatchUtil.empty(CONTEXT_RJ))
			{
				CONTEXT_RJ = CONTEXT_RJ.replaceAll("\n", System.lineSeparator());
				OUTPUT_FILE_NAME_RJ = OUTPUT_FILE_NAME_RJ.replace("YYYYMMDD", DATE);
				
				file_RJ = new File(FILE_PATH+File.separator+OUTPUT_FILE_NAME_RJ);
				FileUtils.writeStringToFile(file_RJ, CONTEXT_RJ,REJECT_LETTER_FILE_ENCODING);
			}else
			{
				EMPTY_OUPUT_FILE_NAME_RJ = EMPTY_OUPUT_FILE_NAME_RJ.replace("YYYYMMDD", DATE);
				file_RJ = new File(FILE_PATH+File.separator+EMPTY_OUPUT_FILE_NAME_RJ);
				FileUtils.writeStringToFile(file_RJ, CONTEXT_RJ,REJECT_LETTER_FILE_ENCODING);
			}
			
			logger.debug("KPL - EDOCGEN AP OUTPUT FILE : "+file_AP);
			logger.debug("KPL - EDOCGEN RJ OUTPUT FILE : "+file_RJ);
			
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR",e1);
			}
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		System.gc();
	}
	
}
