package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.PLTrackingDataM;
import com.eaf.orig.ulo.pl.model.app.TrackingDataM;

public class PLOrigTrackingDAOImpl extends OrigObjectDAO implements PLOrigTrackingDAO{	
	static Logger log = Logger.getLogger(PLOrigTrackingDAOImpl.class);
	@Override
	public Vector<PLTrackingDataM> queryTracking(String owner)throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COUNT ('1') NOQUE, WQ.APP_STATUS ");
			sql.append("FROM WF_WORK_QUEUE WQ, ");
			sql.append("USER_ROLE, ROLE, US_USER_DETAIL US WHERE OWNER IS NOT NULL AND ");
			sql.append("USER_ROLE.ROLE_ID = ROLE.ROLE_ID AND US.USER_NAME = USER_ROLE.USER_NAME ");
			sql.append("AND OWNER = USER_ROLE.USER_NAME AND OWNER = ? GROUP BY WQ.OWNER, WQ.APP_STATUS");
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, owner);

			rs = ps.executeQuery();
			Vector<PLTrackingDataM> trackingVect = new Vector<PLTrackingDataM>();
			PLTrackingDataM trackingM = new PLTrackingDataM();
			if (rs.next()) {
				
				trackingM.setCountAction(rs.getInt(1));
				trackingM.setAction(rs.getString(2));
				trackingVect.add(trackingM);
				
			}
			
			return trackingVect;
			
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

	@Override
	public int countUser(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COUNT(DISTINCT(UQ.USER_NAME)) ");
			sql.append("FROM USER_ROLE_JOIN_ROLE UR, USER_WORK_QUEUE UQ, US_USER_DETAIL US ");
			sql.append("WHERE UR.ROLE_NAME = ? AND UQ.USER_NAME = UR.USER_NAME AND UR.USER_NAME = US.USER_NAME");
			
			boolean logNoIsNull = false;
			if ("N".equals(logOn)) {
				logOn = "";
				logNoIsNull = true;
			}
			if (!OrigUtil.isEmptyString(firstName)) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				sql.append(" AND US.LOGON_FLAG = ? ");
			} else if (logNoIsNull) {
				sql.append(" AND US.LOGON_FLAG IS NULL ");
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				sql.append(" AND UQ.ONJOB_FLAG = ? ");
			}
            
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, role);
			
    		if (!OrigUtil.isEmptyString(firstName)) {
				ps.setString(index++, firstName+"%");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				ps.setString(index++, lastName+"%");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				ps.setString(index++, logOn);
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				ps.setString(index++, onJob);
			}
			
			rs = ps.executeQuery();
			if (rs.next()) {
				returnCount = rs.getInt(1);
			}
			
			return returnCount;
			
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

	@Override
	public int countLogOnStatus(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COUNT(LOGON_FLAG) FROM (SELECT DISTINCT (USER_ROLE.USER_NAME) USERN, LOGON_FLAG ");
            sql.append("FROM USER_ROLE, ROLE, US_USER_DETAIL US, WF_WORK_QUEUE, USER_WORK_QUEUE UWQ ");
            sql.append("WHERE USER_ROLE.ROLE_ID = ROLE.ROLE_ID AND ROLE.ROLE_NAME = ? ");
            sql.append("AND US.USER_NAME = USER_ROLE.USER_NAME AND LOGON_FLAG = ? AND UWQ.USER_NAME = US.USER_NAME");
            
            boolean logNoIsNull = false; 
    		if("N".equals(logOn)){
    			logOn = "";
    			logNoIsNull = true;
    		}
            if (!OrigUtil.isEmptyString(firstName)) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				sql.append(" AND US.LOGON_FLAG = ? ");
			} else if(logNoIsNull){
				sql.append(" AND US.LOGON_FLAG IS NULL ");
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				sql.append(" AND UWQ.ONJOB_FLAG = ? ");
			}
			
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, role);
			ps.setString(index++, OrigConstant.FLAG_Y);
			
    		if (!OrigUtil.isEmptyString(firstName)) {
				ps.setString(index++, firstName+"%");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				ps.setString(index++, lastName+"%");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				ps.setString(index++, logOn);
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				ps.setString(index++, onJob);
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				returnCount = rs.getInt(1);
			}
			
			return returnCount;
			
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

	@Override
	public int countOnjobStatus(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COUNT(UQ.USER_NAME) LOGON_FLAG ");
            sql.append("FROM USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US, ");
            sql.append("( SELECT DISTINCT SUQ.USER_NAME, SUQ.ONJOB_FLAG FROM USER_WORK_QUEUE SUQ ) UQ ");
            sql.append("WHERE UR.USER_NAME = US.USER_NAME AND US.USER_NAME = UQ.USER_NAME AND UQ.ONJOB_FLAG = ? AND UR.ROLE_NAME = ?");
            
            boolean logNoIsNull = false; 
    		if("N".equals(logOn)){
    			logOn = "";
    			logNoIsNull = true;
    		}
    		
    		if (!OrigUtil.isEmptyString(firstName)) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				sql.append(" AND US.LOGON_FLAG = ? ");
			} else if(logNoIsNull){
				sql.append(" AND US.LOGON_FLAG IS NULL ");
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				sql.append(" AND UQ.ONJOB_FLAG = ? ");
			}
			
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			
			ps.setString(index++, OrigConstant.FLAG_Y);
			ps.setString(index++, role);
    		
    		if (!OrigUtil.isEmptyString(firstName)) {
				ps.setString(index++, firstName+"%");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				ps.setString(index++, lastName+"%");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				ps.setString(index++, logOn);
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				ps.setString(index++, onJob);
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				returnCount = rs.getInt(1);
			}
			
			return returnCount;
			
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

	@Override
	public int countPreviousJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COUNT(DISTINCT(IH.JOB_ID)) ");
			sql.append("FROM USER_WORK_QUEUE UQ, WF_INSTANT_HISTORY IH, USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US ");
			sql.append("WHERE TRUNC(IH.CREATE_DATE) < TRUNC(SYSDATE) ");
			sql.append("AND ( TRUNC(IH.COMPLETE_DATE) = TRUNC(SYSDATE) OR IH.COMPLETE_DATE IS NULL ) ");
			sql.append("AND IH.APP_STATUS NOT IN ("+WorkflowConstant.StatusPreviousJobEdit.ALL+") AND IH.OWNER = UR.USER_NAME ");
			sql.append("AND UR.USER_NAME = US.USER_NAME AND US.USER_NAME = UQ.USER_NAME AND UR.ROLE_NAME = ? ");
			
            boolean logNoIsNull = false; 
    		if("N".equals(logOn)){
    			logOn = "";
    			logNoIsNull = true;
    		}
    		
    		if (!OrigUtil.isEmptyString(firstName)) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				sql.append(" AND US.LOGON_FLAG = ? ");
			} else if(logNoIsNull){
				sql.append(" AND US.LOGON_FLAG IS NULL ");
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				sql.append(" AND UQ.ONJOB_FLAG = ? ");
			}
			
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, role);
			
    		if (!OrigUtil.isEmptyString(firstName)) {
				ps.setString(index++, firstName+"%");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				ps.setString(index++, lastName+"%");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				ps.setString(index++, logOn);
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				ps.setString(index++, onJob);
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				returnCount = rs.getInt(1);
			}
			
			return returnCount;
			
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

	@Override
	public int countPreviousJobEdit(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COUNT(DISTINCT(IH.JOB_ID)) ");
			sql.append("FROM USER_WORK_QUEUE UQ, WF_INSTANT_HISTORY IH, USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US ");
			sql.append("WHERE TRUNC(IH.CREATE_DATE) < TRUNC(SYSDATE) ");
			sql.append("AND ( TRUNC(IH.COMPLETE_DATE) = TRUNC(SYSDATE) OR IH.COMPLETE_DATE IS NULL ) ");
			sql.append("AND IH.APP_STATUS IN ("+WorkflowConstant.StatusPreviousJobEdit.ALL+") AND IH.OWNER = UR.USER_NAME ");
			sql.append("AND UR.USER_NAME = US.USER_NAME AND US.USER_NAME = UQ.USER_NAME AND UR.ROLE_NAME = ? ");
            
            boolean logNoIsNull = false; 
    		if("N".equals(logOn)){
    			logOn = "";
    			logNoIsNull = true;
    		}
    		
    		if (!OrigUtil.isEmptyString(firstName)) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				sql.append(" AND US.LOGON_FLAG = ? ");
			} else if(logNoIsNull){
				sql.append(" AND US.LOGON_FLAG IS NULL ");
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				sql.append(" AND UQ.ONJOB_FLAG = ? ");
			}
			
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, role);
			
    		if (!OrigUtil.isEmptyString(firstName)) {
				ps.setString(index++, firstName+"%");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				ps.setString(index++, lastName+"%");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				ps.setString(index++, logOn);
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				ps.setString(index++, onJob);
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				returnCount = rs.getInt(1);
			}
			
			return returnCount;
			
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

	@Override
	public int countNewJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COUNT(DISTINCT(IH.JOB_ID)) ");
			sql.append("FROM USER_WORK_QUEUE UQ, WF_INSTANT_HISTORY IH, USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US ");
			sql.append("WHERE TRUNC(IH.CREATE_DATE) = TRUNC(SYSDATE) AND IH.OWNER = UR.USER_NAME ");
			sql.append("AND IH.APP_STATUS NOT IN ("+WorkflowConstant.StatusComplete.ALL+","+ WorkflowConstant.StatusPreviousJobEdit.ALL+","+WorkflowConstant.StatusSendback.ALL+") ");
			sql.append("AND IH.OWNER = UR.USER_NAME AND UR.USER_NAME = US.USER_NAME AND US.USER_NAME = UQ.USER_NAME AND UR.ROLE_NAME = ? ");
            
            boolean logNoIsNull = false; 
    		if("N".equals(logOn)){
    			logOn = "";
    			logNoIsNull = true;
    		}
