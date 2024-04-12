package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationLogDataM;
import com.eaf.orig.ulo.pl.model.app.PLAuditTrailDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonLogDataM;
import com.eaf.orig.ulo.pl.model.app.search.PLSearchHistoryActionLogDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigApplicationLogDAOImpl extends OrigObjectDAO implements PLOrigApplicationLogDAO {
	
	private static Logger log = Logger.getLogger(PLOrigApplicationLogDAOImpl.class);

	@Override
	public void saveUpdateOrigApplicationLog(Vector<PLApplicationLogDataM> appLogVect, String appRecId)	throws PLOrigApplicationException{		
		try{			
			if (appLogVect != null && appLogVect.size() > 0){				
				for(PLApplicationLogDataM appLogM : appLogVect){					
					String appLogID = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.APP_LOG_ID);
					
					appLogM.setAppLogId(appLogID);					
					appLogM.setLifeCycle(loadLifeCycleFormAppliction(appLogID));
					this.insertTableOrig_Application_Log(appLogM, appLogID);
					
					this.saveUpdateTableOrig_Reason_Log(appLogM.getReasonLogVect(), appLogID);
					this.saveUpdateTableOrig_Audit_trail(appLogM.getAuditLogDataM(), appLogID);					
				}				
			}			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void insertTableOrig_Application_Log(PLApplicationLogDataM appLogM, String appLogId)throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_APPLICATION_LOG ");
			sql.append("( APP_LOG_ID, APPLICATION_RECORD_ID, ACTION, JOB_STATE, CREATE_BY ");
			sql.append(", CREATE_DATE, UPDATE_BY, UPDATE_DATE, APPLICATION_STATUS, CLAIM_DATE ");
			sql.append(", LIFE_CYCLE, ACTION_DESC ) ");
			sql.append(" VALUES(?,?,?,?,?  ,SYSDATE,?,SYSDATE,?,?   ,?,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appLogId);
			ps.setString(2, appLogM.getApplicationRecordID());
			ps.setString(3, appLogM.getAction());
			ps.setString(4, appLogM.getJobState());
			ps.setString(5, appLogM.getCreateBy());
			
			ps.setString(6, appLogM.getUpdateBy());
			ps.setString(7, appLogM.getApplicationStatus());
			ps.setTimestamp(8, appLogM.getClaimDate());
			
			ps.setInt(9, appLogM.getLifeCycle());
			ps.setString(10, appLogM.getActionDesc());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public Vector<PLApplicationLogDataM> loadOrigApplicationLog(String appRecId) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLApplicationLogDataM> appLogVect = new Vector<PLApplicationLogDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APP_LOG_ID, ACTION, JOB_STATE, CREATE_BY, CREATE_DATE ");
			sql.append(", UPDATE_BY, UPDATE_DATE, APPLICATION_STATUS, CLAIM_DATE, LIFE_CYCLE ");
			sql.append(", ACTION_DESC ");
			sql.append(" FROM ORIG_APPLICATION_LOG WHERE APPLICATION_RECORD_ID=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			while (rs.next()) {
				PLApplicationLogDataM appLogM = new PLApplicationLogDataM();
				
				appLogM.setAppLogId(rs.getString(1));
				appLogM.setAction(rs.getString(2));
				appLogM.setJobState(rs.getString(3));
				appLogM.setCreateBy(rs.getString(4));
				appLogM.setCreateDate(rs.getTimestamp(5));
				
				appLogM.setUpdateBy(rs.getString(6));
				appLogM.setUpdatDate(rs.getTimestamp(7));
				appLogM.setApplicationStatus(rs.getString(8));
				
				appLogM.setClaimDate(rs.getTimestamp(9));
				appLogM.setLifeCycle(rs.getInt(10));
				
				appLogM.setActionDesc(rs.getString(11));
				
				appLogM.setReasonLogVect(loadTableOrig_Reason_Log(rs.getString(1)));
				appLogM.setAuditLogDataM(loadTableOrig_Audit_trail(rs.getString(1)));
				
				appLogVect.add(appLogM);
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return appLogVect;
	}
	
	private Vector<PLReasonLogDataM> loadTableOrig_Reason_Log(String appLogId) throws PLOrigApplicationException{		
		try{
			PLOrigReasonLogDAO reasonLogDAO = PLORIGDAOFactory.getPLOrigReasonLogDAO();
			return reasonLogDAO.loadOrigReasonLog(appLogId);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}
	
	private void saveUpdateTableOrig_Reason_Log(Vector<PLReasonLogDataM> reasonLogVect, String appLogId) throws PLOrigApplicationException{		
		try{			
			PLOrigReasonLogDAO reasonLogDAO = PLORIGDAOFactory.getPLOrigReasonLogDAO();
				reasonLogDAO.saveUpdateOrigReasonLog(reasonLogVect, appLogId);		
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}
	
	private Vector<PLAuditTrailDataM> loadTableOrig_Audit_trail(String appLogId) throws PLOrigApplicationException{		
		try{
			PLOrigAuditTrailDAO auditTrailDAO = PLORIGDAOFactory.getPLOrigAuditTrailDAO();
			return auditTrailDAO.loadOrigAuditTrail(appLogId);	
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}
	
	private void saveUpdateTableOrig_Audit_trail(Vector<PLAuditTrailDataM> auditVect, String appLogId) throws PLOrigApplicationException{		
		try{			
			PLOrigAuditTrailDAO auditTrailDAO = PLORIGDAOFactory.getPLOrigAuditTrailDAO();
				auditTrailDAO.saveUpdateOrigAuditTrail(auditVect, appLogId);		
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}

	@Override
	public void saveApplicationLog(PLApplicationLogDataM appLogM, PLApplicationDataM applicationM) throws PLOrigApplicationException{		
		try{			
			String appLogID = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.APP_LOG_ID);
			appLogM.setAppLogId(appLogID);
			appLogM.setLifeCycle(applicationM.getLifeCycle());
			
			if("0".equals(appLogM.getLifeCycle())) {
				PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				appLogM.setLifeCycle(applicationDAO.selectLifeCycle(applicationM.getAppRecordID()));
			}
			
			this.insertTableOrig_Application_Log(appLogM, appLogID);
			
//			#septemwi if save_draft not insert_reasonlog
			if(!OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getSubmitType())){ 
				this.saveUpdateTableOrig_Reason_Log(appLogM.getReasonLogVect(), appLogID);
			}
			
			this.saveUpdateTableOrig_Audit_trail(applicationM.getAuditTrailDataVect(), appLogID);
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}

	@Override
	public void saveTableApplicationLog(PLApplicationLogDataM appLogM) throws PLOrigApplicationException{		
		try{
			String appLogID = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.APP_LOG_ID);
			appLogM.setAppLogId(appLogID);
			appLogM.setLifeCycle(loadLifeCycleFormAppliction(appLogM.getApplicationRecordID()));			
			this.insertTableOrig_Application_Log(appLogM, appLogID);			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}

	@Override
	public Vector<PLSearchHistoryActionLogDataM> loadHistoryActionLog(String appRecId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLSearchHistoryActionLogDataM> searchVect = new Vector<PLSearchHistoryActionLogDataM>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT AL.APPLICATION_STATUS, AL.ACTION, AL.APP_LOG_ID, AL.CREATE_BY, AL.CREATE_DATE,AL.ACTION_DESC, AL.JOB_STATE ");
			sql.append(" FROM ORIG_APPLICATION_LOG AL ");
			sql.append(" WHERE AL.APPLICATION_RECORD_ID = ? ");
			sql.append(" ORDER BY to_number(AL.APP_LOG_ID)");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			while(rs.next()){				
				PLSearchHistoryActionLogDataM searchM = new PLSearchHistoryActionLogDataM();				
				searchM.setStatus(rs.getString(1));
				searchM.setAction(rs.getString(2));
				searchM.setReason(rs.getString(3));
				searchM.setCreateBy(rs.getString(4));
				searchM.setCreateDate(rs.getTimestamp(5));				
				searchM.setActionDesc(rs.getString(6));
				searchM.setJobState(rs.getString(7));
				searchVect.add(searchM);
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return searchVect;
	}
	
	private int loadLifeCycleFormAppliction (String appRecId) throws PLOrigApplicationException{		
		int lifeCycle = 0;		
		PLOrigApplicationDAO origApplicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
		lifeCycle = origApplicationDAO.selectLifeCycle(appRecId);		
		return lifeCycle;		
	}

	@Override
	public String getPreviousAppStatus(String appId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String previousStatus = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_fu_util.get_previous_app_status(?) previous_status from dual");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appId);

			rs = ps.executeQuery();
			if (rs.next()) {
				previousStatus = rs.getString("previous_status");
			}			
			return previousStatus;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
}
