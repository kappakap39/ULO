package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.WorkFlowDecisionM;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;
import com.orig.bpm.workflow.service.model.BPMCAAuthorityServiceResp;

public class SummaryNormalForm extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(SummaryNormalForm.class);
	private String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	private String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String DECISION_ESCALATE  = SystemConstant.getConstant("BPM_DECISION_ESCALATE");
	String DECISION_REJECT = SystemConstant.getConstant("FICO_DECISION_REJECTED");		
 	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
 	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
 	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
 	String FINAL_APP_DECISION_APPROVE = SystemConstant.getConstant("FINAL_APP_DECISION_APPROVE");
 	String CA_FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("CA_FINAL_APP_DECISION_REJECT");
 	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
 	String[] DECISION_FINAL_DECISION_CONDITION = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_CONDITION");
 	String  DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
 	String  DECISION_FINAL_DECISION_APPROVE = SystemConstant.getConstant("DECISION_FINAL_DECISION_APPROVE");
 	String  CA_DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	@Override
	public Object getObjectForm(){
		String DECISION_ESCALATE_DESC   = MessageUtil.getText(request, "DECISION_ESCALATE_DESC");
		String DECISION_ESCALATE_MSG  = MessageUtil.getText(request, "DECISION_ESCALATE_MSG");	
		String DECISION_REJECT_DESC = MessageUtil.getText(request, "DECISION_REJECT_DESC");
		String DECISION_REJECT_MSG_ALL_OR_POLICIES  = MessageUtil.getText(request, "DECISION_REJECT_MSG_ALL_OR_POLICIES");
		String DECISION_REJECT_MSG_APP_FICO  = MessageUtil.getText(request, "DECISION_REJECT_MSG_APP_FICO");
		String DECISION_REJECT_MSG_DEBT_BURDEN  = MessageUtil.getText(request, "DECISION_REJECT_MSG_DEBT_BURDEN");
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		ArrayList<WorkFlowDecisionM> workFlowDecisionList =  new ArrayList<WorkFlowDecisionM>();	
		String callDecisionFlag = MConstant.FLAG_Y;
		try{
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			if(!rejectCancelApplicationDecision(applicationGroup)){  //CALL ESCALATE
				WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT);
				WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
				workflowRequest.setApplicationGroup(applicationGroup);
				workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				workflowRequest.setUserId(userM.getUserName());	
				workflowRequest.setUsername(userM.getUserName());
				workflowRequest.setFromRole(userM.getCurrentRole());
				if(ApplicationUtil.isRejectDecision(applicationGroup)){
					workflowRequest.setDecisionAction(DECISION_REJECT);
				}
				applicationGroup.setCalledEscalateFlag(MConstant.FLAG_N);
				BPMCAAuthorityServiceResp workflowResponse = workflowManager.verifyCAAuthority(workflowRequest);
				if(!Util.empty(workflowResponse)){
					String decisionAction = workflowResponse.getDecision();
					logger.debug("decisionAction >> "+decisionAction);
					if(DECISION_ESCALATE.equals(workflowResponse.getDecision())){
						applicationGroup.setCalledEscalateFlag(MConstant.FLAG_Y);
						WorkFlowDecisionM workFlowDecision = new WorkFlowDecisionM();
						workFlowDecision.setDecision(decisionAction);
						workFlowDecision.setDecisionDesc(DECISION_ESCALATE_DESC);
						workFlowDecision.setMessage(DECISION_ESCALATE_MSG);
						workFlowDecisionList.add(workFlowDecision);
						callDecisionFlag = MConstant.FLAG_N;
					}else if(DECISION_REJECT.equals(workflowResponse.getDecision())){						
						if(workflowResponse.getOverAllPolicy()){
							WorkFlowDecisionM workFlowDecision = new WorkFlowDecisionM();
							workFlowDecision.setDecision(decisionAction);
							workFlowDecision.setDecisionDesc(DECISION_REJECT_DESC);
							workFlowDecision.setMessage(DECISION_REJECT_MSG_ALL_OR_POLICIES);
							workFlowDecisionList.add(workFlowDecision);
							ORIGForm.getObjectForm().setOverPercentORFlag(MConstant.FLAG_Y);
							setRejectDecision(applicationGroup);
						}
						if(workflowResponse.getOverAppFicoScore()){
							WorkFlowDecisionM workFlowDecision = new WorkFlowDecisionM();
							workFlowDecision.setDecision(decisionAction);
							workFlowDecision.setDecisionDesc(DECISION_REJECT_DESC);
							workFlowDecision.setMessage(DECISION_REJECT_MSG_APP_FICO);
							workFlowDecisionList.add(workFlowDecision);
							//DF#3078
							setRejectDecision(applicationGroup);
						}
						if(workflowResponse.getOverDebtBurden()){
							WorkFlowDecisionM workFlowDecision = new WorkFlowDecisionM();
							workFlowDecision.setDecision(decisionAction);
							workFlowDecision.setDecisionDesc(DECISION_REJECT_DESC);
							workFlowDecision.setMessage(DECISION_REJECT_MSG_DEBT_BURDEN);
							workFlowDecisionList.add(workFlowDecision);
							//DF#3078
							setRejectDecision(applicationGroup);
						}
						
					}
				}
			} 
			
			if(isCancelAllApplicationDecision(applicationGroup)){
				callDecisionFlag = MConstant.FLAG_N;
			}
			
			logger.debug(">>>applicationGroup.getLastDecision>>>"+applicationGroup.getDecisionAction());
			logger.debug(">>>callFicoFlag>>>"+callDecisionFlag);
			applicationGroup.setCallFicoFlag(callDecisionFlag);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		ORIGForm.getObjectForm().setWorkFlowDecisions(workFlowDecisionList);
		return null;
	}
	private boolean isCancelAllApplicationDecision(ApplicationGroupDataM applicationGroup){		 
		boolean isCancelAll = false;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		int applicationSize = applications.size();
		int cancelSize = 0;
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				String FINAL_APP_DECISION = application.getFinalAppDecision();
				if(!Util.empty(FINAL_APP_DECISION)){
					FINAL_APP_DECISION = FINAL_APP_DECISION.toUpperCase();
				}
				if(DECISION_FINAL_DECISION_CANCEL.toUpperCase().equals(FINAL_APP_DECISION)){
					cancelSize++;
				}
			}
		}
		logger.debug(">>>applicationSize>>>"+applicationSize);
		logger.debug(">>>cancelSize>>>"+cancelSize);
		if(applicationSize == cancelSize){
			isCancelAll = true;
		}
		return isCancelAll;		
	}
	
	private boolean rejectCancelApplicationDecision(ApplicationGroupDataM applicationGroup){
		ArrayList<String> FINAL_DECISION_CONDITION_LIST = new ArrayList<String>(Arrays.asList(DECISION_FINAL_DECISION_CONDITION));
		boolean rejectAction = false;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		int applicationSize = applications.size();
		int rejectSize = 0;
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				if(FINAL_DECISION_CONDITION_LIST.contains(application.getFinalAppDecision())){
					rejectSize++;
				}
			}
		}
		logger.debug(">>>applicationSize>>>"+applicationSize);
		logger.debug(">>>rejectCancelSize>>>"+rejectSize);
		if(applicationSize == rejectSize){
			rejectAction = true;
		}
		return rejectAction;		
	}
