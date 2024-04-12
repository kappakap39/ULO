package com.eaf.orig.ulo.pl.app.utility;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.cache.properties.DocumentReasonProperties;
import com.eaf.orig.cache.properties.GeneralParamProperties;
import com.eaf.orig.cache.properties.MainSaleTypeProperties;
import com.eaf.orig.cache.properties.ProductProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.model.EmailM;
import com.eaf.orig.shared.model.EmailTemplateMasterM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.PLOrigEmailSMSDAO;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFUVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;


public class PLOrigEmailUtil {
	private static Logger logger = Logger.getLogger(PLOrigEmailSMSUtil.class);
	private static String special = "\\";
		
	public void sendEmail(EmailM emailM, PLApplicationDataM appM, String templateName) throws Exception{
		PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();
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
			queueConFac = (QueueConnectionFactory) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageMailCF"),QueueConnectionFactory.class);
			queue = (Queue) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageMailQueue"),Queue.class);
			queueCon = queueConFac.createQueueConnection();
			queueSess = queueCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queueSend = queueSess.createSender(queue);
			objMessage = queueSess.createObjectMessage();
			
			PLOrigContactDataM origContactM = new PLOrigContactDataM();
			origContactM.setContactLogId(emailSMSUtil.getContactLogID());
			origContactM.setApplicationRecordId(appM.getAppRecordID());
			origContactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL);
			origContactM.setCreateBy(appM.getUpdateBy());
			origContactM.setSendBy(appM.getUpdateBy());
			origContactM.setEmail(emailM);
			origContactM.setTemplateName(templateName);
			
