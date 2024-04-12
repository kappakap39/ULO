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
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;

public class OrigIdentifyQuestionDAOImpl extends OrigObjectDAO implements OrigIdentifyQuestionDAO {
	public OrigIdentifyQuestionDAOImpl(String userId) {
		this.userId = userId;
	}

	public OrigIdentifyQuestionDAOImpl() {

	}

	private String userId = "";

	@Override
	public void createOrigIdentifyQuestionM(IdentifyQuestionDataM identifyQuestionM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigIdentifyQuestionM(identifyQuestionM, conn);
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
	public void createOrigIdentifyQuestionM(
			IdentifyQuestionDataM identifyQuestionM, Connection conn)
			throws ApplicationException {
		try {
			logger.debug("identifyQuestionM.getIdentifyQuestionId() :" + identifyQuestionM.getIdentifyQuestionId());
			if (Util.empty(identifyQuestionM.getIdentifyQuestionId())) {
				String identifyQuestionId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_IDENTIFY_QUESTION_PK,conn);
				identifyQuestionM.setIdentifyQuestionId(identifyQuestionId);
			}
			identifyQuestionM.setCreateBy(userId);
			identifyQuestionM.setUpdateBy(userId);
			createTableXRULES_IDENTIFY_QUESTION(identifyQuestionM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	
	private void createTableXRULES_IDENTIFY_QUESTION(IdentifyQuestionDataM identifyQuestionM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			// conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_IDENTIFY_QUESTION ");
			sql.append("( INDENTIFY_QUESTION_ID, VER_RESULT_ID, CUSTOMER_ANSWER, ");
			sql.append(" QUESTION_NO, RESULT, SEQ, QUESTION_SET_TYPE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, ");
			sql.append(" QUESTION_SET_CODE) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,  ?,?,?,?, ? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, identifyQuestionM.getIdentifyQuestionId());
			ps.setString(cnt++, identifyQuestionM.getVerResultId());
			ps.setString(cnt++, identifyQuestionM.getCustomerAnswer());

			ps.setString(cnt++, identifyQuestionM.getQuestionNo());
			ps.setString(cnt++, identifyQuestionM.getResult());
			ps.setInt(cnt++, identifyQuestionM.getSeq());
			ps.setString(cnt++, identifyQuestionM.getQuestionSetType());

			ps.setString(cnt++, identifyQuestionM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, identifyQuestionM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());

			ps.setString(cnt++, identifyQuestionM.getQuestionSetCode());

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
	public void deleteOrigIdentifyQuestionM(String verResultId, String identifyQuestionId) throws ApplicationException {
		try {
			deleteTableXRULES_IDENTIFY_QUESTION(verResultId, identifyQuestionId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_IDENTIFY_QUESTION(String verResultId, String identifyQuestionId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_IDENTIFY_QUESTION ");
			sql.append(" WHERE VER_RESULT_ID = ? ");
			if (!Util.empty(identifyQuestionId)) {
				sql.append(" AND INDENTIFY_QUESTION_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);
			if (!Util.empty(identifyQuestionId)) {
				ps.setString(2, identifyQuestionId);
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
	public ArrayList<IdentifyQuestionDataM> loadOrigIdentifyQuestionM(String verResultId) throws ApplicationException {
		Connection conn = null;
		try {
			conn = getConnection();
			return selectTableXRULES_IDENTIFY_QUESTION(verResultId, conn);
		} catch (Exception e) {
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
	public ArrayList<IdentifyQuestionDataM> loadOrigIdentifyQuestionM(String verResultId, Connection conn) throws ApplicationException {
		return selectTableXRULES_IDENTIFY_QUESTION(verResultId, conn);
	}

	private ArrayList<IdentifyQuestionDataM> selectTableXRULES_IDENTIFY_QUESTION(String verResultId, Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<IdentifyQuestionDataM> identifyQuestList = new ArrayList<IdentifyQuestionDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT INDENTIFY_QUESTION_ID, VER_RESULT_ID, CUSTOMER_ANSWER, ");
			sql.append(" QUESTION_NO, RESULT, SEQ, QUESTION_SET_TYPE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, QUESTION_SET_CODE ");
			sql.append(" FROM XRULES_IDENTIFY_QUESTION WHERE VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while (rs.next()) {
				IdentifyQuestionDataM identifyQuestionM = new IdentifyQuestionDataM();
				identifyQuestionM.setIdentifyQuestionId(rs.getString("INDENTIFY_QUESTION_ID"));
				identifyQuestionM.setVerResultId(rs.getString("VER_RESULT_ID"));
				identifyQuestionM.setCustomerAnswer(rs.getString("CUSTOMER_ANSWER"));

				identifyQuestionM.setQuestionNo(rs.getString("QUESTION_NO"));
				identifyQuestionM.setResult(rs.getString("RESULT"));
				identifyQuestionM.setSeq(rs.getInt("SEQ"));
				identifyQuestionM.setQuestionSetType(rs.getString("QUESTION_SET_TYPE"));

				identifyQuestionM.setCreateBy(rs.getString("CREATE_BY"));
				identifyQuestionM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				identifyQuestionM.setUpdateBy(rs.getString("UPDATE_BY"));
				identifyQuestionM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));

				identifyQuestionM.setQuestionSetCode(rs.getString("QUESTION_SET_CODE"));

				identifyQuestList.add(identifyQuestionM);
			}

			return identifyQuestList;
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
	public void saveUpdateOrigIdentifyQuestionM(
			IdentifyQuestionDataM identifyQuestionM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigIdentifyQuestionM(identifyQuestionM, conn);
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
	public void saveUpdateOrigIdentifyQuestionM(IdentifyQuestionDataM identifyQuestionM,Connection conn) throws ApplicationException {
		try {
			int returnRows = 0;
			identifyQuestionM.setUpdateBy(userId);
			returnRows = updateTableXRULES_IDENTIFY_QUESTION(identifyQuestionM,conn);
			if (returnRows == 0) {
				identifyQuestionM.setCreateBy(userId);
				identifyQuestionM.setUpdateBy(userId);
				createOrigIdentifyQuestionM(identifyQuestionM,conn);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_IDENTIFY_QUESTION(IdentifyQuestionDataM identifyQuestionM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			// conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_IDENTIFY_QUESTION ");
			sql.append(" SET CUSTOMER_ANSWER = ?, QUESTION_NO = ?, RESULT = ?, ");
			sql.append(" SEQ = ?, QUESTION_SET_TYPE = ?, UPDATE_DATE = ?, UPDATE_BY = ?, ");
			sql.append(" QUESTION_SET_CODE = ? ");
			sql.append(" WHERE VER_RESULT_ID = ? AND INDENTIFY_QUESTION_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, identifyQuestionM.getCustomerAnswer());
			ps.setString(cnt++, identifyQuestionM.getQuestionNo());
			ps.setString(cnt++, identifyQuestionM.getResult());
			logger.debug("identifyQuestionM.getResult() >> " + identifyQuestionM.getResult());
			ps.setInt(cnt++, identifyQuestionM.getSeq());
			ps.setString(cnt++, identifyQuestionM.getQuestionSetType());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, identifyQuestionM.getUpdateBy());
			ps.setString(cnt++, identifyQuestionM.getQuestionSetCode());

			// WHERE CLAUSE
			ps.setString(cnt++, identifyQuestionM.getVerResultId());
			ps.setString(cnt++, identifyQuestionM.getIdentifyQuestionId());

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
	public void deleteNotInKeyIdentifyQuestion(ArrayList<IdentifyQuestionDataM> identifyQuestionList, String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyIdentifyQuestion(identifyQuestionList, verResultId, conn);
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
	public void deleteNotInKeyIdentifyQuestion(
			ArrayList<IdentifyQuestionDataM> identifyQuestionList,
			String verResultId, Connection conn) throws ApplicationException {
		deleteNotInKeyTableXRULES_IDENTIFY_QUESTION(identifyQuestionList, verResultId, conn);
	}
	private void deleteNotInKeyTableXRULES_IDENTIFY_QUESTION(ArrayList<IdentifyQuestionDataM> identifyQuestionList, String verResultId,Connection conn)
			throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			// conn = Get Connection
//			conn = getConnection();

			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM XRULES_IDENTIFY_QUESTION ");
			sql.append(" WHERE VER_RESULT_ID = ? ");

			if (!Util.empty(identifyQuestionList)) {
				sql.append(" AND INDENTIFY_QUESTION_ID NOT IN ( ");

				for (IdentifyQuestionDataM identifyQuestM : identifyQuestionList) {
					sql.append(" '" + identifyQuestM.getIdentifyQuestionId() + "' , ");
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
			logger.error("ERROR >> ", e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.error("ERROR >> ", e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