//    		log.debug("logNoIsNull = "+logNoIsNull);
    		if (!OrigUtil.isEmptyString(firstName)) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				sql.append(" AND US.LOGON_FLAG = ? ");
			} else if(logNoIsNull){
				sql.append(" AND US.LOGON_FLAG IS NULL ");
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				sql.append(" AND UQ.ONJOB_FLAG = ? ");
			}
            
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, role);
			
    		if (!OrigUtil.isEmptyString(firstName)) {
				ps.setString(index++, firstName+"%");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				ps.setString(index++, lastName+"%");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				ps.setString(index++, logOn);
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				ps.setString(index++, onJob);
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				returnCount = rs.getInt(1);
			}
			
			return returnCount;
			
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

	@Override
	public int countSubmitJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("SELECT COUNT(DISTINCT(IH.JOB_ID)) ");
			sql.append("FROM USER_WORK_QUEUE UQ, WF_INSTANT_HISTORY IH, USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US ");
			/**#SeptemWi Modify PROCESS_STATE - 'REJECT',SEND_BACKX To SQL*/
			sql.append("WHERE TRUNC(IH.CREATE_DATE) = TRUNC(SYSDATE) AND IH.PROCESS_STATE IN ('SEND','SENDX','SENDX_M','SEND_BACK','SEND_BACKX','SEND_M','REJECT') ");
			sql.append("AND IH.CREATE_BY= UR.USER_NAME AND IH.ACTION NOT IN ("+WorkflowConstant.ActionSendBack.ALL+") ");
			sql.append("AND UR.USER_NAME = US.USER_NAME AND US.USER_NAME = UQ.USER_NAME AND UR.ROLE_NAME = ? ");
            
            boolean logNoIsNull = false; 
    		if("N".equals(logOn)){
    			logOn = "";
    			logNoIsNull = true;
    		}
    		
    		if (!OrigUtil.isEmptyString(firstName)) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				sql.append(" AND US.LOGON_FLAG = ? ");
			} else if(logNoIsNull){
				sql.append(" AND US.LOGON_FLAG IS NULL ");
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				sql.append(" AND UQ.ONJOB_FLAG = ? ");
			}
			
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, role);
    		
    		if (!OrigUtil.isEmptyString(firstName)) {
				ps.setString(index++, firstName+"%");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				ps.setString(index++, lastName+"%");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				ps.setString(index++, logOn);
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				ps.setString(index++, onJob);
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				returnCount = rs.getInt(1);
			}
			
			return returnCount;
			
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

	@Override
	public int countSubmitEditJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COUNT(DISTINCT(IH.JOB_ID)) ");
			sql.append("FROM USER_WORK_QUEUE UQ, WF_INSTANT_HISTORY IH, USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US ");
			sql.append("WHERE TRUNC(IH.CREATE_DATE) = TRUNC(SYSDATE) AND ACTION IN ("+WorkflowConstant.ActionSendBack.ALL+") ");
			sql.append("AND IH.CREATE_BY= UR.USER_NAME AND IH.OWNER = UR.USER_NAME AND UR.USER_NAME = US.USER_NAME AND US.USER_NAME = UQ.USER_NAME AND UR.ROLE_NAME = ? ");
			
            boolean logNoIsNull = false; 
    		if("N".equals(logOn)){
    			logOn = "";
    			logNoIsNull = true;
    		}
    		
    		if (!OrigUtil.isEmptyString(firstName)) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				sql.append(" AND US.LOGON_FLAG = ? ");
			} else if(logNoIsNull){
				sql.append(" AND US.LOGON_FLAG IS NULL ");
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				sql.append(" AND UQ.ONJOB_FLAG = ? ");
			}
			
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, role);
			
    		if (!OrigUtil.isEmptyString(firstName)) {
				ps.setString(index++, firstName+"%");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				ps.setString(index++, lastName+"%");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				ps.setString(index++, logOn);
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				ps.setString(index++, onJob);
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				returnCount = rs.getInt(1);
			}
			
			return returnCount;
			
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

	@Override
	public int countRemainJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnCount = 0;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COUNT(DISTINCT(WQ.JOB_ID)) ");
			sql.append("FROM USER_WORK_QUEUE UQ, WF_WORK_QUEUE WQ, USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US ");
			sql.append("WHERE WQ.OWNER = UR.USER_NAME AND UR.USER_NAME = US.USER_NAME AND US.USER_NAME = UQ.USER_NAME AND UR.ROLE_NAME = ? ");
			
			boolean logNoIsNull = false; 
    		if("N".equals(logOn)){
    			logOn = "";
    			logNoIsNull = true;
    		}
    		
    		if (!OrigUtil.isEmptyString(firstName)) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				sql.append(" AND US.LOGON_FLAG = ? ");
			} else if(logNoIsNull){
				sql.append(" AND US.LOGON_FLAG IS NULL ");
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				sql.append(" AND UQ.ONJOB_FLAG = ? ");
			}
			
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, role);
			
    		if (!OrigUtil.isEmptyString(firstName)) {
				ps.setString(index++, firstName+"%");
			}
			if (!OrigUtil.isEmptyString(lastName)) {
				ps.setString(index++, lastName+"%");
			}
			if (!OrigUtil.isEmptyString(logOn) && (!logNoIsNull)) {
				ps.setString(index++, logOn);
			}
			if (!OrigUtil.isEmptyString(onJob)) {
				ps.setString(index++, onJob);
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				returnCount = rs.getInt(1);
			}
			
			return returnCount;
			
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

	@Override
	public int countAutoQueue(String role ,String roleWf) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		Connection conn = null;
