package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.LoanFeeDataM;
import com.eaf.orig.ulo.model.app.LoanPricingDataM;
import com.eaf.orig.ulo.model.app.LoanTierDataM;

public class OrigLoanDAOImpl extends OrigObjectDAO implements OrigLoanDAO {
	public OrigLoanDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigLoanDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigLoanM(LoanDataM loanM) throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("loanM.getLoanId() :"+loanM.getLoanId());
		    if(Util.empty(loanM.getLoanId())){
				String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK );
				loanM.setLoanId(loanId);
				loanM.setCreateBy(userId);
		    }
		    loanM.setUpdateBy(userId);
			createTableORIG_LOAN(loanM);

			CardDataM cardDataM = loanM.getCard();
			if (cardDataM != null) {
				OrigCardDAO origCardDAO = ORIGDAOFactory.getCardDAO(userId);
				cardDataM.setLoanId(loanM.getLoanId());
				origCardDAO.createOrigCardM(cardDataM);
			}
			
			ArrayList<CashTransferDataM> cashTransferList = loanM.getCashTransfers();
			if(!Util.empty(cashTransferList)) {
				OrigCashTransferDAO origCashTransferDAO = ORIGDAOFactory.getCashTransferDAO(userId);
				for(CashTransferDataM cashTransferM: cashTransferList) {
					cashTransferM.setLoanId(loanM.getLoanId());
					origCashTransferDAO.createOrigCashTransferM(cashTransferM);
				}
			}
			
			ArrayList<LoanTierDataM> tierList = loanM.getLoanTiers();
			if(!Util.empty(tierList)) {
				OrigLoanTierDAO tierDAO = ORIGDAOFactory.getLoanTierDAO(userId);
				for(LoanTierDataM tierM: tierList) {
					tierM.setLoanId(loanM.getLoanId());
					tierDAO.createOrigLoanTierM(tierM);
				}
			}
			
			ArrayList<LoanFeeDataM> feeList = loanM.getLoanFees();
			if(!Util.empty(feeList)) {
				OrigLoanFeeDAO feeDAO = ORIGDAOFactory.getLoanFeeDAO(userId);
				for(LoanFeeDataM feeM: feeList) {
					feeM.setRefId(loanM.getLoanId());
					feeM.setFeeLevelType(MConstant.REF_LEVEL.LOAN);
					feeDAO.createOrigLoanFeeM(feeM);
				}
			}
			
			//MLP Additional
			ArrayList<LoanPricingDataM> pricingList = loanM.getLoanPricings();
			if(!Util.empty(pricingList)) {
				OrigLoanPricingDAO pricingDAO = ORIGDAOFactory.getLoanPricingDAO(userId);
				for(LoanPricingDataM pricingM: pricingList) 
				{
					pricingDAO.saveUpdateOrigLoanPricing(pricingM);
				}
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_LOAN(LoanDataM loanM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_LOAN ");
			sql.append("( LOAN_ID, APPLICATION_RECORD_ID, REQUEST_LOAN_AMT, TERM, ");
			sql.append(" RECOMMEND_LOAN_AMT, LOAN_AMT, FINAL_CREDIT_LIMIT, CURRENT_CREDIT_LIMIT, ");
			sql.append(" REQUEST_TERM, INTEREST_RATE, INSTALLMENT_AMT, ACCOUNT_NO, ");
			sql.append(" MAX_CREDIT_LIMIT, MIN_CREDIT_LIMIT, MAX_CREDIT_LIMIT_BOT, ");
			sql.append(" RECOMMEND_TERM, FIRST_INSTALLMENT_DATE, RATE_TYPE, SIGN_SPREAD, ");
			sql.append(" SPECIAL_PROJECT, DISBURSEMENT_AMT, OLD_ACCOUNT_NO, FIRST_INTEREST_DATE,");
			sql.append(" PAYMENT_METHOD_ID, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY,  ");
			sql.append(" BOT_FACTOR, BOT_CONDITION, PRODUCT_FACTOR, DEBT_BURDEN, DEBT_BURDEN_CREDITLIMIT, ");
			sql.append(" DEBT_AMOUNT, DEBT_RECOMMEND, ACCOUNT_OPEN_DATE, STAMP_DUTY, AMOUNT_CAPPORT_NAME, APPLICATION_CAPPORT_NAME, "); //KPL Additional
			sql.append(" CB_PRODUCT, CB_SUB_PRODUCT, CB_MARKET_CODE, "); //KPL Additional
			sql.append(" MAX_CREDIT_LIMIT_WITHOUT_DBR, "); //For CR0216
			sql.append(" MAX_DBR_CREDIT_LIMIT )"); //For DF_FLP-02552,02530
			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,?,?, ?,?, ?,?,?,?,?, ? , ?,?,? ,?,?,?, ?, ? )");  
			//KPL Additional add extra ?,?,?,? for accOpenDate, StampDuty, AmountCapPortName, ApplicationCapPortName field
			//KPL Additional add extra ?,?,? for coreBankProduct, coreBankSubProduct, coreBankMarketCode
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, loanM.getLoanId());
			ps.setString(cnt++, loanM.getApplicationRecordId());
			ps.setBigDecimal(cnt++, loanM.getRequestLoanAmt());
			ps.setBigDecimal(cnt++, loanM.getTerm());
			
			ps.setBigDecimal(cnt++, loanM.getRecommendLoanAmt());
			ps.setBigDecimal(cnt++, loanM.getLoanAmt());
			ps.setBigDecimal(cnt++, loanM.getFinalCreditLimit());
			ps.setBigDecimal(cnt++, loanM.getCurrentCreditLimit());
			
			ps.setBigDecimal(cnt++, loanM.getRequestTerm());
			ps.setBigDecimal(cnt++, loanM.getInterestRate());
			ps.setBigDecimal(cnt++, loanM.getInstallmentAmt());
			ps.setString(cnt++, loanM.getAccountNo());
			
			ps.setBigDecimal(cnt++, loanM.getMaxCreditLimit());
			ps.setBigDecimal(cnt++, loanM.getMinCreditLimit());
			ps.setBigDecimal(cnt++, loanM.getMaxCreditLimitBot());
			
			ps.setBigDecimal(cnt++, loanM.getRecommendTerm());
			ps.setDate(cnt++, loanM.getFirstInstallmentDate());
			ps.setString(cnt++, loanM.getRateType());
			ps.setString(cnt++, loanM.getSignSpread());
			
			ps.setString(cnt++, loanM.getSpecialProject());
			ps.setBigDecimal(cnt++, loanM.getDisbursementAmt());
			ps.setString(cnt++, loanM.getOldAccountNo());
			ps.setDate(cnt++, loanM.getFirstInterestDate());
			
			ps.setString(cnt++, loanM.getPaymentMethodId());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, loanM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, loanM.getUpdateBy());
			
			ps.setBigDecimal(cnt++, loanM.getBotFactor());
			ps.setString(cnt++, loanM.getBotCondition());
			
			ps.setBigDecimal(cnt++, loanM.getProductFactor());
			ps.setBigDecimal(cnt++, loanM.getDebtBurden());
			ps.setBigDecimal(cnt++, loanM.getDebtBurdenCreditLimit());
			ps.setBigDecimal(cnt++, loanM.getDebtAmount());
			ps.setBigDecimal(cnt++, loanM.getDebtRecommend());
			
			//KPL Additional
			ps.setDate(cnt++, loanM.getAccountOpenDate()); 
			ps.setBigDecimal(cnt++, loanM.getStampDuty());
			ps.setString(cnt++, loanM.getAmountCapPortName());
			ps.setString(cnt++, loanM.getApplicationCapPortName());
			
			ps.setString(cnt++, loanM.getCoreBankProduct());
			ps.setString(cnt++, loanM.getCoreBankSubProduct());
			ps.setString(cnt++, loanM.getCoreBankMarketCode());
			
			ps.setBigDecimal(cnt++, loanM.getMaxCreditLimitWithOutDBR());
			ps.setBigDecimal(cnt++, loanM.getMaxDebtBurdenCreditLimit());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.debug("Error LoanDataM : "+loanM);
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigLoanM(String applicationRecordId, String loanId) 
			throws ApplicationException {
		OrigCardDAO origCardDAO = ORIGDAOFactory.getCardDAO();
		OrigCashTransferDAO origCashTransferDAO = ORIGDAOFactory.getCashTransferDAO();
		OrigLoanTierDAO tierDAO = ORIGDAOFactory.getLoanTierDAO();
		OrigLoanFeeDAO feeDAO = ORIGDAOFactory.getLoanFeeDAO();
		OrigLoanPricingDAO pricingDAO = ORIGDAOFactory.getLoanPricingDAO();
		OrigAdditionalServiceMapDAO origAdditionalServiceDAO = ORIGDAOFactory.getAdditionalServiceMapDAO();
		
		if(Util.empty(loanId)) {
			ArrayList<String> loanList = selectLoanIdORIG_LOAN(applicationRecordId);
			logger.debug("loanList>>>"+loanList);
			if(!Util.empty(loanList)) {
				for(String loanID: loanList) {
					origCardDAO.deleteOrigCardM(loanID, null);					
					origCashTransferDAO.deleteOrigCashTransferM(loanID, null);					
					tierDAO.deleteOrigLoanTierM(loanID, null);
					pricingDAO.deleteLoanPricing(loanID, null);
					feeDAO.deleteOrigLoanFeeM(loanID, MConstant.REF_LEVEL.LOAN, null);
					origAdditionalServiceDAO.deleteOrigAdditionalServiceMapM(loanID, null);
				}
			}
		} else {
			origCardDAO.deleteOrigCardM(loanId, null);					
			origCashTransferDAO.deleteOrigCashTransferM(loanId, null);					
			tierDAO.deleteOrigLoanTierM(loanId, null);
			feeDAO.deleteOrigLoanFeeM(loanId, MConstant.REF_LEVEL.LOAN, null);
			pricingDAO.deleteLoanPricing(loanId, null);
			origAdditionalServiceDAO.deleteOrigAdditionalServiceMapM(loanId, null);
		}	
		
		deleteTableORIG_LOAN(applicationRecordId, loanId);
	}

	private ArrayList<String> selectLoanIdORIG_LOAN(String applicationRecordId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> loanList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT LOAN_ID ");
			sql.append(" FROM ORIG_LOAN WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();

			while(rs.next()) {
				loanList.add(rs.getString("LOAN_ID"));
			}

			return loanList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private void deleteTableORIG_LOAN(String applicationRecordId, String loanId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_LOAN ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			if(!Util.empty(loanId)) {
				sql.append(" AND LOAN_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("applicationRecordId=" + applicationRecordId);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationRecordId);
			if(!Util.empty(loanId)) {
				ps.setString(2, loanId);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<LoanDataM> loadOrigLoanM(String applicationRecordId)throws ApplicationException {
		ArrayList<LoanDataM> result = selectTableORIG_LOAN(applicationRecordId);
		if(!Util.empty(result)) {
			
			for(LoanDataM loanM: result){
				
				OrigCardDAO origCardDAO = ORIGDAOFactory.getCardDAO();
				CardDataM cardDataM = origCardDAO.loadOrigCardM(loanM.getLoanId());
				if (cardDataM != null) {
					loanM.setCard(cardDataM);
				}
				
				OrigCashTransferDAO origCashTransferDAO = ORIGDAOFactory.getCashTransferDAO();
				ArrayList<CashTransferDataM> cashTransferList = origCashTransferDAO.loadOrigCashTransferM(loanM.getLoanId());
				if (!Util.empty(cashTransferList)) {
					loanM.setCashTransfers(cashTransferList);
				}
				
				OrigLoanTierDAO tierDAO = ORIGDAOFactory.getLoanTierDAO();
				ArrayList<LoanTierDataM> tierList = tierDAO.loadOrigLoanTierM(loanM.getLoanId());
				if(!Util.empty(tierList)) {
					loanM.setLoanTiers(tierList);
				}
				
				OrigLoanFeeDAO feeDAO = ORIGDAOFactory.getLoanFeeDAO();
				ArrayList<LoanFeeDataM> feeList = feeDAO.loadOrigLoanFeeM(loanM.getLoanId(), MConstant.REF_LEVEL.LOAN);
				if(!Util.empty(feeList)) {
					loanM.setLoanFees(feeList);
				}
				
				//KBMF V2.4
				OrigLoanPricingDAO pricingDAO = ORIGDAOFactory.getLoanPricingDAO();
				ArrayList<LoanPricingDataM> pricings = pricingDAO.loadLoanPricingByLoanId(loanM.getLoanId());
				if(!Util.empty(pricings)) {
					loanM.setLoanPricings(pricings);
				}
				
				OrigAdditionalServiceMapDAO origAdditionalServiceDAO = ORIGDAOFactory.getAdditionalServiceMapDAO();
				ArrayList<String> serviceIdList = origAdditionalServiceDAO.loadOrigAdditionalServicesM(loanM.getLoanId());
				if (!Util.empty(serviceIdList)) {
					loanM.setSpecialAdditionalServiceIds(serviceIdList);
					
				}
			}
			
		}
		return result;
	}
	@Override
	public ArrayList<LoanDataM> loadOrigLoanVlink(String applicationRecordId,Connection conn) throws ApplicationException {
		ArrayList<LoanDataM> loans = selectTableORIG_LOAN(applicationRecordId,conn);
		if(!Util.empty(loans)){
			for(LoanDataM loan: loans){
				OrigCardDAO origCardDAO = ORIGDAOFactory.getCardDAO();
				CardDataM cardDataM = origCardDAO.loadOrigCardM(loan.getLoanId(),conn);
				if (cardDataM != null) {
					loan.setCard(cardDataM);
				}
			}
		}
		return loans;
	}
	@Override
	public ArrayList<LoanDataM> loadOrigLoanM(String applicationRecordId,
			Connection conn) throws ApplicationException {
		
		ArrayList<LoanDataM> result = selectTableORIG_LOAN(applicationRecordId,conn);
		if(!Util.empty(result)) {
			
			for(LoanDataM loanM: result){
				
				OrigCardDAO origCardDAO = ORIGDAOFactory.getCardDAO();
				CardDataM cardDataM = origCardDAO.loadOrigCardM(loanM.getLoanId(),conn);
				if (cardDataM != null) {
					loanM.setCard(cardDataM);
				}
				
				OrigCashTransferDAO origCashTransferDAO = ORIGDAOFactory.getCashTransferDAO();
				ArrayList<CashTransferDataM> cashTransferList = origCashTransferDAO.loadOrigCashTransferM(loanM.getLoanId(),conn);
				if (!Util.empty(cashTransferList)) {
					loanM.setCashTransfers(cashTransferList);
				}
				
				OrigLoanTierDAO tierDAO = ORIGDAOFactory.getLoanTierDAO();
				ArrayList<LoanTierDataM> tierList = tierDAO.loadOrigLoanTierM(loanM.getLoanId(),conn);
				if(!Util.empty(tierList)) {
					loanM.setLoanTiers(tierList);
				}
				
				OrigLoanFeeDAO feeDAO = ORIGDAOFactory.getLoanFeeDAO();
				ArrayList<LoanFeeDataM> feeList = feeDAO.loadOrigLoanFeeM(loanM.getLoanId(), MConstant.REF_LEVEL.LOAN,conn);
				if(!Util.empty(feeList)) {
					loanM.setLoanFees(feeList);
				}
				
				//KBMF V2.4
				OrigLoanPricingDAO pricingDAO = ORIGDAOFactory.getLoanPricingDAO();
				ArrayList<LoanPricingDataM> pricings = pricingDAO.loadLoanPricingByLoanId(loanM.getLoanId(),conn);
				if(!Util.empty(pricings)) {
					loanM.setLoanPricings(pricings);
				}
				
				OrigAdditionalServiceMapDAO origAdditionalServiceDAO = ORIGDAOFactory.getAdditionalServiceMapDAO();
				ArrayList<String> serviceIdList = origAdditionalServiceDAO.loadOrigAdditionalServicesM(loanM.getLoanId(),conn);
				if (!Util.empty(serviceIdList)) {
					loanM.setSpecialAdditionalServiceIds(serviceIdList);
					
				}
			}
			
		}
		return result;
	}
	private ArrayList<LoanDataM> selectTableORIG_LOAN(String applicationRecordId) 
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_LOAN(applicationRecordId,conn);
		}catch (Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	private ArrayList<LoanDataM> selectTableORIG_LOAN(String applicationRecordId,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<LoanDataM> loanList = new ArrayList<LoanDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT LOAN_ID, APPLICATION_RECORD_ID, REQUEST_LOAN_AMT, TERM, ");
			sql.append(" RECOMMEND_LOAN_AMT, LOAN_AMT, FINAL_CREDIT_LIMIT, CURRENT_CREDIT_LIMIT, ");
			sql.append(" REQUEST_TERM, INTEREST_RATE, INSTALLMENT_AMT, ACCOUNT_NO, ");
			sql.append(" MAX_CREDIT_LIMIT, MIN_CREDIT_LIMIT, MAX_CREDIT_LIMIT_BOT, ");
			sql.append(" RECOMMEND_TERM, FIRST_INSTALLMENT_DATE, RATE_TYPE, SIGN_SPREAD, ");
			sql.append(" SPECIAL_PROJECT, DISBURSEMENT_AMT, OLD_ACCOUNT_NO, FIRST_INTEREST_DATE,");
			sql.append(" PAYMENT_METHOD_ID, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" BOT_FACTOR, BOT_CONDITION, ");
			sql.append(" PRODUCT_FACTOR, DEBT_BURDEN, DEBT_BURDEN_CREDITLIMIT, DEBT_AMOUNT, DEBT_RECOMMEND ");
			sql.append(" , ACCOUNT_OPEN_DATE, STAMP_DUTY, AMOUNT_CAPPORT_NAME, APPLICATION_CAPPORT_NAME "); //KPL Additional
			sql.append(" , CB_PRODUCT, CB_SUB_PRODUCT, CB_MARKET_CODE "); //KPL Additional
			sql.append(" , MAX_CREDIT_LIMIT_WITHOUT_DBR "); //For CR0216
			sql.append(" , MAX_DBR_CREDIT_LIMIT "); //For DF_FLP-02552,02530
			sql.append(" FROM ORIG_LOAN WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();

			while(rs.next()) {
				LoanDataM loanM = new LoanDataM();
				loanM.setLoanId(rs.getString("LOAN_ID"));
				loanM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				loanM.setRequestLoanAmt(rs.getBigDecimal("REQUEST_LOAN_AMT"));
				loanM.setTerm(rs.getBigDecimal("TERM"));

				loanM.setRecommendLoanAmt(rs.getBigDecimal("RECOMMEND_LOAN_AMT"));
				loanM.setLoanAmt(rs.getBigDecimal("LOAN_AMT"));
				loanM.setFinalCreditLimit(rs.getBigDecimal("FINAL_CREDIT_LIMIT"));
				loanM.setCurrentCreditLimit(rs.getBigDecimal("CURRENT_CREDIT_LIMIT"));
				
				loanM.setRequestTerm(rs.getBigDecimal("REQUEST_TERM"));
				loanM.setInterestRate(rs.getBigDecimal("INTEREST_RATE"));
				loanM.setInstallmentAmt(rs.getBigDecimal("INSTALLMENT_AMT"));
				loanM.setAccountNo(rs.getString("ACCOUNT_NO"));
				
				loanM.setMaxCreditLimit(rs.getBigDecimal("MAX_CREDIT_LIMIT"));
				loanM.setMinCreditLimit(rs.getBigDecimal("MIN_CREDIT_LIMIT"));
				loanM.setMaxCreditLimitBot(rs.getBigDecimal("MAX_CREDIT_LIMIT_BOT"));
				
				loanM.setRecommendTerm(rs.getBigDecimal("RECOMMEND_TERM"));
				loanM.setFirstInstallmentDate(rs.getDate("FIRST_INSTALLMENT_DATE"));
				loanM.setRateType(rs.getString("RATE_TYPE"));
				loanM.setSignSpread(rs.getString("SIGN_SPREAD"));
				
				loanM.setSpecialProject(rs.getString("SPECIAL_PROJECT"));
				loanM.setDisbursementAmt(rs.getBigDecimal("DISBURSEMENT_AMT"));
				loanM.setOldAccountNo(rs.getString("OLD_ACCOUNT_NO"));
				loanM.setFirstInterestDate(rs.getDate("FIRST_INTEREST_DATE"));
				
				loanM.setPaymentMethodId(rs.getString("PAYMENT_METHOD_ID"));
				loanM.setCreateBy(rs.getString("CREATE_BY"));
				loanM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				loanM.setUpdateBy(rs.getString("UPDATE_BY"));
				loanM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				loanM.setBotFactor(rs.getBigDecimal("BOT_FACTOR"));
				loanM.setBotCondition(rs.getString("BOT_CONDITION"));
				
				loanM.setProductFactor(rs.getBigDecimal("PRODUCT_FACTOR"));
				loanM.setDebtBurden(rs.getBigDecimal("DEBT_BURDEN"));
				loanM.setDebtBurdenCreditLimit(rs.getBigDecimal("DEBT_BURDEN_CREDITLIMIT"));
				loanM.setDebtAmount(rs.getBigDecimal("DEBT_AMOUNT"));
				loanM.setDebtRecommend(rs.getBigDecimal("DEBT_RECOMMEND"));		
				
				//KPL Additional
				loanM.setAccountOpenDate(rs.getDate("ACCOUNT_OPEN_DATE"));
				loanM.setStampDuty(rs.getBigDecimal("STAMP_DUTY"));
				loanM.setAmountCapPortName(rs.getString("AMOUNT_CAPPORT_NAME"));
				loanM.setApplicationCapPortName(rs.getString("APPLICATION_CAPPORT_NAME"));
				
				loanM.setCoreBankProduct(rs.getString("CB_PRODUCT"));
				loanM.setCoreBankSubProduct(rs.getString("CB_SUB_PRODUCT"));
				loanM.setCoreBankMarketCode(rs.getString("CB_MARKET_CODE"));
				
				//For CR0216
				loanM.setMaxCreditLimitWithOutDBR(rs.getBigDecimal("MAX_CREDIT_LIMIT_WITHOUT_DBR"));
				
				//DF_FLP-02552, 02530
				loanM.setMaxDebtBurdenCreditLimit( rs.getBigDecimal("MAX_DBR_CREDIT_LIMIT") );
				
				loanList.add(loanM);
			}

			return loanList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigLoanM(LoanDataM loanM)
			throws ApplicationException {
		int returnRows = 0;
		loanM.setUpdateBy(userId);
		returnRows = updateTableORIG_LOAN(loanM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_LOAN then call Insert method");
			loanM.setCreateBy(userId);
			createOrigLoanM(loanM);
		} else {
			
			CardDataM cardDataM = loanM.getCard();
			if (cardDataM != null) {
				OrigCardDAO origCardDAO = ORIGDAOFactory.getCardDAO(userId);
				cardDataM.setLoanId(loanM.getLoanId());
				origCardDAO.saveUpdateOrigCardM(cardDataM);
			}
			
			OrigCashTransferDAO origCashTransferDAO = ORIGDAOFactory.getCashTransferDAO(userId);
			ArrayList<CashTransferDataM> cashTransferList = loanM.getCashTransfers();
			if(!Util.empty(cashTransferList)) {
				for(CashTransferDataM cashTransferM: cashTransferList) {
					cashTransferM.setLoanId(loanM.getLoanId());
					origCashTransferDAO.saveUpdateOrigCashTransferM(cashTransferM);
				}
			}
			origCashTransferDAO.deleteNotInKeyCashTransfer(cashTransferList, loanM.getLoanId());
			
			ArrayList<LoanTierDataM> tierList = loanM.getLoanTiers();
			OrigLoanTierDAO tierDAO = ORIGDAOFactory.getLoanTierDAO(userId);
			if(!Util.empty(tierList)) {
				for(LoanTierDataM tierM: tierList) {
					tierM.setLoanId(loanM.getLoanId());
					tierDAO.saveUpdateOrigLoanTierM(tierM);
				}
			}
			tierDAO.deleteNotInKeyLoanTier(tierList, loanM.getLoanId());
			
			ArrayList<LoanFeeDataM> feeList = loanM.getLoanFees();
			OrigLoanFeeDAO feeDAO = ORIGDAOFactory.getLoanFeeDAO(userId);
			if(!Util.empty(feeList)) {
				for(LoanFeeDataM feeM: feeList) {
					feeM.setRefId(loanM.getLoanId());
					feeM.setFeeLevelType(MConstant.REF_LEVEL.LOAN);
					feeDAO.saveUpdateOrigLoanFeeM(feeM);
				}
			}
			feeDAO.deleteNotInKeyLoanFee(feeList, loanM.getLoanId(), MConstant.REF_LEVEL.LOAN);
			
			//KBMF V2.4 - Store FICO Pricing also
			List<LoanPricingDataM> loanPricings = loanM.getLoanPricings();
			OrigLoanPricingDAO pricingDAO = ORIGDAOFactory.getLoanPricingDAO(userId);
			if(!Util.empty(loanPricings)) {
				for(LoanPricingDataM loanPricing : loanPricings){
					loanPricing.setLoanId(loanM.getLoanId());
					pricingDAO.saveUpdateOrigLoanPricing(loanPricing);
				}
			}
			pricingDAO.deleteNotInKeyLoanPricing(loanPricings, loanM.getLoanId());
		}
		
	}

	private int updateTableORIG_LOAN(LoanDataM loanM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_LOAN ");
			sql.append(" SET REQUEST_LOAN_AMT = ?, TERM = ?, RECOMMEND_LOAN_AMT = ?, LOAN_AMT = ?, ");
			sql.append(" FINAL_CREDIT_LIMIT = ?, CURRENT_CREDIT_LIMIT = ?, REQUEST_TERM = ?, ");
			sql.append(" INTEREST_RATE = ?, INSTALLMENT_AMT = ?, ACCOUNT_NO = ?, MAX_CREDIT_LIMIT = ?, ");
			sql.append(" MIN_CREDIT_LIMIT = ?, MAX_CREDIT_LIMIT_BOT = ?, RECOMMEND_TERM = ?, ");
			sql.append(" FIRST_INSTALLMENT_DATE = ?, RATE_TYPE = ?, SIGN_SPREAD = ?, ");
			sql.append(" SPECIAL_PROJECT = ?, DISBURSEMENT_AMT = ?, OLD_ACCOUNT_NO = ?, ");
			sql.append(" FIRST_INTEREST_DATE = ?, PAYMENT_METHOD_ID = ?, UPDATE_BY = ?, UPDATE_DATE = ?, ");
			sql.append(" BOT_FACTOR = ?, BOT_CONDITION = ?,");
			sql.append("  PRODUCT_FACTOR = ?, DEBT_BURDEN = ?, DEBT_BURDEN_CREDITLIMIT = ?, DEBT_AMOUNT = ?, DEBT_RECOMMEND = ?  ");
			sql.append(" , ACCOUNT_OPEN_DATE = ? , STAMP_DUTY = ? , AMOUNT_CAPPORT_NAME = ? , APPLICATION_CAPPORT_NAME = ? "); //KPL Additional
			sql.append(" , CB_PRODUCT = ? , CB_SUB_PRODUCT = ? , CB_MARKET_CODE = ? "); //KPL Additional
			sql.append(" , MAX_CREDIT_LIMIT_WITHOUT_DBR = ? "); //For CR0216
			sql.append(" , MAX_DBR_CREDIT_LIMIT = ? "); //For DF_FLP-02552, 02530
			sql.append(" WHERE LOAN_ID = ? AND APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setBigDecimal(cnt++, loanM.getRequestLoanAmt());
			ps.setBigDecimal(cnt++, loanM.getTerm());
			ps.setBigDecimal(cnt++, loanM.getRecommendLoanAmt());
			ps.setBigDecimal(cnt++, loanM.getLoanAmt());
			
			ps.setBigDecimal(cnt++, loanM.getFinalCreditLimit());
			ps.setBigDecimal(cnt++, loanM.getCurrentCreditLimit());
			ps.setBigDecimal(cnt++, loanM.getRequestTerm());
			
			ps.setBigDecimal(cnt++, loanM.getInterestRate());
			ps.setBigDecimal(cnt++, loanM.getInstallmentAmt());
			ps.setString(cnt++, loanM.getAccountNo());
			ps.setBigDecimal(cnt++, loanM.getMaxCreditLimit());
			
			ps.setBigDecimal(cnt++, loanM.getMinCreditLimit());
			ps.setBigDecimal(cnt++, loanM.getMaxCreditLimitBot());
			ps.setBigDecimal(cnt++, loanM.getRecommendTerm());
			
			ps.setDate(cnt++, loanM.getFirstInstallmentDate());
			ps.setString(cnt++, loanM.getRateType());
			ps.setString(cnt++, loanM.getSignSpread());
			
			ps.setString(cnt++, loanM.getSpecialProject());
			ps.setBigDecimal(cnt++, loanM.getDisbursementAmt());
			ps.setString(cnt++, loanM.getOldAccountNo());
			
			ps.setDate(cnt++, loanM.getFirstInterestDate());
			ps.setString(cnt++, loanM.getPaymentMethodId());
			ps.setString(cnt++, loanM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setBigDecimal(cnt++, loanM.getBotFactor());
			ps.setString(cnt++, loanM.getBotCondition());
			
			ps.setBigDecimal(cnt++, loanM.getProductFactor());
			ps.setBigDecimal(cnt++, loanM.getDebtBurden());
			ps.setBigDecimal(cnt++, loanM.getDebtBurdenCreditLimit());
			ps.setBigDecimal(cnt++, loanM.getDebtAmount());
			ps.setBigDecimal(cnt++, loanM.getDebtRecommend());
			
			//KPL Additional
			ps.setDate(cnt++, loanM.getAccountOpenDate());
			ps.setBigDecimal(cnt++, loanM.getStampDuty());
			ps.setString(cnt++, loanM.getAmountCapPortName());
			ps.setString(cnt++, loanM.getApplicationCapPortName());
			
			ps.setString(cnt++, loanM.getCoreBankProduct());
			ps.setString(cnt++, loanM.getCoreBankSubProduct());
			ps.setString(cnt++, loanM.getCoreBankMarketCode());
			//For CR0216
			ps.setBigDecimal(cnt++, loanM.getMaxCreditLimitWithOutDBR());
			
			//DF_FLP-02552, 02530
			ps.setBigDecimal(cnt++, loanM.getMaxDebtBurdenCreditLimit());
			
			// WHERE CLAUSE
			ps.setString(cnt++, loanM.getLoanId());
			ps.setString(cnt++, loanM.getApplicationRecordId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void deleteNotInKeyLoan(ArrayList<LoanDataM> loanList,
			String applicationRecordId) throws ApplicationException {
		ArrayList<String> notInKeyIdList = selectNotInKeyTableORIG_LOAN(loanList, applicationRecordId);
		if(notInKeyIdList != null && !notInKeyIdList.isEmpty()) {
			OrigCardDAO origCardDAO = ORIGDAOFactory.getCardDAO();
			OrigCashTransferDAO origCashTransferDAO = ORIGDAOFactory.getCashTransferDAO();
			OrigAdditionalServiceMapDAO origAdditionalServiceDAO = ORIGDAOFactory.getAdditionalServiceMapDAO();
			OrigLoanTierDAO tierDAO = ORIGDAOFactory.getLoanTierDAO();
			OrigLoanFeeDAO feeDAO = ORIGDAOFactory.getLoanFeeDAO();
			OrigLoanPricingDAO pricingDAO = ORIGDAOFactory.getLoanPricingDAO();
			for(String loanId: notInKeyIdList) {
				origCardDAO.deleteOrigCardM(loanId, null);
				origCashTransferDAO.deleteOrigCashTransferM(loanId, null);
				origAdditionalServiceDAO.deleteOrigAdditionalServiceMapM(loanId, null);
				tierDAO.deleteOrigLoanTierM(loanId, null);
				feeDAO.deleteOrigLoanFeeM(loanId, MConstant.REF_LEVEL.LOAN, null);
				pricingDAO.deleteLoanPricing(loanId, null);
			}
		}
		deleteNotInKeyTableORIG_LOAN(loanList, applicationRecordId);
	}

	private ArrayList<String> selectNotInKeyTableORIG_LOAN(
			ArrayList<LoanDataM> loanList, String applicationRecordId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<String> idList = new ArrayList<String>();
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT LOAN_ID FROM ORIG_LOAN ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
            
            if (loanList != null && !loanList.isEmpty()) {
                sql.append(" AND LOAN_ID NOT IN ( ");

                for (LoanDataM loanM: loanList) {
                    sql.append(" '" + loanM.getLoanId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationRecordId);
            
            rs = ps.executeQuery();
            while(rs.next()) {
            	String incomeId =  rs.getString("LOAN_ID");
            	idList.add(incomeId);
            }

            return idList;
        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	private void deleteNotInKeyTableORIG_LOAN(ArrayList<LoanDataM> loanList,
			String applicationRecordId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_LOAN ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
            
            if (!Util.empty(loanList)) {
                sql.append(" AND LOAN_ID NOT IN ( ");
                for (LoanDataM loanM: loanList) {
                	logger.debug("loanM.getLoanId() = "+loanM.getLoanId());
                    sql.append(" '" + loanM.getLoanId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationRecordId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public ArrayList<LoanDataM> loadOrigLoanMGroup(String applicationRecordId)throws ApplicationException {
		ArrayList<LoanDataM> result = selectTableORIG_LOAN(applicationRecordId);
		return result;
	}
	@Override
	public void updateKPLOpenLoanAccountInfo(String applicationRecordId,
			String accountNo, Timestamp accountOpenDate,Connection conn)
			throws ApplicationException 
	{
		//For InfBatchSetupLoanResult
		PreparedStatement ps = null;
		try {
			
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_LOAN ");
			sql.append(" SET ACCOUNT_NO = ?, ACCOUNT_OPEN_DATE = ? ");
			sql.append(" ,UPDATE_DATE = SYSDATE ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, accountNo);
			ps.setTimestamp(cnt++, accountOpenDate);
			ps.setString(cnt++, applicationRecordId);
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

}
