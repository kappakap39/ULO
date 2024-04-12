package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.BankStatementDataM;
import com.eaf.orig.ulo.model.app.BankStatementDetailDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public class OrigBankStatementDAOImpl extends OrigObjectDAO implements
		OrigBankStatementDAO {
	public OrigBankStatementDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigBankStatementDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigBankStatementM(BankStatementDataM bankStatementM)
			throws ApplicationException {
		logger.debug("bankStatementM.getBankStatementId() :"+bankStatementM.getBankStatementId());
	    if(Util.empty(bankStatementM.getBankStatementId())){
			String statementId = GenerateUnique.generate(OrigConstant.SeqNames.INC_BANK_STATEMENT_PK);
			bankStatementM.setBankStatementId(statementId);
			bankStatementM.setCreateBy(userId);
	    }
	    bankStatementM.setUpdateBy(userId);
		createTableINC_BANK_STATEMENT(bankStatementM);
		
		ArrayList<BankStatementDetailDataM> detailList = bankStatementM.getBankStatementDetails();
		if(!Util.empty(detailList)) {
			OrigBankStatementDetailDAO detailDAO = ORIGDAOFactory.getBankStatementDetailDAO(userId);
			for(BankStatementDetailDataM detailM: detailList) {
				detailM.setBankStatementId(bankStatementM.getBankStatementId());
				detailDAO.createOrigBankStatementDetailM(detailM);
			}
		}
	}

	private void createTableINC_BANK_STATEMENT(BankStatementDataM bankStatementM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_BANK_STATEMENT ");
			sql.append("( BANK_STATEMENT_ID, BANK_CODE, STATEMENT_CODE, ");
			sql.append(" INCOME_ID, ADDITIONAL_CODE, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bankStatementM.getBankStatementId());
			ps.setString(cnt++, bankStatementM.getBankCode());
			ps.setString(cnt++, bankStatementM.getStatementCode());

			ps.setString(cnt++, bankStatementM.getIncomeId());
			ps.setString(cnt++, bankStatementM.getAdditionalCode());
			ps.setString(cnt++, bankStatementM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bankStatementM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bankStatementM.getUpdateBy());
			
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
	public void deleteOrigBankStatementM(String incomeId,
			String bankStatementId) throws ApplicationException {
		OrigBankStatementDetailDAO detailDAO = ORIGDAOFactory.getBankStatementDetailDAO();
		if(Util.empty(bankStatementId)) {
			ArrayList<String> statementIdList = selectBankStatementIdINC_BANK_STATEMENT(incomeId);
			if(!Util.empty(statementIdList)) {
				for(String statementId: statementIdList) {
					detailDAO.deleteOrigBankStatementDetailM(statementId, null);
				}
			}
		} else {
			detailDAO.deleteOrigBankStatementDetailM(bankStatementId, null);
		}
		
		deleteTableINC_BANK_STATEMENT(incomeId, bankStatementId);
	}

	private ArrayList<String> selectBankStatementIdINC_BANK_STATEMENT(String incomeId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> bankStatList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT BANK_STATEMENT_ID ");
			sql.append(" FROM INC_BANK_STATEMENT WHERE INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				bankStatList.add(rs.getString("BANK_STATEMENT_ID"));
			}

			return bankStatList;
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

	private void deleteTableINC_BANK_STATEMENT(String incomeId,
			String bankStatementId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_BANK_STATEMENT ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(bankStatementId)) {
				sql.append(" AND BANK_STATEMENT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(bankStatementId)) {
				ps.setString(2, bankStatementId);
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
	public ArrayList<BankStatementDataM> loadOrigBankStatementM(
			String incomeId, String incomeType, Connection conn)
			throws ApplicationException {
		return loadOrigBankStatementM(incomeId, incomeType);
	}
	@Override
	public ArrayList<BankStatementDataM> loadOrigBankStatementM(
			String incomeId, String incomeType) throws ApplicationException {
		ArrayList<BankStatementDataM> result = selectTableINC_BANK_STATEMENT(incomeId, incomeType);
		
		if(!Util.empty(result)) {
			OrigBankStatementDetailDAO detailDAO = ORIGDAOFactory.getBankStatementDetailDAO();
			for(BankStatementDataM statementM: result) {
				ArrayList<BankStatementDetailDataM> detailList = detailDAO.
						loadOrigBankStatementDetailM(statementM.getBankStatementId());
				if(!Util.empty(detailList)) {
					statementM.setBankStatementDetails(detailList);					
				}
			}
		}
		return result;
	}

	private ArrayList<BankStatementDataM> selectTableINC_BANK_STATEMENT(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<BankStatementDataM> bankStatList = new ArrayList<BankStatementDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT BANK_STATEMENT_ID, BANK_CODE, STATEMENT_CODE, ");
			sql.append(" INCOME_ID, ADDITIONAL_CODE, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_BANK_STATEMENT WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY BANK_STATEMENT_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				BankStatementDataM bankStatementM = new BankStatementDataM();
				bankStatementM.setSeq(bankStatList.size()+1);
				bankStatementM.setIncomeType(incomeType);
				bankStatementM.setBankStatementId(rs.getString("BANK_STATEMENT_ID"));
				bankStatementM.setBankCode(rs.getString("BANK_CODE"));
				bankStatementM.setStatementCode(rs.getString("STATEMENT_CODE"));
				
				bankStatementM.setIncomeId(rs.getString("INCOME_ID"));
				bankStatementM.setAdditionalCode(rs.getString("ADDITIONAL_CODE"));
				bankStatementM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				bankStatementM.setCreateBy(rs.getString("CREATE_BY"));
				bankStatementM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				bankStatementM.setUpdateBy(rs.getString("UPDATE_BY"));
				bankStatementM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				bankStatList.add(bankStatementM);
			}

			return bankStatList;
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
	public void saveUpdateOrigBankStatementM(BankStatementDataM bankStatementM)
			throws ApplicationException {
		int returnRows = 0;
		bankStatementM.setUpdateBy(userId);
		returnRows = updateTableINC_BANK_STATEMENT(bankStatementM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_BANK_STATEMENT then call Insert method");
			bankStatementM.setCreateBy(userId);
			createOrigBankStatementM(bankStatementM);
		} else {
		
			ArrayList<BankStatementDetailDataM> detailList = bankStatementM.getBankStatementDetails();
			OrigBankStatementDetailDAO detailDAO = ORIGDAOFactory.getBankStatementDetailDAO(userId);
			if(!Util.empty(detailList)) {
				for(BankStatementDetailDataM detailM: detailList) {
					detailM.setBankStatementId(bankStatementM.getBankStatementId());
					detailDAO.saveUpdateOrigBankStatementDetailM(detailM);
				}
			}
			detailDAO.deleteNotInKeyBankStatementDetail(detailList, bankStatementM.getBankStatementId());
		}
	}

	private int updateTableINC_BANK_STATEMENT(BankStatementDataM bankStatementM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_BANK_STATEMENT ");
			sql.append(" SET BANK_CODE = ?, STATEMENT_CODE = ?, ADDITIONAL_CODE = ?, ");
			sql.append(" COMPARE_FLAG = ?, UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE BANK_STATEMENT_ID = ? AND INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bankStatementM.getBankCode());			
			ps.setString(cnt++, bankStatementM.getStatementCode());
			ps.setString(cnt++, bankStatementM.getAdditionalCode());
			
			ps.setString(cnt++, bankStatementM.getCompareFlag());
			ps.setString(cnt++, bankStatementM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, bankStatementM.getBankStatementId());
			ps.setString(cnt++, bankStatementM.getIncomeId());
			
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
	public void deleteNotInKeyBankStatement(
			ArrayList<IncomeCategoryDataM> bankStatementList, String incomeId)
			throws ApplicationException {
		ArrayList<String> statementList = selectNotInKeyTableINC_BANK_STATEMENT(bankStatementList, incomeId);
		if(!Util.empty(statementList)) {
			OrigBankStatementDetailDAO detailDAO = ORIGDAOFactory.getBankStatementDetailDAO();
			for(String statementId: statementList) {
				detailDAO.deleteOrigBankStatementDetailM(statementId, null);
			}
		}
		
		deleteNotInKeyTableINC_BANK_STATEMENT(bankStatementList, incomeId);
	}

	private ArrayList<String> selectNotInKeyTableINC_BANK_STATEMENT(
			ArrayList<IncomeCategoryDataM> bankStatementList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> statementList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT BANK_STATEMENT_ID ");
			sql.append(" FROM INC_BANK_STATEMENT WHERE INCOME_ID = ? ");
			if (!Util.empty(bankStatementList)) {
                sql.append(" AND BANK_STATEMENT_ID NOT IN ( ");

                for (IncomeCategoryDataM statementM: bankStatementList) {
                    sql.append(" '" + statementM.getId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String statementId = rs.getString("BANK_STATEMENT_ID");
				statementList.add(statementId);
			}

			return statementList;
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

	private void deleteNotInKeyTableINC_BANK_STATEMENT(
			ArrayList<IncomeCategoryDataM> bankStatementList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_BANK_STATEMENT ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(bankStatementList)) {
                sql.append(" AND BANK_STATEMENT_ID NOT IN ( ");

                for (IncomeCategoryDataM bankStatementM: bankStatementList) {
                    sql.append(" '" + bankStatementM.getId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, incomeId);
            
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
