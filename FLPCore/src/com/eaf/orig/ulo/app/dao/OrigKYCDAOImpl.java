package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.KYCDataM;

public class OrigKYCDAOImpl extends OrigObjectDAO implements OrigKYCDAO {
	public OrigKYCDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigKYCDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigKYCM(KYCDataM kycM) throws ApplicationException {
		kycM.setCreateBy(userId);
		kycM.setUpdateBy(userId);
		createTableORIG_KYC(kycM);
	}

	private void createTableORIG_KYC(KYCDataM kycM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_KYC ");
			sql.append("( RELATION_FLAG, REL_NAME, REL_SURNAME, REL_POSITION, ");
			sql.append(" REL_DETAIL, WORK_START_DATE, WORK_END_DATE, SANCTION_LIST, ");
			sql.append(" CUSTOMER_RISK_GRADE, REL_TITLE_NAME, PERSONAL_ID, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, REL_TITLE_DESC ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,  ?,?,?, ?,?,?,? , ? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, kycM.getRelationFlag());
			ps.setString(cnt++, kycM.getRelName());
			ps.setString(cnt++, kycM.getRelSurname());
			ps.setString(cnt++, kycM.getRelPosition());
			
			ps.setString(cnt++, kycM.getRelDetail());
			ps.setDate(cnt++, kycM.getWorkStartDate());
			ps.setDate(cnt++, kycM.getWorkEndDate());
			ps.setString(cnt++, kycM.getSanctionList());
			
			ps.setString(cnt++, kycM.getCustomerRiskGrade());
			ps.setString(cnt++, kycM.getRelTitleName());
			ps.setString(cnt++, kycM.getPersonalId());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, kycM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, kycM.getUpdateBy());
			ps.setString(cnt++, kycM.getRelTitleDesc());
			
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
	public void deleteOrigKYCM(String personalID) throws ApplicationException {
		deleteTableORIG_KYC(personalID);
	}

	private void deleteTableORIG_KYC(String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_KYC ");
			sql.append(" WHERE PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);
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
	public KYCDataM loadOrigKYCM(String personalID) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_KYC(personalID,conn);
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
	public KYCDataM loadOrigKYCM(String personalID, Connection conn)
			throws ApplicationException {
		return selectTableORIG_KYC(personalID, conn);
	}
	private KYCDataM selectTableORIG_KYC(String personalID,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		KYCDataM kycM = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT RELATION_FLAG, REL_NAME, REL_SURNAME, REL_POSITION, ");
			sql.append(" REL_DETAIL, WORK_START_DATE, WORK_END_DATE, SANCTION_LIST, ");
			sql.append(" CUSTOMER_RISK_GRADE, REL_TITLE_NAME, PERSONAL_ID, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY, REL_TITLE_DESC ");
			sql.append(" FROM ORIG_KYC  WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			if (rs.next()) {
				kycM = new KYCDataM();
				kycM.setRelationFlag(rs.getString("RELATION_FLAG"));
				kycM.setRelName(rs.getString("REL_NAME"));
				kycM.setRelSurname(rs.getString("REL_SURNAME"));
				kycM.setRelPosition(rs.getString("REL_POSITION"));
				
				kycM.setRelDetail(rs.getString("REL_DETAIL"));
				kycM.setWorkStartDate(rs.getDate("WORK_START_DATE"));
				kycM.setWorkEndDate(rs.getDate("WORK_END_DATE"));
				kycM.setSanctionList(rs.getString("SANCTION_LIST"));
				
				kycM.setCustomerRiskGrade(rs.getString("CUSTOMER_RISK_GRADE"));
				kycM.setRelTitleName(rs.getString("REL_TITLE_NAME"));
				kycM.setPersonalId(rs.getString("PERSONAL_ID"));
				
				kycM.setCreateBy(rs.getString("CREATE_BY"));
				kycM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				kycM.setUpdateBy(rs.getString("UPDATE_BY"));
				kycM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				kycM.setRelTitleDesc(rs.getString("REL_TITLE_DESC"));
			}

			return kycM;
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
	public void saveUpdateOrigKYCM(KYCDataM kycM) throws ApplicationException {
		int returnRows = 0;
		kycM.setUpdateBy(userId);
		returnRows = updateTableORIG_KYC(kycM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_KYC then call Insert method");
			kycM.setCreateBy(userId);
			createTableORIG_KYC(kycM);
		}
	}

	private int updateTableORIG_KYC(KYCDataM kycM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_KYC ");
			sql.append(" SET RELATION_FLAG = ?, REL_NAME = ?, REL_SURNAME = ?, ");
			sql.append(" REL_POSITION = ?, REL_DETAIL = ?, WORK_START_DATE = ?, ");
			sql.append(" WORK_END_DATE = ?, SANCTION_LIST = ?, CUSTOMER_RISK_GRADE = ?, ");
			sql.append(" REL_TITLE_NAME = ?, UPDATE_BY = ?,UPDATE_DATE = ?, ");
			sql.append(" REL_TITLE_DESC = ? ");
			sql.append(" WHERE PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, kycM.getRelationFlag());
			ps.setString(cnt++, kycM.getRelName());
			ps.setString(cnt++, kycM.getRelSurname());
			
			ps.setString(cnt++, kycM.getRelPosition());			
			ps.setString(cnt++, kycM.getRelDetail());
			ps.setDate(cnt++, kycM.getWorkStartDate());
			
			ps.setDate(cnt++, kycM.getWorkEndDate());
			ps.setString(cnt++, kycM.getSanctionList());			
			ps.setString(cnt++, kycM.getCustomerRiskGrade());
			
			ps.setString(cnt++, kycM.getRelTitleName());
			ps.setString(cnt++, kycM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, kycM.getRelTitleDesc());
			
			// WHERE CLAUSE
			ps.setString(cnt++, kycM.getPersonalId());
			
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

}
