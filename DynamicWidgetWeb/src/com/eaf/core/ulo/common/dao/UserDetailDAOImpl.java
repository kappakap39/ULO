package com.eaf.core.ulo.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;

public class UserDetailDAOImpl extends ObjectDAO implements UserDetailDAO{
	private static final transient Logger logger = LogManager.getLogger(UserDetailDAOImpl.class);
	@Override
	public void setUserTeam(UserDetailM userM) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection(ServiceLocator.ORIG_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT * FROM MS_USER_TEAM WHERE USER_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("SQL >> "+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1,userM.getUserName());
			rs = ps.executeQuery();			
			if(rs.next()){
				userM.setPositionId(rs.getString("POSITION_ID"));
				userM.setTeamId(rs.getString("TEAM_ID"));
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void setGridId(UserDetailM userM) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT * FROM GRID WHERE POSITION_LEVEL = ? AND CHILD_ID IS NOT NULL");
			String dSql = String.valueOf(sql);
			logger.debug("SQL >> "+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1,userM.getPositionId());
			rs = ps.executeQuery();			
			if(rs.next()){
				userM.setGridId(rs.getString("ID"));
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void setAuthGrid(UserDetailM userM) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> authGrids = new ArrayList<String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ID GRID_ID FROM GRID START WITH ID = ? CONNECT BY PRIOR CHILD_ID = ID");
			String dSql = String.valueOf(sql);
			logger.debug("SQL >> "+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1,userM.getGridId());
			rs = ps.executeQuery();			
			while(rs.next()){
				String gridId = String.valueOf(rs.getInt("GRID_ID"));
				authGrids.add(gridId);
			}
			userM.setAuthGrids(authGrids);
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
}
