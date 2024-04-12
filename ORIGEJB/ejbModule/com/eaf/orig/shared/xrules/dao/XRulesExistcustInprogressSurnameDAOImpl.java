/*
 * Created on Dec 20, 2007
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
import com.eaf.orig.shared.xrules.dao.exception.XRulesExistcustInprogressException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesExistcustDataM;
import com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesExistcustInprogressDAOImpl
 */
public class XRulesExistcustInprogressSurnameDAOImpl extends OrigObjectDAO implements
        XRulesExistcustInprogressSurnameDAO {
    private static Logger log = Logger
            .getLogger(XRulesExistcustInprogressSurnameDAOImpl.class);

    /**
     *  
     */
    public XRulesExistcustInprogressSurnameDAOImpl() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressDAO#createModelXRulesExistcustInprogressM(com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM)
     */
    public void createModelXRulesExistcustInprogressM(
            XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM)
            throws XRulesExistcustInprogressException {
        try {
            createTableXRULES_EXISTCUST_INPROGRESS(prmXRulesExistcustInprogressDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustInprogressException(e.getMessage());
        }

    }

    /**
     * @param prmXRulesExistcustInprogressDataM
     */
    private void createTableXRULES_EXISTCUST_INPROGRESS(
            XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM)
            throws XRulesExistcustInprogressException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("INSERT INTO XRULES_EXISTCUST_INP_SURN ");
            /*sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,SEQ,APPLICATION_NO   ");
            sql.append("  ,CUSTOMER_TYPE ,APPLICATION_DATE  ,FINANCE_AMT,APPLICATION_STATUS,UPDATE_DATE ");
            sql.append(" ,UPDATE_BY ,INSTALLMENT ,COBORROWER_FLAG,TITCDE,THNAME,THSURN )");*/
            sql.append("( PERSONAL_ID,SEQ,APPLICATION_NO   ");
            sql.append("  ,CUSTOMER_TYPE ,APPLICATION_DATE  ,FINANCE_AMT,APPLICATION_STATUS,UPDATE_DATE ");
            sql.append(" ,UPDATE_BY ,INSTALLMENT ,COBORROWER_FLAG,TITCDE,THNAME,THSURN )");
            sql.append(" VALUES(?,?,?  ,?,?,?,?,SYSDATE ,?,?,?,?,? ,?) ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);

            /*ps.setString(1, prmXRulesExistcustInprogressDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesExistcustInprogressDataM.getCmpCde());
            ps.setString(3, prmXRulesExistcustInprogressDataM.getIdNo());
            ps.setInt(4, prmXRulesExistcustInprogressDataM.getSeq());
            ps.setString(5, prmXRulesExistcustInprogressDataM.getApplicationNo());
            ps.setString(6, prmXRulesExistcustInprogressDataM.getCustomerType());
            ps.setDate(7, this.parseDate(prmXRulesExistcustInprogressDataM.getApplicationDate()));
            ps.setBigDecimal(8, prmXRulesExistcustInprogressDataM.getFinanceAmt());
            ps.setString(9, prmXRulesExistcustInprogressDataM.getApplicationStatus());
            ps.setString(10, prmXRulesExistcustInprogressDataM.getUpdateBy());
            ps.setBigDecimal(11,prmXRulesExistcustInprogressDataM.getInstallment());
            ps.setString(12,prmXRulesExistcustInprogressDataM.getCoBorrowerFlag());
            ps.setString(13,prmXRulesExistcustInprogressDataM.getTitleCode());
            ps.setString(14,prmXRulesExistcustInprogressDataM.getFirstName());
            ps.setString(15,prmXRulesExistcustInprogressDataM.getLastName());*/
            ps.setString(1, prmXRulesExistcustInprogressDataM.getPersonalID());
            ps.setInt(2, prmXRulesExistcustInprogressDataM.getSeq());
            ps.setString(3, prmXRulesExistcustInprogressDataM.getApplicationNo());
            
            ps.setString(4, prmXRulesExistcustInprogressDataM.getCustomerType());
            ps.setDate(5, this.parseDate(prmXRulesExistcustInprogressDataM.getApplicationDate()));
            ps.setBigDecimal(6, prmXRulesExistcustInprogressDataM.getFinanceAmt());
            ps.setString(7, prmXRulesExistcustInprogressDataM.getApplicationStatus());

            ps.setString(8, prmXRulesExistcustInprogressDataM.getUpdateBy());
            ps.setBigDecimal(9,prmXRulesExistcustInprogressDataM.getInstallment());
            ps.setString(10,prmXRulesExistcustInprogressDataM.getCoBorrowerFlag());
            ps.setString(11,prmXRulesExistcustInprogressDataM.getTitleCode());
            ps.setString(12,prmXRulesExistcustInprogressDataM.getFirstName());
            
            ps.setString(13,prmXRulesExistcustInprogressDataM.getLastName());
            ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustInprogressException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressDAO#deleteModelXRulesExistcustInprogressM(com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM)
     */
    public void deleteModelXRulesExistcustInprogressM(
            XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM)
            throws XRulesExistcustInprogressException {
        try {
            deleteTableXRULES_EXISTCUST_INPROGRESS(prmXRulesExistcustInprogressDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustInprogressException(e.getMessage());
        }

    }

    /**
     * @param prmXRulesExistcustInprogressDataM
     */
    private void deleteTableXRULES_EXISTCUST_INPROGRESS(
            XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM)
            throws XRulesExistcustInprogressException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_EXISTCUST_INP_SURN ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? AND SEQ=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, prmXRulesExistcustInprogressDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesExistcustInprogressDataM.getCmpCde());
            ps.setString(3, prmXRulesExistcustInprogressDataM.getIdNo());
            ps.setInt(4, prmXRulesExistcustInprogressDataM.getSeq());*/
            ps.setString(1, prmXRulesExistcustInprogressDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesExistcustInprogressDataM.getPersonalID());
            ps.setInt(3, prmXRulesExistcustInprogressDataM.getSeq());
            ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustInprogressException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesExistcustInprogressException(e.getMessage());
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressDAO#loadModelXRulesExistcustInprogressM(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public Vector loadModelXRulesExistcustInprogressM(String personalID)
            throws XRulesExistcustInprogressException {
        try {
            Vector vResult = selectTableXRULES_EXISTCUST_INPROGRESS(personalID);
            return vResult;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustInprogressException(e.getMessage());
        }
    }

    /**
     * @param applicationRecordId
     * @param cmpcde
     * @param idno
     * @return
     */
    private Vector selectTableXRULES_EXISTCUST_INPROGRESS(String personalID)
            throws XRulesExistcustInprogressException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT  PERSONAL_ID,SEQ,APPLICATION_NO,CUSTOMER_TYPE   ");
            sql.append("  ,APPLICATION_DATE ,FINANCE_AMT,APPLICATION_STATUS,UPDATE_DATE ,UPDATE_BY ,INSTALLMENT,COBORROWER_FLAG ");      
            sql.append(",TITCDE,THNAME,THSURN");
            sql.append(" FROM XRULES_EXISTCUST_INP_SURN WHERE PERSONAL_ID=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            rs = null;
            ps.setString(1, personalID);
            rs = ps.executeQuery();
            Vector vt = new Vector();
            while (rs.next()) {
                XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM = new XRulesExistcustInprogressDataM();
                prmXRulesExistcustInprogressDataM.setPersonalID(rs.getString(1));
                prmXRulesExistcustInprogressDataM.setSeq(rs.getInt(2));
                prmXRulesExistcustInprogressDataM.setApplicationNo(rs.getString(3));
                prmXRulesExistcustInprogressDataM.setCustomerType(rs.getString(4));
                prmXRulesExistcustInprogressDataM.setApplicationDate(rs.getDate(5));
                prmXRulesExistcustInprogressDataM.setFinanceAmt(rs.getBigDecimal(6));
                prmXRulesExistcustInprogressDataM.setApplicationStatus(rs.getString(7));
                prmXRulesExistcustInprogressDataM.setUpdateDate(rs.getTimestamp(8));
                prmXRulesExistcustInprogressDataM.setUpdateBy(rs.getString(9));
                prmXRulesExistcustInprogressDataM.setInstallment(rs.getBigDecimal(10));
                prmXRulesExistcustInprogressDataM.setCoBorrowerFlag(rs.getString(11));
                prmXRulesExistcustInprogressDataM.setTitleCode(rs.getString(12));
                prmXRulesExistcustInprogressDataM.setFirstName(rs.getString(13));
                prmXRulesExistcustInprogressDataM.setLastName(rs.getString(14));
                vt.add(prmXRulesExistcustInprogressDataM);                
            }
            return vt;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustInprogressException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressDAO#saveUpdateModelXRulesExistcustInprogressM(com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM)
     */
    public void saveUpdateModelXRulesExistcustInprogressM(
            XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM)
            throws XRulesExistcustInprogressException {
        double returnRows = 0;
        returnRows = updateTableXRULES_EXISTCUST_INPROGRESS(prmXRulesExistcustInprogressDataM);
        if (returnRows == 0) {
            log.debug("New record then can't update record in table XRULES_EXISTCUST then call Insert method");
            createTableXRULES_EXISTCUST_INPROGRESS(prmXRulesExistcustInprogressDataM);
        }

    }

    /**
     * @param prmXRulesExistcustInprogressDataM
     * @return
     */
    private double updateTableXRULES_EXISTCUST_INPROGRESS(
            XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM)
            throws XRulesExistcustInprogressException {
        double returnRows = 0;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("UPDATE XRULES_EXISTCUST_INP_SURN ");
            sql.append(" SET  APPLICATION_NO=?,CUSTOMER_TYPE=?,APPLICATION_DATE=?  ,FINANCE_AMT=?,APPLICATION_STATUS=?   ");
            sql.append("  ,UPDATE_DATE=SYSDATE,UPDATE_BY=?,INSTALLMENT=?,COBORROWER_FLAG=? ");
            sql.append(" ,TITCDE=?,THNAME=?,THSURN=? ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
            sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);

            /*ps.setString(1, prmXRulesExistcustInprogressDataM.getApplicationNo());
            ps.setString(2, prmXRulesExistcustInprogressDataM.getCustomerType());
            ps.setDate(3, this.parseDate(prmXRulesExistcustInprogressDataM.getApplicationDate()));
            ps.setBigDecimal(4, prmXRulesExistcustInprogressDataM.getFinanceAmt());
            ps.setString(5, prmXRulesExistcustInprogressDataM.getApplicationStatus());
            ps.setString(6, prmXRulesExistcustInprogressDataM.getUpdateBy());
            ps.setBigDecimal(7,prmXRulesExistcustInprogressDataM.getInstallment());
            ps.setString(8,prmXRulesExistcustInprogressDataM.getCoBorrowerFlag());
            ps.setString(9,prmXRulesExistcustInprogressDataM.getTitleCode());
            ps.setString(10,prmXRulesExistcustInprogressDataM.getFirstName());
            ps.setString(11,prmXRulesExistcustInprogressDataM.getLastName());
            ps.setString(12, prmXRulesExistcustInprogressDataM.getApplicationRecordId());
            ps.setString(13, prmXRulesExistcustInprogressDataM.getCmpCde());
            ps.setString(14, prmXRulesExistcustInprogressDataM.getIdNo());
            ps.setInt(15, prmXRulesExistcustInprogressDataM.getSeq());*/
            
            ps.setString(1, prmXRulesExistcustInprogressDataM.getApplicationNo());
            ps.setString(2, prmXRulesExistcustInprogressDataM.getCustomerType());
            ps.setDate(3, this.parseDate(prmXRulesExistcustInprogressDataM.getApplicationDate()));
            ps.setBigDecimal(4, prmXRulesExistcustInprogressDataM.getFinanceAmt());
            ps.setString(5, prmXRulesExistcustInprogressDataM.getApplicationStatus());
            ps.setString(6, prmXRulesExistcustInprogressDataM.getUpdateBy());
            ps.setBigDecimal(7,prmXRulesExistcustInprogressDataM.getInstallment());
            ps.setString(8,prmXRulesExistcustInprogressDataM.getCoBorrowerFlag());
            ps.setString(9,prmXRulesExistcustInprogressDataM.getTitleCode());
            ps.setString(10,prmXRulesExistcustInprogressDataM.getFirstName());
            ps.setString(11,prmXRulesExistcustInprogressDataM.getLastName());
            ps.setString(12, prmXRulesExistcustInprogressDataM.getPersonalID());
            ps.setInt(13, prmXRulesExistcustInprogressDataM.getSeq());
            returnRows = ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustInprogressException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressDAO#saveUpdateModelXRulesExistcustInprogressM(java.util.Vector)
     */
    public void saveUpdateModelXRulesExistcustInprogressM(
            Vector vXRulesExistcustInprogressDataM)
            throws XRulesExistcustInprogressException {

        try {
            //update data
            if (vXRulesExistcustInprogressDataM != null) {
                for (int i = 0; i < vXRulesExistcustInprogressDataM.size(); i++) {
                    this.saveUpdateModelXRulesExistcustInprogressM((XRulesExistcustInprogressDataM) vXRulesExistcustInprogressDataM.get(i));
                }
            }
            //delete table
            deleteSomeTableXRULES_EXISTCUST_INPROGRESS(vXRulesExistcustInprogressDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesExistcustInprogressException(e.getMessage());
        }

    }

    /**
     * @param rulesExistcustInprogressDataM
     */
    private void deleteSomeTableXRULES_EXISTCUST_INPROGRESS(
            Vector vXRulesExistcustInprogressDataM)
            throws XRulesExistcustInprogressException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            if (vXRulesExistcustInprogressDataM == null) {
                log.debug("XRulesExistcustDAOImpl-->deleteSomeTableXRULES_EXISTCUST_INPROGRESS vXRulesExistcustInprogressDataM=null");
                return;
            }
            if (vXRulesExistcustInprogressDataM.size() < 1) {
                log.debug("XRulesExistcustDAOImpl-->deleteSomeTableXRULES_EXISTCUST_INPROGRESS vXRulesExistcustInprogressDataM=0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_EXISTCUST_INP_SURN ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
            sql.append(" WHERE PERSONAL_ID=? ");
            sql.append(" AND SEQ not in(");
            XRulesExistcustDataM prmXRulesDebtDbDataM;
            prmXRulesDebtDbDataM = (XRulesExistcustDataM) vXRulesExistcustInprogressDataM.get(0);
            sql.append(prmXRulesDebtDbDataM.getSeq());
            for (int i = 1; i < vXRulesExistcustInprogressDataM.size()
                    && i < XRulesConstant.limitDeleteSQLParam; i++) {
                prmXRulesDebtDbDataM = (XRulesExistcustDataM) vXRulesExistcustInprogressDataM.get(i);
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
            throw new XRulesExistcustInprogressException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesExistcustInprogressException(e.getMessage());
            }
        }

    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressDAO#deleteModelXRulesExistcustInprogressM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesExistcustInprogressM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesExistcustInprogressException {
    	Connection conn = null;
        PreparedStatement ps = null;
		try {
			//conn = Get Connection
			if (idNoVects == null) {
				log
						.debug("XRulesExistcustInprogressDAOImpl-->deleteModelXRulesExistcustInprogressM  idno Vects=null");
				return;
			}
			if (idNoVects.size() < 1) {
				log
						.debug("XRulesExistcustInprogressDAOImpl-->deleteModelXRulesExistcustInprogressM  Idno size =0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_EXISTCUST_INP_SURN ");
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
			throw new XRulesExistcustInprogressException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesExistcustInprogressException(e.getMessage());
			}
		}

    }
}
