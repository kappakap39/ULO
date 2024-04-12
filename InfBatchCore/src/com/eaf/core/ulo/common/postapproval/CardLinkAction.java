package com.eaf.core.ulo.common.postapproval;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.ProcessApplicationManager;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.ulo.app.util.CardLinkCustomerUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.core.ulo.common.postapproval.CJDCardLinkAction;

public class CardLinkAction {
	private static transient Logger logger = Logger.getLogger(CardLinkAction.class);	
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String DECISION_FINAL_DECISION_APPROVE = SystemConstant.getConstant("DECISION_FINAL_DECISION_APPROVE");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");	
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	int MAX_MAIN_CARDLINK = SystemConstant.getIntConstant("MAX_MAIN_CARDLINK");
	int MAX_SUP_CARDLINK = SystemConstant.getIntConstant("MAX_SUP_CARDLINK");
	int MAX_MAIN_INCREASE_CARDLINK = SystemConstant.getIntConstant("MAX_MAIN_INCREASE_CARDLINK");
	int MAX_SUP_INCREASE_CARDLINK = SystemConstant.getIntConstant("MAX_SUP_INCREASE_CARDLINK");
	int MAIN_CARDLINK_SEQ = SystemConstant.getIntConstant("MAIN_CARDLINK_SEQ");
	int SUP_CARDLINK_SEQ = SystemConstant.getIntConstant("SUP_CARDLINK_SEQ");
	String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String ORIG_ID_CC = SystemConstant.getConstant("ORIG_ID_CC");
	String ORIG_ID_KEC = SystemConstant.getConstant("ORIG_ID_KEC");
	String THAIBEV_CARD_TYPE = SystemConstant.getConstant("THAIBEV_CARD_TYPE");
	ArrayList<CardLinkRow> cardLinkRows = new ArrayList<CardLinkAction.CardLinkRow>();
	private void processCardLinkRow(CardLinkInfo cardLinkInfo,ApplicationGroupDataM applicationGroup,ApplicationDataM application){
		String productId = application.getProduct();
		String applicationRecordId = application.getApplicationRecordId();
		CardDataM cardM = application.getCard();
		String cardTypeId = cardM.getCardType();
		String applicationCardType = cardM.getApplicationType();
		String projectCode = application.getProjectCode();
		String finalAppDecision = application.getFinalAppDecision();
		logger.debug("productId : "+productId);
		logger.debug("applicationRecordId : "+applicationRecordId);
		logger.debug("cardTypeId : "+cardTypeId);
		logger.debug("applicationCardType : "+applicationCardType);
		logger.debug("projectCode : "+projectCode);
		logger.debug("finalAppDecision : "+finalAppDecision);
		CardLinkRow cardLinkRow = findBlankQueueCardLinkRow(applicationRecordId,applicationGroup);
		if(null == cardLinkRow){
			cardLinkRow = new CardLinkRow();
			String runningId = String.valueOf(cardLinkInfo.nextSeq());
			String uniqueRefId = cardLinkInfo.getCardLinkRefNo()+EjbUtil.appendZero(runningId,2);
			logger.debug("runningId : "+runningId);
			logger.debug("uniqueRefId : "+uniqueRefId);
			cardLinkRow.setCardLinkRefNo(uniqueRefId);
			cardLinkRow.setProductId(productId);
			cardLinkRow.setProjectCode(projectCode);
			cardLinkRow.setApplicationRecordId(applicationRecordId);
			cardLinkRow.setFinalAppDecision(finalAppDecision);
			cardLinkRow.setCardlinkRow(cardLinkRows.size()+1);
			if(PRODUCT_CRADIT_CARD.equals(productId)&&SystemConstant.lookup("PROCESS_CARDLINK_SEPARATE_CARD_TYPE",cardTypeId)){
				cardLinkRow.setCardTypeId(cardTypeId);
			}
			cardLinkRows.add(cardLinkRow);
		}
		logger.debug(cardLinkRow);
		CardLink cardLink = new CardLink();
			cardLink.setUniqueId(applicationRecordId);
			cardLink.setCardLinkType(applicationCardType);
			cardLink.setCardLinkSeq(getCardLinkSeq(applicationGroup,application,cardLinkRow));
		cardLinkRow.add(cardLink);
	}
	public int getCardLinkSeq(ApplicationGroupDataM applicationGroup,ApplicationDataM application,CardLinkRow cardLinkRow){
		String applicationType = applicationGroup.getApplicationType();
		String productId = application.getProduct();
		CardDataM cardM = application.getCard();
		String applicationCardType = cardM.getApplicationType();
		int cardLinkItem = cardLinkRow.getCardLinkType(applicationCardType);
		logger.debug("cardLinkItem : "+cardLinkItem);
		int cardLinkSeq = 1;
		if(SystemConstant.lookup("APPLICATION_TYPE_NORMAL",applicationType)&&PRODUCT_CRADIT_CARD.equals(productId)){
			if(cardLinkItem == 0){
				cardLinkSeq = (APPLICATION_CARD_TYPE_BORROWER.equals(applicationCardType))?MAIN_CARDLINK_SEQ:SUP_CARDLINK_SEQ;
			}else{
				if(APPLICATION_CARD_TYPE_BORROWER.equals(applicationCardType)){
					cardLinkSeq = cardLinkItem+1;
				}else{
					cardLinkSeq = SUP_CARDLINK_SEQ+cardLinkItem;
				}
			}
		}else{
			if(cardLinkItem == 0){
				cardLinkSeq = (APPLICATION_CARD_TYPE_BORROWER.equals(applicationCardType))?MAIN_CARDLINK_SEQ:SUP_CARDLINK_SEQ;
			}else{
				//cardLinkSeq = (APPLICATION_CARD_TYPE_BORROWER.equals(applicationCardType))?cardLinkItem+1:(SUP_CARDLINK_SEQ-1)+cardLinkItem;
				cardLinkSeq = (APPLICATION_CARD_TYPE_BORROWER.equals(applicationCardType))?cardLinkItem+1:(SUP_CARDLINK_SEQ)+cardLinkItem;
			}
		}
		return cardLinkSeq;
	}
	private void initProcessCardlink(ApplicationGroupDataM applicationGroup){
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		if(!Util.empty(applications)){
			for (ApplicationDataM application : applications) {
				if(!CardLinkRow.Flag.SUCCESS.equals(application.getCardlinkFlag())){
					application.setCardlinkFlag(CardLinkRow.Flag.NO_ACTION);
				}
				if(!CardLinkRow.Flag.SUCCESS.equals(application.getVlinkFlag())){
					application.setVlinkFlag(CardLinkRow.Flag.NO_ACTION);
				}
			}
		}
	}
	@SuppressWarnings("serial")
	public class CardLinkInfo implements Serializable,Cloneable{
		private String cardLinkRefNo;
		private int seq = 0;
		public String getCardLinkRefNo() {
			return cardLinkRefNo;
		}
		public void setCardLinkRefNo(String cardLinkRefNo) {
			this.cardLinkRefNo = cardLinkRefNo;
		}
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}	
		public int nextSeq(){
			seq++;
			return seq;
		}
	}
	private CardLinkInfo getCardLinkInfo(ApplicationGroupDataM applicationGroup){
		CardLinkInfo cardLink = new CardLinkInfo();
		cardLink.setCardLinkRefNo(getCardlinkRefNo(applicationGroup));
		if(!Util.empty(cardLink.getCardLinkRefNo())){
			cardLink.setSeq(getCardLinkSeq(applicationGroup));
		}
		return cardLink;
	}
	public int getCardLinkSeq(ApplicationGroupDataM applicationGroup){
		ArrayList<Integer> cardLinkSeqs = new ArrayList<Integer>();
		int lifeCycle = applicationGroup.getMaxLifeCycle();
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				String cardLinkRefNo = application.getCardlinkRefNo();
				if(!Util.empty(cardLinkRefNo) && !(lifeCycle == application.getLifeCycle())){
					String cardLinkSeq = cardLinkRefNo.substring(7,9);
					if(!Util.empty(cardLinkSeq)){
						cardLinkSeqs.add(Integer.valueOf(cardLinkSeq));
					}
				}
			}
		}
		return (!Util.empty(cardLinkSeqs))?Collections.max(cardLinkSeqs):0;
	}
	private String getCardlinkRefNo(ApplicationGroupDataM applicationGroup){
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				String cardLinkRefNo = application.getCardlinkRefNo();
				if(!Util.empty(cardLinkRefNo)){
					return cardLinkRefNo.substring(0,7);
				}
			}
		}
		return null;
	}
