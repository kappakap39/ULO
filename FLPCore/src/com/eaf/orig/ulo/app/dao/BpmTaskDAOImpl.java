package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;

public class BpmTaskDAOImpl extends OrigObjectDAO implements BpmTaskDAO {

	private static transient Logger logger = Logger.getLogger(BpmTaskDAOImpl.class);
	
	@Override
	public String foundEappTaskInstance(List<String> roles, String prefixInstanceName)
			throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String result = "N";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT 1");
			sql.append(" FROM ( SELECT T.BPD_INSTANCE_ID, LBI.INSTANCE_NAME, U.USER_ID ");
			sql.append("  		FROM LSW_TASK T ");
			sql.append("  		JOIN LSW_BPD_INSTANCE LBI ON T.BPD_INSTANCE_ID = LBI.BPD_INSTANCE_ID ");
			sql.append("		LEFT JOIN LSW_USR_XREF U ON U.USER_ID = T.USER_ID ");
			sql.append("  		WHERE ( 1 = 1 ) ");
			sql.append("  		AND T.STATUS = 12 ");
			sql.append("  		AND EXISTS ");
			sql.append("  			(SELECT * ");
			sql.append("  			 FROM LSW_TASK T ");
			sql.append("  			 JOIN LSW_BPD_INSTANCE I      ON T.BPD_INSTANCE_ID = I.BPD_INSTANCE_ID AND T.STATUS = 12 ");
			sql.append("  			 JOIN LSW_PARTICIPANT_GROUP G ON T.PARTICIPANT_ID = G.PARTICIPANT_ID AND I.SNAPSHOT_ID = G.SNAPSHOT_ID ");
			sql.append("  			 JOIN LSW_PARTICIPANT P       ON G.PARTICIPANT_ID = P.PARTICIPANT_ID AND G.CACHED_PART_VERSION_ID = P.VERSION_ID ");
			sql.append("  			 WHERE LBI.BPD_INSTANCE_ID = I.BPD_INSTANCE_ID ");
			sql.append("  			 AND P.NAME IN ( SELECT TRIM(REGEXP_SUBSTR(?, '[^,]+', 1, COLUMN_VALUE)) BPM_GROUP ");
			sql.append("  			 				 FROM TABLE(CAST(MULTISET( SELECT LEVEL FROM DUAL CONNECT BY LEVEL <= regexp_count (?,',') + 1) AS SYS.ODCINUMBERLIST )) LEVELS ) ) ");
			sql.append(" GROUP BY T.BPD_INSTANCE_ID, LBI.INSTANCE_NAME, U.USER_ID ) X ");
			sql.append(" WHERE INSTR(X.INSTANCE_NAME,?) = 1 AND X.USER_ID IS NULL ");
			
			logger.debug("foundEappTaskInstance [sql] : " + sql.toString());
			StringBuilder roleDV = new StringBuilder();	
			
			for (String role : roles) {
				if (roleDV.length() == 0) {
					roleDV.append(role);
				} else {
					roleDV.append("," + role);
				}
			}
			logger.debug(" roles DV : " + roleDV);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,roleDV.toString());
			ps.setString(2,roleDV.toString());
			ps.setString(3,prefixInstanceName);
			rs = ps.executeQuery();
			if(rs.next()){
				result = "Y";
			}
		}catch(SQLException e){
			logger.fatal(e.getLocalizedMessage());
			throw e;
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return result;
	}

	@Override
	public void saveUpdateCacheParameter(String cacheName, String cacheValue) throws SQLException {
		int returnRows = 0;
		returnRows = updateCacheParameter(cacheName, cacheValue);
		if (returnRows == 0) {
			logger.debug(" update = 0 then create cache parameter.");
			createCacheParameter(cacheName, cacheValue);
		}
		
	}
	
	private int updateCacheParameter(String cacheName, String cacheValue) throws SQLException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			ps = conn.prepareStatement("UPDATE CACHE_PARAMETER SET CACHE_VALUE = ? WHERE CACHE_NAME = ?");
			int cnt = 1;
			ps.setString(cnt++, cacheValue);
			ps.setString(cnt++, cacheName);
			
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	private void createCacheParameter(String cacheName, String cacheValue) throws SQLException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			String dSql = " INSERT INTO CACHE_PARAMETER ( CACHE_NAME, CACHE_VALUE ) VALUES (?, ?) ";
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cacheName);
			ps.setString(cnt++, cacheValue);
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

}
