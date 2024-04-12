package com.eaf.service.rest.controller.followup;

import java.io.StringReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsefa.Deserializer;
import org.jsefa.common.lowlevel.filter.HeaderAndFooterFilter;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.display.FollowUpResultSLADocInf;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.model.DocumentScenarioDataM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.model.followup.FollowUpResultRequest;
import com.eaf.orig.ulo.model.followup.FollowUpResultResponse;
import com.eaf.orig.ulo.service.followup.result.dao.FollowUpResultDAO;
import com.eaf.orig.ulo.service.followup.result.dao.FollowUpResultFactory;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpCSVContentDataM;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.model.KbankError;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@RestController
@RequestMapping("/service/FollowUpResult")
public class FollowUpResultController {
	private static transient Logger logger = Logger.getLogger(FollowUpResultController.class);	
	@RequestMapping(value="/follow", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> followUpResult(@RequestBody FollowUpResultRequest followUpResultRequest
			, @RequestHeader HttpHeaders requestHeaders){
		
		if(null == followUpResultRequest){
			followUpResultRequest = new FollowUpResultRequest();
		}
		followUpResultRequest.setFuncNm(requestHeaders.getFirst("KBank-FuncNm"));
		followUpResultRequest.setRqUID(requestHeaders.getFirst("KBank-RqUID"));
		followUpResultRequest.setRqDt(requestHeaders.getFirst("KBank-RqDt"));
		followUpResultRequest.setRqAppId(requestHeaders.getFirst("KBank-RqAppId"));
		followUpResultRequest.setUserId(requestHeaders.getFirst("KBank-UserId"));
		followUpResultRequest.setTerminalId(requestHeaders.getFirst("KBank-TerminalId"));
		followUpResultRequest.setUserLangPref(requestHeaders.getFirst("KBank-UserLangPref"));
		followUpResultRequest.setCorrID(requestHeaders.getFirst("KBank-CorrID"));			
		
		logger.debug("followUpResultRequest : "+followUpResultRequest);
		
		String caseId  = followUpResultRequest.getCaseID();
		String applicationGroupId = getApplicationGroupId(caseId);
		String userId = requestHeaders.getFirst("KBank-UserId");
		HttpStatus httpStatus = HttpStatus.OK;
		if(Util.empty(userId)){
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		logger.debug("caseId >> "+caseId);
		logger.debug("applicationGroupId >> "+applicationGroupId);
		
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		logger.debug("serviceReqRespId >> "+serviceReqRespId);
		
		ServiceCenterController serviceController = new ServiceCenterController();		
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(caseId);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(followUpResultRequest);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.FollowUpResult);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		String RsAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		FollowUpResultResponse followUpResultResponse = new FollowUpResultResponse();
			followUpResultResponse.setFuncNm(ServiceConstant.ServiceId.FollowUpResult);		
			followUpResultResponse.setRqUID(requestHeaders.getFirst("KBank-RqUID"));		
			followUpResultResponse.setRsAppId(RsAppId);
			followUpResultResponse.setRsUID(serviceReqRespId);
			followUpResultResponse.setRsDt(ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		
		String errorMsg = "";
		try{
			if(!Util.empty(applicationGroupId)){
				FollowUpCSVContentDataM followUpContent = getFollowUpContent(followUpResultRequest);
				FollowUpResultDAO followUpResultDAO = FollowUpResultFactory.getFollowUpResultDAO(userId);
				FollowUpResultApplicationDataM followUpResultApplication = followUpResultDAO.loadApplicationData(applicationGroupId);
				ArrayList<DocumentCheckListDataM> documentCheckList = followUpResultDAO.selectDocumentCheckListDataM(applicationGroupId);
				ArrayList<DocumentScenarioDataM> documentScenarioList = followUpResultDAO.SelectDocumentScenarioDataM(applicationGroupId);
				followUpResultApplication.setDocumentCheckListDataM(documentCheckList);
				followUpResultApplication.setDocumentScenarioDataM(documentScenarioList);
				followUpResultApplication.setFollowUpStatus(followUpContent.getFollowUpStatus());
				followUpResultApplication.setUserId(userId);
				followUpResultApplication.setFollowUpContent(followUpContent);
				ProcessActionInf processActionInf  = getFollowUpProcessAction(followUpResultApplication);
				if(null != processActionInf){
					processActionInf.init(followUpResultApplication);
					ProcessActionResponse processResponse = (ProcessActionResponse)processActionInf.processAction();
					String processResultCode = processResponse.getResultCode();
					logger.debug("applicationGroupId : "+applicationGroupId);
					logger.debug("processResultCode : "+processResultCode);
					if(ServiceResponse.Status.SUCCESS.equals(processResultCode)){
						followUpResultResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
					}else{
						followUpResultResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
						errorMsg = processResponse.getErrorMsg();
					}					
				}else{
					logger.error("FOLLOWUP_RESULT_ERROR_SERVICE_NOT_SUPPORT");
					errorMsg = MessageErrorUtil.getText("FOLLOWUP_RESULT_ERROR_SERVICE_NOT_SUPPORT");
					followUpResultResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
					KbankError kbankError = new KbankError();
						kbankError.setErrorAppId(RsAppId);
						kbankError.setErrorCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
						kbankError.setErrorDesc(errorMsg);
						kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);			
					followUpResultResponse.error(kbankError);
				}		
			}else{
				logger.error("FOLLOWUP_RESULT_ERROR_NOT_FOUND_CASE_ID");
				errorMsg = MessageErrorUtil.getText("FOLLOWUP_RESULT_ERROR_NOT_FOUND_CASE_ID");
				followUpResultResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				KbankError kbankError = new KbankError();
					kbankError.setErrorAppId(RsAppId);
					kbankError.setErrorCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					kbankError.setErrorDesc(errorMsg);
					kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);			
				followUpResultResponse.error(kbankError);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			errorMsg = e.getLocalizedMessage();
			followUpResultResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			KbankError kbankError = new KbankError();
				kbankError.setErrorAppId(RsAppId);
				kbankError.setErrorCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				kbankError.setErrorDesc(errorMsg);
				kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);			
			followUpResultResponse.error(kbankError);
		}
		
		HttpHeaders httpHeaderResp = new HttpHeaders();
			httpHeaderResp.set("KBank-FuncNm", ServiceConstant.ServiceId.FollowUpResult);
			httpHeaderResp.set("KBank-RqUID", ServiceUtil.displayText(requestHeaders.getFirst("KBank-RqUID")));
			httpHeaderResp.set("KBank-RsAppId", ServiceUtil.displayText(RsAppId));
			httpHeaderResp.set("KBank-RsUID", ServiceUtil.displayText(serviceReqRespId));
			httpHeaderResp.set("KBank-RsDt", ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		
		KbankError kbankError = getFirstError(followUpResultResponse.getError());			
		httpHeaderResp.set("KBank-StatusCode", ServiceUtil.displayText(followUpResultResponse.getStatusCode()));
		if(null != kbankError){
			httpHeaderResp.set("KBank-ErrorAppId", ServiceUtil.displayText(kbankError.getErrorAppId()));
			httpHeaderResp.set("KBank-ErrorAppAbbrv", ServiceUtil.displayText(kbankError.getErrorAppAbbrv()));
			httpHeaderResp.set("KBank-ErrorCode", ServiceUtil.displayText(kbankError.getErrorCode()));
			httpHeaderResp.set("KBank-ErrorDesc", ServiceUtil.displayText(kbankError.getErrorDesc()));
			httpHeaderResp.set("KBank-ErrorSeverity", ServiceUtil.displayText(kbankError.getErrorSeverity()));			
		}
		
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
			serviceLogResponse.setServiceReqRespId(serviceReqRespId);
			serviceLogResponse.setRefCode(caseId);
			serviceLogResponse.setActivityType(ServiceConstant.OUT);
			serviceLogResponse.setServiceDataObject(followUpResultResponse);
			serviceLogResponse.setServiceId(ServiceConstant.ServiceId.FollowUpResult);
			serviceLogResponse.setUniqueId(applicationGroupId);
			serviceLogResponse.setUserId(userId);
			serviceLogResponse.setRespCode(followUpResultResponse.getStatusCode());
			try{
				Gson gson = new Gson();
				if(null != followUpResultResponse.getError()){
					serviceLogResponse.setRespDesc(gson.toJson(followUpResultResponse.getError()));
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
		logger.debug("errorMsg >> "+errorMsg);
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
		Gson gson = new Gson();
		return new ResponseEntity<String>(gson.toJson(followUpResultResponse),httpHeaderResp,httpStatus);
	}		
	private  FollowUpCSVContentDataM getFollowUpContent(FollowUpResultRequest followUpResultRequest){
		FollowUpCSVContentDataM FollowUpContent = new FollowUpCSVContentDataM();
		String csvContent = followUpResultRequest.getCSVContent();
		logger.debug("CSV Content : "+csvContent);			
        CsvConfiguration csvConfiguration = new CsvConfiguration();
        csvConfiguration.setFieldDelimiter(',');
        csvConfiguration.setLineFilter(new HeaderAndFooterFilter(1, false, false));	
        Deserializer deserializer = CsvIOFactory.createFactory(csvConfiguration, FollowUpCSVContentDataM.class).createDeserializer();	
        deserializer.open(new StringReader(csvContent));
        if(deserializer.hasNext()) {
        	FollowUpContent = deserializer.next();
        }
        deserializer.close(true);
		return FollowUpContent;
	}		
	private ProcessActionInf getFollowUpProcessAction(FollowUpResultApplicationDataM followUpResultApplication) throws Exception{
//		DocumentCheckListDataM docCheckListDataM = ;
		String CACHE_FOLLOW_UP_DECISION_RESULT = SystemConstant.getConstant("CACHE_FOLLOW_UP_DECISION_RESULT");
		String DECISION_TYPE_WAIT_DOC_AT_FU = SystemConstant.getConstant("DECISION_TYPE_WAIT_DOC_AT_FU");
		String DECISION_TYPE_WAIT_DOC_AT_DV = SystemConstant.getConstant("DECISION_TYPE_WAIT_DOC_AT_DV");
		String WIP_JOBSTATE_WAIT_FOLLOW_FU = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_FU");
		String WIP_JOBSTATE_WAIT_FOLLOW_DV = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_DV");
		String JOBSTATE_FOLLOW_SAVING_PLUS = SystemConstant.getConstant("JOBSTATE_FOLLOW_SAVING_PLUS");
		String jobState = followUpResultApplication.getJobState();
		String followUpStatus = followUpResultApplication.getFollowUpStatus();
		logger.debug("jobState : "+jobState);
		logger.debug("followUpStatus : "+followUpStatus);		
		ProcessActionInf processActionInf = null;	
		String followUpResultFuncId ="";
		String implementId="";
		if(SystemConfig.containsGeneralParam(WIP_JOBSTATE_WAIT_FOLLOW_FU,jobState)){
			followUpResultFuncId = DECISION_TYPE_WAIT_DOC_AT_FU+"_"+followUpStatus;
			implementId = DECISION_TYPE_WAIT_DOC_AT_FU;
		}else if(SystemConfig.containsGeneralParam(WIP_JOBSTATE_WAIT_FOLLOW_DV,jobState)){
			followUpResultFuncId = DECISION_TYPE_WAIT_DOC_AT_DV+"_"+followUpStatus;
			implementId = DECISION_TYPE_WAIT_DOC_AT_DV;
		}			
		String className = CacheControl.getName(CACHE_FOLLOW_UP_DECISION_RESULT,followUpResultFuncId);
		logger.debug("followUpResultFuncId : "+followUpResultFuncId);
		logger.debug("className : "+className);
		if(!Util.empty(className)){
			processActionInf = (ProcessActionInf)Class.forName(className).newInstance();
			String docSLAtype = CacheControl.getName(CACHE_FOLLOW_UP_DECISION_RESULT,"CODE",followUpResultFuncId,"DECISION02");
			ArrayList<String> docSLATypeList = new ArrayList<String>();
			if(SystemConstant.getConstant("OVER_SLA").equals(followUpStatus)){
				for(DocumentCheckListDataM doc : followUpResultApplication.getDocumentCheckListDataM())
					if(!Util.empty(doc.getDocTypeId()) && doc.getDocTypeId().equals(SystemConstant.getConstant("INCOME_DOC"))){
							logger.debug("DocType#######"+doc.getDocTypeId());
							if(!docSLATypeList.contains(SystemConstant.getConstant("DOC_SLA_TYPE04"))){
								docSLATypeList.add(SystemConstant.getConstant("DOC_SLA_TYPE04"));
							}
							logger.debug("LoopFor04##############");
							break;
					}else{
							if(!docSLATypeList.contains(SystemConstant.getConstant("DOC_SLA_TYPE01"))){
								docSLATypeList.add(SystemConstant.getConstant("DOC_SLA_TYPE01"));
							}
						}
			}
			
			// # Case FU AND DV For get slaDocumentType
			FollowUpResultSLADocInf followUpSlaDoc = ImplementControl.getFollowUpSLADocumetInf(SystemConstant.getConstant("FOLLOW_UP_RESULT"), implementId);
			if(!Util.empty(followUpSlaDoc)){
				docSLATypeList = followUpSlaDoc.getSlaDocumentType(followUpResultApplication.getDocumentCheckListDataM(),followUpResultApplication.getDocumentScenarioDataM(),followUpStatus);
			}
			
			//DF#2934 Case Saving Plus DV pending validating if not receive doc, set docSLA to 01
			if(JOBSTATE_FOLLOW_SAVING_PLUS.equals(jobState) && (SystemConstant.getConstant("OVER_SLA").equals(followUpStatus) 
					|| SystemConstant.getConstant("UNABLE_TO_CONTACT").equals(followUpStatus)
					|| SystemConstant.getConstant("CUSTOMER_DENIED_TO_SEND").equals(followUpStatus))
				&&  !docSLATypeList.contains(SystemConstant.getConstant("DOC_SLA_TYPE01"))
			  )
			{
				docSLATypeList.add(SystemConstant.getConstant("DOC_SLA_TYPE01"));
			}
			
			logger.debug("docSLAtype :"+docSLAtype);
			followUpResultApplication.setDocumentSLAType(docSLATypeList);
		}
		return processActionInf;
	}
	private String getApplicationGroupId(String setId){
		try{
			return ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByQr2(setId);
		}catch(Exception e){
			return null;
		}
	}
	public KbankError getFirstError(ArrayList<KbankError> errorList){
		KbankError kbankError = null;
		if(errorList != null && errorList.size() > 0){
			errorList.get(0);
		}
		return kbankError;
	}
}

 