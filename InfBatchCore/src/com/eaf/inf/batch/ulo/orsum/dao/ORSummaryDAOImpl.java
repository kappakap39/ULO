package com.eaf.inf.batch.ulo.orsum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.SystemConstant;

public class ORSummaryDAOImpl extends InfBatchObjectDAO implements ORSummaryDAO {
	private static transient Logger logger = Logger.getLogger(ORSummaryDAOImpl.class);
	private static final String OR_EXEMPTION_REGEX = InfBatchProperty.getInfBatchConfig("REFRESH_SUMMARY_TABLE_OR_EXEMPT_PATTERN"); 
	private static final String JOB_STATE_APPROVE = InfBatchProperty.getInfBatchConfig("REFRESH_SUMMARY_TABLE_JOB_STATE_APPROVED");
	private static final String JOB_STATE_REJECT = InfBatchProperty.getInfBatchConfig("REFRESH_SUMMARY_TABLE_JOB_STATE_REJECTED");
	private static final String JOB_STATE_POSTAPPROVE = InfBatchProperty.getInfBatchConfig("REFRESH_SUMMARY_TABLE_JOB_STATE_POSTAPPROVED");
	// Number of months to summarize OR
//	private static final int orMonths = 6;
	// Number of periods in OR period summary to be updated
	private static final int MAX_UPDATE_PERIOD = Integer.parseInt(InfBatchProperty.getInfBatchConfig("REFRESH_SUMMARY_TABLE_REFRESH_OR_PERIOD"));
	
	public static Calendar setTime(Calendar cal, int yy, int mm, int dd, boolean strictEOM) {
		cal.set(yy, mm, dd);
		if (strictEOM) {
			int new_dd = cal.get(Calendar.DAY_OF_MONTH);
			if (new_dd != dd) {
				// If day of month is not the same as value we set, then that value is beyond last day of month
				if (mm == Calendar.FEBRUARY)
					new_dd = (new GregorianCalendar().isLeapYear(yy)) ? 29 : 28;
				else if (mm == Calendar.APRIL || mm == Calendar.JUNE || mm == Calendar.SEPTEMBER || mm == Calendar.NOVEMBER)
					new_dd = 30;
				else
					new_dd = 31;
				cal.set(yy, mm, new_dd);
			}
		}
		return cal;
	}

