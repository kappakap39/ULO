package com.eaf.service.rest.controller.reprocess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.NotePadDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.service.ejb.view.ServiceCenterManager;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.ExpiredCacheServiceProxy;
import com.eaf.service.rest.model.ServiceResponse;
import com.kasikornbank.expiredcache.ExpiredCache;

@RestController
@RequestMapping("/service/reprocess")
public class ReprocessController {

	private static transient Logger logger = Logger.getLogger(ReprocessController.class);
	
	private final String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	
	@RequestMapping(value="/doreprocess", method={RequestMethod.POST})
    public @ResponseBody ResponseEntity<ProcessResponse> doreprocess(@ModelAttribute ReprocessRequest reprocessRequest){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceConstant.Status.SUCCESS);
		try {
			String applicationGroupId = reprocessRequest.getApplicationGroupId();
			String reprocessRemark = "Reprocess After Release Duplicate";
			logger.debug("Do Reprocess :: Application Group ID = " + applicationGroupId);
			
			String LOG_MESSAGE_BLOCK_REPROCESS = "Block for Reprocess";
			ServiceCenterManager manager = LookupServiceCenter.getServiceCenterManager();
			OrigApplicationGroupDAO applicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
			ApplicationGroupDataM applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
			applicationGroup.setReprocessFlag(FLAG_YES);
			
			ArrayList<NotePadDataM> notePads = applicationGroup.getNotePads();
			if(null==notePads){
				notePads = new ArrayList<NotePadDataM>(); 
			}
			NotePadDataM notePad = new NotePadDataM();
				notePad.setSeq(notePads.size());
			 	notePad.setNotePadDesc(reprocessRemark);
				notePad.setUpdateBy("SYSTEM");
				notePad.setUpdateDate(ApplicationDate.getTimestamp());
				notePad.setCreateBy("SYSTEM");
				notePad.setUserRole("SYSTEM");
				notePad.setCreateDate(ApplicationDate.getTimestamp());
				notePad.setStatus(FLAG_YES);
			notePads.add(notePad);
			applicationGroup.setNotePads(notePads);
			
			PersonalInfoDataM  personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			if(Util.empty(personalInfo)){
				personalInfo = new PersonalInfoDataM();
			}
			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			if(Util.empty(verificationResult)){
				verificationResult = new VerificationResultDataM();
			}
			verificationResult.clearVerifyResult();
			ArrayList<ApplicationDataM> applications = applicationGroup.getApplicationLifeCycle(new BigDecimal(applicationGroup.getLifeCycle()));
			for(ApplicationDataM application : applications){
				application.clearFinalAppDecisionValue();
				application.setReExecuteKeyProgramFlag(null); //KPL Additional
				VerificationResultDataM verResultApp = application.getVerificationResult();
				if(!Util.empty(verResultApp)){
					verResultApp.setPolicyRules(null);
				}
				
				CardDataM card = application.getCard();
				if(card != null)
				{
					HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfo(card.getRequestCardType());
					
					String cardLevel=card.getCardLevel();
					if(!Util.empty(cardInfo)){
						cardLevel = SQLQueryEngine.display(cardInfo, "CARD_LEVEL");
					}
					card.setCardType(card.getRequestCardType());
					card.setCardLevel(cardLevel);
				}
				//DF#FLP-02664,FLP-03115
				application.clearReason();
			}
			manager.reprocessApplication(applicationGroup, "SYSTEM", "SYSTEM", LOG_MESSAGE_BLOCK_REPROCESS);
			//Call ExpiredCacheSerice iib
			for(PersonalInfoDataM personalList : applicationGroup.getPersonalInfos()){
				requestExpiredCacheService(applicationGroup,personalList);
			}
			
		} catch(Exception e) {
			logger.fatal("ERROR",e);
			ErrorData errorData = error(e);
			processResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(errorData);
			processResponse.setData(e.getMessage());
		}
		return ResponseEntity.ok(processResponse);
	}
	
	@RequestMapping(value="/doreprocesstest", method={RequestMethod.POST})
    public @ResponseBody ResponseEntity<ProcessResponse> doreprocesstest(@RequestBody ReprocessRequest reprocessRequest){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceConstant.Status.SUCCESS);
		try {
			String applicationGroupId = reprocessRequest.getApplicationGroupId();
			String reprocessRemark = "Reprocess After Release Duplicate";
			logger.debug("Do Reprocess Test :: Application Group ID = " + applicationGroupId);
			
			String LOG_MESSAGE_BLOCK_REPROCESS = "Block for Reprocess";
			ServiceCenterManager manager = LookupServiceCenter.getServiceCenterManager();
			OrigApplicationGroupDAO applicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
			ApplicationGroupDataM applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
			applicationGroup.setReprocessFlag(FLAG_YES);
			ArrayList<NotePadDataM> notePads = applicationGroup.getNotePads();
			if(null==notePads){
				notePads = new ArrayList<NotePadDataM>(); 
			}
			NotePadDataM notePad = new NotePadDataM();
				notePad.setSeq(notePads.size());
			 	notePad.setNotePadDesc(reprocessRemark);
				notePad.setUpdateBy("SYSTEM");
				notePad.setUpdateDate(ApplicationDate.getTimestamp());
				notePad.setCreateBy("SYSTEM");
				notePad.setUserRole("SYSTEM");
				notePad.setCreateDate(ApplicationDate.getTimestamp());
				notePad.setStatus(FLAG_YES);
			notePads.add(notePad);
			applicationGroup.setNotePads(notePads);
			PersonalInfoDataM  personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			if(Util.empty(personalInfo)){
				personalInfo = new PersonalInfoDataM();
			}
			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			if(Util.empty(verificationResult)){
				verificationResult = new VerificationResultDataM();
			}
			verificationResult.clearVerifyResult();
			ArrayList<ApplicationDataM> applications = applicationGroup.getApplicationLifeCycle(new BigDecimal(applicationGroup.getLifeCycle()));
			for(ApplicationDataM application : applications){
				application.clearFinalAppDecisionValue();
				application.setReExecuteKeyProgramFlag(null); //KPL Additional
				VerificationResultDataM verResultApp = application.getVerificationResult();
				if(!Util.empty(verResultApp)){
					verResultApp.setPolicyRules(null);
				}
				
				CardDataM card = application.getCard();
				if(card != null)
				{
					HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfo(card.getRequestCardType());
					
					String cardLevel=card.getCardLevel();
					if(!Util.empty(cardInfo)){
						cardLevel = SQLQueryEngine.display(cardInfo, "CARD_LEVEL");
					}
					card.setCardType(card.getRequestCardType());
					card.setCardLevel(cardLevel);
				}
				//DF#FLP-02664,FLP-03115
				application.clearReason();
			}
			manager.reprocessApplication(applicationGroup, "SYSTEM", "SYSTEM", LOG_MESSAGE_BLOCK_REPROCESS);
			//Call ExpiredCacheSerice iib
			for(PersonalInfoDataM personalList : applicationGroup.getPersonalInfos()){
				requestExpiredCacheService(applicationGroup,personalList);
			}
		} catch(Exception e) {
			logger.fatal("ERROR",e);
			ErrorData errorData = error(e);
			processResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(errorData);
			processResponse.setData(e.getMessage());
		}
		return ResponseEntity.ok(processResponse);
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
	
	public void requestExpiredCacheService(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalList) throws Exception{	
		try {		
			logger.debug(">>> Call ExpiredCacheService <<<");	
			ExpiredCache requestExpCache = new ExpiredCache();
			String lifeCycle = String.valueOf(applicationGroup.getLifeCycle());
			String applicationGroupNo = applicationGroup.getApplicationGroupNo();
			String idNo = personalList.getIdno();
			List<String> cacheIdLists = SystemConstant.getArrayListConstant("EXPIRED_CACHE_SERVICE_ID");
			requestExpCache.setApplicationGroupNo(applicationGroupNo);
			requestExpCache.setIdNo(idNo);
			requestExpCache.setLifeCycle(lifeCycle);
			logger.debug("requestExpCache applicationGroupNo >> "+applicationGroupNo);
			logger.debug("requestExpCache Idno >> "+idNo);
			logger.debug("requestExpCache lifeCycle >> "+lifeCycle);
			for(String cacheId : cacheIdLists){
				requestExpCache.getCacheIds().add(cacheId);
				logger.debug("requestExpCache cacheId >> "+cacheId);
			}
			logger.debug("ExpiredCacheServiceProxy serviceId >> "+ExpiredCacheServiceProxy.serviceId);
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(ExpiredCacheServiceProxy.serviceId);
				serviceRequest.setUserId(applicationGroup.getUserId());
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(requestExpCache);
			logger.debug("serviceRequest ObjectData >> "+requestExpCache.toString());	
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
			}else{
				throw new Exception(ServiceResponse.Status.BUSINESS_EXCEPTION+" : call decision error "); 
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);	
			throw new Exception(e.getLocalizedMessage()); 
		}
	}
}
