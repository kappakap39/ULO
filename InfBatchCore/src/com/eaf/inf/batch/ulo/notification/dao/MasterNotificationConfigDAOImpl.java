package com.eaf.inf.batch.ulo.notification.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.letter.reject.template.product.GenerateLetterUtil;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.inf.batch.ulo.notification.model.NotificationCriteriaDataM;

public class MasterNotificationConfigDAOImpl extends InfBatchObjectDAO implements MasterNotificationConfigDAO{
	private static transient Logger logger = Logger.getLogger(MasterNotificationConfigDAOImpl.class);
	String DEFUALT_DATA_TYPE_ALL = InfBatchProperty.getInfBatchConfig("DEFUALT_DATA_TYPE_ALL");
	@Override
	public ArrayList<String> getRejectReasons(String templateCode,String saleChannel,String recommendChannel,String sendTime
			,ArrayList<String> notificationTypes)throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> rejectReasonCodes = new ArrayList<String>();
		Connection conn = null;
		logger.debug("notificationTypes : "+notificationTypes);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("	SELECT DISTINCT RE.REASON_CODE	");
			sql.append("	FROM MS_NOTI_APP_TEMPLATE T	");
			sql.append("	INNER JOIN MS_NOTI_GROUP G ON G.GROUP_CODE =T.GROUP_CODE	");
			sql.append("	INNER JOIN MS_NOTI_CHANNEL C ON C.GROUP_CODE =T.GROUP_CODE	");
			sql.append("	        AND ( C.CHANNEL_CODE = ? OR  C.CHANNEL_CODE = ? )	");
			sql.append("	INNER JOIN  MS_NOTI_SENDING S ON S.GROUP_CHANNEL_CODE = C.GROUP_CHANNEL_CODE 	");
			sql.append("	INNER JOIN MS_NOTI_SENDING_DETAIL D ON S.SENDING_ID = D.SENDING_ID	");
			sql.append("	INNER JOIN MS_NOTI_REJECT_REASON RE ON RE.GROUP_CODE = G.GROUP_CODE	");
			sql.append("	LEFT JOIN MS_NOTI_SENDING_PRIORITY P ON P.GROUP_CHANNEL_CODE = S.GROUP_CHANNEL_CODE AND P.SENDING_ID = S.SENDING_ID	");
			sql.append("	LEFT JOIN MS_NOTI_MANAGER_CHANNEL M ON M.CHANNEL_CODE =C.CHANNEL_CODE	");
			sql.append("	WHERE S.SEND_TIME = ?	");
			sql.append("	AND (  T.TEMPLATE_CODE = ? OR  T.TEMPLATE_CODE = ? )	");
			sql.append("    AND S.NOTIFICATION_TYPE IN ( ");
			String comma = "";
			for(String notificationType : notificationTypes){
				sql.append(comma + " ? ");
				comma = ",";
			}
			sql.append(" ) ");
			ps = conn.prepareStatement(sql.toString());						
			int index = 1;
			ps.setString(index++, saleChannel);
			ps.setString(index++, recommendChannel);
			ps.setString(index++, sendTime);
			ps.setString(index++, templateCode);
			ps.setString(index++, DEFUALT_DATA_TYPE_ALL);
			for(String notificationType : notificationTypes){
				ps.setString(index++, notificationType);
			}
			rs = ps.executeQuery();
			while(rs.next()){
				String reasonCode = rs.getString("REASON_CODE");
				rejectReasonCodes.add(reasonCode);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		logger.debug("rejectReasonCodes : "+rejectReasonCodes);
		return rejectReasonCodes;
	}
	@Override
	public void getNotificationPriority(NotificationCriteriaDataM criteria,HashMap<String,String> priorityMap,Connection conn) throws InfBatchException{
		String NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder sql = new StringBuilder();
				sql.append("	SELECT DISTINCT     T.TEMPLATE_CODE,	");
				sql.append("	    RE.REASON_CODE,	");
				sql.append("	    G.CASH_TRANSFER_TYPE,	");
				sql.append("	    P.PRIORITY AS PRIORITY,	");
				sql.append("		S.NOTIFICATION_TYPE,	");
				sql.append("		S.SEND_TIME	");
				sql.append("	FROM MS_NOTI_APP_TEMPLATE T 	");
				sql.append("	INNER JOIN MS_NOTI_GROUP G ON G.GROUP_CODE =T.GROUP_CODE	");
				sql.append("	INNER JOIN MS_NOTI_CHANNEL C ON C.GROUP_CODE =T.GROUP_CODE AND C.CHANNEL_CODE = 'ALL'	");
				sql.append("	INNER JOIN MS_NOTI_SENDING S ON S.GROUP_CHANNEL_CODE = C.GROUP_CHANNEL_CODE	");
				sql.append("	INNER JOIN MS_NOTI_SENDING_DETAIL D ON S.SENDING_ID = D.SENDING_ID	");
				sql.append("	LEFT JOIN MS_NOTI_SENDING_PRIORITY P ON P.GROUP_CHANNEL_CODE = S.GROUP_CHANNEL_CODE AND P.SENDING_ID = S.SENDING_ID	");
				sql.append("	LEFT JOIN MS_NOTI_REJECT_REASON RE ON RE.GROUP_CODE = G.GROUP_CODE	");
				sql.append("	WHERE ( T.TEMPLATE_CODE = ? OR  T.TEMPLATE_CODE = '"+DEFUALT_DATA_TYPE_ALL+"')	");
				sql.append("	    AND G.CASH_TRANSFER_TYPE = ?	");
				sql.append("	    AND S.SEND_TIME = ?	");
				sql.append("		AND RE.REASON_CODE = ? ");
				sql.append("		AND S.NOTIFICATION_TYPE = ? ");
				sql.append("	    AND D.SEND_TO IN (  	");
				String comma = "";
				for(String sendTo : criteria.getSendToList()){
					sql.append(comma+" ? ");
					comma = ",";
				}
				sql.append(" 		 ) ");
			logger.debug("sql : "+sql);
			logger.debug("ApplicationTemplate : "+criteria.getApplicationTemplate());
			logger.debug("CashTransferType : "+criteria.getCashTransferType());
			logger.debug("SendTime : "+criteria.getSendTime());
			logger.debug("ReasonCode : "+criteria.getReasonCode());
			logger.debug("NotificationType : "+criteria.getNotificationType());
			logger.debug("sendTo : "+criteria.getSendToList());
			ps = conn.prepareStatement(sql.toString());						
				int index = 1;
				ps.setString(index++, criteria.getApplicationTemplate());
				ps.setString(index++, criteria.getCashTransferType());
				ps.setString(index++, criteria.getSendTime());
				ps.setString(index++, criteria.getReasonCode());
				ps.setString(index++, criteria.getNotificationType());
				for(String sendTo : criteria.getSendToList()){
					ps.setString(index++, sendTo);
				}
			rs = ps.executeQuery();
			while(rs.next()){
				String NOTIFICATION_TYPE = rs.getString("NOTIFICATION_TYPE");
				String SEND_TIME = rs.getString("SEND_TIME");
				String TEMPLATE_CODE = rs.getString("TEMPLATE_CODE");
				String REASON_CODE = rs.getString("REASON_CODE");
				String CASH_TRANSFER_TYPE = rs.getString("CASH_TRANSFER_TYPE");
				String PRIORITY = rs.getString("PRIORITY");
				if(!InfBatchUtil.empty(SEND_TIME) && NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME.equals(SEND_TIME)){
					PRIORITY = "1";
				}
				if(!InfBatchUtil.empty(TEMPLATE_CODE) && DEFUALT_DATA_TYPE_ALL.equals(TEMPLATE_CODE)){
					TEMPLATE_CODE = criteria.getApplicationTemplate();
				}
				if(!InfBatchUtil.empty(CASH_TRANSFER_TYPE) && DEFUALT_DATA_TYPE_ALL.equals(CASH_TRANSFER_TYPE)){
					CASH_TRANSFER_TYPE = criteria.getCashTransferType();
				}
				String priorityKey = GenerateLetterUtil.generateKeyPriorityRejectLetter(NOTIFICATION_TYPE, SEND_TIME, TEMPLATE_CODE, REASON_CODE, CASH_TRANSFER_TYPE);
				if(!priorityMap.containsKey(priorityKey)){
					priorityMap.put(priorityKey, PRIORITY);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void getNotificationSeller(NotificationCriteriaDataM criteria, HashMap<String, String> sellerMap, Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder sql = new StringBuilder();
				sql.append("	SELECT DISTINCT S.NOTIFICATION_TYPE, S.SEND_TIME, T.TEMPLATE_CODE, RE.REASON_CODE	");
				sql.append("	FROM MS_NOTI_APP_TEMPLATE T 	");
				sql.append("	INNER JOIN MS_NOTI_GROUP G ON G.GROUP_CODE =T.GROUP_CODE	");
				sql.append("	INNER JOIN MS_NOTI_CHANNEL C ON C.GROUP_CODE =T.GROUP_CODE	");
				sql.append("	INNER JOIN MS_NOTI_SENDING S ON S.GROUP_CHANNEL_CODE = C.GROUP_CHANNEL_CODE	");
				sql.append("	INNER JOIN MS_NOTI_SENDING_DETAIL D ON S.SENDING_ID = D.SENDING_ID	");
				sql.append("	INNER JOIN MS_NOTI_REJECT_REASON RE ON RE.GROUP_CODE = G.GROUP_CODE 	");
				sql.append("	WHERE (T.TEMPLATE_CODE = ? OR  T.TEMPLATE_CODE = '"+DEFUALT_DATA_TYPE_ALL+"') 	");
				sql.append("	AND G.HAVE_SALE_FLAG = '"+RejectLetterUtil.Y+"'	");
				//sql.append("	AND ( C.CHANNEL_CODE = ? OR  C.CHANNEL_CODE = '"+DEFUALT_DATA_TYPE_ALL+"' )	");
				sql.append("	AND ( C.CHANNEL_CODE = ?  )	");
				sql.append("	AND G.APPLICATION_STATUS = ? 	");
				sql.append("	AND RE.REASON_CODE = ? 	");
				sql.append("	AND S.NOTIFICATION_TYPE = ? 	");
				sql.append("	AND S.SEND_TIME = ?	");
				sql.append("	AND D.SEND_TO IN (	");
					String comma = "";
					for(String sendTo : criteria.getSendToList()){
						sql.append(comma+" ? ");
						comma = ",";
					}
				sql.append("	)	");
			logger.debug("sql  :"+sql);
			logger.debug("ApplicationTemplate  :"+criteria.getApplicationTemplate());
			logger.debug("ChannelCode  :"+criteria.getChannelCode());
			logger.debug("ApplicationStatus  :"+criteria.getApplicationStatus());
			logger.debug("ReasonCode  :"+criteria.getReasonCode());
			logger.debug("NotificationType  :"+criteria.getNotificationType());
			logger.debug("SendTime  :"+criteria.getSendTime());
			logger.debug("SendToList  :"+criteria.getSendToList());
			ps = conn.prepareStatement(sql.toString());	
				int index = 1;
				ps.setString(index++, criteria.getApplicationTemplate());
				ps.setString(index++, criteria.getChannelCode());
				ps.setString(index++, criteria.getApplicationStatus());
				ps.setString(index++, criteria.getReasonCode());
				ps.setString(index++, criteria.getNotificationType());
				ps.setString(index++, criteria.getSendTime());
				for(String sendTo : criteria.getSendToList()){
					ps.setString(index++, sendTo);
				}
			rs = ps.executeQuery();
			while(rs.next()){
				String NOTIFICATION_TYPE = rs.getString("NOTIFICATION_TYPE");
				String SEND_TIME = rs.getString("SEND_TIME");
				String TEMPLATE_CODE = rs.getString("TEMPLATE_CODE");
				String REASON_CODE = rs.getString("REASON_CODE");
				if(!InfBatchUtil.empty(TEMPLATE_CODE) && DEFUALT_DATA_TYPE_ALL.equals(TEMPLATE_CODE)){
					TEMPLATE_CODE = criteria.getApplicationTemplate();
				}
				String key = GenerateLetterUtil.generateKeyMakePdfSeller(NOTIFICATION_TYPE, SEND_TIME, TEMPLATE_CODE, REASON_CODE);
				if(!sellerMap.containsKey(sellerMap)){
					sellerMap.put(key, NOTIFICATION_TYPE);
				}
			}	
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
}
