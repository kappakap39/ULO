package com.eaf.orig.ulo.app.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public class ServiceDAOImpl extends OrigObjectDAO implements ServiceDAO{
	private static transient Logger logger = Logger.getLogger(ServiceDAOImpl.class);	
	@Override
	public String getServiceResult(String serviceId, String appId,String activityType) throws ApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String serviceResult = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT RESP_CODE FROM SERVICE_REQ_RESP WHERE SERVICE_ID = ? AND APP_ID = ? AND ACTIVITY_TYPE = ? ORDER BY CREATE_DATE DESC");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, serviceId);
			ps.setString(2, appId);
			ps.setString(3, activityType);
			rs = ps.executeQuery();
			if(rs.next()){
				serviceResult = rs.getString("RESP_CODE");
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getLocalizedMessage());
			}
		}		
		logger.debug("serviceId : "+serviceId);
		logger.debug("serviceResult : "+serviceResult);
		return serviceResult;
	}

}
