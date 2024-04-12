package com.eaf.inf.batch.ulo.letter.reject.template.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterDAO;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterConfigDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFPersonalInfoDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateAppDecisionDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateReasonCodeDetailDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplicationSegment;
import com.eaf.inf.batch.ulo.notification.model.Reason;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;
import com.eaf.service.common.util.ServiceUtil;

public class GenerateLetterUtil {
	private static transient Logger logger = Logger.getLogger(GenerateLetterUtil.class);	
	private static String GEN_PARAM_CC_INFINITE = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_INFINITE");//0001
	private static String GEN_PARAM_CC_WISDOM = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_WISDOM");//0002
	private static String GEN_PARAM_CC_PREMIER = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_PREMIER");//0003
	private static String PERSONAL_TYPE_APPLICANT=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");	
	private static String PERSONAL_TYPE_SUPPLEMENTARY=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_SUPPLEMENTARY");	
	 public static String NOTIFICATION_FINAL_APP_DECISION_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_APPROVE");
	 public static String NOTIFICATION_FINAL_APP_DECISION_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_REJECT");
	 public static String POLICY_PROGRAM_SUPPLEMENTARY = InfBatchProperty.getInfBatchConfig("POLICY_PROGRAM_SUPPLEMENTARY");
	 private static String NOTIFICATION_APPLICATION_TYPE_SUP = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_TYPE_SUP");
	 private static String REJECT_LETTER_PRIORITY_FIRST=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_FIRST");
	 private static String REJECT_LETTER_PRIORITY_SECOND=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_SECOND");
	 private static String REJECT_LETTER_PRIORITY_THIRD=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_THIRD");
	 private static String DEFUALT_DATA_TYPE_ALL = InfBatchProperty.getInfBatchConfig("DEFUALT_DATA_TYPE_ALL");
	 private static String REJECT_LETTER_SEND_TIME = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_SEND_TIME");
	 private static String NEXTDAY_SEND_TIME = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME");
	 private static String PDF_NOTIFICATION_TYPE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_NOFIFICATION_TYPE");
	 private static String NOTIFICATION_SEND_TO_TYPE_SELLER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	 private static ArrayList<String> NEXTDAY_NOTIFICATION_TYPE = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_NOTIFICATION_TYPE_LIST");
	public class GENERATE_LETTER_CASE{
		public static final String WISDOM_INFINITE_PREMIER_REJECT_ALL="WISDOM_INFINITE_PREMIER_REJECT_ALL";
		public static final String ADD_SUP_REJECT_ALL = "ADD_SUP_REJECT_ALL";
		public static final String MAIN_APPROVE_SUP_REJECT = "MAIN_APPROVE_SUP_REJECT";
		public static final String MAIN_REJECT_SUP_REJECT = "MAIN_REJECT_SUP_REJECT";
 
	}
	public static ArrayList<TemplateReasonCodeDetailDataM>  getLetterConditionCase(RejectLetterDataM rejectLetterDataM,String product,boolean isInfiniteWisdomPremire, RejectLetterDAO dao) throws Exception{
		try {
			NotifyApplicationSegment notifyApplicationSegment  = NotificationUtil.notifyApplication(rejectLetterDataM.getApplicationGroupId());
			HashMap<String, ArrayList<TemplateReasonCodeDetailDataM>>  filterTemplateProduct = dao.selectTemplateResonInfo(rejectLetterDataM,notifyApplicationSegment);
			if(null!=filterTemplateProduct && !filterTemplateProduct.isEmpty()){
				ArrayList<TemplateReasonCodeDetailDataM> rejectLetterProcessConditionList = new ArrayList<TemplateReasonCodeDetailDataM>();
				
				ArrayList<String> keys = new ArrayList<String>(filterTemplateProduct.keySet());
				for(String keyName : keys){
					ArrayList<TemplateReasonCodeDetailDataM>  templateReasonCodes =  filterTemplateProduct.get(keyName);			
					// Case Infinit Wisdom premier
					if(null!=templateReasonCodes && templateReasonCodes.size()>0){
						if(isInfiniteWisdomPremire){
								boolean isRejectAll=isRejectAll(templateReasonCodes);
								boolean isAddSupApplication=isAddSupplementaryApplication(templateReasonCodes);
								boolean isMainApprove=isMainApprove(templateReasonCodes);
								boolean isSupReject=isSuplemenratyReject(templateReasonCodes);
								logger.debug("case Infinit Wisdom premier is Reject All:"+isRejectAll);
								logger.debug("case Infinit Wisdom premier is isAddSupApplication:"+isAddSupApplication);
								TemplateAppDecisionDataM templateAppDecision = new TemplateAppDecisionDataM();
									templateAppDecision.setAddSupApplication(isAddSupApplication);
									templateAppDecision.setMainApprove(isMainApprove);
									templateAppDecision.setRejectAll(isRejectAll);
									templateAppDecision.setSupReject(isSupReject);
								 if(isRejectAll && !isAddSupApplication){
									 for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
										 if(PERSONAL_TYPE_APPLICANT.equals(templatReasonCode.getPersonalType())){
											 addConditionList(rejectLetterProcessConditionList, templatReasonCode, templateAppDecision);
										 }
									 }
								 }else if(isRejectAll && isAddSupApplication){
									 for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
										 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(templatReasonCode.getPersonalType())){
											 addConditionList(rejectLetterProcessConditionList, templatReasonCode, templateAppDecision);
										 }
									 }
								 }else if(isMainApprove && isSupReject){
									 for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
										 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(templatReasonCode.getPersonalType())){
											 addConditionList(rejectLetterProcessConditionList, templatReasonCode, templateAppDecision);
										 }
									 }
								 }
							}else{
								//Case not  infinite wisdom premier check priority if still not send Email or sms will send Letter				
								//Reject All
								boolean isRejectAll=isRejectAll(templateReasonCodes);
								//MainApprove and SupReject
								boolean isMainApprove=isMainApprove(templateReasonCodes);
								boolean isSupReject=isSuplemenratyReject(templateReasonCodes);
								//Case Add sup and Reject All
								boolean isAddSupApplication=isAddSupplementaryApplication(templateReasonCodes);
								logger.debug("isRejectAll>>"+isRejectAll);
								logger.debug("isAddSupApplication>>"+isAddSupApplication);
								logger.debug("isMainApprove>>"+isMainApprove);
								TemplateAppDecisionDataM templateAppDecision = new TemplateAppDecisionDataM();
									templateAppDecision.setAddSupApplication(isAddSupApplication);
									templateAppDecision.setMainApprove(isMainApprove);
									templateAppDecision.setRejectAll(isRejectAll);
									templateAppDecision.setSupReject(isSupReject);
								if(isAddSupApplication && isRejectAll){
									for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
										 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(templatReasonCode.getPersonalType())){
											 addConditionList(rejectLetterProcessConditionList, templatReasonCode, templateAppDecision);
										 }
									 }
									
								}else{
									if(isMainApprove && isSupReject){							
										for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
											 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(templatReasonCode.getPersonalType())){
												 addConditionList(rejectLetterProcessConditionList, templatReasonCode, templateAppDecision);
											 }
										 }
									}else if(isRejectAll){ 
										for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
											 if(PERSONAL_TYPE_APPLICANT.equals(templatReasonCode.getPersonalType())){
												 addConditionList(rejectLetterProcessConditionList, templatReasonCode, templateAppDecision);
											 }
										 }
									}
								}											
							}
						}			
					}
				return rejectLetterProcessConditionList;
				}	
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new Exception(e);
		}
		return null;
	}
	private static void addConditionList(ArrayList<TemplateReasonCodeDetailDataM> rejectLetterProcessConditionList,TemplateReasonCodeDetailDataM templatReasonCode,TemplateAppDecisionDataM templateAppDecision){
//		Reason reason = notifyApplicationSegment.findReasonPerson(templatReasonCode.getPersonalId());
//		String REASON_CODE = reason.getReasonCode();
//		logger.debug("REASON_CODE : "+REASON_CODE);
//		logger.debug("templatReasonCode : "+templatReasonCode.getReasonCode());
//		if(!InfBatchUtil.empty(REASON_CODE) && REASON_CODE.equals(templatReasonCode.getReasonCode())){
//			templatReasonCode.setTemplateAppDecision(templateAppDecision);
//			rejectLetterProcessConditionList.add(templatReasonCode);
//		}
		logger.debug("ReasonCode : "+templatReasonCode.getReasonCode());
		if(!InfBatchUtil.empty(templatReasonCode.getReasonCode())){
			templatReasonCode.setTemplateAppDecision(templateAppDecision);
			rejectLetterProcessConditionList.add(templatReasonCode);
		}
	}
	public static boolean isInfiniteWisdomPremierApplication(String templateId){
		if(!ServiceUtil.empty(templateId)){
			if(InfBatchProperty.getGeneralParam(GEN_PARAM_CC_INFINITE).contains(templateId) ||
					InfBatchProperty.getGeneralParam(GEN_PARAM_CC_WISDOM).contains(templateId) ||
					InfBatchProperty.getGeneralParam(GEN_PARAM_CC_PREMIER).contains(templateId)){
					return true;
			}
		}
		return false;
	}
	@Deprecated
	public static String getRejectLetterTypeByCondition( boolean isInfiniteWisdomPremierApplication,RejectLetterDataM rejectLetterDataM,RejectLetterConfigDataM config,TemplateReasonCodeDetailDataM templateReasonCodeDetailDataM, RejectLetterDAO dao){
		try {			 
//			 int notiSendingTime =0;
			 boolean isGenPaper =false;
			 boolean isGenEmail =false;
			 HashMap<String,String> priorityMap = config.getPriorityMap();
			 logger.debug("isInfiniteWisdomPremierApplication>>"+isInfiniteWisdomPremierApplication);
			 logger.debug("isInfiniteWisdomPremierApplication>>"+templateReasonCodeDetailDataM.getReasonCode());
		 	String applicationTemplate = rejectLetterDataM.getApplicationTemplateId();
		 	String reasonCode = rejectLetterDataM.getReasonCode();
			String cashTransferType = !InfBatchUtil.empty(rejectLetterDataM.getCashTransferType()) ? rejectLetterDataM.getCashTransferType() : DEFUALT_DATA_TYPE_ALL ;
			 if(isInfiniteWisdomPremierApplication && !rejectLetterDataM.getNotificationtype().equals(TemplateBuilderConstant.TemplateType.MAKEPDF_CH)){
				 return RejectLetterUtil.PAPER;
			 }
//			 else if(!isInfiniteWisdomPremierApplication && rejectLetterDataM.getNotificationtype().equals(TemplateBuilderConstant.TemplateType.MAKEPDF_CH)){
//				 String EMAIL_PRIORITY = rejectLetterDataM.getEmailPriority();
//				 if(REJECT_LETTER_PRIORITY_FIRST.equals(EMAIL_PRIORITY) ||
//						 REJECT_LETTER_PRIORITY_SECOND.equals(EMAIL_PRIORITY) ||
//						 REJECT_LETTER_PRIORITY_THIRD.equals(EMAIL_PRIORITY)){
//					 isGenEmail = true;
//				 }
//			 }
			 else{
				 ArrayList<String> priorityNextDayList = new ArrayList<String>(); 
				 for(String type : NEXTDAY_NOTIFICATION_TYPE){
					 String key = generateKeyPriorityRejectLetter(type, NEXTDAY_SEND_TIME, applicationTemplate, reasonCode, cashTransferType);
					 String priority = priorityMap.get(key);
					 if(!InfBatchUtil.empty(priority) && !priorityNextDayList.contains(priority)){
						 priorityNextDayList.add(priority);
					 }
				 }
				 String keyMakePDF = generateKeyPriorityRejectLetter(PDF_NOTIFICATION_TYPE, REJECT_LETTER_SEND_TIME, applicationTemplate, reasonCode, cashTransferType);
				 String priorityMakePDF = priorityMap.get(keyMakePDF);
				 String personalType = templateReasonCodeDetailDataM.getPersonalType();
				 if(PERSONAL_TYPE_APPLICANT.equals(personalType)){
					 String EMAIL_PRIORITY = rejectLetterDataM.getEmailPriority();
					 String PAPER_PRIORITY =rejectLetterDataM.getPaperPriority();
					 if(REJECT_LETTER_PRIORITY_FIRST.equals(EMAIL_PRIORITY)){
						 isGenEmail = true;
					 }else if(REJECT_LETTER_PRIORITY_FIRST.equals(PAPER_PRIORITY)){
						 isGenPaper = true;
					 }
					 
					 if(REJECT_LETTER_PRIORITY_SECOND.equals(EMAIL_PRIORITY)){
						 //notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.EMAIL);
//						 notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.MAKEPDF);
//						 if(0==notiSendingTime){
//							 isGenEmail = true;
//						 }
						 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())){
							 isGenEmail = false;	// send Nextday
						 }else{
							 isGenEmail = true;
						 }
					 } 
					 
					 if(REJECT_LETTER_PRIORITY_SECOND.equals(PAPER_PRIORITY) && !isGenEmail){
//						 notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.LETTER);
//						 if(0==notiSendingTime){
//							 isGenPaper = true;
//						 }
						 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())){
							 isGenPaper = false; // send Nextday
						 }else if(!InfBatchUtil.empty(priorityMakePDF) && REJECT_LETTER_PRIORITY_FIRST.equals(priorityMakePDF) && !InfBatchUtil.empty(rejectLetterDataM.getEmailPrimary())){
							 isGenPaper = false; // send email (makePDF)
						 }else{
							 isGenPaper = true;
						 }
					 }
					 
					 if(!isGenEmail && !isGenPaper){
						 if(REJECT_LETTER_PRIORITY_THIRD.equals(EMAIL_PRIORITY)){
							 //notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.EMAIL);
//							 notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.MAKEPDF);
//							 if(0==notiSendingTime){
//								 isGenEmail = true;
//							 }
							 if(!InfBatchUtil.empty(priorityNextDayList)  && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())
									 && (priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) || priorityNextDayList.contains(REJECT_LETTER_PRIORITY_SECOND))){
								 isGenEmail = false; // send email (makePDF)
							 }else{
								 isGenEmail = true;
							 }
						 } 
						 
						 
						 if(REJECT_LETTER_PRIORITY_THIRD.equals(PAPER_PRIORITY) && !isGenEmail){
//							 notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.LETTER);
//							 if(0==notiSendingTime){
//								 isGenPaper = true;
//							 }
							 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())){
								 isGenPaper = false; // send Nextday
							 }else if(!InfBatchUtil.empty(priorityMakePDF) && REJECT_LETTER_PRIORITY_SECOND.equals(priorityMakePDF) && !InfBatchUtil.empty(rejectLetterDataM.getEmailPrimary())){
								 isGenPaper = false; // send email (makePDF)
							 }else{
								 isGenPaper = true;
							 }
						 }
					 } 
				 }else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
					 String SUB_EMAIL_PRIORITY = rejectLetterDataM.getSubEmailPriority();
					 String SUB_PAPER_PRIORITY =rejectLetterDataM.getSubPaperPriority();
					 
					 if(REJECT_LETTER_PRIORITY_FIRST.equals(SUB_EMAIL_PRIORITY)){
						 isGenEmail = true;
					 }else if(REJECT_LETTER_PRIORITY_FIRST.equals(SUB_PAPER_PRIORITY)){
						 isGenPaper = true;
					 }
					 
					 if(REJECT_LETTER_PRIORITY_SECOND.equals(SUB_EMAIL_PRIORITY)){
						 //notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.EMAIL);
//						 notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.MAKEPDF);
//						 if(0==notiSendingTime){
//							 isGenEmail = true;
//						 }
						 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())){
							 isGenEmail = false;	// send Nextday
						 }else{
							 isGenEmail = true;
						 }
					 } 
					 
					 if(REJECT_LETTER_PRIORITY_SECOND.equals(SUB_PAPER_PRIORITY) && !isGenEmail){
//						 notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.LETTER);
//						 if(0==notiSendingTime){
//							 isGenPaper = true;
//						 }
						 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())){
							 isGenPaper = false; // send Nextday
						 }else if(!InfBatchUtil.empty(priorityMakePDF) && REJECT_LETTER_PRIORITY_FIRST.equals(priorityMakePDF) && !InfBatchUtil.empty(rejectLetterDataM.getEmailPrimary())){
							 isGenPaper = false; // send email (makePDF)
						 }else{
							 isGenPaper = true;
						 }
					 }
					 
					 if(!isGenEmail && !isGenPaper){
						 if(REJECT_LETTER_PRIORITY_THIRD.equals(SUB_EMAIL_PRIORITY)){
							 //notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.EMAIL);
//							 notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.MAKEPDF);
//							 if(0==notiSendingTime){
//								 isGenEmail = true;
//							 }
							 if(!InfBatchUtil.empty(priorityNextDayList)  && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())
									 && (priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) || priorityNextDayList.contains(REJECT_LETTER_PRIORITY_SECOND))){
								 isGenEmail = false; // send email (makePDF)
							 }else{
								 isGenEmail = true;
							 }
						 } 
						 
						 
						 if(REJECT_LETTER_PRIORITY_THIRD.equals(SUB_PAPER_PRIORITY) && !isGenEmail){
//							 notiSendingTime =dao.getSenddingTimeOfCustomer(rejectLetterDataM.getApplicationGroupId(),TemplateBuilderConstant.TemplateType.LETTER);
//							 if(0==notiSendingTime){
//								 isGenPaper = true;
//							 }
							 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())){
								 isGenPaper = false; // send Nextday
							 }else if(!InfBatchUtil.empty(priorityMakePDF) && REJECT_LETTER_PRIORITY_SECOND.equals(priorityMakePDF) && !InfBatchUtil.empty(rejectLetterDataM.getEmailPrimary())){
								 isGenPaper = false; // send email (makePDF)
							 }else{
								 isGenPaper = true;
							 }
						 }
						 
					 }
					 					 
				 }
				 
				 logger.debug("isGenEmail>>"+isGenEmail);
				 logger.debug("isGenPaper>>"+isGenPaper);
				 if(isGenEmail){
					 //return RejectLetterUtil.EMAIL;
					 return RejectLetterUtil.EMAIL;
				 }else if(isGenPaper){
					 return RejectLetterUtil.PAPER;
				 }else if(!isGenEmail && !isGenPaper && isSendSellerNoCust(rejectLetterDataM, config)){
					 return RejectLetterUtil.EMAIL_TO_SELLER;
				 }
			 }
			 			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return "";
	}
		
	public static boolean isApprovedAll(ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes) {// approve all card
		 int count=0;
		 if(null!=templateReasonCodes && templateReasonCodes.size()>0){
			 for(TemplateReasonCodeDetailDataM templateReasonCode : templateReasonCodes){
				 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(templateReasonCode.getFinalDecision())){
					 count++;
				 }
			 }
			 if(count==templateReasonCodes.size()){
				 return true;
			 }
		 }
		return false;
	 }
	 public static boolean isRejectAll(ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes) { //reject all card
		 int count=0;
		 if(null!=templateReasonCodes && templateReasonCodes.size()>0){
			 for(TemplateReasonCodeDetailDataM templateReasonCode : templateReasonCodes){
				 if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(templateReasonCode.getFinalDecision())){
					 count++;
				 }
			 }
			 if(count==templateReasonCodes.size()){
				 logger.debug("isReject ALL>>");
				 return true;
			 }
		 }
		return false;
	 }
	 
	 public static boolean isMainApprove(ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes) {// main approve 
		 if(null!=templateReasonCodes && templateReasonCodes.size()>0){
			 for(TemplateReasonCodeDetailDataM templateReasonCode : templateReasonCodes){
				 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(templateReasonCode.getFinalDecision()) && PERSONAL_TYPE_APPLICANT.equals(templateReasonCode.getPersonalType()) ){
					return true;
				 }
			 }
		 }
		return false;
	 } 
	 
	 public static boolean isSuplemenratyReject(ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes) {// sub reject
		 if(null!=templateReasonCodes && templateReasonCodes.size()>0){
			 for(TemplateReasonCodeDetailDataM templateReasonCode : templateReasonCodes){
				 if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(templateReasonCode.getFinalDecision()) && PERSONAL_TYPE_SUPPLEMENTARY.equals(templateReasonCode.getPersonalType()) ){
					return true;
				 }
			 }
		 }
		return false;
	 } 
	 
	 public static boolean isOnlySuplemenratyApprove(ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes) {// supplementary approve approve only
		 boolean isAllMainReject =true;
		 boolean isAllSupApprove =true;
		 if(null!=templateReasonCodes && templateReasonCodes.size()>0){
			 for(TemplateReasonCodeDetailDataM templateReasonCode : templateReasonCodes){
				 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(templateReasonCode.getFinalDecision()) && PERSONAL_TYPE_APPLICANT.equals(templateReasonCode.getPersonalType()) ){
					 isAllMainReject= false;
				 }else if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(templateReasonCode.getFinalDecision()) && NOTIFICATION_APPLICATION_TYPE_SUP.equals(templateReasonCode.getPersonalType())){
					 isAllSupApprove =false;
				 }
			 }
			 if(isAllMainReject && isAllSupApprove){
				 logger.debug(" All Sup Approve and Main ALl Main Reject");
				 return true;
			 }
		 }
		return false;
	 } 
	 
	 public static boolean isAddSupplementaryApplication(ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes){
		 if(null!=templateReasonCodes && templateReasonCodes.size()>0){
			 for(TemplateReasonCodeDetailDataM templateReasonCode : templateReasonCodes){
				 if(PERSONAL_TYPE_APPLICANT.equals(templateReasonCode.getPersonalType())){
					 logger.debug("==is contain main applicant==");
					return false;
				 }
			 }
		 }else{
			 return false;
		 }
		 return true;
	 }
	 
	 public static boolean isPDFApprovedAll(ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos ) {// approve all card
		 int count=0;
		 if(null!=pdfPersonalInfos && pdfPersonalInfos.size()>0){
			 for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
				 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(pdfPersonalInfo.getFinalDecision())){
					 count++;
				 }
			 }
			 if(count==pdfPersonalInfos.size()){
				 return true;
			 }
		 }
		return false;
	 }
	 public static boolean isPDFRejectAll(ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos ) { //reject all card
		 int count=0;
		 if(null!=pdfPersonalInfos && pdfPersonalInfos.size()>0){
			 for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
				 if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(pdfPersonalInfo.getFinalDecision())){
					 count++;
				 }
			 }
			 if(count==pdfPersonalInfos.size()){
				 logger.debug("isReject ALL>>");
				 return true;
			 }
		 }
		return false;
	 }
	 
	 public static boolean isPDFMainApprove(ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos ) {// main approve 
		 if(null!=pdfPersonalInfos && pdfPersonalInfos.size()>0){
			 for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
				 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(pdfPersonalInfo.getFinalDecision()) && PERSONAL_TYPE_APPLICANT.equals(pdfPersonalInfo.getPersonalType()) ){
					return true;
				 }
			 }
		 }
		return false;
	 } 
	 
	 public static boolean isPDFSuplemenratyReject(ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos ) {// sub reject
		 if(null!=pdfPersonalInfos && pdfPersonalInfos.size()>0){
			 for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
				 if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(pdfPersonalInfo.getFinalDecision()) && PERSONAL_TYPE_SUPPLEMENTARY.equals(pdfPersonalInfo.getPersonalType()) ){
					return true;
				 }
			 }
		 }
		return false;
	 } 
	 
	 public static boolean isPDFOnlySuplemenratyApprove(ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos ) {// supplementary approve approve only
		 boolean isAllMainReject =true;
		 boolean isAllSupApprove =true;
		 if(null!=pdfPersonalInfos && pdfPersonalInfos.size()>0){
			 for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
				 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(pdfPersonalInfo.getFinalDecision()) && PERSONAL_TYPE_APPLICANT.equals(pdfPersonalInfo.getPersonalType()) ){
					 isAllMainReject= false;
				 }else if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(pdfPersonalInfo.getFinalDecision()) && NOTIFICATION_APPLICATION_TYPE_SUP.equals(pdfPersonalInfo.getPersonalType())){
					 isAllSupApprove =false;
				 }
			 }
			 if(isAllMainReject && isAllSupApprove){
				 logger.debug(" All Sup Approve and Main ALl Main Reject");
				 return true;
			 }
		 }
		return false;
	 } 
	 
	 public static boolean isPDFAddSupplementaryApplication(ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos){
		 if(null!=pdfPersonalInfos && pdfPersonalInfos.size()>0){
			 for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
				 if(PERSONAL_TYPE_APPLICANT.equals(pdfPersonalInfo.getPersonalType())){
					 logger.debug("==is contain main applicant==");
					return false;
				 }
			 }
		 }else{
			 return false;
		 }
		 return true;
	 }
	 
	 public static RejectLetterPDFPersonalInfoDataM  getPDFLetterConditionCase(ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos,String templateId){
		 if(!InfBatchUtil.empty(pdfPersonalInfos)){	
					// Case Infinit Wisdom premier
						if(InfBatchProperty.getGeneralParam(GEN_PARAM_CC_INFINITE).contains(templateId) ||
								InfBatchProperty.getGeneralParam(GEN_PARAM_CC_WISDOM).contains(templateId) ||
								InfBatchProperty.getGeneralParam(GEN_PARAM_CC_PREMIER).contains(templateId)){
								boolean isRejectAll=isPDFRejectAll(pdfPersonalInfos);
								boolean isAddSupApplication=isPDFAddSupplementaryApplication(pdfPersonalInfos);
								 logger.debug("case Infinit Wisdom premier is Reject All:"+isRejectAll);
								 logger.debug("case Infinit Wisdom premier is isAddSupApplication:"+isAddSupApplication);
								 if(isRejectAll && !isAddSupApplication){
									 for(RejectLetterPDFPersonalInfoDataM rejectLetterPDFPersonalInfoDataM : pdfPersonalInfos){
										 if(PERSONAL_TYPE_APPLICANT.equals(rejectLetterPDFPersonalInfoDataM.getPersonalType())){
											 return  rejectLetterPDFPersonalInfoDataM;
										 }
									 }
								 }
								 
							}else{
								//Case not  infinite wisdom premier check priority if still not send Email or sms will send Letter				
									//Reject All
									boolean isRejectAll=isPDFRejectAll(pdfPersonalInfos);
									
									//MainApprove and SupReject
									boolean isMainApprove=isPDFMainApprove(pdfPersonalInfos);
									boolean isSupReject=isPDFSuplemenratyReject(pdfPersonalInfos);
									
									
									//Case Add sup and Reject All
									boolean isAddSupApplication=isPDFAddSupplementaryApplication(pdfPersonalInfos);
									
									 
									logger.debug("isRejectAll>>"+isRejectAll);
									logger.debug("isAddSupApplication>>"+isAddSupApplication);
									logger.debug("isMainApprove>>"+isMainApprove);

									if(isAddSupApplication && isRejectAll){
										for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
											 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(pdfPersonalInfo.getPersonalType())){
												 return pdfPersonalInfo;
											 }
										 }
										
									}else{
										if(isMainApprove && isSupReject){							
											for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
												 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(pdfPersonalInfo.getPersonalType())){
													  return pdfPersonalInfo;
												 }
											 }
										}else if(isRejectAll){ 
											for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
												 if(PERSONAL_TYPE_APPLICANT.equals(pdfPersonalInfo.getPersonalType())){
													 return pdfPersonalInfo;
												 }
											 }
										}
									}										
							}		
				}	
				return null;
			}
	 public static RejectLetterPDFPersonalInfoDataM getPDFPersonalInfoByType(ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos,String personalType){
		 if(!InfBatchUtil.empty(pdfPersonalInfos)){
			 for(RejectLetterPDFPersonalInfoDataM pdfPersonalInfo : pdfPersonalInfos){
				 if(!InfBatchUtil.empty(pdfPersonalInfos)){
					 if(pdfPersonalInfo.getPersonalType().equals(personalType)){
						 return pdfPersonalInfo;
					 }
				 }
			 }
		 }
		 return null;
	 }
	 public static String gerProductType(String businessClassId){
		 String productType = "";
		 if(!InfBatchUtil.empty(businessClassId)){
			 String arrSplited[] =  businessClassId.split("\\_");
			 if(!InfBatchUtil.empty(arrSplited)){
				 productType = arrSplited[0];
				 return Formatter.display(productType);
			 }
		}
		 return productType;
	 }
	 public static String generateKeyPriorityRejectLetter(String notificationType,String sendTime,String applicationTemplate,String reasonCode,String cashTransferType){
		 return notificationType+"_"+sendTime+"_"+applicationTemplate+"_"+reasonCode+"_"+cashTransferType;
	 }
	 public static String generateKeyMakePdfSeller(String notificationType,String sendTime,String applicationTemplate,String reasonCode){
		 return notificationType+"_"+sendTime+"_"+applicationTemplate+"_"+reasonCode;
	 }
	 private static boolean isSendSellerNoCust(RejectLetterDataM rejectLetter, RejectLetterConfigDataM config){
		 HashMap<String, String>  sellerMap = config.getSellerMap();
			 String applicationtemplateId = rejectLetter.getApplicationTemplateId();
			 String reasonCode = rejectLetter.getReasonCode();
		 String sellerKey = generateKeyMakePdfSeller(TemplateBuilderConstant.TemplateType.MAKEPDF, REJECT_LETTER_SEND_TIME, applicationtemplateId, reasonCode);
		 logger.debug("sellerMap : "+sellerMap);
		 logger.debug("sellerKey : "+sellerKey);
		 if(NOTIFICATION_SEND_TO_TYPE_SELLER.equals(rejectLetter.getSendTo())){
			 return true;
		 }else if(!NOTIFICATION_SEND_TO_TYPE_SELLER.equals(rejectLetter.getSendTo()) && sellerMap.containsKey(sellerKey)){
			 return true;
		 }
		 return false;
	 }
	 
	 public static ArrayList<TemplateReasonCodeDetailDataM>  getLetterConditionCaseBundle(RejectLetterDataM rejectLetterDataM,String product,boolean isInfiniteWisdomPremire, RejectLetterDAO dao) throws Exception{
			try {
				NotifyApplicationSegment notifyApplicationSegment  = NotificationUtil.notifyApplication(rejectLetterDataM.getApplicationGroupId());
				HashMap<String, ArrayList<TemplateReasonCodeDetailDataM>>  filterTemplateProduct = dao.selectTemplateResonInfoByProduct(rejectLetterDataM, product);
				if(null!=filterTemplateProduct && !filterTemplateProduct.isEmpty()){
					ArrayList<TemplateReasonCodeDetailDataM> rejectLetterProcessConditionList = new ArrayList<TemplateReasonCodeDetailDataM>();
					
					ArrayList<String> keys = new ArrayList<String>(filterTemplateProduct.keySet());
					for(String keyName : keys){
						ArrayList<TemplateReasonCodeDetailDataM>  templateReasonCodes =  filterTemplateProduct.get(keyName);			
						// Case Infinit Wisdom premier
						if(null!=templateReasonCodes && templateReasonCodes.size()>0){
							if(isInfiniteWisdomPremire){
									boolean isRejectAll=isRejectAll(templateReasonCodes);
									boolean isAddSupApplication=isAddSupplementaryApplication(templateReasonCodes);
									 logger.debug("case Infinit Wisdom premier is Reject All:"+isRejectAll);
									 logger.debug("case Infinit Wisdom premier is isAddSupApplication:"+isAddSupApplication);
									 if(isRejectAll && !isAddSupApplication){
										 for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
											 if(PERSONAL_TYPE_APPLICANT.equals(templatReasonCode.getPersonalType())){
												 //rejectLetterProcessConditionList.add(templatReasonCode);
												 addConditionList(rejectLetterProcessConditionList, templatReasonCode, notifyApplicationSegment, product);
											 }
										 }
									 }else if(isRejectAll && isAddSupApplication){
										 for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
											 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(templatReasonCode.getPersonalType())){
												 //rejectLetterProcessConditionList.add(templatReasonCode);
												 addConditionList(rejectLetterProcessConditionList, templatReasonCode, notifyApplicationSegment, product);
											 }
										 }
									 }
								}else{
									//Case not  infinite wisdom premier check priority if still not send Email or sms will send Letter				
									//Reject All
									boolean isRejectAll=isRejectAll(templateReasonCodes);
									
									//MainApprove and SupReject
									boolean isMainApprove=isMainApprove(templateReasonCodes);
									boolean isSupReject=isSuplemenratyReject(templateReasonCodes);
									
									
									//Case Add sup and Reject All
									boolean isAddSupApplication=isAddSupplementaryApplication(templateReasonCodes);
									
									 
									logger.debug("isRejectAll>>"+isRejectAll);
									logger.debug("isAddSupApplication>>"+isAddSupApplication);
									logger.debug("isMainApprove>>"+isMainApprove);

									if(isAddSupApplication && isRejectAll){
										for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
											 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(templatReasonCode.getPersonalType())){
												 //rejectLetterProcessConditionList.add(templatReasonCode);
												 addConditionList(rejectLetterProcessConditionList, templatReasonCode, notifyApplicationSegment, product);
											 }
										 }
										
									}else{
										if(isMainApprove && isSupReject){							
											for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
												 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(templatReasonCode.getPersonalType())){
													 //rejectLetterProcessConditionList.add(templatReasonCode);
													 addConditionList(rejectLetterProcessConditionList, templatReasonCode, notifyApplicationSegment, product);
												 }
											 }
										}else if(isRejectAll){ 
											for(TemplateReasonCodeDetailDataM templatReasonCode : templateReasonCodes){
												 if(PERSONAL_TYPE_APPLICANT.equals(templatReasonCode.getPersonalType())){
													 //rejectLetterProcessConditionList.add(templatReasonCode);
													 addConditionList(rejectLetterProcessConditionList, templatReasonCode, notifyApplicationSegment, product);
												 }
											 }
										}
									}											
								}
							}			
						}
					return rejectLetterProcessConditionList;
					}	
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new Exception(e);
			}
			return null;
		}
	 private static void addConditionList(ArrayList<TemplateReasonCodeDetailDataM> rejectLetterProcessConditionList, TemplateReasonCodeDetailDataM templatReasonCode, NotifyApplicationSegment notifyApplicationSegment, String product){
			Reason reason = notifyApplicationSegment.findReasonPersonAndProduct(templatReasonCode.getPersonalId(),product);
			String REASON_CODE = reason.getReasonCode();
			logger.debug("REASON_CODE : "+REASON_CODE);
			logger.debug("templatReasonCode : "+templatReasonCode.getReasonCode());
			if(!InfBatchUtil.empty(REASON_CODE) && REASON_CODE.equals(templatReasonCode.getReasonCode())){
				rejectLetterProcessConditionList.add(templatReasonCode);
			}
		}
}
