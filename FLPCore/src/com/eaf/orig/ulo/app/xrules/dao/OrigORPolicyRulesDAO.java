package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;

public interface OrigORPolicyRulesDAO {
	
	public void createOrigORPolicyRulesM(ORPolicyRulesDataM orPolicyRulesM)throws ApplicationException;
	public void createOrigORPolicyRulesM(ORPolicyRulesDataM orPolicyRulesM,Connection conn)throws ApplicationException;
	public void deleteOrigORPolicyRulesM(String policyRulesId, String orPolicyRulesId)throws ApplicationException;
	public ArrayList<ORPolicyRulesDataM> loadOrigORPolicyRulesM(String policyRulesId)throws ApplicationException;	 
	public ArrayList<ORPolicyRulesDataM> loadOrigORPolicyRulesM(String policyRulesId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigORPolicyRulesM(ORPolicyRulesDataM orPolicyRulesM)throws ApplicationException;
	public void saveUpdateOrigORPolicyRulesM(ORPolicyRulesDataM orPolicyRulesM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyORPolicyRules(ArrayList<ORPolicyRulesDataM> orPolicyRulesList, 
			String policyRulesId) throws ApplicationException;
	public void deleteNotInKeyORPolicyRules(ArrayList<ORPolicyRulesDataM> orPolicyRulesList, 
			String policyRulesId,Connection conn) throws ApplicationException;
}
