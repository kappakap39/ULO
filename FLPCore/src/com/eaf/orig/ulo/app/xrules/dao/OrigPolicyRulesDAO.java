package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;

public interface OrigPolicyRulesDAO {
	
	public void createOrigPolicyRulesM(PolicyRulesDataM policyRulesM)throws ApplicationException;
	public void createOrigPolicyRulesM(PolicyRulesDataM policyRulesM,Connection conn)throws ApplicationException;
	public void deleteOrigPolicyRulesM(String verResultId, String policyRulesId)throws ApplicationException;
	public ArrayList<PolicyRulesDataM> loadOrigPolicyRulesM(String verResultId)throws ApplicationException;	
	public ArrayList<PolicyRulesDataM> loadOrigPolicyRulesM(String verResultId,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigPolicyRulesM(PolicyRulesDataM policyRulesM)throws ApplicationException;
	public void saveUpdateOrigPolicyRulesM(PolicyRulesDataM policyRulesM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyPolicyRules(ArrayList<PolicyRulesDataM> policyRulesList, 
			String verResultId) throws ApplicationException;
}
