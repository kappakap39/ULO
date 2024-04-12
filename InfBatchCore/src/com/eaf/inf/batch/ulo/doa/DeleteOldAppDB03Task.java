package com.eaf.inf.batch.ulo.doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.ctoa.ApplicationGroupCatDataM;
import com.eaf.inf.batch.ulo.ctoa.CatalogAndTransformOldAppDAO;
import com.eaf.inf.batch.ulo.ctoa.CatalogAndTransformOldAppFactory;


public class DeleteOldAppDB03Task extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(DeleteOldAppDB03Task.class);
	DeleteOldAppDAO doaDAO = DeleteOldAppFactory.getDeleteOldAppDAO();
	CatalogAndTransformOldAppDAO ctoaDAO = CatalogAndTransformOldAppFactory.getCatalogAndTransformOldAppDAO();
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<>();
		try
		{
			//Load Catalog appGroup with ARC_STATUS = 1
			ArrayList<ApplicationGroupCatDataM> appGroupCatList = DeleteOldAppFactory.getDeleteOldAppDAO().loadOldAppToDelete();
    		
			for(ApplicationGroupCatDataM appGroupCat : appGroupCatList)
			{
				TaskObjectDataM taskObject = new TaskObjectDataM();
				taskObject.setUniqueId(appGroupCat.getApplicationGroupId());
				taskObject.setObject(appGroupCat);
				taskObjects.add(taskObject);
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.info("taskObjects size : "+taskObjects.size());
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		Thread.currentThread().setName("DeleteOldApp" + Thread.currentThread().getId());
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		Connection conn = null;
		ArrayList<String> appGroupIdList = new ArrayList<String>();
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setUniqueId(taskObject.getUniqueId());
			ApplicationGroupCatDataM appGroupCat = (ApplicationGroupCatDataM) taskObject.getObject();
			appGroupIdList.add(appGroupCat.getApplicationGroupId());
			
			//Delete Old BPM Data
			deleteOldBPM(appGroupCat);
			
			//Delete Old IM Data
			//deleteOldIMData(appGroupCat);
			
			//Delete Old ULO Data
			deleteOldULOData(appGroupCat);
			
			logger.debug("DeleteOldAppDB03 " + appGroupCat.getApplicationGroupNo() + " Done.");
			
			taskExecute.setResponseObject(null);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
			try {
				//Update ARC_STATUS = F
				conn = getConnection();
				doaDAO.updateArcStatusFail(conn, e.getMessage(), appGroupIdList);
			} catch (SQLException e1) {
				logger.fatal("ERROR ",e1);
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(e1.getLocalizedMessage());
			}
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(e.getLocalizedMessage());
			}
		}
		return taskExecute;
	}
	
	private void deleteOldBPM(ApplicationGroupCatDataM appGroupCat) 
			throws Exception {
		if(Util.empty(appGroupCat.getPurgeDateBPMDBS()))
		{
			Connection bpmConn = null;
			Connection conn = ThreadLocalConnection.getConn();
			conn.setAutoCommit(false);
			
			ArrayList<String> applicationGroupNoList = new ArrayList<String>();
			applicationGroupNoList.add(appGroupCat.getApplicationGroupNo());
	
			try
			{
				//Get Related ApplicationGroup instantIdList
				ArrayList<Integer> instantIdList = new ArrayList<Integer>();
				ArrayList<String> temp = doaDAO.selectUtil1(conn, "ORIG_APPLICATION_GROUP", "INSTANT_ID", "APPLICATION_GROUP_NO", applicationGroupNoList);
				if(!Util.empty(temp))
				{
					for(String tempStr : temp)
					{
						instantIdList.add(Integer.parseInt(tempStr));
					}
				}
				
				if(instantIdList.size() > 0)
				{
					bpmConn = ThreadLocalConnection.getBpmConn();
					bpmConn.setAutoCommit(false);
					
					//Delete old data from BPM
					doaDAO.call_LSW_BPD_INSTANCE_DELETE(bpmConn, instantIdList);
					bpmConn.commit();
				}
				
				//Update ORIG_APPLICATION_GROUP_CAT.PURGE_DATE_BPMDBS
				doaDAO.updatePurgeDateP(ThreadLocalConnection.getConnPsHm().get("PURGE_DATE_BPMDBS"), applicationGroupNoList);
				
				conn.commit();
				
				logger.debug("Delete OldBPMData " + appGroupCat.getApplicationGroupNo() + " Done.");
			}
			catch(Exception e)
			{
				logger.fatal("ERROR ",e);
				try 
				{
					bpmConn.rollback();
					conn.rollback();
				} 
				catch (SQLException e1) 
				{
					logger.fatal("ERROR",e1);
				}
				throw new InfBatchException(e.getMessage());
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void deleteOldIMData(ApplicationGroupCatDataM appGroupCat) 
			throws Exception {
		
		if(Util.empty(appGroupCat.getPurgeDateIMApp()))
		{
			
			ArrayList<String> appGroupNoListIM = new ArrayList<String>();
			appGroupNoListIM.add(appGroupCat.getApplicationGroupNo());
			//logger.debug("deleteOldIMData - " + appGroupCat.getApplicationGroupNo());
			
			Connection conn = ThreadLocalConnection.getConn();
			conn.setAutoCommit(false);
			
			Connection imConn = ThreadLocalConnection.getImConn();
			imConn.setAutoCommit(false);
			
			try
			{
				//Find TransactionId from IM_APP.SERVICE_REQ_RESP using appGroupNoList - REF_CODE
				ArrayList<String> hashIdList = ctoaDAO.getImageSplitHashId(conn, appGroupCat.getApplicationGroupId());
				ArrayList<String> imTransactionIdList = doaDAO.getIMTransactionIdList(imConn, hashIdList);
				ArrayList<String> fllwReasonIdList = doaDAO.getIMFllwReasonIdList(imConn, imTransactionIdList); //Create index IM_FOLLOW_REASON_INX01
					
				//Delete records from IM table
				doaDAO.deleteUtil1(imConn,"IM_AUTHORITY", "AUTHORITY_ID", null); //No key
				doaDAO.deleteUtil1(imConn,"IM_CUSTOMER", "TRANSACTIONID", imTransactionIdList);
				doaDAO.deleteUtil1(imConn,"IM_FOLLOW_REASON", "TRANSACTIONID", imTransactionIdList); //Create index IM_FOLLOW_REASON_INX01
				doaDAO.deleteUtil1(imConn,"IM_FOLLOW_REASON_DETAIL", "REASON_ID", fllwReasonIdList);
				doaDAO.deleteUtil1(imConn,"IM_IMAGE", "HASHING_ID", hashIdList); //Index Ok
				doaDAO.deleteUtil1(imConn,"IM_JOB", "PARAM3", appGroupNoListIM); //Index Ok
				doaDAO.deleteUtil1(imConn,"IM_JOB_HISTORY", "PARAM3", appGroupNoListIM); //Create Index IM_JOB_HISTORY_INX01
				doaDAO.deleteUtil1(imConn,"IM_TRANSACTION", "TRANSACTIONID", imTransactionIdList); //Create Index IM_FOLLOW_REASON_INX01
				doaDAO.deleteUtil1(imConn,"SERVICE_REQ_RESP", "REF_CODE", appGroupNoListIM); //Create Index SERVICE_REQ_RESP_INX01
			
				//Commit
				imConn.commit();
				
				//Update ORIG_APPLICATION_GROUP_CAT.PURGE_DATE_IM_APP
				doaDAO.updatePurgeDateP(ThreadLocalConnection.getConnPsHm().get("PURGE_DATE_IM_APP"), appGroupNoListIM);
				conn.commit();
					
				logger.debug("Delete OldIMData " + appGroupCat.getApplicationGroupNo() + " Done.");
			}
			catch(Exception e)
			{
				logger.fatal("ERROR ",e);
				try 
				{
					imConn.rollback();
					conn.rollback();
				}
				catch (SQLException e1)
				{
					logger.fatal("ERROR",e1);
				}
				throw new InfBatchException(e.getMessage());
			}
		}
	}
	
	private void deleteOldULOData(ApplicationGroupCatDataM appGroupCat) 
			throws Exception {
		
		if(Util.empty(appGroupCat.getPurgeDateOrigApp()))
		{
			Connection conn = null;
			try
			{
				conn = ThreadLocalConnection.getConn();
				conn.setAutoCommit(false);
				
				HashMap<String, PreparedStatement> psHm = ThreadLocalConnection.getConnPsHm();
				
				ArrayList<String> applicationGroupIdList = new ArrayList<String>();
				ArrayList<String> applicationGroupNoList = new ArrayList<String>();
					
					applicationGroupIdList.add(appGroupCat.getApplicationGroupId());
					applicationGroupNoList.add(appGroupCat.getApplicationGroupNo());
					
					//logger.debug("deleteOldULOData - " + appGroupCat.getApplicationGroupNo());
					ArrayList<String> appImageIdList = doaDAO.selectUtilP(psHm.get("appImageId"), "ORIG_APPLICATION_IMG", "APP_IMG_ID", applicationGroupIdList);
					ArrayList<String> agVerResultIdList = doaDAO.selectUtilP(psHm.get("agVerResultId"), "XRULES_VERIFICATION_RESULT", "VER_RESULT_ID", applicationGroupIdList);
						ArrayList<String> agPolicyRuleIdList = doaDAO.selectUtilP(psHm.get("agPolicyRuleId"), "XRULES_POLICY_RULES", "POLICY_RULES_ID", agVerResultIdList);
						ArrayList<String> personalIdList = doaDAO.selectUtilP(psHm.get("personalId"), "ORIG_PERSONAL_INFO", "PERSONAL_ID", applicationGroupIdList);
							ArrayList<String> incomeIdList = doaDAO.selectUtilP(psHm.get("incomeId"), "INC_INFO", "INCOME_ID", personalIdList);
								ArrayList<String> monthlyTawiIdList = doaDAO.selectUtilP(psHm.get("monthlyTawiId"), "INC_MONTHLY_TAWI", "MONTHLY_TAWI_ID", incomeIdList);
								ArrayList<String> opnEndFundIdList = doaDAO.selectUtilP(psHm.get("opnEndFundId"), "INC_OPN_END_FUND", "OPN_END_FUND_ID", incomeIdList);
								ArrayList<String> payslipIdList = doaDAO.selectUtilP(psHm.get("payslipId"), "INC_PAYSLIP", "PAYSLIP_ID", incomeIdList);
									ArrayList<String> payslipMIdList = doaDAO.selectUtilP(psHm.get("payslipMId"), "INC_PAYSLIP_MONTHLY", "PAYSLIP_MONTHLY_ID", payslipIdList);
								ArrayList<String> bankStmIdList = doaDAO.selectUtilP(psHm.get("bankStmId"), "INC_BANK_STATEMENT", "BANK_STATEMENT_ID", incomeIdList);
								ArrayList<String> savAccIdList = doaDAO.selectUtilP(psHm.get("savAccId"), "INC_SAVING_ACC", "SAVING_ACC_ID", incomeIdList);
							ArrayList<String> pVerResultIdList = doaDAO.selectUtilP(psHm.get("pVerResultId"), "XRULES_VERIFICATION_RESULT", "VER_RESULT_ID", personalIdList);
								ArrayList<String> pPolicyRuleIdList = doaDAO.selectUtilP(psHm.get("pPolicyRuleId"), "XRULES_POLICY_RULES", "POLICY_RULES_ID", pVerResultIdList);
									ArrayList<String> pOrPolicyRulesIdList = doaDAO.selectUtilP(psHm.get("pOrPolicyRulesId"), "XRULES_OR_POLICY_RULES", "OR_POLICY_RULES_ID", pPolicyRuleIdList);
										ArrayList<String> pOrPolicyRulesDetailIdList = doaDAO.selectUtilP(psHm.get("pOrPolicyRulesDetailId"), "XRULES_OR_POLICY_RULES_DETAIL", "OR_POLICY_RULES_DETAIL_ID", pOrPolicyRulesIdList);
								ArrayList<String> requireDocIdList = doaDAO.selectUtilP(psHm.get("requireDocId"), "XRULES_REQUIRED_DOC", "REQUIRED_DOC_ID", pVerResultIdList);
								ArrayList<String> prvlgPrjCodeIdList = doaDAO.selectUtilP(psHm.get("prvlgPrjCodeId"), "XRULES_PRVLG_PRJ_CDE", "PRVLG_PRJ_CDE_ID", pVerResultIdList);
									ArrayList<String> prvlgDocIdList = doaDAO.selectUtilP(psHm.get("prvlgDocId"), "XRULES_PRVLG_DOC", "PRVLG_DOC_ID", prvlgPrjCodeIdList);
							ArrayList<String> ncbTrackingCodeList = doaDAO.selectUtilP(psHm.get("ncbTrackingCode"), "NCB_INFO", "TRACKING_CODE", personalIdList);
								ArrayList<String> ncbAccountReportIdList = doaDAO.selectUtilP(psHm.get("ncbAccountReportId"), "NCB_ACCOUNT_REPORT", "ACCOUNT_REPORT_ID", ncbTrackingCodeList);
						ArrayList<String> applicationRecordIdList = doaDAO.selectUtilP(psHm.get("applicationRecordId"), "ORIG_APPLICATION", "APPLICATION_RECORD_ID", applicationGroupIdList);
						ArrayList<String> cardLinkRefNoList = doaDAO.selectUtilP(psHm.get("cardLinkRefNo"), "ORIG_APPLICATION", "CARDLINK_REF_NO", applicationGroupIdList);
							ArrayList<String> loanIdList = doaDAO.selectUtilP(psHm.get("loanId"), "ORIG_LOAN", "LOAN_ID", applicationRecordIdList);
								ArrayList<String> cardIdList = doaDAO.selectUtilP(psHm.get("cardId"), "ORIG_CARD", "CARD_ID", loanIdList);
							ArrayList<String> appVerResultIdList = doaDAO.selectUtilP(psHm.get("appVerResultId"), "XRULES_VERIFICATION_RESULT", "VER_RESULT_ID", applicationRecordIdList);
								ArrayList<String> appPolicyRuleIdList = doaDAO.selectUtilP(psHm.get("appPolicyRuleId"), "XRULES_POLICY_RULES", "POLICY_RULES_ID", appVerResultIdList);
									ArrayList<String> appOrPolicyRulesIdList = doaDAO.selectUtilP(psHm.get("appOrPolicyRulesId"), "XRULES_OR_POLICY_RULES", "OR_POLICY_RULES_ID", appPolicyRuleIdList);
										ArrayList<String> appOrPolicyRulesDetailIdList = doaDAO.selectUtilP(psHm.get("appOrPolicyRulesDetailId"), "XRULES_OR_POLICY_RULES_DETAIL", "OR_POLICY_RULES_DETAIL_ID", appOrPolicyRulesIdList);
						ArrayList<String> docCheckListIdList = doaDAO.selectUtilP(psHm.get("docCheckListId"), "ORIG_DOC_CHECK_LIST", "DOC_CHECK_LIST_ID", applicationGroupIdList);
						
						//Delete from leaf to root
						
								doaDAO.deleteUtil1_P(psHm,"XRULES_GUIDELINE_DATA", appOrPolicyRulesDetailIdList);
								doaDAO.deleteUtil1_P(psHm,"XRULES_OR_POLICY_RULES_DETAIL", appOrPolicyRulesIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_OR_POLICY_RULES", appPolicyRuleIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_POLICY_RULES_CONDITION", appPolicyRuleIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_POLICY_RULES", appVerResultIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_VERIFICATION_RESULT", appVerResultIdList);
					
						doaDAO.deleteUtil1_P(psHm,"XRULES_HR_VERIFICATION", pVerResultIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_IDENTIFY_QUESTION", pVerResultIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_IDENTIFY_QUESTION_SET", pVerResultIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_PHONE_VERIFICATION", pVerResultIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_WEB_VERIFICATION", pVerResultIdList);
								doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_TRADING_DOC", prvlgDocIdList);
								doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_MGM_DOC", prvlgDocIdList);
								doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_EXCEPTION_DOC", prvlgDocIdList);
								doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_TRANSFER_DOC", prvlgDocIdList);
								doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_KASSET_DOC", prvlgDocIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_PRODUCT_CCA", prvlgPrjCodeIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_PRODUCT_INSURANCE", prvlgPrjCodeIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_PRODUCT_SAVING", prvlgPrjCodeIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_RCCMD_PRJ_CDE", prvlgPrjCodeIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_DOC", prvlgPrjCodeIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_REQUIRED_DOC_DETAIL", requireDocIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_REQUIRED_DOC", pVerResultIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_DOCUMENT_VERIFICATION", pVerResultIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_PRVLG_PRJ_CDE", prvlgPrjCodeIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_GUIDELINE_DATA", pOrPolicyRulesDetailIdList);
							doaDAO.deleteUtil1_P(psHm,"XRULES_OR_POLICY_RULES_DETAIL", pOrPolicyRulesIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_OR_POLICY_RULES", pPolicyRuleIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_POLICY_RULES_CONDITION", pPolicyRuleIdList);
						doaDAO.deleteUtil1_P(psHm,"XRULES_POLICY_RULES", pVerResultIdList);
					doaDAO.deleteUtil1_P(psHm,"XRULES_VERIFICATION_RESULT", pVerResultIdList);
						
						doaDAO.deleteUtil1_P(psHm,"XRULES_POLICY_RULES_CONDITION", agPolicyRuleIdList);	
						doaDAO.deleteUtil1_P(psHm,"XRULES_POLICY_RULES", agVerResultIdList);
					doaDAO.deleteUtil1_P(psHm,"XRULES_VERIFICATION_RESULT", agVerResultIdList);
					
						doaDAO.deleteUtil1_P(psHm,"INC_CLS_END_FUND", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_FIN_INSTRUMENT", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_FIXED_ACC", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_FIXED_GUARANTEE", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_KVI", incomeIdList);
							doaDAO.deleteUtil1_P(psHm,"INC_MONTHLY_TAWI_DETAIL", monthlyTawiIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_MONTHLY_TAWI", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_YEARLY_TAWI", incomeIdList);
							doaDAO.deleteUtil1_P(psHm,"INC_OPN_END_FUND_DETAIL", opnEndFundIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_OPN_END_FUND", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_PAYROLL_FILE", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_PAYROLL", incomeIdList);
								doaDAO.deleteUtil1_P(psHm,"INC_PAYSLIP_MONTHLY_DETAIL", payslipMIdList);
							doaDAO.deleteUtil1_P(psHm,"INC_PAYSLIP_MONTHLY", payslipIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_PAYSLIP", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_PREVIOUS_INCOME", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_SALARY_CERT", incomeIdList);
							doaDAO.deleteUtil1_P(psHm,"INC_BANK_STATEMENT_DETAIL", bankStmIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_BANK_STATEMENT",  incomeIdList);
							doaDAO.deleteUtil1_P(psHm,"INC_SAVING_ACC_DETAIL", savAccIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_SAVING_ACC", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_TAWEESAP", incomeIdList);
						doaDAO.deleteUtil1_P(psHm,"INC_OTH_INCOME", incomeIdList);
					doaDAO.deleteUtil1_P(psHm,"INC_INFO", personalIdList);
					
							doaDAO.deleteUtil1_P(psHm,"NCB_ACCOUNT_RULE_REPORT", ncbAccountReportIdList, false);
						doaDAO.deleteUtil1_P(psHm,"NCB_ACCOUNT_REPORT", ncbTrackingCodeList, false);
						doaDAO.deleteUtil1_P(psHm,"NCB_ACCOUNT", ncbTrackingCodeList);
						doaDAO.deleteUtil1_P(psHm,"NCB_ID", ncbTrackingCodeList);
						doaDAO.deleteUtil1_P(psHm,"NCB_NAME", ncbTrackingCodeList);
						doaDAO.deleteUtil1_P(psHm,"NCB_ADDRESS", ncbTrackingCodeList, false);
					doaDAO.deleteUtil1_P(psHm,"NCB_INFO", personalIdList);
							doaDAO.deleteUtil1_P(psHm,"ORIG_WISDOM_INFO", cardIdList);
						doaDAO.deleteUtil1_P(psHm,"ORIG_CARD", loanIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_CARDLINK_CUSTOMER", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_CARDLINK_CARD", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_KYC", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_NCB_DOCUMENT_HISTORY", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_PERSONAL_ADDRESS", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_PERSONAL_RELATION", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_INCOME_SOURCE", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_DEBT_INFO", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_ADDITIONAL_SERVICE", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_BSCORE", personalIdList);
					doaDAO.deleteUtil1_P(psHm,"CVRS_VALIDATION_RESULT", personalIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_PERSONAL_INFO", applicationGroupIdList);
					
						doaDAO.deleteUtil1_P(psHm,"ORIG_ADDITIONAL_SERVICE_MAP", loanIdList);
						doaDAO.deleteUtil1_P(psHm,"ORIG_LOAN_PRICING", loanIdList);
						doaDAO.deleteUtil1_P(psHm,"ORIG_LOAN_FEE", loanIdList);
						doaDAO.deleteUtil1_P(psHm,"ORIG_LOAN_TIER", loanIdList);
						doaDAO.deleteUtil1_P(psHm,"ORIG_CASH_TRANSFER", loanIdList);
						doaDAO.deleteUtil1_P(psHm,"LOAN_SETUP_STAMP_DUTY", loanIdList);
						doaDAO.deleteUtil1_P(psHm,"LOAN_SETUP_CLAIM", loanIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_LOAN", applicationRecordIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_PAYMENT_METHOD", personalIdList); //ORIG_LOAN FK
					doaDAO.deleteUtil1_P(psHm,"ORIG_BUNDLE_HL", applicationRecordIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_BUNDLE_KL", applicationRecordIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_BUNDLE_SME", applicationRecordIdList);
					doaDAO.deleteUtil1_P(psHm,"ULO_CAP_PORT_TRANSACTION", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"INF_CARDLINK_RESULT", cardLinkRefNoList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_APPLICATION", applicationGroupIdList);
					
					doaDAO.deleteUtil1_P(psHm,"ORIG_DOC_RELATION", docCheckListIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_DOC_CHECK_LIST_REASON", docCheckListIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_DOC_CHECK_LIST", applicationGroupIdList);
					
					doaDAO.deleteUtil1_P(psHm,"ORIG_APPLICATION_IMG_SPLIT", appImageIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_APPLICATION_IMG", applicationGroupIdList);
					
					doaDAO.deleteUtil1_P(psHm,"ORIG_DOCUMENT_COMMENT", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_NOTE_PAD_DATA", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_AUDIT_TRAIL", applicationGroupIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_ATTACHMENT_HISTORY", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_APPLICATION_LOG", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_SALE_INFO", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_REFERENCE_PERSON", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_HISTORY_DATA", applicationGroupIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_COMPARISON_DATA", applicationGroupIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_ENQUIRY_LOG", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_CONTACT_LOG", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_REASON_LOG", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_REASON", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_APPLICATION_INCREASE", applicationGroupIdList);
					doaDAO.deleteUtil1_P(psHm,"ORIG_FOLLOW_DOC_HISTORY", applicationGroupIdList);
					//doaDAO.deleteUtil1_P(psHm,"LETTER_HISTORY", applicationGroupNoList);
					
					//Delete in batch log using both key
					doaDAO.deleteUtil1_P(psHm.get("DEL_INF_BATCH_LOG_SYSTEM01"),psHm.get("SEL_INF_BATCH_LOG_SYSTEM01"),"INF_BATCH_LOG", applicationGroupNoList, false);
					doaDAO.deleteUtil1_P(psHm.get("DEL_INF_BATCH_LOG_REF_ID"),psHm.get("SEL_INF_BATCH_LOG_REF_ID"),"INF_BATCH_LOG", applicationGroupNoList, false);
					doaDAO.deleteUtil1_P(psHm.get("DEL_INF_BATCH_LOG_AG"),psHm.get("SEL_INF_BATCH_LOG_AG"),"INF_BATCH_LOG", applicationGroupIdList, false);
					doaDAO.deleteUtil1_P(psHm.get("DEL_INF_BATCH_LOG_AP"),psHm.get("SEL_INF_BATCH_LOG_AP"),"INF_BATCH_LOG", applicationRecordIdList, false);
					
					doaDAO.deleteUtil1_P(psHm,"SERVICE_REQ_RESP", applicationGroupNoList, false);
					
					//Last one to Delete
					doaDAO.deleteUtil1_P(psHm,"ORIG_APPLICATION_GROUP", applicationGroupIdList);
					
					//Update ORIG_APPLICATION_GROUP_CAT.PURGE_DATE_ORIG_APP
					doaDAO.updatePurgeDateP(psHm.get("PURGE_DATE_ORIG_APP"), applicationGroupIdList);
					
					conn.commit();
					
					logger.debug("Delete OldULOData " + appGroupCat.getApplicationGroupNo() + " Done.");
				
			}
			catch(Exception e)
			{
				logger.fatal("ERROR ",e);
				try 
				{
					conn.rollback();
					logger.fatal("ERROR ", e);
				} 
				catch (SQLException e1) 
				{
					logger.fatal("ERROR",e1);
				}
				throw new InfBatchException(e.getMessage());
			}
		}
	}

}
