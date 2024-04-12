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
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.PayrollFileDataM;

public class OrigPayrollFileDAOImpl extends OrigObjectDAO implements
		OrigPayrollFileDAO {
	public OrigPayrollFileDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPayrollFileDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPayrollFileM(PayrollFileDataM payrollFileM)
			throws ApplicationException {
		logger.debug("payrollFileM.getPayrollFileId() :"+payrollFileM.getPayrollFileId());
	    if(Util.empty(payrollFileM.getPayrollFileId())){
			String payrollFileId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_PAYROLL_FILE_PK);
			payrollFileM.setPayrollFileId(payrollFileId);
			payrollFileM.setCreateBy(userId);
	    }
	    payrollFileM.setUpdateBy(userId);
		createTableINC_PAYROLL_FILE(payrollFileM);
	}

	private void createTableINC_PAYROLL_FILE(PayrollFileDataM payrollFileM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_PAYROLL_FILE ");
			sql.append("( PAYROLL_FILE_ID, INCOME_ID, MONTH, YEAR,  ");
			sql.append(" AMOUNT, FROM_FILE_FLAG,  ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, payrollFileM.getPayrollFileId());
			ps.setString(cnt++, payrollFileM.getIncomeId());		
			ps.setString(cnt++, payrollFileM.getMonth());
			ps.setString(cnt++, payrollFileM.getYear());
			
			ps.setBigDecimal(cnt++, payrollFileM.getAmount());
			ps.setString(cnt++, payrollFileM.getFromFileFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payrollFileM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payrollFileM.getUpdateBy());
			
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
	public void deleteOrigPayrollFileM(String incomeId, String payrollFileId)
			throws ApplicationException {
		deleteTableINC_PAYROLL_FILE(incomeId, payrollFileId);
	}

	private void deleteTableINC_PAYROLL_FILE(String incomeId,
			String payrollFileId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_PAYROLL_FILE ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(payrollFileId)) {
				sql.append(" AND PAYROLL_FILE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(payrollFileId)) {
				ps.setString(2, payrollFileId);
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
	public ArrayList<PayrollFileDataM> loadOrigPayrollFileM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigPayrollFileM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<PayrollFileDataM> loadOrigPayrollFileM(String incomeId,
			String incomeType) throws ApplicationException {
		ArrayList<PayrollFileDataM> result = selectTableINC_PAYROLL_FILE(incomeId, incomeType);
		return result;
	}

	private ArrayList<PayrollFileDataM> selectTableINC_PAYROLL_FILE(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PayrollFileDataM> payrollFileList = new ArrayList<PayrollFileDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYROLL_FILE_ID, INCOME_ID, MONTH, YEAR,  ");
			sql.append(" AMOUNT, FROM_FILE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_PAYROLL_FILE WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY PAYROLL_FILE_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				PayrollFileDataM payrollFileM = new PayrollFileDataM();
				payrollFileM.setSeq(payrollFileList.size()+1);
				payrollFileM.setPayrollFileId(rs.getString("PAYROLL_FILE_ID"));
				payrollFileM.setIncomeId(rs.getString("INCOME_ID"));
				payrollFileM.setMonth(rs.getString("MONTH"));
				payrollFileM.setYear(rs.getString("YEAR"));
				
				payrollFileM.setAmount(rs.getBigDecimal("AMOUNT"));
				payrollFileM.setFromFileFlag(rs.getString("FROM_FILE_FLAG"));
				
				payrollFileM.setCreateBy(rs.getString("CREATE_BY"));
				payrollFileM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				payrollFileM.setUpdateBy(rs.getString("UPDATE_BY"));
				payrollFileM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				payrollFileList.add(payrollFileM);
			}

			return payrollFileList;
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
	public void saveUpdateOrigPayrollFileM(PayrollFileDataM payrollFileM)
			throws ApplicationException {
		int returnRows = 0;
		payrollFileM.setUpdateBy(userId);
		returnRows = updateTableINC_PAYROLL_FILE(payrollFileM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_PAYROLL_FILE then call Insert method");
			payrollFileM.setCreateBy(userId);
			createOrigPayrollFileM(payrollFileM);
		}
	}

	private int updateTableINC_PAYROLL_FILE(PayrollFileDataM payrollFileM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_PAYROLL_FILE ");
			sql.append(" SET MONTH = ?, YEAR = ?,  ");
			sql.append(" AMOUNT = ?, FROM_FILE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE PAYROLL_FILE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, payrollFileM.getMonth());
			ps.setString(cnt++, payrollFileM.getYear());
			
			ps.setBigDecimal(cnt++, payrollFileM.getAmount());
			ps.setString(cnt++, payrollFileM.getFromFileFlag());
			
			ps.setString(cnt++, payrollFileM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, payrollFileM.getPayrollFileId());
			
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
	public void deleteNotInKeyPayrollFile(
			ArrayList<IncomeCategoryDataM> payrollFileList, String incomeId)
			throws ApplicationException {
		deleteNotInKeyTableINC_PAYROLL_FILE(payrollFileList, incomeId);
	}

	private void deleteNotInKeyTableINC_PAYROLL_FILE(
			ArrayList<IncomeCategoryDataM> payrollFileList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_PAYROLL_FILE ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(payrollFileList)) {
                sql.append(" AND PAYROLL_FILE_ID NOT IN ( ");

                for (IncomeCategoryDataM payslipMonthlyM: payrollFileList) {
                    sql.append(" '" + payslipMonthlyM.getId() + "' , ");
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
