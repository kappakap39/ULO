package com.eaf.core.ulo.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationDateDAOImpl extends ObjectDAO implements ApplicationDateDAO{	
	private static transient Logger logger = LogManager.getLogger(ApplicationDateDAOImpl.class);
	@Override
	public Date getApplicationDate() throws Exception{
		Date applicationDate = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection(ServiceLocator.ORIG_DB);
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
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		logger.debug("applicationDate >> "+applicationDate);
		return applicationDate;
	}
}
