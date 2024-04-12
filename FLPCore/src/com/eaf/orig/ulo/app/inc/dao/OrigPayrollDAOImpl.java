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
import com.eaf.orig.ulo.model.app.PayrollDataM;

public class OrigPayrollDAOImpl extends OrigObjectDAO implements OrigPayrollDAO {
	public OrigPayrollDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPayrollDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPayrollM(PayrollDataM payrollM)
			throws ApplicationException {
		logger.debug("payrollM.getPayrollId() :"+payrollM.getPayrollId());
	    if(Util.empty(payrollM.getPayrollId())){
			String payrollId = GenerateUnique.generate(OrigConstant.SeqNames.INC_PAYROLL_PK);
			payrollM.setPayrollId(payrollId);
			payrollM.setCreateBy(userId);
	    }
	    payrollM.setUpdateBy(userId);
		createTableINC_PAYROLL(payrollM);
	}

	private void createTableINC_PAYROLL(PayrollDataM payrollM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_PAYROLL ");
			sql.append("( PAYROLL_ID, INCOME_ID, INCOME, ");
//			sql.append("EDITABLE, MONTH, YEAR, ");
			sql.append(" COMPARE_FLAG, NO_OF_EMPLOYEE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?, ?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, payrollM.getPayrollId());
			ps.setString(cnt++, payrollM.getIncomeId());
			ps.setBigDecimal(cnt++, payrollM.getIncome());		
//			ps.setString(cnt++, payrollM.getEditable());		
//			ps.setString(cnt++, payrollM.getMonth());
//			ps.setString(cnt++, payrollM.getYear());
			ps.setString(cnt++, payrollM.getCompareFlag());
			ps.setInt(cnt++, payrollM.getNoOfEmployee());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payrollM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payrollM.getUpdateBy());
			
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
	public void deleteOrigPayrollM(String incomeId, String payrollId)
			throws ApplicationException {
		deleteTableINC_PAYROLL(incomeId, payrollId);
	}

	private void deleteTableINC_PAYROLL(String incomeId, String payrollId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_PAYROLL ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(payrollId)) {
				sql.append(" AND PAYROLL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(payrollId)) {
				ps.setString(2, payrollId);
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
	public ArrayList<PayrollDataM> loadOrigPayrollM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigPayrollM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<PayrollDataM> loadOrigPayrollM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<PayrollDataM> result = selectTableINC_PAYROLL(incomeId, incomeType);
		return result;
	}

	private ArrayList<PayrollDataM> selectTableINC_PAYROLL(String incomeId, String incomeType) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PayrollDataM> payrollList = new ArrayList<PayrollDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYROLL_ID, INCOME_ID, ");
			sql.append(" INCOME, COMPARE_FLAG, NO_OF_EMPLOYEE, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_PAYROLL WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY PAYROLL_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				PayrollDataM payrollM = new PayrollDataM();
				payrollM.setSeq(payrollList.size()+1);
				payrollM.setIncomeType(incomeType);
				payrollM.setPayrollId(rs.getString("PAYROLL_ID"));
				payrollM.setIncomeId(rs.getString("INCOME_ID"));
//				payrollM.setYear(rs.getString("YEAR"));
//				payrollM.setMonth(rs.getString("MONTH"));
				payrollM.setIncome(rs.getBigDecimal("INCOME"));
//				payrollM.setEditable(rs.getString("EDITABLE"));
				payrollM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				payrollM.setNoOfEmployee(rs.getInt("NO_OF_EMPLOYEE"));
				
				payrollM.setCreateBy(rs.getString("CREATE_BY"));
				payrollM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				payrollM.setUpdateBy(rs.getString("UPDATE_BY"));
				payrollM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				payrollList.add(payrollM);
			}

			return payrollList;
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
	public void saveUpdateOrigPayrollM(PayrollDataM payrollM)
			throws ApplicationException {
		int returnRows = 0;
		payrollM.setUpdateBy(userId);
		returnRows = updateTableINC_PAYROLL(payrollM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_PAYROLL then call Insert method");
			payrollM.setCreateBy(userId);
			createOrigPayrollM(payrollM);
		}
	}

	private int updateTableINC_PAYROLL(PayrollDataM payrollM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_PAYROLL ");
			sql.append(" SET INCOME = ?, COMPARE_FLAG = ?, NO_OF_EMPLOYEE = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE PAYROLL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
//			ps.setString(cnt++, payrollM.getMonth());
//			ps.setString(cnt++, payrollM.getYear());
//			ps.setString(cnt++, payrollM.getEditable());
			ps.setBigDecimal(cnt++, payrollM.getIncome());
			ps.setString(cnt++, payrollM.getCompareFlag());
			ps.setInt(cnt++, payrollM.getNoOfEmployee());
			
			ps.setString(cnt++, payrollM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, payrollM.getPayrollId());
			
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
	public void deleteNotInKeyPayroll(ArrayList<IncomeCategoryDataM> payrollList,
			String incomeId) throws ApplicationException {
		deleteNotInKeyTableINC_PAYROLL(payrollList, incomeId);
	}

	private void deleteNotInKeyTableINC_PAYROLL(
			ArrayList<IncomeCategoryDataM> payrollList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_PAYROLL ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(payrollList)) {
                sql.append(" AND PAYROLL_ID NOT IN ( ");

                for (IncomeCategoryDataM payrollM: payrollList) {
                    sql.append(" '" + payrollM.getId() + "' , ");
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
