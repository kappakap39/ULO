package com.eaf.orig.ulo.pl.app.ejb;

import javax.ejb.ActivationConfigProperty;
//import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
//import javax.ejb.MessageDrivenBean;
//import javax.ejb.MessageDrivenContext;
//import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
//import javax.jms.ObjectMessage;
//import javax.jms.Queue;
//import javax.jms.QueueConnection;
//import javax.jms.QueueConnectionFactory;
//import javax.jms.QueueSender;
//import javax.jms.QueueSession;
//import javax.jms.Session;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;

import org.apache.log4j.Logger;

//import com.eaf.orig.cache.properties.GeneralParamProperties;
//import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.PLORIGDAOFactory;
//import com.eaf.orig.shared.ejb.ORIGContactManager;
//import com.eaf.orig.shared.model.EmailM;
//import com.eaf.orig.shared.model.EmailTemplateMasterM;
//import com.eaf.orig.shared.model.ServiceDataM;
//import com.eaf.orig.shared.model.ServiceEmailSMSQLogM;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailSMSUtil;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailUtil;
//import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.orig.ulo.pl.model.contract.PLOrigContactDataM;
//import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * Message-Driven Bean implementation class for: SendMailExceptionBean
 *
 */
//@MessageDriven(
//		name="SendMailExceptionBean", activationConfig = { 
//				@ActivationConfigProperty( propertyName = "destinationType", propertyValue = "javax.jms.Queue" ), 
//				@ActivationConfigProperty(propertyName="destination", propertyValue="jms/messageMailEXQueue")
//				})
public class SendMailExceptionBean implements MessageListener {
//	private MessageDrivenContext mdc;
	Logger logger = Logger.getLogger(SendMailExceptionBean.class);
    /**
     * Default constructor. 
     */
    public SendMailExceptionBean() {
       super();
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
//    	#septemwi comment do resend Email
//        // TODO Auto-generated method stub
//    	logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ SendMailExceptionBean");
//    	ObjectMessage msg = null;
//	    try {
//	        if (message instanceof ObjectMessage) {
//	            msg = (ObjectMessage) message;
//	            logger.debug("@@@@@ MESSAGE DRIVEN BEAN Mail: Message received: " + msg.getObject().getClass().getName());
//	            if(msg.getObject() instanceof PLOrigContactDataM){
//	            	PLOrigContactDataM origContactM = (PLOrigContactDataM)msg.getObject();
//
//        			PLOrigEmailUtil emailUtil = new PLOrigEmailUtil();
//	            	if(origContactM.getAppM() != null){
//		            	PLApplicationDataM appM = origContactM.getAppM();
//	            		EmailTemplateMasterM emailTemplateM = PLORIGDAOFactory.getPLOrigEmailSMSDAO().getEMailTemplateMaster(origContactM.getTemplateName(), appM.getBusinessClassId());
//	        			if(emailTemplateM == null) emailTemplateM = new EmailTemplateMasterM();
//	        			
//	    				EmailM mailM = emailUtil.prepareEmailMessage(appM, emailTemplateM);
//	    				origContactM.setEmail(mailM);
//            			origContactM.setAppM(null); //Clear application
//	            	}
//
//	            	PLOrigEmailSMSUtil util = new PLOrigEmailSMSUtil();
//	            	ORIGContactManager contactManager = PLORIGEJBService.getORIGContactManager();
//
//	            	ServiceEmailSMSQLogM logM = new ServiceEmailSMSQLogM();
//	            	logM.setContactLogID(origContactM.getContactLogId());
//        			logM.setServiceType(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL);
//	            	
//	            	int emailQueueCount = util.getEmailSMSQCount(origContactM.getContactLogId());
//	            	int maxReSend = 3; //set default to 3
//	            	GeneralParamProperties reSendParam = util.getGeneralParamDetails(OrigConstant.GeneralParamCode.EMAIL_RE_SEND);
//	            	if(reSendParam != null && reSendParam.getParamvalue() != null && !"".equals(reSendParam.getParamvalue())){
//	            		maxReSend = Integer.parseInt(reSendParam.getParamvalue());
//	            	}
//	            	logger.debug("@@@@@ maxReSend :" + maxReSend);
//	            	if(emailQueueCount > 0 && emailQueueCount < maxReSend){
//	            		contactManager.increaseEmailSMSQCount(origContactM.getContactLogId()); //increase queue count
//	            		
//	            		try{        //Clear model application 
//	            			emailUtil.sendEmail(origContactM); //Re-send mail to queue
//	            		}catch (Exception e){
//	            			logger.error("##### Cannot re-send mail to Queue :"+e.getMessage());
//	            			if(origContactM.getApplicationRecordId() != null && !"".equals(origContactM.getApplicationRecordId())){
//		            			origContactM.setSendStatus(OrigConstant.FLAG_N);
//		            			contactManager.createOrigContractLog(origContactM);
//	            			}
//	            			contactManager.deleteEmailSMSQueue(origContactM.getContactLogId());
//	            		}
//	            	}else{
//	            		if(origContactM.getApplicationRecordId() != null && !"".equals(origContactM.getApplicationRecordId())){
//	            			origContactM.setSendStatus(OrigConstant.FLAG_N);
//	            			contactManager.createOrigContractLog(origContactM);
//            			}
//            			contactManager.deleteEmailSMSQueue(origContactM.getContactLogId());
//	            	}
//	            }else{
//	            	logger.error("##### Object of wrong type: " + msg.getObject().getClass().getName());
//	            }
//	        } else {
//	        	logger.error("##### Message of wrong type: " + message.getClass().getName());
//	        }
//	    } catch (JMSException e) {
//	        //e.printStackTrace();
//	        mdc.setRollbackOnly();
//	    } catch (Throwable te) {
//	        te.printStackTrace();
//	        mdc.setRollbackOnly();
//	    }
    }
    
}
