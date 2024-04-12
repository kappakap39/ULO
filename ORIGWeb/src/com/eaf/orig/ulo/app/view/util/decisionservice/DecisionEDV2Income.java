package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;

public class DecisionEDV2Income extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionEDV2Income.class);
	private String VARIANCE_LIMIT = SystemConfig.getGeneralParam("VARIANCE_LIMIT");
	public Object processAction() {
		logger.debug("DefaultDecisionIncome.processAction()..");
		DecisionApplication decisionApplication = new DecisionApplication();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		decisionApplication.setDiffRequestFlag(MConstant.FLAG_N);
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		DecisionServiceRequestDataM decisionRequest = new DecisionServiceRequestDataM();
		decisionRequest.setApplicationGroup(applicationGroup);
		decisionRequest.setUserId(userM.getUserName());
		decisionRequest.setRoleId(FormControl.getFormRoleId(request));
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		decisionRequest.setTransactionId(transactionId);
		String decisionAction = applicationGroup.getDecisionAction();
		String formAction = applicationGroup.getFormAction();
		String jobstate = applicationGroup.getJobState();
		logger.debug("decisionAction : "+decisionAction);
		logger.debug("formAction : "+formAction);
		logger.debug("jobstate : "+jobstate);
		try{
			String responseResult = "";
					
			DecisionServiceResponse decisionResponse = processDecisionIncome(decisionRequest,applicationGroup);
			responseResult = decisionResponse.getResultCode();
			logger.debug("decisionResponseResult : "+responseResult);

			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SUCCESS);	
			decisionApplication.setResultDesc(decisionResponse.getResultDesc());
			decisionApplication.setErrorType(decisionResponse.getErrorType());
					
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){}
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		logger.debug("formAction : "+applicationGroup.getFormAction());		
		logger.debug("decisionApplication : "+ decisionApplication.getDiffRequestFlag());
		return decisionApplication;
	}

	public DecisionServiceResponse  processDecisionIncome(DecisionServiceRequestDataM decsionRequest,ApplicationGroupDataM applicationGroup){		
		DecisionServiceResponse responseData = new DecisionServiceResponse();
		try {
			decsionRequest.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_SERVICE);
			responseData = DecisionApplicationUtil.requestDecisionService(decsionRequest);
			String errorMsg = "WARNING_DIFF";
			logger.debug("processDecisionIncome : "+applicationGroup.getApplicationGroupNo());
			ArrayList<PersonalInfoDataM> personalInfoDatas = applicationGroup.getPersonalInfos();
			for(PersonalInfoDataM personalInfoData:personalInfoDatas){
				BigDecimal applicationIncome = personalInfoData.getApplicationIncome();
				BigDecimal verifiedIncome = personalInfoData.getTotVerifiedIncome();
				BigDecimal percent = new BigDecimal(100);
				BigDecimal variance = new BigDecimal(VARIANCE_LIMIT);
				BigDecimal differentInPercent = new BigDecimal(0);
				if(null == verifiedIncome){
					verifiedIncome = new BigDecimal(0);
				}
				if(null == applicationIncome){
					applicationIncome = new BigDecimal(0);
				}else{
					differentInPercent = ((applicationIncome.subtract(verifiedIncome)).divide(verifiedIncome)).multiply(percent);
				}
				logger.debug("processDecisionIncome differentInPercent: "+differentInPercent);
				logger.debug("processDecisionIncome differentInPercent: "+differentInPercent.compareTo(variance));
				if(differentInPercent.compareTo(variance) > 0){
					responseData.setResultCode(DecisionApplicationUtil.ResultCode.SUCCESS);
					responseData.setResultDesc(errorMsg);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			responseData.setResultCode(DecisionServiceResponseDataM.Result.SYSTEM_EXCEPTION);
			responseData.setResultDesc(e.getLocalizedMessage());
		}
		return  responseData;
	}
	
}
	
