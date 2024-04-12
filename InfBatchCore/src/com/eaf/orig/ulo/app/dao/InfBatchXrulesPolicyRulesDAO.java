package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.app.model.PolicyRulesDataM;

public interface InfBatchXrulesPolicyRulesDAO{
	public ArrayList<PolicyRulesDataM> loadPolicyRules(String verResultId,Connection conn)throws InfBatchException;
}
