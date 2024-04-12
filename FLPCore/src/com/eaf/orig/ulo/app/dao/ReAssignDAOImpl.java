package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.SearchDAOImpl;
import com.eaf.core.ulo.common.exception.EngineException;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;

public class ReAssignDAOImpl  extends SearchDAOImpl  implements ReAssignDAO {
	private static transient Logger logger = Logger.getLogger(ReAssignDAOImpl.class);
	@Override
	public ArrayList<String> getUserRole(ArrayList<String> roles) throws Exception {
		ArrayList<String> userNames = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		if(Util.empty(roles)){
			return null;
		}		
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();			
			SQL.append(" SELECT USER_ROLE.USER_NAME FROM ROLE INNER JOIN  USER_ROLE ON ROLE.ROLE_ID = USER_ROLE.ROLE_ID");
			SQL.append(" WHERE  ROLE.ROLE_NAME IN (");
				String COMMA = "";
				for (int i = 0, count = roles.size(); i < count ; i++) {
					SQL.append(COMMA+"?");
					COMMA = ",";
				}
				SQL.append(" ) ");	
				
			logger.debug("SQL=" + SQL);
			ps = conn.prepareStatement(SQL.toString());	
			int index=1;
			for(String roleName: roles){
				ps.setString(index++,roleName); 							 
			}
			rs = ps.executeQuery();	
			while(rs.next()){
				userNames.add(rs.getString("USER_NAME"));
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
		return userNames;
	}
	
	@Override
	public ArrayList<String> getActiveInboxUserRole(ArrayList<String> roles) throws Exception {
		ArrayList<String> userNames = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		if(Util.empty(roles)){
			return null;
		}		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();			
			sql.append(" SELECT USER_ROLE.USER_NAME FROM ROLE INNER JOIN  USER_ROLE ON ROLE.ROLE_ID = USER_ROLE.ROLE_ID");
			sql.append(" JOIN US_USER_DETAIL U ON UPPER(USER_ROLE.USER_NAME) = UPPER(U.USER_NAME)");
			sql.append(" JOIN  USER_INBOX_INFO I ON UPPER(U.USER_NAME) = UPPER(I.USER_NAME)");
			sql.append(" WHERE  ROLE.ROLE_NAME IN (");
			String COMMA = "";
			for (int i = 0, count = roles.size(); i < count ; i++) {
				sql.append(COMMA+"?");
				COMMA = ",";
			}
			sql.append(" ) AND I.INBOX_FLAG = 'Y' AND U.LOGON_FLAG = 'Y'");	
			
			logger.debug("SQL=" + sql);
			ps = conn.prepareStatement(sql.toString());	
			int index=1;
			for(String roleName: roles){
				ps.setString(index++,roleName); 							 
			}
			rs = ps.executeQuery();	
			while(rs.next()){
				userNames.add(rs.getString("USER_NAME"));
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
		return userNames;
	}
	
	@Override
	public boolean checkUserLogOnAndInboxFlag(String userName) throws Exception {
	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		boolean canAssign=false;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();			
			SQL.append(" SELECT USER_INBOX_INFO.INBOX_FLAG,US_USER_DETAIL.LOGON_FLAG ");
			SQL.append(" FROM  US_USER_DETAIL");
			SQL.append(" INNER JOIN USER_INBOX_INFO ON UPPER(USER_INBOX_INFO.USER_NAME) = UPPER(US_USER_DETAIL.USER_NAME)");
			SQL.append(" WHERE  UPPER(US_USER_DETAIL.USER_NAME) = UPPER(?)");
				 
			logger.debug("SQL=" + SQL);
			ps = conn.prepareStatement(SQL.toString());	
			ps.setString(1,userName); 	
			rs = ps.executeQuery();	
			if(rs.next()){
				if(MConstant.FLAG.YES.equals(rs.getString("INBOX_FLAG")) &&  MConstant.FLAG.YES.equals(rs.getString("LOGON_FLAG"))){
					canAssign =true;
				}
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
		logger.debug("userName : "+userName+" canAssign : "+canAssign);
		return canAssign;
	}

}
