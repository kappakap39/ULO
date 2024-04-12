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
import com.eaf.orig.ulo.model.app.FinancialInstrumentDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public class OrigFinInstrumentDAOImpl extends OrigObjectDAO implements
		OrigFinInstrumentDAO {
	public OrigFinInstrumentDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigFinInstrumentDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigFinInstrumentM(FinancialInstrumentDataM finInstrumentM)
			throws ApplicationException {
		logger.debug("finInstrumentM.getFinInstrument() :"+finInstrumentM.getFinInstrument());
	    if(Util.empty(finInstrumentM.getFinInstrument())){
			String finInstrumentId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_FIN_INSTRUMENT_PK);
			finInstrumentM.setFinInstrument(finInstrumentId);
			finInstrumentM.setCreateBy(userId);
	    }
	    finInstrumentM.setUpdateBy(userId);
		createTableINC_FIN_INSTRUMENT(finInstrumentM);
	}

	private void createTableINC_FIN_INSTRUMENT(
			FinancialInstrumentDataM finInstrumentM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_FIN_INSTRUMENT ");
			sql.append("( FIN_INSTRUMENT, INCOME_ID, OPEN_DATE, EXPIRE_DATE, ISSUER_NAME, ");
			sql.append(" INSTRUMENT_TYPE, HOLDER_NAME, HOLDING_RATIO, CURRENT_BALANCE, INSTRUMENT_TYPE_DESC,");
			sql.append(" COMPARE_FLAG, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, finInstrumentM.getFinInstrument());
			ps.setString(cnt++, finInstrumentM.getIncomeId());
			ps.setDate(cnt++, finInstrumentM.getOpenDate());
			ps.setDate(cnt++, finInstrumentM.getExpireDate());
			ps.setString(cnt++, finInstrumentM.getIssuerName());
			
			ps.setString(cnt++, finInstrumentM.getInstrumentType());
			ps.setString(cnt++, finInstrumentM.getHolderName());
			ps.setBigDecimal(cnt++, finInstrumentM.getHoldingRatio());
			ps.setBigDecimal(cnt++, finInstrumentM.getCurrentBalance());
			ps.setString(cnt++, finInstrumentM.getInstrumentTypeDesc());
			
			ps.setString(cnt++, finInstrumentM.getCompareFlag());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, finInstrumentM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, finInstrumentM.getUpdateBy());
			
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
	public void deleteOrigFinInstrumentM(String incomeId, String finInstrument)
			throws ApplicationException {
		deleteTableINC_FIN_INSTRUMENT(incomeId, finInstrument);
	}

	private void deleteTableINC_FIN_INSTRUMENT(String incomeId,
			String finInstrument) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_FIN_INSTRUMENT ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(finInstrument)) {
				sql.append(" AND FIN_INSTRUMENT = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(finInstrument)) {
				ps.setString(2, finInstrument);
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
	public ArrayList<FinancialInstrumentDataM> loadOrigFinInstrumentM(
			String incomeId, String incomeType, Connection conn)
			throws ApplicationException {
		return loadOrigFinInstrumentM(incomeId, incomeType);
	}

	@Override
	public ArrayList<FinancialInstrumentDataM> loadOrigFinInstrumentM(
			String incomeId, String incomeType) throws ApplicationException {
		ArrayList<FinancialInstrumentDataM> result = selectTableINC_FIN_INSTRUMENT(incomeId, incomeType);
		return result;
	}

	private ArrayList<FinancialInstrumentDataM> selectTableINC_FIN_INSTRUMENT(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<FinancialInstrumentDataM> finInstrumentList = new ArrayList<FinancialInstrumentDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT FIN_INSTRUMENT, INCOME_ID, OPEN_DATE, EXPIRE_DATE, ISSUER_NAME, ");
			sql.append(" INSTRUMENT_TYPE, HOLDER_NAME, HOLDING_RATIO, CURRENT_BALANCE, INSTRUMENT_TYPE_DESC, ");
			sql.append(" COMPARE_FLAG, CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_FIN_INSTRUMENT WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY FIN_INSTRUMENT ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				FinancialInstrumentDataM finInstrumentM = new FinancialInstrumentDataM();
				finInstrumentM.setSeq(finInstrumentList.size()+1);
				finInstrumentM.setIncomeType(incomeType);
				finInstrumentM.setFinInstrument(rs.getString("FIN_INSTRUMENT"));
				finInstrumentM.setIncomeId(rs.getString("INCOME_ID"));
				finInstrumentM.setOpenDate(rs.getDate("OPEN_DATE"));
				finInstrumentM.setExpireDate(rs.getDate("EXPIRE_DATE"));
				finInstrumentM.setIssuerName(rs.getString("ISSUER_NAME"));
				
				finInstrumentM.setInstrumentType(rs.getString("INSTRUMENT_TYPE"));
				finInstrumentM.setHolderName(rs.getString("HOLDER_NAME"));
				finInstrumentM.setHoldingRatio(rs.getBigDecimal("HOLDING_RATIO"));
				finInstrumentM.setCurrentBalance(rs.getBigDecimal("CURRENT_BALANCE"));
				finInstrumentM.setInstrumentTypeDesc(rs.getString("INSTRUMENT_TYPE_DESC"));
				
				finInstrumentM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				finInstrumentM.setCreateBy(rs.getString("CREATE_BY"));
				finInstrumentM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				finInstrumentM.setUpdateBy(rs.getString("UPDATE_BY"));
				finInstrumentM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				finInstrumentList.add(finInstrumentM);
			}

			return finInstrumentList;
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
	public void saveUpdateOrigFinInstrumentM(
			FinancialInstrumentDataM finInstrumentM) throws ApplicationException {
		int returnRows = 0;
		finInstrumentM.setUpdateBy(userId);
		returnRows = updateTableINC_FIN_INSTRUMENT(finInstrumentM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_FIN_INSTRUMENT then call Insert method");
			finInstrumentM.setCreateBy(userId);
			createOrigFinInstrumentM(finInstrumentM);
		}
	}

	private int updateTableINC_FIN_INSTRUMENT(
			FinancialInstrumentDataM finInstrumentM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_FIN_INSTRUMENT ");
			sql.append(" SET OPEN_DATE = ?, EXPIRE_DATE = ?, ISSUER_NAME = ?, INSTRUMENT_TYPE = ?, ");
			sql.append(" HOLDER_NAME = ?, HOLDING_RATIO = ?, CURRENT_BALANCE = ?, INSTRUMENT_TYPE_DESC = ?,");
			sql.append(" COMPARE_FLAG = ?, UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE FIN_INSTRUMENT = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setDate(cnt++, finInstrumentM.getOpenDate());
			ps.setDate(cnt++, finInstrumentM.getExpireDate());
			ps.setString(cnt++, finInstrumentM.getIssuerName());			
			ps.setString(cnt++, finInstrumentM.getInstrumentType());
			
			ps.setString(cnt++, finInstrumentM.getHolderName());
			ps.setBigDecimal(cnt++, finInstrumentM.getHoldingRatio());
			ps.setBigDecimal(cnt++, finInstrumentM.getCurrentBalance());
			ps.setString(cnt++, finInstrumentM.getInstrumentTypeDesc());
			
			ps.setString(cnt++, finInstrumentM.getCompareFlag());
			ps.setString(cnt++, finInstrumentM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, finInstrumentM.getFinInstrument());
			
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
	public void deleteNotInKeyFinInstrument(ArrayList<IncomeCategoryDataM> finInstrumentList,
			String incomeId) throws ApplicationException {
		deleteNotInKeyTableINC_FIN_INSTRUMENT(finInstrumentList, incomeId);
	}

	private void deleteNotInKeyTableINC_FIN_INSTRUMENT(
			ArrayList<IncomeCategoryDataM> finInstrumentList,
			String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_FIN_INSTRUMENT ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(finInstrumentList)) {
                sql.append(" AND FIN_INSTRUMENT NOT IN ( ");

                for (IncomeCategoryDataM finInstrumentM: finInstrumentList) {
                    sql.append(" '" + finInstrumentM.getId() + "' , ");
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
