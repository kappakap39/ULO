package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BundleHLDataM;

public class OrigBundleHLDAOImpl extends OrigObjectDAO implements OrigBundleHLDAO {
	public OrigBundleHLDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigBundleHLDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigBundleHLM(BundleHLDataM bundleHLDataM) throws ApplicationException {
		logger.debug("bundleHLDataM.getApplicationRecordId() :"+bundleHLDataM.getApplicationRecordId());
		bundleHLDataM.setUpdateBy(userId);
		bundleHLDataM.setCreateBy(userId);
		createTableORIG_BUNDLE_HL(bundleHLDataM);
	}

	private void createTableORIG_BUNDLE_HL(BundleHLDataM bundleHLDataM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_BUNDLE_HL ");
			sql.append("( APPLICATION_RECORD_ID, APPROVE_CREDIT_LINE, VERIFIED_INCOME, ");
			sql.append(" APPROVED_DATE, CC_APPROVED_AMT, KEC_APPROVED_AMT, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY, ");
			sql.append(" MORTGAGE_DATE,ACCOUNT_NO,ACCOUNT_NAME,OPEN_DATE ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,  ?,?,?,?, ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bundleHLDataM.getApplicationRecordId());
			ps.setBigDecimal(cnt++, bundleHLDataM.getApproveCreditLine());
			ps.setBigDecimal(cnt++, bundleHLDataM.getVerifiedIncome());
			
			ps.setDate(cnt++, bundleHLDataM.getApprovedDate());
			ps.setBigDecimal(cnt++, bundleHLDataM.getCcApprovedAmt());
			ps.setBigDecimal(cnt++, bundleHLDataM.getKecApprovedAmt());
			ps.setString(cnt++, bundleHLDataM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bundleHLDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bundleHLDataM.getUpdateBy());
			
			ps.setDate(cnt++, bundleHLDataM.getMortGageDate());
			ps.setString(cnt++, bundleHLDataM.getAccountNo());
			ps.setString(cnt++, bundleHLDataM.getAccountName());
			ps.setDate(cnt++, bundleHLDataM.getAccountOpenDate());
			
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
	public void deleteOrigBundleHLM(String applicationRecordId)
			throws ApplicationException {
		deleteTableORIG_BUNDLE_HL(applicationRecordId);
	}

	private void deleteTableORIG_BUNDLE_HL(String applicationRecordId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_BUNDLE_HL ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationRecordId);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public BundleHLDataM loadOrigBundleHLM(String applicationRecordId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_BUNDLE_HL(applicationRecordId,conn);
		}catch (Exception e) {
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
	public BundleHLDataM loadOrigBundleHLM(String applicationRecordId,Connection conn)
			throws ApplicationException {
		return selectTableORIG_BUNDLE_HL(applicationRecordId,conn);
	}
	private BundleHLDataM selectTableORIG_BUNDLE_HL(String applicationRecordId,Connection conn) 
									throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		BundleHLDataM bundleHLDataM = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID, APPROVE_CREDIT_LINE, VERIFIED_INCOME, ");
			sql.append(" APPROVED_DATE, CC_APPROVED_AMT, KEC_APPROVED_AMT, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY, ");
			sql.append(" MORTGAGE_DATE,ACCOUNT_NO,ACCOUNT_NAME,OPEN_DATE ");
			sql.append(" FROM ORIG_BUNDLE_HL  WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();

			if(rs.next()) {
				bundleHLDataM = new BundleHLDataM();
				bundleHLDataM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				bundleHLDataM.setApproveCreditLine(rs.getBigDecimal("APPROVE_CREDIT_LINE"));
				bundleHLDataM.setVerifiedIncome(rs.getBigDecimal("VERIFIED_INCOME"));
				
				bundleHLDataM.setApprovedDate(rs.getDate("APPROVED_DATE"));
				bundleHLDataM.setCcApprovedAmt(rs.getBigDecimal("CC_APPROVED_AMT"));
				bundleHLDataM.setKecApprovedAmt(rs.getBigDecimal("KEC_APPROVED_AMT"));
				bundleHLDataM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				bundleHLDataM.setCreateBy(rs.getString("CREATE_BY"));
				bundleHLDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				bundleHLDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				bundleHLDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				bundleHLDataM.setMortGageDate(rs.getDate("MORTGAGE_DATE"));
				bundleHLDataM.setAccountNo(rs.getString("ACCOUNT_NO"));
				bundleHLDataM.setAccountName(rs.getString("ACCOUNT_NAME"));
				bundleHLDataM.setAccountOpenDate(rs.getDate("OPEN_DATE"));
			}

			return bundleHLDataM;
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

	@Override
	public void saveUpdateOrigBundleHLM(BundleHLDataM bundleHLDataM)
			throws ApplicationException {
		int returnRows = 0;
		bundleHLDataM.setUpdateBy(userId);
		returnRows = updateTableORIG_BUNDLE_HL(bundleHLDataM);
		if (returnRows == 0) {
			bundleHLDataM.setCreateBy(userId);
			logger.debug("New record then can't update record in table ORIG_BUNDLE_HL then call Insert method");
			createTableORIG_BUNDLE_HL(bundleHLDataM);
		}
	}

	private int updateTableORIG_BUNDLE_HL(BundleHLDataM bundleHLDataM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_BUNDLE_HL ");
			sql.append(" SET APPROVE_CREDIT_LINE = ?, VERIFIED_INCOME = ?, APPROVED_DATE = ?, ");
			sql.append(" CC_APPROVED_AMT = ?, KEC_APPROVED_AMT = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ?, ");
			sql.append(" MORTGAGE_DATE = ?, ACCOUNT_NO = ?, ACCOUNT_NAME = ?, OPEN_DATE = ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setBigDecimal(cnt++, bundleHLDataM.getApproveCreditLine());			
			ps.setBigDecimal(cnt++, bundleHLDataM.getVerifiedIncome());			
			ps.setDate(cnt++, bundleHLDataM.getApprovedDate());			
			
			ps.setBigDecimal(cnt++, bundleHLDataM.getCcApprovedAmt());			
			ps.setBigDecimal(cnt++, bundleHLDataM.getKecApprovedAmt());			
			ps.setString(cnt++, bundleHLDataM.getCompareFlag());
			
			ps.setString(cnt++, bundleHLDataM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setDate(cnt++, bundleHLDataM.getMortGageDate());	
			ps.setString(cnt++, bundleHLDataM.getAccountNo());
			ps.setString(cnt++, bundleHLDataM.getAccountName());
			ps.setDate(cnt++, bundleHLDataM.getAccountOpenDate());
			// WHERE CLAUSE
			ps.setString(cnt++, bundleHLDataM.getApplicationRecordId());
			
			
			returnRows = ps.executeUpdate();
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
		return returnRows;
	}

}
