package com.eaf.service.module.manual;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.CBS1215I01RequestDataM;
import com.eaf.service.module.model.CBS1215I01ResponseDataM;
import com.eaf.service.module.model.CBSRetentionDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cbs1215i01.CBS1215I01Response;
import com.kasikornbank.eai.cbs1215i01.CBS1215I01SoapProxy;
import com.kasikornbank.eai.cbs1215i01.CBS1215I01_Type;
import com.kasikornbank.eai.cbs1215i01.CBSRetention;
import com.kasikornbank.eai.cbs1215i01.RetentionsObj;
import com.kasikornbank.eai.cbs1215i01.__doServiceResponse_CBS1215I01Response_CBSAccount;
import com.kasikornbank.eai.cbs1215i01.__doServiceResponse_CBS1215I01Response_CBSNavCntrl;
import com.kasikornbank.eai.cbs1215i01.__doServiceResponse_CBS1215I01Response_KBankHeader;
import com.kasikornbank.eai.cbs1215i01.__doService_CBS1215I01_CBSAccount;
import com.kasikornbank.eai.cbs1215i01.__doService_CBS1215I01_CBSNavCntrl;
import com.kasikornbank.eai.cbs1215i01.__doService_CBS1215I01_KBankHeader;

public class CBS1215I01ServiceProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(CBS1215I01ServiceProxy.class);
	public final static String url = "urlWebService";
	public final static String serviceId = "CBS1215I01";	
//	public final static String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public final static String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public final static String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstant{
		public final static String account = "account";
		public final static String retentionSituationCode = "retentionSituationCode";
		public final static String retentionType = "retentionType";
		public final static String subAccountNumber = "subAccountNumber";
		public final static String maxRow = "maxRow";
		public final static String startIndex = "startIndex";
	}	
	
	public static class responseConstant{
		public final static String account = "account";			
		public final static String domicileBranchId = "domicileBranchId";	
		public class retentionInfomantionList{
			public final static String cancelAuthUserId = "cancelAuthUserId";	//1
			public final static String cancelBranchDesc = "cancelBranchDesc";	//2
			public final static String cancelBranchId = "cancelBranchId";	//3
			public final static String cancelRententionReason = "cancelRententionReason";	//4
			public final static String cancelUserId = "cancelUserId";	//5
			public final static String concept = "concept";	//6
			public final static String createAuthUserId = "createAuthUserId";	//7
			public final static String currencyCode = "currencyCode";	//8
			public final static String entryOrigin = "entryOrigin";	//9
			public final static String indexSequence = "indexSequence";	//10
			public final static String retentionAmount = "retentionAmount";	//11
			public final static String retentionCancelDate = "retentionCancelDate";	//12
			public final static String retentionCancelTime = "retentionCancelTime";	//13
			public final static String retentionCreateTime = "retentionCreateTime";	//14
			public final static String retentionCreateDate = "retentionCreateDate";	//15
			public final static String retentionIndicator = "retentionIndicator";	//16
			public final static String retentionIndicatorDesc = "retentionIndicatorDesc";	//17
			public final static String retentionMaturityDate = "retentionMaturityDate";	//18
			public final static String retentionNumber = "retentionNumber";	//19
			public final static String retentionSituationCode = "retentionSituationCode";	//20
			public final static String retentionType = "retentionType";	//21
			public final static String retentionTypeDesc = "retentionTypeDesc";	//22
			public final static String subAccountNum = "subAccountNumber";	//23
			public final static String transactionDate = "transactionDate";	//24
			public final static String transactionBranchId = "transactionBranchId";	//25
			public final static String transactionBranchName ="transactionBranchName";	//26
			public final static String userId = "userId";	//27
		}		
		public final static String totalRetentionAmount = "totalRetentionAmount";
		public final static String moreDataInd = "moreDataIndicator";
		public final static String numberRecord = "numberRecord";
		public final static String totalRecord = "totalRecord";
	}
	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CBS1215I01_Type requestObject = new CBS1215I01_Type();
		__doService_CBS1215I01_KBankHeader KBankHeader = new __doService_CBS1215I01_KBankHeader();
			KBankHeader.setFuncNm(serviceId);			
			KBankHeader.setUserId(ServiceCache.getGeneralParam("KBANK_CBS_USER_ID"));
			KBankHeader.setTerminalId(ServiceCache.getGeneralParam("KBANK_CBS_TERMINAL_ID"));
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			KBankHeader.setRqAppId(RqAppId);
			KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
			KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
			KBankHeader.setRqDt(ServiceApplicationDate.getCalendar());		
		CBS1215I01RequestDataM CBS1215I01Request = (CBS1215I01RequestDataM)serviceRequest.getObjectData();
		__doService_CBS1215I01_CBSAccount CBSAccount = new __doService_CBS1215I01_CBSAccount();
			CBSAccount.setAcctId(CBS1215I01Request.getAccountId());
