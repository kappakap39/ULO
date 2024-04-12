package com.eaf.service.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.SMSKMobileRecipientDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.inf.batch.ulo.notification.condition.NotificationActionInf;
import com.eaf.inf.batch.ulo.notification.condition.NotificationControl;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;
import com.eaf.notify.task.NotifyTask;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.model.KmobileRequest;
import com.kbank.eappu.model.Message;
import com.kbank.eappu.util.EAppMsgUtil;

public class NotifyKMobileUtil {
	private static transient Logger logger = Logger.getLogger(NotifyKMobileUtil.class);
	
	private static String PREFIX_CONFIG_NAME=InfBatchProperty.getInfBatchConfig("NOTIFICATION_PREFIX_CONFIG_NAME");
	
	private static String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	private static String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	private static String PRODUCT_CODE_PLP = SystemConstant.getConstant("PRODUCT_CODE_PLP");
	private static String RECOMMEND_DECISION_APPROVED = SystemConstant.getConstant("RECOMMEND_DECISION_APPROVED");
	private static String RECOMMEND_DECISION_REJECTED = SystemConstant.getConstant("RECOMMEND_DECISION_REJECTED");
	
	private NotificationInfoDataM notificationInfo;
	
	public String getMessage(ApplicationGroupDataM applicationGroup, String mobileNo, String finalAppDecision, ArrayList<String> applicationRecordId, ArrayList<NotifyTemplateDataM> notifyTemplates) throws Exception{
		
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		
		logger.debug("notifyTemplates-"+notifyTemplates);
		logger.debug("notifyTemplates size : "+notifyTemplates.size());
		if(!InfBatchUtil.empty(notifyTemplates)){
			NotifyTemplateDataM notifyTemplate;
			if(RECOMMEND_DECISION_APPROVED.equals(finalAppDecision)) {
				notifyTemplate = getNotifyTemplateApprove(notifyTemplates, applicationRecordId);		
			} else {
				notifyTemplate = getNotifyTemplateReject(notifyTemplates, applicationRecordId);				
			}
			logger.debug("notifyTemplate- "+notifyTemplate);
			if(null != notifyTemplate){
				logger.debug("notifyTemplate- getTemplateId "+notifyTemplate.getTemplateId());
				logger.debug("notifyTemplate- getTemplateType "+notifyTemplate.getTemplateType());
				logger.debug("receipt size " + ((ArrayList<RecipientInfoDataM>) notifyTemplate.getTemplateObject()).size());
			
//				ArrayList<String> recipientTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_SEND_TO_"+notifyTemplate.getTemplateId());
		
				Map<String, Object> kMobileRequest = getSMSKMobileRequest(applicationGroup, notifyTemplate);
		
				Message msg=EAppMsgUtil.createMsgTemplate(notifyTemplate.getTemplateId());
				logger.debug("Message cardNO " + msg.getCardNo());
				logger.debug("kMobileRequest-"+kMobileRequest);
				
				msg.getHeader().setRequestDateTime(ServiceUtil.formatDate(ServiceApplicationDate.getDate(),"yyyyMMddHHmmss"));
				msg.getHeader().setRequestUniqueId(ServiceUtil.generateRqUID(RqAppId,applicationGroup.getApplicationGroupId()));
				msg.getHeader().setMobileNo(mobileNo);
				
				msg.getData().setTrackingID(applicationGroup.getApplicationGroupNo());
				msg.getData().setCustomer(mobileNo);
				
				logger.debug("cardType : " + getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE)));
				logger.debug("full card no : " + getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.FULL_CARD_NUMBER)));
				msg.setCardType(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE)));
				msg.setCreditLine(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.CREDIT_LINE)));
				msg.setKbankTelNo(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_TH)));
				msg.setProductName(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH)));
				msg.setCardNo(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.CARD_NO)));
				msg.setAccountLast4Digit(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.ACCOUNT_NO_LAST_4_DIGIT)));
				msg.setTerm(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.TERM)));
				msg.setInterestRate(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.INTEREST)));
				msg.setInstallmentAmt(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.INSTALLMENT)));
				msg.setDocuments(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.EAPP_DOCUMENT_LIST_TH)));
				msg.setTransferAmount(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.AMOUNT)));
				msg.setProductName2lang(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_2LANG)));
				msg.setFullCardNo(getValue(kMobileRequest.get(TemplateBuilderConstant.TemplateVariableName.FULL_CARD_NUMBER)));
				
				HashMap<String, String> artworkMap = new HashMap<>();
				if (applicationGroup.foundProduct(PRODUCT_K_PERSONAL_LOAN)) {
					String imageIMode = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, PRODUCT_CODE_PLP, "SYSTEM_ID8");
					String imageLanding = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, PRODUCT_CODE_PLP, "SYSTEM_ID9");
					if (!InfBatchUtil.empty(imageIMode)) {
						artworkMap.put(TemplateBuilderConstant.TemplateVariableName.ARTWORK_KPLUS, imageIMode);
					}
					if (!InfBatchUtil.empty(imageLanding)) {
						artworkMap.put(TemplateBuilderConstant.TemplateVariableName.ARTWORK_LANDING_PAGE, imageLanding);
					}
				} else {
					CardDataM card = null;
					if(RECOMMEND_DECISION_APPROVED.equals(finalAppDecision)) {
						card = applicationGroup.getCardApplicationRecordId(applicationRecordId.get(0));
					}
					if (!InfBatchUtil.empty(card)) {
						artworkMap = EAppMsgUtil.getArtworkByCardType(card.getCardType());						
					}
				}
				logger.debug("Artwork card >> " + artworkMap);
				if (artworkMap.size() > 0) {
					msg.getData().setImodeImgEN(getValue(artworkMap.get(TemplateBuilderConstant.TemplateVariableName.ARTWORK_KPLUS)));
					msg.getData().setImodeImgTH(getValue(artworkMap.get(TemplateBuilderConstant.TemplateVariableName.ARTWORK_KPLUS)));
					
					List<String> landingPageImg = new ArrayList<>();
					landingPageImg.add(artworkMap.get(TemplateBuilderConstant.TemplateVariableName.ARTWORK_LANDING_PAGE));
					msg.getData().setLandingPageImgEN(landingPageImg);
					msg.getData().setLandingPageImgTH(landingPageImg);
				}
