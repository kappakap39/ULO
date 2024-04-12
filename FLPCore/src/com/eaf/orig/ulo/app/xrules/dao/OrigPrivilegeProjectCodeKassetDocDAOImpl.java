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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeKassetDocDataM;

public class OrigPrivilegeProjectCodeKassetDocDAOImpl extends OrigObjectDAO
		implements OrigPrivilegeProjectCodeKassetDocDAO {
	public OrigPrivilegeProjectCodeKassetDocDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeProjectCodeKassetDocDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeProjectCodeKassetDocM(
			PrivilegeProjectCodeKassetDocDataM privilegeProjectCodeKassetDocM)
			throws ApplicationException {
		try {
			logger.debug("privilegeProjectCodeKassetDocM.getKassetDocId() :"+privilegeProjectCodeKassetDocM.getKassetDocId());
		    if(Util.empty(privilegeProjectCodeKassetDocM.getKassetDocId())){
				String kassetDocId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_KASSET_DOC_PK); 
				privilegeProjectCodeKassetDocM.setKassetDocId(kassetDocId);
			}
		    privilegeProjectCodeKassetDocM.setCreateBy(userId);
		    privilegeProjectCodeKassetDocM.setUpdateBy(userId);
			createTableXRULES_PRVLG_KASSET_DOC(privilegeProjectCodeKassetDocM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_KASSET_DOC(
			PrivilegeProjectCodeKassetDocDataM privilegeProjectCodeKassetDocM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_KASSET_DOC ");
			sql.append("( KASSET_DOC_ID, PRVLG_DOC_ID, ");
			sql.append(" FUND_6M, MONTH1_AMT, MONTH2_AMT, MONTH3_AMT, ");
			sql.append(" MONTH4_AMT, MONTH5_AMT, MONTH6_AMT, KASSET_TYPE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?, ?,?,?,?, ?,?,?,?, ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeKassetDocM.getKassetDocId());
			ps.setString(cnt++, privilegeProjectCodeKassetDocM.getPrvlgDocId());
			
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getFund6m());
//			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getFundL6m());
//			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getWealth());
//			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getIncome());
			
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth1m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth2m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth3m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth4m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth5m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth6m());
			
			ps.setString(cnt++, privilegeProjectCodeKassetDocM.getKassetType());
			
			ps.setString(cnt++, privilegeProjectCodeKassetDocM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeKassetDocM.getUpdateBy());
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
	public void deleteOrigPrivilegeProjectCodeKassetDocM(String prvlgDocId,
			String kassetDocId) throws ApplicationException {
		try {
			deleteTableXRULES_PRVLG_KASSET_DOC(prvlgDocId, kassetDocId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_KASSET_DOC(String prvlgDocId,
			String kassetDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_KASSET_DOC ");
			sql.append(" WHERE PRVLG_DOC_ID = ? ");
			if(!Util.empty(kassetDocId)) {
				sql.append(" AND KASSET_DOC_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prvlgDocId);
			if(!Util.empty(kassetDocId)) {
				ps.setString(2, kassetDocId);
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
	public ArrayList<PrivilegeProjectCodeKassetDocDataM> loadOrigPrivilegeProjectCodeKassetDocM(
			String prvlgDocId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeKassetDocDataM> result = selectTableXRULES_PRVLG_KASSET_DOC(prvlgDocId);
		return result;
	}

	private ArrayList<PrivilegeProjectCodeKassetDocDataM> selectTableXRULES_PRVLG_KASSET_DOC(
			String prvlgDocId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeKassetDocDataM> prvKassetDocList = new ArrayList<PrivilegeProjectCodeKassetDocDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT KASSET_DOC_ID, PRVLG_DOC_ID, ");		
			sql.append(" FUND_6M, MONTH1_AMT, MONTH2_AMT, MONTH3_AMT, ");
			sql.append(" MONTH4_AMT, MONTH5_AMT, MONTH6_AMT, KASSET_TYPE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_PRVLG_KASSET_DOC WHERE PRVLG_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prvlgDocId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeKassetDocDataM privKassetDocM = new PrivilegeProjectCodeKassetDocDataM();
				privKassetDocM.setKassetDocId(rs.getString("KASSET_DOC_ID"));
				privKassetDocM.setPrvlgDocId(rs.getString("PRVLG_DOC_ID"));
				
				privKassetDocM.setFund6m(rs.getBigDecimal("FUND_6M"));
				privKassetDocM.setMonth1m(rs.getBigDecimal("MONTH1_AMT"));
				privKassetDocM.setMonth2m(rs.getBigDecimal("MONTH2_AMT"));
				privKassetDocM.setMonth3m(rs.getBigDecimal("MONTH3_AMT"));
				privKassetDocM.setMonth4m(rs.getBigDecimal("MONTH4_AMT"));
				privKassetDocM.setMonth5m(rs.getBigDecimal("MONTH5_AMT"));
				privKassetDocM.setMonth6m(rs.getBigDecimal("MONTH6_AMT"));
				
				privKassetDocM.setKassetType(rs.getString("KASSET_TYPE"));
				
				privKassetDocM.setCreateBy(rs.getString("CREATE_BY"));
				privKassetDocM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				privKassetDocM.setUpdateBy(rs.getString("UPDATE_BY"));
				privKassetDocM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				prvKassetDocList.add(privKassetDocM);
			}

			return prvKassetDocList;
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
	public void saveUpdateOrigPrivilegeProjectCodeKassetDocM(
			PrivilegeProjectCodeKassetDocDataM privilegeProjectCodeKassetDocM)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			privilegeProjectCodeKassetDocM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_KASSET_DOC(privilegeProjectCodeKassetDocM);
			if (returnRows == 0) {
				privilegeProjectCodeKassetDocM.setCreateBy(userId);
			    privilegeProjectCodeKassetDocM.setUpdateBy(userId);
				createOrigPrivilegeProjectCodeKassetDocM(privilegeProjectCodeKassetDocM);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_KASSET_DOC(
			PrivilegeProjectCodeKassetDocDataM privilegeProjectCodeKassetDocM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_KASSET_DOC ");
			sql.append(" SET FUND_6M = ?, MONTH1_AMT = ?, MONTH2_AMT = ?, MONTH3_AMT = ?,  ");
			sql.append(" MONTH4_AMT = ?, MONTH5_AMT = ?, MONTH6_AMT = ?, KASSET_TYPE = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PRVLG_DOC_ID = ? AND KASSET_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getFund6m());
//			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getFundL6m());
//			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getWealth());
			
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth1m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth2m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth3m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth4m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth5m());
			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getMonth6m());
			
			ps.setString(cnt++, privilegeProjectCodeKassetDocM.getKassetType());
			
//			ps.setBigDecimal(cnt++, privilegeProjectCodeKassetDocM.getIncome());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeKassetDocM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, privilegeProjectCodeKassetDocM.getPrvlgDocId());
			ps.setString(cnt++, privilegeProjectCodeKassetDocM.getKassetDocId());
			
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
	public void deleteNotInKeyPrivilegeProjectCodeKassetDoc(
			ArrayList<PrivilegeProjectCodeKassetDocDataM> privilegeProjectCodeKassetDocList,
			String prvlgDocId) throws ApplicationException {
		deleteNotInKeyTableXRULES_PRVLG_KASSET_DOC(privilegeProjectCodeKassetDocList, prvlgDocId);
	}

	private void deleteNotInKeyTableXRULES_PRVLG_KASSET_DOC(
			ArrayList<PrivilegeProjectCodeKassetDocDataM> privilegeProjectCodeKassetDocList,
			String prvlgDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_KASSET_DOC ");
            sql.append(" WHERE PRVLG_DOC_ID = ? ");
            
            if (!Util.empty(privilegeProjectCodeKassetDocList)) {
                sql.append(" AND KASSET_DOC_ID NOT IN ( ");

                for (PrivilegeProjectCodeKassetDocDataM prvKassetDocM: privilegeProjectCodeKassetDocList) {
                    sql.append(" '" + prvKassetDocM.getKassetDocId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            logger.debug("Sql="+dSql);
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
