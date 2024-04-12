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
import com.eaf.orig.ulo.model.app.SalaryCertDataM;

public class OrigSalaryCertDAOImpl extends OrigObjectDAO implements
		OrigSalaryCertDAO {
	public OrigSalaryCertDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigSalaryCertDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigSalaryCertM(SalaryCertDataM salaryCertM)
			throws ApplicationException {
		logger.debug("salaryCertM.getSalaryCertId() :"+salaryCertM.getSalaryCertId());
	    if(Util.empty(salaryCertM.getSalaryCertId())){
			String salaryCertId = GenerateUnique.generate(OrigConstant.SeqNames.INC_SALARY_CERT_PK);
			salaryCertM.setSalaryCertId(salaryCertId);
			salaryCertM.setCreateBy(userId);
	    }
	    salaryCertM.setUpdateBy(userId);
		createTableINC_SALARY_CERT(salaryCertM);
	}

	private void createTableINC_SALARY_CERT(SalaryCertDataM salaryCertM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_SALARY_CERT ");
			sql.append("( SALARY_CERT_ID, INCOME_ID, INCOME, COMPANY_NAME, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY , OTHER_INCOME, TOTAL_INCOME) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,? ,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, salaryCertM.getSalaryCertId());
			ps.setString(cnt++, salaryCertM.getIncomeId());	
			ps.setBigDecimal(cnt++, salaryCertM.getIncome());
			ps.setString(cnt++, salaryCertM.getCompanyName());
			ps.setString(cnt++, salaryCertM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, salaryCertM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, salaryCertM.getUpdateBy());
			ps.setBigDecimal(cnt++, salaryCertM.getOtherIncome());
			ps.setBigDecimal(cnt++, salaryCertM.getTotalIncome());
			
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
	public void deleteOrigSalaryCertM(String incomeId, String salaryCertId)
			throws ApplicationException {
		deleteTableINC_SALARY_CERT(incomeId, salaryCertId);
	}

	private void deleteTableINC_SALARY_CERT(String incomeId, String salaryCertId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_SALARY_CERT ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(salaryCertId)) {
				sql.append(" AND SALARY_CERT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(salaryCertId)) {
				ps.setString(2, salaryCertId);
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
	public ArrayList<SalaryCertDataM> loadOrigSalaryCertM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigSalaryCertM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<SalaryCertDataM> loadOrigSalaryCertM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<SalaryCertDataM> result = selectTableINC_SALARY_CERT(incomeId, incomeType);
		return result;
	}

	private ArrayList<SalaryCertDataM> selectTableINC_SALARY_CERT(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<SalaryCertDataM> salaryCertList = new ArrayList<SalaryCertDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT SALARY_CERT_ID, INCOME_ID, INCOME, COMPANY_NAME, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, OTHER_INCOME, TOTAL_INCOME  ");
			sql.append(" FROM INC_SALARY_CERT WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY SALARY_CERT_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				SalaryCertDataM salaryCertM = new SalaryCertDataM();
				salaryCertM.setSeq(salaryCertList.size()+1);
				salaryCertM.setIncomeType(incomeType);
				salaryCertM.setSalaryCertId(rs.getString("SALARY_CERT_ID"));
				salaryCertM.setIncomeId(rs.getString("INCOME_ID"));
				salaryCertM.setIncome(rs.getBigDecimal("INCOME"));
				salaryCertM.setCompanyName(rs.getString("COMPANY_NAME"));
				salaryCertM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				salaryCertM.setCreateBy(rs.getString("CREATE_BY"));
				salaryCertM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				salaryCertM.setUpdateBy(rs.getString("UPDATE_BY"));
				salaryCertM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				salaryCertM.setOtherIncome(rs.getBigDecimal("OTHER_INCOME"));
				salaryCertM.setTotalIncome(rs.getBigDecimal("TOTAL_INCOME"));
				salaryCertList.add(salaryCertM);
			}

			return salaryCertList;
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
	public void saveUpdateOrigSalaryCertM(SalaryCertDataM salaryCertM)
			throws ApplicationException {
		int returnRows = 0;
		salaryCertM.setUpdateBy(userId);
		returnRows = updateTableINC_SALARY_CERT(salaryCertM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_SALARY_CERT then call Insert method");
			salaryCertM.setCreateBy(userId);
			createOrigSalaryCertM(salaryCertM);
		}
	}

	private int updateTableINC_SALARY_CERT(SalaryCertDataM salaryCertM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_SALARY_CERT ");
			sql.append(" SET INCOME = ?, COMPANY_NAME = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? , OTHER_INCOME = ?, TOTAL_INCOME = ? ");
			sql.append(" WHERE SALARY_CERT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setBigDecimal(cnt++, salaryCertM.getIncome());			
			ps.setString(cnt++, salaryCertM.getCompanyName());
			ps.setString(cnt++, salaryCertM.getCompareFlag());
			
			ps.setString(cnt++, salaryCertM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setBigDecimal(cnt++, salaryCertM.getOtherIncome());			
			ps.setBigDecimal(cnt++, salaryCertM.getTotalIncome());			
			// WHERE CLAUSE
			ps.setString(cnt++, salaryCertM.getSalaryCertId());
			
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
	public void deleteNotInKeySalaryCert(
			ArrayList<IncomeCategoryDataM> salaryCertList, String incomeId)
			throws ApplicationException {
		deleteNotInKeyTableINC_SALARY_CERT(salaryCertList, incomeId);
	}

	private void deleteNotInKeyTableINC_SALARY_CERT(
			ArrayList<IncomeCategoryDataM> salaryCertList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_SALARY_CERT ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(salaryCertList)) {
                sql.append(" AND SALARY_CERT_ID NOT IN ( ");

                for (IncomeCategoryDataM salaryCertM: salaryCertList) {
                    sql.append(" '" + salaryCertM.getId() + "' , ");
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
