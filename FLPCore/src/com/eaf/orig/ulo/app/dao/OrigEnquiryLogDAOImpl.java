package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.EnquiryLogDataM;

public class OrigEnquiryLogDAOImpl extends OrigObjectDAO implements
		OrigEnquiryLogDAO {
	public OrigEnquiryLogDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigEnquiryLogDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigEnquiryLogM(EnquiryLogDataM enquiryLogM)
			throws ApplicationException {
		logger.debug("enquiryLogM.getEnquiryLogId() :"+enquiryLogM.getEnquiryLogId());
	    if(Util.empty(enquiryLogM.getEnquiryLogId())){
			String enquiryLogId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ENQUIRY_LOG_PK);
			enquiryLogM.setEnquiryLogId(enquiryLogId);
	    }
		createTableORIG_ENQUIRY_LOG(enquiryLogM);
	}

	private void createTableORIG_ENQUIRY_LOG(EnquiryLogDataM enquiryLogM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_ENQUIRY_LOG ");
			sql.append("( ENQUIRY_LOG_ID,APPLICATION_GROUP_ID, USERNAME, ENQUIRY_DATE ) ");

			sql.append(" VALUES(?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, enquiryLogM.getEnquiryLogId());
			ps.setString(cnt++, enquiryLogM.getApplicationGroupId());
			ps.setString(cnt++, enquiryLogM.getUsername());
			ps.setDate(cnt++, enquiryLogM.getEnquiryDate());
			
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
	public void deleteOrigEnquiryLogM(String applicationGroupId, String enquiryLogId)
			throws ApplicationException {
		deleteTableORIG_ENQUIRY_LOG(applicationGroupId, enquiryLogId);
	}

	private void deleteTableORIG_ENQUIRY_LOG(String applicationGroupId, 
			String enquiryLogId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_ENQUIRY_LOG ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(enquiryLogId != null && !enquiryLogId.isEmpty()) {
				sql.append(" AND ENQUIRY_LOG_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(enquiryLogId != null && !enquiryLogId.isEmpty()) {
				ps.setString(2, enquiryLogId);
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
	public ArrayList<EnquiryLogDataM> loadOrigEnquiryLogM(
			String applicationGroupId) throws ApplicationException {
		ArrayList<EnquiryLogDataM> result = selectTableORIG_ENQUIRY_LOG(applicationGroupId);
		return result;
	}

	private ArrayList<EnquiryLogDataM> selectTableORIG_ENQUIRY_LOG(
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<EnquiryLogDataM> enquiryLogList = new ArrayList<EnquiryLogDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ENQUIRY_LOG_ID,APPLICATION_GROUP_ID, USERNAME, ENQUIRY_DATE ");
			sql.append(" FROM ORIG_ENQUIRY_LOG WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				EnquiryLogDataM enquiryLogM = new EnquiryLogDataM();
				enquiryLogM.setEnquiryLogId(rs.getString("ENQUIRY_LOG_ID"));
				enquiryLogM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				enquiryLogM.setUsername(rs.getString("USERNAME"));
				enquiryLogM.setEnquiryDate(rs.getDate("ENQUIRY_DATE"));
				enquiryLogList.add(enquiryLogM);
			}

			return enquiryLogList;
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

	@Override
	public void saveUpdateOrigEnquiryLogM(EnquiryLogDataM enquiryLogM)
			throws ApplicationException {
		int returnRows = 0;
		returnRows = updateTableORIG_ENQUIRY_LOG(enquiryLogM);
		if (returnRows == 0) {
			createOrigEnquiryLogM(enquiryLogM);
		}
	}

	private int updateTableORIG_ENQUIRY_LOG(EnquiryLogDataM enquiryLogM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_ENQUIRY_LOG ");
			sql.append(" SET USERNAME = ?, ENQUIRY_DATE = ? ");			
			sql.append(" WHERE ENQUIRY_LOG_ID = ? AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, enquiryLogM.getUsername());
			ps.setDate(cnt++, enquiryLogM.getEnquiryDate());
			// WHERE CLAUSE
			ps.setString(cnt++, enquiryLogM.getEnquiryLogId());		
			ps.setString(cnt++, enquiryLogM.getApplicationGroupId());			
			
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
	public void deleteNotInKeyEnquiryLog(
			ArrayList<EnquiryLogDataM> enquiryLogList, String applicationGroupId)
			throws ApplicationException {
		deleteNotInKeyTableORIG_ENQUIRY_LOG(enquiryLogList, applicationGroupId);
	}

	private void deleteNotInKeyTableORIG_ENQUIRY_LOG(
			ArrayList<EnquiryLogDataM> enquiryLogList, String applicationGroupId) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_ENQUIRY_LOG ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (enquiryLogList != null && !enquiryLogList.isEmpty()) {
                sql.append(" AND ENQUIRY_LOG_ID NOT IN ( ");
                for (EnquiryLogDataM enquiryLogM: enquiryLogList) {
                	logger.debug("enquiryLogM.getEnquiryLogId() = "+enquiryLogM.getEnquiryLogId());
                    sql.append(" '" + enquiryLogM.getEnquiryLogId() + "', ");
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
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