//			CBSAccount.setRetentionSitnCode(CBS1215I01Request.getRententionSituationCode());
//			CBSAccount.setRetentionType(CBS1215I01Request.getRententionType());
			CBSAccount.setRetentionSitnCode(ServiceCache.getConstant("RENTENTION_SITUATION_CODE"));
			CBSAccount.setRetentionType(ServiceCache.getConstant("RETENTION_TYPE"));			
			CBSAccount.setSubTermNum(CBS1215I01Request.getSubAccountNumber());			
		__doService_CBS1215I01_CBSNavCntrl CBSNavCntrl = new __doService_CBS1215I01_CBSNavCntrl();
			CBSNavCntrl.setMaxRows(ServiceUtil.empty(CBS1215I01Request.getMaxRow()) ? 0 : CBS1215I01Request.getMaxRow());
			CBSNavCntrl.setStartInd(ServiceUtil.empty(CBS1215I01Request.getStartIndex()) ? 0 : CBS1215I01Request.getStartIndex());			
		requestObject.setKBankHeader(KBankHeader);
		requestObject.setCBSAccount(CBSAccount);
		requestObject.setCBSNavCntrl(CBSNavCntrl);
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestObject, serviceRequest);
		return requestTransaction;
	} 	
	
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		CBS1215I01_Type requestObject = (CBS1215I01_Type)requestServiceObject; 
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CBS1215I01SoapProxy proxy = new CBS1215I01SoapProxy();
			String endPointUrl = getEndpointUrl();
			logger.debug("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CBS1215I01Response responseObject = proxy.doService(requestObject);
			serviceTransaction.setServiceTransactionObject(responseObject);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}	
	
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		CBS1215I01Response responseObject = (CBS1215I01Response)serviceTransaction.getServiceTransactionObject();
		
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && !ServiceUtil.empty(responseObject)){
			try{
				__doServiceResponse_CBS1215I01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
				if(ServiceResponse.Status.SUCCESS.equals(KBankHeader.getStatusCode())){	
					serviceResponse.setObjectData(getCBS1215I01Response(responseObject));
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
				}else{
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
					com.kasikornbank.eai.cbs1215i01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();		
						errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.EAI);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.eai.cbs1215i01.Error Error = Errors[0];
						errorInfo.setErrorCode(Error.getErrorCode());
						errorInfo.setErrorDesc(Error.getErrorDesc());
						errorInfo.setErrorInformation(new Gson().toJson(Errors));
					}
					serviceResponse.setErrorInfo(errorInfo);
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);			
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}
	
	private CBS1215I01ResponseDataM getCBS1215I01Response(CBS1215I01Response responseObject){
		__doServiceResponse_CBS1215I01Response_CBSAccount CBSAccount = responseObject.getCBSAccount();
		RetentionsObj retentionsObj = CBSAccount.getRetentionsObj();
		CBSRetention[] CBSRetentions = retentionsObj.getRetentionsVect();
		ArrayList<CBSRetentionDataM> cbsRetentionList = new ArrayList<>();
		if(null != CBSRetentions){
			for (CBSRetention cbsRetention : CBSRetentions) {
				CBSRetentionDataM cbsRetetionM = new CBSRetentionDataM();
					cbsRetetionM.setCancelAuthUserId(cbsRetention.getCancelAuthUserId());	//1
					cbsRetetionM.setCancelBranchDesc(cbsRetention.getCancelBranchDesc());	//2
					cbsRetetionM.setCancelBranchId(cbsRetention.getCancelBranchId());	//3
					cbsRetetionM.setCancelRententionReason(cbsRetention.getCancelRetentionReason());	//4
					cbsRetetionM.setCancelUserId(cbsRetention.getCancelUserId());	//5
					cbsRetetionM.setConcept(cbsRetention.getConcept());		//6
					cbsRetetionM.setCreateAuthUserId(cbsRetention.getCreateAuthUserId());	//7
					cbsRetetionM.setCurrencyCode(cbsRetention.getCurCode());	//8
					cbsRetetionM.setEntryOrigin(cbsRetention.getEntryOrigin());	//9
					cbsRetetionM.setIndexSequence(cbsRetention.getIndexSeq());	//10
					cbsRetetionM.setRetentionAmount(cbsRetention.getRetentionAmt());	//11
					cbsRetetionM.setRetentionCancelDate(ServiceUtil.toDate(cbsRetention.getRetentionCancelDt()));	//12
					cbsRetetionM.setRetentionCancelTime(ServiceUtil.toTimestamp(cbsRetention.getRetentionCancelTime()));	//13
					cbsRetetionM.setRetentionCreateTime(ServiceUtil.toTimestamp(cbsRetention.getRetentionCreateTime()));	//14
					cbsRetetionM.setRetentionCreateDate(ServiceUtil.toDate(cbsRetention.getRetentionCreationDt()));	//15
					
					cbsRetetionM.setRetentionIndicator(cbsRetention.getRetentionInd());	//16
					cbsRetetionM.setRetentionIndicatorDesc(cbsRetention.getRetentionIndDesc());	//17
					cbsRetetionM.setRetentionMaturityDate(ServiceUtil.toDate(cbsRetention.getRetentionMatDt()));	//18
					cbsRetetionM.setRetentionNumber(cbsRetention.getRetentionNum());	//19
					cbsRetetionM.setRetentionSituationCode(cbsRetention.getRetentionSitnCode());	//20
					cbsRetetionM.setRetentionType(cbsRetention.getRetentionType());	//21
					cbsRetetionM.setRetentionTypeDesc(cbsRetention.getRetentionTypeDesc());	//22
					cbsRetetionM.setSubAccountNum(cbsRetention.getSubTermNum());	//23
					cbsRetetionM.setTransactionDate(ServiceUtil.toDate(cbsRetention.getTransactionDt()));	//24
					cbsRetetionM.setTransactionBranchId(cbsRetention.getTrnBranchId());	//25
					cbsRetetionM.setTransactionBranchName(cbsRetention.getTrnBranchName());	//26
					cbsRetetionM.setUserId(cbsRetention.getUserId());	//27
				cbsRetentionList.add(cbsRetetionM);
			}
		}			
		__doServiceResponse_CBS1215I01Response_CBSNavCntrl CBSNavCntrl = responseObject.getCBSNavCntrl();	
		CBS1215I01ResponseDataM CBS1215I01Response = new CBS1215I01ResponseDataM();
			CBS1215I01Response.setAccountId(CBSAccount.getAcctId());
			CBS1215I01Response.setCbsRententions(cbsRetentionList);
			CBS1215I01Response.setTotalRententionAmount(retentionsObj.getTotalRetentionAmt());
			CBS1215I01Response.setMoreDataInd(CBSNavCntrl.getMoreDataInd());
			CBS1215I01Response.setNumberRecord(CBSNavCntrl.getNumRec());
			CBS1215I01Response.setTotalRecord(CBSNavCntrl.getTotalRec());
		return CBS1215I01Response;
	}
}
