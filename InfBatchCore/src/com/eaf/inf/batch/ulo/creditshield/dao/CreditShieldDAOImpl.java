package com.eaf.inf.batch.ulo.creditshield.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;

public class CreditShieldDAOImpl extends InfBatchObjectDAO implements CreditShieldDAO {
	private static transient Logger logger = Logger.getLogger(CreditShieldDAOImpl.class);
	private static final String JOB_STATE_POSTAPPROVE = InfBatchProperty.getInfBatchConfig("REFRESH_SUMMARY_TABLE_JOB_STATE_POSTAPPROVED");

	private String getRefreshConditionSQL(String jobState, String period) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT AG.APPLICATION_GROUP_ID ");
		sql.append("FROM ");
		sql.append(		"ORIG_APPLICATION_GROUP AG ");
		sql.append("JOIN ");
		sql.append(		"ORIG_APPLICATION_LOG AGL ");
		sql.append(		"ON AGL.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID AND AGL.JOB_STATE='").append(jobState).append("' ");
		sql.append(			"AND TO_CHAR(AGL.CREATE_DATE,'YYYYMM')='").append(period).append("' AND AGL.LIFE_CYCLE=AG.LIFE_CYCLE ");
		sql.append("WHERE ");
		sql.append(		"AG.APPLICATION_GROUP_ID IS NOT NULL ");
		sql.append(		"AND AG.JOB_STATE='").append(jobState).append("' ");
		
