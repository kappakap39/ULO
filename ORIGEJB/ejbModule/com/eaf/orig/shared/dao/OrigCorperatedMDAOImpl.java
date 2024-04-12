/*
 * Created on Nov 7, 2007
 * Created by Prawit Limwattanachai
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
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigCustomerAddressMException;
import com.eaf.orig.shared.model.CorperatedDataM;

/**
 * @author Administrator
 *
 * Type: OrigCorperatedMDAOImpl
 */
public class OrigCorperatedMDAOImpl extends OrigObjectDAO implements
		OrigCorperatedMDAO {
	
	private static Logger log = Logger.getLogger(OrigCorperatedMDAOImpl.class);
	/**
	 * 
	 */
	public OrigCorperatedMDAOImpl() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigCustomerAddressMDAO#createModelOrigCustomerAddressM(com.eaf.orig.shared.model.AddressDataM)
	 */
	public void createModelOrigCorperatedM(CorperatedDataM prmCorperatedDataM)
			throws OrigCustomerAddressMException {
		try {
			createTableORIG_CORPERATED(prmCorperatedDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		}

	}

	/**
	 * @param prmAddressDataM
	 */
	private void createTableORIG_CORPERATED(CorperatedDataM prmCorperatedDataM) throws OrigCustomerAddressMException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_BALANCE_SHEET ");
			
			sql.append("( IDNO, CMPCDE ");
			
			//Assets
			sql.append(" , AS_CURRENT_ASSET , AS_CASH_IN_HAND , AS_ACCOUNT_RECIEVAVLE, AS_NOTES_RECEIVABLE");
			sql.append(" ,AS_ACCRUED_INCOME, AS_INVENTORIES, AS_OTHER_CURRENT_ASSETS, AS_TOTAL_CURRENT_ASSETS, AS_DEPOSIT  ");
			sql.append(" ,AS_PROP_PLANT_EQUIP, AS_OTHER_ASSETS, AS_TOTAL_ASSETS  ");
			
			//Liabilities
			sql.append(" , LI_CURRENT_LIABILITIES, LI_BANK_OVERDRAFT_LOAN, LI_ACCOUNT_PAYABLE, LI_CURRENT_PROTION  ");
			sql.append(" ,LI_NOTES_PAYABLE, LI_LOANS_FORM_FINANCIAL, LI_ACCRUED_EXPENSES, LI_OTHER_CURRENT_LIABILITIES  ");
			sql.append(" ,LI_TOTAL_CURRENT_LIABLITIES, LI_LONG_TERM_DEBT, LI_PAYABLE_HIRE_PURCHASE, LI_TOTAL_LIABILITIES  ");
			
			//Shareholders Equity
			sql.append(" ,SH_SHARE_CAPITAL, SH_ISSUED_AND_PAID, SH_PREMIUM, SH_RETAIN_EARNING,SH_LEGAL_RESERVE  ");
			sql.append(" ,SH_UNAPPROPRIATED, SH_TOTAL_SHAREHOLDERS  ");
			
			//Income Statement
			sql.append(" ,IN_REVENUE,IN_SALES, IN_OTHER_INCOME, IN_TOTAL_REVENUE, IN_EXPENSES  ");
			sql.append(" ,IN_COST_OF_SALES, IN_SA_EXPENSES, IN_SHARE_OF_NET_LOSS, IN_LOSS_FROM_GOODS, IN_TOTAL_EXPENSES  ");
			sql.append(" ,IN_EARNING_BEFORE_INTEREST, IN_INTEREST_EXPENSES, IN_EARNING_BEFORE_TAX, IN_INCOME_TAX, IN_NET_INCOME  ");
			
			//Ratios
			sql.append(" ,RA_CURRENT_RATIO, RA_QUICK_RATIO, RA_DEBT_TO_EQUITY, RA_GROSS_PROFIT_MARGIN, RA_NET_PROFIT_SALES  ");
			
			sql.append(" ,CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE, YEAR   )");
			
			sql.append(" VALUES(?,?,?,?,?,?,?,?,?,? ");
			sql.append(" ,?,?,?,?,?,?,?,?,?,?  ");
			sql.append(" ,?,?,?,?,?,?,?,?,?,?  ");
			sql.append(" ,?,?,?,?,?,?,?,?,?,?  ");
			sql.append(" ,?,?,?,?,?,?,?,?,?,?  ");
			sql.append(" ,?,?,?,?,?,SYSDATE,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmCorperatedDataM.getIdNo());
			ps.setString(2, prmCorperatedDataM.getCmpCode());	
			
			//Assets
			ps.setBigDecimal(3, prmCorperatedDataM.getAsstCurrentAssets());
			ps.setBigDecimal(4, prmCorperatedDataM.getAsstCashInHandAtBank());
			ps.setBigDecimal(5, prmCorperatedDataM.getAsstAccountReceivableNet());
			ps.setBigDecimal(6, prmCorperatedDataM.getAsstNoteReceivable());
			ps.setBigDecimal(7, prmCorperatedDataM.getAsstAccruedIncome());
			ps.setBigDecimal(8, prmCorperatedDataM.getAsstInventories());
			ps.setBigDecimal(9, prmCorperatedDataM.getAsstOtherCurAssets());
			ps.setBigDecimal(10, prmCorperatedDataM.getAsstTotalCurAssets());
			ps.setBigDecimal(11, prmCorperatedDataM.getAsstDeposit());
			ps.setBigDecimal(12, prmCorperatedDataM.getAsstPropertyPlantEquipment());
			ps.setBigDecimal(13, prmCorperatedDataM.getAsstOtherAssets());
			ps.setBigDecimal(14, prmCorperatedDataM.getAsstTotalAssets());
			
			//Liabilities
			ps.setBigDecimal(15, prmCorperatedDataM.getLbCurrentLb());
			ps.setBigDecimal(16, prmCorperatedDataM.getLbBankOverdraftAndLoan());
			ps.setBigDecimal(17, prmCorperatedDataM.getLbAccountPayable());
			ps.setBigDecimal(18, prmCorperatedDataM.getLbCurrentPortionOfLongTermDebt());
			ps.setBigDecimal(19, prmCorperatedDataM.getLbNotesPayable());
			ps.setBigDecimal(20, prmCorperatedDataM.getLbLoanFromFinanInstitution());
			ps.setBigDecimal(21, prmCorperatedDataM.getLbAccruedExpense());
			ps.setBigDecimal(22, prmCorperatedDataM.getLbOtherCurLb());
			ps.setBigDecimal(23, prmCorperatedDataM.getLbTotalCurLb());
			ps.setBigDecimal(24, prmCorperatedDataM.getLbLongTermDebt());
			ps.setBigDecimal(25, prmCorperatedDataM.getLbPayHirePurchase());
			ps.setBigDecimal(26, prmCorperatedDataM.getLbTotalLb());
    		
			//Shareholder Equity
			ps.setBigDecimal(27, prmCorperatedDataM.getShdEqShareCapital());
			ps.setBigDecimal(28, prmCorperatedDataM.getShdEqIssuePaidUpCapital());
			ps.setBigDecimal(29, prmCorperatedDataM.getShdEqPremium());
			ps.setBigDecimal(30, prmCorperatedDataM.getShdEqRetainEarning());
			ps.setBigDecimal(31, prmCorperatedDataM.getShdEqLegalReserve());
			ps.setBigDecimal(32, prmCorperatedDataM.getShdEqUnappropriated());
			ps.setBigDecimal(33, prmCorperatedDataM.getShdEqTotalShareHdEqity());
			
			//Income Statement
			ps.setBigDecimal(34, prmCorperatedDataM.getIncStmtRevenue());
			ps.setBigDecimal(35, prmCorperatedDataM.getIncStmtSales());
			ps.setBigDecimal(36, prmCorperatedDataM.getIncStmtOtherIncome());
			ps.setBigDecimal(37, prmCorperatedDataM.getIncStmtTotalRevenue());
			ps.setBigDecimal(38, prmCorperatedDataM.getIncStmtExpense());
			ps.setBigDecimal(39, prmCorperatedDataM.getIncStmtCostofSale());
			ps.setBigDecimal(40, prmCorperatedDataM.getIncStmtSAExpense());
			ps.setBigDecimal(41, prmCorperatedDataM.getIncStmtShareNetLossSubsidiaries());
			ps.setBigDecimal(42, prmCorperatedDataM.getIncStmtLossGoodsDeterioration());
			ps.setBigDecimal(43, prmCorperatedDataM.getIncStmtTotalExpense());
			ps.setBigDecimal(44, prmCorperatedDataM.getIncStmtEarnBfInterestAndTax());
			ps.setBigDecimal(45, prmCorperatedDataM.getIncStmtInterestExpense());
			ps.setBigDecimal(46, prmCorperatedDataM.getIncStmtEarnBfTax());
			ps.setBigDecimal(47, prmCorperatedDataM.getIncStmtIncTax());
			ps.setBigDecimal(48, prmCorperatedDataM.getIncStmtNetIncome());
			
			//Ratios
			ps.setBigDecimal(49, prmCorperatedDataM.getRatCurrentRatio());
			ps.setBigDecimal(50, prmCorperatedDataM.getRatQuickRatio());
			ps.setBigDecimal(51, prmCorperatedDataM.getRatDebtToEquity());
			ps.setBigDecimal(52, prmCorperatedDataM.getRatGrossProfitMargin());
			ps.setBigDecimal(53, prmCorperatedDataM.getRatNetProfitSale());
			ps.setString(54, prmCorperatedDataM.getCreateBy());
			ps.setString(55, prmCorperatedDataM.getUpdateBy());
			ps.setString(56, prmCorperatedDataM.getYear());
			
						
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigCustomerAddressMDAO#deleteModelOrigCustomerAddressM(com.eaf.orig.shared.model.AddressDataM)
	 */
	/*public void deleteModelOrigCorperatedM(CorperatedDataM prmCorperatedDataM)
			throws OrigCustomerAddressMException {
		try {
			deleteModelOrigCorperatedM(prmCorperatedDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		}

	}	*/	

 

	/**
	 * @param prmAddressDataM
	 */
/*	private void deleteTableORIG_Corperated(CorperatedDataM prmCorperatedDataM)throws OrigCustomerAddressMException {
		PreparedStatement ps = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_BALANCE_SHEET ");
			sql.append(" WHERE YEAR = ? and IDNO=? and CMPCDE=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmCorperatedDataM.getYear());
			ps.setString(2, prmCorperatedDataM.getIdNo());
			ps.setString(3, prmCorperatedDataM.getCmpCode());
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigCustomerAddressMException(e.getMessage());
			}
		}
		
	}	*/

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigCustomerAddressMDAO#loadModelOrigCustomerAddressM(java.lang.String)
	 */
	public Vector loadModelOrigCorperatedM(String cmpcde, String personalID)
			throws OrigCustomerAddressMException {
		try {
			Vector result = selectTableORIG_Corperated(cmpcde,personalID);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		}
	}	

 

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_Corperated(String cmpcde,String personalID)throws OrigCustomerAddressMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT IDNO, CMPCDE   ");			
			
			//Assets
			sql.append(" , AS_CURRENT_ASSET , AS_CASH_IN_HAND , AS_ACCOUNT_RECIEVAVLE, AS_NOTES_RECEIVABLE");
			sql.append(" ,AS_ACCRUED_INCOME, AS_INVENTORIES, AS_OTHER_CURRENT_ASSETS, AS_TOTAL_CURRENT_ASSETS, AS_DEPOSIT  ");
			sql.append(" ,AS_PROP_PLANT_EQUIP, AS_OTHER_ASSETS, AS_TOTAL_ASSETS  ");
			
			//Liabilities
			sql.append(" , LI_CURRENT_LIABILITIES, LI_BANK_OVERDRAFT_LOAN, LI_ACCOUNT_PAYABLE, LI_CURRENT_PROTION  ");
			sql.append(" ,LI_NOTES_PAYABLE, LI_LOANS_FORM_FINANCIAL, LI_ACCRUED_EXPENSES, LI_OTHER_CURRENT_LIABILITIES  ");
			sql.append(" ,LI_TOTAL_CURRENT_LIABLITIES, LI_LONG_TERM_DEBT, LI_PAYABLE_HIRE_PURCHASE, LI_TOTAL_LIABILITIES  ");
			
			//Shareholders Equity
			sql.append(" ,SH_SHARE_CAPITAL, SH_ISSUED_AND_PAID, SH_PREMIUM, SH_RETAIN_EARNING,SH_LEGAL_RESERVE  ");
			sql.append(" ,SH_UNAPPROPRIATED, SH_TOTAL_SHAREHOLDERS  ");
			
			//Income Statement
			sql.append(" ,IN_REVENUE,IN_SALES, IN_OTHER_INCOME, IN_TOTAL_REVENUE, IN_EXPENSES  ");
			sql.append(" ,IN_COST_OF_SALES, IN_SA_EXPENSES, IN_SHARE_OF_NET_LOSS, IN_LOSS_FROM_GOODS, IN_TOTAL_EXPENSES  ");
			sql.append(" ,IN_EARNING_BEFORE_INTEREST, IN_INTEREST_EXPENSES, IN_EARNING_BEFORE_TAX, IN_INCOME_TAX, IN_NET_INCOME  ");
			
			//Ratios
			sql.append(" ,RA_CURRENT_RATIO, RA_QUICK_RATIO, RA_DEBT_TO_EQUITY, RA_GROSS_PROFIT_MARGIN, RA_NET_PROFIT_SALES  ");
			
			sql.append(" ,CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE, YEAR  ");
			
			sql.append("FROM ORIG_BALANCE_SHEET WHERE IDNO=? and CMPCDE=? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;			
			ps.setString(1, personalID);
			ps.setString(2, cmpcde);
			rs = ps.executeQuery();
			Vector vt = new Vector();
			int seq = 1;
			while (rs.next()) {
				CorperatedDataM prmCorperatedDataM = new CorperatedDataM();
				
		//		prmAddressDataM.setApplicationRecordId(applicationRecordId);				
				prmCorperatedDataM.setIdNo(rs.getString(1));
				prmCorperatedDataM.setCmpCode(rs.getString(2));
				
				//Assets
				prmCorperatedDataM.setAsstCurrentAssets(rs.getBigDecimal(3));
				prmCorperatedDataM.setAsstCashInHandAtBank(rs.getBigDecimal(4));
				prmCorperatedDataM.setAsstAccountReceivableNet(rs.getBigDecimal(5));
				prmCorperatedDataM.setAsstNoteReceivable(rs.getBigDecimal(6));
				prmCorperatedDataM.setAsstAccruedIncome(rs.getBigDecimal(7));
				prmCorperatedDataM.setAsstInventories(rs.getBigDecimal(8));
				prmCorperatedDataM.setAsstOtherCurAssets(rs.getBigDecimal(9));
				prmCorperatedDataM.setAsstTotalCurAssets(rs.getBigDecimal(10));
				prmCorperatedDataM.setAsstDeposit(rs.getBigDecimal(11));
				prmCorperatedDataM.setAsstPropertyPlantEquipment(rs.getBigDecimal(12));
				prmCorperatedDataM.setAsstOtherAssets(rs.getBigDecimal(13));
				prmCorperatedDataM.setAsstTotalAssets(rs.getBigDecimal(14));				
				
				//Liabilities
				prmCorperatedDataM.setLbCurrentLb(rs.getBigDecimal(15));
				prmCorperatedDataM.setLbBankOverdraftAndLoan(rs.getBigDecimal(16));
				prmCorperatedDataM.setLbAccountPayable(rs.getBigDecimal(17));
				prmCorperatedDataM.setLbCurrentPortionOfLongTermDebt(rs.getBigDecimal(18));
				prmCorperatedDataM.setLbNotesPayable(rs.getBigDecimal(19));
				prmCorperatedDataM.setLbLoanFromFinanInstitution(rs.getBigDecimal(20));
				prmCorperatedDataM.setLbAccruedExpense(rs.getBigDecimal(21));
				prmCorperatedDataM.setLbOtherCurLb(rs.getBigDecimal(22));
				prmCorperatedDataM.setLbTotalCurLb(rs.getBigDecimal(23));
				prmCorperatedDataM.setLbLongTermDebt(rs.getBigDecimal(24));
				prmCorperatedDataM.setLbPayHirePurchase(rs.getBigDecimal(25));
				prmCorperatedDataM.setLbTotalLb(rs.getBigDecimal(26));					
	    		
				//Shareholder Equity
				prmCorperatedDataM.setShdEqShareCapital(rs.getBigDecimal(27));
				prmCorperatedDataM.setShdEqIssuePaidUpCapital(rs.getBigDecimal(28));
				prmCorperatedDataM.setShdEqPremium(rs.getBigDecimal(29));
				prmCorperatedDataM.setShdEqRetainEarning(rs.getBigDecimal(30));
				prmCorperatedDataM.setShdEqLegalReserve(rs.getBigDecimal(31));
				prmCorperatedDataM.setShdEqUnappropriated(rs.getBigDecimal(32));
				prmCorperatedDataM.setShdEqTotalShareHdEqity(rs.getBigDecimal(33));					
				
				//Income Statement
				prmCorperatedDataM.setIncStmtRevenue(rs.getBigDecimal(34));
				prmCorperatedDataM.setIncStmtSales(rs.getBigDecimal(35));
				prmCorperatedDataM.setIncStmtOtherIncome(rs.getBigDecimal(36));
				prmCorperatedDataM.setIncStmtTotalRevenue(rs.getBigDecimal(37));
				prmCorperatedDataM.setIncStmtExpense(rs.getBigDecimal(38));
				prmCorperatedDataM.setIncStmtCostofSale(rs.getBigDecimal(39));
				prmCorperatedDataM.setIncStmtSAExpense(rs.getBigDecimal(40));
				prmCorperatedDataM.setIncStmtShareNetLossSubsidiaries(rs.getBigDecimal(41));
				prmCorperatedDataM.setIncStmtLossGoodsDeterioration(rs.getBigDecimal(42));
				prmCorperatedDataM.setIncStmtTotalExpense(rs.getBigDecimal(43));
				prmCorperatedDataM.setIncStmtEarnBfInterestAndTax(rs.getBigDecimal(44));
				prmCorperatedDataM.setIncStmtInterestExpense(rs.getBigDecimal(45));
				prmCorperatedDataM.setIncStmtEarnBfTax(rs.getBigDecimal(46));
				prmCorperatedDataM.setIncStmtIncTax(rs.getBigDecimal(47));
				prmCorperatedDataM.setIncStmtNetIncome(rs.getBigDecimal(48));				
				
				//Ratios
				prmCorperatedDataM.setRatCurrentRatio(rs.getBigDecimal(49));
				prmCorperatedDataM.setRatQuickRatio(rs.getBigDecimal(50));
				prmCorperatedDataM.setRatDebtToEquity(rs.getBigDecimal(51));
				prmCorperatedDataM.setRatGrossProfitMargin(rs.getBigDecimal(52));
				prmCorperatedDataM.setRatNetProfitSale(rs.getBigDecimal(53));
				prmCorperatedDataM.setCreateBy(rs.getString(54));
				prmCorperatedDataM.setUpdateBy(rs.getString(55));
				prmCorperatedDataM.setCreateDate(rs.getTimestamp(56));
				prmCorperatedDataM.setUpdateDate(rs.getTimestamp(57));
				prmCorperatedDataM.setYear(rs.getString(58));				
				
				prmCorperatedDataM.setSeq(seq++);
				
				vt.add(prmCorperatedDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}	

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigCustomerAddressMDAO#saveUpdateModelOrigCustomerAddressM(com.eaf.orig.shared.model.AddressDataM)
	 */
	public void saveUpdateModelOrigCorperatedM(CorperatedDataM prmCorperatedDataM)
			throws OrigCustomerAddressMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_Corperated(prmCorperatedDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_CUSTOMER_ADDRESS then call Insert method");
				createTableORIG_CORPERATED(prmCorperatedDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		}


	}	

	/**
	 * @param prmAddressDataM
	 * @return
	 */
	private double updateTableORIG_Corperated(CorperatedDataM prmCorperatedDataM) throws OrigCustomerAddressMException{
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_BALANCE_SHEET ");
			sql.append(" SET   UPDATE_By=?,UPDATE_DATE=SYSDATE ");
			
			//Assets
			sql.append(" , AS_CURRENT_ASSET=? , AS_CASH_IN_HAND=? , AS_ACCOUNT_RECIEVAVLE=?, AS_NOTES_RECEIVABLE=? ");
			sql.append(" ,AS_ACCRUED_INCOME=?, AS_INVENTORIES=?, AS_OTHER_CURRENT_ASSETS=?, AS_TOTAL_CURRENT_ASSETS=?, AS_DEPOSIT=?  ");
			sql.append(" ,AS_PROP_PLANT_EQUIP=?, AS_OTHER_ASSETS=?, AS_TOTAL_ASSETS=?  ");
			
			//Liabilities
			sql.append(" , LI_CURRENT_LIABILITIES=?, LI_BANK_OVERDRAFT_LOAN=?, LI_ACCOUNT_PAYABLE=?, LI_CURRENT_PROTION=?  ");
			sql.append(" ,LI_NOTES_PAYABLE=?, LI_LOANS_FORM_FINANCIAL=?, LI_ACCRUED_EXPENSES=?, LI_OTHER_CURRENT_LIABILITIES=?  ");
			sql.append(" ,LI_TOTAL_CURRENT_LIABLITIES=?, LI_LONG_TERM_DEBT=?, LI_PAYABLE_HIRE_PURCHASE=?, LI_TOTAL_LIABILITIES=?  ");
			
			//Shareholders Equity
			sql.append(" ,SH_SHARE_CAPITAL=?, SH_ISSUED_AND_PAID=?, SH_PREMIUM=?, SH_RETAIN_EARNING=?,SH_LEGAL_RESERVE=?  ");
			sql.append(" ,SH_UNAPPROPRIATED=?, SH_TOTAL_SHAREHOLDERS=?  ");
			
			//Income Statement
			sql.append(" ,IN_REVENUE=? ,IN_SALES=?, IN_OTHER_INCOME=?, IN_TOTAL_REVENUE=?, IN_EXPENSES=?  ");
			sql.append(" ,IN_COST_OF_SALES=?, IN_SA_EXPENSES=?, IN_SHARE_OF_NET_LOSS=?, IN_LOSS_FROM_GOODS=?, IN_TOTAL_EXPENSES=?  ");
			sql.append(" ,IN_EARNING_BEFORE_INTEREST=?, IN_INTEREST_EXPENSES=?, IN_EARNING_BEFORE_TAX=?, IN_INCOME_TAX=?, IN_NET_INCOME=?  ");
			
			//Ratios
			sql.append(" ,RA_CURRENT_RATIO=?, RA_QUICK_RATIO=?, RA_DEBT_TO_EQUITY=?, RA_GROSS_PROFIT_MARGIN=?, RA_NET_PROFIT_SALES=?  ");
			
			sql.append(" WHERE YEAR=? AND IDNO=? AND CMPCDE=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
	    	
	    	ps.setString(1, prmCorperatedDataM.getUpdateBy());			
			
			//Assets
			ps.setBigDecimal(2, prmCorperatedDataM.getAsstCurrentAssets());
			ps.setBigDecimal(3, prmCorperatedDataM.getAsstCashInHandAtBank());
			ps.setBigDecimal(4, prmCorperatedDataM.getAsstAccountReceivableNet());
			ps.setBigDecimal(5, prmCorperatedDataM.getAsstNoteReceivable());
			ps.setBigDecimal(6, prmCorperatedDataM.getAsstAccruedIncome());
			ps.setBigDecimal(7, prmCorperatedDataM.getAsstInventories());
			ps.setBigDecimal(8, prmCorperatedDataM.getAsstOtherCurAssets());
			ps.setBigDecimal(9, prmCorperatedDataM.getAsstTotalCurAssets());
			ps.setBigDecimal(10, prmCorperatedDataM.getAsstDeposit());
			ps.setBigDecimal(11, prmCorperatedDataM.getAsstPropertyPlantEquipment());
			ps.setBigDecimal(12, prmCorperatedDataM.getAsstOtherAssets());
			ps.setBigDecimal(13, prmCorperatedDataM.getAsstTotalAssets());
			
			//Liabilities
			ps.setBigDecimal(14, prmCorperatedDataM.getLbCurrentLb());
			ps.setBigDecimal(15, prmCorperatedDataM.getLbBankOverdraftAndLoan());
			ps.setBigDecimal(16, prmCorperatedDataM.getLbAccountPayable());
			ps.setBigDecimal(17, prmCorperatedDataM.getLbCurrentPortionOfLongTermDebt());
			ps.setBigDecimal(18, prmCorperatedDataM.getLbNotesPayable());
			ps.setBigDecimal(19, prmCorperatedDataM.getLbLoanFromFinanInstitution());
			ps.setBigDecimal(20, prmCorperatedDataM.getLbAccruedExpense());
			ps.setBigDecimal(21, prmCorperatedDataM.getLbOtherCurLb());
			ps.setBigDecimal(22, prmCorperatedDataM.getLbTotalCurLb());
			ps.setBigDecimal(23, prmCorperatedDataM.getLbLongTermDebt());
			ps.setBigDecimal(24, prmCorperatedDataM.getLbPayHirePurchase());
			ps.setBigDecimal(25, prmCorperatedDataM.getLbTotalLb());
    	
			//Shareholder Equity
			ps.setBigDecimal(26, prmCorperatedDataM.getShdEqShareCapital());
			ps.setBigDecimal(27, prmCorperatedDataM.getShdEqIssuePaidUpCapital());
			ps.setBigDecimal(28, prmCorperatedDataM.getShdEqPremium());
			ps.setBigDecimal(29, prmCorperatedDataM.getShdEqRetainEarning());
			ps.setBigDecimal(30, prmCorperatedDataM.getShdEqLegalReserve());
			ps.setBigDecimal(31, prmCorperatedDataM.getShdEqUnappropriated());
			ps.setBigDecimal(32, prmCorperatedDataM.getShdEqTotalShareHdEqity());
			
			//Income Statement
			ps.setBigDecimal(33, prmCorperatedDataM.getIncStmtRevenue());
			ps.setBigDecimal(34, prmCorperatedDataM.getIncStmtSales());
			ps.setBigDecimal(35, prmCorperatedDataM.getIncStmtOtherIncome());
			ps.setBigDecimal(36, prmCorperatedDataM.getIncStmtTotalRevenue());
			ps.setBigDecimal(37, prmCorperatedDataM.getIncStmtExpense());
			ps.setBigDecimal(38, prmCorperatedDataM.getIncStmtCostofSale());
			ps.setBigDecimal(39, prmCorperatedDataM.getIncStmtSAExpense());
			ps.setBigDecimal(40, prmCorperatedDataM.getIncStmtShareNetLossSubsidiaries());
			ps.setBigDecimal(41, prmCorperatedDataM.getIncStmtLossGoodsDeterioration());
			ps.setBigDecimal(42, prmCorperatedDataM.getIncStmtTotalExpense());
			ps.setBigDecimal(43, prmCorperatedDataM.getIncStmtEarnBfInterestAndTax());
			ps.setBigDecimal(44, prmCorperatedDataM.getIncStmtInterestExpense());
			ps.setBigDecimal(45, prmCorperatedDataM.getIncStmtEarnBfTax());
			ps.setBigDecimal(46, prmCorperatedDataM.getIncStmtIncTax());
			ps.setBigDecimal(47, prmCorperatedDataM.getIncStmtNetIncome());
			
			//Ratios
			ps.setBigDecimal(48, prmCorperatedDataM.getRatCurrentRatio());
			ps.setBigDecimal(49, prmCorperatedDataM.getRatQuickRatio());
			ps.setBigDecimal(50, prmCorperatedDataM.getRatDebtToEquity());
			ps.setBigDecimal(51, prmCorperatedDataM.getRatGrossProfitMargin());
			ps.setBigDecimal(52, prmCorperatedDataM.getRatNetProfitSale());
			
			ps.setString(53, prmCorperatedDataM.getYear());
			ps.setString(54, prmCorperatedDataM.getIdNo());
			ps.setString(55, prmCorperatedDataM.getCmpCode());
			
	    	
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
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
	public void deleteNotInKeyTableORIG_Corperated(Vector corperatedVect, String cmpCode, String idNo) throws OrigCustomerAddressMException{
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_BALANCE_SHEET");
            sql.append(" WHERE CMPCDE = ? AND IDNO = ? ");
            
            if ((corperatedVect != null) && (corperatedVect.size() != 0)) {
                sql.append(" AND YEAR NOT IN ( ");

                for (int i = 0; i < corperatedVect.size(); i++) {
                	CorperatedDataM corperatedDataM = (CorperatedDataM) corperatedVect.get(i);
                    sql.append(" '" + corperatedDataM.getYear() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);

            ps = conn.prepareStatement(dSql);

            ps.setString(1, cmpCode);
            ps.setString(2, idNo);            
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new OrigCustomerAddressMException(e.getMessage());
			}
		}
	}	
	
	/**
	 * @param appRecordID
	 */
	public void deleteTableORIG_Corperated(String cmpCode, String idNo)throws OrigCustomerAddressMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_BALANCE_SHEET ");
			sql.append(" WHERE CMPCDE = ? AND IDNO = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cmpCode);
			ps.setString(2, idNo);			
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigCustomerAddressMException(e.getMessage());
			}
		}
		
	}	

}
