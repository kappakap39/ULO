package com.eaf.orig.ulo.app.dao;

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
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public class OrigCashTransferDAOImpl extends OrigObjectDAO implements
		OrigCashTransferDAO {
	public OrigCashTransferDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigCashTransferDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigCashTransferM(CashTransferDataM cashTransferM) throws ApplicationException {
		//Get Primary Key
	    logger.debug("cashTransferM.getCashTransferId() :"+cashTransferM.getCashTransferId());
	    if(Util.empty(cashTransferM.getCashTransferId())){
			String cashTransferId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CASH_TRANSFER_PK);
			cashTransferM.setCashTransferId(cashTransferId);
			cashTransferM.setCreateBy(userId);
	    }
		logger.debug("cashTransferM.getLoanId() :"+cashTransferM.getLoanId());		
		cashTransferM.setUpdateBy(userId);
		createTableORIG_CASH_TRANSFER(cashTransferM);
		
	}

	private void createTableORIG_CASH_TRANSFER(CashTransferDataM cashTransferM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_CASH_TRANSFER ");
			sql.append("( LOAN_ID, CASH_TRANSFER_ID, PRODUCT_TYPE, TRANSFER_ACCOUNT, ");
			sql.append(" ACCOUNT_NAME, PERCENT_TRANSFER, FIRST_TRANSFER_AMOUNT, ");
			sql.append(" EXPRESS_TRANSFER, CASH_TRANSFER_TYPE, BANK_TRANSFER, COMPLETE_DATA, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, TRANSFER_ACCOUNT_TYPE ) ");

			sql.append(" VALUES(?,?,?,?, ?,?,?, ?,?,?,?, ?,?,?,? ,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cashTransferM.getLoanId());
			ps.setString(cnt++, cashTransferM.getCashTransferId());
			ps.setString(cnt++, cashTransferM.getProductType());
			ps.setString(cnt++, cashTransferM.getTransferAccount());
			
			ps.setString(cnt++, cashTransferM.getAccountName());
			ps.setBigDecimal(cnt++, cashTransferM.getPercentTransfer());
			ps.setBigDecimal(cnt++, cashTransferM.getFirstTransferAmount());
			
			ps.setString(cnt++, cashTransferM.getExpressTransfer());
			ps.setString(cnt++, cashTransferM.getCashTransferType());
			ps.setString(cnt++, cashTransferM.getBankTransfer());
			ps.setString(cnt++, cashTransferM.getCompleteData());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, cashTransferM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, cashTransferM.getUpdateBy());
			ps.setString(cnt++, cashTransferM.getTransferAccountType());
			
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
	public void deleteOrigCashTransferM(String loanID, String transferType) throws ApplicationException {
		deleteTableORIG_CASH_TRANSFER(loanID, transferType);
	}

	private void deleteTableORIG_CASH_TRANSFER(String loanID, String transferId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_CASH_TRANSFER ");
			sql.append(" WHERE LOAN_ID = ? ");
			if(!Util.empty(transferId)) {
				sql.append(" AND CASH_TRANSFER_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, loanID);
			if(!Util.empty(transferId)) {
				ps.setString(2, transferId);
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
	public ArrayList<CashTransferDataM> loadOrigCashTransferM(String loanID)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_CASH_TRANSFER(loanID,conn);
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
	public ArrayList<CashTransferDataM> loadOrigCashTransferM(String loanID,
			Connection conn) throws ApplicationException {
		return selectTableORIG_CASH_TRANSFER(loanID, conn);
	}
	private ArrayList<CashTransferDataM> selectTableORIG_CASH_TRANSFER(String loanID,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CashTransferDataM> cashTransferDataMs = new ArrayList<CashTransferDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT LOAN_ID, CASH_TRANSFER_ID, PRODUCT_TYPE, TRANSFER_ACCOUNT, ");
			sql.append(" ACCOUNT_NAME, PERCENT_TRANSFER, FIRST_TRANSFER_AMOUNT, ");
			sql.append(" EXPRESS_TRANSFER, CASH_TRANSFER_TYPE, BANK_TRANSFER, COMPLETE_DATA, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, TRANSFER_ACCOUNT_TYPE ");
			sql.append(" FROM ORIG_CASH_TRANSFER  WHERE LOAN_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, loanID);

			rs = ps.executeQuery();

			while (rs.next()) {
				CashTransferDataM cashTransferM = new CashTransferDataM();
				cashTransferM.setLoanId(rs.getString("LOAN_ID"));
				cashTransferM.setCashTransferId(rs.getString("CASH_TRANSFER_ID"));
				cashTransferM.setProductType(rs.getString("PRODUCT_TYPE"));
				cashTransferM.setTransferAccount(rs.getString("TRANSFER_ACCOUNT"));
				
				cashTransferM.setAccountName(rs.getString("ACCOUNT_NAME"));
				cashTransferM.setPercentTransfer(rs.getBigDecimal("PERCENT_TRANSFER"));
				cashTransferM.setFirstTransferAmount(rs.getBigDecimal("FIRST_TRANSFER_AMOUNT"));
				
				cashTransferM.setExpressTransfer(rs.getString("EXPRESS_TRANSFER"));
				cashTransferM.setCashTransferType(rs.getString("CASH_TRANSFER_TYPE"));
				cashTransferM.setBankTransfer(rs.getString("BANK_TRANSFER"));
				cashTransferM.setCompleteData(rs.getString("COMPLETE_DATA"));
				
				cashTransferM.setCreateBy(rs.getString("CREATE_BY"));
				cashTransferM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				cashTransferM.setUpdateBy(rs.getString("UPDATE_BY"));
				cashTransferM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				cashTransferM.setTransferAccountType(rs.getString("TRANSFER_ACCOUNT_TYPE"));
				cashTransferDataMs.add(cashTransferM);
			}

			return cashTransferDataMs;
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
	public void saveUpdateOrigCashTransferM(CashTransferDataM cashTransferM)
			throws ApplicationException {
		int returnRows = 0;
		cashTransferM.setUpdateBy(userId);
		returnRows = updateTableORIG_CASH_TRANSFER(cashTransferM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_CASH_TRANSFER then call Insert method");
			cashTransferM.setCreateBy(userId);
			createOrigCashTransferM(cashTransferM);
		}
	}

	private int updateTableORIG_CASH_TRANSFER(CashTransferDataM cashTransferM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_CASH_TRANSFER ");
			sql.append(" SET CASH_TRANSFER_TYPE = ?, PRODUCT_TYPE =?, TRANSFER_ACCOUNT =?, ACCOUNT_NAME =?, ");
			sql.append(" PERCENT_TRANSFER =?, FIRST_TRANSFER_AMOUNT =?, EXPRESS_TRANSFER = ?, ");
			sql.append(" BANK_TRANSFER = ?, COMPLETE_DATA = ?, UPDATE_BY = ?, UPDATE_DATE =? ,TRANSFER_ACCOUNT_TYPE=?");
			
			sql.append(" WHERE LOAN_ID = ? AND CASH_TRANSFER_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;	
			ps.setString(cnt++, cashTransferM.getCashTransferType());
			ps.setString(cnt++, cashTransferM.getProductType());
			ps.setString(cnt++, cashTransferM.getTransferAccount());
			ps.setString(cnt++, cashTransferM.getAccountName());
			
			ps.setBigDecimal(cnt++, cashTransferM.getPercentTransfer());
			ps.setBigDecimal(cnt++, cashTransferM.getFirstTransferAmount());
			ps.setString(cnt++, cashTransferM.getExpressTransfer());
			
			ps.setString(cnt++, cashTransferM.getBankTransfer());
			ps.setString(cnt++, cashTransferM.getCompleteData());
			ps.setString(cnt++, cashTransferM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, cashTransferM.getTransferAccountType());
			// WHERE CLAUSE
			ps.setString(cnt++, cashTransferM.getLoanId());
			ps.setString(cnt++, cashTransferM.getCashTransferId());
			
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
	public void deleteNotInKeyCashTransfer(ArrayList<CashTransferDataM> cashTransferList, String loanID)
			throws ApplicationException {
		deleteNotInKeyTableORIG_CASH_TRANSFER(cashTransferList, loanID);
	}

	private void deleteNotInKeyTableORIG_CASH_TRANSFER(
			ArrayList<CashTransferDataM> cashTransferList, String loanID) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_CASH_TRANSFER ");
            sql.append(" WHERE LOAN_ID = ? ");
            
            if (!Util.empty(cashTransferList)) {
                sql.append(" AND CASH_TRANSFER_ID NOT IN ( ");
                for (CashTransferDataM cashTransferM: cashTransferList) {
                	logger.debug("cashTransferM.getCashTransferId() = "+cashTransferM.getCashTransferId());
                    sql.append(" '" + cashTransferM.getCashTransferId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, loanID);
            
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
