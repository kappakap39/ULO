package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.HistoryDataM;

public class OrigHistoryDataDAOImpl extends OrigObjectDAO implements
		OrigHistoryDataDAO {
	public OrigHistoryDataDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigHistoryDataDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigHistoryDataM(HistoryDataM historyDataM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigHistoryDataM(historyDataM, conn);
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
	public void createOrigHistoryDataM(HistoryDataM historyDataM,Connection conn)
			throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("historyDataM.getHistoryDataId() :"+historyDataM.getHistoryDataId());
		    if(Util.empty(historyDataM.getHistoryDataId())){
				String histDataId =GenerateUnique.generate(OrigConstant.SeqNames.ORIG_HISTORY_DATA_PK );
				historyDataM.setHistoryDataId(histDataId);
				historyDataM.setCreateBy(userId);
		    }
		    historyDataM.setUpdateBy(userId);
			createTableORIG_HISTORY_DATA(historyDataM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_HISTORY_DATA(HistoryDataM historyDataM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_HISTORY_DATA ");
			sql.append("( HISTORY_DATA_ID, APPLICATION_GROUP_ID, ROLE, APP_DATA, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, historyDataM.getHistoryDataId());
			ps.setString(cnt++, historyDataM.getApplicationGroupId());
			ps.setString(cnt++, historyDataM.getRole());
			ps.setString(cnt++, historyDataM.getAppData());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, historyDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, historyDataM.getUpdateBy());
			
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
	public void saveUpdateOrigHistoryDataM(HistoryDataM historyDataM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigHistoryDataM(historyDataM, conn);
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
	public void saveUpdateOrigHistoryDataM(HistoryDataM historyDataM,
			Connection conn) throws ApplicationException {
		try {
			int returnRows = 0;
			historyDataM.setUpdateBy(userId);
			returnRows = updateTableORIG_HISTORY_DATA(historyDataM,conn);
			if (returnRows == 0) {
				historyDataM.setCreateBy(userId);
				createOrigHistoryDataM(historyDataM);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableORIG_HISTORY_DATA(HistoryDataM historyDataM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_HISTORY_DATA ");
			sql.append(" SET APP_DATA = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND ROLE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, historyDataM.getAppData());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, historyDataM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, historyDataM.getApplicationGroupId());
			ps.setString(cnt++, historyDataM.getRole());
			
			returnRows = ps.executeUpdate();
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
		return returnRows;
	}

	@Override
	public HistoryDataM loadOrigHistoryDataM(String appGroupId,
			String role) throws ApplicationException {
		try {
			HistoryDataM result = selectTableORIG_HISTORY_DATA(appGroupId, role);
			return result;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private HistoryDataM selectTableORIG_HISTORY_DATA(String appGroupId,
			String role) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT HISTORY_DATA_ID, APPLICATION_GROUP_ID, ROLE, APP_DATA, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_HISTORY_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND ROLE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appGroupId);
			ps.setString(2, role);

			rs = ps.executeQuery();

			if(rs.next()) {
				HistoryDataM histDataM = new HistoryDataM();
				histDataM.setHistoryDataId(rs.getString("HISTORY_DATA_ID"));
				histDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				histDataM.setRole(rs.getString("ROLE"));
				histDataM.setAppData(rs.getString("APP_DATA"));
				
				histDataM.setCreateBy(rs.getString("CREATE_BY"));
				histDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				histDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				histDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				return histDataM;
			}

			return null;
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
