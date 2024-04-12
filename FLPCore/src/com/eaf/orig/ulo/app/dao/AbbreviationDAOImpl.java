package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eaf.orig.shared.dao.OrigObjectDAO;


public class AbbreviationDAOImpl extends OrigObjectDAO implements AbbreviationDAO{
	@Override
	public String getAbbreviation(String fullword) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuilder sql = new StringBuilder("");
		String result=null; 
		try {

			sql.append("SELECT ABBREVIATION FROM ");
			sql.append(" MS_ABBREVIATION ");
			sql.append(" WHERE ");
			sql.append(" FULL_WORD = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql +" :Param >>"+fullword);
			conn = getConnection();
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1,fullword);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				result = rs.getString("ABBREVIATION");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.fatal(e.getLocalizedMessage());
			//throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return result;
	}

}
