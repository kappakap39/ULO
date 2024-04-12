package com.eaf.inf.batch.ulo.kemail;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchKEmail extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchKEmail.class); 
	String K_EMAIL_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("K_EMAIL_FILE_ENCODING");
	String K_EMAIL_MODULE_ID = InfBatchProperty.getInfBatchConfig("K_EMAIL_MODULE_ID");
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try {
			 ProcessTaskDataM processTask = new ProcessTaskDataM("K_EMAIL_TASK");
			 ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			ArrayList<TaskExecuteDataM> taskExecuteList = processTask.getTaskExecutes();
			
			writeTextFiles(taskExecuteList, infBatchResponse, infBatchRequest.getSystemDate());
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
	public void writeTextFiles(ArrayList<TaskExecuteDataM> taskExecuteList, InfBatchResponseDataM infBatchResponse, String controlMDate) throws Exception{
		File outputFile = null;
		Connection conn = InfBatchObjectDAO.getConnection();
		try{
			conn.setAutoCommit(false);
			
			String FILE_PATH = PathUtil.getPath("K_EMAIL_OUTPUT_PATH");
			String K_EMAIL_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("K_EMAIL_OUTPUT_NAME");
			
			String DATE = (!Util.empty(controlMDate)) ? controlMDate : Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.YYYYMMDD);
			
			logger.debug("FILE_PATH >> "+FILE_PATH);
			FileControl.mkdir(FILE_PATH);
			
			int totalRecord = taskExecuteList.size();
			int successRecord = 0;
			StringBuilder stringBuilder = new StringBuilder();
			InfDAO dao = InfFactory.getInfDAO();
			for(TaskExecuteDataM taskExecute : taskExecuteList)
			{
				KEmailDataM kEmail = (KEmailDataM)taskExecute.getResponseObject();
				String accountNameTh = kEmail.getAccountNameThai();
				String accountNo = kEmail.getAccountNumber();
				logger.debug("accountNameTh : " + accountNameTh);
				logger.debug("accountNo : " + accountNo);
				
				try
				{
					//Build text content
					stringBuilder.append(kEmail.getCustomerName());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getDocumentPassword());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getChannel());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getCisID());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getCustomerType());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getRequestID());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getAccountNameEnglish());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getAccountNameThai());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getAccountNumber());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getCycle());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getEmailAddress());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getFormat());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getLanguage());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getProduct());
					stringBuilder.append("|");
					stringBuilder.append(kEmail.getSendMethod());
					stringBuilder.append("\n");
					
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
					infBatchLog.setSystem01(kEmail.getAccountNameThai());
					infBatchLog.setInterfaceCode(K_EMAIL_MODULE_ID);
					infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
					infBatchLog.setRefId(kEmail.getAccountNumber());
					infBatchLog.setApplicationGroupId(kEmail.getAppGroupId());
					infBatchLog.setApplicationRecordId(kEmail.getAppRecordId());
					infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
					infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
					dao.insertInfBatchLog(infBatchLog, conn);
					successRecord++;
				}
				catch(Exception e)
				{
					logger.debug("ERROR ",e);
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
					infBatchLog.setSystem01(kEmail.getAccountNameThai());
					infBatchLog.setInterfaceCode(K_EMAIL_MODULE_ID);
					infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_ERROR);
					infBatchLog.setRefId(kEmail.getAccountNumber());
					infBatchLog.setApplicationGroupId(kEmail.getAppGroupId());
					infBatchLog.setApplicationRecordId(kEmail.getAppRecordId());
					infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
					infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
					infBatchLog.setLogMessage(e.getMessage());
					dao.insertInfBatchLog(infBatchLog, conn);
				}
			}
			
			//Write file
			String outputPath = "";
			String contents = stringBuilder.toString();
			
			contents = StringUtils.chomp(contents);
			K_EMAIL_OUTPUT_NAME = K_EMAIL_OUTPUT_NAME.replace("YYYYMMDD", DATE);
			outputPath = FILE_PATH+File.separator + K_EMAIL_OUTPUT_NAME;
			outputFile = new File(outputPath);
			logger.debug("K-EMAIL OUTPUT FILE : " + outputFile);
			FileUtils.writeStringToFile(outputFile, contents, K_EMAIL_FILE_ENCODING);
			
			conn.commit();
			
			logger.debug("totalRecord : " + totalRecord);
			logger.debug("successRecord : " + successRecord);
			
		}catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR",e1);
			}
		}finally
		{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		System.gc();
	}
	
}
