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
import com.eaf.orig.ulo.model.app.DocumentCommentDataM;

public class OrigDocumentCommentDAOImpl extends OrigObjectDAO implements OrigDocumentCommentDAO {
	public OrigDocumentCommentDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigDocumentCommentDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigDocumentCommentM(DocumentCommentDataM documentComment)
			throws ApplicationException {
		logger.debug("documentComment.getDocCommentId() :" + documentComment.getDocCommentId());
		if (Util.empty(documentComment.getDocCommentId())) {
			String notepadId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_NOTE_PAD_DATA_PK);
			documentComment.setDocCommentId(notepadId);
			documentComment.setCreateBy(userId);
		}
		documentComment.setUpdateBy(userId);
		createTableORIG_DOCUMENT_COMMENT_DATA(documentComment);
	}

	private void createTableORIG_DOCUMENT_COMMENT_DATA(DocumentCommentDataM documentComment)
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_DOCUMENT_COMMENT ");
			sql.append("( DOC_COMMENT_ID, APPLICATION_GROUP_ID, COMMENT_DESC, ");
			sql.append("CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY,STATUS ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, documentComment.getDocCommentId());
			ps.setString(cnt++, documentComment.getApplicationGroupId());
			ps.setString(cnt++, documentComment.getCommentDesc());

			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, documentComment.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, documentComment.getUpdateBy());
			ps.setString(cnt++, documentComment.getStatus());

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
	public void deleteOrigDocumentCommentM(String applicationGroupId, String id)
			throws ApplicationException {
		deleteTableORIG_DOCUMENT_COMMENT_DATA(applicationGroupId, id);
	}

	private void deleteTableORIG_DOCUMENT_COMMENT_DATA(String applicationGroupId, String id)
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_DOCUMENT_COMMENT ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if (id != null && !id.isEmpty()) {
				sql.append(" AND DOC_COMMENT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if (id != null && !id.isEmpty()) {
				ps.setString(2, id);
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
	public ArrayList<DocumentCommentDataM> loadOrigDocumentCommentM(String applicationGroupId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_DOCUMENT_COMMENT_DATA(applicationGroupId,conn);
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
	public ArrayList<DocumentCommentDataM> loadOrigDocumentCommentM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		return selectTableORIG_DOCUMENT_COMMENT_DATA(applicationGroupId, conn);
	}
	private ArrayList<DocumentCommentDataM> selectTableORIG_DOCUMENT_COMMENT_DATA(
			String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DocumentCommentDataM> documentComments = new ArrayList<DocumentCommentDataM>();
		try {
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DOC_COMMENT_ID, APPLICATION_GROUP_ID, COMMENT_DESC, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY,STATUS ");
			sql.append(" FROM ORIG_DOCUMENT_COMMENT WHERE APPLICATION_GROUP_ID = ? ");
			sql.append(" ORDER BY DOC_COMMENT_ID ASC ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				DocumentCommentDataM documentCommentM = new DocumentCommentDataM();
				documentCommentM.setDocCommentId(rs.getString("DOC_COMMENT_ID"));
				documentCommentM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				documentCommentM.setCommentDesc(rs.getString("COMMENT_DESC"));

				documentCommentM.setCreateBy(rs.getString("CREATE_BY"));
				documentCommentM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				documentCommentM.setUpdateBy(rs.getString("UPDATE_BY"));
				documentCommentM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				documentCommentM.setStatus(rs.getString("STATUS"));

				documentComments.add(documentCommentM);
			}

			return documentComments;
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
	public void saveUpdateOrigDocumentCommentM(DocumentCommentDataM documentCommment)
			throws ApplicationException {
		int returnRows = 0;
		documentCommment.setUpdateBy(userId);
		returnRows = updateTableORIG_DOCUMENT_COMMENT_DATA(documentCommment);
		if (returnRows == 0) {
			documentCommment.setCreateBy(userId);
			createOrigDocumentCommentM(documentCommment);
		}
	}

	private int updateTableORIG_DOCUMENT_COMMENT_DATA(DocumentCommentDataM documentComment) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_DOCUMENT_COMMENT ");
			sql.append(" SET COMMENT_DESC = ?, UPDATE_DATE = ?, UPDATE_BY = ?, STATUS=? ");
			sql.append(" WHERE DOC_COMMENT_ID = ? AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, documentComment.getCommentDesc());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, documentComment.getUpdateBy());
			ps.setString(cnt++, documentComment.getStatus());
			// WHERE CLAUSE
			ps.setString(cnt++, documentComment.getDocCommentId());
			ps.setString(cnt++, documentComment.getApplicationGroupId());

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
	public void deleteNotInKeyDocumentComment(ArrayList<DocumentCommentDataM> documentComment,
			String applicationGroupId) throws ApplicationException {
		deleteNotInKeyTableORIG_DOCUMENT_COMMENT_DATA(documentComment, applicationGroupId);
	}

	private void deleteNotInKeyTableORIG_DOCUMENT_COMMENT_DATA(
			ArrayList<DocumentCommentDataM> documentComments, String applicationGroupId)
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();

			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE FROM ORIG_DOCUMENT_COMMENT ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");

			if (documentComments != null && !documentComments.isEmpty()) {
				sql.append(" AND DOC_COMMENT_ID NOT IN ( ");
				for (DocumentCommentDataM documentComment : documentComments) {
					logger.debug("notepadM.getDocCommentId() = " + documentComment.getDocCommentId());
					sql.append(" '" + documentComment.getDocCommentId() + "', ");
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
