package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;

public class OrigApplicationImageDAOImpl extends OrigObjectDAO implements OrigApplicationImageDAO{
	private static transient Logger logger = Logger.getLogger(OrigApplicationImageDAOImpl.class);	
	public OrigApplicationImageDAOImpl(){
		
	}
	public OrigApplicationImageDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigApplicationImageDAOImpl(String userId,String transactionId){
		this.userId = userId;
		this.transactionId = transactionId;
	}
	private String userId = "";
	private String transactionId = "";
	@Override
	public void createOrigApplicationImageM(ApplicationImageDataM applicationImageDataM,Connection conn)  throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("applicationImageDataM.getAppImgId() :"+applicationImageDataM.getAppImgId());
		    if(Util.empty(applicationImageDataM.getAppImgId())){
				String appImgId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_IMG_PK,conn);
				applicationImageDataM.setAppImgId(appImgId);
			    applicationImageDataM.setCreateBy(userId);
		    }
		    applicationImageDataM.setUpdateBy(userId);
			createTableORIG_APPLICATION_IMG(applicationImageDataM,conn);
			
			ArrayList<ApplicationImageSplitDataM> imageSplitList = applicationImageDataM.getApplicationImageSplits();
			if(imageSplitList != null && !imageSplitList.isEmpty()) {
				OrigApplicationImageSplitDAO splitDAO = ORIGDAOFactory.getApplicationImageSplitDAO(userId);
				for(ApplicationImageSplitDataM splitM: imageSplitList) {
					splitM.setAppImgId(applicationImageDataM.getAppImgId());
					splitDAO.createOrigApplicationImageSplitDataM(splitM,conn);
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void createOrigApplicationImageM(ApplicationImageDataM applicationImageDataM)  throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigApplicationImageM(applicationImageDataM, conn);
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

	private void createTableORIG_APPLICATION_IMG(ApplicationImageDataM applicationImageDataM,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_APPLICATION_IMG ");
			sql.append("( APP_IMG_ID,REF_NO,PATH,REAL_PATH,FILE_NAME,FILE_TYPE, ");
			sql.append(" ACTIVE_STATUS,USER_ID,ACTIVITY_TYPE,APPLICATION_GROUP_ID, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,?,  ?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, applicationImageDataM.getAppImgId());
			ps.setString(cnt++, applicationImageDataM.getRefNo());
			ps.setString(cnt++, applicationImageDataM.getPath());
			ps.setString(cnt++, applicationImageDataM.getRealPath());
			ps.setString(cnt++, applicationImageDataM.getFileName());
			ps.setString(cnt++, applicationImageDataM.getFileType());
			
			ps.setString(cnt++, applicationImageDataM.getActiveStatus());
			ps.setString(cnt++, applicationImageDataM.getUserId());
			ps.setString(cnt++, applicationImageDataM.getActivityType());
			ps.setString(cnt++, applicationImageDataM.getApplicationGroupId());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationImageDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationImageDataM.getUpdateBy());
			
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
	public void deleteOrigApplicationImageM(String applicationGroupId, String appImgId) 
			throws ApplicationException {
		OrigApplicationImageSplitDAO splitDAO = ORIGDAOFactory.getApplicationImageSplitDAO();
		if(appImgId == null || appImgId.isEmpty()) {
			ArrayList<ApplicationImageDataM> appImageList = loadOrigApplicationImageM(applicationGroupId);
			if(appImageList != null && !appImageList.isEmpty()) {
				for(ApplicationImageDataM appImageM: appImageList) {
					splitDAO.deleteOrigApplicationImageSplitDataM(appImageM.getAppImgId(), null);
				}
			}
		} else {
			splitDAO.deleteOrigApplicationImageSplitDataM(appImgId, null);
		}
		deleteTableORIG_APPLICATION_IMG(applicationGroupId, appImgId);
	}

	private void deleteTableORIG_APPLICATION_IMG(String applicationGroupId, String appImgId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_APPLICATION_IMG ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(appImgId != null && !appImgId.isEmpty()) {
				sql.append(" AND APP_IMG_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(appImgId != null && !appImgId.isEmpty()) {
				ps.setString(2, appImgId);
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
	public ArrayList<ApplicationImageDataM> loadOrigApplicationImageM(String applicationGroupId)throws ApplicationException {
		ArrayList<ApplicationImageDataM> result = selectTableORIG_APPLICATION_IMG(applicationGroupId);
		if(!Util.empty(result)) {
			OrigApplicationImageSplitDAO splitDAO = ORIGDAOFactory.getApplicationImageSplitDAO();
			for(ApplicationImageDataM imageM : result) {
				ArrayList<ApplicationImageSplitDataM> splitList = splitDAO.loadOrigApplicationImageM(imageM.getAppImgId());
				if(splitList != null && !splitList.isEmpty()) {
					imageM.setApplicationImageSplits(splitList);
				}
			}
		}
		return result;
	}
	@Override
	public ArrayList<ApplicationImageDataM> loadOrigApplicationImageM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		ArrayList<ApplicationImageDataM> result = selectTableORIG_APPLICATION_IMG(applicationGroupId,conn);
		if(!Util.empty(result)) {
			OrigApplicationImageSplitDAO splitDAO = ORIGDAOFactory.getApplicationImageSplitDAO();
			for(ApplicationImageDataM imageM : result) {
				ArrayList<ApplicationImageSplitDataM> splitList = splitDAO.loadOrigApplicationImageM(imageM.getAppImgId(),conn);
				if(splitList != null && !splitList.isEmpty()) {
					imageM.setApplicationImageSplits(splitList);
				}
			}
		}
		return result;
	}
	private ArrayList<ApplicationImageDataM> selectTableORIG_APPLICATION_IMG(String applicationGroupId) 
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_APPLICATION_IMG(applicationGroupId,conn);
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
	private ArrayList<ApplicationImageDataM> selectTableORIG_APPLICATION_IMG(String applicationGroupId,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationImageDataM> appList = new ArrayList<ApplicationImageDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APP_IMG_ID,REF_NO,PATH,REAL_PATH,FILE_NAME,FILE_TYPE, ");
			sql.append(" ACTIVE_STATUS,USER_ID,ACTIVITY_TYPE,APPLICATION_GROUP_ID, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM ORIG_APPLICATION_IMG  WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ApplicationImageDataM applicationImageDataM = new ApplicationImageDataM();
				applicationImageDataM.setAppImgId(rs.getString("APP_IMG_ID"));
				applicationImageDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				applicationImageDataM.setRefNo(rs.getString("REF_NO"));
				applicationImageDataM.setPath(rs.getString("PATH"));
				applicationImageDataM.setRealPath(rs.getString("REAL_PATH"));
				applicationImageDataM.setFileName(rs.getString("FILE_NAME"));
				applicationImageDataM.setFileType(rs.getString("FILE_TYPE"));
				applicationImageDataM.setActiveStatus(rs.getString("ACTIVE_STATUS"));
				applicationImageDataM.setUserId(rs.getString("USER_ID"));
				applicationImageDataM.setActivityType(rs.getString("ACTIVITY_TYPE"));
				applicationImageDataM.setCreateBy(rs.getString("CREATE_BY"));
				applicationImageDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				applicationImageDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				applicationImageDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				appList.add(applicationImageDataM);
			}

			return appList;
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
	public void saveUpdateOrigApplicationImageM(
			ApplicationImageDataM applicationImageDataM, Connection conn)
			throws ApplicationException {
		int returnRows = 0;
		applicationImageDataM.setUpdateBy(userId);
		returnRows = updateTableORIG_APPLICATION_IMG(applicationImageDataM,conn);
		if (returnRows == 0) {
			applicationImageDataM.setCreateBy(userId);
			createOrigApplicationImageM(applicationImageDataM,conn);
		} else {
		
			ArrayList<ApplicationImageSplitDataM> imageSplitList = applicationImageDataM.getApplicationImageSplits();
			OrigApplicationImageSplitDAO splitDAO = ORIGDAOFactory.getApplicationImageSplitDAO(userId);
			if(imageSplitList != null && !imageSplitList.isEmpty()) {
				for(ApplicationImageSplitDataM splitM: imageSplitList) {
					splitM.setAppImgId(applicationImageDataM.getAppImgId());
					splitDAO.saveUpdateOrigApplicationImageSplitDataM(splitM,conn);
				}
			}
			splitDAO.deleteNotInKeyApplicationImageSplit(imageSplitList, applicationImageDataM.getAppImgId(),conn);
		}
	}
	@Override
	public void saveUpdateOrigApplicationImageM(ApplicationImageDataM applicationImageDataM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigApplicationImageM(applicationImageDataM, conn);
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

	private int updateTableORIG_APPLICATION_IMG(ApplicationImageDataM applicationImageDataM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION_IMG ");
			sql.append(" SET REF_NO = ?,PATH = ?,REAL_PATH = ?,FILE_NAME = ?,FILE_TYPE = ?, ");
			sql.append(" ACTIVE_STATUS = ?,USER_ID = ?,ACTIVITY_TYPE = ?,APPLICATION_GROUP_ID = ? ");
			sql.append(" ,UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE APP_IMG_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, applicationImageDataM.getRefNo());
			ps.setString(cnt++, applicationImageDataM.getPath());
			ps.setString(cnt++, applicationImageDataM.getRealPath());
			ps.setString(cnt++, applicationImageDataM.getFileName());
			ps.setString(cnt++, applicationImageDataM.getFileType());
			
			ps.setString(cnt++, applicationImageDataM.getActiveStatus());			
			ps.setString(cnt++, applicationImageDataM.getUserId());			
			ps.setString(cnt++, applicationImageDataM.getActivityType());			
			ps.setString(cnt++, applicationImageDataM.getApplicationGroupId());			
			
			ps.setString(cnt++, applicationImageDataM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, applicationImageDataM.getAppImgId());
			
			
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
	public void deleteNotInKeyApplicationImage(
			ArrayList<ApplicationImageDataM> appImageList,
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		ArrayList<String> extraList = loadNotInKeyApplicationImageM(appImageList,applicationGroupId,conn);
		if(!Util.empty(extraList)) {
			OrigApplicationImageSplitDAO splitDAO = ORIGDAOFactory.getApplicationImageSplitDAO();
			for(String idNotInKey: extraList) {
				splitDAO.deleteOrigApplicationImageSplitDataM(idNotInKey,null,conn);
			}
		}
		deleteNotInKeyTableORIG_APPLICATION_IMG(appImageList, applicationGroupId,conn);
	}
	@Override
	public void deleteNotInKeyApplicationImage(	ArrayList<ApplicationImageDataM> appImageList,
			String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyApplicationImage(appImageList, applicationGroupId, conn);
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

	private ArrayList<String> loadNotInKeyApplicationImageM(
			ArrayList<ApplicationImageDataM> appImageList,String applicationGroupId,Connection conn) 
					throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Connection conn = null;
		ArrayList<String> appList = new ArrayList<String>();
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APP_IMG_ID ");
			sql.append(" FROM ORIG_APPLICATION_IMG  WHERE APPLICATION_GROUP_ID = ? ");
			if (appImageList != null && !appImageList.isEmpty()) {
                sql.append(" AND APP_IMG_ID NOT IN ( ");
                for (ApplicationImageDataM imgDataM: appImageList) {
                	logger.debug("imgDataM.getAppImgId() = "+imgDataM.getAppImgId());
                    sql.append(" '" + imgDataM.getAppImgId() + "', ");
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
				appList.add(rs.getString("APP_IMG_ID"));
			}

			return appList;
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

	private void deleteNotInKeyTableORIG_APPLICATION_IMG(ArrayList<ApplicationImageDataM> appImageList,
			String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_APPLICATION_IMG ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (appImageList != null && !appImageList.isEmpty()) {
                sql.append(" AND APP_IMG_ID NOT IN ( ");
                for (ApplicationImageDataM imgDataM: appImageList) {
                	logger.debug("imgDataM.getAppImgId() = "+imgDataM.getAppImgId());
                    sql.append(" '" + imgDataM.getAppImgId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
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
