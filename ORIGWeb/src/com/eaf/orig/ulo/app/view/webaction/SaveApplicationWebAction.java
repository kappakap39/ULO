package com.eaf.orig.ulo.app.view.webaction;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.engine.NotifyDataM;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.form.cis.compare.CISCompareAction;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.event.ApplicationEvent;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.BackWebActionUtil;
import com.eaf.orig.ulo.control.util.CancleClaimUtil;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.HistoryDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.google.gson.Gson;
import com.orig.bpm.workflow.handle.WorkflowAction;

@SuppressWarnings("serial")
public class SaveApplicationWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(SaveApplicationWebAction.class);
	int eventType = ApplicationEvent.SAVE_APPLICATION;
	String BUTTON_ACTION_SAVE = SystemConstant.getConstant("BUTTON_ACTION_SAVE");
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	String BUTTON_ACTION_SEND_BACK = SystemConstant.getConstant("BUTTON_ACTION_SEND_BACK");
	String SEND_BACK_DECISION = SystemConstant.getConstant("SEND_BACK_DECISION");
	String BUTTON_ACTION_SEND_TO_FU = SystemConstant.getConstant("BUTTON_ACTION_SEND_TO_FU");
	String BUTTON_ACTION_SEND_TO_FU_PEGA = SystemConstant.getConstant("BUTTON_ACTION_SEND_TO_FU_PEGA");
	String FICO_DECISION_FOLLOW_UP = SystemConstant.getConstant("FICO_DECISION_FOLLOW_UP");
	String BUTTON_ACTION_CANCEL = SystemConstant.getConstant("BUTTON_ACTION_CANCEL");
	ArrayList<String> ROLE_ALL_CA_INBOX = SystemConstant.getArrayListConstant("ROLE_ALL_CA_INBOX");
	String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
	
	public static final String FOLLOW_TO_PEGA_PROCESS_ACTION = "followUpToPega";
		
	@Override
	public Event toEvent() {
		logger.debug("eventType.."+eventType);
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(getRequest());
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		applicationGroup.setUserId(userM.getUserName());
		ApplicationEvent applicationEvent = new ApplicationEvent(eventType,applicationGroup,userM);
		return applicationEvent;
	}
	@Override
	public boolean requiredModelRequest() {
		return true;
	}
	@Override
	public boolean processEventResponse(EventResponse response) {
		return defaultProcessResponse(response);
	}
	@Override
	public boolean preModelRequest() {
		logger.debug("Business Logic Save Application..");
		String transactionId = (String)getRequest().getSession().getAttribute("transactionId");
		TraceController trace = new TraceController(this.getClass().getName(),transactionId);
		trace.create("preModelRequest");
		String buttonAction = getRequest().getParameter("buttonAction");
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		ORIGFormHandler ORIGForm = (ORIGFormHandler)getRequest().getSession().getAttribute("ORIGForm");
		String roleId = ORIGForm.getRoleId();
		logger.debug("roleId >> "+roleId);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		applicationGroup.setRoleId(roleId);
		applicationGroup.setTransactionId(transactionId);
		String formAction = applicationGroup.getFormAction();
		logger.debug("formAction : "+formAction);			
		if(BUTTON_ACTION_SUBMIT.equals(buttonAction)&& WorkflowAction.CANCEL_APPLICATION.equals(formAction)){
			buttonAction = BUTTON_ACTION_CANCEL;
		}
		
		//Add override logic for following KPL SavingPlus
		String SavingPlusFollowupAction = "FUSP";
		boolean isFUSP = false;
		if(!Util.empty(applicationGroup.getDecisionAction()) && SavingPlusFollowupAction.equals(applicationGroup.getDecisionAction()))
		{
			applicationGroup.setDecisionAction(FICO_DECISION_FOLLOW_UP);
			buttonAction = BUTTON_ACTION_SEND_TO_FU_PEGA;
			isFUSP = true;
		}
		
		logger.debug("buttonAction >> "+buttonAction);
		try{
			applicationGroup.setUserId(userM.getUserName());			
			if(BUTTON_ACTION_SAVE.equals(buttonAction)){
				eventType = ApplicationEvent.SAVE_APPLICATION;
			}else if(BUTTON_ACTION_SUBMIT.equals(buttonAction)){
				trace.create("ProcessAction");
				CISCompareAction cisCompareProcessor = new CISCompareAction();			
				HashMap<String,CompareDataM> cisCompareFields = cisCompareProcessor.processAction(applicationGroup, roleId);
				if(!Util.empty(cisCompareFields)){
					ComparisonGroupDataM comparisonGroup = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.CIS);
					if(null == comparisonGroup){
						logger.debug(">>>>>comparisonGroup is null >>>");
						comparisonGroup = new ComparisonGroupDataM();
						comparisonGroup.setSrcOfData(CompareDataM.SoruceOfData.CIS);
						applicationGroup.addComparisonGroups(comparisonGroup);
						comparisonGroup.setComparisonFields(cisCompareFields);
					}					
				}
				eventType = ApplicationEvent.SUBMIT_APPLICATION;
				String decisionAction = getRequest().getParameter("decisionAction");
				logger.debug("decisionAction >> "+decisionAction);
				if(!Util.empty(decisionAction)){
					applicationGroup.setDecisionAction(decisionAction);
				}
				try{
					ProcessActionInf processAction = ImplementControl.getProcessAction(ORIGFormHandler.PROCESS_APPLICATION_ACTION,roleId);
					if(null != processAction){
						processAction.init(getRequest(),buttonAction,applicationGroup);
						Object responseObject = processAction.processAction();
						if(null != responseObject && responseObject instanceof ProcessActionResponse){
							ProcessActionResponse processActionResponse = (ProcessActionResponse)responseObject;
							String processActionResult = processActionResponse.getResultCode();
							logger.debug("processActionResult >> "+processActionResult);
							if(null!=processActionResult && !ServiceResponse.Status.SUCCESS.contains(processActionResult)){
								NotifyForm.error(getRequest(),processActionResponse.getResultDesc());
								return false;
							}
						}
					}
				}catch(Exception e){
					NotifyForm.error(getRequest(),e);
					return false;
				}
				trace.end("ProcessAction");
				applicationGroup.setAuditLogFlag(MConstant.AuditFlag.AUDIT_DATA);
			}else if(BUTTON_ACTION_SEND_BACK.equals(buttonAction)){
				eventType = ApplicationEvent.SUBMIT_APPLICATION;
				applicationGroup.setDecisionAction(SEND_BACK_DECISION);
				applicationGroup.setAuditLogFlag(MConstant.AuditFlag.AUDIT_DATA);
			}else if(BUTTON_ACTION_SEND_TO_FU.equals(buttonAction)){
				eventType = ApplicationEvent.SUBMIT_APPLICATION;
				applicationGroup.setDecisionAction(FICO_DECISION_FOLLOW_UP);
			}else if(BUTTON_ACTION_SEND_TO_FU_PEGA.equals(buttonAction)){
				eventType = ApplicationEvent.SUBMIT_APPLICATION;
				applicationGroup.setDecisionAction(FICO_DECISION_FOLLOW_UP);
				try{
					ProcessActionInf processAction = ImplementControl.getProcessAction(FOLLOW_TO_PEGA_PROCESS_ACTION,roleId);
					if(null != processAction){
						processAction.init(getRequest(),getResponse(),applicationGroup);
						Object responseObject = processAction.processAction();
						if(null != responseObject && responseObject instanceof ProcessActionResponse){
							ProcessActionResponse processActionResponse = (ProcessActionResponse)responseObject;
							String processActionResult = processActionResponse.getResultCode();
							logger.debug("FOLLOW_TO_PEGA_PROCESS_ACTION.processActionResult : "+processActionResult);
							if(!ServiceResponse.Status.SUCCESS.equals(processActionResult)){
								NotifyForm.error(getRequest());
								return false;
							}
						}
					}
				}catch(Exception e){
					NotifyForm.error(getRequest());
					return false;
				}
				//Not Show PEGA warning message when sent to FU PEGA by DE1_2, CA role - KPL Additional
				//Defect#2995 //if(!ROLE_DE1_2.equals(roleId)  || (!Util.empty(ROLE_ALL_CA_INBOX) && !ROLE_ALL_CA_INBOX.contains(roleId)))
				//Change to not show PEGA warning if FUSP
				//Add logic show if not CJD
				if(!isFUSP && !ApplicationUtil.cjd(applicationGroup.getSource()))
				{NotifyForm.warn(getRequest(), MessageUtil.getText(getRequest(), "MESSAGE_FU_PEGA"));}
			}else if(BUTTON_ACTION_CANCEL.equals(buttonAction)){
				eventType = ApplicationEvent.CANCEL_APPLICATION;
				applicationGroup.setFormAction(WorkflowAction.CANCEL_APPLICATION);
			}
			logger.debug("applicationGroup >> "+applicationGroup);
			logger.debug("eventType >> "+eventType);

			String auditFlag = applicationGroup.getAuditLogFlag();
			logger.debug("auditFlag >> "+auditFlag);			
			ORIGForm.auditForm(getRequest());			
			DocumentCheckListUtil.defaultNotReceivedDocumentReason(applicationGroup);
			//to create history data
			Gson gson = new Gson();
			HistoryDataM histData = new HistoryDataM();
				histData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				histData.setRole(roleId);
				histData.setAppData(gson.toJson(applicationGroup));
				histData.setCreateBy(userM.getUserName());
				histData.setUpdateBy(userM.getUserName());
			applicationGroup.setHistoryData(histData);	
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			NotifyForm.error(getRequest());
			return false;
		}
		trace.end("preModelRequest");
		trace.trace();
		return true;
	}
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	@Override
	public String getNextActionParameter(){		
		return BackWebActionUtil.processBackWebAction(getRequest());
	}
	@Override
	protected void doSuccess(EventResponse erp) {
		CancleClaimUtil.cancleClaim(getRequest());
	}
	@Override
	protected void doFail(EventResponse erp) {
		NotifyDataM notify = erp.getNotify();
		if(null != notify){
			NotifyForm.error(getRequest(), notify);
		}else{
			NotifyForm.error(getRequest());
		}
	}
}
