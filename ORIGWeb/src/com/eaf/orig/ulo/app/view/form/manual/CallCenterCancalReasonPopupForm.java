package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.NotifyMessage;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.control.action.ApplicationAction;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.proxy.BpmProxyConstant.BPMActivity;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class CallCenterCancalReasonPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(CallCenterCancalReasonPopupForm.class);
	String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	@Override
	public Object getObjectForm(){
		logger.debug("getFormObject()..");
		String rowData = getRequestData("rowData");
		logger.debug("rowData : "+rowData);
		return new ReasonDataM();
	}
	@Override
	public String processForm() {
		logger.debug("processForm()..");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String applicationGroupId = null;
		try{
			String rowData = getRequestData("rowData");
			logger.debug("rowData : "+rowData);
			JSONObject jsonData = new JSONObject(rowData);
			String rowType = JSONUtil.getString(jsonData,"rowtype");
			applicationGroupId = JSONUtil.getString(jsonData,"uniqueid");
			String applicationRecordId = JSONUtil.getString(jsonData,"itemid");
			logger.debug("rowType : "+rowType);
			logger.debug("applicationGroupId : "+applicationGroupId);
			logger.debug("applicationRecordId : "+applicationRecordId);
			ReasonDataM reasonM = (ReasonDataM)objectForm;			
			ApplicationReasonDataM applicationReason = mapApplicationReason(reasonM);	
			OrigApplicationGroupDAO origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
			int maxLifeCycle = origApplicationGroup.getMaxLifeCycle(applicationGroupId);
			logger.debug("maxLifeCycle : "+maxLifeCycle);
			ArrayList<String> cancelUniqueIds = new ArrayList<String>();
			if("group".equals(rowType)){				
				cancelUniqueIds = ORIGDAOFactory.getApplicationDAO().loadApplicationUniqueIds(applicationGroupId,maxLifeCycle);
			}else if("item".equals(rowType)){
				cancelUniqueIds.add(applicationRecordId);	
				cancelUniqueIds.addAll(ORIGDAOFactory.getApplicationDAO().loadRefUniqueIds(applicationGroupId, applicationRecordId));
			}
			logger.debug("cancelUniqueIds : "+cancelUniqueIds);
			ORIGServiceProxy.getApplicationManager().cancelApplication(applicationGroupId,cancelUniqueIds,applicationReason,userM);
			String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
			logger.debug("DECISION_FINAL_DECISION_CANCEL : "+DECISION_FINAL_DECISION_CANCEL);
			ArrayList<String> finalAppDecisions = ORIGDAOFactory.getApplicationDAO().loadFinalAppDecision(applicationGroupId,maxLifeCycle);
			boolean activityCancelWorkflow = activityCancelWorkflow(DECISION_FINAL_DECISION_CANCEL, finalAppDecisions);
			logger.debug("activityCancelWorkflow : "+activityCancelWorkflow);
			if(activityCancelWorkflow || "group".equals(rowType)){				
				String BPM_DECISION_CANCEL = SystemConstant.getConstant("BPM_DECISION_CANCEL");
				logger.debug("BPM_DECISION_CANCEL : "+BPM_DECISION_CANCEL);
				origApplicationGroup.updateLastDecision(applicationGroupId, BPM_DECISION_CANCEL);
				String instantId = origApplicationGroup.getInstantId(applicationGroupId);
				logger.debug("instantId : "+instantId);
				WorkflowRequestDataM workFlowRequest = new WorkflowRequestDataM();
					workFlowRequest.setInstantId(instantId);
					workFlowRequest.setActivity(BPMActivity.CANCEL_PROCESS);
					workFlowRequest.setUsername(userM.getUserName());
					workFlowRequest.setFromRole(userM.getCurrentRole());
				WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
				WorkflowResponseDataM workflowResponse = workflowManager.cancelBPMInstance(workFlowRequest);
				logger.debug("workflowResponse.getResultCode : "+workflowResponse.getResultCode());
				logger.debug("workflowResponse.getResultDesc : "+workflowResponse.getResultDesc());
				if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
					throw new Exception(workflowResponse.getResultDesc());
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			NotifyForm.error(request,NotifyMessage.errorProcessActionApplication(request,LabelUtil.getText(request,"CANCEL_APPLICATION"),applicationGroupId));
		}
		return "";
	}
	private boolean activityCancelWorkflow(String DECISION_FINAL_DECISION_CANCEL,ArrayList<String> finalAppDecisions){
		logger.debug("finalAppDecisions : "+finalAppDecisions);
		if(!Util.empty(finalAppDecisions)){
			for (String finalAppDecision : finalAppDecisions) {
				if(!DECISION_FINAL_DECISION_CANCEL.equals(finalAppDecision)){
					return false;
				}
			}
		}else{
			return false;
		}
		return true;
	}
	public ApplicationReasonDataM mapApplicationReason(ReasonDataM reasonM){
		return ApplicationAction.mapApplicationReason(reasonM);
	}
}
