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
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;

public class OrigPolicyRulesDAOImpl extends OrigObjectDAO implements OrigPolicyRulesDAO {
	public OrigPolicyRulesDAOImpl(String userId) {
		this.userId = userId;
	}

	public OrigPolicyRulesDAOImpl() {

	}

	private String userId = "";
	@Override
	public void createOrigPolicyRulesM(PolicyRulesDataM policyRulesM,
			Connection conn) throws ApplicationException {
		try {
			logger.debug("policyRulesM.getPolicyRulesId() :" + policyRulesM.getPolicyRulesId());
			if (Util.empty(policyRulesM.getPolicyRulesId())) {
				String policyRulesId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_POLICY_RULES_PK,conn);
				policyRulesM.setPolicyRulesId(policyRulesId);
			}
			policyRulesM.setCreateBy(userId);
			policyRulesM.setUpdateBy(userId);
			createTableXRULES_POLICY_RULES(policyRulesM,conn);

			ArrayList<ORPolicyRulesDataM> orPolicyRulesList = policyRulesM.getOrPolicyRules();
			if (!Util.empty(orPolicyRulesList)) {
				OrigORPolicyRulesDAO detailDAO = ORIGDAOFactory.getORPolicyRulesDAO(userId);
				for (ORPolicyRulesDataM orPolicyRuleM : orPolicyRulesList) {
					orPolicyRuleM.setPolicyRulesId(policyRulesM.getPolicyRulesId());
					detailDAO.createOrigORPolicyRulesM(orPolicyRuleM,conn);
				}
			}

			ArrayList<PolicyRulesConditionDataM> policyRulesConditionList = policyRulesM.getPolicyRulesConditions();
			if (!Util.empty(policyRulesConditionList)) {
				OrigPolicyRulesConditionDAO detailDAO = ORIGDAOFactory.getPolicyRulesConditionDAO(userId);
				for (PolicyRulesConditionDataM policyRulesCondition : policyRulesConditionList) {
					policyRulesCondition.setPolicyRulesId(policyRulesM.getPolicyRulesId());
					detailDAO.createOrigPolicyRuleCondition(policyRulesCondition,conn);
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void createOrigPolicyRulesM(PolicyRulesDataM policyRulesM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigPolicyRulesM(policyRulesM, conn);
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

	private void createTableXRULES_POLICY_RULES(PolicyRulesDataM policyRulesM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			// conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_POLICY_RULES ");
			sql.append("( POLICY_RULES_ID, VER_RESULT_ID, RESULT, ");
			sql.append(" POLICY_CODE, VERIFIED_RESULT, REASON, RANK, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, ");
			sql.append(" OVERRIDE_FLAG )");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,  ?,?,?,?, ?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, policyRulesM.getPolicyRulesId());
			ps.setString(cnt++, policyRulesM.getVerResultId());
			ps.setString(cnt++, policyRulesM.getResult());

			ps.setString(cnt++, policyRulesM.getPolicyCode());
			ps.setString(cnt++, policyRulesM.getVerifiedResult());
			ps.setString(cnt++, policyRulesM.getReason());
			ps.setInt(cnt++, policyRulesM.getRank());

			ps.setString(cnt++, policyRulesM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, policyRulesM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());

			ps.setString(cnt++, policyRulesM.getOverrideFlag());

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
	public void deleteOrigPolicyRulesM(String verResultId, String policyRulesId) throws ApplicationException {
		try {
			OrigORPolicyRulesDAO orPolicyDAO = ORIGDAOFactory.getORPolicyRulesDAO();
			OrigPolicyRulesConditionDAO policyConditionDAO = ORIGDAOFactory.getPolicyRulesConditionDAO();
			if (Util.empty(policyRulesId)) {
				ArrayList<PolicyRulesDataM> policyRuleList = loadOrigPolicyRulesM(verResultId);
				if (!Util.empty(policyRuleList)) {
					for (PolicyRulesDataM policyRuleM : policyRuleList) {
						orPolicyDAO.deleteOrigORPolicyRulesM(policyRuleM.getPolicyRulesId(), null);
						policyConditionDAO.deleteOrigPolicyRuleCondition(policyRuleM.getPolicyRulesId(), null);
					}
				}
			} else {
				orPolicyDAO.deleteOrigORPolicyRulesM(policyRulesId, null);
				policyConditionDAO.deleteOrigPolicyRuleCondition(policyRulesId, null);
			}

			deleteTableXRULES_POLICY_RULES(verResultId, policyRulesId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_POLICY_RULES(String verResultId, String policyRulesId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_POLICY_RULES ");
			sql.append(" WHERE VER_RESULT_ID = ? ");
			if (!Util.empty(policyRulesId)) {
				sql.append(" AND POLICY_RULES_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);
			if (!Util.empty(policyRulesId)) {
				ps.setString(2, policyRulesId);
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
	public ArrayList<PolicyRulesDataM> loadOrigPolicyRulesM(String verResultId,
			Connection conn) throws ApplicationException {
		ArrayList<PolicyRulesDataM> result = selectTableXRULES_POLICY_RULES(verResultId,conn);
		if(!Util.empty(result)) {
			OrigORPolicyRulesDAO orPolicyRuleDAO = ORIGDAOFactory.getORPolicyRulesDAO();
			OrigPolicyRulesConditionDAO policyRuleConditionDAO = ORIGDAOFactory.getPolicyRulesConditionDAO();
			for(PolicyRulesDataM policyRuleM: result) {
				ArrayList<ORPolicyRulesDataM> orPolicyRuleList = orPolicyRuleDAO.loadOrigORPolicyRulesM(policyRuleM.getPolicyRulesId(),conn);
				ArrayList<PolicyRulesConditionDataM> policyRuleConditionList = policyRuleConditionDAO.loadOrigPolicyRuleCondition(policyRuleM.getPolicyRulesId(),conn);
				if(!Util.empty(orPolicyRuleList)) {
					policyRuleM.setOrPolicyRules(orPolicyRuleList);
				}
				if(!Util.empty(policyRuleConditionList)){
					policyRuleM.setPolicyRulesConditions(policyRuleConditionList);
				}
			}
		}
		return result;
	}
	@Override
	public ArrayList<PolicyRulesDataM> loadOrigPolicyRulesM(String verResultId)
			throws ApplicationException {
		ArrayList<PolicyRulesDataM> result = selectTableXRULES_POLICY_RULES(verResultId);
		if(!Util.empty(result)) {
			OrigORPolicyRulesDAO orPolicyRuleDAO = ORIGDAOFactory.getORPolicyRulesDAO();
			OrigPolicyRulesConditionDAO policyRuleConditionDAO = ORIGDAOFactory.getPolicyRulesConditionDAO();
			for(PolicyRulesDataM policyRuleM: result) {
				ArrayList<ORPolicyRulesDataM> orPolicyRuleList = orPolicyRuleDAO.loadOrigORPolicyRulesM(policyRuleM.getPolicyRulesId());
				ArrayList<PolicyRulesConditionDataM> policyRuleConditionList = policyRuleConditionDAO.loadOrigPolicyRuleCondition(policyRuleM.getPolicyRulesId());
				if(!Util.empty(orPolicyRuleList)) {
					policyRuleM.setOrPolicyRules(orPolicyRuleList);
				}
				if(!Util.empty(policyRuleConditionList)){
					policyRuleM.setPolicyRulesConditions(policyRuleConditionList);
				}
			}
		}
		return result;
	}
	private ArrayList<PolicyRulesDataM> selectTableXRULES_POLICY_RULES(String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_POLICY_RULES(verResultId,conn);
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
	private ArrayList<PolicyRulesDataM> selectTableXRULES_POLICY_RULES(
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PolicyRulesDataM> policyRulesList = new ArrayList<PolicyRulesDataM>();
		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT POLICY_RULES_ID, VER_RESULT_ID, RESULT, ");
			sql.append(" POLICY_CODE, VERIFIED_RESULT, REASON, RANK, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" OVERRIDE_FLAG ");
			sql.append(" FROM XRULES_POLICY_RULES WHERE VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while (rs.next()) {
				PolicyRulesDataM policyRuleM = new PolicyRulesDataM();
				policyRuleM.setPolicyRulesId(rs.getString("POLICY_RULES_ID"));
				policyRuleM.setVerResultId(rs.getString("VER_RESULT_ID"));
				policyRuleM.setResult(rs.getString("RESULT"));

				policyRuleM.setPolicyCode(rs.getString("POLICY_CODE"));
				policyRuleM.setVerifiedResult(rs.getString("VERIFIED_RESULT"));
				policyRuleM.setReason(rs.getString("REASON"));
				policyRuleM.setRank(rs.getInt("RANK"));

				policyRuleM.setCreateBy(rs.getString("CREATE_BY"));
				policyRuleM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				policyRuleM.setUpdateBy(rs.getString("UPDATE_BY"));
				policyRuleM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));

				policyRuleM.setOverrideFlag(rs.getString("OVERRIDE_FLAG"));

				policyRulesList.add(policyRuleM);
			}

			return policyRulesList;
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
	public void saveUpdateOrigPolicyRulesM(PolicyRulesDataM policyRulesM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigPolicyRulesM(policyRulesM, conn);
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
	public void saveUpdateOrigPolicyRulesM(PolicyRulesDataM policyRulesM,Connection 
			 conn) throws ApplicationException {
		try {
			int returnRows = 0;
			policyRulesM.setUpdateBy(userId);
			returnRows = updateTableXRULES_POLICY_RULES(policyRulesM,conn);
			if (returnRows == 0) {
				policyRulesM.setCreateBy(userId);
				policyRulesM.setUpdateBy(userId);
				createOrigPolicyRulesM(policyRulesM,conn);
			} else {
				ArrayList<ORPolicyRulesDataM> orPolicyRulesList = policyRulesM.getOrPolicyRules();
				ArrayList<PolicyRulesConditionDataM> policyRulesConditionList = policyRulesM.getPolicyRulesConditions();
				if (!Util.empty(orPolicyRulesList)) {
					OrigORPolicyRulesDAO detailDAO = ORIGDAOFactory.getORPolicyRulesDAO(userId);
					for (ORPolicyRulesDataM orPolicyRuleM : orPolicyRulesList) {
						orPolicyRuleM.setPolicyRulesId(policyRulesM.getPolicyRulesId());
						detailDAO.saveUpdateOrigORPolicyRulesM(orPolicyRuleM,conn);
					}
					detailDAO.deleteNotInKeyORPolicyRules(orPolicyRulesList, policyRulesM.getPolicyRulesId(),conn);
				}
				if (!Util.empty(policyRulesConditionList)) {
					OrigPolicyRulesConditionDAO policyRuleConditionDAO = ORIGDAOFactory.getPolicyRulesConditionDAO();
					for (PolicyRulesConditionDataM policyRulesCondition : policyRulesConditionList) {
						policyRulesCondition.setPolicyRulesId(policyRulesM.getPolicyRulesId());
						policyRuleConditionDAO.saveUpdateOrigPolicyRuleCondition(policyRulesCondition,conn);
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableXRULES_POLICY_RULES(PolicyRulesDataM policyRulesM,Connection 
			 conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			// conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_POLICY_RULES ");
			sql.append(" SET RESULT = ?, POLICY_CODE = ?, VERIFIED_RESULT = ?, ");
			sql.append(" REASON = ?, RANK = ?, UPDATE_DATE = ?, UPDATE_BY = ?, ");
			sql.append(" OVERRIDE_FLAG = ? ");
			sql.append(" WHERE VER_RESULT_ID = ? AND POLICY_RULES_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, policyRulesM.getResult());
			ps.setString(cnt++, policyRulesM.getPolicyCode());
			ps.setString(cnt++, policyRulesM.getVerifiedResult());

			ps.setString(cnt++, policyRulesM.getReason());
			ps.setInt(cnt++, policyRulesM.getRank());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, policyRulesM.getUpdateBy());

			ps.setString(cnt++, policyRulesM.getOverrideFlag());

			// WHERE CLAUSE
			ps.setString(cnt++, policyRulesM.getVerResultId());
			ps.setString(cnt++, policyRulesM.getPolicyRulesId());

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
	public void deleteNotInKeyPolicyRules(ArrayList<PolicyRulesDataM> policyRulesList, String verResultId) throws ApplicationException {
		ArrayList<String> notInKeyList = selectNotInKeyTableXRULES_POLICY_RULES(policyRulesList, verResultId);
		if (!Util.empty(notInKeyList)) {
			OrigORPolicyRulesDAO detailDAO = ORIGDAOFactory.getORPolicyRulesDAO();
			OrigPolicyRulesConditionDAO policyRulesConditionDAO = ORIGDAOFactory.getPolicyRulesConditionDAO();
			for (String policyRuleId : notInKeyList) {
				detailDAO.deleteOrigORPolicyRulesM(policyRuleId, null);
				policyRulesConditionDAO.deleteOrigPolicyRuleCondition(policyRuleId, null);
			}
		}
		deleteNotInKeyTableXRULES_POLICY_RULES(policyRulesList, verResultId);
	}

	private ArrayList<String> selectNotInKeyTableXRULES_POLICY_RULES(ArrayList<PolicyRulesDataM> policyRulesList, String verResultId)
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> notInKeyList = new ArrayList<String>();
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT POLICY_RULES_ID ");
			sql.append(" FROM XRULES_POLICY_RULES WHERE VER_RESULT_ID = ? ");
			if (!Util.empty(policyRulesList)) {
				sql.append(" AND POLICY_RULES_ID NOT IN ( ");

				for (PolicyRulesDataM policyRulesM : policyRulesList) {
					sql.append(" '" + policyRulesM.getPolicyRulesId() + "' , ");
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

			while (rs.next()) {
				String notInKeyId = rs.getString("POLICY_RULES_ID");
				notInKeyList.add(notInKeyId);
			}

			return notInKeyList;
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

	private void deleteNotInKeyTableXRULES_POLICY_RULES(ArrayList<PolicyRulesDataM> policyRulesList, String verResultId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();

			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM XRULES_POLICY_RULES ");
			sql.append(" WHERE VER_RESULT_ID = ? ");

			if (!Util.empty(policyRulesList)) {
				sql.append(" AND POLICY_RULES_ID NOT IN ( ");

				for (PolicyRulesDataM policyRuleM : policyRulesList) {
					sql.append(" '" + policyRuleM.getPolicyRulesId() + "' , ");
				}

				if (sql.toString().trim().endsWith(",")) {
					sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
				}

				sql.append(" ) ");
			}

			String dSql = String.valueOf(sql);
			logger.debug("dSql >> " + dSql);

			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);

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
