package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbAccountReportRuleDataM;

public interface OrigNCBAccountReportRuleDAO {
	
	public void createOrigNcbAccountReportRuleM(NcbAccountReportRuleDataM ncbAccountReportRuleM)throws ApplicationException;
	public void deleteOrigNcbAccountReportRuleM(String accountReportId, String ruleId)throws ApplicationException;	
	public ArrayList<NcbAccountReportRuleDataM> loadOrigNcbAccountReportRuleM(String accountReportId)throws ApplicationException;
	public ArrayList<NcbAccountReportRuleDataM> loadOrigNcbAccountReportRuleM(String accountReportId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigNcbAccountReportRuleM(NcbAccountReportRuleDataM ncbAccountReportRuleM)throws ApplicationException;
	public void deleteNotInKeyNcbAccountReportRule(ArrayList<NcbAccountReportRuleDataM> ncbAccountReportRuleList, String accountReportId)
			throws ApplicationException;
}
