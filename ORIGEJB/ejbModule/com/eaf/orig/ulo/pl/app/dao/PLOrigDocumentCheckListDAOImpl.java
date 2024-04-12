package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigDocumentCheckListDAOImpl extends OrigObjectDAO implements PLOrigDocumentCheckListDAO {
	
	static Logger log = Logger.getLogger(PLOrigDocumentCheckListDAOImpl.class);

	@Override
	public void SaveUpdateOrigDocumentCheckList(Vector<PLDocumentCheckListDataM> docCheckListV, String appRecId) throws PLOrigApplicationException {
		try {
			if(!OrigUtil.isEmptyVector(docCheckListV)){
				Vector<String> docIDVect = new Vector<String>();
				for(PLDocumentCheckListDataM docChkM : docCheckListV){					
					if(OrigUtil.isEmptyString(docChkM.getDocCheckListId())){
						String docListID = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.DOC_LIST_ID);
						docChkM.setDocCheckListId(docListID);
					}
					
					int rowUpdate = this.updateTableORIG_DOC_CHECK_LIST(docChkM, appRecId);
					if(rowUpdate == 0){						
						this.insertTableORIG_DOC_CHECK_LIST(docChkM, appRecId);
					}
					
					docIDVect.add(docChkM.getDocCheckListId());
					
					this.saveUpdateTableORIG_DOC_CHECK_LIST_REASON(docChkM.getDocCkReasonVect(), docChkM.getDocCheckListId());
					
				}
				
				if(!OrigUtil.isEmptyVector(docIDVect)){
					this.deleteTableORIG_DOC_CHECK_LIST(docIDVect, appRecId);
				}			
				
			}			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}

	}
	
	private int updateTableORIG_DOC_CHECK_LIST(PLDocumentCheckListDataM docCheckListM, String appRecId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		int rowUpdate = 0;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append(" UPDATE ORIG_DOC_CHECK_LIST ");
			sql.append(" SET DOCUMENT_CODE=?, JOB_STATE=?, APPLICANT_TYPE=?, DOC_TYPE_ID=?, DOC_TYPE_DESC=? ");
			sql.append(",REQUIRED=?, RECEIVE=?, ROLE=?, REMARK=?, WAIVE=? ");
			sql.append(",REQUEST_DOC=? ,UPDATE_BY=? ,UPDATE_DATE = SYSDATE ");
			sql.append(" WHERE DOC_CHECK_LIST_ID=? AND APPLICATION_RECORD_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			
			ps.setString(index++, docCheckListM.getDocCode());
			ps.setString(index++, docCheckListM.getJobState());
			ps.setString(index++, docCheckListM.getApplicationType());
			ps.setString(index++, docCheckListM.getDocTypeId());
			ps.setString(index++, docCheckListM.getDocTypeDesc());
			ps.setString(index++, docCheckListM.getRequired());
			ps.setString(index++, docCheckListM.getReceive());
			ps.setString(index++, docCheckListM.getRole());
			ps.setString(index++, docCheckListM.getRemark());
			ps.setString(index++, docCheckListM.getWaive());			
			ps.setString(index++, docCheckListM.getRequestDoc());	
			ps.setString(index++, docCheckListM.getUpdateBy());
			ps.setString(index++, docCheckListM.getDocCheckListId());
			ps.setString(index++, appRecId);

			rowUpdate = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return rowUpdate;
	}
	
	private void insertTableORIG_DOC_CHECK_LIST(PLDocumentCheckListDataM docCheckListM, String appRecId)throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_DOC_CHECK_LIST ");
			sql.append("( DOC_CHECK_LIST_ID, DOCUMENT_CODE, APPLICATION_RECORD_ID, JOB_STATE, APPLICANT_TYPE");
			sql.append(", DOC_TYPE_ID, DOC_TYPE_DESC, REQUIRED, RECEIVE, CREATE_BY");
			sql.append(", CREATE_DATE, ROLE, REMARK, WAIVE, REQUEST_DOC ,UPDATE_BY,UPDATE_DATE)");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,?  ,SYSDATE,?,?,?,?,?,SYSDATE) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, docCheckListM.getDocCheckListId());
			ps.setString(index++, docCheckListM.getDocCode());
			ps.setString(index++, appRecId);
			ps.setString(index++, docCheckListM.getDocTypeDesc());
			ps.setString(index++, docCheckListM.getApplicationType());			
			ps.setString(index++, docCheckListM.getDocTypeId());
			ps.setString(index++, docCheckListM.getDocTypeDesc());
			ps.setString(index++, docCheckListM.getRequired());
			ps.setString(index++, docCheckListM.getReceive());
			ps.setString(index++, docCheckListM.getCreateBy());			
			ps.setString(index++, docCheckListM.getRole());
			ps.setString(index++, docCheckListM.getRemark());
			ps.setString(index++, docCheckListM.getWaive());
			ps.setString(index++, docCheckListM.getRequestDoc());
			ps.setString(index++, docCheckListM.getUpdateBy());
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	
	private void deleteTableORIG_DOC_CHECK_LIST(Vector<String> docIDVect, String appRecId)throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_DOC_CHECK_LIST ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND DOC_CHECK_LIST_ID NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(docIDVect));
			sql.append(" )");			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, appRecId);
			PreparedStatementParameter(index, ps, docIDVect);
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public Vector<PLDocumentCheckListDataM> LoadOrigDocumentCheckList(String appRecId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLDocumentCheckListDataM> docVect = new Vector<PLDocumentCheckListDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DOC_CHECK_LIST_ID, DOCUMENT_CODE, JOB_STATE, APPLICANT_TYPE, DOC_TYPE_ID ");
			sql.append(" ,DOC_TYPE_DESC, REQUIRED, RECEIVE, CREATE_BY, CREATE_DATE");
			sql.append(" ,ROLE, REMARK, WAIVE, REQUEST_DOC ");
			sql.append(" FROM ORIG_DOC_CHECK_LIST WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			while (rs.next()) {
				int index = 1;
				PLDocumentCheckListDataM docChkListM = new PLDocumentCheckListDataM();		
				
				docChkListM.setDocCheckListId(rs.getString(index++));
				docChkListM.setDocCode(rs.getString(index++));
				docChkListM.setJobState(rs.getString(index++));
				docChkListM.setApplicationType(rs.getString(index++));
				docChkListM.setDocTypeId(rs.getString(index++));				
				docChkListM.setDocTypeDesc(rs.getString(index++));
				docChkListM.setRequired(rs.getString(index++));
				docChkListM.setReceive(rs.getString(index++));
				docChkListM.setCreateBy(rs.getString(index++));
				docChkListM.setCreateDate(rs.getTimestamp(index++));				
				docChkListM.setRole(rs.getString(index++));
				docChkListM.setRemark(rs.getString(index++));
				docChkListM.setWaive(rs.getString(index++));
				docChkListM.setRequestDoc(rs.getString(index++));
				
				docChkListM.setDocCkReasonVect(this.LoadTableORIG_DOC_CHECK_LIST_REASON(docChkListM.getDocCheckListId(),docChkListM.getDocCode()));
				
				docVect.add(docChkListM);
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return docVect;
	}
	
	private Vector<PLDocumentCheckListReasonDataM> LoadTableORIG_DOC_CHECK_LIST_REASON(String docChkListID ,String docCode) throws PLOrigApplicationException{
		try{
			PLOrigDocumentCheckListReasonDAO docCheckListReasonDAO = PLORIGDAOFactory.getPLOrigDocumentCheckListReasonDAO();
			return docCheckListReasonDAO.LoadOrigDocumentCheckListReason(docChkListID , docCode);		
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void saveUpdateTableORIG_DOC_CHECK_LIST_REASON(Vector<PLDocumentCheckListReasonDataM> docCheckListReasonDataVect, String docChkListID) throws PLOrigApplicationException{
		try{
			PLOrigDocumentCheckListReasonDAO docChkReasonDAO = PLORIGDAOFactory.getPLOrigDocumentCheckListReasonDAO();
				docChkReasonDAO.SaveUpdateOrigDocumentCheckListReason(docCheckListReasonDataVect, docChkListID);		
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
		
	}

	@Override
	public Vector<String> loadTrackingDoclistName(String appRecId) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT MCL.TH_DESC FROM ");
			sql.append("ORIG_DOC_CHECK_LIST CL, MS_DOC_LIST MCL ");
			sql.append("WHERE  CL.DOCUMENT_CODE = MCL.DOCUMENT_CODE AND MCL.ACTIVE_STATUS = ? ");  //A
			sql.append("AND CL.APPLICATION_RECORD_ID = ? AND CL.RECEIVE = ? ");  //appRecId, T
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, OrigConstant.ACTIVE_FLAG);
			ps.setString(2, appRecId);
//			ps.setString(3, HTMLRenderUtil.RadioBoxCompare.TrackDoc);

			rs = ps.executeQuery();
			Vector<String> checkDoc = new Vector<String>();
			while (rs.next()) {
				checkDoc.add(rs.getString(1));
			}			
			return checkDoc;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

}