//		try{
//			conn = getConnection();
//			StringBuilder sql = new StringBuilder("");
//			sql.append(" SELECT COUNT(DISTINCT WQ.JOB_ID) FROM WF_ACTIVITY_TEMPLATE AT,WF_WORK_QUEUE WQ ");
//			sql.append(" WHERE WQ.ATID = AT.ATID AND AT.ACTIVITY_TYPE IN('AQ','CQ') AND AT.ROLE_ID = ? ");			
//			
//			if(ORIGLogic.RoleICDC(role)){
//				sql.append(" AND AT.PTID IN(?) ");
//			}else{
//				sql.append(" AND AT.PTID IN(?,?) ");
//			}
//			
//			String dSql = String.valueOf(sql);
//			ps = conn.prepareStatement(dSql);
//			int index = 1;
//			ps.setString(index++, roleWf);
//			if(ORIGLogic.RoleICDC(role)){
//				ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP002);
//			}else{
//				ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//				ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//			}
//			rs = ps.executeQuery();
//			if(rs.next()){
//				count = rs.getInt(1);
//			}			
//		}catch(Exception e){
//			log.fatal(e.getLocalizedMessage());
//			throw new PLOrigApplicationException(e.getMessage());
//		}finally{
//			try{
//				closeConnection(conn, rs, ps);
//			}catch(Exception e){
//				log.fatal(e.getLocalizedMessage());
//				throw new PLOrigApplicationException(e.getMessage());
//			}
//		}
		return count;
	}
	
	@Override
	public int countInboxAutoQueueSup(String menuId, String role ,String roleWf) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT COUNT(DISTINCT WQ.JOB_ID)");
			sql.append(" FROM WF_ACTIVITY_TEMPLATE AT");
			sql.append(" INNER JOIN WF_WORK_QUEUE WQ ON WQ.ATID = AT.ATID");
			sql.append(" INNER JOIN WF_ACTIVITY_LINK AL ON AL.FROM_ATID = WQ.ATID");
			sql.append(" INNER JOIN WF_TODO_LIST TDL ON TDL.ATID = AL.TO_ATID");
			sql.append(" WHERE WQ.ATID = AT.ATID AND AT.ACTIVITY_TYPE IN('AQ','CQ') AND TDL.TDID = ?");			
			
			String dSql = String.valueOf(sql);
			log.debug("countAutoQueueSup sql:" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			//ps.setString(index++, roleWf);
			ps.setString(index++, menuId);

			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
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
		return count;
	}

	@Override
	public Vector<PLTrackingDataM> trackingAction(String owner, String role) throws PLOrigApplicationException{
		Vector<PLTrackingDataM> trackVect = new Vector<PLTrackingDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DISTINCT COUNT (1), WQ.APP_STATUS ");
			sql.append("FROM WF_WORK_QUEUE WQ, USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US, ");
			sql.append("( SELECT DISTINCT SUQ.USER_NAME, SUQ.ONJOB_FLAG FROM USER_WORK_QUEUE SUQ ) UQ ");
			sql.append("WHERE US.USER_NAME = UR.USER_NAME AND UR.USER_NAME = UQ.USER_NAME AND UQ.USER_NAME = WQ.OWNER ");
			sql.append("AND UR.ROLE_NAME = ? AND UR.USER_NAME = ? GROUP BY WQ.APP_STATUS, UQ.ONJOB_FLAG ORDER BY WQ.APP_STATUS");
			String dSql = String.valueOf(sql);
			//log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, role);
			ps.setString(2, owner);

			rs = ps.executeQuery();
			while (rs.next()) {
				PLTrackingDataM trackM = new PLTrackingDataM();
				trackM.setCountAction(rs.getInt(1));
				trackM.setAction(rs.getString(2));
				trackVect.add(trackM);
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
		return trackVect;
	}
	
	@Override
	public Vector<String[]> loadRenderOnjobFlag (String userName) throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		Connection conn = null;
		Vector<String[]> onJobArr = new Vector<String[]>();		
		try{
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DISTINCT TL.TODO_LIST_NAME, DECODE(UQ.ONJOB_FLAG, 'N', 'Off', 'Y', 'On') FLAG ");
			sql.append("FROM USER_WORK_QUEUE UQ, WF_TODO_LIST TL ");
			sql.append("WHERE UQ.TDID = TL.TDID AND UQ.USER_NAME = ? ORDER BY TL.TODO_LIST_NAME");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userName);

			rs = ps.executeQuery();
			while (rs.next()) {
				String onJob[] = new String[2];
				onJob[0] = rs.getString(1);
				onJob[1] = rs.getString(2);
				onJobArr.add(onJob);
			}
//			log.debug("onJobArr.size()"+onJobArr.size()+" userName = "+userName);			
			
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
		return onJobArr;
	}
	
	@Override
	public int countUser(TrackingDataM trackM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		Connection conn = null;
		try{			
			Vector<ORIGCacheDataM> vRole = trackM.getvRole();
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT COUNT(DISTINCT(UR.USER_NAME)) ");
			sql.append(" FROM USER_ROLE UR, ROLE RO, US_USER_DETAIL US ");			
			sql.append(" WHERE UR.ROLE_ID = RO.ROLE_ID ");
			
			if(trackM.isFixrole()){
				sql.append(" AND RO.ROLE_NAME = ? ");			
			}else{
				if(null != vRole && vRole.size() > 0){					
					sql.append(" AND RO.ROLE_NAME IN (");
					int i = 0;
					for(ORIGCacheDataM dataM : vRole){	
						if(i != 0){
							sql.append(",");
						}
						sql.append("?");
						i++;
					}
					sql.append(" ) ");					
				}			
			}
			
			sql.append(" AND US.USER_NAME = UR.USER_NAME AND US.ACTIVE_STATUS = ?   ");
			
			if(!OrigUtil.isEmptyString(trackM.getFirstName())){
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getLastName())){
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}

			if(OrigConstant.FLAG_Y.equals(trackM.getLogOn())){
				sql.append(" AND US.LOGON_FLAG = ? ");
			}else if(OrigConstant.FLAG_N.equals(trackM.getLogOn())){
				sql.append(" AND (US.LOGON_FLAG = ? OR US.LOGON_FLAG IS NULL) ");
			}
						
			if(!OrigUtil.isEmptyString(trackM.getOnJob())){
				sql.append(" AND EXISTS ( ");
					sql.append(" SELECT ");
					sql.append("     'X' ");
					sql.append(" FROM ");
					sql.append("     USER_WORK_QUEUE, ");
					sql.append("     WF_TODO_LIST_MASTER, ");
					sql.append("     WF_TODO_LIST, ");
					sql.append("     WF_ACTIVITY_TEMPLATE ");
					sql.append(" WHERE ");
					sql.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
					sql.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
					sql.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
					sql.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
					sql.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
					sql.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
					sql.append(" AND USER_WORK_QUEUE.ONJOB_FLAG = ? ");
					if(trackM.isFixrole()){
//						if(ORIGLogic.RoleICDC(trackM.getRole())){
//							sql.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
//						}else{
//							sql.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
//						}
					}
				sql.append(" ) ");
			}else{
				sql.append(" AND EXISTS ( ");
					sql.append(" SELECT ");
					sql.append("     'X' ");
					sql.append(" FROM ");
					sql.append("     USER_WORK_QUEUE, ");
					sql.append("     WF_TODO_LIST_MASTER, ");
					sql.append("     WF_TODO_LIST, ");
					sql.append("     WF_ACTIVITY_TEMPLATE ");
					sql.append(" WHERE ");
					sql.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
					sql.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
					sql.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
					sql.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
					sql.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
					sql.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
					if(trackM.isFixrole()){
//						if(ORIGLogic.RoleICDC(trackM.getRole())){
//							sql.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
//						}else{
//							sql.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
//						}
					}
				sql.append(" ) ");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getEmpID())){
				sql.append(" AND US.USER_NO = ? ");
			}
			
			String dSql = String.valueOf(sql);
			
			logger.debug("dSql >> "+dSql);
			
			ps = conn.prepareStatement(dSql);
			int index = 1;
			
			if(trackM.isFixrole()){
				ps.setString(index++, trackM.getRole());
			}else{
				if(null != vRole && vRole.size() > 0){
					for(ORIGCacheDataM dataM : vRole){
						ps.setString(index++,dataM.getCode());
					}
				}
			}
			
			ps.setString(index++, OrigConstant.ACTIVE_FLAG);
			
			if(!OrigUtil.isEmptyString(trackM.getFirstName())){
				ps.setString(index++, trackM.getFirstName()+"%");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getLastName())){
				ps.setString(index++, trackM.getLastName()+"%");
			}

			if(OrigConstant.FLAG_Y.equals(trackM.getLogOn())){
				ps.setString(index++,OrigConstant.FLAG_Y);
			}else if(OrigConstant.FLAG_N.equals(trackM.getLogOn())){
				ps.setString(index++,OrigConstant.FLAG_N);
			}
						
			if(!OrigUtil.isEmptyString(trackM.getOnJob())){
				ps.setString(index++, trackM.getOnJob());
				if(trackM.isFixrole()){
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//					}else{
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//					}
				}
			}else{
				if(trackM.isFixrole()){
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//					}else{
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//					}
				}
			}
			
			if(!OrigUtil.isEmptyString(trackM.getEmpID())){
				ps.setString(index++,trackM.getEmpID());
			}
			
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}			
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return count;
	}

	@Override
	public String countLogOn(TrackingDataM trackM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = "0/0";
		Connection conn = null;
		try{
			Vector<ORIGCacheDataM> vRole = trackM.getvRole();		
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT NVL(SUM(CASE WHEN US.LOGON_FLAG = 'Y' THEN 1 ELSE 0 END),0) ||'/'|| NVL(SUM(CASE WHEN US.LOGON_FLAG IS NULL OR US.LOGON_FLAG = 'N' THEN 1 ELSE 0 END),0) ");
			sql.append(" FROM USER_ROLE UR, ROLE RO, US_USER_DETAIL US ");			
			sql.append(" WHERE UR.ROLE_ID = RO.ROLE_ID ");
			
			if(trackM.isFixrole()){
				sql.append(" AND RO.ROLE_NAME = ? ");		
			}else{
				if(null != vRole && vRole.size() > 0){					
					sql.append(" AND RO.ROLE_NAME IN (");
					int i = 0;
					for(ORIGCacheDataM dataM : vRole){	
						if(i != 0){
							sql.append(",");
						}
						sql.append("?");
						i++;
					}
					sql.append(" ) ");					
				}			
			}
			
			sql.append(" AND US.USER_NAME = UR.USER_NAME AND US.ACTIVE_STATUS = ? ");
			
			if(!OrigUtil.isEmptyString(trackM.getFirstName())){
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getLastName())){
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
			}

			if(OrigConstant.FLAG_Y.equals(trackM.getLogOn())){
				sql.append(" AND US.LOGON_FLAG = ? ");
			}else if(OrigConstant.FLAG_N.equals(trackM.getLogOn())){
				sql.append(" AND (US.LOGON_FLAG = ? OR US.LOGON_FLAG IS NULL) ");
			}
						

			if(!OrigUtil.isEmptyString(trackM.getOnJob())){
				sql.append(" AND EXISTS ( ");
					sql.append(" SELECT ");
					sql.append("     'X' ");
					sql.append(" FROM ");
					sql.append("     USER_WORK_QUEUE, ");
					sql.append("     WF_TODO_LIST_MASTER, ");
					sql.append("     WF_TODO_LIST, ");
					sql.append("     WF_ACTIVITY_TEMPLATE ");
					sql.append(" WHERE ");
					sql.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
					sql.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
					sql.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
					sql.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
					sql.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
					sql.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
					sql.append(" AND USER_WORK_QUEUE.ONJOB_FLAG = ? ");
					if(trackM.isFixrole()){
//						if(ORIGLogic.RoleICDC(trackM.getRole())){
//							sql.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
//						}else{
//							sql.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
//						}
					}
				sql.append(" ) ");
			}else{
				sql.append(" AND EXISTS ( ");
					sql.append(" SELECT ");
					sql.append("     'X' ");
					sql.append(" FROM ");
					sql.append("     USER_WORK_QUEUE, ");
					sql.append("     WF_TODO_LIST_MASTER, ");
					sql.append("     WF_TODO_LIST, ");
					sql.append("     WF_ACTIVITY_TEMPLATE ");
					sql.append(" WHERE ");
					sql.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
					sql.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
					sql.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
					sql.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
					sql.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
					sql.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
					if(trackM.isFixrole()){
//						if(ORIGLogic.RoleICDC(trackM.getRole())){
//							sql.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
//						}else{
//							sql.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
//						}
					}
				sql.append(" ) ");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getEmpID())){
				sql.append(" AND US.USER_NO = ? ");
			}
			
			String dSql = String.valueOf(sql);
						
			log.debug("countLogOn().. dSql "+dSql);
			
			ps = conn.prepareStatement(dSql);
			int index = 1;
			
			if(trackM.isFixrole()){
				ps.setString(index++, trackM.getRole());
			}else{
				if(null != vRole && vRole.size() > 0){
					for(ORIGCacheDataM dataM : vRole){
						ps.setString(index++,dataM.getCode());
					}
				}
			}			
			
			ps.setString(index++, OrigConstant.ACTIVE_FLAG);
			
			if(!OrigUtil.isEmptyString(trackM.getFirstName())){
				ps.setString(index++, trackM.getFirstName()+"%");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getLastName())){
				ps.setString(index++, trackM.getLastName()+"%");
			}

			if(OrigConstant.FLAG_Y.equals(trackM.getLogOn())){
				ps.setString(index++,OrigConstant.FLAG_Y);
			}else if(OrigConstant.FLAG_N.equals(trackM.getLogOn())){
				ps.setString(index++,OrigConstant.FLAG_N);
			}
						
			if(!OrigUtil.isEmptyString(trackM.getOnJob())){
				ps.setString(index++, trackM.getOnJob());
				if(trackM.isFixrole()){
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//					}else{
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//					}
				}
			}else{
				if(trackM.isFixrole()){
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//					}else{
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//					}
				}
			}
			
			if(!OrigUtil.isEmptyString(trackM.getEmpID())){
				ps.setString(index++,trackM.getEmpID());
			}
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				result = rs.getString(1);
			}			
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public String countOnJob(TrackingDataM trackM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = "0/0";
		Connection conn = null;
		try{			
//			Vector<ORIGCacheDataM> vRole = trackM.getvRole();			
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");			
				SQL.append(" SELECT ");
				SQL.append("     NVL(SUM( ");
				SQL.append("         CASE ");
				SQL.append("             WHEN FLAG = 'Y' ");
				SQL.append("             THEN 1 ");
				SQL.append("             ELSE 0 ");
				SQL.append("         END),0) ||'/'|| NVL(SUM( ");
				SQL.append("         CASE ");
				SQL.append("             WHEN FLAG IS NULL ");
				SQL.append("              OR FLAG = 'N' ");
				SQL.append("             THEN 1 ");
				SQL.append("             ELSE 0 ");
				SQL.append("         END),0) COUNT ");
				SQL.append(" FROM ");
				SQL.append("     ( ");
				SQL.append("         SELECT DISTINCT ");
				SQL.append("             UQ.USER_NAME USER_NAME, ");
				SQL.append("             TLM.TODO_LIST_NAME TODO_LIST_NAME, ");
				SQL.append("             UQ.ONJOB_FLAG FLAG , ");
				SQL.append("             ACT.ROLE_ID ROLE_ID ");
				SQL.append("         FROM ");
				SQL.append("             USER_WORK_QUEUE UQ, ");
				SQL.append("             WF_TODO_LIST TL, ");
				SQL.append("             WF_TODO_LIST_MASTER TLM, ");
				SQL.append("             WF_ACTIVITY_TEMPLATE ACT ");
				SQL.append("         WHERE ");
				SQL.append("             UQ.TDID = TL.TDID ");
				SQL.append("         AND TL.TDID = TLM.TDID ");
				SQL.append("         AND TLM.QUEUE_FLAG = 'Y' ");
				SQL.append("         AND TL.ATID = ACT.ATID ");
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND  ACT.PTID = ? ");
//					}else{
//						SQL.append("  AND  ACT.PTID IN (?,?) ");
//					}
				SQL.append("         ORDER BY ");
				SQL.append("             TLM.TODO_LIST_NAME ");
				SQL.append("     ) ");
				SQL.append("     TMP, ");
				SQL.append("     US_USER_DETAIL US ");
				SQL.append(" WHERE ");
				SQL.append("     US.USER_NAME = TMP.USER_NAME ");
     	
			if(trackM.isFixrole()){
				SQL.append(" AND TMP.ROLE_ID = ? ");

			}else{
//				#septemwi modify
//				if(null != vRole && vRole.size() > 0){					
//					SQL.append(" AND RO.ROLE_NAME IN (");
//					int i = 0;
//					for(ORIGCacheDataM dataM : vRole){	
//						if(i != 0){
//							SQL.append(",");
//						}
//						SQL.append("?");
//						i++;
//					}
//					SQL.append(" ) ");					
//				}			
			}
			
			SQL.append(" AND US.ACTIVE_STATUS = ? ");
			
//			SQL.append(" AND US.USER_NAME = UWQ.USER_NAME ");
			
			if(!OrigUtil.isEmptyString(trackM.getFirstName())){
				SQL.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getLastName())){
				SQL.append(" AND US.THAI_LASTNAME LIKE ? ");
			}

			if(OrigConstant.FLAG_Y.equals(trackM.getLogOn())){
				SQL.append(" AND US.LOGON_FLAG = ? ");
			}else if(OrigConstant.FLAG_N.equals(trackM.getLogOn())){
				SQL.append(" AND (US.LOGON_FLAG = ? OR US.LOGON_FLAG IS NULL) ");
			}
						

			if(!OrigUtil.isEmptyString(trackM.getOnJob())){
				SQL.append(" AND EXISTS ( ");
					SQL.append(" SELECT ");
					SQL.append("     'X' ");
					SQL.append(" FROM ");
					SQL.append("     USER_WORK_QUEUE, ");
					SQL.append("     WF_TODO_LIST_MASTER, ");
					SQL.append("     WF_TODO_LIST, ");
					SQL.append("     WF_ACTIVITY_TEMPLATE ");
					SQL.append(" WHERE ");
					SQL.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
					SQL.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
					SQL.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
					SQL.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
					SQL.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
					SQL.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
					SQL.append(" AND USER_WORK_QUEUE.ONJOB_FLAG = ? ");
					if(trackM.isFixrole()){
//						if(ORIGLogic.RoleICDC(trackM.getRole())){
//							SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
//						}else{
//							SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
//						}
					}
				SQL.append(" ) ");
			}else{
				SQL.append(" AND EXISTS ( ");
					SQL.append(" SELECT ");
					SQL.append("     'X' ");
					SQL.append(" FROM ");
					SQL.append("     USER_WORK_QUEUE, ");
					SQL.append("     WF_TODO_LIST_MASTER, ");
					SQL.append("     WF_TODO_LIST, ");
					SQL.append("     WF_ACTIVITY_TEMPLATE ");
					SQL.append(" WHERE ");
					SQL.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
					SQL.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
					SQL.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
					SQL.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
					SQL.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
					SQL.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
					if(trackM.isFixrole()){
//						if(ORIGLogic.RoleICDC(trackM.getRole())){
//							SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
//						}else{
//							SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
//						}
					}
				SQL.append(" ) ");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getEmpID())){
				SQL.append(" AND US.USER_NO = ? ");
			}
			
			log.debug("SQL >> "+SQL);			
			String dSql = String.valueOf(SQL);
						
			ps = conn.prepareStatement(dSql);
			int index = 1;
			
