package com.eaf.orig.ulo.app.view.form.manual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.NotifyMessage;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.ejb.view.ApplicationManager;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.NotePadDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.ReprocessRemarkDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.ExpiredCacheServiceProxy;
import com.eaf.service.rest.model.ServiceResponse;
import com.kasikornbank.expiredcache.ExpiredCache;

public class SubmitApplicationReprocessPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(SubmitApplicationReprocessPopupForm.class);
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	@Override
	public Object getObjectForm()throws Exception{
		String[] applicationGroupIds = request.getParameterValues("application_group_ids");
		logger.debug("applicationGroupIds : "+Arrays.toString(applicationGroupIds));
		if(!Util.empty(applicationGroupIds)){
			for(String applicationGroupId : applicationGroupIds){
				setUniqueId(applicationGroupId);
			}
		}
		return new ReprocessRemarkDataM();
	}
	@Override
	public String processForm(){
		String LOG_MESSAGE_BLOCK_REPROCESS = MessageUtil.getText(request,"LOG_MESSAGE_BLOCK_REPROCESS");
		ArrayList<String> applicationGroupIds = getUniqueIds();
		logger.debug("applicationGroupIds : "+applicationGroupIds);
		ReprocessRemarkDataM reprocessRemarkM = (ReprocessRemarkDataM)objectForm;
		if(null==reprocessRemarkM){
			reprocessRemarkM = new ReprocessRemarkDataM();
		}
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
		OrigApplicationGroupDAO applicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
		ApplicationManager manager = ORIGServiceProxy.getApplicationManager();
		String reprocessRemark = reprocessRemarkM.getRemark();
		if(!Util.empty(applicationGroupIds)){
			for(String applicationGroupId : applicationGroupIds){
				try{
					ApplicationGroupDataM applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
						applicationGroup.setReprocessFlag(FLAG_YES);
					if(!Util.empty(reprocessRemark)){
						ArrayList<NotePadDataM> notePads = applicationGroup.getNotePads();
						if(null==notePads){
							notePads = new ArrayList<NotePadDataM>(); 
						}
						NotePadDataM notePad = new NotePadDataM();
							notePad.setSeq(notePads.size());
						 	notePad.setNotePadDesc(reprocessRemark);
							notePad.setUpdateBy(userM.getUserName());
							notePad.setUpdateDate(ApplicationDate.getTimestamp());
							notePad.setCreateBy(userM.getUserName());
							notePad.setUserRole(ORIGForm.getRoleId());
							notePad.setCreateDate(ApplicationDate.getTimestamp());
							notePad.setStatus(FLAG_YES);
						notePads.add(notePad);
						applicationGroup.setNotePads(notePads);
					}
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
					manager.reprocessApplication(applicationGroup,userM,LOG_MESSAGE_BLOCK_REPROCESS);
					//Call ExpiredCacheSerice iib
					for(PersonalInfoDataM personalList : applicationGroup.getPersonalInfos()){
						requestExpiredCacheService(applicationGroup,personalList);
					}
					
//					ReprocessLogDataM reprocessLog = new ReprocessLogDataM();
//						reprocessLog.setApplicationGroupId(applicationGroupId);
//						reprocessLog.setLifeCycle(applicationGroup.getLifeCycle());
//						reprocessLog.setCreateBy(userM.getUserName());
//						reprocessLog.setCreateDate(ApplicationDate.getTimestamp());
//						reprocessLog.setUpdateBy(userM.getUserName());
//						reprocessLog.setUpdateDate(ApplicationDate.getTimestamp());
//					manager.reprocessApplication(applicationGroup,reprocessLog,userM,LOG_MESSAGE_BLOCK_REPROCESS);
				}catch(Exception e){
					logger.fatal("ERROR",e);
					NotifyForm.error(request,NotifyMessage.errorProcessActionApplication(request,LabelUtil.getText(request,"SUBMIT_REPROCESS"),applicationGroupId));
				}
			}
		}
		return "";
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
