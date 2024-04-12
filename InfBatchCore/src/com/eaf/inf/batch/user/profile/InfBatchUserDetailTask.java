package com.eaf.inf.batch.user.profile;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.batch.core.dao.BatchUserDetailDAO;
import com.eaf.batch.core.dao.BatchUserDetailDAOImpl;
import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class InfBatchUserDetailTask implements TaskInf{
	private static transient Logger logger = Logger.getLogger(InfBatchUserDetailTask.class);
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> tasks = new ArrayList<TaskObjectDataM>();
		try{
			BatchUserDetailDAO userDetailDAO = new BatchUserDetailDAOImpl();
			String USERDETAIL_DATE_EXPIRE = InfBatchProperty.getGeneralParam("USERDETAIL_DATE_EXPIRE");
			logger.debug("USERDETAIL_DATE_EXPIRE : "+USERDETAIL_DATE_EXPIRE);
			List<String> expireUsers = userDetailDAO.getexpireUsers(USERDETAIL_DATE_EXPIRE);
			if(null!=expireUsers){
				for(String userId : expireUsers){
					TaskObjectDataM task = new TaskObjectDataM();
					task.setUniqueId(userId);
					tasks.add(task);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new TaskException(e);
		}
		return tasks;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			String uniqueId = taskObject.getUniqueId();
			logger.debug("userId : "+uniqueId);
			BatchUserDetailDAO userDetailDAO = new BatchUserDetailDAOImpl();
			userDetailDAO.deleteOrganizationChart(uniqueId);
			userDetailDAO.deleteUserRole(uniqueId);
			String BPM_HOST = InfBatchProperty.getInfBatchConfig("BPM_HOST");
			String BPM_PORT = InfBatchProperty.getInfBatchConfig("BPM_PORT");
			String BPM_USER_ID = InfBatchProperty.getInfBatchConfig("BPM_USER_ID");
			String BPM_PASSWORD = InfBatchProperty.getInfBatchConfig("BPM_PASSWORD");
			WorkflowManager manager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
			WorkflowResponseDataM workflowResponse = manager.removeUserInGroup(uniqueId);
			if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
				taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			}else{
				taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
				taskExecute.setResultDesc(workflowResponse.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}

}
