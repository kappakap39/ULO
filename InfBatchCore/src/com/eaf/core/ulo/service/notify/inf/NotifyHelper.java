package com.eaf.core.ulo.service.notify.inf;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.email.EmailClient;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.TransactionLogDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.OrigContactLogDataM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.SMSServiceProxy;
import com.eaf.service.module.model.SMSRequestDataM;
import com.eaf.service.module.model.SMSResponseDataM;
import com.eaf.service.rest.model.SMSRequest;
import com.eaf.service.rest.model.SMSResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.store.CacheStoreManager;
//import com.eaf.core.ulo.common.rest.RESTClient;
//import com.eaf.core.ulo.common.rest.RESTResponse;

public abstract class NotifyHelper implements NotifyInf{
	private static transient Logger logger = Logger.getLogger(NotifyHelper.class);
	String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
	String INTERFACE_STATUS_ERROR = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_ERROR");
	private NotifyRequest notifyRequest;
	private RecipientTypeDataM recipient;
	private List<TransactionLogDataM> transactionLogs;
	@Override
	public void init(NotifyRequest notifyRequest, RecipientTypeDataM recipient) {
		this.notifyRequest = notifyRequest;
		this.recipient = recipient;
	}
	@Override
	public void init(NotifyRequest notifyRequest, RecipientTypeDataM recipient, List<TransactionLogDataM> transactionLogs) {
		this.notifyRequest = notifyRequest;
		this.recipient = recipient;
		this.transactionLogs = transactionLogs;
	}
	@Override
	public NotifyRequest getNotifyRequest(){
		return notifyRequest;
	}
	@Override
	public RecipientTypeDataM getRecipient(){
		return recipient;
	}
	@Override
	public List<TransactionLogDataM> getTransactionLog() {
		if(null == transactionLogs){
			transactionLogs = new ArrayList<TransactionLogDataM>();
		}
		return transactionLogs;
	}
	
