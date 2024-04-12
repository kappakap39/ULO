package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.CapportTransactionDataM;
import com.eaf.orig.ulo.model.app.LoanSetupDataM;
import com.eaf.orig.ulo.model.app.LoanSetupStampDutyDataM;

public class CapportTransactionDAOImpl extends OrigObjectDAO implements CapportTransactionDAO {
	private String userId = "";

	public CapportTransactionDAOImpl(){
		
	}
	public CapportTransactionDAOImpl(String userId){
		this.userId = userId;
	}

	@Override
	public void createCapportTransaction(CapportTransactionDataM capportTranDataM) throws ApplicationException {
		try {
			//Get Primary Key
		    
		    //if(Util.empty(capportTranDataM.getCapportId())){
				String capportId = GenerateUnique.generate(MConstant.CAPPORT_SEQUENCE_NAME.CPT_CAP_PORT_ID_PK);
				capportTranDataM.setCapportId(capportId);
				logger.debug("capportTranDataM.getCapportId() :"+capportTranDataM.getCapportId());
				capportTranDataM.setCreateBy(userId);
		    //}
		    capportTranDataM.setUpdateBy(userId);
		    insertTableULO_CAP_PORT_TRANSACTION(capportTranDataM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public CapportTransactionDataM loadCapportTransaction(String capportId)	throws ApplicationException {
		Connection conn = null;
		CapportTransactionDataM capportTranDataM = null;
		try {
		    logger.debug("loadCapportTransaction from capport ID :"+capportId);
		    if(!Util.empty(capportId)){
				conn = getConnection();
				capportTranDataM = selectTableULO_CAP_PORT_TRANSACTION(capportId, conn);
		    }
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
		return capportTranDataM;
	}

	private void insertTableULO_CAP_PORT_TRANSACTION(CapportTransactionDataM capportTranDataM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ULO_CAP_PORT_TRANSACTION( ");
				sql.append("CAP_PORT_ID, CAP_PORT_NAME, APPLICATION_GROUP_ID, APPLICATION_RECORD_ID, ");
				sql.append("GRANT_TYPE, APPROVE_AMOUNT, APPROVE_NUMBER_OF_APP, ADJUST_FLAG, ");
				sql.append("CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");
			sql.append("VALUES(?,?,?,?, ");
				sql.append("?,?,?,?, ");
				sql.append("?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, capportTranDataM.getCapportId());
			ps.setString(cnt++, capportTranDataM.getCapportName());
			ps.setString(cnt++, capportTranDataM.getApplicationGroupId());
			ps.setString(cnt++, capportTranDataM.getApplicationRecordId());
			
			ps.setString(cnt++, capportTranDataM.getGrantType());
			ps.setBigDecimal(cnt++, capportTranDataM.getApproveAmt());
			ps.setBigDecimal(cnt++, capportTranDataM.getApproveApp());
			ps.setString(cnt++, capportTranDataM.getAdjustFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, capportTranDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, capportTranDataM.getUpdateBy());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	private CapportTransactionDataM selectTableULO_CAP_PORT_TRANSACTION(String capportId, Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		CapportTransactionDataM capportTranDataM = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ");
				sql.append("CAP_PORT_ID, CAP_PORT_NAME, APPLICATION_GROUP_ID, APPLICATION_RECORD_ID, ");
				sql.append("GRANT_TYPE, APPROVE_AMOUNT, APPROVE_NUMBER_OF_APP, ADJUST_FLAG, ");
				sql.append("CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append("FROM ULO_CAP_PORT_TRANSACTION ");
			sql.append("WHERE CAP_PORT_ID=?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, capportId);

			rs = ps.executeQuery();
			if (rs.next()) {
				capportTranDataM = new CapportTransactionDataM();
				capportTranDataM.setCapportId(rs.getString("CAP_PORT_ID"));
				capportTranDataM.setCapportName(rs.getString("CAP_PORT_NAME"));
				capportTranDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				capportTranDataM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				
				capportTranDataM.setGrantType(rs.getString("GRANT_TYPE"));
				capportTranDataM.setApproveAmt(rs.getBigDecimal("APPROVE_AMOUNT"));
				capportTranDataM.setApproveApp(rs.getBigDecimal("APPROVE_NUMBER_OF_APP"));
				capportTranDataM.setAdjustFlag(rs.getString("ADJUST_FLAG"));

				capportTranDataM.setCreateBy(rs.getString("CREATE_BY"));
				capportTranDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				capportTranDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				capportTranDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
			}
			return capportTranDataM;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
}
