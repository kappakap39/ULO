package com.eaf.batch.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.constant.MConstant;

public class BatchUserDetailDAOImpl extends InfBatchObjectDAO implements BatchUserDetailDAO{
	private static Logger logger = Logger.getLogger(BatchUserDetailDAOImpl.class);
	@Override
	public List<String> getexpireUsers(String USERDETAIL_DATE_EXPIRE)throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		List<String> expireUsers = new ArrayList<String>();
		try{	
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			int index = 1;
			sql.append(" SELECT USER_NAME FROM US_USER_DETAIL ");
			sql.append(" WHERE TRUNC(UPDATE_DATE) <=" +
					" (SELECT TRUNC(APP_DATE)-"+USERDETAIL_DATE_EXPIRE+" FROM APPLICATION_DATE)");
			sql.append(" AND ACTIVE_STATUS = ? ");
			logger.debug("SQL : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(index++, MConstant.FLAG.INACTIVE);
			rs = ps.executeQuery();
			while(rs.next()){
				expireUsers.add(rs.getString("USER_NAME"));
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e);
			}
		}
		return expireUsers;
	}
	
	@Override
	public String getGeneralParam(String paramId) throws InfBatchException {
		String generalParam = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();
			SQL.append("SELECT * FROM GENERAL_PARAM WHERE PARAM_CODE = ?");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			ps.setString(1,paramId);
			rs = ps.executeQuery();
			if(rs.next()){
				generalParam = rs.getString("PARAM_VALUE");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		logger.debug("generalParam >> "+generalParam);
		return generalParam;
	}

	@Override
	public void deleteUserProfile(String userName, Connection conn)throws InfBatchException {
		PreparedStatement ps = null;
		try{	
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM USER_PROFILE WHERE USER_NAME = ?");
			logger.debug("SQL : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,userName);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				if(ps!=null){
					ps.close();
					ps=null;
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e);
			}
		}
	}

	@Override
	public void deleteUserLeave(String userName, Connection conn)throws InfBatchException {
		PreparedStatement ps = null;
		try{	
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM USER_LEAVE WHERE USER_NAME = ? ");
			logger.debug("SQL : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,userName);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				if(ps!=null){
					ps.close();
					ps=null;
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e);
			}
		}
	}

	@Override
	public void deleteUserPerformance(String userName, Connection conn)throws InfBatchException {
		PreparedStatement ps = null;
		try{	
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM USER_PERFORMANCE WHERE USER_NAME = ?");
			logger.debug("SQL : " + sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, userName);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				if(ps!=null){
					ps.close();
					ps=null;
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e);
			}
		}
	}

	@Override
	public void deleteUserTeam (String userName,Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM MS_USER_TEAM WHERE USER_ID = ? ");
			logger.debug("SQL : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, userName);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				if(null!=ps){
					ps.close();
					ps=null;
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e);
			}
		}
	}

	@Override
	public void deleteUserRole(String userName) throws InfBatchException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{	
			conn = getConnection(InfBatchServiceLocator.ORIG_IAS);
			conn.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM USER_ROLE WHERE USER_NAME = ?");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, userName);
			ps.executeUpdate();
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e);
			}
		}
	}

	@Override
	public void deleteOrganizationChart(String userName) throws InfBatchException{
		Connection conn = null;
		try{	
			conn = getConnection();
			conn.setAutoCommit(false);
			deleteUserLeave(userName, conn);
			deleteUserPerformance(userName, conn);
			deleteUserTeam(userName, conn);
			deleteUserProfile(userName, conn);
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			try{
				conn.rollback();
			}catch(Exception e1){
				logger.fatal("ERROR",e1);
			}
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
		}
	}

	@Override
	public void deleteUserDetail(String userName) throws InfBatchException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{	
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM US_USER_DETAIL WHERE USER_NAME =? ");
			logger.debug("SQL : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,userName);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn,ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e);
			}
		}
	}

	@Override
	public void deleteIasUser(String userName) throws InfBatchException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{	
			conn = getConnection(InfBatchServiceLocator.ORIG_IAS);
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM USERS WHERE USER_NAME = ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, userName);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e);
			}
		}
	}

	

}
