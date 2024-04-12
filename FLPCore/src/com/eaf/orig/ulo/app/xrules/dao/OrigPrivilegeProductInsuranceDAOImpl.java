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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM;

public class OrigPrivilegeProductInsuranceDAOImpl extends OrigObjectDAO
		implements OrigPrivilegeProductInsuranceDAO {
	public OrigPrivilegeProductInsuranceDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeProductInsuranceDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeProductInsuranceM(
			PrivilegeProjectCodeProductInsuranceDataM privilegeProdInsuranceM)
			throws ApplicationException {
		try {
			logger.debug("privilegeProdInsuranceM.getProductInsuranceId() :"+privilegeProdInsuranceM.getProductInsuranceId());
		    if(Util.empty(privilegeProdInsuranceM.getProductInsuranceId())){
				String productInsuranceId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_PROD_INSURANCE_PK); 
				privilegeProdInsuranceM.setProductInsuranceId(productInsuranceId);
			}
		    privilegeProdInsuranceM.setCreateBy(userId);
		    privilegeProdInsuranceM.setUpdateBy(userId);
			createTableXRULES_PRVLG_PRODUCT_INSURANCE(privilegeProdInsuranceM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_PRODUCT_INSURANCE(
			PrivilegeProjectCodeProductInsuranceDataM privilegeProdInsuranceM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_PRODUCT_INSURANCE ");
			sql.append("( PRODUCT_INSURANCE_ID, PRVLG_PRJ_CDE_ID, ");
			sql.append(" INSURANCE_TYPE, POLICY_NO, PREMIUM,  ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE , CIS_NO, RELATIONSHIP ) ");
			sql.append(" VALUES(?,?,  ?,?,?, ?,? ,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProdInsuranceM.getProductInsuranceId());
			ps.setString(cnt++, privilegeProdInsuranceM.getPrvlgPrjCdeId());
			
			ps.setString(cnt++, privilegeProdInsuranceM.getInsuranceType());
			ps.setString(cnt++, privilegeProdInsuranceM.getPolicyNo());
			ps.setBigDecimal(cnt++, privilegeProdInsuranceM.getPremium());
						
			ps.setString(cnt++, privilegeProdInsuranceM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProdInsuranceM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setString(cnt++, privilegeProdInsuranceM.getCisno());
			ps.setString(cnt++, privilegeProdInsuranceM.getRelationTranfer());
			
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
	public void deleteOrigPrivilegeProductInsuranceM(String prvlgPrjCdeId,
			String productInsuranceId) throws ApplicationException {
		try {
			deleteTableXRULES_PRVLG_PRODUCT_INSURANCE(prvlgPrjCdeId, productInsuranceId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_PRODUCT_INSURANCE(
			String prvlgPrjCdeId, String productInsuranceId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_PRODUCT_INSURANCE ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
			if(!Util.empty(productInsuranceId)) {
				sql.append(" AND PRODUCT_INSURANCE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prvlgPrjCdeId);
			if(!Util.empty(productInsuranceId)) {
				ps.setString(2, productInsuranceId);
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
	public ArrayList<PrivilegeProjectCodeProductInsuranceDataM> loadOrigPrivilegeProductInsuranceM(
			String prvlgPrjCdeId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeProductInsuranceDataM> result = selectTableXRULES_PRVLG_PRODUCT_INSURANCE(prvlgPrjCdeId);
		return result;
	}

	private ArrayList<PrivilegeProjectCodeProductInsuranceDataM> selectTableXRULES_PRVLG_PRODUCT_INSURANCE(
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeProductInsuranceDataM> prvProdInsuranceList = new ArrayList<PrivilegeProjectCodeProductInsuranceDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PRODUCT_INSURANCE_ID, PRVLG_PRJ_CDE_ID, ");
			sql.append(" INSURANCE_TYPE, POLICY_NO, PREMIUM, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ,CIS_NO, RELATIONSHIP ");
			sql.append(" FROM XRULES_PRVLG_PRODUCT_INSURANCE WHERE PRVLG_PRJ_CDE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prvlgPrjCdeId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeProductInsuranceDataM privProdInsuranceM = new PrivilegeProjectCodeProductInsuranceDataM();
				privProdInsuranceM.setProductInsuranceId(rs.getString("PRODUCT_INSURANCE_ID"));
				privProdInsuranceM.setPrvlgPrjCdeId(rs.getString("PRVLG_PRJ_CDE_ID"));
				
				privProdInsuranceM.setInsuranceType(rs.getString("INSURANCE_TYPE"));
				privProdInsuranceM.setPolicyNo(rs.getString("POLICY_NO"));
				privProdInsuranceM.setPremium(rs.getBigDecimal("PREMIUM"));
				privProdInsuranceM.setCisno(rs.getString("CIS_NO"));
				privProdInsuranceM.setRelationTranfer(rs.getString("RELATIONSHIP"));
				
				privProdInsuranceM.setCreateBy(rs.getString("CREATE_BY"));
				privProdInsuranceM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				privProdInsuranceM.setUpdateBy(rs.getString("UPDATE_BY"));
				privProdInsuranceM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				privProdInsuranceM.setSeq(prvProdInsuranceList.size());
				prvProdInsuranceList.add(privProdInsuranceM);
			}

			return prvProdInsuranceList;
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
	public void saveUpdateOrigPrivilegeProductInsuranceM(
			PrivilegeProjectCodeProductInsuranceDataM privilegeProdInsuranceM)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			privilegeProdInsuranceM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_PRODUCT_INSURANCE(privilegeProdInsuranceM);
			if (returnRows == 0) {
				privilegeProdInsuranceM.setCreateBy(userId);
			    privilegeProdInsuranceM.setUpdateBy(userId);
				createOrigPrivilegeProductInsuranceM(privilegeProdInsuranceM);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_PRODUCT_INSURANCE(
			PrivilegeProjectCodeProductInsuranceDataM privilegeProdInsuranceM) 
					throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_PRODUCT_INSURANCE ");
			sql.append(" SET INSURANCE_TYPE = ?, POLICY_NO = ?, PREMIUM = ?, ");
			sql.append(" UPDATE_DATE = ?, UPDATE_BY = ? , CIS_NO = ? , RELATIONSHIP = ? ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? AND PRODUCT_INSURANCE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProdInsuranceM.getInsuranceType());
			ps.setString(cnt++, privilegeProdInsuranceM.getPolicyNo());
			ps.setBigDecimal(cnt++, privilegeProdInsuranceM.getPremium());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProdInsuranceM.getUpdateBy());
			
			ps.setString(cnt++, privilegeProdInsuranceM.getCisno());
			ps.setString(cnt++, privilegeProdInsuranceM.getRelationTranfer());
			// WHERE CLAUSE
			ps.setString(cnt++, privilegeProdInsuranceM.getPrvlgPrjCdeId());
			ps.setString(cnt++, privilegeProdInsuranceM.getProductInsuranceId());
			
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
	public void deleteNotInKeyPrivilegeProductInsurance(
			ArrayList<PrivilegeProjectCodeProductInsuranceDataM> privilegeProdInsuranceList,
			String verResultId) throws ApplicationException {
		deleteNotInKeyTableXRULES_PRVLG_PRODUCT_INSURANCE(privilegeProdInsuranceList, verResultId);
	}

	private void deleteNotInKeyTableXRULES_PRVLG_PRODUCT_INSURANCE(
			ArrayList<PrivilegeProjectCodeProductInsuranceDataM> privilegeProdInsuranceList,
			String verResultId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_PRODUCT_INSURANCE ");
            sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
            
            if (!Util.empty(privilegeProdInsuranceList)) {
                sql.append(" AND PRODUCT_INSURANCE_ID NOT IN ( ");

                for (PrivilegeProjectCodeProductInsuranceDataM prodInsuranceM: privilegeProdInsuranceList) {
                    sql.append(" '" + prodInsuranceM.getProductInsuranceId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, verResultId);
            
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
