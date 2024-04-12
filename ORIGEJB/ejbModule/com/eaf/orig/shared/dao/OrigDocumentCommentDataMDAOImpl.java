/*
 * Copyright (c) 2007 Avalant Co.,Ltd. 20 North Sathorn Road, 15-16th Floor
 * Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand All rights reserved.
 * 
 * This software is the confidential and prorietary infomation of Avalant
 * Co.,Ltd. ("Confidential Infomation"). You shall not disclose such
 * Confidential Infomation and shall use it only in accordance with the terms of
 * the license agreement you entered into with Avalant Co.,Ltd.
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.ApplicationException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.ulo.model.app.DocumentCommentDataM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class OrigDocumentCommentDataMDAOImpl extends OrigObjectDAO implements OrigDocumentCommentDataMDAO {
	private static Logger log = Logger.getLogger(OrigDocumentCommentDataMDAOImpl.class);

	public OrigDocumentCommentDataMDAOImpl() {
		super();

	}

	public void createModelOrigDocumentCommentDataM(DocumentCommentDataM documentComment) throws ApplicationException {
		try {
			createTableORIG_DocumentCommentDATA(documentComment);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_DocumentCommentDATA(DocumentCommentDataM documentComment) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			ORIGGeneratorManager generatorManager = PLORIGEJBService.getGeneratorManager();
			int notePadDataID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.NOTE_PAD_DATA_ID));

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_DOCUMENT_COMMENT ");
			sql.append("( DOC_COMMENT_ID ,APPLICATION_GROUP_ID,COMMENT_DESC ");
			sql.append(" ,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE, STATUS)");
			sql.append(" VALUES(?,?,?   ,?,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);

			ps = conn.prepareStatement(dSql);
			ps.setInt(1, notePadDataID);
			ps.setString(2, documentComment.getApplicationGroupId());
			ps.setString(3, documentComment.getCommentDesc());
			ps.setString(4, documentComment.getCreateBy());
			ps.setTimestamp(5, documentComment.getCreateDate());
			ps.setString(6, documentComment.getUpdateBy());
			ps.setTimestamp(7, documentComment.getUpdateDate());
			ps.setString(8, "A");
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}

	public void deleteModelOrigDocumentCommentDataM(DocumentCommentDataM docComment) throws ApplicationException {
		try {
			deleteTableORIG_DOCUMENT_COMMENT_DATA(docComment);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}

	}

	private void deleteTableORIG_DOCUMENT_COMMENT_DATA(DocumentCommentDataM documentComment) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_DOCUMENT_COMMENT ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND DOC_COMMENT_ID =? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, documentComment.getApplicationGroupId());
			ps.setString(2, documentComment.getDocCommentId());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}

	}

	public Vector<DocumentCommentDataM> loadModelOrigNotePadDataM(String applicationGroupId) throws ApplicationException {
		try {
			return selectTableORIG_DOCUMENT_COMMENT_DATA(applicationGroupId);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private Vector<DocumentCommentDataM> selectTableORIG_DOCUMENT_COMMENT_DATA(String applicationgroupId) throws ApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<DocumentCommentDataM> vNotePadData = new Vector<DocumentCommentDataM>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DOC_COMMENT_ID,COMMENT_DESC ");
			sql.append(" ,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE ");
			sql.append(" FROM ORIG_DOCUMENT_COMMENT WHERE APPLICATION_GROUP_ID = ? ORDER BY CREATE_DATE ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationgroupId);

			rs = ps.executeQuery();
			while (rs.next()) {
				DocumentCommentDataM documentCommentM = new DocumentCommentDataM();
//				documentCommentM.setDocCommentId(rs.getInt(1));
				documentCommentM.setApplicationGroupId(applicationgroupId);
				documentCommentM.setCommentDesc(rs.getString(2));
				// notePadDataM.setNotePadType(rs.getString(3));
				// notePadDataM.setAccountId(rs.getString(5));
				documentCommentM.setCreateBy(rs.getString(3));
				documentCommentM.setCreateDate(rs.getTimestamp(4));
				documentCommentM.setUpdateBy(rs.getString(5));
				documentCommentM.setUpdateDate(rs.getTimestamp(6));
				vNotePadData.add(documentCommentM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return vNotePadData;
	}

	public void saveUpdateModelOrigDocumentCommentDataM(DocumentCommentDataM documentCommentDataM) throws ApplicationException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_DOCUMENT_COMMENT_DATA(documentCommentDataM);
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_DOCUMENT_COMMENT then call Insert method");

				createTableORIG_DocumentCommentDATA(documentCommentDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}

	}

	@Override
	public void saveUpdateModelOrigDocumentCommentDataVect(Vector<DocumentCommentDataM> docCommentVect, String appRecId) throws PLOrigApplicationException {
		try {
			for (DocumentCommentDataM docComment : docCommentVect) {
				docComment.setApplicationGroupId(appRecId);
				double returnRows = this.updateTableORIG_DOCUMENT_COMMENT_DATA(docComment);
				if (returnRows == 0) {
					this.createTableORIG_DocumentCommentDATA(docComment);
				}
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}

	private double updateTableORIG_DOCUMENT_COMMENT_DATA(DocumentCommentDataM documentCommentM) throws ApplicationException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_DOCUMENT_COMMENT ");
			sql.append(" SET  COMMENT_DESC=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND DOC_COMMENT_ID =? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, documentCommentM.getCommentDesc());
			ps.setString(2, documentCommentM.getUpdateBy());
			ps.setString(3, documentCommentM.getApplicationGroupId());
			ps.setString(4, documentCommentM.getDocCommentId());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

}
