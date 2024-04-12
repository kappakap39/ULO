package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;

public interface OrigORPolicyRulesDetailDAO {
	
	public void createOrigORPolicyRulesDetailM(ORPolicyRulesDetailDataM orPolicyRulesDtlM)throws ApplicationException;
	public void createOrigORPolicyRulesDetailM(ORPolicyRulesDetailDataM orPolicyRulesDtlM,Connection conn)throws ApplicationException;
	public void deleteOrigORPolicyRulesDetailM(String orPolicyRulesId, String orPolicyRulesDetailId)throws ApplicationException;
	public ArrayList<ORPolicyRulesDetailDataM> loadOrigORPolicyRulesDetailM(String orPolicyRulesId)throws ApplicationException;	
	public ArrayList<ORPolicyRulesDetailDataM> loadOrigORPolicyRulesDetailM(String orPolicyRulesId,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigORPolicyRulesDetailM(ORPolicyRulesDetailDataM orPolicyRulesDtlM)throws ApplicationException;
	public void deleteNotInKeyORPolicyRulesDetail(ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDtlList, 
			String orPolicyRulesId) throws ApplicationException;
	public void deleteNotInKeyORPolicyRulesDetail(ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDtlList, 
			String orPolicyRulesId,Connection conn) throws ApplicationException;
	void saveUpdateOrigORPolicyRulesDetailM(
			ORPolicyRulesDetailDataM orPolicyRulesDtlM, Connection conn)
			throws ApplicationException;
	void deleteOrigORPolicyRulesDetailM(String orPolicyRulesId,
			String orPolicyRulesDetailId, Connection conn)
			throws ApplicationException;
}
