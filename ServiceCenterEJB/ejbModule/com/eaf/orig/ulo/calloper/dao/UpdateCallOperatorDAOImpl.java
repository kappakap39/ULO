package com.eaf.orig.ulo.calloper.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.eaf.service.common.exception.ServiceControlException;
import com.eaf.service.common.master.ObjectDAO;

public class UpdateCallOperatorDAOImpl extends ObjectDAO implements UpdateCallOperatorDAO {
	private static transient Logger logger = Logger.getLogger(UpdateCallOperatorDAOImpl.class);
	
	@Override
	public void updateCallOperator(String applicationGroupId,String CallOperator) throws ServiceControlException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE ORIG_APPLICATION_GROUP SET CALL_OPERATOR = ? WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, CallOperator);
			ps.setString(parameter++, applicationGroupId);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ServiceControlException(e.getLocalizedMessage());
			}
		}
	}
	
}
