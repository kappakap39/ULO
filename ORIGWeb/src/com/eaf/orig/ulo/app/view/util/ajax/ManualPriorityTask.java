package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.NotifyMessage;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class ManualPriorityTask implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(ManualPriorityTask.class);
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.MANUAL_PRIORITY_TASK);
		try{
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			String[] CHECK_BOX_VALUE = request.getParameterValues("CHECK_BOX_VALUE");
			String PRIORITY = request.getParameter("PRIORITY");
			ApplicationGroupDataM  applicationGroup = new ApplicationGroupDataM();
			WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);			
			if(!Util.empty(CHECK_BOX_VALUE)){
				for(String checkbox :CHECK_BOX_VALUE){
					String[] valueList  = checkbox.split(",");					
					String appGroupStr = valueList[0];
					String instantIdStr = valueList[1];
					String taskIdStr =valueList[3];
					String APPLICATION_GROUP_ID = appGroupStr.substring("APPLICATION_GROUP_ID=".length());					 					
					String INSTANT_ID = instantIdStr.substring("INSTANT_ID=".length());					
					String TASK_ID = taskIdStr.substring("TASK_ID=".length());										 
					try{
						OrigApplicationGroupDAO origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
						applicationGroup = origApplicationGroup.loadOrigApplicationGroupM(APPLICATION_GROUP_ID);
//						applicationGroup.setApplicationGroupId(APPLICATION_GROUP_ID);
						applicationGroup.setInstantId(Integer.parseInt(INSTANT_ID));
						applicationGroup.setTaskId(TASK_ID);
						applicationGroup.setPriorityMode(PRIORITY);						
						workflowRequest = new WorkflowRequestDataM();
						workflowRequest.setApplicationGroup(applicationGroup);
						workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
						workflowRequest.setPriority(PRIORITY);		
						workflowRequest.setFromRole(userM.getCurrentRole());
						workflowRequest.setUsername(userM.getUserName());
						workflowRequest.setActionUser(userM.getUserName());
						workflowRequest.setActionRole(userM.getCurrentRole());
						WorkflowResponseDataM workflowResponse = workflowManager.setApplicationPriority(workflowRequest, applicationGroup.getTaskId());						
						origApplicationGroup.updatePriorityMode(applicationGroup);
						if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
							throw new Exception(workflowResponse.getResultDesc());
						}	
					}catch(Exception e){
						NotifyForm.error(request,NotifyMessage
								.errorProcessActionApplication(request,LabelUtil.getText(request,"MANUAL_SET_PRIORITY"),APPLICATION_GROUP_ID));
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
}
