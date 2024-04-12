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
import com.eaf.orig.ulo.model.app.OtherIncomeDataM;

public class OrigOtherIncomeDAOImpl extends OrigObjectDAO implements
		OrigOtherIncomeDAO {
	public OrigOtherIncomeDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigOtherIncomeDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigOtherIncomeM(OtherIncomeDataM othIncomeM)
			throws ApplicationException {
		logger.debug("othIncomeM.getOthIncomeId() :"+othIncomeM.getOthIncomeId());
	    if(Util.empty(othIncomeM.getOthIncomeId())){
			String othIncomeId = GenerateUnique.generate(OrigConstant.SeqNames.INC_OTH_INCOME_PK);
			othIncomeM.setOthIncomeId(othIncomeId);
			othIncomeM.setCreateBy(userId);
	    }
	    othIncomeM.setUpdateBy(userId);
		createTableINC_OTH_INCOME(othIncomeM);
	}

	private void createTableINC_OTH_INCOME(OtherIncomeDataM othIncomeM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_OTH_INCOME ");
			sql.append("( OTH_INCOME_ID, INCOME_ID, INCOME_TYPE_DESC, INCOME_AMOUNT, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, othIncomeM.getOthIncomeId());
			ps.setString(cnt++, othIncomeM.getIncomeId());
			ps.setString(cnt++, othIncomeM.getIncomeTypeDesc());
			ps.setBigDecimal(cnt++, othIncomeM.getIncomeAmount());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, othIncomeM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, othIncomeM.getUpdateBy());
			
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
	public void deleteOrigOtherIncomeM(String incomeId, String othIncomeId)
			throws ApplicationException {
		deleteTableINC_OTH_INCOME(incomeId, othIncomeId);
	}

	private void deleteTableINC_OTH_INCOME(String incomeId, String othIncomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_OTH_INCOME ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(othIncomeId)) {
				sql.append(" AND OTH_INCOME_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(othIncomeId)) {
				ps.setString(2, othIncomeId);
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
	public ArrayList<OtherIncomeDataM> loadOrigOtherIncomeM(String incomeId,
			Connection conn) throws ApplicationException {
		return loadOrigOtherIncomeM(incomeId);
	}
	
	@Override
	public ArrayList<OtherIncomeDataM> loadOrigOtherIncomeM(String incomeId)
			throws ApplicationException {
		ArrayList<OtherIncomeDataM> result = selectTableINC_OTH_INCOME(incomeId);		
		return result;
	}

	private ArrayList<OtherIncomeDataM> selectTableINC_OTH_INCOME(
			String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<OtherIncomeDataM> othIncomeList = new ArrayList<OtherIncomeDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT OTH_INCOME_ID, INCOME_ID, INCOME_TYPE_DESC, INCOME_AMOUNT, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_OTH_INCOME WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY OTH_INCOME_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while(rs.next()) {
				OtherIncomeDataM othIncomeM = new OtherIncomeDataM();
				othIncomeM.setOthIncomeId(rs.getString("OTH_INCOME_ID"));		
				othIncomeM.setIncomeId(rs.getString("INCOME_ID"));
				othIncomeM.setIncomeTypeDesc(rs.getString("INCOME_TYPE_DESC"));
				othIncomeM.setIncomeAmount(rs.getBigDecimal("INCOME_AMOUNT"));
				
				othIncomeM.setCreateBy(rs.getString("CREATE_BY"));
				othIncomeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				othIncomeM.setUpdateBy(rs.getString("UPDATE_BY"));
				othIncomeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				othIncomeList.add(othIncomeM);
			}

			return othIncomeList;
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
	public void saveUpdateOrigOtherIncomeM(OtherIncomeDataM othIncomeM)
			throws ApplicationException {
		int returnRows = 0;
		othIncomeM.setUpdateBy(userId);
		returnRows = updateTableINC_OTH_INCOME(othIncomeM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_OTH_INCOME then call Insert method");
			othIncomeM.setCreateBy(userId);
			createOrigOtherIncomeM(othIncomeM);
		}
	}

	private int updateTableINC_OTH_INCOME(OtherIncomeDataM othIncomeM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_OTH_INCOME ");
			sql.append(" SET INCOME_TYPE_DESC = ?, INCOME_AMOUNT = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");			
			sql.append(" WHERE OTH_INCOME_ID = ? AND INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, othIncomeM.getIncomeTypeDesc());
			ps.setBigDecimal(cnt++, othIncomeM.getIncomeAmount());
			
			ps.setString(cnt++, othIncomeM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, othIncomeM.getOthIncomeId());
			ps.setString(cnt++, othIncomeM.getIncomeId());
			
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
	public void deleteNotInKeyOtherIncome(
			ArrayList<OtherIncomeDataM> othIncomeList, String incomeId)	throws ApplicationException {
		deleteNotInKeyTableINC_OTH_INCOME(othIncomeList, incomeId);
	}

	private void deleteNotInKeyTableINC_OTH_INCOME(
			ArrayList<OtherIncomeDataM> othIncomeList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_OTH_INCOME ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(othIncomeList)) {
                sql.append(" AND OTH_INCOME_ID NOT IN ( ");

                for (OtherIncomeDataM othIncomeM: othIncomeList) {
                    sql.append(" '" + othIncomeM.getOthIncomeId()+ "' , ");
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
