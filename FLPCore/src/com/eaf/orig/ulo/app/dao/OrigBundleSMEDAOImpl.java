package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

public class OrigBundleSMEDAOImpl extends OrigObjectDAO implements
		OrigBundleSMEDAO {
	public OrigBundleSMEDAOImpl(){
		
	}
	public OrigBundleSMEDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigBundleSMEM(BundleSMEDataM bundleSME) throws ApplicationException {
		logger.debug("bundleSMEDataM.getApplicationRecordId() :"+bundleSME.getApplicationRecordId());
		bundleSME.setCreateBy(userId);
		bundleSME.setUpdateBy(userId);
		createTableORIG_BUNDLE_SME(bundleSME);
	}

	private void createTableORIG_BUNDLE_SME(BundleSMEDataM bundleSME) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_BUNDLE_SME ");
			sql.append("( APPLICATION_RECORD_ID, APPLICANT_QUALITY, APPROVAL_LIMIT, ");
			sql.append(" BUSINESS_OWNER_FLAG, CORPORATE_RATIO, G_DSCR_REQ, G_TOTAL_EXIST_PAYMENT, ");
			sql.append(" G_TOTAL_NEWPAY_REQ, INDIVIDUAL_RATIO, BORROWING_TYPE, BUSINESS_CODE, ");
			sql.append(" SME_APPROVE_DATE, COMPARE_FLAG,");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY , ACCOUNT_TYPE, ACCOUNT_NO, ACCOUNT_NAME, ACCOUNT_OPEN_DATE, ");
			sql.append(" VERIFIED_INCOME ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,  ?,?,?,? ,?,?,?,?, ?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bundleSME.getApplicationRecordId());
			ps.setString(cnt++, bundleSME.getApplicantQuality());
			ps.setBigDecimal(cnt++, bundleSME.getApprovalLimit());
			
			logger.debug("Date Arrpover"+bundleSME.getApprovalDate());
			logger.debug("Date Acount Date"+bundleSME.getAccountDate());
			
			ps.setString(cnt++, bundleSME.getBusOwnerFlag());
			ps.setBigDecimal(cnt++, bundleSME.getCorporateRatio());
			ps.setBigDecimal(cnt++, bundleSME.getgDscrReq());
			ps.setBigDecimal(cnt++, bundleSME.getgTotExistPayment());
			
			ps.setBigDecimal(cnt++, bundleSME.getgTotNewPayReq());
			ps.setBigDecimal(cnt++, bundleSME.getIndividualRatio());
			ps.setString(cnt++, bundleSME.getBorrowingType());
			ps.setString(cnt++, bundleSME.getBusinessCode());
			
			ps.setDate(cnt++, bundleSME.getApprovalDate());
			ps.setString(cnt++, bundleSME.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bundleSME.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bundleSME.getUpdateBy());
			
			ps.setString(cnt++,bundleSME.getTypeLimit());
			ps.setString(cnt++, bundleSME.getAccountNo());
			ps.setString(cnt++, bundleSME.getAccountName());
			ps.setDate(cnt++, bundleSME.getAccountDate());
			ps.setBigDecimal(cnt++, bundleSME.getVerifiedIncome());
			
			
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
	public void deleteOrigBundleSMEM(String applicationRecordId)
			throws ApplicationException {
		deleteTableORIG_BUNDLE_SME(applicationRecordId);
	}

	private void deleteTableORIG_BUNDLE_SME(String applicationRecordId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_BUNDLE_SME ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
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
	public BundleSMEDataM loadOrigBundleSMEM(String applicationRecordId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_BUNDLE_SME(applicationRecordId,conn);
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
	public BundleSMEDataM loadOrigBundleSMEM(String applicationRecordId,
			Connection conn) throws ApplicationException {
		return selectTableORIG_BUNDLE_SME(applicationRecordId, conn);
	}
	private BundleSMEDataM selectTableORIG_BUNDLE_SME(String applicationRecordId,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		BundleSMEDataM bundleSME = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID, APPLICANT_QUALITY, APPROVAL_LIMIT, ");
			sql.append(" BUSINESS_OWNER_FLAG, CORPORATE_RATIO, G_DSCR_REQ, G_TOTAL_EXIST_PAYMENT, ");
			sql.append(" G_TOTAL_NEWPAY_REQ, INDIVIDUAL_RATIO, BORROWING_TYPE, BUSINESS_CODE, ");
			sql.append(" SME_APPROVE_DATE,COMPARE_FLAG,CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ,ACCOUNT_NAME,ACCOUNT_OPEN_DATE,ACCOUNT_NO,ACCOUNT_TYPE, ");
			sql.append(" VERIFIED_INCOME ");
			sql.append(" FROM ORIG_BUNDLE_SME  WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
//			,ACCOUNT_NAME,ACCOUNT_OPEN_DATE,ACCOUNT_NO,ACCOUNT_TYPE
			if(rs.next()) {
				bundleSME = new BundleSMEDataM();
				bundleSME.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				bundleSME.setApplicantQuality(rs.getString("APPLICANT_QUALITY"));
				bundleSME.setApprovalLimit(rs.getBigDecimal("APPROVAL_LIMIT"));
				
				bundleSME.setBusOwnerFlag(rs.getString("BUSINESS_OWNER_FLAG"));
				bundleSME.setCorporateRatio(rs.getBigDecimal("CORPORATE_RATIO"));
				bundleSME.setgDscrReq(rs.getBigDecimal("G_DSCR_REQ"));
				bundleSME.setgTotExistPayment(rs.getBigDecimal("G_TOTAL_EXIST_PAYMENT"));
				
				bundleSME.setgTotNewPayReq(rs.getBigDecimal("G_TOTAL_NEWPAY_REQ"));
				bundleSME.setIndividualRatio(rs.getBigDecimal("INDIVIDUAL_RATIO"));
				bundleSME.setBorrowingType(rs.getString("BORROWING_TYPE"));
				bundleSME.setBusinessCode(rs.getString("BUSINESS_CODE"));
				
				bundleSME.setApprovalDate(rs.getDate("SME_APPROVE_DATE"));
				bundleSME.setCompareFlag(rs.getString("COMPARE_FLAG"));
				bundleSME.setCreateBy(rs.getString("CREATE_BY"));
				bundleSME.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				bundleSME.setUpdateBy(rs.getString("UPDATE_BY"));
				bundleSME.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				bundleSME.setAccountName(rs.getString("ACCOUNT_NAME"));
				bundleSME.setAccountDate(rs.getDate("ACCOUNT_OPEN_DATE"));
				bundleSME.setAccountNo(rs.getString("ACCOUNT_NO"));
				bundleSME.setTypeLimit(rs.getString("ACCOUNT_TYPE"));
				
				bundleSME.setVerifiedIncome(rs.getBigDecimal("VERIFIED_INCOME"));
			}

			return bundleSME;
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
	public void saveUpdateOrigBundleSMEM(BundleSMEDataM bundleSME)
			throws ApplicationException {
		int returnRows = 0;
		bundleSME.setUpdateBy(userId);
		returnRows = updateTableORIG_BUNDLE_SME(bundleSME);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_BUNDLE_SME then call Insert method");
			bundleSME.setCreateBy(userId);
			createTableORIG_BUNDLE_SME(bundleSME);
		}
	}

	private int updateTableORIG_BUNDLE_SME(BundleSMEDataM bundleSME) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_BUNDLE_SME ");
			sql.append(" SET APPLICANT_QUALITY = ?, APPROVAL_LIMIT = ?, BUSINESS_OWNER_FLAG = ?, ");
			sql.append(" CORPORATE_RATIO = ?, G_DSCR_REQ = ?, G_TOTAL_EXIST_PAYMENT = ?, ");
			sql.append(" G_TOTAL_NEWPAY_REQ = ?, INDIVIDUAL_RATIO = ?, BORROWING_TYPE = ?, ");
			sql.append(" BUSINESS_CODE = ?, SME_APPROVE_DATE = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ,ACCOUNT_TYPE = ?,ACCOUNT_NO = ? ,ACCOUNT_NAME = ?,ACCOUNT_OPEN_DATE = ?, ");
			sql.append(" VERIFIED_INCOME = ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bundleSME.getApplicantQuality());
			ps.setBigDecimal(cnt++, bundleSME.getApprovalLimit());
			ps.setString(cnt++, bundleSME.getBusOwnerFlag());
			
			ps.setBigDecimal(cnt++, bundleSME.getCorporateRatio());
			ps.setBigDecimal(cnt++, bundleSME.getgDscrReq());
			ps.setBigDecimal(cnt++, bundleSME.getgTotExistPayment());
			
			ps.setBigDecimal(cnt++, bundleSME.getgTotNewPayReq());
			ps.setBigDecimal(cnt++, bundleSME.getIndividualRatio());
			ps.setString(cnt++, bundleSME.getBorrowingType());
			
			ps.setString(cnt++, bundleSME.getBusinessCode());
			ps.setDate(cnt++, bundleSME.getApprovalDate());
			ps.setString(cnt++, bundleSME.getCompareFlag());
			
			ps.setString(cnt++, bundleSME.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setString(cnt++,bundleSME.getTypeLimit());
			ps.setString(cnt++, bundleSME.getAccountNo());
			ps.setString(cnt++, bundleSME.getAccountName());
			ps.setDate(cnt++, bundleSME.getAccountDate());
			
			ps.setBigDecimal(cnt++, bundleSME.getVerifiedIncome());
			// WHERE CLAUSE
			ps.setString(cnt++, bundleSME.getApplicationRecordId());
						
			
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
