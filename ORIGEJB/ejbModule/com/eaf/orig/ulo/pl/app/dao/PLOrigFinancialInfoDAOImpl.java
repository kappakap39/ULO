package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLFinancialInfoDataM;

public class PLOrigFinancialInfoDAOImpl extends OrigObjectDAO implements PLOrigFinancialInfoDAO {
	
	private static Logger log = Logger.getLogger(PLOrigFinancialInfoDAOImpl.class);

	@Override
	public void updateSaveFinancialInfo(Vector<PLFinancialInfoDataM> financialVect, String personalID)throws PLOrigApplicationException {
		try{			
			if(!OrigUtil.isEmptyVector(financialVect)){				
				Vector<Integer> seqVect = new Vector<Integer>();
				for(PLFinancialInfoDataM financialM : financialVect){
					int updateRole = updateTableOrig_Financial_Info(financialM, personalID);
					if(updateRole==0){
						insertTableOrig_Financial_Info(financialM, personalID);
					} 
					seqVect.add(financialM.getSeq());				
				}
				if(!OrigUtil.isEmptyVector(seqVect)){
					deleteTableOrig_Financial_Info(seqVect, personalID);
				}
			}			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}

	}
	
	private int seletcTableOrig_Financial_Info_MaxSeq(String personalID) throws PLOrigApplicationException {
		Connection conn = null;
		int maxSeq = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT MAX(SEQ) FROM ORIG_FINANCIAL_INFO WHERE PERSONAL_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				maxSeq = rs.getInt(1);
			}
			return maxSeq;
			
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
		
	}
	
	private void insertTableOrig_Financial_Info(PLFinancialInfoDataM financialDocM, String personalID)throws PLOrigApplicationException{		
		PreparedStatement ps = null;	
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_FINANCIAL_INFO ");
			sql.append("( PERSONAL_ID, SEQ, FINANCIAL_TYPE, FINANCIAL_NO, BANK ");
			sql.append(", BRANCH, OPEN_DATE, EXP_DATE, FINANCIAL_AMOUNT, CREATE_DATE ");
			sql.append(", CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,SYSDATE   ,?,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, personalID);
			ps.setInt(2, financialDocM.getSeq());
			ps.setString(3, financialDocM.getFinancialType());
			ps.setString(4, financialDocM.getFinancialNo());
			ps.setString(5, financialDocM.getBank());
			
			ps.setString(6, financialDocM.getBranch());
			if (financialDocM.getOpenDate() != null) {
				ps.setDate(7, new java.sql.Date(financialDocM.getOpenDate().getTime()));
			} else {
				ps.setDate(7, null);
			}
			if (financialDocM.getExpiryDate() != null) {
				ps.setDate(8, new java.sql.Date(financialDocM.getExpiryDate().getTime()));
			} else {
				ps.setDate(8, null);
			}
			ps.setBigDecimal(9, financialDocM.getFinancialAmount());
			
			ps.setString(10, financialDocM.getCreateBy());
			ps.setString(11, financialDocM.getUpdateBy());
			
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
	
	private void deleteTableOrig_Financial_Info(Vector<Integer> seq, String personalId) throws PLOrigApplicationException{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_FINANCIAL_INFO ");
			sql.append(" WHERE PERSONAL_ID = ? AND SEQ NOT IN ");
			sql.append("( ");
			if (seq != null && seq.size() > 0) {
				for (int i=0; i<seq.size(); i++){
					sql.append("?,");
				}
				sql.deleteCharAt((sql.length()-1));
				
			}else{
				sql.append(" ? ");
			}
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if (seq != null && seq.size() > 0) {
				for (int i=0; i<seq.size(); i++){
					ps.setInt((i+2), seq.get(i));
				}
			}else{
				ps.setString(2, null);
			}
			ps.executeUpdate();

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
		
	}
	
	private int updateTableOrig_Financial_Info(PLFinancialInfoDataM financialDocM, String personalID)
			throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_FINANCIAL_INFO ");
			sql.append(" SET FINANCIAL_TYPE=?, FINANCIAL_NO=?, BANK=?, BRANCH=?, OPEN_DATE=? ");
			sql.append(", EXP_DATE=?, FINANCIAL_AMOUNT=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?");
			sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, financialDocM.getFinancialType());
			ps.setString(2, financialDocM.getFinancialNo());
			ps.setString(3, financialDocM.getBank());
			ps.setString(4, financialDocM.getBranch());
			if (financialDocM.getOpenDate() != null) {
				ps.setDate(5, this.parseDate(financialDocM.getOpenDate()));
			} else {
				ps.setDate(5, null);
			}
			if (financialDocM.getExpiryDate() != null) {
				ps.setDate(6, this.parseDate(financialDocM.getExpiryDate()));
			} else {
				ps.setDate(6, null);
			}
			
			ps.setBigDecimal(7, financialDocM.getFinancialAmount());
			ps.setString(8, financialDocM.getUpdateBy());
			
			ps.setString(9, personalID);
			ps.setInt(10, financialDocM.getSeq());
			
			return ps.executeUpdate();
			
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
	}
	

	@Override
	public Vector<PLFinancialInfoDataM> loadFinancialInfo(String personalId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLFinancialInfoDataM> financialVect = new Vector<PLFinancialInfoDataM>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PERSONAL_ID, SEQ, FINANCIAL_TYPE, FINANCIAL_NO, BANK ");
			sql.append(", BRANCH, OPEN_DATE, EXP_DATE, FINANCIAL_AMOUNT, CREATE_DATE ");
			sql.append(", CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_FINANCIAL_INFO WHERE PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();
			
			while(rs.next()){
				PLFinancialInfoDataM financialM = new PLFinancialInfoDataM();
				
				financialM.setPersonalID(rs.getString(1));
				financialM.setSeq(rs.getInt(2));
				financialM.setFinancialType(rs.getString(3));
				financialM.setFinancialNo(rs.getString(4));
				financialM.setBank(rs.getString(5));
				
				financialM.setBranch(rs.getString(6));
				financialM.setOpenDate(rs.getDate(7));
				financialM.setExpiryDate(rs.getDate(8));
				financialM.setFinancialAmount(rs.getBigDecimal(9));
				financialM.setCreateDate(rs.getTimestamp(10));
				
				financialM.setCreateBy(rs.getString(11));
				financialM.setUpdateDate(rs.getTimestamp(12));
				financialM.setUpdateBy(rs.getString(13));
				
				financialVect.add(financialM);
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
		return financialVect;
	}

}
