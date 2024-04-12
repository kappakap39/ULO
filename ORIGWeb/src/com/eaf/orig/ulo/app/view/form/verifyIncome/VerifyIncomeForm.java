package com.eaf.orig.ulo.app.view.form.verifyIncome;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.CompareIncomeControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormNotifyUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationUtil;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionServiceResponse;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleHLDataM;
import com.eaf.orig.ulo.model.app.BundleKLDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM.CallerScreen;
import com.google.gson.Gson;


public class VerifyIncomeForm  extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(VerifyIncomeForm.class);
	public static final String IIB_IMPLEMENT_TYPE = "DECISION_SERVICE";
	String DECISION_SERVICE_ACTION = SystemConstant.getConstant("DECISION_SERVICE_ACTION");	
	private String VARIANCE_LIMIT = SystemConfig.getGeneralParam("VARIANCE_LIMIT");
	@Override
	public String processForm() {
		ArrayList<String> INCOME_TYPE_EXCEPTION = SystemConfig.getArrayListGeneralParam("INCOME_TYPE_EXCEPTION");
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		String ficoStatus = request.getParameter("ficoStatus");
		logger.info("ficoStatus >>>> "+ficoStatus);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ORIGForm.setObjectForm(applicationGroup);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		VerificationResultDataM verificationResultM = personalInfo.getVerificationResult();
		if(verificationResultM == null) {
			verificationResultM = new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResultM);
		}
		String roleId = FormControl.getFormRoleId(request);
		boolean haveIncomeScreen = false;
		boolean haveIncomeIncomplete = false;
		ArrayList<IncomeInfoDataM> incomelist = personalInfo.getIncomeInfos();
		ArrayList<ApplicationDataM> appList = applicationGroup.filterApplicationLifeCycle();
		if(!Util.empty(incomelist)){
			for(IncomeInfoDataM incomeM: incomelist) {
				logger.debug("incomeM.getIncomeType()>>"+incomeM.getIncomeType());
				logger.debug("INCOME_TYPE_EXCEPTION>>"+INCOME_TYPE_EXCEPTION);
				if(!INCOME_TYPE_EXCEPTION.contains(incomeM.getIncomeType())){
					haveIncomeScreen = true;
					if(!IncomeTypeUtility.isIncomeComplete(incomeM, roleId)) {
						haveIncomeIncomplete = true;
					}
				}
			}
		}
		if(!Util.empty(appList)){
			if(!haveIncomeIncomplete) {
				if(!Util.empty(appList)) {
					for(ApplicationDataM appM : appList) {
						BundleHLDataM bundleHL =appM.getBundleHL();
						if(!Util.empty(bundleHL)) {
							haveIncomeScreen = true;
							if(!IncomeTypeUtility.isBundleHLComplete(bundleHL, roleId)) {
								haveIncomeIncomplete = true;
							}
						}						
						BundleKLDataM bundleKL =appM.getBundleKL();
						if(!Util.empty(bundleKL)) {
							haveIncomeScreen = true;
							if(!IncomeTypeUtility.isBundleKLComplete(bundleKL, roleId)) {
								haveIncomeIncomplete = true;
							}
						}						
						BundleSMEDataM bundleSME =appM.getBundleSME();
						if(!Util.empty(bundleSME)) {
							haveIncomeScreen = true;
							if(!IncomeTypeUtility.isBundleSMEComplete(bundleSME, roleId)) {
								haveIncomeIncomplete = true;
							}
						}
					}
				}
			}
		}
		if(!haveIncomeScreen){
			haveIncomeIncomplete = true;
		}
		logger.debug("haveIncomeScreen : "+haveIncomeScreen);
		logger.debug("haveIncomeIncomplete : "+haveIncomeIncomplete);
		if(haveIncomeIncomplete){
			verificationResultM.setSummaryIncomeResult(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS"), SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED")));
			verificationResultM.setSummaryIncomeResultCode(SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED"));
		} else {
			verificationResultM.setSummaryIncomeResult(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS"), SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED")));
			verificationResultM.setSummaryIncomeResultCode(SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED"));
		}
		
		HashMap<String, String> data = new HashMap<>();
		data.put("ficoStatus", ficoStatus==null?null:ficoStatus);
		data.put("decisionResult",DecisionApplicationUtil.ResultCode.SUCCESS);
		/*if(ApplicationUtil.eApp(applicationGroup.getSource())){
			//Call Decision Income Service 
			ApplicationGroupDataM applicationGroupClone = (ApplicationGroupDataM) SerializeUtil.clone((Serializable)applicationGroup);
			DecisionApplication decisionApplication = new DecisionApplication();
			
			try{
				Object objectProcess = processActionDV2Income(applicationGroupClone);
				if(objectProcess instanceof DecisionApplication){
					DecisionApplication objectApplication = (DecisionApplication)objectProcess;
					mapDecisionApplication(decisionApplication, objectApplication);
					if(Util.empty(decisionApplication.getResultCode())){
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.NO_ACTION);
					}
				}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
				decisionApplication.setResultDesc(e.getLocalizedMessage());
				decisionApplication.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
			}
			data.put("decisionResult",decisionApplication.getResultCode());
			data.put("decisionResultDesc",decisionApplication.getResultDesc());
		}*/
		logger.debug("decisionApplication toJson : "+new Gson().toJson(data));
		return new Gson().toJson(data);
	}
	
	public Object processActionDV2Income(ApplicationGroupDataM applicationGroup) {
		logger.debug("processActionDV2Income.processAction()..");
		DecisionApplication decisionApplication = new DecisionApplication();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		decisionApplication.setDiffRequestFlag(MConstant.FLAG_N);
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

	public void mapDecisionApplication(DecisionApplication decisionApplication,DecisionApplication objectDecisionApplication){
		decisionApplication.setDecisionId(objectDecisionApplication.getDecisionId());
		decisionApplication.setDecision(objectDecisionApplication.getDecision());
		decisionApplication.setIncomeScreenFlag(objectDecisionApplication.getIncomeScreenFlag());
		decisionApplication.setDocCompleteFlag(objectDecisionApplication.getDocCompleteFlag());
		decisionApplication.setResultCode(objectDecisionApplication.getResultCode());
		decisionApplication.setResultDesc(objectDecisionApplication.getResultDesc());
		decisionApplication.setDiffRequestFlag(objectDecisionApplication.getDiffRequestFlag());
		decisionApplication.setCallEscalateFlag(objectDecisionApplication.getCallEscalateFlag());
		decisionApplication.setResultCode(objectDecisionApplication.getResultCode());
		decisionApplication.setResultDesc(objectDecisionApplication.getResultDesc());
		decisionApplication.setErrorType(objectDecisionApplication.getErrorType());
		decisionApplication.setFraudFlag(objectDecisionApplication.getFraudFlag());
		decisionApplication.setBlockedFlag(objectDecisionApplication.getBlockedFlag());
		decisionApplication.setRejectFlag(objectDecisionApplication.getRejectFlag());
		decisionApplication.setDebtAmount(objectDecisionApplication.getDebtAmount());
		decisionApplication.setDebtBurden(objectDecisionApplication.getDebtBurden());
		decisionApplication.setDebtBurdenCreditLimit(objectDecisionApplication.getDebtBurdenCreditLimit());
		decisionApplication.setDebtRecommend(objectDecisionApplication.getDebtRecommend());
		logger.debug("implementId>>"+objectDecisionApplication.getDebtAmount());
		logger.debug("implementId>>"+objectDecisionApplication.getDebtBurden());
		//KPL Additional
		decisionApplication.setSavingPlusFlag(objectDecisionApplication.getSavingPlusFlag());
	}
	
	public DecisionServiceResponse  processDecisionIncome(DecisionServiceRequestDataM decsionRequest,ApplicationGroupDataM applicationGroup){		
		DecisionServiceResponse responseData = new DecisionServiceResponse();
		try {
			decsionRequest.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_SERVICE);
			decsionRequest.setCallerScreen(CallerScreen.INCOME_VARIENCE);
			responseData = DecisionApplicationUtil.requestDecisionService(decsionRequest);
			String errorMsg = "WARNING_DIFF";
			logger.info("processDecisionIncome : "+applicationGroup.getApplicationGroupNo());
			ArrayList<PersonalInfoDataM> personalInfoDatas = applicationGroup.getPersonalInfos();
			for(PersonalInfoDataM personalInfoData:personalInfoDatas){
				BigDecimal applicationIncome = personalInfoData.getApplicationIncome();
				BigDecimal verifiedIncome = personalInfoData.getTotVerifiedIncome()==null?new BigDecimal(0):personalInfoData.getTotVerifiedIncome();

				BigDecimal percent = new BigDecimal(100);
				BigDecimal variance = new BigDecimal(VARIANCE_LIMIT);
				logger.info("processDecisionIncome applicationIncome: "+applicationIncome);
				logger.info("processDecisionIncome verifiedIncome: "+verifiedIncome);
				if(null == applicationIncome){
					applicationIncome = new BigDecimal(0);
				}
				MathContext mc = new MathContext(128);
				BigDecimal differentInPercent = BigDecimal.ZERO;
				if ( verifiedIncome.compareTo( BigDecimal.ZERO ) > 0 ) {
					try {
						differentInPercent = ((applicationIncome.subtract(verifiedIncome)).divide(verifiedIncome,mc)).multiply(percent);
					} catch (Exception e) {
						differentInPercent = BigDecimal.ZERO;
						logger.fatal("ERROR",e);
					}
				}
				differentInPercent = differentInPercent.abs();
				logger.info("processDecisionIncome differentInPercent: "+differentInPercent);
				if(differentInPercent.compareTo(variance) > 0 || differentInPercent.compareTo(variance) == 0){
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
	
	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		logger.debug("applicationGroup >>>> "+applicationGroup);
		if(Util.empty(applicationGroup)){
			applicationGroup = new ApplicationGroupDataM();
		}
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<DebtInfoDataM> debtList = personalInfo.getDebtInfos();
		if(Util.empty(debtList)) {
			debtList = new ArrayList<DebtInfoDataM>();
	 		personalInfo.setDebtInfos(debtList);
		}
		String[] debtTypeArray = ListBoxControl.searchListBox(SystemConstant.getConstant("FIELD_ID_DEBT_TYPE"),"CHOICE_NO",request);
		ArrayList<String> debtTypes = new ArrayList<String>(Arrays.asList(debtTypeArray));
		if(null != debtTypes){
			for (String debtType : debtTypes) {
				DebtInfoDataM debtInfoM = personalInfo.getDebtInfoByType(debtType);
				if(debtInfoM == null) {
					debtInfoM = new DebtInfoDataM();
					debtInfoM.setSeq(debtList.size()+1);
					debtInfoM.setDebtType(debtType);
					debtInfoM.setPersonalId(personalInfo.getPersonalId());
					debtList.add(debtInfoM);
				}
			}
		}
		return applicationGroup; 
	}

	@Override
	public HashMap<String, ArrayList<String>> onLoadNotifyForm() {		
		FormNotifyUtil notifyUtil = new FormNotifyUtil();
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = null;
		// Mask exisiting data from DV1 if user is DV2 and screen compare is NO
		if(MConstant.ROLE.DV.equals(roleId)) {
			applicationGroup = (ApplicationGroupDataM)objectForm;
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			ArrayList<CompareIncomeInf> compareFieldElements = CompareIncomeControl.getFields(MConstant.COMPARE_SCREEN_TYPE.DEBT_INFO);
			if(!Util.empty(compareFieldElements)) {
				for(CompareIncomeInf element: compareFieldElements) {
					element.maskField(request, personalInfo);
				}
			}
		}
		return notifyUtil.getFormNotification();
	}

}
