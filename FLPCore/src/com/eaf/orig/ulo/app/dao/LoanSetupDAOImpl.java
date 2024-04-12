package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationLogDataM;
import com.eaf.orig.ulo.model.app.LoanSetupDataM;

public class LoanSetupDAOImpl extends OrigObjectDAO implements LoanSetupDAO {
	private String userId = "";

	public LoanSetupDAOImpl() {

	}

	public LoanSetupDAOImpl(String userId) {
		this.userId = userId;
	}

	@Override
	public void createLoanSetup(LoanSetupDataM loanSetupDataM)
			throws ApplicationException {
		Connection conn = null;
		try {
			// Get Primary Key
			String claimId = GenerateUnique.generate(MConstant.LOAN_SETUP_SEQUENCE_NAME.PA_CLAIM_ID_PK);
			loanSetupDataM.setClaimId(claimId);
			logger.debug("loanSetupDataM.getClaimId() :"+ loanSetupDataM.getClaimId());
			
			loanSetupDataM.setCreateBy(userId);
			loanSetupDataM.setUpdateBy(userId);
			conn = getConnection();
			insertTableLOAN_SETUP_CLAIM(loanSetupDataM, conn);
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
	}

	@Override
	public LoanSetupDataM loadLoanSetup(String applicationGroupNo)
			throws ApplicationException {
		Connection conn = null;
		LoanSetupDataM loanSetupDataM = null;
		try {
			logger.debug("loadLoanSetup from applicationGroupNo :"
					+ applicationGroupNo);
			if (!Util.empty(applicationGroupNo)) {
				conn = getConnection();
				loanSetupDataM = selectTableLOAN_SETUP_CLAIMByApplicationGroupNo(
						applicationGroupNo, conn);
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
		return loanSetupDataM;
	}

	private LoanSetupDataM selectTableLOAN_SETUP_CLAIMByApplicationGroupNo(
			String applicationGroupNo, Connection conn)
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		LoanSetupDataM loanSetupDataM = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ");
			sql.append("LOAN_ID, CLAIM_ID, ADDRESS_ID, TYPE, APPLICATION_GROUP_NO, CIS_NO, ");
			sql.append("TH_TITLE_CODE, TH_FIRST_NAME, TH_MID_NAME, TH_LAST_NAME, DE2_SUBMIT_DATE, ");
			sql.append("CLAIM_FLAG, CLAIM_BY, CLAIM_DATE, UN_CLAIM_FLAG_BY, UN_CLAIM_FLAG_DATE, ");
			sql.append("COMPLETE_FLAG, COMPLETE_BY, COMPLETE_DATE, ");
			sql.append("LOAN_SETUP_STATUS, LOAN_ACCOUNT_NO, LOAN_ACCOUNT_STATUS, LOAN_ACCOUNT_OPEN_DATE, ");
			sql.append("CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append("FROM LOAN_SETUP_CLAIM ");
			sql.append("WHERE APPLICATION_GROUP_NO=?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupNo);

			rs = ps.executeQuery();
			if (rs.next()) {
				loanSetupDataM = new LoanSetupDataM();
				loanSetupDataM.setClaimId(rs.getString("CLAIM_ID"));
				loanSetupDataM.setLoanId(rs.getString("LOAN_ID"));
				loanSetupDataM.setAddressId(rs.getString("ADDRESS_ID"));
				loanSetupDataM.setType(rs.getString("TYPE"));
				loanSetupDataM.setApplicationGroupNo(rs
						.getString("APPLICATION_GROUP_NO"));
				loanSetupDataM.setCisNo(rs.getString("CIS_NO"));

				loanSetupDataM.setThTitle(rs.getString("TH_TITLE_CODE"));
				loanSetupDataM.setThName(rs.getString("TH_FIRST_NAME"));
				loanSetupDataM.setThMidName(rs.getString("TH_MID_NAME"));
				loanSetupDataM.setThSurname(rs.getString("TH_LAST_NAME"));
				loanSetupDataM
						.setSubmitDate(rs.getTimestamp("DE2_SUBMIT_DATE"));

				loanSetupDataM.setClaimFlag(rs.getString("CLAIM_FLAG"));
				loanSetupDataM.setClaimBy(rs.getString("CLAIM_BY"));
				loanSetupDataM.setClaimDate(rs.getTimestamp("CLAIM_DATE"));
				loanSetupDataM.setUnclaimBy(rs.getString("UN_CLAIM_FLAG_BY"));
				loanSetupDataM.setUnclaimDate(rs
						.getTimestamp("UN_CLAIM_FLAG_DATE"));

				loanSetupDataM.setCompleteFlag(rs.getString("COMPLETE_FLAG"));
				loanSetupDataM.setCompleteBy(rs.getString("COMPLETE_BY"));
				loanSetupDataM
						.setCompleteDate(rs.getTimestamp("COMPLETE_DATE"));

				loanSetupDataM.setStatus(rs.getString("LOAN_SETUP_STATUS"));
				loanSetupDataM.setAccountNo(rs.getString("LOAN_ACCOUNT_NO"));
				loanSetupDataM.setAccountStatus(rs
						.getString("LOAN_ACCOUNT_STATUS"));
				loanSetupDataM.setAccountOpenDate(rs
						.getTimestamp("LOAN_ACCOUNT_OPEN_DATE"));

				loanSetupDataM.setCreateBy(rs.getString("CREATE_BY"));
				loanSetupDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				loanSetupDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				loanSetupDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
			}
			return loanSetupDataM;
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

	private void insertTableLOAN_SETUP_CLAIM(LoanSetupDataM loanSetupDataM,
			Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO LOAN_SETUP_CLAIM( ");
			sql.append("CLAIM_ID, LOAN_ID, ADDRESS_ID, TYPE, APPLICATION_GROUP_NO, CIS_NO, ");
			sql.append("TH_TITLE_CODE, TH_FIRST_NAME, TH_MID_NAME, TH_LAST_NAME, DE2_SUBMIT_DATE, ");
			sql.append("CLAIM_FLAG, CLAIM_BY, CLAIM_DATE, UN_CLAIM_FLAG_BY, UN_CLAIM_FLAG_DATE, ");
			sql.append("COMPLETE_FLAG, COMPLETE_BY, COMPLETE_DATE, ");
			sql.append("LOAN_SETUP_STATUS, LOAN_ACCOUNT_NO, LOAN_ACCOUNT_STATUS, LOAN_ACCOUNT_OPEN_DATE, ");
			sql.append("CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");
			sql.append("VALUES(?,?,?,?,?,?, ");
			sql.append("?,?,?,?,?, ");
			sql.append("?,?,?,?,?, ");
			sql.append("?,?,?, ");
			sql.append("?,?,?,?, ");
			sql.append("?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, loanSetupDataM.getClaimId());
			ps.setString(cnt++, loanSetupDataM.getLoanId());
			ps.setString(cnt++, loanSetupDataM.getAddressId());
			ps.setString(cnt++, loanSetupDataM.getType());
			ps.setString(cnt++, loanSetupDataM.getApplicationGroupNo());
			ps.setString(cnt++, loanSetupDataM.getCisNo());

			ps.setString(cnt++, loanSetupDataM.getThTitle());
			ps.setString(cnt++, loanSetupDataM.getThName());
			ps.setString(cnt++, loanSetupDataM.getThMidName());
			ps.setString(cnt++, loanSetupDataM.getThSurname());
			ps.setTimestamp(cnt++, loanSetupDataM.getSubmitDate());

			ps.setString(cnt++, loanSetupDataM.getClaimFlag());
			ps.setString(cnt++, loanSetupDataM.getClaimBy());
			ps.setTimestamp(cnt++, loanSetupDataM.getClaimDate());
			ps.setString(cnt++, loanSetupDataM.getUnclaimBy());
			ps.setTimestamp(cnt++, loanSetupDataM.getUnclaimDate());

			ps.setString(cnt++, loanSetupDataM.getCompleteFlag());
			ps.setString(cnt++, loanSetupDataM.getCompleteBy());
			ps.setTimestamp(cnt++, loanSetupDataM.getCompleteDate());

			ps.setString(cnt++, loanSetupDataM.getStatus());
			ps.setString(cnt++, loanSetupDataM.getAccountNo());
			ps.setString(cnt++, loanSetupDataM.getAccountStatus());
			ps.setTimestamp(cnt++, loanSetupDataM.getAccountOpenDate());

			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, loanSetupDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, loanSetupDataM.getUpdateBy());

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

	@Override
	public void changeInfoAfterSetupLoan(String applicationGroupNo, String accountNo, Timestamp openDate, String userId, Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE LOAN_SETUP_CLAIM ");
			sql.append("SET ");
				sql.append(" LOAN_SETUP_STATUS = CASE WHEN TYPE = 'S' THEN 'Completed' ELSE 'Not Claim' END ");
				sql.append(" ,UPDATE_DATE = SYSDATE ");
				sql.append(" ,UPDATE_BY = ? ");
				sql.append(" ,LOAN_ACCOUNT_NO = ? ");
				sql.append(" ,LOAN_ACCOUNT_OPEN_DATE = ? ");
				sql.append(" ,LOAN_ACCOUNT_STATUS = ? ");
			sql.append(" WHERE APPLICATION_GROUP_NO = ? " );
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, userId);
			ps.setString(cnt++, accountNo);
			ps.setTimestamp(cnt++, openDate);
			ps.setString(cnt++, (Util.empty(accountNo)) ? "" : MConstant.FLAG.ACTIVE);
			ps.setString(cnt++, applicationGroupNo);
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if (null != ps)
					ps.close();
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
}
