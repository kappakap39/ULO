package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.NotifyMessage;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.ejb.view.ApplicationManager;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.control.util.CheckFraudApplicationAction;
import com.eaf.orig.ulo.model.app.FraudRemarkDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.proxy.BpmProxyConstant.BPMActivity;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class SendApplicationToFraudPopupForm extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(SendApplicationToFraudPopupForm.class);
	String WIP_JOBSTATE_FRAUD = SystemConfig.getGeneralParam("WIP_JOBSTATE_FRAUD");
	@Override
	public Object getObjectForm() {	
		logger.debug("getFormObject()..");
		String[] applicationGroupIds = request.getParameterValues("application_group_ids");
		logger.debug("applicationGroupIds : "+applicationGroupIds);
		if(null != applicationGroupIds){
			for (String applicationGroupId : applicationGroupIds) {
				setUniqueId(applicationGroupId);
			}
		}
		return new FraudRemarkDataM();
	}
	@Override
	public String processForm() {
		logger.debug("processForm()..");
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");		
		ArrayList<String> applicationGroupIds = getUniqueIds();
		logger.debug("applicationGroupIds : "+applicationGroupIds);
		FraudRemarkDataM fraudRemarkM = (FraudRemarkDataM)objectForm;
		if(null == fraudRemarkM){
			fraudRemarkM = new FraudRemarkDataM();
		}
		UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
		String userId = userM.getUserName();
		String fraudRemark = fraudRemarkM.getRemark();
		logger.debug("fraudRemark : "+fraudRemark);
		logger.debug("userId : "+userId);
		if(null != applicationGroupIds){
			ArrayList<String> fruadApplicationIds =  fruadApplicationGroupIds();
			ApplicationManager manager = ORIGServiceProxy.getApplicationManager();
			OrigApplicationGroupDAO origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
			for(String applicationGroupId : applicationGroupIds){
				if(!fruadApplicationIds.contains(applicationGroupId)){
					try{
						manager.updateFraudRemark(applicationGroupId,fraudRemark,userId);
						WorkflowRequestDataM workFlowRequest = new WorkflowRequestDataM();
						String instantId = origApplicationGroup.getInstantId(applicationGroupId);
						logger.debug("instantId : "+instantId);
						workFlowRequest.setInstantId(instantId);
						workFlowRequest.setActivity(BPMActivity.FRAUD_PROCESS);
						workFlowRequest.setFromRole(userM.getCurrentRole());
						workFlowRequest.setUsername(userM.getUserName());
						workFlowRequest.setUniqueId(applicationGroupId);
						WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
						WorkflowResponseDataM workflowResponse = workflowManager.moveWorkflowActivity(workFlowRequest, BPMActivity.FRAUD_PROCESS);
						logger.debug("workflowResponse.getResultCode : "+workflowResponse.getResultCode());
						logger.debug("workflowResponse.getResultDesc : "+workflowResponse.getResultDesc());
						if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
							throw new Exception(workflowResponse.getResultDesc());
						}
					}catch(Exception e){
						logger.fatal("ERROR",e);
						NotifyForm.error(request,NotifyMessage.errorProcessActionApplication(request
								,LabelUtil.getText(request,"SEND_APPLICATION_TO_FRAUD"),applicationGroupId));
					}
				}
			}
		}
		return "";
	}
	
	private ArrayList<String> fruadApplicationGroupIds(){
		ArrayList<String> fruadApplicationIds = new ArrayList<String>();
		CheckFraudApplicationAction checkFraudApplicationAction = new CheckFraudApplicationAction();
		ArrayList<String> applicationGroupIds =  getUniqueIds();
		if(!Util.empty(applicationGroupIds)){
			SQLQueryEngine Query = new SQLQueryEngine();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT JOB_STATE, APPLICATION_GROUP_NO ,APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID IN (");
			String COMMA="";
			for(String applicationGroupId :applicationGroupIds){
				sql.append(COMMA+"'"+applicationGroupId+"'");
				COMMA=",";
			}					
			sql.append(")");		
			Vector<HashMap>  rowResults =	Query.LoadModuleList(sql.toString());	
			if(!Util.empty(rowResults)){
				for(HashMap rowResult : rowResults){
					String JOB_STATE = SQLQueryEngine.display(rowResult,"JOB_STATE");
					String APPLICATION_GROUP_NO = SQLQueryEngine.display(rowResult,"APPLICATION_GROUP_NO");
					String APPLICATION_GROUP_ID = SQLQueryEngine.display(rowResult,"APPLICATION_GROUP_ID");
					
					if(WIP_JOBSTATE_FRAUD.contains(JOB_STATE)){
						fruadApplicationIds.add(APPLICATION_GROUP_ID);
						checkFraudApplicationAction.addFruadApplicationGroupNo(APPLICATION_GROUP_NO);
					}
				}
			}
		}
		request.getSession().setAttribute(CheckFraudApplicationAction.SEND_TO_FRAUD, checkFraudApplicationAction);
		return fruadApplicationIds;
	}
}
