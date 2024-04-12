package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;

public class OrigRequiredDocDAOImpl extends OrigObjectDAO implements
		OrigRequiredDocDAO {
	public OrigRequiredDocDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigRequiredDocDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigRequiredDocM(RequiredDocDataM requiredDocM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigRequiredDocM(requiredDocM, conn);
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
	public void createOrigRequiredDocM(RequiredDocDataM requiredDocM,
			Connection conn) throws ApplicationException {
		try {
			logger.debug("requiredDocM.getRequiredDocId() :"+requiredDocM.getRequiredDocId());
		    if(Util.empty(requiredDocM.getRequiredDocId())){
				String requiredDocId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_REQUIRED_DOC_PK,conn); 
				requiredDocM.setRequiredDocId(requiredDocId);
			}
		    requiredDocM.setCreateBy(userId);
		    requiredDocM.setUpdateBy(userId);
			createTableXRULES_REQUIRED_DOC(requiredDocM,conn);
			
			ArrayList<RequiredDocDetailDataM> requiredDocDtlList = requiredDocM.getRequiredDocDetails();
			if(!Util.empty(requiredDocDtlList)) {
				OrigRequiredDocDetailDAO detailDAO = ORIGDAOFactory.getRequiredDocDetailDAO(userId);
				for(RequiredDocDetailDataM requiredDocDtlM: requiredDocDtlList) {
					requiredDocDtlM.setRequiredDocId(requiredDocM.getRequiredDocId());
					detailDAO.createOrigRequiredDocDetailM(requiredDocDtlM,conn);
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void createTableXRULES_REQUIRED_DOC(RequiredDocDataM requiredDocM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_REQUIRED_DOC ");
			sql.append("( REQUIRED_DOC_ID, VER_RESULT_ID, DOCUMENT_GROUP_CODE, SCENARIO_TYPE,DOC_COMPLETED_FLAG, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, requiredDocM.getRequiredDocId());
			ps.setString(cnt++, requiredDocM.getVerResultId());
			ps.setString(cnt++, requiredDocM.getDocumentGroupCode());
			ps.setString(cnt++, requiredDocM.getScenarioType());
			ps.setString(cnt++, requiredDocM.getDocCompletedFlag());
			
			ps.setString(cnt++, requiredDocM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, requiredDocM.getUpdateBy());
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
	public void deleteOrigRequiredDocM(String verResultId, String requiredDocId)
			throws ApplicationException {
		try {
			OrigRequiredDocDetailDAO detailDAO = ORIGDAOFactory.getRequiredDocDetailDAO();
			if(Util.empty(requiredDocId)) {
				ArrayList<RequiredDocDataM> requiredDocList = loadOrigRequiredDocM(verResultId);
				if(!Util.empty(requiredDocList)) {
					for(RequiredDocDataM requiredDocM: requiredDocList) {
						detailDAO.deleteOrigRequiredDocDetailM(requiredDocM.getRequiredDocId(), null);
					}
				}
			} else {
				detailDAO.deleteOrigRequiredDocDetailM(requiredDocId, null);
			}
			
			deleteTableXRULES_REQUIRED_DOC(verResultId, requiredDocId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_REQUIRED_DOC(String verResultId,
			String requiredDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_REQUIRED_DOC ");
			sql.append(" WHERE VER_RESULT_ID = ? ");
			if(!Util.empty(requiredDocId)) {
				sql.append(" AND REQUIRED_DOC_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);
			if(!Util.empty(requiredDocId)) {
				ps.setString(2, requiredDocId);
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
	public ArrayList<RequiredDocDataM> loadOrigRequiredDocM(String verResultId,
			Connection conn) throws ApplicationException {
		ArrayList<RequiredDocDataM> result = selectTableXRULES_REQUIRED_DOC(verResultId,conn);
		if(!Util.empty(result)) {
			OrigRequiredDocDetailDAO detailDAO = ORIGDAOFactory.getRequiredDocDetailDAO();
			for(RequiredDocDataM requiredDocM: result) {
				ArrayList<RequiredDocDetailDataM> detailList = detailDAO.loadOrigRequiredDocDetailM(requiredDocM.getRequiredDocId(),conn);
				if(!Util.empty(detailList)) {
					requiredDocM.setRequiredDocDetails(detailList);				
				}
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<RequiredDocDataM> loadOrigRequiredDocM(String verResultId)
			throws ApplicationException {
		ArrayList<RequiredDocDataM> result = selectTableXRULES_REQUIRED_DOC(verResultId);
		if(!Util.empty(result)) {
			OrigRequiredDocDetailDAO detailDAO = ORIGDAOFactory.getRequiredDocDetailDAO();
			for(RequiredDocDataM requiredDocM: result) {
				ArrayList<RequiredDocDetailDataM> detailList = detailDAO.loadOrigRequiredDocDetailM(requiredDocM.getRequiredDocId());
				if(!Util.empty(detailList)) {
					requiredDocM.setRequiredDocDetails(detailList);				
				}
			}
		}
		return result;
	}
	private ArrayList<RequiredDocDataM> selectTableXRULES_REQUIRED_DOC(
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_REQUIRED_DOC(verResultId,conn);
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
			
	private ArrayList<RequiredDocDataM> selectTableXRULES_REQUIRED_DOC(
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RequiredDocDataM> requiredDocList = new ArrayList<RequiredDocDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT REQUIRED_DOC_ID, VER_RESULT_ID, DOCUMENT_GROUP_CODE, SCENARIO_TYPE,DOC_COMPLETED_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_REQUIRED_DOC WHERE VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while(rs.next()) {
				RequiredDocDataM requiredDocM = new RequiredDocDataM();
				requiredDocM.setRequiredDocId(rs.getString("REQUIRED_DOC_ID"));
				requiredDocM.setVerResultId(rs.getString("VER_RESULT_ID"));
				requiredDocM.setDocumentGroupCode(rs.getString("DOCUMENT_GROUP_CODE"));
				requiredDocM.setScenarioType(rs.getString("SCENARIO_TYPE"));
				requiredDocM.setDocCompletedFlag(rs.getString("DOC_COMPLETED_FLAG"));
				
				requiredDocM.setCreateBy(rs.getString("CREATE_BY"));
				requiredDocM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				requiredDocM.setUpdateBy(rs.getString("UPDATE_BY"));
				requiredDocM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				requiredDocList.add(requiredDocM);
			}

			return requiredDocList;
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
	public void saveUpdateOrigRequiredDocM(RequiredDocDataM requiredDocM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigRequiredDocM(requiredDocM, conn);
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
	public void saveUpdateOrigRequiredDocM(RequiredDocDataM requiredDocM,
			Connection conn) throws ApplicationException {
		try { 
			int returnRows = 0;
			 requiredDocM.setUpdateBy(userId);
			returnRows = updateTableXRULES_REQUIRED_DOC(requiredDocM,conn);
			if (returnRows == 0) {
				requiredDocM.setCreateBy(userId);
			    requiredDocM.setUpdateBy(userId);
				createOrigRequiredDocM(requiredDocM,conn);
			} else {
				OrigRequiredDocDetailDAO detailDAO = ORIGDAOFactory.getRequiredDocDetailDAO(userId);
				ArrayList<RequiredDocDetailDataM> requiredDocDtlList = requiredDocM.getRequiredDocDetails();
				if(!Util.empty(requiredDocDtlList)) {
					for(RequiredDocDetailDataM requiredDocDtlM: requiredDocDtlList) {
						requiredDocDtlM.setRequiredDocId(requiredDocM.getRequiredDocId());
						detailDAO.saveUpdateOrigRequiredDocDetailM(requiredDocDtlM,conn);
					}
				}
				
				detailDAO.deleteNotInKeyRequiredDocDetail(requiredDocDtlList, requiredDocM.getRequiredDocId(),conn);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableXRULES_REQUIRED_DOC(RequiredDocDataM requiredDocM,Connection conn) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_REQUIRED_DOC ");
			sql.append(" SET DOCUMENT_GROUP_CODE = ?, SCENARIO_TYPE = ?, DOC_COMPLETED_FLAG = ? , UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE VER_RESULT_ID = ? AND REQUIRED_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, requiredDocM.getDocumentGroupCode());
			ps.setString(cnt++, requiredDocM.getScenarioType());
			ps.setString(cnt++, requiredDocM.getDocCompletedFlag());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, requiredDocM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, requiredDocM.getVerResultId());
			ps.setString(cnt++, requiredDocM.getRequiredDocId());
			
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
	public void deleteNotInKeyRequiredDoc(
			ArrayList<RequiredDocDataM> requiredDocList, String verResultId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyRequiredDoc(requiredDocList, verResultId, conn);
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
	public void deleteNotInKeyRequiredDoc(
			ArrayList<RequiredDocDataM> requiredDocList, String verResultId,
			Connection conn) throws ApplicationException {
		ArrayList<String> notInKeyList = selectNotInKeyTableXRULES_REQUIRED_DOC(requiredDocList,verResultId,conn);
		if(!Util.empty(notInKeyList)) {
			OrigRequiredDocDetailDAO detailDAO = ORIGDAOFactory.getRequiredDocDetailDAO();
			for(String reqDocId: notInKeyList) {
				detailDAO.deleteOrigRequiredDocDetailM(reqDocId, null,conn);
			}
		}
		
		deleteNotInKeyTableXRULES_REQUIRED_DOC(requiredDocList, verResultId,conn);
	}
	private ArrayList<String> selectNotInKeyTableXRULES_REQUIRED_DOC(
			ArrayList<RequiredDocDataM> requiredDocList, String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Connection conn = null;
		ArrayList<String> notInKeyList = new ArrayList<String>();
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT REQUIRED_DOC_ID ");
			sql.append(" FROM XRULES_REQUIRED_DOC WHERE VER_RESULT_ID = ? ");
			if (!Util.empty(requiredDocList)) {
                sql.append(" AND REQUIRED_DOC_ID NOT IN ( ");

                for (RequiredDocDataM requiredDocM: requiredDocList) {
                    sql.append(" '" + requiredDocM.getRequiredDocId() + "' , ");
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
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while(rs.next()) {
				String notInKeyId = rs.getString("REQUIRED_DOC_ID");
				notInKeyList.add(notInKeyId);
			}

			return notInKeyList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, rs, ps);
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private void deleteNotInKeyTableXRULES_REQUIRED_DOC(
			ArrayList<RequiredDocDataM> requiredDocList, String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_REQUIRED_DOC ");
            sql.append(" WHERE VER_RESULT_ID = ? ");
            
            if (!Util.empty(requiredDocList)) {
                sql.append(" AND REQUIRED_DOC_ID NOT IN ( ");

                for (RequiredDocDataM requiredDocM: requiredDocList) {
                    sql.append(" '" + requiredDocM.getRequiredDocId() + "' , ");
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
