package com.eaf.service.module.manual;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.OrigContactLogDataM;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.NotifyKMobileUtil;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.ErrorDataM;
import com.eaf.service.module.model.NotifyFLPApplicationDataM;
import com.eaf.service.module.model.NotifyFLPFinalApproveResultRequestBodyM;
import com.eaf.service.module.model.NotifyFLPFinalApproveResultRequestHeaderM;
import com.eaf.service.module.model.NotifyFLPFinalApproveResultRequestM;
import com.eaf.service.module.model.NotifyFLPFinalApproveResultResponseHeaderM;
import com.eaf.service.module.model.NotifyFLPFinalApproveResultResponseM;
import com.eaf.service.module.model.NotifyFLPFinalApproveServiceProxyRequest;
import com.eaf.service.module.model.NotifyFLPLoanSetupDataM;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NotifyFLPFinalApproveResultServiceProxy extends ServiceControlHelper implements ServiceControl {

	private static transient Logger logger = Logger.getLogger(NotifyFLPFinalApproveResultServiceProxy.class);
	public static final String serviceId = "NotifyFLPFinalApproveResult";
	private String RECOMMEND_DECISION_APPROVED = SystemConstant.getConstant("RECOMMEND_DECISION_APPROVED");
	private String RECOMMEND_DECISION_CANCEL = SystemConstant.getConstant("RECOMMEND_DECISION_CANCEL");
	private String RECOMMEND_DECISION_REJECTED = SystemConstant.getConstant("RECOMMEND_DECISION_REJECTED");
	private String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	private String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	
	public ServiceRequestTransaction requestTransaction() throws Exception {
		logger.debug("Request Transaction");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		NotifyFLPFinalApproveServiceProxyRequest notifyRequest = (NotifyFLPFinalApproveServiceProxyRequest) serviceRequest.getObjectData();
		ApplicationGroupDataM applicationGroup = notifyRequest.getApplicationGroup();
		ArrayList<NotifyTemplateDataM> notifyTemplates = notifyRequest.getNotifyTemplates();
		
		// Getting parameters
		String toDepartment = InfBatchProperty.getGeneralParam("LOAN_SETUP_DEPARTMENT");
		String newAction = InfBatchProperty.getGeneralParam("LOAN_SETUP_ACTION_FOR_NEW");		// Different action for each apply type
		String incAction = InfBatchProperty.getGeneralParam("LOAN_SETUP_ACTION_FOR_INC");		// Different action for each apply type
		String relationship = InfBatchProperty.getGeneralParam("LOAN_SETUP_RELATIONSHIP");
		String productGroup = InfBatchProperty.getGeneralParam("LOAN_SETUP_PRODUCT_GROUP");
		String currency = InfBatchProperty.getGeneralParam("LOAN_SETUP_CURRENCY");
		String termUnit = InfBatchProperty.getGeneralParam("LOAN_SETUP_TERM_UNIT");
		String paymentFreq = InfBatchProperty.getGeneralParam("LOAN_SETUP_PAYMENT_FREQ");
		String businessCode = InfBatchProperty.getGeneralParam("LOAN_SETUP_BUSINESS_CODE");
		String finalityCode = InfBatchProperty.getGeneralParam("LOAN_SETUP_FINALITY_CODE");
		String branchCode = InfBatchProperty.getGeneralParam("LOAN_SETUP_BRANCH_CODE");
		String accountType = InfBatchProperty.getGeneralParam("LOAN_SETUP_ACCOUNT_TYPE");
		String penalty = InfBatchProperty.getGeneralParam("LOAN_SETUP_PENALTY");
		String intIndex = InfBatchProperty.getGeneralParam("LOAN_SETUP_INT_INDEX");
		String paymentCal = InfBatchProperty.getGeneralParam("LOAN_SETUP_PAYMENT_CAL");
//		String appDate = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.ddMMyyyy);
		Date appDate = InfBatchProperty.getDate();
		
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		
		NotifyFLPFinalApproveResultRequestM notifyFLPRequestObject = new NotifyFLPFinalApproveResultRequestM();
		// Header
		NotifyFLPFinalApproveResultRequestHeaderM notifyFLPRequestHeader = new NotifyFLPFinalApproveResultRequestHeaderM();
		notifyFLPRequestHeader.setAppId(RqAppId);
		notifyFLPRequestHeader.setMessageUid(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		
		String format_yyyyMMddHHmmss = "yyyyMMddHHmmss";
		String format_yyyyMMdd = "yyyy-MM-dd";
		
		String messageDt = getDateAsString(ApplicationDate.getCalendar().getTime(), format_yyyyMMddHHmmss);

		notifyFLPRequestHeader.setMessageDt(messageDt);

		// Body
		NotifyFLPFinalApproveResultRequestBodyM notifyFLPRequestBody = new NotifyFLPFinalApproveResultRequestBodyM();
		notifyFLPRequestBody.setApplicationGroupNo( getString( applicationGroup.getApplicationGroupNo(), "") );
		notifyFLPRequestBody.setApplicationDate( getDateAsString(applicationGroup.getApplicationDate(), format_yyyyMMddHHmmss) );
		notifyFLPRequestBody.setApplicationTemplate( getString( applicationGroup.getApplicationTemplate(), "" ) );
		notifyFLPRequestBody.setApplyChannel( getString( applicationGroup.getApplyChannel(), "" ) );
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo( PersonalInfoDataM.PersonalType.APPLICANT );
		String nameTh = getName( personalInfo.getThTitleDesc(), personalInfo.getThFirstName(), personalInfo.getThMidName(), personalInfo.getThLastName() );
		String nameEn = getName( personalInfo.getEnTitleDesc(), personalInfo.getEnFirstName(), personalInfo.getEnMidName(), personalInfo.getEnLastName() );
		notifyFLPRequestBody.setMobileNo( personalInfo.getMobileNo() );
		
		String stmtAddrId = "";
		ArrayList<AddressDataM> addresss = personalInfo.getAddresses();
		String mailingAddress = personalInfo.getMailingAddress();
		for(AddressDataM address : addresss){
			if(address.getAddressType().equals(mailingAddress)){
				stmtAddrId = address.getAddressIdRef();
			}
		}
		
		String personalId = personalInfo.getPersonalId();
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId, PRODUCT_K_PERSONAL_LOAN);
		if(Util.empty(paymentMethod)){
			paymentMethod = new PaymentMethodDataM();
		}
		
		if ( ! ServiceUtil.empty( applicationGroup.getApplications() ) ) {
			ArrayList<NotifyFLPApplicationDataM> applications = new ArrayList<NotifyFLPApplicationDataM>();
			
			//case approve
			for (ApplicationDataM application : applicationGroup.filterApplicationLifeCycleByFinalAppDecision(RECOMMEND_DECISION_APPROVED) ) {
//				if ( ApplicationDataM.STATUS.APPLICATION_STATUS_APPROVED.equals( application.getFinalAppDecision() ) ) {
//				if(RECOMMEND_DECISION_APPROVED.equals(application.getFinalAppDecision())){
					NotifyFLPApplicationDataM notifyApplication = new NotifyFLPApplicationDataM();
					notifyApplication.setApplicationRecordId( getString( application.getApplicationRecordId(), "" ) );
					notifyApplication.setOrgId( getString( application.getProduct(), "" ) );
					
					CardDataM card = applicationGroup.getCardApplicationRecordId(application.getApplicationRecordId());
					if(null != card){
						notifyApplication.setSubProduct( ProcessUtil.getSubProduct( application.getProduct(), card.getCardType() ) );
					}
					
					notifyApplication.setRecommendDecision( application.getRecommendDecision() );
					notifyApplication.setApproveDate( getDateAsString(application.getFinalAppDecisionDate(), format_yyyyMMddHHmmss) );
					
					NotifyKMobileUtil kMobileUtil = new NotifyKMobileUtil();
					ArrayList<String> applicationRecordIdList = new ArrayList<>();
					applicationRecordIdList.add(application.getApplicationRecordId());
					
					String notifyMessage = kMobileUtil.getMessage(applicationGroup, personalInfo.getMobileNo(), application.getFinalAppDecision(), applicationRecordIdList, notifyTemplates);
					
					logger.debug("notifyMessage_beforeReplace :: " + notifyMessage);
					notifyMessage = notifyMessage.replace("\\\\n", "\\n");
					notifyMessage = notifyMessage.replace("\\\\\\\\n", "\\\\n");
					logger.debug("notifyMessage_afterReplace  :: " + notifyMessage);
					
					notifyApplication.setNotifyMessage(notifyMessage);
					if ( ! ServiceUtil.empty( application.getLoans() ) ) {
						for (LoanDataM loan : application.getLoans() ) {
							if ( application.getApplicationRecordId().equals( loan.getApplicationRecordId() ) 
									&& PRODUCT_K_PERSONAL_LOAN.equals(application.getProduct())
									&& RECOMMEND_DECISION_APPROVED.equals(application.getFinalAppDecision())) {
								NotifyFLPLoanSetupDataM notifyLoan = new NotifyFLPLoanSetupDataM();
								notifyLoan.setIsmId( getString( applicationGroup.getApplicationGroupNo(), "" ) );
								notifyLoan.setTo( toDepartment );
//								notifyLoan.setDe2SubmitDate( ServiceUtil.toDate( appDate, Formatter.EN )  );
								notifyLoan.setDe2SubmitDate(getDateAsString(appDate, format_yyyyMMdd));
								notifyLoan.setToPerform( newAction );
								notifyLoan.setLoanAcctRel( relationship );	// relationship = 1 -> ("Single") ?
								notifyLoan.setCisId( getString( personalInfo.getCisNo(), "" ) );
								notifyLoan.setCustomerName( nameTh );
								notifyLoan.setProductGroup( productGroup );
								notifyLoan.setProductType( getString( loan.getCoreBankProduct(), "" ) );
								notifyLoan.setAcctSubType( getString( loan.getCoreBankSubProduct(), "" ) );
								notifyLoan.setMarketCode( getString( loan.getCoreBankMarketCode(), "" ) );
								notifyLoan.setAcctTitleThai( nameTh );
								notifyLoan.setIdNo( getString( personalInfo.getIdno(), "" ) );
								notifyLoan.setContractLimit( getBigDecimal(loan.getLoanAmt()) );
								notifyLoan.setCreditLimit( getBigDecimal(loan.getLoanAmt()) );
								notifyLoan.setCurrency( currency );
//								notifyLoan.setContractDate( ServiceUtil.toDate( appDate, Formatter.EN ) );
								notifyLoan.setContractDate(getDateAsString(appDate, format_yyyyMMdd));
								notifyLoan.setTerm( loan.getTerm().intValue() );
								notifyLoan.setTermUnit( getString( termUnit, "" ) );
//								notifyLoan.setDisbursementDate( ServiceUtil.toDate( appDate, Formatter.EN ) );
								notifyLoan.setDisbursementDate(getDateAsString(appDate, format_yyyyMMdd));
								
								String installmentDate = getDateAsString(loan.getFirstInstallmentDate(), Formatter.Format.ddMMyyyy);
								paymentFreq = "1MA" + StringUtils.stripStart(installmentDate.substring(0, 2),"0");
								
								logger.debug("installmentDate " + installmentDate);
								logger.debug("paymentFreq " + paymentFreq);
								
								notifyLoan.setPaymentFrequency( paymentFreq );
								notifyLoan.setFirstPaymentDue( getDateAsString(loan.getFirstInstallmentDate(), format_yyyyMMdd) );
								notifyLoan.setDeductAcct( getString( paymentMethod.getAccountNo(), "" ) );
								notifyLoan.setBusinessCode( businessCode );
								notifyLoan.setSpecialProject( getString( application.getProjectCode(), "" ) );
								notifyLoan.setFinalityCode( finalityCode );
								notifyLoan.setOwnerBranch( branchCode );
								notifyLoan.setAccountOption( accountType );
								notifyLoan.setEarlyPayoffPenaltyMtd( penalty );
//								notifyLoan.setInterestEffectiveDate( ServiceUtil.toDate(appDate, Formatter.EN ) );
								notifyLoan.setInterestEffectiveDate(getDateAsString(appDate, format_yyyyMMdd));
								notifyLoan.setInterestIndex( intIndex );
								notifyLoan.setInterestSpread( getBigDecimal(loan.getInterestRate()) );
//								notifyLoan.setInstallmentEffectiveDate( ServiceUtil.toDate(appDate, Formatter.EN ) );
								notifyLoan.setInstallmentEffectiveDate(getDateAsString(appDate, format_yyyyMMdd));
								notifyLoan.setPaymentCalculation( paymentCal );
								notifyLoan.setInstallmentAmount( getBigDecimal(loan.getInstallmentAmt()) );
								notifyLoan.setDisbursementAmount( getBigDecimal(loan.getLoanAmt()) );
								notifyLoan.setCustAcctNo1( getString( paymentMethod.getAccountNo(), "" ) );
								BigDecimal loanAmt = loan.getLoanAmt();
								if (null == loanAmt)
									loanAmt = BigDecimal.ZERO;
								BigDecimal stampDuty = loan.getStampDuty();
								if (null == stampDuty)
									stampDuty = BigDecimal.ZERO;
								BigDecimal transferAmt = loanAmt.subtract(stampDuty);
								notifyLoan.setCustAcctNoAmt1( transferAmt );
								notifyLoan.setBorrowStampDutyAmt( stampDuty );
								
								String unsecuredApprovalName = "";
								String unsecuredApprovalId = "";
								String unsecuredApprovalIniName = "";
								String unsecuredApprovalIniId = "";
								
								notifyLoan.setUnsecuredApprovalName(unsecuredApprovalName);
								notifyLoan.setUnsecuredApprovalId(unsecuredApprovalId);
								notifyLoan.setUnsecuredApprovalIniName(unsecuredApprovalIniName);
								notifyLoan.setUnsecuredApprovalIniId(unsecuredApprovalIniId);
								
								notifyLoan.setStmtAddrId(stmtAddrId);
								notifyApplication.setLoanSetup(notifyLoan);
							}
						}
					}
					applications.add(notifyApplication);
//				}
			}
			
			//case reject
			ArrayList<ApplicationDataM> applicationRejects = applicationGroup.filterApplicationLifeCycleByFinalAppDecision(RECOMMEND_DECISION_REJECTED);
			if(!Util.empty(applicationRejects) && applicationRejects.size() > 0){
				NotifyKMobileUtil kMobileUtil = new NotifyKMobileUtil();
				
				ArrayList<String> applicationRecordIdList = new ArrayList<>();
				applicationRecordIdList = applicationGroup.filterApplicationIdsLifeCycleByFinalAppDecision(RECOMMEND_DECISION_REJECTED);
				String notifyMessage = kMobileUtil.getMessage(applicationGroup, personalInfo.getMobileNo(), RECOMMEND_DECISION_REJECTED, applicationRecordIdList, notifyTemplates);
				logger.debug("notifyMessage_beforeReplace :: " + notifyMessage);
				notifyMessage = notifyMessage.replace("\\\\n", "\\n");
				notifyMessage = notifyMessage.replace("\\\\\\\\n", "\\\\n");
				logger.debug("notifyMessage_afterReplace  :: " + notifyMessage);
				
				for (ApplicationDataM application : applicationRejects) {
					NotifyFLPApplicationDataM notifyApplication = new NotifyFLPApplicationDataM();
					notifyApplication.setApplicationRecordId( getString( application.getApplicationRecordId(), "" ) );
					notifyApplication.setOrgId( getString( application.getProduct(), "" ) );
					notifyApplication.setRecommendDecision( application.getRecommendDecision() );
					notifyApplication.setApproveDate( getDateAsString(application.getFinalAppDecisionDate(), format_yyyyMMddHHmmss) );
					notifyApplication.setNotifyMessage(notifyMessage);
					
					applications.add(notifyApplication);
				}
			}
			
			notifyFLPRequestBody.setApplications( applications );
		}
		notifyFLPRequestObject.setHeader( notifyFLPRequestHeader );
		notifyFLPRequestObject.setBody( notifyFLPRequestBody );
		System.out.println(" notifyFLPRequestObject : " + new Gson().toJson(notifyFLPRequestObject) );
		requestTransaction.serviceInfo(ServiceConstant.OUT, notifyFLPRequestObject, serviceRequest);
		return requestTransaction;
	}

	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("Service Transaction");
		NotifyFLPFinalApproveServiceProxyRequest notifyRequest = (NotifyFLPFinalApproveServiceProxyRequest) serviceRequest.getObjectData();
		ApplicationGroupDataM applicationGroup = notifyRequest.getApplicationGroup();
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		NotifyFLPFinalApproveResultRequestM notifyFLPRequest = (NotifyFLPFinalApproveResultRequestM) requestServiceObject;
		try{
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
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl : "+endPointUrl);
	        HttpHeaders httpHeaderReq = new HttpHeaders();
	        httpHeaderReq.add("Content-Type", "application/json; charset=UTF-8");
	        Gson gson = new GsonBuilder().setDateFormat("yyyyMMDDhhmmss").create();			
			HttpEntity<String> requestEntity = new HttpEntity<String>( gson.toJson(notifyFLPRequest), httpHeaderReq );
			ResponseEntity<String> responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,String.class);			
			HttpHeaders httpHeaderResp = responseEntity.getHeaders();
			String responseBody = responseEntity.getBody();
			logger.debug("httpHeaderResp >> "+httpHeaderResp);
			logger.debug("responseBody >> "+responseBody);
			
			NotifyFLPFinalApproveResultResponseM notifyFLPResponse = gson.fromJson(responseBody, NotifyFLPFinalApproveResultResponseM.class);
//			NotifyFLPFinalApproveResultResponseHeaderM responseHeader = gson.fromJson(responseBody, NotifyFLPFinalApproveResultResponseHeaderM.class);
			NotifyFLPFinalApproveResultResponseHeaderM responseHeader = notifyFLPResponse.getHeader();
			String statusCode = responseHeader.getRespCode();
			logger.debug("StatusCode >> "+statusCode);
			ErrorDataM kbankError = new ErrorDataM();
				kbankError.setErrorCode(responseHeader.getRespCode());
				kbankError.setErrorDesc(responseHeader.getRespDesc());
			ArrayList<ErrorDataM> errorList = new ArrayList<ErrorDataM>();
				errorList.add(kbankError);
//			NotifyFLPFinalApproveResultResponseM notifyFLPResponse = new NotifyFLPFinalApproveResultResponseM();
//			NotifyFLPFinalApproveResultResponseHeaderM responseHeader = new NotifyFLPFinalApproveResultResponseHeaderM();
//				responseHeader.setMessageUid(httpHeaderResp.getFirst("messageUid"));
//				responseHeader.setMessageDt(ServiceUtil.toDate(httpHeaderResp.getFirst("messageDt"), FormatUtil.EN));
//				responseHeader.setRespCode(httpHeaderResp.getFirst("respCode"));
//				responseHeader.setRespDesc(httpHeaderResp.getFirst("respDesc"));
//				responseHeader.setReqMessageUid(httpHeaderResp.getFirst("reqMessageUid"));
//				notifyFLPResponse.setHeader( responseHeader );
			serviceTransaction.setServiceTransactionObject(notifyFLPResponse);
			serviceTransaction.setStatusCode(ServiceConstant.Status.SUCCESS);
			
			OrigContactLogDataM  contactLog = new OrigContactLogDataM();
			contactLog.setApplicationGroupId(applicationGroup.getApplicationGroupId());
			contactLog.setLifeCycle(applicationGroup.getLifeCycle());
			contactLog.setContactType(TemplateBuilderConstant.TemplateType.MLS_KPLUS);
			contactLog.setSendTo(notifyFLPRequest.getBody().getMobileNo());
			contactLog.setTemplateName(TemplateBuilderConstant.TemplateName.NOTIFY_MLS);
			String notifyMessage = "";
			for(NotifyFLPApplicationDataM application : notifyFLPRequest.getBody().getApplications()){
				notifyMessage = notifyMessage + application.getNotifyMessage();
			}
			contactLog.setMessage(notifyMessage);
			contactLog.setSendBy(InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			contactLog.setSendStatus(statusCode);
			NotificationFactory.getNotificationDAO().insertTableContactLog(contactLog);
			
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			serviceTransaction.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}

	@Override
	public ServiceResponseTransaction responseTransaction(
			ServiceTransaction serviceTransaction) {
		logger.debug("Response Transaction");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		NotifyFLPFinalApproveResultResponseM finalResultResponse = (NotifyFLPFinalApproveResultResponseM) serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode())&& null != finalResultResponse){
			serviceResponse.setObjectData(finalResultResponse);		
			if(ServiceConstant.Status.SUCCESS.equals(finalResultResponse.getHeader().getRespCode())){
				serviceResponse.setStatusCode(finalResultResponse.getHeader().getRespCode());
			}else{
				serviceResponse.setStatusCode(finalResultResponse.getHeader().getRespCode());
				ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
				errorInfo.setServiceId(serviceId);
				errorInfo.setErrorDesc(finalResultResponse.getHeader().getRespDesc());
				serviceResponse.setErrorInfo(errorInfo); 
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		serviceResponse.setServiceData(serviceRequest.getServiceData());
		responseTransaction.serviceInfo(ServiceConstant.IN, finalResultResponse, serviceResponse);
		return responseTransaction;
	}
	
	private String getName(String titleDesc, String titleName, String midName, String lastName) {
		StringBuilder sb = new StringBuilder();
		if ( ! ServiceUtil.empty( titleDesc ) ) {
			sb.append( titleDesc )
				.append(" ");
		}
		if ( ! ServiceUtil.empty( titleName ) ) {
			sb.append( titleName )
				.append(" ");
		}
		if ( ! ServiceUtil.empty( midName ) ) {
			sb.append( midName )
				.append(" ");
		}
		if ( ! ServiceUtil.empty( lastName ) ) {
			sb.append( lastName );
		}
		return sb.toString();
	}
	
	private String getString(String value, String defaultValue) {
		if (ServiceUtil.empty(value))
			return defaultValue;
		return value;
	}
	
	private String getBigDecimalAsString(BigDecimal value, int decimalPoint, String defaultValue) {
		if (null == value)
			return defaultValue;
		if (decimalPoint >= 0)
			return value.setScale(decimalPoint, RoundingMode.UP).toPlainString();
		return value.toPlainString();
	}
	
	private BigDecimal getBigDecimal(BigDecimal value) {
		if(ServiceUtil.empty(value)){
			return BigDecimal.ZERO;
		}
		return value;
	}
	
	private String getDateAsString(Date date, String format) {
		if(ServiceUtil.empty(date)){
			return null;
		}
		if(ServiceUtil.empty(format)){
			return new SimpleDateFormat().format(date);
		}
		return new SimpleDateFormat(format, Locale.US).format(date);
	}

}
