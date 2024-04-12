package com.eaf.orig.ulo.app.dao;

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
import com.eaf.orig.ulo.model.app.BScoreDataM;
import com.eaf.orig.ulo.model.ncb.NcbNameDataM;

public class OrigBScoreDAOImpl extends OrigObjectDAO implements OrigBScoreDAO {
	public OrigBScoreDAOImpl(String userId) {
		this.userId = userId;
	}

	public OrigBScoreDAOImpl() {
		
	}

	private String userId = "";

	@Override
	public void createOrigBScoreM(BScoreDataM bScoreM) throws ApplicationException {
		try {
			// Get Primary Key
			logger.debug("bScoreM.getBscoreId() :" + bScoreM.getBscoreId());
			if (Util.empty(bScoreM.getBscoreId())) {
				String bScoreId = GenerateUnique.generate(OrigConstant.SeqNames.BSCORE_ID_PK);
				bScoreM.setBscoreId(bScoreId);
				bScoreM.setCreateBy(userId);
			}
			bScoreM.setUpdateBy(userId);
			createTableORIG_B_SCORE(bScoreM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_B_SCORE(BScoreDataM bScoreM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_BSCORE ");
			sql.append("( BSCORE_ID,PERSONAL_ID,LPM_ID,POST_DATE,PRODUCT_TYPE, ");
			sql.append("  RISK_GRADE,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bScoreM.getBscoreId());
			ps.setString(cnt++, bScoreM.getPersonalId());
			ps.setString(cnt++, bScoreM.getLpmId());
			ps.setDate(cnt++, bScoreM.getPostDate());
			ps.setString(cnt++, bScoreM.getProductType());
			ps.setString(cnt++, bScoreM.getRiskGrade());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bScoreM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, bScoreM.getUpdateBy());

			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigBScoreM(String personalId, String bScoreId) throws ApplicationException {
		deleteTableORIG_B_SCORE(personalId, bScoreId);
	}

	private void deleteTableORIG_B_SCORE(String personalId, String bScoreId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_BSCORE ");
			sql.append(" WHERE PERSONAL_ID = ?");
			if (!Util.empty(bScoreId)) {
				sql.append(" AND BSCORE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if (!Util.empty(bScoreId)) {
				ps.setString(2, bScoreId);
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
	public ArrayList<BScoreDataM> loadOrigBScoreM(String personalID,Connection conn) throws ApplicationException {
		ArrayList<BScoreDataM> result = selectTableORIG_B_SCORE(personalID,conn);
		return result;
	}

	private ArrayList<BScoreDataM> selectTableORIG_B_SCORE(String personalID,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BScoreDataM> bScoreList = new ArrayList<BScoreDataM>();
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT BSCORE_ID,PERSONAL_ID,LPM_ID,POST_DATE,PRODUCT_TYPE,RISK_GRADE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_BSCORE WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while (rs.next()) {
				BScoreDataM bScoreM = new BScoreDataM();
				bScoreM.setBscoreId(rs.getString("BSCORE_ID"));
				bScoreM.setPersonalId(rs.getString("PERSONAL_ID"));
				bScoreM.setLpmId(rs.getString("LPM_ID"));
				bScoreM.setPostDate(rs.getDate("POST_DATE"));
				bScoreM.setProductType(rs.getString("PRODUCT_TYPE"));
				bScoreM.setRiskGrade(rs.getString("RISK_GRADE"));
				bScoreM.setCreateBy(rs.getString("CREATE_BY"));
				bScoreM.setUpdateBy(rs.getString("UPDATE_BY"));
				bScoreM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				bScoreM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				bScoreList.add(bScoreM);
			}

			return bScoreList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public ArrayList<BScoreDataM> loadOrigBScoreM(String personalID) throws ApplicationException {
		ArrayList<BScoreDataM> result = selectTableORIG_B_SCORE(personalID);
		return result;
	}

	private ArrayList<BScoreDataM> selectTableORIG_B_SCORE(String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<BScoreDataM> bScoreList = new ArrayList<BScoreDataM>();
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT BSCORE_ID,PERSONAL_ID,LPM_ID,POST_DATE,PRODUCT_TYPE,RISK_GRADE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_BSCORE WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while (rs.next()) {
				BScoreDataM bScoreM = new BScoreDataM();
				bScoreM.setBscoreId(rs.getString("BSCORE_ID"));
				bScoreM.setPersonalId(rs.getString("PERSONAL_ID"));
				bScoreM.setLpmId(rs.getString("LPM_ID"));
				bScoreM.setPostDate(rs.getDate("POST_DATE"));
				bScoreM.setProductType(rs.getString("PRODUCT_TYPE"));
				bScoreM.setRiskGrade(rs.getString("RISK_GRADE"));
				bScoreM.setCreateBy(rs.getString("CREATE_BY"));
				bScoreM.setUpdateBy(rs.getString("UPDATE_BY"));
				bScoreM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				bScoreM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				bScoreList.add(bScoreM);
			}

			return bScoreList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void saveUpdateOrigBScoreM(BScoreDataM bScoreM) throws ApplicationException {
		int returnRows = 0;
		bScoreM.setUpdateBy(userId);
		returnRows = updateTableORIG_B_SCORE(bScoreM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_B_SCORE then call Insert method");
			bScoreM.setCreateBy(userId);
			createOrigBScoreM(bScoreM);
		}
	}

	private int updateTableORIG_B_SCORE(BScoreDataM bScoreM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" UPDATE ORIG_BSCORE ");
			sql.append(" SET PRODUCT_TYPE = ?, LPM_ID = ?, POST_DATE = ?,RISK_GRADE = ?, UPDATE_BY = ?,UPDATE_DATE = ? ");
			sql.append(" WHERE BSCORE_ID = ? AND PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, bScoreM.getProductType());
			ps.setString(cnt++, bScoreM.getLpmId());
			ps.setDate(cnt++, bScoreM.getPostDate());
			ps.setString(cnt++, bScoreM.getRiskGrade());
			ps.setString(cnt++, bScoreM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, bScoreM.getBscoreId());
			ps.setString(cnt++, bScoreM.getPersonalId());

			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void deleteNotInKeyBScore(ArrayList<BScoreDataM> bScoreList, String personalID) throws ApplicationException {
		deleteNotInKeyORIG_B_SCORE(bScoreList, personalID);
	}

	private void deleteNotInKeyORIG_B_SCORE(ArrayList<BScoreDataM> bScoreList, String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();

			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_BSCORE ");
			sql.append(" WHERE PERSONAL_ID = ? ");

			if (bScoreList != null && !bScoreList.isEmpty()) {
				sql.append(" AND BSCORE_ID NOT IN ( ");
				for (BScoreDataM bScoreM : bScoreList) {
					logger.debug("bScoreM.getBscoreId() = " + bScoreM.getBscoreId());
					sql.append(" '" + bScoreM.getBscoreId() + "', ");
				}

				if (sql.toString().trim().endsWith(",")) {
					sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
				}

				sql.append(" ) ");
			}

			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			logger.error("ERROR >> ", e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ", e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	
}
