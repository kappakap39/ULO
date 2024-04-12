package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public class OrigAdditionalServiceMapDAOImpl extends OrigObjectDAO implements
		OrigAdditionalServiceMapDAO {
	public OrigAdditionalServiceMapDAOImpl(){
		
	}
	public OrigAdditionalServiceMapDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigAdditionalServiceMapM(String loanId, String serviceId)
			throws ApplicationException {
		createTableORIG_ADDITIONAL_SERVICE_MAP(loanId, serviceId);
	}

	private void createTableORIG_ADDITIONAL_SERVICE_MAP(String loanId,
			String serviceId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_ADDITIONAL_SERVICE_MAP ");
			sql.append("( SERVICE_ID, LOAN_ID, CREATE_DATE, UPDATE_DATE,CREATE_BY,UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, serviceId);
			ps.setString(cnt++, loanId);
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, userId);
			ps.setString(cnt++, userId);
			
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
	public boolean checkMapExists(String loanId, String serviceId)
			throws ApplicationException {
		return checkTableORIG_ADDITIONAL_SERVICE_MAP(loanId, serviceId);
	}

	private boolean checkTableORIG_ADDITIONAL_SERVICE_MAP(String loanId,
			String serviceId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT LOAN_ID, SERVICE_ID ");
			sql.append(" FROM ORIG_ADDITIONAL_SERVICE_MAP ");
			sql.append(" WHERE LOAN_ID = ? AND SERVICE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, loanId);
			ps.setString(2, serviceId);

			rs = ps.executeQuery();

			if(rs.next()) {
				return true;
			}

			return false;
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
	public void deleteOrigAdditionalServiceMapM(String loanId, String serviceId)
			throws ApplicationException {
		deleteTableORIG_ADDITIONAL_SERVICE_MAP(loanId, serviceId);
	}

	private void deleteTableORIG_ADDITIONAL_SERVICE_MAP(String loanId,
			String serviceId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_ADDITIONAL_SERVICE_MAP ");
			sql.append(" WHERE LOAN_ID = ? ");
			if(!Util.empty(serviceId)) {
				sql.append(" AND SERVICE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, loanId);
			if(!Util.empty(serviceId)) {
				ps.setString(2, serviceId);
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
	public ArrayList<String> loadOrigAdditionalServicesM(String loanId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_ADDITIONAL_SERVICE_MAP(loanId,conn);
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public ArrayList<String> loadOrigAdditionalServicesM(String loanId,
			Connection conn) throws ApplicationException {
		return selectTableORIG_ADDITIONAL_SERVICE_MAP(loanId, conn);
	}
	private ArrayList<String> selectTableORIG_ADDITIONAL_SERVICE_MAP(
			String loanId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> serviceList = new ArrayList<String>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT SERVICE_ID ");
			sql.append(" FROM ORIG_ADDITIONAL_SERVICE_MAP WHERE LOAN_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, loanId);

			rs = ps.executeQuery();

			while(rs.next()) {
				serviceList.add(rs.getString("SERVICE_ID"));
			}

			return serviceList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteNotInKeyAdditionalService(ArrayList<String> serviceList,
			String loanId) throws ApplicationException {
		deleteNotInKeyTableORIG_ADDITIONAL_SERVICE_MAP(serviceList, loanId);
	}

	private void deleteNotInKeyTableORIG_ADDITIONAL_SERVICE_MAP(
			ArrayList<String> serviceList, String loanId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_ADDITIONAL_SERVICE_MAP ");
            sql.append(" WHERE LOAN_ID = ? ");
            
            if (serviceList != null && !serviceList.isEmpty()) {
                sql.append(" AND SERVICE_ID NOT IN ( ");
                for (String serviceId: serviceList) {
                	logger.debug("serviceId = "+serviceId);
                    sql.append(" '" + serviceId + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, loanId);
            
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

	@Override
	public void saveUpdateOrigAdditionalServiceM(String loanId, String serviceId)
			throws ApplicationException {
		boolean isExists = checkTableORIG_ADDITIONAL_SERVICE_MAP(loanId, serviceId);
		if(!isExists) {
			createTableORIG_ADDITIONAL_SERVICE_MAP(loanId, serviceId);
		}
		
	}
	@Override
	public void saveUpdateOrigAdditionalServiceM(String loanId,
			String serviceId, Connection conn) throws ApplicationException {
		boolean isExists = checkTableORIG_ADDITIONAL_SERVICE_MAP(loanId, serviceId);
		if(!isExists) {
			createTableORIG_ADDITIONAL_SERVICE_MAP(loanId, serviceId);
		}
	}
	@Override
	public void deleteNotInKeyAdditionalService(ArrayList<String> serviceList,
			String loanId, Connection conn) throws ApplicationException {
		deleteNotInKeyAdditionalService(serviceList, loanId);
	}
}
