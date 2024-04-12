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
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.exceptions.OrigReasonMException;
import com.eaf.orig.shared.model.OtherNameDataM;
import com.eaf.orig.shared.model.ReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigReasonMDAOImpl
 */
public class OrigReasonMDAOImpl extends OrigObjectDAO implements OrigReasonMDAO {
	private static Logger log = Logger.getLogger(OrigReasonMDAOImpl.class);

	/**
	 *  
	 */
	public OrigReasonMDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigReasonMDAO#createModelOrigReasonM(com.eaf.orig.shared.model.ReasonDataM)
	 */
	public void createModelOrigReasonM(ReasonDataM prmReasonDataM)
			throws OrigReasonMException {
		try {
			createTableORIG_REASON(prmReasonDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonMException(e.getMessage());
		}

	}

	/**
	 * @param prmReasonDataM
	 */
	private void createTableORIG_REASON(ReasonDataM prmReasonDataM)
			throws OrigReasonMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_REASON ");
			sql
					.append("( APPLICATION_RECORD_ID,REASON_SEQ,REASON_TYPE ,REASON_CODE,CREATE_BY  ");
			sql.append(" ,CREATE_DATE,UPDATE_BY,UPDATE_DATE, ROLE)");
			sql.append(" VALUES(?,?,?,?,?   ,SYSDATE,?,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			log.debug("prmReasonDataM.getReasonCode() = "+prmReasonDataM.getReasonCode());
			log.debug("prmReasonDataM.getRole() = "+prmReasonDataM.getRole());
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmReasonDataM.getApplicationRecordId());
			ps.setInt(2, prmReasonDataM.getReasonSeq());
			ps.setString(3, prmReasonDataM.getReasonType());
			ps.setString(4, prmReasonDataM.getReasonCode());
			ps.setString(5, prmReasonDataM.getCreateBy());
			ps.setString(6, prmReasonDataM.getUpdateBy());
			ps.setString(7, prmReasonDataM.getRole());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigReasonMDAO#deleteModelOrigReasonM(com.eaf.orig.shared.model.ReasonDataM)
	 */
	public void deleteModelOrigReasonM(ReasonDataM prmReasonDataM)
			throws OrigReasonMException {
		try {
			deleteTableORIG_REASON(prmReasonDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonMException(e.getMessage());
		}

	}

	/**
	 * @param prmReasonDataM
	 */
	private void deleteTableORIG_REASON(ReasonDataM prmReasonDataM)
			throws OrigReasonMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_REASON ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND REASON_SEQ=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmReasonDataM.getApplicationRecordId());
			ps.setInt(2, prmReasonDataM.getReasonSeq());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigReasonMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigReasonMDAO#loadModelOrigReasonM(java.lang.String)
	 */
	public Vector loadModelOrigReasonM(String applicationRecordId)
			throws OrigReasonMException {
		try {
			Vector result = selectTableORIG_REASON(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_REASON(String applicationRecordId)
			throws OrigReasonMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT  REASON_SEQ,REASON_TYPE ,REASON_CODE ,CREATE_BY,CREATE_DATE");
			sql.append(" ,UPDATE_BY,UPDATE_DATE,ROLE");
			sql.append(" FROM ORIG_REASON WHERE APPLICATION_RECORD_ID = ?   ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			Vector vReason = new Vector();
			while (rs.next()) {
				ReasonDataM reasonDataM = new ReasonDataM();
				reasonDataM.setApplicationRecordId(applicationRecordId);
				reasonDataM.setReasonSeq(rs.getInt(1));
				reasonDataM.setReasonType(rs.getString(2));
				reasonDataM.setReasonCode(rs.getString(3));
				reasonDataM.setCreateBy(rs.getString(4));
				reasonDataM.setCreateDate(rs.getTimestamp(5));
				reasonDataM.setUpdateBy(rs.getString(6));
				reasonDataM.setUpdateDate(rs.getTimestamp(7));
				reasonDataM.setRole(rs.getString(8));
				vReason.add(reasonDataM);
			}
			return vReason;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigReasonMDAO#saveUpdateModelOrigReasonM(com.eaf.orig.shared.model.ReasonDataM)
	 */
	public void saveUpdateModelOrigReasonM(ReasonDataM prmReasonDataM)
			throws OrigReasonMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_REASON(prmReasonDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_REASON then call Insert method");
				createTableORIG_REASON(prmReasonDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonMException(e.getMessage());
		}

	}

	/**
	 * @param prmReasonDataM
	 * @return
	 */
	private double updateTableORIG_REASON(ReasonDataM prmReasonDataM)
			throws OrigReasonMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_REASON ");
			sql
					.append(" SET  REASON_TYPE=? ,REASON_CODE=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND REASON_SEQ=? AND ROLE=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmReasonDataM.getReasonType());
			ps.setString(2, prmReasonDataM.getReasonCode());
			ps.setString(3, prmReasonDataM.getUpdateBy());
			ps.setString(4, prmReasonDataM.getApplicationRecordId());
			ps.setInt(5, prmReasonDataM.getReasonSeq());
			ps.setString(6, prmReasonDataM.getRole());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	/**
	 * 
	 * @param reasonVect
	 * @param appRecordID
	 * @param roleID
	 * @throws OrigReasonMException
	 */
	public void deleteNotInKeyTableORIG_REASON(Vector reasonVect, String appRecordID, String roleID) throws OrigReasonMException{
		PreparedStatement ps = null;
		String cmpCode = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_REASON");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
            
            if ((reasonVect != null) && (reasonVect.size() != 0)) {
                sql.append(" AND ROLE NOT IN ( ");
                ReasonDataM reasonDataM;
                for (int i = 0; i < reasonVect.size(); i++) {
                	reasonDataM = (ReasonDataM) reasonVect.get(i);
                	logger.debug("reasonDataM.getRole() = "+reasonDataM.getRole());
                    sql.append(" '" + reasonDataM.getRole() + "' , ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            logger.debug("SQL = "+dSql+ ": appRecordID ="+appRecordID);
            ps = conn.prepareStatement(dSql);

            ps.setString(1, appRecordID);
            //ps.setString(2, roleID);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigReasonMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
				throw new OrigReasonMException(e.getMessage());
			}
		}
	}
	
	/**
	 * @param appRecordID
	 */
	public void deleteTableORIG_REASON(String appRecordID)throws OrigReasonMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_REASON ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecordID);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR >> ",e);
			throw new OrigReasonMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
				throw new OrigReasonMException(e.getMessage());
			}
		}

	}

	@Override
	public Vector<PLReasonDataM> getConfirmRejectReasons(String appRecordID, UserDetailM userM)throws OrigReasonMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DISTINCT R.REASON_TYPE, R.REASON_CODE ");
			sql.append("FROM  ORIG_REASON R, ");
			sql.append("(SELECT ROLE, APPLICATION_RECORD_ID ");
			sql.append("FROM (SELECT ROLE, APPLICATION_RECORD_ID ");
			sql.append("FROM ORIG_REASON TR ");
			sql.append("WHERE TR.APPLICATION_RECORD_ID = ? ");
			sql.append("ORDER BY TR.CREATE_DATE DESC) ");
			sql.append("WHERE  ROWNUM = 1 ) TMP ");
			sql.append("WHERE R.APPLICATION_RECORD_ID = TMP.APPLICATION_RECORD_ID ");
			sql.append("AND TMP.ROLE = R.ROLE  AND R.APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecordID);
			ps.setString(2, appRecordID);

			rs = ps.executeQuery();
			Vector vReason = new Vector();
			Timestamp currentTime = new Timestamp(new Date().getTime());
			while (rs.next()) {
				PLReasonDataM reasonDataM = new PLReasonDataM();
				reasonDataM.setApplicationRecordId(appRecordID);
				reasonDataM.setReasonType(rs.getString("REASON_TYPE"));
				reasonDataM.setReasonCode(rs.getString("REASON_CODE"));
				reasonDataM.setCreateBy(userM.getUserName());
				reasonDataM.setCreateDate(currentTime);
				reasonDataM.setUpdateBy(userM.getUserName());
				reasonDataM.setUpdateDate(currentTime);
				reasonDataM.setRole(userM.getCurrentRole());
				vReason.add(reasonDataM);
			}
			return vReason;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigReasonMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

}
