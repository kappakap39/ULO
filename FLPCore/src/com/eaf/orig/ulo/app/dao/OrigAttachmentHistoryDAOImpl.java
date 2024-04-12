package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.AttachmentDataM;

public class OrigAttachmentHistoryDAOImpl extends OrigObjectDAO implements
		OrigAttachmentHistoryDAO {
	public OrigAttachmentHistoryDAOImpl(){
		
	}
	public OrigAttachmentHistoryDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigAttachmentHistoryM(
			AttachmentDataM attachmentHistoryDataM) throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("attachmentHistoryDataM.getAttachId() :"+attachmentHistoryDataM.getAttachId());
		    logger.debug("attachmentHistoryDataM.getApplicationGroupId() :"+attachmentHistoryDataM.getApplicationGroupId());
		    if(Util.empty(attachmentHistoryDataM.getAttachId())){
				String attachId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ATTACHMENT_PK);
				attachmentHistoryDataM.setAttachId(attachId);
				attachmentHistoryDataM.setCreateBy(userId);
		    }
		    attachmentHistoryDataM.setCreateBy(userId);
		    attachmentHistoryDataM.setUpdateBy(userId);
			createTableORIG_ATTACHMENT_HISTORY(attachmentHistoryDataM);
		   
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_ATTACHMENT_HISTORY(
			AttachmentDataM attachmentHistory) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_ATTACHMENT_HISTORY ");
			sql.append("( APPLICATION_GROUP_ID, ATTACH_ID ,FILE_NAME ,FILE_SIZE, ");
			sql.append(" CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE ");
			sql.append(" ,FILE_TYPE, FILE_PATH, MIME_TYPE, FILE_TYPE_OTH, REF_ID )");
			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,?,?,?,?) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			int cnt =1;
			ps.setString(cnt++, attachmentHistory.getApplicationGroupId());
			ps.setString(cnt++, attachmentHistory.getAttachId());
			ps.setString(cnt++, attachmentHistory.getFileName());
			ps.setBigDecimal(cnt++, attachmentHistory.getFileSize());
			
			ps.setString(cnt++, attachmentHistory.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, attachmentHistory.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setString(cnt++, attachmentHistory.getFileType());
			ps.setString(cnt++, attachmentHistory.getFilePath());
			ps.setString(cnt++, attachmentHistory.getMimeType());
			ps.setString(cnt++, attachmentHistory.getFileTypeOth());
			ps.setString(cnt++, attachmentHistory.getRefId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigAttachmentHistoryM(String applicationGroupId, String attachId) 
			throws ApplicationException {
		deleteTableORIG_ATTACHMENT_HISTORY(applicationGroupId, attachId);
	}

	private void deleteTableORIG_ATTACHMENT_HISTORY(String applicationGroupId, 
			String attachId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_ATTACHMENT_HISTORY ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(attachId != null && !attachId.isEmpty()) {
				sql.append(" AND ATTACH_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(attachId != null && !attachId.isEmpty()) {
				ps.setString(2, attachId);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<AttachmentDataM> loadOrigAttachmentHistoryM(
			String applicationGroupId) throws ApplicationException {		
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_ATTACHMENT_HISTORY(applicationGroupId,conn);
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
		
	}
	@Override
	public ArrayList<AttachmentDataM> loadOrigAttachmentHistoryM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		return selectTableORIG_ATTACHMENT_HISTORY(applicationGroupId, conn);
	}
	private ArrayList<AttachmentDataM> selectTableORIG_ATTACHMENT_HISTORY(
			String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ATTACH_ID ,FILE_NAME ,FILE_SIZE, FILE_PATH, MIME_TYPE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, FILE_TYPE, FILE_TYPE_OTH, REF_ID ");
			sql.append("FROM ORIG_ATTACHMENT_HISTORY WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();
			ArrayList<AttachmentDataM> vt = new ArrayList<AttachmentDataM>();
			while (rs.next()) {
				AttachmentDataM attachmentDataM = new AttachmentDataM();
				attachmentDataM.setApplicationGroupId(applicationGroupId);
				attachmentDataM.setAttachId(rs.getString("ATTACH_ID"));
				attachmentDataM.setFileName(rs.getString("FILE_NAME"));
				attachmentDataM.setFileSize(rs.getBigDecimal("FILE_SIZE"));
				attachmentDataM.setFilePath(rs.getString("FILE_PATH"));
				attachmentDataM.setMimeType(rs.getString("MIME_TYPE"));
				attachmentDataM.setCreateBy(rs.getString("CREATE_BY"));
				attachmentDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				attachmentDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				attachmentDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				attachmentDataM.setFileType(rs.getString("FILE_TYPE"));
				attachmentDataM.setFileTypeOth(rs.getString("FILE_TYPE_OTH"));
				attachmentDataM.setRefId(rs.getString("REF_ID"));
				vt.add(attachmentDataM);
			}

			return vt;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection( rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigAttachmentHistoryM(
			AttachmentDataM attachmentHistory) throws ApplicationException {
		double returnRows = 0;
		try {
			attachmentHistory.setUpdateBy(userId);
			returnRows = updateTableORIG_ATTACHMENT_HISTORY(attachmentHistory);
			if (returnRows == 0) {
				attachmentHistory.setCreateBy(userId);
				createOrigAttachmentHistoryM(attachmentHistory);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableORIG_ATTACHMENT_HISTORY(
			AttachmentDataM attachmentHistory) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_ATTACHMENT_HISTORY ");
			sql.append(" SET FILE_NAME = ? ,FILE_SIZE = ?, FILE_PATH = ?, MIME_TYPE = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ?, FILE_TYPE = ? ,FILE_TYPE_OTH=? ");
			sql.append(" ,REF_ID = ? ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND ATTACH_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			logger.debug("attachmentHistoryDataM.getAttachId()="+attachmentHistory.getAttachId());
			logger.debug("attachmentHistoryDataM.getApplicationGroupId()="+attachmentHistory.getApplicationGroupId());
			ps = conn.prepareStatement(dSql);

			int cnt =1;
			ps.setString(cnt++, attachmentHistory.getFileName());
			ps.setBigDecimal(cnt++, attachmentHistory.getFileSize());
			ps.setString(cnt++, attachmentHistory.getFilePath());
			ps.setString(cnt++, attachmentHistory.getMimeType());
			
			ps.setString(cnt++, attachmentHistory.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, attachmentHistory.getFileType());
			ps.setString(cnt++, attachmentHistory.getFileTypeOth());
			ps.setString(cnt++, attachmentHistory.getRefId());
			
			//WHERE CLAUSE
	     	ps.setString(cnt++, attachmentHistory.getApplicationGroupId());
		    ps.setString(cnt++, attachmentHistory.getAttachId());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void deleteNotInKeyAttachmentHistory(
			ArrayList<AttachmentDataM> attachList, String applicationGroupId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyAttachmentHistory(attachList, applicationGroupId, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void deleteNotInKeyAttachmentHistory(
			ArrayList<AttachmentDataM> attachList, String applicationGroupId,
			Connection conn) throws ApplicationException {
		deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(attachList, applicationGroupId, conn);
	}
	private void deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(
			ArrayList<AttachmentDataM> attachList, String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_ATTACHMENT_HISTORY ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (attachList != null && !attachList.isEmpty()) {
                sql.append(" AND ATTACH_ID NOT IN ( ");

                for (AttachmentDataM attachdata: attachList) {
                    sql.append(" '" + attachdata.getAttachId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	

@Override
	public AttachmentDataM loadAttachmentInfo(String attachId)throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_GROUP_ID ,ATTACH_ID ,FILE_NAME ,FILE_SIZE, FILE_PATH, MIME_TYPE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, FILE_TYPE, FILE_TYPE_OTH, REF_ID   ");
			sql.append("FROM ORIG_ATTACHMENT_HISTORY WHERE ATTACH_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, attachId);

			rs = ps.executeQuery();
			AttachmentDataM attachmentDataM = new AttachmentDataM();
			if (rs.next()) {
				attachmentDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				attachmentDataM.setAttachId(rs.getString("ATTACH_ID"));
				attachmentDataM.setFileName(rs.getString("FILE_NAME"));
				attachmentDataM.setFileSize(rs.getBigDecimal("FILE_SIZE"));
				attachmentDataM.setFilePath(rs.getString("FILE_PATH"));
				attachmentDataM.setMimeType(rs.getString("MIME_TYPE"));
				attachmentDataM.setCreateBy(rs.getString("CREATE_BY"));
				attachmentDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				attachmentDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				attachmentDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				attachmentDataM.setFileType(rs.getString("FILE_TYPE"));
				attachmentDataM.setFileTypeOth(rs.getString("FILE_TYPE_OTH"));
				attachmentDataM.setRefId(rs.getString("REF_ID"));
			}

			return attachmentDataM;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

}
