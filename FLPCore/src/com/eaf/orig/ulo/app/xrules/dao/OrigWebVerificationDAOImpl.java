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
import com.eaf.orig.ulo.model.app.WebVerificationDataM;

public class OrigWebVerificationDAOImpl extends OrigObjectDAO implements
		OrigWebVerificationDAO {
	public OrigWebVerificationDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigWebVerificationDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigWebVerificationM(
			WebVerificationDataM webVerificationM, Connection conn)
			throws ApplicationException {
		try {
			logger.debug("webVerificationM.getWebVerifyId() :"+webVerificationM.getWebVerifyId());
		    if(Util.empty(webVerificationM.getWebVerifyId())){
				String webVerifyId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_WEB_VERIFICATION_PK,conn); 
				webVerificationM.setWebVerifyId(webVerifyId);
			}
		    webVerificationM.setCreateBy(userId);
		    webVerificationM.setUpdateBy(userId);
			createTableXRULES_WEB_VERIFICATION(webVerificationM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void createOrigWebVerificationM(WebVerificationDataM webVerificationM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigWebVerificationM(webVerificationM, conn);
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

	private void createTableXRULES_WEB_VERIFICATION(
			WebVerificationDataM webVerificationM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_WEB_VERIFICATION ");
			sql.append("( WEB_VERIFY_ID, VER_RESULT_ID, REMARK, VER_RESULT, WEB_CODE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, webVerificationM.getWebVerifyId());
			ps.setString(cnt++, webVerificationM.getVerResultId());
			ps.setString(cnt++, webVerificationM.getRemark());
			ps.setString(cnt++, webVerificationM.getVerResult());
			ps.setString(cnt++, webVerificationM.getWebCode());
			
			ps.setString(cnt++, webVerificationM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, webVerificationM.getUpdateBy());
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
	public void deleteOrigWebVerificationM(String verResultId,
			String webVerifyId) throws ApplicationException {
		try {
			deleteTableXRULES_WEB_VERIFICATION(verResultId, webVerifyId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_WEB_VERIFICATION(String verResultId,
			String webVerifyId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_WEB_VERIFICATION ");
			sql.append(" WHERE VER_RESULT_ID = ? ");
			if(!Util.empty(webVerifyId)) {
				sql.append(" AND WEB_VERIFY_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);
			if(!Util.empty(webVerifyId)) {
				ps.setString(2, webVerifyId);
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
	public ArrayList<WebVerificationDataM> loadOrigWebVerificationM(
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_WEB_VERIFICATION(verResultId,conn);
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
	public ArrayList<WebVerificationDataM> loadOrigWebVerificationM(
			String verResultId, Connection conn) throws ApplicationException {
		return selectTableXRULES_WEB_VERIFICATION(verResultId, conn);
	}
	
	private ArrayList<WebVerificationDataM> selectTableXRULES_WEB_VERIFICATION(
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<WebVerificationDataM> webVerResultList = new ArrayList<WebVerificationDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT WEB_VERIFY_ID, VER_RESULT_ID, REMARK, VER_RESULT, WEB_CODE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_WEB_VERIFICATION WHERE VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while(rs.next()) {
				WebVerificationDataM webVerResultM = new WebVerificationDataM();
				webVerResultM.setWebVerifyId(rs.getString("WEB_VERIFY_ID"));
				webVerResultM.setVerResultId(rs.getString("VER_RESULT_ID"));
				webVerResultM.setRemark(rs.getString("REMARK"));
				webVerResultM.setVerResult(rs.getString("VER_RESULT"));
				webVerResultM.setWebCode(rs.getString("WEB_CODE"));
				
				webVerResultM.setCreateBy(rs.getString("CREATE_BY"));
				webVerResultM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				webVerResultM.setUpdateBy(rs.getString("UPDATE_BY"));
				webVerResultM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				webVerResultList.add(webVerResultM);
			}

			return webVerResultList;
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
	public void saveUpdateOrigWebVerificationM(
			WebVerificationDataM webVerificationM, Connection conn)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			webVerificationM.setUpdateBy(userId);
			returnRows = updateTableXRULES_WEB_VERIFICATION(webVerificationM,conn);
			if (returnRows == 0) {
				webVerificationM.setCreateBy(userId);
			    webVerificationM.setUpdateBy(userId);
				createOrigWebVerificationM(webVerificationM,conn);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void saveUpdateOrigWebVerificationM(
			WebVerificationDataM webVerificationM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigWebVerificationM(webVerificationM, conn);
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

	private int updateTableXRULES_WEB_VERIFICATION(
			WebVerificationDataM webVerificationM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_WEB_VERIFICATION ");
			sql.append(" SET REMARK = ?, VER_RESULT = ?, WEB_CODE = ?, ");
			sql.append(" UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE VER_RESULT_ID = ? AND WEB_VERIFY_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, webVerificationM.getRemark());
			ps.setString(cnt++, webVerificationM.getVerResult());
			ps.setString(cnt++, webVerificationM.getWebCode());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, webVerificationM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, webVerificationM.getVerResultId());
			ps.setString(cnt++, webVerificationM.getWebVerifyId());
			
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
	public void deleteNotInKeyWebVerification(
			ArrayList<WebVerificationDataM> webVerificationList,
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyWebVerification(webVerificationList, verResultId, conn);
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
	public void deleteNotInKeyWebVerification(
			ArrayList<WebVerificationDataM> webVerificationList,
			String verResultId, Connection conn) throws ApplicationException {
		deleteNotInKeyTableXRULES_WEB_VERIFICATION(webVerificationList, verResultId, conn);
	}
	private void deleteNotInKeyTableXRULES_WEB_VERIFICATION(
			ArrayList<WebVerificationDataM> webVerificationList,
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_WEB_VERIFICATION ");
            sql.append(" WHERE VER_RESULT_ID = ? ");
            
            if (!Util.empty(webVerificationList)) {
                sql.append(" AND WEB_VERIFY_ID NOT IN ( ");

                for (WebVerificationDataM webVerificationM: webVerificationList) {
                    sql.append(" '" + webVerificationM.getWebVerifyId() + "' , ");
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
