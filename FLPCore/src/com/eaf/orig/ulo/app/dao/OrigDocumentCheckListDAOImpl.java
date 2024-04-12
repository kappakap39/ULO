package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.DocumentRelationDataM;

public class OrigDocumentCheckListDAOImpl extends OrigObjectDAO implements OrigDocumentCheckListDAO {
	public OrigDocumentCheckListDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigDocumentCheckListDAOImpl(){
		
	}
	private String userId = "";
	
	@Override
	public void createOrigDocumentCheckListM(DocumentCheckListDataM docuCheckListM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigDocumentCheckListM(docuCheckListM, conn);
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
	public void createOrigDocumentCheckListM(DocumentCheckListDataM docuCheckListM,Connection conn) throws ApplicationException {
		try{
			//Get Primary Key
		    logger.debug("docuCheckListM.getDocCheckListId() :"+docuCheckListM.getDocCheckListId());
		    if(Util.empty(docuCheckListM.getDocCheckListId())) {
				String docCheckListId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DOC_CHECK_LIST_PK );
				docuCheckListM.setDocCheckListId(docCheckListId);
			    docuCheckListM.setCreateBy(userId);
		    }
		    docuCheckListM.setUpdateBy(userId);
			createTableORIG_DOC_CHECK_LIST(docuCheckListM,conn);
			
			ArrayList<DocumentCheckListReasonDataM> docChkReasonList = docuCheckListM.getDocumentCheckListReasons();
			if(!Util.empty(docChkReasonList)) {
				OrigDocumentCheckListReasonDAO docChkReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO(userId);
				for(DocumentCheckListReasonDataM docChkReasonM : docChkReasonList){					
					docChkReasonM.setDocCheckListId(docuCheckListM.getDocCheckListId());
					docChkReasonDAO.createOrigDocumentCheckListReasonM(docChkReasonM,conn);
				}
			}
			
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO(userId);
			ArrayList<DocumentRelationDataM>  docRelationList = docuCheckListM.getDocumentRelations();
			if(docRelationList != null) {
				for(DocumentRelationDataM docRelationM : docRelationList){
					docRelationDAO.createOrigDocumentRelationM(docRelationM,conn);
				}
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_DOC_CHECK_LIST(DocumentCheckListDataM docuCheckListM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_DOC_CHECK_LIST ");
			sql.append("( DOC_CHECK_LIST_ID, APPLICATION_GROUP_ID, DOCUMENT_CODE, ");
			sql.append(" APPLICANT_TYPE, APPLICANT_TYPE_IM, DOC_TYPE_ID, RECEIVE, REMARK, MANUAL_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,?,?,   ?,?,?,?  )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, docuCheckListM.getDocCheckListId());
			ps.setString(cnt++, docuCheckListM.getApplicationGroupId());
			ps.setString(cnt++, docuCheckListM.getDocumentCode());
			
			ps.setString(cnt++, docuCheckListM.getApplicantType());
			ps.setString(cnt++, docuCheckListM.getApplicantTypeIM());
			ps.setString(cnt++, docuCheckListM.getDocTypeId());
			ps.setString(cnt++, docuCheckListM.getReceive());
			ps.setString(cnt++, docuCheckListM.getRemark());
			ps.setString(cnt++, docuCheckListM.getManualFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, docuCheckListM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, docuCheckListM.getUpdateBy());
			
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
	public void deleteOrigDocumentCheckListM(String appGroupId,String docCheckListId) throws ApplicationException {
		OrigDocumentCheckListReasonDAO docChkReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO();
		if(!Util.empty(docCheckListId)) {
			ArrayList<String> docChkListList = selectDocChckIdORIG_DOC_CHECK_LIST(appGroupId);
			if(!Util.empty(docChkListList)) {
				for(String docChkListId: docChkListList) {
					docChkReasonDAO.deleteOrigDocumentCheckListReasonM(docChkListId, null);
				}
			}
		} else {
			docChkReasonDAO.deleteOrigDocumentCheckListReasonM(docCheckListId, null);
		}
		
		deleteTableORIG_DOC_CHECK_LIST(appGroupId, docCheckListId);
	}
	
	@Override
	public void deleteOrigDocumentCheckListM(String appGroupId,String personalId,String docCheckListId) throws ApplicationException {
		String DOC_RELATION_REF_LEVEL_PERSONAL = SystemConstant.getConstant("DOC_RELATION_REF_LEVEL_PERSONAL");
		
		OrigDocumentCheckListReasonDAO docChkReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO();
		OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO();
		
		
		if(!Util.empty(docCheckListId)) {
			ArrayList<String> docChkListList = selectDocChckIdORIG_DOC_CHECK_LIST(appGroupId,personalId,DOC_RELATION_REF_LEVEL_PERSONAL);
			if(!Util.empty(docChkListList)) {
				for(String docChkListId: docChkListList) {
					docChkReasonDAO.deleteOrigDocumentCheckListReasonM(docChkListId, null);
					docRelationDAO.deleteOrigDocumentRelation(docChkListId);
					deleteTableORIG_DOC_CHECK_LIST(appGroupId, docChkListId);
				}
			}
		} else {
			docChkReasonDAO.deleteOrigDocumentCheckListReasonM(docCheckListId, null);
		}
		
	}

	private ArrayList<String> selectDocChckIdORIG_DOC_CHECK_LIST(String appGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> docCheckListList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOC_CHECK_LIST_ID ");
			sql.append(" FROM ORIG_DOC_CHECK_LIST WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				docCheckListList.add(rs.getString("DOC_CHECK_LIST_ID"));
			}

			return docCheckListList;
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
	
	private ArrayList<String> selectDocChckIdORIG_DOC_CHECK_LIST(String appGroupId,String refId,String refLevel) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> docCheckListList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ORIG_DOC_CHECK_LIST.DOC_CHECK_LIST_ID FROM ORIG_DOC_CHECK_LIST  ");
			sql.append(" join ORIG_DOC_RELATION on ORIG_DOC_RELATION.DOC_CHECK_LIST_ID=ORIG_DOC_CHECK_LIST.DOC_CHECK_LIST_ID ");
			sql.append(" WHERE ORIG_DOC_CHECK_LIST.APPLICATION_GROUP_ID = ?");
			sql.append(" and ORIG_DOC_RELATION.REF_ID=? and ORIG_DOC_RELATION.REF_LEVEL =? ");

			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appGroupId);
			ps.setString(2, refId);
			ps.setString(3, refLevel);

			rs = ps.executeQuery();

			while(rs.next()) {
				docCheckListList.add(rs.getString("DOC_CHECK_LIST_ID"));
			}

			return docCheckListList;
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

	private void deleteTableORIG_DOC_CHECK_LIST(String applicationGroupId,String docCheckListId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_DOC_CHECK_LIST ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(!Util.empty(docCheckListId)) {
				sql.append(" AND DOC_CHECK_LIST_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(!Util.empty(docCheckListId)) {
				ps.setString(2, docCheckListId);
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
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListM(String applicationGroupId) throws ApplicationException {
		ArrayList<DocumentCheckListDataM> result = selectTableORIG_DOC_CHECK_LIST(applicationGroupId);
		
		if(!Util.empty(result)) {
			OrigDocumentCheckListReasonDAO docChkListReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO();
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO();
			for(DocumentCheckListDataM docChkListM : result) {
				ArrayList<DocumentCheckListReasonDataM> docChkReasonList = docChkListReasonDAO.
						loadOrigDocumentCheckListReasonM(docChkListM.getDocCheckListId());
				if(!Util.empty(docChkReasonList)) {
					docChkListM.setDocumentCheckListReasons(docChkReasonList);
				}
				
				ArrayList<DocumentRelationDataM>  docRelationList = docRelationDAO.loadOrigDocumentRelationM(docChkListM.getDocCheckListId());
				if(!Util.empty(docRelationList)){
					docChkListM.setDocumentRelations(docRelationList);
				}
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		ArrayList<DocumentCheckListDataM> result = selectTableORIG_DOC_CHECK_LIST(applicationGroupId,conn);
		
		if(!Util.empty(result)) {
			OrigDocumentCheckListReasonDAO docChkListReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO();
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO();
			for(DocumentCheckListDataM docChkListM : result) {
				ArrayList<DocumentCheckListReasonDataM> docChkReasonList = docChkListReasonDAO.
						loadOrigDocumentCheckListReasonM(docChkListM.getDocCheckListId(),conn);
				if(!Util.empty(docChkReasonList)) {
					docChkListM.setDocumentCheckListReasons(docChkReasonList);
				}
				
				ArrayList<DocumentRelationDataM>  docRelationList = docRelationDAO.loadOrigDocumentRelationM(docChkListM.getDocCheckListId(),conn);
				if(!Util.empty(docRelationList)){
					docChkListM.setDocumentRelations(docRelationList);
				}
			}
		}
		return result;
	}
	private ArrayList<DocumentCheckListDataM> selectTableORIG_DOC_CHECK_LIST(String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_DOC_CHECK_LIST(applicationGroupId);
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
	
	private ArrayList<DocumentCheckListDataM> selectTableORIG_DOC_CHECK_LIST(String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DocumentCheckListDataM> docCheckListList = new ArrayList<DocumentCheckListDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOC_CHECK_LIST_ID, APPLICATION_GROUP_ID, DOCUMENT_CODE, ");
			sql.append(" APPLICANT_TYPE, APPLICANT_TYPE_IM, DOC_TYPE_ID, RECEIVE, REMARK, MANUAL_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_DOC_CHECK_LIST WHERE APPLICATION_GROUP_ID = ? AND MANUAL_FLAG IS NULL ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				DocumentCheckListDataM docCheckListM = new DocumentCheckListDataM();
				docCheckListM.setDocCheckListId(rs.getString("DOC_CHECK_LIST_ID"));
				docCheckListM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				docCheckListM.setDocumentCode(rs.getString("DOCUMENT_CODE"));
				
				docCheckListM.setApplicantType(rs.getString("APPLICANT_TYPE"));
				docCheckListM.setApplicantTypeIM(rs.getString("APPLICANT_TYPE_IM"));
				docCheckListM.setDocTypeId(rs.getString("DOC_TYPE_ID"));
				docCheckListM.setReceive(rs.getString("RECEIVE"));
				docCheckListM.setRemark(rs.getString("REMARK"));
				docCheckListM.setManualFlag(rs.getString("MANUAL_FLAG"));

				docCheckListM.setCreateBy(rs.getString("CREATE_BY"));
				docCheckListM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				docCheckListM.setUpdateBy(rs.getString("UPDATE_BY"));
				docCheckListM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				docCheckListList.add(docCheckListM);
			}

			return docCheckListList;
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
	public void saveUpdateOrigDocumentCheckListM(
			DocumentCheckListDataM docuCheckListM, Connection conn)
			throws ApplicationException {
		int returnRows = 0;
		docuCheckListM.setUpdateBy(userId);
		returnRows = updateTableORIG_DOC_CHECK_LIST(docuCheckListM,conn);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_DOC_CHECK_LIST then call Insert method");
			docuCheckListM.setCreateBy(userId);
			createOrigDocumentCheckListM(docuCheckListM,conn);
		} else {
		
			ArrayList<DocumentCheckListReasonDataM> docChkReasonList = docuCheckListM.getDocumentCheckListReasons();
			OrigDocumentCheckListReasonDAO docChkReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO(userId);
			if(!Util.empty(docChkReasonList)) {
				for(DocumentCheckListReasonDataM docChkReasonM : docChkReasonList){
					docChkReasonM.setDocCheckListId(docuCheckListM.getDocCheckListId());
					docChkReasonDAO.saveUpdateOrigDocumentCheckListReasonM(docChkReasonM,conn);
				}
			}			
			docChkReasonDAO.deleteNotInKeyDocumentCheckListReason(docChkReasonList, docuCheckListM.getDocCheckListId(),conn);
		}
	}
	@Override
	public void saveUpdateOrigDocumentCheckListM(DocumentCheckListDataM docuCheckListM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigDocumentCheckListM(docuCheckListM, conn);
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

	private int updateTableORIG_DOC_CHECK_LIST(DocumentCheckListDataM docuCheckListM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_DOC_CHECK_LIST ");
			sql.append(" SET DOCUMENT_CODE = ?, APPLICANT_TYPE = ?, APPLICANT_TYPE_IM = ?, ");
			sql.append(" DOC_TYPE_ID = ?, RECEIVE = ?, REMARK = ?, MANUAL_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE DOC_CHECK_LIST_ID = ? AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, docuCheckListM.getDocumentCode());
			ps.setString(cnt++, docuCheckListM.getApplicantType());
			ps.setString(cnt++, docuCheckListM.getApplicantTypeIM());
			
			ps.setString(cnt++, docuCheckListM.getDocTypeId());		
			ps.setString(cnt++, docuCheckListM.getReceive());		
			ps.setString(cnt++, docuCheckListM.getRemark());
			ps.setString(cnt++, docuCheckListM.getManualFlag());
			
			ps.setString(cnt++, docuCheckListM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, docuCheckListM.getDocCheckListId());	
			ps.setString(cnt++, docuCheckListM.getApplicationGroupId());
			
			
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
	public void deleteNotInKeyDocumentCheckList(
			ArrayList<DocumentCheckListDataM> docuCheckList,
			String applicationGroupId, Connection conn)
			throws ApplicationException {

		ArrayList<String> notInKeyList = loadNotInKeyDocumentCheckListM(docuCheckList,applicationGroupId,conn);
		
		if(!Util.empty(notInKeyList)) {
			OrigDocumentCheckListReasonDAO docChkReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO();
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO();
			for(String idNotInKey: notInKeyList) {
				docChkReasonDAO.deleteOrigDocumentCheckListReasonM(idNotInKey,null,conn);
				docRelationDAO.deleteOrigDocumentRelation(idNotInKey,conn);
			}
		}
		
		deleteNotInKeyTableORIG_DOC_CHECK_LIST(docuCheckList, applicationGroupId);
	}
	@Override
	public void deleteNotInKeyDocumentCheckList(ArrayList<DocumentCheckListDataM> docuCheckList,String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyDocumentCheckList(docuCheckList, applicationGroupId, conn);
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

	private ArrayList<String> loadNotInKeyDocumentCheckListM(ArrayList<DocumentCheckListDataM> docuCheckList, String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Connection conn = null;
		ArrayList<String> appList = new ArrayList<String>();
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOC_CHECK_LIST_ID ");
			sql.append(" FROM ORIG_DOC_CHECK_LIST  WHERE APPLICATION_GROUP_ID = ? ");
			if (!Util.empty(docuCheckList)) {
                sql.append(" AND DOC_CHECK_LIST_ID NOT IN ( ");
                for (DocumentCheckListDataM docuChkListM: docuCheckList) {
                	logger.debug("docuChkListM.getDocCheckListId() = "+docuChkListM.getDocCheckListId());
                    sql.append(" '" + docuChkListM.getDocCheckListId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				appList.add(rs.getString("DOC_CHECK_LIST_ID"));
			}

			return appList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, rs, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	private void deleteNotInKeyTableORIG_DOC_CHECK_LIST(ArrayList<DocumentCheckListDataM> docuCheckList,String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_DOC_CHECK_LIST ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (!Util.empty(docuCheckList)) {
                sql.append(" AND DOC_CHECK_LIST_ID NOT IN ( ");
                for (DocumentCheckListDataM docCheckListM: docuCheckList) {
                	logger.debug("docCheckListM.getDocCheckListId() = "+docCheckListM.getDocCheckListId());
                    sql.append(" '" + docCheckListM.getDocCheckListId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            logger.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
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
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListManualM(String applicationGroupId) throws ApplicationException {
		ArrayList<DocumentCheckListDataM> result = selectTableORIG_DOC_CHECK_LIST_MANUAL(applicationGroupId);
		
		if(!Util.empty(result)) {
			OrigDocumentCheckListReasonDAO docChkListReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO();
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO();
			for(DocumentCheckListDataM docChkListM : result) {
				ArrayList<DocumentCheckListReasonDataM> docChkReasonList = docChkListReasonDAO.
						loadOrigDocumentCheckListReasonM(docChkListM.getDocCheckListId());
				if(!Util.empty(docChkReasonList)) {
					docChkListM.setDocumentCheckListReasons(docChkReasonList);
				}
				
				ArrayList<DocumentRelationDataM>  docRelationList = docRelationDAO.loadOrigDocumentRelationM(docChkListM.getDocCheckListId());
				if(!Util.empty(docRelationList)){
					docChkListM.setDocumentRelations(docRelationList);
				}
			}
		}
		return result;
	}
	@Override
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListManualM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		ArrayList<DocumentCheckListDataM> result = selectTableORIG_DOC_CHECK_LIST_MANUAL(applicationGroupId,conn);
		
		if(!Util.empty(result)) {
			OrigDocumentCheckListReasonDAO docChkListReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO();
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO();
			for(DocumentCheckListDataM docChkListM : result) {
				ArrayList<DocumentCheckListReasonDataM> docChkReasonList = docChkListReasonDAO.
						loadOrigDocumentCheckListReasonM(docChkListM.getDocCheckListId(),conn);
				if(!Util.empty(docChkReasonList)) {
					docChkListM.setDocumentCheckListReasons(docChkReasonList);
				}
				
				ArrayList<DocumentRelationDataM>  docRelationList = docRelationDAO.loadOrigDocumentRelationM(docChkListM.getDocCheckListId(),conn);
				if(!Util.empty(docRelationList)){
					docChkListM.setDocumentRelations(docRelationList);
				}
			}
		}
		return result;
	}
	private ArrayList<DocumentCheckListDataM> selectTableORIG_DOC_CHECK_LIST_MANUAL(String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_DOC_CHECK_LIST_MANUAL(applicationGroupId);
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
	private ArrayList<DocumentCheckListDataM> selectTableORIG_DOC_CHECK_LIST_MANUAL(String applicationGroupId,Connection conn) throws ApplicationException {
		String DOC_CHECKLIST_MANUAL_FLAG_INCOME = SystemConstant.getConstant("DOC_CHECKLIST_MANUAL_FLAG_INCOME");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DocumentCheckListDataM> docCheckListList = new ArrayList<DocumentCheckListDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOC_CHECK_LIST_ID, APPLICATION_GROUP_ID, DOCUMENT_CODE, ");
			sql.append(" APPLICANT_TYPE, APPLICANT_TYPE_IM, DOC_TYPE_ID, RECEIVE, REMARK, MANUAL_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_DOC_CHECK_LIST WHERE APPLICATION_GROUP_ID = ? AND MANUAL_FLAG = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);
			ps.setString(2, DOC_CHECKLIST_MANUAL_FLAG_INCOME);

			rs = ps.executeQuery();

			while(rs.next()) {
				DocumentCheckListDataM docCheckListM = new DocumentCheckListDataM();
				docCheckListM.setDocCheckListId(rs.getString("DOC_CHECK_LIST_ID"));
				docCheckListM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				docCheckListM.setDocumentCode(rs.getString("DOCUMENT_CODE"));
				
				docCheckListM.setApplicantType(rs.getString("APPLICANT_TYPE"));
				docCheckListM.setApplicantTypeIM(rs.getString("APPLICANT_TYPE_IM"));
				docCheckListM.setDocTypeId(rs.getString("DOC_TYPE_ID"));
				docCheckListM.setReceive(rs.getString("RECEIVE"));
				docCheckListM.setRemark(rs.getString("REMARK"));
				docCheckListM.setManualFlag(rs.getString("MANUAL_FLAG"));

				docCheckListM.setCreateBy(rs.getString("CREATE_BY"));
				docCheckListM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				docCheckListM.setUpdateBy(rs.getString("UPDATE_BY"));
				docCheckListM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				docCheckListList.add(docCheckListM);
			}

			return docCheckListList;
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
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListManualAdditionalM(String applicationGroupId) throws ApplicationException {
		ArrayList<DocumentCheckListDataM> result = selectTableORIG_DOC_CHECK_LIST_MANUAL_ADDITIONAL(applicationGroupId);
		
		if(!Util.empty(result)) {
			OrigDocumentCheckListReasonDAO docChkListReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO();
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO();
			for(DocumentCheckListDataM docChkListM : result) {
				ArrayList<DocumentCheckListReasonDataM> docChkReasonList = docChkListReasonDAO.
						loadOrigDocumentCheckListReasonM(docChkListM.getDocCheckListId());
				if(!Util.empty(docChkReasonList)) {
					docChkListM.setDocumentCheckListReasons(docChkReasonList);
				}
				
				ArrayList<DocumentRelationDataM>  docRelationList = docRelationDAO.loadOrigDocumentRelationM(docChkListM.getDocCheckListId());
				if(!Util.empty(docRelationList)){
					docChkListM.setDocumentRelations(docRelationList);
				}
			}
		}
		return result;
	}
	@Override
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListManualAdditionalM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		ArrayList<DocumentCheckListDataM> result = selectTableORIG_DOC_CHECK_LIST_MANUAL_ADDITIONAL(applicationGroupId,conn);
		
		if(!Util.empty(result)) {
			OrigDocumentCheckListReasonDAO docChkListReasonDAO = ORIGDAOFactory.getDocumentCheckListReasonDAO();
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO();
			for(DocumentCheckListDataM docChkListM : result) {
				ArrayList<DocumentCheckListReasonDataM> docChkReasonList = docChkListReasonDAO.
						loadOrigDocumentCheckListReasonM(docChkListM.getDocCheckListId(),conn);
				if(!Util.empty(docChkReasonList)) {
					docChkListM.setDocumentCheckListReasons(docChkReasonList);
				}
				
				ArrayList<DocumentRelationDataM>  docRelationList = docRelationDAO.loadOrigDocumentRelationM(docChkListM.getDocCheckListId(),conn);
				if(!Util.empty(docRelationList)){
					docChkListM.setDocumentRelations(docRelationList);
				}
			}
		}
		return result;
	}
	private ArrayList<DocumentCheckListDataM> selectTableORIG_DOC_CHECK_LIST_MANUAL_ADDITIONAL(String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_DOC_CHECK_LIST_MANUAL_ADDITIONAL(applicationGroupId);
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
	private ArrayList<DocumentCheckListDataM> selectTableORIG_DOC_CHECK_LIST_MANUAL_ADDITIONAL(String applicationGroupId,Connection conn) throws ApplicationException {
		String DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL = SystemConstant.getConstant("DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DocumentCheckListDataM> docCheckListList = new ArrayList<DocumentCheckListDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DOC_CHECK_LIST_ID, APPLICATION_GROUP_ID, DOCUMENT_CODE, ");
			sql.append(" APPLICANT_TYPE, APPLICANT_TYPE_IM, DOC_TYPE_ID, RECEIVE, REMARK, MANUAL_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_DOC_CHECK_LIST WHERE APPLICATION_GROUP_ID = ? AND MANUAL_FLAG = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);
			ps.setString(2, DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL);

			rs = ps.executeQuery();

			while(rs.next()) {
				DocumentCheckListDataM docCheckListM = new DocumentCheckListDataM();
				docCheckListM.setDocCheckListId(rs.getString("DOC_CHECK_LIST_ID"));
				docCheckListM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				docCheckListM.setDocumentCode(rs.getString("DOCUMENT_CODE"));
				
				docCheckListM.setApplicantType(rs.getString("APPLICANT_TYPE"));
				docCheckListM.setApplicantTypeIM(rs.getString("APPLICANT_TYPE_IM"));
				docCheckListM.setDocTypeId(rs.getString("DOC_TYPE_ID"));
				docCheckListM.setReceive(rs.getString("RECEIVE"));
				docCheckListM.setRemark(rs.getString("REMARK"));
				docCheckListM.setManualFlag(rs.getString("MANUAL_FLAG"));

				docCheckListM.setCreateBy(rs.getString("CREATE_BY"));
				docCheckListM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				docCheckListM.setUpdateBy(rs.getString("UPDATE_BY"));
				docCheckListM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				docCheckListList.add(docCheckListM);
			}

			return docCheckListList;
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

}
