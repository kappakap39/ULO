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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductCCADataM;

public class OrigPrivilegeProductCCADAOImpl extends OrigObjectDAO implements
		OrigPrivilegeProductCCADAO {
	public OrigPrivilegeProductCCADAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeProductCCADAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeProductCCAM(
			PrivilegeProjectCodeProductCCADataM privilegeProdCCAM)
			throws ApplicationException {
		try {
			logger.debug("privilegeProdCCAM.getProductCcaId() :"+privilegeProdCCAM.getProductCcaId());
		    if(Util.empty(privilegeProdCCAM.getProductCcaId())){
				String productCcaId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_PRODUCT_CCA_PK); 
				privilegeProdCCAM.setProductCcaId(productCcaId);
			}
		    privilegeProdCCAM.setCreateBy(userId);
		    privilegeProdCCAM.setUpdateBy(userId);
			createTableXRULES_PRVLG_PRODUCT_CCA(privilegeProdCCAM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_PRODUCT_CCA(
			PrivilegeProjectCodeProductCCADataM privilegeProdCCAM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_PRODUCT_CCA ");
			sql.append("( PRODUCT_CCA_ID, PRVLG_PRJ_CDE_ID, CCA_PRODUCT, RESULT, ");
			sql.append(" CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?, ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProdCCAM.getProductCcaId());
			ps.setString(cnt++, privilegeProdCCAM.getPrvlgPrjCdeId());
			ps.setString(cnt++, privilegeProdCCAM.getCcaProduct());
			ps.setString(cnt++, privilegeProdCCAM.getResult());
			
			ps.setString(cnt++, privilegeProdCCAM.getCreateBy());
			ps.setString(cnt++, privilegeProdCCAM.getUpdateBy()); 
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
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
	public void deleteOrigPrivilegeProductCCAM(String prvlgPrjCdeId,
			String productCcaId) throws ApplicationException {
		try {
			deleteTableXRULES_PRVLG_PRODUCT_CCA(prvlgPrjCdeId, productCcaId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_PRODUCT_CCA(String prvlgPrjCdeId,
			String productCcaId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_PRODUCT_CCA ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
			if(!Util.empty(productCcaId)) {
				sql.append(" AND PRODUCT_CCA_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prvlgPrjCdeId);
			if(!Util.empty(productCcaId)) {
				ps.setString(2, productCcaId);
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
	public ArrayList<PrivilegeProjectCodeProductCCADataM> loadOrigPrivilegeProductCCAM(
			String prvlgPrjCdeId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeProductCCADataM> result = selectTableXRULES_PRVLG_PRODUCT_CCA(prvlgPrjCdeId);
		return result;
	}

	private ArrayList<PrivilegeProjectCodeProductCCADataM> selectTableXRULES_PRVLG_PRODUCT_CCA(
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeProductCCADataM> prvProdCCAList = new ArrayList<PrivilegeProjectCodeProductCCADataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PRODUCT_CCA_ID, PRVLG_PRJ_CDE_ID, CCA_PRODUCT, RESULT, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_PRVLG_PRODUCT_CCA WHERE PRVLG_PRJ_CDE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prvlgPrjCdeId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeProductCCADataM privProdCCAM = new PrivilegeProjectCodeProductCCADataM();
				privProdCCAM.setProductCcaId(rs.getString("PRODUCT_CCA_ID"));
				privProdCCAM.setPrvlgPrjCdeId(rs.getString("PRVLG_PRJ_CDE_ID"));
				privProdCCAM.setCcaProduct(rs.getString("CCA_PRODUCT"));
				privProdCCAM.setResult(rs.getString("RESULT"));
				
				privProdCCAM.setCreateBy(rs.getString("CREATE_BY"));
				privProdCCAM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				privProdCCAM.setUpdateBy(rs.getString("UPDATE_BY"));
				privProdCCAM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				prvProdCCAList.add(privProdCCAM);
			}

			return prvProdCCAList;
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
	public void saveUpdateOrigPrivilegeProductCCAM(
			PrivilegeProjectCodeProductCCADataM privilegeProdCCAM) throws ApplicationException {
		try { 
			int returnRows = 0;
			privilegeProdCCAM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_PRODUCT_CCA(privilegeProdCCAM);
			if (returnRows == 0) {
				privilegeProdCCAM.setCreateBy(userId);
			    privilegeProdCCAM.setUpdateBy(userId);
				createOrigPrivilegeProductCCAM(privilegeProdCCAM);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_PRODUCT_CCA(
			PrivilegeProjectCodeProductCCADataM privilegeProdCCAM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_PRODUCT_CCA ");
			sql.append(" SET CCA_PRODUCT = ?, RESULT = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? AND PRODUCT_CCA_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProdCCAM.getCcaProduct());
			ps.setString(cnt++, privilegeProdCCAM.getResult());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProdCCAM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, privilegeProdCCAM.getPrvlgPrjCdeId());
			ps.setString(cnt++, privilegeProdCCAM.getProductCcaId());
			
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
	public void deleteNotInKeyPrivilegeProductCCA(
			ArrayList<PrivilegeProjectCodeProductCCADataM> privilegeProdCCAList,
			String prvlgPrjCdeId) throws ApplicationException {
		deleteNotInKeyTableXRULES_PRVLG_PRODUCT_CCA(privilegeProdCCAList, prvlgPrjCdeId);
	}

	private void deleteNotInKeyTableXRULES_PRVLG_PRODUCT_CCA(
			ArrayList<PrivilegeProjectCodeProductCCADataM> privilegeProdCCAList,
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_PRODUCT_CCA ");
            sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
            
            if (!Util.empty(privilegeProdCCAList)) {
                sql.append(" AND PRODUCT_CCA_ID NOT IN ( ");

                for (PrivilegeProjectCodeProductCCADataM prodCCAM: privilegeProdCCAList) {
                    sql.append(" '" + prodCCAM.getProductCcaId() + "' , ");
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
