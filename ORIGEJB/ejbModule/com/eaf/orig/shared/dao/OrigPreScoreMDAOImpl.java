/*
 * Created on Jan 2, 2008
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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigPreScoreMException;
import com.eaf.orig.shared.model.PreScoreDataM;

/**
 * @author DQVIII
 *
 * Type: OrigPreScoreMDAOImpl
 */
public class OrigPreScoreMDAOImpl extends OrigObjectDAO implements
        OrigPreScoreMDAO {
    private static Logger log = Logger.getLogger(OrigPreScoreMDAOImpl.class);
    /**
     * 
     */
    public OrigPreScoreMDAOImpl() {
        super();
 
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPreScoreMDAO#createModelOrigPreScoreM(com.eaf.orig.shared.model.PreScoreDataM)
     */
    public void createModelOrigPreScoreM(PreScoreDataM prmOrigPreScoreDataM)
            throws OrigPreScoreMException {
        try {
			createTableORIG_PRE_SCORE(prmOrigPreScoreDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigPreScoreMException(e.getMessage());
		}

    }

    /**
     * @param prmOrigPreScoreDataM
     */
    private void createTableORIG_PRE_SCORE(PreScoreDataM prmOrigPreScoreDataM) throws OrigPreScoreMException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_PRE_SCORE ");
			sql.append("( APPLICATION_RECORD_ID ,MARKETING_CODE,POSITION,BUSINESS_SIZE,SALARY ");
			sql.append(" ,QUALIFICATION,HOUSE_REGIST_STATUS,LAND_OWNERSHIP,NO_OF_EMPLOYEE,OCCUPATION ");
			sql.append(" ,BUSINESS_TYPE,SHARE_CAPITAL,OTHER_INCOME,TOTAL_WORK_YEAR,TOTAL_WORK_MONTH   " );
			sql.append(" ,TIME_IN_CURRENT_ADDRESS,CHEQUE_RETURNED,MAX_OVERDUE_6M ,OVERDUE_12M ,NO_REVOLVING_LOAN " );
			sql.append(" ,NO_AUTO_HIRE_PURCHASE,NPL,DOWN_PAYMENT,TERM_LOAN,NO_OF_GUARANTOR ");
			sql.append(" ,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,RESULT ");
			sql.append(" ,SCORE,CAR_PRICE,FINANCE_AMT_VAT,INSTALLMENT_AMT_VAT,HOUSEIDNO");
			//Remove CMPCDE
			//sql.append(" ,CMPCDE,IDNO)");
			sql.append(" ,IDNO)");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append("       ,?,?,?,?,?  ,SYSDATE,?,SYSDATE,?,? ,?,?,?,?,?  ");
			//Remove CMPCDE
			//sql.append(" ,?,? )");
			sql.append(" ,? )");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPreScoreDataM.getApplicationRecordId());
			ps.setString(2, prmOrigPreScoreDataM.getMarketingCode());
			ps.setString(3, prmOrigPreScoreDataM.getPosition());
			ps.setString(4, prmOrigPreScoreDataM.getBusinessSize());
			ps.setBigDecimal(5, prmOrigPreScoreDataM.getSalary());
			ps.setString(6,prmOrigPreScoreDataM.getQualification());
			ps.setString(7,prmOrigPreScoreDataM.getHouseRegistStatus());
			ps.setString(8,prmOrigPreScoreDataM.getLandOwnerShip());
			ps.setInt(9,prmOrigPreScoreDataM.getNoOfEmployee());
			ps.setString(10,prmOrigPreScoreDataM.getOccupation());
			ps.setString(11,prmOrigPreScoreDataM.getBusinessType());
			ps.setBigDecimal(12,prmOrigPreScoreDataM.getShareCapital()  );
			ps.setBigDecimal(13,prmOrigPreScoreDataM.getOtherIncome());
			ps.setInt(14,prmOrigPreScoreDataM.getTotalWorkYear());
			ps.setInt(15,prmOrigPreScoreDataM.getTotalWorkMonth());
			ps.setBigDecimal(16, new BigDecimal((double)prmOrigPreScoreDataM.getTimeInCurrentAddressYear()+ ((double)prmOrigPreScoreDataM.getTimeInCurrentAddressMonth())/100d ));
			ps.setBigDecimal(17,prmOrigPreScoreDataM.getChequeReturn());
			ps.setInt(18,prmOrigPreScoreDataM.getMaxOverDue6Month());
			ps.setInt(19,prmOrigPreScoreDataM.getOverDue60dayIn12Month());
			ps.setInt(20,prmOrigPreScoreDataM.getNoRevolvingLoan());
			ps.setInt(21,prmOrigPreScoreDataM.getNoAutoHirePurchase());
			ps.setString(22,prmOrigPreScoreDataM.getNPL());
			ps.setBigDecimal(23,prmOrigPreScoreDataM.getDownPayment());
			ps.setInt(24,prmOrigPreScoreDataM.getTermLoan());
			ps.setInt(25,prmOrigPreScoreDataM.getNoOfGuarantor());
			ps.setString(26,prmOrigPreScoreDataM.getCreateBy());
			ps.setString(27,prmOrigPreScoreDataM.getUpdateBy());
			ps.setString(28,prmOrigPreScoreDataM.getResult());
			ps.setInt(29,prmOrigPreScoreDataM.getScore());
			ps.setBigDecimal(30,prmOrigPreScoreDataM.getCarPrice());
			ps.setBigDecimal(31,prmOrigPreScoreDataM.getFinanceAmountVAT());
			ps.setBigDecimal(32,prmOrigPreScoreDataM.getInstallmentAmountVAT());
			ps.setString(33,prmOrigPreScoreDataM.getHouseIdno());
			//Remove CMPCDE
			//ps.setString(34,prmOrigPreScoreDataM.getCmpCode());
			ps.setString(34,prmOrigPreScoreDataM.getIdNo());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigPreScoreMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPreScoreMDAO#deleteModelOrigPreScoreM(com.eaf.orig.shared.model.PreScoreDataM)
     */
    public void deleteModelOrigPreScoreM(PreScoreDataM prmOrigPreScoreDataM)
            throws OrigPreScoreMException {
        try {
			deleteTableORIG_PRE_SCORE(prmOrigPreScoreDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigPreScoreMException(e.getMessage());
		}

    }

    /**
     * @param prmOrigPreScoreDataM
     */
    private void deleteTableORIG_PRE_SCORE(PreScoreDataM prmOrigPreScoreDataM)throws OrigPreScoreMException {
    	PreparedStatement ps = null;
    	Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_PRE_SCORE ");
			//Remove CMPCDE
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE= ? and IDNO=?  ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?  and IDNO=?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPreScoreDataM.getApplicationRecordId());
			//Remove CMPCDE
			//ps.setString(2,prmOrigPreScoreDataM.getCmpCode());
			ps.setString(2,prmOrigPreScoreDataM.getIdNo());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e );
			throw new OrigPreScoreMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigPreScoreMException(e.getMessage());
			}
		}
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPreScoreMDAO#loadModelOrigPreScoreM(java.lang.String)
     */
    public PreScoreDataM loadModelOrigPreScoreM(String applicationRecordId,String cmpcde,String idno)
            throws OrigPreScoreMException {
        try {
			PreScoreDataM result = selectTableORIG_PRE_SCORE(applicationRecordId,cmpcde,idno);			
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigPreScoreMException(e.getMessage());
		}
    }

    /**
     * @param applicationRecordId
     * @return
     */
    private PreScoreDataM selectTableORIG_PRE_SCORE(String applicationRecordId,String cmpcde,String idno) throws OrigPreScoreMException{
        PreparedStatement ps = null;
		ResultSet rs = null;
		PreScoreDataM preScoreDataM = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT  MARKETING_CODE,POSITION,BUSINESS_SIZE,SALARY ");
			sql.append(" ,QUALIFICATION,HOUSE_REGIST_STATUS,LAND_OWNERSHIP,NO_OF_EMPLOYEE,OCCUPATION ");
			sql.append(" ,BUSINESS_TYPE,SHARE_CAPITAL,OTHER_INCOME,TOTAL_WORK_YEAR,TOTAL_WORK_MONTH   " );
			sql.append(" ,TIME_IN_CURRENT_ADDRESS,CHEQUE_RETURNED,MAX_OVERDUE_6M ,OVERDUE_12M ,NO_REVOLVING_LOAN " );
			sql.append(" ,NO_AUTO_HIRE_PURCHASE,NPL,DOWN_PAYMENT,TERM_LOAN,NO_OF_GUARANTOR,RESULT ");
			sql.append(" ,SCORE,CAR_PRICE,FINANCE_AMT_VAT,INSTALLMENT_AMT_VAT,HOUSEIDNO ");
			//Remove CMPCDE
			//sql.append(" ,CMPCDE,IDNO ");
			sql.append(" ,IDNO ");
			//sql.append(" ,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			//Remove CMPCDE
			//sql.append(" FROM ORIG_PRE_SCORE WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO =? ");
			sql.append(" FROM ORIG_PRE_SCORE WHERE APPLICATION_RECORD_ID = ? AND IDNO =? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);
			//Remove CMPCDE
            //ps.setString(2,cmpcde);
            ps.setString(2,idno);
			rs = ps.executeQuery();
			if (rs.next()) {
			    preScoreDataM = new PreScoreDataM();
			    preScoreDataM.setApplicationRecordId(applicationRecordId);
			    preScoreDataM.setMarketingCode(rs.getString(1));
			    preScoreDataM.setPosition(rs.getString(2));
			    preScoreDataM.setBusinessSize(rs.getString(3));
			    preScoreDataM.setSalary(rs.getBigDecimal(4));
			    preScoreDataM.setQualification(rs.getString(5));
			    preScoreDataM.setHouseRegistStatus(rs.getString(6));
			    preScoreDataM.setLandOwnerShip(rs.getString(7));
			    preScoreDataM.setNoOfEmployee(rs.getInt(8));
			    preScoreDataM.setOccupation(rs.getString(9));
			    preScoreDataM.setBusinessType(rs.getString(10));
			    preScoreDataM.setShareCapital(rs.getBigDecimal(11));
			    preScoreDataM.setOtherIncome(rs.getBigDecimal(12));
			    preScoreDataM.setTotalWorkYear(rs.getInt(13));
			    preScoreDataM.setTotalWorkMonth(rs.getInt(14));
			    BigDecimal timeIncurrenAddress=rs.getBigDecimal(15);
			    preScoreDataM.setTimeInCurrentAddressYear(timeIncurrenAddress.intValue());
			    preScoreDataM.setTimeInCurrentAddressMonth( (int)(Math.round((timeIncurrenAddress.doubleValue()*100)%100)));
			    preScoreDataM.setChequeReturn(rs.getBigDecimal(16));
			    preScoreDataM.setMaxOverDue6Month(rs.getInt(17));
			    preScoreDataM.setOverDue60dayIn12Month(rs.getInt(18));
			    preScoreDataM.setNoRevolvingLoan(rs.getInt(19));
			    preScoreDataM.setNoAutoHirePurchase(rs.getInt(20));
			    preScoreDataM.setNPL(rs.getString(21));
			    preScoreDataM.setDownPayment(rs.getBigDecimal(22));
			    preScoreDataM.setTermLoan(rs.getInt(23));
			    preScoreDataM.setNoOfGuarantor(rs.getInt(24));
			    preScoreDataM.setResult(rs.getString(25));
			    preScoreDataM.setScore(rs.getInt(26));
			    preScoreDataM.setCarPrice(rs.getBigDecimal(27));
			    preScoreDataM.setFinanceAmountVAT(rs.getBigDecimal(28));
			    preScoreDataM.setInstallmentAmountVAT(rs.getBigDecimal(29));	
			    preScoreDataM.setHouseIdno(rs.getString(30));
			    //Remove CMPCDE
			    //preScoreDataM.setCmpCode(rs.getString(31));
			    preScoreDataM.setIdNo(rs.getString(31));
			   // preScoreDataM.setCreateDate(rs.getTimestamp(25));
			   // preScoreDataM.setApplicationRecordId(rs.getString(1));
			   // preScoreDataM.setApplicationRecordId(rs.getString(1));
			   // preScoreDataM.setApplicationRecordId(rs.getString(1));
				 
			}
			return preScoreDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigPreScoreMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPreScoreMDAO#saveUpdateModelOrigPreScoreM(com.eaf.orig.shared.model.PreScoreDataM)
     */
    public void saveUpdateModelOrigPreScoreM(PreScoreDataM prmOrigPreScoreDataM)
            throws OrigPreScoreMException {
        double returnRows = 0;
		try {
			returnRows = updateTableORIG_PRE_SCORE(prmOrigPreScoreDataM);
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_PRE_SCORE then call Insert method");
				createTableORIG_PRE_SCORE(prmOrigPreScoreDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigPreScoreMException(e.getMessage());
		}


    }

    /**
     * @param prmOrigPreScoreDataM
     * @return
     */
    private double updateTableORIG_PRE_SCORE(PreScoreDataM prmOrigPreScoreDataM) throws OrigPreScoreMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_PRE_SCORE ");
			sql.append(" SET MARKETING_CODE=?,POSITION=?,BUSINESS_SIZE=?,SALARY=? ");
			sql.append(" ,QUALIFICATION=?,HOUSE_REGIST_STATUS=?,LAND_OWNERSHIP=?,NO_OF_EMPLOYEE=?,OCCUPATION=? ");
			sql.append(" ,BUSINESS_TYPE=?,SHARE_CAPITAL=?,OTHER_INCOME=?,TOTAL_WORK_YEAR=?,TOTAL_WORK_MONTH=?   " );
			sql.append(" ,TIME_IN_CURRENT_ADDRESS=?,CHEQUE_RETURNED=?,MAX_OVERDUE_6M =?,OVERDUE_12M=? ,NO_REVOLVING_LOAN=? " );
			sql.append(" ,NO_AUTO_HIRE_PURCHASE=?,NPL=?,DOWN_PAYMENT=?,TERM_LOAN=?,NO_OF_GUARANTOR=? ");
			sql.append(" ,UPDATE_DATE=SYSDATE,UPDATE_BY=?,RESULT=?,SCORE=?,CAR_PRICE=?,FINANCE_AMT_VAT=?,INSTALLMENT_AMT_VAT=? ");
			sql.append(" ,HOUSEIDNO=?  ");
			//Remove CMPCDE
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND IDNO=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, prmOrigPreScoreDataM.getMarketingCode());
			ps.setString(2, prmOrigPreScoreDataM.getPosition());
			ps.setString(3, prmOrigPreScoreDataM.getBusinessSize());
			ps.setBigDecimal(4, prmOrigPreScoreDataM.getSalary());
			ps.setString(5,prmOrigPreScoreDataM.getQualification());
			
			ps.setString(6,prmOrigPreScoreDataM.getHouseRegistStatus());
			ps.setString(7,prmOrigPreScoreDataM.getLandOwnerShip());
			ps.setInt(8,prmOrigPreScoreDataM.getNoOfEmployee());
			ps.setString(9,prmOrigPreScoreDataM.getOccupation());
			ps.setString(10,prmOrigPreScoreDataM.getBusinessType());
			ps.setBigDecimal(11,prmOrigPreScoreDataM.getShareCapital()  );
			ps.setBigDecimal(12,prmOrigPreScoreDataM.getOtherIncome());
			ps.setInt(13,prmOrigPreScoreDataM.getTotalWorkYear());
			ps.setInt(14,prmOrigPreScoreDataM.getTotalWorkMonth());
			ps.setBigDecimal(15,new BigDecimal((double)prmOrigPreScoreDataM.getTimeInCurrentAddressYear()+ ((double)prmOrigPreScoreDataM.getTimeInCurrentAddressMonth())/100d ));
			ps.setBigDecimal(16,prmOrigPreScoreDataM.getChequeReturn());
			ps.setInt(17,prmOrigPreScoreDataM.getMaxOverDue6Month());
			ps.setInt(18,prmOrigPreScoreDataM.getOverDue60dayIn12Month());
			ps.setInt(19,prmOrigPreScoreDataM.getNoRevolvingLoan());
			ps.setInt(20,prmOrigPreScoreDataM.getNoAutoHirePurchase());
			ps.setString(21,prmOrigPreScoreDataM.getNPL());
			ps.setBigDecimal(22,prmOrigPreScoreDataM.getDownPayment());
			ps.setInt(23,prmOrigPreScoreDataM.getTermLoan());
			ps.setInt(24,prmOrigPreScoreDataM.getNoOfGuarantor());
			ps.setString(25,prmOrigPreScoreDataM.getUpdateBy());
			ps.setString(26,prmOrigPreScoreDataM.getResult()); 	
			ps.setInt(27,prmOrigPreScoreDataM.getScore());
			ps.setBigDecimal(28,prmOrigPreScoreDataM.getCarPrice());
			ps.setBigDecimal(29,prmOrigPreScoreDataM.getFinanceAmountVAT());
			ps.setBigDecimal(30,prmOrigPreScoreDataM.getInstallmentAmountVAT());
			ps.setString(31,prmOrigPreScoreDataM.getHouseIdno());
			ps.setString(32, prmOrigPreScoreDataM.getApplicationRecordId());
			//Remove CMPCDE 
			//ps.setString(33,prmOrigPreScoreDataM.getCmpCode());
			ps.setString(33,prmOrigPreScoreDataM.getIdNo());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigPreScoreMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
    }

}
