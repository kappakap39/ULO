package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class PLOrigZipcodeDAOImpl extends OrigObjectDAO implements PLOrigZipcodeDAO {
		Logger log = Logger.getLogger(PLOrigZipcodeDAOImpl.class);
		
	@Override
	public String Zipcode(String Tambol) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String zipcodeResult = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ZIPCODE, ZIPCODE_ID, SUB_DISTRICT_ID");
			sql.append(" FROM MS_ZIPCODE WHERE SUB_DISTRICT_ID = ? ");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, Tambol);

			rs = ps.executeQuery();
			
			if(rs.next()) {
			zipcodeResult =	rs.getString(1);
			}
			
			return zipcodeResult;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

}
