package com.eaf.orig.ulo.dm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.dm.dao.exception.DMException;

public class DMImportExcelDAOImpl extends OrigObjectDAO implements DMImportExcelDAO {

	@Override
	public void createDmWithdrawalAuth(String userNo,String updateBy) throws DMException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection(OrigServiceLocator.WAREHOUSE_DB);
			StringBuilder sql = new StringBuilder("");					
			sql.append(" INSERT INTO US_AUTH ");
			sql.append(" ( ");
			sql.append(" EMPLOYEE_NO,UPDATE_BY,UPDATE_DATE ");
			sql.append(" )VALUES( ");
			sql.append(" ?, ?, SYSDATE ");
			sql.append(" ) ");
						
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int index = 1;
			ps.setString(index++, userNo);
			logger.debug("EMPLOYEE_NO : "+userNo);
			ps.setString(index++, updateBy);
			logger.debug("UPDATE_BY : "+updateBy);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void deleteAllDmWithdrawalAuthEmployee() throws DMException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			
			conn = getConnection(OrigServiceLocator.WAREHOUSE_DB);
			StringBuilder sql = new StringBuilder("");					
			sql.append(" DELETE FROM  US_AUTH ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
	}

}
