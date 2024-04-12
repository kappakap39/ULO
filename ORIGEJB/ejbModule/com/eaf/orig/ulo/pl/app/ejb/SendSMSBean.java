package com.eaf.orig.ulo.pl.app.ejb;

//import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
//import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

//import com.eaf.orig.ulo.pl.app.utility.SMSEngine;
//import com.eaf.orig.ulo.pl.app.utility.SMSLogic;
import com.eaf.orig.ulo.pl.model.app.EmailSMSDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;

//@MessageDriven(
//		activationConfig = { @ActivationConfigProperty(
//				propertyName = "destinationType", propertyValue = "javax.jms.Queue"
//		) }, 
//		messageListenerInterface = MessageListener.class)
		
public class SendSMSBean implements MessageListener {

	Logger logger = Logger.getLogger(SendMailBean.class);

    public SendSMSBean() {
    	super();
    }

    public void onMessage(Message message) {
    	ObjectMessage msg = null;
	    try{
	        if(message instanceof ObjectMessage) {
	            msg = (ObjectMessage) message;
	            if(msg.getObject() instanceof PLOrigContactDataM){
	            	PLOrigContactDataM origContactM = (PLOrigContactDataM)msg.getObject();
	            	doMessage(origContactM);
	            }else if(msg.getObject() instanceof EmailSMSDataM){
	            	EmailSMSDataM emailSmsM = (EmailSMSDataM)msg.getObject();
	            	doMessage(emailSmsM);
	            }
	        } 
	    }catch(Exception e){
	    	logger.fatal("ERROR ",e);
	    	throw new EJBException(e.getMessage());
	    } 
    }
    
    public void doMessage(EmailSMSDataM emailSmsM) throws Exception{
    	logger.debug("doMessage().."+emailSmsM.getAppRecID());
//    	SMSLogic logic = new SMSLogic();
//    		logic.process(emailSmsM);
    }
    
    public void doMessage(PLOrigContactDataM origContactM) throws Exception{
//    	SMSEngine engine = new SMSEngine();
//    		engine.send(origContactM);
    }
}
