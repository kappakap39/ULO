package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigNCBDocumentDAOImpl extends OrigObjectDAO implements PLOrigNCBDocumentDAO{
	
	private static Logger log = Logger.getLogger(PLOrigNCBDocumentDAOImpl.class);

	@Override
	public void updateSaveNCBDocument(Vector<PLNCBDocDataM> ncbDocVect, String personalID)throws PLOrigApplicationException {
		try{			
			if (!OrigUtil.isEmptyVector(ncbDocVect)){				
				Vector<String> docFileIDVect = new Vector<String>();
				for(PLNCBDocDataM ncbDocM : ncbDocVect){					
					
					if(OrigUtil.isEmptyString(ncbDocM.getImgID())){
						String ncbImgId = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.NCB_IMG_ID);
						ncbDocM.setImgID(ncbImgId);
					}
					
					int returnRow = updateTableOrig_NCB_Document(ncbDocM, ncbDocM.getImgID(), personalID);					
					if(returnRow == 0){
						insertTableOrig_NCB_Document(ncbDocM, personalID);
					}	
					
					docFileIDVect.add(ncbDocM.getImgID());
					
				}
				if(!OrigUtil.isEmptyVector(docFileIDVect)){
					deleteTableOrig_NCB_Document(docFileIDVect,personalID);
				}
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void insertTableOrig_NCB_Document(PLNCBDocDataM ncbDocM, String personalID) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_NCB_DOCUMENT ");
			sql.append("( PERSONAL_ID, DOCUMENT_CODE, IMG_ID, CREATE_DATE, CREATE_BY ");
			sql.append(", UPDATE_DATE, UPDATE_BY )");
			sql.append(" VALUES(?,?,?,SYSDATE,?  ,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);
			ps.setString(2, ncbDocM.getDocCode());
			ps.setString(3, ncbDocM.getImgID());
			ps.setString(4, ncbDocM.getCreateBy());
			ps.setString(5, ncbDocM.getUpdateBy());
			
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
	
	public void deleteORIG_NCB_DOCUMENT(String personalID) throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append("DELETE ORIG_NCB_DOCUMENT WHERE PERSONAL_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, personalID);
			ps.executeUpdate();
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}
	
	private void deleteTableOrig_NCB_Document(Vector<String> imgId, String personalId) throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_NCB_DOCUMENT ");
			sql.append(" WHERE PERSONAL_ID = ? AND IMG_ID NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(imgId));
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, personalId);
			PreparedStatementParameter(index, ps, imgId);
			ps.executeUpdate();

		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}

	private int updateTableOrig_NCB_Document(PLNCBDocDataM ncbDocM, String imgId, String personalID) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_NCB_DOCUMENT ");
			sql.append(" SET PERSONAL_ID=?, DOCUMENT_CODE=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?  ");
			sql.append(" WHERE PERSONAL_ID=? AND IMG_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);
			ps.setString(2, ncbDocM.getDocCode());
			ps.setString(3, ncbDocM.getUpdateBy());
			
			ps.setString(4, personalID);
			ps.setString(5, imgId);
			
			return ps.executeUpdate();
			
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
	public Vector<PLNCBDocDataM> loadNCBDocument(String personalId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLNCBDocDataM> ncbDocVect = new Vector<PLNCBDocDataM>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DOCUMENT_CODE, IMG_ID, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY ");
			sql.append(" FROM ORIG_NCB_DOCUMENT WHERE PERSONAL_ID = ? ORDER BY IMG_ID ASC ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				PLNCBDocDataM ncbDocM = new PLNCBDocDataM();
				
				ncbDocM.setDocCode(rs.getString(1));
				ncbDocM.setImgID(rs.getString(2));
				ncbDocM.setCreateDate(rs.getTimestamp(3));
				ncbDocM.setCreateBy(rs.getString(4));
				
				ncbDocM.setUpdateDate(rs.getTimestamp(5));
				ncbDocM.setUpdateBy(rs.getString(6));
				ncbDocM.setPersonalID(personalId);
				
				ncbDocVect.add(ncbDocM);
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
		return ncbDocVect;
	}

}
