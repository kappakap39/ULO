package com.eaf.orig.ulo.app.view.form.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationUtil;
import com.eaf.orig.ulo.app.view.util.pega.PegaApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.pega.ErrorDataM;
import com.eaf.orig.ulo.model.pega.FollowUpResponse;
import com.ibm.websphere.ce.cm.DuplicateKeyException;

public class ProcessActionCA extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(ProcessActionCA.class);
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	String DECISION_FINAL_DECISION_REJECT =  SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String APPLICATION_TYPE_INCREASE =SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String APPLICATION_TYPE_ADD_SUP =SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
 	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
 	String DECISION_ACTION_REJECT = SystemConstant.getConstant("DECISION_ACTION_REJECT");
	String DECISION_REJECT = SystemConstant.getConstant("FICO_DECISION_REJECTED");		
	
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	private ArrayList<String> WIP_JOBSTATE_DE2 = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_DE2");
	private ArrayList<String> JOB_STATE_DE2_APPROVE_SUBMIT = SystemConfig.getArrayListGeneralParam("JOB_STATE_DE2_APPROVE_SUBMIT");
	private ArrayList<String> JOB_STATE_EAPP_SYSTEM = SystemConfig.getArrayListGeneralParam("JOB_STATE_EAPP_SYSTEM");
	private ArrayList<String> JOB_STATE_FULL_FRAUD = SystemConfig.getArrayListGeneralParam("JOB_STATE_FULL_FRAUD");
	private ArrayList<String> WIP_JOBSTATE_CA = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_CA");
	private ArrayList<String> WIP_JOBSTATE_VT = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_VT");
	private ArrayList<String> JOB_STATE_DV_SAVING_PLUS = SystemConfig.getArrayListGeneralParam("JOB_STATE_DV_SAVING_PLUS");
	private ArrayList<String> WIP_JOBSTATE_FRAUD = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_FRAUD");
	private ArrayList<String> JOB_STATE_KEC_LOWINCOME = SystemConfig.getArrayListGeneralParam("JOB_STATE_KEC_LOWINCOME");
	private String CJD_SOURCE = SystemConfig.getGeneralParam("CJD_SOURCE");
	
	private String ERROR_SUBMIT_APP_WAIT_FOR_APPROVE = MessageErrorUtil.getText("ERROR_SUBMIT_APP_WAIT_FOR_APPROVE");
	
	@Override
	public HashMap<String, Object> validateAction(HttpServletRequest request,Object objectForm){
		FormErrorUtil formError = new FormErrorUtil();
		String buttonAction = getButtonAction();
		logger.debug("buttonAction : "+buttonAction);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ArrayList<ApplicationDataM> applications =  applicationGroup.filterApplicationLifeCycle();
		String APPLICATION_TYPE = applicationGroup.getApplicationType();
		logger.debug("APPLICATION_TYPE : "+APPLICATION_TYPE);
		if(BUTTON_ACTION_SUBMIT.equals(buttonAction)){
			if(!Util.empty(applications)){
				for(ApplicationDataM application : applications){
					LoanDataM loan =   application.getLoan();
					CardDataM card = application.getCard();
					if(Util.empty(card)){
						card = new CardDataM();
					}
					BigDecimal FINAL_CREDIT_LIMIT = (null==loan.getLoanAmt())?BigDecimal.ZERO:loan.getLoanAmt();					
					BigDecimal MIN_CREDIT_LIMIT = (null==loan.getMinCreditLimit())?BigDecimal.ZERO:loan.getMinCreditLimit();					
					BigDecimal MAX_CREDIT_LIMIT = (null==loan.getMaxCreditLimit())?BigDecimal.ZERO:loan.getMaxCreditLimit();					
					BigDecimal CURRENT_CREDIT_LIMIT = (null==loan.getCurrentCreditLimit())?BigDecimal.ZERO:loan.getCurrentCreditLimit();					
					BigDecimal RECOMEND_CREDIT_LIMIT = (null==loan.getRecommendLoanAmt())?BigDecimal.ZERO:loan.getRecommendLoanAmt();					
					BigDecimal MAIN_FINAL_CREDIT_LIMIT = getMainFinalCreditLimit(applicationGroup,application.getMaincardRecordId());	
					int max = Util.compareBigDecimal(FINAL_CREDIT_LIMIT, MAX_CREDIT_LIMIT);
					int min = Util.compareBigDecimal(FINAL_CREDIT_LIMIT, MIN_CREDIT_LIMIT);
					int increaseMin = Util.compareBigDecimal(FINAL_CREDIT_LIMIT, CURRENT_CREDIT_LIMIT);					
					int supMaxRecommend =Util.compareBigDecimal(FINAL_CREDIT_LIMIT, RECOMEND_CREDIT_LIMIT);
					int supMaxFinalCreditMain =Util.compareBigDecimal(FINAL_CREDIT_LIMIT, MAIN_FINAL_CREDIT_LIMIT);
					logger.debug("FINAL_CREDIT_LIMIT : "+FINAL_CREDIT_LIMIT);		
					logger.debug("MIN_CREDIT_LIMIT : "+MIN_CREDIT_LIMIT);
					logger.debug("MAX_CREDIT_LIMIT : "+MAX_CREDIT_LIMIT);
					logger.debug("CURRENT_CREDIT_LIMIT : "+CURRENT_CREDIT_LIMIT);
					logger.debug("RECOMEND_CREDIT_LIMIT : "+RECOMEND_CREDIT_LIMIT);
					logger.debug("MAIN_FINAL_CREDIT_LIMIT : "+MAIN_FINAL_CREDIT_LIMIT);
					logger.debug("max : "+max);
					logger.debug("min : "+min);
					logger.debug("increaseMin : "+increaseMin);
					logger.debug("supMaxRecommend : "+supMaxRecommend);
					logger.debug("supMaxFinalCreditMain : "+supMaxFinalCreditMain);
					if(!DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){	
						if(APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE)){
							if(1!=increaseMin){
								formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_LESS_THAN_CURRENT_CREDIT_LIMIT"));
								break;
							}else if(1==max){
								formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_MORE_THAN_MAX_CREDIT_LIMIT"));
								break;
							}
						}else if(APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)){
							 if(1==supMaxRecommend){
								 formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_MORE_THAN_REC_CREDIT_LIMIT"));
									break;
							 }else if(-1==min){
									formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_LESS_THAN_MIN_CREDIT_LIMIT"));
									break;
								}
						}else{
							if(APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(card.getApplicationType())){
								 if(1==supMaxRecommend || 1==supMaxFinalCreditMain){
									 formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_MORE_THAN_MAIN_CREDIT_LIMIT_REC_CREDIT_LIMIT"));
										break;
								 }else if(-1==min){
										formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_LESS_THAN_MIN_CREDIT_LIMIT"));
										break;
									}
							}else{
								if(1==max){
									formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_MORE_THAN_MAX_CREDIT_LIMIT"));
									break;
								}else if(-1==min){
									formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_LESS_THAN_MIN_CREDIT_LIMIT"));
									break;
								}
							}
						}
												
/*						if(APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)){
							 if(1==supMaxRecommend){
								 formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_MORE_THAN_REC_CREDIT_LIMIT"));
									break;
							 }							
						}else if(APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(card.getApplicationType())){
							 if(1==supMaxRecommend || 1==supMaxFinalCreditMain){
								 formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_MORE_THAN_MAIN_CREDIT_LIMIT_REC_CREDIT_LIMIT"));
									break;
							 }
						}else{
							if(1==max){
								formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_MORE_THAN_MAX_CREDIT_LIMIT"));
								break;
							}
							if(APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE)){
								if(1!=increaseMin){
									formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_LESS_THAN_CURRENT_CREDIT_LIMIT"));
									break;
								}
							}else {
								if(-1==min){
									formError.error(MessageErrorUtil.getText(request,"ERROR_FINAL_CREDIT_LIMIT_NOT_LESS_THAN_MIN_CREDIT_LIMIT"));
									break;
								}								
							}
						}*/
					}
				}
			}
		}
		return formError.getFormError();
	}
	private BigDecimal getMainFinalCreditLimit(ApplicationGroupDataM applicationGroup,String maincardRecordId){
		ApplicationDataM application = applicationGroup.getApplicationById(maincardRecordId);
		if(!Util.empty(application)){
			LoanDataM loan = application.getLoan();
			if(!Util.empty(loan)){
				BigDecimal finalCrediLimit = loan.getLoanAmt();
				return null==finalCrediLimit?BigDecimal.ZERO:finalCrediLimit;
			}
		}
		return BigDecimal.ZERO;
	}
	@Override
	public void propertiesAction(HttpServletRequest request, Object objectForm) {
		String buttonAction = getButtonAction();
		logger.debug("buttonAction : "+buttonAction);
		if(BUTTON_ACTION_SUBMIT.equals(buttonAction)){
			ApplicationGroupDataM	applicationGroup = (ApplicationGroupDataM)objectForm; 
			boolean rejectAction = false;
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
			int applicationSize = applications.size();
			int rejectSize = 0;
			if(!Util.empty(applications)){
				for(ApplicationDataM application : applications){
					if(DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
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
		}
	}
	
	@Override
	public Object processAction() {
		logger.debug("CASaveApplication.processAction..");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try {			
			String buttonAction = getButtonAction();
			logger.debug("buttonAction : "+buttonAction);
			if(BUTTON_ACTION_SUBMIT.equals(buttonAction)) {
				String appGroupNo = applicationGroup.getApplicationGroupNo();
				String idNo = "";
				ArrayList<String> products = applicationGroup.getProducts();
				if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
				} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
				}
				logger.debug("appGroupNo = " + appGroupNo);
				logger.debug("idNo = " + idNo);
				logger.debug("products = " + products.toString());
				UtilityDAO utilDao = new UtilityDAOImpl();
				ArrayList<String> jobStates = new ArrayList<>();
				jobStates.addAll(WIP_JOBSTATE_DE2);
				jobStates.addAll(JOB_STATE_DE2_APPROVE_SUBMIT);
				jobStates.addAll(JOB_STATE_EAPP_SYSTEM);
				jobStates.addAll(JOB_STATE_FULL_FRAUD);
				jobStates.addAll(WIP_JOBSTATE_CA);
				jobStates.addAll(WIP_JOBSTATE_VT);
				jobStates.addAll(JOB_STATE_DV_SAVING_PLUS);
				jobStates.addAll(WIP_JOBSTATE_FRAUD);
				jobStates.addAll(JOB_STATE_KEC_LOWINCOME);
				if(utilDao.isAppWaitForApprove(idNo, appGroupNo, jobStates, products, CJD_SOURCE)) {
					processActionResponse.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
					processActionResponse.setResultDesc(ERROR_SUBMIT_APP_WAIT_FOR_APPROVE);
					return processActionResponse;
				}
			}
		} catch(Exception e) {
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	
}
