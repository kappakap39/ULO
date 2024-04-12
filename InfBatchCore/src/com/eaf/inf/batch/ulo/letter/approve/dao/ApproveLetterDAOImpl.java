package com.eaf.inf.batch.ulo.letter.approve.dao;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.letter.approve.model.ApproveLetterDataM;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public class ApproveLetterDAOImpl extends InfBatchObjectDAO implements ApproveLetterDAO{
	private static transient Logger logger = Logger.getLogger(ApproveLetterDAOImpl.class);
	
	public ArrayList<ApproveLetterDataM> getApproveLetterData() throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApproveLetterDataM> approveLetterList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PKA_REJECT_LETTER.GET_LETTER_NO(ORIG_PERSONAL_INFO.NATIONALITY) LETTER_NO, ");
			sql.append(" TO_CHAR(ORIG_APPLICATION.FINAL_APP_DECISION_DATE,'DD/MM/YYYY') FINAL_APP_DECISION_DATE, ");
			sql.append(" CASE ORIG_PERSONAL_INFO.NATIONALITY WHEN 'TH' THEN ");
			sql.append(" TH.DISPLAY_NAME||ORIG_PERSONAL_INFO.TH_FIRST_NAME||' '||ORIG_PERSONAL_INFO.TH_LAST_NAME ");
			sql.append(" ELSE EN.DISPLAY_NAME||ORIG_PERSONAL_INFO.EN_FIRST_NAME||' '||ORIG_PERSONAL_INFO.EN_LAST_NAME END NAME, ");
			sql.append(" ORIG_PERSONAL_ADDRESS.ADDRESS1, ORIG_PERSONAL_ADDRESS.ADDRESS2, ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO, BUSINESS_CLASS.ORG_ID, ");
			sql.append(" CASE BUSINESS_CLASS.BUS_CLASS_ID WHEN ? THEN ? ELSE ? END TEMPLATE_TYPE, ");
			sql.append(" ORIG_LOAN.LOAN_AMT, SPELL_NUMBER_THAI(ORIG_LOAN.LOAN_AMT) LOAN_AMT_TEXT, ORIG_PERSONAL_INFO.IDNO, ");
			sql.append(" ORIG_LOAN.TERM,ORIG_LOAN_TIER.MONTHLY_INSTALLMENT, SPELL_NUMBER_THAI(ORIG_LOAN_TIER.MONTHLY_INSTALLMENT) MONTHLY_INSTALLMENT_TEXT, ");
			sql.append(" ORIG_LOAN_TIER.RATE_AMOUNT, ORIG_LOAN.ACCOUNT_NO, ORIG_LOAN.INTEREST_RATE, ");
			sql.append(" TO_CHAR(ORIG_LOAN.FIRST_INSTALLMENT_DATE,'MM/YYYY','NLS_CALENDAR=''THAI BUDDHA''') FIRST_INSTALLMENT_MONTH_YEAR, ");
			sql.append(" TO_CHAR(ORIG_LOAN.FIRST_INSTALLMENT_DATE,'FMDD') FIRST_INSTALLMENT_DAY, ");
			sql.append(" ? CONTACT_CENTER, ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID, ORIG_APPLICATION.APPLICATION_RECORD_ID ");
			sql.append(" , ORIG_PERSONAL_INFO.CIS_NO, ORIG_APPLICATION.LETTER_CHANNEL "); //Additional
			sql.append(" , ORIG_LOAN.INSTALLMENT_AMT, ORIG_PERSONAL_INFO.EMAIL_PRIMARY "); //Additional
			sql.append(" , ORIG_PERSONAL_INFO.TH_FIRST_NAME, ORIG_PERSONAL_INFO.TH_MID_NAME, ORIG_PERSONAL_INFO.TH_LAST_NAME "); //Additional
			sql.append(" , CASE WHEN ORIG_APPLICATION.PROLICY_PROGRAM_ID IN ('KPL_PAY_STA','KPL_PAY_PAY') THEN 'Y' ELSE 'N' END AS PAYROLL_FLAG "); //Additional
			sql.append(" , PKA_REJECT_LETTER.GET_PRODUCT_TH_EN(ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID, ORIG_APPLICATION.APPLICATION_RECORD_ID) PRODUCT_NAME_TH_EN ");
			sql.append(" FROM ORIG_APPLICATION_GROUP ");
			sql.append(" LEFT JOIN ORIG_APPLICATION ");
			sql.append(" ON ORIG_APPLICATION.APPLICATION_GROUP_ID = ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID ");
			sql.append(" LEFT JOIN BUSINESS_CLASS ");
			sql.append(" ON BUSINESS_CLASS.BUS_CLASS_ID = ORIG_APPLICATION.BUSINESS_CLASS_ID ");
			sql.append(" LEFT JOIN ORIG_LOAN ");
			sql.append(" ON ORIG_LOAN.APPLICATION_RECORD_ID = ORIG_APPLICATION.APPLICATION_RECORD_ID ");
			sql.append(" LEFT JOIN ORIG_PERSONAL_RELATION ");
			sql.append(" ON (ORIG_PERSONAL_RELATION.REF_ID = ORIG_APPLICATION.APPLICATION_RECORD_ID AND ORIG_PERSONAL_RELATION.RELATION_LEVEL = ?) ");
			sql.append(" LEFT JOIN ORIG_PERSONAL_INFO ");
			sql.append(" ON ORIG_PERSONAL_INFO.PERSONAL_ID = ORIG_PERSONAL_RELATION.PERSONAL_ID ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER TH ");
			sql.append(" ON TH.FIELD_ID = ? AND TH.CHOICE_NO = ? ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER EN ");
			sql.append(" ON EN.FIELD_ID = ? AND EN.CHOICE_NO = ORIG_PERSONAL_INFO.EN_TITLE_CODE ");
			sql.append(" LEFT JOIN ORIG_PERSONAL_ADDRESS ");
			sql.append(" ON (ORIG_PERSONAL_ADDRESS.PERSONAL_ID = ORIG_PERSONAL_INFO.PERSONAL_ID AND ORIG_PERSONAL_ADDRESS.ADDRESS_TYPE = ORIG_PERSONAL_INFO.MAILING_ADDRESS) ");	
			sql.append(" LEFT JOIN ORIG_LOAN_TIER ");
			sql.append(" ON (ORIG_LOAN_TIER.LOAN_ID = ORIG_LOAN.LOAN_ID AND ORIG_LOAN_TIER.SEQ='1') ");
			sql.append(" WHERE (ORIG_APPLICATION_GROUP.JOB_STATE = ? OR ORIG_APPLICATION_GROUP.JOB_STATE = ?) ");
			sql.append(" AND(ORIG_APPLICATION.FINAL_APP_DECISION = ? AND BUSINESS_CLASS.ORG_ID = ?) ");
			sql.append(" AND ORIG_LOAN.ACCOUNT_NO IS NOT NULL ");
			sql.append(" AND NOT EXISTS ");
			sql.append(" ( ");
			sql.append(" 	SELECT * FROM INF_BATCH_LOG ");
			sql.append(" 	WHERE INF_BATCH_LOG.APPLICATION_RECORD_ID = ORIG_APPLICATION.APPLICATION_RECORD_ID ");
			sql.append(" 	AND INTERFACE_CODE = ? ");
			sql.append("	AND INTERFACE_STATUS = ? ");
			sql.append(" ) ");
			sql.append(" ORDER BY ZIPCODE ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, InfBatchProperty.getGeneralParam("BUSCLASS_KPL_PAYROLL"));
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("PAYROLL_TEMPLATE"));
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("GENERIC_TEMPLATE"));
			ps.setString(parameter++, InfBatchProperty.getGeneralParam("CONTACT_CENTER_NO_KPL_TH"));
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT"));
			
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("FIELD_ID_TH_TITLE_CODE"));
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_TH_TITLE_CODE"));
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("FIELD_ID_EN_TITLE_CODE"));
			
			ps.setString(parameter++, InfBatchProperty.getGeneralParam("JOB_STATE_AFTER_CARDLINK"));
			ps.setString(parameter++, InfBatchProperty.getGeneralParam("JOB_STATE_DE2_APPROVE_SUBMIT"));
			
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("STATUS_APPROVE"));
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("ORG_KPL"));
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_MODULE_ID"));
			ps.setString(parameter++, InfBatchConstant.STATUS_COMPLETE);
			
			logger.debug("BUSCLASS_KPL_PAYROLL : "+InfBatchProperty.getGeneralParam("BUSCLASS_KPL_PAYROLL"));
			logger.debug("PAYROLL_TEMPLATE : "+InfBatchProperty.getInfBatchConfig("PAYROLL_TEMPLATE"));
			logger.debug("GENERIC_TEMPLATE : "+InfBatchProperty.getInfBatchConfig("GENERIC_TEMPLATE"));
			logger.debug("CONTACT_CENTER_NO_KPL : "+InfBatchProperty.getGeneralParam("CONTACT_CENTER_NO_KPL_TH"));
			logger.debug("FIELD_ID_TH_TITLE_CODE : "+InfBatchProperty.getInfBatchConfig("FIELD_ID_TH_TITLE_CODE"));
			logger.debug("APPROVE_LETTER_TH_TITLE_CODE : "+InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_TH_TITLE_CODE"));
			logger.debug("FIELD_ID_EN_TITLE_CODE : "+InfBatchProperty.getInfBatchConfig("FIELD_ID_EN_TITLE_CODE"));
			logger.debug("JOB_STATE_AFTER_CARDLINK : "+InfBatchProperty.getGeneralParam("JOB_STATE_AFTER_CARDLINK"));
			logger.debug("STATUS_APPROVE : "+InfBatchProperty.getInfBatchConfig("STATUS_APPROVE"));
			logger.debug("ORG_KPL : "+InfBatchProperty.getInfBatchConfig("ORG_KPL"));
			logger.debug("APPROVE_LETTER_MODULE_ID : "+InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_MODULE_ID"));
			logger.debug("STATUS : "+InfBatchConstant.STATUS_COMPLETE);
			rs = ps.executeQuery();
			while(rs.next()){
				ApproveLetterDataM approveLetter = new ApproveLetterDataM();
				approveLetter.setLetterNo(rs.getString("LETTER_NO"));
				approveLetter.setFinalAppDecisionDate(rs.getString("FINAL_APP_DECISION_DATE"));
				approveLetter.setName(rs.getString("NAME"));
				approveLetter.setAddress1(rs.getString("ADDRESS1"));
				approveLetter.setAddress2(rs.getString("ADDRESS2"));
				approveLetter.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				approveLetter.setOrgId(rs.getString("ORG_ID"));
				approveLetter.setTemplateType(rs.getString("TEMPLATE_TYPE"));
				approveLetter.setLoanAmt(rs.getString("LOAN_AMT") == null ? new BigDecimal(0) : new BigDecimal(rs.getString("LOAN_AMT")));
				approveLetter.setLoanAmtText(rs.getString("LOAN_AMT_TEXT"));
				approveLetter.setIdNo(rs.getString("IDNO"));
				approveLetter.setTerm(rs.getString("TERM") == null ? 0 : Integer.parseInt(rs.getString("TERM")));
				approveLetter.setMonthlyInstallment(rs.getString("MONTHLY_INSTALLMENT") == null ? new BigDecimal(0) : new BigDecimal(rs.getString("MONTHLY_INSTALLMENT")));
				approveLetter.setMonthlyInstallmentText(rs.getString("MONTHLY_INSTALLMENT_TEXT"));
				approveLetter.setRateAmount(rs.getString("INTEREST_RATE") == null ? new BigDecimal(0) : new BigDecimal(rs.getString("INTEREST_RATE")));
				approveLetter.setAccountNo(rs.getString("ACCOUNT_NO"));
				approveLetter.setFirstInstallmentMonthYear(rs.getString("FIRST_INSTALLMENT_MONTH_YEAR"));
				approveLetter.setFirstInstallmentDay(rs.getString("FIRST_INSTALLMENT_DAY"));
				approveLetter.setContactCenter(rs.getString("CONTACT_CENTER"));
				approveLetter.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				approveLetter.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				approveLetter.setModuleId(InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_MODULE_ID"));
				
				//Additional
				approveLetter.setCisNo(rs.getString("CIS_NO"));
				approveLetter.setLetterChannel(rs.getString("LETTER_CHANNEL"));
				approveLetter.setInstallmentAmount(rs.getString("INSTALLMENT_AMT") == null ? new BigDecimal(0) : new BigDecimal(rs.getString("INSTALLMENT_AMT")));  
				approveLetter.setThFirstName(rs.getString("TH_FIRST_NAME"));
				approveLetter.setThMidName(rs.getString("TH_MID_NAME"));
				approveLetter.setThLastName(rs.getString("TH_LAST_NAME"));
				approveLetter.setEmailPrimary(rs.getString("EMAIL_PRIMARY"));
				approveLetter.setPayrollFlag(rs.getString("PAYROLL_FLAG"));
				
				approveLetter.setProductNameThEn(rs.getString("PRODUCT_NAME_TH_EN"));
				
				approveLetterList.add(approveLetter);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return approveLetterList;
	}
	
}
