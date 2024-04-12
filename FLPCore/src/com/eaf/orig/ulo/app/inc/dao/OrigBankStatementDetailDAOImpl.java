package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.BankStatementDetailDataM;

public class OrigBankStatementDetailDAOImpl extends OrigObjectDAO implements
		OrigBankStatementDetailDAO {
	public OrigBankStatementDetailDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigBankStatementDetailDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigBankStatementDetailM(
			BankStatementDetailDataM bankStatementDetailM) throws ApplicationException {
		logger.debug("bankStatementDetailM.getBankStatementDetailId() :" + bankStatementDetailM.getBankStatementDetailId());
	    if(Util.empty(bankStatementDetailM.getBankStatementDetailId())){
			String detailId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_BANK_STATEMENT_DETAIL_PK);
			bankStatementDetailM.setBankStatementDetailId(detailId);
			bankStatementDetailM.setCreateBy(userId);
	    }
	    bankStatementDetailM.setUpdateBy(userId);
		createTableINC_BANK_STATEMENT_DETAIL(bankStatementDetailM);
	}

	private void createTableINC_BANK_STATEMENT_DETAIL(
			BankStatementDetailDataM bankStatementDetailM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_BANK_STATEMENT_DETAIL ");
			sql.append("( BANK_STATEMENT_DETAIL_ID, BANK_STATEMENT_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,  ?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bankStatementDetailM.getBankStatementDetailId());
			ps.setString(cnt++, bankStatementDetailM.getBankStatementId());
			
			ps.setString(cnt++, bankStatementDetailM.getMonth());
			ps.setString(cnt++, bankStatementDetailM.getYear());
			ps.setBigDecimal(cnt++, bankStatementDetailM.getAmount());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bankStatementDetailM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bankStatementDetailM.getUpdateBy());
			
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
	public void deleteOrigBankStatementDetailM(String bankStatementId,
			String bankStatementDetailId) throws ApplicationException {
		deleteTableINC_BANK_STATEMENT_DETAIL(bankStatementId, bankStatementDetailId);
	}

	private void deleteTableINC_BANK_STATEMENT_DETAIL(String bankStatementId,
			String bankStatementDetailId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_BANK_STATEMENT_DETAIL ");
			sql.append(" WHERE BANK_STATEMENT_ID = ? ");
			if(!Util.empty(bankStatementDetailId)) {
				sql.append(" AND BANK_STATEMENT_DETAIL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, bankStatementId);
			if(!Util.empty(bankStatementDetailId)) {
				ps.setString(2, bankStatementDetailId);
			}
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
	public ArrayList<BankStatementDetailDataM> loadOrigBankStatementDetailM(
			String bankStatementId) throws ApplicationException {
		ArrayList<BankStatementDetailDataM> result = selectTableINC_BANK_STATEMENT_DETAIL(bankStatementId);
		return result;
	}

	private ArrayList<BankStatementDetailDataM> selectTableINC_BANK_STATEMENT_DETAIL(
			String bankStatementId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<BankStatementDetailDataM> bankStatDetailList = new ArrayList<BankStatementDetailDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT BANK_STATEMENT_DETAIL_ID, BANK_STATEMENT_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_BANK_STATEMENT_DETAIL WHERE BANK_STATEMENT_ID = ? ");
			sql.append(" ORDER BY BANK_STATEMENT_DETAIL_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, bankStatementId);

			rs = ps.executeQuery();

			while (rs.next()) {
				BankStatementDetailDataM bankStatementDetailM = new BankStatementDetailDataM();
				bankStatementDetailM.setSeq(bankStatDetailList.size()+1);
				bankStatementDetailM.setBankStatementDetailId(rs.getString("BANK_STATEMENT_DETAIL_ID"));
				bankStatementDetailM.setBankStatementId(rs.getString("BANK_STATEMENT_ID"));
				
				bankStatementDetailM.setMonth(rs.getString("MONTH"));
				bankStatementDetailM.setYear(rs.getString("YEAR"));
				bankStatementDetailM.setAmount(rs.getBigDecimal("AMOUNT"));
				
				bankStatementDetailM.setCreateBy(rs.getString("CREATE_BY"));
				bankStatementDetailM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				bankStatementDetailM.setUpdateBy(rs.getString("UPDATE_BY"));
				bankStatementDetailM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				bankStatDetailList.add(bankStatementDetailM);
			}

			return bankStatDetailList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigBankStatementDetailM(
			BankStatementDetailDataM bankStatementDetailM) throws ApplicationException {
		int returnRows = 0;
		bankStatementDetailM.setUpdateBy(userId);
		returnRows = updateTableINC_BANK_STATEMENT_DETAIL(bankStatementDetailM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_BANK_STATEMENT_DETAIL then call Insert method");
			bankStatementDetailM.setCreateBy(userId);
			createOrigBankStatementDetailM(bankStatementDetailM);
		}
	}

	private int updateTableINC_BANK_STATEMENT_DETAIL(
			BankStatementDetailDataM bankStatementDetailM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_BANK_STATEMENT_DETAIL ");
			sql.append(" SET MONTH = ?, YEAR = ?, AMOUNT = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE BANK_STATEMENT_DETAIL_ID = ? AND BANK_STATEMENT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bankStatementDetailM.getMonth());
			ps.setString(cnt++, bankStatementDetailM.getYear());
			ps.setBigDecimal(cnt++, bankStatementDetailM.getAmount());
			
			ps.setString(cnt++, bankStatementDetailM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, bankStatementDetailM.getBankStatementDetailId());
			ps.setString(cnt++, bankStatementDetailM.getBankStatementId());			
			
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

	@Override
	public void deleteNotInKeyBankStatementDetail( 
			ArrayList<BankStatementDetailDataM> bankStatementDtlList,
			String bankStatementId) throws ApplicationException {
		deleteNotInKeyTableINC_BANK_STATEMENT_DETAIL(bankStatementDtlList, bankStatementId);
	}

	private void deleteNotInKeyTableINC_BANK_STATEMENT_DETAIL(
			ArrayList<BankStatementDetailDataM> bankStatementDtlList,
			String bankStatementId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_BANK_STATEMENT_DETAIL ");
            sql.append(" WHERE BANK_STATEMENT_ID = ? ");
            
            if (!Util.empty(bankStatementDtlList)) {
                sql.append(" AND BANK_STATEMENT_DETAIL_ID NOT IN ( ");

                for (BankStatementDetailDataM detailM: bankStatementDtlList) {
                    sql.append(" '" + detailM.getBankStatementDetailId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, bankStatementId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
