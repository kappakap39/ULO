package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.SearchDAOImpl;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.exception.EngineException;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.ulo.cache.store.MethodCacheManager;
import com.orig.bpm.workflow.model.BPMInboxInstance;

public class InboxDAOImpl extends SearchDAOImpl  implements InboxDAO {
	private static transient Logger logger = Logger.getLogger(InboxDAOImpl.class);
	@Override
	public ArrayList<HashMap<String,Object>> search(List<BPMInboxInstance> instants) throws Exception {
		ArrayList<HashMap<String,Object>> List = new ArrayList<HashMap<String,Object>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		if(Util.empty(instants)){
			return null;
		}		
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();	
			SQL.append("SELECT RESULT.*, ");
            SQL.append(" (PKA_LEG_CAL.F_CAL_LEG1(RESULT.APPLICATION_GROUP_ID) + PKA_LEG_CAL.F_CAL_LEG2(RESULT.APPLICATION_GROUP_ID)) AS SLA_USED_DAY, ");
            SQL.append(" PKA_SEARCH_INFO.GET_INBOX_PRODUCT(RESULT.APPLICATION_GROUP_ID) AS PROCUCT_NAME ");
            SQL.append(" FROM (");
				SQL.append(" SELECT DISTINCT ");
		        SQL.append(" AG.APPLICATION_GROUP_ID, ");
	            SQL.append(" AG.INSTANT_ID, ");
	            SQL.append(" AG.APPLICATION_GROUP_NO, ");
	            SQL.append(" AG.JOB_STATE, ");
	            SQL.append(" AG.APPLICATION_TEMPLATE, ");
	            SQL.append(" AG.APPLICATION_DATE, ");
	            SQL.append(" NVL(AG.SLA_DAY,0) AS SLA_DAY, ");
	            SQL.append(" AG.JOB_TYPE, ");
	            SQL.append(" AG.PRIORITY, ");
	            SQL.append(" AG.APPLICATION_TYPE, ");
	            SQL.append(" AG.LIFE_CYCLE MAX_LIFE_CYCLE, ");
	            SQL.append(" AG.REPROCESS_FLAG, ");
	            SQL.append(" AG.SOURCE ");
	            SQL.append(" FROM  ORIG_APPLICATION_GROUP AG ");
	        	SQL.append(" WHERE  INSTANT_ID IN ( ");
	        		String COMMA = "";
					for (int i = 0, count = instants.size() ; i < count ; i++) {
						SQL.append(COMMA+"?");
						COMMA = ",";
					}
				SQL.append("   ) ");
				SQL.append(" ORDER BY AG.PRIORITY ASC ");
			SQL.append(")RESULT ");
			logger.debug("SQL=" + SQL);
			ps = conn.prepareStatement(SQL.toString());				
			int index = 1;					 
			for(BPMInboxInstance instant : instants){
				logger.debug("InstanceId >> "+instant.getInstanceId());
				ps.setInt(index++,instant.getInstanceId()); 							 
			}
			rs = ps.executeQuery();			
			ResultSetMetaData rsmd = rs.getMetaData();			
			int colCnt = rsmd.getColumnCount();			
			while(rs.next()){
				HashMap<String, Object> Row = new HashMap<String, Object>();
				for(int i=1;i<=colCnt;i++){
					set(Row, i, rsmd, rs);
				}
				List.add(Row);
			}			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		return List;
	}
	
