package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentRelationDataM;

public class OrigDocumentRelationDAOImpl extends OrigObjectDAO implements
		OrigDocumentRelationDAO {
	public OrigDocumentRelationDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigDocumentRelationDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigDocumentRelationM(DocumentRelationDataM docRelationM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigDocumentRelationM(docRelationM, conn);
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
	public void createOrigDocumentRelationM(DocumentRelationDataM docRelationM,Connection conn)
			throws ApplicationException {
		docRelationM.setCreateBy(userId);
		docRelationM.setUpdateBy(userId);
		createTableORIG_DOC_RELATION(docRelationM,conn);
	}
	private void createTableORIG_DOC_RELATION(DocumentRelationDataM docRelationM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_DOC_RELATION ");
			sql.append("( DOC_CHECK_LIST_ID, REF_ID, REF_LEVEL, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, docRelationM.getDocCheckListId());
			ps.setString(cnt++, docRelationM.getRefId());
			ps.setString(cnt++, docRelationM.getRefLevel());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, docRelationM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, docRelationM.getUpdateBy());
			
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
	public void deleteOrigDocumentRelationM(String docCheckListId,
			String refId, String refLevel) throws ApplicationException {
		deleteTableORIG_DOC_RELATION(docCheckListId, refId, refLevel);
	}

	private void deleteTableORIG_DOC_RELATION(String docCheckListId,
			String refId, String refLevel) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_DOC_RELATION ");
			sql.append(" WHERE DOC_CHECK_LIST_ID = ? ");
			if(!Util.empty(refId) && !Util.empty(refLevel)) {
				sql.append(" AND REF_ID = ? ");
				//sql.append(" AND REF_LEVEL = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, docCheckListId);
			if(!Util.empty(refId) && !Util.empty(refLevel)) {
				ps.setString(2, refId);
				//ps.setString(3, refLevel);
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
	public ArrayList<DocumentRelationDataM> loadOrigDocumentRelationM(String docCheckListId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_DOC_RELATION(docCheckListId,conn);
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
	public ArrayList<DocumentRelationDataM> loadOrigDocumentRelationM(
			String docCheckListId, Connection conn) throws ApplicationException {
		return selectTableORIG_DOC_RELATION(docCheckListId, conn);
	}
	private ArrayList<DocumentRelationDataM> selectTableORIG_DOC_RELATION(
			String docCheckListId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DocumentRelationDataM> docRelationList = new ArrayList<DocumentRelationDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOC_CHECK_LIST_ID, REF_ID, REF_LEVEL, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_DOC_RELATION WHERE DOC_CHECK_LIST_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, docCheckListId);

			rs = ps.executeQuery();

			while(rs.next()) {
				DocumentRelationDataM docRelationM = new DocumentRelationDataM();
				docRelationM.setDocCheckListId(rs.getString("DOC_CHECK_LIST_ID"));
				docRelationM.setRefId(rs.getString("REF_ID"));
				docRelationM.setRefLevel(rs.getString("REF_LEVEL"));
				
				docRelationM.setCreateBy(rs.getString("CREATE_BY"));
				docRelationM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				docRelationM.setUpdateBy(rs.getString("UPDATE_BY"));
				docRelationM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				docRelationList.add(docRelationM);
			}

			return docRelationList;
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
	public ArrayList<DocumentRelationDataM> loadOrigDocumentRelationM(String refId, String refLevel) throws ApplicationException {
		ArrayList<DocumentRelationDataM> result = selectTableORIG_DOC_RELATION(refId, refLevel);
		return result;
	}
	private ArrayList<DocumentRelationDataM> selectTableORIG_DOC_RELATION(
			String refId, String refLevel) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<DocumentRelationDataM> docRelationList = new ArrayList<DocumentRelationDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOC_CHECK_LIST_ID, REF_ID, REF_LEVEL, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_DOC_RELATION ");
			sql.append(" WHERE REF_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, refId);
//			/ps.setString(2, refLevel);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				DocumentRelationDataM docRelationM = new DocumentRelationDataM();
				docRelationM.setDocCheckListId(rs.getString("DOC_CHECK_LIST_ID"));
				docRelationM.setRefId(rs.getString("REF_ID"));
				docRelationM.setRefLevel(rs.getString("REF_LEVEL"));
				
				docRelationM.setCreateBy(rs.getString("CREATE_BY"));
				docRelationM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				docRelationM.setUpdateBy(rs.getString("UPDATE_BY"));
				docRelationM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				docRelationList.add(docRelationM);
			}
			
			return docRelationList;
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
	public String loadOrigDocumentRelationMByPersonalId(String personalId,String DOC_CODE,String REF_LEVEL) throws ApplicationException {
		String result = selectTableORIG_DOC_RELATION_ByRefId(personalId,DOC_CODE, REF_LEVEL);
		return result;
	}

	private String selectTableORIG_DOC_RELATION_ByRefId(String RefId,String DOC_CODE,String REF_LEVEL) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String docRelationListID = "";
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT A.DOC_CHECK_LIST_ID ");
			sql.append(" FROM ORIG_DOC_RELATION A ");
			sql.append(" LEFT JOIN ORIG_DOC_CHECK_LIST B ON B.DOC_CHECK_LIST_ID = A.DOC_CHECK_LIST_ID ");
			sql.append(" WHERE  A.REF_ID = ? ");
			sql.append(" AND B.DOCUMENT_CODE = ? ");
			//sql.append(" AND A.REF_LEVEL = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index=1;
			ps.setString(index++, RefId);
			ps.setString(index++, DOC_CODE);
			//ps.setString(index++, REF_LEVEL);

			rs = ps.executeQuery();

			while(rs.next()) {
				docRelationListID = rs.getString("DOC_CHECK_LIST_ID");
			}

			return docRelationListID;
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
	public void saveUpdateOrigDocumentRelationM(
			DocumentRelationDataM docRelationM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigDocumentRelationM(docRelationM, conn);
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
	public void saveUpdateOrigDocumentRelationM(
			DocumentRelationDataM docRelationM,Connection conn) throws ApplicationException {
		boolean isExists = false;
		docRelationM.setUpdateBy(userId);
		isExists = isExistORIG_DOC_RELATION(docRelationM,conn);
		if (!isExists) {
			docRelationM.setCreateBy(userId);
			createOrigDocumentRelationM(docRelationM,conn);
		} 
	}
	private boolean isExistORIG_DOC_RELATION(DocumentRelationDataM docRelationM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOC_CHECK_LIST_ID, REF_ID, REF_LEVEL ");
			sql.append(" FROM ORIG_DOC_RELATION ");
			sql.append(" WHERE DOC_CHECK_LIST_ID = ? AND REF_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, docRelationM.getDocCheckListId());
			ps.setString(2, docRelationM.getRefId());
			//ps.setString(3, docRelationM.getRefLevel());

			rs = ps.executeQuery();

			if(rs.next()) {
				return true;
			}
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
		return false;
	}

	@Override
	public void deleteNotInKeyDocumentRelation(ArrayList<DocumentRelationDataM> docRelationList,String refId, String refLevel) throws ApplicationException {
		deleteNotInKeyORIG_DOC_RELATION(docRelationList, refId, refLevel);
	}

	private void deleteNotInKeyORIG_DOC_RELATION(
			ArrayList<DocumentRelationDataM> docRelationList,
			String refId, String refLevel) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE FROM ORIG_DOC_RELATION ");
			sql.append(" WHERE REF_ID = ? ");
			if (!Util.empty(docRelationList)) {
                sql.append(" AND DOC_CHECK_LIST_ID NOT IN ( ");
                for (DocumentRelationDataM docRelationM: docRelationList) {
                	sql.append(" '" + docRelationM.getDocCheckListId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, refId);
			//ps.setString(2, refLevel);
            
			ps.executeUpdate();

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
	public void deleteOrigDocumentRelation(String docCheckListId,Connection conn)throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
            // conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			if (!Util.empty(docCheckListId)) {
				sql.append(" DELETE FROM ORIG_DOC_RELATION ");
				sql.append(" WHERE ");
                sql.append(" DOC_CHECK_LIST_ID = ? ");
            }

			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql); 
			ps.setString(1, docCheckListId);
			
			ps.executeUpdate();

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
	@Override
	public void deleteOrigDocumentRelation(String docCheckListId)throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteOrigDocumentRelation(docCheckListId, conn);
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
	public void deleteOrigDocumentRelation(ArrayList<DocumentCheckListDataM> docCheckLists ,String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			if (!Util.empty(docCheckLists)) {
				sql.append(" DELETE FROM ORIG_DOC_RELATION ");
				sql.append(" WHERE DOC_CHECK_LIST_ID IN (SELECT DOC_CHECK_LIST_ID FROM ORIG_DOC_CHECK_LIST WHERE APPLICATION_GROUP_ID =?");
				sql.append(" AND  DOC_CHECK_LIST_ID NOT IN (");
				String COMMA ="";
				for(DocumentCheckListDataM documentCheckList : docCheckLists){
					sql.append(COMMA+"'"+documentCheckList.getDocCheckListId()+"'");
					COMMA=",";
				}				
				sql.append(" )");
				sql.append(" )");
            }

			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);  
			ps.setString(1, applicationGroupId);
			ps.executeUpdate();

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
