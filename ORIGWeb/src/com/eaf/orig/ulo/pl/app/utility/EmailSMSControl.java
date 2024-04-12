package com.eaf.orig.ulo.pl.app.utility;

import java.util.HashMap;
import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.model.app.EmailSMSDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;

public class EmailSMSControl {
	static Logger logger = Logger.getLogger(EmailSMSControl.class);
	
	public void send(PLApplicationDataM applicationM,UserDetailM userM){
		try{
			if(LogicIgnoreSendEmail(applicationM)){
				logger.debug("IgnoreSendEmail..>> "+applicationM.getAppRecordID());
				return;
			}
			sendEmail(applicationM, userM);
			sendSMS(applicationM, userM);						
		}catch (Exception e) {
			logger.fatal("ERROR ",e);
		}
	}
	public boolean LogicIgnoreSendEmail(PLApplicationDataM applicationM){
		if(OrigConstant.FLAG_C.equals(applicationM.getBlockFlag()) 
				&& OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision())){
			return true;
		}
		return false;
	}	
	public void sendEmail(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		final EmailSMSDataM email = new EmailSMSDataM();
			email.setAppRecID(applicationM.getAppRecordID());
			email.setRole(userM.getCurrentRole());
			email.setType(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL);
			email.setUserID(userM.getUserName());
			email.setBusClassID(applicationM.getBusinessClassId());
			email.setData(map(applicationM, userM));
			email.setApplyChannel(applicationM.getApplyChannel());
			email.setCaDecision(applicationM.getCaDecision());
			
			PLCashTransferDataM cashTransferM = applicationM.getCashTransfer();			
			if(null != cashTransferM && OrigConstant.cashDayType.CASH_NA.equals(cashTransferM.getCashTransferType())){
				email.setCashTransferType(cashTransferM.getCashTransferType());
			}
			
			new Thread(new Runnable() {
		           public void run() {
			        	try {
			        		doSendEmail(email);
						}catch(Exception e){
							logger.fatal("ERROR ",e);
						}		   			
		           }
				}).start();
	}
	
	public void doSendEmail(EmailSMSDataM email) throws Exception{		
		logger.debug("doSendEmail()..AppRecID >> "+email.getAppRecID());	
		logger.debug("doSendEmail()..Role >> "+email.getRole());		
		InitialContext context = null;
		QueueConnectionFactory queueConFac = null;
		QueueConnection queueCon = null;
		QueueSession queueSess = null;
		Queue queue = null;
		QueueSender queueSend = null;
		ObjectMessage objMessage = null;		
		try{
			context = new InitialContext();
			queueConFac = (QueueConnectionFactory) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageMailCF"),QueueConnectionFactory.class);
			queue = (Queue) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageMailQueue"),Queue.class);
			queueCon = queueConFac.createQueueConnection();
			queueSess = queueCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queueSend = queueSess.createSender(queue);
			objMessage = queueSess.createObjectMessage();			
			objMessage.setObject(email);
			queueSend.send(objMessage);
		}catch (NamingException e){
			throw new Exception("ERROR "+e.getMessage());
		}catch (JMSException e){
			throw new Exception("ERROR "+e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
		logger.debug("doSendEmail().. Success!! AppRecID >> "+email.getAppRecID());	
	}
	
	public void sendSMS(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		final EmailSMSDataM sms = new EmailSMSDataM();		
			sms.setAppRecID(applicationM.getAppRecordID());
			sms.setRole(userM.getCurrentRole());
			sms.setType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
			sms.setUserID(userM.getUserName());
			sms.setBusClassID(applicationM.getBusinessClassId());
			sms.setData(map(applicationM, userM));
			sms.setApplyChannel(applicationM.getApplyChannel());
			sms.setCaDecision(applicationM.getCaDecision());
		
		PLCashTransferDataM cashTransferM = applicationM.getCashTransfer();			
		if(null != cashTransferM && OrigConstant.cashDayType.CASH_NA.equals(cashTransferM.getCashTransferType())){
			sms.setCashTransferType(cashTransferM.getCashTransferType());
		}
		
		if(validateSMS(userM.getCurrentRole(), applicationM)){
			new Thread(new Runnable() {
		           public void run() {
			        	try {
			        		doSendSMS(sms);
						}catch(Exception e){
							logger.fatal("ERROR ",e);
						}		   			
		           }
				}).start();
		}
		
	}
	
	public boolean validateSMS(String role,PLApplicationDataM applicationM){
		if(OrigConstant.ROLE_DE.equals(role) || OrigConstant.ROLE_DC.equals(role)){
			boolean validate = true;
			if(OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision())){
				Vector<PLReasonDataM> reasonVt = applicationM.getReasonVect();
				if(reasonVt != null && reasonVt.size() > 0){
					for(PLReasonDataM reasonM:reasonVt){
						if(OrigConstant.fieldId.CANCEL_REASON.equals(reasonM.getReasonType()) &&  
								OrigConstant.cancelReason.CUST_CANCEL.equals(reasonM.getReasonCode())){
							validate = false;
							break;
						}
					}
				}
			}
			return validate;
		}
		return true;
	}
	
	public void doSendSMS(EmailSMSDataM sms) throws Exception{		
		logger.debug("doSendSMS()..AppRecID >> "+sms.getAppRecID());	
		logger.debug("doSendSMS()..Role >> "+sms.getRole());		
		InitialContext context = null;
		QueueConnectionFactory queueConFac = null;
		QueueConnection queueCon = null;
		QueueSession queueSess = null;
		Queue queue = null;
		QueueSender queueSend = null;
		ObjectMessage objMessage = null;		
		try{
			context = new InitialContext();
			queueConFac = (QueueConnectionFactory) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageSMSCF"),QueueConnectionFactory.class);
			queue = (Queue) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageSMSQueue"),Queue.class);
			queueCon = queueConFac.createQueueConnection();
			queueSess = queueCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queueSend = queueSess.createSender(queue);
			objMessage = queueSess.createObjectMessage();			
			objMessage.setObject(sms);
			queueSend.send(objMessage);
		}catch (NamingException e){
			throw new Exception("ERROR "+e.getMessage());
		}catch (JMSException e){
			throw new Exception("ERROR "+e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
		logger.debug("doSendSMS().. Success!! AppRecID >> "+sms.getAppRecID());	
	}
	
	public HashMap<String, String> map(PLApplicationDataM applicationM,UserDetailM userM){
		PLSaleInfoDataM saleInfoM = applicationM.getSaleInfo();
		if(null == saleInfoM){
			saleInfoM = new PLSaleInfoDataM();
		}
		String customerName = "";
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		if(null == personalM){
			personalM = new PLPersonalInfoDataM();
		}
		customerName = 	HTMLRenderUtil.replaceNull(personalM.getThaiFirstName())+" "+HTMLRenderUtil.replaceNull(personalM.getThaiLastName());	
	
		HashMap<String, String> data = new HashMap<String, String>();
			data.put(OrigConstant.EmailSMS.KEY_APPLICATION_NO, applicationM.getApplicationNo());
			data.put(OrigConstant.EmailSMS.KEY_BRANCH_NAME, saleInfoM.getSalesBranchCode());
			data.put(OrigConstant.EmailSMS.KEY_CUSTOMER_NAME, customerName);
			data.put(OrigConstant.EmailSMS.KEY_PRODUCE_NAME, applicationM.getProduct());
			data.put(OrigConstant.EmailSMS.KEY_REF_NO, applicationM.getRefNo());
			data.put(OrigConstant.EmailSMS.KEY_SALE_TYPE, applicationM.getSaleType());
			data.put(OrigConstant.EmailSMS.KEY_SALE_ID, saleInfoM.getSalesName());
			data.put(OrigConstant.EmailSMS.KEY_CHENNEL, applicationM.getApplyChannel());
			data.put(OrigConstant.EmailSMS.KEY_REF_NAME, saleInfoM.getRefName());
		return data;
	}
	
}
