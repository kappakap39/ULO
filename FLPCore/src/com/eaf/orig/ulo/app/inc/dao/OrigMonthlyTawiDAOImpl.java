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
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DetailDataM;

public class OrigMonthlyTawiDAOImpl extends OrigObjectDAO implements
		OrigMonthlyTawiDAO {
	public OrigMonthlyTawiDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigMonthlyTawiDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	private String userId = "";
	@Override
	public void createOrigMonthlyTawiM(MonthlyTawi50DataM monthlyTawiM) throws ApplicationException {
		logger.debug("monthlyTawiM.getMonthlyTawiId() :"+monthlyTawiM.getMonthlyTawiId());
	    if(Util.empty(monthlyTawiM.getMonthlyTawiId())){
			String monthlyTawiId = GenerateUnique.generate(OrigConstant.SeqNames.INC_MONTHLY_TAWI_PK);
			monthlyTawiM.setMonthlyTawiId(monthlyTawiId);
			monthlyTawiM.setCreateBy(userId);
	    }
	    monthlyTawiM.setUpdateBy(userId);
		createTableINC_MONTHLY_TAWI(monthlyTawiM);
		
		ArrayList<MonthlyTawi50DetailDataM> detailList = monthlyTawiM.getMonthlyTaxi50Details();
		if(!Util.empty(detailList)) {
			OrigMonthlyTawiDetailDAO detailDAO = ORIGDAOFactory.getMonthlyTawiDetailDAO(userId);
			for(MonthlyTawi50DetailDataM detailM: detailList) {
				detailM.setMonthlyTawiId(monthlyTawiM.getMonthlyTawiId());
				detailDAO.createOrigMonthlyTawiDetailM(detailM);
			}
		}
	}

	private void createTableINC_MONTHLY_TAWI(MonthlyTawi50DataM monthlyTawiM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_MONTHLY_TAWI ");
			sql.append("( MONTHLY_TAWI_ID, INCOME_ID, INCOME_TYPE, COMPANY_NAME, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, monthlyTawiM.getMonthlyTawiId());
			ps.setString(cnt++, monthlyTawiM.getIncomeId());		
			ps.setString(cnt++, monthlyTawiM.getTawiIncomeType());
			ps.setString(cnt++, monthlyTawiM.getCompanyName());
			ps.setString(cnt++, monthlyTawiM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, monthlyTawiM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, monthlyTawiM.getUpdateBy());
			
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
	public void deleteOrigMonthlyTawiM(String incomeId, String monthlyTawiId) throws ApplicationException {
		OrigMonthlyTawiDetailDAO detailDAO = ORIGDAOFactory.getMonthlyTawiDetailDAO();
		if(Util.empty(monthlyTawiId)) {
			ArrayList<String> monthlyTawiForIncome = selectMonthlyTawiIdINC_MONTHLY_TAWI(incomeId);
			if(!Util.empty(monthlyTawiForIncome)) {
				for(String monthlyTawiID: monthlyTawiForIncome) {
					detailDAO.deleteOrigMonthlyTawiDetailM(monthlyTawiID, null);
				}
			}
		} else {
			detailDAO.deleteOrigMonthlyTawiDetailM(monthlyTawiId, null);
		}
		
		deleteTableINC_MONTHLY_TAWI(incomeId, monthlyTawiId);
	}

	private ArrayList<String> selectMonthlyTawiIdINC_MONTHLY_TAWI(
			String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> monthlyList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT MONTHLY_TAWI_ID ");
			sql.append(" FROM INC_MONTHLY_TAWI WHERE INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				monthlyList.add(rs.getString("MONTHLY_TAWI_ID"));
			}

			return monthlyList;
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

	private void deleteTableINC_MONTHLY_TAWI(String incomeId, String monthlyTawiId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_MONTHLY_TAWI ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(monthlyTawiId)) {
				sql.append(" AND MONTHLY_TAWI_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(monthlyTawiId)) {
				ps.setString(2, monthlyTawiId);
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
	public ArrayList<MonthlyTawi50DataM> loadOrigMonthlyTawiM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigMonthlyTawiM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<MonthlyTawi50DataM> loadOrigMonthlyTawiM(String incomeId, String incomeType) throws ApplicationException {
		ArrayList<MonthlyTawi50DataM> result = selectTableINC_MONTHLY_TAWI(incomeId, incomeType);

		if(!Util.empty(result)) {
			OrigMonthlyTawiDetailDAO detailDAO = ORIGDAOFactory.getMonthlyTawiDetailDAO();
			for(MonthlyTawi50DataM monthlyM: result) {
				ArrayList<MonthlyTawi50DetailDataM> detailList = detailDAO.
						loadOrigMonthlyTawiDetailM(monthlyM.getMonthlyTawiId());
				if(!Util.empty(detailList)) {
					monthlyM.setMonthlyTaxi50Details(detailList);					
				}
			}
		}
		return result;
	}

	private ArrayList<MonthlyTawi50DataM> selectTableINC_MONTHLY_TAWI(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<MonthlyTawi50DataM> monthlyTawiList = new ArrayList<MonthlyTawi50DataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT MONTHLY_TAWI_ID, INCOME_ID, INCOME_TYPE, COMPANY_NAME, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_MONTHLY_TAWI WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY MONTHLY_TAWI_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				MonthlyTawi50DataM monthlyTawiM = new MonthlyTawi50DataM();
				monthlyTawiM.setSeq(monthlyTawiList.size()+1);
				monthlyTawiM.setIncomeType(incomeType);
				monthlyTawiM.setMonthlyTawiId(rs.getString("MONTHLY_TAWI_ID"));
				monthlyTawiM.setIncomeId(rs.getString("INCOME_ID"));		
				monthlyTawiM.setTawiIncomeType(rs.getString("INCOME_TYPE"));
				monthlyTawiM.setCompanyName(rs.getString("COMPANY_NAME"));
				monthlyTawiM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				monthlyTawiM.setCreateBy(rs.getString("CREATE_BY"));
				monthlyTawiM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				monthlyTawiM.setUpdateBy(rs.getString("UPDATE_BY"));
				monthlyTawiM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				monthlyTawiList.add(monthlyTawiM);
			}

			return monthlyTawiList;
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
	public void saveUpdateOrigMonthlyTawiM(MonthlyTawi50DataM monthlyTawiM) throws ApplicationException {
		int returnRows = 0;
		monthlyTawiM.setUpdateBy(userId);
		returnRows = updateTableINC_MONTHLY_TAWI(monthlyTawiM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_MONTHLY_TAWI then call Insert method");
			monthlyTawiM.setCreateBy(userId);
			createOrigMonthlyTawiM(monthlyTawiM);
		} else {
		
			ArrayList<MonthlyTawi50DetailDataM> detailList = monthlyTawiM.getMonthlyTaxi50Details();
			OrigMonthlyTawiDetailDAO detailDAO = ORIGDAOFactory.getMonthlyTawiDetailDAO(userId);
			if(!Util.empty(detailList)) {
				for(MonthlyTawi50DetailDataM detailM: detailList) {
					detailM.setMonthlyTawiId(monthlyTawiM.getMonthlyTawiId());
					detailDAO.saveUpdateOrigMonthlyTawiDetailM(detailM);
				}
			}
			detailDAO.deleteNotInKeyMonthlyTawiDetail(detailList, monthlyTawiM.getMonthlyTawiId());
		}
	}

	private int updateTableINC_MONTHLY_TAWI(MonthlyTawi50DataM monthlyTawiM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_MONTHLY_TAWI ");
			sql.append(" SET INCOME_TYPE = ?, COMPANY_NAME = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE MONTHLY_TAWI_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, monthlyTawiM.getTawiIncomeType());
			ps.setString(cnt++, monthlyTawiM.getCompanyName());
			ps.setString(cnt++, monthlyTawiM.getCompareFlag());
			
			ps.setString(cnt++, monthlyTawiM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, monthlyTawiM.getMonthlyTawiId());
			
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
	public void deleteNotInKeyMonthlyTawi(
			ArrayList<IncomeCategoryDataM> monthlyTawiMList, String incomeId) throws ApplicationException {
		ArrayList<String> monthlyList = selectNotInKeyTableINC_MONTHLY_TAWI(monthlyTawiMList, incomeId);
		if(!Util.empty(monthlyList)) {
			OrigMonthlyTawiDetailDAO detailDAO = ORIGDAOFactory.getMonthlyTawiDetailDAO();
			for(String monthlyId: monthlyList) {
				detailDAO.deleteOrigMonthlyTawiDetailM(monthlyId, null);
			}
		}
		
		deleteNotInKeyTableINC_MONTHLY_TAWI(monthlyTawiMList, incomeId);
	}

	private ArrayList<String> selectNotInKeyTableINC_MONTHLY_TAWI(
			ArrayList<IncomeCategoryDataM> monthlyTawiMList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> monthlyList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT MONTHLY_TAWI_ID ");
			sql.append(" FROM INC_MONTHLY_TAWI WHERE INCOME_ID = ? ");
			if (!Util.empty(monthlyTawiMList)) {
                sql.append(" AND MONTHLY_TAWI_ID NOT IN ( ");

                for (IncomeCategoryDataM monthlyM: monthlyTawiMList) {
                    sql.append(" '" + monthlyM.getId() + "' , ");
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
				String monthlyId = rs.getString("MONTHLY_TAWI_ID");
				monthlyList.add(monthlyId);
			}

			return monthlyList;
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

	private void deleteNotInKeyTableINC_MONTHLY_TAWI(
			ArrayList<IncomeCategoryDataM> monthlyTawiMList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_MONTHLY_TAWI ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(monthlyTawiMList)) {
                sql.append(" AND MONTHLY_TAWI_ID NOT IN ( ");

                for (IncomeCategoryDataM monthlyTawiM: monthlyTawiMList) {
                    sql.append(" '" + monthlyTawiM.getId() + "' , ");
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
