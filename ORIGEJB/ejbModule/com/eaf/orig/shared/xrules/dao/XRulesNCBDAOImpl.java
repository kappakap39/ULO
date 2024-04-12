/*
 * Created on Oct 8, 2007
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
package com.eaf.orig.shared.xrules.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.xrules.dao.exception.XRulesNCBException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesNCBDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesNCBVerificationDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesNCBDAOImpl
 */
public class XRulesNCBDAOImpl extends OrigObjectDAO implements XRulesNCBDAO {
    private static Logger log = Logger.getLogger(XRulesNCBDAOImpl.class);

    /**
     *  
     */
    public XRulesNCBDAOImpl() {
        super();
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBDAO#createModelXRulesNCBM(com.eaf.xrules.shared.model.XRulesNCBDataM)
     */
    public void createModelXRulesNCBM(XRulesNCBDataM prmXRulesNCBDataM) throws XRulesNCBException {
        try {
            createTableXRULES_NCB(prmXRulesNCBDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        }

    }

    /**
     * @param prmXRulesNCBDataM
     */
    private void createTableXRULES_NCB(XRulesNCBDataM prmXRulesNCBDataM) throws XRulesNCBException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("INSERT INTO XRULES_NCB ");
            /*sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,SEQ,LOAN_TYPE");
            sql.append("  ,ACCOUNT_STATUS ,MA_DATE,LOAN_AMOUNT  ,CREDIT_LIMIT ,CREDIT_USE ");
            sql.append("  ,INSTALLMENT_AMOUNT ,OS_BALANCE,OVERDUE_MONTHS,CREATE_DATE,CREATE_BY  ");
            sql.append("  ,UPDATE_DATE,UPDATE_BY,COLOR,SUM_ACCOUNT   )");*/
            sql.append("( PERSONAL_ID,SEQ,LOAN_TYPE");
            sql.append("  ,ACCOUNT_STATUS ,MA_DATE,LOAN_AMOUNT  ,CREDIT_LIMIT ,CREDIT_USE ");
            sql.append("  ,INSTALLMENT_AMOUNT ,OS_BALANCE,OVERDUE_MONTHS,CREATE_DATE,CREATE_BY  ");
            sql.append("  ,UPDATE_DATE,UPDATE_BY,COLOR,SUM_ACCOUNT   )");
            sql.append(" VALUES(?,?,?  ,?,?,?,?,?  ,?,?,?,SYSDATE,?   ,SYSDATE,?,?,? ) ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);

            /*ps.setString(1, prmXRulesNCBDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesNCBDataM.getCmpCode());
            ps.setString(3, prmXRulesNCBDataM.getIdNo());
            ps.setInt(4, prmXRulesNCBDataM.getSeq());
            ps.setString(5, prmXRulesNCBDataM.getLoanType());
            ps.setString(6, prmXRulesNCBDataM.getAccountStatus());
            ps.setDate(7, this.parseDate(prmXRulesNCBDataM.getMADate()));
            ps.setBigDecimal(8, prmXRulesNCBDataM.getLoanAmount());
            ps.setBigDecimal(9, prmXRulesNCBDataM.getCreditLimit());
            ps.setBigDecimal(10, prmXRulesNCBDataM.getCreditUse());
            ps.setBigDecimal(11, prmXRulesNCBDataM.getInstallationAmount());
            ps.setBigDecimal(12, prmXRulesNCBDataM.getOSBalance());
            ps.setString(13, prmXRulesNCBDataM.getOverDueMonth());
            ps.setString(14, prmXRulesNCBDataM.getCreateBy());
            ps.setString(15, prmXRulesNCBDataM.getUpdateBy());
            ps.setString(16, prmXRulesNCBDataM.getColor());
            ps.setInt(17, prmXRulesNCBDataM.getSumUnit());*/
            
            ps.setString(1, prmXRulesNCBDataM.getPersonalID());
            ps.setInt(2, prmXRulesNCBDataM.getSeq());
            ps.setString(3, prmXRulesNCBDataM.getLoanType());
            ps.setString(4, prmXRulesNCBDataM.getAccountStatus());
            ps.setDate(5, this.parseDate(prmXRulesNCBDataM.getMADate()));
            ps.setBigDecimal(6, prmXRulesNCBDataM.getLoanAmount());
            ps.setBigDecimal(7, prmXRulesNCBDataM.getCreditLimit());
            ps.setBigDecimal(8, prmXRulesNCBDataM.getCreditUse());
            ps.setBigDecimal(9, prmXRulesNCBDataM.getInstallationAmount());
            ps.setBigDecimal(10, prmXRulesNCBDataM.getOSBalance());
            ps.setString(11, prmXRulesNCBDataM.getOverDueMonth());
            ps.setString(12, prmXRulesNCBDataM.getCreateBy());
            ps.setString(13, prmXRulesNCBDataM.getUpdateBy());
            ps.setString(14, prmXRulesNCBDataM.getColor());
            ps.setInt(15, prmXRulesNCBDataM.getSumUnit());
            ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBDAO#deleteModelXRulesNCBM(com.eaf.xrules.shared.model.XRulesNCBDataM)
     */
    public void deleteModelXRulesNCBM(XRulesNCBDataM prmXRulesNCBDataM) throws XRulesNCBException {
        try {
            deleteTableXRULES_NCB(prmXRulesNCBDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        }

    }

    /**
     * @param XRulesDebBdDataM
     */
    private void deleteTableXRULES_NCB(XRulesNCBDataM XRulesDebBdDataM) throws XRulesNCBException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_NCB ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? AND SEQ=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, XRulesDebBdDataM.getApplicationRecordId());
            ps.setString(2, XRulesDebBdDataM.getCmpCode());
            ps.setString(3, XRulesDebBdDataM.getIdNo());
            ps.setInt(4, XRulesDebBdDataM.getSeq());*/
            ps.setString(1, XRulesDebBdDataM.getApplicationRecordId());
            ps.setString(2, XRulesDebBdDataM.getPersonalID());
            ps.setInt(3, XRulesDebBdDataM.getSeq());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesNCBException(e.getMessage());
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBDAO#loadModelXRulesNCBM(java.lang.String)
     */
    public Vector loadModelXRulesNCBM(String personalID) throws XRulesNCBException {
        try {
            Vector vResult = selectTableXRULES_NCB(personalID);
            return vResult;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        }
    }

    /**
     * @param applicationRecordId
     * @return
     */
    private Vector selectTableXRULES_NCB(String personalID) throws XRulesNCBException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("SELECT  CMPCDE,IDNO,SEQ,LOAN_TYPE");
            sql.append("  ,ACCOUNT_STATUS ,MA_DATE,LOAN_AMOUNT  ,CREDIT_LIMIT ,CREDIT_USE ");
            sql.append("  ,INSTALLMENT_AMOUNT ,OS_BALANCE,OVERDUE_MONTHS,CREATE_DATE,CREATE_BY  ");
            sql.append("  ,UPDATE_DATE,UPDATE_BY,COLOR,SUM_ACCOUNT  ");
            sql.append(" FROM XRULES_NCB WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO =? ORDER BY SEQ ");*/
            sql.append("SELECT  PERSONAL_ID,SEQ,LOAN_TYPE");
            sql.append("  ,ACCOUNT_STATUS ,MA_DATE,LOAN_AMOUNT  ,CREDIT_LIMIT ,CREDIT_USE ");
            sql.append("  ,INSTALLMENT_AMOUNT ,OS_BALANCE,OVERDUE_MONTHS,CREATE_DATE,CREATE_BY  ");
            sql.append("  ,UPDATE_DATE,UPDATE_BY,COLOR,SUM_ACCOUNT  ");
            sql.append(" FROM XRULES_NCB WHERE PERSONAL_ID=? ORDER BY SEQ ");
            
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            rs = null;
            ps.setString(1, personalID);
            rs = ps.executeQuery();
            Vector vt = new Vector();
            while (rs.next()) {
                XRulesNCBDataM prmXRulesNCBDataM = new XRulesNCBDataM();
                /*prmXRulesNCBDataM.setApplicationRecordId(applicationRecordId);
                prmXRulesNCBDataM.setCmpCode(rs.getString(1));
                prmXRulesNCBDataM.setIdNo(rs.getString(2));
                prmXRulesNCBDataM.setSeq(rs.getInt(3));
                prmXRulesNCBDataM.setLoanType(rs.getString(4));
                prmXRulesNCBDataM.setAccountStatus(rs.getString(5));
                prmXRulesNCBDataM.setMADate(rs.getDate(6));
                prmXRulesNCBDataM.setLoanAmount(rs.getBigDecimal(7));
                prmXRulesNCBDataM.setCreditLimit(rs.getBigDecimal(8));
                prmXRulesNCBDataM.setCreditUse(rs.getBigDecimal(9));
                prmXRulesNCBDataM.setInstallationAmount(rs.getBigDecimal(10));
                prmXRulesNCBDataM.setOSBalance(rs.getBigDecimal(11));
                prmXRulesNCBDataM.setOverDueMonth(rs.getString(12));
                prmXRulesNCBDataM.setCreateDate(rs.getTimestamp(13));
                prmXRulesNCBDataM.setCreateBy(rs.getString(14));
                prmXRulesNCBDataM.setUpdateDate(rs.getTimestamp(15));
                prmXRulesNCBDataM.setUpdateBy(rs.getString(16));
                prmXRulesNCBDataM.setColor(rs.getString(17));
                prmXRulesNCBDataM.setSumUnit(rs.getInt(18));*/
                prmXRulesNCBDataM.setPersonalID(rs.getString(1));
                prmXRulesNCBDataM.setSeq(rs.getInt(2));
                prmXRulesNCBDataM.setLoanType(rs.getString(3));
                prmXRulesNCBDataM.setAccountStatus(rs.getString(4));
                prmXRulesNCBDataM.setMADate(rs.getDate(5));
                prmXRulesNCBDataM.setLoanAmount(rs.getBigDecimal(6));
                prmXRulesNCBDataM.setCreditLimit(rs.getBigDecimal(7));
                prmXRulesNCBDataM.setCreditUse(rs.getBigDecimal(8));
                prmXRulesNCBDataM.setInstallationAmount(rs.getBigDecimal(9));
                prmXRulesNCBDataM.setOSBalance(rs.getBigDecimal(10));
                prmXRulesNCBDataM.setOverDueMonth(rs.getString(11));
                prmXRulesNCBDataM.setCreateDate(rs.getTimestamp(12));
                prmXRulesNCBDataM.setCreateBy(rs.getString(13));
                prmXRulesNCBDataM.setUpdateDate(rs.getTimestamp(14));
                prmXRulesNCBDataM.setUpdateBy(rs.getString(15));
                prmXRulesNCBDataM.setColor(rs.getString(16));
                prmXRulesNCBDataM.setSumUnit(rs.getInt(17));
                vt.add(prmXRulesNCBDataM);
            }
            return vt;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, rs, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBDAO#saveUpdateModelXRulesNCBM(com.eaf.xrules.shared.model.XRulesNCBDataM)
     */
    public void saveUpdateModelXRulesNCBM(XRulesNCBDataM prmXRulesNCBDataM) throws XRulesNCBException {
        double returnRows = 0;
        returnRows = updateTableXRULES_NCB(prmXRulesNCBDataM);
        if (returnRows == 0) {
            log.debug("New record then can't update record in table XRULES_NCB then call Insert method");
            createTableXRULES_NCB(prmXRulesNCBDataM);
        }

    }

    /**
     * @param prmXRulesDebBdDataM
     * @return
     */
    private double updateTableXRULES_NCB(XRulesNCBDataM prmXRulesNCBDataM) throws XRulesNCBException {
        double returnRows = 0;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("UPDATE XRULES_NCB ");
            sql.append(" SET LOAN_TYPE=?,ACCOUNT_STATUS =?,MA_DATE=?,LOAN_AMOUNT =? ,CREDIT_LIMIT=?");
            sql.append(" ,CREDIT_USE=?,INSTALLMENT_AMOUNT=? ,OS_BALANCE=?,OVERDUE_MONTHS=?,UPDATE_DATE=SYSDATE ");
            sql.append(" ,UPDATE_BY=?,COLOR=? ,SUM_ACCOUNT=? ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
            sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, prmXRulesNCBDataM.getLoanType());
            ps.setString(2, prmXRulesNCBDataM.getAccountStatus());
            ps.setDate(3, this.parseDate(prmXRulesNCBDataM.getMADate()));
            ps.setBigDecimal(4, prmXRulesNCBDataM.getLoanAmount());
            ps.setBigDecimal(5, prmXRulesNCBDataM.getCreditLimit());
            ps.setBigDecimal(6, prmXRulesNCBDataM.getCreditUse());
            ps.setBigDecimal(7, prmXRulesNCBDataM.getInstallationAmount());
            ps.setBigDecimal(8, prmXRulesNCBDataM.getOSBalance());
            ps.setString(9, prmXRulesNCBDataM.getOverDueMonth());
            ps.setString(10, prmXRulesNCBDataM.getUpdateBy());
            ps.setString(11, prmXRulesNCBDataM.getColor());
            ps.setInt(12, prmXRulesNCBDataM.getSumUnit());
            ps.setString(13, prmXRulesNCBDataM.getApplicationRecordId());
            ps.setString(14, prmXRulesNCBDataM.getCmpCode());
            ps.setString(15, prmXRulesNCBDataM.getIdNo());
            ps.setInt(16, prmXRulesNCBDataM.getSeq());*/ps.setString(1, prmXRulesNCBDataM.getLoanType());
            ps.setString(2, prmXRulesNCBDataM.getAccountStatus());
            ps.setDate(3, this.parseDate(prmXRulesNCBDataM.getMADate()));
            ps.setBigDecimal(4, prmXRulesNCBDataM.getLoanAmount());
            ps.setBigDecimal(5, prmXRulesNCBDataM.getCreditLimit());
            ps.setBigDecimal(6, prmXRulesNCBDataM.getCreditUse());
            ps.setBigDecimal(7, prmXRulesNCBDataM.getInstallationAmount());
            ps.setBigDecimal(8, prmXRulesNCBDataM.getOSBalance());
            ps.setString(9, prmXRulesNCBDataM.getOverDueMonth());
            ps.setString(10, prmXRulesNCBDataM.getUpdateBy());
            ps.setString(11, prmXRulesNCBDataM.getColor());
            ps.setInt(12, prmXRulesNCBDataM.getSumUnit());
            ps.setString(13, prmXRulesNCBDataM.getPersonalID());
            ps.setInt(14, prmXRulesNCBDataM.getSeq());
            returnRows = ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
            }
        }
        return returnRows;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesDedupDAxO#saveUpdateModelXRulesDedupM(java.util.Vector)
     */
    public void saveUpdateModelXRulesNCBM(Vector vXRulesNCBDataM) throws XRulesNCBException {

        try {
            //update data
            if (vXRulesNCBDataM != null) {
                for (int i = 0; i < vXRulesNCBDataM.size(); i++) {
                    this.saveUpdateModelXRulesNCBM((XRulesNCBDataM) vXRulesNCBDataM.get(i));
                }
            }
            //delete table
            deleteSomeTableXRULES_NCB(vXRulesNCBDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        }

    }

    /**
     * @param vXRulesNCBDataM
     */
    private void deleteSomeTableXRULES_NCB(Vector vXRulesNCBDataM) throws XRulesNCBException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            if (vXRulesNCBDataM == null) {
                log.debug("XRulesNCBDAOImpl-->deleteSomeTableXRULES_NCB vXRulesNCBDataM=null");
                return;
            }
            if (vXRulesNCBDataM.size() < 1) {
                log.debug("XRulesBlacklistDAOImpl-->deleteSomeTableXRULES_NCB vXRulesNCBDataM=0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_NCB ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
            sql.append(" WHERE PERSONAL_ID=? ");
            sql.append(" AND SEQ not in(");
            XRulesNCBDataM prmXRulesDebtDbDataM;
            prmXRulesDebtDbDataM = (XRulesNCBDataM) vXRulesNCBDataM.get(0);
            sql.append(prmXRulesDebtDbDataM.getSeq());
            for (int i = 1; i < vXRulesNCBDataM.size() && i < XRulesConstant.limitDeleteSQLParam; i++) {
                prmXRulesDebtDbDataM = (XRulesNCBDataM) vXRulesNCBDataM.get(i);
                sql.append("," + prmXRulesDebtDbDataM.getSeq());
            }
            sql.append(" )");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, prmXRulesDebtDbDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesDebtDbDataM.getCmpCode());
            ps.setString(3, prmXRulesDebtDbDataM.getIdNo());*/
            ps.setString(1, prmXRulesDebtDbDataM.getPersonalID());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesNCBException(e.getMessage());
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBDAO#deleteModelXRulesNCBM(java.lang.String,
     *      java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesNCBM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesNCBException {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
            //conn = Get Connection
            if (idNoVects == null) {
                log.debug("XRulesNCBDAOImpl-->deleteModelXRulesNCBM  idno Vects=null");
                return;
            }
            if (idNoVects.size() < 1) {
                log.debug("XRulesNCBDAOImpl-->deleteModelXRulesNCBM  Idno size =0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_NCB ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=?   ");
            sql.append(" AND IDNO not in('");           
            sql.append((String) idNoVects.get(0));
            sql.append("'");
            for (int i = 1; i < idNoVects.size() && i < XRulesConstant.limitDeleteSQLParam; i++) {
                String idNo = (String) idNoVects.get(i);
                sql.append("," + "'" +idNo+"'");
            }
            sql.append(" )");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationRecordId);
            ps.setString(2, cmpCde);
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesNCBException(e.getMessage());
            }
        }

    }

	@Override
	public Vector<PLXRulesNCBVerificationDataM> getDisplayNCB( String trackingCode, String personalId) throws XRulesNCBException {
		CallableStatement callStmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            callStmt = conn.prepareCall("{? = call pka_ncb.displayNCBAccount(?)}");
			int n = 1;
			callStmt.registerOutParameter(n++,OracleTypes.CURSOR);
			callStmt.setString(n++,trackingCode);	
			callStmt.executeQuery();			
			rs = (ResultSet)callStmt.getObject(1);	
	        Vector<PLXRulesNCBVerificationDataM> resultVt = new Vector<PLXRulesNCBVerificationDataM>();
			PLXRulesNCBVerificationDataM ncbVer = null;
			int seq = 1;
			while(rs.next()){
				ncbVer = new PLXRulesNCBVerificationDataM();
				ncbVer.setPersonalID(personalId);
				ncbVer.setSeq(seq++);
				ncbVer.setAccountType(rs.getString("account_type"));
				ncbVer.setDateOfLastPay(rs.getDate("date_of_last_pmt"));
				ncbVer.setCreditLimOriLoanAmt(rs.getDouble("creditlim_orloanamt"));
				ncbVer.setAmtOwed(rs.getDouble("amt_owed"));
				ncbVer.setAccountStatus(rs.getString("account_status"));
				ncbVer.setAsOfDate(rs.getDate("as_of_date"));
				ncbVer.setPaymentHist(convertHistoryLongtoShort(rs.getString("payment_history")));
				ncbVer.setOwnershipIndicator(rs.getString("ownership_indicator"));
				ncbVer.setMemberShortName(rs.getString("member_short_name"));
				ncbVer.setCreateBy(OrigConstant.SYSTEM);
				ncbVer.setUpdateBy(OrigConstant.SYSTEM);
				resultVt.add(ncbVer);
            }
            return resultVt;
        } catch (Exception e) {
        	if(e.getMessage().indexOf("ORA-01403") == -1){
                log.fatal("Error:", e);
                throw new XRulesNCBException(e.getMessage());
        	}else{
        		return null;
        	}
        } finally {
            try {
                closeConnection(conn, rs, callStmt);
            } catch (Exception e) {
                log.fatal("Error:", e);
            }
        }
	}
	
	public String convertHistoryLongtoShort(String longHistory)throws Exception{
		if(longHistory != null && longHistory.length() > 2){
			StringBuilder shortHistory = new StringBuilder("");
			for(int i=2;i<longHistory.length();i=i+3){
				shortHistory.append(longHistory.charAt(i));
			}
			return shortHistory.toString();
		}else{
			return "";
		}
	}
}
