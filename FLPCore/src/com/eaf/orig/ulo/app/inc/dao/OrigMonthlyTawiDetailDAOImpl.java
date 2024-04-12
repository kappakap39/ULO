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
import com.eaf.orig.ulo.model.app.MonthlyTawi50DetailDataM;

public class OrigMonthlyTawiDetailDAOImpl extends OrigObjectDAO implements
		OrigMonthlyTawiDetailDAO {
	public OrigMonthlyTawiDetailDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigMonthlyTawiDetailDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	private String userId = "";
	@Override
	public void createOrigMonthlyTawiDetailM(
			MonthlyTawi50DetailDataM monthlyTawiDetailM) throws ApplicationException {
		logger.debug("monthlyTawiDetailM.getMonthlyTawiDetailId() :" + monthlyTawiDetailM.getMonthlyTawiDetailId());
	    if(Util.empty(monthlyTawiDetailM.getMonthlyTawiDetailId())){
			String detailId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_MONTHLY_TAWI_DETAIL_PK);
			monthlyTawiDetailM.setMonthlyTawiDetailId(detailId);
			monthlyTawiDetailM.setCreateBy(userId);
	    }
	    monthlyTawiDetailM.setUpdateBy(userId);
		createTableINC_MONTHLY_TAWI_DETAIL(monthlyTawiDetailM);
	}

	private void createTableINC_MONTHLY_TAWI_DETAIL(
			MonthlyTawi50DetailDataM monthlyTawiDetailM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_MONTHLY_TAWI_DETAIL ");
			sql.append("( MONTHLY_TAWI_DETAIL_ID, MONTHLY_TAWI_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,  ?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, monthlyTawiDetailM.getMonthlyTawiDetailId());
			ps.setString(cnt++, monthlyTawiDetailM.getMonthlyTawiId());
			
			ps.setString(cnt++, monthlyTawiDetailM.getMonth());
			ps.setString(cnt++, monthlyTawiDetailM.getYear());
			ps.setBigDecimal(cnt++, monthlyTawiDetailM.getAmount());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, monthlyTawiDetailM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, monthlyTawiDetailM.getUpdateBy());
			
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
	public void deleteOrigMonthlyTawiDetailM(String monthlyTawiId,
			String monthlyTawiDetailId) throws ApplicationException {
		deleteTableINC_MONTHLY_TAWI_DETAIL(monthlyTawiId, monthlyTawiDetailId);
	}

	private void deleteTableINC_MONTHLY_TAWI_DETAIL(String monthlyTawiId,
			String monthlyTawiDetailId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_MONTHLY_TAWI_DETAIL ");
			sql.append(" WHERE MONTHLY_TAWI_ID = ? ");
			if(!Util.empty(monthlyTawiDetailId)) {
				sql.append(" AND MONTHLY_TAWI_DETAIL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, monthlyTawiId);
			if(!Util.empty(monthlyTawiDetailId)) {
				ps.setString(2, monthlyTawiDetailId);
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
	public ArrayList<MonthlyTawi50DetailDataM> loadOrigMonthlyTawiDetailM(
			String monthlyTawiId) throws ApplicationException {
		ArrayList<MonthlyTawi50DetailDataM> result = selectTableINC_MONTHLY_TAWI_DETAIL(monthlyTawiId);
		return result;
	}

	private ArrayList<MonthlyTawi50DetailDataM> selectTableINC_MONTHLY_TAWI_DETAIL(
			String monthlyTawiId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<MonthlyTawi50DetailDataM> monthlyDetailList = new ArrayList<MonthlyTawi50DetailDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT MONTHLY_TAWI_DETAIL_ID, MONTHLY_TAWI_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_MONTHLY_TAWI_DETAIL WHERE MONTHLY_TAWI_ID = ? ");
			sql.append(" ORDER BY MONTHLY_TAWI_DETAIL_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, monthlyTawiId);

			rs = ps.executeQuery();

			while (rs.next()) {
				MonthlyTawi50DetailDataM monthlyTawiDetailM = new MonthlyTawi50DetailDataM();
				monthlyTawiDetailM.setSeq(monthlyDetailList.size()+1);
				monthlyTawiDetailM.setMonthlyTawiDetailId(rs.getString("MONTHLY_TAWI_DETAIL_ID"));
				monthlyTawiDetailM.setMonthlyTawiId(rs.getString("MONTHLY_TAWI_ID"));
				
				monthlyTawiDetailM.setMonth(rs.getString("MONTH"));
				monthlyTawiDetailM.setYear(rs.getString("YEAR"));
				monthlyTawiDetailM.setAmount(rs.getBigDecimal("AMOUNT"));
				
				monthlyTawiDetailM.setCreateBy(rs.getString("CREATE_BY"));
				monthlyTawiDetailM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				monthlyTawiDetailM.setUpdateBy(rs.getString("UPDATE_BY"));
				monthlyTawiDetailM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				monthlyDetailList.add(monthlyTawiDetailM);
			}

			return monthlyDetailList;
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
	public void saveUpdateOrigMonthlyTawiDetailM(
			MonthlyTawi50DetailDataM monthlyTawiDetailM) throws ApplicationException {
		int returnRows = 0;
		monthlyTawiDetailM.setUpdateBy(userId);
		returnRows = updateTableINC_MONTHLY_TAWI_DETAIL(monthlyTawiDetailM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_MONTHLY_TAWI_DETAIL then call Insert method");
			monthlyTawiDetailM.setCreateBy(userId);
			createOrigMonthlyTawiDetailM(monthlyTawiDetailM);
		}
	}

	private int updateTableINC_MONTHLY_TAWI_DETAIL(
			MonthlyTawi50DetailDataM monthlyTawiDetailM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_MONTHLY_TAWI_DETAIL ");
			sql.append(" SET MONTH = ?, YEAR = ?, AMOUNT = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE MONTHLY_TAWI_DETAIL_ID = ? AND MONTHLY_TAWI_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, monthlyTawiDetailM.getMonth());
			ps.setString(cnt++, monthlyTawiDetailM.getYear());
			ps.setBigDecimal(cnt++, monthlyTawiDetailM.getAmount());
			
			ps.setString(cnt++, monthlyTawiDetailM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, monthlyTawiDetailM.getMonthlyTawiDetailId());
			ps.setString(cnt++, monthlyTawiDetailM.getMonthlyTawiId());			
			
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
	public void deleteNotInKeyMonthlyTawiDetail(
			ArrayList<MonthlyTawi50DetailDataM> monthlyTawiDtlList,
			String monthlyTawiId) throws ApplicationException {
		deleteNotInKeyTableINC_MONTHLY_TAWI_DETAIL(monthlyTawiDtlList, monthlyTawiId);
	}

	private void deleteNotInKeyTableINC_MONTHLY_TAWI_DETAIL(
			ArrayList<MonthlyTawi50DetailDataM> monthlyTawiDtlList,
			String monthlyTawiId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_MONTHLY_TAWI_DETAIL ");
            sql.append(" WHERE MONTHLY_TAWI_ID = ? ");
            
            if (!Util.empty(monthlyTawiDtlList)) {
                sql.append(" AND MONTHLY_TAWI_DETAIL_ID NOT IN ( ");

                for (MonthlyTawi50DetailDataM detailM: monthlyTawiDtlList) {
                    sql.append(" '" + detailM.getMonthlyTawiDetailId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, monthlyTawiId);
            
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
