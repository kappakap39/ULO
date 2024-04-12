/*
 * Created on Sep 21, 2007
 * Created by Sankom Sanpunya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationLogMException;
import com.eaf.orig.shared.model.ApplicationLogDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigApplicaitonLogMDAOImpl
 */
public class OrigApplicationLogMDAOImpl extends OrigObjectDAO implements
		OrigApplicationLogMDAO {
	private static Logger log = Logger.getLogger(OrigApplicationLogMDAOImpl.class);

	/**
	 *  
	 */
	public OrigApplicationLogMDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationLogMDAO#createModelOrigApplicationLogM(com.ge.naosasia.model.appform.ApplicationLogM)
	 */
	public void createModelOrigApplicationLogM(
			ApplicationLogDataM prmApplicationDataLogM)
			throws OrigApplicationLogMException {
		try {
			prmApplicationDataLogM.setLogSeq(getNextSeq(prmApplicationDataLogM.getApplicationRecordID()));
			createTableORIG_APPLICATION_LOG(prmApplicationDataLogM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationLogMException(e.getMessage());
		}

	}

	/**
	 * @param prmApplicationLogM
	 */
	private void createTableORIG_APPLICATION_LOG(
			ApplicationLogDataM prmApplicationDataLogM)
			throws OrigApplicationLogMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_APPLICATION_LOG ");
			sql.append("( APPLICATION_RECORD_ID,LOG_SEQ ,ACTION,JOB_STATE,");
			sql.append(" UPDATE_DATE ,UPDATE_BY,APPLICATION_STATUS,CLAIM_DATE  )");
			sql.append(" VALUES(?,?,?,?  ,SYSDATE,?,?,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			
			ps = conn.prepareStatement(dSql);			
			ps.setString(1, prmApplicationDataLogM.getApplicationRecordID());
			ps.setInt(2, prmApplicationDataLogM.getLogSeq());
			ps.setString(3, prmApplicationDataLogM.getAction());
			ps.setString(4, prmApplicationDataLogM.getJobState());
			ps.setString(5, prmApplicationDataLogM.getUpdateBy());
			ps.setString(6, prmApplicationDataLogM.getApplicationStatus());
			ps.setTimestamp(7,prmApplicationDataLogM.getClaimDate());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationLogMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationLogMDAO#deleteModelOrigApplicationLogM(com.ge.naosasia.model.appform.ApplicationLogM)
	 */
	public void deleteModelOrigApplicationLogM(
			ApplicationLogDataM prmApplicationLogDataM)
			throws OrigApplicationLogMException {
		try {
			deleteTableORIG_APPLICATION_LOG(prmApplicationLogDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationLogMException(e.getMessage());
		}

	}

	/**
	 * @param prmApplicationLogDataM
	 */
	private void deleteTableORIG_APPLICATION_LOG(
			ApplicationLogDataM prmApplicationLogDataM)
			throws OrigApplicationLogMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_APPLICATION_LOG ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? and LOG_SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmApplicationLogDataM.getApplicationRecordID());
			ps.setInt(2, prmApplicationLogDataM.getLogSeq());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationLogMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationLogMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationLogMDAO#loadModelOrigApplicationLogM(java.lang.String,
	 *      int)
	 */
	public Vector loadModelOrigApplicationLogM(String applicationRecordId)
			throws OrigApplicationLogMException {
		try {
			Vector result = selectTableORIG_APPLICATION_LOG(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationLogMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_APPLICATION_LOG(String applicationRecordId)
			throws OrigApplicationLogMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT APPLICATION_RECORD_ID,LOG_SEQ,ACTION,JOB_STATE,APPLICATION_STATUS,UPDATE_BY,UPDATE_DATE,CLAIM_DATE ");
			sql
					.append(" FROM ORIG_APPLICATION_LOG WHERE APPLICATION_RECORD_ID = ? ORDER BY LOG_SEQ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				ApplicationLogDataM prmApplicationLogDataM = new ApplicationLogDataM();
				prmApplicationLogDataM.setApplicationRecordID(rs.getString(1));
				prmApplicationLogDataM.setLogSeq(rs.getInt(2));
				prmApplicationLogDataM.setAction(rs.getString(3));
				prmApplicationLogDataM.setJobState(rs.getString(4));
				prmApplicationLogDataM.setApplicationStatus(rs.getString(5));
				prmApplicationLogDataM.setUpdateBy(rs.getString(6));
				prmApplicationLogDataM.setUpdatDate(rs.getTimestamp(7));
				prmApplicationLogDataM.setClaimDate(rs.getTimestamp(8));
				vt.add(prmApplicationLogDataM);
			}		 
			return vt;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationLogMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationLogMDAO#saveUpdateModelOrigApplicationLogM(com.ge.naosasia.model.appform.ApplicationLogM)
	 */
	public void saveUpdateModelOrigApplicationLogM(
			ApplicationLogDataM prmApplicationLogDataM)
			throws OrigApplicationLogMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_APPLICATION_LOG(prmApplicationLogDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_APPLICATION_LOG then call Insert method");
				prmApplicationLogDataM.setLogSeq(getNextSeq(prmApplicationLogDataM.getApplicationRecordID()));
				createTableORIG_APPLICATION_LOG(prmApplicationLogDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationLogMException(e.getMessage());
		}

	}

	/**
	 * @param prmApplicationLogDataM
	 * @return
	 */
	private double updateTableORIG_APPLICATION_LOG(
			ApplicationLogDataM prmApplicationLogDataM)
			throws OrigApplicationLogMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION_LOG ");
			sql.append(" SET ACTION=?,JOB_STATE=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,APPLICATION_STATUS=?,CLAIM_DATE=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND LOG_SEQ=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);

			ps.setString(1, prmApplicationLogDataM.getAction());

			ps.setString(2, prmApplicationLogDataM.getJobState());
			ps.setString(3, prmApplicationLogDataM.getUpdateBy());
			ps.setString(4, prmApplicationLogDataM.getApplicationStatus());
			ps.setTimestamp(5,prmApplicationLogDataM.getClaimDate());
	     	ps.setString(6, prmApplicationLogDataM.getApplicationRecordID());
		    ps.setInt(7, prmApplicationLogDataM.getLogSeq());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationLogMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public int getNextSeq(String applicationRecordId)throws OrigApplicationLogMException{
		try{
			return this.getMaxSeq(applicationRecordId)+1;
		}catch(Exception e){
			log.error(">>> getNextSeq has error",e);
			throw new OrigApplicationLogMException(e.toString());
		}
	}
	
	private int getMaxSeq(String applicationRecordId)throws OrigApplicationLogMException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			String sql = "SELECT MAX(LOG_SEQ) FROM ORIG_APPLICATION_LOG WHERE APPLICATION_RECORD_ID = ?";
	
			log.debug("getMaxSeq="+sql);
	
			ps = conn.prepareStatement(sql);
	
			ps.setString(1, applicationRecordId);
	
			rs = ps.executeQuery();
	
			if(rs.next()) return rs.getInt(1);
			return 0;

		}catch(Exception e){
			log.error(">>> getMaxSeq has error",e);
			throw new OrigApplicationLogMException(e.toString());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.error(">>> closeConnection() has error",e);
				throw new OrigApplicationLogMException(e.getMessage());
			}
		}
	}
}
