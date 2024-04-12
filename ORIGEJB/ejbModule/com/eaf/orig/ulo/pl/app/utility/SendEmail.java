/*
 * Created on Dec 8, 2007
 * Created by Administrator
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.ulo.pl.app.utility;

import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.shared.constant.EmailConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.model.EmailM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.wf.shared.ORIGWFConstant;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterGenParamDAO;

/**
 * @author Administrator
 *
 * Type: SendEmail
 */
public class SendEmail extends Thread {
	EmailM emailM;
	private String unsentAddresses;
	private String invalidAddresses;

	public static Logger logger = Logger.getLogger(SendEmail.class);

	public void setEmail(EmailM emailM){
		this.emailM  = emailM;
	}
	public EmailM getEmail(){
		return emailM;
	}
	
	public void run(ServiceDataM serviceDataM) {
		try{
			sendEmail(serviceDataM);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String sendEmail(ServiceDataM serviceDataM){
		String result = "";
		if(getEmail() == null){
			//throw new EmailMException("Error - SendEmail.sendEmail().....EmailM doesn't exists");
		}
		
		String emailHost = serviceDataM.getEmailHost();
		String emailPort = serviceDataM.getEmailPort();
		
		logger.debug("[SendEmail.sendEmail()....]"+printEmailM(getEmail()));
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.port", emailPort);
        //props.put(EmailConstant.SMTP_PROPERTY, smtpHost);
        
        try{
            Session session = Session.getDefaultInstance(props, null);
	        Message message = createMessage(session,getEmail(), serviceDataM);
	        //Calendar cal = new GregorianCalendar();
        	//message.setSentDate(cal.getTime());
    	    unsentAddresses = null;
	    	invalidAddresses = null;
	    
	    	Transport transport = session.getTransport();
	        //Transport transport = session.getTransport(EmailConstant.SMTP_PROTOCOL);
	        transport.connect();
	        //transport.connect(smtpHost, EmailConstant.userName, EmailConstant.password);
	        logger.debug("[SendEmail.sendEmail()] ....userName :"+EmailConstant.userName+" Password :"+EmailConstant.password+" is connected :"+transport.isConnected());

/*--------- print email to system.out----*
message.writeTo(System.out);
/*---------*/
	        transport.sendMessage(message, message.getAllRecipients());      
	        result = "success";  
	        transport.close();
	        return result;
        }catch(SendFailedException ex){
			ex.printStackTrace();
			Address[] invalidAddress = ex.getInvalidAddresses();
			Address[] sendFailAddress = ex.getValidUnsentAddresses();


			unsentAddresses = convertInternetAddress(sendFailAddress);
			invalidAddresses = convertInternetAddress(invalidAddress);

			logger.debug("[SendEmail.sendEmail()] ..... invalid Address :"+invalidAddresses);
			logger.debug("[SendEmail.sendEmail()] ..... sendFail Address :"+unsentAddresses);
			result = "failed";
			 return result;
			//createEmailInbox(getEmail(),unsentAddresses,invalidAddresses);
        }catch(Exception ex){
        	//ex.printStackTrace();
        	logger.debug("Error >> ", ex);
        	result = "failed";
			 return result;
        	//throw new EmailMException("Send mail error "+ex.getMessage());
        }
	}
	
	public String sendEmailSecurity(ServiceDataM serviceDataM, String userName, String password){
		String result = "";
		if(getEmail() == null){
			//throw new EmailMException("Error - SendEmail.sendEmail().....EmailM doesn't exists");
		}
		
		String emailHost = serviceDataM.getEmailHost();
		String emailPort = serviceDataM.getEmailPort();
		
		logger.debug("[SendEmail.sendEmail()....]"+printEmailM(getEmail()));
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.port", emailPort);
        //props.put(EmailConstant.SMTP_PROPERTY, smtpHost);
        
        try{
            Session session = Session.getDefaultInstance(props, null);
	        Message message = createMessage(session,getEmail(), serviceDataM);
	        //Calendar cal = new GregorianCalendar();
        	//message.setSentDate(cal.getTime());
    	    unsentAddresses = null;
	    	invalidAddresses = null;
	    
	    	Transport transport = session.getTransport();
	        //Transport transport = session.getTransport(EmailConstant.SMTP_PROTOCOL);
	        transport.connect();
	        transport.connect(emailHost, userName, password);
	        logger.debug("[SendEmail.sendEmail()] ....userName :"+EmailConstant.userName+" Password :"+EmailConstant.password+" is connected :"+transport.isConnected());

/*--------- print email to system.out----*
message.writeTo(System.out);
/*---------*/
	        transport.sendMessage(message, message.getAllRecipients());      
	        result = "success";  
	        transport.close();
	        return result;
        }catch(SendFailedException ex){
			ex.printStackTrace();
			Address[] invalidAddress = ex.getInvalidAddresses();
			Address[] sendFailAddress = ex.getValidUnsentAddresses();


			unsentAddresses = convertInternetAddress(sendFailAddress);
			invalidAddresses = convertInternetAddress(invalidAddress);

			logger.debug("[SendEmail.sendEmail()] ..... invalid Address :"+invalidAddresses);
			logger.debug("[SendEmail.sendEmail()] ..... sendFail Address :"+unsentAddresses);
			result = "failed";
			 return result;
			//createEmailInbox(getEmail(),unsentAddresses,invalidAddresses);
        }catch(Exception ex){
        	//ex.printStackTrace();
        	logger.debug("Error >> ", ex);
        	result = "failed";
			 return result;
        	//throw new EmailMException("Send mail error "+ex.getMessage());
        }
	}

	private Message createMessage(Session session,EmailM mail, ServiceDataM serviceDataM) throws Exception{
        
        String senderEmail = serviceDataM.getEmailSender();
        
        MimeMessage message = new MimeMessage(session);
 	    
 	    message.addRecipients(Message.RecipientType.TO, SendEmail.getEmailAddress(mail.getTo()));
 	    message.addRecipients(Message.RecipientType.CC, SendEmail.getEmailAddress(mail.getCcTo()));
		message.setFrom(new InternetAddress(senderEmail));
	    message.setSubject(mail.getSubject(),"UTF-8");
	    //message.setText(mail.getContent(),"UTF-8");
	    message.setContent(mail.getContent(),"text/html;charset=windows-874");
	    message.setSentDate(mail.getSentDate());
 		
		return message;
	}

	public static Address[] getEmailAddress(String emailString) throws AddressException{
		if(emailString==null || emailString.length()==0){
			return null;
		}
		
		String delim=",";
		
		StringTokenizer token = new StringTokenizer(emailString,delim);
		Address[] address  = new Address[token.countTokens()];
		int i=0;
		while(token.hasMoreTokens()){
				address[i++] = new InternetAddress(token.nextToken().trim());
		}
		
		return address;
	}

	private String convertInternetAddress(Address[] address){
		if(address==null || address.length==0){
			return null;
		}
		String str=null;
		StringBuffer buffer = new StringBuffer();
		int size = address.length;
		for(int i=0;i<size;i++){
			buffer.append(address[i].toString());
			if(i != (size-1) ){ // check for append "," to separate email address
				buffer.append(",");
			}
		}
		if(buffer.capacity() >0){
			str = buffer.toString();
		}
		return str;
	}


	public String getUnsentAddresses(){
		return unsentAddresses;
	}
	
	public String getInvalidAddresses(){
		return invalidAddresses;
	}

//	public void createEmailInbox(EmailM mail, String unsentAddr,String invalidAddr) throws EmailMException{
//		ManageEmailInbox emailInbox = new ManageEmailInbox();
//		emailInbox.createEmailInbox(mail,unsentAddr,invalidAddr);
//	}


	public static String printEmailM(EmailM email){
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n------------------------------------------- email "+(email == null?" is null":""));
		buffer.append("\n EmailID :"+email.getEmailID());
		buffer.append("\n To :"+email.getTo());
		buffer.append("\n From :"+email.getFrom());
		buffer.append("\n Created date :"+email.getCreateDate());
		buffer.append("\n Subject :"+email.getSubject());
		buffer.append("\n Content :"+email.getContent());
		buffer.append("\n ResentFlag :"+email.getResentFlag());
		buffer.append("\n ToInbox :"+email.getToInbox());
		return buffer.toString();
	}
	
	
	public String getMessageForXCMR(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_XCMR, OrigConstant.BUSINESS_CLASS_AL);
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
														.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_XCMR, OrigConstant.BUSINESS_CLASS_AL);
			
			String decision = appForm.getXcmrDecision();
			String content = generalParamM.getParamValue();
			//content = content.replaceAll(OrigConstant.P_APPNO, appForm.getApplicationNo());
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME,  personalInfoDataM.getThaiFirstName());
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME, personalInfoDataM.getThaiLastName());
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION,decision);
			content = content.replaceAll(OrigConstant.MailSmsParam.USER, appForm.getUpdateBy());
			buffer.append("<html><body>");
			buffer.append("<table><TR><TD>");
			buffer.append(content);
			buffer.append("<BR>");
			//buffer.append("CMR : "+cmrOwner);
			buffer.append("</TD></TR></table>");
			buffer.append("</body></html>");
		}catch(Exception e){
			logger.error("Error >>> ", e);
		}
		
		return buffer.toString();
	}
	
	public String getMessageForUW(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			#SeptemWi
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);
//			
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
												.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);
			
			String content = generalParamM.getParamValue();
			//content = content.replaceAll(OrigConstant.P_APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			String uwDecision = appForm.getUwDecision();
			String decision = uwDecision;
			if(ORIGWFConstant.ApplicationDecision.UW_PENDING.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.PENDING;
			}else if(ORIGWFConstant.ApplicationDecision.APPROVE.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.APPROVED;
			}else if(ORIGWFConstant.ApplicationDecision.CANCEL.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.CANCELLED;
			}else if(ORIGWFConstant.ApplicationDecision.REJECT.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.REJECTED;
			}else if(ORIGWFConstant.ApplicationDecision.ESCALATE.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.ESCALATED;
			}else if(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION.equals(decision)){
				decision = "Sent to Exception CMR";
			}
			
			//content = content.replaceAll(OrigConstant.P_DECISION, "<b>\""+decision+"\"</b>");
			//content = content.replaceAll(OrigConstant.P_CA, "<b>"+appForm.getUpdateBy()+"</b>");
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME,  personalInfoDataM.getThaiFirstName());
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME,  personalInfoDataM.getThaiLastName());
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION,  decision );
			content = content.replaceAll(OrigConstant.MailSmsParam.USER,  appForm.getUpdateBy() );
			buffer.append("<html><body>");
			buffer.append("<table><tr><td>");
			buffer.append(content);
			buffer.append("<BR>");
			//buffer.append("CMR : "+cmrOwner);
			buffer.append("</td></tr></table>");
			buffer.append("</body></html>");
		}catch(Exception e){
			logger.error("Error >>> ", e);
		}
		
		return buffer.toString();
	}
	
	public String getContent(ApplicationDataM appForm, String  owner,String sendRole){
		if(OrigConstant.ROLE_XCMR.equals(sendRole)){
			return getMessageForXCMR(appForm, owner);
			//dataM.setContent(new String(getMessageForUW(appForm).getBytes("ISO8859_1"),"UTF-8"));
		}else if (OrigConstant.ROLE_UW.equals(sendRole)){
			return getMessageForUW(appForm, owner);
			//dataM.setContent(new String(getMessageForXCMR(appForm).getBytes("ISO8859_1"),"UTF-8"));
		}else if (OrigConstant.ROLE_XUW.equals(sendRole)){
		    return getMessageForXUW(appForm, owner);
		}else if (OrigConstant.ROLE_DE.equals(sendRole)){
		    return getMessageForDE(appForm, owner);
		}else{
		 return "";    
		}		
	}
	public String getMessageForXUW(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);			
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
												.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);
			
			String content = generalParamM.getParamValue();			
			String xuwDecision = appForm.getXuwDecision();
			String decision = xuwDecision;
			if(OrigConstant.ApplicationDecision.OVERIDED_POLICY.equals(decision)){
				decision = "UW Exception Overided Policy";
			}else if(OrigConstant.ApplicationDecision.NOT_OVERIDED_POLICY.equals(decision)){			 
			    decision = "UW Exception not Overided Policy";
			}
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME, personalInfoDataM.getThaiFirstName() );
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME,  personalInfoDataM.getThaiLastName());
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION,  decision );
			content = content.replaceAll(OrigConstant.MailSmsParam.USER,  appForm.getUpdateBy() );			
			buffer.append("<html><body>");
			buffer.append("<table><tr><td>");
			buffer.append(content);
			buffer.append("<BR>");
			//buffer.append("CMR : "+cmrOwner);
			buffer.append("</td></tr></table>");
			buffer.append("</body></html>");
		}catch(Exception e){
			logger.error("Error >>> ", e);
		}
		
		return buffer.toString();
	}
	public PersonalInfoDataM getPersonalInfoApplicaintType(ApplicationDataM applicationDataM ) {
        Vector personalInfoVect = applicationDataM.getPersonalInfoVect();
        PersonalInfoDataM personalInfoDataM = null;
        if (personalInfoVect != null && personalInfoVect.size() > 0) {
            for (int i = 0; i < personalInfoVect.size(); i++) {
                personalInfoDataM = (PersonalInfoDataM) personalInfoVect.get(i);
                if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())) {
                    return personalInfoDataM;
                } else {
                    personalInfoDataM = null;
                }
            }
        }
        return personalInfoDataM;
    }
	public String getSubject(ApplicationDataM appForm, String  owner,String sendRole){
		if(OrigConstant.ROLE_XCMR.equals(sendRole)){
			return getSubjectForXCMR(appForm, owner);
			//dataM.setContent(new String(getMessageForUW(appForm).getBytes("ISO8859_1"),"UTF-8"));
		}else if (OrigConstant.ROLE_UW.equals(sendRole)){
			return getSubjectForUW(appForm, owner);
			//dataM.setContent(new String(getMessageForXCMR(appForm).getBytes("ISO8859_1"),"UTF-8"));
		}else if (OrigConstant.ROLE_XUW.equals(sendRole)){
		    return getSubjectForXUW(appForm, owner);
		}else if (OrigConstant.ROLE_DE.equals(sendRole)){
		    return getSubjectForDE(appForm, owner);
		}else{
		 return "";    
		}		
	}
	
	public String getSubjectForXUW(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);			
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
														.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);
			
			String content = generalParamM.getParamValue();			
			String xuwDecision = appForm.getXuwDecision();
			String decision = xuwDecision;
			if(OrigConstant.ApplicationDecision.OVERIDED_POLICY.equals(decision)){
				decision = "UW Exception Overided Policy";
			}else if(OrigConstant.ApplicationDecision.NOT_OVERIDED_POLICY.equals(decision)){			 
			    decision = "UW Exception not Overided Policy";
			}
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, appForm.getApplicationNo());
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME, personalInfoDataM.getThaiFirstName());
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME, personalInfoDataM.getThaiLastName());
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION, decision);
			content = content.replaceAll(OrigConstant.MailSmsParam.USER, appForm.getUpdateBy());			
			//buffer.append("<html><body>");
			//buffer.append("<table><tr><td>");
			buffer.append(content);
			//buffer.append("<BR>");
			//buffer.append("CMR : "+cmrOwner);
			////buffer.append("</td></tr></table>");
			//buffer.append("</body></html>");
		}catch(Exception e){
			logger.error("Error >>> ", e);
		}
		
		return buffer.toString();
	}
	public String getSubjectForXCMR(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_XCMR, OrigConstant.BUSINESS_CLASS_AL);
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
												.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_XCMR, OrigConstant.BUSINESS_CLASS_AL);
			
			String decision = appForm.getXcmrDecision();
			String content = generalParamM.getParamValue();
			//content = content.replaceAll(OrigConstant.P_APPNO, appForm.getApplicationNo());
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO,  appForm.getApplicationNo());
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME,  personalInfoDataM.getThaiFirstName());
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME,  personalInfoDataM.getThaiLastName());
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION,  decision);
			content = content.replaceAll(OrigConstant.MailSmsParam.USER, appForm.getUpdateBy());
			//buffer.append("<html><body>");
			//buffer.append("<table><TR><TD>");
			buffer.append(content);
			//buffer.append("<BR>");
			//buffer.append("CMR : "+cmrOwner);
			//buffer.append("</TD></TR></table>");
			//buffer.append("</body></html>");
		}catch(Exception e){
			logger.error("Error >>> ", e);
		}
		
		return buffer.toString();
	}
	
	public String getSubjectForUW(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
													.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);
			
			
			String content = generalParamM.getParamValue();
			//content = content.replaceAll(OrigConstant.P_APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			String uwDecision = appForm.getUwDecision();
			String decision = uwDecision;
			if(ORIGWFConstant.ApplicationDecision.UW_PENDING.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.PENDING;
			}else if(ORIGWFConstant.ApplicationDecision.APPROVE.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.APPROVED;
			}else if(ORIGWFConstant.ApplicationDecision.CANCEL.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.CANCELLED;
			}else if(ORIGWFConstant.ApplicationDecision.REJECT.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.REJECTED;
			}else if(ORIGWFConstant.ApplicationDecision.ESCALATE.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.ESCALATED;
			}else if(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION.equals(decision)){
				decision = "Sent to Exception CMR";
			}
			
			//content = content.replaceAll(OrigConstant.P_DECISION, "<b>\""+decision+"\"</b>");
			//content = content.replaceAll(OrigConstant.P_CA, "<b>"+appForm.getUpdateBy()+"</b>");
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, appForm.getApplicationNo());
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME,personalInfoDataM.getThaiFirstName());
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME, personalInfoDataM.getThaiLastName());
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION,decision);
			content = content.replaceAll(OrigConstant.MailSmsParam.USER,  appForm.getUpdateBy());
			//buffer.append("<html><body>");
			//buffer.append("<table><tr><td>");
			buffer.append(content);
			//buffer.append("<BR>");
			//buffer.append("CMR : "+cmrOwner);
			//buffer.append("</td></tr></table>");
			//buffer.append("</body></html>");
		}catch(Exception e){
			logger.error("Error >>> ", e);
		}
		
		return buffer.toString();
	}
	public String getMessageForDE(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_DE, OrigConstant.BUSINESS_CLASS_AL);
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
												.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_DE, OrigConstant.BUSINESS_CLASS_AL);
			
			
			String content = generalParamM.getParamValue();
			//content = content.replaceAll(OrigConstant.P_APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			String uwDecision = appForm.getUwDecision();
			String decision = "Document not completed. Please follow up.<br>";
			
			Vector vDocCheckList = appForm.getDocumentCheckListDataM();
    		int count = 0;
    		if(vDocCheckList!=null && vDocCheckList.size()>0){
    		    for(int i=0; i<vDocCheckList.size(); i++){
    		        DocumentCheckListDataM docCheckList= (DocumentCheckListDataM)vDocCheckList.elementAt(i);
    		        if(docCheckList!=null && "C".equals(docCheckList.getRequire()) 
    		                && !"Y".equals(docCheckList.getWaive()) 
    		                && !"Y".equals(docCheckList.getReceive())){
    		            logger.debug("document checklist that not completed = "+docCheckList.getDocId()+" >> "+docCheckList.getDocTypeDesc());
    		            decision = decision + ++count + ". " + docCheckList.getDocTypeDesc()+ "<br>";
    		        }
    		    }
    		}
			
			//content = content.replaceAll(OrigConstant.P_DECISION, "<b>\""+decision+"\"</b>");
			//content = content.replaceAll(OrigConstant.P_CA, "<b>"+appForm.getUpdateBy()+"</b>");
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME,  personalInfoDataM.getThaiFirstName());
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME,  personalInfoDataM.getThaiLastName());
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION,  decision );
			content = content.replaceAll(OrigConstant.MailSmsParam.USER,  appForm.getUpdateBy() );
			buffer.append("<html><body>");
			buffer.append("<table><tr><td>");
			buffer.append(content);
			buffer.append("<BR>");
			//buffer.append("CMR : "+cmrOwner);
			buffer.append("</td></tr></table>");
			buffer.append("</body></html>");
		}catch(Exception e){
			logger.error("Error >>> ", e);
		}
		
		return buffer.toString();
	}
	
	public String getSubjectForDE(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_SUBJECT_DE, OrigConstant.BUSINESS_CLASS_AL);			
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
													.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_SUBJECT_DE, OrigConstant.BUSINESS_CLASS_AL);
			
			String content = generalParamM.getParamValue();			
			String xuwDecision = appForm.getXuwDecision();
			
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, appForm.getApplicationNo());
			//buffer.append("<html><body>");
			//buffer.append("<table><tr><td>");
			buffer.append(content);
			//buffer.append("<BR>");
			//buffer.append("CMR : "+cmrOwner);
			////buffer.append("</td></tr></table>");
			//buffer.append("</body></html>");
		}catch(Exception e){
			logger.error("Error >>> ", e);
		}
		
		return buffer.toString();
	}
}
