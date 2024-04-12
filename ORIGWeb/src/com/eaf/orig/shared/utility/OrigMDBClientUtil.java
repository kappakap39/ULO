package com.eaf.orig.shared.utility;

import com.eaf.orig.shared.model.EmailM;
import com.eaf.orig.shared.model.SMSDataM;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class OrigMDBClientUtil {
	public void sendEmail(EmailM emailM){
		Context context = null;
		QueueConnectionFactory queueConFac = null;
		QueueConnection queueCon = null;
		QueueSession queueSess = null;
		Queue queue = null;
		QueueSender queueSend = null;
		ObjectMessage objMessage = null;
		try{
			context = new InitialContext();
			queueConFac = (QueueConnectionFactory) context.lookup("java:comp/env/jms/messageQueueCF");
			queue = (Queue) context.lookup("java:comp/env/jms/messageMailQueue");
			queueCon = queueConFac.createQueueConnection();
			queueSess = queueCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queueSend = queueSess.createSender(queue);

			objMessage = queueSess.createObjectMessage();
			
			objMessage.setObject(emailM);
			queueSend.send(objMessage);			
		}catch (NamingException e){
			System.out.println(e.getMessage());
		}catch (JMSException e){
			System.out.println(e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
	}
	
	public void sendSMS(SMSDataM smsM){
		Context context = null;
		QueueConnectionFactory queueConFac = null;
		QueueConnection queueCon = null;
		QueueSession queueSess = null;
		Queue queue = null;
		QueueSender queueSend = null;
		ObjectMessage objMessage = null;
		try{
			context = new InitialContext();
			queueConFac = (QueueConnectionFactory) context.lookup("java:comp/env/jms/messageQueueCF");
			queue = (Queue) context.lookup("java:comp/env/jms/messageSMSQueue");
			queueCon = queueConFac.createQueueConnection();
			queueSess = queueCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queueSend = queueSess.createSender(queue);

			objMessage = queueSess.createObjectMessage();
			
			objMessage.setObject(smsM);
			queueSend.send(objMessage);			
		}catch (NamingException e){
			System.out.println(e.getMessage());
		}catch (JMSException e){
			System.out.println(e.getMessage());
		}finally{
			if(queueCon != null){
				try{
					queueCon.close();
				}catch (JMSException e){}
			}
		}
	}
}
