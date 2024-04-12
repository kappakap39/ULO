/*
 * Created on Jan 23, 2008
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.model.SLADataM;

/**
 * @author Weeraya
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OrigMasterSLADAOImpl extends OrigObjectDAO implements OrigMasterSLADAO {
	Logger logger = Logger.getLogger(OrigMasterSLADAOImpl.class);
	
	
	public double updateSLADataM(SLADataM dataM)throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE SLA_NOTIFY ");
			sql.append(" SET TIME=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE Q_NAME=? AND ROLE=?");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setDouble(1, dataM.getTime());
			ps.setString(2, dataM.getUpdateBy());
			ps.setString(3, dataM.getQName());
			ps.setString(4, dataM.getRole());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
		return returnRows;
	}
}
