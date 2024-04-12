package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.ApplicationLogDataM;

public class OrigApplicationLogDAOImpl extends OrigObjectDAO implements
		OrigApplicationLogDAO {
	public OrigApplicationLogDAOImpl(){
		
	}
	public OrigApplicationLogDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigApplicationLogM(
			ApplicationLogDataM applicationLogDataM) throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("applicationLogDataM.getAppLogId() :"+applicationLogDataM.getAppLogId());
		    if(Util.empty(applicationLogDataM.getAppLogId())){
				String appLogId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_LOG_PK);
				applicationLogDataM.setAppLogId(appLogId);
				applicationLogDataM.setCreateBy(userId);
		    }
		    applicationLogDataM.setUpdateBy(userId);
			createTableORIG_APPLICATION_LOG(applicationLogDataM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_APPLICATION_LOG(
			ApplicationLogDataM applicationLogDataM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_APPLICATION_LOG ");
			sql.append("( APP_LOG_ID, ACTION, JOB_STATE, APPLICATION_STATUS, ");
			sql.append(" CLAIM_DATE, LIFE_CYCLE, APPLICATION_GROUP_ID, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,ROLE_NAME ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,  ?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, applicationLogDataM.getAppLogId());
			ps.setString(cnt++, applicationLogDataM.getAction());
			ps.setString(cnt++, applicationLogDataM.getJobState());
			ps.setString(cnt++, applicationLogDataM.getApplicationStatus());
			
			ps.setDate(cnt++, applicationLogDataM.getClaimDate());			
			ps.setBigDecimal(cnt++, applicationLogDataM.getLifeCycle());
			ps.setString(cnt++, applicationLogDataM.getApplicationGroupId());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationLogDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationLogDataM.getUpdateBy());
			
			ps.setString(cnt++, applicationLogDataM.getRoleName());
			
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
	public void deleteOrigApplicationLogM(String applicationGroupId, String appLogId) 
			throws ApplicationException {
		deleteTableORIG_APPLICATION_LOG(applicationGroupId, appLogId);
	}

	private void deleteTableORIG_APPLICATION_LOG(String applicationGroupId, String appLogId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_APPLICATION_LOG ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(appLogId != null && !appLogId.isEmpty()) {
				sql.append(" AND APP_LOG_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(appLogId != null && !appLogId.isEmpty()) {
				ps.setString(2, appLogId);
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
	public ArrayList<ApplicationLogDataM> loadOrigApplicationLogM(
			String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_APPLICATION_LOG(applicationGroupId,conn);
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
	public ArrayList<ApplicationLogDataM> loadOrigApplicationLogM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		return selectTableORIG_APPLICATION_LOG(applicationGroupId, conn);
	}
	private ArrayList<ApplicationLogDataM> selectTableORIG_APPLICATION_LOG(
			String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationLogDataM> logList = new ArrayList<ApplicationLogDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APP_LOG_ID, ACTION, JOB_STATE, APPLICATION_STATUS, ");
			sql.append(" CLAIM_DATE, LIFE_CYCLE, APPLICATION_GROUP_ID, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY, ");
			sql.append(" ACTION_DESC,APPLICATION_RECORD_ID  ,ROLE_NAME,REPROCESS_VERSION ");
			sql.append(" FROM ORIG_APPLICATION_LOG  WHERE APPLICATION_GROUP_ID = ? ");
			sql.append(" ORDER BY APP_LOG_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ApplicationLogDataM applicationLogDataM = new ApplicationLogDataM();
				applicationLogDataM.setAppLogId(rs.getString("APP_LOG_ID"));
				applicationLogDataM.setAction(rs.getString("ACTION"));
				applicationLogDataM.setJobState(rs.getString("JOB_STATE"));
				applicationLogDataM.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
				applicationLogDataM.setClaimDate(rs.getDate("CLAIM_DATE"));
				applicationLogDataM.setLifeCycle(rs.getBigDecimal("LIFE_CYCLE"));
				applicationLogDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				applicationLogDataM.setCreateBy(rs.getString("CREATE_BY"));
				applicationLogDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				applicationLogDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				applicationLogDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				applicationLogDataM.setActionDesc(rs.getString("ACTION_DESC"));
				applicationLogDataM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				applicationLogDataM.setRoleName(rs.getString("ROLE_NAME"));
				applicationLogDataM.setReprocessVersion(rs.getBigDecimal("REPROCESS_VERSION"));
				logList.add(applicationLogDataM);
			}

			return logList;
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
	public void saveUpdateOrigApplicationLogM(
			ApplicationLogDataM applicationLogDataM) throws ApplicationException {
		int returnRows = 0;
		applicationLogDataM.setUpdateBy(userId);
		returnRows = updateTableORIG_APPLICATION_LOG(applicationLogDataM);
		if (returnRows == 0) {
			applicationLogDataM.setCreateBy(userId);
			createOrigApplicationLogM(applicationLogDataM);
		}
	}

	private int updateTableORIG_APPLICATION_LOG(
			ApplicationLogDataM applicationLogDataM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION_LOG ");
			sql.append(" SET ACTION = ?, JOB_STATE = ?, APPLICATION_STATUS = ?, ");
			sql.append(" CLAIM_DATE = ?, LIFE_CYCLE = ?, APPLICATION_GROUP_ID = ? ");
			sql.append(" ,UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE APP_LOG_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, applicationLogDataM.getAction());
			ps.setString(cnt++, applicationLogDataM.getJobState());
			ps.setString(cnt++, applicationLogDataM.getApplicationStatus());			
			
			ps.setDate(cnt++, applicationLogDataM.getClaimDate());			
			ps.setBigDecimal(cnt++, applicationLogDataM.getLifeCycle());			
			ps.setString(cnt++, applicationLogDataM.getApplicationGroupId());			
			
			ps.setString(cnt++, applicationLogDataM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, applicationLogDataM.getAppLogId());
			
			
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
	public Date getDE2SubmitDate(String applicationGroupId, int lifeCycle) throws ApplicationException 
	{
		String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
		String DE2SUBMIT_ACTION = SystemConstant.getConstant("DE2SUBMIT_ACTION");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Date de2SubmitDate = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CREATE_DATE FROM ORIG_APPLICATION_LOG WHERE APPLICATION_GROUP_ID = ? ");
			sql.append(" AND LIFE_CYCLE = ? AND ACTION_DESC = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
				ps.setString(1,applicationGroupId);
				ps.setInt(2,lifeCycle);
				ps.setString(3,DE2SUBMIT_ACTION);
			rs = ps.executeQuery();
			if(rs.next()){
				de2SubmitDate = rs.getDate("CREATE_DATE");
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
		return de2SubmitDate;
	}
	public Date getDateOfSearch(String applicationGroupId, int lifeCycle) throws ApplicationException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Date dateOfSearch = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DATE_OF_SEARCH ");
			sql.append("FROM NCB_INFO ");
			sql.append("JOIN ORIG_PERSONAL_INFO  ON ORIG_PERSONAL_INFO.PERSONAL_ID = NCB_INFO.PERSONAL_ID ");
			sql.append("JOIN ORIG_APPLICATION_GROUP ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_PERSONAL_INFO.APPLICATION_GROUP_ID ");
			sql.append("WHERE ORIG_PERSONAL_INFO.APPLICATION_GROUP_ID = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
				ps.setString(1,applicationGroupId);
			rs = ps.executeQuery();
			if(rs.next()){
				dateOfSearch = rs.getDate("DATE_OF_SEARCH");
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
		return dateOfSearch;
	}
}
