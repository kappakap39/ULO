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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeTransferDocDataM;

public class OrigPrivilegeProjectCodeTransferDocDAOImpl extends OrigObjectDAO
		implements OrigPrivilegeProjectCodeTransferDocDAO {
	public OrigPrivilegeProjectCodeTransferDocDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeProjectCodeTransferDocDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeProjectCodeTransferDocM(
			PrivilegeProjectCodeTransferDocDataM privilegeProjectCodeTransferDocM)
			throws ApplicationException {
		try {
			logger.debug("privilegeProjectCodeTransferDocM.getPrvlgTransferDocId() :"+privilegeProjectCodeTransferDocM.getPrvlgTransferDocId());
		    if(Util.empty(privilegeProjectCodeTransferDocM.getPrvlgTransferDocId())){
				String prvlgTransferDocId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_TRANSFER_DOC_PK); 
				privilegeProjectCodeTransferDocM.setPrvlgTransferDocId(prvlgTransferDocId);
			}
		    privilegeProjectCodeTransferDocM.setCreateBy(userId);
		    privilegeProjectCodeTransferDocM.setUpdateBy(userId);
			createTableXRULES_PRVLG_TRANSFER_DOC(privilegeProjectCodeTransferDocM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_TRANSFER_DOC(
			PrivilegeProjectCodeTransferDocDataM privilegeProjectCodeTransferDocM) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_TRANSFER_DOC ");
			sql.append("( PRVLG_TRANSFER_DOC_ID, PRVLG_DOC_ID, INVEST_TYPE, ACCOUNT_NO, ");
			sql.append(" HOLDING_RATIO, AMOUNT, ID_NO, CIS_NO, RELATIONSHIP, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getPrvlgTransferDocId());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getPrvlgDocId());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getInvestType());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getAccountNo());
			
			ps.setBigDecimal(cnt++, privilegeProjectCodeTransferDocM.getHoldingRatio());
			ps.setBigDecimal(cnt++, privilegeProjectCodeTransferDocM.getAmount());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getIdNo());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getCisNo());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getRelationship());
			
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getUpdateBy());
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
	public void deleteOrigPrivilegeProjectCodeTransferDocM(String prvlgDocId,
			String prvlgTransferDocId) throws ApplicationException {
		try {
			deleteTableXRULES_PRVLG_TRANSFER_DOC(prvlgDocId, prvlgTransferDocId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_TRANSFER_DOC(String prvlgDocId,
			String prvlgTransferDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_TRANSFER_DOC ");
			sql.append(" WHERE PRVLG_DOC_ID = ? ");
			if(!Util.empty(prvlgTransferDocId)) {
				sql.append(" AND PRVLG_TRANSFER_DOC_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prvlgDocId);
			if(!Util.empty(prvlgTransferDocId)) {
				ps.setString(2, prvlgTransferDocId);
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
	public ArrayList<PrivilegeProjectCodeTransferDocDataM> loadOrigPrivilegeProjectCodeTransferDocM(
			String prvlgDocId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeTransferDocDataM> result = selectTableXRULES_PRVLG_TRANSFER_DOC(prvlgDocId);
		return result;
	}

	private ArrayList<PrivilegeProjectCodeTransferDocDataM> selectTableXRULES_PRVLG_TRANSFER_DOC(
			String prvlgDocId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeTransferDocDataM> prvProjCodeTransferDocList = new ArrayList<PrivilegeProjectCodeTransferDocDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PRVLG_TRANSFER_DOC_ID, PRVLG_DOC_ID, INVEST_TYPE, ACCOUNT_NO, ");
			sql.append(" HOLDING_RATIO, AMOUNT, ID_NO, CIS_NO, RELATIONSHIP, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_PRVLG_TRANSFER_DOC WHERE PRVLG_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prvlgDocId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeTransferDocDataM privProjCodeTrasferDocM = new PrivilegeProjectCodeTransferDocDataM();
				privProjCodeTrasferDocM.setPrvlgTransferDocId(rs.getString("PRVLG_TRANSFER_DOC_ID"));
				privProjCodeTrasferDocM.setPrvlgDocId(rs.getString("PRVLG_DOC_ID"));
				privProjCodeTrasferDocM.setInvestType(rs.getString("INVEST_TYPE"));
				privProjCodeTrasferDocM.setAccountNo(rs.getString("ACCOUNT_NO"));
				
				privProjCodeTrasferDocM.setHoldingRatio(rs.getBigDecimal("HOLDING_RATIO"));
				privProjCodeTrasferDocM.setAmount(rs.getBigDecimal("AMOUNT"));
				privProjCodeTrasferDocM.setIdNo(rs.getString("ID_NO"));
				privProjCodeTrasferDocM.setCisNo(rs.getString("CIS_NO"));
				privProjCodeTrasferDocM.setRelationship(rs.getString("RELATIONSHIP"));
				
				privProjCodeTrasferDocM.setCreateBy(rs.getString("CREATE_BY"));
				privProjCodeTrasferDocM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				privProjCodeTrasferDocM.setUpdateBy(rs.getString("UPDATE_BY"));
				privProjCodeTrasferDocM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				privProjCodeTrasferDocM.setSeq(prvProjCodeTransferDocList.size());
				prvProjCodeTransferDocList.add(privProjCodeTrasferDocM);
			}

			return prvProjCodeTransferDocList;
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
	public void saveUpdateOrigPrivilegeProjectCodeTransferDocM(
			PrivilegeProjectCodeTransferDocDataM privilegeProjectCodeTransferDocM)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			privilegeProjectCodeTransferDocM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_TRANSFER_DOC(privilegeProjectCodeTransferDocM);
			if (returnRows == 0) {
				privilegeProjectCodeTransferDocM.setCreateBy(userId);
			    privilegeProjectCodeTransferDocM.setUpdateBy(userId);
				createOrigPrivilegeProjectCodeTransferDocM(privilegeProjectCodeTransferDocM);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_TRANSFER_DOC(
			PrivilegeProjectCodeTransferDocDataM privilegeProjectCodeTransferDocM) 
					throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_TRANSFER_DOC ");
			sql.append(" SET INVEST_TYPE = ?, ACCOUNT_NO = ?, HOLDING_RATIO = ?, ");
			sql.append(" AMOUNT = ?, ID_NO = ?, CIS_NO = ?, RELATIONSHIP = ?, ");
			sql.append(" UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PRVLG_DOC_ID = ? AND PRVLG_TRANSFER_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getInvestType());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getAccountNo());
			ps.setBigDecimal(cnt++, privilegeProjectCodeTransferDocM.getHoldingRatio());
			
			ps.setBigDecimal(cnt++, privilegeProjectCodeTransferDocM.getAmount());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getIdNo());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getCisNo());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getRelationship());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getPrvlgDocId());
			ps.setString(cnt++, privilegeProjectCodeTransferDocM.getPrvlgTransferDocId());
			
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
	public void deleteNotInKeyPrivilegeProjectCodeTransferDoc(
			ArrayList<PrivilegeProjectCodeTransferDocDataM> privilegeProjectCodeTransferDocList,
			String prvlgDocId) throws ApplicationException {
		deleteNotInKeyTableXRULES_PRVLG_TRANSFER_DOC(privilegeProjectCodeTransferDocList, prvlgDocId);
	}

	private void deleteNotInKeyTableXRULES_PRVLG_TRANSFER_DOC(
			ArrayList<PrivilegeProjectCodeTransferDocDataM> privilegeProjectCodeTransferDocList,
			String prvlgDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_TRANSFER_DOC ");
            sql.append(" WHERE PRVLG_DOC_ID = ? ");
            
            if (!Util.empty(privilegeProjectCodeTransferDocList)) {
                sql.append(" AND PRVLG_TRANSFER_DOC_ID NOT IN ( ");

                for (PrivilegeProjectCodeTransferDocDataM projCodeTrnfrDocM: privilegeProjectCodeTransferDocList) {
                    sql.append(" '" + projCodeTrnfrDocM.getPrvlgTransferDocId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prvlgDocId);
            
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