//			if(ORIGLogic.RoleICDC(trackM.getRole())){
//				ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//			}else{
//				ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//				ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//			}
			
			if(trackM.isFixrole()){
				ps.setString(index++, trackM.getWfRole());
			}else{
//				#septemwi modify
//				if(null != vRole && vRole.size() > 0){
//					for(ORIGCacheDataM dataM : vRole){
//						ps.setString(index++,dataM.getCode());
//					}
//				}
			}
			
			ps.setString(index++, OrigConstant.ACTIVE_FLAG);
			
			if(!OrigUtil.isEmptyString(trackM.getFirstName())){
				ps.setString(index++, trackM.getFirstName()+"%");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getLastName())){
				ps.setString(index++, trackM.getLastName()+"%");
			}

			if(OrigConstant.FLAG_Y.equals(trackM.getLogOn())){
				ps.setString(index++,OrigConstant.FLAG_Y);
			}else if(OrigConstant.FLAG_N.equals(trackM.getLogOn())){
				ps.setString(index++,OrigConstant.FLAG_N);
			}
						
			if(!OrigUtil.isEmptyString(trackM.getOnJob())){
				ps.setString(index++, trackM.getOnJob());
				if(trackM.isFixrole()){
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//					}else{
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//					}
				}
			}else{
				if(trackM.isFixrole()){
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//					}else{
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//					}
				}
			}	
			
			if(!OrigUtil.isEmptyString(trackM.getEmpID())){
				ps.setString(index++,trackM.getEmpID());
			}
			
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getString("COUNT");
			}			
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public HashMap<String, Integer> countWfJob(TrackingDataM trackM) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<ORIGCacheDataM> vRole = trackM.getvRole();
		
		HashMap<String, Integer> hWfJob = new HashMap<String, Integer>();
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
			
				SQL.append(" SELECT ");
				
				SQL.append(" SUM(NVL(PREV_JOB.PREV_JOB,0) + NVL(PREV_BLOCKER.PREV_BLOCKED,0)) PREV_JOB, ");
				SQL.append(" SUM(NVL(PREV_JOB.PREV_EDIT,0)) PREV_EDIT, ");
				
				SQL.append(" SUM(NVL(INPUT.INPUT,0))  INPUT, ");
				SQL.append(" SUM(CASE WHEN NVL(OUTPUT.OUTPUT,0)  ");
				SQL.append("                 - NVL(SEND_BACK.SEND_BACK,0)  ");
				SQL.append("                 -NVL(REASSIGN.REASSIGN,0)  ");
				SQL.append("                 -(NVL(BLOCKER.BLOCKED,NVL(PREV_BLOCKER.PREV_BLOCKED,0)) -NVL(PREV_BLOCKER.PREV_BLOCKED,0) )  ");
				SQL.append("                 - NVL(CANCEL.CANCELED,0) > 0  ");
				SQL.append("     THEN NVL(OUTPUT.OUTPUT,0)  ");
				SQL.append("                 - NVL(SEND_BACK.SEND_BACK,0)  ");
				SQL.append("                 -NVL(REASSIGN.REASSIGN,0)  ");
				SQL.append("                 -(NVL(BLOCKER.BLOCKED,NVL(PREV_BLOCKER.PREV_BLOCKED,0)) -NVL(PREV_BLOCKER.PREV_BLOCKED,0) )  ");
				SQL.append("                 - NVL(CANCEL.CANCELED,0) ");
				SQL.append("     ELSE 0 END ) OUTPUT, ");
				
				SQL.append(" SUM(NVL(SEND_BACK.SEND_BACK,0)) SEND_BACK , ");
				SQL.append(" SUM(NVL(CANCEL.CANCELED,0)) CANCEL ,  ");
				SQL.append(" SUM(NVL(REASSIGN.REASSIGN,0))   RE_ASSIGN ,  ");
				SQL.append(" SUM(NVL(BLOCKER.BLOCKED,0)) BLOCKED , ");
				
				SQL.append(" SUM(NVL(( ");
				SQL.append(" SELECT  COUNT (1) CNT ");
				SQL.append(" FROM  WF_WORK_QUEUE WQ, WF_ACTIVITY_TEMPLATE ACT");
				SQL.append(" WHERE ACT.ATID = WQ.ATID ");
				SQL.append(" AND WQ.OWNER = US.USER_NAME ");
				
				if(trackM.isFixrole()){
					SQL.append(" AND ACT.ROLE_ID = ? ");
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND ACT.PTID = ? ");
//					}else{
//						SQL.append("  AND ACT.PTID IN (?,?) ");
//					}
				}				
				SQL.append(" ),0)) ");
				
				
				SQL.append(" FROM ");
				SQL.append("     USER_ROLE UR, ");
				SQL.append("     ROLE RO, ");
				SQL.append("     US_USER_DETAIL US , ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             OWNER , ");
				SQL.append("             SUM(PREV_JOB) - SUM(PREV_EDIT) PREV_JOB , ");
				SQL.append("             SUM(PREV_EDIT) PREV_EDIT ");
				SQL.append("         FROM ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     WF_HIS.OWNER OWNER , ");
				SQL.append("                     1 AS PREV_JOB , ");
				SQL.append("                     CASE ");
				SQL.append("                         WHEN WF_HIS.APP_STATUS LIKE '%(Edit)%' ");
				SQL.append("                         THEN 1 ");
				SQL.append("                         ELSE 0 ");
				SQL.append("                     END PREV_EDIT ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_INSTANT_HISTORY WF_HIS ,WF_ACTIVITY_TEMPLATE ");
				SQL.append("                 WHERE ");
				SQL.append("                 	 WF_ACTIVITY_TEMPLATE.ATID = WF_HIS.ATID ");
				SQL.append("                 AND WF_HIS.SEQ = ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             MAX(WF.SEQ) ");
				SQL.append("                         FROM ");
				SQL.append("                             WF_INSTANT_HISTORY WF, ");
				SQL.append("                             WF_ACTIVITY_TEMPLATE TEMP ");
				SQL.append("                         WHERE ");
				SQL.append("                             WF.ATID = TEMP.ATID ");
				SQL.append("                         AND WF.JOB_ID = WF_HIS.JOB_ID ");
				SQL.append("                 AND WF.ATID LIKE 'STI%' ");
				SQL.append("                 AND WF.CREATE_DATE < TRUNC(SYSDATE) ");
				SQL.append("                 AND ( ");
				SQL.append("                         WF.COMPLETE_DATE >= TRUNC(SYSDATE) ");
				SQL.append("                      OR WF.COMPLETE_DATE IS NULL ");
				SQL.append("                     ) ");							
							if(trackM.isFixrole()){
								SQL.append(" AND TEMP.ROLE_ID = ? ");	
//								if(ORIGLogic.RoleICDC(trackM.getRole())){
//									SQL.append("  AND TEMP.PTID = ? ");
//								}else{
//									SQL.append("  AND TEMP.PTID IN (?,?) ");
//								}
							}							
				
				SQL.append("                         GROUP BY ");
				SQL.append("                             WF.JOB_ID ");
				SQL.append("                     ) ");
				SQL.append(" 	AND WF_HIS.ATID LIKE 'STI%' ");
				if(trackM.isFixrole()){
					SQL.append(" AND WF_ACTIVITY_TEMPLATE.ROLE_ID = ? ");	
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
//					}else{
//						SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
//					}
				}
				SQL.append("             ) ");
				SQL.append("         GROUP BY ");
				SQL.append("             OWNER ");
				SQL.append("     ) ");
				SQL.append("     PREV_JOB , ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             OWNER, ");
				SQL.append("             COUNT(DISTINCT JOB_ID) INPUT ");
				SQL.append("         FROM ");
				SQL.append("             WF_INSTANT_HISTORY HIS , ");
				SQL.append("             WF_ACTIVITY_TEMPLATE ACT ");
				SQL.append("         WHERE ");
				SQL.append("             HIS.CREATE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) + .99999 ");
				SQL.append("         AND HIS.ATID LIKE 'STI%' ");
				SQL.append("         AND HIS.ATID = ACT.ATID ");
				SQL.append("         AND ACTION <> 'Unblock' ");

				if(trackM.isFixrole()){
					SQL.append(" AND ACT.ROLE_ID = ? ");
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND ACT.PTID = ? ");
//					}else{
//						SQL.append("  AND ACT.PTID IN (?,?) ");
//					}
				}
				
				
				SQL.append("         AND JOB_ID NOT IN ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     JOB_ID ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_INSTANT_HISTORY WF_HIS , ");
				SQL.append("                     WF_ACTIVITY_TEMPLATE ");
				SQL.append("                 WHERE ");
				SQL.append("                     WF_HIS.ATID = WF_ACTIVITY_TEMPLATE.ATID ");
				SQL.append("                 AND WF_HIS.ATID LIKE 'STI%' ");
				SQL.append("                 AND WF_HIS.CREATE_DATE < TRUNC(SYSDATE) ");
				SQL.append("                 AND ");
				SQL.append("                     ( ");
				SQL.append("                         WF_HIS.COMPLETE_DATE >= TRUNC(SYSDATE) ");
				SQL.append("                      OR WF_HIS.COMPLETE_DATE IS NULL ");
				SQL.append("                     ) ");
				SQL.append("                 AND WF_ACTIVITY_TEMPLATE.ROLE_ID = WF_ACTIVITY_TEMPLATE.ROLE_ID ");
				SQL.append("                 AND WF_HIS.OWNER = HIS.OWNER ");
				SQL.append("                 AND WF_HIS.SEQ = ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             MAX(WF.SEQ) ");
				SQL.append("                         FROM ");
				SQL.append("                             WF_INSTANT_HISTORY WF, ");
				SQL.append("                             WF_ACTIVITY_TEMPLATE TEMP ");
				SQL.append("                         WHERE ");
				SQL.append("                             WF.ATID = TEMP.ATID ");
				SQL.append("                         AND WF.JOB_ID = WF_HIS.JOB_ID ");
				SQL.append("                         AND TEMP.ROLE_ID = WF_ACTIVITY_TEMPLATE.ROLE_ID ");
				SQL.append("                         AND WF.CREATE_DATE < TRUNC(SYSDATE) ");
				SQL.append("                         GROUP BY ");
				SQL.append("                             WF.JOB_ID ");
				SQL.append("                     ) ");
				SQL.append("             ) ");
				SQL.append("         GROUP BY ");
				SQL.append("             OWNER ");
				SQL.append("     ) ");
				SQL.append("     INPUT , ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             WF_HIS.OWNER , ");
				SQL.append("             NVL(COUNT(WF_HIS.JOB_ID),0) OUTPUT ");
				SQL.append("         FROM ");
				SQL.append("             WF_INSTANT_HISTORY WF_HIS , ");
				SQL.append("             WF_ACTIVITY_TEMPLATE ACT ");
				SQL.append("         WHERE ");
				SQL.append("             WF_HIS.ATID = ACT.ATID ");
				
				if(trackM.isFixrole()){
					SQL.append(" AND ACT.ROLE_ID = ? ");
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND ACT.PTID = ? ");
//					}else{
//						SQL.append("  AND ACT.PTID IN (?,?) ");
//					}
				}
				
