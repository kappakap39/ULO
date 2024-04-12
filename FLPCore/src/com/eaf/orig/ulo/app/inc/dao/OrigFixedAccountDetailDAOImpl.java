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
import com.eaf.orig.ulo.model.app.FixedAccountDetailDataM;

public class OrigFixedAccountDetailDAOImpl extends OrigObjectDAO implements
		OrigFixedAccountDetailDAO {
	public OrigFixedAccountDetailDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigFixedAccountDetailDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigFixedAccountDetailM(
			FixedAccountDetailDataM fixedAccountDetailM) throws ApplicationException {
		logger.debug("fixedAccountDetailM.getFixedAccDetailId() :"+fixedAccountDetailM.getFixedAccDetailId());
	    if(Util.empty(fixedAccountDetailM.getFixedAccDetailId())){
			String fixedAcctId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_FIXED_ACC_DETAIL_PK);
			fixedAccountDetailM.setFixedAccDetailId(fixedAcctId);
			fixedAccountDetailM.setCreateBy(userId);
	    }
	    fixedAccountDetailM.setUpdateBy(userId);
		createTableINC_FIXED_ACC_DETAIL(fixedAccountDetailM);
	}

	private void createTableINC_FIXED_ACC_DETAIL(
			FixedAccountDetailDataM fixedAccountDetailM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_FIXED_ACC_DETAIL ");
			sql.append("( FIXED_ACC_DETAIL_ID, FIXED_ACC_ID, SUB, ");
			sql.append(" DEPOSIT_DATE, OUTSTANDING_BALANCE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,  ?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, fixedAccountDetailM.getFixedAccDetailId());
			ps.setString(cnt++, fixedAccountDetailM.getFixedAccId());
			ps.setString(cnt++, fixedAccountDetailM.getSub());
			
			ps.setDate(cnt++, fixedAccountDetailM.getDepositDate());
			ps.setBigDecimal(cnt++, fixedAccountDetailM.getOutstandingBalance());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, fixedAccountDetailM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, fixedAccountDetailM.getUpdateBy());
			
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
	public void deleteOrigFixedAccountDetailM(String fixedAccId,
			String fixedAccDetailId) throws ApplicationException {
		deleteTableINC_FIXED_ACC_DETAIL(fixedAccId, fixedAccDetailId);
	}

	private void deleteTableINC_FIXED_ACC_DETAIL(String fixedAccId,
			String fixedAccDetailId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_FIXED_ACC_DETAIL ");
			sql.append(" WHERE FIXED_ACC_ID = ? ");
			if(!Util.empty(fixedAccDetailId)) {
				sql.append(" AND FIXED_ACC_DETAIL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, fixedAccId);
			if(!Util.empty(fixedAccDetailId)) {
				ps.setString(2, fixedAccDetailId);
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
	public ArrayList<FixedAccountDetailDataM> loadOrigFixedAccountDetailM(
			String fixedAccId) throws ApplicationException {
		ArrayList<FixedAccountDetailDataM> result = selectTableINC_FIXED_ACC_DETAIL(fixedAccId);
		return result;
	}

	private ArrayList<FixedAccountDetailDataM> selectTableINC_FIXED_ACC_DETAIL(
			String fixedAccId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<FixedAccountDetailDataM> fixAcctDetailList = new ArrayList<FixedAccountDetailDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT FIXED_ACC_DETAIL_ID, FIXED_ACC_ID, SUB, ");
			sql.append(" DEPOSIT_DATE, OUTSTANDING_BALANCE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM INC_FIXED_ACC_DETAIL WHERE FIXED_ACC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, fixedAccId);

			rs = ps.executeQuery();

			while (rs.next()) {
				FixedAccountDetailDataM fixAcctDetailM = new FixedAccountDetailDataM();
				fixAcctDetailM.setSeq(fixAcctDetailList.size()+1);
				fixAcctDetailM.setFixedAccDetailId(rs.getString("FIXED_ACC_DETAIL_ID"));
				fixAcctDetailM.setFixedAccId(rs.getString("FIXED_ACC_ID"));
				fixAcctDetailM.setSub(rs.getString("SUB"));
				
				fixAcctDetailM.setDepositDate(rs.getDate("DEPOSIT_DATE"));
				fixAcctDetailM.setOutstandingBalance(rs.getBigDecimal("OUTSTANDING_BALANCE"));
				
				fixAcctDetailM.setCreateBy(rs.getString("CREATE_BY"));
				fixAcctDetailM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				fixAcctDetailM.setUpdateBy(rs.getString("UPDATE_BY"));
				fixAcctDetailM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				fixAcctDetailList.add(fixAcctDetailM);
			}

			return fixAcctDetailList;
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
	public void saveUpdateOrigFixedAccountDetailM(
			FixedAccountDetailDataM fixedAccountDetailM) throws ApplicationException {
		int returnRows = 0;
		fixedAccountDetailM.setUpdateBy(userId);
		returnRows = updateTableINC_FIXED_ACC_DETAIL(fixedAccountDetailM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_FIXED_ACC_DETAIL then call Insert method");
			fixedAccountDetailM.setCreateBy(userId);
			createOrigFixedAccountDetailM(fixedAccountDetailM);
		}
	}

	private int updateTableINC_FIXED_ACC_DETAIL(
			FixedAccountDetailDataM fixedAccountDetailM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_FIXED_ACC_DETAIL ");
			sql.append(" SET SUB = ?, DEPOSIT_DATE = ?, OUTSTANDING_BALANCE = ?, ");
			sql.append(" UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE FIXED_ACC_DETAIL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;			
			ps.setString(cnt++, fixedAccountDetailM.getSub());
			ps.setDate(cnt++, fixedAccountDetailM.getDepositDate());
			ps.setBigDecimal(cnt++, fixedAccountDetailM.getOutstandingBalance());
			
			ps.setString(cnt++, fixedAccountDetailM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, fixedAccountDetailM.getFixedAccDetailId());
			
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
	public void deleteNotInKeyFixedAccountDetail(
			ArrayList<FixedAccountDetailDataM> fixedAccountDtlList, String fixedAccId)
			throws ApplicationException {
		deleteNotInKeyTableINC_FIXED_ACC_DETAIL(fixedAccountDtlList, fixedAccId);
	}

	private void deleteNotInKeyTableINC_FIXED_ACC_DETAIL(
			ArrayList<FixedAccountDetailDataM> fixedAccountDtlList,
			String fixedAccId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_FIXED_ACC_DETAIL ");
            sql.append(" WHERE FIXED_ACC_ID = ? ");
            
            if (!Util.empty(fixedAccountDtlList)) {
                sql.append(" AND FIXED_ACC_DETAIL_ID NOT IN ( ");

                for (FixedAccountDetailDataM fixAccDtlM: fixedAccountDtlList) {
                    sql.append(" '" + fixAccDtlM.getFixedAccDetailId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, fixedAccId);
            
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