//	private boolean isRejectDecision(ApplicationGroupDataM applicationGroup){
//		boolean rejectAction = false;
//		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
//		int applicationSize = applications.size();
//		int rejectSize = 0;
//		if(!Util.empty(applications)){
//			for(ApplicationDataM application : applications){
//				if(DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
//					rejectSize++;
//				}
//			}
//		}
//		logger.debug(">>>applicationSize>>>"+applicationSize);
//		logger.debug(">>>rejectSize>>>"+rejectSize);
//		if(applicationSize == rejectSize){
//			rejectAction = true;
//		}
//		return rejectAction;		
//	}
	/*private boolean isApproveToRejectDecision(ApplicationGroupDataM applicationGroup){
		boolean isContainApprove = false;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				if(FINAL_APP_DECISION_APPROVE.equals(application.getFinalAppDecision())){
					application.setFinalAppDecision(FINAL_APP_DECISION_REJECT);
					isContainApprove =true;
				}
			}
		}
		logger.debug(">>>isContainApprove>>>"+isContainApprove);
		return isContainApprove;		
	}*/
	
	private void setRejectDecision(ApplicationGroupDataM applicationGroup){
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				logger.debug("application.getFinalAppDecision()>>"+application.getFinalAppDecision());
				if(DECISION_FINAL_DECISION_APPROVE.equals(application.getFinalAppDecision())){
					application.setFinalAppDecision(CA_DECISION_FINAL_DECISION_REJECT);
				}
			}
		}		
	}
	
	@Override
	public String processForm(){
		return null;
	}
}
