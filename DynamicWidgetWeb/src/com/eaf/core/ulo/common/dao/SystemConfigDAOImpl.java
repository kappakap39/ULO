package com.eaf.core.ulo.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemConfigDAOImpl extends ObjectDAO implements SystemConfigDAO{
	static final transient Logger logger = LogManager.getLogger(SystemConfigDAOImpl.class);
	@Override
	public String getGenerateParam(String paramCode) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String paramValue = null;		
		try {
			conn = getConnection(ServiceLocator.ORIG_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT * FROM GENERAL_PARAM WHERE PARAM_CODE = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, paramCode);
			rs = ps.executeQuery();			
			if (rs.next()) {
				paramValue = rs.getString("PARAM_VALUE");
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return paramValue;
	}

}
