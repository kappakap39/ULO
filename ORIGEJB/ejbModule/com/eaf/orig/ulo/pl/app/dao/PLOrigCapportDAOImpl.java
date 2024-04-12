package com.eaf.orig.ulo.pl.app.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.OverrideCapportDataM;

public class PLOrigCapportDAOImpl extends OrigObjectDAO implements PLOrigCapportDAO {

	private static Logger log = Logger.getLogger(PLOrigUserDAOImpl.class);
	
	@Override
	public CapportGroupDataM getCapportGroupDetails(String capportNo)
			throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		CapportGroupDataM capGroupM = new CapportGroupDataM();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select cpg.* ");
            sql.append("from ms_cap_port_group cpg ");
            sql.append("inner join ms_cap_port_group_map cmap on cmap.cap_port_group_id = cpg.cap_port_group_id ");
            sql.append("inner join ms_cap_port cp on cp.cap_port_no = cmap.cap_port_no ");
            sql.append("where cp.cap_port_no = ? and cpg.effective_date <= trim(sysdate) and cpg.expire_date >= trim(sysdate) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, capportNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				capGroupM.setCapportAmount(rs.getDouble("CAP_PORT_AMOUNT"));
				capGroupM.setCapportGroupId(rs.getString("CAP_PORT_GROUP_ID"));
				capGroupM.setCapportGroupName(rs.getString("CAP_PORT_GROUP_NAME"));
				capGroupM.setCapportUsed(rs.getDouble("CAP_PORT_USED"));
				capGroupM.setEffectiveDate(rs.getDate("EFFECTIVE_DATE"));
				capGroupM.setExpireDate(rs.getDate("EXPIRE_DATE"));
				capGroupM.setPercentWorning(rs.getDouble("PERCENT_WARNING"));
			}			
			return capGroupM;
			
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
	public void updateCapportUsed(String capportNo, String appRecordId, String busClass, String capportType, String userName ) throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" CALL pka_app_util.update_capport_used(?, ?, ?, ?, ?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ processAutoIncreaseDecrease SQL:"+dSql);
			logger.debug("@@@@@ capportNo :"+capportNo);
			logger.debug("@@@@@ appRecordId :"+appRecordId);
			logger.debug("@@@@@ busClass :"+busClass);
			logger.debug("@@@@@ capportType :"+capportType);
			logger.debug("@@@@@ userName :"+userName);
			ps = conn.prepareCall(dSql);
			ps.setString(1,capportNo);
			ps.setString(2,appRecordId);
			ps.setString(3,busClass);
			ps.setString(4,capportType);
			ps.setString(5,userName);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public OverrideCapportDataM getOverrideCapport(String appRecordID) throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		OverrideCapportDataM overideCapportM = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("call pka_capport.sp_override_capport(?,?)");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			cs = conn.prepareCall(dSql);
			cs.setString(1, appRecordID);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			if (rs.next()) {
				overideCapportM = new OverrideCapportDataM();
				overideCapportM.setAppRecordID(appRecordID);
				overideCapportM.setResult(rs.getString("result"));
				overideCapportM.setPercent(rs.getDouble("percent_override"));
				overideCapportM.setOverideAppAmount(rs.getInt("override_app_amount"));
				overideCapportM.setAppInAmount(rs.getInt("app_in_amount"));
			}			
			return overideCapportM;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, cs);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

}
