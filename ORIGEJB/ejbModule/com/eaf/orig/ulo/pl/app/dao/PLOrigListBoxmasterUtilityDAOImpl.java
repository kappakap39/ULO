package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class PLOrigListBoxmasterUtilityDAOImpl extends OrigObjectDAO implements
		PLOrigListBoxMasterUtilityDAO {

	private static Logger log = Logger.getLogger(PLOrigListBoxmasterUtilityDAOImpl.class);
	
	@Override
	public Vector<ORIGCacheDataM> loadByChoice_No(String Field_ID)
			throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CHOICE_NO, DISPLAY_NAME FROM LIST_BOX_MASTER WHERE FIELD_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, Field_ID);

			rs = ps.executeQuery();
			Vector<ORIGCacheDataM> CacheVect = new Vector<ORIGCacheDataM>();
			while (rs.next()) {
				ORIGCacheDataM CacheM = new ORIGCacheDataM();
				CacheM.setCode(rs.getString(1));
				CacheM.setShortDesc(rs.getString(2));
				CacheVect.add(CacheM);
			}
			
			return CacheVect;
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
