package com.eaf.inf.batch.ulo.applicationdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;

public class UpdateApplicationDateDAOImpl  extends InfBatchObjectDAO implements UpdateApplicationDateDAO {
	private static transient Logger logger = Logger.getLogger(UpdateApplicationDateDAOImpl.class);
	@Override
	public void updateApplicationDate(int numDate) throws InfBatchException {
		try {
			Timestamp app_date =  this.selectApplicatioDate(numDate);
			int returnRows = this.updateTableApplicationDate(app_date);
			if(returnRows == 0){				 
				this.insertTableApplicationDate(numDate);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		}
	}

	private void insertTableApplicationDate(int numdate) throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO APPLICATION_DATE ");
			sql.append("(APP_DATE)");
			sql.append(" VALUES (SYSDATE +"+numdate+") ");
		
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);		
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	
	}
	
	private int updateTableApplicationDate(Timestamp app_date) throws InfBatchException {
		Connection conn = null;
		int returnRows = 0;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");					
			sql.append("UPDATE  APPLICATION_DATE ");
			sql.append("SET APP_DATE=?");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("app_date=" + app_date);
			ps = conn.prepareStatement(dSql);	
			int intdex = 1;
			ps.setTimestamp(intdex++,app_date);
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return returnRows;		
	}
	
	private Timestamp selectApplicatioDate(int numDate) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT NVL(APP_DATE,SYSDATE)+"+numDate+" AS APP_DATE  FROM APPLICATION_DATE ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getTimestamp("APP_DATE");
			}
		 
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		 return null;
		}
	}
 
