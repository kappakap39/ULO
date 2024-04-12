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
import com.eaf.orig.ulo.model.app.AuditTrailDataM;

public class OrigAuditTrailDAOImpl extends OrigObjectDAO implements
		OrigAuditTrailDAO {
	public OrigAuditTrailDAOImpl(){
		
	}
	public OrigAuditTrailDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigAuditTrailM(AuditTrailDataM auditTrailDataM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigAuditTrailM(auditTrailDataM, conn);
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
	public void createOrigAuditTrailM(AuditTrailDataM auditTrailDataM,
			Connection conn) throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("auditTrailDataM.getAuditTrailId() :"+auditTrailDataM.getAuditTrailId());
		    if(Util.empty(auditTrailDataM.getAuditTrailId())){
				String auditTrailId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_AUDIT_TRAIL_PK,conn);
				auditTrailDataM.setAuditTrailId(auditTrailId);
				if(Util.empty(auditTrailDataM.getCreateBy())){
					auditTrailDataM.setCreateBy(userId);
				}
			    auditTrailDataM.setUpdateBy(userId);
				createTableORIG_AUDIT_TRAIL(auditTrailDataM,conn);
		    }

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void createTableORIG_AUDIT_TRAIL(AuditTrailDataM auditTrailDataM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_AUDIT_TRAIL ");
			sql.append("( AUDIT_TRAIL_ID, APPLICATION_GROUP_ID, ROLE, CREATE_ROLE, FIELD_NAME, ");
			sql.append(" OLD_VALUE, NEW_VALUE, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY, FIRST_VALUE_FLAG) ");

			sql.append(" VALUES(?,?,?,?,?,?,?,  ?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, auditTrailDataM.getAuditTrailId());
			ps.setString(cnt++, auditTrailDataM.getApplicationGroupId());
			ps.setString(cnt++, auditTrailDataM.getRole());
			ps.setString(cnt++, auditTrailDataM.getCreateRole());
			ps.setString(cnt++, auditTrailDataM.getFieldName());
			ps.setString(cnt++, auditTrailDataM.getOldValue());
			ps.setString(cnt++, auditTrailDataM.getNewValue());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, auditTrailDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, auditTrailDataM.getUpdateBy());
			ps.setString(cnt++, auditTrailDataM.getFirstValueFlag());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public ArrayList<AuditTrailDataM> loadOrigAuditTrailM(
			String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_AUDIT_TRAIL(applicationGroupId,conn);
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
	public ArrayList<AuditTrailDataM> loadOrigAuditTrailM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		return selectTableORIG_AUDIT_TRAIL(applicationGroupId, conn);
	}
	@Override
	public ArrayList<AuditTrailDataM> loadAllOrigAuditTrailM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		return selectTableAllORIG_AUDIT_TRAIL(applicationGroupId, conn);
	}
	private ArrayList<AuditTrailDataM> selectTableORIG_AUDIT_TRAIL(
			String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AuditTrailDataM> auditList = new ArrayList<AuditTrailDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT AUDIT_TRAIL_ID, ROLE, CREATE_ROLE, FIELD_NAME, OLD_VALUE, NEW_VALUE, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM ORIG_AUDIT_TRAIL  WHERE APPLICATION_GROUP_ID = ? AND NVL(FIRST_VALUE_FLAG,'N') <> 'Y' ORDER BY UPDATE_DATE ASC ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				AuditTrailDataM auditTrailDataM = new AuditTrailDataM();
				auditTrailDataM.setAuditTrailId(rs.getString("AUDIT_TRAIL_ID"));
				auditTrailDataM.setApplicationGroupId(applicationGroupId);
				auditTrailDataM.setRole(rs.getString("ROLE"));
				auditTrailDataM.setCreateRole(rs.getString("CREATE_ROLE"));
				auditTrailDataM.setFieldName(rs.getString("FIELD_NAME"));
				auditTrailDataM.setOldValue(rs.getString("OLD_VALUE"));
				auditTrailDataM.setNewValue(rs.getString("NEW_VALUE"));
				
				auditTrailDataM.setCreateBy(rs.getString("CREATE_BY"));
				auditTrailDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				auditTrailDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				auditTrailDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				auditList.add(auditTrailDataM);
			}

			return auditList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	private ArrayList<AuditTrailDataM> selectTableAllORIG_AUDIT_TRAIL(
			String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AuditTrailDataM> auditList = new ArrayList<AuditTrailDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT AUDIT_TRAIL_ID, ROLE, CREATE_ROLE, FIELD_NAME, OLD_VALUE, NEW_VALUE, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM ORIG_AUDIT_TRAIL  WHERE APPLICATION_GROUP_ID = ?  ORDER BY UPDATE_DATE ASC ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				AuditTrailDataM auditTrailDataM = new AuditTrailDataM();
				auditTrailDataM.setAuditTrailId(rs.getString("AUDIT_TRAIL_ID"));
				auditTrailDataM.setApplicationGroupId(applicationGroupId);
				auditTrailDataM.setRole(rs.getString("ROLE"));
				auditTrailDataM.setCreateRole(rs.getString("CREATE_ROLE"));
				auditTrailDataM.setFieldName(rs.getString("FIELD_NAME"));
				auditTrailDataM.setOldValue(rs.getString("OLD_VALUE"));
				auditTrailDataM.setNewValue(rs.getString("NEW_VALUE"));
				
				auditTrailDataM.setCreateBy(rs.getString("CREATE_BY"));
				auditTrailDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				auditTrailDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				auditTrailDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				auditList.add(auditTrailDataM);
			}

			return auditList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	

}
