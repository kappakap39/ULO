package com.eaf.orig.ulo.app.dao;

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
import com.eaf.orig.ulo.model.app.DebtInfoDataM;

public class OrigDebtInfoDAOImpl extends OrigObjectDAO implements
		OrigDebtInfoDAO {
	public OrigDebtInfoDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigDebtInfoDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigDebtInfoM(DebtInfoDataM debtInfoM)
			throws ApplicationException {
		logger.debug("debtInfoM.getDebtId() :"+debtInfoM.getDebtId());
	    if(Util.empty(debtInfoM.getDebtId())){
			String debtId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DEBT_INFO_PK );
			debtInfoM.setDebtId(debtId);
			debtInfoM.setCreateBy(userId);
	    }
	    debtInfoM.setUpdateBy(userId);
		createTableORIG_DEBT_INFO(debtInfoM);
	}

	private void createTableORIG_DEBT_INFO(DebtInfoDataM debtInfoM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_DEBT_INFO ");
			sql.append("( DEBT_ID, PERSONAL_ID, DEBT_TYPE, DEBT_AMT, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,   ?,?,?,?  )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, debtInfoM.getDebtId());
			ps.setString(cnt++, debtInfoM.getPersonalId());
			ps.setString(cnt++, debtInfoM.getDebtType());
			ps.setBigDecimal(cnt++, debtInfoM.getDebtAmt());
			ps.setString(cnt++, debtInfoM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, debtInfoM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, debtInfoM.getUpdateBy());
			
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
	public void deleteOrigDebtInfoM(String personalId, String debtId)
			throws ApplicationException {
		deleteTableORIG_DEBT_INFO(personalId, debtId);
	}

	private void deleteTableORIG_DEBT_INFO(String personalId, String debtId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_DEBT_INFO ");
			sql.append(" WHERE PERSONAL_ID = ? ");
			if(!Util.empty(debtId)) {
				sql.append(" AND DEBT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if(!Util.empty(debtId)) {
				ps.setString(2, debtId);
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
	public ArrayList<DebtInfoDataM> loadOrigDebtInfoM(String personalId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_DEBT_INFO(personalId,conn);
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
		
	}
	@Override
	public ArrayList<DebtInfoDataM> loadOrigDebtInfoM(String personalId,
			Connection conn) throws ApplicationException {
		return selectTableORIG_DEBT_INFO(personalId, conn);
	}
	private ArrayList<DebtInfoDataM> selectTableORIG_DEBT_INFO(String personalId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DebtInfoDataM> debtList = new ArrayList<DebtInfoDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DEBT_ID, PERSONAL_ID, DEBT_TYPE, DEBT_AMT, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_DEBT_INFO WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();

			while(rs.next()) {
				DebtInfoDataM debtInfoM = new DebtInfoDataM();
				debtInfoM.setDebtId(rs.getString("DEBT_ID"));
				debtInfoM.setSeq(debtList.size()+1);
				debtInfoM.setPersonalId(rs.getString("PERSONAL_ID"));
				debtInfoM.setDebtType(rs.getString("DEBT_TYPE"));
				debtInfoM.setDebtAmt(rs.getBigDecimal("DEBT_AMT"));
				debtInfoM.setCompareFlag(rs.getString("COMPARE_FLAG"));

				debtInfoM.setCreateBy(rs.getString("CREATE_BY"));
				debtInfoM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				debtInfoM.setUpdateBy(rs.getString("UPDATE_BY"));
				debtInfoM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				debtList.add(debtInfoM);
			}

			return debtList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigDebtInfoM(DebtInfoDataM debtInfoM)
			throws ApplicationException {
		int returnRows = 0;
		debtInfoM.setUpdateBy(userId);
		returnRows = updateTableORIG_DEBT_INFO(debtInfoM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_DEBT_INFO then call Insert method");
			debtInfoM.setCreateBy(userId);
			createOrigDebtInfoM(debtInfoM);
		}
	}

	private int updateTableORIG_DEBT_INFO(DebtInfoDataM debtInfoM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_DEBT_INFO ");
			sql.append(" SET DEBT_TYPE = ?, DEBT_AMT = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE DEBT_ID = ? AND PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, debtInfoM.getDebtType());
			ps.setBigDecimal(cnt++, debtInfoM.getDebtAmt());
			ps.setString(cnt++, debtInfoM.getCompareFlag());
			
			ps.setString(cnt++, debtInfoM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, debtInfoM.getDebtId());	
			ps.setString(cnt++, debtInfoM.getPersonalId());
			
			
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
	public void deleteNotInKeyDebtInfo(ArrayList<DebtInfoDataM> debtList,
			String personalId) throws ApplicationException {
		deleteNotInKeyTableORIG_DEBT_INFO(debtList, personalId);
	}

	private void deleteNotInKeyTableORIG_DEBT_INFO(
			ArrayList<DebtInfoDataM> debtList, String personalId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_DEBT_INFO ");
            sql.append(" WHERE PERSONAL_ID = ? ");
            
            if (!Util.empty(debtList)) {
                sql.append(" AND DEBT_ID NOT IN ( ");
                for (DebtInfoDataM debtInfoM: debtList) {
                	logger.debug("debtInfoM.getDebtId() = "+debtInfoM.getDebtId());
                    sql.append(" '" + debtInfoM.getDebtId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, personalId);
            
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
