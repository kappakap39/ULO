package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.app.model.PolicyRulesDataM;

public class InfBatchXrulesPolicyRulesDAOImpl extends InfBatchObjectDAO implements InfBatchXrulesPolicyRulesDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchXrulesPolicyRulesDAOImpl.class);
	@Override
	public ArrayList<PolicyRulesDataM> loadPolicyRules(String verResultId,Connection conn)throws InfBatchException{
		ArrayList<PolicyRulesDataM> policyRules = selectTableXRULES_POLICY_RULES(verResultId,conn);
		return policyRules;
	}
	private ArrayList<PolicyRulesDataM> selectTableXRULES_POLICY_RULES(String verResultId,Connection conn)throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PolicyRulesDataM> policyRulesList = new ArrayList<PolicyRulesDataM>();
		try{
			StringBuffer sql = new StringBuffer("");
				sql.append("SELECT POLICY_RULES_ID, VER_RESULT_ID, RESULT, ");
				sql.append(" POLICY_CODE, VERIFIED_RESULT, REASON, RANK, ");
				sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
				sql.append(" FROM XRULES_POLICY_RULES WHERE VER_RESULT_ID=? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql : " + dSql);
			logger.debug("verResultId : "+verResultId);
			ps = conn.prepareStatement(dSql);
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
				policyRulesList.add(policyRuleM);
			}
			return policyRulesList;
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e);
		}finally{
			try {
				closeConnection(ps,rs);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
}
