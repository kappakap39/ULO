package com.eaf.inf.batch.ulo.debt.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.debt.model.GenerateDebtDataM;
import com.eaf.inf.batch.ulo.debt.model.GenerateDebtReportDataM;

public class DebtDAOImpl extends InfBatchObjectDAO implements DebtDAO{
	private static transient Logger logger = Logger.getLogger(DebtDAOImpl.class);
	String PERSONAL_TYPE_APPLICANT = InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_SUPPLEMENTARY");
	String GENERATE_CONSENT_MAIN_APPLICANT = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MAIN_APPLICANT");
	String GENERATE_CONSENT_SUP_APPLICANT = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_SUP_APPLICANT");
	
	@Override
	public ArrayList<GenerateDebtDataM> getOutputDebt() throws InfBatchException {
		ArrayList<GenerateDebtDataM> generateDebts = new ArrayList<GenerateDebtDataM>();
		String GENERATE_DEBT_CUT_OFF_DATE = InfBatchProperty.getInfBatchConfig("GENERATE_DEBT_CUT_OFF_DATE");
		String GENERATE_DEBT_SOURCE_OF_APP = InfBatchProperty.getInfBatchConfig("GENERATE_DEBT_SOURCE_OF_APP");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
					SQL.append(" SELECT APPLICATION_GROUP_NO, JOB_STATE, CONSENT_REF_NO, PERSONAL_TYPE, CIS_NO, IDNO , SEQ, APPLICATION_GROUP_ID, PRODUCT, ");
					SQL.append(" 	VI, LIFE_CYCLE, APPROVE_AMOUNT, SUM(DEBT_AMT) OTHER_DEBT_AMT ");
					SQL.append(" FROM ( ");
					SQL.append(" SELECT ");
					SQL.append("     AG.APPLICATION_GROUP_NO APPLICATION_GROUP_NO,AG.JOB_STATE, ");
					SQL.append("     DH.CONSENT_REF_NO       CONSENT_REF_NO, ");
					SQL.append("     P.PERSONAL_TYPE         PERSONAL_TYPE, ");
					SQL.append("     P.CIS_NO                CIS_NO, ");
					SQL.append("     P.IDNO                  IDNO, ");
					SQL.append("     P.SEQ                   SEQ, ");
//					SQL.append("     NVL(SUM(DEBT.DEBT_AMT),0) OTHER_DEBT_AMT, ");
					SQL.append(" 	 NVL(DEBT.DEBT_AMT,0) DEBT_AMT, ");
					SQL.append("     AG.APPLICATION_GROUP_ID APPLICATION_GROUP_ID, ");
					SQL.append("     B.ORG_ID PRODUCT, ");
					SQL.append("     NVL(P.TOT_VERIFIED_INCOME,0) VI, ");
					SQL.append("     MAX(CASE ");
					SQL.append("		WHEN A.RECOMMEND_DECISION = 'RJ' ");
					SQL.append("		THEN 0 ");
					SQL.append("		ELSE NVL(LOAN.LOAN_AMT,0) ");
					SQL.append("	 END) APPROVE_AMOUNT, AG.LIFE_CYCLE, DEBT.DEBT_TYPE ");
					SQL.append(" FROM  ORIG_APPLICATION_GROUP AG ");
					SQL.append(" JOIN ORIG_PERSONAL_INFO P ON AG.APPLICATION_GROUP_ID = P.APPLICATION_GROUP_ID ");
					SQL.append(" JOIN  NCB_INFO DH ON  P.PERSONAL_ID = DH.PERSONAL_ID ");
					SQL.append(" JOIN ORIG_PERSONAL_RELATION R ON P.PERSONAL_ID = R.PERSONAL_ID AND R.PERSONAL_TYPE = '"+PERSONAL_TYPE_APPLICANT+"' ");
					SQL.append(" JOIN ORIG_APPLICATION A       ON R.REF_ID = A.APPLICATION_RECORD_ID ");
//					SQL.append(" JOIN  ORIG_APPLICATION A ON  AG.APPLICATION_GROUP_ID = A .APPLICATION_GROUP_ID ");
//					SQL.append(" AND A.LIFE_CYCLE = AG.LIFE_CYCLE ");
					SQL.append(" JOIN  BUSINESS_CLASS B ON  A.BUSINESS_CLASS_ID = B.BUS_CLASS_ID ");
					SQL.append(" LEFT JOIN ORIG_DEBT_INFO DEBT ON DEBT.PERSONAL_ID = P.PERSONAL_ID ");
					SQL.append(" JOIN ORIG_LOAN LOAN ON LOAN.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
					SQL.append(" JOIN ORIG_APPLICATION_LOG AL ON AG.APPLICATION_GROUP_ID = AL.APPLICATION_GROUP_ID AND AG.LIFE_CYCLE = AL.LIFE_CYCLE ");
					SQL.append(" AND AL.JOB_STATE IN ("+InfBatchProperty.getSQLParameter("GENERATE_DEBT_DE2_JOB_STATE")+") ");
					SQL.append(" WHERE AG.JOB_STATE IN ("+InfBatchProperty.getSQLParameter("GENERATE_DEBT_JOB_STATE")+") ");
					SQL.append(" AND AL.CREATE_DATE > TO_DATE(?,'yyyy-MM-dd HH24:MI:SS') ");
					SQL.append(" AND NOT EXISTS ( SELECT 1 FROM INF_BATCH_LOG L  WHERE  L.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
					SQL.append(" AND L.INTERFACE_CODE = '"+InfBatchProperty.getInfBatchConfig("GENERATE_DEBT_MODULE_ID")+"' ");
					SQL.append("	AND L.INTERFACE_STATUS='C' AND L.LIFE_CYCLE = AG.LIFE_CYCLE ) ");
					SQL.append(" AND AG.APPLICATION_TEMPLATE NOT IN ("+InfBatchProperty.getSQLParameter("GENERATE_DEBT_EXCEPT_TEMPLATE")+") AND DH.CONSENT_REF_NO IS NOT NULL ");
//					SQL.append(" AND AG.APPLICATION_TYPE <> 'ADD' ");
					SQL.append(" GROUP BY  ");
					SQL.append("    AG.APPLICATION_GROUP_NO ,AG.JOB_STATE, ");
					SQL.append("     DH.CONSENT_REF_NO, ");
					SQL.append("     P.PERSONAL_TYPE, ");
					SQL.append("     P.CIS_NO, ");
					SQL.append("     P.IDNO , ");
					SQL.append("     P.SEQ, ");
					SQL.append("     AG.APPLICATION_GROUP_ID, ");
					SQL.append("     B.ORG_ID, ");
					SQL.append("     P.TOT_VERIFIED_INCOME, AG.LIFE_CYCLE, DEBT.DEBT_AMT, DEBT.DEBT_TYPE ) A ");
					SQL.append(" GROUP BY APPLICATION_GROUP_NO, JOB_STATE, CONSENT_REF_NO, PERSONAL_TYPE, CIS_NO, IDNO , SEQ, APPLICATION_GROUP_ID, PRODUCT, ");
					SQL.append(" 	VI, LIFE_CYCLE, APPROVE_AMOUNT ");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			int index = 0;
			ps.setString(++index, GENERATE_DEBT_CUT_OFF_DATE);
			rs = ps.executeQuery();
			while(rs.next()){
				GenerateDebtDataM generateDebt = new GenerateDebtDataM();
					generateDebt.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					generateDebt.setConsentRefNo(rs.getString("CONSENT_REF_NO"));
					generateDebt.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
					generateDebt.setCisNo(rs.getString("CIS_NO"));
					generateDebt.setIdNo(rs.getString("IDNO"));
					generateDebt.setOtherDebtAmount(rs.getBigDecimal("OTHER_DEBT_AMT"));
					generateDebt.setSourceOfApp(GENERATE_DEBT_SOURCE_OF_APP);
					generateDebt.setProduct(rs.getString("PRODUCT"));
					generateDebt.setVerifiedIncome(rs.getBigDecimal("VI"));
					generateDebt.setApproveAmount(rs.getBigDecimal("APPROVE_AMOUNT"));
					generateDebt.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				int SEQ = rs.getInt("SEQ");
				if(PERSONAL_TYPE_APPLICANT.equals(PERSONAL_TYPE)){
					generateDebt.setApplicantType(GENERATE_CONSENT_MAIN_APPLICANT);
				}else{
					generateDebt.setApplicantType(GENERATE_CONSENT_SUP_APPLICANT+String.valueOf(SEQ));
				}
				generateDebts.add(generateDebt);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return generateDebts;
	}
	
	@Override
	public ArrayList<GenerateDebtReportDataM> getOutputDebtReport(String systemDate) throws InfBatchException {
		ArrayList<GenerateDebtReportDataM> generateDebtReports = new ArrayList<GenerateDebtReportDataM>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Date appDate = (Date) ApplicationDateFactory.getInfChangeDateDAO().getCurrentDate();
		Date appDate = new Date(InfBatchUtil.convertDate(systemDate, InfBatchConstant.SYSTEM_DATE_FORMAT).getTime());
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT DISTINCT ");
				SQL.append("     BOT.APPLICATION_NO, ");
				SQL.append("     BOT.APPROVE_AMOUNT, ");
				SQL.append("     BOT.CIS_NO, ");
				SQL.append("     BOT.COMMERCIAL_AMOUNT, ");
				SQL.append("     BOT.CONSENT_REF_NO, ");
				SQL.append("     BOT.CONSUMER_AMOUNT, ");
				SQL.append("     BOT.DATA_DATE, ");
				SQL.append("     BOT.IDNO, ");
				SQL.append("     BOT.OTHER_DEBT_AMOUNT, ");
				SQL.append("     BOT.PRODUCT_TYPE, ");
				SQL.append("     BOT.SOURCE_OF_APP,        ");
				SQL.append("     BOT.TOTAL_DEBT_AMOUNT, ");
				SQL.append("     BOT.VERIFIED_INCOME ");
				SQL.append(" FROM  BOT_DEBT_AMOUNT BOT  ");
				SQL.append(" WHERE TO_CHAR(?,'YYYYMM') = TO_CHAR(BOT.DATA_DATE,'YYYYMM') AND DEBT_STATUS ='Y' ");
			logger.debug("SQL >> "+SQL);
			logger.debug("systemDate : " + appDate);
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;		
			ps.setDate(index++,appDate);
			
			rs = ps.executeQuery();
			while(rs.next()){
				GenerateDebtReportDataM generateDebtReport = new GenerateDebtReportDataM();
					generateDebtReport.setConsentRefNo(rs.getString("CONSENT_REF_NO"));
					generateDebtReport.setApplicationNo(rs.getString("APPLICATION_NO"));
					generateDebtReport.setCisNo(rs.getString("CIS_NO"));
					generateDebtReport.setIdNo(rs.getString("IDNO"));
					generateDebtReport.setOtherDebtAmount(rs.getBigDecimal("OTHER_DEBT_AMOUNT"));
					generateDebtReport.setCommercialAmount(rs.getBigDecimal("COMMERCIAL_AMOUNT"));
					generateDebtReport.setConsumerAmount(rs.getBigDecimal("CONSUMER_AMOUNT"));
					generateDebtReport.setTotalDebtAmount(rs.getBigDecimal("TOTAL_DEBT_AMOUNT"));
					generateDebtReport.setSourceOfApp(rs.getString("SOURCE_OF_APP"));
					generateDebtReport.setProductType(rs.getString("PRODUCT_TYPE"));
					generateDebtReport.setVerifiedIncome(rs.getBigDecimal("VERIFIED_INCOME"));
					generateDebtReport.setApproveAmount(rs.getBigDecimal("APPROVE_AMOUNT"));
					generateDebtReport.setDataDate(rs.getDate("DATA_DATE"));
					generateDebtReports.add(generateDebtReport);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return generateDebtReports;
	}
	public void deleteBotDebtAmount(String systemDate) throws InfBatchException  {
		PreparedStatement ps = null;
		Connection conn = null;
		String GENERATE_DEBTREPORT_OLD_DAYS = InfBatchProperty.getInfBatchConfig("GENERATE_DEBTREPORT_OLD_DAYS");
		Date appDate = new Date(InfBatchUtil.convertDate(systemDate, InfBatchConstant.SYSTEM_DATE_FORMAT).getTime());
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append(" DELETE FROM BOT_DEBT_AMOUNT BOT ");
            sql.append(" WHERE  ");
            sql.append(" TO_DATE(TO_CHAR(?,'YYYY-DD-MM'),'YYYY-DD-MM') - TO_DATE(TO_CHAR(BOT.DATA_DATE,'YYYY-DD-MM'),'YYYY-DD-MM') > ? ");
            

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            int index = 0;
            ps.setDate(++index, appDate);
            ps.setString(++index, GENERATE_DEBTREPORT_OLD_DAYS);
            
            ps.executeUpdate();

		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
}