//				logger.debug("" + msg.get);
				
				return msg.toJson();
			}
		}
		
		return "";
	}
	
	public NotifyTemplateDataM getNotifyTemplateApprove(ArrayList<NotifyTemplateDataM> notifyTemplates, ArrayList<String> applicationRecordId){
		NotifyTemplateDataM sendNotifyTemplate = new NotifyTemplateDataM();
		for(NotifyTemplateDataM notifyTemplate : notifyTemplates){
			logger.debug("templateType :: " + notifyTemplate.getTemplateType());
			//get only k mobile template
			if(!TemplateBuilderConstant.TemplateType.K_MOBILE.equals(notifyTemplate.getTemplateType())){
				continue;
			}
			sendNotifyTemplate.setTemplateId(notifyTemplate.getTemplateId());
			sendNotifyTemplate.setTemplateType(notifyTemplate.getTemplateType());
			sendNotifyTemplate.setNationality(notifyTemplate.getNationality());
			
			logger.debug("applicationRecordId :: " + applicationRecordId);
			ArrayList<RecipientInfoDataM> receiptInfos = (ArrayList<RecipientInfoDataM>) ((ArrayList<RecipientInfoDataM>) notifyTemplate.getTemplateObject()).clone();
			ArrayList<RecipientInfoDataM> sendReceiptInfos = new ArrayList<RecipientInfoDataM>();
			for(RecipientInfoDataM receiptInfo : receiptInfos){
				logger.debug(receiptInfo.toString());
				logger.debug("receiptInfo applicationRecordId :: " + receiptInfo.getApplicationRecordId());
				if(applicationRecordId.contains(receiptInfo.getApplicationRecordId())){
					sendReceiptInfos.add(receiptInfo);
				}
			}
			if(null != sendReceiptInfos && sendReceiptInfos.size() > 0) {
				logger.debug("sendReceiptInfos.size() = " + sendReceiptInfos.size() + ", returns");
				sendNotifyTemplate.setTemplateObject(sendReceiptInfos);
				return sendNotifyTemplate;				
			}
		}
		logger.debug("no template return null");
		return null;
	}
	
	public NotifyTemplateDataM getNotifyTemplateReject(ArrayList<NotifyTemplateDataM> notifyTemplates, ArrayList<String> applicationRecordId){
		NotifyTemplateDataM sendNotifyTemplate = new NotifyTemplateDataM();
		for(NotifyTemplateDataM notifyTemplate : notifyTemplates){
			logger.debug("templateType :: " + notifyTemplate.getTemplateType());
			//get only k mobile template
			if(!TemplateBuilderConstant.TemplateType.K_MOBILE.equals(notifyTemplate.getTemplateType())){
				continue;
			}
			sendNotifyTemplate.setTemplateId(notifyTemplate.getTemplateId());
			sendNotifyTemplate.setTemplateType(notifyTemplate.getTemplateType());
			sendNotifyTemplate.setNationality(notifyTemplate.getNationality());
			sendNotifyTemplate.setTemplateObject(notifyTemplate.getTemplateObject());
			
			logger.debug("applicationRecordId :: " + applicationRecordId);
			ArrayList<RecipientInfoDataM> receiptInfos = (ArrayList<RecipientInfoDataM>) ((ArrayList<RecipientInfoDataM>) notifyTemplate.getTemplateObject()).clone();
			ArrayList<RecipientInfoDataM> sendReceiptInfos = new ArrayList<RecipientInfoDataM>();
			for(RecipientInfoDataM receiptInfo : receiptInfos){
				logger.debug(receiptInfo.toString());
				logger.debug("receiptInfo applicationRecordId :: " + receiptInfo.getApplicationRecordId());
				if(applicationRecordId.contains(receiptInfo.getApplicationRecordId())){
					return notifyTemplate;
				}
			}
		}
		logger.debug("no template return null");
		return null;
	}
	
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult,RecipientTypeDataM recipient, String applicationRecordId) throws Exception {
		String NOTIFICATION_SEND_TO_TYPE_CUSTOMER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
		ArrayList<NotifyTemplateDataM>  notifyTemplates = new ArrayList<NotifyTemplateDataM>();
		RecipientTypeDataM recipientTypeDataM =recipient;
		NotificationInfoDataM notification = (NotificationInfoDataM)transactionResult.getTransactionObject();
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();	
		String APP_STATUS = notification.getApplicationStatus();
		String APPLICATION_GROUP_ID = notification.getApplicationGroupId();
		int LIFE_CYCLE = notification.getMaxLifeCycle();

		
		if(!InfBatchUtil.empty(recipientTypeDataM.getSmsKMobileRecipients())){//listSmsRecipients
			ArrayList<SMSKMobileRecipientDataM> listKmobileRecipients = recipientTypeDataM.getSmsKMobileRecipients();
			if(!InfBatchUtil.empty(listKmobileRecipients)){
				HashMap<String, ArrayList<RecipientInfoDataM>> hTemplateApplicant = new HashMap<String, ArrayList<RecipientInfoDataM>>();
				for(SMSKMobileRecipientDataM kMobile: listKmobileRecipients){
					String SEND_TO =kMobile.getRecipientType();
					ArrayList<RecipientInfoDataM>  recipientinfos= (ArrayList<RecipientInfoDataM>)kMobile.getRecipientObject();
					logger.debug("KMobileRecipient SEND_TO >> "+SEND_TO);
					if(null != recipientinfos){
						for(RecipientInfoDataM recipientinfo :recipientinfos){
							logger.debug("applicationRecordId : " + applicationRecordId);
							logger.debug("recipientinfo.getApplicationRecordId() : " + recipientinfo.getApplicationRecordId());
							if(!Util.empty(applicationRecordId) && !applicationRecordId.equals(recipientinfo.getApplicationRecordId())){
								continue;
							}
							HashMap<String, ArrayList<String>>  hListTemplate = notificationDAO.loadSMSTemplateNotification(notification,recipientinfo);
							ArrayList<String> listSMSTemplate = hListTemplate.get(SEND_TO);
							if(null!=listSMSTemplate && listSMSTemplate.size()>0){
								for(String templateId : listSMSTemplate){
									kMobile.addTemplates(templateId);
									ArrayList<RecipientInfoDataM> mapRecipients =(ArrayList<RecipientInfoDataM>)hTemplateApplicant.get(templateId);
									if(InfBatchUtil.empty(mapRecipients)){
										mapRecipients = new ArrayList<RecipientInfoDataM>();
										hTemplateApplicant.put(templateId, mapRecipients);
									}
									if(!mapRecipients.contains(recipientinfo)){
										mapRecipients.add(recipientinfo);
									}
								} 
							}
						}
					}
				}
				if(null!=hTemplateApplicant && !hTemplateApplicant.isEmpty()){
					for(String tempId: new ArrayList<>(hTemplateApplicant.keySet())){
						ArrayList<RecipientInfoDataM> recipientinfoMap = hTemplateApplicant.get(tempId);
						notifyTemplates.add(getNotifyTemplate(tempId,TemplateBuilderConstant.TemplateType.K_MOBILE,recipientinfoMap));
					}
				}
			}
		}
		return notifyTemplates;		
	}
	
	
	public RecipientTypeDataM getRecipient(NotificationInfoRequestDataM notificationRequest) throws Exception{
		
	
		
		RecipientTypeDataM recipientType = new RecipientTypeDataM();
		//NotificationInfoRequestDataM notificationRequest = (NotificationInfoRequestDataM) notifyRequest.getRequestObject();
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();	
		notificationInfo  = notificationDAO.getNotificationInfo(notificationRequest);		
		if(!InfBatchUtil.empty(notificationInfo)){
			notificationInfo.setApplicationStatus(notificationRequest.getApplicationStatus());
			notificationInfo.setApplicationGroupId(notificationRequest.getApplicationGroupId());
			notificationInfo.setSendingTime(notificationRequest.getSendingTime());
			notificationInfo.setSaleType(notificationRequest.getSaleType());
			notificationInfo.setMaxLifeCycle(notificationRequest.getLifeCycle());
			notificationInfo.setApplyType(NotificationUtil.getApplicationType(notificationInfo.getApplicationGroupId(), notificationInfo.getApplicationType()
					, notificationInfo.getApplicationTemplate()));
			ArrayList<String> sendTos = notificationInfo.getSendTos();
			if(!InfBatchUtil.empty(sendTos)){
				for(String sendTo :sendTos){
					NotificationActionInf notification = NotificationControl.getNotification(PREFIX_CONFIG_NAME+sendTo);
					notification.notificationProcessCondition(notificationInfo,recipientType);
				}
			}
		}
		return recipientType;
	}
	
	private NotifyTemplateDataM getNotifyTemplate(String templateId,String templateType,ArrayList<RecipientInfoDataM> recipientinfos){
		if(!InfBatchUtil.empty(templateId) && !InfBatchUtil.empty(templateType)){
			NotifyTemplateDataM notifyTemplate = new NotifyTemplateDataM();
			notifyTemplate.setTemplateId(templateId);
			notifyTemplate.setTemplateType(templateType);
			notifyTemplate.setTemplateObject(recipientinfos);
			if(!Util.empty(recipientinfos.get(0).getNationality())){
				notifyTemplate.setNationality(recipientinfos.get(0).getNationality());
			}
			logger.debug("templateId >> "+templateId);
			logger.debug("templateType >> "+templateType);
			return notifyTemplate;
		}
		return null;
	}
	
	
	public NotifyTransactionDataM processNotifyTransaction(RecipientTypeDataM recipient){
		NotifyTransactionDataM notifyTransaction = null;
		if(null!= recipient && recipient.isValid()){
			notifyTransaction = new NotifyTransactionDataM();
			 ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
			 NotifyTransactionResultDataM notifyTransactionResult = new NotifyTransactionResultDataM();
			 notifyTransactionResult.setTransactionObject(notificationInfo);
			 notifyTransactionResult.setUniqueId(notificationInfo.getApplicationGroupId(),notificationInfo.getMaxLifeCycle());
			 transactions.add(notifyTransactionResult);
			 notifyTransaction.setTransactions(transactions);
		}
		return notifyTransaction;
	}
	
