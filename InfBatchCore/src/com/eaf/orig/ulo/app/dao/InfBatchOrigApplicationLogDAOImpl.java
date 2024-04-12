package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.app.model.ApplicationLogDataM;

public class InfBatchOrigApplicationLogDAOImpl extends InfBatchObjectDAO implements InfBatchOrigApplicationLogDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchOrigApplicationLogDAOImpl.class);
	@Override
	public ArrayList<ApplicationLogDataM> loadOrigApplicationLogM(String applicationGroupId, Connection conn)throws InfBatchException{
		return selectTableORIG_APPLICATION_LOG(applicationGroupId, conn);
	}
	public ArrayList<ApplicationLogDataM> loadOrigApplicationLogM(String applicationGroupId)throws InfBatchException{
		ArrayList<ApplicationLogDataM> applicationLogList = new ArrayList<ApplicationLogDataM>();
		Connection conn = null;
		try {
			conn = getConnection();
			applicationLogList = loadOrigApplicationLogM(applicationGroupId, conn);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try {
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
		}
		return applicationLogList;
	}
	private ArrayList<ApplicationLogDataM> selectTableORIG_APPLICATION_LOG(String applicationGroupId,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationLogDataM> logList = new ArrayList<ApplicationLogDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
				sql.append("SELECT APP_LOG_ID, ACTION, JOB_STATE, APPLICATION_STATUS, ");
				sql.append(" CLAIM_DATE, LIFE_CYCLE, APPLICATION_GROUP_ID, ");
				sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY, ");
				sql.append(" ACTION_DESC,APPLICATION_RECORD_ID  ,ROLE_NAME ");
				sql.append(" FROM ORIG_APPLICATION_LOG  WHERE APPLICATION_GROUP_ID = ? ");
				sql.append(" ORDER BY APP_LOG_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
				ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
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
				logList.add(applicationLogDataM);
			}
			return logList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e);
		} finally {
			try {
				closeConnection(ps,rs);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
}
