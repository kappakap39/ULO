package com.eaf.core.ulo.service.email;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
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

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.flp.util.FLPPasswordUtil;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.sun.mail.smtp.SMTPAddressFailedException;

public class EmailClient {
	private static transient Logger logger = Logger.getLogger(EmailClient.class);
	String EMAIL_USER_ID = InfBatchProperty.getInfBatchConfig("EMAIL_USER_ID");
	String EMAIL_PASSWORD = InfBatchProperty.getInfBatchConfig("EMAIL_PASSWORD");
	String EMAIL_AUTHENTICATED = InfBatchProperty.getInfBatchConfig("EMAIL_AUTHENTICATED");
	String EMAIL_HOST = InfBatchProperty.getInfBatchConfig("EMAIL_HOST");
	String EMAIL_PORT = InfBatchProperty.getInfBatchConfig("EMAIL_PORT");
	String EMAIL_CHARSET = InfBatchProperty.getInfBatchConfig("EMAIL_CHARSET");
	String EMAIL_CONTENT_TYPE = InfBatchProperty.getInfBatchConfig("EMAIL_CONTENT_TYPE");
	String EMAIL_SSL = InfBatchProperty.getInfBatchConfig("EMAIL_SSL");
	String EMAIL_CONTENT_TRANSFER_ENCODING = InfBatchProperty.getInfBatchConfig("EMAIL_CONTENT_TRANSFER_ENCODING");
	public EmailClient(){
		super();
		logger.debug("EMAIL_USER_ID : "+EMAIL_USER_ID);
		logger.debug("EMAIL_PASSWORD : "+EMAIL_PASSWORD);
		logger.debug("EMAIL_AUTHENTICATED : "+EMAIL_AUTHENTICATED);
		logger.debug("EMAIL_HOST : "+EMAIL_HOST);
		logger.debug("EMAIL_PORT : "+EMAIL_PORT);
		logger.debug("EMAIL_CHARSET : "+EMAIL_CHARSET);
		logger.debug("EMAIL_CONTENT_TYPE : "+EMAIL_CONTENT_TYPE);
		logger.debug("EMAIL_SSL : "+EMAIL_SSL);
		logger.debug("EMAIL_CONTENT_TRANSFER_ENCODING : "+EMAIL_CONTENT_TRANSFER_ENCODING);
	}
	public EmailResponse send(EmailRequest emailRequest){
		EmailResponse emailResponse = new EmailResponse();
		//logger.debug("emailRequest >> "+emailRequest);
        try{
        	Properties props = getProperties();
        	Session session = Session.getInstance(props,null);
	    	MimeMessage mimeMessage = getMimeMessage(session,emailRequest);
	    	Transport transport = session.getTransport();
	    	logger.debug("EMAIL_AUTHENTICATED : "+EMAIL_AUTHENTICATED);
	    	if("Y".equals(EMAIL_AUTHENTICATED)){
	    		transport.connect(EMAIL_HOST,Integer.parseInt(EMAIL_PORT),EMAIL_USER_ID,FLPPasswordUtil.decrypt(EMAIL_PASSWORD));
	    	}else{
	    		transport.connect();
	    	}
	    	transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
	    	transport.close();
	    	emailResponse.setStatusCode(EmailResponse.Status.SUCCESS);
        }catch(SMTPAddressFailedException e){
        	logger.fatal("ERROR ",e);
			String invalidEmail = getEmailAddress(e.getInvalidAddresses());
			String sendFailEmail = getEmailAddress(e.getValidUnsentAddresses());
			emailResponse.setStatusCode(EmailResponse.Status.WARNING);
			emailResponse.setStatusDesc(e.getLocalizedMessage());
			emailResponse.setInvalidEmail(invalidEmail);
			emailResponse.setSendFailEmail(sendFailEmail);
		}catch(SendFailedException e){
        	logger.fatal("ERROR ",e);
			String invalidEmail = getEmailAddress(e.getInvalidAddresses());
			String sendFailEmail = getEmailAddress(e.getValidUnsentAddresses());
			emailResponse.setStatusCode(EmailResponse.Status.WARNING);
			emailResponse.setStatusDesc(e.getLocalizedMessage());
			emailResponse.setInvalidEmail(invalidEmail);
			emailResponse.setSendFailEmail(sendFailEmail);
        }catch(Exception e){
        	logger.fatal("ERROR",e);
        	emailResponse.setStatusCode(EmailResponse.Status.FAIL);
        	emailResponse.setStatusDesc(e.getLocalizedMessage());
        }
        return emailResponse;
	}

