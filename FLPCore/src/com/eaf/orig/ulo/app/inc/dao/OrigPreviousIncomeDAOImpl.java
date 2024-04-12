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
import com.eaf.orig.ulo.model.app.PreviousIncomeDataM;

public class OrigPreviousIncomeDAOImpl extends OrigObjectDAO implements
		OrigPreviousIncomeDAO {
	public OrigPreviousIncomeDAOImpl(String userId){
		this.userId = userId ;
	}
	public OrigPreviousIncomeDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPreviousIncomeM(PreviousIncomeDataM prevIncomeM) throws ApplicationException {
		logger.debug("prevIncomeM.getPreviousIncomeId() :"+prevIncomeM.getPreviousIncomeId());
	    if(Util.empty(prevIncomeM.getPreviousIncomeId())){
			String prevIncomeId = GenerateUnique.generate(OrigConstant.SeqNames.INC_PREVIOUS_INCOME_PK);
			prevIncomeM.setPreviousIncomeId(prevIncomeId);
			prevIncomeM.setCreateBy(userId);
	    }
	    prevIncomeM.setUpdateBy(userId);
		createTableINC_PREVIOUS_INCOME(prevIncomeM);
	}

	private void createTableINC_PREVIOUS_INCOME(PreviousIncomeDataM prevIncomeM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_PREVIOUS_INCOME ");
			sql.append("( PREVIOUS_INCOME_ID, INCOME_ID, INCOME_DATE, PRODUCT, INCOME, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, prevIncomeM.getPreviousIncomeId());
			ps.setString(cnt++, prevIncomeM.getIncomeId());
			ps.setDate(cnt++, prevIncomeM.getIncomeDate());
			ps.setString(cnt++, prevIncomeM.getProduct());
			ps.setBigDecimal(cnt++, prevIncomeM.getIncome());
			ps.setString(cnt++, prevIncomeM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, prevIncomeM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, prevIncomeM.getUpdateBy());
			
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
	public void deleteOrigPreviousIncomeM(String incomeId,
			String previousIncomeId) throws ApplicationException {
		deleteTableINC_PREVIOUS_INCOME(incomeId, previousIncomeId);
	}

	private void deleteTableINC_PREVIOUS_INCOME(String incomeId,
			String previousIncomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_PREVIOUS_INCOME ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(previousIncomeId)) {
				sql.append(" AND PREVIOUS_INCOME_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(previousIncomeId)) {
				ps.setString(2, previousIncomeId);
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
	public ArrayList<PreviousIncomeDataM> loadOrigPreviousIncomeM(
			String incomeId, String incomeType, Connection conn)
			throws ApplicationException {
		return loadOrigPreviousIncomeM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<PreviousIncomeDataM> loadOrigPreviousIncomeM(
			String incomeId, String incomeType) throws ApplicationException {
		ArrayList<PreviousIncomeDataM> result = selectTableINC_PREVIOUS_INCOME(incomeId, incomeType);
		return result;
	}

	private ArrayList<PreviousIncomeDataM> selectTableINC_PREVIOUS_INCOME(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PreviousIncomeDataM> prevIncomeList = new ArrayList<PreviousIncomeDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PREVIOUS_INCOME_ID, INCOME_ID, INCOME_DATE, PRODUCT, INCOME, ");
			sql.append(" COMPARE_FLAG, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM INC_PREVIOUS_INCOME WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY PREVIOUS_INCOME_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				PreviousIncomeDataM prevIncomeM = new PreviousIncomeDataM();
				prevIncomeM.setSeq(prevIncomeList.size()+1);
				prevIncomeM.setIncomeType(incomeType);
				prevIncomeM.setPreviousIncomeId(rs.getString("PREVIOUS_INCOME_ID"));
				prevIncomeM.setIncomeId(rs.getString("INCOME_ID"));
				prevIncomeM.setIncomeDate(rs.getDate("INCOME_DATE"));
				prevIncomeM.setProduct(rs.getString("PRODUCT"));
				prevIncomeM.setIncome(rs.getBigDecimal("INCOME"));
				
				prevIncomeM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				prevIncomeM.setCreateBy(rs.getString("CREATE_BY"));
				prevIncomeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				prevIncomeM.setUpdateBy(rs.getString("UPDATE_BY"));
				prevIncomeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				prevIncomeList.add(prevIncomeM);
			}

			return prevIncomeList;
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
	public void saveUpdateOrigPreviousIncomeM(PreviousIncomeDataM prevIncomeM)
			throws ApplicationException {
		int returnRows = 0;
		prevIncomeM.setUpdateBy(userId);
		returnRows = updateTableINC_PREVIOUS_INCOME(prevIncomeM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_PREVIOUS_INCOME then call Insert method");
			prevIncomeM.setCreateBy(userId);
			createOrigPreviousIncomeM(prevIncomeM);
		}
	}

	private int updateTableINC_PREVIOUS_INCOME(PreviousIncomeDataM prevIncomeM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_PREVIOUS_INCOME ");
			sql.append(" SET INCOME_DATE = ?, PRODUCT = ?, INCOME = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE PREVIOUS_INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setDate(cnt++, prevIncomeM.getIncomeDate());
			ps.setString(cnt++, prevIncomeM.getProduct());
			ps.setBigDecimal(cnt++, prevIncomeM.getIncome());
			ps.setString(cnt++, prevIncomeM.getCompareFlag());
			
			ps.setString(cnt++, prevIncomeM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, prevIncomeM.getPreviousIncomeId());
			
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
	public void deleteNotInKeyPreviousIncome(
			ArrayList<IncomeCategoryDataM> prevIncomeList, String incomeId)
			throws ApplicationException {
		deleteNotInKeyTableINC_PREVIOUS_INCOME(prevIncomeList, incomeId);
	}

	private void deleteNotInKeyTableINC_PREVIOUS_INCOME(
			ArrayList<IncomeCategoryDataM> prevIncomeList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_PREVIOUS_INCOME ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(prevIncomeList)) {
                sql.append(" AND PREVIOUS_INCOME_ID NOT IN ( ");

                for (IncomeCategoryDataM prevIncomeM: prevIncomeList) {
                    sql.append(" '" + prevIncomeM.getId() + "' , ");
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
