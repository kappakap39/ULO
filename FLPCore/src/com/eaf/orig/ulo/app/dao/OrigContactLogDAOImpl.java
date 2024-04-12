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
import com.eaf.orig.ulo.model.app.ContactLogDataM;

public class OrigContactLogDAOImpl extends OrigObjectDAO implements
		OrigContactLogDAO {
	public OrigContactLogDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigContactLogDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigContactLogM(ContactLogDataM contactLogM)
			throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("contactLogM.getContactLogId() :"+contactLogM.getContactLogId());
		    if(Util.empty(contactLogM.getContactLogId())){
				String contactLogId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CONTACT_LOG_PK);
				contactLogM.setContactLogId(contactLogId);
				contactLogM.setCreateBy(userId);
		    }
			createTableORIG_CONTACT_LOG(contactLogM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_CONTACT_LOG(ContactLogDataM contactLogM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_CONTACT_LOG ");
			sql.append("( CONTACT_LOG_ID, APPLICATION_GROUP_ID, SEND_TO, SEND_BY, ");
			sql.append(" SEND_DATE, SEND_STATUS, CONTACT_TYPE, TEMPLATE_NAME, CC_TO, ");
			sql.append(" CREATE_DATE,CREATE_BY )");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, contactLogM.getContactLogId());
			ps.setString(cnt++, contactLogM.getApplicationGroupId());
			ps.setString(cnt++, contactLogM.getSendTo());
			ps.setString(cnt++, contactLogM.getSendBy());
			
			ps.setDate(cnt++, contactLogM.getSendDate());			
			ps.setString(cnt++, contactLogM.getSendStatus());			
			ps.setString(cnt++, contactLogM.getContactType());
			ps.setString(cnt++, contactLogM.getTemplateName());
			ps.setString(cnt++, contactLogM.getCcTo());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, contactLogM.getCreateBy());
			
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
	public void deleteOrigContactLogM(String applicationGroupId, String contactLogId)
			throws ApplicationException {
		deleteTableORIG_CONTACT_LOG(applicationGroupId, contactLogId);
	}

	private void deleteTableORIG_CONTACT_LOG(String applicationGroupId, String contactLogId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_CONTACT_LOG ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?");
			if(contactLogId != null && !contactLogId.isEmpty()) {
				sql.append(" AND CONTACT_LOG_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(contactLogId != null && !contactLogId.isEmpty()) {
				ps.setString(2, contactLogId);
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
	public ArrayList<ContactLogDataM> loadOrigContactLogM(
			String applicationGroupId) throws ApplicationException {
		ArrayList<ContactLogDataM> result = selectTableORIG_CONTACT_LOG(applicationGroupId);
		return result;
	}

	private ArrayList<ContactLogDataM> selectTableORIG_CONTACT_LOG(
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<ContactLogDataM> contactLogLst = new ArrayList<ContactLogDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CONTACT_LOG_ID, APPLICATION_GROUP_ID, SEND_TO, SEND_BY, SEND_DATE,");
			sql.append(" SEND_STATUS, CONTACT_TYPE, TEMPLATE_NAME, CC_TO, ");
			sql.append(" CREATE_DATE,CREATE_BY ");
			sql.append(" FROM ORIG_CONTACT_LOG  WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ContactLogDataM contactLogM = new ContactLogDataM();
				contactLogM.setContactLogId(rs.getString("CONTACT_LOG_ID"));
				contactLogM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				contactLogM.setSendTo(rs.getString("SEND_TO"));
				contactLogM.setSendBy(rs.getString("SEND_BY"));
				contactLogM.setSendDate(rs.getDate("SEND_DATE"));
				contactLogM.setSendStatus(rs.getString("SEND_STATUS"));
				contactLogM.setContactType(rs.getString("CONTACT_TYPE"));
				contactLogM.setTemplateName(rs.getString("TEMPLATE_NAME"));
				contactLogM.setCcTo(rs.getString("CC_TO"));
				contactLogM.setCreateBy(rs.getString("CREATE_BY"));
				contactLogM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				contactLogLst.add(contactLogM);
			}

			return contactLogLst;
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
	public void saveUpdateOrigContactLogM(ContactLogDataM contactLogM)
			throws ApplicationException {
		int returnRows = 0;
		returnRows = updateTableORIG_CONTACT_LOG(contactLogM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_CONTACT_LOG then call Insert method");
			contactLogM.setCreateBy(userId);
			createOrigContactLogM(contactLogM);
		}
	}

	private int updateTableORIG_CONTACT_LOG(ContactLogDataM contactLogM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_CONTACT_LOG ");
			sql.append(" SET SEND_TO = ?, SEND_BY = ?, SEND_DATE = ?, SEND_STATUS = ?, ");
			sql.append(" CONTACT_TYPE = ?, TEMPLATE_NAME = ?, CC_TO = ? ");
			
			sql.append(" WHERE CONTACT_LOG_ID = ? AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, contactLogM.getSendTo());
			ps.setString(cnt++, contactLogM.getSendBy());
			ps.setDate(cnt++, contactLogM.getSendDate());
			ps.setString(cnt++, contactLogM.getSendStatus());
			ps.setString(cnt++, contactLogM.getContactType());
			ps.setString(cnt++, contactLogM.getTemplateName());			
			ps.setString(cnt++, contactLogM.getCcTo());	
			// WHERE CLAUSE
			ps.setString(cnt++, contactLogM.getContactLogId());		
			ps.setString(cnt++, contactLogM.getApplicationGroupId());	
			
			
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
	public void deleteNotInKeyContactLog(
			ArrayList<ContactLogDataM> contactLogList, String applicationGroupId)
			throws ApplicationException {
		deleteNotInKeyTableORIG_CONTACT_LOG(contactLogList, applicationGroupId);
	}

	private void deleteNotInKeyTableORIG_CONTACT_LOG(
			ArrayList<ContactLogDataM> contactLogList, String applicationGroupId) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_CONTACT_LOG ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (contactLogList != null && !contactLogList.isEmpty()) {
                sql.append(" AND CONTACT_LOG_ID NOT IN ( ");
                for (ContactLogDataM contactLogM: contactLogList) {
                	logger.debug("contactLogM.getContactLogId() = "+contactLogM.getContactLogId());
                    sql.append(" '" + contactLogM.getContactLogId()+ "', ");
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
