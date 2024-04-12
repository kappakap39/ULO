package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLWFDAOException;

public class PLWFLoadStatusOnJobDAOImpl extends OrigObjectDAO implements
		PLWFLoadStatusOnJobDAO {
	
	private static Logger log = Logger.getLogger(PLWFLoadCentralJobAmountDAOImpl.class);

	@Override
	public String loadStatusOnJob(String userName, String TDID) throws PLWFDAOException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String statusOnJob = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT wq.ONJOB_FLAG FROM USER_WORK_QUEUE wq, WF_TODO_LIST tl ");
			sql.append("WHERE wq.USER_NAME = ? and wq.ATID = tl.ATID and tl.TDID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userName);
			ps.setString(2, TDID);

			rs = ps.executeQuery();
			if (rs.next()) {
				statusOnJob = rs.getString(1);
			}
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLWFDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLWFDAOException(e.getMessage());
			}
		}
		
		return statusOnJob;
	}

}
