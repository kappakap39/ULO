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
import com.eaf.orig.ulo.model.app.NCBDocumentDataM;

public class OrigNCBDocumentDAOImpl extends OrigObjectDAO implements OrigNCBDocumentDAO {
	public OrigNCBDocumentDAOImpl(String userId) {
		this.userId = userId;
	}

	public OrigNCBDocumentDAOImpl() {
		
	}

	private String userId = "";

	@Override
	public void createOrigNCBDocumentM(NCBDocumentDataM ncbDocumentM) throws ApplicationException {
		try {
			// Get Primary Key
			logger.debug("ncbDocumentM.getNcbDocumentId() :" + ncbDocumentM.getNcbDocumentId());
			if (Util.empty(ncbDocumentM.getNcbDocumentId())) {
				String ncbDocumentId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_NCB_DOCUMENT_PK);
				ncbDocumentM.setNcbDocumentId(ncbDocumentId);
				ncbDocumentM.setCreateBy(userId);
			}
			ncbDocumentM.setUpdateBy(userId);
			createTableORIG_CONTACT_LOG(ncbDocumentM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_CONTACT_LOG(NCBDocumentDataM ncbDocumentM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_NCB_DOCUMENT ");
			sql.append("( NCB_DOCUMENT_ID, PERSONAL_ID, DOCUMENT_CODE, IMG_ID, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbDocumentM.getNcbDocumentId());
			ps.setString(cnt++, ncbDocumentM.getPersonalId());
			ps.setString(cnt++, ncbDocumentM.getDocumentCode());
			ps.setString(cnt++, ncbDocumentM.getImgId());

			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbDocumentM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbDocumentM.getUpdateBy());

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
	public void deleteOrigNCBDocumentM(String personalId, String ncbDocumentId) throws ApplicationException {
		deleteTableORIG_NCB_DOCUMENT(personalId, ncbDocumentId);
	}

	private void deleteTableORIG_NCB_DOCUMENT(String personalId, String ncbDocumentId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_NCB_DOCUMENT ");
			sql.append(" WHERE PERSONAL_ID = ?");
			if (!Util.empty(ncbDocumentId)) {
				sql.append(" AND NCB_DOCUMENT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if (!Util.empty(ncbDocumentId)) {
				ps.setString(2, ncbDocumentId);
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
	public ArrayList<NCBDocumentDataM> loadOrigNCBDocumentM(String personalID) throws ApplicationException {
		ArrayList<NCBDocumentDataM> result = selectTableORIG_NCB_DOCUMENT(personalID);
		return result;
	}

	private ArrayList<NCBDocumentDataM> selectTableORIG_NCB_DOCUMENT(String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<NCBDocumentDataM> ncbDocList = new ArrayList<NCBDocumentDataM>();
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT NCB_DOCUMENT_ID, PERSONAL_ID, DOCUMENT_CODE, IMG_ID, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_NCB_DOCUMENT WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while (rs.next()) {
				NCBDocumentDataM ncbDocumentM = new NCBDocumentDataM();
				ncbDocumentM.setNcbDocumentId(rs.getString("NCB_DOCUMENT_ID"));
				ncbDocumentM.setPersonalId(rs.getString("PERSONAL_ID"));
				ncbDocumentM.setDocumentCode(rs.getString("DOCUMENT_CODE"));
				ncbDocumentM.setImgId(rs.getString("IMG_ID"));

				ncbDocumentM.setCreateBy(rs.getString("CREATE_BY"));
				ncbDocumentM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ncbDocumentM.setUpdateBy(rs.getString("UPDATE_BY"));
				ncbDocumentM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				ncbDocList.add(ncbDocumentM);
			}

			return ncbDocList;
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
	public void saveUpdateOrigNCBDocumentM(NCBDocumentDataM ncbDocumentM) throws ApplicationException {
		int returnRows = 0;
		ncbDocumentM.setUpdateBy(userId);
		returnRows = updateTableORIG_NCB_DOCUMENT(ncbDocumentM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_NCB_DOCUMENT then call Insert method");
			ncbDocumentM.setCreateBy(userId);
			createOrigNCBDocumentM(ncbDocumentM);
		}
	}

	private int updateTableORIG_NCB_DOCUMENT(NCBDocumentDataM ncbDocumentM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_NCB_DOCUMENT ");
			sql.append(" SET DOCUMENT_CODE = ?, IMG_ID = ?, UPDATE_BY = ?,UPDATE_DATE = ? ");
			sql.append(" WHERE NCB_DOCUMENT_ID = ? AND PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbDocumentM.getDocumentCode());
			ps.setString(cnt++, ncbDocumentM.getImgId());
			ps.setString(cnt++, ncbDocumentM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, ncbDocumentM.getNcbDocumentId());
			ps.setString(cnt++, ncbDocumentM.getPersonalId());

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
	public void deleteNotInKeyNCBDocument(ArrayList<NCBDocumentDataM> ncbDocList, String personalID) throws ApplicationException {
		deleteNotInKeyORIG_NCB_DOCUMENT(ncbDocList, personalID);
	}

	private void deleteNotInKeyORIG_NCB_DOCUMENT(ArrayList<NCBDocumentDataM> ncbDocList, String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();

			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_NCB_DOCUMENT ");
			sql.append(" WHERE PERSONAL_ID = ? ");

			if (ncbDocList != null && !ncbDocList.isEmpty()) {
				sql.append(" AND NCB_DOCUMENT_ID NOT IN ( ");
				for (NCBDocumentDataM ncbDocM : ncbDocList) {
					logger.debug("ncbDocM.getNcbDocumentId() = " + ncbDocM.getNcbDocumentId());
					sql.append(" '" + ncbDocM.getNcbDocumentId() + "', ");
				}

				if (sql.toString().trim().endsWith(",")) {
					sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
				}

				sql.append(" ) ");
			}

			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			logger.error("ERROR >> ", e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ", e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