	public RecipientTypeDataM getEmailRecipient(String recipientType){
		RecipientTypeDataM filterRecipientType = new RecipientTypeDataM();
		if(null!=recipient){
			
		}
		return filterRecipientType;
	}
	@Override
	public NotifyTransactionDataM processNotifyTransaction() {
		NotifyTransactionDataM notifyTransactionDataM = new NotifyTransactionDataM();
		try {
			ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
			NotifyTransactionResultDataM notifyTransactionResult = new NotifyTransactionResultDataM();
			String uniqueId = getNotifyRequest().getUniqueId();
			logger.debug("uniqueId >> "+uniqueId);
			notifyTransactionResult.setUniqueId(uniqueId);
			notifyTransactionResult.setTransactionObject(getNotifyRequest().getRequestObject());
			transactions.add(notifyTransactionResult);
			notifyTransactionDataM.setTransactions(transactions);			 
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}		
		return notifyTransactionDataM;
	}
	@Override
	public EmailRequest getEmailRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
		logger.debug("getEmailRequest..");
		String EMAIL_ADDRESS_FROM = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_EMAIL_ADDRESS_FROM");
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
			templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
			templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
			templateBuilderRequest.setDbType(notifyTemplate.getDbType());
			templateBuilderRequest.setRequestObject(transactionResult.getTransactionObject());
			templateBuilderRequest.setConfigurationObject(transactionResult.getConfigurationObject());
			templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
		TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
		EmailRequest emailRequest = new EmailRequest();
			emailRequest.setUniqueId(transactionResult.getUniqueId());
			emailRequest.setTemplateId(templateBuilderResponse.getTemplateId());
			emailRequest.setTemplateName(templateBuilderResponse.getTemplateName());
			emailRequest.setTo(recipient.getEmailAddress());
			emailRequest.setCcTo(recipient.getCcEmailAddress());
			emailRequest.setFrom(EMAIL_ADDRESS_FROM);
			emailRequest.setSubject(templateBuilderResponse.getHeaderMsg());
			emailRequest.setContent(templateBuilderResponse.getBodyMsg());
			emailRequest.setSentDate(InfBatchProperty.getTimestamp());
			emailRequest.setAttachments(templateBuilderResponse.getAttachments());
		logger.debug("emailRequest : "+emailRequest);
		return emailRequest;
	}
	@Override
	public SMSRequest getSMSRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
		logger.debug("getSMSRequest..");
		SMSRequest  smsRequest = new SMSRequest();
		ArrayList<String>  mobileNoElement =  recipient.getMobileNos();	
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
			templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
			templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
			templateBuilderRequest.setDbType(notifyTemplate.getDbType());
			templateBuilderRequest.setRequestObject(transactionResult.getTransactionObject());
			templateBuilderRequest.setConfigurationObject(transactionResult.getConfigurationObject());
			templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
		TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
		smsRequest.setUniqueId(transactionResult.getUniqueId());
		smsRequest.setTemplateName(templateBuilderResponse.getTemplateName());
		smsRequest.setMobileNoElement(mobileNoElement);
		smsRequest.setMsg(templateBuilderResponse.getBodyMsg());
		return smsRequest;
	}
	
	@Override
	public EmailResponse sendEmail(EmailRequest emailRequest) throws Exception{
		logger.debug("sendEmailTo : "+emailRequest.getEmailToString());
		logger.debug("Id : "+emailRequest.getUniqueId().getId());
		EmailResponse emailResponse = new EmailResponse();
		try{
			EmailClient emailClient = new EmailClient();
			emailResponse = emailClient.send(emailRequest);		
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}		
		try{
			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
			OrigContactLogDataM  contactLog = new OrigContactLogDataM();
				contactLog.setApplicationGroupId(emailRequest.getUniqueId().getId());
				contactLog.setLifeCycle(emailRequest.getUniqueId().getLifeCycle());
				contactLog.setContactType(TemplateBuilderConstant.TemplateType.EMAIL);
				contactLog.setSendTo(emailRequest.getEmailToString());
				contactLog.setCcTo(emailRequest.getCCemailToString());
				contactLog.setMessage(emailRequest.getContent());
				contactLog.setSubject(emailRequest.getSubject());
				contactLog.setTemplateName(emailRequest.getTemplateName());
				contactLog.setSendBy(emailRequest.getFrom());
				contactLog.setSendStatus(emailResponse.getStatusCode());
			notificationDAO.insertTableContactLog(contactLog);	
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}		
		return emailResponse;
	}
	
	@Override
	public SMSResponse sendSMS(SMSRequest smsRequest) throws Exception{
		logger.debug("sendSMS..TO.."+smsRequest.getMobileToString());
		logger.debug("smsRequest.getUniqueId().."+smsRequest.getUniqueId());
		SMSResponse smsResponse  = new SMSResponse();
		try{	
			String runtime = CacheStoreManager.getRuntime();
			logger.debug("Runtime >> "+runtime);
			if(CacheConstant.Runtime.JAVA.equals(runtime)){
				String SEND_SMS_REST_URL = SystemConfig.getProperty("SEND_SMS_REST_URL");
				logger.debug("SEND_SMS_REST_URL : "+SEND_SMS_REST_URL);
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
				ResponseEntity<SMSResponse> responseEntity = restTemplate.postForEntity(SEND_SMS_REST_URL,smsRequest,SMSResponse.class);
				HttpStatus httpStatus = responseEntity.getStatusCode();
				logger.debug("httpStatus >> "+httpStatus);
				smsResponse = responseEntity.getBody();
			}else if(CacheConstant.Runtime.SERVER.equals(runtime)){
				smsResponse = send(smsRequest);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);	
			smsResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			smsResponse.setResultDesc(e.getLocalizedMessage());
			throw new Exception(e.getLocalizedMessage());
		}	
		logger.debug(smsResponse);
		try{
			NotificationDAO dao = NotificationFactory.getNotificationDAO();
			OrigContactLogDataM  contactLog = new OrigContactLogDataM();
			contactLog.setApplicationGroupId(smsRequest.getUniqueId().getId());
			contactLog.setLifeCycle(smsRequest.getUniqueId().getLifeCycle());
			contactLog.setContactType(TemplateBuilderConstant.TemplateType.SMS);
			contactLog.setSendTo(smsRequest.getMobileToString());
			contactLog.setMessage(smsRequest.getMsg());
			contactLog.setSendBy(InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			contactLog.setSendStatus(smsResponse.getStatusCode());
			contactLog.setTemplateName(smsRequest.getTemplateName());
				dao.insertTableContactLog(contactLog);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}
		return smsResponse;
	}
	
    public static SMSResponse send(SMSRequest smsRequest){
		logger.debug(smsRequest);	
		SMSResponse smsResponse = new SMSResponse();
		boolean isConnectionError = false;
		String errorInformation = "";
		ServiceResponseDataM serviceResponse = new ServiceResponseDataM();
			serviceResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		String applicationGroupNo = "";			
		try{
			String applicationGroupId = smsRequest.getUniqueId().getId();
			boolean validMobileNo = fixMobileNo(smsRequest);
			applicationGroupNo = ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupNo(applicationGroupId);
			logger.debug("applicationGroupId >> "+applicationGroupId);
			logger.debug("validMobileNo : "+validMobileNo);
			if(validMobileNo){
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setUniqueId(applicationGroupId);
				serviceRequest.setRefId(applicationGroupNo);
				serviceRequest.setServiceId(SMSServiceProxy.serviceId);
				String SMS_SERVICE_ENDPOINT_URL = ServiceCache.getProperty("SMS_SERVICE_ENDPOINT_URL");
				logger.debug("SMS_SERVICE_ENDPOINT_URL >> "+SMS_SERVICE_ENDPOINT_URL);
				serviceRequest.setEndpointUrl(SMS_SERVICE_ENDPOINT_URL);
				serviceRequest.setObjectData(getSMSRequest(smsRequest));			
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				serviceResponse = proxy.requestService(serviceRequest);
				SMSResponseDataM responseObject = (SMSResponseDataM)serviceResponse.getObjectData();
				logger.debug("responseObject >> "+responseObject);
				smsResponse.setStatusCode(serviceResponse.getStatusCode());
				ServiceErrorInfo errorInfo = serviceResponse.getErrorInfo();
				if(!InfBatchUtil.empty(errorInfo)){
					smsResponse.setStatusDesc(errorInfo.getErrorDesc());
					if(ErrorData.ErrorType.CONNECTION_ERROR.equals(errorInfo.getErrorType())){
						isConnectionError = true;
						errorInformation = errorInfo.getErrorInformation();
					}
				}
			}
			logger.debug("isConnectionError : "+isConnectionError);
			if(isConnectionError){
				StringBuilder result = new StringBuilder(applicationGroupNo).append(" ").append(errorInformation);
				
				smsResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				smsResponse.setResultDesc(result.toString());
			}else if(!isConnectionError && !ServiceResponse.Status.SUCCESS.equals(serviceResponse.getStatusCode())){
				String previousResultDesc = smsResponse.getResultDesc();
				StringBuilder result = new StringBuilder(applicationGroupNo).append(" ").append(Formatter.displayText(previousResultDesc));
				
				smsResponse.setStatusCode(ServiceResponse.Status.WARNING);
				smsResponse.setStatusDesc(result.toString());
			}else if(!validMobileNo){
				StringBuilder result = new StringBuilder(applicationGroupNo).append(" ").append("MobileNo format is wrong");
				
				smsResponse.setStatusCode(ServiceResponse.Status.WARNING);
				smsResponse.setStatusDesc(result.toString());
			}
		}catch(Exception e){
			StringBuilder result = new StringBuilder(applicationGroupNo).append(" ").append(e.getLocalizedMessage());
			
			smsResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			smsResponse.setResultDesc(result.toString());
			logger.fatal("ERROR",e);
		}
		logger.debug(smsResponse);
		return smsResponse;
	}

	private static SMSRequestDataM getSMSRequest(SMSRequest smsRequest){
		SMSRequestDataM smsRequestM = new SMSRequestDataM();
		smsRequestM.setMobileNoElement(smsRequest.getMobileNoElement());
		smsRequestM.setMessageType(smsRequestM.getMessageType());
		smsRequestM.setTemplateId(smsRequest.getTemplateId());
		smsRequestM.setSmsLanguage(smsRequest.getSmsLanguage());
		smsRequestM.setDepartmentCode(smsRequest.getDepartmentCode());
		smsRequestM.setPriority(smsRequest.getPriority());
		smsRequestM.setMsg(smsRequest.getMsg());
		smsRequestM.setClientId(smsRequest.getClientId());
		return smsRequestM;
	}
	private static boolean fixMobileNo(SMSRequest smsRequest){
		boolean validMobileNo = true;
		ArrayList<String> fixedMobileNo = new ArrayList<String>(); 
		List<String> mobileNos = smsRequest.getMobileNoElement();
		if(!InfBatchUtil.empty(mobileNos)){
			for(String mobileNo : mobileNos){
				mobileNo = mobileNo.replaceAll("[\\D]", "");	// remove all character but number
				if(validateMobileNoFormat(mobileNo)){
					fixedMobileNo.add(mobileNo);
				}else{
					validMobileNo = false;
				}
			}
		}
		smsRequest.setMobileNoElement(fixedMobileNo);
		return validMobileNo;
	}
	protected static boolean validateMobileNoFormat(String mobileNo){
		String REGEX_MOBILE_PHONE = InfBatchProperty.getInfBatchConfig("REGEX_MOBILE_PHONE");
		Pattern pattern = Pattern.compile(REGEX_MOBILE_PHONE);	// matching with mobile no format
		Matcher matcher = pattern.matcher(mobileNo);
		if(matcher.matches()){
			return true;
		}
		return false;
	}
	protected String getInterfaceStatusEmailResponse(EmailResponse emailResponse){
		String responseStatusCode = emailResponse.getStatusCode();
		String interfaceStatus = "";
		if(!InfBatchUtil.empty(responseStatusCode) && responseStatusCode.equals(EmailResponse.Status.SUCCESS)){
			interfaceStatus = INTERFACE_STATUS_COMPLETE;
		}else{
			interfaceStatus = INTERFACE_STATUS_ERROR;
		}
		return interfaceStatus;
	}
	protected String getInterfaceStatusSmsResponse(SMSResponse smsResponse){
		String responseStatusCode = smsResponse.getStatusCode();
		String interfaceStatus = "";
		if(!InfBatchUtil.empty(responseStatusCode) && responseStatusCode.equals(ServiceConstant.Status.SUCCESS)){
			interfaceStatus = INTERFACE_STATUS_COMPLETE;
		}else{
			interfaceStatus = INTERFACE_STATUS_ERROR;
		}
		return interfaceStatus;
	}
	@Override
	public void notifyLog(Object object, NotifyResponse notifyResponse)throws Exception{

	}
}
