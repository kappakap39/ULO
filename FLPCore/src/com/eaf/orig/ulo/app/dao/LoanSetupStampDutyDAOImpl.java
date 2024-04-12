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
import com.eaf.orig.ulo.model.app.LoanSetupDataM;
import com.eaf.orig.ulo.model.app.LoanSetupStampDutyDataM;

public class LoanSetupStampDutyDAOImpl extends OrigObjectDAO implements LoanSetupStampDutyDAO {
	private String userId = "";

	public LoanSetupStampDutyDAOImpl(){
		
	}
	public LoanSetupStampDutyDAOImpl(String userId){
		this.userId = userId;
	}
	
	@Override
	public void createLoanSetupStampDuty(LoanSetupStampDutyDataM loanSetupStampDutyDataM) throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("loanSetupStampDutyDataM.getStampDutyId() :"+loanSetupStampDutyDataM.getStampDutyId());
		    if(Util.empty(loanSetupStampDutyDataM.getStampDutyId())){
				String stampDutyId = GenerateUnique.generate(MConstant.LOAN_SETUP_SEQUENCE_NAME.PA_STAMP_DUTY_ID_PK);
				loanSetupStampDutyDataM.setStampDutyId(stampDutyId);
//				loanSetupStampDutyDataM.setCreateBy(userId);
		    }
//		    loanSetupStampDutyDataM.setUpdateBy(userId);
		    insertTableLOAN_SETUP_STAMP_DUTY(loanSetupStampDutyDataM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	
	@Override
	public LoanSetupStampDutyDataM loadLoanSetupStampDuty(String claimId) throws ApplicationException {
		Connection conn = null;
		LoanSetupStampDutyDataM loanSetupStampDutyDataM = null;
		try {
		    logger.debug("loadLoanSetupStampDuty from claim ID :"+claimId);
		    if(!Util.empty(claimId)){
				conn = getConnection();
			    loanSetupStampDutyDataM = selectTableLOAN_SETUP_STAMP_DUTY(claimId, conn);
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
		return loanSetupStampDutyDataM;
	}

	private void insertTableLOAN_SETUP_STAMP_DUTY(LoanSetupStampDutyDataM loanSetupStampDutyDataM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO LOAN_SETUP_STAMP_DUTY( ");
				sql.append("CLAIM_ID, LOAN_ID, STAMP_DUTY_ID, ");
				sql.append("PERIOD_NO, PERIOD_DATE_FROM, PERIOD_DATE_TO, PERIOD_MONTH, PERIOD_YEAR, ");
				sql.append("STAMP_DUTY_FEE, FINAL_LOAN_AMT, REQUESTOR_NAME, REQUESTOR_POSITION) ");
			sql.append("VALUES(?,?,?, ");
				sql.append("?,?,?,?,?, ");
				sql.append("?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, loanSetupStampDutyDataM.getClaimId());
			ps.setString(cnt++, loanSetupStampDutyDataM.getLoanId());
			ps.setString(cnt++, loanSetupStampDutyDataM.getStampDutyId());
			ps.setBigDecimal(cnt++, loanSetupStampDutyDataM.getPeriodNo());
			ps.setBigDecimal(cnt++, loanSetupStampDutyDataM.getPeriodDateFrom());
			ps.setBigDecimal(cnt++, loanSetupStampDutyDataM.getPeriodDateTo());
			ps.setBigDecimal(cnt++, loanSetupStampDutyDataM.getPeriodMonth());
			ps.setBigDecimal(cnt++, loanSetupStampDutyDataM.getPeriodYear());
			
			ps.setBigDecimal(cnt++, loanSetupStampDutyDataM.getStampDutyFee());
			ps.setBigDecimal(cnt++, loanSetupStampDutyDataM.getFinalLoanAmt());
			ps.setString(cnt++, loanSetupStampDutyDataM.getRequesterName());
			ps.setString(cnt++, loanSetupStampDutyDataM.getRequesterPosition());
			
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
	
	private LoanSetupStampDutyDataM selectTableLOAN_SETUP_STAMP_DUTY(String claimId, Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		LoanSetupStampDutyDataM loanSetupStampDutyDataM = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ");
				sql.append("LOAN_ID, CLAIM_ID, STAMP_DUTY_ID, ");
				sql.append("PERIOD_NO, PERIOD_DATE_FROM, PERIOD_DATE_TO, PERIOD_MONTH, PERIOD_YEAR, ");
				sql.append("STAMP_DUTY_FEE, FINAL_LOAN_AMT, REQUESTOR_NAME, REQUESTOR_POSITION ");
			sql.append("FROM LOAN_SETUP_STAMP_DUTY ");
			sql.append("WHERE CLAIM_ID=?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, claimId);

			rs = ps.executeQuery();
			if (rs.next()) {
				loanSetupStampDutyDataM = new LoanSetupStampDutyDataM();
				loanSetupStampDutyDataM.setClaimId(rs.getString("CLAIM_ID"));
				loanSetupStampDutyDataM.setLoanId(rs.getString("LOAN_ID"));
				loanSetupStampDutyDataM.setStampDutyId(rs.getString("STAMP_DUTY_ID"));
				loanSetupStampDutyDataM.setPeriodNo(rs.getBigDecimal("PERIOD_NO"));
				loanSetupStampDutyDataM.setPeriodDateFrom(rs.getBigDecimal("PERIOD_DATE_FROM"));
				loanSetupStampDutyDataM.setPeriodDateTo(rs.getBigDecimal("PERIOD_DATE_TO"));
				loanSetupStampDutyDataM.setPeriodMonth(rs.getBigDecimal("PERIOD_MONTH"));
				loanSetupStampDutyDataM.setPeriodYear(rs.getBigDecimal("PERIOD_YEAR"));
				
				loanSetupStampDutyDataM.setStampDutyFee(rs.getBigDecimal("STAMP_DUTY_FEE"));
				loanSetupStampDutyDataM.setFinalLoanAmt(rs.getBigDecimal("FINAL_LOAN_AMT"));
				loanSetupStampDutyDataM.setRequesterName(rs.getString("REQUESTOR_NAME"));
				loanSetupStampDutyDataM.setRequesterPosition(rs.getString("REQUESTOR_POSITION"));
			}
			return loanSetupStampDutyDataM;
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
