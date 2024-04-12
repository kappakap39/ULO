package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;

public class PLOrigDocumentCheckListReasonDAOImpl extends OrigObjectDAO implements PLOrigDocumentCheckListReasonDAO {
	
	static Logger log = Logger.getLogger(PLOrigDocumentCheckListReasonDAOImpl.class);

	@Override
	public void SaveUpdateOrigDocumentCheckListReason(Vector<PLDocumentCheckListReasonDataM> docChkReasonVect ,String docChkListID)throws PLOrigApplicationException {
		try{
			if(!OrigUtil.isEmptyVector(docChkReasonVect)){
				Vector<String> docReasonVect = new Vector<String>();
				for(PLDocumentCheckListReasonDataM docChkReasonM : docChkReasonVect){					
					int returnRows = this.updateTableORIG_DOC_CHECK_LIST_REASON(docChkReasonM, docChkListID);
					if(returnRows == 0){
						this.insertTableORIG_DOC_CHECK_LIST_REASON(docChkReasonM, docChkListID);
					}					
					docReasonVect.add(docChkReasonM.getDocReasonID());					
				}
				if(!OrigUtil.isEmptyVector(docReasonVect)){
					this.deleteTableORIG_DOC_CHECK_LIST_REASON(docReasonVect, docChkListID);
				}
			}			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private int updateTableORIG_DOC_CHECK_LIST_REASON(PLDocumentCheckListReasonDataM docChkReasonM, String docChkListID)throws PLOrigApplicationException{
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append(" UPDATE ORIG_DOC_CHECK_LIST_REASON ");
			sql.append(" SET DOC_REASON=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?");
			sql.append(" WHERE DOC_CHECK_LIST_ID = ? AND DOC_REASON=?");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);			
			int index = 1;
			ps.setString(index++, docChkReasonM.getDocReasonID());
			ps.setString(index++, docChkReasonM.getUpdateBy());			
			ps.setString(index++, docChkListID);
			ps.setString(index++, docChkReasonM.getDocReasonID());
			
			returnRows = ps.executeUpdate();
			
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
		return returnRows;
	}
	
	private void insertTableORIG_DOC_CHECK_LIST_REASON(PLDocumentCheckListReasonDataM docChkReasonM, String docChkListID)throws PLOrigApplicationException{
		PreparedStatement ps = null;	
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_DOC_CHECK_LIST_REASON ");
			sql.append("( DOC_CHECK_LIST_ID, DOC_REASON, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY ) ");
			sql.append(" VALUES(?,?,SYSDATE,?,SYSDATE,?) ");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, docChkListID);
			ps.setString(index++, docChkReasonM.getDocReasonID());
			ps.setString(index++, docChkReasonM.getCreateBy());
			ps.setString(index++, docChkReasonM.getUpdateBy());
			
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
	
	private void deleteTableORIG_DOC_CHECK_LIST_REASON(Vector<String> docReasonVect, String docChkListID)throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_DOC_CHECK_LIST_REASON ");
			sql.append(" WHERE DOC_CHECK_LIST_ID = ? AND DOC_REASON NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(docReasonVect));
			sql.append(" )");			
			String dSql = String.valueOf(sql);
//			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, docChkListID);
			PreparedStatementParameter(index, ps, docReasonVect);
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
	public Vector<PLDocumentCheckListReasonDataM> LoadOrigDocumentCheckListReason(String docChkListID , String docCode) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLDocumentCheckListReasonDataM> docChkReasonVect = new Vector<PLDocumentCheckListReasonDataM>();		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DOC_CHECK_LIST_ID, DOC_REASON, CREATE_DATE, CREATE_BY, UPDATE_DATE ,UPDATE_BY ");
			sql.append(" FROM ORIG_DOC_CHECK_LIST_REASON WHERE DOC_CHECK_LIST_ID = ? ");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, docChkListID);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				int index = 1;
				PLDocumentCheckListReasonDataM docChkReasonM = new PLDocumentCheckListReasonDataM();	
				docChkReasonM.setDocCode(docCode);
				docChkReasonM.setDocCheckListId(rs.getString(index++));
				docChkReasonM.setDocReasonID(rs.getString(index++));
				docChkReasonM.setCreateDate(rs.getTimestamp(index++));
				docChkReasonM.setCreateBy(rs.getString(index++));
				docChkReasonM.setUpdateDate(rs.getTimestamp(index++));				
				docChkReasonM.setUpdateBy(rs.getString(index++));
				docChkReasonVect.add(docChkReasonM);
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
		return docChkReasonVect;
	}

}
