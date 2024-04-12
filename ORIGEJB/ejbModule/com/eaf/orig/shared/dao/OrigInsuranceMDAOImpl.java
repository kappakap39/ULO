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

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigInsuranceMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.InsuranceDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigInsuranceMDAOImpl
 */
public class OrigInsuranceMDAOImpl extends OrigObjectDAO implements
		OrigInsuranceMDAO {
	private static Logger log = Logger
	.getLogger(OrigInsuranceMDAOImpl.class);
	/**
	 * 
	 */
	public OrigInsuranceMDAOImpl() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigInsuranceMDAO#createModelOrigInsuranceM(com.eaf.orig.shared.model.InsuranceDataM)
	 */
	public void createModelOrigInsuranceM(InsuranceDataM prmOrigInsuranceDataM)
			throws OrigInsuranceMException {
		try {
			//int insuaranceID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.INSURANCE_ID));
			//prmOrigInsuranceDataM.setInsuranceID(insuaranceID);
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			int insuaranceID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.INSURANCE_ID));
			prmOrigInsuranceDataM.setInsuranceID(insuaranceID);
			createTableORIG_INSURANCE(prmOrigInsuranceDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		}

	}

	/**
	 * @param prmOrigInsuranceDataM
	 */
	private void createTableORIG_INSURANCE(InsuranceDataM prmOrigInsuranceDataM) throws OrigInsuranceMException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_INSURANCE ");
			sql
					.append("( INSURANCE_ID,APPLICATION_RECORD_ID,OWER_INSURANCE,GUARANTEE_AMOUNT,INSURANCE_TYPE ");
			sql.append(" ,POLICY_NO,INSURANCE_COMPANY,NET_PREMIUM_AMT,GROSS_PREMIUM_AMT,EXPIRY_DATE");
			sql.append(" ,EFFECTIVE_DATE,ACC_POLICY_NUMBER,ACC_INSURANCE,ACC_NET_PREMIUN_AMT,ACC_INSURANCE_COMANY");
			sql.append(" ,ACC_EXPIRY_DATE,NOTIFICATION_NUMBER,PAYMENT_TYPE,ACC_GROSS_PREMIUM_AMT,COVERAGE_AMT");
			sql.append(" ,ACC_EFFECTIVE_DATE,CONFIRM_AMT,NOTIFICATION_DATE,ACC_CONFIRM_AMT,CREATE_BY");
			sql.append(" ,CREATE_DATE,UPDATE_BY,UPDATE_DATE,VEHICLE_ID,IDNO");
			sql.append(" ,PAY_INSURANCE_BY,VAT_AMT,DUTY_AMT,WH_TAX_AMT,WH_TAX_REQ ");
			sql.append(" ,ACC_VAT_AMT,ACC_DUTY_AMT,ACC_WH_TAX_AMT,ACC_WH_TAX_REQ,DISCOUNT_TO_CUST_AMT ");
			sql.append(" ,REBATE_TO_DEALER_AMT,DEDUCTED_BY_SALES,ACC_DISCOUNT_TO_CUST_AMT,ACC_REBATE_TO_DEALER_AMT,ACC_DEDUCTED_BY_SALES" );
			sql.append(" )");			
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,? ,SYSDATE,?,SYSDATE,?,?   ");
			sql.append("  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, prmOrigInsuranceDataM.getInsuranceID());
			ps.setString(2, prmOrigInsuranceDataM.getAppRecordID());
			ps.setString(3, prmOrigInsuranceDataM.getOwerInsurance());
			ps.setBigDecimal(4, prmOrigInsuranceDataM.getGuaranteeAmount());
			ps.setString(5, prmOrigInsuranceDataM.getInsuranceType());
			ps.setString(6, prmOrigInsuranceDataM.getPolicyNo());
			ps.setString(7, prmOrigInsuranceDataM.getInsuranceCompany());
			ps.setBigDecimal(8, prmOrigInsuranceDataM.getNetPremiumAmt());
			ps.setBigDecimal(9, prmOrigInsuranceDataM.getGrossPremiumAmt());
			ps.setDate(10,this.parseDate( prmOrigInsuranceDataM.getExpiryDate()));
			ps.setDate(11,this.parseDate( prmOrigInsuranceDataM.getEffectiveDate()));
			ps.setString(12,prmOrigInsuranceDataM.getAccPolicyNo());
			ps.setString(13,prmOrigInsuranceDataM.getAccInsurance());
			ps.setBigDecimal(14,prmOrigInsuranceDataM.getAccNetPremiunAmt());
			ps.setString(15,prmOrigInsuranceDataM.getAccInsuranceComany());
			ps.setDate(16,this.parseDate(prmOrigInsuranceDataM.getAccExpiryDate()));
			ps.setString(17,prmOrigInsuranceDataM.getNotificationNo());
			ps.setString(18,prmOrigInsuranceDataM.getPaymentType());
			ps.setBigDecimal(19,prmOrigInsuranceDataM.getAccGrossPremiumAmt());
			ps.setBigDecimal(20,prmOrigInsuranceDataM.getCoverageAmt());
			ps.setDate(21,this.parseDate(prmOrigInsuranceDataM.getAccEffectiveDate()));
			ps.setBigDecimal(22,prmOrigInsuranceDataM.getConfirmAmt());
			ps.setDate(23,this.parseDate(prmOrigInsuranceDataM.getNotificationDate()));
			ps.setBigDecimal(24,prmOrigInsuranceDataM.getAccConfirmAmt());
			ps.setString(25,prmOrigInsuranceDataM.getCreateBy());
			ps.setString(26,prmOrigInsuranceDataM.getUpdateBy());
			ps.setInt(27,prmOrigInsuranceDataM.getVenhicleId());
			ps.setString(28,prmOrigInsuranceDataM.getIdNo());
			ps.setString(29,prmOrigInsuranceDataM.getPayInsuranceBy());
			ps.setBigDecimal(30,prmOrigInsuranceDataM.getVatAmount());
			ps.setBigDecimal(31,prmOrigInsuranceDataM.getDutyAmount());
			ps.setBigDecimal(32,prmOrigInsuranceDataM.getWithHoldingTaxAmount());
			ps.setString(33,prmOrigInsuranceDataM.getWithHoldingTaxRequire());
			ps.setBigDecimal(34,prmOrigInsuranceDataM.getAccountVatAmount());
			ps.setBigDecimal(35,prmOrigInsuranceDataM.getAccountDutyAmount());
			ps.setBigDecimal(36,prmOrigInsuranceDataM.getAccountWithHoldingTaxAmount());
			ps.setString(37,prmOrigInsuranceDataM.getAccountWithHoldingTaxRequire());
			ps.setBigDecimal(38,prmOrigInsuranceDataM.getDistcountToCustAmount());
			ps.setBigDecimal(39,prmOrigInsuranceDataM.getRebateToDealerAmount());
			ps.setBigDecimal(40,prmOrigInsuranceDataM.getDeductBySales());
			ps.setBigDecimal(41,prmOrigInsuranceDataM.getAccountDistcountToCustAmount());
			ps.setBigDecimal(42,prmOrigInsuranceDataM.getAccountRebateToDealerAmount());
			ps.setBigDecimal(43,prmOrigInsuranceDataM.getAccountDeductBySales());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigInsuranceMDAO#deleteModelOrigInsuranceM(com.eaf.orig.shared.model.InsuranceDataM)
	 */
	public void deleteModelOrigInsuranceM(InsuranceDataM prmOrigInsuranceDataM)
			throws OrigInsuranceMException {
		try {
			deleteTableORIG_INSURANCE(prmOrigInsuranceDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		}

	}

	/**
	 * @param prmOrigInsuranceDataM
	 */
	private void deleteTableORIG_INSURANCE(InsuranceDataM prmOrigInsuranceDataM)throws OrigInsuranceMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_INSURANCE ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND INSURANCE_ID = ? AND VEHICLE_ID =?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigInsuranceDataM.getAppRecordID());
			ps.setInt(2, prmOrigInsuranceDataM.getInsuranceID());
			ps.setInt(3, prmOrigInsuranceDataM.getVenhicleId());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigInsuranceMException(e.getMessage());
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigInsuranceMDAO#loadModelOrigInsuranceM(java.lang.String)
	 */
	public InsuranceDataM loadModelOrigInsuranceM(String applicatinReorcId)
			throws OrigInsuranceMException {
		try {
			InsuranceDataM result = selectTableORIG_INSURANCE( applicatinReorcId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private InsuranceDataM selectTableORIG_INSURANCE(String applicatinReorcId )throws OrigInsuranceMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT    OWER_INSURANCE,GUARANTEE_AMOUNT,INSURANCE_TYPE ");
			sql.append(" ,POLICY_NO,INSURANCE_COMPANY,NET_PREMIUM_AMT,GROSS_PREMIUM_AMT,EXPIRY_DATE");
			sql.append(" ,EFFECTIVE_DATE,ACC_POLICY_NUMBER,ACC_INSURANCE,ACC_NET_PREMIUN_AMT,ACC_INSURANCE_COMANY");
			sql.append(" ,ACC_EXPIRY_DATE,NOTIFICATION_NUMBER,PAYMENT_TYPE,ACC_GROSS_PREMIUM_AMT,COVERAGE_AMT");
			sql.append(" ,ACC_EFFECTIVE_DATE,CONFIRM_AMT,NOTIFICATION_DATE,ACC_CONFIRM_AMT,CREATE_BY");
			sql.append(" ,CREATE_DATE,UPDATE_BY,UPDATE_DATE,INSURANCE_ID,VEHICLE_ID");	
			sql.append(" ,PAY_INSURANCE_BY,VAT_AMT,DUTY_AMT,WH_TAX_AMT,WH_TAX_REQ ");
			sql.append(" ,ACC_VAT_AMT,ACC_DUTY_AMT,ACC_WH_TAX_AMT,ACC_WH_TAX_REQ,DISCOUNT_TO_CUST_AMT ");
			sql.append(" ,REBATE_TO_DEALER_AMT,DEDUCTED_BY_SALES,ACC_DISCOUNT_TO_CUST_AMT,ACC_REBATE_TO_DEALER_AMT,ACC_DEDUCTED_BY_SALES" );
			sql
					.append(" FROM ORIG_INSURANCE WHERE APPLICATION_RECORD_ID = ?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicatinReorcId);						 
			rs = ps.executeQuery();
			InsuranceDataM insuranceDataM = null;
			if (rs.next()) {
				insuranceDataM = new InsuranceDataM();
				insuranceDataM.setAppRecordID(applicatinReorcId);				
				insuranceDataM.setOwerInsurance(rs.getString(1));
				insuranceDataM.setGuaranteeAmount(rs.getBigDecimal(2));
				insuranceDataM.setInsuranceType(rs.getString(3));
				insuranceDataM.setPolicyNo(rs.getString(4));
				insuranceDataM.setInsuranceCompany(rs.getString(5));
				insuranceDataM.setNetPremiumAmt(rs.getBigDecimal(6));
				insuranceDataM.setGrossPremiumAmt(rs.getBigDecimal(7));
				insuranceDataM.setExpiryDate(rs.getDate(8));
				insuranceDataM.setEffectiveDate(rs.getDate(9));
				insuranceDataM.setAccPolicyNo(rs.getString(10));
				insuranceDataM.setAccInsurance(rs.getString(11));
				insuranceDataM.setAccNetPremiunAmt(rs.getBigDecimal(12));
				insuranceDataM.setAccInsuranceComany(rs.getString(13));
				insuranceDataM.setAccExpiryDate(rs.getDate(14));
				insuranceDataM.setNotificationNo(rs.getString(15));
				insuranceDataM.setPaymentType(rs.getString(16));
				insuranceDataM.setAccGrossPremiumAmt(rs.getBigDecimal(17));
				insuranceDataM.setCoverageAmt(rs.getBigDecimal(18));
				insuranceDataM.setAccEffectiveDate(rs.getDate(19 ));
				insuranceDataM.setConfirmAmt(rs.getBigDecimal(20));
				insuranceDataM.setNotificationDate(rs.getDate(21));
				insuranceDataM.setAccConfirmAmt(rs.getBigDecimal(22));
				insuranceDataM.setCreateBy(rs.getString(23));
				insuranceDataM.setCreateDate(rs.getTimestamp(24));
				insuranceDataM.setUpdateBy(rs.getString(25));
				insuranceDataM.setUpdateDate(rs.getTimestamp(26));
				insuranceDataM.setInsuranceID(rs.getInt(27));
				insuranceDataM.setVenhicleId(rs.getInt(28));
				insuranceDataM.setPayInsuranceBy(rs.getString(29));
				insuranceDataM.setVatAmount(rs.getBigDecimal(30));
				insuranceDataM.setDutyAmount(rs.getBigDecimal(31));
				insuranceDataM.setWithHoldingTaxAmount(rs.getBigDecimal(32)); 
				insuranceDataM.setWithHoldingTaxRequire(rs.getString(33)); 
				insuranceDataM.setAccountVatAmount(rs.getBigDecimal(34 ));
				insuranceDataM.setAccountDutyAmount(rs.getBigDecimal(35));
				insuranceDataM.setAccountWithHoldingTaxAmount(rs.getBigDecimal(36));
				insuranceDataM.setAccountWithHoldingTaxRequire(rs.getString(37));
			    insuranceDataM.setDistcountToCustAmount(rs.getBigDecimal(38));
				insuranceDataM.setRebateToDealerAmount(rs.getBigDecimal(39));
				insuranceDataM.setDeductBySales(rs.getBigDecimal(40));
				insuranceDataM.setAccountDistcountToCustAmount(rs.getBigDecimal(41));
			    insuranceDataM.setAccountRebateToDealerAmount(rs.getBigDecimal(42));
				insuranceDataM.setAccountDeductBySales(rs.getBigDecimal(43));
			}
			return insuranceDataM;
		} catch (Exception e) {
			log.fatal("error ->",e);
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigInsuranceMDAO#saveUpdateModelOrigInsuranceM(com.eaf.orig.shared.model.InsuranceDataM)
	 */
	public void saveUpdateModelOrigInsuranceM(
			InsuranceDataM prmOrigInsuranceDataM)
			throws OrigInsuranceMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_INSURANCE(prmOrigInsuranceDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_INSURANCE then call Insert method");
				//int insuaranceID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.INSURANCE_ID));
				//prmOrigInsuranceDataM.setInsuranceID(insuaranceID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				int insuaranceID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.INSURANCE_ID));
				prmOrigInsuranceDataM.setInsuranceID(insuaranceID);
				createTableORIG_INSURANCE(prmOrigInsuranceDataM);
			}
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		}

	}

	/**
	 * @param prmOrigInsuranceDataM
	 * @return
	 */
	private double updateTableORIG_INSURANCE(InsuranceDataM prmOrigInsuranceDataM)throws OrigInsuranceMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_INSURANCE ");
			sql
					.append(" SET OWER_INSURANCE=?,GUARANTEE_AMOUNT=?,INSURANCE_TYPE =?");
			sql.append(" ,POLICY_NO=?,INSURANCE_COMPANY=?,NET_PREMIUM_AMT=?,GROSS_PREMIUM_AMT=?,EXPIRY_DATE=?");
			sql.append(" ,EFFECTIVE_DATE=?,ACC_POLICY_NUMBER=?,ACC_INSURANCE=?,ACC_NET_PREMIUN_AMT=?,ACC_INSURANCE_COMANY=?");
			sql.append(" ,ACC_EXPIRY_DATE=?,NOTIFICATION_NUMBER=?,PAYMENT_TYPE=?,ACC_GROSS_PREMIUM_AMT=?,COVERAGE_AMT=?");
			sql.append(" ,ACC_EFFECTIVE_DATE=?,CONFIRM_AMT=?,NOTIFICATION_DATE=?,ACC_CONFIRM_AMT=?");
			sql.append("  ,UPDATE_BY=?,UPDATE_DATE=SYSDATE");
			sql.append(" ,PAY_INSURANCE_BY=?,VAT_AMT=?,DUTY_AMT=?,WH_TAX_AMT=?,WH_TAX_REQ=? ");
			sql.append(" ,ACC_VAT_AMT=?,ACC_DUTY_AMT=?,ACC_WH_TAX_AMT=?,ACC_WH_TAX_REQ=?,DISCOUNT_TO_CUST_AMT=? ");
			sql.append(" ,REBATE_TO_DEALER_AMT=?,DEDUCTED_BY_SALES=?,ACC_DISCOUNT_TO_CUST_AMT=?,ACC_REBATE_TO_DEALER_AMT=?,ACC_DEDUCTED_BY_SALES=?" );
			sql.append(" ,VEHICLE_ID=?  WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigInsuranceDataM.getOwerInsurance());
			ps.setBigDecimal(2, prmOrigInsuranceDataM.getGuaranteeAmount());
			ps.setString(3, prmOrigInsuranceDataM.getInsuranceType());
			ps.setString(4, prmOrigInsuranceDataM.getPolicyNo());
			ps.setString(5, prmOrigInsuranceDataM.getInsuranceCompany());
			ps.setBigDecimal(6, prmOrigInsuranceDataM.getNetPremiumAmt());
			ps.setBigDecimal(7, prmOrigInsuranceDataM.getGrossPremiumAmt());
			ps.setDate(8,this.parseDate( prmOrigInsuranceDataM.getExpiryDate()));
			ps.setDate(9,this.parseDate( prmOrigInsuranceDataM.getEffectiveDate()));
			ps.setString(10,prmOrigInsuranceDataM.getAccPolicyNo());
			ps.setString(11,prmOrigInsuranceDataM.getAccInsurance());
			ps.setBigDecimal(12,prmOrigInsuranceDataM.getAccNetPremiunAmt());
			ps.setString(13,prmOrigInsuranceDataM.getAccInsuranceComany());
			ps.setDate(14,this.parseDate(prmOrigInsuranceDataM.getAccExpiryDate()));
			ps.setString(15,prmOrigInsuranceDataM.getNotificationNo());
			ps.setString(16,prmOrigInsuranceDataM.getPaymentType());
			ps.setBigDecimal(17,prmOrigInsuranceDataM.getAccGrossPremiumAmt());
			ps.setBigDecimal(18,prmOrigInsuranceDataM.getCoverageAmt());
			ps.setDate(19,this.parseDate(prmOrigInsuranceDataM.getAccEffectiveDate()));
			ps.setBigDecimal(20,prmOrigInsuranceDataM.getConfirmAmt());
			ps.setDate(21,this.parseDate(prmOrigInsuranceDataM.getNotificationDate()));
			ps.setBigDecimal(22,prmOrigInsuranceDataM.getAccConfirmAmt());			 
			ps.setString(23,prmOrigInsuranceDataM.getUpdateBy());
			ps.setString(24,prmOrigInsuranceDataM.getPayInsuranceBy());
			ps.setBigDecimal(25,prmOrigInsuranceDataM.getVatAmount());
			ps.setBigDecimal(26,prmOrigInsuranceDataM.getDutyAmount());
			ps.setBigDecimal(27,prmOrigInsuranceDataM.getWithHoldingTaxAmount());
			ps.setString(28,prmOrigInsuranceDataM.getWithHoldingTaxRequire());
			ps.setBigDecimal(29,prmOrigInsuranceDataM.getAccountVatAmount());
			ps.setBigDecimal(30,prmOrigInsuranceDataM.getAccountDutyAmount());
			ps.setBigDecimal(31,prmOrigInsuranceDataM.getAccountWithHoldingTaxAmount());
			ps.setString(32,prmOrigInsuranceDataM.getAccountWithHoldingTaxRequire());
			ps.setBigDecimal(33,prmOrigInsuranceDataM.getDistcountToCustAmount());
			ps.setBigDecimal(34,prmOrigInsuranceDataM.getRebateToDealerAmount());
			ps.setBigDecimal(35,prmOrigInsuranceDataM.getDeductBySales());
			ps.setBigDecimal(36,prmOrigInsuranceDataM.getAccountDistcountToCustAmount());
			ps.setBigDecimal(37,prmOrigInsuranceDataM.getAccountRebateToDealerAmount());
			ps.setBigDecimal(38,prmOrigInsuranceDataM.getAccountDeductBySales());
			ps.setInt(39,prmOrigInsuranceDataM.getVenhicleId());
			//ps.setInt(40, prmOrigInsuranceDataM.getInsuranceID());
			ps.setString(40, prmOrigInsuranceDataM.getAppRecordID());			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigInsuranceMDAO#saveUpdateModelOrigInsuranceM(com.eaf.orig.shared.model.InsuranceDataM)
	 */
	public void saveUpdateModelOrigInsuranceMForCreateCar(
			InsuranceDataM prmOrigInsuranceDataM)
			throws OrigInsuranceMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_INSURANCEForCreateCar(prmOrigInsuranceDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_INSURANCE then call Insert method");
				//int insuaranceID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.INSURANCE_ID));
				//prmOrigInsuranceDataM.setInsuranceID(insuaranceID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				int insuaranceID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.INSURANCE_ID));
				prmOrigInsuranceDataM.setInsuranceID(insuaranceID);
				createTableORIG_INSURANCE(prmOrigInsuranceDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		}

	}

	/**
	 * @param prmOrigInsuranceDataM
	 * @return
	 */
	private double updateTableORIG_INSURANCEForCreateCar(InsuranceDataM prmOrigInsuranceDataM)throws OrigInsuranceMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_INSURANCE ");
			sql
					.append(" SET OWER_INSURANCE=?,GUARANTEE_AMOUNT=?,INSURANCE_TYPE =?");
			sql.append(" ,POLICY_NO=?,INSURANCE_COMPANY=?,NET_PREMIUM_AMT=?,GROSS_PREMIUM_AMT=?,EXPIRY_DATE=?");
			sql.append(" ,EFFECTIVE_DATE=?,ACC_POLICY_NUMBER=?,ACC_INSURANCE=?,ACC_NET_PREMIUN_AMT=?,ACC_INSURANCE_COMANY=?");
			sql.append(" ,ACC_EXPIRY_DATE=?,NOTIFICATION_NUMBER=?,PAYMENT_TYPE=?,ACC_GROSS_PREMIUM_AMT=?,COVERAGE_AMT=?");
			sql.append(" ,ACC_EFFECTIVE_DATE=?,CONFIRM_AMT=?,NOTIFICATION_DATE=?,ACC_CONFIRM_AMT=?");			
			sql.append("  ,UPDATE_BY=?,UPDATE_DATE=SYSDATE");
			sql.append(" ,PAY_INSURANCE_BY=?,VAT_AMT=?,DUTY_AMT=?,WH_TAX_AMT=?,WH_TAX_REQ=? ");
			sql.append(" ,ACC_VAT_AMT=?,ACC_DUTY_AMT=?,ACC_WH_TAX_AMT=?,ACC_WH_TAX_REQ=?,DISCOUNT_TO_CUST_AMT=? ");
			sql.append(" ,REBATE_TO_DEALER_AMT=?,DEDUCTED_BY_SALES=?,ACC_DISCOUNT_TO_CUST_AMT=?,ACC_REBATE_TO_DEALER_AMT=?,ACC_DEDUCTED_BY_SALES=?" );
			sql.append(" WHERE VEHICLE_ID=? AND INSURANCE_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigInsuranceDataM.getOwerInsurance());
			ps.setBigDecimal(2, prmOrigInsuranceDataM.getGuaranteeAmount());
			ps.setString(3, prmOrigInsuranceDataM.getInsuranceType());
			ps.setString(4, prmOrigInsuranceDataM.getPolicyNo());
			ps.setString(5, prmOrigInsuranceDataM.getInsuranceCompany());
			ps.setBigDecimal(6, prmOrigInsuranceDataM.getNetPremiumAmt());
			ps.setBigDecimal(7, prmOrigInsuranceDataM.getGrossPremiumAmt());
			ps.setDate(8,this.parseDate( prmOrigInsuranceDataM.getExpiryDate()));
			ps.setDate(9,this.parseDate( prmOrigInsuranceDataM.getEffectiveDate()));
			ps.setString(10,prmOrigInsuranceDataM.getAccPolicyNo());
			ps.setString(11,prmOrigInsuranceDataM.getAccInsurance());
			ps.setBigDecimal(12,prmOrigInsuranceDataM.getAccNetPremiunAmt());
			ps.setString(13,prmOrigInsuranceDataM.getAccInsuranceComany());
			ps.setDate(14,this.parseDate(prmOrigInsuranceDataM.getAccExpiryDate()));
			ps.setString(15,prmOrigInsuranceDataM.getNotificationNo());
			ps.setString(16,prmOrigInsuranceDataM.getPaymentType());
			ps.setBigDecimal(17,prmOrigInsuranceDataM.getAccGrossPremiumAmt());
			ps.setBigDecimal(18,prmOrigInsuranceDataM.getCoverageAmt());
			ps.setDate(19,this.parseDate(prmOrigInsuranceDataM.getAccEffectiveDate()));
			ps.setBigDecimal(20,prmOrigInsuranceDataM.getConfirmAmt());
			ps.setDate(21,this.parseDate(prmOrigInsuranceDataM.getNotificationDate()));
			ps.setBigDecimal(22,prmOrigInsuranceDataM.getAccConfirmAmt());			 
			ps.setString(23,prmOrigInsuranceDataM.getUpdateBy());
			//ps.setString(24, prmOrigInsuranceDataM.getAppRecordID());
			ps.setString(24,prmOrigInsuranceDataM.getPayInsuranceBy());
			ps.setBigDecimal(25,prmOrigInsuranceDataM.getVatAmount());
			ps.setBigDecimal(26,prmOrigInsuranceDataM.getDutyAmount());
			ps.setBigDecimal(27,prmOrigInsuranceDataM.getWithHoldingTaxAmount());
			ps.setString(28,prmOrigInsuranceDataM.getWithHoldingTaxRequire());
			ps.setBigDecimal(29,prmOrigInsuranceDataM.getAccountVatAmount());
			ps.setBigDecimal(30,prmOrigInsuranceDataM.getAccountDutyAmount());
			ps.setBigDecimal(31,prmOrigInsuranceDataM.getAccountWithHoldingTaxAmount());
			ps.setString(32,prmOrigInsuranceDataM.getAccountWithHoldingTaxRequire());
			ps.setBigDecimal(33,prmOrigInsuranceDataM.getDistcountToCustAmount());
			ps.setBigDecimal(34,prmOrigInsuranceDataM.getRebateToDealerAmount());
			ps.setBigDecimal(35,prmOrigInsuranceDataM.getDeductBySales());
			ps.setBigDecimal(36,prmOrigInsuranceDataM.getAccountDistcountToCustAmount());
			ps.setBigDecimal(37,prmOrigInsuranceDataM.getAccountRebateToDealerAmount());
			ps.setBigDecimal(38,prmOrigInsuranceDataM.getAccountDeductBySales());
			ps.setInt(39,prmOrigInsuranceDataM.getVenhicleId());
			ps.setInt(40, prmOrigInsuranceDataM.getInsuranceID());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public InsuranceDataM loadModelOrigInsuranceMByVehicleId(int vehicleId)
	throws OrigInsuranceMException {
		try {
			InsuranceDataM result = selectTableORIG_INSURANCE_BY_VEHICLEID(vehicleId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		}
	}
	
	private InsuranceDataM selectTableORIG_INSURANCE_BY_VEHICLEID(int vehicleId )throws OrigInsuranceMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT    OWER_INSURANCE,GUARANTEE_AMOUNT,INSURANCE_TYPE ");
			sql.append(" ,POLICY_NO,INSURANCE_COMPANY,NET_PREMIUM_AMT,GROSS_PREMIUM_AMT,EXPIRY_DATE");
			sql.append(" ,EFFECTIVE_DATE,ACC_POLICY_NUMBER,ACC_INSURANCE,ACC_NET_PREMIUN_AMT,ACC_INSURANCE_COMANY");
			sql.append(" ,ACC_EXPIRY_DATE,NOTIFICATION_NUMBER,PAYMENT_TYPE,ACC_GROSS_PREMIUM_AMT,COVERAGE_AMT");
			sql.append(" ,ACC_EFFECTIVE_DATE,CONFIRM_AMT,NOTIFICATION_DATE,ACC_CONFIRM_AMT,CREATE_BY");
			sql.append(" ,CREATE_DATE,UPDATE_BY,UPDATE_DATE,INSURANCE_ID,VEHICLE_ID,IDNO");			
			sql.append(" ,PAY_INSURANCE_BY,VAT_AMT,DUTY_AMT,WH_TAX_AMT,WH_TAX_REQ ");
			sql.append(" ,ACC_VAT_AMT,ACC_DUTY_AMT,ACC_WH_TAX_AMT,ACC_WH_TAX_REQ,DISCOUNT_TO_CUST_AMT ");
			sql.append(" ,REBATE_TO_DEALER_AMT,DEDUCTED_BY_SALES,ACC_DISCOUNT_TO_CUST_AMT,ACC_REBATE_TO_DEALER_AMT,ACC_DEDUCTED_BY_SALES" );
			sql
					.append(" FROM ORIG_INSURANCE WHERE VEHICLE_ID = ?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setInt(1, vehicleId);						 
			rs = ps.executeQuery();
			InsuranceDataM insuranceDataM = null;
			if (rs.next()) {
				insuranceDataM = new InsuranceDataM();			
				insuranceDataM.setOwerInsurance(rs.getString(1));
				insuranceDataM.setGuaranteeAmount(rs.getBigDecimal(2));
				insuranceDataM.setInsuranceType(rs.getString(3));
				insuranceDataM.setPolicyNo(rs.getString(4));
				insuranceDataM.setInsuranceCompany(rs.getString(5));
				insuranceDataM.setNetPremiumAmt(rs.getBigDecimal(6));
				insuranceDataM.setGrossPremiumAmt(rs.getBigDecimal(7));
				insuranceDataM.setExpiryDate(rs.getDate(8));
				insuranceDataM.setEffectiveDate(rs.getDate(9));
				insuranceDataM.setAccPolicyNo(rs.getString(10));
				insuranceDataM.setAccInsurance(rs.getString(11));
				insuranceDataM.setAccNetPremiunAmt(rs.getBigDecimal(12));
				insuranceDataM.setAccInsuranceComany(rs.getString(13));
				insuranceDataM.setAccExpiryDate(rs.getDate(14));
				insuranceDataM.setNotificationNo(rs.getString(15));
				insuranceDataM.setPaymentType(rs.getString(16));
				insuranceDataM.setAccGrossPremiumAmt(rs.getBigDecimal(17));
				insuranceDataM.setCoverageAmt(rs.getBigDecimal(18));
				insuranceDataM.setAccEffectiveDate(rs.getDate(19 ));
				insuranceDataM.setConfirmAmt(rs.getBigDecimal(20));
				insuranceDataM.setNotificationDate(rs.getDate(21));
				insuranceDataM.setAccConfirmAmt(rs.getBigDecimal(22));
				insuranceDataM.setCreateBy(rs.getString(23));
				insuranceDataM.setCreateDate(rs.getTimestamp(24));
				insuranceDataM.setUpdateBy(rs.getString(25));
				insuranceDataM.setUpdateDate(rs.getTimestamp(26));
				insuranceDataM.setInsuranceID(rs.getInt(27));
				insuranceDataM.setVenhicleId(rs.getInt(28));
				insuranceDataM.setIdNo(rs.getString(29));
				insuranceDataM.setPayInsuranceBy(rs.getString(30));
				insuranceDataM.setVatAmount(rs.getBigDecimal(31));
				insuranceDataM.setDutyAmount(rs.getBigDecimal(32));
				insuranceDataM.setWithHoldingTaxAmount(rs.getBigDecimal(33)); 
				insuranceDataM.setWithHoldingTaxRequire(rs.getString(34)); 
				insuranceDataM.setAccountVatAmount(rs.getBigDecimal(35));
				insuranceDataM.setAccountDutyAmount(rs.getBigDecimal(36));
				insuranceDataM.setAccountWithHoldingTaxAmount(rs.getBigDecimal(37));
				insuranceDataM.setAccountWithHoldingTaxRequire(rs.getString(38));
			    insuranceDataM.setDistcountToCustAmount(rs.getBigDecimal(39));
				insuranceDataM.setRebateToDealerAmount(rs.getBigDecimal(40));
				insuranceDataM.setDeductBySales(rs.getBigDecimal(41));
				insuranceDataM.setAccountDistcountToCustAmount(rs.getBigDecimal(42));
			    insuranceDataM.setAccountRebateToDealerAmount(rs.getBigDecimal(43));
				insuranceDataM.setAccountDeductBySales(rs.getBigDecimal(44));
			}
			return insuranceDataM;
		} catch (Exception e) {
			log.fatal("error ->",e);
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	public void saveUpdateModelOrigInsuranceMByVehicleId(
			InsuranceDataM prmOrigInsuranceDataM)
			throws OrigInsuranceMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_INSURANCE_BY_VEHICLE_ID(prmOrigInsuranceDataM);
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_INSURANCE then call Insert method");
				//int insuaranceID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.INSURANCE_ID));
				//prmOrigInsuranceDataM.setInsuranceID(insuaranceID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				int insuaranceID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.INSURANCE_ID));
				prmOrigInsuranceDataM.setInsuranceID(insuaranceID);
				createTableORIG_INSURANCE(prmOrigInsuranceDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		}
	}
	
	private double updateTableORIG_INSURANCE_BY_VEHICLE_ID(InsuranceDataM prmOrigInsuranceDataM)throws OrigInsuranceMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_INSURANCE ");
			sql
					.append(" SET OWER_INSURANCE=?,GUARANTEE_AMOUNT=?,INSURANCE_TYPE =?");
			sql.append(" ,POLICY_NO=?,INSURANCE_COMPANY=?,NET_PREMIUM_AMT=?,GROSS_PREMIUM_AMT=?,EXPIRY_DATE=?");
			sql.append(" ,EFFECTIVE_DATE=?,ACC_POLICY_NUMBER=?,ACC_INSURANCE=?,ACC_NET_PREMIUN_AMT=?,ACC_INSURANCE_COMANY=?");
			sql.append(" ,ACC_EXPIRY_DATE=?,NOTIFICATION_NUMBER=?,PAYMENT_TYPE=?,ACC_GROSS_PREMIUM_AMT=?,COVERAGE_AMT=?");
			sql.append(" ,ACC_EFFECTIVE_DATE=?,CONFIRM_AMT=?,NOTIFICATION_DATE=?,ACC_CONFIRM_AMT=?");
			sql.append("  ,UPDATE_BY=?,UPDATE_DATE=SYSDATE");	
			sql.append(" ,PAY_INSURANCE_BY=?,VAT_AMT=?,DUTY_AMT=?,WH_TAX_AMT=?,WH_TAX_REQ=? ");
			sql.append(" ,ACC_VAT_AMT=?,ACC_DUTY_AMT=?,ACC_WH_TAX_AMT=?,ACC_WH_TAX_REQ=?,DISCOUNT_TO_CUST_AMT=? ");
			sql.append(" ,REBATE_TO_DEALER_AMT=?,DEDUCTED_BY_SALES=?,ACC_DISCOUNT_TO_CUST_AMT=?,ACC_REBATE_TO_DEALER_AMT=?,ACC_DEDUCTED_BY_SALES=?" );
			
			sql.append(" WHERE VEHICLE_ID=? AND INSURANCE_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigInsuranceDataM.getOwerInsurance());
			ps.setBigDecimal(2, prmOrigInsuranceDataM.getGuaranteeAmount());
			ps.setString(3, prmOrigInsuranceDataM.getInsuranceType());
			ps.setString(4, prmOrigInsuranceDataM.getPolicyNo());
			ps.setString(5, prmOrigInsuranceDataM.getInsuranceCompany());
			ps.setBigDecimal(6, prmOrigInsuranceDataM.getNetPremiumAmt());
			ps.setBigDecimal(7, prmOrigInsuranceDataM.getGrossPremiumAmt());
			ps.setDate(8,this.parseDate( prmOrigInsuranceDataM.getExpiryDate()));
			ps.setDate(9,this.parseDate( prmOrigInsuranceDataM.getEffectiveDate()));
			ps.setString(10,prmOrigInsuranceDataM.getAccPolicyNo());
			ps.setString(11,prmOrigInsuranceDataM.getAccInsurance());
			ps.setBigDecimal(12,prmOrigInsuranceDataM.getAccNetPremiunAmt());
			ps.setString(13,prmOrigInsuranceDataM.getAccInsuranceComany());
			ps.setDate(14,this.parseDate(prmOrigInsuranceDataM.getAccExpiryDate()));
			ps.setString(15,prmOrigInsuranceDataM.getNotificationNo());
			ps.setString(16,prmOrigInsuranceDataM.getPaymentType());
			ps.setBigDecimal(17,prmOrigInsuranceDataM.getAccGrossPremiumAmt());
			ps.setBigDecimal(18,prmOrigInsuranceDataM.getCoverageAmt());
			ps.setDate(19,this.parseDate(prmOrigInsuranceDataM.getAccEffectiveDate()));
			ps.setBigDecimal(20,prmOrigInsuranceDataM.getConfirmAmt());
			ps.setDate(21,this.parseDate(prmOrigInsuranceDataM.getNotificationDate()));
			ps.setBigDecimal(22,prmOrigInsuranceDataM.getAccConfirmAmt());			 
			ps.setString(23,prmOrigInsuranceDataM.getUpdateBy());
			ps.setString(24,prmOrigInsuranceDataM.getPayInsuranceBy());
			ps.setBigDecimal(25,prmOrigInsuranceDataM.getVatAmount());
			ps.setBigDecimal(26,prmOrigInsuranceDataM.getDutyAmount());
			ps.setBigDecimal(27,prmOrigInsuranceDataM.getWithHoldingTaxAmount());
			ps.setString(28,prmOrigInsuranceDataM.getWithHoldingTaxRequire());
			ps.setBigDecimal(29,prmOrigInsuranceDataM.getAccountVatAmount());
			ps.setBigDecimal(30,prmOrigInsuranceDataM.getAccountDutyAmount());
			ps.setBigDecimal(31,prmOrigInsuranceDataM.getAccountWithHoldingTaxAmount());
			ps.setString(32,prmOrigInsuranceDataM.getAccountWithHoldingTaxRequire());
			ps.setBigDecimal(33,prmOrigInsuranceDataM.getDistcountToCustAmount());
			ps.setBigDecimal(34,prmOrigInsuranceDataM.getRebateToDealerAmount());
			ps.setBigDecimal(35,prmOrigInsuranceDataM.getDeductBySales());
			ps.setBigDecimal(36,prmOrigInsuranceDataM.getAccountDistcountToCustAmount());
			ps.setBigDecimal(37,prmOrigInsuranceDataM.getAccountRebateToDealerAmount());
			ps.setBigDecimal(38,prmOrigInsuranceDataM.getAccountDeductBySales());
			ps.setInt(39,prmOrigInsuranceDataM.getVenhicleId());
			ps.setInt(40, prmOrigInsuranceDataM.getInsuranceID());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	
	public void deleteModelOrigInsuranceMByIdNo(String IdNo,String vehicleId)
	throws OrigInsuranceMException {
		try {
		    deleteTableORIG_INSURANCE_BY_IDNO(IdNo,vehicleId);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInsuranceMException(e.getMessage());
		}
	}
	
	private void deleteTableORIG_INSURANCE_BY_IDNO(String IdNo,String vehicleId)throws OrigInsuranceMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_INSURANCE ");
			sql.append(" WHERE IDNO =?");
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
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigInsuranceMException(e.getMessage());
			}
		}
		
	}
	public int updateInsuranceForChangeCar(int insuranceId, String apprecordID)throws OrigInsuranceMException {
		int returnRow = 0;
		try {
			
			returnRow = updateInsuranceForOldCar(insuranceId, apprecordID);
			returnRow = updateInsuranceForNewCar(insuranceId, apprecordID);
			
			return returnRow;
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigInsuranceMException(e.getMessage());
		}
	}
	private int updateInsuranceForNewCar(int insuranceId, String apprecordID)throws OrigInsuranceMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_INSURANCE ");
			sql.append(" SET APPLICATION_RECORD_ID=?");
			sql.append(" WHERE INSURANCE_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("insuranceId=" + insuranceId);
			
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, apprecordID);
			ps.setInt(2, insuranceId);
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error >> ", e);
			}
		}
		return returnRows;
	}
	private int updateInsuranceForOldCar(int insuranceId, String apprecordID)throws OrigInsuranceMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_INSURANCE ");
			sql.append(" SET APPLICATION_RECORD_ID=null");
			sql.append(" WHERE INSURANCE_ID <> ? AND APPLICATION_RECORD_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("insuranceId=" + insuranceId);
			
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, insuranceId);
			ps.setString(2, apprecordID);
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigInsuranceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error >> ", e);
			}
		}
		return returnRows;
	}
}
