package com.eaf.orig.ulo.pl.app.ejb;

//import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
//import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

//import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.PLORIGDAOFactory;
//import com.eaf.orig.shared.model.EmailM;
//import com.eaf.orig.shared.model.EmailTemplateMasterM;
//import com.eaf.orig.ulo.pl.app.utility.EmailEngine;
//import com.eaf.orig.ulo.pl.app.utility.EmailLogic;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailUtil;
import com.eaf.orig.ulo.pl.model.app.EmailSMSDataM;
//import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;

//@MessageDriven(name="SendMailBean", activationConfig = {
//    @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
//    @ActivationConfigProperty(propertyName="destination", propertyValue="jms/messageMailQueue")
//})

public class SendMailBean implements MessageListener {
	
	Logger logger = Logger.getLogger(SendMailBean.class);
 
    public SendMailBean() {
    	super();
    }

    public void onMessage(Message message) {
    	logger.debug("onMessage()..");
    	ObjectMessage msg = null;
	    try {
	        if (message instanceof ObjectMessage) {
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
    	logger.debug("doEmailSMSDataM().."+emailSmsM.getAppRecID());
//    	EmailLogic logic = new EmailLogic();
//    		logic.process(emailSmsM);
    }
        
    public void doMessage(PLOrigContactDataM origContactM) throws Exception{
//    	if(origContactM.getAppM() == null){ 
////    		this.processEmail(origContactM);
//    		EmailEngine engine = new EmailEngine();
//				engine.send(origContactM);
//    	}else{
//    		PLApplicationDataM appM = origContactM.getAppM();
//    		EmailTemplateMasterM emailTemplateM = PLORIGDAOFactory.getPLOrigEmailSMSDAO().getEMailTemplateMaster(origContactM.getTemplateName(), appM.getBusinessClassId());
//			if(emailTemplateM == null) emailTemplateM = new EmailTemplateMasterM();
//			
//			if(OrigConstant.FLAG_Y.equals(emailTemplateM.getEnable())){
//				PLOrigEmailUtil util = new PLOrigEmailUtil();
//				EmailM mailM = util.prepareEmailMessage(appM, emailTemplateM);
//				origContactM.setEmail(mailM);
////				this.processEmail(origContactM);	
//	    		EmailEngine engine = new EmailEngine();
//					engine.send(origContactM);				
//			}
//    	}
    }
    
}
