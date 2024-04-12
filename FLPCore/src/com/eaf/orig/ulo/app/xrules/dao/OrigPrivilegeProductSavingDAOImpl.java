package com.eaf.orig.ulo.app.xrules.dao;

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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductSavingDataM;

public class OrigPrivilegeProductSavingDAOImpl extends OrigObjectDAO implements
		OrigPrivilegeProductSavingDAO {
	public OrigPrivilegeProductSavingDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeProductSavingDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeProductSavingM(
			PrivilegeProjectCodeProductSavingDataM privilegeProdSavingM) throws ApplicationException {
		try {
			logger.debug("privilegeProdSavingM.getProductSavingId() :"+privilegeProdSavingM.getProductSavingId());
		    if(Util.empty(privilegeProdSavingM.getProductSavingId())){
				String productSavingId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_PRODUCT_SAVING_PK); 
				privilegeProdSavingM.setProductSavingId(productSavingId);
			}
		    privilegeProdSavingM.setCreateBy(userId);
		    privilegeProdSavingM.setUpdateBy(userId);
			createTableXRULES_PRVLG_PRODUCT_SAVING(privilegeProdSavingM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_PRODUCT_SAVING(
			PrivilegeProjectCodeProductSavingDataM privilegeProdSavingM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_PRODUCT_SAVING ");
			sql.append("( PRODUCT_SAVING_ID, PRVLG_PRJ_CDE_ID, ID_NO, CIS_NO, RELATIONSHIP_TRANSFER, ");
			sql.append(" PRODUCT_TYPE, ACCOUNT_NO, HOLDING_RATIO, ACCOUNT_BALANCE, START_VALUES, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProdSavingM.getProductSavingId());
			ps.setString(cnt++, privilegeProdSavingM.getPrvlgPrjCdeId());
			ps.setString(cnt++, privilegeProdSavingM.getIdNo());
			ps.setString(cnt++, privilegeProdSavingM.getCisNo());
			ps.setString(cnt++, privilegeProdSavingM.getRelationshipTransfer());
			
			ps.setString(cnt++, privilegeProdSavingM.getProductType());
			ps.setString(cnt++, privilegeProdSavingM.getAccountNo());
			ps.setBigDecimal(cnt++, privilegeProdSavingM.getHoldingRatio());
			ps.setBigDecimal(cnt++, privilegeProdSavingM.getAccountBalance());
			ps.setBigDecimal(cnt++, privilegeProdSavingM.getAccountBalanceStart());
			
			ps.setString(cnt++, privilegeProdSavingM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProdSavingM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
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
	public void deleteOrigPrivilegeProductSavingM(String prvlgPrjCdeId,
			String productSavingId) throws ApplicationException {
		try {
			deleteTableXRULES_PRVLG_PRODUCT_SAVING(prvlgPrjCdeId, productSavingId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_PRODUCT_SAVING(String prvlgPrjCdeId,
			String productSavingId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_PRODUCT_SAVING ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
			if(!Util.empty(productSavingId)) {
				sql.append(" AND PRODUCT_SAVING_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prvlgPrjCdeId);
			if(!Util.empty(productSavingId)) {
				ps.setString(2, productSavingId);
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
	public ArrayList<PrivilegeProjectCodeProductSavingDataM> loadOrigPrivilegeProductSavingM(
			String prvlgPrjCdeId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeProductSavingDataM> result = selectTableXRULES_PRVLG_PRODUCT_SAVING(prvlgPrjCdeId);
		return result;
	}

	private ArrayList<PrivilegeProjectCodeProductSavingDataM> selectTableXRULES_PRVLG_PRODUCT_SAVING(
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeProductSavingDataM> prodSavingList = new ArrayList<PrivilegeProjectCodeProductSavingDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PRODUCT_SAVING_ID, PRVLG_PRJ_CDE_ID, ID_NO, CIS_NO, RELATIONSHIP_TRANSFER, ");
			sql.append(" PRODUCT_TYPE, ACCOUNT_NO, HOLDING_RATIO, ACCOUNT_BALANCE,START_VALUES, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_PRVLG_PRODUCT_SAVING WHERE PRVLG_PRJ_CDE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prvlgPrjCdeId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeProductSavingDataM prodSavingM = new PrivilegeProjectCodeProductSavingDataM();
				prodSavingM.setProductSavingId(rs.getString("PRODUCT_SAVING_ID"));
				prodSavingM.setPrvlgPrjCdeId(rs.getString("PRVLG_PRJ_CDE_ID"));
				prodSavingM.setIdNo(rs.getString("ID_NO"));
				prodSavingM.setCisNo(rs.getString("CIS_NO"));
				prodSavingM.setRelationshipTransfer(rs.getString("RELATIONSHIP_TRANSFER"));
				
				prodSavingM.setProductType(rs.getString("PRODUCT_TYPE"));
				prodSavingM.setAccountNo(rs.getString("ACCOUNT_NO"));
				prodSavingM.setHoldingRatio(rs.getBigDecimal("HOLDING_RATIO"));
				prodSavingM.setAccountBalance(rs.getBigDecimal("ACCOUNT_BALANCE"));
				prodSavingM.setAccountBalanceStart(rs.getBigDecimal("START_VALUES"));
				
				prodSavingM.setCreateBy(rs.getString("CREATE_BY"));
				prodSavingM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				prodSavingM.setUpdateBy(rs.getString("UPDATE_BY"));
				prodSavingM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				prodSavingM.setSeq(prodSavingList.size());
				prodSavingList.add(prodSavingM);
			}

			return prodSavingList;
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
	public void saveUpdateOrigPrivilegeProductSavingM(
			PrivilegeProjectCodeProductSavingDataM privilegeProdSavingM) throws ApplicationException {
		try { 
			int returnRows = 0;
			privilegeProdSavingM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_PRODUCT_SAVING(privilegeProdSavingM);
			if (returnRows == 0) {
				privilegeProdSavingM.setCreateBy(userId);
			    privilegeProdSavingM.setUpdateBy(userId);
				createOrigPrivilegeProductSavingM(privilegeProdSavingM);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_PRODUCT_SAVING(
			PrivilegeProjectCodeProductSavingDataM privilegeProdSavingM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_PRODUCT_SAVING ");
			sql.append(" SET ID_NO = ?, CIS_NO = ?, RELATIONSHIP_TRANSFER = ?, PRODUCT_TYPE = ?, ");
			sql.append(" ACCOUNT_NO = ?, HOLDING_RATIO = ?, ACCOUNT_BALANCE = ?, START_VALUES = ?, ");
			sql.append(" UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? AND PRODUCT_SAVING_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProdSavingM.getIdNo());
			ps.setString(cnt++, privilegeProdSavingM.getCisNo());
			ps.setString(cnt++, privilegeProdSavingM.getRelationshipTransfer());
			ps.setString(cnt++, privilegeProdSavingM.getProductType());
			
			ps.setString(cnt++, privilegeProdSavingM.getAccountNo());
			ps.setBigDecimal(cnt++, privilegeProdSavingM.getHoldingRatio());
			ps.setBigDecimal(cnt++, privilegeProdSavingM.getAccountBalance());
			ps.setBigDecimal(cnt++, privilegeProdSavingM.getAccountBalanceStart());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProdSavingM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, privilegeProdSavingM.getPrvlgPrjCdeId());
			ps.setString(cnt++, privilegeProdSavingM.getProductSavingId());
			
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
	public void deleteNotInKeyPrivilegeProductSaving(
			ArrayList<PrivilegeProjectCodeProductSavingDataM> privilegeProdSavingList,
			String prvlgPrjCdeId) throws ApplicationException {
		deleteNotInKeyTableXRULES_PRVLG_PRODUCT_SAVING(privilegeProdSavingList, prvlgPrjCdeId);
	}

	private void deleteNotInKeyTableXRULES_PRVLG_PRODUCT_SAVING(
			ArrayList<PrivilegeProjectCodeProductSavingDataM> privilegeProdSavingList,
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_PRODUCT_SAVING ");
            sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
            
            if (!Util.empty(privilegeProdSavingList)) {
                sql.append(" AND PRODUCT_SAVING_ID NOT IN ( ");

                for (PrivilegeProjectCodeProductSavingDataM prodSavingM: privilegeProdSavingList) {
                    sql.append(" '" + prodSavingM.getProductSavingId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prvlgPrjCdeId);
            
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