//	public HashMap<String, Object> getParam(RecipientTypeDataM recipient){
//		TemplateDAO  dao = NotificationFactory.getTemplateDAO();
//		ArrayList<RecipientInfoDataM> receipientInfos = new ArrayList<RecipientInfoDataM>();
//		receipientInfos.add(notificationInfo);
//		TemplateMasterDataM templateMaster = new TemplateMasterDataM();
//	//	logger.debug(">>GET TEMPLATE VARIALBE<< By condition ="+ new Gson().toJson(receipientInfos));
//		HashMap<String, Object> hData = dao.getSMSTemplateCCandKECNonIncrease(receipientInfos,templateMaster);
//	}
	
	
	public Map<String, Object> getSMSKMobileRequest(ApplicationGroupDataM applicationGroup, NotifyTemplateDataM notifyTemplate) throws Exception {
		logger.debug("getNotification KMobile Request..");
		String TEMPLATE_ID=notifyTemplate.getTemplateId();
		ArrayList<String> recipientTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_SEND_TO_"+TEMPLATE_ID);
		String NOTIFICATION_SEND_TO_TYPE_CUSTOMER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
		logger.debug("template id : "+TEMPLATE_ID);
		logger.debug("recipientTypes : "+InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_SEND_TO_"+TEMPLATE_ID));
		for(String recipientType : recipientTypes){
			if(NOTIFICATION_SEND_TO_TYPE_CUSTOMER.equals(recipientType)){
				TemplateController templateController = new TemplateController();
				TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
				templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
				templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
				templateBuilderRequest.setDbType(notifyTemplate.getDbType());
				templateBuilderRequest.setRequestObject(notifyTemplate.getTemplateObject());
				templateBuilderRequest.setUniqueId(new NotifyTask(applicationGroup.getApplicationGroupId(), applicationGroup.getLifeCycle()));
				templateBuilderRequest.setLanguage(notifyTemplate.getNationality());
				
				TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
				return templateBuilderResponse.getTemplateVariable().getTemplateVariable();
				
//				kMobileRequest.setRequestID(transactionResult.getUniqueId());
//				kMobileRequest.setUsername(NOTIFICATION_SEND_KMOBILE_USERNAME);
//				kMobileRequest.setPassphrase(NOTIFICATION_SEND_KMOBILE_PASSPHRASE);
//				kMobileRequest.setPageCode(NOTIFICATION_SEND_KMOBILE_PAGECODE);
//				kMobileRequest.setMobileNo(recipient.getKMobileNos(recipientTypes,TEMPLATE_ID).get(0));
//				kMobileRequest.setTemplateName(templateBuilderResponse.getTemplateName());
//				kMobileRequest.setMessageTH(templateBuilderResponse.getBodyMsgTh());
//				kMobileRequest.setMessageEN(templateBuilderResponse.getBodyMsgEn());
//				kMobileRequest.setImageType(BATCH_KMOBILE_IMAGE_TYPE_DEFAULT);
//				kMobileRequest.setMessageName(BATCH_KMOBILE_MESSAGE_NAME_DEFAULT);
//				kMobileRequest.setCampaignCode(BATCH_KMOBILE_CAMPAIGN_CODE_DEFAULT);				
//				kMobileRequest.setSendBy(BATCH_KMOBILE_SEND_BY);
//				kMobileRequest.setPageCode(BATCH_KMOBILE_PAGE_CODE);
//				kMobileRequest.setSendFlag(BATCH_KMOBILE_SEND_FLAG);
//				kMobileRequest.setAlertMessageTH(templateBuilderResponse.getAlertMessageTh());
//				kMobileRequest.setAlertMessageEN(templateBuilderResponse.getAlertMessageEn());
			}
		}
		return new HashMap<>();
	}
	
	public String getValue(Object obj){
		if(obj==null){
			return "";
		}
		
		return (String) obj;
	}
}
