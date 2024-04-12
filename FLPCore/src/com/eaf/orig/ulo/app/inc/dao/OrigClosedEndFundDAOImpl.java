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
import com.eaf.orig.ulo.model.app.ClosedEndFundDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public class OrigClosedEndFundDAOImpl extends OrigObjectDAO implements
		OrigClosedEndFundDAO {
	public OrigClosedEndFundDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigClosedEndFundDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigClosedEndFundM(ClosedEndFundDataM clsEndFundM)
			throws ApplicationException {
		logger.debug("clsEndFundM.getClsEndFundId() :"+clsEndFundM.getClsEndFundId());
	    if(Util.empty(clsEndFundM.getClsEndFundId())){
			String clsEndFundId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_CLS_END_FUND_PK);
			clsEndFundM.setClsEndFundId(clsEndFundId);
			clsEndFundM.setCreateBy(userId);
	    }
	    clsEndFundM.setUpdateBy(userId);
		createTableINC_CLS_END_FUND(clsEndFundM);
	}

	private void createTableINC_CLS_END_FUND(ClosedEndFundDataM clsEndFundM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_CLS_END_FUND ");
			sql.append("( CLS_END_FUND_ID, INCOME_ID, OPEN_DATE, BANK_CODE, FUND_NAME, ");
			sql.append(" ACCOUNT_NO, ACCOUNT_NAME, HOLDING_RATIO, ACCOUNT_BALANCE, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, clsEndFundM.getClsEndFundId());
			ps.setString(cnt++, clsEndFundM.getIncomeId());
			ps.setDate(cnt++, clsEndFundM.getOpenDate());
			ps.setString(cnt++, clsEndFundM.getBankCode());
			ps.setString(cnt++, clsEndFundM.getFundName());
			
			ps.setString(cnt++, clsEndFundM.getAccountNo());
			ps.setString(cnt++, clsEndFundM.getAccountName());
			ps.setBigDecimal(cnt++, clsEndFundM.getHoldingRatio());
			ps.setBigDecimal(cnt++, clsEndFundM.getAccountBalance());
			ps.setString(cnt++, clsEndFundM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, clsEndFundM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, clsEndFundM.getUpdateBy());
			
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
	public void deleteOrigClosedEndFundM(String incomeId, String clsEndFundId)
			throws ApplicationException {
		deleteTableINC_CLS_END_FUND(incomeId, clsEndFundId);
	}

	private void deleteTableINC_CLS_END_FUND(String incomeId,
			String clsEndFundId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_CLS_END_FUND ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(clsEndFundId)) {
				sql.append(" AND CLS_END_FUND_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(clsEndFundId)) {
				ps.setString(2, clsEndFundId);
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
	public ArrayList<ClosedEndFundDataM> loadOrigClosedEndFundM(
			String incomeId, String incomeType, Connection conn)
			throws ApplicationException {
		return loadOrigClosedEndFundM(incomeId, incomeType);
	}
	@Override
	public ArrayList<ClosedEndFundDataM> loadOrigClosedEndFundM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<ClosedEndFundDataM> result = selectTableINC_CLS_END_FUND(incomeId, incomeType);
		return result;
	}

	private ArrayList<ClosedEndFundDataM> selectTableINC_CLS_END_FUND(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<ClosedEndFundDataM> clsEndList = new ArrayList<ClosedEndFundDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CLS_END_FUND_ID, INCOME_ID, OPEN_DATE, BANK_CODE, FUND_NAME, ");
			sql.append(" ACCOUNT_NO, ACCOUNT_NAME, HOLDING_RATIO, ACCOUNT_BALANCE, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_CLS_END_FUND WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY CLS_END_FUND_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				ClosedEndFundDataM clsEndFundM = new ClosedEndFundDataM();
				clsEndFundM.setSeq(clsEndList.size()+1);
				clsEndFundM.setIncomeType(incomeType);
				clsEndFundM.setClsEndFundId(rs.getString("CLS_END_FUND_ID"));
				clsEndFundM.setIncomeId(rs.getString("INCOME_ID"));
				clsEndFundM.setOpenDate(rs.getDate("OPEN_DATE"));
				clsEndFundM.setBankCode(rs.getString("BANK_CODE"));
				clsEndFundM.setFundName(rs.getString("FUND_NAME"));
				
				clsEndFundM.setAccountNo(rs.getString("ACCOUNT_NO"));
				clsEndFundM.setAccountName(rs.getString("ACCOUNT_NAME"));
				clsEndFundM.setHoldingRatio(rs.getBigDecimal("HOLDING_RATIO"));
				clsEndFundM.setAccountBalance(rs.getBigDecimal("ACCOUNT_BALANCE"));
				clsEndFundM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				clsEndFundM.setCreateBy(rs.getString("CREATE_BY"));
				clsEndFundM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				clsEndFundM.setUpdateBy(rs.getString("UPDATE_BY"));
				clsEndFundM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				clsEndList.add(clsEndFundM);
			}

			return clsEndList;
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
	public void saveUpdateOrigClosedEndFundM(ClosedEndFundDataM clsEndFundM)
			throws ApplicationException {
		int returnRows = 0;
		clsEndFundM.setUpdateBy(userId);
		returnRows = updateTableINC_CLS_END_FUND(clsEndFundM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_CLS_END_FUND then call Insert method");
			clsEndFundM.setCreateBy(userId);
			createOrigClosedEndFundM(clsEndFundM);
		}
	}

	private int updateTableINC_CLS_END_FUND(ClosedEndFundDataM clsEndFundM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_CLS_END_FUND ");
			sql.append(" SET OPEN_DATE = ?, BANK_CODE = ?, FUND_NAME = ?, ACCOUNT_NO = ?, ");
			sql.append(" ACCOUNT_NAME = ?, HOLDING_RATIO = ?, ACCOUNT_BALANCE = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE CLS_END_FUND_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setDate(cnt++, clsEndFundM.getOpenDate());
			ps.setString(cnt++, clsEndFundM.getBankCode());
			ps.setString(cnt++, clsEndFundM.getFundName());			
			ps.setString(cnt++, clsEndFundM.getAccountNo());
			
			ps.setString(cnt++, clsEndFundM.getAccountName());
			ps.setBigDecimal(cnt++, clsEndFundM.getHoldingRatio());
			ps.setBigDecimal(cnt++, clsEndFundM.getAccountBalance());
			ps.setString(cnt++, clsEndFundM.getCompareFlag());
			
			ps.setString(cnt++, clsEndFundM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, clsEndFundM.getClsEndFundId());
			
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
	public void deleteNotInKeyClosedEndFund(
			ArrayList<IncomeCategoryDataM> clsEndFundList, String incomeId)
			throws ApplicationException {
		deleteNotInKeyTableINC_CLS_END_FUND(clsEndFundList, incomeId);
	}

	private void deleteNotInKeyTableINC_CLS_END_FUND(
			ArrayList<IncomeCategoryDataM> clsEndFundList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_CLS_END_FUND ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(clsEndFundList)) {
                sql.append(" AND CLS_END_FUND_ID NOT IN ( ");

                for (IncomeCategoryDataM clsEndFundM: clsEndFundList) {
                    sql.append(" '" + clsEndFundM.getId() + "' , ");
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
