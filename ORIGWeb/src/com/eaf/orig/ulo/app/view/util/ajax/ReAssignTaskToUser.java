package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.NotifyMessage;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ReAssignDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class ReAssignTaskToUser implements AjaxInf  {
	private static transient Logger logger = Logger.getLogger(ReAssignTaskToUser.class);
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String USER_NAME = SystemConfig.getProperty("BPM_USER_ID");
	String PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	String[] RE_ASSING_COMPLETE_JOB_STATE = SystemConstant.getArrayConstant("RE_ASSING_COMPLETE_JOB_STATE");
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.REASSIGN_TASK_TO_USER);
		try{
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			ArrayList<String> completeWFConditionList = new ArrayList<String>(Arrays.asList(RE_ASSING_COMPLETE_JOB_STATE));
			String[] CHECK_BOX_VALUE = request.getParameterValues("CHECK_BOX_VALUE");
			String REASSIGN_TO = request.getParameter("REASSIGN_TO");
			ReAssignDAO reassignDao = ModuleFactory.getReAssignDAO();
			boolean isLogOn = reassignDao.checkUserLogOnAndInboxFlag(REASSIGN_TO);
			if(!isLogOn){
				String errorMsg = MessageErrorUtil.getText(request,"ERROR_USER_IS_LOG_OFF");
				NotifyForm.warn(request,String.format(errorMsg,FormatUtil.displayText(REASSIGN_TO)));
				return null;
			}
			ApplicationGroupDataM applicationGroup = new ApplicationGroupDataM();
			WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,USER_NAME,PASSWORD);			 
			if(!Util.empty(CHECK_BOX_VALUE) &&  isLogOn){
				for(String valueList :CHECK_BOX_VALUE){  
					String[] ValueCh = valueList.split(",");		
					logger.debug("check box value list ::"+valueList);
					String appGroupIdStr = ValueCh[0];
					String instantIdStr = ValueCh[1];
					String taskIdStr =ValueCh[3];
					String jobStates =ValueCh[4];
					String APPLICATION_GROUP_ID = appGroupIdStr.substring("APPLICATION_GROUP_ID=".length());					 					
					String INSTANT_ID = instantIdStr.substring("INSTANT_ID=".length());					
					String TASK_ID = taskIdStr.substring("TASK_ID=".length());
					String JOB_STATE = jobStates.substring("JOB_STATE=".length());
					logger.debug("JOB_STATE ::"+JOB_STATE);
					logger.debug("INSTANT_ID ::"+INSTANT_ID);
					logger.debug("APPLICATION_GROUP_ID ::"+APPLICATION_GROUP_ID);
					try{							
						if(!Util.empty(APPLICATION_GROUP_ID) && !isClaimApplication(request,APPLICATION_GROUP_ID)){
							applicationGroup = new ApplicationGroupDataM();
							applicationGroup.setApplicationGroupId(APPLICATION_GROUP_ID);
							applicationGroup.setInstantId(Integer.parseInt(INSTANT_ID));
							applicationGroup.setTaskId(TASK_ID);							
							workflowRequest  = new WorkflowRequestDataM();
							mappingWorkflowRequest(applicationGroup,workflowRequest,REASSIGN_TO);
							workflowRequest.setUsername(userM.getUserName());
							workflowRequest.setFromRole(userM.getCurrentRole());
							workflowRequest.setActionUser(userM.getUserName());
							workflowRequest.setActionRole(userM.getCurrentRole());
							WorkflowResponseDataM workflowResponse =  null;
							if(completeWFConditionList.contains(JOB_STATE)){
								workflowRequest.setUsername(REASSIGN_TO);	//To reassign task in wait box, username is needed to be set, instead of userId
//								workflowResponse = workflowManager.completeWorkflowTask(workflowRequest, TASK_ID);
								workflowResponse =  workflowManager.reAssignTask(workflowRequest,TASK_ID, true);
							}else{
								workflowResponse =  workflowManager.reAssignTask(workflowRequest,TASK_ID, false);
							}
							if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
								logger.debug(">>>>wf reaspond>>"+workflowResponse.getResultDesc());
								throw new Exception(workflowResponse.getResultDesc());
							}else if(BpmProxyConstant.RestAPIResult.ERROR_TASK_UNASSIGNABLE.equals(workflowResponse.getResultCode())){
								NotifyForm.error(request,NotifyMessage.errorTaskUnassignable(request,APPLICATION_GROUP_ID));
							}						
						}
					}catch(Exception e){
						NotifyForm.error(request,NotifyMessage.errorProcessActionApplication(request,LabelUtil.getText(request,"RE_ASSIGN"),APPLICATION_GROUP_ID));
						logger.fatal("ERROR",e);
					}
				}
			}		
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
 
	@SuppressWarnings("unchecked")
	private boolean isClaimApplication(HttpServletRequest request,String applicationGroupId){
		try{
			SQLQueryEngine Query = new SQLQueryEngine();
			@SuppressWarnings("rawtypes")
			HashMap rowResult = Query.LoadModule("SELECT CLAIM_BY FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ?",applicationGroupId);
			String CLAIM_BY = SQLQueryEngine.display(rowResult,"CLAIM_BY");		
			logger.debug("CLAIM_BY >> "+CLAIM_BY);
			if(!Util.empty(CLAIM_BY)){
				NotifyForm.warn(request,NotifyMessage.errorClaimApplicationRunning(request,applicationGroupId));
				return true;
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return false;
	}	
	private void mappingWorkflowRequest(ApplicationGroupDataM applicationGroup,WorkflowRequestDataM workflowRequest,String reassignTo){
		workflowRequest.setApplicationGroup(applicationGroup);
		workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
		workflowRequest.setInstantId(FormatUtil.toString(applicationGroup.getInstantId()));
		workflowRequest.setUserId(reassignTo);
//		workflowRequest.setUsername(reassignTo);
	}
}
