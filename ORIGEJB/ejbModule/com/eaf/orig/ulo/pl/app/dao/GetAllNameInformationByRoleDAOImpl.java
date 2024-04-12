package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eaf.orig.shared.dao.OrigObjectDAO;

public class GetAllNameInformationByRoleDAOImpl extends OrigObjectDAO implements GetAllNameInformationByRoleDAO {

	@Override
	public String getallNameInformationByRole(String username) {
		StringBuilder fullName = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT THAI_FIRSTNAME, THAI_LASTNAME FROM US_USER_DETAIL ");
			sql.append("WHERE USER_NAME = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareCall(dSql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			

		
			if(rs.next()){
				fullName.append(rs.getString(1));
				fullName.append(" ");
				fullName.append(rs.getString(2));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());

			}
		}
		return fullName.toString();

	}

}