//				#Septemwi Comment count output count unblock
//				SQL.append("         AND WF_HIS.ACTION <> 'Unblock' ");
				
				SQL.append("         AND ");
				SQL.append("             ( ");
				SQL.append("                 WF_HIS.ATID LIKE 'STI%' ");
				SQL.append("              OR WF_HIS.ATID IN ('STC0401','STC0402','STC0403','STC0411') ");
				SQL.append("             ) ");
				SQL.append("         AND WF_HIS.COMPLETE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) + .99999 ");
				SQL.append("         AND WF_HIS.SEQ = ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     MAX(WF.SEQ) ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_INSTANT_HISTORY WF , ");
				SQL.append("                     WF_ACTIVITY_TEMPLATE TEMP ");
				SQL.append("                 WHERE ");
				SQL.append("                     WF.ATID = TEMP.ATID ");
				SQL.append("                 AND WF.JOB_ID =WF_HIS.JOB_ID ");
				SQL.append("                 AND TEMP.ROLE_ID =ACT.ROLE_ID ");
				SQL.append("                 AND WF.OWNER = WF_HIS.OWNER ");
				SQL.append("                 GROUP BY ");
				SQL.append("                     WF.JOB_ID ");
				SQL.append("             ) ");
				SQL.append("         GROUP BY ");
				SQL.append("             WF_HIS.OWNER ");
				SQL.append("     ) ");
				SQL.append("     OUTPUT , ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             WF.OWNER , ");
				SQL.append("             COUNT(WF.JOB_ID) BLOCKED ");
				SQL.append("         FROM ");
				SQL.append("             WF_INSTANT_HISTORY WF, ");
				SQL.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     JOB_ID, ");
				SQL.append("                     FROM_ATID, ");
				SQL.append("                     SEQ ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_INSTANT_HISTORY WF_HIS ");
				SQL.append("                 WHERE ");
				SQL.append("                     SEQ = ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             MAX(SEQ) ");
				SQL.append("                         FROM ");
				SQL.append("                             WF_INSTANT_HISTORY ");
				SQL.append("                         WHERE ");
				SQL.append("                             JOB_ID = WF_HIS.JOB_ID ");
				SQL.append("                     ) ");
				SQL.append("                 AND ACTION LIKE '%Block%' ");
				SQL.append("                 AND SUBSTR(WF_HIS.ATID,1,7) = FROM_ATID ");
				SQL.append("                 AND ");
				SQL.append("                     ( ");
				SQL.append("                         WF_HIS.FROM_ATID NOT LIKE 'STC%' ");
				SQL.append("                     AND WF_HIS.FROM_ATID NOT LIKE 'STA%' ");
				SQL.append("                     ) ");
				SQL.append("                 AND ");
				SQL.append("                     ( ");
				SQL.append("                         WF_HIS.ATID NOT LIKE 'STC%' ");
				SQL.append("                     AND WF_HIS.ATID NOT LIKE 'STA%' ");
				SQL.append("                     ) ");
				SQL.append("             ) ");
				SQL.append("             BLOCKER ");
				SQL.append("         WHERE ");
				SQL.append("             WF.ATID = TEMP.ATID ");
				
				if(trackM.isFixrole()){
					SQL.append(" AND TEMP.ROLE_ID = ? ");
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND TEMP.PTID = ? ");
//					}else{
//						SQL.append("  AND TEMP.PTID IN (?,?) ");
//					}
				}
				
				SQL.append("         AND WF.JOB_ID = BLOCKER.JOB_ID ");
				SQL.append("         AND WF.ATID = BLOCKER.FROM_ATID ");
				SQL.append("         AND WF.SEQ = (BLOCKER.SEQ - 1) ");
				SQL.append("         GROUP BY ");
				SQL.append("             OWNER ");
				SQL.append("     ) ");
				SQL.append("     BLOCKER , ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             WF.OWNER , ");
				SQL.append("             COUNT(WF.JOB_ID) PREV_BLOCKED ");
				SQL.append("         FROM ");
				SQL.append("             WF_INSTANT_HISTORY WF, ");
				SQL.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     JOB_ID, ");
				SQL.append("                     FROM_ATID, ");
				SQL.append("                     SEQ ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_INSTANT_HISTORY WF_HIS ");
				SQL.append("                 WHERE ");
				SQL.append("                     SEQ = ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             MAX(SEQ) ");
				SQL.append("                         FROM ");
				SQL.append("                             WF_INSTANT_HISTORY ");
				SQL.append("                         WHERE ");
				SQL.append("                             JOB_ID = WF_HIS.JOB_ID ");
				SQL.append("                         AND WF_INSTANT_HISTORY.CREATE_DATE < TRUNC(SYSDATE) ");
				SQL.append("                     ) ");
				SQL.append("                 AND ACTION LIKE '%Block%' ");
				SQL.append("                 AND SUBSTR(WF_HIS.ATID,1,7) = FROM_ATID ");
				SQL.append("                 AND ");
				SQL.append("                     ( ");
				SQL.append("                         WF_HIS.FROM_ATID NOT LIKE 'STC%' ");
				SQL.append("                     AND WF_HIS.FROM_ATID NOT LIKE 'STA%' ");
				SQL.append("                     ) ");
				SQL.append("                 AND ");
				SQL.append("                     ( ");
				SQL.append("                         WF_HIS.ATID NOT LIKE 'STC%' ");
				SQL.append("                     AND WF_HIS.ATID NOT LIKE 'STA%' ");
				SQL.append("                     ) ");
				SQL.append("                 AND WF_HIS.CREATE_DATE < TRUNC(SYSDATE) ");
				SQL.append("             ) ");
				SQL.append("             BLOCKER ");
				SQL.append("         WHERE ");
				SQL.append("             WF.ATID = TEMP.ATID ");

				if(trackM.isFixrole()){
					SQL.append(" AND TEMP.ROLE_ID = ? ");
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND TEMP.PTID = ? ");
//					}else{
//						SQL.append("  AND TEMP.PTID IN (?,?) ");
//					}
				}
				
				SQL.append("         AND WF.JOB_ID = BLOCKER.JOB_ID ");
				SQL.append("         AND WF.ATID = BLOCKER.FROM_ATID ");
				SQL.append("         AND WF.SEQ = (BLOCKER.SEQ - 1) ");
				SQL.append("         GROUP BY ");
				SQL.append("             OWNER ");
				SQL.append("     ) ");
				SQL.append("     PREV_BLOCKER , ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             WF.OWNER , ");
				SQL.append("             COUNT(DISTINCT WF.JOB_ID) REASSIGN ");
				SQL.append("         FROM ");
				SQL.append("             WF_INSTANT_HISTORY WF, ");
				SQL.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
				SQL.append("             ( ");
				SQL.append("                 SELECT DISTINCT ");
				SQL.append("                     T.ROLE_ID, ");
				SQL.append("                     JOB_ID, ");
				SQL.append("                     FROM_ATID, ");
				SQL.append("                     SEQ ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_INSTANT_HISTORY WF_HIS, ");
				SQL.append("                     WF_ACTIVITY_TEMPLATE T ");
				SQL.append("                 WHERE ");
				SQL.append("                     WF_HIS.ATID = T.ATID ");
				
				if(trackM.isFixrole()){
					SQL.append(" AND T.ROLE_ID = ? ");	
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND T.PTID = ? ");
//					}else{
//						SQL.append("  AND T.PTID IN (?,?) ");
//					}
				}
				
				SQL.append("                 AND ACTION IN('Reassign','Reallocate') ");
				SQL.append("                 AND WF_HIS.CREATE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) +0.99999 ");
				SQL.append("             ) ");
				SQL.append("             REASSING ");
				SQL.append("         WHERE ");
				SQL.append("             WF.ATID = TEMP.ATID ");
				
				if(trackM.isFixrole()){
					SQL.append(" AND TEMP.ROLE_ID = ? ");
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND TEMP.PTID = ? ");
//					}else{
//						SQL.append("  AND TEMP.PTID IN (?,?) ");
//					}
				}
				
				SQL.append("         AND WF.JOB_ID = REASSING.JOB_ID ");
				SQL.append("         AND WF.ATID = REASSING.FROM_ATID ");
				SQL.append("         AND WF.SEQ = (REASSING.SEQ - 1) ");
				SQL.append("         AND NOT EXISTS ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     1 ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_INSTANT_HISTORY TMP_WF, ");
				SQL.append("                     WF_ACTIVITY_TEMPLATE TEMP, ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             JOB_ID, ");
				SQL.append("                             FROM_ATID, ");
				SQL.append("                             SEQ ");
				SQL.append("                         FROM ");
				SQL.append("                             WF_INSTANT_HISTORY WF_HIS ");
				SQL.append("                         WHERE ");
				SQL.append("                             SEQ = ");
				SQL.append("                             ( ");
				SQL.append("                                 SELECT ");
				SQL.append("                                     MAX(SEQ) ");
				SQL.append("                                 FROM ");
				SQL.append("                                     WF_INSTANT_HISTORY ");
				SQL.append("                                 WHERE ");
				SQL.append("                                     JOB_ID = WF_HIS.JOB_ID ");
				SQL.append("                             ) ");
				SQL.append("                         AND ACTION LIKE '%Block%' ");
				SQL.append("                         AND SUBSTR(WF_HIS.ATID,1,7) = FROM_ATID ");
				SQL.append("                         AND ");
				SQL.append("                             ( ");
				SQL.append("                                 WF_HIS.FROM_ATID NOT LIKE 'STC%' ");
				SQL.append("                             AND WF_HIS.FROM_ATID NOT LIKE 'STA%' ");
				SQL.append("                             ) ");
				SQL.append("                         AND ");
				SQL.append("                             ( ");
				SQL.append("                                 WF_HIS.ATID NOT LIKE 'STC%' ");
				SQL.append("                             AND WF_HIS.ATID NOT LIKE 'STA%' ");
				SQL.append("                             ) ");
				SQL.append("                     ) ");
				SQL.append("                     BLOCKER ");
				SQL.append("                 WHERE ");
				SQL.append("                     TMP_WF.ATID = TEMP.ATID ");

				if(trackM.isFixrole()){
					SQL.append(" AND TEMP.ROLE_ID = ? ");
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND TEMP.PTID = ? ");
//					}else{
//						SQL.append("  AND TEMP.PTID IN (?,?) ");
//					}
				}
				
				SQL.append("                 AND TMP_WF.JOB_ID = BLOCKER.JOB_ID ");
				SQL.append("                 AND TMP_WF.ATID = BLOCKER.FROM_ATID ");
				SQL.append("                 AND TMP_WF.SEQ = (BLOCKER.SEQ - 1) ");
				SQL.append("                 AND TMP_WF.JOB_ID = WF.JOB_ID ");
				SQL.append("                 AND TMP_WF.OWNER = WF.OWNER ");
				SQL.append("             ) ");
				SQL.append("         AND NOT EXISTS ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     JOB_ID, ");
				SQL.append("                     OWNER ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_WORK_QUEUE WQ, ");
				SQL.append("                     USER_ROLE_JOIN_ROLE UR ");
				SQL.append("                 WHERE ");
				SQL.append("                     UR.USER_NAME = WQ.OWNER ");
				
				if(trackM.isFixrole()){		
					SQL.append("                 AND UR.ROLE_NAME = ? ");
				}
				
				SQL.append("                 AND UR.USER_NAME = WF.OWNER ");
				SQL.append("                 AND WQ.JOB_ID = WF.JOB_ID ");
				SQL.append("             ) ");
				SQL.append("         AND NOT EXISTS ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     1 ");
				SQL.append("                 FROM ");
				SQL.append("                     ORIG_APPLICATION , ");
				SQL.append("                     WF_JOBID_MAPPING ");
				SQL.append("                 WHERE ");
				SQL.append("                     ORIG_APPLICATION.FINAL_APP_DECISION_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC( ");
				SQL.append("                     SYSDATE)+0.99999 ");
				SQL.append("                 AND WF_JOBID_MAPPING.APPLICATION_RECORD_ID = ORIG_APPLICATION.APPLICATION_RECORD_ID ");
				SQL.append("                 AND WF_JOBID_MAPPING.JOB_ID = WF.JOB_ID ");
				SQL.append("                 AND WF_JOBID_MAPPING.JOB_STATUS='ACTIVE' ");
				SQL.append("                 AND ORIG_APPLICATION.FINAL_APP_DECISION_BY = WF.OWNER ");
				SQL.append("                 AND JOB_STATE IN ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             SUBSTR(CSV, INSTR(CSV,',',1,LEV) + 1, INSTR(CSV,',',1,LEV+1 )-INSTR(CSV ");
				SQL.append("                             ,',',1,LEV)-1) ");
				SQL.append("                         FROM ");
				SQL.append("                             ( ");
				SQL.append("                                 SELECT ");
				SQL.append("                                     ','||PARAM_VALUE||',' CSV ");
				SQL.append("                                 FROM ");
				SQL.append("                                     GENERAL_PARAM ");
				SQL.append("                                 WHERE ");
				SQL.append("                                     PARAM_CODE = 'JOBSTATE_CANCEL' ");
				SQL.append("                             ) ");
				SQL.append("                             , ");
				SQL.append("                             ( ");
				SQL.append("                                 SELECT ");
				SQL.append("                                     LEVEL LEV ");
				SQL.append("                                 FROM ");
				SQL.append("                                     DUAL CONNECT BY LEVEL <= 100 ");
				SQL.append("                             ) ");
				SQL.append("                         WHERE ");
				SQL.append("                             LEV <= LENGTH(CSV)-LENGTH(REPLACE(CSV,','))-1 ");
				SQL.append("                     ) ");
				SQL.append("             ) ");
				SQL.append("         AND EXISTS ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     1 ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_INSTANT_HISTORY ");
				SQL.append("                 WHERE ");
				SQL.append("                     JOB_ID = WF.JOB_ID ");
				SQL.append("                 AND ACTION IN('Reassign','Reallocate') ");
				SQL.append("                 AND SEQ = ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             MAX(SEQ) +1 ");
				SQL.append("                         FROM ");
				SQL.append("                             WF_INSTANT_HISTORY ");
				SQL.append("                         WHERE ");
				SQL.append("                             JOB_ID = WF.JOB_ID ");
				SQL.append("                         AND OWNER = WF.OWNER ");
				SQL.append("                     ) ");
				SQL.append("             ) ");
				SQL.append("         GROUP BY ");
				SQL.append("             OWNER ");
				SQL.append("     ) ");
				SQL.append("     REASSIGN , ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             ORIG_APPLICATION.FINAL_APP_DECISION_BY , ");
				SQL.append("             COUNT(1) CANCELED ");
				SQL.append("         FROM ");
				SQL.append("             ORIG_APPLICATION ");
				SQL.append("         WHERE ");
				SQL.append("             ORIG_APPLICATION.FINAL_APP_DECISION_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE)+ ");
				SQL.append("             0.99999 ");
				SQL.append("         AND JOB_STATE IN ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     SUBSTR(CSV, INSTR(CSV,',',1,LEV) + 1, INSTR(CSV,',',1,LEV+1 )-INSTR(CSV,',',1, ");
				SQL.append("                     LEV)-1) ");
				SQL.append("                 FROM ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             ','||PARAM_VALUE||',' CSV ");
				SQL.append("                         FROM ");
				SQL.append("                             GENERAL_PARAM ");
				SQL.append("                         WHERE ");
				SQL.append("                             PARAM_CODE = 'JOBSTATE_CANCEL' ");
				SQL.append("                     ) ");
				SQL.append("                     , ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             LEVEL LEV ");
				SQL.append("                         FROM ");
				SQL.append("                             DUAL CONNECT BY LEVEL <= 100 ");
				SQL.append("                     ) ");
				SQL.append("                 WHERE ");
				SQL.append("                     LEV <= LENGTH(CSV)-LENGTH(REPLACE(CSV,','))-1 ");
				SQL.append("             ) ");
				SQL.append("         GROUP BY ");
				SQL.append("             ORIG_APPLICATION.FINAL_APP_DECISION_BY ");
				SQL.append("     ) ");
				SQL.append("     CANCEL , ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             WF.OWNER , ");
				SQL.append("             COUNT( WF.JOB_ID) SEND_BACK ");
				SQL.append("         FROM ");
				SQL.append("             WF_INSTANT_HISTORY WF, ");
				SQL.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     T.ROLE_ID, ");
				SQL.append("                     JOB_ID, ");
				SQL.append("                     FROM_ATID, ");
				SQL.append("                     SEQ ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_INSTANT_HISTORY WF_HIS, ");
				SQL.append("                     WF_ACTIVITY_TEMPLATE T ");
				SQL.append("                 WHERE ");
				SQL.append("                     WF_HIS.ATID = T.ATID ");
				SQL.append("                 AND SEQ = ");
				SQL.append("                     ( ");
				SQL.append("                         SELECT ");
				SQL.append("                             MAX(SEQ) ");
				SQL.append("                         FROM ");
				SQL.append("                             WF_INSTANT_HISTORY T1, ");
				SQL.append("                             WF_ACTIVITY_TEMPLATE T2 ");
				SQL.append("                         WHERE ");
				SQL.append("                             T1.JOB_ID = WF_HIS.JOB_ID ");
				SQL.append("                         AND T1.ATID = T2.ATID ");
				SQL.append("                         AND T2.ROLE_ID = T.ROLE_ID ");
				SQL.append("                         AND ");
				SQL.append("                             ( ");
				SQL.append("                                 PROCESS_STATE IN ( 'SEND_BACKX','SEND_BACK') ");
				SQL.append("                              OR ");
				SQL.append("                                 ( ");
				SQL.append("                                     ACTION LIKE 'Send back to % Block' ");
				SQL.append("                                 AND PROCESS_STATE NOT IN ('SEND_M', 'SENDX_M','SENDX') ");
				SQL.append("                                 ) ");
				SQL.append("                             ) ");
				SQL.append("                         AND WF_HIS.CREATE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) +0.99999 ");
				SQL.append("                     ) ");
				SQL.append("             ) ");
				SQL.append("             SEND_BACK ");
				SQL.append("         WHERE ");
				SQL.append("             WF.ATID = TEMP.ATID ");
				
				if(trackM.isFixrole()){
					SQL.append(" AND TEMP.ROLE_ID = ? ");
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						SQL.append("  AND TEMP.PTID = ? ");
//					}else{
//						SQL.append("  AND TEMP.PTID IN (?,?) ");
//					}
				}
				
				SQL.append("         AND WF.JOB_ID = SEND_BACK.JOB_ID ");
				SQL.append("         AND WF.ATID = SEND_BACK.FROM_ATID ");
				SQL.append("         AND WF.SEQ = (SEND_BACK.SEQ - 1) ");
				SQL.append("         AND NOT EXISTS ");
				SQL.append("             ( ");
				SQL.append("                 SELECT ");
				SQL.append("                     JOB_ID, ");
				SQL.append("                     OWNER ");
				SQL.append("                 FROM ");
				SQL.append("                     WF_WORK_QUEUE WQ, ");
				SQL.append("                     USER_ROLE_JOIN_ROLE UR ");
				SQL.append("                 WHERE ");
				SQL.append("                     UR.USER_NAME = WQ.OWNER ");
				
				if(trackM.isFixrole()){	
					SQL.append("                 AND UR.ROLE_NAME = ? ");
				}
				
				SQL.append("                 AND UR.USER_NAME = WF.OWNER ");
				SQL.append("                 AND WQ.JOB_ID = WF.JOB_ID ");
				SQL.append("             ) ");
				SQL.append("         GROUP BY ");
				SQL.append("             OWNER ");
				SQL.append("     ) ");
				SQL.append("     SEND_BACK ");
				SQL.append(" WHERE ");
				SQL.append("     UR.ROLE_ID = RO.ROLE_ID ");
				
				if(trackM.isFixrole()){
					SQL.append(" AND RO.ROLE_NAME = ? ");			
				}else{
					if(null != vRole && vRole.size() > 0){					
						SQL.append(" AND RO.ROLE_NAME IN (");
						int i = 0;
						for(ORIGCacheDataM dataM : vRole){	
							if(i != 0){
								SQL.append(",");
							}
							SQL.append("?");
							i++;
						}
						SQL.append(" ) ");					
					}			
				}
								
				SQL.append("     AND US.USER_NAME = UR.USER_NAME ");
				SQL.append("     AND US.ACTIVE_STATUS = ? ");
				SQL.append("     AND US.USER_NAME = PREV_JOB.OWNER(+) ");
				SQL.append("     AND US.USER_NAME = INPUT.OWNER(+) ");
				SQL.append("     AND US.USER_NAME = OUTPUT.OWNER(+) ");
				SQL.append("     AND US.USER_NAME = BLOCKER.OWNER(+) ");
				SQL.append("     AND US.USER_NAME = REASSIGN.OWNER(+) ");
				SQL.append("     AND US.USER_NAME = CANCEL.FINAL_APP_DECISION_BY(+)  ");
				SQL.append("     AND US.USER_NAME = SEND_BACK.OWNER(+) ");
				SQL.append("     AND US.USER_NAME = PREV_BLOCKER.OWNER(+) ");
					
				if(!OrigUtil.isEmptyString(trackM.getFirstName())){
					SQL.append(" AND US.THAI_FIRSTNAME LIKE ? ");
				}
				
				if(!OrigUtil.isEmptyString(trackM.getLastName())){
					SQL.append(" AND US.THAI_LASTNAME LIKE ? ");
				}

				if(OrigConstant.FLAG_Y.equals(trackM.getLogOn())){
					SQL.append(" AND US.LOGON_FLAG = ? ");
				}else if(OrigConstant.FLAG_N.equals(trackM.getLogOn())){
					SQL.append(" AND (US.LOGON_FLAG = ? OR US.LOGON_FLAG IS NULL)");
				}
							
				if(!OrigUtil.isEmptyString(trackM.getOnJob())){
					SQL.append(" AND EXISTS ( ");
						SQL.append(" SELECT ");
						SQL.append("     'X' ");
						SQL.append(" FROM ");
						SQL.append("     USER_WORK_QUEUE, ");
						SQL.append("     WF_TODO_LIST_MASTER, ");
						SQL.append("     WF_TODO_LIST, ");
						SQL.append("     WF_ACTIVITY_TEMPLATE ");
						SQL.append(" WHERE ");
						SQL.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
						SQL.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
						SQL.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
						SQL.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
						SQL.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
						SQL.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
						SQL.append(" AND USER_WORK_QUEUE.ONJOB_FLAG = ? ");
						if(trackM.isFixrole()){
//							if(ORIGLogic.RoleICDC(trackM.getRole())){
//								SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
//							}else{
//								SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
//							}
						}
					SQL.append(" ) ");
				}else{
					SQL.append(" AND EXISTS ( ");
						SQL.append(" SELECT ");
						SQL.append("     'X' ");
						SQL.append(" FROM ");
						SQL.append("     USER_WORK_QUEUE, ");
						SQL.append("     WF_TODO_LIST_MASTER, ");
						SQL.append("     WF_TODO_LIST, ");
						SQL.append("     WF_ACTIVITY_TEMPLATE ");
						SQL.append(" WHERE ");
						SQL.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
						SQL.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
						SQL.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
						SQL.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
						SQL.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
						SQL.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
						if(trackM.isFixrole()){
//							if(ORIGLogic.RoleICDC(trackM.getRole())){
//								SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
//							}else{
//								SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
//							}
						}
					SQL.append(" ) ");
				}
				
				if(!OrigUtil.isEmptyString(trackM.getEmpID())){
					SQL.append(" AND US.USER_NO = ? ");
				}	
				
			String dSql = String.valueOf(SQL);
						
			logger.debug("dSql >> "+dSql);
			
			ps = conn.prepareStatement(dSql);
			int index = 1;
						
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){		
				ps.setString(index++, trackM.getRole());
			}
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}
			if(trackM.isFixrole()){	
				ps.setString(index++, trackM.getRole());
			}			
			
			if(trackM.isFixrole()){
				ps.setString(index++, trackM.getRole());
			}else{
				if(null != vRole && vRole.size() > 0){
					for(ORIGCacheDataM dataM : vRole){
						ps.setString(index++,dataM.getCode());
					}
				}
			}
			
			ps.setString(index++, OrigConstant.ACTIVE_FLAG);
							
			if(!OrigUtil.isEmptyString(trackM.getFirstName())){
				ps.setString(index++, trackM.getFirstName()+"%");
			}
			
			if(!OrigUtil.isEmptyString(trackM.getLastName())){
				ps.setString(index++, trackM.getLastName()+"%");
			}

			if(OrigConstant.FLAG_Y.equals(trackM.getLogOn())){
				ps.setString(index++,OrigConstant.FLAG_Y);
			}else if(OrigConstant.FLAG_N.equals(trackM.getLogOn())){
				ps.setString(index++,OrigConstant.FLAG_N);
			}
						
			if(!OrigUtil.isEmptyString(trackM.getOnJob())){
				ps.setString(index++, trackM.getOnJob());
				if(trackM.isFixrole()){
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//					}else{
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//					}
				}
			}else{
				if(trackM.isFixrole()){
//					if(ORIGLogic.RoleICDC(trackM.getRole())){
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP002);
//					}else{
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//						ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//					}
				}
			}
			
			if(!OrigUtil.isEmptyString(trackM.getEmpID())){
				ps.setString(index++, trackM.getEmpID());
			}	
			
			rs = ps.executeQuery();
			if(rs.next()){
				hWfJob.put("countPreviousJob", rs.getInt(1));
				hWfJob.put("countPreviousJobEdit", rs.getInt(2));
				hWfJob.put("countNewJob", rs.getInt(3));
				hWfJob.put("countSubmitJob", rs.getInt(4));
				hWfJob.put("countSubmitEditJob", rs.getInt(5));	
				hWfJob.put("countCancleJob", rs.getInt(6));	
				hWfJob.put("countReassignJob", rs.getInt(7));	
				hWfJob.put("countBlockJob", rs.getInt(8));				
				hWfJob.put("countRemainJob", rs.getInt(9));
			}			
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return hWfJob;
	}
	
	@Override
	public int countAutoQueue(TrackingDataM trackM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		Connection conn = null;
		try{
			boolean isICDC = false;
			boolean isNormal = false;
			
			Vector<ORIGCacheDataM> vRole = trackM.getvRole();
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT COUNT(DISTINCT WQ.JOB_ID) ");
			sql.append(" FROM WF_ACTIVITY_TEMPLATE AT,WF_WORK_QUEUE WQ ");
			sql.append(" WHERE WQ.ATID = AT.ATID AND AT.ACTIVITY_TYPE IN('AQ','CQ')  ");			
			
			if(trackM.isFixrole()){
				sql.append(" AND AT.ROLE_ID = ? ");
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					sql.append(" AND AT.PTID IN(?) ");
//				}else{
//					sql.append(" AND AT.PTID IN(?,?) ");
//				}
			}else{
				if(null != vRole && vRole.size() > 0){
					
					sql.append(" AND AT.ROLE_ID IN (");
					int i = 0;
					for(ORIGCacheDataM dataM : vRole){
//						if(ORIGLogic.RoleICDC(dataM.getCode())){
//							isICDC = true;							
//						}else{
//							isNormal = true;
//						}
						if(i != 0){
							sql.append(",");
						}
						sql.append("?");
						i++;
					}
					sql.append(" ) ");
					
					if(isICDC){
						sql.append(" AND AT.PTID IN(?) ");
					}
					
					if(isNormal){
						sql.append(" AND AT.PTID IN(?,?) ");
					}
					
				}
			}
						
			String dSql = String.valueOf(sql);
			
			log.debug("dSql >> "+dSql);
			
			ps = conn.prepareStatement(dSql);
			int index = 1;
			
			if(trackM.isFixrole()){
				ps.setString(index++,trackM.getWfRole());
//				if(ORIGLogic.RoleICDC(trackM.getRole())){
//					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP002);
//				}else{
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
//					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
//				}
			}else{
				if(null != vRole && vRole.size() > 0){
					for(ORIGCacheDataM dataM : vRole){
//						ps.setString(index++,ORIGLogic.getRoleWf(dataM.getCode()));
					}
				}
				if(isICDC){
					ps.setString(index++,  WorkflowConstant.ProcessTemplate.KLOP002);
				}
				
				if(isNormal){
					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP001);
					ps.setString(index++, WorkflowConstant.ProcessTemplate.KLOP003);
				}
			}
			
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
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
		return count;
	}
}