	public static Date dateFromReferenceDate(Date referenceDate, boolean strictEOM, boolean absoluteDD, Integer dd, boolean absoluteMM, Integer mm, boolean absoluteYY, Integer yy){
		if (null != referenceDate) {
			try{
				Calendar cal = Calendar.getInstance();
				cal.setTime(referenceDate);
				int ref_dd = cal.get(Calendar.DAY_OF_MONTH);
				int ref_mm = cal.get(Calendar.MONTH);
				int ref_yy = cal.get(Calendar.YEAR);
				ref_dd = (absoluteDD) ? (dd) : (ref_dd+dd);
				ref_mm = (absoluteMM) ? (mm) : (ref_mm+mm);
				ref_yy = (absoluteYY) ? (yy) : (ref_yy+yy);
				cal = setTime(cal, ref_yy, ref_mm, ref_dd, strictEOM);
				return cal.getTime();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

/*	
	public String getOrForAppGroupSQL(String applicationGroupNo) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO OR_SUM(QR_NO,APP_IN_PERIOD,APPROVE_PERIOD,REJECT_PERIOD,PRODUCT,EXCEPTION_FLAG,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,OR_01,OR_02,OR_03,OR_04,OR_05,OR_06,OR_07,OR_08,OR_09,OR_10,OR_11,OR_12,OR_13,OR_14,OR_15,OR_16,OR_17,OR_18,OR_19,OR_20,OR_21,OR_22,OR_23,OR_24,OR_25) ");
		sql.append("SELECT * FROM ( ");
		sql.append(		"SELECT DISTINCT "); 
		sql.append(			"ag.APPLICATION_GROUP_NO QR_NO, "); 
		sql.append(			"TO_CHAR(ag.CREATE_DATE,'YYYY-MM') APP_IN_PERIOD, "); 
		sql.append(			"TO_CHAR(SYSDATE,'YYYY-MM') APPROVE_PERIOD, "); 
		sql.append(			"TO_CHAR(SYSDATE,'YYYY-MM') REJECT_PERIOD, ");
		sql.append(			"bu.ORG_ID PRODUCT, ");
		sql.append(			"CASE "); 
		sql.append(				"WHEN ag.CA_REMARK IS NULL OR NOT REGEXP_LIKE(ag.CA_REMARK, '").append(ORSummaryDAOImpl.OR_EXEMPTION_REGEX).append("') THEN 'N' "); 
		sql.append(				"ELSE 'Y' "); 
		sql.append(			"END AS EXCEPTION_FLAG, ");
		sql.append(			"SYSDATE AS CREATE_DATE,'SYSTEM' CREATE_BY, SYSDATE UPDATE_DATE, 'SYSTEM' UPDATE_BY, ");
		sql.append(			"xor.POLICY_CODE OR_CODE ");
		sql.append(		"FROM ORIG_APP.ORIG_APPLICATION_GROUP ag ");
		sql.append(       	"JOIN ORIG_APP.ORIG_APPLICATION ap ON ap.application_group_id = ag.APPLICATION_GROUP_ID ");
		sql.append(       	"LEFT JOIN ORIG_APP.ORIG_PERSONAL_RELATION opr ON opr.REF_ID = ap.APPLICATION_RECORD_ID AND opr.RELATION_LEVEL = 'A' ");
		sql.append(       	"LEFT JOIN ORIG_APP.ORIG_PERSONAL_INFO op ON op.personal_id = opr.PERSONAL_ID ");
		sql.append(       	"LEFT JOIN ORIG_APP.XRULES_VERIFICATION_RESULT xvs ON ( ");
		sql.append(				"(xvs.REF_ID = ag.APPLICATION_GROUP_ID AND xvs.VER_LEVEL = 'G') ");
		sql.append(       		"OR (xvs.REF_ID = ap.APPLICATION_RECORD_ID AND xvs.VER_LEVEL = 'A') ");
		sql.append(       		"OR (xvs.REF_ID = op.PERSONAL_ID AND xvs.VER_LEVEL = 'P') ");
		sql.append(			") ");
		sql.append(       	"LEFT JOIN ORIG_APP.XRULES_POLICY_RULES xpr ON xpr.VER_RESULT_ID = xvs.VER_RESULT_ID ");
		sql.append(       	"LEFT JOIN ORIG_APP.XRULES_OR_POLICY_RULES xor ON xor.POLICY_RULES_ID = xpr.POLICY_RULES_ID ");
		sql.append(       	"JOIN ORIG_APP.BUSINESS_CLASS bu ON bu.BUS_CLASS_ID = ap.BUSINESS_CLASS_ID ");
		sql.append(		"WHERE 1=1 ");
		sql.append(			"AND ag.APPLICATION_GROUP_NO='").append(applicationGroupNo).append("' ");
		sql.append(") ");
		sql.append("PIVOT ( "); 
		sql.append(   "MAX(1) FOR OR_CODE in (  1 OR_01,  2 OR_02,  3 OR_03,  4 OR_04,  5 OR_05,  6 OR_06,  7 OR_07,  8 OR_08,  9 OR_09, 10 OR_10, ");
		sql.append(							  "11 OR_11, 12 OR_12, 13 OR_13, 14 OR_14, 15 OR_15, 16 OR_16, 17 OR_17, 18 OR_18, 19 OR_19, 20 OR_20, ");
		sql.append(                           "21 OR_21, 22 OR_22, 23 OR_23, 24 OR_24, 25 OR_25) ");
		sql.append(") ");
		return String.valueOf(sql);
	}
*/
	
//	public String getOrSummarySQL(String fromDate, String toDate, String dateAfterToDate) {
	public String getOrSummarySQL() {
		String[] CJD_SOURCE = SystemConstant.getArrayConstant("CJD_SOURCE");
		String COMMA = "";
		logger.debug("OR exempt pattern: " + ORSummaryDAOImpl.OR_EXEMPTION_REGEX);
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO OR_SUM(GRP_ID,QR_NO,APP_IN_PERIOD,APPROVE_PERIOD,REJECT_PERIOD,PRODUCT,EXCEPTION_FLAG,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,OR_01,OR_02,OR_03,OR_04,OR_05,OR_06,OR_07,OR_08,OR_09,OR_10,OR_11,OR_12,OR_13,OR_14,OR_15,OR_16,OR_17,OR_18,OR_19,OR_20,OR_21,OR_22,OR_23,OR_24,OR_25) ");
		sql.append("SELECT * FROM ( ");
		sql.append(		"SELECT DISTINCT "); 
		sql.append(			"ag.APPLICATION_GROUP_ID GRP_ID, "); 
		sql.append(			"ag.APPLICATION_GROUP_NO QR_NO, "); 
		sql.append(			"TO_CHAR(ag.CREATE_DATE,'YYYY-MM') APP_IN_PERIOD, "); 
		sql.append(			"TO_CHAR(agl.DE2_SUBMIT_DATE,'YYYY-MM') APPROVE_PERIOD, "); 
		sql.append(			"TO_CHAR(aglr.DE2_SUBMIT_DATE,'YYYY-MM') REJECT_PERIOD, ");
		sql.append(			"bu.ORG_ID PRODUCT, ");
		sql.append(			"CASE "); 
		sql.append(				"WHEN ag.CA_REMARK IS NULL OR NOT REGEXP_LIKE(ag.CA_REMARK, '").append(ORSummaryDAOImpl.OR_EXEMPTION_REGEX).append("') THEN 'N' "); 
		sql.append(				"ELSE 'Y' "); 
		sql.append(			"END AS EXCEPTION_FLAG, ");
		sql.append(			"SYSDATE AS CREATE_DATE,'SYSTEM' CREATE_BY, SYSDATE UPDATE_DATE, 'SYSTEM' UPDATE_BY, ");
		sql.append(			"t.POLICY_CODE OR_CODE ");
		sql.append(		"FROM ORIG_APP.ORIG_APPLICATION_GROUP ag ");
		sql.append(       	"JOIN ORIG_APP.ORIG_APPLICATION ap ON ap.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID ");
		sql.append(       	"LEFT JOIN ORIG_APP.ORIG_PERSONAL_RELATION opr ON opr.REF_ID = ap.APPLICATION_RECORD_ID AND opr.RELATION_LEVEL = 'A' ");
		sql.append(       	"LEFT JOIN ORIG_APP.ORIG_PERSONAL_INFO op ON op.personal_id = opr.PERSONAL_ID ");
		sql.append(       	"LEFT JOIN ORIG_APP.XRULES_VERIFICATION_RESULT xvs ON ( ");
		sql.append(				"(xvs.REF_ID = ag.APPLICATION_GROUP_ID AND xvs.VER_LEVEL = 'G') ");
		sql.append(       		"OR (xvs.REF_ID = ap.APPLICATION_RECORD_ID AND xvs.VER_LEVEL = 'A') ");
		sql.append(       		"OR (xvs.REF_ID = op.PERSONAL_ID AND xvs.VER_LEVEL = 'P') ");
		sql.append(			") ");
		sql.append(       	"LEFT JOIN ORIG_APP.XRULES_POLICY_RULES xpr ON xpr.VER_RESULT_ID = xvs.VER_RESULT_ID ");
		sql.append(       	"LEFT JOIN ORIG_APP.XRULES_OR_POLICY_RULES xor ON xor.POLICY_RULES_ID = xpr.POLICY_RULES_ID ");
		sql.append(       	"LEFT JOIN ( ");
								// Subquery to find application group in approve,postapproval status which has override
		sql.append(				"SELECT ag.APPLICATION_GROUP_ID,agl.DE2_SUBMIT_DATE,XOR.POLICY_CODE, ag.CA_REMARK ");
		sql.append(           	"FROM ORIG_APP.ORIG_APPLICATION_GROUP ag ");
		sql.append(               	"JOIN ORIG_APP.ORIG_APPLICATION ap ON ag.APPLICATION_GROUP_ID = ap.APPLICATION_GROUP_ID AND ap.FINAL_APP_DECISION = 'Approve' ");
		sql.append(               	"JOIN ( ");
										// Subquery to find when application group status is approve
		sql.append(						"SELECT MAX(al.CREATE_DATE) DE2_SUBMIT_DATE, APPLICATION_GROUP_ID ");
		sql.append(               		"FROM ORIG_APP.ORIG_APPLICATION_LOG al ");
		sql.append(               		"WHERE al.JOB_STATE IN ('").append(ORSummaryDAOImpl.JOB_STATE_APPROVE).append("') ");
		sql.append(               		"GROUP BY al.APPLICATION_GROUP_ID ");
		sql.append(					") agl ON agl.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID ");
		sql.append(               	"JOIN ORIG_APP.ORIG_PERSONAL_RELATION opr ON opr.REF_ID = ap.APPLICATION_RECORD_ID AND opr.RELATION_LEVEL = 'A' ");
		sql.append(               	"JOIN ORIG_APP.XRULES_VERIFICATION_RESULT xvs ON ( ");
		sql.append(						"(xvs.REF_ID = ag.APPLICATION_GROUP_ID AND xvs.VER_LEVEL = 'G') ");
		sql.append(               		"OR (xvs.REF_ID = ap.APPLICATION_RECORD_ID AND xvs.VER_LEVEL = 'A') ");
		sql.append(               		"OR (xvs.REF_ID = opr.PERSONAL_ID AND xvs.VER_LEVEL = 'P') ");
		sql.append(					") ");
		sql.append(               	"JOIN ORIG_APP.XRULES_POLICY_RULES xpr ON xpr.VER_RESULT_ID = xvs.VER_RESULT_ID ");
		sql.append(               	"JOIN ORIG_APP.XRULES_OR_POLICY_RULES xor ON xor.POLICY_RULES_ID = xpr.POLICY_RULES_ID ");
		sql.append(           	"WHERE ag.JOB_STATE IN ('").append(ORSummaryDAOImpl.JOB_STATE_APPROVE).append("','").append(ORSummaryDAOImpl.JOB_STATE_POSTAPPROVE).append("') ");
//		sql.append(					"AND ag.CREATE_DATE < to_date('").append(dateAfterToDate).append("','YYYY-MM-DD') ");
		sql.append(			") t ON t.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID AND t.POLICY_CODE = xor.POLICY_CODE ");
		sql.append(       	"LEFT JOIN ( ");
								// Subquery to find application group which has ever been in approve status and latest date of approve status
		sql.append(				"SELECT MAX(al.CREATE_DATE) DE2_SUBMIT_DATE, al.APPLICATION_GROUP_ID ");
		sql.append(           	"FROM ORIG_APP.ORIG_APPLICATION_LOG al ");
		sql.append(					"JOIN ORIG_APP.ORIG_APPLICATION_GROUP ag ON ag.APPLICATION_GROUP_ID = al.APPLICATION_GROUP_ID ");
		sql.append(					"JOIN ORIG_APP.ORIG_APPLICATION ap ON ap.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID ");
		sql.append(           	"WHERE al.JOB_STATE IN ('").append(ORSummaryDAOImpl.JOB_STATE_APPROVE).append("') ");
//		sql.append(					"AND al.CREATE_DATE < to_date('").append(dateAfterToDate).append("','YYYY-MM-DD') ");
		sql.append(           	"GROUP BY al.APPLICATION_GROUP_ID ");
		sql.append(			") agl ON agl.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID ");
		sql.append(       	"LEFT JOIN ( ");
								// Subquery to find application group which has ever been in reject status and latest date of reject status
		sql.append(				"SELECT MAX(al.CREATE_DATE) DE2_SUBMIT_DATE, al.APPLICATION_GROUP_ID ");
		sql.append(				"FROM ORIG_APP.ORIG_APPLICATION_LOG al ");
		sql.append(              	"JOIN ORIG_APP.ORIG_APPLICATION ap ON ap.APPLICATION_GROUP_ID = al.APPLICATION_GROUP_ID ");
		sql.append(				"WHERE al.JOB_STATE IN ('").append(ORSummaryDAOImpl.JOB_STATE_REJECT).append("') ");
//		sql.append(					"AND al.CREATE_DATE < to_date('").append(dateAfterToDate).append("','YYYY-MM-DD') ");
		sql.append(				"GROUP BY al.APPLICATION_GROUP_ID ");
		sql.append(			") aglr ON aglr.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID ");
		sql.append(       	"JOIN ORIG_APP.BUSINESS_CLASS bu ON bu.BUS_CLASS_ID = ap.BUSINESS_CLASS_ID ");
		sql.append(		"WHERE 1=1 ");
		
		if(null != CJD_SOURCE && CJD_SOURCE.length > 0)
		{
			sql.append(" AND ag.SOURCE NOT IN ( ");
			for (String cjdSource : CJD_SOURCE) 
			{
				sql.append(COMMA + "'" + cjdSource + "'");
				COMMA = ",";
			}
			sql.append(" )");
		}
//		sql.append(			"AND ag.APPLICATION_GROUP_NO IS NOT NULL ");
//		sql.append(			"AND ag.UPDATE_DATE BETWEEN to_date('").append(fromDate).append("','YYYY-MM-DD') AND to_date('").append(toDate).append(" 23:59:59','YYYY-MM-DD HH24:MI:SS') ");
		sql.append(") ");
		sql.append("PIVOT ( "); 
		sql.append(   "COUNT(OR_CODE) FOR OR_CODE in (  1 OR_01,  2 OR_02,  3 OR_03,  4 OR_04,  5 OR_05,  6 OR_06,  7 OR_07,  8 OR_08,  9 OR_09, 10 OR_10, ");
		sql.append(							  		  "11 OR_11, 12 OR_12, 13 OR_13, 14 OR_14, 15 OR_15, 16 OR_16, 17 OR_17, 18 OR_18, 19 OR_19, 20 OR_20, ");
		sql.append(                           		  "21 OR_21, 22 OR_22, 23 OR_23, 24 OR_24, 25 OR_25) ");
		sql.append(") ");
		return String.valueOf(sql);
	}

	
/*	
	public String getOrSummarySQL(String fromDate, String toDate, String dateAfterToDate) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("MERGE INTO OR_SUM S "); 
		sql.append("USING ( ");
		sql.append(		"SELECT * FROM ( ");
		sql.append(				"SELECT DISTINCT "); 
		sql.append(					"ag.APPLICATION_GROUP_NO QR_NO, "); 
		sql.append(					"TO_CHAR(ag.CREATE_DATE,'YYYY-MM') APP_IN_PERIOD, "); 
		sql.append(					"TO_CHAR(agl.DE2_SUBMIT_DATE,'YYYY-MM') APPROVE_PERIOD, "); 
		sql.append(					"TO_CHAR(aglr.DE2_SUBMIT_DATE,'YYYY-MM') REJECT_PERIOD, ");
		sql.append(					"bu.ORG_ID PRODUCT, ");
		sql.append(					"CASE "); 
		sql.append(						"WHEN ag.CA_REMARK IS NULL OR NOT REGEXP_LIKE(ag.CA_REMARK, '").append(ORSummaryDAOImpl.OR_EXEMPTION_REGEX).append("') THEN 'N' "); 
		sql.append(						"ELSE 'Y' "); 
		sql.append(					"END AS EXCEPTION_FLAG, ");
		sql.append(					"SYSDATE AS CREATE_DATE,'SYSTEM' CREATE_BY, SYSDATE UPDATE_DATE, 'SYSTEM' UPDATE_BY, ");
		sql.append(					"t.POLICY_CODE OR_CODE ");
		sql.append(				"FROM ORIG_APP.ORIG_APPLICATION_GROUP ag ");
		sql.append(		       	"JOIN ORIG_APP.ORIG_APPLICATION ap ON ap.application_group_id = ag.APPLICATION_GROUP_ID ");
		sql.append(		       	"LEFT JOIN ORIG_APP.ORIG_PERSONAL_RELATION opr ON opr.REF_ID = ap.APPLICATION_RECORD_ID AND opr.RELATION_LEVEL = 'A' ");
		sql.append(		       	"LEFT JOIN ORIG_APP.ORIG_PERSONAL_INFO op ON op.personal_id = opr.PERSONAL_ID ");
		sql.append(		       	"LEFT JOIN ORIG_APP.XRULES_VERIFICATION_RESULT xvs ON ( ");
		sql.append(						"(xvs.REF_ID = ag.APPLICATION_GROUP_ID AND xvs.VER_LEVEL = 'G') ");
		sql.append(		       		"OR (xvs.REF_ID = ap.APPLICATION_RECORD_ID AND xvs.VER_LEVEL = 'A') ");
		sql.append(		       		"OR (xvs.REF_ID = op.PERSONAL_ID AND xvs.VER_LEVEL = 'P') ");
		sql.append(					") ");
		sql.append(		       	"LEFT JOIN ORIG_APP.XRULES_POLICY_RULES xpr ON xpr.VER_RESULT_ID = xvs.VER_RESULT_ID ");
		sql.append(		       	"LEFT JOIN ORIG_APP.XRULES_OR_POLICY_RULES xor ON xor.POLICY_RULES_ID = xpr.POLICY_RULES_ID ");
		sql.append(		       	"LEFT JOIN ( ");
										// Subquery to find application group in approve,postapproval status which has override
		sql.append(						"SELECT ag.APPLICATION_GROUP_ID,agl.DE2_SUBMIT_DATE,XOR.POLICY_CODE, ag.CA_REMARK ");
		sql.append(		           	"FROM ORIG_APP.ORIG_APPLICATION_GROUP ag ");
		sql.append(		               	"JOIN ORIG_APP.ORIG_APPLICATION ap ON ag.APPLICATION_GROUP_ID = ap.APPLICATION_GROUP_ID AND ap.FINAL_APP_DECISION = 'Approve' ");
		sql.append(		               	"JOIN ( ");
												// Subquery to find when application group status is approve
		sql.append(								"SELECT MAX(al.CREATE_DATE) DE2_SUBMIT_DATE, APPLICATION_GROUP_ID ");
		sql.append(		               		"FROM ORIG_APP.ORIG_APPLICATION_LOG al ");
		sql.append(		               		"WHERE al.JOB_STATE IN ('").append(ORSummaryDAOImpl.JOB_STATE_APPROVE).append("') ");
		sql.append(		               		"GROUP BY al.APPLICATION_GROUP_ID ");
		sql.append(							") agl ON agl.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID ");
		sql.append(		               	"JOIN ORIG_APP.ORIG_PERSONAL_RELATION opr ON opr.REF_ID = ap.APPLICATION_RECORD_ID AND opr.RELATION_LEVEL = 'A' ");
		sql.append(		               	"JOIN ORIG_APP.XRULES_VERIFICATION_RESULT xvs ON ( ");
		sql.append(								"(xvs.REF_ID = ag.APPLICATION_GROUP_ID AND xvs.VER_LEVEL = 'G') ");
		sql.append(		               		"OR (xvs.REF_ID = ap.APPLICATION_RECORD_ID AND xvs.VER_LEVEL = 'A') ");
		sql.append(		               		"OR (xvs.REF_ID = opr.PERSONAL_ID AND xvs.VER_LEVEL = 'P') ");
		sql.append(							") ");
		sql.append(		               	"JOIN ORIG_APP.XRULES_POLICY_RULES xpr ON xpr.VER_RESULT_ID = xvs.VER_RESULT_ID ");
		sql.append(		               	"JOIN ORIG_APP.XRULES_OR_POLICY_RULES xor ON xor.POLICY_RULES_ID = xpr.POLICY_RULES_ID ");
		sql.append(		           	"WHERE ag.JOB_STATE IN ('").append(ORSummaryDAOImpl.JOB_STATE_APPROVE).append("','").append(ORSummaryDAOImpl.JOB_STATE_POSTAPPROVE).append("') ");
		sql.append(							"AND ag.CREATE_DATE < to_date('").append(dateAfterToDate).append("','YYYY-MM-DD') ");
		sql.append(					") t ON t.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID AND t.POLICY_CODE = xor.POLICY_CODE ");
		sql.append(		       	"LEFT JOIN ( ");
										// Subquery to find application group which has ever been in approve status and latest date of approve status
		sql.append(						"SELECT MAX(al.CREATE_DATE) DE2_SUBMIT_DATE, al.APPLICATION_GROUP_ID ");
		sql.append(		           	"FROM ORIG_APP.ORIG_APPLICATION_LOG al ");
		sql.append(							"JOIN ORIG_APP.ORIG_APPLICATION_GROUP ag ON ag.APPLICATION_GROUP_ID = al.APPLICATION_GROUP_ID ");
		sql.append(							"JOIN ORIG_APP.ORIG_APPLICATION ap ON ap.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID ");
		sql.append(		           	"WHERE al.JOB_STATE IN ('").append(ORSummaryDAOImpl.JOB_STATE_APPROVE).append("') ");
		sql.append(							"AND al.CREATE_DATE < to_date('").append(dateAfterToDate).append("','YYYY-MM-DD') ");
		sql.append(		           	"GROUP BY al.APPLICATION_GROUP_ID ");
		sql.append(					") agl ON agl.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID ");
		sql.append(		       	"LEFT JOIN ( ");
										// Subquery to find application group which has ever been in reject status and latest date of reject status
		sql.append(						"SELECT MAX(al.CREATE_DATE) DE2_SUBMIT_DATE, al.APPLICATION_GROUP_ID ");
		sql.append(						"FROM ORIG_APP.ORIG_APPLICATION_LOG al ");
		sql.append(		              	"JOIN ORIG_APP.ORIG_APPLICATION ap ON ap.APPLICATION_GROUP_ID = al.APPLICATION_GROUP_ID ");
		sql.append(						"WHERE al.JOB_STATE IN ('").append(ORSummaryDAOImpl.JOB_STATE_REJECT).append("') ");
		sql.append(							"AND al.CREATE_DATE < to_date('").append(dateAfterToDate).append("','YYYY-MM-DD') ");
		sql.append(						"GROUP BY al.APPLICATION_GROUP_ID ");
		sql.append(					") aglr ON aglr.APPLICATION_GROUP_ID = ag.APPLICATION_GROUP_ID ");
		sql.append(		       	"JOIN ORIG_APP.BUSINESS_CLASS bu ON bu.BUS_CLASS_ID = ap.BUSINESS_CLASS_ID ");
		sql.append(				"WHERE 1=1 ");
		sql.append(					"AND ag.APPLICATION_GROUP_NO IS NOT NULL ");
//		sql.append(					"AND ag.UPDATE_DATE BETWEEN to_date('").append(fromDate).append("','YYYY-MM-DD') AND to_date('").append(toDate).append(" 23:59:59','YYYY-MM-DD HH24:MI:SS') ");
		sql.append(		") ");
		sql.append(		"PIVOT ( "); 
		sql.append(		   "COUNT(OR_CODE) FOR OR_CODE in (  1 OR_01,  2 OR_02,  3 OR_03,  4 OR_04,  5 OR_05,  6 OR_06,  7 OR_07,  8 OR_08,  9 OR_09, 10 OR_10, ");
		sql.append(									  	   "11 OR_11, 12 OR_12, 13 OR_13, 14 OR_14, 15 OR_15, 16 OR_16, 17 OR_17, 18 OR_18, 19 OR_19, 20 OR_20, ");
		sql.append(		                           		   "21 OR_21, 22 OR_22, 23 OR_23, 24 OR_24, 25 OR_25) ");
		sql.append(		") ");
		sql.append(") NEW ");
		sql.append("ON (S.QR_NO = NEW.QR_NO AND S.PRODUCT = NEW.PRODUCT) ");
		sql.append("WHEN MATCHED THEN ");
		sql.append("UPDATE SET ");
		sql.append(		"S.APP_IN_PERIOD  = NEW.APP_IN_PERIOD, ");
		sql.append(		"S.APPROVE_PERIOD = NEW.APPROVE_PERIOD, ");
		sql.append(		"S.REJECT_PERIOD  = NEW.REJECT_PERIOD, ");
		sql.append(		"S.EXCEPTION_FLAG = NEW.EXCEPTION_FLAG, ");
		sql.append(		"S.OR_01          = NEW.OR_01, ");
		sql.append(		"S.OR_02          = NEW.OR_02, ");
		sql.append(		"S.OR_03          = NEW.OR_03, ");
		sql.append(		"S.OR_04          = NEW.OR_04, ");
		sql.append(		"S.OR_05          = NEW.OR_05, ");
		sql.append(		"S.OR_06          = NEW.OR_06, ");
		sql.append(		"S.OR_07          = NEW.OR_07, ");
		sql.append(		"S.OR_08          = NEW.OR_08, ");
		sql.append(		"S.OR_09          = NEW.OR_09, ");
		sql.append(		"S.OR_10          = NEW.OR_10, ");
		sql.append(		"S.OR_11          = NEW.OR_11, ");
		sql.append(		"S.OR_12          = NEW.OR_12, ");
		sql.append(		"S.OR_13          = NEW.OR_13, ");
		sql.append(		"S.OR_14          = NEW.OR_14, ");
		sql.append(		"S.OR_15          = NEW.OR_15, ");
		sql.append(		"S.OR_16          = NEW.OR_16, ");
		sql.append(		"S.OR_17          = NEW.OR_17, ");
		sql.append(		"S.OR_18          = NEW.OR_18, ");
		sql.append(		"S.OR_19          = NEW.OR_19, ");
		sql.append(		"S.OR_20          = NEW.OR_20, ");
		sql.append(		"S.OR_21          = NEW.OR_21, ");
		sql.append(		"S.OR_22          = NEW.OR_22, ");
		sql.append(		"S.OR_23          = NEW.OR_23, ");
		sql.append(		"S.OR_24          = NEW.OR_24, ");
		sql.append(		"S.OR_25          = NEW.OR_25, ");
		sql.append(		"S.UPDATE_DATE    = SYSDATE, ");
		sql.append(		"S.UPDATE_BY      = 'SYSTEM' ");
		sql.append("WHEN NOT MATCHED THEN ");
		sql.append("INSERT ( ");
		sql.append(		"S.QR_NO,S.APP_IN_PERIOD,S.APPROVE_PERIOD,S.REJECT_PERIOD,S.PRODUCT,S.EXCEPTION_FLAG, ");
		sql.append(		"S.OR_01,S.OR_02,S.OR_03,S.OR_04,S.OR_05,S.OR_06,S.OR_07,S.OR_08,S.OR_09,S.OR_10, ");
		sql.append(		"S.OR_11,S.OR_12,S.OR_13,S.OR_14,S.OR_15,S.OR_16,S.OR_17,S.OR_18,S.OR_19,S.OR_20, ");
		sql.append(		"S.OR_21,S.OR_22,S.OR_23,S.OR_24,S.OR_25, ");
		sql.append(		"S.CREATE_DATE,S.CREATE_BY,S.UPDATE_DATE,S.UPDATE_BY ");
		sql.append(") ");
		sql.append("VALUES ( ");
		sql.append(		"NEW.QR_NO,NEW.APP_IN_PERIOD,NEW.APPROVE_PERIOD,NEW.REJECT_PERIOD,NEW.PRODUCT,NEW.EXCEPTION_FLAG, ");
		sql.append(		"NEW.OR_01,NEW.OR_02,NEW.OR_03,NEW.OR_04,NEW.OR_05,NEW.OR_06,NEW.OR_07,NEW.OR_08,NEW.OR_09,NEW.OR_10, ");
		sql.append(		"NEW.OR_11,NEW.OR_12,NEW.OR_13,NEW.OR_14,NEW.OR_15,NEW.OR_16,NEW.OR_17,NEW.OR_18,NEW.OR_19,NEW.OR_20, ");
		sql.append(		"NEW.OR_21,NEW.OR_22,NEW.OR_23,NEW.OR_24,NEW.OR_25, ");
		sql.append(		"SYSDATE,'SYSTEM',SYSDATE,'SYSTEM' ");
		sql.append(") ");

		return String.valueOf(sql);
	}
*/	
	
	public String getDeleteOrSummarySQL() {
		StringBuilder sql = new StringBuilder();
//		sql.append("DELETE FROM OR_SUM WHERE QR_NO IN ( ");
//		sql.append(		"SELECT APPLICATION_GROUP_NO FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_NO IS NOT NULL ");
		sql.append("DELETE FROM OR_SUM WHERE GRP_ID IN ( ");
		sql.append(		"SELECT APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP");
		sql.append(")");

		return String.valueOf(sql);
	}

	
	/**
	 * Repopulate override usage per application into OR_SUM table. The data is gathered starting from currentDate 
	 * and backward to n months as indicate by orMonths variable. This method does not perform commit, so calling has to
	 * handle commit/rollback.
	 * 
	 * @param:		currentDate		is starting date to gather override usage
	 * 				conn			connection to database
	 */
	public void refreshOrSummary(Date currentDate, Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String fromDate = df.format(ORSummaryDAOImpl.dateFromReferenceDate(currentDate, false, true, 1, false, 1-ORSummaryDAOImpl.orMonths, false, 0));
//		String toDate = df.format(currentDate);
//		String dateAfterToDate = df.format(ORSummaryDAOImpl.dateFromReferenceDate(currentDate, false, false, 1, false, 0, false, 0));
//		logger.debug("fromDate="+fromDate+", toDate="+toDate+", dateAfterToDate"+dateAfterToDate);
		
		try{
			String dSql;
			int result;
			// Truncate table
//			dSql = "TRUNCATE TABLE OR_SUM";
//			logger.debug("Truncate table OR_SUM. Sql=" + dSql);
//			ps = conn.prepareStatement(dSql);
//			ps.executeUpdate();
			// Delete QR from OR_SUM
			dSql = getDeleteOrSummarySQL();
			logger.debug("Delete QR from table OR_SUM. Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			result = ps.executeUpdate();
			ps.close();
			logger.info(result + " rows have been deleted from OR_SUM");

			
			// Insert records
//			dSql = getOrSummarySQL(fromDate, toDate, dateAfterToDate);
			dSql = getOrSummarySQL();
			logger.debug("Insert table OR_SUM. Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			result = ps.executeUpdate();			
			logger.info(result + " rows have been inserted into OR_SUM");
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	

	public String getOrPeriodSummarySQL(String period, String product) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT period.PRODUCT, period.PERIOD, appin.TOTAL_APP_IN, rej.TOTAL_REJECT, apr.TOTAL_APPROVE, ");
		sql.append(		"or_apr.TOTAL_OR, or_apr.TOTAL_OR_EXEMPT, or_apr.TOTAL_OR_NONLOWSIDE, or_apr.TOTAL_OR_010203, TOTAL_OR_04, TOTAL_OR_XX, ");
		sql.append(		"or_apr.OR_01, or_apr.OR_02, or_apr.OR_03, or_apr.OR_04, or_apr.OR_05, or_apr.OR_06, or_apr.OR_07, or_apr.OR_08, or_apr.OR_09, or_apr.OR_10, ");
		sql.append(		"or_apr.OR_11, or_apr.OR_12, or_apr.OR_13, or_apr.OR_14, or_apr.OR_15, or_apr.OR_16, or_apr.OR_17, or_apr.OR_18, or_apr.OR_19, or_apr.OR_20, ");
		sql.append(		"or_apr.OR_21, or_apr.OR_22, or_apr.OR_23, or_apr.OR_24, or_apr.OR_25 ");
		sql.append("FROM ");
		sql.append(     "( ");
		sql.append(        "SELECT DISTINCT APPROVE_PERIOD AS PERIOD, PRODUCT FROM OR_SUM WHERE APPROVE_PERIOD IS NOT NULL ");
		sql.append(        "UNION ");
		sql.append(        "SELECT DISTINCT REJECT_PERIOD AS PERIOD, PRODUCT FROM OR_SUM WHERE REJECT_PERIOD IS NOT NULL ");
		sql.append(     ") period ");
		sql.append(		"LEFT JOIN (SELECT APP_IN_PERIOD, PRODUCT, COUNT(*) AS TOTAL_APP_IN FROM OR_SUM WHERE APP_IN_PERIOD IS NOT NULL GROUP BY APP_IN_PERIOD, PRODUCT) appin ON appin.APP_IN_PERIOD=period.PERIOD and appin.PRODUCT=period.PRODUCT ");
		sql.append(    	"LEFT JOIN (SELECT REJECT_PERIOD, PRODUCT, COUNT(*) AS TOTAL_REJECT FROM OR_SUM WHERE REJECT_PERIOD IS NOT NULL GROUP BY REJECT_PERIOD, PRODUCT) rej ON rej.REJECT_PERIOD=period.PERIOD and rej.PRODUCT=period.PRODUCT ");
		sql.append(		"LEFT JOIN (SELECT APPROVE_PERIOD, PRODUCT, COUNT(*) AS TOTAL_APPROVE FROM OR_SUM WHERE APPROVE_PERIOD IS NOT NULL GROUP BY APPROVE_PERIOD, PRODUCT) apr ON apr.APPROVE_PERIOD=period.PERIOD and apr.PRODUCT=period.PRODUCT ");
		sql.append(		"LEFT JOIN ( ");
		sql.append(			"SELECT ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' THEN 1 END) AS TOTAL_OR, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='Y' THEN 1 END) AS TOTAL_OR_EXEMPT, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND (OR_02+OR_03+OR_04+OR_05+OR_06+OR_07+OR_08+OR_09+OR_10+OR_11+OR_12+OR_13+OR_14+OR_15+OR_16+OR_17+OR_18+OR_19+OR_20+OR_21+OR_22+OR_23+OR_24+OR_25) > 0 THEN 1 END) AS TOTAL_OR_NONLOWSIDE, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND (OR_01+OR_02+OR_03) > 0 THEN 1 END) AS TOTAL_OR_010203, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND (OR_04) > 0 THEN 1 END) AS TOTAL_OR_04, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND (OR_05+OR_06+OR_07+OR_08+OR_09+OR_10+OR_11+OR_12+OR_13+OR_14+OR_15+OR_16+OR_17+OR_18+OR_19+OR_20+OR_21+OR_22+OR_23+OR_24+OR_25) > 0 THEN 1 END) AS TOTAL_OR_XX, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_01 > 0 THEN OR_01 END) OR_01, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_02 > 0 THEN OR_02 END) OR_02, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_03 > 0 THEN OR_03 END) OR_03, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_04 > 0 THEN OR_04 END) OR_04, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_05 > 0 THEN OR_05 END) OR_05, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_06 > 0 THEN OR_06 END) OR_06, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_07 > 0 THEN OR_07 END) OR_07, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_08 > 0 THEN OR_08 END) OR_08, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_09 > 0 THEN OR_09 END) OR_09, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_10 > 0 THEN OR_10 END) OR_10, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_11 > 0 THEN OR_11 END) OR_11, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_12 > 0 THEN OR_12 END) OR_12, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_13 > 0 THEN OR_13 END) OR_13, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_14 > 0 THEN OR_14 END) OR_14, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_15 > 0 THEN OR_15 END) OR_15, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_16 > 0 THEN OR_16 END) OR_16, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_17 > 0 THEN OR_17 END) OR_17, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_18 > 0 THEN OR_18 END) OR_18, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_19 > 0 THEN OR_19 END) OR_19, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_20 > 0 THEN OR_20 END) OR_20, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_21 > 0 THEN OR_21 END) OR_21, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_22 > 0 THEN OR_22 END) OR_22, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_23 > 0 THEN OR_23 END) OR_23, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_24 > 0 THEN OR_24 END) OR_24, ");
		sql.append(				"COUNT(CASE WHEN EXCEPTION_FLAG='N' AND OR_25 > 0 THEN OR_25 END) OR_25, ");
		sql.append(            	"APPROVE_PERIOD AS OR_APPROVE_PERIOD, PRODUCT AS OR_PRODUCT ");
		sql.append(			"FROM OR_SUM  ");
		sql.append(			"WHERE APPROVE_PERIOD IS NOT NULL ");
		sql.append(				"AND (OR_01+OR_02+OR_03+OR_04+OR_05+OR_06+OR_07+OR_08+OR_09+OR_10+OR_11+OR_12+OR_13+OR_14+OR_15+OR_16+OR_17+OR_18+OR_19+OR_20+OR_21+OR_22+OR_23+OR_24+OR_25) > 0 ");
		sql.append(			"GROUP BY APPROVE_PERIOD, PRODUCT ");
		sql.append(		") or_apr ON or_apr.OR_APPROVE_PERIOD=period.PERIOD AND or_apr.OR_PRODUCT=period.PRODUCT ");
		sql.append("WHERE 1=1 ");
		if (period != null)
			sql.append("AND period.PERIOD='").append(period).append("' ");
		if (product != null)
			sql.append("AND period.PRODUCT='").append(product).append("' ");
		sql.append("ORDER BY period.PERIOD DESC, period.PRODUCT ");
		return String.valueOf(sql);
	}
	
	
	/**
	 * Update override usage per period into OR_PERIOD_SUM table. All data in OR_SUM table
	 * are summarized into period but only the latest n periods (n is determined from MAX_UPDATE_PERIOD) 
	 * will be updated into OR_PERIOD_SUM. This method does not perform commit, so calling has to
	 * handle commit/rollback.
	 * 
	 * @param:		conn			connection to database
	 */
	public void accumOrPeriodSummary(Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement psupd = null;
		PreparedStatement psins = null;
		int index, rowCount;
		ArrayList<String> updatePeriod = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		String dSql; 
		String msg=null;
		
		try {
			// Prepare SQL for update
			sql.append("UPDATE OR_PERIOD_SUM SET ");
			sql.append(		"APP_IN=?, APPROVE=?, REJECT=?, WITH_OR=?, WITH_EXEMPT_OR=?, WITH_NONLOWSIDE_OR=?, WITH_OR_010203=?, WITH_OR_04=?, WITH_OR_XX=?, ");
			sql.append(		"OR_01=?, OR_02=?, OR_03=?, OR_04=?, OR_05=?, OR_06=?, OR_07=?, OR_08=?, OR_09=?, OR_10=?, ");
			sql.append(		"OR_11=?, OR_12=?, OR_13=?, OR_14=?, OR_15=?, OR_16=?, OR_17=?, OR_18=?, OR_19=?, OR_20=?, ");
			sql.append(		"OR_21=?, OR_22=?, OR_23=?, OR_24=?, OR_25=?, ");
			sql.append(		"UPDATE_DATE=SYSDATE, UPDATE_BY='SYSTEM' ");
			sql.append(	"WHERE PERIOD=? AND PRODUCT=? ");
			dSql = String.valueOf(sql);
			logger.debug("Update OR_PERIOD_SUM. Sql=" + dSql);
			psupd = conn.prepareStatement(dSql);

			// Prepare SQL for insert
			sql.setLength(0);
			sql.append("INSERT INTO OR_PERIOD_SUM( ");
			sql.append(		"PERIOD, APP_IN, APPROVE, REJECT, WITH_OR, PRODUCT, WITH_EXEMPT_OR, WITH_NONLOWSIDE_OR, WITH_OR_010203, WITH_OR_04, WITH_OR_XX, ");
			sql.append(		"OR_01, OR_02, OR_03, OR_04, OR_05, OR_06, OR_07, OR_08, OR_09, OR_10, ");
			sql.append(		"OR_11, OR_12, OR_13, OR_14, OR_15, OR_16, OR_17, OR_18, OR_19, OR_20, ");
			sql.append(		"OR_21, OR_22, OR_23, OR_24, OR_25, ");
			sql.append(		"CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY "); 
			sql.append(") VALUES ( ");
			sql.append(		"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sql.append(		"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sql.append(		"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sql.append(		"?, ?, ?, ?, ?, ");
			sql.append(		"SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM' ");
			sql.append(") ");
			dSql = String.valueOf(sql);
			logger.debug("Insert to OR_PERIOD_SUM. Sql=" + dSql);
			psins = conn.prepareStatement(dSql);
			
			// Generate summary of OR by period
			dSql = getOrPeriodSummarySQL(null, null);
			logger.debug("Summarize from table OR_SUM. Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = ps.executeQuery();			
			logger.debug("Summarize from table OR_SUM done");

			// Update to OR_PERIOD_SUM
			while (rs.next()) {
				String period = rs.getString("PERIOD");
				String product = rs.getString("PRODUCT");
				long app_in = rs.getLong("TOTAL_APP_IN");
				long approve = rs.getLong("TOTAL_APPROVE");
				long approveWithOR = rs.getLong("TOTAL_OR");
				long reject = rs.getLong("TOTAL_REJECT");
				long orExempt = rs.getLong("TOTAL_OR_EXEMPT");
				long orNonLowSide = rs.getLong("TOTAL_OR_NONLOWSIDE");
				long or_010203 = rs.getLong("TOTAL_OR_010203");
				long or_04 = rs.getLong("TOTAL_OR_04");
				long or_XX = rs.getLong("TOTAL_OR_XX");
				long or01 = rs.getLong("OR_01");
				long or02 = rs.getLong("OR_02");
				long or03 = rs.getLong("OR_03");
				long or04 = rs.getLong("OR_04");
				long or05 = rs.getLong("OR_05");
				long or06 = rs.getLong("OR_06");
				long or07 = rs.getLong("OR_07");
				long or08 = rs.getLong("OR_08");
				long or09 = rs.getLong("OR_09");
				long or10 = rs.getLong("OR_10");
				long or11 = rs.getLong("OR_11");
				long or12 = rs.getLong("OR_12");
				long or13 = rs.getLong("OR_13");
				long or14 = rs.getLong("OR_14");
				long or15 = rs.getLong("OR_15");
				long or16 = rs.getLong("OR_16");
				long or17 = rs.getLong("OR_17");
				long or18 = rs.getLong("OR_18");
				long or19 = rs.getLong("OR_19");
				long or20 = rs.getLong("OR_20");
				long or21 = rs.getLong("OR_21");
				long or22 = rs.getLong("OR_22");
				long or23 = rs.getLong("OR_23");
				long or24 = rs.getLong("OR_24");
				long or25 = rs.getLong("OR_25");
				
				// Control number of periods to be updated
				if (!updatePeriod.contains(period) && updatePeriod.size() >= ORSummaryDAOImpl.MAX_UPDATE_PERIOD)
					break;
				
				// Try to update first
				msg = "Update OR_PERIOD_SUM for period=" + period + ", product=" + product;
				index = 1;
				psupd.clearParameters();
				psupd.setLong(index++, app_in);
				psupd.setLong(index++, approve);
				psupd.setLong(index++, reject);
				psupd.setLong(index++, approveWithOR);
				psupd.setLong(index++, orExempt);
				psupd.setLong(index++, orNonLowSide);
				psupd.setLong(index++, or_010203);
				psupd.setLong(index++, or_04);
				psupd.setLong(index++, or_XX);
				psupd.setLong(index++, or01);
				psupd.setLong(index++, or02);
				psupd.setLong(index++, or03);
				psupd.setLong(index++, or04);
				psupd.setLong(index++, or05);
				psupd.setLong(index++, or06);
				psupd.setLong(index++, or07);
				psupd.setLong(index++, or08);
				psupd.setLong(index++, or09);
				psupd.setLong(index++, or10);
				psupd.setLong(index++, or11);
				psupd.setLong(index++, or12);
				psupd.setLong(index++, or13);
				psupd.setLong(index++, or14);
				psupd.setLong(index++, or15);
				psupd.setLong(index++, or16);
				psupd.setLong(index++, or17);
				psupd.setLong(index++, or18);
				psupd.setLong(index++, or19);
				psupd.setLong(index++, or20);
				psupd.setLong(index++, or21);
				psupd.setLong(index++, or22);
				psupd.setLong(index++, or23);
				psupd.setLong(index++, or24);
				psupd.setLong(index++, or25);
				psupd.setString(index++, period);
				psupd.setString(index++, product);
//				logger.debug("Period=" + period + ", or01-03=" + or_010203 + ", or04=" + or_04 + ", orXX=" + or_XX);
				rowCount = psupd.executeUpdate();
				logger.info(msg + " => " + rowCount + " rows updated");

				if (rowCount == 0) {
					// Try to insert
					msg = "Insert OR_PERIOD_SUM for period=" + period + ", product=" + product;
					index = 1;
					psins.clearParameters();
					psins.setString(index++, period);
					psins.setLong(index++, app_in);
					psins.setLong(index++, approve);
					psins.setLong(index++, reject);
					psins.setLong(index++, approveWithOR);
					psins.setString(index++, product);
					psins.setLong(index++, orExempt);
					psins.setLong(index++, orNonLowSide);
					psins.setLong(index++, or_010203);
					psins.setLong(index++, or_04);
					psins.setLong(index++, or_XX);
					psins.setLong(index++, or01);
					psins.setLong(index++, or02);
					psins.setLong(index++, or03);
					psins.setLong(index++, or04);
					psins.setLong(index++, or05);
					psins.setLong(index++, or06);
					psins.setLong(index++, or07);
					psins.setLong(index++, or08);
					psins.setLong(index++, or09);
					psins.setLong(index++, or10);
					psins.setLong(index++, or11);
					psins.setLong(index++, or12);
					psins.setLong(index++, or13);
					psins.setLong(index++, or14);
					psins.setLong(index++, or15);
					psins.setLong(index++, or16);
					psins.setLong(index++, or17);
					psins.setLong(index++, or18);
					psins.setLong(index++, or19);
					psins.setLong(index++, or20);
					psins.setLong(index++, or21);
					psins.setLong(index++, or22);
					psins.setLong(index++, or23);
					psins.setLong(index++, or24);
					psins.setLong(index++, or25);
					rowCount = psins.executeUpdate();
					if (rowCount == 1)
						logger.info(msg + " => " + rowCount + " rows inserted");
					else {
						logger.error(msg + " => " + rowCount + " rows inserted");
						break;
					}
				}
				if (!updatePeriod.contains(period))
					updatePeriod.add(period);
			}
			logger.info("OR_PERIOD_SUM has been updated (max period=" + ORSummaryDAOImpl.MAX_UPDATE_PERIOD + ") for period: " + updatePeriod);
		} catch (Exception e) {
			logger.fatal("Exception raised while " + msg);
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps, rs);
				closeConnection(psupd);
				closeConnection(psins);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
/*	
	public static void main(String[] args) {
		Connection conn = null;
		ORSummaryDAO orsum = ORSummaryDAOFactory.getORSummaryDAO();
		try {
			// Create DB connection
			String username = "orig_app";
			String password = "passw0rd";
			String url = "jdbc:oracle:thin:@172.30.165.35:1525:ULODB";
			Properties connectionProps = new Properties();
		    connectionProps.put("user", username);
		    connectionProps.put("password", password);
			conn = DriverManager.getConnection(url, connectionProps);
			conn.setAutoCommit(false);
			Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-30");
			orsum.refreshOrSummary(currentDate, conn);
			conn.commit();
			orsum.accumOrPeriodSummary(conn);
			conn.commit();
		}
		catch (Exception e) {
			System.err.println("Error while connect to database. Exception is:" + e);
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			conn = null;
		}

	}
*/
}
