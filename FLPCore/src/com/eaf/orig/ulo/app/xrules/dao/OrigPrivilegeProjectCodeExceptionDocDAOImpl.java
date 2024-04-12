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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeExceptionDocDataM;

public class OrigPrivilegeProjectCodeExceptionDocDAOImpl extends OrigObjectDAO
		implements OrigPrivilegeProjectCodeExceptionDocDAO {
	public OrigPrivilegeProjectCodeExceptionDocDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeProjectCodeExceptionDocDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeProjectCodeExceptionDocM(
			PrivilegeProjectCodeExceptionDocDataM privilegeProjectCodeExceptionDocM)
			throws ApplicationException {
		try {
			logger.debug("privilegeProjectCodeExceptionDocM.getExceptDocId() :"+privilegeProjectCodeExceptionDocM.getExceptDocId());
		    if(Util.empty(privilegeProjectCodeExceptionDocM.getExceptDocId())){
				String exceptDocId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_EXCEPTION_DOC_PK); 
				privilegeProjectCodeExceptionDocM.setExceptDocId(exceptDocId);
			}
		    privilegeProjectCodeExceptionDocM.setCreateBy(userId);
		    privilegeProjectCodeExceptionDocM.setUpdateBy(userId);
			createTableXRULES_PRVLG_EXCEPTION_DOC(privilegeProjectCodeExceptionDocM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_EXCEPTION_DOC(
			PrivilegeProjectCodeExceptionDocDataM privilegeProjectCodeExceptionDocM) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_EXCEPTION_DOC ");
			sql.append("( EXCEPT_DOC_ID, PRVLG_DOC_ID, EXCEPT_POLICY, EXCEPT_POLICY_OTH, EXCEPT_DOC_FROM, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getExceptDocId());
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getPrvlgDocId());
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getExceptPolicy());
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getExceptPolicyOth());
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getExceptDocFrom());
			
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getUpdateBy());
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
	public void deleteOrigPrivilegeProjectCodeExceptionDocM(String prvlgDocId,
			String exceptDocId) throws ApplicationException {
		try {
			deleteTableXRULES_PRVLG_EXCEPTION_DOC(prvlgDocId, exceptDocId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_EXCEPTION_DOC(String prvlgDocId,
			String exceptDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_EXCEPTION_DOC ");
			sql.append(" WHERE PRVLG_DOC_ID = ? ");
			if(!Util.empty(exceptDocId)) {
				sql.append(" AND EXCEPT_DOC_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prvlgDocId);
			if(!Util.empty(exceptDocId)) {
				ps.setString(2, exceptDocId);
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
	public ArrayList<PrivilegeProjectCodeExceptionDocDataM> loadOrigPrivilegeProjectCodeExceptionDocM(
			String prvlgDocId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeExceptionDocDataM> result = selectTableXRULES_PRVLG_EXCEPTION_DOC(prvlgDocId);
		return result;
	}

	private ArrayList<PrivilegeProjectCodeExceptionDocDataM> selectTableXRULES_PRVLG_EXCEPTION_DOC(
			String prvlgDocId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeExceptionDocDataM> prvProjCodeExptDocList = new ArrayList<PrivilegeProjectCodeExceptionDocDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT EXCEPT_DOC_ID, PRVLG_DOC_ID, EXCEPT_POLICY, EXCEPT_POLICY_OTH, EXCEPT_DOC_FROM, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_PRVLG_EXCEPTION_DOC WHERE PRVLG_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prvlgDocId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeExceptionDocDataM privProjCodeExcptDocM = new PrivilegeProjectCodeExceptionDocDataM();
				privProjCodeExcptDocM.setExceptDocId(rs.getString("EXCEPT_DOC_ID"));
				privProjCodeExcptDocM.setPrvlgDocId(rs.getString("PRVLG_DOC_ID"));
				privProjCodeExcptDocM.setExceptPolicy(rs.getString("EXCEPT_POLICY"));
				privProjCodeExcptDocM.setExceptPolicyOth(rs.getString("EXCEPT_POLICY_OTH"));
				privProjCodeExcptDocM.setExceptDocFrom(rs.getString("EXCEPT_DOC_FROM"));
				
				privProjCodeExcptDocM.setCreateBy(rs.getString("CREATE_BY"));
				privProjCodeExcptDocM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				privProjCodeExcptDocM.setUpdateBy(rs.getString("UPDATE_BY"));
				privProjCodeExcptDocM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				prvProjCodeExptDocList.add(privProjCodeExcptDocM);
			}

			return prvProjCodeExptDocList;
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
	public void saveUpdateOrigPrivilegeProjectCodeExceptionDocM(
			PrivilegeProjectCodeExceptionDocDataM privilegeProjectCodeExceptionDocM)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			privilegeProjectCodeExceptionDocM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_EXCEPTION_DOC(privilegeProjectCodeExceptionDocM);
			if (returnRows == 0) {
				privilegeProjectCodeExceptionDocM.setCreateBy(userId);
			    privilegeProjectCodeExceptionDocM.setUpdateBy(userId);
				createOrigPrivilegeProjectCodeExceptionDocM(privilegeProjectCodeExceptionDocM);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_EXCEPTION_DOC(
			PrivilegeProjectCodeExceptionDocDataM privilegeProjectCodeExceptionDocM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_EXCEPTION_DOC ");
			sql.append(" SET EXCEPT_POLICY = ?, EXCEPT_POLICY_OTH = ?, EXCEPT_DOC_FROM = ?, ");
			sql.append(" UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PRVLG_DOC_ID = ? AND EXCEPT_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getExceptPolicy());
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getExceptPolicyOth());
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getExceptDocFrom());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getPrvlgDocId());
			ps.setString(cnt++, privilegeProjectCodeExceptionDocM.getExceptDocId());
			
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
	public void deleteNotInKeyPrivilegeProjectCodeExceptionDoc(
			ArrayList<PrivilegeProjectCodeExceptionDocDataM> privilegeProjectCodeExceptionDocList,
			String prvlgDocId) throws ApplicationException {
		deleteNotInKeyTableXRULES_PRVLG_EXCEPTION_DOC(privilegeProjectCodeExceptionDocList, prvlgDocId);
	}

	private void deleteNotInKeyTableXRULES_PRVLG_EXCEPTION_DOC(
			ArrayList<PrivilegeProjectCodeExceptionDocDataM> privilegeProjectCodeExceptionDocList,
			String prvlgDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_EXCEPTION_DOC ");
            sql.append(" WHERE PRVLG_DOC_ID = ? ");
            
            if (!Util.empty(privilegeProjectCodeExceptionDocList)) {
                sql.append(" AND EXCEPT_DOC_ID NOT IN ( ");

                for (PrivilegeProjectCodeExceptionDocDataM projCodeExcptDocM: privilegeProjectCodeExceptionDocList) {
                    sql.append(" '" + projCodeExcptDocM.getExceptDocId() + "' , ");
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