	private Properties getProperties(){
        Properties props = new Properties();
        logger.debug("EMAIL_HOST >> "+EMAIL_HOST);
        logger.debug("EMAIL_PORT >> "+EMAIL_PORT);
//        	props.setProperty("mail.mime.charset",EMAIL_CHARSET);
        	props.setProperty("mail.mime.encodefilename","true");
            props.setProperty("mail.transport.protocol", "smtp");
            if("Y".equals(EMAIL_SSL)){
            	props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            	props.setProperty("mail.smtp.socketFactory.fallback", "false");
            }
            props.setProperty("mail.host", EMAIL_HOST);
            props.setProperty("mail.port", EMAIL_PORT);
		return props;        
	}
	private MimeMessage getMimeMessage(Session session,EmailRequest email) throws Exception{
		MimeMessage message = new MimeMessage(session); 
		String from = email.getFrom();
		String[] to = email.getTo();
		String[] ccTo = email.getCcTo();
		String subject = email.getSubject();
		String content = email.getContent();
		String contentType = email.getContentType();
		Timestamp sendDate = email.getSentDate();			
		//logger.debug(email);
    	message.setFrom(new InternetAddress(from)); 
    	if(!InfBatchUtil.empty(email.getFileName())){
    		message.setFileName(email.getFileName());
    	}
    	if(!Util.empty(EMAIL_CONTENT_TRANSFER_ENCODING)){
    		message.setHeader("Content-Transfer-Encoding",EMAIL_CONTENT_TRANSFER_ENCODING);
    	}
    	message.addRecipients(Message.RecipientType.TO,getRecipients(to));
  	    message.addRecipients(Message.RecipientType.CC,getRecipients(ccTo));    	 
  	    message.setSentDate(sendDate);
    	message.setSubject(subject,EMAIL_CHARSET);
    	if(InfBatchUtil.empty(contentType)){
    		contentType = EMAIL_CONTENT_TYPE;
    	}
    	message.setContent(content,contentType);
    	ArrayList<String> attachments = email.getAttachments();
    	if(!InfBatchUtil.empty(attachments)){
    		message.setContent(getMultipart(content, contentType, attachments));
    	}
		return message;
	}
	private String getEmailAddress(Address[] address){
		if(address==null || address.length==0){
			return null;
		}
		StringBuilder email = new StringBuilder();
		int size = address.length;
		for(int i=0;i<size;i++){
			email.append(address[i].toString());
			if(i != (size-1) ){
				email.append(",");
			}
		}
		return email.toString();
	}
	private Multipart getMultipart(String content,String contentType,ArrayList<String> attachments) throws Exception{
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		messageBodyPart.setContent(content,contentType);
        multipart.addBodyPart(messageBodyPart);
        for (String filePath : attachments) {
            MimeBodyPart attachPart = new MimeBodyPart();	
        	logger.debug("filePath >> "+filePath);
            try {
                attachPart.attachFile(filePath);
            }catch(IOException e){
            	logger.fatal("ERROR",e);
            } 
            multipart.addBodyPart(attachPart);
        }
		return multipart;
	}
	public Address[] getRecipients(String[] email) throws AddressException{
		if(email==null || email.length == 0){
			return null;
		}		
		Address[] emailAddress  = new Address[email.length];
		for(int i=0; i<email.length; i++){
			emailAddress[i] = new InternetAddress(email[i]);
		}
		logger.debug("emailAddress >> "+emailAddress.toString());
		return emailAddress;
	}
}
