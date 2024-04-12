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
import com.eaf.orig.ulo.model.app.TaweesapDataM;

public class OrigTaweesapDAOImpl extends OrigObjectDAO implements
		OrigTaweesapDAO {
	public OrigTaweesapDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigTaweesapDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigTaweesapM(TaweesapDataM taweesapM)
			throws ApplicationException {
		logger.debug("taweesapM.getTweesapId() :"+taweesapM.getTweesapId());
	    if(Util.empty(taweesapM.getTweesapId())){
			String taweesapId = GenerateUnique.generate(OrigConstant.SeqNames.INC_TAWEESAP_PK);
			taweesapM.setTweesapId(taweesapId);
			taweesapM.setCreateBy(userId);
	    }
	    taweesapM.setUpdateBy(userId);
		createTableINC_TAWEESAP(taweesapM);
	}

	private void createTableINC_TAWEESAP(TaweesapDataM taweesapM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_TAWEESAP ");
			sql.append("( TAWEESAP_ID, INCOME_ID, AUM, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, taweesapM.getTweesapId());
			ps.setString(cnt++, taweesapM.getIncomeId());	
			ps.setBigDecimal(cnt++, taweesapM.getAum());
			ps.setString(cnt++, taweesapM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, taweesapM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, taweesapM.getUpdateBy());
			
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
	public void deleteOrigTaweesapM(String incomeId, String taweesapId)
			throws ApplicationException {
		deleteTableINC_TAWEESAP(incomeId, taweesapId);
	}

	private void deleteTableINC_TAWEESAP(String incomeId, String taweesapId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_TAWEESAP ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(taweesapId)) {
				sql.append(" AND TAWEESAP_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(taweesapId)) {
				ps.setString(2, taweesapId);
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
	public ArrayList<TaweesapDataM> loadOrigTaweesapM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigTaweesapM(incomeId, incomeType);
	}
	@Override
	public ArrayList<TaweesapDataM> loadOrigTaweesapM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<TaweesapDataM> result = selectTableINC_TAWEESAP(incomeId, incomeType);
		return result;
	}

	private ArrayList<TaweesapDataM> selectTableINC_TAWEESAP(String incomeId, String incomeType) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<TaweesapDataM> taweesapList = new ArrayList<TaweesapDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TAWEESAP_ID, INCOME_ID, AUM, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM INC_TAWEESAP WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY TAWEESAP_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				TaweesapDataM taweesapM = new TaweesapDataM();
				taweesapM.setSeq(taweesapList.size()+1);
				taweesapM.setIncomeType(incomeType);
				taweesapM.setTweesapId(rs.getString("TAWEESAP_ID"));
				taweesapM.setIncomeId(rs.getString("INCOME_ID"));
				taweesapM.setAum(rs.getBigDecimal("AUM"));
				taweesapM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				taweesapM.setCreateBy(rs.getString("CREATE_BY"));
				taweesapM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				taweesapM.setUpdateBy(rs.getString("UPDATE_BY"));
				taweesapM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				taweesapList.add(taweesapM);
			}

			return taweesapList;
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
	public void saveUpdateOrigTaweesapM(TaweesapDataM taweesapM)
			throws ApplicationException {
		int returnRows = 0;
		taweesapM.setUpdateBy(userId);
		returnRows = updateTableINC_TAWEESAP(taweesapM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_TAWEESAP then call Insert method");
			taweesapM.setCreateBy(userId);
			createOrigTaweesapM(taweesapM);
		}
	}

	private int updateTableINC_TAWEESAP(TaweesapDataM taweesapM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_TAWEESAP ");
			sql.append(" SET AUM = ?, UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE TAWEESAP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setBigDecimal(cnt++, taweesapM.getAum());			
			ps.setString(cnt++, taweesapM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, taweesapM.getTweesapId());
			
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
	public void deleteNotInKeyTaweesap(ArrayList<IncomeCategoryDataM> taweesapList,
			String incomeId) throws ApplicationException {
		deleteNotInKeyTableINC_TAWEESAP(taweesapList, incomeId);
	}

	private void deleteNotInKeyTableINC_TAWEESAP(
			ArrayList<IncomeCategoryDataM> taweesapList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_TAWEESAP ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(taweesapList)) {
                sql.append(" AND TAWEESAP_ID NOT IN ( ");

                for (IncomeCategoryDataM taweesapM: taweesapList) {
                    sql.append(" '" + taweesapM.getId() + "' , ");
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
