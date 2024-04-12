/*
 * Created on Oct 19, 2007
 * Created by Weeraya
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

import com.eaf.orig.shared.dao.exceptions.OrigOtherNameMException;
import com.eaf.orig.shared.model.OtherNameDataM;
//import com.ibm.ISecurityLocalObjectCSIv2UtilityImpl.Connection;


/**
 * @author Weeraya
 *
 * Type: OrigOtherNameDAOImpl
 */
public class OrigOtherNameMDAOImpl extends OrigObjectDAO implements OrigOtherNameMDAO {
	private static Logger log = Logger.getLogger(OrigOtherNameMDAOImpl.class);

	/**
	 *  
	 */
	public OrigOtherNameMDAOImpl() {
		super();

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigOtherNameMException#createModelOtherNameDataM(com.eaf.orig.shared.model.OtherNameDataM)
	 */
	public void createModelOtherNameDataM(OtherNameDataM otherNameDataM) throws OrigOtherNameMException {
		try {
			
			createTableORIG_OTHER_NAME(otherNameDataM);
			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigOtherNameMException(e.getMessage());
		}
	}
	
	/**
	 * @param otherNameDataM
	 */
	private void createTableORIG_OTHER_NAME(OtherNameDataM otherNameDataM) throws OrigOtherNameMException {
		PreparedStatement ps = null;
		java.sql.Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_OTHER_NAME ");
			sql.append("(APPLICATION_RECORD_ID, CITIZEN_ID, TITLE_NAME, FIRST_NAME, LAST_NAME");
			sql.append(", POSITION, DESCRIPTION, CREATE_DATE, CREATE_BY, SEQ, OCCUPATION) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,SYSDATE,?,?,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql + " : appRecordID = "+otherNameDataM.getApplicationRecordId()+" , seq = "+otherNameDataM.getSeq());
			ps = conn.prepareStatement(dSql);		
			ps.setString(1, otherNameDataM.getApplicationRecordId());
			ps.setString(2, otherNameDataM.getCitizenId());
			ps.setString(3, otherNameDataM.getTitleName());
			ps.setString(4, otherNameDataM.getName());
			ps.setString(5, otherNameDataM.getLastName());
			ps.setString(6, otherNameDataM.getPosition());
			ps.setString(7, otherNameDataM.getDescription());
			ps.setString(8, otherNameDataM.getCreateBy());
			ps.setInt(9, otherNameDataM.getSeq());
			ps.setString(10, otherNameDataM.getOccupation());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigOtherNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigOtherNameMException#loadModelOtherNameDataM(java.lang.String)
	 */
	public Vector loadModelOtherNameDataM(String applicationRecordId) throws OrigOtherNameMException {
		try {
			Vector result = selectTableORIG_OTHER_NAME(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigOtherNameMException(e.getMessage());
		}
	}
	
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_OTHER_NAME(String applicationRecordId) throws OrigOtherNameMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("SELECT CITIZEN_ID, TITLE_NAME, FIRST_NAME, LAST_NAME, POSITION ");
			sql.append(",DESCRIPTION, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, SEQ, OCCUPATION ");
			sql.append(" FROM ORIG_OTHER_NAME WHERE APPLICATION_RECORD_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			OtherNameDataM otherNameDataM = null;
			Vector resultVect = new Vector();
			while (rs.next()) {
				otherNameDataM = new OtherNameDataM();
				otherNameDataM.setApplicationRecordId(applicationRecordId);
				otherNameDataM.setCitizenId(rs.getString(1));
				otherNameDataM.setTitleName(rs.getString(2));
				otherNameDataM.setName(rs.getString(3));
				otherNameDataM.setLastName(rs.getString(4));
				otherNameDataM.setPosition(rs.getString(5));
				otherNameDataM.setDescription(rs.getString(6));
				otherNameDataM.setCreateDate(rs.getTimestamp(7));
				otherNameDataM.setCreateBy(rs.getString(8));
				otherNameDataM.setUpdateDate(rs.getTimestamp(9));
				otherNameDataM.setUpdateBy(rs.getString(10));
				otherNameDataM.setSeq(rs.getInt(11));
				otherNameDataM.setOccupation(rs.getString(12));
				
				resultVect.add(otherNameDataM);
			}
			return resultVect;
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigOtherNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigOtherNameMException#saveUpdateModelOtherNameDataM(com.eaf.orig.shared.model.OtherNameDataM)
	 */
	public void saveUpdateModelOtherNameDataM(OtherNameDataM otherNameDataM) throws OrigOtherNameMException {
		int returnRows = 0;
		try {
			returnRows = updateTableORIG_OTHER_NAME(otherNameDataM);
			
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_OTHER_NAME then call Insert method");	
				createTableORIG_OTHER_NAME(otherNameDataM);
			}
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigOtherNameMException(e.getMessage());
		}

	}
	
	/**
	 * @param otherNameDataM
	 * @return
	 */
	private int updateTableORIG_OTHER_NAME(OtherNameDataM otherNameDataM) throws OrigOtherNameMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_OTHER_NAME ");
			sql.append(" SET CITIZEN_ID = ?, TITLE_NAME = ?, FIRST_NAME = ?, LAST_NAME = ?, POSITION = ?");
			sql.append(", DESCRIPTION = ?, UPDATE_DATE = SYSDATE, UPDATE_BY = ?, OCCUPATION=? ");

			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND SEQ = ?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("otherNameDataM.getApplicationRecordId()=" + otherNameDataM.getApplicationRecordId());
			log.debug("otherNameDataM.getSeq()=" + otherNameDataM.getSeq());
			ps = conn.prepareStatement(dSql);
			ps.setString(1, otherNameDataM.getCitizenId());
			ps.setString(2, otherNameDataM.getTitleName());
			ps.setString(3, otherNameDataM.getName());
			ps.setString(4, otherNameDataM.getLastName());
			ps.setString(5, otherNameDataM.getPosition());
			ps.setString(6, otherNameDataM.getDescription());
			ps.setString(7, otherNameDataM.getUpdateBy());
			ps.setString(8, otherNameDataM.getOccupation());
			ps.setString(9, otherNameDataM.getApplicationRecordId());
			ps.setInt(10, otherNameDataM.getSeq());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigOtherNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
			}
		}
		return returnRows;
	}
	
	/** delete not in key**/
	public void deleteNotInKeyTableORIG_OTHER_NAME(Vector otherNameVect, String appRecordID) throws OrigOtherNameMException{
		PreparedStatement ps = null;
		String cmpCode = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_OTHER_NAME");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
            
            if ((otherNameVect != null) && (otherNameVect.size() != 0)) {
                sql.append(" AND SEQ NOT IN ( ");
                OtherNameDataM otherNameDataM;
                for (int i = 0; i < otherNameVect.size(); i++) {
                	otherNameDataM = (OtherNameDataM) otherNameVect.get(i);
                    sql.append(" '" + otherNameDataM.getSeq() + "' , ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);

            ps = conn.prepareStatement(dSql);

            ps.setString(1, appRecordID);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigOtherNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
				throw new OrigOtherNameMException(e.getMessage());
			}
		}
	}
	
	/**
	 * @param appRecordID
	 */
	public void deleteTableORIG_OTHER_NAME(String appRecordID)throws OrigOtherNameMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_OTHER_NAME ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecordID);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR >> ",e);
			throw new OrigOtherNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
				throw new OrigOtherNameMException(e.getMessage());
			}
		}

	}
}
