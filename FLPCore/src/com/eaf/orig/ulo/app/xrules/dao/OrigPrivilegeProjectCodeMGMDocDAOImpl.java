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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeMGMDocDataM;

public class OrigPrivilegeProjectCodeMGMDocDAOImpl extends OrigObjectDAO
		implements OrigPrivilegeProjectCodeMGMDocDAO {
	public OrigPrivilegeProjectCodeMGMDocDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeProjectCodeMGMDocDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeProjectCodeMGMDocM(
			PrivilegeProjectCodeMGMDocDataM privilegeProjectCodeMGMDocM)
			throws ApplicationException {
		try {
			logger.debug("privilegeProjectCodeMGMDocM.getPrivlgMgmDocId() :"+privilegeProjectCodeMGMDocM.getPrivlgMgmDocId());
		    if(Util.empty(privilegeProjectCodeMGMDocM.getPrivlgMgmDocId())){
				String privlgMgmDocId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_MGM_DOC_PK); 
				privilegeProjectCodeMGMDocM.setPrivlgMgmDocId(privlgMgmDocId);
			}
		    privilegeProjectCodeMGMDocM.setCreateBy(userId);
		    privilegeProjectCodeMGMDocM.setUpdateBy(userId);
			createTableXRULES_PRVLG_MGM_DOC(privilegeProjectCodeMGMDocM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_MGM_DOC(
			PrivilegeProjectCodeMGMDocDataM privilegeProjectCodeMGMDocM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_MGM_DOC ");
			sql.append("( PRIVLG_MGM_DOC_ID, PRVLG_DOC_ID, REFER_CARD_NO, REFER_CARD_HOLDER_NAME, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getPrivlgMgmDocId());
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getPrvlgDocId());
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getReferCardNo());
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getReferCardHolderName());
			
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getUpdateBy());
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
	public void deleteOrigPrivilegeProjectCodeMGMDocM(String prvlgDocId,
			String privlgMgmDocId) throws ApplicationException {
		try {
			deleteTableXRULES_PRVLG_MGM_DOC(prvlgDocId, privlgMgmDocId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_MGM_DOC(String prvlgDocId,
			String privlgMgmDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_MGM_DOC ");
			sql.append(" WHERE PRVLG_DOC_ID = ? ");
			if(!Util.empty(privlgMgmDocId)) {
				sql.append(" AND PRIVLG_MGM_DOC_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prvlgDocId);
			if(!Util.empty(privlgMgmDocId)) {
				ps.setString(2, privlgMgmDocId);
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
	public ArrayList<PrivilegeProjectCodeMGMDocDataM> loadOrigPrivilegeProjectCodeMGMDocM(
			String prvlgDocId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeMGMDocDataM> result = selectTableXRULES_PRVLG_MGM_DOC(prvlgDocId);
		return result;
	}

	private ArrayList<PrivilegeProjectCodeMGMDocDataM> selectTableXRULES_PRVLG_MGM_DOC(
			String prvlgDocId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeMGMDocDataM> prvProjCodeMGMDocList = new ArrayList<PrivilegeProjectCodeMGMDocDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PRIVLG_MGM_DOC_ID, PRVLG_DOC_ID, REFER_CARD_NO, REFER_CARD_HOLDER_NAME, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_PRVLG_MGM_DOC WHERE PRVLG_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prvlgDocId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeMGMDocDataM privProjCodeMGMDocM = new PrivilegeProjectCodeMGMDocDataM();
				privProjCodeMGMDocM.setPrivlgMgmDocId(rs.getString("PRIVLG_MGM_DOC_ID"));
				privProjCodeMGMDocM.setPrvlgDocId(rs.getString("PRVLG_DOC_ID"));
				privProjCodeMGMDocM.setReferCardNo(rs.getString("REFER_CARD_NO"));
				privProjCodeMGMDocM.setReferCardHolderName(rs.getString("REFER_CARD_HOLDER_NAME"));
				
				privProjCodeMGMDocM.setCreateBy(rs.getString("CREATE_BY"));
				privProjCodeMGMDocM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				privProjCodeMGMDocM.setUpdateBy(rs.getString("UPDATE_BY"));
				privProjCodeMGMDocM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				prvProjCodeMGMDocList.add(privProjCodeMGMDocM);
			}

			return prvProjCodeMGMDocList;
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
	public void saveUpdateOrigPrivilegeProjectCodeMGMDocM(
			PrivilegeProjectCodeMGMDocDataM privilegeProjectCodeMGMDocM)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			privilegeProjectCodeMGMDocM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_MGM_DOC(privilegeProjectCodeMGMDocM);
			if (returnRows == 0) {
				privilegeProjectCodeMGMDocM.setCreateBy(userId);
			    privilegeProjectCodeMGMDocM.setUpdateBy(userId);
				createOrigPrivilegeProjectCodeMGMDocM(privilegeProjectCodeMGMDocM);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_MGM_DOC(
			PrivilegeProjectCodeMGMDocDataM privilegeProjectCodeMGMDocM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_MGM_DOC ");
			sql.append(" SET REFER_CARD_NO = ?, REFER_CARD_HOLDER_NAME = ?, ");
			sql.append(" UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PRVLG_DOC_ID = ? AND PRIVLG_MGM_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getReferCardNo());
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getReferCardHolderName());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getPrvlgDocId());
			ps.setString(cnt++, privilegeProjectCodeMGMDocM.getPrivlgMgmDocId());
			
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
	public void deleteNotInKeyPrivilegeProjectCodeMGMDoc(
			ArrayList<PrivilegeProjectCodeMGMDocDataM> privilegeProjectCodeMGMDocList,
			String prvlgDocId) throws ApplicationException {
		deleteNotInKeyTableXRULES_PRVLG_MGM_DOC(privilegeProjectCodeMGMDocList, prvlgDocId);
	}

	private void deleteNotInKeyTableXRULES_PRVLG_MGM_DOC(
			ArrayList<PrivilegeProjectCodeMGMDocDataM> privilegeProjectCodeMGMDocList,
			String prvlgDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_MGM_DOC ");
            sql.append(" WHERE PRVLG_DOC_ID = ? ");
            
            if (!Util.empty(privilegeProjectCodeMGMDocList)) {
                sql.append(" AND PRIVLG_MGM_DOC_ID NOT IN ( ");

                for (PrivilegeProjectCodeMGMDocDataM projCodeMGMDocM: privilegeProjectCodeMGMDocList) {
                    sql.append(" '" + projCodeMGMDocM.getPrivlgMgmDocId() + "' , ");
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
