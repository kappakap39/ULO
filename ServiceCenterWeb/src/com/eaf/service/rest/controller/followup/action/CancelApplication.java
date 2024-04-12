package com.eaf.service.rest.controller.followup.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationManager;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.service.rest.controller.followup.FollowUpResultUtil;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.proxy.BpmProxyConstant.BPMActivity;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class CancelApplication extends ProcessActionHelper{
	private static Logger logger = Logger.getLogger(CancelApplication.class);
	String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
	String DECISION_ACTION_CANCEL = SystemConstant.getConstant("DECISION_ACTION_CANCEL");
	String SYSTEM_USER_ID =SystemConstant.getConstant("SYSTEM_USER_ID");
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	String REASON_CODE_OTH = SystemConstant.getConstant("REASON_CODE_OTH");	
	@Override
	public Object processAction(){
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			FollowUpResultApplicationDataM followUpResultApplication = (FollowUpResultApplicationDataM)objectForm;
			String userId = followUpResultApplication.getUserId();
			String applicationGroupId = followUpResultApplication.getApplicationGroupId();
			ApplicationGroupDataM applicationGroup = SubmitApplicationManager.loadApplicationGroup(applicationGroupId);
			applicationGroup.setUserId(userId);
			FollowUpResultUtil.mapFollowUpData(applicationGroup,followUpResultApplication);
			LookupServiceCenter.getServiceCenterManager().saveApplication(applicationGroup,userId,false);
			String followUpStatus = followUpResultApplication.getFollowUpStatus();
			logger.debug("applicationGroupId : "+applicationGroupId);
			logger.debug("followUpStatus : "+followUpStatus);
			cancelApplication(applicationGroup,followUpStatus,userId);
			processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}		 
		return processActionResponse;
	}
	public void cancelApplication(ApplicationGroupDataM applicationGroup,String followUpStatus,String userId) throws Exception{
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		logger.debug("applicationGroupId : "+applicationGroupId);
		ArrayList<String> cancelUniqueIds = new ArrayList<String>();
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		if(!Util.empty(applications)){
			for (ApplicationDataM application : applications) {
				cancelUniqueIds.add(application.getApplicationRecordId());
			}
		}
		logger.debug("cancelUniqueIds : "+cancelUniqueIds);
		ReasonDataM reasonM = new ReasonDataM();
		reasonM.setUpdateBy(userId);
		reasonM.setCreateBy(userId);
		reasonM.setReasonCode(REASON_CODE_OTH);
		reasonM.setReasonOthDesc("");
		reasonM.setReasonType(REASON_TYPE_CANCEL);
		reasonM.setRemark(followUpStatus);
		ApplicationReasonDataM applicationReason = mapApplicationReason(reasonM);
		LookupServiceCenter.getServiceCenterManager().cancelApplication(applicationGroupId,cancelUniqueIds,applicationReason,userId);
		OrigApplicationGroupDAO origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
		String BPM_DECISION_CANCEL = SystemConstant.getConstant("BPM_DECISION_CANCEL");
		logger.debug("BPM_DECISION_CANCEL : "+BPM_DECISION_CANCEL);
		origApplicationGroup.updateLastDecision(applicationGroupId, BPM_DECISION_CANCEL);
		String instantId = String.valueOf(applicationGroup.getInstantId());
		logger.debug("instantId : "+instantId);
		WorkflowRequestDataM workFlowRequest = new WorkflowRequestDataM();
			workFlowRequest.setInstantId(instantId);
			workFlowRequest.setActivity(BPMActivity.CANCEL_PROCESS);
			workFlowRequest.setUsername(userId);
			workFlowRequest.setUserId(userId);
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = workflowManager.cancelBPMInstance(workFlowRequest);
		logger.debug("workflowResponse.getResultCode : "+workflowResponse.getResultCode());
		logger.debug("workflowResponse.getResultDesc : "+workflowResponse.getResultDesc());
		if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
			throw new Exception(workflowResponse.getResultDesc());
		}
	}
	public static ApplicationReasonDataM mapApplicationReason(ReasonDataM reasonM){
		String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
		ArrayList<ReasonDataM> reasons = new ArrayList<>();
		if(null != reasonM){
			ReasonDataM reasonAppM = new ReasonDataM();
				reasonAppM.setCreateBy(reasonM.getCreateBy());
				reasonAppM.setUpdateBy(reasonM.getUpdateBy());
				reasonAppM.setReasonCode(reasonM.getReasonCode());
				reasonAppM.setReasonOthDesc(reasonM.getReasonOthDesc());
				reasonAppM.setReasonType(REASON_TYPE_CANCEL);
				reasonAppM.setRemark(reasonM.getRemark());	
				reasons.add(reasonAppM);
		}
		ApplicationReasonDataM applicationReason = new ApplicationReasonDataM();
			applicationReason.setReasons(reasons);
		return applicationReason;
	}
}