		return sql.toString();
	}
	
	private String getDeleteAppGrpSumSQL(String jobState, String period) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("DELETE FROM APP_GRP_SUM WHERE GRP_ID IN (");
		sql.append(getRefreshConditionSQL(jobState, period));
		sql.append(")");
		
		return sql.toString();
	}
	
	/*
	 * As merge statement is quite slow, then we use delete and insert.
	 * 
	 * PLS MAKE SURE THAT DELETE AND INSERT CONDITION ARE THE SAME!!!!
	 */
	private String getInsertAppGrpSumSQL(String jobState, String period) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO APP_GRP_SUM( ");
		sql.append(		"GRP_ID, QR_NO, LIFE_CYCLE, APP_TYPE,  ");
		sql.append(		"SALE_RC_NO, SALE_BR_NO, SALE_BR_NAME, SALE_BR_REGION, SALE_BR_ZONE, SALE_CHANNEL, SALE_ID, SALE_NAME, ");
		sql.append(		"REFR_RC_NO, REFR_BR_NO, REFR_BR_NAME, REFR_BR_REGION, REFR_BR_ZONE, REFR_CHANNEL, REFR_ID, REFR_NAME, ");
		sql.append(		"APP_CREATE_DATE, APP_APPLY_DATE, APP_SCAN_DATE, ");
		sql.append(		"CUST_NAME_1, CUST_LASTNAME_1, CUST_NAME_2, CUST_LASTNAME_2, ");
		sql.append(		"CARD_SETUP_DATE, ");
		sql.append(		"CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE ");
		sql.append(") ");
		sql.append("SELECT ");
		sql.append(		"AG.APPLICATION_GROUP_ID, ");
		sql.append(		"AG.APPLICATION_GROUP_NO, ");
		sql.append(		"AG.LIFE_CYCLE, ");
		sql.append(		"DECODE(AG.APPLICATION_TYPE,'NEW','New','SUP','New','Existing') APPLICATION_TYPE, ");
		sql.append(		"SL1.SALES_RC_CODE SALES_RC_NO, ");
		sql.append(		"SL1.SALES_BRANCH_CODE SALES_BRANCH_NO, ");
		sql.append(		"SL1.SALES_BRANCH_NAME SALES_BRANCH_NAME, ");
		sql.append(		"SL1.REGION SALES_BRANCH_REGION, ");
		sql.append(		"SL1.ZONE SALES_BRANCH_ZONE, ");
		sql.append(		"SL1.SALE_CHANNEL, ");
		sql.append(		"SL1.SALES_ID, ");
		sql.append(		"SL1.SALES_NAME, ");
		sql.append(		"SL2.SALES_RC_CODE REFER_RC_NO, ");
		sql.append(		"SL2.SALES_BRANCH_CODE REFER_BRANCH_NO, ");
		sql.append(		"SL2.SALES_BRANCH_NAME REFER_BRANCH_NAME, ");
		sql.append(		"SL2.REGION REFER_BRANCH_REGION, ");
		sql.append(		"SL2.ZONE REFER_BRANCH_ZONE, ");
		sql.append(		"SL2.SALE_CHANNEL REFER_CHANNEL, ");
		sql.append(		"SL2.SALES_ID REFER_ID, ");
		sql.append(		"SL2.SALES_NAME REFER_NAME, ");
		sql.append(		"AG.CREATE_DATE ON_SCREEN_DATE, ");
		sql.append(		"AG.APPLY_DATE,  ");
		sql.append(		"AG.APPLICATION_DATE, ");
		sql.append(		"MPSN.TH_FIRST_NAME MAIN_CUST_NAME, MPSN.TH_LAST_NAME MAIN_CUST_LASTNAME, ");
		sql.append(		"SPSN.TH_FIRST_NAME SUP_CUST_NAME, SPSN.TH_LAST_NAME SUP_CUST_LASTNAME, ");
		sql.append(		"AGL.CREATE_DATE CARD_GENDATE, ");
		sql.append(		"'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE ");
		sql.append("FROM ");
		sql.append(		"ORIG_APPLICATION_GROUP AG ");
		sql.append("JOIN ");
		sql.append(		"ORIG_APPLICATION_LOG AGL ");
		sql.append(		"ON AGL.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID AND AGL.JOB_STATE='").append(jobState).append("' ");
		sql.append(			"AND TO_CHAR(AGL.CREATE_DATE,'YYYYMM')='").append(period).append("' AND AGL.LIFE_CYCLE=AG.LIFE_CYCLE ");
		sql.append("LEFT JOIN ");
		sql.append(		"ORIG_PERSONAL_INFO MPSN ");
		sql.append(		"ON MPSN.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID AND MPSN.PERSONAL_TYPE='A' ");
		sql.append("LEFT JOIN ");
		sql.append(		"ORIG_PERSONAL_INFO SPSN ");
		sql.append(		"ON SPSN.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID AND SPSN.PERSONAL_TYPE='S' ");
		sql.append("LEFT JOIN ");
		// Select first record of each sale type (sometimes we have more than 1 records for each sale type) ");
		sql.append(		"( ");
		sql.append(			"SELECT * ");
		sql.append(			"FROM ");
		sql.append(			"(	 ");
		sql.append(				"SELECT ");
		sql.append(					"ROW_NUMBER() OVER (PARTITION BY APPLICATION_GROUP_ID, SALES_TYPE ORDER BY SALES_INFO_ID) RN, ORIG_SALE_INFO.* ");
		sql.append(				"FROM ");
		sql.append(					"ORIG_SALE_INFO ");
		sql.append(				"WHERE SALES_TYPE='01' ");
		sql.append(			") SL ");
		sql.append(			"WHERE SL.RN=1 ");
		sql.append(		") SL1 ON SL1.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID ");
		// Select first record of each sale type (sometimes we have more than 1 records for each sale type) ");
		sql.append("LEFT JOIN ");
		sql.append(		"( ");
		sql.append(			"SELECT * ");
		sql.append(			"FROM ");
		sql.append(			"(	 ");
		sql.append(				"SELECT ");
		sql.append(					"ROW_NUMBER() OVER (PARTITION BY APPLICATION_GROUP_ID, SALES_TYPE ORDER BY SALES_INFO_ID) RN, ORIG_SALE_INFO.* ");
		sql.append(				"FROM ");
		sql.append(					"ORIG_SALE_INFO ");
		sql.append(				"WHERE SALES_TYPE='02' ");
		sql.append(			") SL ");
		sql.append(			"WHERE SL.RN=1 ");
		sql.append(		") SL2 ON SL2.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID ");
		sql.append("WHERE ");
		sql.append(		"AG.APPLICATION_GROUP_ID IS NOT NULL ");
		sql.append(		"AND AG.JOB_STATE='").append(jobState).append("' ");
		
		return sql.toString();
	}

	private String getDeleteAppSvcSumSQL(String jobState, String period) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("DELETE FROM APP_SVC_SUM WHERE GRP_ID IN (");
		sql.append(getRefreshConditionSQL(jobState, period));
		sql.append(")");
		
		return sql.toString();
	}

	private String getInsertAppSvcSumSQL(String jobState, String period) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO APP_SVC_SUM( ");
		sql.append(		"CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
		sql.append(		"GRP_ID, LOAN_ID, CARDLINK_REF_NO, CARDLINK_SEQ, PROJECT_CODE, CARD_LEVEL, ");
		sql.append(		"SPENDING_ALERT, DUE_ALERT, EMAIL_STMT, CREDIT_SHIELD ");
		sql.append(") ");
		sql.append("SELECT SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM', SVC.* ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append(		"SELECT  ");
		sql.append(			"AG.GRP_ID, LN.LOAN_ID, AP.CARDLINK_REF_NO, AP.CARDLINK_SEQ, AP.PROJECT_CODE, CT.CARD_LEVEL, SVC.SERVICE_TYPE ");
		sql.append(		"FROM ");
		sql.append(			"APP_GRP_SUM AG ");
		sql.append(		"LEFT JOIN ");
		sql.append(			"APP_SVC_SUM SV ");
		sql.append(			"ON SV.GRP_ID=AG.GRP_ID ");
		sql.append(		"JOIN ");
		sql.append(			"ORIG_APPLICATION AP ");
		sql.append(			"ON AP.APPLICATION_GROUP_ID=AG.GRP_ID AND AP.LIFE_CYCLE=AG.LIFE_CYCLE AND AP.APPLICATION_TYPE IN ('NEW','ADD','UPG','SUP') AND AP.FINAL_APP_DECISION='Approve' ");
		sql.append(		"JOIN ");
		sql.append(			"BUSINESS_CLASS CLS ");
		sql.append(			"ON CLS.BUS_CLASS_ID=AP.BUSINESS_CLASS_ID AND CLS.ORG_ID='CC' ");
		sql.append(		"JOIN ");
		sql.append(			"ORIG_LOAN LN ");
		sql.append(			"ON LN.APPLICATION_RECORD_ID=AP.APPLICATION_RECORD_ID ");
		sql.append(		"JOIN ");
		sql.append(			"ORIG_CARD CRD ");
		sql.append(			"ON CRD.LOAN_ID=LN.LOAN_ID ");
		sql.append(		"LEFT JOIN ");
		sql.append(			"CARD_TYPE CT ");
		sql.append(			"ON CT.CARD_TYPE_ID=CRD.CARD_TYPE ");
		sql.append(		"LEFT JOIN ");
		sql.append(			"ORIG_ADDITIONAL_SERVICE_MAP SVCM ");
		sql.append(			"ON SVCM.LOAN_ID=LN.LOAN_ID ");
		sql.append(		"LEFT JOIN  ");
		sql.append(			"ORIG_ADDITIONAL_SERVICE SVC  ");
		sql.append(			"ON SVC.SERVICE_ID=SVCM.SERVICE_ID ");
		sql.append(		"WHERE  ");
		sql.append(			"SV.GRP_ID IS NULL ");			// Select grp_id in APP_GRP_SUM but not APP_SVC_SUM
		sql.append(") ");
		sql.append("PIVOT ");
		sql.append("( ");
		sql.append(		"COUNT(SERVICE_TYPE) FOR SERVICE_TYPE IN( '01' SPENDING_ALERT, '02' DUE_ALERT, '03' EMAIL_STMT, '04' CREDIT_SHIELD )  ");
		sql.append(") SVC ");
		
		return sql.toString();
	}
	
	@Override
	public void refreshCreditShieldSummary(Date currentDate, Connection conn) throws InfBatchException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		String period = df.format(currentDate);
		
		String deleteAppGrpSumSQL = getDeleteAppGrpSumSQL(JOB_STATE_POSTAPPROVE, period);
		String insertAppGrpSumSQL = getInsertAppGrpSumSQL(JOB_STATE_POSTAPPROVE, period);
		String deleteAppSvcSumSQL = getDeleteAppSvcSumSQL(JOB_STATE_POSTAPPROVE, period);
		String insertAppSvcSumSQL = getInsertAppSvcSumSQL(JOB_STATE_POSTAPPROVE, period);
		int result;
		try{
			// Refresh APP_GRP_SUM by delete and re-insert
			logger.debug("Delete QR from table APP_GRP_SUM. Sql=" + deleteAppGrpSumSQL);
			ps = conn.prepareStatement(deleteAppGrpSumSQL);
			result = ps.executeUpdate();
			ps.close();
			logger.info(result + " rows have been deleted from APP_GRP_SUM");
			logger.debug("Insert table APP_GRP_SUM. Sql=" + insertAppGrpSumSQL);
			ps = conn.prepareStatement(insertAppGrpSumSQL);
			result = ps.executeUpdate();			
			ps.close();
			logger.info(result + " rows have been inserted to APP_GRP_SUM");
			logger.info("Refresh table APP_GRP_SUM done");
			
			// Refresh APP_SVC_SUM by delete and re-insert
			logger.debug("Delete QR from table APP_SVC_SUM. Sql=" + deleteAppSvcSumSQL);
			ps = conn.prepareStatement(deleteAppSvcSumSQL);
			result = ps.executeUpdate();
			ps.close();
			logger.info(result + " rows have been deleted from APP_SVC_SUM");
			logger.debug("Insert table APP_SVC_SUM. Sql=" + insertAppSvcSumSQL);
			ps = conn.prepareStatement(insertAppSvcSumSQL);
			result = ps.executeUpdate();			
			ps.close();
			logger.info(result + " rows have been inserted to APP_SVC_SUM");
			logger.info("Refresh table APP_SVC_SUM done");
			
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
	
/*
	public static void main(String[] args) {
		Connection conn = null;
		CreditShieldDAO creditShield = CreditShieldDAOFactory.getCreditShieldDAO();
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
			Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-02-15");
			creditShield.refreshCreditShieldSummary(currentDate, conn);
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
