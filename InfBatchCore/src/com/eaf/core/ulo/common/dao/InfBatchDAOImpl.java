package com.eaf.core.ulo.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.exception.InfBatchException;

public class InfBatchDAOImpl extends InfBatchObjectDAO implements InfBatchDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchDAOImpl.class);
	@Override
	public Date getApplicationDate() throws InfBatchException {
		Date applicationDate = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();
			SQL.append("SELECT * FROM APPLICATION_DATE");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				applicationDate = rs.getDate("APP_DATE");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		logger.debug("applicationDate >> "+applicationDate);
		return applicationDate;
	}
	@Override
	public String getGeneralParam(String paramId){
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
	public String getMessage(String messageId) {
		String message = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();
			SQL.append("SELECT * FROM M_MESSAGE WHERE MESSAGE_ID = ?");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			ps.setString(1,messageId);
			rs = ps.executeQuery();
			if(rs.next()){
				message = rs.getString("MESSAGE");
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
		logger.debug("message >> "+message);
		return message;
	}

}
