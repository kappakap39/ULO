package com.eaf.inf.batch.ulo.memo.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.memo.model.DebitInterestDataM;
import com.eaf.inf.batch.ulo.memo.model.InstructionMemoDataM;

public class InstructionMemoDAOImpl extends InfBatchObjectDAO implements InstructionMemoDAO {
	private static transient Logger logger = Logger.getLogger(InstructionMemoDAOImpl.class);
	@Override
	public InstructionMemoDataM getInstructionMemo(String applicationGroupId) throws InfBatchException {
		InstructionMemoDataM result = new InstructionMemoDataM();
		mapReportParam(result);
		mapOrigApplicationGroup(result, applicationGroupId);
		return result;
	}
	private void mapReportParam(InstructionMemoDataM instructionMemo) {
		if (null == instructionMemo) {
			logger.debug("InstructionMemo is NULL!");
			return;
		}
		String PARAM_TYPE = "KPL_INSTRUCTION_MEMO_OPEN";
		String PARAM_VALUE = "PARAM_VALUE";
		instructionMemo.setDearTo(InfBatchProperty.getReportParam(PARAM_TYPE, "01", PARAM_VALUE));
		instructionMemo.setTariffCode(InfBatchProperty.getReportParam(PARAM_TYPE, "02", PARAM_VALUE));
		instructionMemo.setAdministrativeBranch(InfBatchProperty.getReportParam(PARAM_TYPE, "03",PARAM_VALUE));
		instructionMemo.setAdministrativeBranchOrg(instructionMemo.getAdministrativeBranch());
		instructionMemo.setFinality(InfBatchProperty.getReportParam(PARAM_TYPE, "04", PARAM_VALUE));
		instructionMemo.setCnaeCode(InfBatchProperty.getReportParam(PARAM_TYPE, "05", PARAM_VALUE));
	}
	private void mapOrigApplicationGroup(InstructionMemoDataM instructionMemo,String applicationGroupId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			if (rs.next()) {
				instructionMemo.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				instructionMemo.setApplyDate(rs.getDate("APPLY_DATE"));
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
		mapApplication(instructionMemo, applicationGroupId);
	}
	private void mapApplication(InstructionMemoDataM instructionMemo, String applicationGroupId) {
		String applicationRecordId = null;
		String businessClassId = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APP.APPLICATION_RECORD_ID, APP.BUSINESS_CLASS_ID " +
					"FROM ORIG_APPLICATION APP " +
					"LEFT JOIN BUSINESS_CLASS BUS ON APP.BUSINESS_CLASS_ID = BUS.BUS_CLASS_ID " +
					"WHERE APPLICATION_GROUP_ID = ? AND BUS.ORG_ID = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, applicationGroupId);
			ps.setString(index++, "KPL");
			rs = ps.executeQuery();
			if (rs.next()) {
				applicationRecordId = rs.getString("APPLICATION_RECORD_ID");
				businessClassId = rs.getString("BUSINESS_CLASS_ID");
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
		logger.debug("applicationRecordId : " + applicationRecordId);
		logger.debug("businessClassId : " + businessClassId);
		mapPersonalInfo(instructionMemo, applicationGroupId);
		mapBusinessClass(instructionMemo, businessClassId);
		mapLoan(instructionMemo, applicationRecordId);
	}

	private void mapPersonalInfo(InstructionMemoDataM instructionMemo, String applicationGroupId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_PERSONAL_INFO WHERE APPLICATION_GROUP_ID = ? AND PERSONAL_TYPE = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, applicationGroupId);
			ps.setString(index++, "A");
			rs = ps.executeQuery();
			if (rs.next()) {
				instructionMemo.setIdNo(rs.getString("IDNO"));
				instructionMemo.setOwnerName(rs.getString("TH_FIRST_NAME") + " "+ rs.getString("TH_LAST_NAME"));
				instructionMemo.setPrimaryAccName(rs.getString("TH_FIRST_NAME") + " "+ rs.getString("TH_LAST_NAME"));
				instructionMemo.setSecondaryAccName(rs.getString("EN_FIRST_NAME") + " "+ rs.getString("EN_LAST_NAME"));
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
	}

	private void mapBusinessClass(InstructionMemoDataM instructionMemo, String businessClassId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		logger.debug("businessClassId : " + businessClassId);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT BUS_CLASS_DESC FROM BUSINESS_CLASS WHERE BUS_CLASS_ID = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, businessClassId);
			rs = ps.executeQuery();
			if(rs.next()){
				instructionMemo.setProductName(rs.getString("BUS_CLASS_DESC"));
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
	}

	private void mapLoan(InstructionMemoDataM instructionMemo, String applicationRecordId) {
		String loanId = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * " + " FROM ORIG_LOAN " + " WHERE APPLICATION_RECORD_ID = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationRecordId);
			rs = ps.executeQuery();
			if (rs.next()) {
				loanId = rs.getString("LOAN_ID");
				instructionMemo.setRateType(rs.getString("RATE_TYPE"));
				instructionMemo.setSignSpread(rs.getString("SIGN_SPREAD"));
				instructionMemo.setTerm(rs.getString("TERM"));
				instructionMemo.setAccountNo(rs.getString("ACCOUNT_NO"));
				instructionMemo.setSpecialProject(rs.getString("SPECIAL_PROJECT"));
				instructionMemo.setLoanAmt(rs.getBigDecimal("LOAN_AMT"));
				instructionMemo.setFirstInstallmentDate(rs.getDate("FIRST_INSTALLMENT_DATE"));
				instructionMemo.setDisbursementAmt(rs.getBigDecimal("DISBURSEMENT_AMT"));
				instructionMemo.setFirstInterestDate(rs.getDate("FIRST_INTEREST_DATE"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		mapLoanTier(instructionMemo, loanId);
		mapLoanFee(instructionMemo, loanId);
	}

	private void mapLoanFee(InstructionMemoDataM instructionMemo, String loanId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT LBM.DISPLAY_NAME, LBM.FIELD_ID, LBM.CHOICE_NO " +
					"FROM ORIG_LOAN_FEE FEE " +
					"LEFT JOIN LIST_BOX_MASTER LBM ON FEE.FINAL_FEE_AMOUNT = LBM.CHOICE_NO " +
					"WHERE  FEE.REF_ID = ? " +
					"AND LBM.FIELD_ID = ? " +
					"AND FEE.FEE_TYPE = ? " +
					"AND FEE.FEE_LEVEL_TYPE = ? " +
					"ORDER BY FEE.SEQ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, loanId);
			ps.setString(index++, "114");
			ps.setString(index++, "01");
			ps.setString(index++, "L");
			rs = ps.executeQuery();
			List<String> feeAmount = new ArrayList<String>();
			while (rs.next()) {
				feeAmount.add(rs.getString("DISPLAY_NAME"));
			}
			instructionMemo.setFinalFeeAmt(feeAmount);

		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT FEE.FINAL_FEE_AMOUNT, LBM.DISPLAY_NAME, LBM.FIELD_ID, LBM.CHOICE_NO " +
					"FROM ORIG_LOAN_FEE FEE " +
					"LEFT JOIN LIST_BOX_MASTER LBM ON FEE.FEE_TYPE = LBM.CHOICE_NO " +
					"WHERE  FEE.REF_ID = ? " +
					"AND LBM.FIELD_ID = ? " +
					"AND FEE.FEE_TYPE IN (?,?,?,?,?) " +
					"AND FEE.FEE_LEVEL_TYPE = ? " +
					"ORDER BY FEE.SEQ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, loanId);
			ps.setString(index++, "114");
			ps.setString(index++, "02");
			ps.setString(index++, "03");
			ps.setString(index++, "04");
			ps.setString(index++, "05");
			ps.setString(index++, "99");
			ps.setString(index++, "L");
			rs = ps.executeQuery();
			List<BigDecimal> feeAmountOther = new ArrayList<BigDecimal>(5);
			while (rs.next()) {
				feeAmountOther.add(rs.getBigDecimal("FINAL_FEE_AMOUNT"));
			}
			instructionMemo.setFinalFeeAmtOther(feeAmountOther);

		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}
	private void mapLoanTier(InstructionMemoDataM instructionMemo, String loanId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DebitInterestDataM> debitInterests = new ArrayList<DebitInterestDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT *  FROM ORIG_LOAN_TIER WHERE LOAN_ID = ? ORDER BY SEQ ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, loanId);
			rs = ps.executeQuery();
			while (rs.next()) {
				DebitInterestDataM debitInterest = new DebitInterestDataM();
				instructionMemo.setMonthlyInstallment(rs.getBigDecimal("MONTHLY_INSTALLMENT"));
				String intRateIndex = rs.getString("INT_RATE_INDEX");
				if (null == intRateIndex) {
					debitInterest.setFixedInterestRate(rs.getString("INT_RATE_SPREAD"));
				} else {
					debitInterest.setSpreadRate(rs.getString("INT_RATE_SPREAD"));
				}
				debitInterest.setReferenceRate(intRateIndex);
				debitInterests.add(debitInterest);
			}
			instructionMemo.setDebitInterests(debitInterests);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}
}
