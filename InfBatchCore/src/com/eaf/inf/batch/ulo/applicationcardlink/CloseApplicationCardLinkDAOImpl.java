package com.eaf.inf.batch.ulo.applicationcardlink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.display.Formatter.Format;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class CloseApplicationCardLinkDAOImpl  extends InfBatchObjectDAO  implements CloseApplicationCardLinkDAO {
	private static transient Logger logger = Logger.getLogger(CloseApplicationCardLinkDAOImpl.class);
	static String CLOSE_APPLICATION_CARD_LINK_MODULE_ID = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_MODULE_ID");
	@Override
	public ArrayList<TaskObjectDataM> selectApplicationCardlinkMLP() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		String[] JOBSTATES = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_JOB_STATE_CONDITION").split(",");
		String[] STATUS = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_FAIL").split(",");
		String COMMA ="";
		InfDAO infDAO = InfFactory.getInfDAO();
		InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
		infBatchLog.setInterfaceCode("MLP");
		ArrayList<String> applicationGroupIdList = new ArrayList<String>();
		
		try{
			conn = getConnection(InfBatchServiceLocator.MLP_DB);
			conn.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT APP_GROUP.APPLICATION_GROUP_ID, AP.APPLICATION_RECORD_ID, ");
			sql.append(" AP.CARDLINK_SEQ, AP.CARDLINK_REF_NO, AP.APPLICATION_NO,  ");
			sql.append(" AP.CARDLINK_FLAG, RS.REMARK, ");
			sql.append(" CARD.CARD_NO, CARD.CARD_NO_MARK  ");
			sql.append(" FROM ORIG_APPLICATION_GROUP APP_GROUP ");
			sql.append(" JOIN ORIG_APPLICATION AP ON AP.APPLICATION_GROUP_ID = APP_GROUP.APPLICATION_GROUP_ID ");
			sql.append(" JOIN BUSINESS_CLASS BCLS ON BCLS.BUS_CLASS_ID = AP.BUSINESS_CLASS_ID AND BCLS.ORG_ID IN ('CC','KEC') ");
			sql.append(" JOIN ORIG_LOAN LN ON LN.APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID ");
			sql.append(" JOIN ORIG_CARD CARD ON CARD.LOAN_ID = LN.LOAN_ID ");
			sql.append(" LEFT JOIN ORIG_REASON RS ON AP.APPLICATION_RECORD_ID = RS.APPLICATION_RECORD_ID  ");
			sql.append(" WHERE NOT EXISTS (SELECT 1 FROM ORIG_APPLICATION APP WHERE APP.CARDLINK_FLAG IN ( ");
			if(null!=STATUS){
				for (int i = 0, count = STATUS.length ; i < count ; i++) {
						sql.append(COMMA+"?");
						COMMA = ",";
				}
			}
			sql.append(" )");
			sql.append(" AND APP.APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID)");
			sql.append(" AND  APP_GROUP.JOB_STATE IN (");
			COMMA ="";
			if(null!=JOBSTATES){
				for (int i = 0, count = JOBSTATES.length ; i < count ; i++) {
						sql.append(COMMA+"?");
						COMMA = ",";
				}
			}
			sql.append(" )");
			sql.append(" AND NOT EXISTS (");
			sql.append(" SELECT 1 FROM INF_BATCH_LOG BLOG WHERE BLOG.APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID ");
			sql.append(" AND BLOG.INTERFACE_STATUS = 'C' AND BLOG.INTERFACE_CODE = 'MLP' )");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;	
			for(String status : STATUS){
				logger.debug("status >> "+status);
				ps.setString(index++,status); 							 
			}
			for(String jobState : JOBSTATES){
				logger.debug("jobState >> "+jobState);
				ps.setString(index++,jobState); 							 
			}
			rs = ps.executeQuery();
			String outputText = "";
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("H01");
			stringBuilder.append("|");
			stringBuilder.append(Formatter.format(InfBatchProperty.getTimestamp(), "yyyy-MM-dd'T'HH:mm:ss.SSZ")); //YYYY-MM-DDThh:mm:ss[.s[s*]][TZD] 
			stringBuilder.append("|");
			stringBuilder.append(Formatter.format(InfBatchProperty.getDate(), Format.yyyy_MM_dd)); //YYYY-MM-DD
			stringBuilder.append("|");
			stringBuilder.append("739"); //SrcAppId
			stringBuilder.append("|");
			stringBuilder.append("CARDSETUPRESULT");
			stringBuilder.append("|");
			stringBuilder.append("001"); //FileSeqNum
			stringBuilder.append("\n");
			
			int count = 0;
			while(rs.next()) {
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");	
				String CARDLINK_FLAG = rs.getString("CARDLINK_FLAG");
				String REMARK_2 = rs.getString("REMARK");
				logger.debug("APPLICATION_GROUP_ID >> "+APPLICATION_GROUP_ID);
				
				if(!applicationGroupIdList.contains(APPLICATION_GROUP_ID))
				{
					applicationGroupIdList.add(APPLICATION_GROUP_ID);
					updateMLPPostApproved(APPLICATION_GROUP_ID, conn);
				}
				
				stringBuilder.append("B01");
				stringBuilder.append("|");
				stringBuilder.append(rs.getString("APPLICATION_NO")); //Application No (product level)
				stringBuilder.append("|");
				stringBuilder.append(rs.getString("CARD_NO")); //CC/KEC card number in encrypted form
				stringBuilder.append("|");
				stringBuilder.append(rs.getString("CARD_NO_MARK")); //CC/KEC card number in masking form
				stringBuilder.append("|");
				stringBuilder.append(("S".equals(CARDLINK_FLAG)) ? "00" : "10"); //Card setup status
				stringBuilder.append("|");
				stringBuilder.append(""); //Remark field 1
				stringBuilder.append("|");
				stringBuilder.append(("S".equals(CARDLINK_FLAG)) ? "" : REMARK_2); //Remark field 2
				stringBuilder.append("\n");
				
				infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
				infBatchLog.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				infBatchLog.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				infBatchLog.setRefId(rs.getString("CARDLINK_REF_NO"));
				infBatchLog.setRefSeq(rs.getString("CARDLINK_SEQ"));
				infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
				infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
				infDAO.insertInfBatchLog(infBatchLog, conn);
				
				count++;
			}
			
			stringBuilder.append("T01");
			stringBuilder.append("|");
			stringBuilder.append(StringUtils.leftPad(String.valueOf(count), 9, '0'));
			outputText = stringBuilder.toString();
			
			TaskObjectDataM queueObject = new TaskObjectDataM();
			queueObject = new TaskObjectDataM();
			queueObject.setUniqueId("MLP_" + InfBatchProperty.getTimestamp());
			queueObject.setObject(outputText);
			
			conn.commit();
			
			taskObjects.add(queueObject);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		 return taskObjects;
	}
	
	@Override
	public ArrayList<TaskObjectDataM> selectApplicationCardlink() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		String[] JOBSTATES = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_JOB_STATE_CONDITION").split(",");
		String[] STATUS = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_FAIL").split(",");
		String COMMA ="";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT INSTANT_ID ");
			sql.append(" FROM ORIG_APPLICATION_GROUP  APP_GROUP ");
			sql.append(" JOIN ORIG_APPLICATION AP ON AP.APPLICATION_GROUP_ID = APP_GROUP.APPLICATION_GROUP_ID ");
			sql.append(" JOIN BUSINESS_CLASS BCLS ON BCLS.BUS_CLASS_ID = AP.BUSINESS_CLASS_ID AND BCLS.ORG_ID IN ('CC','KEC') ");
			sql.append(" WHERE NOT EXISTS (SELECT 1 FROM ORIG_APPLICATION APP WHERE APP.CARDLINK_FLAG IN ( ");
			if(null!=STATUS){
				for (int i = 0, count = STATUS.length ; i < count ; i++) {
						sql.append(COMMA+"?");
						COMMA = ",";
				}
			}
			sql.append(" )");
			sql.append("                   AND APP.APPLICATION_GROUP_ID = APP_GROUP.APPLICATION_GROUP_ID)");
			sql.append(" AND  APP_GROUP.JOB_STATE IN (");
			COMMA ="";
			if(null!=JOBSTATES){
				for (int i = 0, count = JOBSTATES.length ; i < count ; i++) {
						sql.append(COMMA+"?");
						COMMA = ",";
				}
			}
			sql.append(" )");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;	
			for(String status : STATUS){
				logger.debug("status >> "+status);
				ps.setString(index++,status); 							 
			}
			for(String jobState : JOBSTATES){
				logger.debug("jobState >> "+jobState);
				ps.setString(index++,jobState); 							 
			}
			rs = ps.executeQuery();
			TaskObjectDataM queueObject = new TaskObjectDataM();
			while(rs.next()) {
				String INSTANT_ID = rs.getString("INSTANT_ID");	
				logger.debug("INSTANT_ID >> "+INSTANT_ID);
				queueObject = new TaskObjectDataM();
				queueObject.setUniqueId(INSTANT_ID);
				taskObjects.add(queueObject);
			}
		 
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		 return taskObjects;
	}
	
	public void updateMLPPostApproved(String applicationGroupId, Connection conn) throws Exception {
		PreparedStatement ps = null;
		String POST_APPROVED = SystemConstant.getConstant("POST_APPROVED");
		String JOBSTATE_POST_APPROVED = SystemConstant.getConstant("JOBSTATE_POST_APPROVED");
		String[] STATUS = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_FAIL").split(",");
		String COMMA ="";
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ORIG_APPLICATION_GROUP ");
			sql.append(" SET APPLICATION_STATUS = ? , JOB_STATE = ?, ");
			sql.append(" UPDATE_DATE = SYSDATE ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			sql.append(" AND NOT EXISTS (SELECT 1 FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION APP ON APP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" AND APP.CARDLINK_FLAG IN ( ");
			if(null!=STATUS){
				for (int i = 0, count = STATUS.length ; i < count ; i++) {
						sql.append(COMMA+"?");
						COMMA = ",";
				}
			}
			sql.append(" )");
			sql.append(" AND AG.APPLICATION_GROUP_ID = ? ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, POST_APPROVED);
			ps.setString(index++, JOBSTATE_POST_APPROVED);
			ps.setString(index++, applicationGroupId);
			for(String status : STATUS)
			{
				ps.setString(index++,status); 							 
			}
			ps.setString(index++, applicationGroupId);
			ps.executeUpdate();
		}catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}
		finally
		{
			try
			{
				closeConnection(ps);
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
	}

}
