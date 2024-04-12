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
import com.eaf.orig.ulo.model.app.LoanTierDataM;

public class OrigLoanTierDAOImpl extends OrigObjectDAO implements
		OrigLoanTierDAO {
	public OrigLoanTierDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigLoanTierDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	private String userId = "";
	@Override
	public void createOrigLoanTierM(LoanTierDataM loanTierM)
			throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("loanTierM.getTierId() :"+loanTierM.getTierId());
		    if(Util.empty(loanTierM.getTierId())){
				String tierId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_TIER_PK );
				loanTierM.setTierId(tierId);
				loanTierM.setCreateBy(userId);
		    }
		    loanTierM.setUpdateBy(userId);
			createTableORIG_LOAN_TIER(loanTierM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_LOAN_TIER(LoanTierDataM loanTierM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_LOAN_TIER ");
			sql.append("( LOAN_ID, TIER_ID, SEQ, RATE_TYPE, ");
			sql.append(" EFFECTIVE_RATE, FLAT_RATE, RATE_AMOUNT, TERM, TERM_TYPE, ");
			sql.append(" MONTHLY_INSTALLMENT, TOTAL_INSTALLMENT, LAST_INSTALLMENT, ");
			sql.append(" INT_RATE_INDEX, INT_RATE_SIGN, INT_RATE_SPREAD, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY,  ");
			sql.append(" INT_RATE_AMOUNT ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,?   ,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, loanTierM.getLoanId());
			ps.setString(cnt++, loanTierM.getTierId());
			ps.setInt(cnt++, loanTierM.getSeq());
			ps.setString(cnt++, loanTierM.getRateType());
			
			ps.setBigDecimal(cnt++, loanTierM.getEffectiveRate());
			ps.setBigDecimal(cnt++, loanTierM.getFlatRate());
			ps.setBigDecimal(cnt++, loanTierM.getRateAmount());
			ps.setBigDecimal(cnt++, loanTierM.getTerm());
			ps.setString(cnt++, loanTierM.getTermType());
			
			ps.setBigDecimal(cnt++, loanTierM.getMonthlyInstallment());
			ps.setBigDecimal(cnt++, loanTierM.getTotalInstallment());
			ps.setBigDecimal(cnt++, loanTierM.getLastInstallment());
			
			ps.setString(cnt++, loanTierM.getIntRateIndex());
			ps.setString(cnt++, loanTierM.getIntRateSign());
			ps.setBigDecimal(cnt++, loanTierM.getIntRateSpread());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, loanTierM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, loanTierM.getUpdateBy());
			ps.setBigDecimal(cnt++, loanTierM.getIntRateAmount());
			
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
	public void deleteOrigLoanTierM(String loanId, String tierId)
			throws ApplicationException {
		deleteTableORIG_LOAN_TIER(loanId, tierId);
	}

	private void deleteTableORIG_LOAN_TIER(String loanId, String tierId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_LOAN_TIER ");
			sql.append(" WHERE LOAN_ID = ? ");
			if(!Util.empty(tierId)) {
				sql.append(" AND TIER_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, loanId);
			if(!Util.empty(tierId)) {
				ps.setString(2, tierId);
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
	public ArrayList<LoanTierDataM> loadOrigLoanTierM(String loanId)
			throws ApplicationException {		
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_LOAN_TIER(loanId,conn);
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
	public ArrayList<LoanTierDataM> loadOrigLoanTierM(String loanId,
			Connection conn) throws ApplicationException {
		return selectTableORIG_LOAN_TIER(loanId, conn);
	}
	private ArrayList<LoanTierDataM> selectTableORIG_LOAN_TIER(String loanId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<LoanTierDataM> loanTierList = new ArrayList<LoanTierDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT LOAN_ID, TIER_ID, SEQ, RATE_TYPE, ");
			sql.append(" EFFECTIVE_RATE, FLAT_RATE, RATE_AMOUNT, TERM, TERM_TYPE, ");
			sql.append(" MONTHLY_INSTALLMENT, TOTAL_INSTALLMENT, LAST_INSTALLMENT, ");
			sql.append(" INT_RATE_INDEX, INT_RATE_SIGN, INT_RATE_SPREAD,");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" INT_RATE_AMOUNT ");
			sql.append(" FROM ORIG_LOAN_TIER WHERE LOAN_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, loanId);

			rs = ps.executeQuery();

			while(rs.next()) {
				LoanTierDataM loanTierM = new LoanTierDataM();
				loanTierM.setLoanId(rs.getString("LOAN_ID"));
				loanTierM.setTierId(rs.getString("TIER_ID"));
				loanTierM.setSeq(rs.getInt("SEQ"));
				loanTierM.setRateType(rs.getString("RATE_TYPE"));

				loanTierM.setEffectiveRate(rs.getBigDecimal("EFFECTIVE_RATE"));
				loanTierM.setFlatRate(rs.getBigDecimal("FLAT_RATE"));
				loanTierM.setRateAmount(rs.getBigDecimal("RATE_AMOUNT"));
				loanTierM.setTerm(rs.getBigDecimal("TERM"));
				loanTierM.setTermType(rs.getString("TERM_TYPE"));
				
				loanTierM.setMonthlyInstallment(rs.getBigDecimal("MONTHLY_INSTALLMENT"));
				loanTierM.setTotalInstallment(rs.getBigDecimal("TOTAL_INSTALLMENT"));
				loanTierM.setLastInstallment(rs.getBigDecimal("LAST_INSTALLMENT"));
				
				loanTierM.setIntRateIndex(rs.getString("INT_RATE_INDEX"));
				loanTierM.setIntRateSign(rs.getString("INT_RATE_SIGN"));
				loanTierM.setIntRateSpread(rs.getBigDecimal("INT_RATE_SPREAD"));
				
				loanTierM.setCreateBy(rs.getString("CREATE_BY"));
				loanTierM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				loanTierM.setUpdateBy(rs.getString("UPDATE_BY"));
				loanTierM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));

				loanTierM.setIntRateAmount(rs.getBigDecimal("INT_RATE_AMOUNT"));
				loanTierList.add(loanTierM);
			}

			return loanTierList;
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
	public void saveUpdateOrigLoanTierM(LoanTierDataM loanTierM)
			throws ApplicationException {
		int returnRows = 0;
		loanTierM.setUpdateBy(userId);
		returnRows = updateTableORIG_LOAN_TIER(loanTierM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_LOAN_TIER then call Insert method");
			loanTierM.setCreateBy(userId);
			createOrigLoanTierM(loanTierM);
		}
	}

	private int updateTableORIG_LOAN_TIER(LoanTierDataM loanTierM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_LOAN_TIER ");
			sql.append(" SET SEQ = ?, RATE_TYPE = ?, EFFECTIVE_RATE = ?, ");
			sql.append(" FLAT_RATE = ?, RATE_AMOUNT = ?, TERM = ?, TERM_TYPE = ?, ");
			sql.append(" MONTHLY_INSTALLMENT = ?, TOTAL_INSTALLMENT = ?, LAST_INSTALLMENT = ?, ");
			sql.append(" INT_RATE_INDEX = ?, INT_RATE_SIGN = ?, INT_RATE_SPREAD = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ?, INT_RATE_AMOUNT = ? ");
			
			sql.append(" WHERE LOAN_ID = ? AND TIER_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int cnt = 1;
			ps.setInt(cnt++, loanTierM.getSeq());
			ps.setString(cnt++, loanTierM.getRateType());
			ps.setBigDecimal(cnt++, loanTierM.getEffectiveRate());
			
			ps.setBigDecimal(cnt++, loanTierM.getFlatRate());
			ps.setBigDecimal(cnt++, loanTierM.getRateAmount());
			ps.setBigDecimal(cnt++, loanTierM.getTerm());
			ps.setString(cnt++, loanTierM.getTermType());
			
			ps.setBigDecimal(cnt++, loanTierM.getMonthlyInstallment());
			ps.setBigDecimal(cnt++, loanTierM.getTotalInstallment());
			ps.setBigDecimal(cnt++, loanTierM.getLastInstallment());
			
			ps.setString(cnt++, loanTierM.getIntRateIndex());
			ps.setString(cnt++, loanTierM.getIntRateSign());
			ps.setBigDecimal(cnt++, loanTierM.getIntRateSpread());
			
			ps.setString(cnt++, loanTierM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setBigDecimal(cnt++, loanTierM.getIntRateAmount());
			
			// WHERE CLAUSE
			ps.setString(cnt++, loanTierM.getLoanId());
			ps.setString(cnt++, loanTierM.getTierId());
			
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
	public void deleteNotInKeyLoanTier(ArrayList<LoanTierDataM> loanTierList,
			String loanId) throws ApplicationException {
		deleteNotInKeyTableORIG_LOAN_TIER(loanTierList, loanId);
	}

	private void deleteNotInKeyTableORIG_LOAN_TIER(
			ArrayList<LoanTierDataM> loanTierList, String loanId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_LOAN_TIER ");
            sql.append(" WHERE LOAN_ID = ? ");
            
            if (!Util.empty(loanTierList)) {
                sql.append(" AND TIER_ID NOT IN ( ");
                for (LoanTierDataM loanTierM: loanTierList) {
                	logger.debug("loanTierM.getTierId() = "+loanTierM.getTierId());
                    sql.append(" '" + loanTierM.getTierId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, loanId);
            
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
