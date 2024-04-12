package com.eaf.orig.ulo.control.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.NotifyDataM;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.action.Action;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.ejb.view.ApplicationManager;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.event.ApplicationEvent;
import com.eaf.orig.ulo.control.event.ApplicationEventResponse;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.proxy.BpmProxyConstant.BPMActivity;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowAction;
import com.orig.bpm.workflow.handle.WorkflowManager;
import com.orig.bpm.workflow.service.model.BPMApplicationLog;

public class ApplicationAction implements Action{
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(ApplicationAction.class);	
	@Override
	public EventResponse perform(Event ev) {
		ApplicationEvent event = (ApplicationEvent) ev;
		int eventType = event.getEventType();
		Object object = event.getObject();
		ApplicationGroupDataM applicationGroup = null;
		if(object instanceof ApplicationGroupDataM){
			applicationGroup = (ApplicationGroupDataM)object;
		}
		String transactionId = applicationGroup.getTransactionId();
		UserDetailM userM = event.getUserM();
		TraceController trace = new TraceController(this.getClass().getName(),transactionId);
		try{
			ApplicationManager manager = ORIGServiceProxy.getApplicationManager();
			String fraudFlag = applicationGroup.getFraudFlag();
			String blockedFlag = applicationGroup.getBlockedFlag();
			String cancelFlag = applicationGroup.getCancelFlag();
			logger.info("fraudFlag >> "+fraudFlag);
			logger.info("eventType >> "+eventType);
			logger.info("blockedFlag >> "+blockedFlag);
			logger.info("cancelFlag >> "+cancelFlag);	
			logger.info("callCompleteTaskFlag >> "+applicationGroup.getCallComplateTaskFlag() + " Application No : " + applicationGroup.getApplicationGroupNo());	
			switch (eventType) {
				case ApplicationEvent.SAVE_APPLICATION:
					trace.create("saveApplication");
					manager.saveApplication(applicationGroup,userM);	
					trace.end("saveApplication");
					if(MConstant.FLAG_Y.equals(cancelFlag)){
						applicationGroup.setDecisionAction(WorkflowAction.CANCEL_APPLICATION);
					}else if(MConstant.FLAG_Y.equals(fraudFlag)){
						applicationGroup.setDecisionAction(WorkflowAction.SAVE_AFTER_FRAUD);
					}else if(MConstant.FLAG_Y.equals(blockedFlag)){
						applicationGroup.setDecisionAction(WorkflowAction.SAVE_AFTER_DUPLICATE);
					}else{
						applicationGroup.setDecisionAction(WorkflowAction.SAVE);
					}
					workflowLog(applicationGroup,userM);					
					break;
				case ApplicationEvent.SUBMIT_APPLICATION:
					trace.create("saveApplication");
					manager.saveApplication(applicationGroup,userM);	
					trace.end("saveApplication");
					if(MConstant.FLAG_Y.equals(cancelFlag)){
						applicationGroup.setDecisionAction(WorkflowAction.CANCEL_APPLICATION);
						workflowLog(applicationGroup,userM);
					}else if(MConstant.FLAG_Y.equals(fraudFlag)){
						applicationGroup.setDecisionAction(WorkflowAction.SUBMIT_AFTER_FRAUD);
						workflowLog(applicationGroup,userM);
					}else if(MConstant.FLAG_Y.equals(blockedFlag)){
						applicationGroup.setDecisionAction(WorkflowAction.SAVE_AFTER_DUPLICATE);
						workflowLog(applicationGroup,userM);
					}else{
						trace.create("workflowAction");
						workflowAction(applicationGroup,userM);
						trace.end("workflowAction");
					}
					
					break;
				case ApplicationEvent.CANCEL_APPLICATION:
					manager.saveApplication(applicationGroup,userM);
					cancelApplication(applicationGroup,userM);					
					break;
				default:break;
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			NotifyDataM notify = NotifyForm.error(e);
			return new ApplicationEventResponse(event.getEventType(),EventResponseHelper.FAILED,notify);
		}
		trace.trace();
		return new ApplicationEventResponse(event.getEventType(),EventResponseHelper.SUCCESS);
	}	
	public void cancelApplication(ApplicationGroupDataM applicationGroup,UserDetailM userM) throws Exception{
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
		ApplicationReasonDataM applicationReason = mapApplicationReason(applicationGroup.getReason());
		ORIGServiceProxy.getApplicationManager().cancelApplication(applicationGroupId,cancelUniqueIds,applicationReason,userM);
		OrigApplicationGroupDAO origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
		String BPM_DECISION_CANCEL = SystemConstant.getConstant("BPM_DECISION_CANCEL");
		logger.debug("BPM_DECISION_CANCEL : "+BPM_DECISION_CANCEL);
		origApplicationGroup.updateLastDecision(applicationGroupId, BPM_DECISION_CANCEL);
		String instantId = String.valueOf(applicationGroup.getInstantId());
		logger.debug("instantId : "+instantId);
		WorkflowRequestDataM workFlowRequest = new WorkflowRequestDataM();
			workFlowRequest.setInstantId(instantId);
			workFlowRequest.setActivity(BPMActivity.CANCEL_PROCESS);
			workFlowRequest.setUsername(userM.getUserName());
			workFlowRequest.setUserId(userM.getUserName());
			workFlowRequest.setFromRole(userM.getCurrentRole());
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
	public void workflowLog(ApplicationGroupDataM applicationGroup,UserDetailM userM) throws Exception{
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		logger.debug("BPM_HOST >> "+BPM_HOST);
		logger.debug("BPM_PORT >> "+BPM_PORT);		
		WorkflowManager workflow = new WorkflowManager(BPM_HOST,BPM_PORT);
		BPMApplicationLog appLog = new BPMApplicationLog();
		appLog.setAction(applicationGroup.getDecisionAction());
		appLog.setAppGroupId(applicationGroup.getApplicationGroupId());
		appLog.setAppStatus(applicationGroup.getApplicationStatus());
		appLog.setJobState(applicationGroup.getJobState());
		appLog.setUsername(userM.getUserName());
		appLog.setToRole(userM.getCurrentRole());
		if(WorkflowAction.SAVE_AFTER_DUPLICATE.equals(applicationGroup.getDecisionAction())){
			appLog.setSpecialCondition("DUP");
		}
		WorkflowResponseDataM workflowResponse = workflow.addAppHistoryLog(appLog);
		logger.debug("workflowResponse.getResultCode >> "+workflowResponse.getResultCode());
		logger.debug("workflowResponse.getResultDesc >> "+workflowResponse.getResultDesc());
		if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
			throw new Exception(workflowResponse.getResultDesc());
		}
	}
	private void workflowAction(ApplicationGroupDataM applicationGroup,UserDetailM userM) throws Exception{
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		logger.debug("BPM_HOST >> "+BPM_HOST);
		logger.debug("BPM_PORT >> "+BPM_PORT);		
		logger.debug("applicationGroup.getDecisionAction() >> "+applicationGroup.getDecisionAction());		
		WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(userM.getUserName());	
			workflowRequest.setUsername(userM.getUserName());
			workflowRequest.setFormAction(applicationGroup.getFormAction());
			workflowRequest.setDecisionAction(applicationGroup.getDecisionAction());
			workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));	
			workflowRequest.setFraudActionType(applicationGroup.getFraudActionType());
			workflowRequest.setFraudDecision(applicationGroup.getFraudDecision());
			workflowRequest.setFromRole(userM.getCurrentRole());
			workflowRequest.setDecisionLog(WorkflowAction.getIncomeFlowAction(applicationGroup));
			workflowRequest.setEApp(ApplicationUtil.eApp(applicationGroup.getSource()) || ApplicationUtil.cjd(applicationGroup.getSource()));
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT);
		if(!MConstant.FLAG_N.equals(applicationGroup.getCallComplateTaskFlag())){
			WorkflowResponseDataM workflowResponse = workflowManager.completeWorkflowTask(workflowRequest,applicationGroup.getTaskId());
			logger.debug("workflowResponse.getResultCode >> "+workflowResponse.getResultCode());
			logger.debug("workflowResponse.getResultDesc >> "+workflowResponse.getResultDesc());
			if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
				DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
				throw new Exception(workflowResponse.getResultDesc());
			}
		}
	}
}
