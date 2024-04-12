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

import org.apache.log4j.Logger;

//import com.eaf.orig.cache.properties.GeneralParamProperties;
//import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.ejb.ORIGContactManager;
//import com.eaf.orig.shared.model.SMSDataM;
//import com.eaf.orig.shared.model.SMSPrepareDataM;
//import com.eaf.orig.shared.model.ServiceEmailSMSQLogM;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailSMSUtil;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailUtil;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigSMSUtil;
//import com.eaf.orig.ulo.pl.model.contract.PLOrigContactDataM;
//import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

//@MessageDriven(
//		name="SendSMSExceptionBean", activationConfig = { 
//				@ActivationConfigProperty( propertyName = "destinationType", propertyValue = "javax.jms.Queue" ), 
//				@ActivationConfigProperty(propertyName="destination", propertyValue="jms/messageSMSEXQueue")
//				})
public class SendSMSExceptionBean implements MessageListener {
//	private MessageDrivenContext mdc;
	Logger logger = Logger.getLogger(SendSMSExceptionBean.class);

    public SendSMSExceptionBean() {
       super();
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
//        logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ SendSMSExceptionBean");
//        ObjectMessage msg = null;
//	    try {
//	        if (message instanceof ObjectMessage) {
//	            msg = (ObjectMessage) message;
//	            logger.debug("@@@@@ MESSAGE DRIVEN BEAN SMS: Message received: " + msg.getObject().getClass().getName());
//	            if(msg.getObject() instanceof PLOrigContactDataM){
//	            	PLOrigContactDataM origContactM = (PLOrigContactDataM)msg.getObject();
//	            	
//	            	PLOrigSMSUtil smsUtil = new PLOrigSMSUtil();
//	            	SMSPrepareDataM smsPrepareM = smsUtil.prepareSMSData(origContactM.getApplicationRecordId(), origContactM.getTemplateName());
//	            	SMSDataM smsM = smsUtil.prepareSMSMessage(smsPrepareM);
//            		origContactM.setSms(smsM);
//
//	            	PLOrigEmailSMSUtil util = new PLOrigEmailSMSUtil();
//	            	ORIGContactManager contactManager = PLORIGEJBService.getORIGContactManager();
//
//	            	ServiceEmailSMSQLogM logM = new ServiceEmailSMSQLogM();
//	            	logM.setContactLogID(origContactM.getContactLogId());
//        			logM.setServiceType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
//	            	
//	            	int smsQueueCount = util.getEmailSMSQCount(origContactM.getContactLogId());
//	            	int maxReSend = 3; //set default to 3
//	            	GeneralParamProperties reSendParam = util.getGeneralParamDetails(OrigConstant.GeneralParamCode.SMS_RE_SEND);
//	            	if(reSendParam != null && reSendParam.getParamvalue() != null && !"".equals(reSendParam.getParamvalue())){
//	            		maxReSend = Integer.parseInt(reSendParam.getParamvalue());
//	            	}
//	            	logger.debug("@@@@@ maxReSend :" + maxReSend);
//	            	if(smsQueueCount > 0 && smsQueueCount < maxReSend){
//	            		contactManager.increaseEmailSMSQCount(origContactM.getContactLogId()); //increase queue count
//	            		
//	            		try{
//	            			smsUtil.sendSMS(origContactM); //Re-send mail to queue
//	            		}catch (Exception e){
//	            			logger.error("##### Cannot re-send sms to Queue :"+e.getMessage());
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
//	        e.printStackTrace();
//	        mdc.setRollbackOnly();
//	    } catch (Throwable te) {
//	        te.printStackTrace();
//	        mdc.setRollbackOnly();
//	    }
    }

}
