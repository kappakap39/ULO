package com.eaf.inf.batch.ulo.applicationdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;

public class InfChangeDateDAOImpl extends InfBatchObjectDAO implements InfChangeDateDAO {
	private static transient Logger logger = Logger.getLogger(UpdateApplicationDateDAOImpl.class);
	@Override
	public Date getCurrentDate() throws InfBatchException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APP_DATE ");
			sql.append(" FROM APPLICATION_DATE ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getDate("APP_DATE");
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
	
	public void updateChangeDate(String currentDate) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			/*sql.append(" INSERT INTO GENERAL_PARAM ");
			sql.append(" (PARAM_VALUE) ");
			sql.append(" VALUES (?) ");
			sql.append(" SELECT PARAM_VALUE, PARAM_CODE ");
			sql.append(" FROM GENERAL_PARAM ");
			sql.append(" WHERE PARAM_CODE = 'CTE_DATE' ");*/
			
			sql.append(" UPDATE GENERAL_PARAM ");
			sql.append(" SET PARAM_VALUE = ? ");
			sql.append(" WHERE PARAM_CODE = 'CTE_DATE' ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int intdex = 1;
			ps.setString(intdex++,currentDate);
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
}
