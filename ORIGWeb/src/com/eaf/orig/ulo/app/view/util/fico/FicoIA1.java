package com.eaf.orig.ulo.app.view.util.fico;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;

public class FicoIA1 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoIA1.class);
	String BUTTON_ACTION_EXECUTE_IA = SystemConstant.getConstant("BUTTON_ACTION_EXECUTE_IA");
	String BUTTON_ACTION_EXECUTE = SystemConstant.getConstant("BUTTON_ACTION_EXECUTE");
	String FICO_DECISION_POINT_IA =SystemConstant.getConstant("FICO_DECISION_POINT_IA");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String DOC_COMPLETE_FLAG = SystemConstant.getConstant("DOC_COMPLETE_FLAG");	
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String CACHE_ORGANIZATION = SystemConstant.getConstant("CACHE_ORGANIZATION");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	String IA_APPLICATION_FORM = SystemConstant.getConstant("DEFAULT_IA_APPLICATION_FORM");
	String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String[] IA_APPLY_TYPE_ERROR_EXCEPTION = SystemConstant.getArrayConstant("IA_APPLY_TYPE_ERROR_EXCEPTION");
	String CACHE_NAME_CARDTYPE = SystemConstant.getConstant("CACHE_NAME_CARDTYPE");
	public Object processAction() {
		logger.debug("FicoIA1.processAction()..");
		String buttonAction = request.getParameter("buttonAction");
		String formName = FormControl.getOrigFormId(request);
		FicoApplication ficoApplication = new FicoApplication();
		try{
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			FicoRequest ficoRequest = new FicoRequest();
				ficoRequest.setApplicationGroup(applicationGroup);
				ficoRequest.setFicoFunction(FICO_DECISION_POINT_IA);
				ficoRequest.setRoleId(FormControl.getFormRoleId(request));
			FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest);
			String ficoResponseResult = ficoResponse.getResultCode();
			logger.debug("ficoResponseResult >> "+ficoResponseResult);
			ficoApplication.setResultCode(ficoResponseResult);	
			ficoApplication.setResultDesc(ficoResponse.getResultDesc());
			if(FicoApplicationUtil.ResultCode.SUCCESS.equals(ficoResponseResult)){		
				ficoApplication.setFicoId(FICO_DECISION_POINT_IA);
				logger.debug("FICO_DECISION_POINT_IA >> "+FICO_DECISION_POINT_IA);
				logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());
				String docCompletedFlag = applicationGroup.getDocCompletedFlag();
				logger.debug("docCompletedFlag >> "+docCompletedFlag);
				ficoApplication.setDocCompleteFlag(docCompletedFlag);			
				logger.debug("buttonAction >> "+buttonAction);
				logger.debug("formName >> "+formName);
				logger.debug("fico application type >> "+applicationGroup.getApplicationType());			
				if(isApplyTypeError(applicationGroup.getApplicationType())){
					ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
					ficoApplication.setResultDesc(MessageErrorUtil.getText("ERROR_APPLY_TYPE"));
				}else{			
					if(BUTTON_ACTION_SUBMIT.equals(buttonAction) && DOC_COMPLETE_FLAG.equals(docCompletedFlag) && 
					   IA_APPLICATION_FORM.equals(formName) && APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType()) ){
						processFlipIncrease(applicationGroup);
					}
				}
				applicationGroup.setExecuteFlag(MConstant.FLAG.YES);				
			}	
		}catch(Exception e){
			logger.fatal("ERROR",e);
			ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			ficoApplication.setResultDesc(e.getLocalizedMessage());
		}
		return ficoApplication;
	}

	private void processFlipIncrease(ApplicationGroupDataM applicationGroup) throws Exception{
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		DIHProxy dihProxy = new DIHProxy();
		if(null != applications){
			for(ApplicationDataM application : applications) {
				CardDataM card = application.getCard();
				if(null != card){
					String cardNo = card.getCardNo();
					String cardTypeId = card.getCardType();
					logger.debug("cardNo : "+cardNo);
					logger.debug("cardTypeId : "+cardTypeId);
					
					DIHQueryResult<CardLinkDataM>  dihCardLink = dihProxy.getCardLinkInfoENCPT(cardNo);	
					if(ResponseData.SUCCESS.equals(dihCardLink.getStatusCode())){
						CardLinkDataM cardLink = dihCardLink.getResult();
						card.setMainCardHolderName(cardLink.getMainCardHolderName());
						card.setApplicationType(cardLink.getApplicationType());
						String businessClassId = CacheControl.getName(CACHE_NAME_CARDTYPE, "CODE",cardTypeId,"BUSINESS_CLASS_ID");
						logger.debug("businessClassId : "+businessClassId);
						application.setBusinessClassId(businessClassId);
						if(Util.empty(personalInfo.getMobileNo())){
							personalInfo.setMobileNo(cardLink.getPhoneNo());
						}
					}
				}
			}
		}		
	}
	
	private boolean isApplyTypeError(String applicationType){
		ArrayList<String> applyTypeExceptions = new ArrayList<>(Arrays.asList(IA_APPLY_TYPE_ERROR_EXCEPTION));
		logger.debug("applicationType>>"+applicationType);
		if(!applyTypeExceptions.contains(applicationType)){
			return true;
		}
		return false;
	}
}
