/*
 * Created on Oct 1, 2007
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

import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.exceptions.OrigReasonLogMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.ReasonDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigReasonLogMDAOImpl
 */
public class OrigReasonLogMDAOImpl extends OrigObjectDAO implements
		OrigReasonLogMDAO {
	private static Logger log = Logger.getLogger(OrigReasonLogMDAOImpl.class);

	/**
	 *  
	 */
	public OrigReasonLogMDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigReasonLogMDAO#createModelOrigReasonLogM(com.eaf.orig.shared.model.ReasonDataM)
	 */
	public void createModelOrigReasonLogM(ReasonDataM prmReasonLogDataM)
			throws OrigReasonLogMException {
		try {
			//int logID = Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.REASON_LOG_ID));
			//prmReasonLogDataM.setReasonLogId(logID);
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			int logID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.REASON_LOG_ID));
			prmReasonLogDataM.setReasonLogId(logID);
			createTableORIG_REASON_LOG(prmReasonLogDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonLogMException(e.getMessage());
		}

	}

	/**
	 * @param prmReasonLogDataM
	 */
	private void createTableORIG_REASON_LOG(ReasonDataM prmReasonLogDataM)
			throws OrigReasonLogMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_REASON_LOG ");
			sql
					.append("( REASON_LOG_ID,APPLICATION_RECORD_ID, REASON_SEQ,REASON_CODE,REASON_TYPE");
			sql.append(",CREATE_DATE,CREATE_BY,ROLE) ");

			sql.append(" VALUES(?,?,?,?,?  ,SYSDATE,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, prmReasonLogDataM.getReasonLogId());
			ps.setString(2, prmReasonLogDataM.getApplicationRecordId());
			ps.setInt(3, prmReasonLogDataM.getReasonSeq());
			ps.setString(4, prmReasonLogDataM.getReasonCode());
			ps.setString(5, prmReasonLogDataM.getReasonType());
			ps.setString(6, prmReasonLogDataM.getCreateBy());
			ps.setString(7, prmReasonLogDataM.getRole());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonLogMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigReasonLogMDAO#deleteModelOrigReasonLogM(com.eaf.orig.shared.model.ReasonDataM)
	 */
	public void deleteModelOrigReasonLogM(ReasonDataM prmReasonLogDataM)
			throws OrigReasonLogMException {
		try {
			deleteTableORIG_REASON_LOG(prmReasonLogDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonLogMException(e.getMessage());
		}

	}

	/**
	 * @param prmReasonLogDataM
	 */
	private void deleteTableORIG_REASON_LOG(ReasonDataM prmReasonLogDataM)
			throws OrigReasonLogMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_REASON_LOG ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND REASON_SEQ=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmReasonLogDataM.getApplicationRecordId());
			ps.setInt(2, prmReasonLogDataM.getReasonSeq());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonLogMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigReasonLogMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigReasonLogMDAO#loadModelOrigReasonLogM(java.lang.String)
	 */
	public Vector loadModelOrigReasonLogM(String applicationRecordId)
			throws OrigReasonLogMException {
		try {
			Vector result = selectTableORIG_REASON_LOG(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonLogMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_REASON_LOG(String applicationRecordId)
			throws OrigReasonLogMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT   REASON_LOG_ID,APPLICATION_RECORD_ID, REASON_SEQ,REASON_CODE,REASON_TYPE");
			sql.append(",CREATE_DATE,CREATE_BY");
			sql
					.append(" FROM ORIG_REASON_LOG WHERE APPLICATION_RECORD_ID = ?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			Vector vReasonLog = new Vector();
			while (rs.next()) {
				ReasonDataM reasonLogDataM = new ReasonDataM();
				reasonLogDataM.setReasonLogId(rs.getInt(1));
				reasonLogDataM.setApplicationRecordId(applicationRecordId);
				reasonLogDataM.setReasonSeq(rs.getInt(3));
				reasonLogDataM.setReasonCode(rs.getString(4));
				reasonLogDataM.setReasonType(rs.getString(5));
				reasonLogDataM.setCreateDate(rs.getTimestamp(6));
				reasonLogDataM.setCreateBy(rs.getString(7));
				vReasonLog.add(reasonLogDataM);
			}
			return vReasonLog;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonLogMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigReasonLogMDAO#saveUpdateModelOrigReasonLogM(com.eaf.orig.shared.model.ReasonDataM)
	 */
	public void saveUpdateModelOrigReasonLogM(ReasonDataM prmReasonLogDataM)
			throws OrigReasonLogMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_REASON_LOG(prmReasonLogDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_REASON_LOG then call Insert method");
				
				//int logID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.REASON_LOG_ID));
				//prmReasonLogDataM.setReasonLogId(logID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				int logID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.REASON_LOG_ID));
				prmReasonLogDataM.setReasonLogId(logID);
				createTableORIG_REASON_LOG(prmReasonLogDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonLogMException(e.getMessage());
		}

	}
	
	/**
	 * @param prmReasonDataM
	 * @return
	 */
	private double updateTableORIG_REASON_LOG(ReasonDataM prmReasonDataM)
			throws OrigReasonLogMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_REASON_LOG ");
			sql
					.append(" SET  REASON_TYPE=? ,REASON_CODE=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND REASON_SEQ=? AND REASON_LOG_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmReasonDataM.getReasonType());
			ps.setString(2, prmReasonDataM.getReasonCode());
			ps.setString(3, prmReasonDataM.getApplicationRecordId());
			ps.setInt(4, prmReasonDataM.getReasonSeq());
			ps.setInt(5, prmReasonDataM.getReasonLogId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonLogMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
}
