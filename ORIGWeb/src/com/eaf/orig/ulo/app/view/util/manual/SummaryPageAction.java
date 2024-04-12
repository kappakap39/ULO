package com.eaf.orig.ulo.app.view.util.manual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.WorkFlowDecisionM;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.google.gson.Gson;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;
import com.orig.bpm.workflow.service.model.BPMCAAuthorityServiceResp;

public class SummaryPageAction implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(SummaryPageAction.class);
	String CACHE_SUMMARY_FORM = SystemConstant.getConstant("CACHE_SUMMARY_FORM");
	String DECISION_ACTION_REJECT = SystemConstant.getConstant("DECISION_ACTION_REJECT");
	String[] DECISION_FINAL_DECISION_CONDITION = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_CONDITION");
	String DECISION_REJECT = SystemConstant.getConstant("FICO_DECISION_REJECTED");	
	private String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	private String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	private String formId;
	private String isCallFico;
		
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.SUMMARY_PAGE_ACTION);
		try {
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			String applicationType = applicationGroup.getApplicationType();
			String callEscalateFlag = request.getParameter("callEscalateFlag");
			logger.debug("applicationType >> "+applicationType);	
			String formId ="";
			String callFico = MConstant.FLAG_N;
			String escalateFlag = MConstant.FLAG_Y;
			if(!rejectApplicationDecision(applicationGroup)){
				formId = CacheControl.getName(CACHE_SUMMARY_FORM,"APPLICATION_TYPE",applicationType,"FORM_ID");
				ArrayList<ApplicationDataM> applicationInfos =  applicationGroup.filterApplicationLifeCycle();
				if(!Util.empty(applicationInfos) && MConstant.FLAG_N.equals(callEscalateFlag)){
					for(ApplicationDataM application :applicationInfos ){
						LoanDataM loan =   application.getLoan();
						BigDecimal FINAL_CREDIT_LIMIT =loan.getLoanAmt();
						FINAL_CREDIT_LIMIT = null==FINAL_CREDIT_LIMIT?BigDecimal.ZERO:FINAL_CREDIT_LIMIT;				
						BigDecimal REC_LOAN_AMT = loan.getRecommendLoanAmt();
						REC_LOAN_AMT = null==REC_LOAN_AMT?BigDecimal.ZERO:REC_LOAN_AMT;
						int compareLoan =Util.compareBigDecimal(FINAL_CREDIT_LIMIT, REC_LOAN_AMT);
						 
						logger.debug("compareLoan >> "+compareLoan);
						logger.debug("FINAL_CREDIT_LIMIT >> "+FINAL_CREDIT_LIMIT);
						logger.debug("REC_LOAN_AMT >> "+REC_LOAN_AMT);
						if(compareLoan ==1){
							callFico =MConstant.FLAG_Y;		
							escalateFlag =MConstant.FLAG_N;
							break;
						}			 
				  }
				}
			}else{
				escalateFlag = MConstant.FLAG_N;
			}
			
//			applicationGroup.setCalledEscalateFlag(escalateFlag);
			setEscalateFlag(request,applicationGroup);
			logger.debug("callEscalateFlag >> "+applicationGroup.getCalledEscalateFlag());
			/*HashMap<String, String> hData = new HashMap<String,String>();
			hData.put("formId", formId);
			hData.put("callFico", callFico);
			String jsonData = gson.toJson(hData);*/
			SummaryPageAction  summaryActionDataM = new  SummaryPageAction();
			summaryActionDataM.setFormId(formId);
			summaryActionDataM.setIsCallFico(callFico);
			
			return responseData.success(new Gson().toJson(summaryActionDataM));
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	
	private void setEscalateFlag(HttpServletRequest request,ApplicationGroupDataM applicationGroup){
		String DECISION_ESCALATE  = SystemConstant.getConstant("BPM_DECISION_ESCALATE");
		WorkflowManager workflowManager;
		try {
			workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT);
			WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
			UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
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
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	
	private boolean rejectApplicationDecision(ApplicationGroupDataM applicationGroup){
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
		logger.debug(">>>rejectSize>>>"+rejectSize);
		if(applicationSize == rejectSize){
			rejectAction = true;
			applicationGroup.setLastDecision(DECISION_ACTION_REJECT);
			applicationGroup.setLastDecisionDate(ApplicationDate.getDate());
			applicationGroup.setDecisionAction(DECISION_REJECT);
		}
		return rejectAction;		
	}
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getIsCallFico() {
		return isCallFico;
	}

	public void setIsCallFico(String isCallFico) {
		this.isCallFico = isCallFico;
	}
	
}
