package com.eaf.orig.ulo.app.dao;

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
import com.eaf.orig.ulo.model.app.ApplicationPointDataM;

public class OrigApplicationPointDAOImpl extends OrigObjectDAO implements
		OrigApplicationPointDAO {
	public OrigApplicationPointDAOImpl(){
		
	}
	public OrigApplicationPointDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigApplicationPointM(ApplicationPointDataM applicationPointDataM) throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("applicationPointDataM.getApplicationPointId() :"+applicationPointDataM.getApplicationPointId());
		    if(Util.empty(applicationPointDataM.getApplicationPointId())){
				String appImgId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_POINT_PK);
				applicationPointDataM.setApplicationPointId(appImgId);
				applicationPointDataM.setCreateBy(userId);
		    }
		    applicationPointDataM.setUpdateBy(userId);
			createTableORIG_APPLICATION_POINT(applicationPointDataM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_APPLICATION_POINT(ApplicationPointDataM applicationPointDataM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_APPLICATION_POINT ");
			sql.append("( APPLICATION_POINT_ID, POINT, POINT_DATE, ACTION,APPLICATION_GROUP_ID, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?, ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, applicationPointDataM.getApplicationPointId());
			ps.setBigDecimal(cnt++, applicationPointDataM.getPoint());
			ps.setDate(cnt++, applicationPointDataM.getPointDate());
			ps.setString(cnt++, applicationPointDataM.getAction());
			
			ps.setString(cnt++, applicationPointDataM.getApplicationGroupId());			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationPointDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationPointDataM.getUpdateBy());
			
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
	public void deleteOrigApplicationPointM(String applicationGroupId, 
			String applicationPointId) throws ApplicationException {
		deleteTableORIG_APPLICATION_POINT(applicationGroupId, applicationPointId);
	}

	private void deleteTableORIG_APPLICATION_POINT(String applicationGroupId, 
			String applicationPointId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_APPLICATION_POINT ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(applicationPointId != null && !applicationPointId.isEmpty()) {
				sql.append(" AND APPLICATION_POINT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(applicationPointId != null && !applicationPointId.isEmpty()) {
				ps.setString(2, applicationPointId);
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
	public ArrayList<ApplicationPointDataM> loadOrigApplicationPointM(
			String applicationGroupId) throws ApplicationException {
		ArrayList<ApplicationPointDataM> result = selectTableORIG_APPLICATION_POINT(applicationGroupId);
		return result;
	}

	private ArrayList<ApplicationPointDataM> selectTableORIG_APPLICATION_POINT(
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<ApplicationPointDataM> appPointList = new ArrayList<ApplicationPointDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_POINT_ID, POINT, POINT_DATE, ACTION, ");
			sql.append(" APPLICATION_GROUP_ID, CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM ORIG_APPLICATION_POINT  WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ApplicationPointDataM applicationPointDataM = new ApplicationPointDataM();
				applicationPointDataM.setApplicationPointId(rs.getString("APPLICATION_POINT_ID"));
				applicationPointDataM.setPoint(rs.getBigDecimal("POINT"));
				applicationPointDataM.setPointDate(rs.getDate("POINT_DATE"));
				applicationPointDataM.setAction(rs.getString("ACTION"));
				applicationPointDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				
				applicationPointDataM.setCreateBy(rs.getString("CREATE_BY"));
				applicationPointDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				applicationPointDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				applicationPointDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				appPointList.add(applicationPointDataM);
			}

			return appPointList;
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
	public void saveUpdateOrigApplicationPointM(
			ApplicationPointDataM applicationPointDataM) throws ApplicationException {
		int returnRows = 0;
		applicationPointDataM.setUpdateBy(userId);
		returnRows = updateTableORIG_APPLICATION_POINT(applicationPointDataM);
		if (returnRows == 0) {
			applicationPointDataM.setCreateBy(userId);
			createOrigApplicationPointM(applicationPointDataM);
		}
	}

	private int updateTableORIG_APPLICATION_POINT(
			ApplicationPointDataM applicationPointDataM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION_POINT ");
			sql.append(" SET POINT = ?, POINT_DATE = ?, ACTION = ?, APPLICATION_GROUP_ID = ? ");
			sql.append(" ,UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE APPLICATION_POINT_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setBigDecimal(cnt++, applicationPointDataM.getPoint());
			ps.setDate(cnt++, applicationPointDataM.getPointDate());
			ps.setString(cnt++, applicationPointDataM.getAction());	
			ps.setString(cnt++, applicationPointDataM.getApplicationGroupId());			
			
			ps.setString(cnt++, applicationPointDataM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, applicationPointDataM.getApplicationPointId());
			
			
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
	public void deleteNotInKeyApplicationPoint(ArrayList<ApplicationPointDataM> appPointList,
			String applicationGroupId) throws ApplicationException {
		deleteNotInKeyTableORIG_APPLICATION_POINT(appPointList, applicationGroupId);
	}

	private void deleteNotInKeyTableORIG_APPLICATION_POINT(ArrayList<ApplicationPointDataM> appPointList,
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_APPLICATION_POINT ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (appPointList != null && !appPointList.isEmpty()) {
                sql.append(" AND APPLICATION_POINT_ID NOT IN ( ");
                for (ApplicationPointDataM apPointDataM: appPointList) {
                	logger.debug("apPointDataM.getApplicationPointId() = "+apPointDataM.getApplicationPointId());
                    sql.append(" '" + apPointDataM.getApplicationPointId() + "', ");
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
