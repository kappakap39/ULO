package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public class OrigYearlyTawiDAOImpl extends OrigObjectDAO implements
		OrigYearlyTawiDAO {
	public OrigYearlyTawiDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigYearlyTawiDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigYearlyTawiM(YearlyTawi50DataM yearlyTawiM)
			throws ApplicationException {
		logger.debug("yearlyTawiM.getYearlyTawiId() :"+yearlyTawiM.getYearlyTawiId());
	    if(Util.empty(yearlyTawiM.getYearlyTawiId())){
			String taweesapId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_YEARLY_TAWI_PK);
			yearlyTawiM.setYearlyTawiId(taweesapId);			
		    yearlyTawiM.setCreateBy(userId);
	    }
	    yearlyTawiM.setUpdateBy(userId);
		createTableINC_YEARLY_TAWI(yearlyTawiM);
	}

	private void createTableINC_YEARLY_TAWI(YearlyTawi50DataM yearlyTawiM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_YEARLY_TAWI ");
			sql.append("( YEARLY_TAWI_ID, INCOME_ID, COMPANY_NAME, YEAR, NO_MONTH, ");
			sql.append(" INCOME40_1, INCOME40_2, SUM_SSO, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?, ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, yearlyTawiM.getYearlyTawiId());
			ps.setString(cnt++, yearlyTawiM.getIncomeId());	
			ps.setString(cnt++, yearlyTawiM.getCompanyName());
			ps.setInt(cnt++, yearlyTawiM.getYear()- FormatUtil.BUDDIST_ERA_MARKER);
			ps.setInt(cnt++, yearlyTawiM.getMonth());
			
			ps.setBigDecimal(cnt++, yearlyTawiM.getIncome401());
			ps.setBigDecimal(cnt++, yearlyTawiM.getIncome402());
			ps.setBigDecimal(cnt++, yearlyTawiM.getSumSso());
			ps.setString(cnt++, yearlyTawiM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, yearlyTawiM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, yearlyTawiM.getUpdateBy());
			
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
	public void deleteOrigYearlyTawiM(String incomeId, String yearlyTawiId)
			throws ApplicationException {
		deleteTableINC_YEARLY_TAWI(incomeId, yearlyTawiId);
	}

	private void deleteTableINC_YEARLY_TAWI(String incomeId, String yearlyTawiId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_YEARLY_TAWI ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(yearlyTawiId)) {
				sql.append(" AND YEARLY_TAWI_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(yearlyTawiId)) {
				ps.setString(2, yearlyTawiId);
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
	public ArrayList<YearlyTawi50DataM> loadOrigYearlyTawiM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigYearlyTawiM(incomeId, incomeType);
	}
	@Override
	public ArrayList<YearlyTawi50DataM> loadOrigYearlyTawiM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<YearlyTawi50DataM> result = selectTableINC_YEARLY_TAWI(incomeId, incomeType);
		return result;
	}

	private ArrayList<YearlyTawi50DataM> selectTableINC_YEARLY_TAWI(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<YearlyTawi50DataM> yearlyTawiList = new ArrayList<YearlyTawi50DataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT YEARLY_TAWI_ID, INCOME_ID, COMPANY_NAME, YEAR, NO_MONTH, ");
			sql.append(" INCOME40_1, INCOME40_2, SUM_SSO, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_YEARLY_TAWI WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY YEARLY_TAWI_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				YearlyTawi50DataM yearlyTawiM = new YearlyTawi50DataM();
				yearlyTawiM.setSeq(yearlyTawiList.size()+1);
				yearlyTawiM.setIncomeType(incomeType);
				yearlyTawiM.setYearlyTawiId(rs.getString("YEARLY_TAWI_ID"));
				yearlyTawiM.setIncomeId(rs.getString("INCOME_ID"));
				yearlyTawiM.setCompanyName(rs.getString("COMPANY_NAME"));
				yearlyTawiM.setYear(rs.getInt("YEAR")+ FormatUtil.BUDDIST_ERA_MARKER);
				yearlyTawiM.setMonth(rs.getInt("NO_MONTH"));
				
				yearlyTawiM.setIncome401(rs.getBigDecimal("INCOME40_1"));
				yearlyTawiM.setIncome402(rs.getBigDecimal("INCOME40_2"));
				yearlyTawiM.setSumSso(rs.getBigDecimal("SUM_SSO"));
				yearlyTawiM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				yearlyTawiM.setCreateBy(rs.getString("CREATE_BY"));
				yearlyTawiM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				yearlyTawiM.setUpdateBy(rs.getString("UPDATE_BY"));
				yearlyTawiM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				yearlyTawiList.add(yearlyTawiM);
			}

			return yearlyTawiList;
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
	public void saveUpdateOrigYearlyTawiM(YearlyTawi50DataM yearlyTawiM)
			throws ApplicationException {
		int returnRows = 0;
		yearlyTawiM.setUpdateBy(userId);
		returnRows = updateTableINC_YEARLY_TAWI(yearlyTawiM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_YEARLY_TAWI then call Insert method");
			yearlyTawiM.setCreateBy(userId);
			createOrigYearlyTawiM(yearlyTawiM);
		}
	}

	private int updateTableINC_YEARLY_TAWI(YearlyTawi50DataM yearlyTawiM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_YEARLY_TAWI ");
			sql.append(" SET COMPANY_NAME = ?, YEAR =?, NO_MONTH =?, INCOME40_1 = ?, INCOME40_2 = ?, ");
			sql.append(" SUM_SSO = ?, COMPARE_FLAG = ?, UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE YEARLY_TAWI_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, yearlyTawiM.getCompanyName());
			ps.setInt(cnt++, yearlyTawiM.getYear()- FormatUtil.BUDDIST_ERA_MARKER);
			ps.setInt(cnt++, yearlyTawiM.getMonth());
			ps.setBigDecimal(cnt++, yearlyTawiM.getIncome401());		
			ps.setBigDecimal(cnt++, yearlyTawiM.getIncome402());
			
			ps.setBigDecimal(cnt++, yearlyTawiM.getSumSso());			
			ps.setString(cnt++, yearlyTawiM.getCompareFlag());
			ps.setString(cnt++, yearlyTawiM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, yearlyTawiM.getYearlyTawiId());
			
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
	public void deleteNotInKeyYearlyTawi(
			ArrayList<IncomeCategoryDataM> yearlyTawiMList, String incomeId)
			throws ApplicationException {
		deleteNotInKeyTableINC_YEARLY_TAWI(yearlyTawiMList, incomeId);
	}

	private void deleteNotInKeyTableINC_YEARLY_TAWI(
			ArrayList<IncomeCategoryDataM> yearlyTawiMList, String incomeId) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_YEARLY_TAWI ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(yearlyTawiMList)) {
                sql.append(" AND YEARLY_TAWI_ID NOT IN ( ");

                for (IncomeCategoryDataM yearlyTawiM: yearlyTawiMList) {
                    sql.append(" '" + yearlyTawiM.getId() + "' , ");
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
