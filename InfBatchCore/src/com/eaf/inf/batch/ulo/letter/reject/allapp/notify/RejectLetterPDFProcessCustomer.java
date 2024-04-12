package com.eaf.inf.batch.ulo.letter.reject.allapp.notify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.letter.reject.inf.NotifyRejectLetterHelper;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFConfigDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFPersonalInfoDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFProcessRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.product.GenerateLetterUtil;

public class RejectLetterPDFProcessCustomer extends NotifyRejectLetterHelper{
	private static transient Logger logger = Logger.getLogger(RejectLetterPDFProcessCustomer.class);
	@Override
	public boolean validateTaskTransaction(Object object)throws InfBatchException{
		ArrayList<String> NEXTDAY_NOTIFICATION_TYPE = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_NOTIFICATION_TYPE_LIST");
		String NEXTDAY_SEND_TIME = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME");
		String DEFUALT_DATA_TYPE_ALL = InfBatchProperty.getInfBatchConfig("DEFUALT_DATA_TYPE_ALL");
		String PERSONAL_TYPE_APPLICANT=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");	
		String PERSONAL_TYPE_SUPPLEMENTARY=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_SUPPLEMENTARY");
		String PRIORITY_FIRST=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_FIRST");
		String PRIORITY_SECOND=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_SECOND");
		String PRIORITY_THIRD=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_THIRD");
		
		RejectLetterPDFProcessRequestDataM rejectLetterPDFProcessRequest = (RejectLetterPDFProcessRequestDataM)object;
		RejectLetterPDFDataM rejectLetterPdf = rejectLetterPDFProcessRequest.getRejectLetterPdf();
		RejectLetterPDFConfigDataM config = rejectLetterPDFProcessRequest.getConfig();
		HashMap<String,String> priorityMap = config.getPriorityMap();
		RejectLetterPDFPersonalInfoDataM rejectedPersonalInfo = rejectLetterPdf.getRejectedPersonalInfo();
		boolean isInfiniteWisdomPremierApplication = GenerateLetterUtil.isInfiniteWisdomPremierApplication(rejectLetterPdf.getAppTemplate());
		logger.debug("isInfiniteWisdomPremierApplication : "+isInfiniteWisdomPremierApplication);
		logger.debug("priorityMap : "+priorityMap);
		if(isInfiniteWisdomPremierApplication){
			return false; // PAPER
		}
		String applicationTemplate = rejectLetterPdf.getAppTemplate();
	 	String reasonCode = rejectedPersonalInfo.getReasonCode();
	 	List<String> cashTransferTypes = rejectLetterPdf.findCashTransferTypePersonal(rejectedPersonalInfo.getPersonalId());
		if(InfBatchUtil.empty(cashTransferTypes)){
			cashTransferTypes = new ArrayList<String>(Arrays.asList(new String[]{ DEFUALT_DATA_TYPE_ALL }));
		}
		ArrayList<String> priorityNextDayList = new ArrayList<String>();
		for(String cashTransferType : cashTransferTypes){
			cashTransferType = !InfBatchUtil.empty(cashTransferType) ? cashTransferType : DEFUALT_DATA_TYPE_ALL ;
			for(String type : NEXTDAY_NOTIFICATION_TYPE){
				 String key = generateKeyPriority(type, NEXTDAY_SEND_TIME, applicationTemplate, reasonCode, cashTransferType);
				 String priority = priorityMap.get(key);
				 if(!InfBatchUtil.empty(priority) && !priorityNextDayList.contains(priority)){
					 priorityNextDayList.add(priority);
				 }
			}
		}
		String personalType = rejectedPersonalInfo.getPersonalType();
		logger.debug("priorityNextDayList : "+priorityNextDayList);
		logger.debug("personalType : "+personalType);
		boolean isGenEmail =false;
		if(PERSONAL_TYPE_APPLICANT.equals(personalType)){
			String EMAIL_PRIORITY = rejectLetterPdf.getEmailPriority();
			logger.debug("EMAIL_PRIORITY : "+EMAIL_PRIORITY);
			if(PRIORITY_FIRST.equals(EMAIL_PRIORITY)){
				isGenEmail = true;
			}
			if(PRIORITY_SECOND.equals(EMAIL_PRIORITY)){
				 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(PRIORITY_FIRST) && !InfBatchUtil.empty(rejectedPersonalInfo.getMobileNo())){
					 isGenEmail = false;	// send Nextday
				 }else{
					 isGenEmail = true;
				 }
			 }
			if(!isGenEmail){
				if(PRIORITY_THIRD.equals(EMAIL_PRIORITY)){
					if(!InfBatchUtil.empty(priorityNextDayList)  && !InfBatchUtil.empty(rejectedPersonalInfo.getMobileNo())
							 && (priorityNextDayList.contains(PRIORITY_FIRST) || priorityNextDayList.contains(PRIORITY_SECOND))){
						 isGenEmail = false; // send email (makePDF)
					 }else{
						 isGenEmail = true;
					 }
				}
			}
		}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
			 String SUB_EMAIL_PRIORITY = rejectLetterPdf.getSubEmailPriority();
			 logger.debug("SUB_EMAIL_PRIORITY : "+SUB_EMAIL_PRIORITY);
			 if(PRIORITY_FIRST.equals(SUB_EMAIL_PRIORITY)){
				 isGenEmail = true;
			 }
			 if(PRIORITY_SECOND.equals(SUB_EMAIL_PRIORITY)){
				 if(!InfBatchUtil.empty(priorityNextDayList) && priorityNextDayList.contains(PRIORITY_FIRST) && !InfBatchUtil.empty(rejectedPersonalInfo.getMobileNo())){
					 isGenEmail = false;	// send Nextday
				 }else{
					 isGenEmail = true;
				 }
			 }
			 if(!isGenEmail){
				 if(PRIORITY_THIRD.equals(SUB_EMAIL_PRIORITY)){
					 if(!InfBatchUtil.empty(priorityNextDayList)  && !InfBatchUtil.empty(rejectedPersonalInfo.getMobileNo())
							 && (priorityNextDayList.contains(PRIORITY_FIRST) || priorityNextDayList.contains(PRIORITY_SECOND))){
						 isGenEmail = false; // send email (makePDF)
					 }else{
						 isGenEmail = true;
					 }
				 }
			 }
		}
		logger.debug("isGenEmail : "+isGenEmail);
		return isGenEmail;
	}
	protected String generateKeyPriority(String notificationType,String sendTime,String applicationTemplate,String reasonCode,String cashTransferType){
		 return notificationType+"_"+sendTime+"_"+applicationTemplate+"_"+reasonCode+"_"+cashTransferType;
	}
}
