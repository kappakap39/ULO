package com.eaf.orig.ulo.app.wmtask;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.exception.EngineException;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;

public class WMTaskDAOImpl extends OrigObjectDAO implements WMTaskDAO {
	
	private final String WM_TASK_RETRY_ADD_TIME = SystemConfig.getProperty("WM_TASK_RETRY_ADD_TIME");
	private final String WM_ACTION_INITIAL = SystemConstant.getConstant("WM_ACTION_INITIAL");
	private final String WM_ACTION_INITIAL_CJD_INCOME = SystemConstant.getConstant("WM_ACTION_INITIAL_CJD_INCOME");
	
	@Override
	public void setRetry(String taskId) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE WM_TASK ");
			sql.append(" SET STATUS = NULL, ");
			sql.append("     NEXT_RUN_TIME = ? + (1/24/60/60 * ?), ");
			sql.append("     ERROR_COUNT = 0 ");
			sql.append(" WHERE TASK_ID = ? ");
			logger.debug("SQL_setRetry() : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setTimestamp(1, ApplicationDate.getTimestamp());
			ps.setString(2, WM_TASK_RETRY_ADD_TIME);
			ps.setString(3, taskId);
			ps.executeUpdate();
		} catch(Exception e) {
			logger.fatal("ERROR ", e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR " + e.getMessage());
			}
		}
	}
	
	@Override
	public void setResubmit(String refCode) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM WM_TASK ");
			sql.append(" WHERE REF_CODE = ? AND ");
			sql.append("       TASK NOT IN (?,?) ");
			logger.debug("SQL_setResubmit_deleteTask() : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, refCode);
			ps.setString(2, WM_ACTION_INITIAL);
			ps.setString(3, WM_ACTION_INITIAL_CJD_INCOME);
			ps.executeUpdate();
		} catch(Exception e) {
			logger.fatal("ERROR ", e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR " + e.getMessage());
			}
		}
		
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE WM_TASK ");
			sql.append(" SET STATUS = NULL, ");
			sql.append("     NEXT_RUN_TIME = ? + (1/24/60/60 * ?), ");
			sql.append("     ERROR_COUNT = 0 ");
			sql.append(" WHERE REF_CODE = ? AND ");
			sql.append("       TASK IN (?, ?) ");
			logger.debug("SQL_setResubmit_updateTask() : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setTimestamp(1, ApplicationDate.getTimestamp());
			ps.setString(2, WM_TASK_RETRY_ADD_TIME);
			ps.setString(3, refCode);
			ps.setString(4, WM_ACTION_INITIAL);
			ps.setString(5, WM_ACTION_INITIAL_CJD_INCOME);
			ps.executeUpdate();
		} catch(Exception e) {
			logger.fatal("ERROR ", e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR " + e.getMessage());
			}
		}
	}
	
	@Override
	public String getMessage(String taskId) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String message = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT E.MSG AS MSG ");
			sql.append(" FROM (SELECT * FROM WM_TASK ORDER BY CREATE_TIME DESC) W ");
			sql.append(" JOIN WM_TASK_ERROR E ");
			sql.append(" ON W.TASK_ID = E.TASK_ID ");
			sql.append(" WHERE W.TASK_ID = ? AND ROWNUM = 1 ");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, taskId);
			logger.debug("TASK_ID = " + taskId);
			rs = ps.executeQuery();
			if(rs.next()) {
				message = clobStringConversion(rs.getClob("MSG"));
				logger.debug("errorMessage = " + message);
			}
		} catch(Exception e) {
			logger.fatal("ERROR ", e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR " + e.getMessage());
			}
		}
		return message;
	}
	
	@Override
	public List<HashMap<String, Object>> getTask(String refCode) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<HashMap<String, Object>> results = new ArrayList<>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT WM.TASK_ID, ");
			sql.append(" 		WM.TASK, ");
			sql.append(" 		WM.REF_ID, ");
			sql.append(" 		WM.REF_CODE, ");
			sql.append(" 		WM.STATUS, ");
			sql.append(" 		TO_CHAR(WM.LAST_RUN_TIME, 'dd/mm/yyyy | hh24:mi') AS LAST_RUN_TIME, ");
			sql.append(" 		WM.ERROR_COUNT, ");
			sql.append(" 		WM.ERROR_TOTAL, ");
			sql.append(" 		AG.APPLICATION_GROUP_ID, ");
			sql.append(" 		AG.APPLICATION_DATE, ");
			sql.append(" 		AG.APPLICATION_STATUS, ");
			sql.append(" 		AG.INSTANT_ID, ");
			sql.append(" 		AG.JOB_STATE, ");
			sql.append(" 		AG.UPDATE_DATE, ");
			sql.append(" 		AG.SOURCE, ");
			sql.append(" 		AG.SOURCE_ACTION, ");
			sql.append(" 		AG.LH_REF_ID, ");
			sql.append(" 		AG.SOURCE_USER_ID ");
			sql.append(" FROM ORIG_APP.WM_TASK WM ");
			sql.append(" LEFT JOIN ORIG_APP.ORIG_APPLICATION_GROUP AG ON WM.REF_CODE = AG.APPLICATION_GROUP_NO ");
			sql.append(" WHERE REF_CODE = ? ");
			sql.append(" ORDER BY CREATE_TIME ");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, refCode);
			logger.debug("REF_CODE = " + refCode);
			rs = ps.executeQuery();
			while(rs.next()) {
				HashMap<String, Object> result = new HashMap<>();
				result.put("TASK_ID", rs.getInt("TASK_ID"));
				result.put("TASK", rs.getString("TASK"));
				result.put("REF_ID", rs.getString("REF_ID"));
				result.put("REF_CODE", rs.getString("REF_CODE"));
				result.put("STATUS", rs.getString("STATUS"));
				result.put("LAST_RUN_TIME", rs.getString("LAST_RUN_TIME"));
				result.put("ERROR_COUNT", rs.getString("ERROR_COUNT"));
				result.put("ERROR_TOTAL", rs.getString("ERROR_TOTAL"));
				result.put("APPLICATION_GROUP_ID", rs.getString("APPLICATION_GROUP_ID"));
				result.put("APPLICATION_DATE", rs.getString("APPLICATION_DATE"));
				result.put("APPLICATION_STATUS", rs.getString("APPLICATION_STATUS"));
				result.put("INSTANT_ID", rs.getString("INSTANT_ID"));
				result.put("JOB_STATE", rs.getString("JOB_STATE"));
				result.put("UPDATE_DATE", rs.getString("UPDATE_DATE"));
				result.put("SOURCE", rs.getString("SOURCE"));
				result.put("SOURCE_ACTION", rs.getString("SOURCE_ACTION"));
				result.put("LH_REF_ID", rs.getString("LH_REF_ID"));
				result.put("SOURCE_USER_ID", rs.getString("SOURCE_USER_ID"));
				results.add(result);
			}
		} catch(Exception e) {
			logger.fatal("ERROR ", e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR " + e.getMessage());
			}
		}
		return results;
	}
	
	private String clobStringConversion(Clob clob) throws IOException, SQLException {
		if(clob == null) {
			return null;
		}
		StringBuffer stringBuffer = new StringBuffer();
		String string;
		BufferedReader bufferReader = new BufferedReader(clob.getCharacterStream());
		while((string = bufferReader.readLine()) != null) {
			stringBuffer.append(string);
		}
		return stringBuffer.toString();
    }
	
}
