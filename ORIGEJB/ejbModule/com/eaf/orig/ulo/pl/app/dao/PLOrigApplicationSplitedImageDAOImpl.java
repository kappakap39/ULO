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
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigApplicationSplitedImageDAOImpl extends OrigObjectDAO implements PLOrigApplicationSplitedImageDAO {
	
	private static Logger log = Logger.getLogger(PLOrigApplicationSplitedImageDAOImpl.class);

	@Override
	public void saveUpdateApplicationImageSplit(Vector<PLApplicationImageSplitDataM> appImageSplitVect ,String appImgID) throws PLOrigApplicationException {
		try {			
			if(!OrigUtil.isEmptyVector(appImageSplitVect)){
//				Vector<String> imgIDVect = new Vector<String>();
				for(PLApplicationImageSplitDataM appImageSplitM : appImageSplitVect){					
					appImageSplitM.setAppImgID(appImgID);					
					if(OrigUtil.isEmptyString(appImageSplitM.getImgID())){
						appImageSplitM.setImgID(PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.IMG_ID));
					}					
					int returnRows = this.updateTableOrig_Application_Image_Split(appImageSplitM);	
					if(returnRows == 0){						
						this.insertTableOrig_Application_Image_Split(appImageSplitM);
					}					
//					imgIDVect.add(appImageSplitM.getImgID());					
				}
//				if(!OrigUtil.isEmptyVector(imgIDVect)){
//					log.debug("Delete ORIG_APPLICATION_IMAGE_SPLIT appImgID >> "+appImgID+" ImgID >> "+imgIDVect.toString());
//					DeleteTableORIG_APPLICATION_IMAGE_SPLIT(imgIDVect, appImgID);
//				}
			}			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	public void DeleteTableORIG_APPLICATION_IMAGE_SPLIT(Vector<String> imgIDVect, String appImgID)throws PLOrigApplicationException{	
		PreparedStatement ps = null;
		Connection connT = null;
		try{
			connT = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_APPLICATION_IMG_SPLIT ");
			sql.append(" WHERE APP_IMG_ID = ? AND IMG_ID NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(imgIDVect));			
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql= "+dSql);
			int index = 1;
			ps = connT.prepareStatement(dSql);
			ps.setString(index++, appImgID);			
			PreparedStatementParameter(index, ps, imgIDVect);		
			ps.executeUpdate();

		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(connT, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}
	
	private int updateTableOrig_Application_Image_Split(PLApplicationImageSplitDataM appImageSplitM) throws PLOrigApplicationException{
		Connection conn = null;
		int returnRows = 0;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_APPLICATION_IMG_SPLIT ");
			sql.append(" SET IMG_ID=?, APP_IMG_ID=?, PATH=?, REAL_PATH=?, FILE_NAME=? ");
			sql.append(", FILE_TYPE=?, DOCUMENT_CATEGORY=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			sql.append(" WHERE IMG_ID=? AND APP_IMG_ID=?");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appImageSplitM.getImgID());
			ps.setString(2, appImageSplitM.getAppImgID());
			ps.setString(3, appImageSplitM.getPath());
			ps.setString(4, appImageSplitM.getRealPath());
			ps.setString(5, appImageSplitM.getFileName());

			ps.setString(6, appImageSplitM.getFileType());
			ps.setString(7, appImageSplitM.getDocumentCategory());
			ps.setString(8, appImageSplitM.getUpdateBy());
			
			ps.setString(9, appImageSplitM.getImgID());
			ps.setString(10, appImageSplitM.getAppImgID());

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
	
	private void insertTableOrig_Application_Image_Split(PLApplicationImageSplitDataM appImageSplitM)throws PLOrigApplicationException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_APPLICATION_IMG_SPLIT ");
			sql.append("( IMG_ID, APP_IMG_ID, PATH, REAL_PATH, FILE_NAME ");
			sql.append(", FILE_TYPE, DOCUMENT_CATEGORY, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY )");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,SYSDATE,?,SYSDATE  ,? ) ");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appImageSplitM.getImgID());
			ps.setString(2, appImageSplitM.getAppImgID());
			ps.setString(3, appImageSplitM.getPath());
			ps.setString(4, appImageSplitM.getRealPath());
			ps.setString(5, appImageSplitM.getFileName());
			
			ps.setString(6, appImageSplitM.getFileType());
			ps.setString(7, appImageSplitM.getDocumentCategory());
			ps.setString(8, appImageSplitM.getCreateBy());
			
			ps.setString(9, appImageSplitM.getUpdateBy());
			
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
	
	private void deleteTableOrig_Application_Image_Split(Vector<String> appImgId, String appRecId)throws PLOrigApplicationException{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_APPLICATION_IMG_SPLIT ");
			sql.append(" WHERE APP_IMG_ID = ? AND IMG_ID NOT IN ");
			sql.append("( ");
			if (appImgId != null && appImgId.size() > 0) {
				for (int i=0; i<appImgId.size(); i++){
					sql.append("?,");
				}
				sql.deleteCharAt((sql.length()-1));
				
			}else{
				sql.append(" ? ");
			}
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
//			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);
			if (appImgId != null && appImgId.size() > 0) {
				for (int i=0; i<appImgId.size(); i++){					
					ps.setString((i+2), appImgId.get(i));
				}
			}else{
				ps.setString(2, null);
			}
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
	public Vector<PLApplicationImageSplitDataM> loadOrigApplicationImageSplit(String appImgId) throws PLOrigApplicationException {		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		Vector<PLApplicationImageSplitDataM> appImageSplitVect = new Vector<PLApplicationImageSplitDataM>();	
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT IMG_ID, APP_IMG_ID, PATH, REAL_PATH, FILE_NAME ");
			sql.append(", FILE_TYPE, DOCUMENT_CATEGORY, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY ");
			sql.append(" FROM ORIG_APPLICATION_IMG_SPLIT WHERE APP_IMG_ID = ? ORDER BY IMG_ID ASC ");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appImgId);

			rs = ps.executeQuery();
						
			while (rs.next()) {
				PLApplicationImageSplitDataM appImageSplit = new PLApplicationImageSplitDataM();				
				appImageSplit.setImgID(rs.getString(1));
				appImageSplit.setAppImgID(rs.getString(2));
				appImageSplit.setPath(rs.getString(3));
				appImageSplit.setRealPath(rs.getString(4));
				appImageSplit.setFileName(rs.getString(5));
				
				appImageSplit.setFileType(rs.getString(6));
				appImageSplit.setDocumentCategory(rs.getString(7));
				appImageSplit.setCreateDate(rs.getTimestamp(8));
				appImageSplit.setCreateBy(rs.getString(9));
				appImageSplit.setUpdateDate(rs.getTimestamp(10));
				
				appImageSplit.setUpdateBy(rs.getString(11));
				
				appImageSplitVect.add(appImageSplit);
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
		return appImageSplitVect;
	}

	@Override
	public Vector<PLApplicationImageSplitDataM> loadImageSplitFromAppRecId(String appRecId) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT AIS.IMG_ID, AIS.APP_IMG_ID, AIS.PATH, AIS.REAL_PATH, AIS.FILE_NAME, ");
			sql.append("AIS.FILE_TYPE, AIS.DOCUMENT_CATEGORY, AIS.CREATE_DATE, AIS.CREATE_BY, AIS.UPDATE_DATE, ");
			sql.append("AIS.UPDATE_BY ");
			sql.append("FROM ORIG_APPLICATION_IMG_SPLIT AIS, ORIG_APPLICATION_IMG AI ");
			sql.append("WHERE AI.APPLICATION_RECORD_ID = ? AND AIS.APP_IMG_ID = AI.APP_IMG_ID ORDER BY AIS.IMG_ID ");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			
			Vector<PLApplicationImageSplitDataM> appImageSplitVect = new Vector<PLApplicationImageSplitDataM>();
			
			while (rs.next()) {
				PLApplicationImageSplitDataM appImageSplit = new PLApplicationImageSplitDataM();
				
				appImageSplit.setImgID(rs.getString(1));
				appImageSplit.setAppImgID(rs.getString(2));
				appImageSplit.setPath(rs.getString(3));
				appImageSplit.setRealPath(rs.getString(4));
				appImageSplit.setFileName(rs.getString(5));
				
				appImageSplit.setFileType(rs.getString(6));
				appImageSplit.setDocumentCategory(rs.getString(7));
				appImageSplit.setCreateDate(rs.getTimestamp(8));
				appImageSplit.setCreateBy(rs.getString(9));
				appImageSplit.setUpdateDate(rs.getTimestamp(10));
				
				appImageSplit.setUpdateBy(rs.getString(11));
				
				appImageSplitVect.add(appImageSplit);
			}
			
			return appImageSplitVect;
			
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
