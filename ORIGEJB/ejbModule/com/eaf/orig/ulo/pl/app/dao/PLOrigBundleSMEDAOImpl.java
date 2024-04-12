package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBundleSMEDataM;

public class PLOrigBundleSMEDAOImpl extends OrigObjectDAO implements PLOrigBundleSMEDAO {
	
	private Logger log = Logger.getLogger(PLOrigBundleSMEDAOImpl.class);

	@Override
	public void saveUpdateBundleSME(PLBundleSMEDataM bundleSMEM, String appRecId) throws PLOrigApplicationException{
		try{		
			if(bundleSMEM!=null){
				int returnRow = this.updateTable_Oirg_Bundle_SMS(bundleSMEM, appRecId);
				if(returnRow == 0){
					this.insertTable_Oirg_Bundle_SMS(bundleSMEM, appRecId);
				}		
			}
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private int updateTable_Oirg_Bundle_SMS(PLBundleSMEDataM bundleSMEM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_BUNDLE_SME SET SME_APPROVE_DATE=?, SME_APPLICATION_NO=?, GROSS_INCOME=?, RISK_GRADE=?, TOTAL_SME_LIMIT=? ");
			sql.append(", APPROVE_STATUS=?, APPROVE_CREDIT_LINE=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?, CREATE_BY=?, APPLICATION_RECORD_ID=? ");
			sql.append("WHERE APPLICATION_RECORD_ID = ?");
			
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setDate(index++, this.parseDate(bundleSMEM.getSmeApproveDate()));
			ps.setString(index++, bundleSMEM.getSmeAppNo());
			ps.setBigDecimal(index++, bundleSMEM.getGrossIncome());
			ps.setString(index++, bundleSMEM.getRiskGrade());
			ps.setBigDecimal(index++, bundleSMEM.getTotalSMELimit());
			
			ps.setString(index++, bundleSMEM.getApproveStatus());
			ps.setBigDecimal(index++, bundleSMEM.getApproveCreditLine());
			ps.setString(index++, bundleSMEM.getUpdateBy());
			ps.setString(index++, bundleSMEM.getCreateBy());//Add for Create Date
			ps.setString(index++, appRecId);
			
			ps.setString(index++, appRecId);
			
			return ps.executeUpdate();
			
			
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void insertTable_Oirg_Bundle_SMS(PLBundleSMEDataM bundleSMEM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_BUNDLE_SME ");
			sql.append("( SME_APPROVE_DATE, SME_APPLICATION_NO, GROSS_INCOME, RISK_GRADE, TOTAL_SME_LIMIT ");
			sql.append(", APPROVE_STATUS, APPROVE_CREDIT_LINE, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, APPLICATION_RECORD_ID ) ");
			sql.append("VALUES (?,?,?,?,?   ,?,?,SYSDATE,?,SYSDATE  ,?,?) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setDate(index++, this.parseDate(bundleSMEM.getSmeApproveDate()));
			ps.setString(index++, bundleSMEM.getSmeAppNo());
			ps.setBigDecimal(index++, bundleSMEM.getGrossIncome());
			ps.setString(index++, bundleSMEM.getRiskGrade());
			ps.setBigDecimal(index++, bundleSMEM.getTotalSMELimit());
			
			ps.setString(index++, bundleSMEM.getApproveStatus());
			ps.setBigDecimal(index++, bundleSMEM.getApproveCreditLine());
			ps.setString(index++, bundleSMEM.getCreateBy());
			
			ps.setString(index++, bundleSMEM.getUpdateBy());
			ps.setString(index++, appRecId);
			
			ps.executeUpdate();
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
			
	}

	@Override
	public PLBundleSMEDataM loadBundleSME(String appRecId) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLBundleSMEDataM bundleSMEM = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SME_APPROVE_DATE, SME_APPLICATION_NO, GROSS_INCOME, RISK_GRADE, TOTAL_SME_LIMIT ");
			sql.append(", APPROVE_STATUS, APPROVE_CREDIT_LINE, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, APPLICATION_RECORD_ID ");
			sql.append("FROM ORIG_BUNDLE_SME WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);
			rs = ps.executeQuery();
			
			
			if(rs.next()){	
				bundleSMEM = new PLBundleSMEDataM();
				int index = 1;				
				bundleSMEM.setSmeApproveDate(rs.getDate(index++));
				bundleSMEM.setSmeAppNo(rs.getString(index++));
				bundleSMEM.setGrossIncome(rs.getBigDecimal(index++));
				bundleSMEM.setRiskGrade(rs.getString(index++));
				bundleSMEM.setTotalSMELimit(rs.getBigDecimal(index++));
				bundleSMEM.setApproveStatus(rs.getString(index++));
				bundleSMEM.setApproveCreditLine(rs.getBigDecimal(index++));
				bundleSMEM.setCreateDate(rs.getTimestamp(index++));
				bundleSMEM.setCreateBy(rs.getString(index++));
				bundleSMEM.setUpdateDate(rs.getTimestamp(index++));
				
				bundleSMEM.setUpdateBy(rs.getString(index++));
				bundleSMEM.setAppRecId(rs.getString(index++));				
			}					
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return bundleSMEM;	
	}

}
