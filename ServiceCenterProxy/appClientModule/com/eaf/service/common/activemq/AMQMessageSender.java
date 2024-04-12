package com.eaf.service.common.activemq;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;



public class AMQMessageSender {

	private static final String CLASS_NAME = AMQMessageSender.class.getName();

	private static transient final Logger LOG = Logger.getLogger(CLASS_NAME);
	
	private static final String PREFIX_FILE_PROPERTIES = "mqConnection";

	//private static String JMS_DEFALT_QUEUE_NAME = SpaceHubConfigManager.getConfig(PREFIX_FILE_PROPERTIES, "jms.smartServe.queueName");

	public final static String SUCCESS = "00";
	public final static String CONNECTION_EXCEPTION = "01";
	public final static String JMS_EXCEPTION = "02";
	public final static String EXCEPTION = "03";

//	private static Connection getMQConnection(String JMS_JNDI_NAME) {
//		final String METHOD_NAME = " getMQConnection() ";
//		Connection conn = null;
//		try {
//			Context ctx = new InitialContext();
//			ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup(JMS_JNDI_NAME);
//			conn = connectionFactory.createConnection();
//		} catch (NamingException e) {
//			LOG.error(CLASS_NAME + METHOD_NAME + e.getMessage(), e);
//			closeMQconnection(conn);
//		} catch (JMSException e) {
//			LOG.error(CLASS_NAME + METHOD_NAME + e.getMessage(), e);
//			closeMQconnection(conn);
//		} catch (Exception e) {
//			LOG.error(CLASS_NAME + METHOD_NAME + e.getMessage(), e);
//			closeMQconnection(conn);
//		}
//		if (conn == null) {
//			LOG.error(CLASS_NAME + METHOD_NAME + " Failed to get Active MQ connection using name " + JMS_JNDI_NAME);
//		}
//		return conn;
//	}


	private static Connection createMqConnect(String url, String user, String password) {
		final String METHOD_NAME = " createMqConnect() ";
		Connection conn = null;
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
			//ConnectionFactory connectionFactory = new ActiveMQConnectionFactory( url);
			conn = connectionFactory.createConnection();
			conn.start();
		} catch (JMSException e) {
			LOG.error(CLASS_NAME + METHOD_NAME + e.getMessage(), e);
			closeMQconnection(conn);
		} catch (Exception e) {
			LOG.error(CLASS_NAME + METHOD_NAME + e.getMessage(), e);
			closeMQconnection(conn);
		}
		if (conn == null) {
			LOG.error(CLASS_NAME + METHOD_NAME + " Failed to get Active MQ connection url " + url);
		}
		return conn;
	}

	private static void closeMQconnection(Connection conn) {
		final String METHOD_NAME = " closeMQconnection(Connection conn) ";
		if (conn != null) {
			try {
				conn.close();
			} catch (JMSException e) {
				LOG.error(CLASS_NAME + METHOD_NAME + e.getMessage(), e);
			}
		}
	}

	

	public String sendMessage(String queueName, String message, String url, String user, String password) {
		final String METHOD_NAME = " sendMessage(String queueName, String message) ";
		Connection conn = null;
		Session session;
		try {
			conn = createMqConnect(url, user, password);
			if (conn == null) {
				return CONNECTION_EXCEPTION;
			}
			// JMS messages are sent and received using a Session. We will
			// create here a non-transactional session object. If you want
			// to use transactions you should set the first parameter to 'true'
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			// MessageProducer is used for sending messages (as opposed
			// to MessageConsumer which is used for receiving them)
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText(message);
			producer.send(textMessage);
		} catch (JMSException e) {
			LOG.error(CLASS_NAME + METHOD_NAME + e.getMessage(), e);
			return JMS_EXCEPTION;
		} catch (Exception e) {
			LOG.error(CLASS_NAME + METHOD_NAME + e.getMessage(), e);
			return EXCEPTION;
		} finally {
			closeMQconnection(conn);
		}
		return SUCCESS;
	}



}
