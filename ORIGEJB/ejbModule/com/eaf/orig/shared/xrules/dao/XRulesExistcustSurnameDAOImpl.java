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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.xrules.dao.exception.XRulesExistcustException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesExistcustDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesExistcustDAOImpl
 */
public class XRulesExistcustSurnameDAOImpl extends OrigObjectDAO implements
        XRulesExistcustSurnameDAO {
    private static Logger log = Logger.getLogger(XRulesExistcustSurnameDAOImpl.class);

    /**
     *  
     */
    public XRulesExistcustSurnameDAOImpl() {
        super();
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustDAO#createModelXRulesExistcustM(com.eaf.xrules.shared.model.XRulesExistcustDataM)
     */
    public void createModelXRulesExistcustM(
            XRulesExistcustDataM prmXRulesExistcustDataM)
            throws XRulesExistcustException {
        try {
            createTableXRULES_EXISTCUST(prmXRulesExistcustDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustException(e.getMessage());
        }

    }

    /**
     * @param prmXRulesExistcustDataM
     */
    private void createTableXRULES_EXISTCUST(
            XRulesExistcustDataM prmXRulesExistcustDataM)
            throws XRulesExistcustException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("INSERT INTO XRULES_EXISTCUST_SURN ");
            sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,SEQ,CONTRACT_NO   ");
            sql.append("  ,COLLECTION_STATUS,CONTRACT_STATUS,CUSTOMER_TYPE,BOOKING_DATE,NET_FINANCE_AMT  ");
            sql.append("  ,OVERDUE_PERIOD,PAID_PERIOD,UNPAID_PERIOD,INSTALLMENT,UPDATE_DATE ");
            sql.append("  ,UPDATE_BY,ORI_FINANCE_AMT,TITCDE,THNAME,THSURN  )");*/
            sql.append("INSERT INTO XRULES_EXISTCUST_SURN ");
            sql.append("( PERSONAL_ID,SEQ,CONTRACT_NO   ");
            sql.append("  ,COLLECTION_STATUS,CONTRACT_STATUS,CUSTOMER_TYPE,BOOKING_DATE,NET_FINANCE_AMT  ");
            sql.append("  ,OVERDUE_PERIOD,PAID_PERIOD,UNPAID_PERIOD,INSTALLMENT,UPDATE_DATE ");
            sql.append("  ,UPDATE_BY,ORI_FINANCE_AMT,TITCDE,THNAME,THSURN  )");
            
            sql.append(" VALUES(?,?,?   ,?,?,?,?,?  ,?,?,?,?,SYSDATE, ?,?,?,?,?) ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);

            /*ps.setString(1, prmXRulesExistcustDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesExistcustDataM.getCmpCde());
            ps.setString(3, prmXRulesExistcustDataM.getIdNo());
            ps.setInt(4, prmXRulesExistcustDataM.getSeq());
            ps.setString(5, prmXRulesExistcustDataM.getContractNo());
            ps.setString(6, prmXRulesExistcustDataM.getCollectionStatus());            
            ps.setString(7, prmXRulesExistcustDataM.getContractStatus());
            ps.setString(8, prmXRulesExistcustDataM.getCustomerType());
            ps.setDate(9, this.parseDate(prmXRulesExistcustDataM
                    .getBookingDate()));
            ps.setBigDecimal(10, prmXRulesExistcustDataM.getNetFinanceAmount());
            ps.setInt(11, prmXRulesExistcustDataM.getOverDuePeriod());
            ps.setInt(12, prmXRulesExistcustDataM.getPaidPeriod());
            ps.setInt(13, prmXRulesExistcustDataM.getUnPaidPeriod());
            ps.setBigDecimal(14, prmXRulesExistcustDataM.getInstallment());
            ps.setString(15, prmXRulesExistcustDataM.getUpdateBy());
            ps.setBigDecimal(16,prmXRulesExistcustDataM.getOriginalFinaceAmount());
            ps.setString(17,prmXRulesExistcustDataM.getTitleCode());
            ps.setString(18,prmXRulesExistcustDataM.getFirstName());
            ps.setString(19,prmXRulesExistcustDataM.getLastName());*/
            ps.setString(1, prmXRulesExistcustDataM.getPersonalID());
            ps.setInt(2, prmXRulesExistcustDataM.getSeq());
            ps.setString(3, prmXRulesExistcustDataM.getContractNo());
            ps.setString(4, prmXRulesExistcustDataM.getCollectionStatus());            
            ps.setString(5, prmXRulesExistcustDataM.getContractStatus());
            ps.setString(6, prmXRulesExistcustDataM.getCustomerType());
            ps.setDate(7, this.parseDate(prmXRulesExistcustDataM.getBookingDate()));
            ps.setBigDecimal(8, prmXRulesExistcustDataM.getNetFinanceAmount());
            ps.setInt(9, prmXRulesExistcustDataM.getOverDuePeriod());
            ps.setInt(10, prmXRulesExistcustDataM.getPaidPeriod());
            ps.setInt(11, prmXRulesExistcustDataM.getUnPaidPeriod());
            ps.setBigDecimal(12, prmXRulesExistcustDataM.getInstallment());
            ps.setString(13, prmXRulesExistcustDataM.getUpdateBy());
            ps.setBigDecimal(14,prmXRulesExistcustDataM.getOriginalFinaceAmount());
            ps.setString(15,prmXRulesExistcustDataM.getTitleCode());
            ps.setString(16,prmXRulesExistcustDataM.getFirstName());
            ps.setString(17,prmXRulesExistcustDataM.getLastName());
            ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustDAO#deleteModelXRulesExistcustM(com.eaf.xrules.shared.model.XRulesExistcustDataM)
     */
    public void deleteModelXRulesExistcustM(
            XRulesExistcustDataM prmXRulesExistcustDataM)
            throws XRulesExistcustException {
        try {
            deleteTableXRULES_EXISTCUST(prmXRulesExistcustDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustException(e.getMessage());
        }

    }

    /**
     * @param XRulesDebBdDataM
     */
    private void deleteTableXRULES_EXISTCUST(
            XRulesExistcustDataM prmXRulesExistcustDataM)
            throws XRulesExistcustException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_EXISTCUST_SURN ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? AND SEQ=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prmXRulesExistcustDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesExistcustDataM.getPersonalID());
            ps.setInt(3, prmXRulesExistcustDataM.getSeq());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesExistcustException(e.getMessage());
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustDAO#loadModelXRulesExistcustM(java.lang.String)
     */
    public Vector loadModelXRulesExistcustM(String personalID) throws XRulesExistcustException {
        try {
            Vector vResult = selectTableXRULES_EXISTCUST(personalID);
            return vResult;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustException(e.getMessage());
        }
    }

    /**
     * @param applicationRecordId
     * @return
     */
    private Vector selectTableXRULES_EXISTCUST(String personalID) throws XRulesExistcustException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT  PERSONAL_ID,SEQ,CONTRACT_NO,COLLECTION_STATUS ");
            sql.append(" ,CONTRACT_STATUS,CUSTOMER_TYPE,BOOKING_DATE,NET_FINANCE_AMT,OVERDUE_PERIOD  ");
            sql.append(" ,PAID_PERIOD,UNPAID_PERIOD,INSTALLMENT,UPDATE_DATE,UPDATE_BY,ORI_FINANCE_AMT ");
            sql.append(" ,TITCDE,THNAME,THSURN");
            sql.append(" FROM XRULES_EXISTCUST_SURN WHERE PERSONAL_ID=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            rs = null;
            ps.setString(1, personalID);
            rs = ps.executeQuery();
            Vector vt = new Vector();
            while (rs.next()) {
                XRulesExistcustDataM prmXRulesExistcustDataM = new XRulesExistcustDataM();
                prmXRulesExistcustDataM.setPersonalID(rs.getString(1));
                prmXRulesExistcustDataM.setSeq(rs.getInt(2));
                prmXRulesExistcustDataM.setContractNo(rs.getString(3));
                prmXRulesExistcustDataM.setCollectionStatus(rs.getString(4));
                prmXRulesExistcustDataM.setContractStatus(rs.getString(5));
                prmXRulesExistcustDataM.setCustomerType(rs.getString(6));
                prmXRulesExistcustDataM.setBookingDate(rs.getDate(7));
                prmXRulesExistcustDataM.setNetFinanceAmount(rs.getBigDecimal(8));
                prmXRulesExistcustDataM.setOverDuePeriod(rs.getInt(9));
                prmXRulesExistcustDataM.setPaidPeriod(rs.getInt(10));
                prmXRulesExistcustDataM.setUnPaidPeriod(rs.getInt(11));
                prmXRulesExistcustDataM.setInstallment(rs.getBigDecimal(12));
                prmXRulesExistcustDataM.setUpdateDate(rs.getTimestamp(13));
                prmXRulesExistcustDataM.setUpdateBy(rs.getString(14));
                prmXRulesExistcustDataM.setOriginalFinaceAmount(rs.getBigDecimal(15));
                prmXRulesExistcustDataM.setTitleCode(rs.getString(16));
                prmXRulesExistcustDataM.setFirstName(rs.getString(17));
                prmXRulesExistcustDataM.setLastName(rs.getString(18));
                vt.add(prmXRulesExistcustDataM);
            }
            return vt;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustDAO#saveUpdateModelXRulesExistcustM(com.eaf.xrules.shared.model.XRulesExistcustDataM)
     */
    public void saveUpdateModelXRulesExistcustM(
            XRulesExistcustDataM prmXRulesExistcustDataM)
            throws XRulesExistcustException {
        double returnRows = 0;
        returnRows = updateTableXRULES_EXISTCUST(prmXRulesExistcustDataM);
        if (returnRows == 0) {
            log
                    .debug("New record then can't update record in table XRULES_EXISTCUST then call Insert method");
            createTableXRULES_EXISTCUST(prmXRulesExistcustDataM);
        }

    }

    /**
     * @param XRulesDebBdDataM
     * @return
     */
    private double updateTableXRULES_EXISTCUST(
            XRulesExistcustDataM prmXRulesExistcustDataM)
            throws XRulesExistcustException {
        double returnRows = 0;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("UPDATE XRULES_EXISTCUST_SURN ");
            sql.append(" SET   CONTRACT_NO=? ,COLLECTION_STATUS=?  , CONTRACT_STATUS=?,CUSTOMER_TYPE =?,BOOKING_DATE=? ");
            sql.append(" ,NET_FINANCE_AMT=?,OVERDUE_PERIOD=?,PAID_PERIOD=?,UNPAID_PERIOD=? ,INSTALLMENT=?");
            sql.append("  ,UPDATE_DATE=SYSDATE,UPDATE_BY=?,ORI_FINANCE_AMT=? ");
            sql.append(" ,TITCDE=?,THNAME=?,THSURN=? ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");*/
            sql.append("UPDATE XRULES_EXISTCUST_SURN ");
            sql.append(" SET   CONTRACT_NO=? ,COLLECTION_STATUS=?  , CONTRACT_STATUS=?,CUSTOMER_TYPE =?,BOOKING_DATE=? ");
            sql.append(" ,NET_FINANCE_AMT=?,OVERDUE_PERIOD=?,PAID_PERIOD=?,UNPAID_PERIOD=? ,INSTALLMENT=?");
            sql.append("  ,UPDATE_DATE=SYSDATE,UPDATE_BY=?,ORI_FINANCE_AMT=? ");
            sql.append(" ,TITCDE=?,THNAME=?,THSURN=? ");
            sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
            
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prmXRulesExistcustDataM.getContractNo());
            ps.setString(2, prmXRulesExistcustDataM.getCollectionStatus());             
            ps.setString(3, prmXRulesExistcustDataM.getContractStatus());
            ps.setString(4, prmXRulesExistcustDataM.getCustomerType());
            ps.setDate(5, this.parseDate(prmXRulesExistcustDataM.getBookingDate()));
            ps.setBigDecimal(6, prmXRulesExistcustDataM.getNetFinanceAmount());
            ps.setInt(7, prmXRulesExistcustDataM.getOverDuePeriod());
            ps.setInt(8, prmXRulesExistcustDataM.getPaidPeriod());
            ps.setInt(9, prmXRulesExistcustDataM.getUnPaidPeriod());
            ps.setBigDecimal(10, prmXRulesExistcustDataM.getInstallment());
            ps.setString(11, prmXRulesExistcustDataM.getUpdateBy());
            ps.setBigDecimal(12, prmXRulesExistcustDataM.getOriginalFinaceAmount());
            ps.setString(13,prmXRulesExistcustDataM.getTitleCode());
            ps.setString(14,prmXRulesExistcustDataM.getFirstName());
            ps.setString(15,prmXRulesExistcustDataM.getLastName());
            ps.setString(16, prmXRulesExistcustDataM.getPersonalID());
            ps.setInt(17, prmXRulesExistcustDataM.getSeq());
            returnRows = ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistDAO#saveUpdateModelXRulesBlacklistM(com.eaf.xrules.shared.model.XRulesBlacklistDataM)
     */
    public void saveUpdateModelXRulesExistcustM(Vector vXRulesExistcustDataM)
            throws XRulesExistcustException {

        try {
            //update data
            if (vXRulesExistcustDataM != null) {
                for (int i = 0; i < vXRulesExistcustDataM.size(); i++) {
                    this.saveUpdateModelXRulesExistcustM((XRulesExistcustDataM) vXRulesExistcustDataM.get(i));
                }
            }
            //delete table
            deleteSomeTableXRULES_EXISTCUST(vXRulesExistcustDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustException(e.getMessage());
        }

    }

    /**
     * @param rulesBlacklistDataM
     */
    private void deleteSomeTableXRULES_EXISTCUST(Vector vXRulesExistcustDataM)
            throws XRulesExistcustException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            if (vXRulesExistcustDataM == null) {
                log.debug("XRulesExistcustDAOImpl-->deleteSomeTableXRULES_EXISTCUST vXRulesDebtDbDataM=null");
                return;
            }
            if (vXRulesExistcustDataM.size() < 1) {
                log.debug("XRulesExistcustDAOImpl-->deleteSomeTableXRULES_EXISTCUST vXRulesDebtDbDataM=0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_EXISTCUST_SURN ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
            sql.append(" WHERE PERSONAL_ID=? "); 
            sql.append(" AND SEQ not in(");
            XRulesExistcustDataM prmXRulesDebtDbDataM;
            prmXRulesDebtDbDataM = (XRulesExistcustDataM) vXRulesExistcustDataM.get(0);
            sql.append(prmXRulesDebtDbDataM.getSeq());
            for (int i = 1; i < vXRulesExistcustDataM.size()
                    && i < XRulesConstant.limitDeleteSQLParam; i++) {
                prmXRulesDebtDbDataM = (XRulesExistcustDataM) vXRulesExistcustDataM.get(i);
                sql.append("," + prmXRulesDebtDbDataM.getSeq());
            }
            sql.append(" )");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, prmXRulesDebtDbDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesDebtDbDataM.getCmpCde());
            ps.setString(3, prmXRulesDebtDbDataM.getIdNo());*/
            ps.setString(1, prmXRulesDebtDbDataM.getPersonalID());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesExistcustException(e.getMessage());
            }
        }

    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustDAO#deleteModelXRulesExistcustM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesExistcustM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesExistcustException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			if (idNoVects == null) {
				log
						.debug("XRulesExistcustDAOImpl-->deleteModelXRulesExistcustM  idno Vects=null");
				return;
			}
			if (idNoVects.size() < 1) {
				log
						.debug("XRulesExistcustDAOImpl-->deleteModelXRulesExistcustM  Idno size =0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_EXISTCUST_SURN ");
			sql
					.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=?   ");
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
			log.fatal("Error:",e);
			throw new XRulesExistcustException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesExistcustException(e.getMessage());
			}
		}

    }
}
