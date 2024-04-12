package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class PLOrigApplicationPointDAOImpl extends OrigObjectDAO implements PLOrigApplicationPointDAO {

	private Logger log = Logger.getLogger(this.getClass());
	@Override
	public void deletePoint(String appRecId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE FROM ORIG_APPLICATION_POINT ap WHERE ap.APPLICATION_RECORD_ID = ? ");
			sql.append("AND ap.LIFE_CYCLE = ( SELECT MAX(sap.LIFE_CYCLE) FROM ORIG_APPLICATION_POINT sap WHERE sap.APPLICATION_RECORD_ID = ? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);
			ps.setString(2, appRecId);
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

}
