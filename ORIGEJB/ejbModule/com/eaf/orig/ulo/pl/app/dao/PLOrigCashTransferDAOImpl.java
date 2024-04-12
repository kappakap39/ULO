package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM;

public class PLOrigCashTransferDAOImpl extends OrigObjectDAO implements PLOrigCashTransferDAO {
	
	private static Logger log = Logger.getLogger(PLOrigCashTransferDAOImpl.class);

	@Override
	public void updateSaveCashTransfer(PLCashTransferDataM cashTransferM, String appRecId)throws PLOrigApplicationException{
		try{
			if(null != cashTransferM){
				int returnRows = this.updateTableOrig_Cash_Transfer(cashTransferM, appRecId);
				if(returnRows == 0){
					this.insertTableOrig_Cash_Transfer(cashTransferM, appRecId);
				}			
			}
		}catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}

	}
	
	private int updateTableOrig_Cash_Transfer(PLCashTransferDataM cashTransferM, String appRecId) throws PLOrigApplicationException{
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_CASH_TRANSFER ");
			sql.append(" SET PRODUCT_TYPE=?, TRANSFER_ACCOUNT=?, ACCOUNT_NAME=?, PERCENT_TRANSFER=?, FIRST_TRANSFER_AMOUNT=? ");
			sql.append(", EXPRESS_TRANSFER=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?, CASH_TRANSFER_TYPE=?, BANK_TRANSFER=? ");
			sql.append(", REMARK=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, cashTransferM.getProductType());
			ps.setString(index++, cashTransferM.getTransAcc());
			ps.setString(index++, cashTransferM.getAccName());
			ps.setBigDecimal(index++, cashTransferM.getPercentTrans());
			ps.setBigDecimal(index++, cashTransferM.getFirstTransAmount());
			
			ps.setString(index++, cashTransferM.getExpressTrans());
			ps.setString(index++, cashTransferM.getUpdateBy());
			ps.setString(index++, cashTransferM.getCashTransferType());
			ps.setString(index++, cashTransferM.getBankTransfer());
			
			ps.setString(index++, cashTransferM.getRemark());
			
			ps.setString(index++, appRecId);
			
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
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
		return returnRows;
	}
	
	private void insertTableOrig_Cash_Transfer(PLCashTransferDataM cashTransferM, String appRecId) throws PLOrigApplicationException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_CASH_TRANSFER ");
			sql.append("( APPLICATION_RECORD_ID, PRODUCT_TYPE, TRANSFER_ACCOUNT, ACCOUNT_NAME, PERCENT_TRANSFER ");
			sql.append(", FIRST_TRANSFER_AMOUNT, EXPRESS_TRANSFER, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, CASH_TRANSFER_TYPE, BANK_TRANSFER, REMARK ) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,SYSDATE,?,SYSDATE  ,?,?,?,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, appRecId);
			ps.setString(index++, cashTransferM.getProductType());
			ps.setString(index++, cashTransferM.getTransAcc());
			ps.setString(index++, cashTransferM.getAccName());
			ps.setBigDecimal(index++, cashTransferM.getPercentTrans());
			
			ps.setBigDecimal(index++, cashTransferM.getFirstTransAmount());
			ps.setString(index++, cashTransferM.getExpressTrans());
			ps.setString(index++, cashTransferM.getCreateBy());
			
			ps.setString(index++, cashTransferM.getUpdateBy());
			ps.setString(index++, cashTransferM.getCashTransferType());
			ps.setString(index++, cashTransferM.getBankTransfer());
			ps.setString(index++, cashTransferM.getRemark());
			
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
	public PLCashTransferDataM loadCashTransfer(String appRecId) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		PLCashTransferDataM cashDataM = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PRODUCT_TYPE, TRANSFER_ACCOUNT, ACCOUNT_NAME, PERCENT_TRANSFER, FIRST_TRANSFER_AMOUNT ");
			sql.append(", EXPRESS_TRANSFER, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(", CASH_TRANSFER_TYPE, BANK_TRANSFER, REMARK ");
			sql.append(" FROM ORIG_CASH_TRANSFER WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			
			if(rs.next()){				
				int index = 1;
				cashDataM = new PLCashTransferDataM();		
				cashDataM.setProductType(rs.getString(index++));
				cashDataM.setTransAcc(rs.getString(index++));
				cashDataM.setAccName(rs.getString(index++));
				cashDataM.setPercentTrans(rs.getBigDecimal(index++));
				cashDataM.setFirstTransAmount(rs.getBigDecimal(index++));
				
				cashDataM.setExpressTrans(rs.getString(index++));
				cashDataM.setCreateDate(rs.getTimestamp(index++));
				cashDataM.setCreateBy(rs.getString(index++));
				cashDataM.setUpdateDate(rs.getTimestamp(index++));
				cashDataM.setUpdateBy(rs.getString(index++));
				
				cashDataM.setCashTransferType(rs.getString(index++));
				cashDataM.setBankTransfer(rs.getString(index++));
				cashDataM.setRemark(rs.getString(index++));				
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
		return cashDataM;
	}

}
