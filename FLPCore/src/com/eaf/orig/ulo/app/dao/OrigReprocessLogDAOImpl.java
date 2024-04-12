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
import com.eaf.orig.ulo.model.app.ReprocessLogDataM;

public class OrigReprocessLogDAOImpl extends OrigObjectDAO implements OrigReprocessLogDAO{
	public OrigReprocessLogDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigReprocessLogDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigReprocessLog(ReprocessLogDataM reprocessLog)throws ApplicationException{
	    if(Util.empty(reprocessLog.getReprocessLogId())){
			String reprocessLogId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_REPROCESS_LOG_PK);
			reprocessLog.setReprocessLogId(reprocessLogId);
			reprocessLog.setCreateBy(userId);
	    }
	    reprocessLog.setUpdateBy(userId);
		createTableORIG_REPROCESS_LOG(reprocessLog);
	}
	private void createTableORIG_REPROCESS_LOG(ReprocessLogDataM reprocessLog)throws ApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_REPROCESS_LOG ");
			sql.append("( REPROCESS_LOG_ID, APPLICATION_GROUP_ID, LIFE_CYCLE, ");
			sql.append("CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql : " + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, reprocessLog.getReprocessLogId());
			ps.setString(cnt++, reprocessLog.getApplicationGroupId());
			ps.setInt(cnt++, reprocessLog.getLifeCycle());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, reprocessLog.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, reprocessLog.getUpdateBy());
			
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
	public void deleteOrigReprocessLog(String applicationGroupId,String reprocessLogId)throws ApplicationException{
		deleteTableORIG_REPROCESS_LOG(applicationGroupId,reprocessLogId);
	}
	private void deleteTableORIG_REPROCESS_LOG(String applicationGroupId,String reprocessLogId)throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_REPROCESS_LOG ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(reprocessLogId != null && !reprocessLogId.isEmpty()) {
				sql.append(" AND REPROCESS_LOG_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql : " + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++,applicationGroupId);
			if(reprocessLogId != null && !reprocessLogId.isEmpty()) {
				ps.setString(index++,reprocessLogId);
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
	public ArrayList<ReprocessLogDataM> loadReprocessLog(String applicationGroupId,Connection conn)throws ApplicationException{
		return selectTableORIG_REPROCESS_LOG(applicationGroupId, conn);
	}
	@Override
	public ArrayList<ReprocessLogDataM> loadReprocessLog(String applicationGroupId)throws ApplicationException{
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_REPROCESS_LOG(applicationGroupId,conn);
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
	private ArrayList<ReprocessLogDataM> selectTableORIG_REPROCESS_LOG(String applicationGroupId,Connection conn)throws ApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ReprocessLogDataM> reprocessLogs = new ArrayList<ReprocessLogDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT REPROCESS_LOG_ID, APPLICATION_GROUP_ID, LIFE_CYCLE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_REPROCESS_LOG WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql : " + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
				int index = 1;
				ps.setString(index++,applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				ReprocessLogDataM reprocessLog = new ReprocessLogDataM();
					reprocessLog.setReprocessLogId(rs.getString("REPROCESS_LOG_ID"));
					reprocessLog.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					reprocessLog.setLifeCycle(rs.getInt("LIFE_CYCLE"));
					
					reprocessLog.setCreateBy(rs.getString("CREATE_BY"));
					reprocessLog.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					reprocessLog.setUpdateBy(rs.getString("UPDATE_BY"));
					reprocessLog.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				reprocessLogs.add(reprocessLog);
			}
			return reprocessLogs;
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
	public void saveUpdateOrigReprocessLog(ReprocessLogDataM reprocessLog)throws ApplicationException{
		int returnRows = 0;
		reprocessLog.setUpdateBy(userId);
		returnRows = updateTableORIG_REPROCESS_LOG(reprocessLog);
		if (returnRows == 0) {
			reprocessLog.setCreateBy(userId);
			createOrigReprocessLog(reprocessLog);
		}
	}
	private int updateTableORIG_REPROCESS_LOG(ReprocessLogDataM reprocessLog)throws ApplicationException{
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_NOTE_PAD_DATA ");
			sql.append(" SET UPDATE_DATE = ?, UPDATE_BY = ? ");			
			sql.append(" WHERE REPROCESS_LOG_ID = ? AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql : " + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, reprocessLog.getUpdateBy());
;
			// WHERE CLAUSE
			ps.setString(cnt++, reprocessLog.getReprocessLogId());		
			ps.setString(cnt++, reprocessLog.getApplicationGroupId());			
			
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
