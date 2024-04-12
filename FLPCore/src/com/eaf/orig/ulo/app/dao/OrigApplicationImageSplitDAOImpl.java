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
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;

public class OrigApplicationImageSplitDAOImpl extends OrigObjectDAO implements OrigApplicationImageSplitDAO {
	public OrigApplicationImageSplitDAOImpl() {

	}

	public OrigApplicationImageSplitDAOImpl(String userId) {
		this.userId = userId;
	}

	private String userId = "";
	@Override
	public void createOrigApplicationImageSplitDataM(
			ApplicationImageSplitDataM appImageSplitDataM, Connection conn)
			throws ApplicationException {
		// Get Primary Key
		logger.debug("appImageSplitDataM.getImgPageId() :" + appImageSplitDataM.getImgPageId());
		if (Util.empty(appImageSplitDataM.getImgPageId())) {
			String appImgId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APP_IMG_SPLIT_PK,conn);
			appImageSplitDataM.setCreateBy(userId);
			appImageSplitDataM.setImgPageId(appImgId);
		}
		appImageSplitDataM.setUpdateBy(userId);
		createTableORIG_APPLICATION_IMG_SPLIT(appImageSplitDataM,conn);
	}
	@Override
	public void createOrigApplicationImageSplitDataM(ApplicationImageSplitDataM appImageSplitDataM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigApplicationImageSplitDataM(appImageSplitDataM,conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private void createTableORIG_APPLICATION_IMG_SPLIT(ApplicationImageSplitDataM appImageSplitDataM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			// conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_APPLICATION_IMG_SPLIT ");
			sql.append("( APP_IMG_ID,IMG_PAGE_ID,IMG_ID,SEQ ");
			sql.append(" ,DOCUMENT_CATEGORY,DOC_TYPE,DOC_TYPE_SEQ,EXPIRY_FLAG ");
			sql.append(" ,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE ");
			sql.append(" , APPLICANT_TYPE_IM, FIXED_ZONE_FLAG, PERSONAL_ID ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,  ?,?,?,?, ?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, appImageSplitDataM.getAppImgId());
			ps.setString(cnt++, appImageSplitDataM.getImgPageId());
			ps.setString(cnt++, appImageSplitDataM.getImageId());
			ps.setInt(cnt++, appImageSplitDataM.getSeq());

			ps.setString(cnt++, appImageSplitDataM.getDocTypeGroup());
			ps.setString(cnt++, appImageSplitDataM.getDocType());
			ps.setInt(cnt++, appImageSplitDataM.getDocTypeSeq());
			ps.setString(cnt++, appImageSplitDataM.getExpiryFlag());

			ps.setString(cnt++, appImageSplitDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, appImageSplitDataM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());

			ps.setString(cnt++, appImageSplitDataM.getApplicantTypeIM());
			ps.setString(cnt++, appImageSplitDataM.getFixedZoneFlag());
			ps.setString(cnt++, appImageSplitDataM.getPersonalId());

			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void deleteOrigApplicationImageSplitDataM(String appImgId, String imgPageId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteOrigApplicationImageSplitDataM(appImgId, imgPageId, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void deleteOrigApplicationImageSplitDataM(String appImgId,
			String imgPageId, Connection conn) throws ApplicationException {
		deleteTableORIG_APPLICATION_IMG_SPLIT(appImgId, imgPageId, conn);
	}
	private void deleteTableORIG_APPLICATION_IMG_SPLIT(String appImgId, String imgPageId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			// conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_APPLICATION_IMG_SPLIT ");
			sql.append(" WHERE APP_IMG_ID = ?");
			if (imgPageId != null && !imgPageId.isEmpty()) {
				sql.append(" AND IMG_PAGE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appImgId);
			if (imgPageId != null && !imgPageId.isEmpty()) {
				ps.setString(2, imgPageId);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<ApplicationImageSplitDataM> loadOrigApplicationImageM(String appImgId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_APPLICATION_IMG_SPLIT(appImgId,conn);
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
	public ArrayList<ApplicationImageSplitDataM> loadOrigApplicationImageM(
			String appImgId, Connection conn) throws ApplicationException {
		return selectTableORIG_APPLICATION_IMG_SPLIT(appImgId, conn);
	}
	private ArrayList<ApplicationImageSplitDataM> selectTableORIG_APPLICATION_IMG_SPLIT(
			String appImgId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationImageSplitDataM> imageSplitList = new ArrayList<ApplicationImageSplitDataM>();
		
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APP_IMG_ID,IMG_PAGE_ID,IMG_ID,SEQ ");
			sql.append(" ,DOCUMENT_CATEGORY,DOC_TYPE,DOC_TYPE_SEQ,EXPIRY_FLAG ");
			sql.append(" ,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE ");
			sql.append(" ,APPLICANT_TYPE_IM, FIXED_ZONE_FLAG, PERSONAL_ID ");
			sql.append(" FROM ORIG_APPLICATION_IMG_SPLIT  WHERE APP_IMG_ID = ? ORDER BY SEQ,DOC_TYPE_SEQ ASC");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appImgId);

			rs = ps.executeQuery();

			while (rs.next()) {
				ApplicationImageSplitDataM appImageSplitDataM = new ApplicationImageSplitDataM();
				
				appImageSplitDataM.setImgPageId(rs.getString("IMG_PAGE_ID"));
				appImageSplitDataM.setAppImgId(rs.getString("APP_IMG_ID"));
				appImageSplitDataM.setImageId(rs.getString("IMG_ID"));
				appImageSplitDataM.setSeq(rs.getInt("SEQ"));
				
				appImageSplitDataM.setDocTypeGroup(rs.getString("DOCUMENT_CATEGORY"));
				appImageSplitDataM.setDocType(rs.getString("DOC_TYPE"));
				appImageSplitDataM.setDocTypeSeq(rs.getInt("DOC_TYPE_SEQ"));
				appImageSplitDataM.setExpiryFlag(rs.getString("EXPIRY_FLAG"));
				
				appImageSplitDataM.setCreateBy(rs.getString("CREATE_BY"));
				appImageSplitDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				appImageSplitDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				appImageSplitDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				appImageSplitDataM.setApplicantTypeIM(rs.getString("APPLICANT_TYPE_IM"));
				appImageSplitDataM.setFixedZoneFlag(rs.getString("FIXED_ZONE_FLAG"));
				appImageSplitDataM.setPersonalId(rs.getString("PERSONAL_ID"));
				
				imageSplitList.add(appImageSplitDataM);
			}

			return imageSplitList;
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
	public void saveUpdateOrigApplicationImageSplitDataM(
			ApplicationImageSplitDataM appImageSplitDataM, Connection conn)
			throws ApplicationException {
		int returnRows = 0;
		appImageSplitDataM.setUpdateBy(userId);
		returnRows = updateTableORIG_APPLICATION_IMG_SPLIT(appImageSplitDataM,conn);
		if (returnRows == 0) {
			appImageSplitDataM.setCreateBy(userId);
			createOrigApplicationImageSplitDataM(appImageSplitDataM,conn);
		}
	}
	@Override
	public void saveUpdateOrigApplicationImageSplitDataM(ApplicationImageSplitDataM appImageSplitDataM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigApplicationImageSplitDataM(appImageSplitDataM,conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	private int updateTableORIG_APPLICATION_IMG_SPLIT(ApplicationImageSplitDataM appImageSplitDataM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			// conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION_IMG_SPLIT ");
			sql.append(" SET IMG_ID = ?,SEQ = ?,DOCUMENT_CATEGORY = ?,DOC_TYPE = ? ");
			sql.append(" ,DOC_TYPE_SEQ = ?,EXPIRY_FLAG = ?");
			sql.append(" ,UPDATE_BY = ?,UPDATE_DATE = ? ");
			sql.append(" ,APPLICANT_TYPE_IM=?, FIXED_ZONE_FLAG=?");
			sql.append(" ,PERSONAL_ID=? ");

			sql.append(" WHERE IMG_PAGE_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, appImageSplitDataM.getImageId());
			ps.setInt(cnt++, appImageSplitDataM.getSeq());
			ps.setString(cnt++, appImageSplitDataM.getDocTypeGroup());
			ps.setString(cnt++, appImageSplitDataM.getDocType());

			ps.setInt(cnt++, appImageSplitDataM.getDocTypeSeq());
			ps.setString(cnt++, appImageSplitDataM.getExpiryFlag());

			ps.setString(cnt++, appImageSplitDataM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());

			ps.setString(cnt++, appImageSplitDataM.getApplicantTypeIM());
			ps.setString(cnt++, appImageSplitDataM.getFixedZoneFlag());

			ps.setString(cnt++, appImageSplitDataM.getPersonalId());

			// WHERE CLAUSE
			ps.setString(cnt++, appImageSplitDataM.getImgPageId());

			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	@Override
	public void deleteNotInKeyApplicationImageSplit(ArrayList<ApplicationImageSplitDataM> appImageSplitList, String appImgId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyApplicationImageSplit(appImageSplitList,appImgId,conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}		
	}
	@Override
	public void deleteNotInKeyApplicationImageSplit(
			ArrayList<ApplicationImageSplitDataM> appImageSplitList,
			String appImgId, Connection conn) throws ApplicationException {
		deleteNotInKeyTable_ORIG_APPLICATION_IMG_SPLIT(appImageSplitList, appImgId, conn);
	}
	private void deleteNotInKeyTable_ORIG_APPLICATION_IMG_SPLIT(ArrayList<ApplicationImageSplitDataM> appImageSplitList, String appImgId,Connection conn)
			throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			// conn = Get Connection
//			conn = getConnection();

			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_APPLICATION_IMG_SPLIT ");
			sql.append(" WHERE APP_IMG_ID = ? ");

			if ((appImageSplitList != null) && (!appImageSplitList.isEmpty())) {
				sql.append(" AND IMG_PAGE_ID NOT IN ( ");
				for (ApplicationImageSplitDataM imgSplitDataM : appImageSplitList) {
					logger.debug("imgSplitDataM.getImgPageId() = " + imgSplitDataM.getImgPageId());
					sql.append(" '" + imgSplitDataM.getImgPageId() + "' , ");
				}

				if (sql.toString().trim().endsWith(",")) {
					sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
				}

				sql.append(" ) ");
			}

			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appImgId);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			logger.error("ERROR >> ", e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.error("ERROR >> ", e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public String selectDocumentGroupForDoc(String personalId, String docCode) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOCUMENT_CATEGORY ");
			sql.append(" FROM ORIG_APPLICATION_IMG_SPLIT ");
			sql.append(" WHERE PERSONAL_ID = ? AND DOC_TYPE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);
			ps.setString(2, docCode);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("DOCUMENT_CATEGORY");
			}

			return null;
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

}
