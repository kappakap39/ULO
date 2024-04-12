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
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public class OrigFixedGuaranteeDAOImpl extends OrigObjectDAO implements
		OrigFixedGuaranteeDAO {
	public OrigFixedGuaranteeDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigFixedGuaranteeDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigFixedGuaranteeM(FixedGuaranteeDataM fixedGuaranteeM)
			throws ApplicationException {
		logger.debug("fixedGuaranteeM.getFixedGuaranteeId() :"+fixedGuaranteeM.getFixedGuaranteeId());
	    if(Util.empty(fixedGuaranteeM.getFixedGuaranteeId())){
			String fixedGuaranteeId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_FIXED_GUARANTEE_PK);
			fixedGuaranteeM.setFixedGuaranteeId(fixedGuaranteeId);
			fixedGuaranteeM.setCreateBy(userId);
	    }
	    fixedGuaranteeM.setUpdateBy(userId);
		createTableINC_FIXED_GUARANTEE(fixedGuaranteeM);
	}

	private void createTableINC_FIXED_GUARANTEE(
			FixedGuaranteeDataM fixedGuaranteeM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_FIXED_GUARANTEE ");
			sql.append("( FIXED_GUARANTEE_ID, INCOME_ID, ACCOUNT_NO, SUB, ACCOUNT_NAME, ");
			sql.append(" BRANCH_NO, RETENTION_DATE, RETENTION_TYPE, RETENTION_AMT, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, RETENTION_TYPE_DESC) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, fixedGuaranteeM.getFixedGuaranteeId());
			ps.setString(cnt++, fixedGuaranteeM.getIncomeId());		
			ps.setString(cnt++, fixedGuaranteeM.getAccountNo());
			ps.setString(cnt++, fixedGuaranteeM.getSub());
			ps.setString(cnt++, fixedGuaranteeM.getAccountName());
			
			ps.setString(cnt++, fixedGuaranteeM.getBranchNo());
			ps.setDate(cnt++, fixedGuaranteeM.getRetentionDate());	
			ps.setString(cnt++, fixedGuaranteeM.getRetentionType());
			ps.setBigDecimal(cnt++, fixedGuaranteeM.getRetentionAmt());
			ps.setString(cnt++, fixedGuaranteeM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, fixedGuaranteeM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, fixedGuaranteeM.getUpdateBy());
			ps.setString(cnt++, fixedGuaranteeM.getRetentionTypeDesc());
			
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
	public void deleteOrigFixedGuaranteeM(String incomeId,
			String fixedGuaranteeId) throws ApplicationException {
		deleteTableINC_FIXED_GUARANTEE(incomeId, fixedGuaranteeId);
	}

	private void deleteTableINC_FIXED_GUARANTEE(String incomeId,
			String fixedGuaranteeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_FIXED_GUARANTEE ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(fixedGuaranteeId)) {
				sql.append(" AND FIXED_GUARANTEE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(fixedGuaranteeId)) {
				ps.setString(2, fixedGuaranteeId);
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
	public ArrayList<FixedGuaranteeDataM> loadOrigFixedGuaranteeM(
			String incomeId, String incomeType, Connection conn)
			throws ApplicationException {
		return loadOrigFixedGuaranteeM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<FixedGuaranteeDataM> loadOrigFixedGuaranteeM(
			String incomeId, String incomeType) throws ApplicationException {
		ArrayList<FixedGuaranteeDataM> result = selectTableINC_FIXED_GUARANTEE(incomeId, incomeType);
		return result;
	}

	private ArrayList<FixedGuaranteeDataM> selectTableINC_FIXED_GUARANTEE(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<FixedGuaranteeDataM> fixGuaranteeList = new ArrayList<FixedGuaranteeDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT FIXED_GUARANTEE_ID, INCOME_ID, ACCOUNT_NO, SUB, ACCOUNT_NAME, ");
			sql.append(" BRANCH_NO, RETENTION_DATE, RETENTION_TYPE, RETENTION_AMT, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, RETENTION_TYPE_DESC ");
			sql.append(" FROM INC_FIXED_GUARANTEE WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY FIXED_GUARANTEE_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				FixedGuaranteeDataM fixGuaranteeM = new FixedGuaranteeDataM();
				fixGuaranteeM.setSeq(fixGuaranteeList.size()+1);
				fixGuaranteeM.setIncomeType(incomeType);
				fixGuaranteeM.setFixedGuaranteeId(rs.getString("FIXED_GUARANTEE_ID"));
				fixGuaranteeM.setIncomeId(rs.getString("INCOME_ID"));		
				fixGuaranteeM.setAccountNo(rs.getString("ACCOUNT_NO"));
				fixGuaranteeM.setSub(rs.getString("SUB"));				
				fixGuaranteeM.setAccountName(rs.getString("ACCOUNT_NAME"));
				
				fixGuaranteeM.setBranchNo(rs.getString("BRANCH_NO"));
				fixGuaranteeM.setRetentionDate(rs.getDate("RETENTION_DATE"));	
				fixGuaranteeM.setRetentionType(rs.getString("RETENTION_TYPE"));				
				fixGuaranteeM.setRetentionAmt(rs.getBigDecimal("RETENTION_AMT"));
				fixGuaranteeM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				fixGuaranteeM.setCreateBy(rs.getString("CREATE_BY"));
				fixGuaranteeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				fixGuaranteeM.setUpdateBy(rs.getString("UPDATE_BY"));
				fixGuaranteeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				fixGuaranteeM.setRetentionTypeDesc(rs.getString("RETENTION_TYPE_DESC"));
				fixGuaranteeList.add(fixGuaranteeM);
			}

			return fixGuaranteeList;
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
	public void saveUpdateOrigFixedGuaranteeM(
			FixedGuaranteeDataM fixedGuaranteeM) throws ApplicationException {
		int returnRows = 0;
		fixedGuaranteeM.setUpdateBy(userId);
		returnRows = updateTableINC_FIXED_GUARANTEE(fixedGuaranteeM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_FIXED_GUARANTEE then call Insert method");
			fixedGuaranteeM.setCreateBy(userId);
			createOrigFixedGuaranteeM(fixedGuaranteeM);
		}
	}

	private int updateTableINC_FIXED_GUARANTEE(
			FixedGuaranteeDataM fixedGuaranteeM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_FIXED_GUARANTEE ");
			sql.append(" SET ACCOUNT_NO = ?, SUB = ?, ACCOUNT_NAME = ?, BRANCH_NO = ?, ");
			sql.append(" RETENTION_DATE = ?, RETENTION_TYPE = ?, RETENTION_AMT = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?,UPDATE_DATE = ?, RETENTION_TYPE_DESC=? ");
			
			sql.append(" WHERE FIXED_GUARANTEE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, fixedGuaranteeM.getAccountNo());
			ps.setString(cnt++, fixedGuaranteeM.getSub());
			ps.setString(cnt++, fixedGuaranteeM.getAccountName());
			ps.setString(cnt++, fixedGuaranteeM.getBranchNo());
			
			ps.setDate(cnt++, fixedGuaranteeM.getRetentionDate());			
			ps.setString(cnt++, fixedGuaranteeM.getRetentionType());
			ps.setBigDecimal(cnt++, fixedGuaranteeM.getRetentionAmt());
			ps.setString(cnt++, fixedGuaranteeM.getCompareFlag());
			
			ps.setString(cnt++, fixedGuaranteeM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, fixedGuaranteeM.getRetentionTypeDesc());
			// WHERE CLAUSE
			ps.setString(cnt++, fixedGuaranteeM.getFixedGuaranteeId());
			
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
	public void deleteNotInKeyFixedGuarantee(
			ArrayList<IncomeCategoryDataM> fixedGuaranteeList, String incomeId)
			throws ApplicationException {
		deleteNotInKeyTableINC_FIXED_GUARANTEE(fixedGuaranteeList, incomeId);
	}

	private void deleteNotInKeyTableINC_FIXED_GUARANTEE(
			ArrayList<IncomeCategoryDataM> fixedGuaranteeList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_FIXED_GUARANTEE ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(fixedGuaranteeList)) {
                sql.append(" AND FIXED_GUARANTEE_ID NOT IN ( ");

                for (IncomeCategoryDataM fixGuaranteeM: fixedGuaranteeList) {
                    sql.append(" '" + fixGuaranteeM.getId() + "' , ");
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
