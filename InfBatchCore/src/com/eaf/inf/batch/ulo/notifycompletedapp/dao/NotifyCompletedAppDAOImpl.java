package com.eaf.inf.batch.ulo.notifycompletedapp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.notifycompletedapp.model.NotifyCompleteAppDataM;
import com.eaf.inf.batch.ulo.notifycompletedapp.model.NotifyCompletedAppUtil;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class NotifyCompletedAppDAOImpl extends InfBatchObjectDAO implements NotifyCompletedAppDAO {
	private static transient Logger logger = Logger.getLogger(NotifyCompletedAppDAOImpl.class);
	String NOTIFY_COMPLETED_APP_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_INTERFACE_CODE");
	String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_INTERFACE_STATUS_COMPLETE");
	String NOTIFY_COMPLETED_APP_JOB_STATE_END = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_JOB_STATE_END");
	String NOTIFY_COMPLETED_APP_JOB_STATE_REJECTED = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_JOB_STATE_REJECTED");
	String IS_VETO_ELIGIBLE_YES = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_IS_VETO_ELIGIBLE_YES");
	String IS_VETO_ELIGIBLE_NO = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_IS_VETO_ELIGIBLE_NO");
	String MIN_LIFE_CYCLE = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_MIN_LIFE_CYCLE");
	String MAX_LIFE_CYCLE = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_MAX_LIFE_CYCLE");
	String RECOMMEND_DECISION_RJ = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_RECOMMEND_DECISION_RJ");
	String NOTIFY_COMPLETED_CONDITION_DAYS = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_CONDITION_DAYS");
	@Override
	public HashMap<String, Object> getNotifyCompletedApp()throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> notifyCompletedData =new HashMap<String, Object>();
		ArrayList<String> Jobstates = new ArrayList<String>(Arrays.asList(NOTIFY_COMPLETED_APP_JOB_STATE_END.split(",")));
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT ");
			sql.append(" 	AG.APPLICATION_GROUP_ID,");
			sql.append(" 	AG.APPLICATION_GROUP_NO,");
			sql.append(" 	AG.JOB_STATE,");
			sql.append(" 	AG.IS_VETO_ELIGIBLE,");
			sql.append(" 	PS.IDNO,");
			sql.append(" 	PS.CID_TYPE,");
			sql.append(" 	PS.PERSONAL_TYPE,");
			sql.append(" 	PS.CIS_NO,");
			sql.append(" 	D.APP_DATE");
			sql.append(" 	FROM  APPLICATION_DATE D, ORIG_APPLICATION_GROUP AG ");
			sql.append(" 	LEFT JOIN ORIG_PERSONAL_INFO PS ON AG.APPLICATION_GROUP_ID = PS.APPLICATION_GROUP_ID AND PS.PERSONAL_TYPE <>'I'");
			sql.append(" 	LEFT JOIN ORIG_APPLICATION A1 ON A1.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID AND A1.LIFE_CYCLE = "+MIN_LIFE_CYCLE+" AND A1.RECOMMEND_DECISION = '"+RECOMMEND_DECISION_RJ+"'");
			sql.append(" 	LEFT JOIN ORIG_APPLICATION A3 ON A3.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID AND A3.LIFE_CYCLE = "+MAX_LIFE_CYCLE+"  AND A3.RECOMMEND_DECISION = '"+RECOMMEND_DECISION_RJ+"'");			 
			sql.append(" 	LEFT JOIN NCB_INFO NCB ON PS.PERSONAL_ID = NCB.PERSONAL_ID ");
			sql.append(" WHERE ((AG.JOB_STATE IN (");
					String COMMA ="";
						for(String jobState : Jobstates){
							sql.append(COMMA+"? ");	
							COMMA=",";
						}					
			sql.append(" )) OR ");
			sql.append(" (AG.JOB_STATE = ? AND AG.IS_VETO_ELIGIBLE = ?) OR ");
			//sql.append(" (AG.JOB_STATE = ? AND AG.IS_VETO_ELIGIBLE = ? AND (A3.LIFE_CYCLE IS NOT NULL OR ( TRUNC(D.APP_DATE) - TRUNC(A1.FINAL_APP_DECISION_DATE) >= ?)))) ");
			sql.append(" (AG.JOB_STATE = ? AND AG.IS_VETO_ELIGIBLE = ? AND (A3.LIFE_CYCLE IS NOT NULL OR ( TRUNC(D.APP_DATE) - NVL(TRUNC(NCB.DATE_OF_SEARCH),TRUNC(A1.FINAL_APP_DECISION_DATE)) >= ?)))) ");
			sql.append(" AND NOT EXISTS (SELECT 1 FROM INF_BATCH_LOG L WHERE L.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID AND L.INTERFACE_CODE = ? AND L.INTERFACE_STATUS=?) ");
			sql.append(" AND  NOT EXISTS (SELECT 1 FROM TABLE( ORIG_APP.F_GET_REPROCESS_APP) RP WHERE AG.APPLICATION_GROUP_ID = RP.APPLICATION_GROUP_ID) ");
			sql.append(" ORDER BY AG.APPLICATION_GROUP_NO ");			
			logger.debug(">>sql>>"+sql);
			logger.debug(">>NOTIFY_COMPLETED_APP_JOB_STATE_END>>"+NOTIFY_COMPLETED_APP_JOB_STATE_END);
			logger.debug(">>NOTIFY_COMPLETED_APP_JOB_STATE_REJECTED>>"+NOTIFY_COMPLETED_APP_JOB_STATE_REJECTED);
			logger.debug(">>NOTIFY_COMPLETED_CONDITION_DAYS>>"+NOTIFY_COMPLETED_CONDITION_DAYS);
			logger.debug(">>NOTIFY_COMPLETED_APP_INTERFACE_CODE>>"+NOTIFY_COMPLETED_APP_INTERFACE_CODE);
			logger.debug(">>NOTIFY_COMPLETED_APP_INTERFACE_CODE>>"+NOTIFY_COMPLETED_APP_INTERFACE_CODE);
			logger.debug(">>INTERFACE_STATUS_COMPLETE>>"+INTERFACE_STATUS_COMPLETE);
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			for(String jobState : Jobstates){
				ps.setString(index++,jobState);
			}
			ps.setString(index++,NOTIFY_COMPLETED_APP_JOB_STATE_REJECTED);
			ps.setString(index++,IS_VETO_ELIGIBLE_NO);
			ps.setString(index++,NOTIFY_COMPLETED_APP_JOB_STATE_REJECTED);
			ps.setString(index++,IS_VETO_ELIGIBLE_YES);
			ps.setInt(index++,Formatter.getInt(NOTIFY_COMPLETED_CONDITION_DAYS));
			ps.setString(index++,NOTIFY_COMPLETED_APP_INTERFACE_CODE); 	
			ps.setString(index++,INTERFACE_STATUS_COMPLETE); 	

			rs = ps.executeQuery();	 
			while(rs.next()) {
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String JOB_STATE = rs.getString("JOB_STATE");
				String IS_VETO_ELIGIBLE = rs.getString("IS_VETO_ELIGIBLE");
				String IDNO = rs.getString("IDNO");
				String CID_TYPE = rs.getString("CID_TYPE");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String CIS_NO = rs.getString("CIS_NO");
				Date  APPLICATION_DATE = rs.getDate("APP_DATE");
				
				HashMap<String,Object> hObject = (HashMap<String,Object>)notifyCompletedData.get(APPLICATION_GROUP_ID);
				if(InfBatchUtil.empty(hObject)){
					hObject = new HashMap<String,Object>();
					hObject.put(NotifyCompletedAppUtil.APPLICATION_GROUP_ID, APPLICATION_GROUP_ID);
					hObject.put(NotifyCompletedAppUtil.APPLICATION_GROUP_NO, APPLICATION_GROUP_NO);
					hObject.put(NotifyCompletedAppUtil.IS_VETO_ELIGIBLE, IS_VETO_ELIGIBLE);
					hObject.put(NotifyCompletedAppUtil.JOB_STATE, JOB_STATE);
					hObject.put(NotifyCompletedAppUtil.APPLICATION_DATE, APPLICATION_DATE);
					notifyCompletedData.put(APPLICATION_GROUP_ID, hObject);
				}	
				
				ArrayList<NotifyCompleteAppDataM> notifyCompleteList = (ArrayList<NotifyCompleteAppDataM>)hObject.get(NotifyCompletedAppUtil.PERSONAL_LIST);
				if(InfBatchUtil.empty(notifyCompleteList)){
					notifyCompleteList = new ArrayList<NotifyCompleteAppDataM>();
					hObject.put(NotifyCompletedAppUtil.PERSONAL_LIST, notifyCompleteList);
				}
				NotifyCompleteAppDataM notifyCompleteAppDataM = new NotifyCompleteAppDataM();
				notifyCompleteAppDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
				notifyCompleteAppDataM.setIdNo(IDNO);
				notifyCompleteAppDataM.setIdType(CID_TYPE);
				notifyCompleteAppDataM.setPersonalType(PERSONAL_TYPE);
				notifyCompleteAppDataM.setCisId(CIS_NO);
				notifyCompleteList.add(notifyCompleteAppDataM);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return notifyCompletedData;
	}
	@Override
	public void insertInfBatchLog(InfBatchLogDataM infBatchLog, Connection conn)throws InfBatchException {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder("");			
			sql.append("INSERT INTO INF_BATCH_LOG ");
			sql.append(" (INTERFACE_LOG_ID, ");
			sql.append(" APPLICATION_GROUP_ID, ");
			sql.append(" APPLICATION_RECORD_ID, ");
			sql.append(" INTERFACE_CODE, ");
			sql.append(" INTERFACE_DATE, ");
			sql.append(" CREATE_DATE, ");
			sql.append(" CREATE_BY, ");
			sql.append(" INTERFACE_STATUS, ");
			sql.append(" REF_ID, ");
			sql.append(" REF_SEQ, ");
			sql.append(" SYSTEM01, ");
			sql.append(" SYSTEM02) ");
			sql.append(" SELECT INF_BATCH_LOG_PK.NEXTVAL, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ? ");
			sql.append(" FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int intdex = 1;
			ps.setString(intdex++,infBatchLog.getApplicationGroupId());
			ps.setString(intdex++,infBatchLog.getApplicationRecordId());
			ps.setString(intdex++,infBatchLog.getInterfaceCode());
			ps.setTimestamp(intdex++,InfBatchProperty.getTimestamp());
			ps.setTimestamp(intdex++,InfBatchProperty.getTimestamp());
			ps.setString(intdex++,InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(intdex++,infBatchLog.getInterfaceStatus());
			ps.setString(intdex++,infBatchLog.getRefId());
			ps.setString(intdex++,infBatchLog.getRefSeq());
			ps.setString(intdex++,infBatchLog.getSystem01());
			ps.setString(intdex++,infBatchLog.getSystem02());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				if (ps != null){
					try{
						ps.close();
					} catch (Exception e) {
						logger.fatal("ERROR",e);
						throw new InfBatchException(e.getLocalizedMessage());
					}
				}
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public Date getApplicationDate() throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		Date applicationDate = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APP_DATE  FROM APPLICATION_DATE");
			 
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());									
			rs = ps.executeQuery();	 
			if(rs.next()) {
				applicationDate = rs.getDate("APP_DATE");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return applicationDate;
	}

	}
