package com.eaf.inf.batch.ulo.dqe.addresszipcode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.inf.batch.ulo.inf.dao.InfDAOImpl;
import com.eaf.mf.dao.exception.UploadManualFileException;
import com.eaf.orig.shared.constant.BatchConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.util.GenerateUnique;

public class MsZipCodeDAOImpl extends InfBatchObjectDAO implements MsZipCodeDAO{
	private static transient Logger logger = Logger.getLogger(MsZipCodeDAOImpl.class);
	@Override
	public void insertMsZipCode() throws InfBatchException {
		PreparedStatement ps = null;
		PreparedStatement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Connection connCvrs = null;
		String status = SystemConfig.getProperty("ACTIVE_STATUS_Y");
		try {
			StringBuilder sql = new StringBuilder("");
			connCvrs = getConnection(InfBatchServiceLocator.CVRS_DB);
			sql.append(" SELECT * FROM CVRSDQ.DQ_CIS_ZPCD_V ");
			String dSql = String.valueOf(sql);
			pstmt = connCvrs.prepareStatement(dSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt = null;
			pstmt.setFetchSize(10000);
			logger.info("Sql=" + dSql);
			rs = pstmt.executeQuery();
			
			sql = new StringBuilder("");
			sql.append(" INSERT INTO MS_ZIPCODE ");
			sql.append(" ( ZIPCODE_ID, ZIPCODE, PROVINCE, DISTRICT, SUB_DISTRICT, ACTIVE_STATUS) ");
			sql.append(" VALUES(?,?,?,?,?,?) ");
			
			dSql = String.valueOf(sql);
			logger.info("Sql=" + dSql);
			conn = getConnection(InfBatchServiceLocator.ORIG_DB);
			stmt = conn.prepareStatement(dSql);
			conn.setAutoCommit(false);
			while (rs.next()) {
			int cnt = 1;
	//		String ZIP_ID = GenerateUnique.generate(BatchConstant.SeqNames.MS_ZIPCODE_PK,OrigServiceLocator.ORIG_DB);
	//		logger.debug("generate key=" + ZIP_ID);
			stmt.setString(cnt++, rs.getString("DQ_CIS_ZPCD_ID"));
			stmt.setString(cnt++, rs.getString("ZPCD_ZIPCODE"));
			stmt.setString(cnt++, rs.getString("ZPCD_PROV"));
			stmt.setString(cnt++, rs.getString("ZPCD_AMP"));
			stmt.setString(cnt++, rs.getString("ZPCD_TUMBOL"));
			stmt.setString(cnt++, status);
			stmt.addBatch();
			if(rs.getRow()%10000==0){
				stmt.executeBatch();
				conn.commit();
				logger.info("#### BATCH 10000 #### ");
				}		
			}
			stmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			throw new InfBatchException(e.getMessage());
		}finally {
			try {
				closeConnection(conn, rs, ps);
				closeConnection(connCvrs,pstmt);
				closeConnection(stmt);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getMessage());
			}
		}
		
	}

	@Override
	public void deleteMSZipCode() throws InfBatchException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE  MS_ZIPCODE ");
			String sqlQ = String.valueOf(sql);
			ps = conn.prepareStatement(sqlQ);
			ps.executeUpdate();
			logger.debug("Delete MS_ZIPCODE ");
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getMessage());
			}
		}

	}

	@Override
	public void cvrsZipCode() throws InfBatchException {
		PreparedStatement ps = null;
		PreparedStatement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection connCvrs = null;
		ArrayList<String> list = new ArrayList<String>();
		String status = SystemConfig.getProperty("ACTIVE_STATUS_Y");
		try {
			StringBuilder sql = new StringBuilder("");
			connCvrs = getConnection(InfBatchServiceLocator.CVRS_DB);
			sql.append(" SELECT ZPCD_ZIPCODE FROM DQ_CIS_ZPCD_V ");
			String dSql = String.valueOf(sql);
			pstmt = connCvrs.prepareStatement(dSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt = null;
			pstmt.setFetchSize(10000);
			logger.info("Sql=" + dSql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				list.add(rs.getString("ZPCD_ZIPCODE"));
			}
			logger.info("LIST ZIPCODE SIZE >> "+list.size());
			stmt.executeBatch();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			throw new InfBatchException(e.getMessage());
		}finally {
			try {
				closeConnection(connCvrs, rs, pstmt);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getMessage());
			}
		}
		
	}

}
