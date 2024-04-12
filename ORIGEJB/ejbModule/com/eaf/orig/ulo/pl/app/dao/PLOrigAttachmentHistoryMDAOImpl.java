/**
 * Create Date Mar 13, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
*/
package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;

/**
 * @author Sankom
 *
 */
public class PLOrigAttachmentHistoryMDAOImpl extends OrigObjectDAO implements
		PLOrigAttachmentHistoryMDAO {
	private static Logger log = Logger.getLogger(PLOrigAttachmentHistoryMDAOImpl.class);
	/* (non-Javadoc)
	 * @see com.eaf.orig.ulo.pl.app.dao.PLOrigAttachmentHistoryMDAO#createModelOrigAttachmentHistoryM(com.eaf.orig.shared.model.AttachmentHistoryDataM)
	 */
	@Override
	public void createModelOrigAttachmentHistoryM(
			PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM)
			throws PLOrigApplicationException {
		createTableORIG_ATTACHMENT_HISTORY(prmPLAttachmentHistoryDataM);
	}

	/**
	 * @param prmPLAttachmentHistoryDataM
	 */
	private void createTableORIG_ATTACHMENT_HISTORY (
			PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_ATTACHMENT_HISTORY ");
			sql
					.append("( APPLICATION_RECORD_ID,ATTACH_ID ,FILE_NAME ,FILE_SIZE,CREATE_BY");
			sql.append(" ,CREATE_DATE,UPDATE_BY,UPDATE_DATE,FILE_TYPE,FILE_PATH,MIME_TYPE   )");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,SYSDATE,?,?,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmPLAttachmentHistoryDataM.getApplicationRecordId());
			ps.setString(2, prmPLAttachmentHistoryDataM.getAttachId());
			ps.setString(3, prmPLAttachmentHistoryDataM.getFileName());
			ps.setLong(4, prmPLAttachmentHistoryDataM.getFileSize());
			ps.setString(5, prmPLAttachmentHistoryDataM.getCreateBy());
			ps.setTimestamp(6, prmPLAttachmentHistoryDataM.getCreateDate());
			ps.setString(7, prmPLAttachmentHistoryDataM.getUpdateBy());
			ps.setString(8, prmPLAttachmentHistoryDataM.getFileType());
			ps.setString(9, prmPLAttachmentHistoryDataM.getFilePath());
			ps.setString(10, prmPLAttachmentHistoryDataM.getMimeType());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.ulo.pl.app.dao.PLOrigAttachmentHistoryMDAO#deleteModelOrigAttachmentHistoryM(com.eaf.orig.shared.model.AttachmentHistoryDataM)
	 */
	@Override
	public void deleteModelOrigAttachmentHistoryM(
			PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM)
			throws PLOrigApplicationException {
		deleteTableORIG_ATTACHMENT_HISTORY(prmPLAttachmentHistoryDataM);

	}

	/**
	 * @param prmPLAttachmentHistoryDataM
	 */
	private void deleteTableORIG_ATTACHMENT_HISTORY(
			PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_ATTACHMENT_HISTORY ");
			sql.append(" WHERE  ATTACH_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);			
			ps.setString(1, prmPLAttachmentHistoryDataM.getAttachId());
			ps.executeUpdate();

		} catch (Exception e) {
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

	/* (non-Javadoc)
	 * @see com.eaf.orig.ulo.pl.app.dao.PLOrigAttachmentHistoryMDAO#loadModelOrigAttachmentHistoryM(java.lang.String)
	 */
	@Override
	public Vector<PLAttachmentHistoryDataM> loadModelOrigAttachmentHistoryM(String applicationRecordId)throws PLOrigApplicationException {
		return this.selectTableORIG_ATTACHMENT_HISTORY(applicationRecordId);
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.ulo.pl.app.dao.PLOrigAttachmentHistoryMDAO#saveUpdateModelOrigAttachmentHistoryM(com.eaf.orig.shared.model.AttachmentHistoryDataM)
	 */
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector<PLAttachmentHistoryDataM> selectTableORIG_ATTACHMENT_HISTORY(String applicationRecordId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLAttachmentHistoryDataM> vt = new Vector<PLAttachmentHistoryDataM>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ATTACH_ID ,FILE_NAME ,FILE_SIZE,CREATE_BY,CREATE_DATE  ,UPDATE_BY,UPDATE_DATE,FILE_TYPE,FILE_PATH,MIME_TYPE   ");
			sql.append("FROM ORIG_ATTACHMENT_HISTORY WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			while (rs.next()) {
				PLAttachmentHistoryDataM prmAttachmentHistoryDataM = new PLAttachmentHistoryDataM();
				prmAttachmentHistoryDataM.setApplicationRecordId(applicationRecordId);
				prmAttachmentHistoryDataM.setAttachId(rs.getString(1));
				prmAttachmentHistoryDataM.setFileName(rs.getString(2));
				prmAttachmentHistoryDataM.setFileSize(rs.getLong(3));
				prmAttachmentHistoryDataM.setCreateBy(rs.getString(4));
				prmAttachmentHistoryDataM.setCreateDate(rs.getTimestamp(5));
				prmAttachmentHistoryDataM.setUpdateBy(rs.getString(6));
				prmAttachmentHistoryDataM.setUpdateDate(rs.getTimestamp(7));
				prmAttachmentHistoryDataM.setFileType(rs.getString(8));
				prmAttachmentHistoryDataM.setFilePath(rs.getString(9));
				prmAttachmentHistoryDataM.setMimeType(rs.getString(10));
				vt.add(prmAttachmentHistoryDataM);
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
			}
		}
		return vt;
	}

	@Override
	public void saveUpdateModelOrigAttachmentHistoryM(
			PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM)
			throws PLOrigApplicationException {
		int returnRows = 0;
		try {
			returnRows = updateTableORIG_ATTACHMENT_HISTORY(prmPLAttachmentHistoryDataM);
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_ATTACHMENT_HISTORY then call Insert method");
				createTableORIG_ATTACHMENT_HISTORY(prmPLAttachmentHistoryDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		}	
			
	}

	/**
	 * @param prmPLAttachmentHistoryDataM
	 * @return
	 */
	private int updateTableORIG_ATTACHMENT_HISTORY(
			PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM) throws PLOrigApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_ATTACHMENT_HISTORY ");
			sql.append(" SET  APPLICATION_RECORD_ID=?,FILE_NAME=? ,FILE_SIZE=?,UPDATE_By=?,UPDATE_DATE=SYSDATE,FILE_TYPE=?,FILE_PATH=?,MIME_TYPE=? ");
			sql.append(" WHERE  ATTACH_ID=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmPLAttachmentHistoryDataM.getApplicationRecordId());
			ps.setString(2, prmPLAttachmentHistoryDataM.getFileName());
			ps.setLong(3, prmPLAttachmentHistoryDataM.getFileSize());
			ps.setString(4, prmPLAttachmentHistoryDataM.getUpdateBy());
			ps.setString(5, prmPLAttachmentHistoryDataM.getFileType());
	     	ps.setString(6, prmPLAttachmentHistoryDataM.getFilePath());
	    	ps.setString(7, prmPLAttachmentHistoryDataM.getMimeType());
		    ps.setString(8, prmPLAttachmentHistoryDataM.getAttachId());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
		 
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.ulo.pl.app.dao.PLOrigAttachmentHistoryMDAO#deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(java.util.Vector, java.lang.String)
	 */
	@Override
	public void deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(Vector<PLAttachmentHistoryDataM> attachVect,
			String appRecordID) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder("");
            sql.append("DELETE FROM ORIG_ATTACHMENT_HISTORY");
            sql.append(" WHERE APPLICATION_RECORD_ID = ?");
            
            if ((attachVect != null) && (attachVect.size() != 0)) {
                sql.append(" AND ATTACH_ID NOT IN ( ");

                for (int i = 0; i < attachVect.size(); i++) {
                	PLAttachmentHistoryDataM attachmentHistoryDataM = (PLAttachmentHistoryDataM) attachVect.get(i);
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
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new PLOrigApplicationException(e.getMessage());
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.ulo.pl.app.dao.PLOrigAttachmentHistoryMDAO#deleteTableORIG_ATTACHMENT_HISTORY(java.lang.String)
	 */
	@Override
	public void deleteTableORIG_ATTACHMENT_HISTORY(String appRecordID)
			throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_ATTACHMENT_HISTORY ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql+" : appRecordID = "+appRecordID);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecordID);
			
			ps.executeUpdate();

		} catch (Exception e) {
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

	/* (non-Javadoc)
	 * @see com.eaf.orig.ulo.pl.app.dao.PLOrigAttachmentHistoryMDAO#loadModelOrigAttachmentHistoryMFromAttachId(java.lang.String)
	 */
	@Override
	public PLAttachmentHistoryDataM loadModelOrigAttachmentHistoryMFromAttachId(String attachmentId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID ,FILE_NAME ,FILE_SIZE,CREATE_BY,CREATE_DATE  ,UPDATE_BY,UPDATE_DATE,FILE_TYPE,FILE_PATH,MIME_TYPE   ");
			sql.append("FROM ORIG_ATTACHMENT_HISTORY WHERE ATTACH_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, attachmentId);

			rs = ps.executeQuery();
			PLAttachmentHistoryDataM  prmAttachmentHistoryDataM = null;
			if (rs.next()) {
				prmAttachmentHistoryDataM = new PLAttachmentHistoryDataM(); 
				prmAttachmentHistoryDataM.setApplicationRecordId(rs.getString(1));
				prmAttachmentHistoryDataM.setAttachId(attachmentId);
				prmAttachmentHistoryDataM.setFileName(rs.getString(2));
				prmAttachmentHistoryDataM.setFileSize(rs.getLong(3));
				prmAttachmentHistoryDataM.setCreateBy(rs.getString(4));
				prmAttachmentHistoryDataM.setCreateDate(rs.getTimestamp(5));
				prmAttachmentHistoryDataM.setUpdateBy(rs.getString(6));
				prmAttachmentHistoryDataM.setUpdateDate(rs.getTimestamp(7));
				prmAttachmentHistoryDataM.setFileType(rs.getString(8));
				prmAttachmentHistoryDataM.setFilePath(rs.getString(9));
				prmAttachmentHistoryDataM.setMimeType(rs.getString(10));				
			}
			return prmAttachmentHistoryDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	public void deleteTableORIG_ATTACHMENT_HISTORY_ByAttachId(String attachId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_ATTACHMENT_HISTORY ");
			sql.append(" WHERE  ATTACH_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);			
			ps.setString(1, attachId);
			ps.executeUpdate();

		} catch (Exception e) {
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
	public Vector<PLAttachmentHistoryDataM> loadModelOrigAttachmentHistory(int day) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLAttachmentHistoryDataM> attachmentHisVT = new Vector<PLAttachmentHistoryDataM>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID ,FILE_NAME ,FILE_SIZE,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,FILE_TYPE,FILE_PATH,MIME_TYPE,ATTACH_ID ");
			sql.append("FROM ORIG_ATTACHMENT_HISTORY WHERE ATTACH_ID = ('CL'||to_char(sysdate-?,'yyyymmdd')) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, day);
			rs = null;

			rs = ps.executeQuery();
			if (rs.next()) {
				PLAttachmentHistoryDataM prmAttachmentHistoryDataM = new PLAttachmentHistoryDataM();				 
				prmAttachmentHistoryDataM.setApplicationRecordId(rs.getString(1));
				prmAttachmentHistoryDataM.setFileName(rs.getString(2));
				prmAttachmentHistoryDataM.setFileSize(rs.getLong(3));
				prmAttachmentHistoryDataM.setCreateBy(rs.getString(4));
				prmAttachmentHistoryDataM.setCreateDate(rs.getTimestamp(5));
				prmAttachmentHistoryDataM.setUpdateBy(rs.getString(6));
				prmAttachmentHistoryDataM.setUpdateDate(rs.getTimestamp(7));
				prmAttachmentHistoryDataM.setFileType(rs.getString(8));
				prmAttachmentHistoryDataM.setFilePath(rs.getString(9));
				prmAttachmentHistoryDataM.setMimeType(rs.getString(10));
				prmAttachmentHistoryDataM.setAttachId(rs.getString(11));	
				attachmentHisVT.add(prmAttachmentHistoryDataM);
			}
			return attachmentHisVT;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

}
