package com.eaf.inf.batch.ulo.setuploan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.setuploan.model.SetupLoanResultDataM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;


public class SetupLoanResultTask implements TaskInf {
	private static transient Logger logger = Logger.getLogger(SetupLoanResultTask.class);
	private static final String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	private String inputFilename = InfBatchProperty.getInfBatchConfig("SETUP_LOAN_RESULT_INPUT_NAME");
	private String ORG_KPL = InfBatchProperty.getInfBatchConfig("ORG_KPL");
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	
	/**
	 * Reading fraud result file into array of TaskObjectDataM
	 */
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		FileReader reader = null;
		BufferedReader buffer =null;
		try {
			// Construct actual input file name
			//inputFilename = inputFilename.replaceFirst("yyyyMMdd", InfBatchSetupLoanResult.systemDate);
			String DATE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.YYYYMMDD);
			inputFilename = inputFilename.replaceFirst("yyyyMMdd", DATE);
			String inputPath = PathUtil.getPath("SETUP_LOAN_RESULT_INPUT_PATH");
			StringBuilder pathBuilder = new StringBuilder(inputPath).append(File.separator).append(inputFilename);
			String path = pathBuilder.toString();
			logger.debug("path>>"+path);
			
			int totalRecord = 0;
			
			// Read contents from input file to array of TaskObjectDataM
			reader = new FileReader(path);
			buffer =new BufferedReader(reader);
			String sReadline;
			int index = 0;
			//TODO Confirm field separator, header, footer, fields
			while((sReadline=buffer.readLine())!=null){
				int countPipe = sReadline.length() - sReadline.replace("|", "").length();
				logger.debug("countPipe : "+countPipe);
				if(sReadline.contains("|")) {								
					String[] listData = sReadline.split("\\|");
					String recordType = getValue(0,listData);
					if("H".equals(recordType))
					{
						//Header
					}
					else if("D".equals(recordType))
					{
						//Body
						String applicationGroupNo = getValue(1,listData);
						String result = getValue(8,listData);
						String accountNo = getValue(5,listData);
						String openDate = getValue(6,listData);
						String errMsg = getValue(10,listData) + " " 
						+ getValue(11,listData) + " " + getValue(12,listData)
						 + " " + getValue(13,listData);
						
						
						SetupLoanResultDataM setupLoanResult = new SetupLoanResultDataM();
						setupLoanResult.setApplicationGroupNo(applicationGroupNo);
						setupLoanResult.setResult(result);
						setupLoanResult.setAccountNo(accountNo);
						setupLoanResult.setOpenDate(openDate);
						setupLoanResult.setErrMsg(errMsg.trim());
						
						TaskObjectDataM queueObject = new TaskObjectDataM();
						queueObject.setUniqueId(applicationGroupNo);
						queueObject.setObject(setupLoanResult);
						taskObjects.add(queueObject);
					}
					else if("F".equals(recordType))
					{
						//Footer
						try
						{
							totalRecord = Integer.parseInt(getValue(1,listData));
						}
						catch(Exception e)
						{
							logger.error("Fail to parse totalRecord ", e);
						}
					}
					else
					{
						//Invalid record type
						throw new TaskException("Invalid recordType : " + recordType + " at line : " + (index+1));
					}
				}
				index++;
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new TaskException(e);
		}finally{
			try{
				if(null!=buffer){
					buffer.close();
				}
				if(null!=reader){
					reader.close();
				}
			}catch(Exception e2){
				logger.fatal("ERROR",e2);
			}
		}
		logger.debug("taskObjects size : "+taskObjects.size());
		return taskObjects;
	}

	
	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		String taskId = task.getTaskId();
		Connection conn = null;

		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setResponseObject(taskObject.getObject());
			logger.debug("taskId : "+taskId);
			
			InfBatchServiceLocator infBatchService = InfBatchServiceLocator.getInstance();
			conn = infBatchService.getConnection(InfBatchServiceLocator.ORIG_DB);
			conn.setAutoCommit(false);
			InfDAO infBatchLogDAO = InfFactory.getInfDAO();
			
			// Prepare request object 
			SetupLoanResultDataM setupLoanResult = (SetupLoanResultDataM)taskObject.getObject();
			String result = setupLoanResult.getResult();
			String applicationGroupNo = setupLoanResult.getApplicationGroupNo();
			String accountNo = setupLoanResult.getAccountNo();
			String slrErrMsg = setupLoanResult.getErrMsg();
			Timestamp openDate = setupLoanResult.getOpenDateAsTimestamp();
			OrigApplicationGroupDAO  origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
			String applicationGroupId = origApplicationGroup.getApplicationGroupIdByQr2(applicationGroupNo);
			
			if (Util.empty(applicationGroupNo) || Util.empty(result) || (!"0".equals(result) && !"1".equals(result))) {
				String errMsg = "ERROR: One of input fields is invalid (applicationGroupNo="+applicationGroupNo+",result="+result+",errMsg="+slrErrMsg+")";
				
				try
				{
					// Update claim status
					ORIGDAOFactory.getLoanSetupDAO().changeInfoAfterSetupLoan(applicationGroupNo, accountNo, openDate, SYSTEM_USER_ID, conn);					
					
					//Early Exit on failure/error from setupLoanResult
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
					infBatchLog.setCreateBy(SYSTEM_USER_ID);
					infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("SETUP_LOAN_RESULT_MODULE_ID"));
					infBatchLog.setApplicationGroupId(applicationGroupId);
					infBatchLog.setRefId(applicationGroupNo);
					infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
					infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
					infBatchLog.setSystem01(result); 
					infBatchLog.setLogMessage(errMsg); //set errorMsg to logMessage
					infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_ERROR);
					infBatchLogDAO.insertInfBatchLog(infBatchLog, conn);
					conn.commit();
				}
				catch(Exception e)
				{
					logger.fatal("ERROR ",e);
					conn.rollback();
					taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
					taskExecute.setResultDesc(e.getLocalizedMessage());
				}finally{
					if(null!=conn){
						conn.close();
					}
				}
				
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(errMsg);
				
				return taskExecute;
			}
			
			// Check with INF_BATCH_LOG whether this application group no had already processed or not
			InfBatchLogDataM infBatchLogDataM = infBatchLogDAO.getSetupLoanInfBatchLogByRefId(applicationGroupNo);
			if (null == infBatchLogDataM || !InfBatchConstant.STATUS_COMPLETE.equals(infBatchLogDataM.getInterfaceStatus())) {
				
				// Update claim status
				ORIGDAOFactory.getLoanSetupDAO().changeInfoAfterSetupLoan(applicationGroupNo, accountNo, openDate, SYSTEM_USER_ID, conn);
				// Find KPL Application
				ApplicationGroupDataM applicationGroupDataM = origApplicationGroup.loadOrigApplicationGroupM(applicationGroupId);
				ArrayList<ApplicationDataM> kplApp = applicationGroupDataM.filterApplicationLifeCycle(ORG_KPL);
				
				if(!Util.empty(accountNo))
				{
					if(kplApp != null)
					{
						// Update ORIG_LOAN - ACCOUNT_NO, ACCOUNT_OPEN_DATE
						String applicationRecordId = kplApp.get(0).getApplicationRecordId();
						logger.debug("updateKPLOpenLoanAccountInfo - applicationRecordId : " + applicationRecordId);
						ORIGDAOFactory.getLoanDAO().updateKPLOpenLoanAccountInfo(applicationRecordId, accountNo, openDate, conn);
					};
				}
				else
				{
					result += " - Account No is NULL";
				}
				
				
				// Stamp transaction to INF_BATCH_LOG
				InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
				infBatchLog.setCreateBy(SYSTEM_USER_ID);
				infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("SETUP_LOAN_RESULT_MODULE_ID"));
				infBatchLog.setApplicationGroupId(applicationGroupId);
				infBatchLog.setRefId(applicationGroupNo);
				infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
				infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
				infBatchLog.setSystem01(result);
				infBatchLog.setLogMessage(slrErrMsg);
				infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
				infBatchLogDAO.insertInfBatchLog(infBatchLog, conn);
				
				// We commit here, and have BPM result as status report. If BPM fail, fix BPM manually
				conn.commit();

				// Move Flow
				if(!Util.empty(accountNo))
				{
					ProcessActionResponse workflowResponse = workflowAction(applicationGroupDataM, SYSTEM_USER_ID);
					String workflowResultCode = workflowResponse.getResultCode();
					
					logger.debug("workflowResultCode : "+workflowResultCode);
					if(ServiceResponse.Status.SUCCESS.equals(workflowResultCode)) {
						if ("F".equals(result))
							taskExecute.setResultCode(InfBatchConstant.ResultCode.WARNING);
						else
							taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
					}
					else {
						taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
						taskExecute.setResultDesc(workflowResponse.getErrorMsg());
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			conn.rollback();
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return taskExecute;
	}

	private ProcessActionResponse workflowAction(ApplicationGroupDataM applicationGroup, String userId){
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{		
			WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
				workflowRequest.setApplicationGroup(applicationGroup);
				workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				workflowRequest.setUserId(userId);	
				workflowRequest.setUsername(userId);
				workflowRequest.setFormAction("");
				//workflowRequest.setDecisionAction(applicationGroup.getDecisionAction());
				workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));	

			logger.debug("workflowRequest.decisionAction >> "+workflowRequest.getDecisionAction());
			
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
			WorkflowResponseDataM workflowResponse = workflowManager.completeWorkflowTask(workflowRequest,null);
			
			logger.debug("workflowResponse.getResultCode >> "+workflowResponse.getResultCode());
			logger.debug("workflowResponse.getResultDesc >> "+workflowResponse.getResultDesc());
			
			if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
				processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
			}else{
				processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				processActionResponse.setErrorMsg(workflowResponse.getResultDesc());
				logger.error("WorkflowManager failed: " + workflowResponse.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return processActionResponse;
	}

	private String getValue(int index,String[] result){
		if(result.length<=index) return "";
			return result[index].trim();
	}
}

