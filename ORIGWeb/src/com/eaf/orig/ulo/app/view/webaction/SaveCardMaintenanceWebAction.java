package com.eaf.orig.ulo.app.view.webaction;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.postapproval.CJDCardLinkAction;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.dao.OrigCardLinkCustomerDAO;
import com.eaf.orig.ulo.app.ejb.view.GeneratorManager;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.xrules.dao.OrigCardMaintenanceDAO;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.action.ApplicationAction;
import com.eaf.orig.ulo.control.util.BackWebActionUtil;
import com.eaf.orig.ulo.formcontrol.view.form.CardMaintenanceFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDataM;
import com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDetailDataM;
import com.eaf.orig.ulo.model.cardMaintenance.PersonalCardMaintenanceDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.proxy.BpmProxyConstant.BPMActivity;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

@SuppressWarnings("serial")
public class SaveCardMaintenanceWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(LoadCardMaintenanceWebAction.class);
	String CARDLINK_FLAG_FAIL = SystemConstant.getConstant("CARDLINK_FLAG_FAIL");
	String CARDLINK_FLAG_SUCCESS = SystemConstant.getConstant("CARDLINK_FLAG_SUCCESS");
	String CARDLINK_BATCH_WAITING = SystemConstant.getConstant("CARDLINK_BATCH_WAITING");
	String CARDLINK_GENERATE_CARDLINK_FLAG = SystemConstant.getConstant("CARDLINK_GENERATE_CARDLINK_FLAG");
	String CARDLINK_RESEND_CARDLINK = SystemConstant.getConstant("CARDLINK_RESEND_CARDLINK");
	String CARDLINK_CANCEL_CARDLINK = SystemConstant.getConstant("CARDLINK_CANCEL_CARDLINK");
	int CARDLINK_TEN_MILLION = Integer.parseInt(SystemConstant.getConstant("CARDLINK_TEN_MILLION"));
	String CARDLINK_REASON_CODE = SystemConstant.getConstant("CARDLINK_REASON_CODE");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String CACHE_ORGANIZATION = SystemConstant.getConstant("CACHE_ORGANIZATION");
	String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	Integer MAIN_CARDLINK_SEQ = SystemConstant.getIntConstant("MAIN_CARDLINK_SEQ");
	Integer SUP_CARDLINK_SEQ = SystemConstant.getIntConstant("SUP_CARDLINK_SEQ");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	private Integer mainCardLinkSeq = MAIN_CARDLINK_SEQ;
	private Integer supCardLinkSeq = SUP_CARDLINK_SEQ;
	@Override
	public Event toEvent() {
		return null;
	}
	@Override
	public boolean requiredModelRequest() {
		return false;
	}
	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}
	String applicationGroupId = "";
	@Override
	public boolean preModelRequest(){
		try{
			UserDetailM user = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			CardMaintenanceFormHandler formHandler = (CardMaintenanceFormHandler)getRequest().getSession().getAttribute("CardMaintenanceForm");
			CardMaintenanceDataM cardMaintenance = formHandler.getObjectForm();
			String regType = cardMaintenance.getRegType();
			applicationGroupId = cardMaintenance.getApplicationGroupId();
			ArrayList<PersonalCardMaintenanceDataM> personalCardMaintenances = cardMaintenance.getPersonalCardMaintenances();
			if(!Util.empty(personalCardMaintenances)){
				Collections.sort(personalCardMaintenances,new PersonalCardMaintenanceDataM());
				for(PersonalCardMaintenanceDataM personalCardMaintenance:personalCardMaintenances){
					String cardlinkFlag = personalCardMaintenance.getPersonalStatus();
					if(CARDLINK_FLAG_SUCCESS.equals(cardlinkFlag)
							||CARDLINK_BATCH_WAITING.equals(cardlinkFlag)){
						if(null!=personalCardMaintenance.getCardMaintenanceDetails()){
							for(CardMaintenanceDetailDataM cardMaintenanceDetail:personalCardMaintenance.getCardMaintenanceDetails()){
								processCardLinkCard(applicationGroupId, cardMaintenanceDetail,personalCardMaintenance.getCustNo()
										,personalCardMaintenance.getPersonalId(),cardlinkFlag, regType);
							}
						}
					}else if(CARDLINK_FLAG_FAIL.equals(cardlinkFlag)){
						processCardlinkCustomer(cardMaintenance.getApplicationGroupId(), personalCardMaintenance, regType);
					}
				}
				if(foundAllCancelCardlink(personalCardMaintenances)){
					if("MLP".equals(regType))
					{
						ORIGDAOFactory.getCardMaintenanceDAO().cancelAppGroupMLP(cardMaintenance.getApplicationGroupId(), user.getUserName());
					}
					else
					{
						cancelApplication(cardMaintenance.getApplicationGroupId(),user);
					}
				}
				else
				{
					//CJD call processCardLinkAction again to do work 
					//from CardLinkFlag changes of Save Card Maintenance
					if(CJDCardLinkAction.isCJDApplication(applicationGroupId))
					{
						String applicationGroupNo = null;
						applicationGroupNo = CJDCardLinkAction.getApplicationGroupNoMLP(applicationGroupId);						
						CJDCardLinkAction.processCardLinkAction(applicationGroupId, applicationGroupNo);
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			NotifyForm.error(getRequest());
			return false;
		}
		return true;
	}
	public boolean foundAllCancelCardlink(ArrayList<PersonalCardMaintenanceDataM> personalCardMaintenances){
		boolean foundAllCancel = false;
		int cancelCardlinkCustomer = 0;
		int cancelCardlinkApplication = 0;
		if(!Util.empty(personalCardMaintenances)){
			int totalCardlinkCustomer = personalCardMaintenances.size();
			int totalCardLinkApplication = 0;
			for (PersonalCardMaintenanceDataM personalCardMaintenance: personalCardMaintenances) {
				if(!Util.empty(personalCardMaintenances)){
					String personalType = personalCardMaintenance.getPersonalType();
					String PERSONAL_CARD_MAINTENANCE_ACTION = getRequest().getParameter("PERSONAL_CARD_MAINTENANCE_ACTION_"+personalType);
					if(CARDLINK_CANCEL_CARDLINK.equals(PERSONAL_CARD_MAINTENANCE_ACTION)){
						cancelCardlinkCustomer++;
					}
					if(!Util.empty(personalCardMaintenance.getCardMaintenanceDetails())){
						totalCardLinkApplication += personalCardMaintenance.getCardMaintenanceDetails().size();
						for(CardMaintenanceDetailDataM cardMaintenanceDetail : personalCardMaintenance.getCardMaintenanceDetails()){
							if(!Util.empty(cardMaintenanceDetail)){
								String applicationType = cardMaintenanceDetail.getApplicationType();
								String cardType = cardMaintenanceDetail.getCardType();
								String CARD_MAINTENANCE_ACTION = getRequest().getParameter("CARD_MAINTENANCE_ACTION_"+applicationType+"_"+cardType);
								if(CARDLINK_CANCEL_CARDLINK.equals(CARD_MAINTENANCE_ACTION)){
									cancelCardlinkApplication++;
								}
							}
						}
					}
				}
			}
			logger.debug("totalCardlinkCustomer : "+totalCardlinkCustomer);
			logger.debug("totalCardLinkApplication : "+totalCardLinkApplication);
			logger.debug("cancelCardlinkCustomer : "+cancelCardlinkCustomer);
			logger.debug("cancelCardlinkApplication : "+cancelCardlinkApplication);
			if(cancelCardlinkCustomer > 0 && cancelCardlinkCustomer == totalCardlinkCustomer){
				foundAllCancel = true;
			}else if(cancelCardlinkApplication > 0 && cancelCardlinkApplication == totalCardLinkApplication){
				foundAllCancel = true;
			}
		}
		logger.debug("foundAllCancel : "+foundAllCancel);
		return foundAllCancel;
	}
	public void processCardLinkCard(String applicationGroupId, CardMaintenanceDetailDataM cardMaintenanceDetail, String cardlinkCustNo,String personalId, String cardlinkFlag, String regType) throws Exception{
		String applicationRecordId = cardMaintenanceDetail.getApplicationRecordId();
		String applicationType = cardMaintenanceDetail.getApplicationType();
		String cardType = cardMaintenanceDetail.getCardType();
		logger.debug("applicationRecordId : "+applicationRecordId);
		OrigCardMaintenanceDAO origCardMaintenanceDAO = ORIGDAOFactory.getCardMaintenanceDAO();
		if(CARDLINK_FLAG_FAIL.equals(cardMaintenanceDetail.getCardStatus())
				||(CARDLINK_TEN_MILLION>cardMaintenanceDetail.getLoanAmt().intValue()
						&&CARDLINK_FLAG_SUCCESS.equals(cardMaintenanceDetail.getCardStatus()))){
			String CARD_MAINTENANCE_ACTION = getRequest().getParameter("CARD_MAINTENANCE_ACTION_"+applicationType+"_"+cardType);
			logger.debug("CARD_MAINTENANCE_ACTION : "+CARD_MAINTENANCE_ACTION);
			if(CARDLINK_GENERATE_CARDLINK_FLAG.equals(CARD_MAINTENANCE_ACTION)){
				CardDataM card = new CardDataM();
				String cardId = origCardMaintenanceDAO.getCardId(applicationRecordId, regType);
				card.setCardType(cardMaintenanceDetail.getCardType());
				card = genarateCardNo(card);
				card.setCardId(cardId);
				card.setPersonalId(personalId);
				origCardMaintenanceDAO.updateCard(card, regType);
				if(CARDLINK_FLAG_SUCCESS.equals(cardlinkFlag)){
					origCardMaintenanceDAO.updateNewCardlinkCustFlag(cardlinkCustNo,personalId, regType);
				}
				origCardMaintenanceDAO.resendCardlinkCard(applicationRecordId,APPLICATION_CARD_TYPE_BORROWER.equals(cardMaintenanceDetail.getApplicationType())?mainCardLinkSeq++:supCardLinkSeq++, regType);
			}else if(CARDLINK_RESEND_CARDLINK.equals(CARD_MAINTENANCE_ACTION)){
				if(CARDLINK_FLAG_SUCCESS.equals(cardlinkFlag)){
					origCardMaintenanceDAO.updateNewCardlinkCustFlag(cardlinkCustNo,personalId, regType);
				}
				origCardMaintenanceDAO.resendCardlinkCard(applicationRecordId,APPLICATION_CARD_TYPE_BORROWER.equals(cardMaintenanceDetail.getApplicationType())?mainCardLinkSeq++:supCardLinkSeq++, regType);
			}else if(CARDLINK_CANCEL_CARDLINK.equals(CARD_MAINTENANCE_ACTION)){
				origCardMaintenanceDAO.cancelCardlinkCard(applicationRecordId, regType);
				UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
				String cancelReason = getRequest().getParameter("CARD_CANCEL_REASON_"+applicationType+"_"+cardType);
				if(MConstant.FLAG_S.equals(applicationType) && Util.empty(cancelReason))
				{
					cancelReason = getRequest().getParameter("CARD_CANCEL_REASON_"+MConstant.FLAG_B+"_"+cardType);
				}
				
				//Send cancellation to MLS
				if(CJDCardLinkAction.isCJDApplication(applicationGroupId))
				{
					Encryptor enc = EncryptorFactory.getDIHEncryptor();
					String cardNoEncrypt = cardMaintenanceDetail.getCardNo();
					String cardNo = enc.decrypt(cardNoEncrypt);
					String cancelReasonMLS = "Cancelled by CardMaintenance " + userM.getUserName() + " - reason: " + cancelReason;
					CJDCardLinkAction.sendCSRResultToMLS(applicationGroupId, REASON_TYPE_CANCEL, cardNoEncrypt, 
							cardNo, cancelReasonMLS, cardlinkCustNo);
				}
				
				if("MLP".equals(regType))
				{
					origCardMaintenanceDAO.cancelAppMLP(applicationRecordId, userM.getUserName());
					origCardMaintenanceDAO.createCancelReasonMLP(applicationGroupId, applicationRecordId, cancelReason, userM.getUserName(), userM.getCurrentRole());
				}
				else
				{
					ArrayList<String> cancelUniqueIds = new ArrayList<String>();
					cancelUniqueIds.add(applicationRecordId);
					ReasonDataM reasonM = new ReasonDataM();
					reasonM.setCreateBy(userM.getUserName());
					reasonM.setUpdateBy(userM.getUserName());
					reasonM.setReasonCode(CARDLINK_REASON_CODE);
					reasonM.setReasonType(REASON_TYPE_CANCEL);
					reasonM.setRemark(!Util.empty(cancelReason) ? cancelReason : "BPM decision Cancel card maintenance");	
					ApplicationReasonDataM applicationReason = mapApplicationReason(reasonM);
					ORIGServiceProxy.getApplicationManager().cancelApplication(applicationGroupId,cancelUniqueIds,applicationReason,userM);
				}
			}
		}
	}
	public void processCardlinkCustomer(String applicationGroupId, PersonalCardMaintenanceDataM personalCardMaintenance,String regType) throws Exception{
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String personalId = personalCardMaintenance.getPersonalId();
		String personalType = personalCardMaintenance.getPersonalType();
		OrigCardMaintenanceDAO origCardMaintenanceDAO = ORIGDAOFactory.getCardMaintenanceDAO();
		
		if(CARDLINK_FLAG_FAIL.equals(personalCardMaintenance.getPersonalStatus())){
			String PERSONAL_CARD_MAINTENANCE_ACTION = getRequest().getParameter("PERSONAL_CARD_MAINTENANCE_ACTION_"+personalType);
			logger.debug("PERSONAL_CARD_MAINTENANCE_ACTION : "+PERSONAL_CARD_MAINTENANCE_ACTION);
			if(CARDLINK_GENERATE_CARDLINK_FLAG.equals(PERSONAL_CARD_MAINTENANCE_ACTION)){
				OrigCardLinkCustomerDAO origCardlinkCustomerDAO = ORIGDAOFactory.getCardLinkCustomerDAO(userM.getUserName());
				String businessClassId = personalCardMaintenance.getBusinessClassId();
				logger.info("businessClassId : "+businessClassId);
				String orgId =  CacheControl.getName(CACHE_BUSINESS_CLASS,"BUS_CLASS_ID",businessClassId,"ORG_ID");
				String orgNo = CacheControl.getName(CACHE_ORGANIZATION,"ORG_ID",orgId,"ORG_NO");
				CardDataM card = new CardDataM();
				card.setCardType(personalCardMaintenance.getCardMaintenanceDetails().get(0).getCardType());
				String cardlinkCustNo = generateCardLinkCustNo(card);
				String cardLinkCustId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARDLINK_CUSTOMER_PK);
				CardlinkCustomerDataM cardLinkCustM = new CardlinkCustomerDataM();
				cardLinkCustM.setCardlinkCustId(cardLinkCustId);
				cardLinkCustM.setCardlinkCustNo(cardlinkCustNo);
				cardLinkCustM.setNewCardlinkCustFlag(MConstant.FLAG.YES);
				cardLinkCustM.setOrgId(orgNo);
				cardLinkCustM.setPersonalId(personalId);
				origCardlinkCustomerDAO.createOrigCardlinkCustomerM(cardLinkCustM);
				if(null!=personalCardMaintenance.getCardMaintenanceDetails()){
					for(CardMaintenanceDetailDataM cardMaintenanceDetail:personalCardMaintenance.getCardMaintenanceDetails()){
						origCardMaintenanceDAO.resendCardlinkCard(cardMaintenanceDetail.getApplicationRecordId(),APPLICATION_CARD_TYPE_BORROWER.equals(cardMaintenanceDetail.getApplicationType())?mainCardLinkSeq++:supCardLinkSeq++, regType);
						String cardId = cardMaintenanceDetail.getCardId();
						origCardMaintenanceDAO.updateCustIdOrigCard(cardId,cardLinkCustId, regType);
						origCardMaintenanceDAO.resendCardlinkCust(personalCardMaintenance.getPersonalId(),cardLinkCustM.getCardlinkCustNo(), regType);
					}
				}
			}else if(CARDLINK_RESEND_CARDLINK.equals(PERSONAL_CARD_MAINTENANCE_ACTION)){
				origCardMaintenanceDAO.resendCardlinkCust(personalId,personalCardMaintenance.getCustNo(), regType);
				if(null!=personalCardMaintenance.getCardMaintenanceDetails()){
					for(CardMaintenanceDetailDataM cardMaintenanceDetail:personalCardMaintenance.getCardMaintenanceDetails()){
						origCardMaintenanceDAO.resendCardlinkCard(cardMaintenanceDetail.getApplicationRecordId(),APPLICATION_CARD_TYPE_BORROWER.equals(cardMaintenanceDetail.getApplicationType())?mainCardLinkSeq++:supCardLinkSeq++, regType);
					}
				}
			}else if(CARDLINK_CANCEL_CARDLINK.equals(PERSONAL_CARD_MAINTENANCE_ACTION)){
				origCardMaintenanceDAO.cancelCardlinkCust(personalId, regType);
				if(null!=personalCardMaintenance.getCardMaintenanceDetails()){
					for(CardMaintenanceDetailDataM cardMaintenanceDetail:personalCardMaintenance.getCardMaintenanceDetails()){
						origCardMaintenanceDAO.cancelCardlinkCard(cardMaintenanceDetail.getApplicationRecordId(), regType);
						
						String cancelReason = getRequest().getParameter("PERSONAL_CARD_CANCEL_REASON_" + personalType);
						String remark = !Util.empty(cancelReason) ? cancelReason : "BPM decision Cancel card maintenance";
						String applicationRecordId = cardMaintenanceDetail.getApplicationRecordId();
						if("MLP".equals(regType))
						{
							origCardMaintenanceDAO.createCancelReasonMLP(applicationGroupId, applicationRecordId, remark, userM.getUserName(), userM.getCurrentRole());	
						}
						else
						{
							ArrayList<String> cancelUniqueIds = new ArrayList<String>();
							cancelUniqueIds.add(applicationRecordId);
							ReasonDataM reasonM = new ReasonDataM();
							reasonM.setCreateBy(userM.getUserName());
							reasonM.setUpdateBy(userM.getUserName());
							reasonM.setReasonCode(CARDLINK_REASON_CODE);
							reasonM.setReasonType(REASON_TYPE_CANCEL);
							reasonM.setRemark(remark);	
							ApplicationReasonDataM applicationReason = mapApplicationReason(reasonM);
							ORIGServiceProxy.getApplicationManager().cancelApplication(applicationGroupId,cancelUniqueIds,applicationReason,userM);
						}
					}
				}
			}
		}
	}
	public String generateCardLinkCustNo(CardDataM card){
		GeneratorManager genManager = ORIGServiceProxy.getGeneratorManager();
		String cardlinkCustNo = genManager.generateCardLinkCustNo(card);
		logger.debug("cardlinkCustNo : "+cardlinkCustNo);
		return cardlinkCustNo;
	}
	public CardDataM genarateCardNo(CardDataM card) throws Exception{
		//Generate Card No
		GeneratorManager genManager = ORIGServiceProxy.getGeneratorManager();
		String cardNo = genManager.generateCardNo(card);
		logger.debug("cardNo : "+cardNo);
		card.setCardNo(cardNo);
		card.setCardNoMark(FormatUtil.maskNumber(cardNo,"######XXXXXX####"));	
		
		//Encrypt Card No for DIH
		Encryptor dihEnc = EncryptorFactory.getDIHEncryptor();
		String encryptedCardDIH = dihEnc.encrypt(card.getCardNo());
		logger.debug("encryptedCardDIH : "+encryptedCardDIH);	
		
		//Encrypt CardNo for KmAlert
		Encryptor kmEnc = EncryptorFactory.getKmAlertEncryptor();
		String encryptedCardKmAlert = kmEnc.encrypt(card.getCardNo());
		logger.debug("encryptedCardKmAlert : "+encryptedCardKmAlert);
		card.setCardNoEncrypted(encryptedCardKmAlert);
		
		//Hashing Card No.
		Hasher hash = HashingFactory.getSHA256Hasher();
		String hashedCardNo = hash.getHashCode(card.getCardNo());
		logger.debug("hashedCardNo : "+hashedCardNo);
		card.setHashingCardNo(hashedCardNo);
		card.setCardNo(encryptedCardDIH);
		return card;
	}
	public void cancelApplication(String applicationGroupId, UserDetailM userM) throws Exception{
		//ReasonDataM reasonM = new ReasonDataM();
		//reasonM.setCreateBy(userM.getUserName());
		//reasonM.setUpdateBy(userM.getUserName());
		//reasonM.setReasonCode(CARDLINK_REASON_CODE);
		//reasonM.setReasonType(REASON_TYPE_CANCEL);
		//reasonM.setRemark(!Util.empty(cancelReason) ? cancelReason : "BPM decision Cancel card maintenance");	
		//ApplicationReasonDataM applicationReason = mapApplicationReason(reasonM);
		OrigApplicationGroupDAO origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
		//int maxLifeCycle = origApplicationGroup.getMaxLifeCycle(applicationGroupId);
		//logger.debug("maxLifeCycle : " + maxLifeCycle);
		//ArrayList<String> cancelUniqueIds = ORIGDAOFactory.getApplicationDAO().loadApplicationUniqueIds(applicationGroupId, maxLifeCycle);
		//logger.debug("cancelUniqueIds : " + cancelUniqueIds);
		//ORIGServiceProxy.getApplicationManager().cancelApplication(applicationGroupId, cancelUniqueIds, applicationReason, userM);
		String BPM_DECISION_CANCEL = SystemConstant.getConstant("BPM_DECISION_CANCEL");
		logger.debug("BPM_DECISION_CANCEL : " + BPM_DECISION_CANCEL);
		origApplicationGroup.updateLastDecision(applicationGroupId,BPM_DECISION_CANCEL);
		String instantId = origApplicationGroup.getInstantId(applicationGroupId);
		logger.debug("instantId : " + instantId);
		WorkflowRequestDataM workFlowRequest = new WorkflowRequestDataM();
		workFlowRequest.setInstantId(instantId);
		workFlowRequest.setActivity(BPMActivity.CANCEL_PROCESS);
		workFlowRequest.setUsername(userM.getUserName());
		workFlowRequest.setFromRole(userM.getCurrentRole());
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT, BPM_USER_ID, BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = workflowManager.cancelBPMInstance(workFlowRequest);
		logger.debug("workflowResponse.getResultCode : " + workflowResponse.getResultCode());
		logger.debug("workflowResponse.getResultDesc : " + workflowResponse.getResultDesc());
		if (BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())) {
			throw new Exception(workflowResponse.getResultDesc());
		}
	}
	public ApplicationReasonDataM mapApplicationReason(ReasonDataM reasonM){
		return ApplicationAction.mapApplicationReason(reasonM);
	}
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	@Override
	public String getNextActionParameter() {
		return BackWebActionUtil.processBackWebAction(getRequest());
	}
	@Override
	protected void doSuccess(EventResponse erp) {
	}
	@Override
	protected void doFail(EventResponse erp) {
		
	}
}
