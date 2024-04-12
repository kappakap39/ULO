package com.eaf.inf.batch.ulo.applicationcardlink;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class CloseCardLinkTask  implements TaskInf{
	private static transient Logger logger = Logger.getLogger(CloseCardLinkTask.class);
	String CLOSE_APPLICATION_CARD_LINK_URL = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_URL");
	String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			CloseApplicationCardLinkDAO dao = ApplicationCardLinkFactory.getCloseApplicationCardLinkDAO();
				taskObjects = dao.selectApplicationCardlink();
		}catch(Exception e){
			logger.debug("ERROR",e);
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task){
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();		
		TaskObjectDataM taskObject = task.getTaskObject();
		String instantId = taskObject.getUniqueId();
		String BPM_HOST = InfBatchProperty.getInfBatchConfig("BPM_HOST");
		String BPM_PORT = InfBatchProperty.getInfBatchConfig("BPM_PORT");
		String USER_NAME = InfBatchProperty.getInfBatchConfig("BPM_USER_ID");
		String PASSWORD = InfBatchProperty.getInfBatchConfig("BPM_PASSWORD");
		String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
		try{
			if(!InfBatchUtil.empty(instantId)){
				logger.debug("instantId : "+instantId);
				WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,USER_NAME,PASSWORD);
				WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
					workflowRequest.setUserId(SYSTEM_USER_ID);
					workflowRequest.setInstantId(instantId);
					workflowRequest.setApplicationGroup(new ApplicationGroupDataM());
				WorkflowResponseDataM workflowResponse = workflowManager.completeWorkflowTask(workflowRequest,null);
				logger.debug("workflowResponse : "+workflowResponse.getResultCode());
				if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
					taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
					taskExecute.setResultDesc(workflowResponse.getResultDesc());
				}else{
					taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
