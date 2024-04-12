/*
 * Created on Sep 26, 2007
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
import com.eaf.orig.shared.dao.exceptions.OrigAttachmentHistoryMException;
import com.eaf.orig.shared.model.AttachmentHistoryDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigAttachmentHistoryMDAOImpl
 */
public class OrigAttachmentHistoryMDAOImpl extends OrigObjectDAO implements
		OrigAttachmentHistoryMDAO {
	private static Logger log = Logger.getLogger(OrigAttachmentHistoryMDAOImpl.class);

	/**
	 *  
	 */
	public OrigAttachmentHistoryMDAOImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigAttachmentHistoryMDAO#createModelOrigAttachmentHistoryM(com.eaf.orig.shared.model.AttachmentHistoryDataM)
	 */
	public void createModelOrigAttachmentHistoryM(
			AttachmentHistoryDataM prmAttachmentHistoryDataM)
			throws OrigAttachmentHistoryMException {
	 
		try {
			
			createTableORIG_ATTACHMENT_HISTORY(prmAttachmentHistoryDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigAttachmentHistoryMException(e.getMessage());
		}
	}

	/**
	 * @param prmAttachmentHistoryDataM
	 */
	private void createTableORIG_ATTACHMENT_HISTORY(
			AttachmentHistoryDataM prmAttachmentHistoryDataM)
			throws OrigAttachmentHistoryMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_ATTACHMENT_HISTORY ");
			sql
					.append("( APPLICATION_RECORD_ID,ATTACH_ID ,FILE_NAME ,FILE_SIZE,CREATE_BY");
			sql.append(" ,CREATE_DATE,UPDATE_BY,UPDATE_DATE,FILE_TYPE   )");
			sql.append(" VALUES(?,?,?,?,?  ,SYSDATE,?,SYSDATE,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmAttachmentHistoryDataM.getApplicationRecordId());
			ps.setInt(2, prmAttachmentHistoryDataM.getAttachId());
			ps.setString(3, prmAttachmentHistoryDataM.getFileName());
			ps.setInt(4, prmAttachmentHistoryDataM.getFileSize());
			ps.setString(5, prmAttachmentHistoryDataM.getCreateBy());
			ps.setString(6, prmAttachmentHistoryDataM.getUpdateBy());
			ps.setString(7, prmAttachmentHistoryDataM.getFileType());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigAttachmentHistoryMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigAttachmentHistoryMDAO#deleteModelOrigAttachmentHistoryM(com.eaf.orig.shared.model.AttachmentHistoryDataM)
	 */
	public void deleteModelOrigAttachmentHistoryM(
			AttachmentHistoryDataM prmAttachmentHistoryDataM)
			throws OrigAttachmentHistoryMException {
		try {
			deleteTableORIG_ATTACHMENT_HISTORY(prmAttachmentHistoryDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigAttachmentHistoryMException(e.getMessage());
		}

	}

	/**
	 * @param prmAttachmentHistoryDataM
	 */
	private void deleteTableORIG_ATTACHMENT_HISTORY(
			AttachmentHistoryDataM prmAttachmentHistoryDataM)
			throws OrigAttachmentHistoryMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_ATTACHMENT_HISTORY ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? and ATTACH_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmAttachmentHistoryDataM.getApplicationRecordId());
			ps.setInt(2, prmAttachmentHistoryDataM.getAttachId());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigAttachmentHistoryMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigAttachmentHistoryMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigAttachmentHistoryMDAO#loadModelOrigAttachmentHistoryM(java.lang.String)
	 */
	public Vector loadModelOrigAttachmentHistoryM(String applicationRecordId)
			throws OrigAttachmentHistoryMException {
		try {
			Vector result = selectTableORIG_ATTACHMENT_HISTORY(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigAttachmentHistoryMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_ATTACHMENT_HISTORY(String applicationRecordId)
			throws OrigAttachmentHistoryMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT ATTACH_ID ,FILE_NAME ,FILE_SIZE,CREATE_BY,CREATE_DATE  ,UPDATE_BY,UPDATE_DATE,FILE_TYPE   ");
			sql
					.append("FROM ORIG_ATTACHMENT_HISTORY WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				AttachmentHistoryDataM prmAttachmentHistoryDataM = new AttachmentHistoryDataM();
				prmAttachmentHistoryDataM
						.setApplicationRecordId(applicationRecordId);
				prmAttachmentHistoryDataM.setAttachId(rs.getInt(1));
				prmAttachmentHistoryDataM.setFileName(rs.getString(2));
				prmAttachmentHistoryDataM.setFileSize(rs.getInt(3));
				prmAttachmentHistoryDataM.setCreateBy(rs.getString(4));
				prmAttachmentHistoryDataM.setCreateDate(rs.getTimestamp(5));
				prmAttachmentHistoryDataM.setUpdateBy(rs.getString(6));
				prmAttachmentHistoryDataM.setUpdateDate(rs.getTimestamp(7));
				prmAttachmentHistoryDataM.setFileType(rs.getString(8));
				vt.add(prmAttachmentHistoryDataM);
			}
			log.debug("closeConnection");
			//rs.close();
			//ps.close();

			return vt;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigAttachmentHistoryMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigAttachmentHistoryMDAO#saveUpdateModelOrigAttachmentHistoryM(com.eaf.orig.shared.model.AttachmentHistoryDataM)
	 */
	public void saveUpdateModelOrigAttachmentHistoryM(
			AttachmentHistoryDataM prmAttachmentHistoryDataM)
			throws OrigAttachmentHistoryMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_ATTACHMENT_HISTORY(prmAttachmentHistoryDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_ATTACHMENT_HISTORY then call Insert method");
				createTableORIG_ATTACHMENT_HISTORY(prmAttachmentHistoryDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigAttachmentHistoryMException(e.getMessage());
		}

	}

	/**
	 * @param prmAttachmentHistoryDataM
	 * @return
	 */
	private double updateTableORIG_ATTACHMENT_HISTORY(
			AttachmentHistoryDataM prmAttachmentHistoryDataM)
			throws OrigAttachmentHistoryMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_ATTACHMENT_HISTORY ");
			sql.append(" SET  FILE_NAME=? ,FILE_SIZE=?,UPDATE_By=?,UPDATE_DATE=SYSDATE,FILE_TYPE=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND ATTACH_ID=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			//log.debug("prmAttachmentHistoryDataM.getSeq()="+prmAttachmentHistoryDataM.getSeq());
			log.debug("prmAttachmentHistoryDataM.getAttachId()="+prmAttachmentHistoryDataM.getAttachId());
			log.debug("prmAttachmentHistoryDataM.getApplicationRecordId()="+prmAttachmentHistoryDataM.getApplicationRecordId());
			ps = conn.prepareStatement(dSql);

			ps.setString(1, prmAttachmentHistoryDataM.getFileName());
			ps.setInt(2, prmAttachmentHistoryDataM.getFileSize());
			ps.setString(3, prmAttachmentHistoryDataM.getUpdateBy());
			ps.setString(4, prmAttachmentHistoryDataM.getFileType());
	     	ps.setString(5, prmAttachmentHistoryDataM.getApplicationRecordId());
		    ps.setInt(6, prmAttachmentHistoryDataM.getAttachId());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigAttachmentHistoryMException(e.getMessage());
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
	 * @param attachVect
	 * @param appRecordID
	 * @param cmpCode
	 * @param idNo
	 * @throws OrigAttachmentHistoryMException
	 */
	public void deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(Vector attachVect, String appRecordID) throws OrigAttachmentHistoryMException{
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_ATTACHMENT_HISTORY");
            sql.append(" WHERE APPLICATION_RECORD_ID = ?");
            
            if ((attachVect != null) && (attachVect.size() != 0)) {
                sql.append(" AND ATTACH_ID NOT IN ( ");

                for (int i = 0; i < attachVect.size(); i++) {
                    AttachmentHistoryDataM attachmentHistoryDataM = (AttachmentHistoryDataM) attachVect.get(i);
                    sql.append(" '" + attachmentHistoryDataM.getAttachId() + "' , ");
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
			throw new OrigAttachmentHistoryMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new OrigAttachmentHistoryMException(e.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * @param appRecordID
	 * @param cmpCode
	 * @param idNo
	 * @throws OrigAttachmentHistoryMException
	 */
	public void deleteTableORIG_ATTACHMENT_HISTORY(String appRecordID)throws OrigAttachmentHistoryMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_ATTACHMENT_HISTORY ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql+" : appRecordID = "+appRecordID);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecordID);
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigAttachmentHistoryMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigAttachmentHistoryMException(e.getMessage());
			}
		}
		
	}
}
