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
import com.eaf.orig.ulo.model.app.SavingAccountDetailDataM;

public class OrigSavingAccountDetailDAOImpl extends OrigObjectDAO implements
		OrigSavingAccountDetailDAO {
	public OrigSavingAccountDetailDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigSavingAccountDetailDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigSavingAccountDetailM(
			SavingAccountDetailDataM savingAccountDetailM) throws ApplicationException {
		logger.debug("savingAccountDetailM.getSavingAccDetailId() :" + 
				savingAccountDetailM.getSavingAccDetailId());
	    if(Util.empty(savingAccountDetailM.getSavingAccDetailId())){
			String detailId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_SAVING_ACC_DETAIL_PK);
			savingAccountDetailM.setSavingAccDetailId(detailId);
			savingAccountDetailM.setCreateBy(userId);
	    }
	    savingAccountDetailM.setUpdateBy(userId);
		createTableINC_SAVING_ACC_DETAIL(savingAccountDetailM);
	}

	private void createTableINC_SAVING_ACC_DETAIL(
			SavingAccountDetailDataM savingAccountDetailM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_SAVING_ACC_DETAIL ");
			sql.append("( SAVING_ACC_DETAIL_ID, SAVING_ACC_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,  ?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, savingAccountDetailM.getSavingAccDetailId());
			ps.setString(cnt++, savingAccountDetailM.getSavingAccId());
			
			ps.setString(cnt++, savingAccountDetailM.getMonth());
			ps.setString(cnt++, savingAccountDetailM.getYear());
			ps.setBigDecimal(cnt++, savingAccountDetailM.getAmount());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, savingAccountDetailM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, savingAccountDetailM.getUpdateBy());
			
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
	public void deleteOrigSavingAccountDetailM(String savingAccId,
			String savingAccDetailId) throws ApplicationException {
		deleteTableINC_SAVING_ACC_DETAIL(savingAccId, savingAccDetailId);
	}

	private void deleteTableINC_SAVING_ACC_DETAIL(String savingAccId,
			String savingAccDetailId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_SAVING_ACC_DETAIL ");
			sql.append(" WHERE SAVING_ACC_ID = ? ");
			if(!Util.empty(savingAccDetailId)) {
				sql.append(" AND SAVING_ACC_DETAIL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, savingAccId);
			if(!Util.empty(savingAccDetailId)) {
				ps.setString(2, savingAccDetailId);
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
	public ArrayList<SavingAccountDetailDataM> loadOrigSavingAccountDetailM(
			String savingAccId) throws ApplicationException {
		ArrayList<SavingAccountDetailDataM> result = selectTableINC_SAVING_ACC_DETAIL(savingAccId);
		return result;
	}

	private ArrayList<SavingAccountDetailDataM> selectTableINC_SAVING_ACC_DETAIL(
			String savingAccId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<SavingAccountDetailDataM> savAcctDetailList = new ArrayList<SavingAccountDetailDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT SAVING_ACC_DETAIL_ID, SAVING_ACC_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_SAVING_ACC_DETAIL WHERE SAVING_ACC_ID = ? ");
			sql.append(" ORDER BY SAVING_ACC_DETAIL_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, savingAccId);

			rs = ps.executeQuery();

			while (rs.next()) {
				SavingAccountDetailDataM saveAcctDetailM = new SavingAccountDetailDataM();
				saveAcctDetailM.setSeq(savAcctDetailList.size()+1);
				saveAcctDetailM.setSavingAccDetailId(rs.getString("SAVING_ACC_DETAIL_ID"));
				saveAcctDetailM.setSavingAccId(rs.getString("SAVING_ACC_ID"));
				
				saveAcctDetailM.setMonth(rs.getString("MONTH"));
				saveAcctDetailM.setYear(rs.getString("YEAR"));
				saveAcctDetailM.setAmount(rs.getBigDecimal("AMOUNT"));
				
				saveAcctDetailM.setCreateBy(rs.getString("CREATE_BY"));
				saveAcctDetailM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				saveAcctDetailM.setUpdateBy(rs.getString("UPDATE_BY"));
				saveAcctDetailM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				savAcctDetailList.add(saveAcctDetailM);
			}

			return savAcctDetailList;
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
	public void saveUpdateOrigSavingAccountDetailM(
			SavingAccountDetailDataM savingAccountDetailM) throws ApplicationException {
		int returnRows = 0;
		savingAccountDetailM.setUpdateBy(userId);
		returnRows = updateTableINC_SAVING_ACC_DETAIL(savingAccountDetailM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_SAVING_ACC_DETAIL then call Insert method");
			savingAccountDetailM.setCreateBy(userId);
			createOrigSavingAccountDetailM(savingAccountDetailM);
		}
	}

	private int updateTableINC_SAVING_ACC_DETAIL(
			SavingAccountDetailDataM savingAccountDetailM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_SAVING_ACC_DETAIL ");
			sql.append(" SET MONTH = ?, YEAR = ?, AMOUNT = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE SAVING_ACC_DETAIL_ID = ? AND SAVING_ACC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, savingAccountDetailM.getMonth());
			ps.setString(cnt++, savingAccountDetailM.getYear());
			ps.setBigDecimal(cnt++, savingAccountDetailM.getAmount());
			
			ps.setString(cnt++, savingAccountDetailM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, savingAccountDetailM.getSavingAccDetailId());
			ps.setString(cnt++, savingAccountDetailM.getSavingAccId());
			
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
	public void deleteNotInKeySavingAccountDetail(
			ArrayList<SavingAccountDetailDataM> savingAccountDtlMList,
			String savingAccId) throws ApplicationException {
		deleteNotInKeyTableINC_SAVING_ACC_DETAIL(savingAccountDtlMList, savingAccId);
	}

	private void deleteNotInKeyTableINC_SAVING_ACC_DETAIL(
			ArrayList<SavingAccountDetailDataM> savingAccountDtlMList,
			String savingAccId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_SAVING_ACC_DETAIL ");
            sql.append(" WHERE SAVING_ACC_ID = ? ");
            
            if (!Util.empty(savingAccountDtlMList)) {
                sql.append(" AND SAVING_ACC_DETAIL_ID NOT IN ( ");

                for (SavingAccountDetailDataM detailM: savingAccountDtlMList) {
                    sql.append(" '" + detailM.getSavingAccDetailId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, savingAccId);
            
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
