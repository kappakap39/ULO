package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.FollowDocHistoryDataM;

public class OrigFollowDocHistoryDAOImpl  extends OrigObjectDAO implements OrigFollowDocHistoryDAO{
	private static transient Logger logger = Logger.getLogger(OrigFollowDocHistoryDAOImpl.class);
	public OrigFollowDocHistoryDAOImpl(){
		
	}
	@Override
	public void createOrigFollowDocHistoryM(FollowDocHistoryDataM followDocHistoryDataM)throws ApplicationException {
		try {
			  logger.debug("followDocHistoryDataM.getApplicationGroupId() :"+followDocHistoryDataM.getApplicationGroupId());
			  createTableFOLLOW_DOC_HISTORY(followDocHistoryDataM);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	@Override
	public void createOrigFollowDocHistoryM(ArrayList<FollowDocHistoryDataM> followDocHistoryList)throws ApplicationException {
		try {
			if(followDocHistoryList != null && !followDocHistoryList.isEmpty()) {
				for(FollowDocHistoryDataM followDocHistoryDataM : followDocHistoryList){
					 createTableFOLLOW_DOC_HISTORY(followDocHistoryDataM);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	private void createTableFOLLOW_DOC_HISTORY(FollowDocHistoryDataM followDocHistoryDataM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_FOLLOW_DOC_HISTORY ");
			sql.append("( APPLICATION_GROUP_ID, DOC_REASON, DOCUMENT_CODE, ");
			sql.append(" FOLLOWUP_SEQ, CREATE_DATE, CREATE_BY,");
			sql.append(" UPDATE_DATE, UPDATE_BY )");

			sql.append(" VALUES(?,?,?,  ?,?,?,  ?,?)");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, followDocHistoryDataM.getApplicationGroupId());
			ps.setString(cnt++, followDocHistoryDataM.getDocReason());
			ps.setString(cnt++, followDocHistoryDataM.getDocumentCode());
			
			ps.setInt(cnt++, followDocHistoryDataM.getFollowupSeq());
			ps.setTimestamp(cnt++,ApplicationDate.getTimestamp());
			ps.setString(cnt++, followDocHistoryDataM.getCreateBy());
			
			ps.setTimestamp(cnt++,ApplicationDate.getTimestamp());
			ps.setString(cnt++, followDocHistoryDataM.getUpdateBy());
			
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
	public void deleteOrigFollowDocHistoryM(FollowDocHistoryDataM FollowDocHistoryDataM)throws ApplicationException {
		try {
			deleteTableFOLLOW_DOC_HISTORY(FollowDocHistoryDataM);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	private void deleteTableFOLLOW_DOC_HISTORY(FollowDocHistoryDataM followDocHistoryDataM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_FOLLOW_DOC_HISTORY ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, followDocHistoryDataM.getApplicationGroupId());
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
	public ArrayList<FollowDocHistoryDataM> loadOrigFollowDocHistoryM(String applicationGroupId) throws ApplicationException {
		try {
			if(null!=applicationGroupId && !"".equals(applicationGroupId)){
				return selectTableFOLLOW_DOC_HISTORY(applicationGroupId);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
	private ArrayList<FollowDocHistoryDataM> selectTableFOLLOW_DOC_HISTORY(String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<FollowDocHistoryDataM> followDocHistoryList = new ArrayList<FollowDocHistoryDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_GROUP_ID, DOC_REASON, DOCUMENT_CODE,");
			sql.append(" FOLLOWUP_SEQ, CREATE_DATE, CREATE_BY,");
			sql.append(" UPDATE_DATE, UPDATE_BY");
			sql.append(" FROM ORIG_FOLLOW_DOC_HISTORY  WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				FollowDocHistoryDataM followDocHistoryDataM  = new FollowDocHistoryDataM();
				followDocHistoryDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				followDocHistoryDataM.setDocReason(rs.getString("DOC_REASON"));
				followDocHistoryDataM.setDocumentCode(rs.getString("DOCUMENT_CODE"));
				
				followDocHistoryDataM.setFollowupSeq(rs.getInt("FOLLOWUP_SEQ"));
				followDocHistoryDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				followDocHistoryDataM.setCreateBy(rs.getString("CREATE_BY"));
				
				followDocHistoryDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				followDocHistoryDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				followDocHistoryList.add(followDocHistoryDataM);
			}
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
		return followDocHistoryList;
	}
	@Override
	public void saveUpdateOrigFollowDocHistoryM(FollowDocHistoryDataM followDocHistoryDataM)throws ApplicationException {
		try {
			int returnRows = 0;
			returnRows = updateTableFOLLOW_DOC_HISTORY(followDocHistoryDataM);
			if (returnRows == 0) {
				logger.debug("New record then can't update record in table FOLLOW_DOC_HISTORY then call Insert method");
				createOrigFollowDocHistoryM(followDocHistoryDataM);
			} 
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	private int updateTableFOLLOW_DOC_HISTORY(FollowDocHistoryDataM followDocHistoryDataM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_FOLLOW_DOC_HISTORY ");
			sql.append(" SET APPLICATION_GROUP_ID = ?, DOC_REASON = ?, DOCUMENT_CODE = ?, ");
			sql.append(" FOLLOWUP_SEQ = ?, CREATE_DATE = ?, CREATE_BY = ?,");
			sql.append(" UPDATE_DATE = ?, UPDATE_BY = ?");
			
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND DOCUMENT_CODE=?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, followDocHistoryDataM.getApplicationGroupId());
			ps.setString(cnt++,followDocHistoryDataM.getDocReason());
			ps.setString(cnt++, followDocHistoryDataM.getDocumentCode());
			
			ps.setInt(cnt++,followDocHistoryDataM.getFollowupSeq());
			ps.setTimestamp(cnt++, followDocHistoryDataM.getCreateDate());
			ps.setString(cnt++, followDocHistoryDataM.getCreateBy());			

			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, followDocHistoryDataM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, followDocHistoryDataM.getApplicationGroupId());
			ps.setString(cnt++, followDocHistoryDataM.getDocumentCode());
			
			
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
	public int selectMaxFollowupSeq(String applicationGroupId)throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int maxSeq=0;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT MAX(FOLLOWUP_SEQ) AS MAX_SEQ");
			sql.append(" FROM ORIG_FOLLOW_DOC_HISTORY  WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			if(rs.next()) {
				maxSeq= rs.getInt("MAX_SEQ");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return maxSeq;
	}

}
