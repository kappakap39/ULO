/*
 * Created on Oct 1, 2007
 * Created by Sankom Sanpunya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.exceptions.OrigLoanMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.CostFee;
import com.eaf.orig.shared.model.InstallmentDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigLoanMDAOImpl
 */
public class OrigLoanMDAOImpl extends OrigObjectDAO implements OrigLoanMDAO {
	private static Logger log = Logger.getLogger(OrigLoanMDAOImpl.class);

	/**
	 *  
	 */
	public OrigLoanMDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigLoanMDAO#createModelOrigLoanM(com.eaf.orig.shared.model.LoanDataM)
	 */
	public void createModelOrigLoanM(LoanDataM prmLoanDataM)
			throws OrigLoanMException {
		try {
			//generate loand id 
			//int loanID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.LOAN_RECORD_ID));
			//prmLoanDataM.setLoanID(loanID);
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			int loanID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.LOAN_RECORD_ID));
			prmLoanDataM.setLoanID(loanID);
			createTableORIG_LOAN(prmLoanDataM);
			//20080723 add Installment
			if(OrigConstant.INSTALLMENT_TYPE_STEP.equals(prmLoanDataM.getInstallmentFlag())){
			  Vector vStepInstallment=prmLoanDataM.getStepInstallmentVect();  
			if(vStepInstallment!=null){
			    OrigInstallmentMDAO origInstallmentDAO=ORIGDAOFactory.getOrigInstallmentMDAO();
			   for(int i=0;i<vStepInstallment.size();i++){
			       InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vStepInstallment.get(i);
			       prmInstallmentDataM.setApplicationRecordId(prmLoanDataM.getAppRecordID());
			       prmInstallmentDataM.setCreateBy(prmLoanDataM.getUpdateBy());
			       prmInstallmentDataM.setUpdateBy(prmLoanDataM.getUpdateBy());
			       origInstallmentDAO.createModelOrigInstallmentM(prmInstallmentDataM);			       
			    }  
			  }    
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		}

	}

	/**
	 * @param prmLoanDataM
	 */
	private void createTableORIG_LOAN(LoanDataM prmLoanDataM)
			throws OrigLoanMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_LOAN ");
			sql
					.append("( LOAN_ID,APPLICATION_RECORD_ID ,LOAN_TYPE  ,MARKETING_CODE,CAMPAIGN ");
			sql
					.append(" ,INTERNAL_CKECKER,REASON_CAMPAIGN ,EXTERNAL_CKECKER ,OVERRIDE_BY ,CREDIT_APPROVAL ");
			sql
					.append(" ,OVERRIDE_DATE,APPRAISAL_PRICE  ,COLLECTION_CODE  ,BALLOON_FLAG,BALLOON_TYPE");
			sql
					.append(" ,BANK_CODE,BRANCH_CODE ,ACCOUNT_NUMBER   ,ACCOUNT_NAME  ,BOOKING_DATE");
			sql
					.append(" ,CONTRACT_DATE,COLLECTOR_CODE  ,FIRST_DUE_DATE ,END_DUE_DATE ,DOWN_TYPE");
			sql
					.append(" ,DOWN_AMOUNT ,PAYMENT_TYPE  ,FIRST_INSTALLMENT ,NET_RATE ,SPECIAL_HIRE_CHARGE");
			sql
					.append(" ,DOCUMENT_DATE,CHEQUE_DATE   ,VAT   ,COST_OF_CARPRICE ,VAT_OF_CARPRICE");
			sql
					.append(" ,TOTAL_OF_CARPRICE,COST_OF_DOWNPAYMENT ,VAT_OF_DOWNPAYMENT ,TOTAL_OF_DOWNPAYMENT      ,COST_OF_FINANCIAL_AMT");
			sql
					.append(" ,VAT_OF_FINANCIAL_AMT ,TOTAL_OF_FINANCIAL_AMT,COST_OF_BALLOON_AMT ,VAT_OF_BALLOON_AMT        ,TOTAL_OF_BALLOON_AMT");
			sql
					.append(" ,RATE1  ,COST_OF_RATE1 ,VAT_OF_RATE1 ,TOTAL_OF_RATE1 ,INSTALLMENT1");
			sql
					.append(" ,COST_OF_INSTALLMENT1  ,VAT_OF_INSTALLMENT1,TOTAL_OF_INSTALLMENT1, COST_OF_HAIR_PURCHASEA_MT ,VAT_OF_HAIR_PURCHASE_AMT");
			sql
					.append(" ,TOTAL_OF_HAIR_PURCHASE_AMT,EFFECTIVE_RATE, IRR_RATE  ,CREATE_BY      ,CREATE_DATE");
			sql
					.append(" ,UPDATE_BY, UPDATE_DATE, VEHICLE_ID, IDNO, PV_BALLOON_TERM ");
			sql
					.append(" , COST_OF_PV_BALLOON, VAT_OF_PV_BALLOON, TOTAL_OF_PV_BALLOON, COST_OF_PV, VAT_OF_PV ");
			sql
					.append(" , TOTAL_OF_PV, PENALTY_RATE, DISC_RATE, RV_PERCENT, COST_OF_RV ");
			sql
					.append(" , VAT_OF_RV, TOTAL_OF_RV, SUBSIDIES_AMOUNT, AGREEMENT_DATE, EXCUTION_DATE ");
			sql
					.append(" , LASTDUE_DATE, DELIVERY_DATE, EXPIRY_DATE, FIRST_INSTALLMENT_PAY_TYPE, FIRST_INSTALLMENT_DEDUCT, PAY_DEALER ");
			sql
					.append(" , ORIG_DOWN_PAYMENT, ORIG_INSTALLMENT_AMT, ORIG_INSTALLMENT_TERM, ORIG_FINANCE, ACTUAL_CAR_PRICE ");
			sql.append(" , ACTUAL_DOWN,INSTALLMENT_FLAG ");
			sql.append(" , SCHEME_CODE, PURPOSE_LOAN, LOAN_AMOUNT, AMOUNT_FINANCE, ACCEPT_FEE_PERCENT, ACCEPT_FEE, FEE_DISCOUNT_PERCENT, FEE_DISCOUNT ");
			sql.append(" , MONTHLY_INST_AMT_1, MONTHLY_INST_AMT_2, LAST_INST_AMT_1, LAST_INST_AMT_2, FIRST_TIER_RATE, SECOND_TIER_RATE, FIRST_TIER_TERM, SECOND_TIER_TERM ");
		
			
			//Wiroon 20100316
			sql.append(" , MONTHLY_INST_AMT_3, LAST_INST_AMT_3, THIRD_TIER_RATE, THIRD_TIER_TERM ");
			sql.append(" , MONTHLY_INST_AMT_4, LAST_INST_AMT_4, FORTH_TIER_RATE, FORTH_TIER_TERM ");
			
			sql.append(" , SERVICE_FEE, REGISTRATION_FEE, TRANSFER_FEE, PREPAYMENT_FEE");
			
			sql.append(" , MRTA_FLAG");
			
			sql.append(" )");
			
			
			sql
					.append(" VALUES( ?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?  ,?,?,?,?,? ");
			sql
					.append("        ,?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?  ,?,?,?,?,? ");
			sql
					.append("        ,?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?  ,?,?,?,?,SYSDATE ");
			sql.append("        ,?,SYSDATE,?,?,? ,?,?,?,?,? ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append("             ,?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append("             ,?,?,?,?,?  ,?,?,?,? ");
			
			//Wiroon 20100316
			sql.append("             ,?,?,?,? ");
			sql.append("             ,?,?,?,?  ");
			sql.append("             ,?,?,?,? ,?) ");
			

			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, prmLoanDataM.getLoanID());
			ps.setString(2, prmLoanDataM.getAppRecordID());
			ps.setString(3, prmLoanDataM.getLoanType());
			ps.setString(4, prmLoanDataM.getMarketingCode());
			ps.setString(5, prmLoanDataM.getCampaign());
			ps.setString(6, prmLoanDataM.getInternalCkecker());
			ps.setString(7, prmLoanDataM.getReasonCampaign());
			ps.setString(8, prmLoanDataM.getExternalCkecker());
			ps.setString(9, prmLoanDataM.getOverrideBy());
			ps.setString(10, prmLoanDataM.getCreditApproval());
			ps.setDate(11, this.parseDate(prmLoanDataM.getOverrideDate()));
			ps.setBigDecimal(12, prmLoanDataM.getAppraisalPrice());
			ps.setString(13, prmLoanDataM.getCollectionCode());
			ps.setString(14, prmLoanDataM.getBalloonFlag());
			ps.setString(15, prmLoanDataM.getBalloonType());
			ps.setString(16, prmLoanDataM.getBankCode());
			ps.setString(17, prmLoanDataM.getBranchCode());
			ps.setString(18, prmLoanDataM.getAccountNo());
			ps.setString(19, prmLoanDataM.getAccountName());
			ps.setDate(20, this.parseDate(prmLoanDataM.getBookingDate()));
			ps.setDate(21,this.parseDate( prmLoanDataM.getContractDate()));
			ps.setString(22, prmLoanDataM.getCollectorCode());
			ps.setDate(23, this.parseDate(prmLoanDataM.getFirstDueDate()));
			ps.setDate(24, this.parseDate(prmLoanDataM.getEndDueDate()));
			ps.setString(25, prmLoanDataM.getDownType());
			ps.setBigDecimal(26, prmLoanDataM.getDownAmount());
			ps.setString(27, prmLoanDataM.getPaymentType());
			ps.setBigDecimal(28, prmLoanDataM.getFirstInstallment());
			ps.setBigDecimal(29, prmLoanDataM.getNetRate());
			ps.setString(30, prmLoanDataM.getSpecialHireCharge());
			ps.setDate(31, this.parseDate(prmLoanDataM.getDocumentDate()));
			ps.setDate(32, this.parseDate(prmLoanDataM.getChequeDate()));
			ps.setString(33, prmLoanDataM.getVAT());
			ps.setBigDecimal(34, prmLoanDataM.getCostOfCarPrice());
			ps.setBigDecimal(35, prmLoanDataM.getVATOfCarPrice());
			ps.setBigDecimal(36, prmLoanDataM.getTotalOfCarPrice());
			ps.setBigDecimal(37, prmLoanDataM.getCostOfDownPayment());
			ps.setBigDecimal(38, prmLoanDataM.getVATOfDownPayment());
			ps.setBigDecimal(39, prmLoanDataM.getTotalOfDownPayment());
			ps.setBigDecimal(40, prmLoanDataM.getCostOfFinancialAmt());
			ps.setBigDecimal(41, prmLoanDataM.getVATOfFinancialAmt());
			ps.setBigDecimal(42, prmLoanDataM.getTotalOfFinancialAmt());
			ps.setBigDecimal(43, prmLoanDataM.getCostOfBalloonAmt());
			ps.setBigDecimal(44, prmLoanDataM.getVATOfBalloonAmt());
			ps.setBigDecimal(45, prmLoanDataM.getTotalOfBalloonAmt());
			ps.setBigDecimal(46, prmLoanDataM.getRate1());
			ps.setBigDecimal(47, prmLoanDataM.getCostOfRate1());
			ps.setBigDecimal(48, prmLoanDataM.getVATOfRate1());
			ps.setBigDecimal(49, prmLoanDataM.getTotalOfRate1());
			ps.setBigDecimal(50, prmLoanDataM.getInstallment1());
			ps.setBigDecimal(51, prmLoanDataM.getCostOfInstallment1());
			ps.setBigDecimal(52, prmLoanDataM.getVATOfInstallment1());
			ps.setBigDecimal(53, prmLoanDataM.getTotalOfInstallment1());
			
			ps.setBigDecimal(54, prmLoanDataM.getCostOfHairPurchaseAmt());
			ps.setBigDecimal(55, prmLoanDataM.getVATOfHairPurchaseAmt());
			ps.setBigDecimal(56, prmLoanDataM.getTotalOfHairPurchaseAmt());
			ps.setBigDecimal(57, prmLoanDataM.getEffectiveRate());
			ps.setBigDecimal(58, prmLoanDataM.getIRRRate());
			ps.setString(59, prmLoanDataM.getCreateBy());
			ps.setString(60, prmLoanDataM.getUpdateBy());
			
			ps.setInt(61, prmLoanDataM.getVehicleId());
			ps.setString(62, prmLoanDataM.getIdNo());
			
			ps.setBigDecimal(63, prmLoanDataM.getBalloonTerm());
			ps.setBigDecimal(64, prmLoanDataM.getCostOfpvBalloonAmt());
			ps.setBigDecimal(65, prmLoanDataM.getVATOfpvBalloonAmt());
			ps.setBigDecimal(66, prmLoanDataM.getTotalOfpvBalloonAmt());
			ps.setBigDecimal(67, prmLoanDataM.getCostOfpv());
			ps.setBigDecimal(68, prmLoanDataM.getVATOfpv());
			ps.setBigDecimal(69, prmLoanDataM.getTotalOfpv());
			ps.setBigDecimal(70, prmLoanDataM.getPenaltyRate());
			ps.setBigDecimal(71, prmLoanDataM.getDiscRate());
			
			ps.setBigDecimal(72, prmLoanDataM.getRvPercent());
			ps.setBigDecimal(73, prmLoanDataM.getCostOfRV());
			ps.setBigDecimal(74, prmLoanDataM.getVATOfRV());
			ps.setBigDecimal(75, prmLoanDataM.getTotalOfRV());
			
			ps.setBigDecimal(76, prmLoanDataM.getSubsidiesAmount());
			ps.setDate(77, this.parseDate(prmLoanDataM.getAgreementDate()));
			ps.setDate(78, this.parseDate(prmLoanDataM.getExcutionDate()));
			ps.setDate(79, this.parseDate(prmLoanDataM.getLastDueDate()));
			ps.setDate(80, this.parseDate(prmLoanDataM.getDeliveryDate()));
			ps.setDate(81, this.parseDate(prmLoanDataM.getExpiryDate()));
			ps.setString(82, prmLoanDataM.getFirstInsPayType());
			ps.setString(83, prmLoanDataM.getFirstInsDeduct());
			ps.setString(84, prmLoanDataM.getPayDealer());
			
			ps.setBigDecimal(85, prmLoanDataM.getOrigDownPayment());
			ps.setBigDecimal(86, prmLoanDataM.getOrigInstallmentAmt());
			ps.setBigDecimal(87, prmLoanDataM.getOrigInstallmentTerm());
			ps.setBigDecimal(88, prmLoanDataM.getOrigFinance());
			ps.setBigDecimal(89, prmLoanDataM.getActualCarPrice());
			ps.setBigDecimal(90, prmLoanDataM.getActualDown());
			ps.setString(91,prmLoanDataM.getInstallmentFlag());
			ps.setString(92,prmLoanDataM.getSchemeCode());
			ps.setString(93,prmLoanDataM.getPurposeLoan());
			ps.setBigDecimal(94,prmLoanDataM.getLoanAmt());
			ps.setBigDecimal(95,prmLoanDataM.getAmountFinance());
			ps.setBigDecimal(96,prmLoanDataM.getAcceptanceFeePercent());
			ps.setBigDecimal(97,prmLoanDataM.getAcceptanceFee());
			ps.setBigDecimal(98,prmLoanDataM.getFeeDiscountPercent());
			ps.setBigDecimal(99,prmLoanDataM.getFeeDiscount());
			ps.setBigDecimal(100,prmLoanDataM.getMonthlyIntalmentAmtOne());
			ps.setBigDecimal(101,prmLoanDataM.getMonthlyIntalmentAmtTwo());
			ps.setBigDecimal(102,prmLoanDataM.getLastIntalmentAmtOne());
			ps.setBigDecimal(103,prmLoanDataM.getLastIntalmentAmtTwo());
			ps.setBigDecimal(104,prmLoanDataM.getFirstTierRate());
			ps.setBigDecimal(105,prmLoanDataM.getSecondTierRate());
			ps.setInt(106,prmLoanDataM.getFirstTierTerm());
			ps.setInt(107,prmLoanDataM.getSecondTierTerm());
			
			//Wiroon 20100316
			int i=108;
			ps.setBigDecimal(i++,prmLoanDataM.getMonthlyIntalmentAmtThree());
			ps.setBigDecimal(i++,prmLoanDataM.getLastIntalmentAmtThree());
			ps.setBigDecimal(i++,prmLoanDataM.getThirdTierRate());
			ps.setInt(i++,prmLoanDataM.getThirdTierTerm());
			ps.setBigDecimal(i++,prmLoanDataM.getMonthlyIntalmentAmtFour());
			ps.setBigDecimal(i++,prmLoanDataM.getLastIntalmentAmtFour());
			ps.setBigDecimal(i++,prmLoanDataM.getForthTierRate());
			ps.setInt(i++,prmLoanDataM.getForthTierTerm());
			
			ps.setBigDecimal(i++,prmLoanDataM.getServiceFee());
			ps.setBigDecimal(i++,prmLoanDataM.getRegistrationFee());
			ps.setBigDecimal(i++,prmLoanDataM.getTransferFee());
			ps.setBigDecimal(i++,prmLoanDataM.getPrepaymentFee());
			
			ps.setString(i++,prmLoanDataM.getMRTAFlag());
			
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigLoanMDAO#deleteModelOrigLoanM(com.eaf.orig.shared.model.LoanDataM)
	 */
	public void deleteModelOrigLoanM(LoanDataM prmLoanDataM)
			throws OrigLoanMException {
		try {
			deleteTableORIG_LOAN(prmLoanDataM);
			
			//20080723 delete installment
			ORIGDAOFactory.getOrigInstallmentMDAO().deleteTableORIG_INSTALLMENT(prmLoanDataM.getAppRecordID());
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		}

	}

	/**
	 * @param prmLoanDataM
	 */
	private void deleteTableORIG_LOAN(LoanDataM prmLoanDataM)
			throws OrigLoanMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_LOAN ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND LOAN_ID=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmLoanDataM.getAppRecordID());
			ps.setInt(2, prmLoanDataM.getLoanID());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigLoanMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigLoanMDAO#loadModelOrigLoanM(java.lang.String)
	 */
	public Vector loadModelOrigLoanM(String applicationRecordId)
			throws OrigLoanMException {
		try {
			Vector result = selectTableORIG_LOAN(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_LOAN(String applicationRecordId)
			throws OrigLoanMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT  LOAN_TYPE   ,MARKETING_CODE,CAMPAIGN ");
			sql
					.append(" ,INTERNAL_CKECKER ,REASON_CAMPAIGN  ,EXTERNAL_CKECKER  ,OVERRIDE_BY  ,CREDIT_APPROVAL ");
			sql
					.append(" ,OVERRIDE_DATE ,APPRAISAL_PRICE  ,COLLECTION_CODE ,BALLOON_FLAG ,BALLOON_TYPE");
			sql
					.append(" ,BANK_CODE ,BRANCH_CODE ,ACCOUNT_NUMBER ,ACCOUNT_NAME ,BOOKING_DATE");
			sql
					.append(" ,CONTRACT_DATE,COLLECTOR_CODE  ,FIRST_DUE_DATE ,END_DUE_DATE ,DOWN_TYPE");
			sql
					.append(" ,DOWN_AMOUNT ,PAYMENT_TYPE  ,FIRST_INSTALLMENT ,NET_RATE ,SPECIAL_HIRE_CHARGE");
			sql
					.append(" ,DOCUMENT_DATE,CHEQUE_DATE,VAT  ,COST_OF_CARPRICE  ,VAT_OF_CARPRICE");
			sql
					.append(" ,TOTAL_OF_CARPRICE,COST_OF_DOWNPAYMENT ,VAT_OF_DOWNPAYMENT  ,TOTAL_OF_DOWNPAYMENT ,COST_OF_FINANCIAL_AMT");
			sql
					.append(" ,VAT_OF_FINANCIAL_AMT ,TOTAL_OF_FINANCIAL_AMT,COST_OF_BALLOON_AMT  ,VAT_OF_BALLOON_AMT ,TOTAL_OF_BALLOON_AMT");
			sql
					.append(" ,RATE1 ,COST_OF_RATE1 ,VAT_OF_RATE1,TOTAL_OF_RATE1 ,INSTALLMENT1");
			sql
					.append(" ,COST_OF_INSTALLMENT1 ,VAT_OF_INSTALLMENT1,TOTAL_OF_INSTALLMENT1 ,COST_OF_HAIR_PURCHASEA_MT ,VAT_OF_HAIR_PURCHASE_AMT");
			sql
					.append(" ,TOTAL_OF_HAIR_PURCHASE_AMT,EFFECTIVE_RATE ,IRR_RATE ,CREATE_BY ,CREATE_DATE ,UPDATE_BY,UPDATE_DATE,LOAN_ID  ");
			sql
					.append(" ,PV_BALLOON_TERM, COST_OF_PV_BALLOON, VAT_OF_PV_BALLOON, TOTAL_OF_PV_BALLOON, COST_OF_PV, VAT_OF_PV, TOTAL_OF_PV, PENALTY_RATE, DISC_RATE ");
			sql
					.append(" ,RV_PERCENT, COST_OF_RV, VAT_OF_RV, TOTAL_OF_RV, SUBSIDIES_AMOUNT, AGREEMENT_DATE ");
			sql
					.append(" ,EXCUTION_DATE,  LASTDUE_DATE, DELIVERY_DATE, EXPIRY_DATE, FIRST_INSTALLMENT_PAY_TYPE, FIRST_INSTALLMENT_DEDUCT, PAY_DEALER ");
			sql
					.append(", ORIG_DOWN_PAYMENT, ORIG_INSTALLMENT_AMT, ORIG_INSTALLMENT_TERM, ORIG_FINANCE, ACTUAL_CAR_PRICE, ACTUAL_DOWN,INSTALLMENT_FLAG ");
			sql.append(" , SCHEME_CODE, PURPOSE_LOAN, LOAN_AMOUNT, AMOUNT_FINANCE, ACCEPT_FEE_PERCENT, ACCEPT_FEE, FEE_DISCOUNT_PERCENT, FEE_DISCOUNT ");
			sql.append(" , MONTHLY_INST_AMT_1, MONTHLY_INST_AMT_2, LAST_INST_AMT_1, LAST_INST_AMT_2, FIRST_TIER_RATE, SECOND_TIER_RATE, FIRST_TIER_TERM, SECOND_TIER_TERM ");
		
			//TODO
			sql.append(" , MONTHLY_INST_AMT_3, LAST_INST_AMT_3, THIRD_TIER_RATE, THIRD_TIER_TERM ");
			sql.append(" , MONTHLY_INST_AMT_4, LAST_INST_AMT_4, FORTH_TIER_RATE, FORTH_TIER_TERM ");
			
			sql.append(" , SERVICE_FEE, REGISTRATION_FEE, TRANSFER_FEE, PREPAYMENT_FEE");
			
			sql.append(" , MRTA_FLAG");
			
			sql
					.append(" FROM ORIG_LOAN WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			ps.setString(1, applicationRecordId);
			//ps.setInt(2, loanId);

			rs = ps.executeQuery();
			Vector vLoanDataM = new Vector();
			OrigInstallmentMDAO  origInstallmentDAO=ORIGDAOFactory.getOrigInstallmentMDAO();
			while (rs.next()) {
				LoanDataM loanDataM = new LoanDataM();

				loanDataM.setLoanType(rs.getString(1));
				loanDataM.setMarketingCode(rs.getString(2));
				loanDataM.setCampaign(rs.getString(3));
				loanDataM.setInternalCkecker(rs.getString(4));
				loanDataM.setReasonCampaign(rs.getString(5));
				loanDataM.setExternalCkecker(rs.getString(6));
				loanDataM.setOverrideBy(rs.getString(7));
				loanDataM.setCreditApproval(rs.getString(8));
				loanDataM.setOverrideDate(rs.getDate(9));
				loanDataM.setAppraisalPrice(rs.getBigDecimal(10));
				loanDataM.setCollectionCode(rs.getString(11));
				loanDataM.setBalloonFlag(rs.getString(12));
				loanDataM.setBalloonType(rs.getString(13));
				loanDataM.setBankCode(rs.getString(14));
				loanDataM.setBranchCode(rs.getString(15));
				loanDataM.setAccountNo(rs.getString(16));
				loanDataM.setAccountName(rs.getString(17));
				loanDataM.setBookingDate(rs.getDate(18));
				loanDataM.setContractDate(rs.getDate(19));
				loanDataM.setCollectorCode(rs.getString(20));
				loanDataM.setFirstDueDate(rs.getDate(21));
				loanDataM.setEndDueDate(rs.getDate(22));
				loanDataM.setDownType(rs.getString(23));
				loanDataM.setDownAmount(rs.getBigDecimal(24));
				loanDataM.setPaymentType(rs.getString(25));
				loanDataM.setFirstInstallment(rs.getBigDecimal(26));
				loanDataM.setNetRate(rs.getBigDecimal(27));
				loanDataM.setSpecialHireCharge(rs.getString(28));
				loanDataM.setDocumentDate(rs.getDate(29));
				loanDataM.setChequeDate(rs.getDate(30));
				loanDataM.setVAT(rs.getString(31));
				loanDataM.setCostOfCarPrice(rs.getBigDecimal(32));
				loanDataM.setVATOfCarPrice(rs.getBigDecimal(33));
				loanDataM.setTotalOfCarPrice(rs.getBigDecimal(34));
				loanDataM.setCostOfDownPayment(rs.getBigDecimal(35));
				loanDataM.setVATOfDownPayment(rs.getBigDecimal(36));
				loanDataM.setTotalOfDownPayment(rs.getBigDecimal(37));
				loanDataM.setCostOfFinancialAmt(rs.getBigDecimal(38));
				loanDataM.setVATOfFinancialAmt(rs.getBigDecimal(39));
				loanDataM.setTotalOfFinancialAmt(rs.getBigDecimal(40));
				loanDataM.setCostOfBalloonAmt(rs.getBigDecimal(41));
				loanDataM.setVATOfBalloonAmt(rs.getBigDecimal(42));
				loanDataM.setTotalOfBalloonAmt(rs.getBigDecimal(43));
				loanDataM.setRate1(rs.getBigDecimal(44));
				loanDataM.setCostOfRate1(rs.getBigDecimal(45));
				loanDataM.setVATOfRate1(rs.getBigDecimal(46));
				loanDataM.setTotalOfRate1(rs.getBigDecimal(47));
				loanDataM.setInstallment1(rs.getBigDecimal(48));
				loanDataM.setCostOfInstallment1(rs.getBigDecimal(49));
				loanDataM.setVATOfInstallment1(rs.getBigDecimal(50));
				loanDataM.setTotalOfInstallment1(rs.getBigDecimal(51));
				
				loanDataM.setCostOfHairPurchaseAmt(rs.getBigDecimal(52));
				loanDataM.setVATOfHairPurchaseAmt(rs.getBigDecimal(53));
				loanDataM.setTotalOfHairPurchaseAmt(rs.getBigDecimal(54));
				loanDataM.setEffectiveRate(rs.getBigDecimal(55));
				loanDataM.setIRRRate(rs.getBigDecimal(56));
				loanDataM.setCreateBy(rs.getString(57));
				loanDataM.setCreateDate(rs.getTimestamp(58));
				loanDataM.setUpdateBy(rs.getString(59));
				loanDataM.setUpdateDate(rs.getTimestamp(60));
				loanDataM.setLoanID(rs.getInt(61));
				
				loanDataM.setBalloonTerm(rs.getBigDecimal(62));
				loanDataM.setCostOfpvBalloonAmt(rs.getBigDecimal(63));
				loanDataM.setVATOfpvBalloonAmt(rs.getBigDecimal(64));
				loanDataM.setTotalOfpvBalloonAmt(rs.getBigDecimal(65));
				loanDataM.setCostOfpv(rs.getBigDecimal(66));
				loanDataM.setVATOfpv(rs.getBigDecimal(67));
				loanDataM.setTotalOfpv(rs.getBigDecimal(68));
				loanDataM.setPenaltyRate(rs.getBigDecimal(69));
				loanDataM.setDiscRate(rs.getBigDecimal(70));
				
				loanDataM.setRvPercent(rs.getBigDecimal(71));
				loanDataM.setCostOfRV(rs.getBigDecimal(72));
				loanDataM.setVATOfRV(rs.getBigDecimal(73));
				loanDataM.setTotalOfRV(rs.getBigDecimal(74));
				
				loanDataM.setSubsidiesAmount(rs.getBigDecimal(75));
				loanDataM.setAgreementDate(rs.getDate(76));
				loanDataM.setExcutionDate(rs.getDate(77));
				loanDataM.setLastDueDate(rs.getDate(78));
				loanDataM.setDeliveryDate(rs.getDate(79));
				loanDataM.setExpiryDate(rs.getDate(80));
				loanDataM.setFirstInsPayType(rs.getString(81));
				loanDataM.setFirstInsDeduct(rs.getString(82));
				loanDataM.setPayDealer(rs.getString(83));
				
				loanDataM.setOrigDownPayment(rs.getBigDecimal(84));
				loanDataM.setOrigInstallmentAmt(rs.getBigDecimal(85));
				loanDataM.setOrigInstallmentTerm(rs.getBigDecimal(86));
				loanDataM.setOrigFinance(rs.getBigDecimal(87));
				loanDataM.setActualCarPrice(rs.getBigDecimal(88));
				loanDataM.setActualDown(rs.getBigDecimal(89));
				loanDataM.setActualCarPriceTmp(rs.getBigDecimal(88));
				loanDataM.setActualDownTmp(rs.getBigDecimal(89));
				loanDataM.setInstallmentFlag(rs.getString(90));

				loanDataM.setSchemeCode(rs.getString("SCHEME_CODE"));
				loanDataM.setPurposeLoan(rs.getString("PURPOSE_LOAN"));
				loanDataM.setLoanAmt(rs.getBigDecimal("LOAN_AMOUNT"));
				loanDataM.setAmountFinance(rs.getBigDecimal("AMOUNT_FINANCE"));
				loanDataM.setAcceptanceFeePercent(rs.getBigDecimal("ACCEPT_FEE_PERCENT"));
				loanDataM.setAcceptanceFee(rs.getBigDecimal("ACCEPT_FEE"));
				loanDataM.setFeeDiscountPercent(rs.getBigDecimal("FEE_DISCOUNT_PERCENT"));
				loanDataM.setFeeDiscount(rs.getBigDecimal("FEE_DISCOUNT"));
				loanDataM.setMonthlyIntalmentAmtOne(rs.getBigDecimal("MONTHLY_INST_AMT_1"));
				loanDataM.setMonthlyIntalmentAmtTwo(rs.getBigDecimal("MONTHLY_INST_AMT_2"));
				loanDataM.setLastIntalmentAmtOne(rs.getBigDecimal("LAST_INST_AMT_1"));
				loanDataM.setLastIntalmentAmtTwo(rs.getBigDecimal("LAST_INST_AMT_2"));
				loanDataM.setFirstTierRate(rs.getBigDecimal("FIRST_TIER_RATE"));
				loanDataM.setSecondTierRate(rs.getBigDecimal("SECOND_TIER_RATE"));
				loanDataM.setFirstTierTerm(rs.getInt("FIRST_TIER_TERM"));
				loanDataM.setSecondTierTerm(rs.getInt("SECOND_TIER_TERM"));
			
				loanDataM.setMonthlyIntalmentAmtThree(rs.getBigDecimal("MONTHLY_INST_AMT_3"));
				loanDataM.setLastIntalmentAmtThree(rs.getBigDecimal("LAST_INST_AMT_3"));
				loanDataM.setThirdTierRate(rs.getBigDecimal("THIRD_TIER_RATE"));
				loanDataM.setThirdTierTerm(rs.getInt("THIRD_TIER_TERM"));
				
				loanDataM.setMonthlyIntalmentAmtFour(rs.getBigDecimal("MONTHLY_INST_AMT_4"));
				loanDataM.setLastIntalmentAmtFour(rs.getBigDecimal("LAST_INST_AMT_4"));
				loanDataM.setForthTierRate(rs.getBigDecimal("FORTH_TIER_RATE"));
				loanDataM.setForthTierTerm(rs.getInt("FORTH_TIER_TERM"));
				
				
				loanDataM.setServiceFee(rs.getBigDecimal("SERVICE_FEE"));
				loanDataM.setRegistrationFee(rs.getBigDecimal("REGISTRATION_FEE"));
				loanDataM.setTransferFee(rs.getBigDecimal("TRANSFER_FEE"));
				loanDataM.setPrepaymentFee(rs.getBigDecimal("PREPAYMENT_FEE"));
				
				loanDataM.setMRTAFlag(rs.getString("MRTA_FLAG"));
				
				
				if("Y".equalsIgnoreCase(loanDataM.getBalloonFlag())){
					loanDataM.setTermTemp(loanDataM.getBalloonTerm());
				}else{
					loanDataM.setTermTemp(loanDataM.getInstallment1());
				}
				loanDataM.setDownPaymentTemp(loanDataM.getCostOfDownPayment());
				loanDataM.setRateTemp(loanDataM.getRate1());
				loanDataM.setCampaignTemp(loanDataM.getCampaign());
				if(OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())){
				Vector vStepInstallment=origInstallmentDAO.loadModelOrigInstallmentM(applicationRecordId);
				loanDataM.setStepInstallmentVect(vStepInstallment);
				}
				//get Installment
				vLoanDataM.add(loanDataM);
			}
			return vLoanDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigLoanMDAO#saveUpdateModelOrigLoanM(com.eaf.orig.shared.model.LoanDataM)
	 */
	public void saveUpdateModelOrigLoanM(LoanDataM prmLoanDataM)
			throws OrigLoanMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_LOAN(prmLoanDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_LOAN then call Insert method");
				//generate loand id 
				//int loanID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.LOAN_RECORD_ID));
				//prmLoanDataM.setLoanID(loanID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				int loanID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.LOAN_RECORD_ID));
				prmLoanDataM.setLoanID(loanID);
				createTableORIG_LOAN(prmLoanDataM);
			}
			 Vector vStepInstallment=prmLoanDataM.getStepInstallmentVect();
			   if(!OrigConstant.INSTALLMENT_TYPE_STEP.equals(prmLoanDataM.getInstallmentFlag())){
			      vStepInstallment=new Vector();
			  }				 
				OrigInstallmentMDAO origInstallmentDAO=ORIGDAOFactory.getOrigInstallmentMDAO();
				if(vStepInstallment!=null){				  
				   for(int i=0;i<vStepInstallment.size();i++){
				       InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vStepInstallment.get(i);
				       prmInstallmentDataM.setApplicationRecordId(prmLoanDataM.getAppRecordID());
				       prmInstallmentDataM.setCreateBy(prmLoanDataM.getUpdateBy());
				       prmInstallmentDataM.setUpdateBy(prmLoanDataM.getUpdateBy());
				       origInstallmentDAO.saveUpdateModelOrigInstallmentM(prmInstallmentDataM);
				    }  
				  }    
				origInstallmentDAO.deleteNotInKeyTableORIG_Installment(vStepInstallment,prmLoanDataM.getAppRecordID());
				//}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		}

	}

	/**
	 * @param prmLoanDataM
	 * @return
	 */
	private double updateTableORIG_LOAN(LoanDataM prmLoanDataM)
			throws OrigLoanMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_LOAN ");
			sql.append(" SET  LOAN_TYPE=?  ,MARKETING_CODE=?   ,CAMPAIGN=? ");
			sql
					.append(" ,INTERNAL_CKECKER=?   ,REASON_CAMPAIGN =?  ,EXTERNAL_CKECKER=?  ,OVERRIDE_BY=?,CREDIT_APPROVAL=? ");
			sql
					.append(" ,OVERRIDE_DATE =?,APPRAISAL_PRICE=? ,COLLECTION_CODE =? ,BALLOON_FLAG =?,BALLOON_TYPE=?");
			sql
					.append(" ,BANK_CODE=?,BRANCH_CODE =?,ACCOUNT_NUMBER=? ,ACCOUNT_NAME=?,BOOKING_DATE=?");
			sql
					.append(" ,CONTRACT_DATE=? ,COLLECTOR_CODE=?,FIRST_DUE_DATE=?,END_DUE_DATE=?,DOWN_TYPE=?");
			sql
					.append(" ,DOWN_AMOUNT =? ,PAYMENT_TYPE =? ,FIRST_INSTALLMENT=?  ,NET_RATE=?  ,SPECIAL_HIRE_CHARGE=?");
			sql
					.append(" ,DOCUMENT_DATE=?  ,CHEQUE_DATE=? ,VAT=? ,COST_OF_CARPRICE=? ,VAT_OF_CARPRICE=?");
			sql
					.append(" ,TOTAL_OF_CARPRICE=? ,COST_OF_DOWNPAYMENT=?,VAT_OF_DOWNPAYMENT=?,TOTAL_OF_DOWNPAYMENT=?,COST_OF_FINANCIAL_AMT=?");
			sql
					.append(" ,VAT_OF_FINANCIAL_AMT=? ,TOTAL_OF_FINANCIAL_AMT=? ,COST_OF_BALLOON_AMT=?,VAT_OF_BALLOON_AMT=?,TOTAL_OF_BALLOON_AMT=?");
			sql
					.append(" ,RATE1 =?,COST_OF_RATE1 =? ,VAT_OF_RATE1 =? ,TOTAL_OF_RATE1=?,INSTALLMENT1=?");
			sql
					.append(" ,COST_OF_INSTALLMENT1 =?,VAT_OF_INSTALLMENT1 =? ,TOTAL_OF_INSTALLMENT1 =?, COST_OF_HAIR_PURCHASEA_MT=? ,VAT_OF_HAIR_PURCHASE_AMT=?");
			sql
					.append(" ,TOTAL_OF_HAIR_PURCHASE_AMT=?,EFFECTIVE_RATE =? ,IRR_RATE=?  ,UPDATE_BY = ?,UPDATE_DATE =SYSDATE  ");
			sql
					.append(" ,PV_BALLOON_TERM=?, COST_OF_PV_BALLOON=?, VAT_OF_PV_BALLOON=?, TOTAL_OF_PV_BALLOON=?, COST_OF_PV=?, VAT_OF_PV=?, TOTAL_OF_PV=?, PENALTY_RATE=?, DISC_RATE=? ");
			sql
					.append(" ,RV_PERCENT=?, COST_OF_RV=?, VAT_OF_RV=?, TOTAL_OF_RV=?,SUBSIDIES_AMOUNT=?, AGREEMENT_DATE=? ");
			sql
					.append(" ,EXCUTION_DATE=?,  LASTDUE_DATE=?, DELIVERY_DATE=?, EXPIRY_DATE=?, FIRST_INSTALLMENT_PAY_TYPE=?, FIRST_INSTALLMENT_DEDUCT=?, PAY_DEALER=? ");
			sql
					.append(", ORIG_DOWN_PAYMENT=?, ORIG_INSTALLMENT_AMT=?, ORIG_INSTALLMENT_TERM=?, ORIG_FINANCE=?, ACTUAL_CAR_PRICE=?, ACTUAL_DOWN=?  ");
			sql.append( " ,INSTALLMENT_FLAG=? ");
			sql.append(" , SCHEME_CODE=?, PURPOSE_LOAN=?, LOAN_AMOUNT=?, AMOUNT_FINANCE=?, ACCEPT_FEE_PERCENT=?, ACCEPT_FEE=?, FEE_DISCOUNT_PERCENT=?, FEE_DISCOUNT=? ");
			sql.append(" , MONTHLY_INST_AMT_1=?, MONTHLY_INST_AMT_2=?, LAST_INST_AMT_1=?, LAST_INST_AMT_2=?, FIRST_TIER_RATE=?, SECOND_TIER_RATE=?, FIRST_TIER_TERM=?, SECOND_TIER_TERM=? ");
			
			//TODO
			
			//Wiroon 20100316
			sql.append(" , MONTHLY_INST_AMT_3=?, LAST_INST_AMT_3=?, THIRD_TIER_RATE=?, THIRD_TIER_TERM=? ");
			sql.append(" , MONTHLY_INST_AMT_4=?, LAST_INST_AMT_4=?, FORTH_TIER_RATE=?, FORTH_TIER_TERM=? ");
			
			sql.append(" , SERVICE_FEE=?, REGISTRATION_FEE=?, TRANSFER_FEE=?, PREPAYMENT_FEE=?");
			
			sql.append(" , MRTA_FLAG=?");
			
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND LOAN_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmLoanDataM.getLoanType());
			ps.setString(2, prmLoanDataM.getMarketingCode());
			ps.setString(3, prmLoanDataM.getCampaign());
			ps.setString(4, prmLoanDataM.getInternalCkecker());
			ps.setString(5, prmLoanDataM.getReasonCampaign());
			ps.setString(6, prmLoanDataM.getExternalCkecker());
			ps.setString(7, prmLoanDataM.getOverrideBy());
			ps.setString(8, prmLoanDataM.getCreditApproval());
			ps.setDate(9, this.parseDate(prmLoanDataM.getOverrideDate()));
			ps.setBigDecimal(10, prmLoanDataM.getAppraisalPrice());
			ps.setString(11, prmLoanDataM.getCollectionCode());
			ps.setString(12, prmLoanDataM.getBalloonFlag());
			ps.setString(13, prmLoanDataM.getBalloonType());
			ps.setString(14, prmLoanDataM.getBankCode());
			ps.setString(15, prmLoanDataM.getBranchCode());
			ps.setString(16, prmLoanDataM.getAccountNo());
			ps.setString(17, prmLoanDataM.getAccountName());
			ps.setDate(18,this.parseDate( prmLoanDataM.getBookingDate()));
			ps.setDate(19,this.parseDate( prmLoanDataM.getContractDate()));
			ps.setString(20, prmLoanDataM.getCollectorCode());
			ps.setDate(21, this.parseDate(prmLoanDataM.getFirstDueDate()));
			ps.setDate(22, this.parseDate(prmLoanDataM.getEndDueDate()));
			ps.setString(23, prmLoanDataM.getDownType());
			ps.setBigDecimal(24, prmLoanDataM.getDownAmount());
			ps.setString(25, prmLoanDataM.getPaymentType());
			ps.setBigDecimal(26, prmLoanDataM.getFirstInstallment());
			ps.setBigDecimal(27, prmLoanDataM.getNetRate());
			ps.setString(28, prmLoanDataM.getSpecialHireCharge());
			ps.setDate(29, this.parseDate(prmLoanDataM.getDocumentDate()));
			ps.setDate(30, this.parseDate(prmLoanDataM.getChequeDate()));
			ps.setString(31, prmLoanDataM.getVAT());
			ps.setBigDecimal(32, prmLoanDataM.getCostOfCarPrice());
			ps.setBigDecimal(33, prmLoanDataM.getVATOfCarPrice());
			ps.setBigDecimal(34, prmLoanDataM.getTotalOfCarPrice());
			ps.setBigDecimal(35, prmLoanDataM.getCostOfDownPayment());
			ps.setBigDecimal(36, prmLoanDataM.getVATOfDownPayment());
			ps.setBigDecimal(37, prmLoanDataM.getTotalOfDownPayment());
			ps.setBigDecimal(38, prmLoanDataM.getCostOfFinancialAmt());
			ps.setBigDecimal(39, prmLoanDataM.getVATOfFinancialAmt());
			ps.setBigDecimal(40, prmLoanDataM.getTotalOfFinancialAmt());
			ps.setBigDecimal(41, prmLoanDataM.getCostOfBalloonAmt());
			ps.setBigDecimal(42, prmLoanDataM.getVATOfBalloonAmt());
			ps.setBigDecimal(43, prmLoanDataM.getTotalOfBalloonAmt());
			ps.setBigDecimal(44, prmLoanDataM.getRate1());
			ps.setBigDecimal(45, prmLoanDataM.getCostOfRate1());
			ps.setBigDecimal(46, prmLoanDataM.getVATOfRate1());
			ps.setBigDecimal(47, prmLoanDataM.getTotalOfRate1());
			ps.setBigDecimal(48, prmLoanDataM.getInstallment1());
			ps.setBigDecimal(49, prmLoanDataM.getCostOfInstallment1());
			ps.setBigDecimal(50, prmLoanDataM.getVATOfInstallment1());
			ps.setBigDecimal(51, prmLoanDataM.getTotalOfInstallment1());
			
			ps.setBigDecimal(52, prmLoanDataM.getCostOfHairPurchaseAmt());
			ps.setBigDecimal(53, prmLoanDataM.getVATOfHairPurchaseAmt());
			ps.setBigDecimal(54, prmLoanDataM.getTotalOfHairPurchaseAmt());
			ps.setBigDecimal(55, prmLoanDataM.getEffectiveRate());
			ps.setBigDecimal(56, prmLoanDataM.getIRRRate());
			ps.setString(57, prmLoanDataM.getUpdateBy());
			
			ps.setBigDecimal(58, prmLoanDataM.getBalloonTerm());
			ps.setBigDecimal(59, prmLoanDataM.getCostOfpvBalloonAmt());
			ps.setBigDecimal(60, prmLoanDataM.getVATOfpvBalloonAmt());
			ps.setBigDecimal(61, prmLoanDataM.getTotalOfpvBalloonAmt());
			ps.setBigDecimal(62, prmLoanDataM.getCostOfpv());
			ps.setBigDecimal(63, prmLoanDataM.getVATOfpv());
			ps.setBigDecimal(64, prmLoanDataM.getTotalOfpv());
			ps.setBigDecimal(65, prmLoanDataM.getPenaltyRate());
			ps.setBigDecimal(66, prmLoanDataM.getDiscRate());
			
			ps.setBigDecimal(67, prmLoanDataM.getRvPercent());
			ps.setBigDecimal(68, prmLoanDataM.getCostOfRV());
			ps.setBigDecimal(69, prmLoanDataM.getVATOfRV());
			ps.setBigDecimal(70, prmLoanDataM.getTotalOfRV());
			
			ps.setBigDecimal(71, prmLoanDataM.getSubsidiesAmount());
			ps.setDate(72, this.parseDate(prmLoanDataM.getAgreementDate()));
			ps.setDate(73, this.parseDate(prmLoanDataM.getExcutionDate()));
			ps.setDate(74, this.parseDate(prmLoanDataM.getLastDueDate()));
			ps.setDate(75, this.parseDate(prmLoanDataM.getDeliveryDate()));
			ps.setDate(76, this.parseDate(prmLoanDataM.getExpiryDate()));
			ps.setString(77, prmLoanDataM.getFirstInsPayType());
			ps.setString(78, prmLoanDataM.getFirstInsDeduct());
			ps.setString(79, prmLoanDataM.getPayDealer());
			
			ps.setBigDecimal(80, prmLoanDataM.getOrigDownPayment());
			ps.setBigDecimal(81, prmLoanDataM.getOrigInstallmentAmt());
			ps.setBigDecimal(82, prmLoanDataM.getOrigInstallmentTerm());
			ps.setBigDecimal(83, prmLoanDataM.getOrigFinance());
			ps.setBigDecimal(84, prmLoanDataM.getActualCarPrice());
			ps.setBigDecimal(85, prmLoanDataM.getActualDown());
			ps.setString(86,prmLoanDataM.getInstallmentFlag());
			
			ps.setString(87,prmLoanDataM.getSchemeCode());
			ps.setString(88,prmLoanDataM.getPurposeLoan());
			ps.setBigDecimal(89,prmLoanDataM.getLoanAmt());
			ps.setBigDecimal(90,prmLoanDataM.getAmountFinance());
			ps.setBigDecimal(91,prmLoanDataM.getAcceptanceFeePercent());
			ps.setBigDecimal(92,prmLoanDataM.getAcceptanceFee());
			ps.setBigDecimal(93,prmLoanDataM.getFeeDiscountPercent());
			ps.setBigDecimal(94,prmLoanDataM.getFeeDiscount());
			ps.setBigDecimal(95,prmLoanDataM.getMonthlyIntalmentAmtOne());
			ps.setBigDecimal(96,prmLoanDataM.getMonthlyIntalmentAmtTwo());
			ps.setBigDecimal(97,prmLoanDataM.getLastIntalmentAmtOne());
			ps.setBigDecimal(98,prmLoanDataM.getLastIntalmentAmtTwo());
			ps.setBigDecimal(99,prmLoanDataM.getFirstTierRate());
			ps.setBigDecimal(100,prmLoanDataM.getSecondTierRate());
			ps.setInt(101,prmLoanDataM.getFirstTierTerm());
			ps.setInt(102,prmLoanDataM.getSecondTierTerm());
			
			//Wiroon 20100316
			
			int i=103;
			ps.setBigDecimal(i++,prmLoanDataM.getMonthlyIntalmentAmtThree());
			ps.setBigDecimal(i++,prmLoanDataM.getLastIntalmentAmtThree());
			ps.setBigDecimal(i++,prmLoanDataM.getThirdTierRate());
			ps.setInt(i++,prmLoanDataM.getThirdTierTerm());
			ps.setBigDecimal(i++,prmLoanDataM.getMonthlyIntalmentAmtFour());
			ps.setBigDecimal(i++,prmLoanDataM.getLastIntalmentAmtFour());
			ps.setBigDecimal(i++,prmLoanDataM.getForthTierRate());
			ps.setInt(i++,prmLoanDataM.getForthTierTerm());
			
			ps.setBigDecimal(i++,prmLoanDataM.getServiceFee());
			ps.setBigDecimal(i++,prmLoanDataM.getRegistrationFee());
			ps.setBigDecimal(i++,prmLoanDataM.getTransferFee());
			ps.setBigDecimal(i++,prmLoanDataM.getPrepaymentFee());
			
			ps.setString(i++,prmLoanDataM.getMRTAFlag());
			
			ps.setString(i++, prmLoanDataM.getAppRecordID());
			ps.setInt(i++, prmLoanDataM.getLoanID());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigLoanMDAO#saveUpdateModelOrigLoanM(com.eaf.orig.shared.model.LoanDataM)
	 */
	public void saveUpdateModelOrigLoanMForCreateProposal(LoanDataM prmLoanDataM)
			throws OrigLoanMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_LOANForCreateCar(prmLoanDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_LOAN then call Insert method");
				//generate loand id 
				//int loanID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.LOAN_RECORD_ID));
				//prmLoanDataM.setLoanID(loanID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				int loanID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.LOAN_RECORD_ID));
				prmLoanDataM.setLoanID(loanID);
				createTableORIG_LOAN(prmLoanDataM);
			}
			Vector vStepInstallment=prmLoanDataM.getStepInstallmentVect();
			   if(!OrigConstant.INSTALLMENT_TYPE_STEP.equals(prmLoanDataM.getInstallmentFlag())){
			      vStepInstallment=new Vector();
			  }				 
				OrigInstallmentMDAO origInstallmentDAO=ORIGDAOFactory.getOrigInstallmentMDAO();
				if(vStepInstallment!=null){				  
				   for(int i=0;i<vStepInstallment.size();i++){
				       InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vStepInstallment.get(i);
				       prmInstallmentDataM.setApplicationRecordId(prmLoanDataM.getAppRecordID());
				       prmInstallmentDataM.setCreateBy(prmLoanDataM.getUpdateBy());
				       prmInstallmentDataM.setUpdateBy(prmLoanDataM.getUpdateBy());
				       origInstallmentDAO.saveUpdateModelOrigInstallmentM(prmInstallmentDataM);
				    }  
				  }    
				origInstallmentDAO.deleteNotInKeyTableORIG_Installment(vStepInstallment,prmLoanDataM.getAppRecordID());
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		}

	}

	/**
	 * @param prmLoanDataM
	 * @return
	 */
	private double updateTableORIG_LOANForCreateCar(LoanDataM prmLoanDataM)
			throws OrigLoanMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_LOAN ");
			sql.append(" SET  LOAN_TYPE=?  ,MARKETING_CODE=?   ,CAMPAIGN=? ");
			sql
					.append(" ,INTERNAL_CKECKER=?   ,REASON_CAMPAIGN =?  ,EXTERNAL_CKECKER=?  ,OVERRIDE_BY=?,CREDIT_APPROVAL=? ");
			sql
					.append(" ,OVERRIDE_DATE =?,APPRAISAL_PRICE=? ,COLLECTION_CODE =? ,BALLOON_FLAG =?,BALLOON_TYPE=?");
			sql
					.append(" ,BANK_CODE=?,BRANCH_CODE =?,ACCOUNT_NUMBER=? ,ACCOUNT_NAME=?,BOOKING_DATE=?");
			sql
					.append(" ,CONTRACT_DATE=? ,COLLECTOR_CODE=?,FIRST_DUE_DATE=?,END_DUE_DATE=?,DOWN_TYPE=?");
			sql
					.append(" ,DOWN_AMOUNT =? ,PAYMENT_TYPE =? ,FIRST_INSTALLMENT=?  ,NET_RATE=?  ,SPECIAL_HIRE_CHARGE=?");
			sql
					.append(" ,DOCUMENT_DATE=?  ,CHEQUE_DATE=? ,VAT=? ,COST_OF_CARPRICE=? ,VAT_OF_CARPRICE=?");
			sql
					.append(" ,TOTAL_OF_CARPRICE=? ,COST_OF_DOWNPAYMENT=?,VAT_OF_DOWNPAYMENT=?,TOTAL_OF_DOWNPAYMENT=?,COST_OF_FINANCIAL_AMT=?");
			sql
					.append(" ,VAT_OF_FINANCIAL_AMT=? ,TOTAL_OF_FINANCIAL_AMT=? ,COST_OF_BALLOON_AMT=?,VAT_OF_BALLOON_AMT=?,TOTAL_OF_BALLOON_AMT=?");
			sql
					.append(" ,RATE1 =?,COST_OF_RATE1 =? ,VAT_OF_RATE1 =? ,TOTAL_OF_RATE1=?,INSTALLMENT1=?");
			sql
					.append(" ,COST_OF_INSTALLMENT1 =?,VAT_OF_INSTALLMENT1 =? ,TOTAL_OF_INSTALLMENT1 =?, COST_OF_HAIR_PURCHASEA_MT=? ,VAT_OF_HAIR_PURCHASE_AMT=?");
			sql
					.append(" ,TOTAL_OF_HAIR_PURCHASE_AMT=?,EFFECTIVE_RATE =? ,IRR_RATE=?  ,UPDATE_BY = ?,UPDATE_DATE =SYSDATE  ");
			sql
					.append(" ,PV_BALLOON_TERM=?, COST_OF_PV_BALLOON=?, VAT_OF_PV_BALLOON=?, TOTAL_OF_PV_BALLOON=?, COST_OF_PV=?, VAT_OF_PV=?, TOTAL_OF_PV=?, PENALTY_RATE=?, DISC_RATE=? ");
			sql
					.append(" ,RV_PERCENT=?, COST_OF_RV=?, VAT_OF_RV=?, TOTAL_OF_RV=?,SUBSIDIES_AMOUNT=?, AGREEMENT_DATE=? ");
			sql
					.append(" ,EXCUTION_DATE=?,  LASTDUE_DATE=?, DELIVERY_DATE=?, EXPIRY_DATE=?, FIRST_INSTALLMENT_PAY_TYPE=?, FIRST_INSTALLMENT_DEDUCT=?, PAY_DEALER=?, APPLICATION_RECORD_ID=? ");
			sql
					.append(", ORIG_DOWN_PAYMENT=?, ORIG_INSTALLMENT_AMT=?, ORIG_INSTALLMENT_TERM=?, ORIG_FINANCE=?, ACTUAL_CAR_PRICE=?, ACTUAL_DOWN=? ,INSTALLMENT_FLAG=?");
			sql.append(" , SCHEME_CODE=?, PURPOSE_LOAN=?, LOAN_AMOUNT=?, AMOUNT_FINANCE=?, ACCEPT_FEE_PERCENT=?, ACCEPT_FEE=?, FEE_DISCOUNT_PERCENT=?, FEE_DISCOUNT=? ");
			sql.append(" , MONTHLY_INST_AMT_1=?, MONTHLY_INST_AMT_2=?, LAST_INST_AMT_1=?, LAST_INST_AMT_2=?, FIRST_TIER_RATE=?, SECOND_TIER_RATE=?, FIRST_TIER_TERM=?, SECOND_TIER_TERM=? ");
			sql
					.append(" WHERE LOAN_ID=?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmLoanDataM.getLoanType());
			ps.setString(2, prmLoanDataM.getMarketingCode());
			ps.setString(3, prmLoanDataM.getCampaign());
			ps.setString(4, prmLoanDataM.getInternalCkecker());
			ps.setString(5, prmLoanDataM.getReasonCampaign());
			ps.setString(6, prmLoanDataM.getExternalCkecker());
			ps.setString(7, prmLoanDataM.getOverrideBy());
			ps.setString(8, prmLoanDataM.getCreditApproval());
			ps.setDate(9, this.parseDate(prmLoanDataM.getOverrideDate()));
			ps.setBigDecimal(10, prmLoanDataM.getAppraisalPrice());
			ps.setString(11, prmLoanDataM.getCollectionCode());
			ps.setString(12, prmLoanDataM.getBalloonFlag());
			ps.setString(13, prmLoanDataM.getBalloonType());
			ps.setString(14, prmLoanDataM.getBankCode());
			ps.setString(15, prmLoanDataM.getBranchCode());
			ps.setString(16, prmLoanDataM.getAccountNo());
			ps.setString(17, prmLoanDataM.getAccountName());
			ps.setDate(18,this.parseDate( prmLoanDataM.getBookingDate()));
			ps.setDate(19,this.parseDate( prmLoanDataM.getContractDate()));
			ps.setString(20, prmLoanDataM.getCollectorCode());
			ps.setDate(21, this.parseDate(prmLoanDataM.getFirstDueDate()));
			ps.setDate(22, this.parseDate(prmLoanDataM.getEndDueDate()));
			ps.setString(23, prmLoanDataM.getDownType());
			ps.setBigDecimal(24, prmLoanDataM.getDownAmount());
			ps.setString(25, prmLoanDataM.getPaymentType());
			ps.setBigDecimal(26, prmLoanDataM.getFirstInstallment());
			ps.setBigDecimal(27, prmLoanDataM.getNetRate());
			ps.setString(28, prmLoanDataM.getSpecialHireCharge());
			ps.setDate(29, this.parseDate(prmLoanDataM.getDocumentDate()));
			ps.setDate(30, this.parseDate(prmLoanDataM.getChequeDate()));
			ps.setString(31, prmLoanDataM.getVAT());
			ps.setBigDecimal(32, prmLoanDataM.getCostOfCarPrice());
			ps.setBigDecimal(33, prmLoanDataM.getVATOfCarPrice());
			ps.setBigDecimal(34, prmLoanDataM.getTotalOfCarPrice());
			ps.setBigDecimal(35, prmLoanDataM.getCostOfDownPayment());
			ps.setBigDecimal(36, prmLoanDataM.getVATOfDownPayment());
			ps.setBigDecimal(37, prmLoanDataM.getTotalOfDownPayment());
			ps.setBigDecimal(38, prmLoanDataM.getCostOfFinancialAmt());
			ps.setBigDecimal(39, prmLoanDataM.getVATOfFinancialAmt());
			ps.setBigDecimal(40, prmLoanDataM.getTotalOfFinancialAmt());
			ps.setBigDecimal(41, prmLoanDataM.getCostOfBalloonAmt());
			ps.setBigDecimal(42, prmLoanDataM.getVATOfBalloonAmt());
			ps.setBigDecimal(43, prmLoanDataM.getTotalOfBalloonAmt());
			ps.setBigDecimal(44, prmLoanDataM.getRate1());
			ps.setBigDecimal(45, prmLoanDataM.getCostOfRate1());
			ps.setBigDecimal(46, prmLoanDataM.getVATOfRate1());
			ps.setBigDecimal(47, prmLoanDataM.getTotalOfRate1());
			ps.setBigDecimal(48, prmLoanDataM.getInstallment1());
			ps.setBigDecimal(49, prmLoanDataM.getCostOfInstallment1());
			ps.setBigDecimal(50, prmLoanDataM.getVATOfInstallment1());
			ps.setBigDecimal(51, prmLoanDataM.getTotalOfInstallment1());
			
			ps.setBigDecimal(52, prmLoanDataM.getCostOfHairPurchaseAmt());
			ps.setBigDecimal(53, prmLoanDataM.getVATOfHairPurchaseAmt());
			ps.setBigDecimal(54, prmLoanDataM.getTotalOfHairPurchaseAmt());
			ps.setBigDecimal(55, prmLoanDataM.getEffectiveRate());
			ps.setBigDecimal(56, prmLoanDataM.getIRRRate());
			ps.setString(57, prmLoanDataM.getUpdateBy());
			
			ps.setBigDecimal(58, prmLoanDataM.getBalloonTerm());
			ps.setBigDecimal(59, prmLoanDataM.getCostOfpvBalloonAmt());
			ps.setBigDecimal(60, prmLoanDataM.getVATOfpvBalloonAmt());
			ps.setBigDecimal(61, prmLoanDataM.getTotalOfpvBalloonAmt());
			ps.setBigDecimal(62, prmLoanDataM.getCostOfpv());
			ps.setBigDecimal(63, prmLoanDataM.getVATOfpv());
			ps.setBigDecimal(64, prmLoanDataM.getTotalOfpv());
			ps.setBigDecimal(65, prmLoanDataM.getPenaltyRate());
			ps.setBigDecimal(66, prmLoanDataM.getDiscRate());
			
			ps.setBigDecimal(67, prmLoanDataM.getRvPercent());
			ps.setBigDecimal(68, prmLoanDataM.getCostOfRV());
			ps.setBigDecimal(69, prmLoanDataM.getVATOfRV());
			ps.setBigDecimal(70, prmLoanDataM.getTotalOfRV());
			
			ps.setBigDecimal(71, prmLoanDataM.getSubsidiesAmount());
			ps.setDate(72, this.parseDate(prmLoanDataM.getAgreementDate()));
			ps.setDate(73, this.parseDate(prmLoanDataM.getExcutionDate()));
			ps.setDate(74, this.parseDate(prmLoanDataM.getLastDueDate()));
			ps.setDate(75, this.parseDate(prmLoanDataM.getDeliveryDate()));
			ps.setDate(76, this.parseDate(prmLoanDataM.getExpiryDate()));
			ps.setString(77, prmLoanDataM.getFirstInsPayType());
			ps.setString(78, prmLoanDataM.getFirstInsDeduct());
			ps.setString(79, prmLoanDataM.getPayDealer());
			ps.setString(80, prmLoanDataM.getAppRecordID());
			
			ps.setBigDecimal(81, prmLoanDataM.getOrigDownPayment());
			ps.setBigDecimal(82, prmLoanDataM.getOrigInstallmentAmt());
			ps.setBigDecimal(83, prmLoanDataM.getOrigInstallmentTerm());
			ps.setBigDecimal(84, prmLoanDataM.getOrigFinance());
			ps.setBigDecimal(85, prmLoanDataM.getActualCarPrice());
			ps.setBigDecimal(86, prmLoanDataM.getActualDown());
			ps.setString(87,prmLoanDataM.getInstallmentFlag());
			
			ps.setString(88,prmLoanDataM.getSchemeCode());
			ps.setString(89,prmLoanDataM.getPurposeLoan());
			ps.setBigDecimal(90,prmLoanDataM.getLoanAmt());
			ps.setBigDecimal(91,prmLoanDataM.getAmountFinance());
			ps.setBigDecimal(92,prmLoanDataM.getAcceptanceFeePercent());
			ps.setBigDecimal(93,prmLoanDataM.getAcceptanceFee());
			ps.setBigDecimal(94,prmLoanDataM.getFeeDiscountPercent());
			ps.setBigDecimal(95,prmLoanDataM.getFeeDiscount());
			ps.setBigDecimal(96,prmLoanDataM.getMonthlyIntalmentAmtOne());
			ps.setBigDecimal(97,prmLoanDataM.getMonthlyIntalmentAmtTwo());
			ps.setBigDecimal(98,prmLoanDataM.getLastIntalmentAmtOne());
			ps.setBigDecimal(99,prmLoanDataM.getLastIntalmentAmtTwo());
			ps.setBigDecimal(100,prmLoanDataM.getFirstTierRate());
			ps.setBigDecimal(101,prmLoanDataM.getSecondTierRate());
			ps.setInt(102,prmLoanDataM.getFirstTierTerm());
			ps.setInt(103,prmLoanDataM.getSecondTierTerm());
			
			ps.setInt(104, prmLoanDataM.getLoanID());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	/** delete not in key**/
	public void deleteNotInKeyTableORIG_LOAN(Vector loanVect, String appRecordID) throws OrigLoanMException{
		PreparedStatement ps = null;
		String cmpCode = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_LOAN");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
            
            if ((loanVect != null) && (loanVect.size() != 0)) {
                sql.append(" AND LOAN_ID NOT IN ( ");
                LoanDataM loanDataM;
                for (int i = 0; i < loanVect.size(); i++) {
                	loanDataM = (LoanDataM) loanVect.get(i);
                	log.debug("loanDataM.getLoanID() = "+loanDataM.getLoanID());
                    sql.append(" '" + loanDataM.getLoanID() + "' , ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);

            ps = conn.prepareStatement(dSql);

            ps.setString(1, appRecordID);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new OrigLoanMException(e.getMessage());
			}
		}
	}
	
	/**
	 * @param appRecordID
	 */
	public void deleteTableORIG_LOAN(String appRecordID)throws OrigLoanMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_LOAN ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecordID);
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigLoanMException(e.getMessage());
			}
		}

	}
	
	public LoanDataM loadModelOrigLoanMByVehicleId(int vehicleId)
	throws OrigLoanMException {
		try {
		    LoanDataM result = selectTableORIG_LOAN_BY_VEHICLEID(vehicleId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		}
	}
	
	
	private LoanDataM selectTableORIG_LOAN_BY_VEHICLEID(int vehicleId)
	throws OrigLoanMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		LoanDataM loanDataM = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT  LOAN_TYPE   ,MARKETING_CODE,CAMPAIGN ");
			sql
					.append(" ,INTERNAL_CKECKER ,REASON_CAMPAIGN  ,EXTERNAL_CKECKER  ,OVERRIDE_BY  ,CREDIT_APPROVAL ");
			sql
					.append(" ,OVERRIDE_DATE ,APPRAISAL_PRICE  ,COLLECTION_CODE ,BALLOON_FLAG ,BALLOON_TYPE");
			sql
					.append(" ,BANK_CODE ,BRANCH_CODE ,ACCOUNT_NUMBER ,ACCOUNT_NAME ,BOOKING_DATE");
			sql
					.append(" ,CONTRACT_DATE,COLLECTOR_CODE  ,FIRST_DUE_DATE ,END_DUE_DATE ,DOWN_TYPE");
			sql
					.append(" ,DOWN_AMOUNT ,PAYMENT_TYPE  ,FIRST_INSTALLMENT ,NET_RATE ,SPECIAL_HIRE_CHARGE");
			sql
					.append(" ,DOCUMENT_DATE,CHEQUE_DATE,VAT  ,COST_OF_CARPRICE  ,VAT_OF_CARPRICE");
			sql
					.append(" ,TOTAL_OF_CARPRICE,COST_OF_DOWNPAYMENT ,VAT_OF_DOWNPAYMENT  ,TOTAL_OF_DOWNPAYMENT ,COST_OF_FINANCIAL_AMT");
			sql
					.append(" ,VAT_OF_FINANCIAL_AMT ,TOTAL_OF_FINANCIAL_AMT,COST_OF_BALLOON_AMT  ,VAT_OF_BALLOON_AMT ,TOTAL_OF_BALLOON_AMT");
			sql
					.append(" ,RATE1 ,COST_OF_RATE1 ,VAT_OF_RATE1,TOTAL_OF_RATE1 ,INSTALLMENT1");
			sql
					.append(" ,COST_OF_INSTALLMENT1 ,VAT_OF_INSTALLMENT1,TOTAL_OF_INSTALLMENT1 ,COST_OF_HAIR_PURCHASEA_MT ,VAT_OF_HAIR_PURCHASE_AMT");
			sql
					.append(" ,TOTAL_OF_HAIR_PURCHASE_AMT,EFFECTIVE_RATE ,IRR_RATE ,CREATE_BY ,CREATE_DATE ,UPDATE_BY,UPDATE_DATE,LOAN_ID,VEHICLE_ID,IDNO  ");
			sql
					.append(" ,PV_BALLOON_TERM, COST_OF_PV_BALLOON, VAT_OF_PV_BALLOON, TOTAL_OF_PV_BALLOON, COST_OF_PV, VAT_OF_PV, TOTAL_OF_PV, PENALTY_RATE, DISC_RATE ");
			sql
					.append(" ,RV_PERCENT, COST_OF_RV, VAT_OF_RV, TOTAL_OF_RV, SUBSIDIES_AMOUNT, AGREEMENT_DATE ");
			sql
					.append(" ,EXCUTION_DATE,  LASTDUE_DATE, DELIVERY_DATE, EXPIRY_DATE, FIRST_INSTALLMENT_PAY_TYPE, FIRST_INSTALLMENT_DEDUCT, PAY_DEALER ");
			sql
					.append(", ORIG_DOWN_PAYMENT, ORIG_INSTALLMENT_AMT, ORIG_INSTALLMENT_TERM, ORIG_FINANCE, ACTUAL_CAR_PRICE, ACTUAL_DOWN, APPLICATION_RECORD_ID ");
			sql.append(" ,INSTALLMENT_FLAG ");
			sql.append(" , SCHEME_CODE, PURPOSE_LOAN, LOAN_AMOUNT, AMOUNT_FINANCE, ACCEPT_FEE_PERCENT, ACCEPT_FEE, FEE_DISCOUNT_PERCENT, FEE_DISCOUNT ");
			sql.append(" , MONTHLY_INST_AMT_1, MONTHLY_INST_AMT_2, LAST_INST_AMT_1, LAST_INST_AMT_2, FIRST_TIER_RATE, SECOND_TIER_RATE, FIRST_TIER_TERM, SECOND_TIER_TERM ");
			sql
					.append(" FROM ORIG_LOAN WHERE VEHICLE_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
		
			ps.setInt(1, vehicleId);
			//ps.setInt(2, loanId);
		
			rs = ps.executeQuery();
			Vector vLoanDataM = new Vector();
			if (rs.next()) {
				loanDataM = new LoanDataM();
		
				loanDataM.setLoanType(rs.getString(1));
				loanDataM.setMarketingCode(rs.getString(2));
				loanDataM.setCampaign(rs.getString(3));
				loanDataM.setInternalCkecker(rs.getString(4));
				loanDataM.setReasonCampaign(rs.getString(5));
				loanDataM.setExternalCkecker(rs.getString(6));
				loanDataM.setOverrideBy(rs.getString(7));
				loanDataM.setCreditApproval(rs.getString(8));
				loanDataM.setOverrideDate(rs.getDate(9));
				loanDataM.setAppraisalPrice(rs.getBigDecimal(10));
				loanDataM.setCollectionCode(rs.getString(11));
				loanDataM.setBalloonFlag(rs.getString(12));
				loanDataM.setBalloonType(rs.getString(13));
				loanDataM.setBankCode(rs.getString(14));
				loanDataM.setBranchCode(rs.getString(15));
				loanDataM.setAccountNo(rs.getString(16));
				loanDataM.setAccountName(rs.getString(17));
				loanDataM.setBookingDate(rs.getDate(18));
				loanDataM.setContractDate(rs.getDate(19));
				loanDataM.setCollectorCode(rs.getString(20));
				loanDataM.setFirstDueDate(rs.getDate(21));
				loanDataM.setEndDueDate(rs.getDate(22));
				loanDataM.setDownType(rs.getString(23));
				loanDataM.setDownAmount(rs.getBigDecimal(24));
				loanDataM.setPaymentType(rs.getString(25));
				loanDataM.setFirstInstallment(rs.getBigDecimal(26));
				loanDataM.setNetRate(rs.getBigDecimal(27));
				loanDataM.setSpecialHireCharge(rs.getString(28));
				loanDataM.setDocumentDate(rs.getDate(29));
				loanDataM.setChequeDate(rs.getDate(30));
				loanDataM.setVAT(rs.getString(31));
				loanDataM.setCostOfCarPrice(rs.getBigDecimal(32));
				loanDataM.setVATOfCarPrice(rs.getBigDecimal(33));
				loanDataM.setTotalOfCarPrice(rs.getBigDecimal(34));
				loanDataM.setCostOfDownPayment(rs.getBigDecimal(35));
				loanDataM.setVATOfDownPayment(rs.getBigDecimal(36));
				loanDataM.setTotalOfDownPayment(rs.getBigDecimal(37));
				loanDataM.setCostOfFinancialAmt(rs.getBigDecimal(38));
				loanDataM.setVATOfFinancialAmt(rs.getBigDecimal(39));
				loanDataM.setTotalOfFinancialAmt(rs.getBigDecimal(40));
				loanDataM.setCostOfBalloonAmt(rs.getBigDecimal(41));
				loanDataM.setVATOfBalloonAmt(rs.getBigDecimal(42));
				loanDataM.setTotalOfBalloonAmt(rs.getBigDecimal(43));
				loanDataM.setRate1(rs.getBigDecimal(44));
				loanDataM.setCostOfRate1(rs.getBigDecimal(45));
				loanDataM.setVATOfRate1(rs.getBigDecimal(46));
				loanDataM.setTotalOfRate1(rs.getBigDecimal(47));
				loanDataM.setInstallment1(rs.getBigDecimal(48));
				loanDataM.setCostOfInstallment1(rs.getBigDecimal(49));
				loanDataM.setVATOfInstallment1(rs.getBigDecimal(50));
				loanDataM.setTotalOfInstallment1(rs.getBigDecimal(51));
				
				loanDataM.setCostOfHairPurchaseAmt(rs.getBigDecimal(52));
				loanDataM.setVATOfHairPurchaseAmt(rs.getBigDecimal(53));
				loanDataM.setTotalOfHairPurchaseAmt(rs.getBigDecimal(54));
				loanDataM.setEffectiveRate(rs.getBigDecimal(55));
				loanDataM.setIRRRate(rs.getBigDecimal(56));
				loanDataM.setCreateBy(rs.getString(57));
				loanDataM.setCreateDate(rs.getTimestamp(58));
				loanDataM.setUpdateBy(rs.getString(59));
				loanDataM.setUpdateDate(rs.getTimestamp(60));
				loanDataM.setLoanID(rs.getInt(61));
				loanDataM.setVehicleId(rs.getInt(62));
				loanDataM.setIdNo(rs.getString(63));
				
				loanDataM.setBalloonTerm(rs.getBigDecimal(64));
				loanDataM.setCostOfpvBalloonAmt(rs.getBigDecimal(65));
				loanDataM.setVATOfpvBalloonAmt(rs.getBigDecimal(66));
				loanDataM.setTotalOfpvBalloonAmt(rs.getBigDecimal(67));
				loanDataM.setCostOfpv(rs.getBigDecimal(68));
				loanDataM.setVATOfpv(rs.getBigDecimal(69));
				loanDataM.setTotalOfpv(rs.getBigDecimal(70));
				loanDataM.setPenaltyRate(rs.getBigDecimal(71));
				loanDataM.setDiscRate(rs.getBigDecimal(72));
				
				loanDataM.setRvPercent(rs.getBigDecimal(73));
				loanDataM.setCostOfRV(rs.getBigDecimal(74));
				loanDataM.setVATOfRV(rs.getBigDecimal(75));
				loanDataM.setTotalOfRV(rs.getBigDecimal(76));
				
				loanDataM.setSubsidiesAmount(rs.getBigDecimal(77));
				loanDataM.setAgreementDate(rs.getDate(78));
				loanDataM.setExcutionDate(rs.getDate(79));
				loanDataM.setLastDueDate(rs.getDate(80));
				loanDataM.setDeliveryDate(rs.getDate(81));
				loanDataM.setExpiryDate(rs.getDate(82));
				loanDataM.setFirstInsPayType(rs.getString(83));
				loanDataM.setFirstInsDeduct(rs.getString(84));
				loanDataM.setPayDealer(rs.getString(85));
				
				loanDataM.setOrigDownPayment(rs.getBigDecimal(86));
				loanDataM.setOrigInstallmentAmt(rs.getBigDecimal(87));
				loanDataM.setOrigInstallmentTerm(rs.getBigDecimal(88));
				loanDataM.setOrigFinance(rs.getBigDecimal(89));
				loanDataM.setActualCarPrice(rs.getBigDecimal(90));
				loanDataM.setActualDown(rs.getBigDecimal(91));
				loanDataM.setActualCarPriceTmp(rs.getBigDecimal(90));
				loanDataM.setActualDownTmp(rs.getBigDecimal(91));
				loanDataM.setAppRecordID(rs.getString(92));
				loanDataM.setInstallmentFlag(rs.getString(93));

				loanDataM.setSchemeCode(rs.getString("SCHEME_CODE"));
				loanDataM.setPurposeLoan(rs.getString("PURPOSE_LOAN"));
				loanDataM.setLoanAmt(rs.getBigDecimal("LOAN_AMOUNT"));
				loanDataM.setAmountFinance(rs.getBigDecimal("AMOUNT_FINANCE"));
				loanDataM.setAcceptanceFeePercent(rs.getBigDecimal("ACCEPT_FEE_PERCENT"));
				loanDataM.setAcceptanceFee(rs.getBigDecimal("ACCEPT_FEE"));
				loanDataM.setFeeDiscountPercent(rs.getBigDecimal("FEE_DISCOUNT_PERCENT"));
				loanDataM.setFeeDiscount(rs.getBigDecimal("FEE_DISCOUNT"));
				loanDataM.setMonthlyIntalmentAmtOne(rs.getBigDecimal("MONTHLY_INST_AMT_1"));
				loanDataM.setMonthlyIntalmentAmtTwo(rs.getBigDecimal("MONTHLY_INST_AMT_2"));
				loanDataM.setLastIntalmentAmtOne(rs.getBigDecimal("LAST_INST_AMT_1"));
				loanDataM.setLastIntalmentAmtTwo(rs.getBigDecimal("LAST_INST_AMT_2"));
				loanDataM.setFirstTierRate(rs.getBigDecimal("FIRST_TIER_RATE"));
				loanDataM.setSecondTierRate(rs.getBigDecimal("SECOND_TIER_RATE"));
				loanDataM.setFirstTierTerm(rs.getInt("FIRST_TIER_TERM"));
				loanDataM.setSecondTierTerm(rs.getInt("SECOND_TIER_TERM"));
				
				if("Y".equalsIgnoreCase(loanDataM.getBalloonFlag())){
					loanDataM.setTermTemp(loanDataM.getBalloonTerm());
				}else{
					loanDataM.setTermTemp(loanDataM.getInstallment1());
				}
				loanDataM.setDownPaymentTemp(loanDataM.getCostOfDownPayment());
				loanDataM.setRateTemp(loanDataM.getRate1());
				loanDataM.setCampaignTemp(loanDataM.getCampaign());
				
			}
			return loanDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		}
	
	public void saveUpdateModelOrigLoanMByVehicleId(LoanDataM prmLoanDataM)
	throws OrigLoanMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_LOAN_BY_VEHICLE_ID(prmLoanDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_LOAN then call Insert method");
				//generate loand id 
				//int loanID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.LOAN_RECORD_ID));
				//prmLoanDataM.setLoanID(loanID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				int loanID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.LOAN_RECORD_ID));
				prmLoanDataM.setLoanID(loanID);
				createTableORIG_LOAN(prmLoanDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		}
	}
	
	private double updateTableORIG_LOAN_BY_VEHICLE_ID(LoanDataM prmLoanDataM)
	throws OrigLoanMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_LOAN ");
			sql.append(" SET  LOAN_TYPE=?  ,MARKETING_CODE=?   ,CAMPAIGN=? ");
			sql
					.append(" ,INTERNAL_CKECKER=?   ,REASON_CAMPAIGN =?  ,EXTERNAL_CKECKER=?  ,OVERRIDE_BY=?,CREDIT_APPROVAL=? ");
			sql
					.append(" ,OVERRIDE_DATE =?,APPRAISAL_PRICE=? ,COLLECTION_CODE =? ,BALLOON_FLAG =?,BALLOON_TYPE=?");
			sql
					.append(" ,BANK_CODE=?,BRANCH_CODE =?,ACCOUNT_NUMBER=? ,ACCOUNT_NAME=?,BOOKING_DATE=?");
			sql
					.append(" ,CONTRACT_DATE=? ,COLLECTOR_CODE=?,FIRST_DUE_DATE=?,END_DUE_DATE=?,DOWN_TYPE=?");
			sql
					.append(" ,DOWN_AMOUNT =? ,PAYMENT_TYPE =? ,FIRST_INSTALLMENT=?  ,NET_RATE=?  ,SPECIAL_HIRE_CHARGE=?");
			sql
					.append(" ,DOCUMENT_DATE=?  ,CHEQUE_DATE=? ,VAT=? ,COST_OF_CARPRICE=? ,VAT_OF_CARPRICE=?");
			sql
					.append(" ,TOTAL_OF_CARPRICE=? ,COST_OF_DOWNPAYMENT=?,VAT_OF_DOWNPAYMENT=?,TOTAL_OF_DOWNPAYMENT=?,COST_OF_FINANCIAL_AMT=?");
			sql
					.append(" ,VAT_OF_FINANCIAL_AMT=? ,TOTAL_OF_FINANCIAL_AMT=? ,COST_OF_BALLOON_AMT=?,VAT_OF_BALLOON_AMT=?,TOTAL_OF_BALLOON_AMT=?");
			sql
					.append(" ,RATE1 =?,COST_OF_RATE1 =? ,VAT_OF_RATE1 =? ,TOTAL_OF_RATE1=?,INSTALLMENT1=?");
			sql
					.append(" ,COST_OF_INSTALLMENT1 =?,VAT_OF_INSTALLMENT1 =? ,TOTAL_OF_INSTALLMENT1 =?, COST_OF_HAIR_PURCHASEA_MT=? ,VAT_OF_HAIR_PURCHASE_AMT=?");
			sql
					.append(" ,TOTAL_OF_HAIR_PURCHASE_AMT=?,EFFECTIVE_RATE =? ,IRR_RATE=?  ,UPDATE_BY = ?,UPDATE_DATE =SYSDATE  ");
			sql
					.append(" ,PV_BALLOON_TERM=?, COST_OF_PV_BALLOON=?, VAT_OF_PV_BALLOON=?, TOTAL_OF_PV_BALLOON=?, COST_OF_PV=?, VAT_OF_PV=?, TOTAL_OF_PV=?, PENALTY_RATE=?, DISC_RATE=? ");
			sql
					.append(" ,RV_PERCENT=?, COST_OF_RV=?, VAT_OF_RV=?, TOTAL_OF_RV=?, SUBSIDIES_AMOUNT=?, AGREEMENT_DATE=? ");
			sql
					.append(" ,EXCUTION_DATE=?,  LASTDUE_DATE=?, DELIVERY_DATE=?, EXPIRY_DATE=?, FIRST_INSTALLMENT_PAY_TYPE=?, FIRST_INSTALLMENT_DEDUCT=?, PAY_DEALER=? ");
			sql
					.append(", ORIG_DOWN_PAYMENT=?, ORIG_INSTALLMENT_AMT=?, ORIG_INSTALLMENT_TERM=?, ORIG_FINANCE=?, ACTUAL_CAR_PRICE=?, ACTUAL_DOWN=? ");
			sql.append("  ,INSTALLMENT_FLAG=? ");
			sql.append(" , SCHEME_CODE=?, PURPOSE_LOAN=?, LOAN_AMOUNT=?, AMOUNT_FINANCE=?, ACCEPT_FEE_PERCENT=?, ACCEPT_FEE=?, FEE_DISCOUNT_PERCENT=?, FEE_DISCOUNT=? ");
			sql.append(" , MONTHLY_INST_AMT_1=?, MONTHLY_INST_AMT_2=?, LAST_INST_AMT_1=?, LAST_INST_AMT_2=?, FIRST_TIER_RATE=?, SECOND_TIER_RATE=?, FIRST_TIER_TERM=?, SECOND_TIER_TERM=? ");
			sql
					.append(" WHERE VEHICLE_ID = ? AND LOAN_ID=?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmLoanDataM.getLoanType());
			ps.setString(2, prmLoanDataM.getMarketingCode());
			ps.setString(3, prmLoanDataM.getCampaign());
			ps.setString(4, prmLoanDataM.getInternalCkecker());
			ps.setString(5, prmLoanDataM.getReasonCampaign());
			ps.setString(6, prmLoanDataM.getExternalCkecker());
			ps.setString(7, prmLoanDataM.getOverrideBy());
			ps.setString(8, prmLoanDataM.getCreditApproval());
			ps.setDate(9, this.parseDate(prmLoanDataM.getOverrideDate()));
			ps.setBigDecimal(10, prmLoanDataM.getAppraisalPrice());
			ps.setString(11, prmLoanDataM.getCollectionCode());
			ps.setString(12, prmLoanDataM.getBalloonFlag());
			ps.setString(13, prmLoanDataM.getBalloonType());
			ps.setString(14, prmLoanDataM.getBankCode());
			ps.setString(15, prmLoanDataM.getBranchCode());
			ps.setString(16, prmLoanDataM.getAccountNo());
			ps.setString(17, prmLoanDataM.getAccountName());
			ps.setDate(18,this.parseDate( prmLoanDataM.getBookingDate()));
			ps.setDate(19,this.parseDate( prmLoanDataM.getContractDate()));
			ps.setString(20, prmLoanDataM.getCollectorCode());
			ps.setDate(21, this.parseDate(prmLoanDataM.getFirstDueDate()));
			ps.setDate(22, this.parseDate(prmLoanDataM.getEndDueDate()));
			ps.setString(23, prmLoanDataM.getDownType());
			ps.setBigDecimal(24, prmLoanDataM.getDownAmount());
			ps.setString(25, prmLoanDataM.getPaymentType());
			ps.setBigDecimal(26, prmLoanDataM.getFirstInstallment());
			ps.setBigDecimal(27, prmLoanDataM.getNetRate());
			ps.setString(28, prmLoanDataM.getSpecialHireCharge());
			ps.setDate(29, this.parseDate(prmLoanDataM.getDocumentDate()));
			ps.setDate(30, this.parseDate(prmLoanDataM.getChequeDate()));
			ps.setString(31, prmLoanDataM.getVAT());
			ps.setBigDecimal(32, prmLoanDataM.getCostOfCarPrice());
			ps.setBigDecimal(33, prmLoanDataM.getVATOfCarPrice());
			ps.setBigDecimal(34, prmLoanDataM.getTotalOfCarPrice());
			ps.setBigDecimal(35, prmLoanDataM.getCostOfDownPayment());
			ps.setBigDecimal(36, prmLoanDataM.getVATOfDownPayment());
			ps.setBigDecimal(37, prmLoanDataM.getTotalOfDownPayment());
			ps.setBigDecimal(38, prmLoanDataM.getCostOfFinancialAmt());
			ps.setBigDecimal(39, prmLoanDataM.getVATOfFinancialAmt());
			ps.setBigDecimal(40, prmLoanDataM.getTotalOfFinancialAmt());
			ps.setBigDecimal(41, prmLoanDataM.getCostOfBalloonAmt());
			ps.setBigDecimal(42, prmLoanDataM.getVATOfBalloonAmt());
			ps.setBigDecimal(43, prmLoanDataM.getTotalOfBalloonAmt());
			ps.setBigDecimal(44, prmLoanDataM.getRate1());
			ps.setBigDecimal(45, prmLoanDataM.getCostOfRate1());
			ps.setBigDecimal(46, prmLoanDataM.getVATOfRate1());
			ps.setBigDecimal(47, prmLoanDataM.getTotalOfRate1());
			ps.setBigDecimal(48, prmLoanDataM.getInstallment1());
			ps.setBigDecimal(49, prmLoanDataM.getCostOfInstallment1());
			ps.setBigDecimal(50, prmLoanDataM.getVATOfInstallment1());
			ps.setBigDecimal(51, prmLoanDataM.getTotalOfInstallment1());
			
			ps.setBigDecimal(52, prmLoanDataM.getCostOfHairPurchaseAmt());
			ps.setBigDecimal(53, prmLoanDataM.getVATOfHairPurchaseAmt());
			ps.setBigDecimal(54, prmLoanDataM.getTotalOfHairPurchaseAmt());
			ps.setBigDecimal(55, prmLoanDataM.getEffectiveRate());
			ps.setBigDecimal(56, prmLoanDataM.getIRRRate());
			ps.setString(57, prmLoanDataM.getUpdateBy());
			
			ps.setBigDecimal(58, prmLoanDataM.getBalloonTerm());
			ps.setBigDecimal(59, prmLoanDataM.getCostOfpvBalloonAmt());
			ps.setBigDecimal(60, prmLoanDataM.getVATOfpvBalloonAmt());
			ps.setBigDecimal(61, prmLoanDataM.getTotalOfpvBalloonAmt());
			ps.setBigDecimal(62, prmLoanDataM.getCostOfpv());
			ps.setBigDecimal(63, prmLoanDataM.getVATOfpv());
			ps.setBigDecimal(64, prmLoanDataM.getTotalOfpv());
			ps.setBigDecimal(65, prmLoanDataM.getPenaltyRate());
			ps.setBigDecimal(66, prmLoanDataM.getDiscRate());
			
			ps.setBigDecimal(67, prmLoanDataM.getRvPercent());
			ps.setBigDecimal(68, prmLoanDataM.getCostOfRV());
			ps.setBigDecimal(69, prmLoanDataM.getVATOfRV());
			ps.setBigDecimal(70, prmLoanDataM.getTotalOfRV());
			
			ps.setBigDecimal(71, prmLoanDataM.getSubsidiesAmount());
			ps.setDate(72, this.parseDate(prmLoanDataM.getAgreementDate()));
			ps.setDate(73, this.parseDate(prmLoanDataM.getExcutionDate()));
			ps.setDate(74, this.parseDate(prmLoanDataM.getLastDueDate()));
			ps.setDate(75, this.parseDate(prmLoanDataM.getDeliveryDate()));
			ps.setDate(79, this.parseDate(prmLoanDataM.getExpiryDate()));
			ps.setString(77, prmLoanDataM.getFirstInsPayType());
			ps.setString(78, prmLoanDataM.getFirstInsDeduct());
			ps.setString(79, prmLoanDataM.getPayDealer());
			
			ps.setBigDecimal(80, prmLoanDataM.getOrigDownPayment());
			ps.setBigDecimal(81, prmLoanDataM.getOrigInstallmentAmt());
			ps.setBigDecimal(82, prmLoanDataM.getOrigInstallmentTerm());
			ps.setBigDecimal(83, prmLoanDataM.getOrigFinance());
			ps.setBigDecimal(84, prmLoanDataM.getActualCarPrice());
			ps.setBigDecimal(85, prmLoanDataM.getActualDown());
			ps.setString(86, prmLoanDataM.getInstallmentFlag());
			
			ps.setString(87,prmLoanDataM.getSchemeCode());
			ps.setString(88,prmLoanDataM.getPurposeLoan());
			ps.setBigDecimal(89,prmLoanDataM.getLoanAmt());
			ps.setBigDecimal(90,prmLoanDataM.getAmountFinance());
			ps.setBigDecimal(91,prmLoanDataM.getAcceptanceFeePercent());
			ps.setBigDecimal(92,prmLoanDataM.getAcceptanceFee());
			ps.setBigDecimal(93,prmLoanDataM.getFeeDiscountPercent());
			ps.setBigDecimal(94,prmLoanDataM.getFeeDiscount());
			ps.setBigDecimal(95,prmLoanDataM.getMonthlyIntalmentAmtOne());
			ps.setBigDecimal(96,prmLoanDataM.getMonthlyIntalmentAmtTwo());
			ps.setBigDecimal(97,prmLoanDataM.getLastIntalmentAmtOne());
			ps.setBigDecimal(98,prmLoanDataM.getLastIntalmentAmtTwo());
			ps.setBigDecimal(99,prmLoanDataM.getFirstTierRate());
			ps.setBigDecimal(100,prmLoanDataM.getSecondTierRate());
			ps.setInt(101,prmLoanDataM.getFirstTierTerm());
			ps.setInt(102,prmLoanDataM.getSecondTierTerm());
			
			ps.setInt(103, prmLoanDataM.getVehicleId());
			ps.setInt(104, prmLoanDataM.getLoanID());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
		}
	
	public void deleteModelOrigLoanMByIdNo(String IdNo,String vehicleId)
	throws OrigLoanMException {
		try {
		    deleteTableORIG_LOAN_BY_IDNO(IdNo,vehicleId);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		}
	}
	
	public void deleteTableORIG_LOAN_BY_IDNO(String IdNo,String vehicleId)throws OrigLoanMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_LOAN ");
			sql.append(" WHERE IDNO = ? ");
			if(vehicleId!=null&&!vehicleId.equals("")){
				sql.append(" AND VEHICLE_ID NOT IN ("+vehicleId+")");
			    }
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, IdNo);
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigLoanMException(e.getMessage());
			}
		}
	}
	
	public int updateLoanForChangeCar(int loanId, String apprecordID)throws OrigLoanMException{
		int returnRow = 0;
		try {
			
			returnRow = updateLoanForOldCar(loanId, apprecordID);
			returnRow = updateLoanForNewCar(loanId, apprecordID);
			
			return returnRow;
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigLoanMException(e.getMessage());
		}
	}
	
	private int updateLoanForOldCar(int loanId, String apprecordID)throws OrigLoanMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_LOAN ");
			sql.append(" SET APPLICATION_RECORD_ID=null");
			sql.append(" WHERE LOAN_ID <> ? AND APPLICATION_RECORD_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("loanId=" + loanId);
			
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, loanId);
			ps.setString(2, apprecordID);
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error >> ", e);
			}
		}
		return returnRows;
	}
	
	private int updateLoanForNewCar(int loanId, String apprecordID)throws OrigLoanMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_LOAN ");
			sql.append(" SET APPLICATION_RECORD_ID=? ");
			sql.append(" WHERE LOAN_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("loanId=" + loanId);
			
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, apprecordID);
			ps.setInt(2, loanId);
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error >> ", e);
			}
		}
		return returnRows;
	}
	
	
    public ArrayList getCostFee(String origType) throws OrigLoanMException {
    	PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("select ORIGINATION_ID, TYPE, ORIGINATION_TYPE, DESCRIPTION, FIX_VALUE, PERCENTAGE_VALUE, AMORTIZATION_FLAG, PRINCIPAL_FLAG, UPDATE_BY, UPDATE_DATE, MAX_LOAN_AMT, MIN_LOAN_AMT, VALIDITY_FROM, VALIDITY_TO ");
			sql
					.append(" FROM ORIG_COST_FEE WHERE ORIGINATION_TYPE = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			ps.setString(1, origType);

			rs = ps.executeQuery();
			ArrayList costFeeList = new ArrayList();
			while (rs.next()) {
			    CostFee costFee = new CostFee();
			    costFee.setOriginationID(rs.getString("ORIGINATION_ID"));
			    costFee.setType(rs.getString("TYPE"));
			    costFee.setOriginationType(rs.getString("ORIGINATION_TYPE"));
			    costFee.setFixValue(rs.getDouble("FIX_VALUE"));
			    costFee.setPercentageValue(rs.getDouble("PERCENTAGE_VALUE"));
			    costFee.setAmortizationFlag(rs.getString("AMORTIZATION_FLAG"));
			    costFee.setPrincipalFlag(rs.getString("PRINCIPAL_FLAG"));
			    costFee.setUpdateBy(rs.getString("UPDATE_BY"));
			    costFee.setUpdateDate(rs.getDate("UPDATE_DATE"));
			    costFee.setMaxLoanAmt(rs.getDouble("MAX_LOAN_AMT"));
			    costFee.setMinLoanAmt(rs.getDouble("MIN_LOAN_AMT"));
			    costFee.setValidityFrom(rs.getDate("VALIDITY_FROM"));
			    costFee.setValidityTo(rs.getDate("VALIDITY_TO"));
			    costFeeList.add(costFee);
			}
			return costFeeList;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigLoanMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
    }
}
