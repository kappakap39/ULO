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
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;

public class OrigRequiredDocDetailDAOImpl extends OrigObjectDAO implements
		OrigRequiredDocDetailDAO {
	public OrigRequiredDocDetailDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigRequiredDocDetailDAOImpl(){
		
	}	
	private String userId = "";
	@Override
	public void createOrigRequiredDocDetailM(
			RequiredDocDetailDataM requiredDocDtlM, Connection conn)
			throws ApplicationException {
		try {
			logger.debug("requiredDocDtlM.getRequiredDocDetailId() :"+requiredDocDtlM.getRequiredDocDetailId());
		    if(Util.empty(requiredDocDtlM.getRequiredDocDetailId())){
				String requiredDocDetailId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_REQUIRED_DOC_DETAIL_PK,conn); 
				requiredDocDtlM.setRequiredDocDetailId(requiredDocDetailId);
			}
		    requiredDocDtlM.setCreateBy(userId);
		    requiredDocDtlM.setUpdateBy(userId);
			createTableXRULES_REQUIRED_DOC_DETAIL(requiredDocDtlM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void createOrigRequiredDocDetailM(
			RequiredDocDetailDataM requiredDocDtlM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigRequiredDocDetailM(requiredDocDtlM, conn);
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

	private void createTableXRULES_REQUIRED_DOC_DETAIL(
			RequiredDocDetailDataM requiredDocDtlM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_REQUIRED_DOC_DETAIL ");
			sql.append("( REQUIRED_DOC_DETAIL_ID, REQUIRED_DOC_ID, DOCUMENT_CODE, ");
			sql.append(" MANDATORY_FLAG, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, requiredDocDtlM.getRequiredDocDetailId());
			ps.setString(cnt++, requiredDocDtlM.getRequiredDocId());
			ps.setString(cnt++, requiredDocDtlM.getDocumentCode());
			
			ps.setString(cnt++, requiredDocDtlM.getMandatoryFlag());
			ps.setString(cnt++, requiredDocDtlM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, requiredDocDtlM.getUpdateBy());
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
	public void deleteOrigRequiredDocDetailM(String requiredDocId,
			String requiredDocDetailId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteOrigRequiredDocDetailM(requiredDocId, requiredDocDetailId, conn);
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
	public void deleteOrigRequiredDocDetailM(String requiredDocId,
			String requiredDocDetailId, Connection conn)
			throws ApplicationException {
		try {
			deleteTableXRULES_REQUIRED_DOC_DETAIL(requiredDocId, requiredDocDetailId,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void deleteTableXRULES_REQUIRED_DOC_DETAIL(String requiredDocId,
			String requiredDocDetailId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_REQUIRED_DOC_DETAIL ");
			sql.append(" WHERE REQUIRED_DOC_ID = ? ");
			if(!Util.empty(requiredDocDetailId)) {
				sql.append(" AND REQUIRED_DOC_DETAIL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, requiredDocId);
			if(!Util.empty(requiredDocDetailId)) {
				ps.setString(2, requiredDocDetailId);
			}
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
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<RequiredDocDetailDataM> loadOrigRequiredDocDetailM(
			String requiredDocId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_REQUIRED_DOC_DETAIL(requiredDocId,conn);
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
	public ArrayList<RequiredDocDetailDataM> loadOrigRequiredDocDetailM(
			String requiredDocId, Connection conn) throws ApplicationException {
		return selectTableXRULES_REQUIRED_DOC_DETAIL(requiredDocId, conn);
	}
	
	private ArrayList<RequiredDocDetailDataM> selectTableXRULES_REQUIRED_DOC_DETAIL(String requiredDocId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RequiredDocDetailDataM> requiredDocDtlList = new ArrayList<RequiredDocDetailDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT REQUIRED_DOC_DETAIL_ID, REQUIRED_DOC_ID, DOCUMENT_CODE, ");
			sql.append(" MANDATORY_FLAG, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_REQUIRED_DOC_DETAIL WHERE REQUIRED_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, requiredDocId);

			rs = ps.executeQuery();

			while(rs.next()) {
				RequiredDocDetailDataM requiredDocDtlM = new RequiredDocDetailDataM();
				requiredDocDtlM.setRequiredDocDetailId(rs.getString("REQUIRED_DOC_DETAIL_ID"));
				requiredDocDtlM.setRequiredDocId(rs.getString("REQUIRED_DOC_ID"));
				requiredDocDtlM.setDocumentCode(rs.getString("DOCUMENT_CODE"));
				
				requiredDocDtlM.setMandatoryFlag(rs.getString("MANDATORY_FLAG"));
				requiredDocDtlM.setCreateBy(rs.getString("CREATE_BY"));
				requiredDocDtlM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				requiredDocDtlM.setUpdateBy(rs.getString("UPDATE_BY"));
				requiredDocDtlM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				requiredDocDtlList.add(requiredDocDtlM);
			}

			return requiredDocDtlList;
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
	public void saveUpdateOrigRequiredDocDetailM(
			RequiredDocDetailDataM requiredDocDtlM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigRequiredDocDetailM(requiredDocDtlM, conn);
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
	public void saveUpdateOrigRequiredDocDetailM(
			RequiredDocDetailDataM requiredDocDtlM, Connection conn)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			requiredDocDtlM.setUpdateBy(userId);
			returnRows = updateTableXRULES_REQUIRED_DOC_DETAIL(requiredDocDtlM,conn);
			if (returnRows == 0) {
				requiredDocDtlM.setCreateBy(userId);
			    requiredDocDtlM.setUpdateBy(userId);
				createOrigRequiredDocDetailM(requiredDocDtlM,conn);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableXRULES_REQUIRED_DOC_DETAIL(
			RequiredDocDetailDataM requiredDocDtlM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_REQUIRED_DOC_DETAIL ");
			sql.append(" SET DOCUMENT_CODE = ?, MANDATORY_FLAG = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE REQUIRED_DOC_ID = ? AND REQUIRED_DOC_DETAIL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, requiredDocDtlM.getDocumentCode());
			ps.setString(cnt++, requiredDocDtlM.getMandatoryFlag());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, requiredDocDtlM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, requiredDocDtlM.getRequiredDocId());
			ps.setString(cnt++, requiredDocDtlM.getRequiredDocDetailId());
			
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
	public void deleteNotInKeyRequiredDocDetail(
			ArrayList<RequiredDocDetailDataM> requiredDocDtlList,
			String requiredDocId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyRequiredDocDetail(requiredDocDtlList, requiredDocId, conn);
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
	public void deleteNotInKeyRequiredDocDetail(
			ArrayList<RequiredDocDetailDataM> requiredDocDtlList,
			String requiredDocId, Connection conn) throws ApplicationException {
		deleteNotInKeyTableXRULES_REQUIRED_DOC_DETAIL(requiredDocDtlList, requiredDocId, conn);
	}
	private void deleteNotInKeyTableXRULES_REQUIRED_DOC_DETAIL(
			ArrayList<RequiredDocDetailDataM> requiredDocDtlList, String requiredDocId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_REQUIRED_DOC_DETAIL ");
            sql.append(" WHERE REQUIRED_DOC_ID = ? ");
            
            if (!Util.empty(requiredDocDtlList)) {
                sql.append(" AND REQUIRED_DOC_DETAIL_ID NOT IN ( ");

                for (RequiredDocDetailDataM requiredDocDtlM: requiredDocDtlList) {
                    sql.append(" '" + requiredDocDtlM.getRequiredDocDetailId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, requiredDocId);
            
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
