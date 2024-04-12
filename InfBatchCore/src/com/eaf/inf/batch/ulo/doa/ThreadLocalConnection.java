package com.eaf.inf.batch.ulo.doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.constant.MConstant;

public class ThreadLocalConnection  
{
	private static transient Logger logger = Logger.getLogger(ThreadLocalConnection.class);
	public static ThreadLocal<Connection> conn = new ThreadLocal<Connection>()
	{
		@Override
		public void remove()
		{
			logger.debug("Close conn...");
			closeConnection(conn.get());
			super.remove();
		}
	};
	
	public static ThreadLocal<HashMap<String,PreparedStatement>> connPsHm = new ThreadLocal<HashMap<String,PreparedStatement>>()
	{
		@Override
		public void remove()
		{
			logger.debug("Close connPsHm...");
			closePs(connPsHm.get());
			super.remove();
		}
	};		
	
	public static ThreadLocal<Connection> imConn = new ThreadLocal<Connection>()
	{
		@Override
		public void remove()
		{
			logger.debug("Close imConn...");
			closeConnection(imConn.get());
			super.remove();
		}
	};
	
	public static ThreadLocal<Connection> olConn = new ThreadLocal<Connection>()
	{
		@Override
		public void remove()
		{
			logger.debug("Close olConn...");
			closeConnection(olConn.get());
			super.remove();
		}
	};
	
	public static ThreadLocal<Connection> resDBConn = new ThreadLocal<Connection>()
	{
		@Override
		public void remove()
		{
			logger.debug("Close resDBConn...");
			closeConnection(resDBConn.get());
			super.remove();
		}
	};
	
	public static ThreadLocal<Connection> bpmConn = new ThreadLocal<Connection>()
	{
		@Override
		public void remove()
		{
			logger.debug("Close bpmConn...");
			closeConnection(bpmConn.get());
			super.remove();
		}
	};
	
	public static void clean()
	{
		logger.info("Cleanup ThreadLocal of " + Thread.currentThread().getName());
		if(conn.get() != null){conn.remove();}
		if(connPsHm.get() != null){connPsHm.remove();}
		if(olConn.get() != null){olConn.remove();}
		if(bpmConn.get() != null){bpmConn.remove();}
		if(imConn.get() != null){imConn.remove();}
		if(resDBConn.get() != null){resDBConn.remove();}
	}
	
	public static void closeConnection(Connection conn)
	{
		try
		{
			if(conn != null)
			{
				conn.close();
				logger.debug("connection closed.");
			}
			
		}
		catch(Exception e)
		{
			logger.fatal("Error ", e);
		}
	}
	
	public static void closePs(HashMap<String,PreparedStatement> psHm)
	{
		if(psHm != null && psHm.values() != null)
		{
			for(PreparedStatement ps : psHm.values())
			{
				try
				{
					if(ps != null)
					{
						ps.close();
					}
				}
				catch(Exception e)
				{
					logger.fatal("Error ", e);
				}
			}
		}
	}

	public static Connection getConn() 
	{
		if(conn.get() == null)
		{
			logger.debug("get new ULO Connection for thread " + Thread.currentThread().getName());
			conn.set(InfBatchObjectDAO.getConnection());
		}
		return conn.get();
	}
	
	public static HashMap<String,PreparedStatement> getConnPsHm() 
	{
		if(connPsHm.get() == null)
		{
			connPsHm.set(initializePsForThreadLocal(conn.get()));
		}
		return connPsHm.get();
	}
	
