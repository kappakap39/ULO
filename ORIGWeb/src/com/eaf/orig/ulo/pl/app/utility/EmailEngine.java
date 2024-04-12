package com.eaf.orig.ulo.pl.app.utility;

import java.io.File;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

//import com.eaf.orig.shared.constant.EmailConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGContactManager;
import com.eaf.orig.shared.model.EmailM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.shared.model.ServiceEmailSMSQLogM;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class EmailEngine {
	
    static Logger logger = Logger.getLogger(EmailEngine.class);
    
    public void send(PLOrigContactDataM origContactM){
    	ServiceEmailSMSQLogM logM = new ServiceEmailSMSQLogM();
		logM.setContactLogID(origContactM.getContactLogId());
		logM.setServiceType(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL);
    	try{
    		EmailM mailM = origContactM.getEmail();
	    	ServiceDataM serviceM = new ServiceDataM();
	    		serviceM.setEmailHost(ORIGConfig.EMAIL_HOST);
	    		serviceM.setEmailPort(ORIGConfig.EMAIL_PORT);
	    	
	    	logger.debug(">>> EmailHost> " + serviceM.getEmailHost());
	    	logger.debug(">>> EmailPort> " + serviceM.getEmailPort());
	    	
	    	serviceM.setEmailSender(mailM.getFrom());
	
	    	ORIGContactManager contactManager = PLORIGEJBService.getORIGContactManager();
	    	String result = OrigConstant.ORIG_RESULT_FAIL;
	    	
    		if(mailM.getFileDir() == null){
    			result = this.sendEmail(serviceM, mailM);
    		}else{
    			result = this.sendEmailWithAttachment(serviceM, mailM);
    		}
    		//insert log
    		if(OrigConstant.SUCCESS.equals(result)){
    			logM.setLogType(OrigConstant.EmailSMSQLog.LOG_TYPE_SUCCESS);
    			logM.setLogDesc(OrigConstant.EmailSMSQLog.SEND_MAIL_SUCCESS);
    			contactManager.createServiceEmailSMSQLog(logM);
    			if(origContactM.getApplicationRecordId() != null && !"".equals(origContactM.getApplicationRecordId())){
        			origContactM.setSendStatus(OrigConstant.FLAG_Y);
        			contactManager.createOrigContractLog(origContactM);
    			}
    		}else{
    			logM.setLogType(OrigConstant.EmailSMSQLog.LOG_TYPE_ERROR);
    			logM.setLogDesc(result);
    			contactManager.createServiceEmailSMSQLog(logM);
    			
    			origContactM.setSendStatus(OrigConstant.FLAG_N);
    			contactManager.createOrigContractLog(origContactM);
    		}
    	}catch(Exception e){
    		logger.fatal("ERROR : ",e);   
    		try{
    			ORIGContactManager contactManager = PLORIGEJBService.getORIGContactManager();
	    		logM.setLogType(OrigConstant.EmailSMSQLog.LOG_TYPE_ERROR);
				logM.setLogDesc(e.getMessage());
				contactManager.createServiceEmailSMSQLog(logM);				
				origContactM.setSendStatus(OrigConstant.FLAG_N);
				contactManager.createOrigContractLog(origContactM);
    		}catch(Exception ex){
    			logger.fatal("ERROR : ",ex);   
    		}
    	}	
    }
    
    public String sendEmail(ServiceDataM serviceDataM, EmailM mailM){
		String result = "";
		
		String emailHost = serviceDataM.getEmailHost();
		String emailPort = serviceDataM.getEmailPort();
		
		//logger.debug("[sendEmail....]"+printEmailM(mailM));
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.port", emailPort);
        //props.put(EmailConstant.SMTP_PROPERTY, smtpHost);
	    String unsentAddresses = null;
    	String invalidAddresses = null;
        
        try{
            Session session = Session.getDefaultInstance(props, null);
	        javax.mail.Message message = createMessage(session,mailM, serviceDataM);
	        //Calendar cal = new GregorianCalendar();
        	//message.setSentDate(cal.getTime());
	    
	    	Transport transport = session.getTransport();
	        //Transport transport = session.getTransport(EmailConstant.SMTP_PROTOCOL);
	        transport.connect();
	        //transport.connect(smtpHost, EmailConstant.userName, EmailConstant.password);
//	        logger.debug("[sendEmail] ....userName :"+EmailConstant.userName+" Password :"+EmailConstant.password+" is connected :"+transport.isConnected());

			/*--------- print email to system.out----*
			message.writeTo(System.out);
			/*---------*/
	        transport.sendMessage(message, message.getAllRecipients());      
	        result = OrigConstant.SUCCESS;  
	        transport.close();
	        return result;
        }catch(SendFailedException ex){
        	logger.fatal("ERROR : ",ex);   
			Address[] invalidAddress = ex.getInvalidAddresses();
			Address[] sendFailAddress = ex.getValidUnsentAddresses();


			unsentAddresses = convertInternetAddress(sendFailAddress);
			invalidAddresses = convertInternetAddress(invalidAddress);

			logger.debug("[sendEmail] ..... invalid Address :"+invalidAddresses);
			logger.debug("[sendEmail] ..... sendFail Address :"+unsentAddresses);
			
			result = OrigConstant.FAIL + ":"+ ex.getMessage();
			return result;
			//createEmailInbox(getEmail(),unsentAddresses,invalidAddresses);
        }catch(Exception ex){
        	//ex.printStackTrace();
        	logger.fatal("ERROR : ",ex);   
        	result = OrigConstant.FAIL + ":"+ ex.getMessage();
			return result;
        	//throw new EmailMException("Send mail error "+ex.getMessage());
        }
    }
    
    public String sendEmailWithAttachment(ServiceDataM serviceDataM, EmailM mailM){
		String result = "";
		String emailHost = serviceDataM.getEmailHost();
		String emailPort = serviceDataM.getEmailPort();
		
		//logger.debug("[sendEmailWithAttachment....]"+printEmailM(mailM));
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.port", emailPort);
        //props.put(EmailConstant.SMTP_PROPERTY, smtpHost);

	    String unsentAddresses = null;
    	String invalidAddresses = null;
        try{
            Session session = Session.getDefaultInstance(props, null);
            javax.mail.Message message = createMessageMultipart(session, mailM, serviceDataM);
	    
	    	Transport transport = session.getTransport();
	        //Transport transport = session.getTransport(EmailConstant.SMTP_PROTOCOL);
	        transport.connect();
	        //transport.connect(smtpHost, EmailConstant.userName, EmailConstant.password);
//	        logger.debug("[sendEmailWithAttachment ....]userName :"+EmailConstant.userName+" Password :"+EmailConstant.password+" is connected :"+transport.isConnected());
	        
	        transport.sendMessage(message, message.getAllRecipients());      
	        result = OrigConstant.SUCCESS;  
	        transport.close();
	        return result;
        }catch(SendFailedException ex){
			//ex.printStackTrace();
        	logger.fatal("ERROR : ",ex);   
			Address[] invalidAddress = ex.getInvalidAddresses();
			Address[] sendFailAddress = ex.getValidUnsentAddresses();


			unsentAddresses = convertInternetAddress(sendFailAddress);
			invalidAddresses = convertInternetAddress(invalidAddress);

			logger.debug("[sendEmailWithAttachment .....] invalid Address :"+invalidAddresses);
			logger.debug("[sendEmailWithAttachment .....] sendFail Address :"+unsentAddresses);
			result = OrigConstant.SUCCESS + ":"+ ex.getMessage();
			 return result;
			//createEmailInbox(getEmail(),unsentAddresses,invalidAddresses);
        }catch(Exception ex){
        	logger.fatal("ERROR : ",ex);   
        	result = OrigConstant.SUCCESS + ":"+ ex.getMessage();
			return result;
        }
	}
    
    public javax.mail.Message createMessageMultipart(Session session,EmailM mail, ServiceDataM serviceDataM) throws Exception{
        
        String senderEmail = serviceDataM.getEmailSender();
		String fileDir = mail.getFileDir();
        logger.debug("@@@@@ File Directory:"+fileDir);
        MimeMessage message = new MimeMessage(session);
 	    
 	    message.addRecipients(javax.mail.Message.RecipientType.TO, getEmailAddress(mail.getTo()));
 	    message.addRecipients(javax.mail.Message.RecipientType.CC, getEmailAddress(mail.getCcTo()));
		message.setFrom(new InternetAddress(senderEmail));
	    message.setSubject(mail.getSubject(),"UTF-8");
	    //message.setText(mail.getContent(),"UTF-8");
	    //message.setContent(mail.getContent(),"text/html;charset=windows-874");
	    message.setSentDate(mail.getSentDate());
	    
	    BodyPart messageBodyPart = new MimeBodyPart(); 
		//messageBodyPart.setText(mail.getContent()); 
		messageBodyPart.setContent(mail.getContent(), "text/html;charset=windows-874");
	    Multipart multipart = new MimeMultipart(); 
		multipart.addBodyPart(messageBodyPart); 
		messageBodyPart = new MimeBodyPart(); 
		DataSource source = new FileDataSource(fileDir); 
		messageBodyPart.setDataHandler(new DataHandler(source)); 
		messageBodyPart.setFileName(fileDir.substring(fileDir.lastIndexOf(File.separator)+1,fileDir.length())); 
		multipart.addBodyPart(messageBodyPart); 
		message.setContent(multipart); 
 		
		return message;
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
    
    public javax.mail.Message createMessage(Session session,EmailM mail, ServiceDataM serviceDataM) throws Exception{
        
        String senderEmail = serviceDataM.getEmailSender();
        
        MimeMessage message = new MimeMessage(session);
 	    
 	    message.addRecipients(javax.mail.Message.RecipientType.TO, SendEmail.getEmailAddress(mail.getTo()));
 	    message.addRecipients(javax.mail.Message.RecipientType.CC, SendEmail.getEmailAddress(mail.getCcTo()));
 	    if(mail.getFromName() != null && !"".equals(mail.getFromName())){
 	    	message.setFrom(new InternetAddress(senderEmail,mail.getFromName()));
 	    }else{
 	    	message.setFrom(new InternetAddress(senderEmail));
 	    }
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
}
