package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;

public class DecisionFIRecalculate extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionFIRecalculate.class);
	String DECISION_SERVICE_POINT_FI_RECAL =SystemConstant.getConstant("DECISION_SERVICE_POINT_FI_RECAL");
	//KPL Additional
	String DECISION_SERVICE_FOLLOW_UP = SystemConstant.getConstant("DECISION_SERVICE_FOLLOW_UP");
	String DECISION_SERVICE_APPPROVE = SystemConstant.getConstant("DECISION_SERVICE_APPPROVE");
	String FLAG_YES = ServiceCache.getConstant("FLAG_YES");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String FINAL_RESULT_REFER = SystemConstant.getConstant("FINAL_RESULT_REFER");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String REC_RESULT_REFER = SystemConstant.getConstant("FINAL_RESULT_REFER");
	String PRODUCT_CC = SystemConstant.getConstant("PRODUCT_CODE_CC");
	String PRODUCT_KEC = SystemConstant.getConstant("PRODUCT_CODE_KEC");
	public Object processAction() {
		logger.debug(" Decision FI Recalculate.processAction()..");
		DecisionApplication decisionApplication = new DecisionApplication();
		try{
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			String APPLICATION_TYPE = applicationGroup.getApplicationType();
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			LoanDataM appLoan = new LoanDataM() ;
			applicationGroup.setReCalculateActionFlag(FLAG_YES);
			DecisionServiceRequestDataM     decisionRequest = new DecisionServiceRequestDataM();
			decisionRequest.setApplicationGroup(applicationGroup);
			decisionRequest.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_FI);
			decisionRequest.setUserId(userM.getUserNo());
			decisionRequest.setRoleId(FormControl.getFormRoleId(request));
			
			ArrayList<ApplicationDataM> applications =  applicationGroup.filterApplicationLifeCycle();
			if(!Util.empty(applications)){
				for(ApplicationDataM  application:applications){
					LoanDataM loan = application.getLoan();
					CardDataM card = application.getCard();
					String applicationCardtype= "";
					if(Util.empty(loan)){
						loan = new  LoanDataM();					
					}
					if(!Util.empty(loan)){
						applicationCardtype=card.getApplicationType();				
					}
					String appRecordId =application.getApplicationRecordId();
					if(PRODUCT_CC.equals(application.getProduct())){
						ccMapping(application, APPLICATION_TYPE);
					}else if(PRODUCT_KEC.equals(application.getProduct())){
						kecMapping(application, APPLICATION_TYPE);
					}
				}
			}
			
			String transactionId = (String)request.getSession().getAttribute("transactionId");
			decisionRequest.setTransactionId(transactionId);
			logger.debug("applicationGroup.getCallEscalateFlag() >> "+applicationGroup.getCalledEscalateFlag());	
			
			DecisionServiceResponse decisionResponse = DecisionApplicationUtil.requestDecisionService(decisionRequest);
				
			String responseResult = decisionResponse.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			decisionApplication.setResultCode(responseResult);	
			decisionApplication.setResultDesc(decisionResponse.getResultDesc());
			decisionApplication.setErrorType(decisionResponse.getErrorType());
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){			
				decisionApplication.setDecisionId(DECISION_SERVICE_POINT_FI_RECAL);
				//For CR0216
				logger.debug("applicationGroup >> "+applicationGroup);
				ArrayList<ApplicationDataM> applicationAppSorts = applicationGroup.getSortLifeCycleApplicationDesc();
				for(ApplicationDataM applicationAppSort :applicationAppSorts){
					if(!"SUP".equals(applicationAppSort.getApplicationType())){
						appLoan = applicationAppSort.getLoan();
						break;
					}
				}
				if(!Util.empty(appLoan)){
					logger.debug("appLoan.getDebtAmount() >> "+appLoan.getDebtAmount());
					decisionApplication.setDebtAmount(appLoan.getDebtAmount());
					decisionApplication.setDebtBurden(appLoan.getDebtBurden());
					decisionApplication.setDebtBurdenCreditLimit(appLoan.getDebtBurdenCreditLimit());
					decisionApplication.setDebtRecommend(appLoan.getDebtRecommend());
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}
	private void ccMapping(ApplicationDataM application ,String applicationType){
		LoanDataM loan = application.getLoan();
		CardDataM card = application.getCard();
		String applicationCardtype= "";
		if(Util.empty(loan)){
			loan = new  LoanDataM();					
		}
		if(!Util.empty(loan)){
			applicationCardtype=card.getApplicationType();				
		}
		String appRecordId =application.getApplicationRecordId();
		if(FINAL_RESULT_REFER.equals(application.getRecommendDecision()) || application.getLifeCycle()>1){
			if((APPLICATION_TYPE_ADD_SUP.equals(applicationType)) || (APPLICATION_TYPE_INCREASE.equals(applicationType))){
				String KCC_FINAL_RESULT = request.getParameter("KCC_FINAL_RESULT_"+appRecordId);
				String KCC_FINAL_CREDIT_LIMIT =request.getParameter("KCC_FINAL_CREDIT_LIMIT_"+appRecordId);
				application.setFinalAppDecision(KCC_FINAL_RESULT);
				loan.setLoanAmt(FormatUtil.toBigDecimal(KCC_FINAL_CREDIT_LIMIT,true));
			}else{
				if(APPLICATION_CARD_TYPE_BORROWER.equals(applicationCardtype)){						
					String KCC_FINAL_RESULT = request.getParameter("KCC_FINAL_RESULT_"+appRecordId);
					String KCC_FINAL_CREDIT_LIMIT =request.getParameter("KCC_FINAL_CREDIT_LIMIT_"+appRecordId);
					application.setFinalAppDecision(KCC_FINAL_RESULT);
					loan.setLoanAmt(FormatUtil.toBigDecimal(KCC_FINAL_CREDIT_LIMIT,true));
				}else if(APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(applicationCardtype)){
					
					String SUP_KCC_FINAL_RESULT = request.getParameter("SUP_KCC_FINAL_RESULT_"+appRecordId);
					String SUP_KCC_FINAL_CREDIT_LIMIT =request.getParameter("SUP_KCC_FINAL_CREDIT_LIMIT_"+appRecordId);
					 
					application.setFinalAppDecision(SUP_KCC_FINAL_RESULT);
					loan.setLoanAmt(FormatUtil.toBigDecimal(SUP_KCC_FINAL_CREDIT_LIMIT,true));
				}
			}
		}
	}
	private void kecMapping(ApplicationDataM application ,String applicationType){
		LoanDataM loan = application.getLoan();
		CardDataM card = application.getCard();
		String applicationCardtype= "";
		if(Util.empty(loan)){
			loan = new  LoanDataM();					
		}
		if(!Util.empty(loan)){
			applicationCardtype=card.getApplicationType();				
		}
		
		if(REC_RESULT_REFER.equals(application.getRecommendDecision()) ||application.getLifeCycle() >1 ){
			String kecAppRecordId =application.getApplicationRecordId();
			String KEC_FINAL_RESULT = request.getParameter("KEC_FINAL_RESULT_"+kecAppRecordId);
			String KEC_FINAL_CREDIT_LIMIT =request.getParameter("KEC_FINAL_CREDIT_LIMIT_"+kecAppRecordId);
			logger.debug("KEC_FINAL_RESULT>>"+KEC_FINAL_RESULT);
			logger.debug("KEC_FINAL_CREDIT_LIMIT>>"+KEC_FINAL_CREDIT_LIMIT);
			application.setFinalAppDecision(KEC_FINAL_RESULT);
			loan.setLoanAmt(FormatUtil.toBigDecimal(KEC_FINAL_CREDIT_LIMIT));
		}
	}
}
