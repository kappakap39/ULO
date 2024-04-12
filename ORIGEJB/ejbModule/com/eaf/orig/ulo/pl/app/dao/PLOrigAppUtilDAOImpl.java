package com.eaf.orig.ulo.pl.app.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.EjbUtilException;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class PLOrigAppUtilDAOImpl extends OrigObjectDAO implements PLOrigAppUtilDAO {
	private static Logger log = Logger.getLogger(PLOrigAppUtilDAOImpl.class);
	@Override
	public Date getDateExtendWorkingDay(Date startCalDate, int extendDay)
			throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date resultDate = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_app_util.get_date_extend_working_day(?,?) resultDate from dual");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setDate(1, this.parseDate(startCalDate));
			ps.setInt(2, extendDay);
			rs = ps.executeQuery();
			if (rs.next()) {
				resultDate = rs.getDate("resultDate");
			}			
			return resultDate;			
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
	
	public String getDefaultCycleCut(String businessClass) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String resultCycle = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select to_char(pka_app_util.get_default_cycle_cut(?)) cycle_cut from dual");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, businessClass);
			rs = ps.executeQuery();
			if (rs.next()) {
				resultCycle = rs.getString("cycle_cut");
			}			
			return resultCycle;			
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
	public String getDLA(BigDecimal creditLimit,String jobType) throws EjbUtilException {
		String result="";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT dla_id  from ms_dla");
			sql.append(" WHERE credit_limit>=?  and  credit_limit >=0 and dla_id like ? ");
			sql.append(" ORDER BY credit_limit asc,dla_id desc");
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setBigDecimal(1, creditLimit);
			ps.setString(2, jobType+"%");
			rs = ps.executeQuery();			 
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}		 
	}
	@Override
	/*20121213 Sankom  Add Parameter Credit Limit for CR*/
	public String getDLAPolicy(String policyRules,BigDecimal creditLimit) throws EjbUtilException {
		String result="";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*
			sql.append("select dla_id,policy_rules from (");
			sql.append(" SELECT POL.DLA_ID,  ''''||LISTAGG(pol.policy_rule_id, ''',''') WITHIN GROUP (ORDER BY policy_rule_id)||'''' AS policy_rules");
			sql.append("  FROM ms_dla_policy pol");
			sql.append("  where POL.POLICY_RULE_ID in ("+policyRules+")");
			sql.append(" GROUP BY pol.dla_id) where policy_rules=?  ");
			sql.append(" ORDER BY dla_id desc");*/
			
			/*20121213 Sankom  Add Parameter Credit Limit for CR*/
			sql.append("select dla_policy.dla_id,dla.dla_desc,dla.credit_limit from  ms_dla dla, ");
			sql.append("  (select dla_id,policy_rules from (");
			sql.append("     SELECT POL.DLA_ID,  ''''||LISTAGG(pol.policy_rule_id, ''',''') WITHIN GROUP (ORDER BY policy_rule_id)||'''' AS policy_rules");
			sql.append("       FROM ms_dla_policy pol  where POL.POLICY_RULE_ID in ("+policyRules+")");
			sql.append("       GROUP BY pol.dla_id) where policy_rules=? ");
			sql.append("    ) dla_policy ");
			sql.append("  where dla.dla_id = dla_policy.dla_id ");
			sql.append(" and dla.credit_limit >= ? ");
			sql.append("  order by dla_id desc ");			 
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;			 
			ps.setString(1, policyRules);
			ps.setBigDecimal(2, creditLimit);
			rs = ps.executeQuery();			 
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}		 
	}

	@Override
	public String getDateExtendWorkingDay(String appRecID) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String DATE = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
			SQL.append("SELECT PKA_FU_UTIL.FU_REJECT_DATE(?) RESULTDATE FROM DUAL");
			
			String dSql = String.valueOf(SQL);
			
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, appRecID);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				DATE = rs.getString("RESULTDATE");
			}					
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return DATE;	
	}
	
	
}
