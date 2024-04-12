package com.eaf.inf.batch.ulo.notification.condition;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.google.gson.Gson;
public class SendtoCustomerUtil {
 public static String NOTIFICATION_FINAL_APP_DECISION_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_APPROVE");
 public static String NOTIFICATION_FINAL_APP_DECISION_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_REJECT");
 public static String POLICY_PROGRAM_SUPPLEMENTARY = InfBatchProperty.getInfBatchConfig("POLICY_PROGRAM_SUPPLEMENTARY");
 private static String PERSONAL_TYPE_APPLICANT=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");	
 private static String PERSONAL_TYPE_SUPPLEMENTARY=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_SUPPLEMENTARY");	
 private static String NOTIFICATION_APPLICATION_TYPE_SUP = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_TYPE_SUP");
 private static transient Logger logger = Logger.getLogger(SendtoCustomerUtil.class);	 
 public static boolean isApprovedAll(ArrayList<RecipientInfoDataM> sendTocustomers) {// approve all card
	 int count=0;
	 if(null!=sendTocustomers && sendTocustomers.size()>0){
		 for(RecipientInfoDataM sendToCustomer : sendTocustomers){
			 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(sendToCustomer.getFinalDecision())){
				 count++;
			 }
		 }
		 if(count==sendTocustomers.size()){
			 return true;
		 }
	 }
	return false;
 }
 public static boolean isRejectAll(ArrayList<RecipientInfoDataM> sendTocustomers) { //reject all card
	 int count=0;
	 if(null!=sendTocustomers && sendTocustomers.size()>0){
		 for(RecipientInfoDataM sendToCustomer : sendTocustomers){
			 if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(sendToCustomer.getFinalDecision())){
				 count++;
			 }
		 }
		 if(count==sendTocustomers.size()){
			 logger.debug("isReject ALL>>");
			 return true;
		 }
	 }
	return false;
 }
 
 public static boolean isMainApprove(ArrayList<RecipientInfoDataM> sendTocustomers) {// main approve 
	 if(null!=sendTocustomers && sendTocustomers.size()>0){
		 for(RecipientInfoDataM sendToCustomer : sendTocustomers){
			 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(sendToCustomer.getFinalDecision()) && PERSONAL_TYPE_APPLICANT.equals(sendToCustomer.getPersonalType()) ){
				return true;
			 }
		 }
	 }
	return false;
 } 
 
 public static boolean isSuplemenratyReject(ArrayList<RecipientInfoDataM> sendTocustomers) {// sub reject
	 if(null!=sendTocustomers && sendTocustomers.size()>0){
		 for(RecipientInfoDataM sendToCustomer : sendTocustomers){
			 if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(sendToCustomer.getFinalDecision()) && PERSONAL_TYPE_SUPPLEMENTARY.equals(sendToCustomer.getPersonalType()) ){
				return true;
			 }
		 }
	 }
	return false;
 } 
 
 public static boolean isOnlySuplemenratyApprove(ArrayList<RecipientInfoDataM> sendTocustomers) {// supplementary approve approve only
	 boolean isAllMainReject =true;
	 boolean isAllSupApprove =true;
	 if(null!=sendTocustomers && sendTocustomers.size()>0){
		 for(RecipientInfoDataM sendToCustomer : sendTocustomers){
			 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(sendToCustomer.getFinalDecision()) && PERSONAL_TYPE_APPLICANT.equals(sendToCustomer.getPersonalType()) ){
				 isAllMainReject= false;
			 }else if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(sendToCustomer.getFinalDecision()) && NOTIFICATION_APPLICATION_TYPE_SUP.equals(sendToCustomer.getPersonalType())){
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
 
 public static boolean isAddSupplementaryApplication(ArrayList<RecipientInfoDataM> recipientInfos){
	 if(null!=recipientInfos && recipientInfos.size()>0){
		 for(RecipientInfoDataM recipientInfo : recipientInfos){
			 if(PERSONAL_TYPE_APPLICANT.equals(recipientInfo.getPersonalType())){
				 logger.debug("==is contain main applicant==");
				return false;
			 }
		 }
	 }else{
		 return false;
	 }
	 return true;
 }
 
 public static HashMap<String, ArrayList<RecipientInfoDataM>> filterRecipientInfoProduct(ArrayList<RecipientInfoDataM> recipientInfos){
		try {
			HashMap<String, ArrayList<RecipientInfoDataM>> hFilterRecipientProduct  = new HashMap<String, ArrayList<RecipientInfoDataM>>();
			if(null!=recipientInfos && recipientInfos.size()>0){
				for(RecipientInfoDataM recipientinfo: recipientInfos){
					String busClass = recipientinfo.getBusinessClassId();
					if(null==busClass) busClass="";
					String cartCode = recipientinfo.getCardCode();
					if(null==cartCode)cartCode="";
					String key = busClass+"_"+cartCode;
					ArrayList<RecipientInfoDataM> filterRecipients =  new ArrayList<RecipientInfoDataM>();
					if(hFilterRecipientProduct.containsKey(key)){
						filterRecipients =  hFilterRecipientProduct.get(key);
					}else{
						hFilterRecipientProduct.put(key, filterRecipients);
					}					
					filterRecipients.add(recipientinfo);			
				}
//				logger.debug("filter personal with product >>"+new Gson().toJson(hFilterRecipientProduct));	
				return hFilterRecipientProduct;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
}
