package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BundleKLDataM;

public class OrigBundleKLDAOImpl extends OrigObjectDAO implements
		OrigBundleKLDAO {
	public OrigBundleKLDAOImpl(){
		
	}
	public OrigBundleKLDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigBundleKLM(BundleKLDataM bundleKLDataM)
			throws ApplicationException {
		logger.debug("bundleKLDataM.getApplicationRecordId() :"+bundleKLDataM.getApplicationRecordId());
		bundleKLDataM.setCreateBy(userId);
		bundleKLDataM.setUpdateBy(userId);
		createTableORIG_BUNDLE_KL(bundleKLDataM);
	}

	private void createTableORIG_BUNDLE_KL(BundleKLDataM bundleKLDataM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_BUNDLE_KL ");
			sql.append("( APPLICATION_RECORD_ID, VERFIED_INCOME, VERFIED_DATE, ");
			sql.append(" ESTIMATED_INCOME, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?, ?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bundleKLDataM.getApplicationRecordId());
			ps.setBigDecimal(cnt++, bundleKLDataM.getVerifiedIncome());
			ps.setDate(cnt++, bundleKLDataM.getVerifiedDate());
			
			ps.setBigDecimal(cnt++, bundleKLDataM.getEstimated_income());
			ps.setString(cnt++, bundleKLDataM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bundleKLDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bundleKLDataM.getUpdateBy());
			
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
	public void deleteOrigBundleKLM(String applicationRecordId)
			throws ApplicationException {
		deleteTableORIG_BUNDLE_KL(applicationRecordId);
	}

	private void deleteTableORIG_BUNDLE_KL(String applicationRecordId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_BUNDLE_KL ");
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
	public BundleKLDataM loadOrigBundleKLM(String applicationRecordId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_BUNDLE_KL(applicationRecordId,conn);
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
	public BundleKLDataM loadOrigBundleKLM(String applicationRecordId,Connection conn) throws ApplicationException {
		return selectTableORIG_BUNDLE_KL(applicationRecordId, conn);
	}
	private BundleKLDataM selectTableORIG_BUNDLE_KL(String applicationRecordId,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		BundleKLDataM bundleKLDataM = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID, VERFIED_INCOME, VERFIED_DATE, ESTIMATED_INCOME, ");
			sql.append(" COMPARE_FLAG, CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM ORIG_BUNDLE_KL  WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();

			if(rs.next()) {
				bundleKLDataM = new BundleKLDataM();
				bundleKLDataM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				bundleKLDataM.setVerifiedIncome(rs.getBigDecimal("VERFIED_INCOME"));
				bundleKLDataM.setVerifiedDate(rs.getDate("VERFIED_DATE"));
				bundleKLDataM.setEstimated_income(rs.getBigDecimal("ESTIMATED_INCOME"));
				
				bundleKLDataM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				bundleKLDataM.setCreateBy(rs.getString("CREATE_BY"));
				bundleKLDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				bundleKLDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				bundleKLDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
			}

			return bundleKLDataM;
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
	public void saveUpdateOrigBundleKLM(BundleKLDataM bundleKLDataM)
			throws ApplicationException {
		int returnRows = 0;
		bundleKLDataM.setUpdateBy(userId);
		returnRows = updateTableORIG_BUNDLE_KL(bundleKLDataM);
		if (returnRows == 0) {
			bundleKLDataM.setCreateBy(userId);
			logger.debug("New record then can't update record in table ORIG_BUNDLE_KL then call Insert method");
			createTableORIG_BUNDLE_KL(bundleKLDataM);
		}
	}

	private int updateTableORIG_BUNDLE_KL(BundleKLDataM bundleKLDataM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_BUNDLE_KL ");
			sql.append(" SET VERFIED_INCOME = ?, VERFIED_DATE = ?, ESTIMATED_INCOME = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setBigDecimal(cnt++, bundleKLDataM.getVerifiedIncome());
			ps.setDate(cnt++, bundleKLDataM.getVerifiedDate());	
			ps.setBigDecimal(cnt++, bundleKLDataM.getEstimated_income());	
			ps.setString(cnt++, bundleKLDataM.getCompareFlag());
			
			ps.setString(cnt++, bundleKLDataM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, bundleKLDataM.getApplicationRecordId());
			
			
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
