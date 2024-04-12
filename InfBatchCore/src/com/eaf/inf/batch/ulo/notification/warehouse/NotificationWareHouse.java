package com.eaf.inf.batch.ulo.notification.warehouse;

 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.notification.condition.NotificationCondition;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.DMNotificationDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailTemplateDataM;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotiCCDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class NotificationWareHouse {
	private static transient Logger logger = Logger.getLogger(NotificationWareHouse.class);
	public static String SEND_TO_TYPE_MANAGER= InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_MANAGER");
	public static String SEND_TO_TYPE_BORROWER= InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_BORROWER");
	public static String SEND_TO_TYPE_FIX= InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_FIX");
	public static String USER_WEB_SCAN= "USER_WEB_SCAN";
	
	public  static boolean  notificationWareHouseProcess(DMNotificationDataM dmNotification,String dayNum,java.sql.Date appDate,java.sql.Date compareDate, HashMap<String, ArrayList<DMNotificationDataM>> mapplingList ){
		boolean isSuccess=false;
		try {
			NotificationDAO dao = NotificationFactory.getNotificationDAO();
			int addDays = toInteger(dayNum);
			Date compareUtilDate = ToJavaDate(compareDate);
			if(null==compareUtilDate){
				logger.debug(">>compareUtilDate Is Null>>");
				return false;
			}
			Date applicationDate = ToJavaDate(appDate);
			Date conditionDate = DateUtils.addDays(compareUtilDate,addDays);
			logger.debug(">>addDays>>"+addDays);
			logger.debug(">>compareUtilDate>>"+compareUtilDate);
			logger.debug(">>applicationDate>>"+applicationDate);
			logger.debug(">>conditionDate>>"+conditionDate);
			if(conditionDate.compareTo(applicationDate)==0){
				HashMap<String,ArrayList<JobCodeDataM>>   hJobCodes = dao.selectDMJobCodes(dmNotification.getApplyChanel(), dmNotification.getChannelGroupCode());
				logger.debug(">>hJobCodes>>"+hJobCodes);
				if(!InfBatchUtil.empty(hJobCodes)){			
					HashMap<String,String> emails = new HashMap<String,String>();
					String userWebSacn = dmNotification.getUserWebScan();
					if(hJobCodes.containsKey(SEND_TO_TYPE_MANAGER)){
						NotificationCondition notificationCondition = new NotificationCondition();
						ArrayList<VCEmpInfoDataM>  vcEmpList =notificationCondition.getSendToVCEmpManagers(userWebSacn, hJobCodes.get(SEND_TO_TYPE_MANAGER));
						arrayVCEmpList(vcEmpList,emails);	 
					} 
					if(hJobCodes.containsKey(SEND_TO_TYPE_FIX)){
						ArrayList<JobCodeDataM> jobCodesList = hJobCodes.get(SEND_TO_TYPE_FIX);
						arrayFixEmailJobCodeList(jobCodesList, emails);
					}
					logger.debug("emails>>>>>>>>>>>>"+emails.size());
					if(!InfBatchUtil.empty(emails)){
						EmailTemplateDataM emailTemplate =  new EmailTemplateDataM();
						emailTemplate.setEmailElement(new ArrayList<String>(emails.values()));
						dmNotification.setEmailtemplatedataM(emailTemplate);
						ccJobCodeProcess(dmNotification,false);
						mappingKeyProcess(mapplingList,dmNotification);
					}
				}
				isSuccess = true;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return isSuccess;
	}
	
	public  static TaskObjectDataM  notificationReturnProcess(DMNotificationDataM dmNotification,int addDays,java.sql.Date appDate){
		try {
			NotificationDAO dao = NotificationFactory.getNotificationDAO();
				if(!InfBatchUtil.empty(dmNotification.getActionDueDate())){
					Date acttionDueDate = ToJavaDate(dmNotification.getActionDueDate());
					Date applicationDate = ToJavaDate(appDate);
					Date addDateCondition = DateUtils.addDays(acttionDueDate,addDays);
					logger.debug(">>addDays>>"+addDays);
					logger.debug(">>acttionDueDate>>"+acttionDueDate);
					logger.debug(">>applicationDate>>"+applicationDate);
					logger.debug(">>addDateCondition>>"+addDateCondition);
					
					if(applicationDate.compareTo(addDateCondition)==0){
						TaskObjectDataM taskObject = new TaskObjectDataM(); 
						HashMap<String,ArrayList<JobCodeDataM>>   hJobCodes = dao.selectDMJobCodes(dmNotification.getApplyChanel(), dmNotification.getChannelGroupCode());
						logger.debug("<<<<hJobCodes>>>"+hJobCodes);
						if(!InfBatchUtil.empty(hJobCodes)){						
							HashMap<String,String> emails = new HashMap<String,String>();
							if(hJobCodes.containsKey(SEND_TO_TYPE_BORROWER)){
								ArrayList<String> empUsers = new ArrayList<String>();
								empUsers.add(dmNotification.getDmRequestUser());
								ArrayList<VCEmpInfoDataM> vcEmpDataInfos = NotificationFactory.getNotificationDAO().selectVCEmpList(empUsers);
								arrayVCEmpList(vcEmpDataInfos, emails);
							}		
							logger.debug(">>.emails>>"+emails);
							if(!InfBatchUtil.empty(emails)){
								EmailTemplateDataM emailTemplate =  new EmailTemplateDataM();
								emailTemplate.setEmailElement(new ArrayList<String>(emails.values()));
								dmNotification.setEmailtemplatedataM(emailTemplate);
								ccJobCodeProcess(dmNotification,true);
								taskObject.setObject(dmNotification);
								taskObject.setUniqueId(dmNotification.getDmId());
							}
						}
						
						return taskObject;
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
	public static void ccJobCodeProcess(DMNotificationDataM dmNotification,boolean isReturn){
 		try {
 			EmailTemplateDataM emailTemplate = dmNotification.getEmailtemplatedataM();
 			if(!InfBatchUtil.empty(emailTemplate)){
 				NotiCCDataM notiCCDataM = dmNotification.getNoticcDataM();
 	 			int roundOfNoti = dmNotification.getRoundOfNotification()+1;
 	 			int ccRoundFrom = notiCCDataM.getCcRoundFrom();
 	 				ccRoundFrom = InfBatchUtil.empty(ccRoundFrom)?0:ccRoundFrom;
 	 			int ccRoundTo =notiCCDataM.getCcRoundTo();
 	 				ccRoundTo = InfBatchUtil.empty(ccRoundTo)?0:ccRoundTo;
 	 				logger.debug("ccRoundFrom>>"+ccRoundFrom);
 	 				logger.debug("ccRoundTo>>"+ccRoundTo);
 	 			if(!InfBatchUtil.empty(notiCCDataM) && ccRoundFrom!=0 ){
 	 				if((ccRoundTo==0 && roundOfNoti>=ccRoundFrom) || (roundOfNoti >=ccRoundFrom  && roundOfNoti <= ccRoundTo)){
 	 					if(notiCCDataM.getCcSendTo().contains(SEND_TO_TYPE_MANAGER)){
 	 						NotificationDAO dao = NotificationFactory.getNotificationDAO();
 	 						ArrayList<String> empList = new ArrayList<String>();
 	 						String empId = isReturn?dmNotification.getDmRequestUser():dmNotification.getUserWebScan();
 	 						logger.debug("empId>>"+empId);
 	 						empList.add(empId);		
 	 						HashMap<String, ArrayList<JobCodeDataM>> hJobCode = dao.selectJobCodeChannelMapping(empList);
 	 						ArrayList<JobCodeDataM> jobCode = hJobCode.get(empId);
 	 						NotificationCondition notificationCondition = new NotificationCondition();
 							ArrayList<VCEmpInfoDataM>  vcEmpList =notificationCondition.getSendToVCEmpManagers(empId,jobCode);
 							HashMap<String,String> ccEmail = new HashMap<String,String>();
 							arrayVCEmpList(vcEmpList,ccEmail);
 							if(!InfBatchUtil.empty(ccEmail)){
 	 							emailTemplate.setCcEmailElement(new ArrayList<String>(ccEmail.values()));
 							}

 	 					}else if(notiCCDataM.getCcSendTo().contains(SEND_TO_TYPE_FIX)){
 	 						emailTemplate.addCCEmailElement(notiCCDataM.getCcFixEmail());
 	 					}
 	 				}		 			 
 	 			}
 			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	public static void mappingKeyProcess(HashMap<String,ArrayList<DMNotificationDataM>> mappingList ,DMNotificationDataM dmNotification){
		EmailTemplateDataM emailTemplate = dmNotification.getEmailtemplatedataM();
		try {
			if(!InfBatchUtil.empty(emailTemplate) && !InfBatchUtil.empty(emailTemplate.getEmailElement())){
				ArrayList<String> emails =emailTemplate.getEmailElement(); 
				for(String sendToEmail: emails){		
						String MAPPING_KEY = sendToEmail+"_"+dmNotification.getRoundOfNotification()+"_"+dmNotification.getUserWebScan();
						ArrayList<DMNotificationDataM> dmNotificationList = mappingList.get(MAPPING_KEY);
						 if(InfBatchUtil.empty(dmNotificationList)){
							 dmNotificationList = new ArrayList<DMNotificationDataM>();
							 mappingList.put(MAPPING_KEY,dmNotificationList);
						 }
						 dmNotificationList.add(dmNotification);
				}
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	public static int  toInteger(String num) {
		if(!InfBatchUtil.empty(num)){
			return Integer.parseInt(num);
		}
		return 0;
	}
 
	public static Date ToJavaDate( java.sql.Date sqlDate) {
        Date javaUtilDate = null;
        if (sqlDate != null) {
        	javaUtilDate = new Date(sqlDate.getTime());
        }
        return javaUtilDate;
    }	
	
	public static void arrayVCEmpList(ArrayList<VCEmpInfoDataM>  vcEmpList,HashMap<String,String> emails){
		try {
			if(!InfBatchUtil.empty(vcEmpList)){
				for(VCEmpInfoDataM vcEmpinfo : vcEmpList){
					if(!InfBatchUtil.empty(vcEmpinfo.getEmail())){
						emails.put(vcEmpinfo.getEmail(),vcEmpinfo.getEmail());
					}
				}
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	public static void arrayFixEmailJobCodeList(ArrayList<JobCodeDataM>  jobCodeList,HashMap<String,String> emails){
		try {
			if(!InfBatchUtil.empty(jobCodeList)){
				for(JobCodeDataM jobCode : jobCodeList){
					if(!InfBatchUtil.empty(jobCode.getFixEmail())){
						emails.put(jobCode.getFixEmail(),jobCode.getFixEmail());
					}
				}
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
}
