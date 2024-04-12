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
import com.eaf.orig.ulo.model.app.LoanFeeDataM;

public class OrigLoanFeeDAOImpl extends OrigObjectDAO implements OrigLoanFeeDAO {
	public OrigLoanFeeDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigLoanFeeDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigLoanFeeM(LoanFeeDataM loanFeeM)
			throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("loanFeeM.getLoanFeeId() :"+loanFeeM.getLoanFeeId());
		    if(Util.empty(loanFeeM.getLoanFeeId())){
				String loanFeeId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_FEE_PK );
				loanFeeM.setLoanFeeId(loanFeeId);
				loanFeeM.setCreateBy(userId);
		    }
		    loanFeeM.setUpdateBy(userId);
			createTableORIG_LOAN_FEE(loanFeeM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_LOAN_FEE(LoanFeeDataM loanFeeM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_LOAN_FEE ");
			sql.append("( LOAN_FEE_ID, SEQ, REF_ID, FEE_LEVEL_TYPE, ");
			sql.append(" FEE_TYPE, MIN_FEE, MAX_FEE, FEE, FEE_CALC_TYPE, ");
			sql.append(" FINAL_FEE_AMOUNT, FEE_SRC, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" START_MONTH, END_MONTH ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,?,  ?,?,?,?,   ?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, loanFeeM.getLoanFeeId());
			ps.setInt(cnt++, loanFeeM.getSeq());
			ps.setString(cnt++, loanFeeM.getRefId());
			ps.setString(cnt++, loanFeeM.getFeeLevelType());
			
			ps.setString(cnt++, loanFeeM.getFeeType());
			ps.setBigDecimal(cnt++, loanFeeM.getMinFee());
			ps.setBigDecimal(cnt++, loanFeeM.getMaxFee());
			ps.setBigDecimal(cnt++, loanFeeM.getFee());
			ps.setString(cnt++, loanFeeM.getFeeCalcType());
			
			ps.setBigDecimal(cnt++, loanFeeM.getFinalFeeAmount());
			ps.setString(cnt++, loanFeeM.getFeeSrc());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, loanFeeM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, loanFeeM.getUpdateBy());
			
			setInteger(ps, cnt++, loanFeeM.getStartMonth());
			setInteger(ps, cnt++, loanFeeM.getEndMonth());
			
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
	public void deleteOrigLoanFeeM(String refId, String refLevel, String loanFeeId)
			throws ApplicationException {
		deleteTableORIG_LOAN_FEE(refId, refLevel, loanFeeId);
	}

	private void deleteTableORIG_LOAN_FEE(String refId, String refLevel,
			String loanFeeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_LOAN_FEE ");
			sql.append(" WHERE REF_ID = ? AND FEE_LEVEL_TYPE = ? ");
			if(!Util.empty(loanFeeId)) {
				sql.append(" AND LOAN_FEE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, refId);
			ps.setString(2, refLevel);
			if(!Util.empty(loanFeeId)) {
				ps.setString(3, loanFeeId);
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
	public ArrayList<LoanFeeDataM> loadOrigLoanFeeM(String refId, String refLevel)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_LOAN_FEE(refId, refLevel,conn);
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
	public ArrayList<LoanFeeDataM> loadOrigLoanFeeM(String refId,
			String refLevel, Connection conn) throws ApplicationException {
		return selectTableORIG_LOAN_FEE(refId, refLevel, conn);
	}
	private ArrayList<LoanFeeDataM> selectTableORIG_LOAN_FEE(String refId,
			String refLevel,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<LoanFeeDataM> loanFeeList = new ArrayList<LoanFeeDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT LOAN_FEE_ID, SEQ, REF_ID, FEE_LEVEL_TYPE, ");
			sql.append(" FEE_TYPE, MIN_FEE, MAX_FEE, FEE, FEE_CALC_TYPE, ");
			sql.append(" FINAL_FEE_AMOUNT, FEE_SRC, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" START_MONTH, END_MONTH ");
			sql.append(" FROM ORIG_LOAN_FEE WHERE REF_ID = ? AND FEE_LEVEL_TYPE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, refId);
			ps.setString(2, refLevel);

			rs = ps.executeQuery();

			while(rs.next()) {
				LoanFeeDataM loanFeeM = new LoanFeeDataM();
				loanFeeM.setLoanFeeId(rs.getString("LOAN_FEE_ID"));
				loanFeeM.setSeq(rs.getInt("SEQ"));
				loanFeeM.setRefId(rs.getString("REF_ID"));
				loanFeeM.setFeeLevelType(rs.getString("FEE_LEVEL_TYPE"));
				
				loanFeeM.setFeeType(rs.getString("FEE_TYPE"));
				loanFeeM.setMinFee(rs.getBigDecimal("MIN_FEE"));
				loanFeeM.setMaxFee(rs.getBigDecimal("MAX_FEE"));
				loanFeeM.setFee(rs.getBigDecimal("FEE"));
				loanFeeM.setFeeCalcType(rs.getString("FEE_CALC_TYPE"));
				
				loanFeeM.setFinalFeeAmount(rs.getBigDecimal("FINAL_FEE_AMOUNT"));
				loanFeeM.setFeeSrc(rs.getString("FEE_SRC"));
				
				loanFeeM.setCreateBy(rs.getString("CREATE_BY"));
				loanFeeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				loanFeeM.setUpdateBy(rs.getString("UPDATE_BY"));
				loanFeeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				loanFeeM.setStartMonth(getInteger(rs,"START_MONTH"));
				loanFeeM.setEndMonth(getInteger(rs,"END_MONTH"));
				
				loanFeeList.add(loanFeeM);
			}

			return loanFeeList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection( rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigLoanFeeM(LoanFeeDataM loanFeeM)
			throws ApplicationException {
		int returnRows = 0;
		loanFeeM.setUpdateBy(userId);
		returnRows = updateTableORIG_LOAN_FEE(loanFeeM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_LOAN_FEE then call Insert method");
			loanFeeM.setCreateBy(userId);
			createOrigLoanFeeM(loanFeeM);
		}
	}

	private int updateTableORIG_LOAN_FEE(LoanFeeDataM loanFeeM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_LOAN_FEE ");
			sql.append(" SET SEQ = ?, FEE_TYPE = ?, MIN_FEE = ?, MAX_FEE = ?, FEE = ?, ");
			sql.append(" FEE_CALC_TYPE = ?, FINAL_FEE_AMOUNT = ?, FEE_SRC = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ?, ");
			sql.append(" START_MONTH = ?, END_MONTH = ? ");
			
			sql.append(" WHERE REF_ID = ? AND FEE_LEVEL_TYPE = ? AND LOAN_FEE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int cnt = 1;
			ps.setInt(cnt++, loanFeeM.getSeq());
			ps.setString(cnt++, loanFeeM.getFeeType());
			ps.setBigDecimal(cnt++, loanFeeM.getMinFee());
			ps.setBigDecimal(cnt++, loanFeeM.getMaxFee());
			ps.setBigDecimal(cnt++, loanFeeM.getFee());
			
			ps.setString(cnt++, loanFeeM.getFeeCalcType());
			ps.setBigDecimal(cnt++, loanFeeM.getFinalFeeAmount());
			ps.setString(cnt++, loanFeeM.getFeeSrc());
			
			ps.setString(cnt++, loanFeeM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			setInteger(ps, cnt++, loanFeeM.getStartMonth());
			setInteger(ps, cnt++, loanFeeM.getEndMonth());
			
			// WHERE CLAUSE
			ps.setString(cnt++, loanFeeM.getRefId());
			ps.setString(cnt++, loanFeeM.getFeeLevelType());
			ps.setString(cnt++, loanFeeM.getLoanFeeId());
			
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
	public void deleteNotInKeyLoanFee(ArrayList<LoanFeeDataM> loanFeeList,
			String refId, String refLevel) throws ApplicationException {
		deleteNotInKeyTableORIG_LOAN_FEE(loanFeeList, refId, refLevel);
	}

	private void deleteNotInKeyTableORIG_LOAN_FEE(
			ArrayList<LoanFeeDataM> loanFeeList, String refId, String refLevel) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_LOAN_FEE ");
            sql.append(" WHERE REF_ID = ? AND FEE_LEVEL_TYPE = ? ");
            
            if (!Util.empty(loanFeeList)) {
                sql.append(" AND LOAN_FEE_ID NOT IN ( ");
                for (LoanFeeDataM loanFeeM: loanFeeList) {
                	logger.debug("loanFeeM.getLoanFeeId() = "+loanFeeM.getLoanFeeId());
                    sql.append(" '" + loanFeeM.getLoanFeeId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, refId);
            ps.setString(2, refLevel);
            
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
