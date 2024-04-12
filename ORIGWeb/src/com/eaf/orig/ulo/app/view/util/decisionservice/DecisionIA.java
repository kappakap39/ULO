package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.ibm.websphere.ce.cm.DuplicateKeyException;

public class DecisionIA  extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(DecisionIA.class);
	String BUTTON_ACTION_EXECUTE_IA = SystemConstant.getConstant("BUTTON_ACTION_EXECUTE_IA");
	String BUTTON_ACTION_EXECUTE = SystemConstant.getConstant("BUTTON_ACTION_EXECUTE");
	String DECISION_SERVICE_POINT_IA =SystemConstant.getConstant("DECISION_SERVICE_POINT_IA");
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
	int SUBMIT_IA_BLOCK_TIME = SystemConstant.getIntConstant("SUBMIT_IA_BLOCK_TIME");
	
	private String ERROR_SUBMIT_CLOSE_INTERVAL_1 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_1");
	private String ERROR_SUBMIT_CLOSE_INTERVAL_2 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_2");
	private String ERROR_SUBMIT_APP_WAIT_FOR_APPROVE = MessageErrorUtil.getText("ERROR_SUBMIT_APP_WAIT_FOR_APPROVE");
	
	public Object processAction() {
		logger.debug("DecisionIA1.processAction()..");
		String buttonAction = request.getParameter("buttonAction");
		String formName = FormControl.getOrigFormId(request);
		DecisionApplication decisionApplication = new DecisionApplication();
		decisionApplication.setDecisionId(DECISION_SERVICE_POINT_IA);
		try{
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			
			//Handle multiple submit in close interval
			if(BUTTON_ACTION_SUBMIT.equals(buttonAction))
			{
				String appGroupNo = applicationGroup.getApplicationGroupNo();
				String idNo = "";
				if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null)
				{
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
				}
				else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null)
				{
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
				}
				
				logger.debug("appGroupNo = " + appGroupNo);
				logger.debug("idNo = " + idNo);
				
				UtilityDAO utilDao = new UtilityDAOImpl();
				utilDao.deleteOldSubmitIATimestamp(appGroupNo, idNo, SUBMIT_IA_BLOCK_TIME);
				try
				{	
					utilDao.insertSubmitIATimestamp(appGroupNo, idNo);
				}
				catch(Exception e)
				{
					if(e instanceof DuplicateKeyException)
					{
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
						decisionApplication.setResultDesc(ERROR_SUBMIT_CLOSE_INTERVAL_1 + SUBMIT_IA_BLOCK_TIME + ERROR_SUBMIT_CLOSE_INTERVAL_2);
						return decisionApplication;
					}
					else
					{
						throw e;
					}
				}
			}
			
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			DecisionServiceRequestDataM reqApplication = new DecisionServiceRequestDataM();
			reqApplication.setApplicationGroup(applicationGroup);
			reqApplication.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_IA);
			reqApplication.setRoleId(FormControl.getFormRoleId(request));
			reqApplication.setUserId(userM.getUserName());
			String transactionId = (String)request.getSession().getAttribute("transactionId");
			reqApplication.setTransactionId(transactionId);
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(reqApplication);
				
			String responseStatusCode =responseData.getResultCode();
			logger.debug("responseStatusCode >> "+responseStatusCode);
			decisionApplication.setResultCode(responseStatusCode);	
			decisionApplication.setResultDesc(responseData.getResultDesc());
			decisionApplication.setErrorType(responseData.getErrorType());
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseStatusCode)){
				logger.debug("DECISION_SERVICE_POINT_IA >> "+DECISION_SERVICE_POINT_IA);
				logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());
				String docCompletedFlag = applicationGroup.getDocCompletedFlag();
				logger.debug("docCompletedFlag >> "+docCompletedFlag);
				decisionApplication.setDocCompleteFlag(docCompletedFlag);			
				logger.debug("buttonAction >> "+buttonAction);
				logger.debug("formName >> "+formName);
				logger.debug("decision application type >> "+applicationGroup.getApplicationType());	
				if(DecisionApplicationUtil.isApplyTypeError(applicationGroup.getApplyTypeStatus()))
				{
					//KPL Additional : bypass ApplyTypeError for KPL TOPUP
					if(!KPLUtil.isKPL_TOPUP(applicationGroup))
					{
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
						decisionApplication.setResultDesc(MessageErrorUtil.getText("ERROR_APPLY_TYPE"));
					}
				}else{			
					if(BUTTON_ACTION_SUBMIT.equals(buttonAction) && DOC_COMPLETE_FLAG.equals(docCompletedFlag) && 
					   IA_APPLICATION_FORM.equals(formName) && APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType()) ){
						processFlipIncrease(applicationGroup);
					}
				}
				applicationGroup.setExecuteFlag(MConstant.FLAG.YES);				
			} else {
				/* change to use method from DecisionApplicationUtil						
					String idNo = "";
				if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
				} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
				}
				UtilityDAO utilDao = new UtilityDAOImpl();
				utilDao.deleteErrorSubmitIATimestamp(idNo);
				*/
				DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
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
						if(null!=cardLink){
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
	}
}
