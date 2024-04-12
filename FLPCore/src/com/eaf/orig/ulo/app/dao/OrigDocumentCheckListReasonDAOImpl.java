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
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;

public class OrigDocumentCheckListReasonDAOImpl extends OrigObjectDAO implements
		OrigDocumentCheckListReasonDAO {
	public OrigDocumentCheckListReasonDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigDocumentCheckListReasonDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigDocumentCheckListReasonM(
			DocumentCheckListReasonDataM docuChkListReasonM, Connection conn)
			throws ApplicationException {
			try {
				//Get Primary Key
			    logger.debug("docuChkListReasonM.getDocCheckListReasonId() :"+docuChkListReasonM.getDocCheckListReasonId());
			    if(Util.empty(docuChkListReasonM.getDocCheckListReasonId())){
					String docCheckListReasonId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DOC_CHKLST_REASON_PK );
					docuChkListReasonM.setDocCheckListReasonId(docCheckListReasonId);
					docuChkListReasonM.setCreateBy(userId);
			    }
			    docuChkListReasonM.setUpdateBy(userId);
				createTableORIG_DOC_CHECK_LIST_REASON(docuChkListReasonM,conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
	}
	@Override
	public void createOrigDocumentCheckListReasonM(
			DocumentCheckListReasonDataM docuChkListReasonM)throws ApplicationException {
	Connection conn = null;
	try{
		conn = getConnection();
		createOrigDocumentCheckListReasonM(docuChkListReasonM, conn);
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

	private void createTableORIG_DOC_CHECK_LIST_REASON(
			DocumentCheckListReasonDataM docuChkListReasonM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_DOC_CHECK_LIST_REASON ");
			sql.append("( DOC_CHECK_LIST_ID, DOC_CHECK_LIST_REASON_ID, DOC_REASON,GENERATE_FLAG,  ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?  )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, docuChkListReasonM.getDocCheckListId());
			ps.setString(cnt++, docuChkListReasonM.getDocCheckListReasonId());
			ps.setString(cnt++, docuChkListReasonM.getDocReason());
			ps.setString(cnt++, docuChkListReasonM.getGenerateFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, docuChkListReasonM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, docuChkListReasonM.getUpdateBy());
			
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
	public void deleteOrigDocumentCheckListReasonM(String docChkListId, String docCheckListReasonId,Connection conn)
			throws ApplicationException {
		deleteTableORIG_DOC_CHECK_LIST_REASON(docChkListId, docCheckListReasonId, conn);
	}
	@Override
	public void deleteOrigDocumentCheckListReasonM(String docChkListId, String docCheckListReasonId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteOrigDocumentCheckListReasonM(docChkListId, docCheckListReasonId, conn);
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

	private void deleteTableORIG_DOC_CHECK_LIST_REASON(String docChkListId, 
			String docCheckListReasonId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_DOC_CHECK_LIST_REASON ");
			sql.append(" WHERE DOC_CHECK_LIST_ID = ? ");
			if(docCheckListReasonId != null && !docCheckListReasonId.isEmpty()) {
				sql.append(" AND DOC_CHECK_LIST_REASON_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, docChkListId);
			if(docCheckListReasonId != null && !docCheckListReasonId.isEmpty()) {
				ps.setString(2, docCheckListReasonId);
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
	public ArrayList<DocumentCheckListReasonDataM> loadOrigDocumentCheckListReasonM(
			String docChkListId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_DOC_CHECK_LIST_REASON(docChkListId,conn);
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
	public ArrayList<DocumentCheckListReasonDataM> loadOrigDocumentCheckListReasonM(
			String docChkListId, Connection conn) throws ApplicationException {
		return selectTableORIG_DOC_CHECK_LIST_REASON(docChkListId, conn);
	}
	private ArrayList<DocumentCheckListReasonDataM> selectTableORIG_DOC_CHECK_LIST_REASON(
			String docChkListId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DocumentCheckListReasonDataM> docCheckreasonList = new ArrayList<DocumentCheckListReasonDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOC_CHECK_LIST_ID, DOC_CHECK_LIST_REASON_ID, DOC_REASON,GENERATE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM ORIG_DOC_CHECK_LIST_REASON  WHERE DOC_CHECK_LIST_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, docChkListId);

			rs = ps.executeQuery();

			while(rs.next()) {
				DocumentCheckListReasonDataM docCheckListReasonM = new DocumentCheckListReasonDataM();
				docCheckListReasonM.setDocCheckListId(rs.getString("DOC_CHECK_LIST_ID"));
				docCheckListReasonM.setDocCheckListReasonId(rs.getString("DOC_CHECK_LIST_REASON_ID"));
				docCheckListReasonM.setDocReason(rs.getString("DOC_REASON"));
				docCheckListReasonM.setGenerateFlag(rs.getString("GENERATE_FLAG"));
				
				docCheckListReasonM.setCreateBy(rs.getString("CREATE_BY"));
				docCheckListReasonM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				docCheckListReasonM.setUpdateBy(rs.getString("UPDATE_BY"));
				docCheckListReasonM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				docCheckreasonList.add(docCheckListReasonM);
			}

			return docCheckreasonList;
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
	public void saveUpdateOrigDocumentCheckListReasonM(
			DocumentCheckListReasonDataM docuChkListReasonM,Connection conn)
			throws ApplicationException {
		int returnRows = 0;
		docuChkListReasonM.setUpdateBy(userId);
		returnRows = updateTableORIG_DOC_CHECK_LIST_REASON(docuChkListReasonM,conn);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_DOC_CHECK_LIST_REASON then call Insert method");
			docuChkListReasonM.setCreateBy(userId);
			createOrigDocumentCheckListReasonM(docuChkListReasonM,conn);
		}
	}
	@Override
	public void saveUpdateOrigDocumentCheckListReasonM(
			DocumentCheckListReasonDataM docuChkListReasonM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigDocumentCheckListReasonM(docuChkListReasonM, conn);
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

	private int updateTableORIG_DOC_CHECK_LIST_REASON(
			DocumentCheckListReasonDataM docuChkListReasonM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_DOC_CHECK_LIST_REASON ");
			sql.append(" SET DOC_REASON = ?, UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE DOC_CHECK_LIST_ID = ? AND DOC_CHECK_LIST_REASON_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, docuChkListReasonM.getDocReason());
			ps.setString(cnt++, docuChkListReasonM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, docuChkListReasonM.getDocCheckListId());	
			ps.setString(cnt++, docuChkListReasonM.getDocCheckListReasonId());
			
			
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
	public void deleteNotInKeyDocumentCheckListReason(
			ArrayList<DocumentCheckListReasonDataM> docuCheckReasonList,
			String docChkListId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyDocumentCheckListReason(docuCheckReasonList, docChkListId, conn);
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
	public void deleteNotInKeyDocumentCheckListReason(
			ArrayList<DocumentCheckListReasonDataM> docuCheckReasonList,
			String docChkListId,Connection conn) throws ApplicationException {
		deleteNotInKeyTableORIG_DOC_CHECK_LIST_REASON(docuCheckReasonList, docChkListId,conn);
	}
	private void deleteNotInKeyTableORIG_DOC_CHECK_LIST_REASON(
			ArrayList<DocumentCheckListReasonDataM> docuCheckReasonList,
			String docChkListId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
            // conn = Get Connection
//            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_DOC_CHECK_LIST_REASON ");
            sql.append(" WHERE DOC_CHECK_LIST_ID = ? ");
            
            if (docuCheckReasonList != null && !docuCheckReasonList.isEmpty()) {
                sql.append(" AND DOC_CHECK_LIST_REASON_ID NOT IN ( ");
                for (DocumentCheckListReasonDataM docChkReason: docuCheckReasonList) {
                	logger.debug("docChkReason.getDocCheckListReasonId() = "+docChkReason.getDocCheckListReasonId());
                    sql.append(" '" + docChkReason.getDocCheckListReasonId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, docChkListId);
            
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
