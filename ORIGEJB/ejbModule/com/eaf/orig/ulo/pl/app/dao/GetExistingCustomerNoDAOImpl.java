package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;

public class GetExistingCustomerNoDAOImpl extends OrigObjectDAO implements GetExistingCustomerNoDAO{
	private static Logger log = Logger.getLogger(GetExistingCustomerNoDAOImpl.class);
	@Override
	public String getCustomerNoDAO(String appNo) {
		StringBuilder result = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select CARDLINK_CUST_NO ");
			sql.append("where APPLICATION_NO = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appNo);
			rs = ps.executeQuery();
			if(rs.next()) {
				result.append(rs.getString(1));
			}			
			return result.toString();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			//throw new e.getMessage();
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				//throw new e.getMessage();
			}
		}
		return result.toString();
	}

}
