package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.NotifyMessage;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class ReAllocateTask  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ReAllocateTask.class);
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String USER_NAME = SystemConfig.getProperty("BPM_USER_ID");
	String PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.REALLOCATE_TASK);
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		try{
			String[] CHECK_BOX_VALUE = request.getParameterValues("CHECK_BOX_VALUE");						
			ApplicationGroupDataM  applicationGroup = new ApplicationGroupDataM();
			WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,USER_NAME,PASSWORD);			
			if(!Util.empty(CHECK_BOX_VALUE)){
				for(String checkbox :CHECK_BOX_VALUE){
					String[] valueList  = checkbox.split(",");					
					String appGroupIdStr = valueList[0];
					String instantIdStr = valueList[1];
					String taskIdStr =valueList[3];
					String APPLICATION_GROUP_ID = appGroupIdStr.substring("APPLICATION_GROUP_ID=".length());					 					
					String INSTANT_ID = instantIdStr.substring("INSTANT_ID=".length());					
					String TASK_ID = taskIdStr.substring("TASK_ID=".length());
					logger.debug("APPLICATION_GROUP_ID ::"+APPLICATION_GROUP_ID);
					logger.debug("INSTANT_ID::"+INSTANT_ID);					
					try{
						if(!isClaimApplication(request,APPLICATION_GROUP_ID)){
							applicationGroup = new ApplicationGroupDataM();
							applicationGroup.setApplicationGroupId(APPLICATION_GROUP_ID);
							applicationGroup.setInstantId(Integer.parseInt(INSTANT_ID));
							applicationGroup.setTaskId(TASK_ID);							
							workflowRequest  = new WorkflowRequestDataM();
							mappingWorkflowRequest(applicationGroup,workflowRequest,userM.getUserName());	
							workflowRequest.setFromRole(userM.getCurrentRole());
							workflowRequest.setActionUser(userM.getUserName());
							workflowRequest.setActionRole(userM.getCurrentRole());
							WorkflowResponseDataM workflowResponse = workflowManager.reAlocateTask(workflowRequest);
							if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
								throw new Exception(workflowResponse.getResultDesc());
							}
						}
					}catch(Exception e){
						NotifyForm.error(request,NotifyMessage
								.errorProcessActionApplication(request,LabelUtil.getText(request,"RE_ALLOCATE"),APPLICATION_GROUP_ID));
						logger.fatal("ERROR ",e);
					}		
				}	
			}
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean isClaimApplication(HttpServletRequest request,String applicationGroupId){
		SQLQueryEngine Query = new SQLQueryEngine();
		HashMap rowResult = Query.LoadModule("SELECT CLAIM_BY FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ?",applicationGroupId);
		String CLAIM_BY = SQLQueryEngine.display(rowResult,"CLAIM_BY");		
		logger.debug("CLAIM_BY >> "+CLAIM_BY);
		if(!Util.empty(CLAIM_BY)){
			NotifyForm.warn(request,NotifyMessage.errorClaimApplicationRunning(request,applicationGroupId));
			return true;
		}
		return false;
	}
	private void mappingWorkflowRequest(ApplicationGroupDataM applicationGroup,WorkflowRequestDataM workflowRequest,String userName){
		workflowRequest.setApplicationGroup(applicationGroup);
		workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
		workflowRequest.setInstantId(FormatUtil.toString(applicationGroup.getInstantId()));
		workflowRequest.setUserId(userName);
		workflowRequest.setUsername(userName);
	}
}
