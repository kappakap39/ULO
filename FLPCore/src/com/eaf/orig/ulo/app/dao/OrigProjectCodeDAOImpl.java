package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ProjectCodeDataM;

public class OrigProjectCodeDAOImpl extends OrigObjectDAO implements
		OrigProjectCodeDAO {
	public OrigProjectCodeDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigProjectCodeDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigProjectCodeM(ProjectCodeDataM projectCodeM)
			throws ApplicationException {
		projectCodeM.setCreateBy(userId);
		projectCodeM.setUpdateBy(userId);
		createTableORIG_PROJECT_CODE(projectCodeM);
	}

	private void createTableORIG_PROJECT_CODE(ProjectCodeDataM projectCodeM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_PROJECT_CODE ");
			sql.append("( APPLICATION_GROUP_ID, PROJECT_CODE, ");
			sql.append("CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,  ?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, projectCodeM.getApplicationGroupId());
			ps.setString(cnt++, projectCodeM.getProjectCode());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, projectCodeM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, projectCodeM.getUpdateBy());
			
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
	public void deleteOrigProjectCodeM(String applicationGroupId, String projectCode)
			throws ApplicationException {
		deleteTableORIG_PROJECT_CODE(applicationGroupId, projectCode);
	}

	private void deleteTableORIG_PROJECT_CODE(String applicationGroupId, String projectCode) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_PROJECT_CODE ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(projectCode != null && !projectCode.isEmpty()) {
				sql.append(" AND PROJECT_CODE = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(projectCode != null && !projectCode.isEmpty()) {
				ps.setString(2, projectCode);
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
	public ArrayList<ProjectCodeDataM> loadOrigProjectCodeM(
			String applicationGroupId) throws ApplicationException {
		ArrayList<ProjectCodeDataM> result = selectTableORIG_PROJECT_CODE(applicationGroupId);
		return result;
	}

	private ArrayList<ProjectCodeDataM> selectTableORIG_PROJECT_CODE(
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<ProjectCodeDataM> projCodeList = new ArrayList<ProjectCodeDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_GROUP_ID, PROJECT_CODE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_PROJECT_CODE WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ProjectCodeDataM projectCodeM = new ProjectCodeDataM();
				projectCodeM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				projectCodeM.setProjectCode(rs.getString("PROJECT_CODE"));
				
				projectCodeM.setCreateBy(rs.getString("CREATE_BY"));
				projectCodeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				projectCodeM.setUpdateBy(rs.getString("UPDATE_BY"));
				projectCodeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				projCodeList.add(projectCodeM);
			}

			return projCodeList;
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
	public void saveUpdateOrigProjectCodeM(ProjectCodeDataM projectCodeM)
			throws ApplicationException {
		int returnRows = 0;
		projectCodeM.setUpdateBy(userId);
		returnRows = updateTableORIG_PROJECT_CODE(projectCodeM);
		if (returnRows == 0) {
			projectCodeM.setCreateBy(userId);
			createTableORIG_PROJECT_CODE(projectCodeM);
		}
		
	}

	private int updateTableORIG_PROJECT_CODE(ProjectCodeDataM projectCodeM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_PROJECT_CODE ");
			sql.append(" SET UPDATE_DATE = ?, UPDATE_BY = ? ");			
			sql.append(" WHERE PROJECT_CODE = ? AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, projectCodeM.getUpdateBy());
			// WHERE CLAUSE
			ps.setString(cnt++, projectCodeM.getProjectCode());
			ps.setString(cnt++, projectCodeM.getApplicationGroupId());
			
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
	public void deleteNotInKeyProjectCode(
			ArrayList<ProjectCodeDataM> projectCodeList,
			String applicationGroupId) throws ApplicationException {
		deleteNotInKeyTableORIG_PROJECT_CODE(projectCodeList, applicationGroupId);
	}

	private void deleteNotInKeyTableORIG_PROJECT_CODE(
			ArrayList<ProjectCodeDataM> projectCodeList,
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_PROJECT_CODE ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (projectCodeList != null && !projectCodeList.isEmpty()) {
                sql.append(" AND PROJECT_CODE NOT IN ( ");
                for (ProjectCodeDataM projCodeM: projectCodeList) {
                	logger.debug("projCodeM.getProjectCode() = "+projCodeM.getProjectCode());
                    sql.append(" '" +projCodeM.getProjectCode()+ "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
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
