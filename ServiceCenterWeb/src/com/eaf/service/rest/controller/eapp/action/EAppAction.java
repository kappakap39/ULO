package com.eaf.service.rest.controller.eapp.action;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.ava.flp.eapp.iib.model.DecisionServiceResponseDataEappM;
import com.avalant.wm.model.WorkManagerRequest;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.OrigComparisionDataDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.SaleInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationIncreaseDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.HistoryDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.EDecisionServiceProxy;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowAction;
import com.orig.bpm.workflow.handle.WorkflowManager;
import com.orig.bpm.workflow.service.model.BPMApplicationLog;

public class EAppAction {
	private static Logger logger = Logger.getLogger(EAppAction.class);
	private static String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	private static String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	private static String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	private static String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	private static String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	private static String BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	private static String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	private static String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private static String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private static String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	
	public static ProcessActionResponse requestDecision(ApplicationGroupDataM applicationGroup, String decisionPoint, String userId){
		return requestDecision(applicationGroup, decisionPoint, userId, null);
	}
	
	public static ProcessActionResponse requestDecision(ApplicationGroupDataM applicationGroup, String decisionPoint, String userId, String fromAction){
		String URL = SystemConfig.getProperty("CALL_EDECISION_SERVICE_URL");
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
//			applicationGroup.setUserId(userId);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(decisionPoint);
//			requestDecision.setRoleId(FormControl.getFormRoleId(request)); session null
			requestDecision.setUserId(userId);
			requestDecision.setFromAction(fromAction);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(EDecisionServiceProxy.serviceId);
				serviceRequest.setUserId(applicationGroup.getUserId());
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(requestDecision);
				
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				DecisionServiceResponseDataEappM decisionResponse =(DecisionServiceResponseDataEappM)serivceResponse.getObjectData();
				if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
				}else{
					processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR+":when  get object data");
				}
			}else{
				processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR);
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	
	public static WorkflowResponseDataM createWorkflowTask(ApplicationGroupDataM applicationGroup) throws Exception{
		WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(applicationGroup.getUserId());
			workflowRequest.setUsername(applicationGroup.getUserId());
			workflowRequest.setEApp(ApplicationUtil.eApp(applicationGroup.getSource()) || ApplicationUtil.cjd(applicationGroup.getSource()));
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = workflowManager.createWorkflowTask(workflowRequest);
		applicationGroup.setInstantId(workflowResponse.getPiid());
		logger.debug("workflowResponse.getResultCode : "+workflowResponse.getResultCode());
		logger.debug("workflowResponse.getResultDesc : "+workflowResponse.getResultDesc());
		if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
			throw new Exception(workflowResponse.getResultDesc());
		}
		return workflowResponse;
	}
	
	public static WorkflowResponseDataM createWorkflowTask(ApplicationGroupDataM applicationGroup, String callAction) throws Exception{
		WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(applicationGroup.getUserId());
			workflowRequest.setUsername(applicationGroup.getUserId());
			workflowRequest.setCallAction(callAction);
			workflowRequest.setEApp(ApplicationUtil.eApp(applicationGroup.getSource()) || ApplicationUtil.cjd(applicationGroup.getSource()));
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = workflowManager.createWorkflowTask(workflowRequest);
		applicationGroup.setInstantId(workflowResponse.getPiid());
		logger.debug("workflowResponse.getResultCode : "+workflowResponse.getResultCode());
		logger.debug("workflowResponse.getResultDesc : "+workflowResponse.getResultDesc());
		if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
			throw new Exception(workflowResponse.getResultDesc());
		}
		return workflowResponse;
	}
	
	public static WorkflowResponseDataM workflowAction(ApplicationGroupDataM applicationGroup) throws Exception{
		logger.debug("BPM_HOST : "+BPM_HOST);
		logger.debug("BPM_PORT : "+BPM_PORT);		
		logger.debug("applicationGroup.getDecisionAction : "+applicationGroup.getDecisionAction());		
		WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(applicationGroup.getUserId());	
			workflowRequest.setUsername(applicationGroup.getUserId());
			workflowRequest.setFormAction("");
			workflowRequest.setDecisionAction(applicationGroup.getDecisionAction());
			workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));
			workflowRequest.setEApp(ApplicationUtil.eApp(applicationGroup.getSource()) || ApplicationUtil.cjd(applicationGroup.getSource()));
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = workflowManager.completeWorkflowTask(workflowRequest,null);			
		logger.debug("workflowResponse.getResultCode : "+workflowResponse.getResultCode());
		logger.debug("workflowResponse.getResultDesc : "+workflowResponse.getResultDesc());			
		if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
			deleteLockSubmitIATimestamp(applicationGroup);
			throw new Exception(workflowResponse.getResultDesc());
		}
		return workflowResponse;
	}
	
	public static ErrorData error(String errorType,String errorCode,String errorDesc){
		ErrorData errorData = new ErrorData();
		errorData.setErrorType(errorType);
		errorData.setErrorCode(errorCode);
		errorData.setErrorDesc(errorDesc);
		errorData.setErrorInformation(errorDesc);
		return errorData;
	}
	public static ErrorData error(Exception e){	
		ErrorData errorData = new ErrorData();
		errorData.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
		errorData.setErrorInformation(e.getLocalizedMessage());
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		errorData.setErrorCode(ErrorData.ErrorType.SYSTEM_ERROR);
		errorData.setErrorDesc(e.getMessage());
		return errorData;
	}
	public static ProcessResponse error(ServiceResponseDataM serviceResponse){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(serviceResponse.getStatusCode());
		ErrorData errorData = new ErrorData();
		ServiceErrorInfo errorInfo = serviceResponse.getErrorInfo();
		if(null != errorInfo){
			errorData.setErrorCode(errorInfo.getErrorCode());
			errorData.setErrorDesc(errorInfo.getErrorDesc());
			errorData.setErrorInformation(errorInfo.getErrorInformation());
			errorData.setErrorSystem(errorInfo.getErrorSystem());
			errorData.setErrorTime(errorInfo.getErrorTime());
			errorData.setErrorType(errorInfo.getErrorType());
			errorData.setServiceId(errorInfo.getServiceId());
		}
		processResponse.setErrorData(errorData);
		return processResponse;
	}
	
	public static ProcessResponse AddTaskToWorkManager(WorkManagerRequest requestBody) throws Exception{
		String WORK_MANAGER_URL = SystemConfig.getProperty("WORK_MANAGER_URL");
		RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
		        if(connection instanceof HttpsURLConnection ){
		            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
						@Override
						public boolean verify(String arg0, SSLSession arg1) {
							return true;
						}
					});
		        }
				super.prepareConnection(connection, httpMethod);
			}
		});
		
		String endPointUrl = WORK_MANAGER_URL;
		HttpHeaders httpHeaderReq = new HttpHeaders();
		httpHeaderReq.add("Content-Type", "application/json; charset=UTF-8");

        Gson gson = new Gson();
        
		logger.debug("body->"+gson.toJson(requestBody));
		HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(requestBody),httpHeaderReq);
	
    	logger.debug("endPointUrl : "+endPointUrl);	
    	ResponseEntity<ProcessResponse> responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,ProcessResponse.class);
    	
    	logger.debug("response >> "+responseEntity);
		return responseEntity.getBody();
	}
	
	public static ProcessResponse AddTaskToWorkManager(ApplicationGroupDataM applicationGroup, String wmFunction, String contentMsg) throws Exception{
		WorkManagerRequest wmRequest = new WorkManagerRequest();
		wmRequest.setWmFn(wmFunction);
		wmRequest.setRefId(applicationGroup.getApplicationGroupId());
		wmRequest.setRefCode(applicationGroup.getApplicationGroupNo());
		wmRequest.setTaskData(contentMsg);
		
		return AddTaskToWorkManager(wmRequest);
	}
	
	public static NotifyResponse notificationProcess(NotificationInfoRequestDataM notificationInfo, ArrayList<String> templateList){
		logger.debug("notificationInfo.getApplicationGroupId : " + notificationInfo.getApplicationGroupId());
		logger.debug("notificationInfo.getApplicationStatus : " + notificationInfo.getApplicationStatus());
		logger.debug("notificationInfo.getSendingTime : " + notificationInfo.getSendingTime());
		logger.debug("notificationInfo.getSaleType : " + notificationInfo.getSaleType());
		logger.debug("notificationInfo.getLifeCycle : " + notificationInfo.getLifeCycle());
		
		NotifyRequest notifyRequest  = new NotifyRequest();
		notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_NOTIFICATION);
		notifyRequest.setRequestObject(notificationInfo);
		
		NotifyResponse notifyResponse = NotifyController.notify(notifyRequest, templateList);
		logger.debug(notifyResponse);
		
		return notifyResponse;
	}
	
	public static void mapCashTransferAccountType( ApplicationGroupDataM uloApplicationGroup ) {
		DIHProxy dihService = new DIHProxy();
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String SAVING_TRANFER_ACC_TYPE = SystemConstant.getConstant("SAVING_ACCOUNT_TYPE");
		String CURRENT_TRANFER_ACC_TYPE = SystemConstant.getConstant("CURRENT_ACCOUNT_TYPE");
		
		ArrayList<ApplicationDataM> applicationItems = uloApplicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
		if(!Util.empty(applicationItems)){
			for (ApplicationDataM applicationItem : applicationItems) {
				ArrayList<LoanDataM> loans = applicationItem.getLoans();
				for(LoanDataM loan : loans){
					ArrayList<CashTransferDataM> cashTransfers = loan.getCashTransfers();
					if(null == cashTransfers){
						cashTransfers = new ArrayList<CashTransferDataM>();
						loan.setCashTransfers(cashTransfers);					
					}
					for(CashTransferDataM cashTransfer : cashTransfers){
//						cashTransfers
//						
//						CashTransferDataM cashTransfer = loan.getCashTransfer(cashTransFerType);
						DIHQueryResult<String> dihCIS_SRC_STM_CD= dihService.getCisInfoByAccountNo(cashTransfer.getTransferAccount(), "CIS_SRC_STM_CD");
						String CIS_SRC_STM_CD = "";
						if(ResponseData.SUCCESS.equals(dihCIS_SRC_STM_CD.getStatusCode())){
							CIS_SRC_STM_CD = dihCIS_SRC_STM_CD.getResult();
						}
						if(!Util.empty(CIS_SRC_STM_CD)){
							cashTransfer.setCompleteData(INFO_IS_CORRECT);
							
							if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.CURRENT.getCode())){
								cashTransfer.setTransferAccountType(CURRENT_TRANFER_ACC_TYPE);
							}
							else if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.SAVING.getCode())){
								cashTransfer.setTransferAccountType(SAVING_TRANFER_ACC_TYPE);
							} 
						}
					}
				}
			}
		}
	}
	
	public static void mapSaleData( ApplicationGroupDataM uloApplicationGroup ) {
		ArrayList<SaleInfoDataM> saleInfos=uloApplicationGroup.getSaleInfos();
		if(saleInfos!=null){
			for(SaleInfoDataM saleInfo:saleInfos){
				SaleInfoUtil.mapSaleInfoDetails(saleInfo);
			}
		}
	}
	
	public static void mapBranchData( ApplicationGroupDataM uloApplicationGroup ) {
		DIHQueryResult<KbankBranchInfoDataM> dIHQueryResult=DIHProxy.getBranchInfoByRC_CD(uloApplicationGroup.getRcCode());
		
		KbankBranchInfoDataM  kKbankBranchInfoDataM=dIHQueryResult.getResult();
		if(kKbankBranchInfoDataM==null){
			kKbankBranchInfoDataM=new KbankBranchInfoDataM();
		}
		KbankBranchInfoDataM kbankBranchInfo = new KbankBranchInfoDataM();
		if(!Util.empty(uloApplicationGroup.getBranchNo())){
			DIHQueryResult<KbankBranchInfoDataM> kbankBranchResult = DIHProxy.getKbankBranchData(uloApplicationGroup.getBranchNo());
			kbankBranchInfo = kbankBranchResult.getResult();
		}
		uloApplicationGroup.setBranchName(kbankBranchInfo.getBranchName());
//		uloApplicationGroup.setBranchNo(kKbankBranchInfoDataM.getBranchNo());
		uloApplicationGroup.setBranchZone(kKbankBranchInfoDataM.getBranchZone());
		uloApplicationGroup.setBranchRegion(kKbankBranchInfoDataM.getBranchRegion());
	}
	
	public static void mapCisData(ApplicationGroupDataM applicationGroup, String userId){
		for(PersonalInfoDataM personalInfo : applicationGroup.getPersonalInfos()) {
			HashMap<String, CompareDataM> comparisonFields = new HashMap<>();
			String cisNo = personalInfo.getCisNo();
			if(!Util.empty(cisNo)){
				DIHProxy dihProxy = new DIHProxy();
				dihProxy.eAppPersonalDataMapper(cisNo, personalInfo, comparisonFields, true);
				logger.debug("mapCisData reached");
				if(!Util.empty(cisNo) && !Util.empty(comparisonFields)){
					logger.debug("cisNo and comparisonField is not empty");
					logger.debug("AppGroupId = " + applicationGroup.getApplicationGroupId());
					LookupServiceCenter.getServiceCenterManager().savePersonalCisData(personalInfo,comparisonFields,applicationGroup.getApplicationGroupId(),applicationGroup.getLifeCycle(),userId);
				}
			}
		}
	}
	
	public static void mapCardAdditionalInfo(ApplicationGroupDataM applicationGroup) {
		logger.debug("mapCardAdditionalInfo");
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		for(ApplicationDataM application : applications) {
			if(APPLICATION_TYPE_INCREASE.equals(application.getApplicationType())) {
				logger.debug("mapCardAdditionalInfo :: Application type = Increase");
				String applicationRecordId = application.getApplicationRecordId();
				ArrayList<PersonalRelationDataM> personalRelations = 
						applicationGroup.getPersonalRelation(applicationRecordId, PERSONAL_RELATION_APPLICATION_LEVEL);
				for(PersonalRelationDataM personalRelation : personalRelations) {
					PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalRelation.getPersonalId());
					if(null != personalInfo && PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())) {
						logger.debug("mapCardAdditionalInfo :: Personal type = Applicant");
						LoanDataM loan = application.getLoan();
						if(null != loan) {
							CardDataM card = loan.getCard();
							if(null != card) {
								String cardNoEncrypted = card.getCardNo();
								CardLinkDataM cardLink = null;
								DIHProxy dihProxy = new DIHProxy();
								DIHQueryResult<CardLinkDataM> dihCardLink = dihProxy.getCardLinkInfoENCPT(cardNoEncrypted);
								if(ResponseData.SUCCESS.equals(dihCardLink.getStatusCode())) {
									logger.debug("mapCardAdditionalInfo :: DIH query success");
									cardLink = dihCardLink.getResult();
									String cisNo = personalInfo.getCisNo();
									String cardNo = "";
									String ENCRYPTION_KM_CARDNO = "";
									String HASHED_CARD_NUMBER = "";
									try {
										Encryptor enc = EncryptorFactory.getDIHEncryptor();
										cardNo = enc.decrypt(cardNoEncrypted);
										Encryptor kmEnc = EncryptorFactory.getKmAlertEncryptor();
										ENCRYPTION_KM_CARDNO = kmEnc.encrypt(cardNo);
										Hasher hash = HashingFactory.getSHA256Hasher();
										HASHED_CARD_NUMBER = hash.getHashCode(cardNo);										
									} catch(Exception e) {
										e.printStackTrace();
									}
									logger.debug("mapCardAdditionalInfo :: cardLink.getApplicationType() : " + cardLink.getApplicationType());
									logger.debug("mapCardAdditionalInfo :: cardLink.getCisNo() : " + cardLink.getCisNo());
									if(!Util.empty(cardLink) && !Util.empty(cisNo) && 
											BORROWER.equals(cardLink.getApplicationType()) && cisNo.equals(cardLink.getCisNo())) {
										logger.debug("mapCardAdditionalInfo :: Start mapping");
										card.setMainCardHolderName(cardLink.getMainCardHolderName());
										card.setCardNoEncrypted(ENCRYPTION_KM_CARDNO);
										card.setHashingCardNo(HASHED_CARD_NUMBER);
										card.setCardNoMark(cardLink.getCardNoMark());
									}
								} else {
									logger.debug("Failed to query card from DIH");									
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void saveApplication(ApplicationGroupDataM applicationGroup, String userId, String role){
		//to create history data
		Gson gson = new Gson();
		HistoryDataM histData = new HistoryDataM();
			histData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
			histData.setRole(role);
			histData.setAppData(gson.toJson(applicationGroup));
			histData.setCreateBy(userId);
			histData.setUpdateBy(userId);
		applicationGroup.setHistoryData(histData);	
		applicationGroup.setClearCompareData(false);
		LookupServiceCenter.getServiceCenterManager().saveApplication(applicationGroup, userId, false);
	}
	
	public static void loadComparisonData(ApplicationGroupDataM applicationGroup) throws Exception{
		OrigComparisionDataDAO origComparisionDao = ModuleFactory.getOrigComparisionDataDAO();
		ComparisonGroupDataM comparisonCis  = origComparisionDao.loadComparisonGroup(applicationGroup.getApplicationGroupId(), CompareDataM.SoruceOfData.CIS);
		logger.debug("comparisonCis : "+comparisonCis);
		ArrayList<ComparisonGroupDataM> comparisonGroups = new ArrayList<ComparisonGroupDataM>();
		if(null==comparisonCis){
			comparisonCis = new ComparisonGroupDataM();
		}
		comparisonCis.setSrcOfData(CompareDataM.SoruceOfData.CIS);
		comparisonGroups.add(comparisonCis);
		
		applicationGroup.setComparisonGroups(comparisonGroups);
	}
	
	public static void workflowLog(ApplicationGroupDataM applicationGroup, String userId, String action) throws Exception{
//		WorkflowManager workflow = new WorkflowManager(BPM_HOST,BPM_PORT);
		WorkflowManager workflow = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
		BPMApplicationLog appLog = new BPMApplicationLog();
		appLog.setAction(action);
		appLog.setAppGroupId(applicationGroup.getApplicationGroupId());
		appLog.setAppStatus(applicationGroup.getApplicationStatus());
		appLog.setJobState(applicationGroup.getJobState());
		appLog.setUsername(userId);
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
	
	private static void deleteLockSubmitIATimestamp(ApplicationGroupDataM applicationGroup) throws Exception{
		String idNo = "";
		if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
			idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
		} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
			idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
		}
		deleteErrorSubmitIATimestamp(idNo);
	}
	
	private static void deleteErrorSubmitIATimestamp(String idNo) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE FROM ORIG_SUBMIT_IA_TIMESTAMP ");
			sql.append(" WHERE IDNO = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, idNo);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
}
