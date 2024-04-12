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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeRccmdPrjCdeDataM;

public class OrigPrivilegeRecommProjCodeDAOImpl extends OrigObjectDAO implements
		OrigPrivilegeRecommProjCodeDAO {
	public OrigPrivilegeRecommProjCodeDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeRecommProjCodeDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeRecommProjCodeM(
			PrivilegeProjectCodeRccmdPrjCdeDataM prvRecommProjCodeM) throws ApplicationException {
		try {
			logger.debug("prvRecommProjCodeM.getRccmdPrjCdeId() :"+prvRecommProjCodeM.getRccmdPrjCdeId());
		    if(Util.empty(prvRecommProjCodeM.getRccmdPrjCdeId())){
				String rccmdPrjCdeId =GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_RCCMD_PRJ_CDE_PK); 
				prvRecommProjCodeM.setRccmdPrjCdeId(rccmdPrjCdeId);
			}
		    prvRecommProjCodeM.setCreateBy(userId);
		    prvRecommProjCodeM.setUpdateBy(userId);
			createTableXRULES_PRVLG_RCCMD_PRJ_CDE(prvRecommProjCodeM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_RCCMD_PRJ_CDE(
			PrivilegeProjectCodeRccmdPrjCdeDataM prvRecommProjCodeM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_RCCMD_PRJ_CDE ");
			sql.append("( RCCMD_PRJ_CDE_ID, PRVLG_PRJ_CDE_ID, PROJECT_CODE, PROJECT_CODE_DESC, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, prvRecommProjCodeM.getRccmdPrjCdeId());
			ps.setString(cnt++, prvRecommProjCodeM.getPrvlgPrjCdeId());
			ps.setString(cnt++, prvRecommProjCodeM.getProjectCode());
			ps.setString(cnt++, prvRecommProjCodeM.getProjectCodeDesc());
			
			ps.setString(cnt++, prvRecommProjCodeM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, prvRecommProjCodeM.getUpdateBy());
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
	public void deleteOrigPrivilegeRecommProjCodeM(String prvlgPrjCdeId,
			String rccmdPrjCdeId) throws ApplicationException {
		try {
			deleteTableXRULES_PRVLG_RCCMD_PRJ_CDE(prvlgPrjCdeId, rccmdPrjCdeId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_RCCMD_PRJ_CDE(String prvlgPrjCdeId,
			String rccmdPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_RCCMD_PRJ_CDE ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
			if(!Util.empty(rccmdPrjCdeId)) {
				sql.append(" AND RCCMD_PRJ_CDE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prvlgPrjCdeId);
			if(!Util.empty(rccmdPrjCdeId)) {
				ps.setString(2, rccmdPrjCdeId);
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
	public ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> loadOrigPrivilegeRecommProjCodeM(
			String prvlgPrjCdeId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> result = selectTableXRULES_PRVLG_RCCMD_PRJ_CDE(prvlgPrjCdeId);
		return result;
	}

	private ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> selectTableXRULES_PRVLG_RCCMD_PRJ_CDE(
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> rccmProjCodeList = new ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT RCCMD_PRJ_CDE_ID, PRVLG_PRJ_CDE_ID, PROJECT_CODE, PROJECT_CODE_DESC, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_PRVLG_RCCMD_PRJ_CDE WHERE PRVLG_PRJ_CDE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prvlgPrjCdeId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeRccmdPrjCdeDataM rccmProjCodeM = new PrivilegeProjectCodeRccmdPrjCdeDataM();
				rccmProjCodeM.setRccmdPrjCdeId(rs.getString("RCCMD_PRJ_CDE_ID"));
				rccmProjCodeM.setPrvlgPrjCdeId(rs.getString("PRVLG_PRJ_CDE_ID"));
				rccmProjCodeM.setProjectCode(rs.getString("PROJECT_CODE"));
				rccmProjCodeM.setProjectCodeDesc(rs.getString("PROJECT_CODE_DESC"));
				
				rccmProjCodeM.setCreateBy(rs.getString("CREATE_BY"));
				rccmProjCodeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				rccmProjCodeM.setUpdateBy(rs.getString("UPDATE_BY"));
				rccmProjCodeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				rccmProjCodeList.add(rccmProjCodeM);
			}

			return rccmProjCodeList;
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
	public void saveUpdateOrigPrivilegeRecommProjCodeM(
			PrivilegeProjectCodeRccmdPrjCdeDataM prvRecommProjCodeM)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			prvRecommProjCodeM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_RCCMD_PRJ_CDE(prvRecommProjCodeM);
			if (returnRows == 0) {
				prvRecommProjCodeM.setCreateBy(userId);
			    prvRecommProjCodeM.setUpdateBy(userId);
				createOrigPrivilegeRecommProjCodeM(prvRecommProjCodeM);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_RCCMD_PRJ_CDE(
			PrivilegeProjectCodeRccmdPrjCdeDataM prvRecommProjCodeM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_RCCMD_PRJ_CDE ");
			sql.append(" SET PROJECT_CODE = ?, PROJECT_CODE_DESC = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? AND RCCMD_PRJ_CDE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, prvRecommProjCodeM.getProjectCode());
			ps.setString(cnt++, prvRecommProjCodeM.getProjectCodeDesc());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, prvRecommProjCodeM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, prvRecommProjCodeM.getPrvlgPrjCdeId());
			ps.setString(cnt++, prvRecommProjCodeM.getRccmdPrjCdeId());
			
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
	public void deleteNotInKeyPrivilegeRecommProjCode(
			ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> prvRecommProjCodeList,
			String prvlgPrjCdeId) throws ApplicationException {
		deleteNotInKeyTableXRULES_PRVLG_RCCMD_PRJ_CDE(prvRecommProjCodeList, prvlgPrjCdeId);
	}

	private void deleteNotInKeyTableXRULES_PRVLG_RCCMD_PRJ_CDE(
			ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> prvRecommProjCodeList,
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_RCCMD_PRJ_CDE ");
            sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
            
            if (!Util.empty(prvRecommProjCodeList)) {
                sql.append(" AND RCCMD_PRJ_CDE_ID NOT IN ( ");

                for (PrivilegeProjectCodeRccmdPrjCdeDataM reccProjCodeM: prvRecommProjCodeList) {
                    sql.append(" '" + reccProjCodeM.getRccmdPrjCdeId() + "' , ");
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
