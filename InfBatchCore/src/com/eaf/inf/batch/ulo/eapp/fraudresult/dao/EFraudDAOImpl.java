package com.eaf.inf.batch.ulo.eapp.fraudresult.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;

public class EFraudDAOImpl extends InfBatchObjectDAO implements EFraudDAO {
	
	private Logger logger = Logger.getLogger(EFraudDAOImpl.class);

	@Override
	public void addWorkManagerTask(String wmFunc) throws Exception {
		PreparedStatement ps = null;	
		Connection conn = null;	
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" INSERT INTO WM_TASK ");
			sql.append("( TASK_ID, TASK, CREATE_TIME ) ");
			sql.append("VALUES (?,?,SYSTIMESTAMP(6) )");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, GenerateUnique.generate(MConstant.WM_TASK_ID_PK_SEQUENCE,conn));
			ps.setString(index++, wmFunc);
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}
}
