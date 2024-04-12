package com.kbank.eappu.lookup;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.orig.shared.service.OrigServiceLocator;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kbank.eappu.dummycore.EAppDummyTemplateGen;
import com.kbank.eappu.model.Message;
import com.kbank.eappu.model.MessageData;
import com.kbank.eappu.model.MessageHeader;

/** 
 * Singleton Lookup for EApp Util. 
 */
public class SingletonEAU {
	private static Logger logger = Logger.getLogger(SingletonEAU.class);
	private static final ReentrantReadWriteLock rwlMsgTemplates = new ReentrantReadWriteLock();
	private static Map<String, Message> msgTemplates;
	
//	static {
//        getLogger().info("SingletonEAU.static initialization:begin");
//        try {
//            inittialize();
//        } catch (Exception ex) {
//            getLogger().error("Omitted exception in SingletonEAU.static initialization", ex);
//        }
//        getLogger().info("SingletonEAU.static initialization:end");
//    }

	public static Logger getLogger() {
		return logger;
	}

	public static Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//        Connection conn = null;
//    	String connStr="jdbc:oracle:thin:@192.168.0.98:1521:ULODB";
//    	String user="orig_app";
//    	String pass="password";
//        Driver driver = (Driver) Class.forName(OracleDriver.class.getName()).newInstance();
//        DriverManager.registerDriver(driver);
//        conn = DriverManager.getConnection(connStr, user, pass);
//        return conn;
		try{
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(1);
		} catch (Exception e) {
			logger.fatal("Connection is null", e);
		}
		return null;
    }
	
	/**
	 * Retrieve message template from database by code
	 * @param templateId is id correspond to template
	 * @param clazz is class correspond to template
	 * @return Map contains templates data.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static Map<String, Message> getMsgTemplates() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        boolean initRequired = false;
        rwlMsgTemplates.readLock().lock();
        try {
            if (msgTemplates == null) {
                initRequired = true;
            }
        } finally {
            rwlMsgTemplates.readLock().unlock();
        }
        if (initRequired) {
            initMsgTemplates();
        }
        return msgTemplates;
    }
	
	public static Message getMsgTemplates(String templateId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvocationTargetException, NoSuchMethodException{
        boolean initRequired = false;
        rwlMsgTemplates.readLock().lock();
        try {
            if (msgTemplates == null) {
                initRequired = true;
            }
        } finally {
            rwlMsgTemplates.readLock().unlock();
        }
        if (initRequired) {
            initMsgTemplates();
        }
        return msgTemplates.get(templateId);
    }
	
	/**
	 * Initialize various of value in current system. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 **/
	private static void inittialize() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		getLogger().info("static.inittialize:begin");
        initMsgTemplates();
        getLogger().info("static.inittialize:end");
	}

	private static void initMsgTemplates() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		rwlMsgTemplates.writeLock().lock();
        try {
        	msgTemplates = generateMsgTemplates();
        } finally {
        	rwlMsgTemplates.writeLock().unlock();
        }
	}

	public static Map<String, Message> generateMsgTemplates() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		logger.info("generateMsgTemplates:begin");
		Map<String, Message>  msgTemplatesLoc = new HashMap<>();
        try {
        	Connection con = null;
            PreparedStatement pre = null;
            ResultSet rs = null;
            
            //Suspended attributes as of designed column from Avalant as of 20190204
            //String landingPageImgTHStr;
            //String[] landingPageImgTHArr;
            //String landingPageImgENStr;
            //String[] landingPageImgENArr;
            try {
                con = getConnection();
                String stmtStr = "SELECT * FROM MS_SMS_TEMPLATE_MASTER WHERE ENABLED='E' ORDER BY TEMPLATE_ID ";
                
                pre = con.prepareStatement(stmtStr);
                rs = pre.executeQuery();

                Message msg;
                String templateId;
                JsonParser parser;
                while (rs.next()) {
                	msg = new Message();
                	templateId = rs.getString("TEMPLATE_ID");
                	
                	//header
                	MessageHeader header = new MessageHeader();
                	
                	//data
                	MessageData data = new MessageData();
                	
                	//2.13 imodeFlag Mandatory Integer
            		data.setImodeFlag(rs.getInt("I_MODE_FLAG"));
                	
                	//2.14 idingFlag Mandatory Integer
            		data.setIdingFlag(rs.getInt("I_DING_FLAG"));
                	
                	//2.15 landingFlag Mandatory Integer
            		data.setLandingFlag(rs.getInt("LANDING_FLAG"));
            		
                	//2.16 iotherType Mandatory Integer
            		data.setIotherType(rs.getInt("I_OTHER_TYPE"));
            		
                	//2.19 imodeBadgeTH Mandatory String
            		data.setImodeBadgeTH(rs.getString("I_MODE_BADGE_TH"));
            		
                	//2.20 imodeBadgeEN Mandatory String
            		data.setImodeBadgeEN(rs.getString("I_MODE_BADGE_EN"));
            		
                	//2.21 imodeMsgTopicTH Mandatory String
            		data.setImodeMsgTopicTH(rs.getString("I_MODE_MSG_TOPIC_TH"));
            		
                	//2.22 imodeMsgTopicEN Mandatory String
            		data.setImodeMsgTopicEN(rs.getString("I_MODE_MSG_TOPIC_EN"));
            		
                	//2.23 imodeMsgBodyTH Mandatory String
            		data.setImodeMsgBodyTH(rs.getString("I_MODE_MSG_BODY_TH"));
            		
                	//2.24 imodeMsgBodyEN Mandatory String
            		data.setImodeMsgBodyEN(rs.getString("I_MODE_MSG_BODY_EN"));
            		
                	//2.25 imodeMsgHighLightTH Optional String
            		data.setImodeMsgHighLightTH(rs.getString("I_MODE_MSG_HIGH_LIGHT_TH"));
            		
                	//2.26 imodeMsgHighLightEN Optional String
            		data.setImodeMsgHighLightEN(rs.getString("I_MODE_MSG_HIGH_LIGHT_EN"));
            		
                	//2.27 imodeMsgFooterTH Optional String
            		data.setImodeMsgFooterTH(rs.getString("I_MODE_MSG_FOOTER_TH"));
            		
                	//2.28 imodeMsgFooterEN Optional String
            		data.setImodeMsgFooterEN(rs.getString("I_MODE_MSG_FOOTER_EN"));
            		
                	//2.29 idingNotiImgTH Optional String
            		data.setIdingNotiImgTH(rs.getString("I_DING_NOTI_IMG_TH"));
            		
                	//2.30 idingNotiImgEN Optional String
            		data.setIdingNotiImgEN(rs.getString("I_DING_NOTI_IMG_EN"));
            		
                	//2.31 idingNotiMsgTopicTH Mandatory String
            		data.setIdingNotiMsgTopicTH(rs.getString("I_DING_NOTI_MSG_TOPIC_TH"));
            		
                	//2.32 idingNotiMsgTopicEN Mandatory String
            		data.setIdingNotiMsgTopicEN(rs.getString("I_DING_NOTI_MSG_TOPIC_EN"));
            		
                	//2.33 idingNotiMsgBodyTH Mandatory String
            		data.setIdingNotiMsgBodyTH(rs.getString("I_DING_NOTI_MSG_BODY_TH"));
            		
                	//2.34 idingNotiMsgBodyEN Mandatory String
            		data.setIdingNotiMsgBodyEN(rs.getString("I_DING_NOTI_MSG_BODY_EN"));
            		
                	//2.35 idingImgTH Mandatory String
            		data.setIdingImgTH(rs.getString("I_DING_IMG_TH"));
            		
                	//2.36 idingImgEN Mandatory String
            		data.setIdingImgEN(rs.getString("I_DING_IMG_EN"));
            		
                	//2.37 idingMsgTopicTH Mandatory String
            		data.setIdingMsgTopicTH(rs.getString("I_DING_MSG_TOPIC_TH"));
            		
                	//2.38 idingMsgTopicEN Mandatory String
            		data.setIdingMsgTopicEN(rs.getString("I_DING_MSG_TOPIC_EN"));
            		
                	//2.39 idingMsgBodyTH Mandatory String
            		data.setIdingMsgBodyTH(rs.getString("I_DING_MSG_BODY_TH"));
            		
                	//2.40 idingMsgBodyEN Mandatory String
            		data.setIdingMsgBodyEN(rs.getString("I_DING_MSG_BODY_EN"));
            		
                	//2.41 idingMsgHighLightTH Optional String
            		data.setIdingMsgHighLightTH(rs.getString("I_DING_MSG_HIGH_LIGHT_TH"));
            		
                	//2.42 idingMsgHighLightEN Optional String
            		data.setIdingMsgHighLightEN(rs.getString("I_DING_MSG_HIGH_LIGHT_EN"));
            		
                	//2.43 idingMsgFooterTH Optional String
            		data.setIdingMsgFooterTH(rs.getString("I_DING_MSG_FOOTER_TH"));
            		
                	//2.44 idingMsgFooterEN Optional String
            		data.setIdingMsgFooterEN(rs.getString("I_DING_MSG_FOOTER_EN"));
            		
                	//2.45 landingPageImgTH Mandatory String Array
            		//landingPageImgTHStr = rs.getString("LANDING_PAGE_IMG_TH");
            		//if(StringUtils.isNotEmpty(landingPageImgTHStr)) {
            		//	landingPageImgTHArr = landingPageImgTHStr.split(",");
            		//	data.setLandingPageImgTH(Arrays.asList(landingPageImgTHArr));
            		//}
            		
                	//2.46 landingPageImgEN Mandatory String Array
            		//landingPageImgENStr = rs.getString("LANDING_PAGE_IMG_EN");
            		//if(StringUtils.isNotEmpty(landingPageImgENStr)) {
            		//	landingPageImgENArr = landingPageImgENStr.split(",");
            		//	data.setLandingPageImgEN(Arrays.asList(landingPageImgENArr));
            		//}
            		
                	//2.47 landingPageAuthorTH Mandatory String
            		data.setLandingPageAuthorTH(rs.getString("LANDING_PAGE_AUTHOR_TH"));
            		
                	//2.48 landingPageAuthorEN Mandatory String
            		data.setLandingPageAuthorEN(rs.getString("LANDING_PAGE_AUTHOR_EN"));
            		
                	//2.49 landingPageAuthorIconTH Mandatory String
            		data.setLandingPageAuthorIconTH(rs.getString("LANDING_PAGE_AUTHOR_ICON_TH"));
            		
                	//2.50 landingPageAuthorIconEN Mandatory String
            		data.setLandingPageAuthorIconEN(rs.getString("LANDING_PAGE_AUTHOR_ICON_EN"));
            		
                	//2.51 landingPageMsgTopicTH Mandatory String
            		data.setLandingPageMsgTopicTH(rs.getString("LANDING_PAGE_MSG_TOPIC_TH"));
            		
                	//2.52 landingPageMsgTopicEN Mandatory String
            		data.setLandingPageMsgTopicEN(rs.getString("LANDING_PAGE_MSG_TOPIC_EN"));
            		
                	//2.53 landingPageMsgBodyTH Mandatory JSON Object
            		if(StringUtils.isNotEmpty(rs.getString("LANDING_PAGE_MSG_BODY_TH"))) {
                		parser = new JsonParser();
                		JsonObject landingPageMsgBodyTHObj = parser.parse(rs.getString("LANDING_PAGE_MSG_BODY_TH")).getAsJsonObject();
                		data.setLandingPageMsgBodyTH(landingPageMsgBodyTHObj);
                	}
            		
                	//2.54 landingPageMsgBodyEN Mandatory JSON Object
            		if(StringUtils.isNotEmpty(rs.getString("LANDING_PAGE_MSG_BODY_EN"))) {
                		parser = new JsonParser();
                		JsonObject landingPageMsgBodyENObj = parser.parse(rs.getString("LANDING_PAGE_MSG_BODY_EN")).getAsJsonObject();
                		data.setLandingPageMsgBodyEN(landingPageMsgBodyENObj);
                	}
            		
                	//2.55 landingPageTemplateID Mandatory String
            		data.setLandingPageTemplateID(rs.getString("LANDING_PAGE_TEMPLATE_ID"));
            		
                	//2.56 landingPageButtonLabelSet Optional String
            		data.setLandingPageButtonLabelSet(rs.getString("LANDING_PAGE_BUTTON_LABEL_SET"));
            		
            		//campaignCode String
            		data.setCampaignCode(rs.getString("CAMPAIGN_CODE"));
            		
            		//score String
            		data.setScore(rs.getString("SCORE"));
            		
            		//customerIndicator String
            		data.setCustomerIndicator(rs.getString("CUSTOMER_INDICATOR"));
            		
            		//hashtag String
            		data.setHashtag(rs.getString("HASHTAG"));
            		
            		//contentCategory String
            		data.setContentCategory(rs.getString("CONTENT_CATEGORY"));
            		
            		//authType Integer
            		data.setAuthType(rs.getInt("AUTH_TYPE"));
            		
            		//command String
            		data.setCommand(rs.getString("COMMAND"));
            		
            		//parameter String(JsonObj)
            		if(StringUtils.isNotEmpty(rs.getString("PARAMETER"))) {
                		parser = new JsonParser();
                		JsonObject parameterObj = parser.parse(rs.getString("PARAMETER")).getAsJsonObject();
                		data.setParameter(parameterObj);
                	}
            		
            		//trackingID String
            		data.setTrackingID(rs.getString("TRACKING_ID"));

                	msg.setHeader(header);
                	msg.setData(data);
                	msgTemplatesLoc.put(templateId, msg);
                }
                logger.debug("msgTemplatesLoc.size = "+msgTemplatesLoc.size());
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            
        	msgTemplates = msgTemplatesLoc;
        } finally {
            logger.info("generateMsgTemplates:end");
        }

        return msgTemplates;
	}
	
	public static Message generateMsgTemplates(String searchTemplateId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		logger.info("generateMsgTemplates:begin");
		Message msg = new Message();
        try {
        	Connection con = null;
            PreparedStatement pre = null;
            ResultSet rs = null;
            
            //Suspended attributes as of designed column from Avalant as of 20190204
            String landingPageImgTHStr;
            String[] landingPageImgTHArr;
            String landingPageImgENStr;
            String[] landingPageImgENArr;
            try {
                con = getConnection();
                String stmtStr = "SELECT MS_SMS_TEMPLATE_MASTER.*, to_char(sysdate + plus_expire_date, 'YYYYMMDDHH24MI') as EXPIRE_DATE FROM MS_SMS_TEMPLATE_MASTER WHERE ENABLED='E' AND TEMPLATE_ID = ? ORDER BY TEMPLATE_ID ";
                
                pre = con.prepareStatement(stmtStr);
                pre.setString(1, searchTemplateId);
                rs = pre.executeQuery();

                String templateId;
                JsonParser parser;
                if (rs.next()) {
                	templateId = rs.getString("TEMPLATE_ID");
                	
                	//header
                	MessageHeader header = new MessageHeader();
                	
                	//data
                	MessageData data = new MessageData();
                	
                	//2.13 imodeFlag Mandatory Integer
            		data.setImodeFlag(rs.getInt("I_MODE_FLAG"));
                	
                	//2.14 idingFlag Mandatory Integer
            		data.setIdingFlag(rs.getInt("I_DING_FLAG"));
                	
                	//2.15 landingFlag Mandatory Integer
            		data.setLandingFlag(rs.getInt("LANDING_FLAG"));
            		
                	//2.16 iotherType Mandatory Integer
            		data.setIotherType(rs.getInt("I_OTHER_TYPE"));
            		
                	//2.19 imodeBadgeTH Mandatory String
            		data.setImodeBadgeTH(rs.getString("I_MODE_BADGE_TH"));
            		
                	//2.20 imodeBadgeEN Mandatory String
            		data.setImodeBadgeEN(rs.getString("I_MODE_BADGE_EN"));
            		
                	//2.21 imodeMsgTopicTH Mandatory String
            		data.setImodeMsgTopicTH(rs.getString("I_MODE_MSG_TOPIC_TH"));
            		
                	//2.22 imodeMsgTopicEN Mandatory String
            		data.setImodeMsgTopicEN(rs.getString("I_MODE_MSG_TOPIC_EN"));
            		
                	//2.23 imodeMsgBodyTH Mandatory String
            		data.setImodeMsgBodyTH(rs.getString("I_MODE_MSG_BODY_TH"));
            		
                	//2.24 imodeMsgBodyEN Mandatory String
            		data.setImodeMsgBodyEN(rs.getString("I_MODE_MSG_BODY_EN"));
            		
                	//2.25 imodeMsgHighLightTH Optional String
            		data.setImodeMsgHighLightTH(rs.getString("I_MODE_MSG_HIGH_LIGHT_TH"));
            		
                	//2.26 imodeMsgHighLightEN Optional String
            		data.setImodeMsgHighLightEN(rs.getString("I_MODE_MSG_HIGH_LIGHT_EN"));
            		
                	//2.27 imodeMsgFooterTH Optional String
            		data.setImodeMsgFooterTH(rs.getString("I_MODE_MSG_FOOTER_TH"));
            		
                	//2.28 imodeMsgFooterEN Optional String
            		data.setImodeMsgFooterEN(rs.getString("I_MODE_MSG_FOOTER_EN"));
            		
                	//2.29 idingNotiImgTH Optional String
            		data.setIdingNotiImgTH(rs.getString("I_DING_NOTI_IMG_TH"));
            		
                	//2.30 idingNotiImgEN Optional String
            		data.setIdingNotiImgEN(rs.getString("I_DING_NOTI_IMG_EN"));
            		
                	//2.31 idingNotiMsgTopicTH Mandatory String
            		data.setIdingNotiMsgTopicTH(rs.getString("I_DING_NOTI_MSG_TOPIC_TH"));
            		
                	//2.32 idingNotiMsgTopicEN Mandatory String
            		data.setIdingNotiMsgTopicEN(rs.getString("I_DING_NOTI_MSG_TOPIC_EN"));
            		
                	//2.33 idingNotiMsgBodyTH Mandatory String
            		data.setIdingNotiMsgBodyTH(rs.getString("I_DING_NOTI_MSG_BODY_TH"));
            		
                	//2.34 idingNotiMsgBodyEN Mandatory String
            		data.setIdingNotiMsgBodyEN(rs.getString("I_DING_NOTI_MSG_BODY_EN"));
            		
                	//2.35 idingImgTH Mandatory String
            		data.setIdingImgTH(rs.getString("I_DING_IMG_TH"));
            		
                	//2.36 idingImgEN Mandatory String
            		data.setIdingImgEN(rs.getString("I_DING_IMG_EN"));
            		
                	//2.37 idingMsgTopicTH Mandatory String
            		data.setIdingMsgTopicTH(rs.getString("I_DING_MSG_TOPIC_TH"));
            		
                	//2.38 idingMsgTopicEN Mandatory String
            		data.setIdingMsgTopicEN(rs.getString("I_DING_MSG_TOPIC_EN"));
            		
                	//2.39 idingMsgBodyTH Mandatory String
            		data.setIdingMsgBodyTH(rs.getString("I_DING_MSG_BODY_TH"));
            		
                	//2.40 idingMsgBodyEN Mandatory String
            		data.setIdingMsgBodyEN(rs.getString("I_DING_MSG_BODY_EN"));
            		
                	//2.41 idingMsgHighLightTH Optional String
            		data.setIdingMsgHighLightTH(rs.getString("I_DING_MSG_HIGH_LIGHT_TH"));
            		
                	//2.42 idingMsgHighLightEN Optional String
            		data.setIdingMsgHighLightEN(rs.getString("I_DING_MSG_HIGH_LIGHT_EN"));
            		
                	//2.43 idingMsgFooterTH Optional String
            		data.setIdingMsgFooterTH(rs.getString("I_DING_MSG_FOOTER_TH"));
            		
                	//2.44 idingMsgFooterEN Optional String
            		data.setIdingMsgFooterEN(rs.getString("I_DING_MSG_FOOTER_EN"));
            		
                	//2.45 landingPageImgTH Mandatory String Array
            		landingPageImgTHStr = rs.getString("LANDING_PAGE_IMG_TH");
            		if(StringUtils.isNotEmpty(landingPageImgTHStr)) {
            			landingPageImgTHArr = landingPageImgTHStr.split(",");
            			data.setLandingPageImgTH(Arrays.asList(landingPageImgTHArr));
            		}
            		
                	//2.46 landingPageImgEN Mandatory String Array
            		landingPageImgENStr = rs.getString("LANDING_PAGE_IMG_EN");
            		if(StringUtils.isNotEmpty(landingPageImgENStr)) {
            			landingPageImgENArr = landingPageImgENStr.split(",");
            			data.setLandingPageImgEN(Arrays.asList(landingPageImgENArr));
            		}
            		
                	//2.47 landingPageAuthorTH Mandatory String
            		data.setLandingPageAuthorTH(rs.getString("LANDING_PAGE_AUTHOR_TH"));
            		
                	//2.48 landingPageAuthorEN Mandatory String
            		data.setLandingPageAuthorEN(rs.getString("LANDING_PAGE_AUTHOR_EN"));
            		
                	//2.49 landingPageAuthorIconTH Mandatory String
            		data.setLandingPageAuthorIconTH(rs.getString("LANDING_PAGE_AUTHOR_ICON_TH"));
            		
                	//2.50 landingPageAuthorIconEN Mandatory String
            		data.setLandingPageAuthorIconEN(rs.getString("LANDING_PAGE_AUTHOR_ICON_EN"));
            		
                	//2.51 landingPageMsgTopicTH Mandatory String
            		data.setLandingPageMsgTopicTH(rs.getString("LANDING_PAGE_MSG_TOPIC_TH"));
            		
                	//2.52 landingPageMsgTopicEN Mandatory String
            		data.setLandingPageMsgTopicEN(rs.getString("LANDING_PAGE_MSG_TOPIC_EN"));
            		
                	//2.53 landingPageMsgBodyTH Mandatory JSON Object
            		if(StringUtils.isNotEmpty(rs.getString("LANDING_PAGE_MSG_BODY_TH"))) {
                		parser = new JsonParser();
                		JsonObject landingPageMsgBodyTHObj = parser.parse(rs.getString("LANDING_PAGE_MSG_BODY_TH")).getAsJsonObject();
                		data.setLandingPageMsgBodyTH(landingPageMsgBodyTHObj);
                	}
            		
                	//2.54 landingPageMsgBodyEN Mandatory JSON Object
            		if(StringUtils.isNotEmpty(rs.getString("LANDING_PAGE_MSG_BODY_EN"))) {
                		parser = new JsonParser();
                		JsonObject landingPageMsgBodyENObj = parser.parse(rs.getString("LANDING_PAGE_MSG_BODY_EN")).getAsJsonObject();
                		data.setLandingPageMsgBodyEN(landingPageMsgBodyENObj);
                	}
            		
                	//2.55 landingPageTemplateID Mandatory String
            		data.setLandingPageTemplateID(rs.getString("LANDING_PAGE_TEMPLATE_ID"));
            		
                	//2.56 landingPageButtonLabelSet Optional String
            		data.setLandingPageButtonLabelSet(rs.getString("LANDING_PAGE_BUTTON_LABEL_SET"));
            		
            		//campaignCode String
            		data.setCampaignCode(rs.getString("CAMPAIGN_CODE"));
            		
            		//score String
            		data.setScore(rs.getString("SCORE"));
            		
            		//customerIndicator String
            		data.setCustomerIndicator(rs.getString("CUSTOMER_INDICATOR"));
            		
            		//hashtag String
            		data.setHashtag(rs.getString("HASHTAG"));
            		
            		//contentCategory String
            		data.setContentCategory(rs.getString("CONTENT_CATEGORY"));
            		
            		//authType Integer
            		data.setAuthType(rs.getInt("AUTH_TYPE"));
            		
            		//command String
            		data.setCommand(rs.getString("COMMAND"));
            		
            		//parameter String(JsonObj)
            		if(StringUtils.isNotEmpty(rs.getString("PARAMETER"))) {
                		parser = new JsonParser();
                		JsonObject parameterObj = parser.parse(rs.getString("PARAMETER")).getAsJsonObject();
                		data.setParameter(parameterObj);
                	}
            		
            		//trackingID String
            		data.setTrackingID(rs.getString("TRACKING_ID"));
            		data.setActionExpireDate(rs.getString("EXPIRE_DATE"));

                	msg.setHeader(header);
                	msg.setData(data);
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        } finally {
            logger.info("generateMsgTemplates:end");
        }

        return msg;
	}
	
	private static Map<String, Message> generateMsgTemplates_Dummy() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
    	return EAppDummyTemplateGen.getDummyMsgTemplates();
	}
	
	private static Map<String, Message> generateMsgTemplates_V2() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		logger.info("generateMsgTemplates_V2:begin");
		Map<String, Message>  msgTemplatesLoc = new HashMap<>();
		
		
        try {
        	
        	Connection con = null;
            PreparedStatement pre = null;
            ResultSet rs = null;
            try {
                con = getConnection();
                String stmtStr = "SELECT * FROM ORIG_APP.MS_SMS_TEMPLATE_MASTER_V2";
                
                pre = con.prepareStatement(stmtStr);
                rs = pre.executeQuery();

                Message msg;
                String templateId;
                JsonParser parser;
                while (rs.next()) {
                	msg = new Message();
                	templateId = rs.getString("template_id");
                	
                	//header
                	MessageHeader header = new MessageHeader();
                	
                	//data
                	MessageData data = new MessageData();
                	
                	//Put value message_en into landingPageMsgBodyTH, change this to real column later.
                	if(StringUtils.isNotEmpty(rs.getString("message_en"))) {
                		parser = new JsonParser();
                		JsonObject landingPageMsgBodyTHObj = parser.parse(rs.getString("message_en")).getAsJsonObject();
                		data.setLandingPageMsgBodyTH(landingPageMsgBodyTHObj);
                	}
                	
                	
                	msg.setHeader(header);
                	msg.setData(data);
                	msgTemplatesLoc.put(templateId, msg);
                }
                logger.debug("msgTemplatesLoc.size = "+msgTemplatesLoc.size());
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            
        	msgTemplates = msgTemplatesLoc;
        } finally {
            logger.info("generateMsgTemplates_V2:end");
        }

        return msgTemplates;
	}
	
	public static HashMap<String, String> getArtworkByCardType(String cardTypeId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		logger.info("getArtworkByCardType:begin");
		HashMap<String, String> artworkMap = new HashMap<>();
		String ARTWORK_KPLUS = "ARTWORK_KPLUS";
		String ARTWORK_LANDING_PAGE = "ARTWORK_LANDING_PAGE";
		String ARTWORK_PRODUCT = "ARTWORK_PRODUCT";
        try {
        	Connection con = null;
            PreparedStatement pre = null;
            ResultSet rs = null;
            
            try {
                con = getConnection();
                String stmtStr = "SELECT * FROM CARD_TYPE WHERE ACTIVE_STATUS = 'A' AND CARD_TYPE_ID = ? ";
                
                pre = con.prepareStatement(stmtStr);
                pre.setString(1, cardTypeId);
                rs = pre.executeQuery();

                if (rs.next()) {
                	artworkMap.put(ARTWORK_KPLUS, rs.getString(ARTWORK_KPLUS));
                	artworkMap.put(ARTWORK_LANDING_PAGE, rs.getString(ARTWORK_LANDING_PAGE));
                	artworkMap.put(ARTWORK_PRODUCT, rs.getString(ARTWORK_PRODUCT));
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        } finally {
            logger.info("getArtworkByCardType:end");
        }

        return artworkMap;
	}
	
}
