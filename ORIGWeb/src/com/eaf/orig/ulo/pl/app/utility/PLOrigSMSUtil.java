package com.eaf.orig.ulo.pl.app.utility;

import java.sql.Timestamp;
import java.util.Date;
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

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.model.SMSDataM;
import com.eaf.orig.shared.model.SMSPrepareDataM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFUVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;


public class PLOrigSMSUtil {
	private static Logger logger = Logger.getLogger(PLOrigEmailSMSUtil.class);
	private static String special = "\\";
		
	public void sendSMS(PLApplicationDataM appM, String templateName) throws Exception{
		PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();
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
						
			PLOrigContactDataM origContactM = new PLOrigContactDataM();
			origContactM.setContactLogId(emailSMSUtil.getContactLogID());
			origContactM.setApplicationRecordId(appM.getAppRecordID());
			origContactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
			origContactM.setCreateBy(appM.getUpdateBy());
			origContactM.setSendBy(appM.getUpdateBy());
			origContactM.setTemplateName(templateName);
			//Create service transaction id
			ServiceReqRespTool serviceTool = new ServiceReqRespTool();
			origContactM.setTransactionId(serviceTool.GenerateTransectionId());
			
			objMessage.setObject(origContactM);
			queueSend.send(objMessage);
			logger.debug("@@@@@ Already send SMS to Queue");
		}catch (NamingException e){
			logger.error("##### Cannot send SMS to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send SMS to Queue :"+e.getMessage());
		}catch (JMSException e){
			logger.error("##### Cannot send SMS to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send SMS to Queue :"+e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
	}
	
	public void sendSMS(PLOrigContactDataM origContactM) throws Exception{
		InitialContext context = null;
		QueueConnectionFactory queueConFac = null;
		QueueConnection queueCon = null;
		QueueSession queueSess = null;
		Queue queue = null;
		QueueSender queueSend = null;
		//TextMessage txtMessage = null;
		ObjectMessage objMessage = null;
		try{
			context = new InitialContext();
			queueConFac = (QueueConnectionFactory) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageSMSCF"),QueueConnectionFactory.class);
			queue = (Queue) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageSMSQueue"),Queue.class);
			queueCon = queueConFac.createQueueConnection();
			queueSess = queueCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queueSend = queueSess.createSender(queue);
			objMessage = queueSess.createObjectMessage();
			
			objMessage.setObject(origContactM);
			queueSend.send(objMessage);
			logger.debug("@@@@@ Already send SMS to Queue");
		}catch (NamingException e){
			logger.error("##### Cannot send SMS to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send SMS to Queue :"+e.getMessage());
		}catch (JMSException e){
			logger.error("##### Cannot send SMS to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send SMS to Queue :"+e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
	}
	private String getMobileNo(PLPersonalInfoDataM personalM){
		if(null != personalM && !OrigUtil.isEmptyString(personalM.getMailingAddress())){
			PLAddressDataM addressM = personalM.getAddressDataM(personalM.getMailingAddress());		
			if(addressM != null && !OrigUtil.isEmptyString(addressM.getMobileNo())){
				return addressM.getMobileNo();
			}
		}
		return "";
	}
	public SMSDataM prepareSMSMessage(SMSPrepareDataM smsPrepareM){
		SMSDataM smsM  = new SMSDataM();
		String content = smsPrepareM.getMessage();
		//if follow up SMS has reference number, add reference to message
		try{
			if(OrigConstant.EmailSMS.SMS_FOLLOW_UP.equals(smsPrepareM.getTemplateName()) && !OrigUtil.isEmptyString(smsPrepareM.getRefNo())){
//				#septem comment remove this message to config
//				content = content + " " + DataFormatUtility.getThaiTextDescMessage("HAVE_REF_NO") + smsPrepareM.getRefNo();
				content = content.replaceAll(special + OrigConstant.EmailSMS.KEY_REF_NO, this.StringNullToSpecific(smsPrepareM.getRefNo(),""));
			}else if(OrigConstant.EmailSMS.SMS_APPROVE_INCREASE.equals(smsPrepareM.getTemplateName()) 
					|| OrigConstant.EmailSMS.SMS_APPROVE_DECREASE.equals(smsPrepareM.getTemplateName())){
				content = content.replaceAll(special + OrigConstant.EmailSMS.KEY_CREDIT_LINE, DataFormatUtility.displayCommaNumber(smsPrepareM.getFinalCreditLimit()));
			}
			smsM.setContent(content);
			smsM.setNumber(smsPrepareM.getMobile());
		}catch(Exception e){
			logger.error("ERROR " + e.getMessage());
		}
		return smsM;
	}
	
	public void sendSMSManual(String smsCode, PLApplicationDataM applicationM) throws Exception{
		logger.debug("sendSMSManual() >> "+smsCode);
		try{
			SMSPrepareDataM smsM = new SMSPrepareDataM();
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);

			String nationality = "";
			if(null != personalM){
				nationality = personalM.getNationality();
			}
			String content = this.getSMSTemplateMasterValue(smsCode, nationality, applicationM.getBusinessClassId());
			String flag = "N";
			if(!OrigUtil.isEmptyString(content)){
				flag = "Y";
			}
			
			smsM.setEnableFlag(flag);
			smsM.setMessage(content);
			smsM.setMobile(getMobileNo(personalM));
			smsM.setRefNo(applicationM.getRefNo());
			smsM.setTemplateName(smsCode);
			smsM.setFinalCreditLimit(applicationM.getFinalCreditLimit());
			
			sendSMS(applicationM, smsCode, smsM);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getMessage());
		}
	}
	
	public void sendSMS(PLApplicationDataM applicationM, String templateName,SMSPrepareDataM smsM) throws Exception{
		PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();
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
						
			PLOrigContactDataM origContactM = new PLOrigContactDataM();
				origContactM.setContactLogId(emailSMSUtil.getContactLogID());
				origContactM.setApplicationRecordId(applicationM.getAppRecordID());
				origContactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
				origContactM.setCreateBy(applicationM.getUpdateBy());
				origContactM.setSendBy(applicationM.getUpdateBy());
				origContactM.setTemplateName(templateName);
				//Create service transaction id
				ServiceReqRespTool serviceTool = new ServiceReqRespTool();
				origContactM.setTransactionId(serviceTool.GenerateTransectionId());
				origContactM.setSmsM(smsM);
				origContactM.setManualFLAG(OrigConstant.FLAG_Y);
				
			objMessage.setObject(origContactM);
			queueSend.send(objMessage);
			logger.debug("@@@@@ Already send SMS to Queue");
		}catch (NamingException e){
			logger.error("##### Cannot send SMS to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send SMS to Queue :"+e.getMessage());
		}catch (JMSException e){
			logger.error("##### Cannot send SMS to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send SMS to Queue :"+e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
	}
	
	public boolean sendSMSWithSMSCode(String smsCode, PLApplicationDataM applicationM) throws Exception{
		logger.debug("sendSMSWithSMSCode()..SMS CODE >> "+smsCode);
		boolean SUCCESS = false;
		try{			
			this.sendSMS(applicationM, smsCode);
			SUCCESS = true;
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String getSMSTemplateMasterValue(String smsTemplateCode, String nationality, String busClass){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getSMSTemplateMasterValue(smsTemplateCode, nationality, busClass);
		}catch (Exception e){
			logger.fatal("##### getKBANKEmployeeEmail error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public SMSPrepareDataM prepareSMSData(String appId, String templateName){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().prepareSMSData(appId, templateName);
		}catch (Exception e){
			logger.fatal("##### prepareSMSData error:" + e.getMessage());
			e.printStackTrace();
			return new SMSPrepareDataM();
		}
	}
	
	public void saveFollowDetail(PLApplicationDataM applicationM) throws Exception{		
		logger.debug("saveFollowDetail().. AppRecordID >>"+applicationM.getAppRecordID());
		
		PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);			
		}
		
		String mobileNo = getMobileNo(personM);	
		
		Vector<PLXRulesFUVerificationDataM> xRulesFUVerVect = xrulesVerM.getxRulesFUVerificationDataMs();
		if(xRulesFUVerVect == null){
			xRulesFUVerVect = new Vector<PLXRulesFUVerificationDataM>();
			xrulesVerM.setxRulesFUVerificationDataMs(xRulesFUVerVect);
		}
		
		PLXRulesFUVerificationDataM xrulesFuVerM = new PLXRulesFUVerificationDataM();
			xrulesFuVerM.setPersonalID(personM.getPersonalID());
			xrulesFuVerM.setSeq(xRulesFUVerVect.size()+1);
			xrulesFuVerM.setCreateBy(applicationM.getUpdateBy());
			xrulesFuVerM.setUpdateBy(applicationM.getUpdateBy());
			xrulesFuVerM.setPhoneVerStatus(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);		
			
			xrulesFuVerM.setCreateDate(new Timestamp((new Date()).getTime()));
			xrulesFuVerM.setUpdateDate(new Timestamp((new Date()).getTime()));
			
		xrulesFuVerM.setPhoneNo(mobileNo);
		
		insertFollowDetail(xrulesFuVerM);
		
		xrulesVerM.add(xrulesFuVerM);	
	}
	
	private void insertFollowDetail(PLXRulesFUVerificationDataM xrulesFuVerM) throws Exception{
		PLORIGDAOFactory.getPLOrigEmailSMSDAO().insertFollowDetail(xrulesFuVerM);
	}
	
	private String StringNullToSpecific(String input, String specific) {
		if (input != null) {
			return input;
		}
		return specific;
	}
}