//	public static void main(String[] args) {
//		String refId = "123456789";
//		System.out.println(refId.substring(0,7));
//		System.out.println(refId.substring(7,9));
//	}
//	public void doAction(ApplicationGroupDataM applicationGroup,boolean isBatch){
//		doActionCardLink(applicationGroup,isBatch);
//	}
//	public void doAction(ApplicationGroupDataM applicationGroup){
//		doActionCardLink(applicationGroup,false);
//	}
	public void processCardlinkAction(ApplicationGroupDataM applicationGroup) throws Exception{
		logger.debug("processCardLink..");
		initProcessCardlink(applicationGroup);
		
		//Exit if not contains CC or KEC product
		ArrayList<String> productList = applicationGroup.getProducts();
		if(!productList.contains(PRODUCT_CRADIT_CARD) && !productList.contains(PRODUCT_K_EXPRESS_CASH))
		{
			return;
		}
		
		String applicationType = applicationGroup.getApplicationType();
		CardLinkInfo cardLinkInfo = getCardLinkInfo(applicationGroup);
		
		if(Util.empty(cardLinkInfo.getCardLinkRefNo())){
//			String cardlinkRefNo = ProcessApplicationManager.generateCardLinkRefNo(isBatch);
			String cardlinkRefNo = ProcessApplicationManager.cardLinkRefNo();
			logger.debug("cardlinkRefNo>>"+cardlinkRefNo);
			cardLinkInfo.setCardLinkRefNo(cardlinkRefNo);
			cardLinkInfo.setSeq(0);
		}
		logger.debug(cardLinkInfo);
		logger.debug("applicationType : "+applicationType);
		ArrayList<String> finalAppDecisions = SystemConstant.getArrayListConstant("PROCESS_CARDLINK_FINAL_APP_DECISION");
		for(String finalAppDecision : finalAppDecisions){
			logger.debug("finalAppDecision : "+finalAppDecision);			
			if(SystemConstant.lookup("APPLICATION_TYPE_NORMAL",applicationType)){
				ArrayList<String> products = applicationGroup.filterProductLifeCycleByFinalAppDecision(finalAppDecision);
				logger.debug("products : "+products);
				if(!Util.empty(products)){
					for(String productId : products){
						logger.debug("productId : "+productId);
						if(PRODUCT_CRADIT_CARD.equals(productId)){
							List<ApplicationDataM> borrowers = applicationGroup.filterApplicationProductLifeCyclesByFinalAppDecision(productId
									,APPLICATION_CARD_TYPE_BORROWER,finalAppDecision);
							if(!Util.empty(borrowers)){
								for(ApplicationDataM borrower : borrowers){
									if(SystemConstant.lookup("PROCESS_CARDLINK_PRODUCT",productId)){String uniqueId = borrower.getApplicationRecordId();
										processCardLinkRow(cardLinkInfo,applicationGroup,borrower);
										List<ApplicationDataM> supplementarys = applicationGroup
												.filterApplicationProductLifeCyclesByFinalAppDecision(productId,APPLICATION_CARD_TYPE_SUPPLEMENTARY
														,uniqueId,finalAppDecision);
										if(!Util.empty(supplementarys)){
											for (ApplicationDataM supplementary : supplementarys) {
												if(SystemConstant.lookup("PROCESS_CARDLINK_PRODUCT",productId)){
													processCardLinkRow(cardLinkInfo,applicationGroup,supplementary);
												}
											}
										}
									}
								}
							}
						}else{
							List<ApplicationDataM> applications = applicationGroup.filterApplicationProductLifeCyclesByFinalAppDecision(productId,finalAppDecision);
							if(!Util.empty(applications)){
								for (ApplicationDataM application : applications) {
									if(SystemConstant.lookup("PROCESS_CARDLINK_PRODUCT",productId)){
										processCardLinkRow(cardLinkInfo,applicationGroup,application);
									}
								}
							}
						}
					}
				}
			}else{
					List<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycleByFinalAppDecision(finalAppDecision);	
					if(!Util.empty(applications)){
						for(ApplicationDataM application : applications){		
							String productId = application.getProduct();
							if(SystemConstant.lookup("PROCESS_CARDLINK_PRODUCT",productId)){
								processCardLinkRow(cardLinkInfo,applicationGroup,application);
							}
						}
					}	
			}
		}
//		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);		
//		logger.debug("cardLinkRows.size : "+cardLinkRows.size());
		if(!Util.empty(cardLinkRows)){
			for(CardLinkRow cardLinkRow : cardLinkRows){
				logger.debug(cardLinkRow);
				String finalAppDecision = cardLinkRow.getFinalAppDecision();
				boolean isNewCardlinkCustFlag = CardLinkCustomerUtil.isNewCardLinkCustomer(applicationGroup, cardLinkRow.getApplicationRecordId());
				logger.debug("isNewCardlinkCustFlag >> "+isNewCardlinkCustFlag);
				if(DECISION_FINAL_DECISION_APPROVE.equals(cardLinkRow.getFinalAppDecision())){
					if(isNewCardlinkCustFlag){
						if(firstCardLinkRow(cardLinkRow,finalAppDecision)){
							cardLinkRow.setCardlinkFlag(CardLinkRow.Flag.GENERATE);
						}else{
							cardLinkRow.setCardlinkFlag(CardLinkRow.Flag.NEXT_DAY);
						}
					}else{
						cardLinkRow.setCardlinkFlag(CardLinkRow.Flag.GENERATE);
					}
				}else{
					cardLinkRow.setCardlinkFlag(CardLinkRow.Flag.NO_ACTION);
				}
				cardLinkRow.setVlinkFlag(getVlinkFlag(applicationGroup));				
				ArrayList<CardLink> cardLinks = cardLinkRow.getCardLinks();
				if(!Util.empty(cardLinks)){
					for(CardLink cardLink : cardLinks) {
						String uniqueId = cardLink.getUniqueId();
						ApplicationDataM application = applicationGroup.getApplicationById(uniqueId);
						if(null != application){
							application.setCardlinkRefNo(cardLinkRow.getCardLinkRefNo());
							application.setCardlinkSeq(cardLink.getCardLinkSeq());
							application.setCardlinkFlag(cardLinkRow.getCardlinkFlag());
							application.setVlinkFlag(cardLinkRow.getVlinkFlag());
						}
					}
				}
			}	
		}
	}
	public String getVlinkFlag(ApplicationGroupDataM applicationGroup){
		//String applyChannel = applicationGroup.getApplyChannel();
		String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");
		SaleInfoDataM saleInfo = applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
		if(null!=saleInfo){
		//PROD Defect: Change to get channel from sale 20170619
		String saleChannel = saleInfo.getSaleChannel();
		String applyType = applicationGroup.getApplicationType();
		logger.debug("saleChannel : "+saleChannel);
		if(SystemConstant.lookup("PROCESS_VLINK_APPLY_CHENNEL",saleChannel)&&SystemConstant.lookup("PROCESS_VLINK_APPLY_TYPE",applyType) 
				&& null != saleInfo && !Util.empty(saleInfo.getSalesId())){
			return CardLinkRow.Flag.GENERATE;
		}
		}
		return CardLinkRow.Flag.NO_ACTION;
	}
	private boolean firstCardLinkRow(CardLinkRow cardLinkRow,String finalAppDecision){
		String productId = cardLinkRow.getProductId();
		String cardTypeId = cardLinkRow.getCardTypeId();
		for(CardLinkRow _cardLinkRow : cardLinkRows){
			if(PRODUCT_CRADIT_CARD.equals(productId)&&SystemConstant.lookup("PROCESS_CARDLINK_FIRST_DAY_CARD_TYPE",cardTypeId)){
				if(null != productId && productId.equals(_cardLinkRow.getProductId())
						&& null != cardTypeId && cardTypeId.equals(_cardLinkRow.getCardTypeId())
							&& !Util.empty(_cardLinkRow.getCardlinkFlag())
								&& null != finalAppDecision && finalAppDecision.equals(cardLinkRow.getFinalAppDecision())){
					return false;
				}
			}else{
				if(null != productId && productId.equals(_cardLinkRow.getProductId())
						&& !Util.empty(_cardLinkRow.getCardlinkFlag())
							&& null != finalAppDecision && finalAppDecision.equals(cardLinkRow.getFinalAppDecision())){
					return false;
				}
			}
		}
		return true;
	}
	private CardLinkRow findBlankQueueCardLinkRow(String applicationRecordId,ApplicationGroupDataM applicationGroup){
		String applicationType = applicationGroup.getApplicationType();
		logger.debug("applicationType : "+applicationType);
		ApplicationDataM application = applicationGroup.getApplicationById(applicationRecordId);
		String productId = application.getProduct();
		CardDataM cardM = application.getCard();
		String cardTypeId = cardM.getCardType();	
		String projectCode = FormatUtil.displayText(application.getProjectCode());
		String finalAppDecision = FormatUtil.displayText(application.getFinalAppDecision()); 
		String cardLinkType = cardM.getApplicationType();
		logger.debug("cardTypeId : "+cardTypeId);
		logger.debug("projectCode : "+projectCode);
		logger.debug("finalAppDecision : "+finalAppDecision);
		logger.debug("cardLinkType : "+cardLinkType);
		if(!Util.empty(cardLinkRows)){
			for(CardLinkRow cardLinkRow : cardLinkRows){
				String _projectCode = FormatUtil.displayText(cardLinkRow.getProjectCode());
				String _finalAppDecision = FormatUtil.displayText(cardLinkRow.getFinalAppDecision()); 
				if(PRODUCT_CRADIT_CARD.equals(productId)&&SystemConstant.lookup("PROCESS_CARDLINK_SEPARATE_CARD_TYPE",cardTypeId)){
					if(null != productId && productId.equals(cardLinkRow.getProductId())
							&& null != cardTypeId && cardTypeId.equals(cardLinkRow.getCardTypeId())
								&& cardLinkRow.hasBlankQueue(applicationType,cardLinkType)
									&& projectCode.equals(_projectCode)
										&& finalAppDecision.equals(_finalAppDecision)){
							return cardLinkRow;
					}
				}else{
					if(null != productId && productId.equals(cardLinkRow.getProductId())
							&&cardLinkRow.hasBlankQueue(applicationType,cardLinkType)
								&& projectCode.equals(_projectCode)
									&& finalAppDecision.equals(_finalAppDecision)){
							return cardLinkRow;
					}
				}
			}
		}
		return null;
	}
	public String getOrgIdByProductId(String productId){
		String orgId = "";
		if(!Util.empty(productId)){
			if(productId.equals(PRODUCT_CRADIT_CARD)){
				orgId = ORIG_ID_CC;
			}else if(productId.equals(PRODUCT_K_EXPRESS_CASH)){
				orgId = ORIG_ID_KEC;
			}
		}
		return orgId;
	}
	@SuppressWarnings("serial")
	public class CardLinkRow implements Serializable,Cloneable{		
		public class Flag{
			public static final String SUCCESS = "S";
			public static final String NO_ACTION = "N";
			public static final String WAIT = "W";
			public static final String NEXT_DAY = "D";
			public static final String GENERATE = "Y";
		}
		private String cardLinkRefNo;
		private int cardlinkRow;
		private String productId;
		private String cardTypeId;		
		private String cardlinkFlag;
		private String vlinkFlag;
		private String projectCode;		
		private String finalAppDecision;		
		private ArrayList<CardLink> cardLinks = new ArrayList<CardLinkAction.CardLink>();
		private String applicationRecordId;
				
		public String getApplicationRecordId() {
			return applicationRecordId;
		}
		public void setApplicationRecordId(String applicationRecordId) {
			this.applicationRecordId = applicationRecordId;
		}
		public String getCardLinkRefNo() {
			return cardLinkRefNo;
		}
		public void setCardLinkRefNo(String cardLinkRefNo) {
			this.cardLinkRefNo = cardLinkRefNo;
		}
		public String getProjectCode() {
			return projectCode;
		}
		public void setProjectCode(String projectCode) {
			this.projectCode = projectCode;
		}
		public ArrayList<CardLink> getCardLinks() {
			return cardLinks;
		}
		public void setCardLinks(ArrayList<CardLink> cardLinks) {
			this.cardLinks = cardLinks;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getCardTypeId() {
			return cardTypeId;
		}
		public void setCardTypeId(String cardTypeId) {
			this.cardTypeId = cardTypeId;
		}	
		public void add(CardLink cardLink){
			cardLinks.add(cardLink);
		}	
		public int getCardLinkType(String cardLinkType){
			int cardLinkTypeValue = 0;			
			if(null != cardLinks){
				for(CardLink cardLink : cardLinks){
					if(null != cardLinkType && cardLinkType.equals(cardLink.getCardLinkType())){
						++cardLinkTypeValue;
					}
				}
			}
			return cardLinkTypeValue;
		}
		public boolean hasBlankQueue(String applicationType,String cardLinkType){
			boolean hasBlank = true;
			int item = getCardLinkType(cardLinkType);
			if(APPLICATION_TYPE_INCREASE.equals(applicationType)){
				if(item>MAX_MAIN_INCREASE_CARDLINK&&APPLICATION_CARD_TYPE_BORROWER.equals(cardLinkType)){
					hasBlank = false;
				}else if(item>MAX_SUP_INCREASE_CARDLINK&&APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(cardLinkType)){
					hasBlank = false;
				}
			}else{
				if(item>MAX_MAIN_CARDLINK&&APPLICATION_CARD_TYPE_BORROWER.equals(cardLinkType)){
					hasBlank = false;
				}else if(item>MAX_SUP_CARDLINK&&APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(cardLinkType)){
					hasBlank = false;
				}
			}
			return hasBlank;
		}
		public int getSeq(String uniqueId){
			if(null != cardLinks){
				for(CardLink cardLink : cardLinks){
					if(null != uniqueId && uniqueId.equals(cardLink.getUniqueId())){
						return cardLink.getCardLinkSeq();
					}
				}
			}
			return 1;
		}
		public String getFinalAppDecision() {
			return finalAppDecision;
		}
		public void setFinalAppDecision(String finalAppDecision) {
			this.finalAppDecision = finalAppDecision;
		}
		public String getCardlinkFlag() {
			return cardlinkFlag;
		}
		public void setCardlinkFlag(String cardlinkFlag) {
			this.cardlinkFlag = cardlinkFlag;
		}
		public String getVlinkFlag() {
			return vlinkFlag;
		}
		public void setVlinkFlag(String vlinkFlag) {
			this.vlinkFlag = vlinkFlag;
		}		
		public int getCardlinkRow() {
			return cardlinkRow;
		}
		public void setCardlinkRow(int cardlinkRow) {
			this.cardlinkRow = cardlinkRow;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("CardLinkRow [");
			if (cardLinkRefNo != null) {
				builder.append("cardLinkRefNo=");
				builder.append(cardLinkRefNo);
				builder.append(", ");
			}
			builder.append("cardlinkRow=");
			builder.append(cardlinkRow);
			builder.append(", ");
			if (productId != null) {
				builder.append("productId=");
				builder.append(productId);
				builder.append(", ");
			}
			if (cardTypeId != null) {
				builder.append("cardTypeId=");
				builder.append(cardTypeId);
				builder.append(", ");
			}
			if (cardlinkFlag != null) {
				builder.append("cardlinkFlag=");
				builder.append(cardlinkFlag);
				builder.append(", ");
			}
			if (vlinkFlag != null) {
				builder.append("vlinkFlag=");
				builder.append(vlinkFlag);
				builder.append(", ");
			}
			if (projectCode != null) {
				builder.append("projectCode=");
				builder.append(projectCode);
				builder.append(", ");
			}
			if (finalAppDecision != null) {
				builder.append("finalAppDecision=");
				builder.append(finalAppDecision);
				builder.append(", ");
			}
			if (applicationRecordId != null) {
				builder.append("applicationRecordId=");
				builder.append(applicationRecordId);
				builder.append(", ");
			}
			if (cardLinks != null) {
				builder.append("cardLinks=");
				builder.append(cardLinks);
			}
			builder.append("]");
			return builder.toString();
		}		
		
	}
	@SuppressWarnings("serial")
	public class CardLink implements Serializable,Cloneable{
		private String uniqueId;
		private int cardLinkSeq = -1;
		private String cardLinkType;
		public String getUniqueId() {
			return uniqueId;
		}
		public void setUniqueId(String uniqueId) {
			this.uniqueId = uniqueId;
		}
		public int getCardLinkSeq() {
			return cardLinkSeq;
		}
		public void setCardLinkSeq(int cardLinkSeq) {
			this.cardLinkSeq = cardLinkSeq;
		}
		public String getCardLinkType() {
			return cardLinkType;
		}
		public void setCardLinkType(String cardLinkType) {
			this.cardLinkType = cardLinkType;
		}		
	}
}
