package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLPaymentMethodDataM;

public class PLOrigPaymentMethodDAOImpl extends OrigObjectDAO implements PLOrigPaymentMethodDAO {
	
	private static Logger log = Logger.getLogger(PLOrigPaymentMethodDAOImpl.class);

	@Override
	public void updateSavePaymentMethod(PLPaymentMethodDataM paymentM, String appRecId)throws PLOrigApplicationException{
		try{
			if(null != paymentM){
				int returnRows = this.updateTableOrig_Payment_Method(paymentM, appRecId);
				if(returnRows == 0){
					this.insertTableOrig_Payment_Method(paymentM, appRecId);
				}			
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private int updateTableOrig_Payment_Method(PLPaymentMethodDataM paymentM, String appRecId) throws PLOrigApplicationException{
		Connection conn = null;
		int returnRows;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_PAYMENT_METHOD ");
			sql.append(" SET PAYMENT_METHOD=?, PAYMENT_RATIO=?, ACCOUNT_NO=?, ACCOUNT_NAME=?, DUE_CYCLE=? ");
			sql.append(", SMS_DUE_ALERT=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?, ACCOUNT_TYPE=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			
			ps.setString(index++, paymentM.getPayMethod());
			ps.setBigDecimal(index++, paymentM.getPayRatio());
			ps.setString(index++, paymentM.getAccNo());
			ps.setString(index++, paymentM.getAccName());
			ps.setString(index++, paymentM.getDueCycle());			
			ps.setString(index++, paymentM.getSmsDueAlert());
			ps.setString(index++, paymentM.getUpdateBy());
			ps.setString(index++, paymentM.getAccType());			
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
	
	private void insertTableOrig_Payment_Method (PLPaymentMethodDataM paymentM, String appRecId) throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_PAYMENT_METHOD ");
			sql.append("( APPLICATION_RECORD_ID, PAYMENT_METHOD, PAYMENT_RATIO, ACCOUNT_NO, ACCOUNT_NAME ");
			sql.append(", DUE_CYCLE, SMS_DUE_ALERT, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, ACCOUNT_TYPE )" );
			sql.append(" VALUES(?,?,?,?,?  ,?,?,SYSDATE,?,SYSDATE  ,?,?) ");
			String dSql = String.valueOf(sql);
			
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, appRecId);
			ps.setString(index++, paymentM.getPayMethod());
			ps.setBigDecimal(index++, paymentM.getPayRatio());
			ps.setString(index++, paymentM.getAccNo());
			ps.setString(index++, paymentM.getAccName());			
			ps.setString(index++, paymentM.getDueCycle());
			ps.setString(index++, paymentM.getSmsDueAlert());
			ps.setString(index++, paymentM.getCreateBy());			
			ps.setString(index++, paymentM.getUpdateBy());
			ps.setString(index++, paymentM.getAccType());
			
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
	public PLPaymentMethodDataM loadPaymentMethod(String appID) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLPaymentMethodDataM paymentDataM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PAYMENT_METHOD, PAYMENT_RATIO, ACCOUNT_NO, ACCOUNT_NAME, DUE_CYCLE ");
			sql.append(", SMS_DUE_ALERT, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ACCOUNT_TYPE ");
			sql.append(" FROM ORIG_PAYMENT_METHOD WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appID);

			rs = ps.executeQuery();
			
			if(rs.next()){	
				paymentDataM = new PLPaymentMethodDataM();
				paymentDataM.setPayMethod(rs.getString(1));
				paymentDataM.setPayRatio(rs.getBigDecimal(2));
				paymentDataM.setAccNo(rs.getString(3));
				paymentDataM.setAccName(rs.getString(4));
				paymentDataM.setDueCycle(rs.getString(5));				
				paymentDataM.setSmsDueAlert(rs.getString(6));
				paymentDataM.setCreateDate(rs.getTimestamp(7));
				paymentDataM.setCreateBy(rs.getString(8));
				paymentDataM.setUpdateDate(rs.getTimestamp(9));
				paymentDataM.setUpdateBy(rs.getString(10));				
				paymentDataM.setAccType(rs.getString(11));				
			}			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return paymentDataM;
	}

}
