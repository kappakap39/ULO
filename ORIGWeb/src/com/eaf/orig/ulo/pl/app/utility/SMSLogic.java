package com.eaf.orig.ulo.pl.app.utility;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.ulo.pl.app.dao.PLOrigEmailSMSDAO;
import com.eaf.orig.ulo.pl.model.app.EmailSMSDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;

public class SMSLogic {
	public void process(EmailSMSDataM emailSmsM)throws Exception{
		if(OrigConstant.ROLE_DE.equals(emailSmsM.getRole())){
			Process_DE(emailSmsM);
		}else if(OrigConstant.ROLE_DC.equals(emailSmsM.getRole())){
			Process_DC(emailSmsM);
		}else if(OrigConstant.ROLE_CA.equals(emailSmsM.getRole())){
			Process_CA(emailSmsM);
		}
	}
	public void Process_CA(EmailSMSDataM emailSmsM) throws Exception{
		if(OrigConstant.Action.APPROVE.equals(emailSmsM.getCaDecision()) || 
				OrigConstant.Action.OVERRIDE.equals(emailSmsM.getCaDecision()) ||
					OrigConstant.Action.POLICY_EXCEPTION.equals(emailSmsM.getCaDecision())){
			if(OrigConstant.BusClass.FCP_KEC_IC.equals(emailSmsM.getBusClassID())){
				
				PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();			
				PLOrigContactDataM contactM = new PLOrigContactDataM();
					contactM.setContactLogId(emailSMSUtil.getContactLogID());
					contactM.setApplicationRecordId(emailSmsM.getAppRecID());
					contactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
					contactM.setCreateBy(emailSmsM.getUserID());
					contactM.setSendBy(emailSmsM.getUserID());
					contactM.setTemplateName(OrigConstant.EmailSMS.SMS_APPROVE_INCREASE);
					ServiceReqRespTool serviceTool = new ServiceReqRespTool();
					contactM.setTransactionId(serviceTool.GenerateTransectionId());	
					
				SMSEngine engine = new SMSEngine();	
					engine.send(contactM);
					
			}else if(OrigConstant.BusClass.FCP_KEC_DC.equals(emailSmsM.getBusClassID())){
				
				PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();		
				PLOrigContactDataM contactM = new PLOrigContactDataM();
					contactM.setContactLogId(emailSMSUtil.getContactLogID());
					contactM.setApplicationRecordId(emailSmsM.getAppRecID());
					contactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
					contactM.setCreateBy(emailSmsM.getUserID());
					contactM.setSendBy(emailSmsM.getUserID());
					contactM.setTemplateName(OrigConstant.EmailSMS.SMS_APPROVE_DECREASE);
					ServiceReqRespTool serviceTool = new ServiceReqRespTool();
					contactM.setTransactionId(serviceTool.GenerateTransectionId());	
				
				SMSEngine engine = new SMSEngine();	
					engine.send(contactM);
					
			}else{				
				if(OrigConstant.cashDayType.CASH_NA.equals(emailSmsM.getCashTransferType())){
					
					PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();		
					PLOrigContactDataM contactM = new PLOrigContactDataM();
						contactM.setContactLogId(emailSMSUtil.getContactLogID());
						contactM.setApplicationRecordId(emailSmsM.getAppRecID());
						contactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
						contactM.setCreateBy(emailSmsM.getUserID());
						contactM.setSendBy(emailSmsM.getUserID());
						contactM.setTemplateName(OrigConstant.EmailSMS.SMS_APPROVE_NON_CASH_DAY1);
						ServiceReqRespTool serviceTool = new ServiceReqRespTool();
						contactM.setTransactionId(serviceTool.GenerateTransectionId());	
						
					SMSEngine engine = new SMSEngine();	
						engine.send(contactM);
				}
			}
		}
	}
	public void Process_DC(EmailSMSDataM emailSmsM) throws Exception{
		PLOrigEmailSMSDAO emailDAO = PLORIGDAOFactory.getPLOrigEmailSMSDAO();
		if(emailDAO.countOrigContactLog(emailSmsM.getAppRecID(), OrigConstant.EmailSMS.SMS_RECEIVE_APPLICATION) == 0){
			
			PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();			
			PLOrigContactDataM contactM = new PLOrigContactDataM();
				contactM.setContactLogId(emailSMSUtil.getContactLogID());
				contactM.setApplicationRecordId(emailSmsM.getAppRecID());
				contactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
				contactM.setCreateBy(emailSmsM.getUserID());
				contactM.setSendBy(emailSmsM.getUserID());
				contactM.setTemplateName(OrigConstant.EmailSMS.SMS_RECEIVE_APPLICATION);
				ServiceReqRespTool serviceTool = new ServiceReqRespTool();
				contactM.setTransactionId(serviceTool.GenerateTransectionId());		
				
			SMSEngine engine = new SMSEngine();	
				engine.send(contactM);
		}
	}
	public void Process_DE(EmailSMSDataM emailSmsM) throws Exception{
		PLOrigEmailSMSDAO emailDAO = PLORIGDAOFactory.getPLOrigEmailSMSDAO();
		if(emailDAO.countOrigContactLog(emailSmsM.getAppRecID(), OrigConstant.EmailSMS.SMS_RECEIVE_APPLICATION) == 0){
			
			PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();			
			PLOrigContactDataM contactM = new PLOrigContactDataM();
				contactM.setContactLogId(emailSMSUtil.getContactLogID());
				contactM.setApplicationRecordId(emailSmsM.getAppRecID());
				contactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
				contactM.setCreateBy(emailSmsM.getUserID());
				contactM.setSendBy(emailSmsM.getUserID());
				contactM.setTemplateName(OrigConstant.EmailSMS.SMS_RECEIVE_APPLICATION);
				ServiceReqRespTool serviceTool = new ServiceReqRespTool();
				contactM.setTransactionId(serviceTool.GenerateTransectionId());	
				
			SMSEngine engine = new SMSEngine();	
				engine.send(contactM);
		}
	}
}