	public static Connection getMLPConn() 
	{
		if(conn.get() == null)
		{
			logger.debug("get new MLP Connection for thread " + Thread.currentThread().getName());
			conn.set(InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB));
		}
		return conn.get();
	}
	
	public static HashMap<String,PreparedStatement> initializePsForThreadLocal(Connection conn)
	{
		HashMap<String,PreparedStatement> psHm = new HashMap<String,PreparedStatement>();
		try
		{
			//Prepare Select Statement
			psHm.put("appImageId", conn.prepareStatement(buildQuerySelect1("ORIG_APPLICATION_IMG", "APP_IMG_ID", "APPLICATION_GROUP_ID")));
			psHm.put("agVerResultId", conn.prepareStatement(buildQuerySelect2("XRULES_VERIFICATION_RESULT", "VER_RESULT_ID", "REF_ID", "VER_LEVEL", MConstant.REF_LEVEL.APPLICATION_GROUP)));
			psHm.put("agPolicyRuleId", conn.prepareStatement(buildQuerySelect1("XRULES_POLICY_RULES", "POLICY_RULES_ID", "VER_RESULT_ID")));
			psHm.put("personalId", conn.prepareStatement(buildQuerySelect1("ORIG_PERSONAL_INFO", "PERSONAL_ID", "APPLICATION_GROUP_ID")));
			psHm.put("incomeId", conn.prepareStatement(buildQuerySelect1("INC_INFO", "INCOME_ID", "PERSONAL_ID")));
			psHm.put("monthlyTawiId", conn.prepareStatement(buildQuerySelect1("INC_MONTHLY_TAWI", "MONTHLY_TAWI_ID", "INCOME_ID")));
			psHm.put("opnEndFundId", conn.prepareStatement(buildQuerySelect1("INC_OPN_END_FUND", "OPN_END_FUND_ID", "INCOME_ID")));
			psHm.put("payslipId", conn.prepareStatement(buildQuerySelect1("INC_PAYSLIP", "PAYSLIP_ID", "INCOME_ID")));
			psHm.put("payslipMId", conn.prepareStatement(buildQuerySelect1("INC_PAYSLIP_MONTHLY", "PAYSLIP_MONTHLY_ID", "PAYSLIP_ID")));
			psHm.put("bankStmId", conn.prepareStatement(buildQuerySelect1("INC_BANK_STATEMENT", "BANK_STATEMENT_ID", "INCOME_ID")));
			psHm.put("savAccId", conn.prepareStatement(buildQuerySelect1("INC_SAVING_ACC", "SAVING_ACC_ID", "INCOME_ID")));
			psHm.put("pVerResultId", conn.prepareStatement(buildQuerySelect2("XRULES_VERIFICATION_RESULT", "VER_RESULT_ID", "REF_ID", "VER_LEVEL", MConstant.REF_LEVEL.PERSONAL_INFO)));
			psHm.put("pPolicyRuleId", conn.prepareStatement(buildQuerySelect1("XRULES_POLICY_RULES", "POLICY_RULES_ID", "VER_RESULT_ID")));
			psHm.put("pOrPolicyRulesId", conn.prepareStatement(buildQuerySelect1("XRULES_OR_POLICY_RULES", "OR_POLICY_RULES_ID", "POLICY_RULES_ID")));
			psHm.put("pOrPolicyRulesDetailId", conn.prepareStatement(buildQuerySelect1("XRULES_OR_POLICY_RULES_DETAIL", "OR_POLICY_RULES_DETAIL_ID", "OR_POLICY_RULES_ID")));
			psHm.put("requireDocId", conn.prepareStatement(buildQuerySelect1("XRULES_REQUIRED_DOC", "REQUIRED_DOC_ID", "VER_RESULT_ID")));
			psHm.put("prvlgPrjCodeId", conn.prepareStatement(buildQuerySelect1("XRULES_PRVLG_PRJ_CDE", "PRVLG_PRJ_CDE_ID", "VER_RESULT_ID")));
			psHm.put("prvlgDocId", conn.prepareStatement(buildQuerySelect1("XRULES_PRVLG_DOC", "PRVLG_DOC_ID", "PRVLG_PRJ_CDE_ID")));
			psHm.put("ncbTrackingCode", conn.prepareStatement(buildQuerySelect1("NCB_INFO", "TRACKING_CODE", "PERSONAL_ID")));
			psHm.put("ncbAccountReportId", conn.prepareStatement(buildQuerySelect1("NCB_ACCOUNT_REPORT", "ACCOUNT_REPORT_ID", "TRACKING_CODE")));
			psHm.put("applicationRecordId", conn.prepareStatement(buildQuerySelect1("ORIG_APPLICATION", "APPLICATION_RECORD_ID", "APPLICATION_GROUP_ID")));
			psHm.put("cardLinkRefNo", conn.prepareStatement(buildQuerySelect1("ORIG_APPLICATION", "CARDLINK_REF_NO", "APPLICATION_GROUP_ID")));
			psHm.put("loanId", conn.prepareStatement(buildQuerySelect1("ORIG_LOAN", "LOAN_ID", "APPLICATION_RECORD_ID")));
			psHm.put("cardId", conn.prepareStatement(buildQuerySelect1("ORIG_CARD", "CARD_ID", "LOAN_ID")));
			psHm.put("appVerResultId", conn.prepareStatement(buildQuerySelect2("XRULES_VERIFICATION_RESULT", "VER_RESULT_ID", "REF_ID", "VER_LEVEL", MConstant.REF_LEVEL.APPLICATION)));
			psHm.put("appPolicyRuleId", conn.prepareStatement(buildQuerySelect1("XRULES_POLICY_RULES", "POLICY_RULES_ID", "VER_RESULT_ID")));
			psHm.put("appOrPolicyRulesId", conn.prepareStatement(buildQuerySelect1("XRULES_OR_POLICY_RULES", "OR_POLICY_RULES_ID", "POLICY_RULES_ID")));
			psHm.put("appOrPolicyRulesDetailId", conn.prepareStatement(buildQuerySelect1("XRULES_OR_POLICY_RULES_DETAIL", "OR_POLICY_RULES_DETAIL_ID", "OR_POLICY_RULES_ID")));
			psHm.put("docCheckListId", conn.prepareStatement(buildQuerySelect1("ORIG_DOC_CHECK_LIST", "DOC_CHECK_LIST_ID", "APPLICATION_GROUP_ID")));
			
			//Prepare DeleteStement
			buildSelDelPs1(psHm, "XRULES_GUIDELINE_DATA", "OR_POLICY_RULES_DETAIL_ID");
			buildSelDelPs1(psHm, "XRULES_OR_POLICY_RULES_DETAIL", "OR_POLICY_RULES_ID");
			buildSelDelPs1(psHm, "XRULES_OR_POLICY_RULES", "POLICY_RULES_ID");
			buildSelDelPs1(psHm, "XRULES_POLICY_RULES_CONDITION", "POLICY_RULES_ID");
			buildSelDelPs1(psHm, "XRULES_POLICY_RULES", "VER_RESULT_ID");
			buildSelDelPs1(psHm, "XRULES_VERIFICATION_RESULT", "VER_RESULT_ID");
			buildSelDelPs1(psHm, "XRULES_HR_VERIFICATION", "VER_RESULT_ID");
			buildSelDelPs1(psHm, "XRULES_IDENTIFY_QUESTION", "VER_RESULT_ID");
			buildSelDelPs1(psHm, "XRULES_IDENTIFY_QUESTION_SET", "VER_RESULT_ID");
			buildSelDelPs1(psHm, "XRULES_PHONE_VERIFICATION", "VER_RESULT_ID");
			buildSelDelPs1(psHm, "XRULES_WEB_VERIFICATION", "VER_RESULT_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_TRADING_DOC", "PRVLG_DOC_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_MGM_DOC", "PRVLG_DOC_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_EXCEPTION_DOC", "PRVLG_DOC_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_TRANSFER_DOC", "PRVLG_DOC_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_KASSET_DOC", "PRVLG_DOC_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_PRODUCT_CCA", "PRVLG_PRJ_CDE_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_PRODUCT_INSURANCE", "PRVLG_PRJ_CDE_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_PRODUCT_SAVING", "PRVLG_PRJ_CDE_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_RCCMD_PRJ_CDE", "PRVLG_PRJ_CDE_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_DOC", "PRVLG_PRJ_CDE_ID");
			buildSelDelPs1(psHm, "XRULES_REQUIRED_DOC_DETAIL", "REQUIRED_DOC_ID");
			buildSelDelPs1(psHm, "XRULES_REQUIRED_DOC", "VER_RESULT_ID");
			buildSelDelPs1(psHm, "XRULES_DOCUMENT_VERIFICATION", "VER_RESULT_ID");
			buildSelDelPs1(psHm, "XRULES_PRVLG_PRJ_CDE", "PRVLG_PRJ_CDE_ID");
			buildSelDelPs1(psHm, "INC_CLS_END_FUND", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_FIN_INSTRUMENT", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_FIXED_ACC", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_FIXED_GUARANTEE", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_KVI", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_MONTHLY_TAWI_DETAIL", "MONTHLY_TAWI_ID");
			buildSelDelPs1(psHm, "INC_MONTHLY_TAWI", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_YEARLY_TAWI", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_OPN_END_FUND_DETAIL", "OPN_END_FUND_ID");
			buildSelDelPs1(psHm, "INC_OPN_END_FUND", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_PAYROLL_FILE", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_PAYROLL", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_PAYSLIP_MONTHLY_DETAIL", "PAYSLIP_MONTHLY_ID");
			buildSelDelPs1(psHm, "INC_PAYSLIP_MONTHLY", "PAYSLIP_ID");
			buildSelDelPs1(psHm, "INC_PAYSLIP", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_PREVIOUS_INCOME", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_SALARY_CERT", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_BANK_STATEMENT_DETAIL", "BANK_STATEMENT_ID");
			buildSelDelPs1(psHm, "INC_BANK_STATEMENT", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_SAVING_ACC_DETAIL", "SAVING_ACC_ID");
			buildSelDelPs1(psHm, "INC_SAVING_ACC", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_TAWEESAP", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_OTH_INCOME", "INCOME_ID");
			buildSelDelPs1(psHm, "INC_INFO", "PERSONAL_ID");
			buildSelDelPs1(psHm, "NCB_ACCOUNT_RULE_REPORT", "ACCOUNT_REPORT_ID");
			buildSelDelPs1(psHm, "NCB_ACCOUNT_REPORT", "TRACKING_CODE");
			buildSelDelPs1(psHm, "NCB_ACCOUNT", "TRACKING_CODE");
			buildSelDelPs1(psHm, "NCB_ID", "TRACKING_CODE");
			buildSelDelPs1(psHm, "NCB_NAME", "TRACKING_CODE");
			buildSelDelPs1(psHm, "NCB_ADDRESS", "TRACKING_CODE");
			buildSelDelPs1(psHm, "NCB_INFO", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_WISDOM_INFO", "CARD_ID");
			buildSelDelPs1(psHm, "ORIG_CARD", "LOAN_ID");
			buildSelDelPs1(psHm, "ORIG_CARDLINK_CUSTOMER", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_CARDLINK_CARD", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_KYC", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_NCB_DOCUMENT_HISTORY", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_PERSONAL_ADDRESS", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_PERSONAL_RELATION", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_INCOME_SOURCE", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_DEBT_INFO", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_ADDITIONAL_SERVICE", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_BSCORE", "PERSONAL_ID");
			buildSelDelPs1(psHm, "CVRS_VALIDATION_RESULT", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_PERSONAL_INFO", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_ADDITIONAL_SERVICE_MAP", "LOAN_ID");
			buildSelDelPs1(psHm, "ORIG_LOAN_PRICING", "LOAN_ID");
			buildSelDelPs1(psHm, "ORIG_LOAN_FEE", "REF_ID");
			buildSelDelPs1(psHm, "ORIG_LOAN_TIER", "LOAN_ID");
			buildSelDelPs1(psHm, "ORIG_CASH_TRANSFER", "LOAN_ID");
			buildSelDelPs1(psHm, "LOAN_SETUP_STAMP_DUTY", "LOAN_ID");
			buildSelDelPs1(psHm, "LOAN_SETUP_CLAIM", "LOAN_ID");
			buildSelDelPs1(psHm, "ORIG_LOAN", "APPLICATION_RECORD_ID");
			buildSelDelPs1(psHm, "ORIG_PAYMENT_METHOD", "PERSONAL_ID");
			buildSelDelPs1(psHm, "ORIG_BUNDLE_HL", "APPLICATION_RECORD_ID");
			buildSelDelPs1(psHm, "ORIG_BUNDLE_KL", "APPLICATION_RECORD_ID");
			buildSelDelPs1(psHm, "ORIG_BUNDLE_SME", "APPLICATION_RECORD_ID");
			buildSelDelPs1(psHm, "ULO_CAP_PORT_TRANSACTION", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "INF_CARDLINK_RESULT", "REF_ID");
			buildSelDelPs1(psHm, "ORIG_APPLICATION", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_DOC_RELATION", "DOC_CHECK_LIST_ID");
			buildSelDelPs1(psHm, "ORIG_DOC_CHECK_LIST_REASON", "DOC_CHECK_LIST_ID");
			buildSelDelPs1(psHm, "ORIG_DOC_CHECK_LIST", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_APPLICATION_IMG_SPLIT", "APP_IMG_ID");
			buildSelDelPs1(psHm, "ORIG_APPLICATION_IMG", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_DOCUMENT_COMMENT", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_NOTE_PAD_DATA", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_AUDIT_TRAIL", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_ATTACHMENT_HISTORY", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_APPLICATION_LOG", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_SALE_INFO", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_REFERENCE_PERSON", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_HISTORY_DATA", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_COMPARISON_DATA", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_ENQUIRY_LOG", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_CONTACT_LOG", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_REASON_LOG", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_REASON", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_APPLICATION_INCREASE", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "ORIG_FOLLOW_DOC_HISTORY", "APPLICATION_GROUP_ID");
			buildSelDelPs1(psHm, "LETTER_HISTORY", "APPLICATION_GROUP_NO");
			
			psHm.put("SEL_INF_BATCH_LOG_AG", conn.prepareStatement(buildQuerySelect1("INF_BATCH_LOG", "*", "APPLICATION_GROUP_ID")));
			psHm.put("DEL_INF_BATCH_LOG_AG", conn.prepareStatement("DELETE FROM INF_BATCH_LOG WHERE APPLICATION_GROUP_ID = ? "));
			psHm.put("SEL_INF_BATCH_LOG_AP", conn.prepareStatement(buildQuerySelect1("INF_BATCH_LOG", "*", "APPLICATION_RECORD_ID")));
			psHm.put("DEL_INF_BATCH_LOG_AP", conn.prepareStatement("DELETE FROM INF_BATCH_LOG WHERE APPLICATION_RECORD_ID = ? "));
			psHm.put("SEL_INF_BATCH_LOG_REF_ID", conn.prepareStatement(buildQuerySelect1("INF_BATCH_LOG", "*", "REF_ID")));
			psHm.put("DEL_INF_BATCH_LOG_REF_ID", conn.prepareStatement("DELETE FROM INF_BATCH_LOG WHERE REF_ID = ? "));
			psHm.put("SEL_INF_BATCH_LOG_SYSTEM01", conn.prepareStatement(buildQuerySelect1("INF_BATCH_LOG", "*", "SYSTEM01")));
			psHm.put("DEL_INF_BATCH_LOG_SYSTEM01", conn.prepareStatement("DELETE FROM INF_BATCH_LOG WHERE SYSTEM01 = ? "));
			
			buildSelDelPs1(psHm, "SERVICE_REQ_RESP", "REF_CODE");
			
			buildSelDelPs1(psHm, "ORIG_APPLICATION_GROUP", "APPLICATION_GROUP_ID");
			
			buildPurgeDatePs(psHm, "PURGE_DATE_BPMDBS", "APPLICATION_GROUP_NO");
			buildPurgeDatePs(psHm, "PURGE_DATE_IM_APP", "APPLICATION_GROUP_NO");
			buildPurgeDatePs(psHm, "PURGE_DATE_ORIG_APP", "APPLICATION_GROUP_ID");
			
			//RESDB, OL_DATA, IMG not using shared prepareStatement
			//buildPurgeDatePs(psHm, "PURGE_DATE_IMAGE_FILE", "APPLICATION_GROUP_ID");
			//buildPurgeDatePs(psHm, "PURGE_DATE_OL_DATA", "APPLICATION_GROUP_NO");
			//buildPurgeDatePs(psHm, "PURGE_DATE_RESDB", "APPLICATION_GROUP_NO");
			
			logger.debug("Initialize prepareStatement for ThreadLocal of Thead " + Thread.currentThread().getId() + " done.");
		}
		catch(Exception e)
		{
			logger.fatal("Fail to initializePsForThreadLocal ", e);
		}
		return psHm;
	}

	public static Connection getImConn() 
	{
		if(imConn.get() == null)
		{
			logger.debug("get new IM Connection for thread " + Thread.currentThread().getName());
			imConn.set(InfBatchObjectDAO.getConnection(InfBatchServiceLocator.IMG_DB));
		}
		return imConn.get();
	}

	public static Connection getOlConn() 
	{
		if(olConn.get() == null)
		{
			logger.debug("get new OL Connection for thread " + Thread.currentThread().getName());
			olConn.set(InfBatchObjectDAO.getConnection(OrigServiceLocator.OL_DB));
		}
		return olConn.get();
	}

	public static Connection getResDBConn() 
	{
		if(resDBConn.get() == null)
		{
			logger.debug("get new RESDB Connection for thread " + Thread.currentThread().getName());
			resDBConn.set(InfBatchObjectDAO.getConnection(InfBatchServiceLocator.RES_DB));
		}
		return resDBConn.get();
	}
	
	public static Connection getBpmConn() 
	{
		if(bpmConn.get() == null)
		{
			logger.debug("get new BPM Connection for thread " + Thread.currentThread().getName());
			bpmConn.set(InfBatchObjectDAO.getConnection(OrigServiceLocator.BPM));
		}
		return bpmConn.get();
	}
	
	public static String buildQuerySelect1(String table, String keyToGet, String key)
	{
		return "SELECT " + keyToGet + " FROM " + table + " WHERE " + key + " = ? ";
	}
	
	public static String buildQuerySelect2(String table, String keyToGet,
			String keyString, String key2, String keyValue2)
	{
		return "SELECT " + keyToGet + " FROM " + table + " WHERE " + keyString + " = ? " 
				+ " AND " + key2 + " = '" + keyValue2 + "' ";
	}
	
	public static void buildSelDelPs1(HashMap<String, PreparedStatement> psHm, String table,String keyName) throws SQLException
	{
		String queryPart = table + " WHERE " + keyName + " = ? ";
		String selectQuery = "SELECT * FROM " + queryPart;
		String deleteQuery = "DELETE FROM " + queryPart;
		psHm.put("SEL_" + table, conn.get().prepareStatement(selectQuery));
		psHm.put("DEL_" + table, conn.get().prepareStatement(deleteQuery));
	}
	
	public static void buildSelDelPs2(HashMap<String, PreparedStatement> psHm, String table, String keyName1, 
			String keyName2, String keyValue2) throws SQLException
	{
		String queryPart = table + " WHERE " + keyName1 + " = ? " + " AND " + keyName2 + "= '" + keyValue2 + "'";
		String selectQuery = "SELECT * FROM " + queryPart;
		String deleteQuery = "DELETE FROM " + queryPart;
		psHm.put("SEL_" + table, conn.get().prepareStatement(selectQuery));
		psHm.put("DEL_" + table, conn.get().prepareStatement(deleteQuery));
	}
	
	public static void buildPurgeDatePs(HashMap<String, PreparedStatement> psHm,
			String field, String key) throws SQLException
	{
		String purgeDateQuery = "UPDATE ORIG_APPLICATION_GROUP_CAT SET " + field + " = SYSDATE WHERE " + key + " = ? ";
		psHm.put(field, conn.get().prepareStatement(purgeDateQuery));
	}
	
}
