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
import com.eaf.orig.ulo.model.app.KVIIncomeDataM;

public class OrigKVIIncomeDAOImpl extends OrigObjectDAO implements
		OrigKVIIncomeDAO {
	public OrigKVIIncomeDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigKVIIncomeDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigKVIIncomeM(KVIIncomeDataM kviIncomeM)
			throws ApplicationException {
		logger.debug("kviIncomeM.getKviId() :"+kviIncomeM.getKviId());
	    if(Util.empty(kviIncomeM.getKviId())){
			String kviId = GenerateUnique.generate(OrigConstant.SeqNames.INC_KVI_PK);
			kviIncomeM.setKviId(kviId);
			kviIncomeM.setCreateBy(userId);
	    }
	    kviIncomeM.setUpdateBy(userId);
		createTableINC_KVI(kviIncomeM);
	}

	private void createTableINC_KVI(KVIIncomeDataM kviIncomeM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_KVI ");
			sql.append("( KVI_ID, INCOME_ID, PERCENT_CHEQUE_RETURN, VERIFIED_INCOME, COMPARE_FLAG, ");
			sql.append(" KVI_FID, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, kviIncomeM.getKviId());
			ps.setString(cnt++, kviIncomeM.getIncomeId());		
			ps.setBigDecimal(cnt++, kviIncomeM.getPercentChequeReturn());
			ps.setBigDecimal(cnt++, kviIncomeM.getVerifiedIncome());
			ps.setString(cnt++, kviIncomeM.getCompareFlag());
			
			ps.setString(cnt++, kviIncomeM.getKviFid());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, kviIncomeM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, kviIncomeM.getUpdateBy());
			
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
	public void deleteOrigKVIIncomeM(String incomeId, String kviId)
			throws ApplicationException {
		deleteTableINC_KVI(incomeId, kviId);
	}

	private void deleteTableINC_KVI(String incomeId, String kviId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_KVI ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(kviId)) {
				sql.append(" AND KVI_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(kviId)) {
				ps.setString(2, kviId);
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
	public ArrayList<KVIIncomeDataM> loadOrigKVIIncomeM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigKVIIncomeM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<KVIIncomeDataM> loadOrigKVIIncomeM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<KVIIncomeDataM> result = selectTableINC_KVI(incomeId, incomeType);
		return result;
	}

	private ArrayList<KVIIncomeDataM> selectTableINC_KVI(String incomeId, String incomeType) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<KVIIncomeDataM> kviList = new ArrayList<KVIIncomeDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT KVI_ID, INCOME_ID, PERCENT_CHEQUE_RETURN, VERIFIED_INCOME, COMPARE_FLAG, ");
			sql.append(" KVI_FID, CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_KVI WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY KVI_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				KVIIncomeDataM kviIncomeM = new KVIIncomeDataM();
				kviIncomeM.setSeq(kviList.size()+1);
				kviIncomeM.setIncomeType(incomeType);
				kviIncomeM.setKviId(rs.getString("KVI_ID"));
				kviIncomeM.setIncomeId(rs.getString("INCOME_ID"));				
				kviIncomeM.setPercentChequeReturn(rs.getBigDecimal("PERCENT_CHEQUE_RETURN"));
				kviIncomeM.setVerifiedIncome(rs.getBigDecimal("VERIFIED_INCOME"));
				kviIncomeM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				kviIncomeM.setKviFid(rs.getString("KVI_FID"));
				kviIncomeM.setCreateBy(rs.getString("CREATE_BY"));
				kviIncomeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				kviIncomeM.setUpdateBy(rs.getString("UPDATE_BY"));
				kviIncomeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				kviList.add(kviIncomeM);
			}

			return kviList;
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
	public void saveUpdateOrigKVIIncomeM(KVIIncomeDataM kviIncomeM)
			throws ApplicationException {
		int returnRows = 0;
		kviIncomeM.setUpdateBy(userId);
		returnRows = updateTableINC_KVI(kviIncomeM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_KVI then call Insert method");
			kviIncomeM.setCreateBy(userId);
			createOrigKVIIncomeM(kviIncomeM);
		}
	}

	private int updateTableINC_KVI(KVIIncomeDataM kviIncomeM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_KVI ");
			sql.append(" SET PERCENT_CHEQUE_RETURN = ?, VERIFIED_INCOME = ?, COMPARE_FLAG = ?, KVI_FID = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE KVI_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setBigDecimal(cnt++, kviIncomeM.getPercentChequeReturn());
			ps.setBigDecimal(cnt++, kviIncomeM.getVerifiedIncome());
			ps.setString(cnt++, kviIncomeM.getCompareFlag());
			ps.setString(cnt++, kviIncomeM.getKviFid());
			
			ps.setString(cnt++, kviIncomeM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, kviIncomeM.getKviId());
			
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
	public void deleteNotInKeyKVIIncome(
			ArrayList<IncomeCategoryDataM> kviIncomeMList, String incomeId)
			throws ApplicationException {
		deleteNotInKeyTableINC_KVI(kviIncomeMList, incomeId);
	}

	private void deleteNotInKeyTableINC_KVI(
			ArrayList<IncomeCategoryDataM> kviIncomeMList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_KVI ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(kviIncomeMList)) {
                sql.append(" AND KVI_ID NOT IN ( ");

                for (IncomeCategoryDataM kviIncomeM: kviIncomeMList) {
                    sql.append(" '" + kviIncomeM.getId() + "' , ");
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
