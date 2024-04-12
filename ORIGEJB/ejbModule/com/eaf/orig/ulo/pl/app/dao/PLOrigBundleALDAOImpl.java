package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBundleALDataM;

public class PLOrigBundleALDAOImpl extends OrigObjectDAO implements PLOrigBundleALDAO {

	private Logger log = Logger.getLogger(PLOrigBundleALDAOImpl.class);
	
	@Override
	public void saveUpdateBundleAL(PLBundleALDataM bundleAlM, String appRecId) throws PLOrigApplicationException{
		try{
			if(bundleAlM!=null){				
				int returnRow = this.updateTable_Oirg_Bundle_AL(bundleAlM, appRecId);
				if(returnRow == 0){
					this.insertTable_Oirg_Bundle_AL(bundleAlM, appRecId);
				}	
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private int updateTable_Oirg_Bundle_AL(PLBundleALDataM bundleAlM, String appRecId) throws PLOrigApplicationException{		
		PreparedStatement ps = null;		
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_BUNDLE_AL SET APPLICATION_RECORD_ID=?, CAR_TYPE=?, CAR_MODEL=?, CONTRACT_NO=?, APPLICANT_SALARY=? ");
			sql.append(", ESTIMATION_SALARY=?, FICO_SCORE=?, DEBT_BURDEN_SCORE=?, DOWN_PAYMENT=?, HP_AMOUNT=? ");
			sql.append(", MONTHLY_INSTALLMENT=?, APPROVE_DATE=?, CONTRACT_CREATE_BY=?, APPROVE_CREDIT_LINE=?, CREDIT_APPROVE_BY=? ");
			sql.append(", UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			sql.append("WHERE APPLICATION_RECORD_ID = ?");
			
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, appRecId);
			ps.setString(index++, bundleAlM.getCarType());
			ps.setString(index++, bundleAlM.getCarModel());
			ps.setString(index++, bundleAlM.getContractNo());
			ps.setBigDecimal(index++, bundleAlM.getAppSalary());
			
			ps.setBigDecimal(index++, bundleAlM.getEstSalary());
			ps.setString(index++, bundleAlM.getFicoScore());
			ps.setString(index++, bundleAlM.getDebtBurdenScore());
			ps.setBigDecimal(index++, bundleAlM.getDownPayment());
			ps.setBigDecimal(index++, bundleAlM.getHpAmount());
			
			ps.setBigDecimal(index++, bundleAlM.getMonthlyInstallment());
			ps.setDate(index++, this.parseDate(bundleAlM.getApproveDate()));
			ps.setString(index++, bundleAlM.getContractCreateBy());
			ps.setBigDecimal(index++, bundleAlM.getApproveCreditLine());
			ps.setString(index++, bundleAlM.getCreditApproveBy());
			
			ps.setString(index++, bundleAlM.getUpdateBy());
			
			ps.setString(index++, appRecId);
			
			return ps.executeUpdate();			
			
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void insertTable_Oirg_Bundle_AL(PLBundleALDataM bundleAlM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_BUNDLE_AL ");
			sql.append("( APPLICATION_RECORD_ID, CAR_TYPE, CAR_MODEL, CONTRACT_NO, APPLICANT_SALARY ");
			sql.append(", ESTIMATION_SALARY, FICO_SCORE, DEBT_BURDEN_SCORE, DOWN_PAYMENT, HP_AMOUNT ");
			sql.append(", MONTHLY_INSTALLMENT, APPROVE_DATE, CONTRACT_CREATE_BY, APPROVE_CREDIT_LINE, CREDIT_APPROVE_BY ");
			sql.append(", CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");
			sql.append("VALUES (?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,SYSDATE,?,SYSDATE,? ) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, appRecId);
			ps.setString(index++, bundleAlM.getCarType());
			ps.setString(index++, bundleAlM.getCarModel());
			ps.setString(index++, bundleAlM.getContractNo());
			ps.setBigDecimal(index++, bundleAlM.getAppSalary());
			
			ps.setBigDecimal(index++, bundleAlM.getEstSalary());
			ps.setString(index++, bundleAlM.getFicoScore());
			ps.setString(index++, bundleAlM.getDebtBurdenScore());
			ps.setBigDecimal(index++, bundleAlM.getDownPayment());
			ps.setBigDecimal(index++, bundleAlM.getHpAmount());
			
			ps.setBigDecimal(index++, bundleAlM.getMonthlyInstallment());
			ps.setDate(index++, this.parseDate(bundleAlM.getApproveDate()));
			ps.setString(index++, bundleAlM.getContractCreateBy());
			ps.setBigDecimal(index++, bundleAlM.getApproveCreditLine());
			ps.setString(index++, bundleAlM.getCreditApproveBy());
			
			ps.setString(index++, bundleAlM.getCreateBy());
			ps.setString(index++, bundleAlM.getUpdateBy());
			
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
	public PLBundleALDataM loadBundleAL(String appRecId) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLBundleALDataM bundleAlM = null;	
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID, CAR_TYPE, CAR_MODEL, CONTRACT_NO, APPLICANT_SALARY ");
			sql.append(", ESTIMATION_SALARY, FICO_SCORE, DEBT_BURDEN_SCORE, DOWN_PAYMENT, HP_AMOUNT ");
			sql.append(", MONTHLY_INSTALLMENT, APPROVE_DATE, CONTRACT_CREATE_BY, APPROVE_CREDIT_LINE, CREDIT_APPROVE_BY ");
			sql.append(", CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append("FROM ORIG_BUNDLE_AL WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);
			rs = ps.executeQuery();
						
			if(rs.next()){
				bundleAlM = new PLBundleALDataM();	
				int index = 1;				
				bundleAlM.setAppRecId(rs.getString(index++));
				bundleAlM.setCarType(rs.getString(index++));
				bundleAlM.setCarModel(rs.getString(index++));
				bundleAlM.setContractNo(rs.getString(index++));
				bundleAlM.setAppSalary(rs.getBigDecimal(index++));
				
				bundleAlM.setEstSalary(rs.getBigDecimal(index++));
				bundleAlM.setFicoScore(rs.getString(index++));
				bundleAlM.setDebtBurdenScore(rs.getString(index++));
				bundleAlM.setDownPayment(rs.getBigDecimal(index++));
				bundleAlM.setHpAmount(rs.getBigDecimal(index++));
				
				bundleAlM.setMonthlyInstallment(rs.getBigDecimal(index++));
				bundleAlM.setApproveDate(rs.getDate(index++));
				bundleAlM.setContractCreateBy(rs.getString(index++));
				bundleAlM.setApproveCreditLine(rs.getBigDecimal(index++));
				bundleAlM.setCreditApproveBy(rs.getString(index++));
				
				bundleAlM.setCreateDate(rs.getTimestamp(index++));
				bundleAlM.setCreateBy(rs.getString(index++));
				bundleAlM.setUpdateDate(rs.getTimestamp(index++));
				bundleAlM.setUpdateBy(rs.getString(index++));
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
		return bundleAlM;
	}

}