			objMessage.setObject(origContactM);
			queueSend.send(objMessage);
			logger.debug("@@@@@ Already send mail to Queue");
		}catch (NamingException e){
			logger.error("##### Cannot send mail to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send mail to Queue :"+e.getMessage());
		}catch (JMSException e){
			logger.error("##### Cannot send mail to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send mail to Queue :"+e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
	}
	
	public void sendEmail(PLApplicationDataM appM, String templateName) throws Exception{
		PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();
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
			queueConFac = (QueueConnectionFactory) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageMailCF"),QueueConnectionFactory.class);
			queue = (Queue) javax.rmi.PortableRemoteObject.narrow(context.lookup("jms/messageMailQueue"),Queue.class);
			queueCon = queueConFac.createQueueConnection();
			queueSess = queueCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queueSend = queueSess.createSender(queue);
			objMessage = queueSess.createObjectMessage();
			
			PLOrigContactDataM origContactM = new PLOrigContactDataM();
			origContactM.setContactLogId(emailSMSUtil.getContactLogID());
			origContactM.setApplicationRecordId(appM.getAppRecordID());
			origContactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL);
			origContactM.setCreateBy(appM.getUpdateBy());
			origContactM.setSendBy(appM.getUpdateBy());
			origContactM.setTemplateName(templateName);
			origContactM.setAppM(this.reduceApplicationSize(appM));
			
			objMessage.setObject(origContactM);
			queueSend.send(objMessage);
			logger.debug("@@@@@ Already send mail to Queue");
		}catch (NamingException e){
			logger.error("##### Cannot send mail to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send mail to Queue :"+e.getMessage());
		}catch (JMSException e){
			logger.error("##### Cannot send mail to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send mail to Queue :"+e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
	}
	
	public void sendEmail(PLOrigContactDataM origContactM) throws Exception{
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
			
			objMessage.setObject(origContactM);
			queueSend.send(objMessage);
			logger.debug("@@@@@ Already send mail to Queue");
		}catch (NamingException e){
			logger.error("##### Cannot send mail to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send mail to Queue :"+e.getMessage());
		}catch (JMSException e){
			logger.error("##### Cannot send mail to Queue :"+e.getMessage());
			throw new Exception("##### Cannot send mail to Queue :"+e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
	}
	
	
	private String getCustomerName(PLApplicationDataM app){
		String customerName = "";
		if(app != null){
			PLPersonalInfoDataM applicantPersonM = app.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			if(applicantPersonM != null){
				customerName = 	applicantPersonM.getThaiFirstName() + " " + applicantPersonM.getThaiLastName();	
			}
		}
		return customerName;
	}
	
	private String getProductName(PLApplicationDataM app){
		String productName = "";
		if(app != null){
			ProductProperties productData = this.getProductDetails(app.getProduct());
			if(productData != null){
				productName = productData.getEnDesc();
			}
		}
		return productName;
	}
	
	private String getBranchName(PLApplicationDataM app){
		String branchName = "";
		if(app != null){
			PLSaleInfoDataM saleInofM= app.getSaleInfo();
			if(saleInofM != null){
				branchName = this.getBranchDesc(saleInofM.getSalesBranchCode(), null);
			}
		}
		return branchName;
	}
	
	private String getPOTelephone(){
		PLOrigEmailSMSUtil sendUtil = new PLOrigEmailSMSUtil();
		String telephoneNo = "";
		GeneralParamProperties poPhoneData = sendUtil.getGeneralParamDetails(OrigConstant.ParamCode.PARAM_PO_PHONE);
		if(poPhoneData != null){
			telephoneNo = poPhoneData.getParamvalue();
		}
		return telephoneNo;
	}
	
	public GeneralParamProperties getPOEmail(){
		PLOrigEmailSMSUtil sendUtil = new PLOrigEmailSMSUtil();
		GeneralParamProperties paramData = sendUtil.getGeneralParamDetails(OrigConstant.ParamCode.PARAM_PO_EMAIL);
		if(paramData != null){
			return paramData;
		}else{
			return new GeneralParamProperties();
		}
	}
	
	private String getBranchDesc(String branchCode, Locale localeID){
		if(localeID == null){
			localeID = new Locale("th_TH");
		}
		try{
			return PLORIGDAOFactory.getPLOrigSalesInfoDAO().getBranchDecription(branchCode, localeID);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private String getFollowDocDetails(PLApplicationDataM app){
		StringBuilder result = new StringBuilder("");
		result.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		if(app != null && app.getDocCheckListVect() != null && app.getDocCheckListVect().size() > 0){
			Vector<PLDocumentCheckListDataM> docVt = app.getDocCheckListVect();
			int cnt = 0;
			for(int i=0;i<docVt.size();i++){
				PLDocumentCheckListDataM docM = (PLDocumentCheckListDataM)docVt.get(i);
				if(docM != null && OrigConstant.DocumentStatus.TrackDoc.equals(docM.getReceive())){
					cnt ++;
					logger.debug("@@@@@ docM.getDocCode():"+docM.getDocCode());
					result.append("<tr><td width=\"4%\" valign=\"top\">"+cnt+"</td>");
					String documentName = "";
					CacheDataM docDataM = this.getDocumentDetails(docM.getDocCode());
					if(docDataM != null){
						documentName = DataFormatUtility.StringNullToSpecific(docDataM.getThDesc(),"");
					} 
					result.append("<td width=\"25%\" valign=\"top\">"+documentName+"</td>");
					if(docM.getDocCkReasonVect() != null && docM.getDocCkReasonVect().size() > 0){
						Vector<PLDocumentCheckListReasonDataM> reasonVt = docM.getDocCkReasonVect();
						StringBuilder reasonDetails = new StringBuilder("");
						for(int j=0;j<reasonVt.size();j++){
							PLDocumentCheckListReasonDataM reasonM = (PLDocumentCheckListReasonDataM)reasonVt.get(j);
							logger.debug("@@@@@ reasonM:"+reasonM.getDocCode()+"|"+reasonM.getDocReasonID()+"|"+reasonM.getIsDocReason());
							String docReasonDesc = "";
							DocumentReasonProperties docReasonDataM = this.getDocumentReasonDetails(reasonM.getDocCode(), reasonM.getDocReasonID());
							if(docReasonDataM != null){
								docReasonDesc = docReasonDataM.getReasonDesc();
							}
							reasonDetails.append(docReasonDesc + ", ");
						}
						String reasonDTStr = reasonDetails.toString();
						if(!"".equals(reasonDTStr)){
							reasonDTStr = reasonDTStr.substring(0,reasonDTStr.length()-2);
						}
						result.append("<td width=\"41%\" valign=\"top\">"+reasonDTStr+"</td>");
					}else{
						result.append("<td width=\"41%\" valign=\"top\"></td>");
					}
					result.append("<td width=\"30%\" valign=\"top\">"+DataFormatUtility.StringNullToSpecific(docM.getRemark(), "")+"</td></tr>");
				}
			}
		}
		result.append("</table>");
		return result.toString();
	}
	
	public EmailM prepareEmailMessage(PLApplicationDataM appM, EmailTemplateMasterM emailTemplateM) throws Exception{
		EmailM emailM = null;
		try{
			GeneralParamProperties poParamM = this.getPOEmail();
			if(OrigConstant.EmailSMS.EMAIL_FOLLOW_DOC_TO_BRANCH.equals(emailTemplateM.getTemplateName())){
				//call function for get FU name
				emailM = this.getSendFromByUser(appM.getUpdateBy());
				
				String sendTo = this.getEmailSendTo(appM, emailTemplateM.getTemplateName());
				logger.debug("@@@@@ sendTo:"+sendTo);
				String subject = this.getEMailSubject(emailTemplateM.getTemplateName(), emailTemplateM.getSubject(), appM, null);
				String content = this.getEMailContent(emailTemplateM.getTemplateName(), emailTemplateM.getContent(), appM, emailM);
				
				emailM.setFromName(poParamM.getParamvalue2());
				emailM.setFrom(poParamM.getParamvalue());
				emailM.setTo(sendTo);
				emailM.setContent(content);
				emailM.setSubject(subject);
			}else if(OrigConstant.EmailSMS.EMAIL_APPROVE_TO_BRANCH.equals(emailTemplateM.getTemplateName())){
				String sendTo = this.getEmailSendTo(appM, emailTemplateM.getTemplateName());
				logger.debug("@@@@@ sendTo:"+sendTo);
				String subject = this.getEMailSubject(emailTemplateM.getTemplateName(), emailTemplateM.getSubject(), appM, null);
				String content = this.getEMailContent(emailTemplateM.getTemplateName(), emailTemplateM.getContent(), appM, null);
				
				emailM = new EmailM();
				emailM.setTo(sendTo);
				emailM.setContent(content);
				emailM.setSubject(subject);
				emailM.setFromName(poParamM.getParamvalue2());
				emailM.setFrom(poParamM.getParamvalue());
			}else if(OrigConstant.EmailSMS.EMAIL_RECEIVE_NEW_APP_TO_BRANCH.equals(emailTemplateM.getTemplateName())){
				String sendTo = this.getEmailSendTo(appM, emailTemplateM.getTemplateName());
				logger.debug("@@@@@ sendTo:"+sendTo);
				String subject = this.getEMailSubject(emailTemplateM.getTemplateName(), emailTemplateM.getSubject(), appM, null);
				String content = this.getEMailContent(emailTemplateM.getTemplateName(), emailTemplateM.getContent(), appM, null);
				
				emailM = new EmailM();
				emailM.setTo(sendTo);
				emailM.setContent(content);
				emailM.setSubject(subject);
				emailM.setFromName(poParamM.getParamvalue2());
				emailM.setFrom(poParamM.getParamvalue());
			}else if(OrigConstant.EmailSMS.EMAIL_RECEIVE_FOLLOW_DOC_TO_BRANCH.equals(emailTemplateM.getTemplateName())){
				String sendTo = this.getEmailSendTo(appM, emailTemplateM.getTemplateName());
				logger.debug("@@@@@ sendTo:"+sendTo);
				String subject = this.getEMailSubject(emailTemplateM.getTemplateName(), emailTemplateM.getSubject(), appM, null);
				String content = this.getEMailContent(emailTemplateM.getTemplateName(), emailTemplateM.getContent(), appM, null);
				
				emailM = new EmailM();
				emailM.setTo(sendTo);
				emailM.setContent(content);
				emailM.setSubject(subject);
				emailM.setFromName(poParamM.getParamvalue2());
				emailM.setFrom(poParamM.getParamvalue());
			}
		
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}
		return emailM;
	}
	
	public boolean sendEmailWithEmailTemplate(String emailTemplate, PLApplicationDataM appM) throws Exception{
		logger.debug("@@@@@ Send Email >> emailTemplate:"+emailTemplate);
		boolean resultSuccess = false;
		try{
			this.sendEmail(appM, emailTemplate);
			resultSuccess = true;
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}
		return resultSuccess;
	}
	
	public boolean sendEmailFollowDocToBranch(String emailTemplate, PLApplicationDataM applicationM, String ccTo) throws Exception{
		logger.debug("EMAIL TemplateID >> "+emailTemplate);
		boolean resultSuccess = false;
		try{
			//load email template master
			PLOrigEmailSMSDAO emailSMSDAO = PLORIGDAOFactory.getPLOrigEmailSMSDAO();
			EmailTemplateMasterM emailTemplateM = emailSMSDAO.getEMailTemplateMaster(emailTemplate, applicationM.getBusinessClassId());
			if(emailTemplateM == null) emailTemplateM = new EmailTemplateMasterM();
			
			//if email template enable
			if(OrigConstant.FLAG_Y.equals(emailTemplateM.getEnable())){
				EmailM emailM = this.getSendFromByUser(applicationM.getUpdateBy());
				
				String sendTo = this.getEmailSendTo(applicationM, emailTemplate);
				logger.debug("SendTo >> "+sendTo);
				String subject = this.getEMailSubject(emailTemplate, emailTemplateM.getSubject(), applicationM, null);
				String content = this.getEMailContent(emailTemplate, emailTemplateM.getContent(), applicationM, null);
				
				GeneralParamProperties poParamM = this.getPOEmail();
					emailM.setFromName(poParamM.getParamvalue2());
					emailM.setFrom(poParamM.getParamvalue());
					emailM.setTo(sendTo);
					emailM.setContent(content);
					emailM.setSubject(subject);
					emailM.setCcTo(ccTo);
				this.sendEmail(emailM, applicationM, emailTemplate);
				
				addFollowDetails(applicationM, sendTo, ccTo);
				resultSuccess = true;
			}else{
				logger.info("EMAIL TemplateID : "+emailTemplate+" Disable");
				resultSuccess = true;
			}
		}catch (Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getMessage());
		}
		return resultSuccess;
	}
	private void addFollowDetails(PLApplicationDataM applicationM, String emailTo, String ccTo) throws Exception{
		PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);			
		}
		
		Vector<PLXRulesFUVerificationDataM> xRulesFUVerVect = xrulesVerM.getxRulesFUVerificationDataMs();
		if(xRulesFUVerVect == null){
			xRulesFUVerVect = new Vector<PLXRulesFUVerificationDataM>();
			xrulesVerM.setxRulesFUVerificationDataMs(xRulesFUVerVect);
		}
		
		PLXRulesFUVerificationDataM xrulesFuVerM = new PLXRulesFUVerificationDataM();
			xrulesFuVerM.setPersonalID(personM.getPersonalID());
			xrulesFuVerM.setSeq(xRulesFUVerVect.size()+1);
			
//			xrulesFuVerM.setCreateDate(new Timestamp((new Date()).getTime()));
			
			xrulesFuVerM.setCreateBy(applicationM.getUpdateBy());
			xrulesFuVerM.setUpdateBy(applicationM.getUpdateBy());
			xrulesFuVerM.setPhoneVerStatus(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL);
			
			String emails = "";
			if(!OrigUtil.isEmptyString(emailTo)){
				emails  = emailTo;
			}
			if(!OrigUtil.isEmptyString(emails) && !OrigUtil.isEmptyString(ccTo)){
				emails = emails + "," + ccTo;
			}
			xrulesFuVerM.setPhoneNo(emails);
			
			xrulesFuVerM.setCreateDate(new Timestamp((new Date()).getTime()));
			xrulesFuVerM.setUpdateDate(new Timestamp((new Date()).getTime()));
		
		insertFollowDetail(xrulesFuVerM);		

		xrulesVerM.add(xrulesFuVerM);		
		
	}
	
	private void insertFollowDetail(PLXRulesFUVerificationDataM xrulesFuVerM) throws Exception{
		PLORIGDAOFactory.getPLOrigEmailSMSDAO().insertFollowDetail(xrulesFuVerM);
	}
	
	private String dateToString(Date d ,String format){
		if(d == null){
			return "";
		}
		if("dd/MM/yyyy".equalsIgnoreCase(format)){
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");		
			return df.format(d);
		}
		return "";
	}
	private String StringNullToSpecific(String input, String specific) {
		if (input != null) {
			return input;
		}
		return specific;
	}
	
	public String StringNullOrEmptyToSpecific(String input, String specific) {
		if (input == null || "".equals(input.trim())) {
			return specific;
		}
		return input;
	}
	
	public String getKBANKEmployeeEmails(String branchCode,String busClass){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getKBANKEmployeeEmails(branchCode, busClass);
		}catch (Exception e){
			logger.fatal("##### getKBANKEmployeeEmail error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String getKBANKSentToEmails(String appId, String channelCode, String saleId, String branchCode, String refId, String busClass, String emailTemplate){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getKBANKSendToEmails(appId, channelCode, saleId, branchCode, refId, busClass, emailTemplate);
		}catch (Exception e){
			logger.fatal("##### getKBANKSentToEmails error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String getFollowUpAutoEmailSentTo(String appId){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getFollowUpAutoEmailSendTo(appId);
		}catch (Exception e){
			logger.fatal("##### getKBANKSentToEmails error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean sendEmailAutoICDCToUser(String userName, String attachmentID, String message, String fileDir) throws Exception{
		boolean resultSuccess = false;
		logger.debug("@@@@@ find email from userName:"+userName);
		String sendTo = "";
		UserDetailM userM = PLORIGDAOFactory.getPLOrigUserDAO().getUserDetail(userName);
		if(userM != null){
			sendTo = userM.getEmail();
		}
		logger.debug("@@@@@ sendTo:"+sendTo);
		if(sendTo != null && !"".equals(sendTo)){
			GeneralParamProperties poParamM = this.getPOEmail();
			String fromName = poParamM.getParamvalue2();
			String fromEmail = poParamM.getParamvalue();
			String subject = "Result auto increase/decrease " + this.dateToString(new Date(), "dd/MM/yyyy");
			String content = message;
			if(fromEmail != null && sendTo != null && !"".equals(sendTo) && subject != null && !"".equals(subject) && content != null && !"".equals(content)){
				PLApplicationDataM appM = new PLApplicationDataM();
				appM.setAppRecordID(attachmentID);
				appM.setUpdateBy("Scheduler");
				
				EmailM emailM = new EmailM();
				emailM.setTo(sendTo);
				emailM.setContent(content);
				emailM.setSubject(subject);
				emailM.setFromName(fromName);
				emailM.setFrom(fromEmail);
				emailM.setFileDir(fileDir);
				this.sendEmail(emailM, appM, null);
				resultSuccess = true;
			}else{
				logger.error("##### Some Email input not valide:[sendTo="+fromEmail+"][sendTo="+sendTo+"][subject="+subject+"][content="+content+"]");
				resultSuccess = false;
			}
		}else{
			logger.error("##### Not found Email send to from master table then return success");
			resultSuccess = true;
		}
		return resultSuccess;
	}
	
	public ProductProperties getProductDetails(String productCode){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getProductData(productCode);
		}catch (Exception e){
			logger.fatal("##### getProductDetails error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public CacheDataM getDocumentDetails(String documentCode){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getDocumentData(documentCode);
		}catch (Exception e){
			logger.fatal("##### getDocumentDetails error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public DocumentReasonProperties getDocumentReasonDetails(String documentCode, String reasonCode){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getDocReasonData(documentCode, reasonCode);
		}catch (Exception e){
			logger.fatal("##### getDocumentReasonDetails error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean sendEmailFollowUpAuto(String appId){
		boolean resultSuccess = false;
		try{
			String emailTemplate = OrigConstant.EmailSMS.EMAIL_FOLLOW_DOC_TO_BRANCH;
			String sendTo = this.getFollowUpAutoEmailSentTo(appId);
			logger.debug("@@@@@ sendTo:"+sendTo);
			PLApplicationDataM appM = PLORIGDAOFactory.getPLOrigApplicationDAO().loadOrigApplication(appId);
			if(appM != null){
				//load email template master
				EmailTemplateMasterM emailTemplateM = PLORIGDAOFactory.getPLOrigEmailSMSDAO().getEMailTemplateMaster(emailTemplate, appM.getBusinessClassId());
				if(emailTemplateM == null) emailTemplateM = new EmailTemplateMasterM();
				
				if(OrigConstant.FLAG_Y.equals(emailTemplateM.getEnable())){
					if(hasFollowDocList(appM)){
						appM.setUpdateBy("Scheduler");
						EmailM emailM = this.getSendFromByOwner(appId);
						String subject = this.getEMailSubject(emailTemplate, emailTemplateM.getSubject(), appM, emailM);
						String content = this.getEMailContent(emailTemplate, emailTemplateM.getContent(), appM, emailM);
						
						GeneralParamProperties poParamM = this.getPOEmail();
						emailM.setFromName(poParamM.getParamvalue2());
						emailM.setFrom(poParamM.getParamvalue());
						emailM.setTo(sendTo);
						emailM.setContent(content);
						emailM.setSubject(subject);
						this.sendEmail(emailM, appM, OrigConstant.EmailSMS.EMAIL_FOLLOW_DOC_TO_BRANCH);
						resultSuccess = true;
					}else{
						logger.info("@@@@@ Not found follow document list then return succes");
						resultSuccess = true;
					}
				}else{
					logger.info("@@@@@ Email template name:" + emailTemplate + " Disable");
					resultSuccess = true;
				}
			}else{
				logger.error("##### Current not found application then return success [appId:"+appId+"]");
				resultSuccess = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultSuccess;
	}
	
	public EmailM getSendFromByUser(String userName){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getSendFromByUser(userName);
		}catch (Exception e){
			logger.fatal("##### getKBANKSentToEmails error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public EmailM getSendFromByOwner(String appId){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getSendFromByOwner(appId);
		}catch (Exception e){
			logger.fatal("##### getKBANKSentToEmails error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	private String getEMailSubject(String emailTemplateID, String templateSubject, PLApplicationDataM app, EmailM emailM){
		String templateResult = templateSubject;
		if(templateResult == null || "".equals(templateResult)){
			return null;
		}else{
			// General Key for all template
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_APPLICATION_NO)){
				logger.debug("@@@@@ applicationNo:"+app.getApplicationNo());
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_APPLICATION_NO, this.StringNullToSpecific(app.getApplicationNo(),""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_BRANCH_NAME)){
				String branchName = this.getBranchName(app);
				logger.debug("@@@@@ branchName:"+branchName);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_BRANCH_NAME, this.StringNullToSpecific(branchName,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_CUSTOMER_NAME)){
				String customerName = this.getCustomerName(app);
				logger.debug("@@@@@ customerName:"+customerName);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_CUSTOMER_NAME, this.StringNullToSpecific(customerName,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_PRODUCE_NAME)){
				String productName = this.getProductName(app);
				logger.debug("@@@@@ productName:"+productName);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_PRODUCE_NAME, this.StringNullToSpecific(productName,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_PHONE_NO)){
				String telephoneNo = this.getPOTelephone();
				logger.debug("@@@@@ telephoneNo:"+telephoneNo);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_PHONE_NO, this.StringNullToSpecific(telephoneNo,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_REF_NO)){
				logger.debug("@@@@@ referenceNo:"+app.getRefNo());
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_REF_NO, this.StringNullToSpecific(app.getRefNo(),""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_SALE_TYPE)){
				String saleTypeDesc = this.getSaleType(app.getSaleType());
				logger.debug("@@@@@ saleTypeDesc:"+saleTypeDesc);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_TYPE, this.StringNullToSpecific(saleTypeDesc,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_SALE_ID)){
				String saleID = "";
				if(app != null && app.getSaleInfo() != null){
					saleID = app.getSaleInfo().getSalesName();
				}
				logger.debug("@@@@@ saleID:"+saleID);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_ID, this.StringNullToSpecific(saleID,"-"));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_SALE_NAME)){
				String saleName = this.getSaleName(app);
				logger.debug("@@@@@ saleName:"+saleName);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_NAME, this.StringNullToSpecific(saleName,"-"));
			}
		}
		return templateResult;
	}
	
	private String getEMailContent(String emailTemplateID, String templateContent, PLApplicationDataM app, EmailM emailM){
		PLOrigEmailSMSUtil sendUtil = new PLOrigEmailSMSUtil();
		String templateResult = templateContent;
		if(templateResult == null || "".equals(templateResult)){
			return null;
		}else{
			// Only template Key
			if(OrigConstant.EmailSMS.EMAIL_FOLLOW_DOC_TO_BRANCH.equals(emailTemplateID)){
				int sla = sendUtil.getFollowUpSLA(app.getAppRecordID());
				String slaStr = "-";
				String slaDateStr = "-";
				if(sla > 0){ 
					slaStr = String.valueOf(sla);
					slaDateStr = sendUtil.getDateExtendWorkingDay(app.getAppRecordID());
				}
				logger.debug("@@@@@ sla:"+slaStr);
				logger.debug("@@@@@ slaLastDate:"+slaDateStr);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_DOC_DETAIL, this.StringNullToSpecific(this.getFollowDocDetails(app),""));
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_SLA_DAY, slaStr);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_SLA_DATE, slaDateStr);
				if(emailM != null && emailM.getFromName() != null){
					templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_FU_NAME, getKBankUserNameSurname(emailM.getFromName()));
				}else{
					templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_FU_NAME, getKBankUserNameSurname(app.getUpdateBy()));
				}
			}
			
			// General Key for all template
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_APPLICATION_NO)){
				logger.debug("@@@@@ applicationNo:"+app.getApplicationNo());
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_APPLICATION_NO, this.StringNullToSpecific(app.getApplicationNo(),""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_BRANCH_NAME)){
				String branchName = this.getBranchName(app);
				logger.debug("@@@@@ branchName:"+branchName);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_BRANCH_NAME, this.StringNullToSpecific(branchName,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_CUSTOMER_NAME)){
				String customerName = this.getCustomerName(app);
				logger.debug("@@@@@ customerName:"+customerName);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_CUSTOMER_NAME, this.StringNullToSpecific(customerName,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_PRODUCE_NAME)){
				String productName = this.getProductName(app);
				logger.debug("@@@@@ productName:"+productName);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_PRODUCE_NAME, this.StringNullToSpecific(productName,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_PHONE_NO)){
				String telephoneNo = this.getPOTelephone();
				logger.debug("@@@@@ telephoneNo:"+telephoneNo);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_PHONE_NO, this.StringNullToSpecific(telephoneNo,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_REF_NO)){
				logger.debug("@@@@@ referenceNo:"+app.getRefNo());
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_REF_NO, this.StringNullToSpecific(app.getRefNo(),""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_SALE_TYPE)){
				String saleTypeDesc = this.getSaleType(app.getSaleType());
				logger.debug("@@@@@ saleTypeDesc:"+saleTypeDesc);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_TYPE, this.StringNullToSpecific(saleTypeDesc,""));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_SALE_ID)){
				String saleID = "";
				if(app != null && app.getSaleInfo() != null){
					saleID = app.getSaleInfo().getSalesName();
				}
				logger.debug("@@@@@ saleID:"+saleID);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_ID, this.StringNullToSpecific(saleID,"-"));
			}
			if(this.foundKey(templateResult, OrigConstant.EmailSMS.KEY_SALE_NAME)){
				String saleName = this.getSaleName(app);
				logger.debug("@@@@@ saleName:"+saleName);
				templateResult = templateResult.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_NAME, this.StringNullToSpecific(saleName,"-"));
			}
		}
		return templateResult;
	}
	
	private String getEmailSendTo(PLApplicationDataM appM, String emailTemplate){
		PLSaleInfoDataM saleInfoM = appM.getSaleInfo();
		if(saleInfoM != null){
			return this.getKBANKSentToEmails(appM.getAppRecordID(), appM.getApplyChannel(), 
											 saleInfoM.getSalesName(), saleInfoM.getSalesBranchCode(), 
					                         saleInfoM.getRefName() ,appM.getBusinessClassId(), emailTemplate);
		}else{
			return "";
		}
	}
	
	private String getSaleName(PLApplicationDataM appM){
		if(appM != null){
			PLSaleInfoDataM saleInofM = appM.getSaleInfo();
			if(saleInofM != null){
				try{
					return ORIGDAOFactory.getUtilityDAO().getSellerName(saleInofM.getSalesName());
				}catch(Exception e){
					return "";
				}
			}
		}
		return "";
	}
	
	private boolean hasFollowDocList(PLApplicationDataM app){
		if(app != null && app.getDocCheckListVect() != null && app.getDocCheckListVect().size() > 0){
			Vector<PLDocumentCheckListDataM> docVt = app.getDocCheckListVect();
			for(int i=0;i<docVt.size();i++){
				PLDocumentCheckListDataM docM = (PLDocumentCheckListDataM)docVt.get(i);
				if(docM != null && OrigConstant.DocumentStatus.TrackDoc.equals(docM.getReceive())){
					return true;
				}
			}
		}
		return false;
	}
	
	private String getKBankUserNameSurname(String userName){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getKBankUserNameSurname(userName);
		}catch(Exception e){
			return "";
		}
	}
	
	private boolean foundKey(String template, String key){
		if(template != null && key != null){
			return template.contains(key);
		}
		return false;
	}
	
	private String getSaleType(String saleTypeCode){
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		Vector<MainSaleTypeProperties> vSaleType = cacheUtil.loadCacheByName("MainSaleType");
		if(vSaleType != null && vSaleType.size() > 0){
			for(MainSaleTypeProperties saleTypeProp : vSaleType){
				if(saleTypeProp.getCode() != null && saleTypeProp.getCode().equals(saleTypeCode)){
					return saleTypeProp.getThDesc();
				}
			}
		}
		return "";
	}	

	private PLApplicationDataM reduceApplicationSize(PLApplicationDataM applicationM){
//		#septemwi comment do cannot reduceApplicationSize()..
//		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
//		if(personM != null){
//			personM.setXrulesVerification(null);
//			personM.setNcbDocVect(null);
//			personM.setFinancialInfoVect(null);
//		}
//		appM.setApplicationImageVect(null);
//		appM.setApplicationLogVect(null);
//		appM.setPaymentMethod(null);
//		appM.setCashTransfer(null);
//		appM.setNotepadDataM(null);
//		appM.setReasonVect(null);
//		appM.setAuditTrailDataVect(null);
//		appM.setAttachmentVect(null);
		return applicationM;
	}
}
