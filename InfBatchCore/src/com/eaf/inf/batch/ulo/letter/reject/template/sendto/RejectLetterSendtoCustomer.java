package com.eaf.inf.batch.ulo.letter.reject.template.sendto;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterConfigDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionResponseDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateReasonCodeDetailDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;

public class RejectLetterSendtoCustomer extends RejectLetterSendingHelper {
	private static transient Logger logger = Logger.getLogger(RejectLetterSendtoCustomer.class);	
	private static String PERSONAL_TYPE_APPLICANT=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");	
	private static String PERSONAL_TYPE_SUPPLEMENTARY=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_SUPPLEMENTARY");	
	 public static String NOTIFICATION_FINAL_APP_DECISION_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_APPROVE");
	 public static String NOTIFICATION_FINAL_APP_DECISION_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_REJECT");
	 public static String POLICY_PROGRAM_SUPPLEMENTARY = InfBatchProperty.getInfBatchConfig("POLICY_PROGRAM_SUPPLEMENTARY");
	 private static String REJECT_LETTER_PRIORITY_FIRST=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_FIRST");
	 private static String REJECT_LETTER_PRIORITY_SECOND=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_SECOND");
	 private static String REJECT_LETTER_PRIORITY_THIRD=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_THIRD");
	 private static String DEFUALT_DATA_TYPE_ALL = InfBatchProperty.getInfBatchConfig("DEFUALT_DATA_TYPE_ALL");
	 private static String REJECT_LETTER_SEND_TIME = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_SEND_TIME");
	 private static String NEXTDAY_SEND_TIME = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME");
	 private static String PDF_NOTIFICATION_TYPE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_NOFIFICATION_TYPE");
	 private static ArrayList<String> NEXTDAY_NOTIFICATION_TYPE = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_NOTIFICATION_TYPE_LIST");
	 @Override
	public Object processSendingCondition(SendingConditionRequestDataM sendingConditionRequest)throws Exception{
		 SendingConditionResponseDataM response = new SendingConditionResponseDataM();
		 String letterType = "";
		 boolean isGenPaper =false;
		 boolean isGenEmail =false;
		 RejectLetterDataM rejectLetterDataM = sendingConditionRequest.getRejectLetterDataM();
		 RejectLetterConfigDataM config = sendingConditionRequest.getConfig();
		 TemplateReasonCodeDetailDataM templateReasonCodeDetailDataM = sendingConditionRequest.getTemplateReasonCodeDetailDataM();
		 HashMap<String,String> priorityMap = config.getPriorityMap();
		 logger.debug("isInfiniteWisdomPremierApplication>>"+sendingConditionRequest.isInfiniteWisdomPremierApplication());
		 logger.debug("isInfiniteWisdomPremierApplication>>"+templateReasonCodeDetailDataM.getReasonCode());
	 	String applicationTemplate = rejectLetterDataM.getApplicationTemplateId();
	 	String reasonCode = rejectLetterDataM.getReasonCode();
		String cashTransferType = !InfBatchUtil.empty(rejectLetterDataM.getCashTransferType()) ? rejectLetterDataM.getCashTransferType() : DEFUALT_DATA_TYPE_ALL ;
		 if(sendingConditionRequest.isInfiniteWisdomPremierApplication()){
			 letterType = RejectLetterUtil.PAPER;
		 }else{
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
					 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())){
						 isGenEmail = false;	// send Nextday
					 }else{
						 isGenEmail = true;
					 }
				 } 
				 
				 if(REJECT_LETTER_PRIORITY_SECOND.equals(PAPER_PRIORITY) && !isGenEmail){
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
						 if(!InfBatchUtil.empty(priorityNextDayList)  && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())
								 && (priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) || priorityNextDayList.contains(REJECT_LETTER_PRIORITY_SECOND))){
							 isGenEmail = false; // send email (makePDF)
						 }else{
							 isGenEmail = true;
						 }
					 } 
					 
					 
					 if(REJECT_LETTER_PRIORITY_THIRD.equals(PAPER_PRIORITY) && !isGenEmail){
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
					 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())){
						 isGenEmail = false;	// send Nextday
					 }else{
						 isGenEmail = true;
					 }
				 } 
				 
				 if(REJECT_LETTER_PRIORITY_SECOND.equals(SUB_PAPER_PRIORITY) && !isGenEmail){
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
						 if(!InfBatchUtil.empty(priorityNextDayList)  && !InfBatchUtil.empty(rejectLetterDataM.getMobileNo())
								 && (priorityNextDayList.contains(REJECT_LETTER_PRIORITY_FIRST) || priorityNextDayList.contains(REJECT_LETTER_PRIORITY_SECOND))){
							 isGenEmail = false; // send email (makePDF)
						 }else{
							 isGenEmail = true;
						 }
					 } 
					 
					 
					 if(REJECT_LETTER_PRIORITY_THIRD.equals(SUB_PAPER_PRIORITY) && !isGenEmail){
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
				 letterType =  RejectLetterUtil.EMAIL;
			 }else if(isGenPaper){
				 letterType =  RejectLetterUtil.PAPER;
			 }
		 }
		 logger.debug("letterType : "+letterType);
		 response.setLetterType(letterType);
		 response.setGenEmail(isGenEmail);
		 response.setGenPaper(isGenPaper);
		 return response;
	}
	 @Override
	public Object postCondition(SendingConditionRequestDataM request, Object object)throws Exception{
		 RejectLetterConfigDataM config = request.getConfig();
		 RejectLetterDataM rejectLetter = request.getRejectLetterDataM();
		 HashMap<String, String>  sellerMap = config.getSellerMap();
		 	if(null==sellerMap){
		 		sellerMap = new HashMap<String, String>();
		 	}
		 	String applicationtemplateId = rejectLetter.getApplicationTemplateId();
			String reasonCode = rejectLetter.getReasonCode();
		 String sellerKey = generateKeyMakePdfSeller(TemplateBuilderConstant.TemplateType.MAKEPDF, REJECT_LETTER_SEND_TIME, applicationtemplateId, reasonCode);
		 logger.debug("sellerMap : "+sellerMap);
		 logger.debug("sellerKey : "+sellerKey);
		 SendingConditionResponseDataM response = (SendingConditionResponseDataM)object;
		 if(!response.isGenEmail() && !response.isGenPaper() && sellerMap.containsKey(sellerKey)){
			 return RejectLetterUtil.EMAIL_TO_SELLER;
		 }
		return "";
	}
	
}
