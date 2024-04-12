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
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;

public class OrigIdentifyQuestionSetDAOImpl extends OrigObjectDAO implements
		OrigIdentifyQuestionSetDAO {
	public OrigIdentifyQuestionSetDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigIdentifyQuestionSetDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigIdentifyQuestionSetM(
			IdentifyQuestionSetDataM identifyQuestionSetM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigIdentifyQuestionSetM(identifyQuestionSetM, conn);
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
	public void createOrigIdentifyQuestionSetM(
			IdentifyQuestionSetDataM identifyQuestionSetM, Connection conn)
			throws ApplicationException {
		try {
			logger.debug("identifyQuestionSetM.getQuestionSetId() :"+identifyQuestionSetM.getQuestionSetId());
		    if(Util.empty(identifyQuestionSetM.getQuestionSetId())){
				String questionSetId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_IDENTFY_QUESTION_SET_PK,conn); 
				identifyQuestionSetM.setQuestionSetId(questionSetId);
			}
		    identifyQuestionSetM.setCreateBy(userId);
		    identifyQuestionSetM.setUpdateBy(userId);
			createTableXRULES_IDENTIFY_QUESTION_SET(identifyQuestionSetM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void createTableXRULES_IDENTIFY_QUESTION_SET(
			IdentifyQuestionSetDataM identifyQuestionSetM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_IDENTIFY_QUESTION_SET ");
			sql.append("( VER_RESULT_ID, QUESTION_SET_ID, QUESTION_SET_CODE, ");
			sql.append(" QUESTION_SET_TYPE, CALL_TO, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,  ?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, identifyQuestionSetM.getVerResultId());
			ps.setString(cnt++, identifyQuestionSetM.getQuestionSetId());
			ps.setString(cnt++, identifyQuestionSetM.getQuestionSetCode());
			
			ps.setString(cnt++, identifyQuestionSetM.getQuestionSetType());
			ps.setString(cnt++, identifyQuestionSetM.getCallTo());
			
			ps.setString(cnt++, identifyQuestionSetM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, identifyQuestionSetM.getUpdateBy());
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
	public void deleteOrigIdentifyQuestionSetM(String verResultId,
			String questionSetId) throws ApplicationException {
		try {
			deleteTableXRULES_IDENTIFY_QUESTION_SET(verResultId, questionSetId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_IDENTIFY_QUESTION_SET(String verResultId,
			String questionSetId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_IDENTIFY_QUESTION_SET ");
			sql.append(" WHERE VER_RESULT_ID = ? ");
			if(!Util.empty(questionSetId)) {
				sql.append(" AND QUESTION_SET_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);
			if(!Util.empty(questionSetId)) {
				ps.setString(2, questionSetId);
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
	public ArrayList<IdentifyQuestionSetDataM> loadOrigIdentifyQuestionSetM(
			String verResultId, Connection conn) throws ApplicationException {
		return selectTableXRULES_IDENTIFY_QUESTION_SET(verResultId, conn);
	}
	
	@Override
	public ArrayList<IdentifyQuestionSetDataM> loadOrigIdentifyQuestionSetM(
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_IDENTIFY_QUESTION_SET(verResultId,conn);
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

	private ArrayList<IdentifyQuestionSetDataM> selectTableXRULES_IDENTIFY_QUESTION_SET(
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<IdentifyQuestionSetDataM> identifyQuestSetList = new ArrayList<IdentifyQuestionSetDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT VER_RESULT_ID, QUESTION_SET_ID, QUESTION_SET_CODE, ");
			sql.append(" QUESTION_SET_TYPE, CALL_TO, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_IDENTIFY_QUESTION_SET WHERE VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while(rs.next()) {
				IdentifyQuestionSetDataM identifyQuestionSetM = new IdentifyQuestionSetDataM();
				identifyQuestionSetM.setVerResultId(rs.getString("VER_RESULT_ID"));
				identifyQuestionSetM.setQuestionSetId(rs.getString("QUESTION_SET_ID"));
				identifyQuestionSetM.setQuestionSetCode(rs.getString("QUESTION_SET_CODE"));
				
				identifyQuestionSetM.setQuestionSetType(rs.getString("QUESTION_SET_TYPE"));
				identifyQuestionSetM.setCallTo(rs.getString("CALL_TO"));
				
				identifyQuestionSetM.setCreateBy(rs.getString("CREATE_BY"));
				identifyQuestionSetM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				identifyQuestionSetM.setUpdateBy(rs.getString("UPDATE_BY"));
				identifyQuestionSetM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				identifyQuestSetList.add(identifyQuestionSetM);
			}

			return identifyQuestSetList;
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
	public void saveUpdateOrigIdentifyQuestionSetM(
			IdentifyQuestionSetDataM identifyQuestionSetM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigIdentifyQuestionSetM(identifyQuestionSetM, conn);
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
	public void saveUpdateOrigIdentifyQuestionSetM(
			IdentifyQuestionSetDataM identifyQuestionSetM,Connection conn) throws ApplicationException {
		try { 
			int returnRows = 0;
			identifyQuestionSetM.setUpdateBy(userId);
			returnRows = updateTableXRULES_IDENTIFY_QUESTION_SET(identifyQuestionSetM,conn);
			if (returnRows == 0) {
				identifyQuestionSetM.setCreateBy(userId);
				identifyQuestionSetM.setUpdateBy(userId);
				createOrigIdentifyQuestionSetM(identifyQuestionSetM,conn);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_IDENTIFY_QUESTION_SET(
			IdentifyQuestionSetDataM identifyQuestionSetM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_IDENTIFY_QUESTION_SET ");
			sql.append(" SET QUESTION_SET_CODE = ?, QUESTION_SET_TYPE = ?, ");
			sql.append(" CALL_TO = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE VER_RESULT_ID = ? AND QUESTION_SET_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, identifyQuestionSetM.getQuestionSetCode());
			ps.setString(cnt++, identifyQuestionSetM.getQuestionSetType());
			
			ps.setString(cnt++, identifyQuestionSetM.getCallTo());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, identifyQuestionSetM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, identifyQuestionSetM.getVerResultId());
			ps.setString(cnt++, identifyQuestionSetM.getQuestionSetId());
			
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
	public void deleteNotInKeyIdentifyQuestionSet(
			ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList,
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyIdentifyQuestionSet(identifyQuestionSetList, verResultId, conn);
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
	public void deleteNotInKeyIdentifyQuestionSet(
			ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList,
			String verResultId, Connection conn) throws ApplicationException {
		deleteNotInKeyTableXRULES_IDENTIFY_QUESTION_SET(identifyQuestionSetList, verResultId, conn);
	}
	private void deleteNotInKeyTableXRULES_IDENTIFY_QUESTION_SET(
			ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList,
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_IDENTIFY_QUESTION_SET ");
            sql.append(" WHERE VER_RESULT_ID = ? ");
            
            if (!Util.empty(identifyQuestionSetList)) {
                sql.append(" AND QUESTION_SET_ID NOT IN ( ");

                for (IdentifyQuestionSetDataM identifyQuestSetM: identifyQuestionSetList) {
                    sql.append(" '" + identifyQuestSetM.getQuestionSetId() + "' , ");
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
