package com.eaf.orig.ulo.pl.app.utility;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.EjbUtilException;
import com.eaf.orig.shared.dao.OrigObjectDAO;

public class PLBPMUtilityDAOImpl extends OrigObjectDAO implements PLBPMUtilityDAO {
	Logger logger = Logger.getLogger(PLBPMUtilityDAOImpl.class);
	public String getUserTimeRemain(String role,String appRecId) throws EjbUtilException {
        CallableStatement cs = null; 	
        String result="";Connection conn = null;
 		try {
 			//conn = Get Connection 	 
 			conn = getConnection();
  			StringBuffer sql = new StringBuffer("{? = call PCK_SLA.getTimeRemainUserTime(?,?)}");  			 
  			logger.debug("Sql="+sql.toString());
  			cs = conn.prepareCall(sql.toString()); 			
 			cs.registerOutParameter(1, java.sql.Types.VARCHAR);
 			cs.setString(2, appRecId);
  			cs.setString(3, role);
  			cs.execute();			   			 
  			result = cs.getString(1);  		  			
  			logger.debug("result="+result);
  			return result;
 		} catch (Exception e) {
 			logger.error("Error >> ", e);
 			throw new EjbUtilException(e.getMessage());
 		} finally {
 			try {
 				closeConnection(conn, cs);
 			} catch (Exception e) {
 				logger.error("Error >> ", e);
 			}
 		}
	}
	public String getSystemTimeRemain(String bussClass,String appRecId) throws EjbUtilException {
         String result="";
         CallableStatement cs = null;  Connection conn = null;		 
  		try {
  			//conn = Get Connection
  			conn = getConnection();
  			StringBuffer sql = new StringBuffer("{? = call PCK_SLA.getTimeRemainApplicationTime(?,?)}");  			 
  			logger.debug("Sql="+sql.toString());
  			cs = conn.prepareCall(sql.toString());
  			cs.registerOutParameter(1, java.sql.Types.VARCHAR);
  			cs.setString(2, appRecId);
  			cs.setString(3, bussClass);
  			cs.execute();			   			 
  			result = cs.getString(1);  		
  			logger.debug("result="+result);
  			return result;
  		} catch (Exception e) {
  			logger.error("Error >> ", e);
  			throw new EjbUtilException(e.getMessage());
  		} finally {
  			try {
  				closeConnection(conn,cs);
  			} catch (Exception e) {
  				logger.error("Error >> ", e);
  			}
  		}
	}
}
