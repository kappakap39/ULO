package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;

public class PLORIGBranchGroupValueImpl extends OrigObjectDAO implements PLORIGBranchGroupValue {
	private static Logger log = Logger.getLogger(PLORIGBranchGroupValueImpl.class);

	@Override
	public String getBranchGroupValue(String branchNo) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String BranchGroupCodeResult = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT BRANCH_GROUP FROM VW_MS_BRANCH_CODE ");
			sql.append("WHERE KBANK_BR_NO = ? ");
			
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, branchNo);

			rs = ps.executeQuery();
			if(rs.next()){
				BranchGroupCodeResult = rs.getString(1);
			}
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
//			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
//				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return BranchGroupCodeResult;
	}

	@Override
	public String getChannelGroupValue(String Channel) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT CHANNEL_GROUP FROM VW_MS_SALE_PERSON ");
			sql.append("WHERE SALE_TYPE = ? ");
			
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, Channel);

			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
//			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
//				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return null;
	}

}
