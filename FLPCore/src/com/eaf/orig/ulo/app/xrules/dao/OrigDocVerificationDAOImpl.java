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
import com.eaf.orig.ulo.model.app.DocumentVerificationDataM;

public class OrigDocVerificationDAOImpl extends OrigObjectDAO implements
		OrigDocVerificationDAO {
	public OrigDocVerificationDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigDocVerificationDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigDocumentVerificationM(
			DocumentVerificationDataM docVerificationM, Connection conn)
			throws ApplicationException {
		try {
			logger.debug("docVerificationM.getDocumentVerId() :"+docVerificationM.getDocumentVerId());
		    if(Util.empty(docVerificationM.getDocumentVerId())){
				String documentVerId =  GenerateUnique.generate(OrigConstant.SeqNames.XRULES_DOC_VERIFICATION_PK,conn); 
				docVerificationM.setDocumentVerId(documentVerId);
			}
		    docVerificationM.setCreateBy(userId);
		    docVerificationM.setUpdateBy(userId);
			createTableXRULES_DOCUMENT_VERIFICATION(docVerificationM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void createOrigDocumentVerificationM(
			DocumentVerificationDataM docVerificationM)	throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigDocumentVerificationM(docVerificationM, conn);
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

	private void createTableXRULES_DOCUMENT_VERIFICATION(
			DocumentVerificationDataM docVerificationM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_DOCUMENT_VERIFICATION ");
			sql.append("( DOCUMENT_VER_ID, VER_RESULT_ID, FOLLOWING_TYPE, VER_RESULT, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, docVerificationM.getDocumentVerId());
			ps.setString(cnt++, docVerificationM.getVerResultId());
			ps.setString(cnt++, docVerificationM.getFollowingType());
			ps.setString(cnt++, docVerificationM.getVerResult());
			
			ps.setString(cnt++, docVerificationM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, docVerificationM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
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
	public void deleteOrigDocumentVerificationM(String verResultId,
			String documentVerId) throws ApplicationException {
		try {
			deleteTableXRULES_DOCUMENT_VERIFICATION(verResultId, documentVerId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_DOCUMENT_VERIFICATION(String verResultId,
			String documentVerId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_DOCUMENT_VERIFICATION ");
			sql.append(" WHERE VER_RESULT_ID = ? ");
			if(!Util.empty(documentVerId)) {
				sql.append(" AND DOCUMENT_VER_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);
			if(!Util.empty(documentVerId)) {
				ps.setString(2, documentVerId);
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
	public ArrayList<DocumentVerificationDataM> loadOrigDocumentVerificationM(
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_DOCUMENT_VERIFICATION(verResultId,conn);
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
	public ArrayList<DocumentVerificationDataM> loadOrigDocumentVerificationM(
			String verResultId, Connection conn) throws ApplicationException {
		return selectTableXRULES_DOCUMENT_VERIFICATION(verResultId, conn);
	}
	
	private ArrayList<DocumentVerificationDataM> selectTableXRULES_DOCUMENT_VERIFICATION(
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DocumentVerificationDataM> docVerResultList = new ArrayList<DocumentVerificationDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOCUMENT_VER_ID, VER_RESULT_ID, FOLLOWING_TYPE, VER_RESULT, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_DOCUMENT_VERIFICATION WHERE VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while(rs.next()) {
				DocumentVerificationDataM docVerResultM = new DocumentVerificationDataM();
				docVerResultM.setDocumentVerId(rs.getString("DOCUMENT_VER_ID"));
				docVerResultM.setVerResultId(rs.getString("VER_RESULT_ID"));
				docVerResultM.setFollowingType(rs.getString("FOLLOWING_TYPE"));
				docVerResultM.setVerResult(rs.getString("VER_RESULT"));
				
				docVerResultM.setCreateBy(rs.getString("CREATE_BY"));
				docVerResultM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				docVerResultM.setUpdateBy(rs.getString("UPDATE_BY"));
				docVerResultM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				docVerResultList.add(docVerResultM);
			}

			return docVerResultList;
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
	public void saveUpdateOrigDocumentVerificationM(
			DocumentVerificationDataM docVerificationM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigDocumentVerificationM(docVerificationM, conn);
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
	public void saveUpdateOrigDocumentVerificationM(
			DocumentVerificationDataM docVerificationM, Connection conn)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			docVerificationM.setUpdateBy(userId);
			returnRows = updateTableXRULES_DOCUMENT_VERIFICATION(docVerificationM,conn);
			if (returnRows == 0) {
				docVerificationM.setCreateBy(userId);
				docVerificationM.setUpdateBy(userId);
				createOrigDocumentVerificationM(docVerificationM,conn);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableXRULES_DOCUMENT_VERIFICATION(
			DocumentVerificationDataM docVerificationM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_DOCUMENT_VERIFICATION ");
			sql.append(" SET FOLLOWING_TYPE = ?, VER_RESULT = ?, ");
			sql.append(" UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE VER_RESULT_ID = ? AND DOCUMENT_VER_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, docVerificationM.getFollowingType());
			ps.setString(cnt++, docVerificationM.getVerResult());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, docVerificationM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, docVerificationM.getVerResultId());
			ps.setString(cnt++, docVerificationM.getDocumentVerId());
			
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
	public void deleteNotInKeyDocumentVerification(
			ArrayList<DocumentVerificationDataM> docVerificationList,
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyDocumentVerification(docVerificationList, verResultId, conn);
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
	public void deleteNotInKeyDocumentVerification(
			ArrayList<DocumentVerificationDataM> docVerificationList,
			String verResultId, Connection conn) throws ApplicationException {
		deleteNotInKeyTableXRULES_DOCUMENT_VERIFICATION(docVerificationList, verResultId, conn);
	}
	private void deleteNotInKeyTableXRULES_DOCUMENT_VERIFICATION(
			ArrayList<DocumentVerificationDataM> docVerificationList,
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_DOCUMENT_VERIFICATION ");
            sql.append(" WHERE VER_RESULT_ID = ? ");
            
            if (!Util.empty(docVerificationList)) {
                sql.append(" AND DOCUMENT_VER_ID NOT IN ( ");

                for (DocumentVerificationDataM docVerificationM: docVerificationList) {
                    sql.append(" '" + docVerificationM.getDocumentVerId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, verResultId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
