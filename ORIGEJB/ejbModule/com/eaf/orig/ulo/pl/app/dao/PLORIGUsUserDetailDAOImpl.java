package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class PLORIGUsUserDetailDAOImpl extends OrigObjectDAO implements PLORIGUsUserDetailDAO {
	
	private static Logger log = Logger.getLogger(PLOrigTrackingDAOImpl.class);

	@Override
	public UserDetailM loadUsUserDetail(String userName) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT THAI_FIRSTNAME, THAI_LASTNAME, USER_NAME, TELEPHONE ");
			sql.append(" FROM US_USER_DETAIL  WHERE USER_NAME = ? AND ACTIVE_STATUS = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userName);
			ps.setString(2, OrigConstant.Status.STATUS_ACTIVE);

			rs = ps.executeQuery();
			
			UserDetailM userM = new UserDetailM();
			if (rs.next()) {
				int index = 1;
				userM.setThaiFirstName(rs.getString(index++));
				userM.setThaiLastName(rs.getString(index++));
				userM.setUserName(rs.getString(index++));
				userM.setTelephone(rs.getString(index++));
			}
			
			return userM;
			
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
