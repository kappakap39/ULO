package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigApplicationImageDAOImpl extends OrigObjectDAO implements	PLOrigApplicationImageDAO {
	
	private static transient Logger log = Logger.getLogger(PLOrigApplicationImageDAOImpl.class);

	@Override
	public void saveUpdateOrigApplicationImage(Vector<PLApplicationImageDataM> appImageVect, String appRecId) throws PLOrigApplicationException {
		try {
			if(!OrigUtil.isEmptyVector(appImageVect)){
				for(PLApplicationImageDataM appImageM : appImageVect){
					if(OrigUtil.isEmptyString(appImageM.getAppImgID())){
						appImageM.setAppImgID(PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.APP_IMG_ID));
					}
					int returnRows = this.updateTableOrig_Application_Image(appImageM, appRecId);
					if(returnRows == 0){						
						this.insertTableOrig_Application_Image(appImageM, appRecId);
					}	
					this.saveUpdateTableOrig_Application_Image_Split(appImageM.getAppImageSplitVect(),appImageM.getAppImgID());
				}
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	public void DeleteTableORIG_APPLICATION_IMAGE(Vector<String> imgIDVect, String appRecId)throws PLOrigApplicationException{	
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_APPLICATION_IMG ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND APP_IMG_ID NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(imgIDVect));			
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql= "+dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, appRecId);			
			PreparedStatementParameter(index, ps, imgIDVect);		
			ps.executeUpdate();

		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}
	
	private int updateTableOrig_Application_Image(PLApplicationImageDataM appImageM, String appRecId)throws PLOrigApplicationException {
		Connection conn = null;
		int returnRows = 0;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append(" UPDATE ORIG_APPLICATION_IMG ");
			sql.append(" SET REF_NO=?, PATH=?, REAL_PATH=?, FILE_NAME=?, FILE_TYPE=? ");
			sql.append(", ACTIVE_STATUS=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			sql.append(", OBJECT_ID = ? , CHRONICLE_ID = ?, USER_ID = ? , ACTIVITY_TYPE = ? , SERVICE_BRANCH_CODE = ? , DOC_VERSION = ?, FILE_SIZE = ? ");
			sql.append(", NO_OF_PAGE_SYSTEM = ? , NO_OF_PAGE_COVERPAGE = ? , MODIFY_DATE = ?, MODIFIER_NAME = ?, APPLICATION_DATE = ? ");
			sql.append("  WHERE APPLICATION_RECORD_ID=? AND APP_IMG_ID =? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
				ps.setString(index++, appImageM.getRefNo());
				ps.setString(index++, appImageM.getPath());
				ps.setString(index++, appImageM.getRealPath());
				ps.setString(index++, appImageM.getFileName());
				ps.setString(index++, appImageM.getFileType());
				ps.setString(index++, appImageM.getActiveStatus());
				ps.setString(index++, appImageM.getUpdateBy());				
				ps.setString(index++, appImageM.getObjectId());
				ps.setString(index++, appImageM.getChronicleId());
				ps.setString(index++, appImageM.getUserId());
				ps.setString(index++, appImageM.getActivityType());
				ps.setString(index++, appImageM.getServiceBranchCode());
				ps.setString(index++, appImageM.getDocVersion());
				ps.setDouble(index++, appImageM.getFileSize());
				ps.setInt(index++, appImageM.getNoOfPageSystem());
				ps.setInt(index++, appImageM.getNoOfPageCoverpage());
				ps.setTimestamp(index++, appImageM.getModifyDate());
				ps.setString(index++, appImageM.getModifierName());
				ps.setTimestamp(index++, appImageM.getAppDate());
				
				ps.setString(index++, appRecId);	
				ps.setString(index++, appImageM.getAppImgID());			
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
	
	private void insertTableOrig_Application_Image(PLApplicationImageDataM appImageM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
//			log.debug("appRecId = "+appRecId+" appImageM.getAppImgID() = "+appImageM.getAppImgID());
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" INSERT INTO ORIG_APPLICATION_IMG ");
			sql.append("( APPLICATION_RECORD_ID, APP_IMG_ID, REF_NO, PATH, REAL_PATH ");
			sql.append(", FILE_NAME, FILE_TYPE, ACTIVE_STATUS, CREATE_DATE, CREATE_BY ");
			sql.append(", UPDATE_DATE, UPDATE_BY ");
			sql.append(", OBJECT_ID, CHRONICLE_ID, USER_ID, ACTIVITY_TYPE, SERVICE_BRANCH_CODE, DOC_VERSION, FILE_SIZE ");
			sql.append(", NO_OF_PAGE_SYSTEM, NO_OF_PAGE_COVERPAGE, MODIFY_DATE, MODIFIER_NAME, APPLICATION_DATE ");
			sql.append(" )");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,SYSDATE,?  ,SYSDATE,? ,?,?,?,?,?,?,?,?,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, appRecId);
			ps.setString(index++, appImageM.getAppImgID());
			ps.setString(index++, appImageM.getRefNo());
			ps.setString(index++, appImageM.getPath());
			ps.setString(index++, appImageM.getRealPath());
			
			ps.setString(index++, appImageM.getFileName());
			ps.setString(index++, appImageM.getFileType());
			ps.setString(index++, appImageM.getActiveStatus());
			ps.setString(index++, appImageM.getCreateBy());
			ps.setString(index++, appImageM.getUpdateBy());
			
			ps.setString(index++, appImageM.getObjectId());
			ps.setString(index++, appImageM.getChronicleId());
			ps.setString(index++, appImageM.getUserId());
			ps.setString(index++, appImageM.getActivityType());
			ps.setString(index++, appImageM.getServiceBranchCode());
			ps.setString(index++, appImageM.getDocVersion());
			ps.setDouble(index++, appImageM.getFileSize());
			ps.setInt(index++, appImageM.getNoOfPageSystem());
			ps.setInt(index++, appImageM.getNoOfPageCoverpage());
			ps.setTimestamp(index++, appImageM.getModifyDate());
			ps.setString(index++, appImageM.getModifierName());
			ps.setTimestamp(index++, appImageM.getAppDate());			
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
	
	private void deleteTableOrig_Application_Image(Vector<String> appImgId, String appRecId)throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_APPLICATION_IMG ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND APP_IMG_ID NOT IN ");
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
			log.debug("Sql="+dSql);
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
	public Vector<PLApplicationImageDataM> loadOrigApplicationImage(String appRecId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLApplicationImageDataM> appImageVect = new Vector<PLApplicationImageDataM>();	
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APP_IMG_ID, REF_NO, PATH, REAL_PATH, FILE_NAME ");
			sql.append(", FILE_TYPE, ACTIVE_STATUS, CREATE_DATE, CREATE_BY ");
			sql.append(", UPDATE_DATE, UPDATE_BY ");
			sql.append(", OBJECT_ID, CHRONICLE_ID, USER_ID, ACTIVITY_TYPE, SERVICE_BRANCH_CODE, DOC_VERSION, FILE_SIZE ");
			sql.append(", NO_OF_PAGE_SYSTEM, NO_OF_PAGE_COVERPAGE, MODIFY_DATE, MODIFIER_NAME, APPLICATION_DATE ");
			sql.append(" FROM ORIG_APPLICATION_IMG WHERE APPLICATION_RECORD_ID = ? ORDER BY CREATE_DATE DESC ");			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);
			rs = ps.executeQuery();					
			while (rs.next()) {
				PLApplicationImageDataM appImageM = new PLApplicationImageDataM();				
				appImageM.setAppImgID(rs.getString("APP_IMG_ID"));
				appImageM.setRefNo(rs.getString("REF_NO"));
				appImageM.setPath(rs.getString("PATH"));
				appImageM.setRealPath(rs.getString("REAL_PATH"));
				appImageM.setFileName(rs.getString("FILE_NAME"));				
				appImageM.setFileType(rs.getString("FILE_TYPE"));
				appImageM.setActiveStatus(rs.getString("ACTIVE_STATUS"));
				appImageM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				appImageM.setCreateBy(rs.getString("CREATE_BY"));
				appImageM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));				
				appImageM.setUpdateBy(rs.getString("UPDATE_BY"));				
				appImageM.setObjectId(rs.getString("OBJECT_ID"));
				appImageM.setChronicleId(rs.getString("CHRONICLE_ID"));
				appImageM.setUserId(rs.getString("USER_ID"));
				appImageM.setActivityType(rs.getString("ACTIVITY_TYPE"));
				appImageM.setServiceBranchCode(rs.getString("SERVICE_BRANCH_CODE"));
				appImageM.setDocVersion(rs.getString("DOC_VERSION"));
				appImageM.setFileSize(rs.getDouble("FILE_SIZE"));
				appImageM.setNoOfPageSystem(rs.getInt("NO_OF_PAGE_SYSTEM"));
				appImageM.setNoOfPageCoverpage(rs.getInt("NO_OF_PAGE_COVERPAGE"));
				appImageM.setModifyDate(rs.getTimestamp("MODIFY_DATE"));
				appImageM.setModifierName(rs.getString("MODIFIER_NAME"));
				appImageM.setAppDate(rs.getTimestamp("APPLICATION_DATE"));
				appImageM.setAppImageSplitVect(this.loadTableOrig_Application_Image_Split(rs.getString(1)));				
				appImageVect.add(appImageM);				
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
		return appImageVect;
	}
	
	private Vector<PLApplicationImageSplitDataM> loadTableOrig_Application_Image_Split(String appImgId) throws PLOrigApplicationException{		
		try{			
			PLOrigApplicationSplitedImageDAO splitedImageDAO = PLORIGDAOFactory.getPLOrigApplicationSplitedImageDAO();
			return splitedImageDAO.loadOrigApplicationImageSplit(appImgId);	
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}
	
	private void saveUpdateTableOrig_Application_Image_Split(Vector<PLApplicationImageSplitDataM> appImgIdVect ,String appImageId) throws PLOrigApplicationException{
		try{			
			PLOrigApplicationSplitedImageDAO splitedImageDAO = PLORIGDAOFactory.getPLOrigApplicationSplitedImageDAO();
				splitedImageDAO.saveUpdateApplicationImageSplit(appImgIdVect,appImageId);		
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}
	
	public void SaveUpdateFollowImage(PLApplicationImageDataM plAppImageM, String appRecId) throws PLOrigApplicationException {
		try{
			if(OrigUtil.isEmptyString(plAppImageM.getAppImgID())){
				plAppImageM.setAppImgID(PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.APP_IMG_ID));
			}
			int returnRows = this.updateTableOrig_Application_Image(plAppImageM, appRecId);
			if(returnRows == 0){
				this.insertTableOrig_Application_Image(plAppImageM, appRecId);
			}				
			this.saveUpdateTableOrig_Application_Image_Split(plAppImageM.getAppImageSplitVect(),plAppImageM.getAppImgID());	
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}

}
