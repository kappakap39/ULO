package com.eaf.orig.ulo.service.ejb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.postapproval.CJDCardLinkAction;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceReqRespDataM;
import com.eaf.service.common.util.ServiceUtil;

/**
 * Message-Driven Bean implementation class for: CJDQueueListenerBean
 */
@MessageDriven(
		name = "CJDMDB", activationConfig = 
		{ 
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"), 
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/FBRESP")
		}
		, mappedName = "jms/CJDMDB"
		)
public class CJDQueueListenerBean implements MessageListener 
{
	private static transient Logger logger = Logger.getLogger(CJDQueueListenerBean.class);
	
	@Resource private MessageDrivenContext mdc;
    /**
     * Default constructor. 
     */
    public CJDQueueListenerBean()
    {
    	
    }
    
    public void setMessageDrivenContext(MessageDrivenContext ctx)
    {
    	mdc = ctx;
    	logger.info("setMessageDrivenContext Done.");
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message)
    {
    	try 
    	{
    		logger.info("Prepare to consume message : " + message);
    		
    		if(mdc == null)
    		{
    			logger.fatal("MessageDrivenContext is null");
    			throw new Exception("MessageDrivenContext is null");
    		}
    		
    		String msgBody = ((TextMessage) message).getText();
    		logger.info("msgBody: " + msgBody);
    		
    		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
    		ServiceReqRespDataM serviceLogRequest = new ServiceReqRespDataM();
			serviceLogRequest.setReqRespId(serviceReqRespId);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.CJDCardResult);
			serviceLogRequest.setCreateBy("SYSTEM");
			
    		if(msgBody != null)
    		{
    			serviceLogRequest.setContentMsg(msgBody);
    			String[] msgSplit = msgBody.split("\\|");

	    		if(!Util.empty(msgSplit[0]) && msgSplit[0].length() == 9)
	    		{
	    			//Insert cjdResponse to Database
	    			String cardLinkRefNo = msgSplit[0].trim();
	    			String applicationGroupNo = CJDCardLinkAction.getApplicationGroupNoByCardLinkRefNo(cardLinkRefNo);
	    			serviceLogRequest.setAppId(cardLinkRefNo);
	    			
	    			if(!Util.empty(applicationGroupNo))
	    			{
		    			logger.info("cardLinkRefNo: " + cardLinkRefNo);
		    			logger.info("applicationGroupNo: " + applicationGroupNo);
		    			
		    			serviceLogRequest.setRefCode(applicationGroupNo);
		    			serviceLogRequest.setRespCode("00");
		    			
		    			//Insert to table ONLINE_CARDLINK_RESULT
		    			CJDCardLinkAction.insertOnlineCardlinkResult(applicationGroupNo, cardLinkRefNo, msgBody);
	    			}
	    			else
	    			{
	    				serviceLogRequest.setRespDesc("ApplicationGroupNo not found for cardLinkRefNo " + cardLinkRefNo);
		    			serviceLogRequest.setRespCode("10");
	    			}
	    		}
	    		else
	    		{
	    			logger.debug("CardLinkRefNo is INVALID.");
	    			serviceLogRequest.setRespDesc("INVALID_CJD_RESPONSE");
	    			serviceLogRequest.setRespCode("10");
	    		}
    		}
    		else
    		{
    			logger.debug("msgBody is null");
    		}
    		
    		CJDCardLinkAction.createServiceReqRespLog(serviceLogRequest);
    		logger.info("Message " + message.getJMSMessageID() + " Consumed.");
    	}
    	catch(Exception e)
    	{
    		logger.error("Exception caught: " + e);
    		//Roll back transaction if Error
    		if(mdc != null)
    		{
    			mdc.setRollbackOnly();
    			logger.debug("Rollback...");
    		}
    		else
    		{
    			logger.info("MessageDrivenContext is null.");
    		}
    	}
	}

}
