package com.eaf.inf.batch.ulo.inf.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.ias.shared.model.RoleM;
import com.eaf.ias.shared.model.UserM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;



public interface InfDAO {
	public String getInfExport(String MODULE_ID) throws Exception;
	public String getInfExport(String MODULE_ID,Connection con) throws Exception;
	public String getCardLinkAccountSetUp(String USER, String OUTPUT_NAME) throws Exception;
	public String getCardLinkCashDay1(String USER, String OUTPUT_NAME) throws Exception;
	public String getCardLinkCustomerOccupation(String USER, String OUTPUT_NAME) throws Exception;
	public String getSalePerformance(String USER, String OUTPUT_NAME) throws Exception;
	public String getSalePerformance(String USER, String OUTPUT_NAME,Connection con) throws Exception;
	public String getKmAlertService(String USER, String OUTPUT_NAME) throws Exception;
	public String getTCB(String USER, String OUTPUT_NAME) throws Exception;
	public String getAFPPrintRejectNonNCB(String USER, String OUTPUT_NAME) throws Exception;
	public String getAFPPrintRejectNCB(String USER, String OUTPUT_NAME) throws Exception;
	public String getAFPPrintApprove(String USER, String OUTPUT_NAME) throws Exception;
	public String getPDFRejectLetter(String USER, String OUTPUT_NAME) throws Exception;
	public String getKVICloseApplication(String USER, String OUTPUT_NAME) throws Exception;
	public String getIMGenerateConsent(String USER, String OUTPUT_NAME) throws Exception;
	public String getIMNotifyCompletedApplication(String USER, String OUTPUT_NAME) throws Exception;
	public String getLotusNoteGetDSA(String USER, String OUTPUT_NAME) throws Exception;
	public void insertInfBatchLog(InfBatchLogDataM infBatchLog,Connection conn) throws InfBatchException;
	public void insertInfBatchLog(InfBatchLogDataM infBatchLog);
	public String getInfExportByDate(String MODULE_ID)throws Exception;
	public ArrayList<String> getGenerateCancelCardLinkRefNoApplicationGroupId()throws Exception;
	public ArrayList<String> getGenerateCancelCardLinkRefNoApplicationGroupId(Connection con)throws Exception;
	public List<com.eaf.orig.ulo.model.app.ApplicationGroupDataM> loadApplicationVlink()throws Exception;
	public void updateCardlinkRefNo(ApplicationDataM application,Connection conn)throws Exception;
	public List<UserM> loadTableImpUser()throws Exception;
	public RoleM getROLEByUserName(String userName)throws Exception;
	public UserM loadTableUsers(String userName)throws Exception;
	public void deleteTableIMP_USER(UserM prmUserM)throws Exception;
	public InfBatchLogDataM getFullFraudInfBatchLogByRefId(String refId, int lifeCycle) throws Exception;
	public String getSetupLoan(String USER, String OUTPUT_NAME) throws Exception;
	public InfBatchLogDataM getSetupLoanInfBatchLogByRefId(String refId) throws Exception;
	public ArrayList<String> getInfExportKMobile(String MODULE_ID)throws Exception;
	public String getCardLinkAccountSetUp(Connection conn, String USER, String OUTPUT_NAME) throws Exception;
	public String getInfExportMLP(String MODULE_ID) throws Exception;
	public String getCardLinkAccountSetUpCJD(String USER, String applicationGroupId) throws Exception;
	public String getCardLinkAccountSetUpCJD(Connection conn, String USER, String applicationGroupId) throws Exception;
}
