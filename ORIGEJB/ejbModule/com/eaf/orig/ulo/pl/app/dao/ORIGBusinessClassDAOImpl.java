package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class ORIGBusinessClassDAOImpl extends OrigObjectDAO implements ORIGBusinessClassDAO {
	private static Logger log = Logger.getLogger(PLOrigRuleDAOImpl.class);
	@Override
	public HashMap<String, String> getBusGroupByBusClass(String busClass)throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select bm.bus_grp_id ");
			sql.append("from bus_class_grp_map bm ");
            sql.append("where bm.bus_class_id = ? ");
            sql.append("group by bm.bus_grp_id");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, busClass);
			rs = ps.executeQuery();
			while (rs.next()) {
//				log.info("bus_grp_id=" + rs.getString("bus_grp_id"));
				result.put(rs.getString("bus_grp_id"), rs.getString("bus_grp_id"));
			}			
			return result;
			
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

	@Override
	public HashMap<String, String> getBusClassByBusGroup(String busGroup)throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> result = new HashMap<String, String>();
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select bm.bus_class_id ");
			sql.append("from bus_class_grp_map bm ");
            sql.append("where bm.bus_grp_id = ? ");
            sql.append("group by bm.bus_class_id");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, busGroup);
			rs = ps.executeQuery();
			while (rs.next()) {
				result.put(rs.getString("bus_class_id"), rs.getString("bus_class_id"));
			}			
			return result;
			
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
