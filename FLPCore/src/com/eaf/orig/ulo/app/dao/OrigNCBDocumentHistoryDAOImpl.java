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
import com.eaf.orig.ulo.model.app.NCBDocumentHistoryDataM;

public class OrigNCBDocumentHistoryDAOImpl extends OrigObjectDAO implements OrigNCBDocumentHistoryDAO {
	public OrigNCBDocumentHistoryDAOImpl(String userId) {
		this.userId = userId;
	}

	public OrigNCBDocumentHistoryDAOImpl() {
		
	}

	private String userId = "";

	@Override
	public void createOrigNCBDocHistoryM(NCBDocumentHistoryDataM ncbDocumentHistM) throws ApplicationException {
		try {
			// Get Primary Key
			logger.debug("ncbDocumentHistM.getNcbDocHistoryId() :" + ncbDocumentHistM.getNcbDocHistoryId());
			if (Util.empty(ncbDocumentHistM.getNcbDocHistoryId())) {
				String ncbDocHistoryId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_NCB_DOCUMENT_HISTORY_PK);
				ncbDocumentHistM.setNcbDocHistoryId(ncbDocHistoryId);
				ncbDocumentHistM.setCreateBy(userId);
			}
			ncbDocumentHistM.setUpdateBy(userId);
			createTableORIG_NCB_DOCUMENT_HISTORY(ncbDocumentHistM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_NCB_DOCUMENT_HISTORY(NCBDocumentHistoryDataM ncbDocumentHistM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_NCB_DOCUMENT_HISTORY ");
			sql.append("( NCB_DOC_HISTORY_ID, PERSONAL_ID, DOCUMENT_CODE, IMG_ID, ");
			sql.append(" CONSENT_REF_NO, CONSENT_GEN_DATE,BUREAU_REQUIRED_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbDocumentHistM.getNcbDocHistoryId());
			ps.setString(cnt++, ncbDocumentHistM.getPersonalId());
			ps.setString(cnt++, ncbDocumentHistM.getDocumentCode());
			ps.setString(cnt++, ncbDocumentHistM.getImgId());

			ps.setString(cnt++, ncbDocumentHistM.getConsentRefNo());
			ps.setDate(cnt++, ncbDocumentHistM.getConsentGenDate());
			ps.setString(cnt++, ncbDocumentHistM.getBureauRequiredFlag());

			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbDocumentHistM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbDocumentHistM.getUpdateBy());

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
	public void deleteOrigNCBDocHistoryM(String personalId, String ncbDocHistoryId) throws ApplicationException {
		deleteTableORIG_NCB_DOCUMENT_HISTORY(personalId, ncbDocHistoryId);
	}

	private void deleteTableORIG_NCB_DOCUMENT_HISTORY(String personalId, String ncbDocHistoryId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_NCB_DOCUMENT_HISTORY ");
			sql.append(" WHERE PERSONAL_ID = ? ");
			if (ncbDocHistoryId != null && !ncbDocHistoryId.isEmpty()) {
				sql.append(" AND NCB_DOC_HISTORY_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if (ncbDocHistoryId != null && !ncbDocHistoryId.isEmpty()) {
				ps.setString(2, ncbDocHistoryId);
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
	public ArrayList<NCBDocumentHistoryDataM> loadOrigNCBDocHistoryM(
			String personalID, Connection conn) throws ApplicationException {
		return selectTableORIG_NCB_DOCUMENT_HISTORY(personalID,conn);
	}

	private ArrayList<NCBDocumentHistoryDataM> selectTableORIG_NCB_DOCUMENT_HISTORY(
			String personalID,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<NCBDocumentHistoryDataM> ncbDocHistList = new ArrayList<NCBDocumentHistoryDataM>();
		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT NCB_DOC_HISTORY_ID, PERSONAL_ID, DOCUMENT_CODE, IMG_ID, CONSENT_REF_NO, ");
			sql.append(" CONSENT_GEN_DATE, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, BUREAU_REQUIRED_FLAG ");
			sql.append(" FROM ORIG_NCB_DOCUMENT_HISTORY WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while (rs.next()) {
				NCBDocumentHistoryDataM ncbDocHistoryM = new NCBDocumentHistoryDataM();
				ncbDocHistoryM.setNcbDocHistoryId(rs.getString("NCB_DOC_HISTORY_ID"));
				ncbDocHistoryM.setPersonalId(rs.getString("PERSONAL_ID"));
				ncbDocHistoryM.setDocumentCode(rs.getString("DOCUMENT_CODE"));
				ncbDocHistoryM.setImgId(rs.getString("IMG_ID"));
				ncbDocHistoryM.setConsentRefNo(rs.getString("CONSENT_REF_NO"));

				ncbDocHistoryM.setConsentGenDate(rs.getDate("CONSENT_GEN_DATE"));

				ncbDocHistoryM.setCreateBy(rs.getString("CREATE_BY"));
				ncbDocHistoryM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ncbDocHistoryM.setUpdateBy(rs.getString("UPDATE_BY"));
				ncbDocHistoryM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				ncbDocHistoryM.setBureauRequiredFlag(rs.getString("BUREAU_REQUIRED_FLAG"));
				ncbDocHistList.add(ncbDocHistoryM);
			}

			return ncbDocHistList;
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
	public void saveUpdateOrigNCBDocHistoryM(NCBDocumentHistoryDataM ncbDocumentHistM) throws ApplicationException {
		int returnRows = 0;
		ncbDocumentHistM.setUpdateBy(userId);
		returnRows = updateTableORIG_NCB_DOCUMENT_HISTORY(ncbDocumentHistM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_NCB_DOCUMENT_HISTORY then call Insert method");
			ncbDocumentHistM.setCreateBy(userId);
			createOrigNCBDocHistoryM(ncbDocumentHistM);
		}
	}
	
	private int updateTableORIG_NCB_DOCUMENT_HISTORY(NCBDocumentHistoryDataM ncbDocumentHistM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_NCB_DOCUMENT_HISTORY ");
			sql.append(" SET DOCUMENT_CODE = ?, IMG_ID = ?, CONSENT_REF_NO = ?, ");
			sql.append(" CONSENT_GEN_DATE = ?, UPDATE_BY = ?, UPDATE_DATE = ?, BUREAU_REQUIRED_FLAG=? ");
			sql.append(" WHERE NCB_DOC_HISTORY_ID = ? AND PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbDocumentHistM.getDocumentCode());
			ps.setString(cnt++, ncbDocumentHistM.getImgId());
			ps.setString(cnt++, ncbDocumentHistM.getConsentRefNo());

			ps.setDate(cnt++, ncbDocumentHistM.getConsentGenDate());
			ps.setString(cnt++, ncbDocumentHistM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbDocumentHistM.getBureauRequiredFlag());
			// WHERE CLAUSE
			ps.setString(cnt++, ncbDocumentHistM.getNcbDocHistoryId());
			ps.setString(cnt++, ncbDocumentHistM.getPersonalId());

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
	public ArrayList<NCBDocumentHistoryDataM> loadOrigNCBDocHistoryM(String personalID) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_NCB_DOCUMENT_HISTORY(personalID,conn);
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
