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
import com.eaf.orig.ulo.model.app.ReasonLogDataM;

public class OrigReasonLogDAOImpl extends OrigObjectDAO implements
		OrigReasonLogDAO {
	public OrigReasonLogDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigReasonLogDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigReasonLogM(ReasonLogDataM reasonLogM)
			throws ApplicationException {
		logger.debug("reasonLogM.getReasonLogId() :"+reasonLogM.getReasonLogId());
	    if(Util.empty(reasonLogM.getReasonLogId())){
			String reasonLogId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_REASON_LOG_PK);
			reasonLogM.setReasonLogId(reasonLogId);
			reasonLogM.setCreateBy(userId);
	    }
		createTableORIG_REASON_LOG(reasonLogM);
	}

	private void createTableORIG_REASON_LOG(ReasonLogDataM reasonLogM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_REASON_LOG ");
			sql.append("( APPLICATION_RECORD_ID, REASON_LOG_ID, REASON_TYPE, ");
			sql.append(" REASON_CODE, ROLE, REMARK, REASON_OTH_DESC, CREATE_DATE, CREATE_BY, APPLICATION_GROUP_ID ) ");

			sql.append(" VALUES(?,?,?, ?,?,?,?,  ?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, reasonLogM.getApplicationRecordId());
			ps.setString(cnt++, reasonLogM.getReasonLogId());
			ps.setString(cnt++, reasonLogM.getReasonType());
			
			ps.setString(cnt++, reasonLogM.getReasonCode());
			ps.setString(cnt++, reasonLogM.getRole());
			ps.setString(cnt++, reasonLogM.getRemark());		
			ps.setString(cnt++, reasonLogM.getReasonOthDesc());		
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, reasonLogM.getCreateBy());
			ps.setString(cnt++, reasonLogM.getApplicationGroupId());
			
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
	public void deleteOrigReasonLogM(String applicationRecordId, String reasonLogId)
			throws ApplicationException {
		deleteTableORIG_REASON_LOG(applicationRecordId, reasonLogId);
	}

	private void deleteTableORIG_REASON_LOG(String applicationRecordId, String reasonLogId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_REASON_LOG ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			if(reasonLogId != null && !reasonLogId.isEmpty()) {
				sql.append(" AND REASON_LOG_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationRecordId);
			if(reasonLogId != null && !reasonLogId.isEmpty()) {
				ps.setString(2, reasonLogId);
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
	public ArrayList<ReasonLogDataM> loadOrigReasonLogM(
			String applicationRecordId) throws ApplicationException {
		ArrayList<ReasonLogDataM> result = selectTableORIG_REASON_LOG(applicationRecordId);
		return result;
	}
	@Override
	public ArrayList<ReasonLogDataM> loadOrigReasonLogMByAppGroupId(
			String applicationGroupId) throws ApplicationException {
		ArrayList<ReasonLogDataM> result = selectTableORIG_REASON_LOGByAppGroupId(applicationGroupId);
		return result;
	}
	
	private ArrayList<ReasonLogDataM> selectTableORIG_REASON_LOGByAppGroupId(String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<ReasonLogDataM> reasonLogList = new ArrayList<ReasonLogDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID, REASON_LOG_ID, REASON_TYPE, ");
			sql.append(" REASON_CODE, ROLE, REMARK, REASON_OTH_DESC, CREATE_DATE, CREATE_BY ,APPLICATION_GROUP_ID ");
			sql.append(" FROM ORIG_REASON_LOG WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ReasonLogDataM reasonLogM = new ReasonLogDataM();
				reasonLogM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				reasonLogM.setReasonLogId(rs.getString("REASON_LOG_ID"));
				reasonLogM.setReasonType(rs.getString("REASON_TYPE"));
				reasonLogM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				reasonLogM.setReasonCode(rs.getString("REASON_CODE"));
				reasonLogM.setRole(rs.getString("ROLE"));
				reasonLogM.setRemark(rs.getString("REMARK"));				
				reasonLogM.setReasonOthDesc(rs.getString("REASON_OTH_DESC"));				
				reasonLogM.setCreateBy(rs.getString("CREATE_BY"));
				reasonLogM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				
				reasonLogList.add(reasonLogM);
			}

			return reasonLogList;
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
	
	private ArrayList<ReasonLogDataM> selectTableORIG_REASON_LOG(String applicationRecordId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<ReasonLogDataM> reasonLogList = new ArrayList<ReasonLogDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID, REASON_LOG_ID, REASON_TYPE, ");
			sql.append(" REASON_CODE, ROLE, REMARK, REASON_OTH_DESC, CREATE_DATE, CREATE_BY ");
			sql.append(" FROM ORIG_REASON_LOG WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ReasonLogDataM reasonLogM = new ReasonLogDataM();
				reasonLogM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				reasonLogM.setReasonLogId(rs.getString("REASON_LOG_ID"));
				reasonLogM.setReasonType(rs.getString("REASON_TYPE"));
				
				reasonLogM.setReasonCode(rs.getString("REASON_CODE"));
				reasonLogM.setRole(rs.getString("ROLE"));
				reasonLogM.setRemark(rs.getString("REMARK"));				
				reasonLogM.setReasonOthDesc(rs.getString("REASON_OTH_DESC"));				
				reasonLogM.setCreateBy(rs.getString("CREATE_BY"));
				reasonLogM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				
				reasonLogList.add(reasonLogM);
			}

			return reasonLogList;
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
	public void saveUpdateOrigReasonLogM(ReasonLogDataM reasonLogM)
			throws ApplicationException {
		int returnRows = 0;
		returnRows = updateTableORIG_REASON_LOG(reasonLogM);
		if (returnRows == 0) {
			reasonLogM.setCreateBy(userId);
			createOrigReasonLogM(reasonLogM);
		}
	}

	private int updateTableORIG_REASON_LOG(ReasonLogDataM reasonLogM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_REASON_LOG ");
			sql.append(" SET REASON_TYPE = ?, REASON_CODE = ?, ROLE = ?, REMARK = ?, REASON_OTH_DESC= ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND REASON_LOG_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, reasonLogM.getReasonType());
			ps.setString(cnt++, reasonLogM.getReasonCode());
			ps.setString(cnt++, reasonLogM.getRole());
			ps.setString(cnt++, reasonLogM.getRemark());	
			ps.setString(cnt++, reasonLogM.getReasonOthDesc());	
			// WHERE CLAUSE
			ps.setString(cnt++, reasonLogM.getApplicationRecordId());	
			ps.setString(cnt++, reasonLogM.getReasonLogId());		
			
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
