package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.NotifyMessage;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.proxy.BpmProxyConstant.BPMActivity;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class IQCancelApplicationForm extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(IQCancelApplicationForm.class);
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
	
	@Override
	public Object getObjectForm() {
		String[] applicationGroupIds = request.getParameterValues("application_group_ids");
		if(null != applicationGroupIds){
			for (String applicationGroupId : applicationGroupIds) {
				setUniqueId(applicationGroupId);
			}
		}
		return new ReasonDataM();
	}
	
	@Override
	public String processForm() {
		logger.debug("processForm()..");	
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ReasonDataM reasonM = (ReasonDataM)objectForm;
		ArrayList<String> applicationGroupIds = getUniqueIds();		
		for(String applicationGroupId : applicationGroupIds){
			try{
				ApplicationReasonDataM applicationReason = mapApplicationReason(reasonM);	
				OrigApplicationGroupDAO origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
				int maxLifeCycle = origApplicationGroup.getMaxLifeCycle(applicationGroupId);
				logger.debug("maxLifeCycle : "+maxLifeCycle);
				ArrayList<String> cancelUniqueIds = ORIGDAOFactory.getApplicationDAO().loadApplicationUniqueIds(applicationGroupId,maxLifeCycle);
				logger.debug("cancelUniqueIds : "+cancelUniqueIds);
				ORIGServiceProxy.getApplicationManager().cancelApplication(applicationGroupId,cancelUniqueIds,applicationReason,userM);
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
			}catch(Exception e){
				logger.error("ERROR ",e);
				NotifyForm.error(request,NotifyMessage.errorProcessActionApplication(request,LabelUtil.getText(request,"CANCEL_APPLICATION"),applicationGroupId));
			}
		}
		return "";
	}
	public ApplicationReasonDataM mapApplicationReason(ReasonDataM reasonM){
		ReasonDataM reasonAppM = new ReasonDataM();
			reasonAppM.setCreateBy(reasonM.getCreateBy());
			reasonAppM.setUpdateBy(reasonM.getUpdateBy());
			reasonAppM.setReasonCode(reasonM.getReasonCode());
			reasonAppM.setReasonOthDesc(reasonM.getReasonOthDesc());
			reasonAppM.setReasonType(REASON_TYPE_CANCEL);
			reasonAppM.setRemark(reasonM.getRemark());		
		ArrayList<ReasonDataM> reasons = new ArrayList<>();
			reasons.add(reasonAppM);
		ApplicationReasonDataM applicationReason = new ApplicationReasonDataM();
			applicationReason.setReasons(reasons);
		return applicationReason;
	}
}
