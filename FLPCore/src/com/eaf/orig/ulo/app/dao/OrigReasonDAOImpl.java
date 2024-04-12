package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ReasonDataM;

public class OrigReasonDAOImpl extends OrigObjectDAO implements OrigReasonDAO {
	public OrigReasonDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigReasonDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigReasonM(ReasonDataM reasonM)
			throws ApplicationException {
		reasonM.setCreateBy(userId);
		reasonM.setUpdateBy(userId);
		createTableORIG_REASON(reasonM);
	}

	private void createTableORIG_REASON(ReasonDataM reasonM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_REASON ");
			sql.append("( APPLICATION_RECORD_ID, REASON_TYPE, REASON_CODE, ROLE, REMARK, ");
			sql.append(" REASON_OTH_DESC, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, APPLICATION_GROUP_ID ) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, reasonM.getApplicationRecordId());
			ps.setString(cnt++, reasonM.getReasonType());
			ps.setString(cnt++, reasonM.getReasonCode());
			ps.setString(cnt++, reasonM.getRole());
			ps.setString(cnt++, reasonM.getRemark());
			
			ps.setString(cnt++, reasonM.getReasonOthDesc());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, reasonM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, reasonM.getUpdateBy());
			ps.setString(cnt++, reasonM.getApplicationGroupId());
			
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
	public void deleteOrigReasonMByAppGroupId(String applicationGroupId)
			throws ApplicationException {
		deleteTableORIG_REASONAppGroupId(applicationGroupId);
		
	}
	@Override
	public ArrayList<ReasonDataM> loadOrigReasonMByAppGroupId(String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_REASONAppGroupId(applicationGroupId,conn);
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public ArrayList<ReasonDataM> loadOrigReasonMByAppGroupId(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		return selectTableORIG_REASONAppGroupId(applicationGroupId,conn);
	}
	@Override
	public void deleteOrigReasonM(String applicationRecordId)
			throws ApplicationException {
		deleteTableORIG_REASON(applicationRecordId);
	}
	
	private void deleteTableORIG_REASONAppGroupId(String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_REASON ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
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
	
	private void deleteTableORIG_REASON(String applicationRecordId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_REASON ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationRecordId);
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
	public ArrayList<ReasonDataM> loadOrigReasonM(String applicationRecordId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_REASON(applicationRecordId,conn);
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
	public ArrayList<ReasonDataM> loadOrigReasonM(String applicationRecordId,
			Connection conn) throws ApplicationException {
		return selectTableORIG_REASON(applicationRecordId, conn);
	}
	private ArrayList<ReasonDataM> selectTableORIG_REASONAppGroupId(String applicationGroupId,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ReasonDataM> reasonList = new ArrayList<ReasonDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID, APPLICATION_GROUP_ID, REASON_TYPE, REASON_CODE, ROLE, REMARK, ");
			sql.append(" REASON_OTH_DESC, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_REASON WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ReasonDataM reasonM = new ReasonDataM();
				reasonM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				reasonM.setReasonType(rs.getString("REASON_TYPE"));
				reasonM.setReasonCode(rs.getString("REASON_CODE"));
				reasonM.setRole(rs.getString("ROLE"));
				reasonM.setRemark(rs.getString("REMARK"));
				reasonM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				
				reasonM.setReasonOthDesc(rs.getString("REASON_OTH_DESC"));
				reasonM.setCreateBy(rs.getString("CREATE_BY"));
				reasonM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				reasonM.setUpdateBy(rs.getString("UPDATE_BY"));
				reasonM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				reasonList.add(reasonM);
			}

			return reasonList;
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
	
	private ArrayList<ReasonDataM> selectTableORIG_REASON(String applicationRecordId,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ReasonDataM> reasonList = new ArrayList<ReasonDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID, APPLICATION_GROUP_ID,REASON_TYPE, REASON_CODE, ROLE, REMARK, ");
			sql.append(" REASON_OTH_DESC, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_REASON WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ReasonDataM reasonM = new ReasonDataM();
				reasonM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				reasonM.setReasonType(rs.getString("REASON_TYPE"));
				reasonM.setReasonCode(rs.getString("REASON_CODE"));
				reasonM.setRole(rs.getString("ROLE"));
				reasonM.setRemark(rs.getString("REMARK"));
				reasonM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				
				reasonM.setReasonOthDesc(rs.getString("REASON_OTH_DESC"));
				reasonM.setCreateBy(rs.getString("CREATE_BY"));
				reasonM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				reasonM.setUpdateBy(rs.getString("UPDATE_BY"));
				reasonM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				reasonList.add(reasonM);
			}

			return reasonList;
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

	@Override
	public void saveUpdateOrigReasonM(ReasonDataM reasonM)
			throws ApplicationException {
		int returnRows = 0;
		reasonM.setUpdateBy(userId);
		returnRows = updateTableORIG_REASON(reasonM);
		if (returnRows == 0) {
			reasonM.setCreateBy(userId);
			createTableORIG_REASON(reasonM);
		}
	}

	private int updateTableORIG_REASON(ReasonDataM reasonM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_REASON ");
			sql.append(" SET ROLE = ?, REMARK = ?, ");
			sql.append(" REASON_OTH_DESC = ? , UPDATE_DATE = ?, UPDATE_BY = ? ");			
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND REASON_TYPE = ? AND REASON_CODE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			
			ps.setString(cnt++, reasonM.getRole());
			ps.setString(cnt++, reasonM.getRemark());
			
			ps.setString(cnt++, reasonM.getReasonOthDesc());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, reasonM.getUpdateBy());		
			
			// WHERE CLAUSE
			ps.setString(cnt++, reasonM.getApplicationRecordId());
			ps.setString(cnt++, reasonM.getReasonType());
			ps.setString(cnt++, reasonM.getReasonCode());
			
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
}
