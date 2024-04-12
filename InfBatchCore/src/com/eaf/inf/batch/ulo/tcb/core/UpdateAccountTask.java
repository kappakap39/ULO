package com.eaf.inf.batch.ulo.tcb.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.inf.batch.ulo.tcb.dao.TcbDAO;
import com.eaf.inf.batch.ulo.tcb.dao.TcbFactory;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class UpdateAccountTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(UpdateAccountTask.class);
	private static final String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	private String inputFilename = InfBatchProperty.getInfBatchConfig("SETUP_LOAN_RESULT_INPUT_NAME");
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
//	String TCB_KPL_TOP_UP_BUS_CLASS_ID = InfBatchProperty.getInfBatchConfig("TCB_KPL_TOP_UP_BUS_CLASS_ID");
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			taskObjects = TcbFactory.getTcbDAO().loadCISNo();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			TcbDAO tcbDAO = TcbFactory.getTcbDAO();
			UpdateListTaskObject data = (UpdateListTaskObject) task.getTaskObject();
			ArrayList<TaskObjectDataM> acctTaskObjectList =  tcbDAO.loadAccountNoFromDIH(data);
			// If get more than 1 account, then put as error to manually update
			if (acctTaskObjectList.size() > 1) {
				StringBuilder acctList = new StringBuilder("Found Lending Account > 1 after approval date ").append(data.getApproveDate()).append(" => [");
				String separator = "";
				for (TaskObjectDataM taskObject:acctTaskObjectList) {
					UpdateListTaskObject acctTaskObject = (UpdateListTaskObject) taskObject;
					acctList.append(separator).append("{acct: ").append(acctTaskObject.getAccountNo()).append(", openDate: ").append(acctTaskObject.getOpenDate()).append("}");
					separator = ",";
				}
				acctList.append("]");
				String errMsg = acctList.toString();
				logger.error(errMsg);
				//throw new TaskException(errMsg);
				
				//Count as success to not make batch fail.
				taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
				taskExecute.setResultDesc(errMsg);
				return taskExecute;
			}
			// Update account number and move flow to the end
			for (TaskObjectDataM taskObject:acctTaskObjectList) {
				UpdateListTaskObject acctTaskObject = (UpdateListTaskObject) taskObject;
				tcbDAO.updateAccountNo(acctTaskObject);
				// Move Flow
				OrigApplicationGroupDAO  origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
				String applicationGroupId = origApplicationGroup.getApplicationGroupIdByQr2(acctTaskObject.getApplicationGroupNo());
				ApplicationGroupDataM applicationGroupDataM = origApplicationGroup.loadOrigApplicationGroupM(applicationGroupId);
				ProcessActionResponse workflowResponse = workflowAction(applicationGroupDataM, SYSTEM_USER_ID);
				String workflowResultCode = workflowResponse.getResultCode();
				logger.debug("workflowResultCode : "+workflowResultCode);
				if(ServiceResponse.Status.SUCCESS.equals(workflowResultCode)) {
					taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
				}
				else {
					taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
					taskExecute.setResultDesc(workflowResponse.getErrorMsg());
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
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

	
}
