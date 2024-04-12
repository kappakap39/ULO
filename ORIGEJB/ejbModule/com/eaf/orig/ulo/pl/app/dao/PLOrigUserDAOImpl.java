package com.eaf.orig.ulo.pl.app.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.UserPointDataM;

public class PLOrigUserDAOImpl  extends OrigObjectDAO implements PLOrigUserDAO{

	private static Logger log = Logger.getLogger(PLOrigUserDAOImpl.class);
	
	@Override
	public UserPointDataM getUserPointDetails(String userName, ProcessMenuM menuM) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserPointDataM userPointM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select us.user_name, us.ca_point_target, ");
            sql.append("ceil((pka_app_util.count_weekly_working_day(sysdate) - pka_app_util.count_weekly_leave(sysdate,us.user_name) - pka_app_util.count_weekly_waive_day(sysdate,?))*us.ca_point_target) as weekly_target, ");
            sql.append("pka_app_util.count_daily_point_work(sysdate,us.user_name) as daily_point, ");
            sql.append("pka_app_util.count_weekly_point_work(sysdate,us.user_name) as weekly_point ");
            sql.append("from us_user_detail us where us.user_name = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql + "|menu:"+menuM.getMenuID()+",username:"+userName);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, menuM.getMenuID());
			ps.setString(2, userName);
			rs = ps.executeQuery();
			if (rs.next()) {
				userPointM = new UserPointDataM();
				userPointM.setUserName(rs.getString("user_name"));
				userPointM.setDailyPointTarget(rs.getDouble("ca_point_target"));
				userPointM.setWeeklyPointTarget(rs.getDouble("weekly_target"));
				userPointM.setDailyPoint(rs.getDouble("daily_point"));
				userPointM.setWeeklyPoint(rs.getDouble("weekly_point"));
			}			
			return userPointM;
			
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
	public BigDecimal getUserLendingLimit(String userName, String busClass)
			throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal resultLendingLimit = new BigDecimal(0);
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_app_util.get_user_lending_limit(?,?) as lending_limit from dual");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userName);
			ps.setString(2, busClass);
			rs = ps.executeQuery();
			if (rs.next()) {
				resultLendingLimit = rs.getBigDecimal("lending_limit");
			}			
			return resultLendingLimit;
			
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
	public UserDetailM getUserDetail(String userName) throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserDetailM userM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select *");
            sql.append("from us_user_detail us where us.user_name = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if (rs.next()) {
				userM = new UserDetailM();
				userM.setUserName(rs.getString("USER_NAME"));
				userM.setEmail(rs.getString("EMAIL_ADDRESS"));
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
