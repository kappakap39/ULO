package com.eaf.orig.ulo.app.view.util.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public class InfBatchLogDAOImpl extends OrigObjectDAO implements InfBatchLogDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchLogDAOImpl.class);
	public InfBatchLogDAOImpl(){
		
	}
	public InfBatchLogDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void blockInfBatchLogReprocess(String applicationGroupId,int lifeCycle,String logMessage)throws ApplicationException{
		Connection conn = null;
		try{
			conn = getConnection();
			blockInfBatchLogReprocess(applicationGroupId,lifeCycle,logMessage,conn);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{		
				if(null!=conn)conn.close();
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public void blockInfBatchLogReprocess(String applicationGroupId,int lifeCycle,String logMessage,Connection conn)throws ApplicationException{
		String INTERFACE_STATUS_ERROR = SystemConstant.getConstant("INTERFACE_STATUS_ERROR");
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
				sql.append(" UPDATE INF_BATCH_LOG 		");
				sql.append(" SET INTERFACE_STATUS = '"+INTERFACE_STATUS_ERROR+"' ");
				sql.append("     , LOG_MESSAGE = CASE WHEN LOG_MESSAGE IS NOT NULL THEN LOG_MESSAGE || ', ' || ? ");
				sql.append("                          ELSE ? END ");
				sql.append(" WHERE APPLICATION_GROUP_ID = ? AND LIFE_CYCLE = ?  ");
				sql.append("	AND INTERFACE_CODE IN ("+SystemConstant.getSQLParameter("INTERFACE_CODE_NOTI_REPROCESS")+") ");
			String dSql = String.valueOf(sql);
			logger.debug("sql : "+dSql);
			ps = conn.prepareStatement(dSql);	
				int index = 1;
				ps.setString(index++,logMessage);
				ps.setString(index++,logMessage);
				// where clause
				ps.setString(index++,applicationGroupId);
				ps.setInt(index++,lifeCycle);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getLocalizedMessage());
		}finally{
			try{
				if(ps != null){
					try{
						ps.close();
					}catch(Exception e){
						logger.fatal("ERROR",e);
						throw new ApplicationException(e.getLocalizedMessage());
					}
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getLocalizedMessage());
			}
		}
	}
}