	@Override
	public HashMap<String, String> summaryInbox(String paramCode,String userName,String roleId) throws Exception {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection conn = null;
		HashMap<String, String>  summaries = new HashMap<String, String>();
//		try{						
//			conn = getConnection();
//			StringBuilder SQL = new StringBuilder();
//			logger.debug("ROLE_ID : "+roleId);
//			if(SystemConstant.getConstant("ROLE_VT").equals(roleId)){
//				SQL.append(" SELECT PKA_INBOX.F_CNT_VT_JOB_ALL_ROLE(?) AS ALL_PROCESS,");
//				SQL.append(" PKA_INBOX.F_CNT_VT_JOB_PRIORITY(?) AS PRIORITY_PROCESS,");
//			}else{
//				SQL.append(" SELECT PKA_INBOX.F_CNT_JOB_ALL_ROLE(?)+PKA_INBOX.F_CNT_FU_JOB_ALL_ROLE(?) AS ALL_PROCESS,");
//				SQL.append(" PKA_INBOX.F_CNT_JOB_PRIORITY(?) AS PRIORITY_PROCESS,");
//			}
//			SQL.append(" PKA_INBOX.F_CNT_JOB_ROLE(?) AS ROLE_PROCESS,");
//			SQL.append(" PKA_INBOX.F_CNT_JOB_PRIORITY_ROLE(?) AS ROLE_PRIORITY_PROCESS,");
//			SQL.append(" PKA_INBOX.F_CNT_ACTUAL_POINT(?) AS ACTUAL_POINT, ");
//			SQL.append(" PKA_INBOX.F_CNT_TARGET_POINT(?) AS TARGET_POINT, ");
//			SQL.append(" PKA_INBOX.F_INBOX_FLAG(?) AS INBOX_FLAG ");
//			SQL.append(" FROM DUAL");
//			logger.debug("SQL >> "+SQL);
//			int index=1;
//			ps = conn.prepareStatement(SQL.toString());	
//			if(SystemConstant.getConstant("ROLE_VT").equals(roleId)){
//				ps.setString(index++, roleId);
//			}else{
//				ps.setString(index++, roleId);
//				logger.debug("F_CNT_FU_JOB_ALL_ROLE >> COUNTING_JOB_FU_"+roleId);
//				ps.setString(index++, SystemConstant.getConstant("COUNTING_JOB_FU_"+roleId));
//			}
//			ps.setString(index++, roleId);
//			ps.setString(index++, paramCode);
//			ps.setString(index++, paramCode);
//			ps.setString(index++, userName);
//			ps.setString(index++, userName);
//			ps.setString(index++, userName);
//			rs = ps.executeQuery();				
//			if(rs.next()){			
//				summaries.put("ALL_PROCESS",String.valueOf(rs.getInt("ALL_PROCESS")));				 
//				summaries.put("PRIORITY_PROCESS",String.valueOf(rs.getInt("PRIORITY_PROCESS")));				 
//				summaries.put("ROLE_PROCESS",String.valueOf(rs.getInt("ROLE_PROCESS")));				 
//				summaries.put("ROLE_PRIORITY_PROCESS",String.valueOf(rs.getInt("ROLE_PRIORITY_PROCESS")));
//				String ACTUAL_POINT = String.valueOf(rs.getFloat("ACTUAL_POINT"));
//				if(ACTUAL_POINT.matches("(.*).0(.*)")){
//					ACTUAL_POINT = ACTUAL_POINT.split("\\.")[0];
//				}
//				summaries.put("ACTUAL_POINT", ACTUAL_POINT);				 
//				summaries.put("TARGET_POINT",String.valueOf(rs.getInt("TARGET_POINT")));				 
//				summaries.put("INBOX_FLAG",String.valueOf(rs.getString("INBOX_FLAG")));				 
//			}						
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//			throw new EngineException(e.getLocalizedMessage());
//		}finally{
//			try{
//				closeConnection(conn, rs, ps);
//			}catch(Exception e){
//				logger.fatal("ERROR",e);
//				throw new EngineException(e.getLocalizedMessage());
//			}
//		}
		if(SystemConstant.getConstant("ROLE_VT").equals(roleId)){
			summaries.put("ALL_PROCESS",String.valueOf(countVTAppAllProcesses(roleId)));
			summaries.put("PRIORITY_PROCESS",String.valueOf(countVTPriorityProcesses(roleId)));
		}else{
			summaries.put("ALL_PROCESS",String.valueOf(countNonVTAppAllProcesses(roleId)));
			summaries.put("PRIORITY_PROCESS",String.valueOf(countNonVTPriorityProcesses(roleId)));
		}
		int[] appProcess = countAppByRole(paramCode);
		summaries.put("ROLE_PROCESS",String.valueOf(appProcess[0]));
		summaries.put("ROLE_PRIORITY_PROCESS",String.valueOf(appProcess[1]));
		summaries.put("ACTUAL_POINT", FormatUtil.display(countActualPointByUsername(userName),"####.#"));
		summaries.put("TARGET_POINT",String.valueOf(countTargetPointByUsername(userName)));				 
		summaries.put("INBOX_FLAG",String.valueOf(getInboxFlagByUsername(userName)));		
		
		return summaries;
	}
	@Override
	public String getInboxFlag(String userName)throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String inboxFlag = "N";
		try{
			conn = getConnection();
			String sql = "SELECT INBOX_FLAG FROM USER_INBOX_INFO WHERE UPPER(USER_NAME) = UPPER(?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();				
			if(rs.next()){		 
				inboxFlag = rs.getString("INBOX_FLAG");				 
			}
		}catch(Exception e){
			logger.error(e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		return inboxFlag;
	}
	@Override
	public void updateTableUserInboxInfo(String userName,String inboxFlag) throws Exception {
		try {
			int returnRows = this.updateInboxFlag(userName,inboxFlag);
			if(returnRows == 0){
				this.insertTableUserInboxInfo(userName,inboxFlag,GenerateUnique.generate(OrigConstant.SeqNames.USER_INBOX_INFO_PK));
			}		
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new Exception(e.getLocalizedMessage());
		}
	}
	
	private void insertTableUserInboxInfo(String userInbox,String inboxFlag,String inboxId) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO USER_INBOX_INFO ");
			sql.append("(");
			sql.append(" USER_NAME, INBOX_ID, INBOX_FLAG, CREATE_BY,");
			sql.append(" UPDATE_BY, CREATE_DATE, UPDATE_DATE ");
			sql.append(")");
			sql.append(" VALUES (?,?,?,?,  ?,SYSDATE,SYSDATE) ");
  
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, userInbox);
			ps.setString(index++,inboxId);
			ps.setString(index++,inboxFlag);
			ps.setString(index++,userInbox);
			ps.setString(index++,userInbox);	
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new Exception(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
	
	}

	
	public int updateInboxFlag(String userName, String inboxFlag)throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		int returnRows = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("UPDATE  USER_INBOX_INFO ");
			sql.append("SET INBOX_FLAG=?,UPDATE_BY=?,UPDATE_DATE= ? ");
			sql.append(" WHERE UPPER(USER_NAME)=UPPER(?) "); 
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,inboxFlag);
			ps.setString(index++,userName);
			ps.setTimestamp(index++, ApplicationDate.getTimestamp());
			//where
			ps.setString(index++,userName);
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		return returnRows;			
	}
	
	public int countNonVTAppAllProcesses(String roleId)throws Exception {
		//Verify cache if valid
		String cacheName = "CACHE_INBOX_NON_VETO_APP_ALL_PROCESS";
		if(MethodCacheManager.isValidCache(cacheName, roleId)){
			logger.debug("## Inbox cache CACHE_INBOX_NON_VETO_APP_ALL_PROCESS hit!!!!");
			return MethodCacheManager.getCacheData(cacheName, int.class, roleId);
		}
		logger.debug("## Inbox cache CACHE_INBOX_NON_VETO_APP_ALL_PROCESS either expired or not hit");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("SELECT PKA_INBOX.F_CNT_JOB_ALL_ROLE (?)+ PKA_INBOX.F_CNT_FU_JOB_ALL_ROLE (?) AS ALL_PROCESS FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,roleId);
			ps.setString(index++,SystemConstant.getConstant("COUNTING_JOB_FU_"+roleId));
			
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		
		//Update cache data
		MethodCacheManager.updateCache(cacheName, result, roleId);
		return result;			
	}
	
	public int countVTAppAllProcesses(String roleId)throws Exception {
		//Verify cache if valid
		String cacheName = "CACHE_INBOX_VETO_APP_ALL_PROCESS";
		if(MethodCacheManager.isValidCache(cacheName, roleId)){
			logger.debug("## Inbox cache CACHE_INBOX_VETO_APP_ALL_PROCESS hit!!!!");
			return MethodCacheManager.getCacheData(cacheName, int.class, roleId);
		}
		logger.debug("## Inbox cache CACHE_INBOX_VETO_APP_ALL_PROCESS either expired or not hit");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("SELECT PKA_INBOX.F_CNT_VT_JOB_ALL_ROLE(?) AS ALL_PROCESS FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,roleId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		
		//Update cache data
		MethodCacheManager.updateCache(cacheName, result, roleId);
		return result;			
	}
	
	public int countNonVTPriorityProcesses(String roleId)throws Exception {
		//Verify cache if valid
		String cacheName = "CACHE_INBOX_NON_VETO_PRIORITY_PROCESS";
		if(MethodCacheManager.isValidCache(cacheName, roleId)){
			logger.debug("## Inbox cache CACHE_INBOX_NON_VETO_PRIORITY_PROCESS hit!!!!");
			return MethodCacheManager.getCacheData(cacheName, int.class, roleId);
		}
		logger.debug("## Inbox cache CACHE_INBOX_NON_VETO_PRIORITY_PROCESS either expired or not hit");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("SELECT PKA_INBOX.F_CNT_JOB_PRIORITY(?) AS PRIORITY_PROCESS FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,roleId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		
		
		//Update cache data
		MethodCacheManager.updateCache(cacheName, result, roleId);
		return result;			
	}
	
	public int countVTPriorityProcesses(String roleId)throws Exception {
		//Verify cache if valid
		String cacheName = "CACHE_INBOX_VETO_PRIORITY_PROCESS";
		if(MethodCacheManager.isValidCache(cacheName, roleId)){
			return MethodCacheManager.getCacheData(cacheName, int.class, roleId);
		}		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("SELECT PKA_INBOX.F_CNT_VT_JOB_PRIORITY(?) AS PRIORITY_PROCESS FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,roleId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		
		//Update cache data
		MethodCacheManager.updateCache(cacheName, result, roleId);
		return result;			
	}
	
	public int[] countAppByRole(String params)throws Exception {
		//Verify cache if valid
		String cacheName = "CACHE_INBOX_APPLICATION_BY_ROLE";
		if(MethodCacheManager.isValidCache(cacheName, params)){
			logger.debug("## Inbox cache CACHE_INBOX_APPLICATION_BY_ROLE hit!!!!");
			return MethodCacheManager.getCacheData(cacheName, int[].class, params);
		}
		logger.debug("## Inbox cache CACHE_INBOX_APPLICATION_BY_ROLE either expired or not hit");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int[] result = {0,0};
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("SELECT PKA_INBOX.F_CNT_JOB_ROLE(?) AS ROLE_PROCESS, PKA_INBOX.F_CNT_JOB_PRIORITY_ROLE(?) AS ROLE_PRIORITY_PROCESS FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,params);
			ps.setString(index++,params);
			
			rs = ps.executeQuery();
			if(rs.next()){
				result[0] = rs.getInt(1);
				result[1] = rs.getInt(2);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		
		//Update cache data
		MethodCacheManager.updateCache(cacheName, result, params);
		return result;			
	}
	
	public BigDecimal countActualPointByUsername(String username)throws Exception {
		//Verify cache if valid
		String cacheName = "CACHE_INBOX_ACTUAL_POINT_BY_USER_NAME";
		if(MethodCacheManager.isValidCache(cacheName, username)){
			logger.debug("## Inbox cache CACHE_INBOX_ACTUAL_POINT_BY_USER_NAME hit!!!!");
			return MethodCacheManager.getCacheData(cacheName, BigDecimal.class, username);
		}
		logger.debug("## Inbox cache CACHE_INBOX_ACTUAL_POINT_BY_USER_NAME either expired or not hit");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal result = new BigDecimal(0);
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("SELECT PKA_INBOX.F_CNT_ACTUAL_POINT(?) AS ACTUAL_POINT FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,username);
			
			rs = ps.executeQuery();
			if(rs.next()){
				result = BigDecimal.valueOf(rs.getFloat(1));
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		
		//Update cache data
		MethodCacheManager.updateCache(cacheName, result, username);
		return result;			
	}
	
	public int countTargetPointByUsername(String username)throws Exception {
		//Verify cache if valid
		String cacheName = "CACHE_INBOX_TARGET_POINT_BY_USER_NAME";
		if(MethodCacheManager.isValidCache(cacheName, username)){
			logger.debug("## Inbox cache CACHE_INBOX_TARGET_POINT_BY_USER_NAME hit!!!!");
			return MethodCacheManager.getCacheData(cacheName, int.class, username);
		}
		logger.debug("## Inbox cache CACHE_INBOX_TARGET_POINT_BY_USER_NAME either expired or not hit");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("SELECT PKA_INBOX.F_CNT_TARGET_POINT(?) AS TARGET_POINT FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,username);
			
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		
		//Update cache data
		MethodCacheManager.updateCache(cacheName, result, username);
		return result;			
	}
	
	public String getInboxFlagByUsername(String username)throws Exception {
		//Verify cache if valid
		String cacheName = "CACHE_INBOX_FLAG_BY_USER_NAME";
		if(MethodCacheManager.isValidCache(cacheName, username)){
			logger.debug("## Inbox cache CACHE_INBOX_FLAG_BY_USER_NAME hit!!!!");
			return MethodCacheManager.getCacheData(cacheName, String.class, username);
		}
		logger.debug("## Inbox cache CACHE_INBOX_FLAG_BY_USER_NAME either expired or not hit");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = "";
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("SELECT PKA_INBOX.F_INBOX_FLAG(?) AS INBOX_FLAG FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,username);
			
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getString(1);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new EngineException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new EngineException(e.getLocalizedMessage());
			}
		}
		
		//Update cache data
		MethodCacheManager.updateCache(cacheName, result, username);
		return result;			
	}

}
