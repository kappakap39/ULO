package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;

public interface OrigPolicyRulesConditionDAO {
	public void createOrigPolicyRuleCondition(PolicyRulesConditionDataM policyRulesConditionDataM)throws ApplicationException;
	public void createOrigPolicyRuleCondition(PolicyRulesConditionDataM policyRulesConditionDataM,Connection conn)throws ApplicationException;
	public void deleteOrigPolicyRuleCondition(String policyRulesId, String policyRulesConditionId)throws ApplicationException;
	public ArrayList<PolicyRulesConditionDataM> loadOrigPolicyRuleCondition(String policyRulesId)throws ApplicationException;
	public ArrayList<PolicyRulesConditionDataM> loadOrigPolicyRuleCondition(String policyRulesId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigPolicyRuleCondition(PolicyRulesConditionDataM policyRulesConditionDataM)throws ApplicationException;
	public void deleteNotInKeyPolicyRuleCondition(ArrayList<PolicyRulesConditionDataM> policyRulesConditionDataM, 
			String policyRulesId) throws ApplicationException;
	void saveUpdateOrigPolicyRuleCondition(
			PolicyRulesConditionDataM policyRulesConditionDataM, Connection conn)
			throws ApplicationException;
}
