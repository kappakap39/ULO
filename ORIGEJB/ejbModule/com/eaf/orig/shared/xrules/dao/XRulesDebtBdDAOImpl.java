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
import com.eaf.orig.shared.xrules.dao.exception.XRulesDebtDdException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesDebtBdDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesDebtBdDAOImpl
 */
public class XRulesDebtBdDAOImpl extends OrigObjectDAO implements
        XRulesDebtBdDAO {
    private static Logger log = Logger.getLogger(XRulesDebtBdDAOImpl.class);

    /**
     *  
     */
    public XRulesDebtBdDAOImpl() {
        super();
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesDebtBdDAO#createModelXRulesDebBdM(com.eaf.xrules.shared.model.XRulesDebBdDataM)
     */
    public void createModelXRulesDebBdM(XRulesDebtBdDataM prmXRulesDebBdDataM)
            throws XRulesDebtDdException {
        try {
            createTableXRULES_DEBT_BD(prmXRulesDebBdDataM);
        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesDebtDdException(e.getMessage());
        }

    }

    /**
     * @param prmXRulesDebBdDataM
     */
    private void createTableXRULES_DEBT_BD(XRulesDebtBdDataM prmXRulesDebBdDataM)
            throws XRulesDebtDdException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("INSERT INTO XRULES_DEBT_BD ");
            sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,USE_FLAG,UPDATE_DATE  ");
            sql.append("  ,BURDENT,DEBT_BURDENT_SCORE,INCOME,UPDATE_BY,BURDENT_ADJUST,DEBT_BURDENT_SCORE_ADJUST )");*/
            sql.append("INSERT INTO XRULES_DEBT_BD ");
            sql.append("( PERSONAL_ID,USE_FLAG,UPDATE_DATE  ");
            sql.append("  ,BURDENT,DEBT_BURDENT_SCORE,INCOME,UPDATE_BY,BURDENT_ADJUST,DEBT_BURDENT_SCORE_ADJUST )");

            sql.append(" VALUES(?,?,SYSDATE  ,?,?,?,?,? ,? ) ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);

            /*ps.setString(1, prmXRulesDebBdDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesDebBdDataM.getCmpCode());
            ps.setString(3, prmXRulesDebBdDataM.getIdNo());
            ps.setString(4, prmXRulesDebBdDataM.getUseFlag());
            ps.setBigDecimal(5, prmXRulesDebBdDataM.getBurden());
            ps.setBigDecimal(6, prmXRulesDebBdDataM.getDebtBurdentScore());
            ps.setBigDecimal(7, prmXRulesDebBdDataM.getIncome());
            ps.setString(8, prmXRulesDebBdDataM.getUpdateBy());
            ps.setBigDecimal(9,prmXRulesDebBdDataM.getBurdenAdjust());
            ps.setBigDecimal(10,prmXRulesDebBdDataM.getDebtBurdentScoreAdjust());*/
            
            ps.setString(1, prmXRulesDebBdDataM.getPersonalID());
            ps.setString(2, prmXRulesDebBdDataM.getUseFlag());
            ps.setBigDecimal(3, prmXRulesDebBdDataM.getBurden());
            ps.setBigDecimal(4, prmXRulesDebBdDataM.getDebtBurdentScore());
            ps.setBigDecimal(5, prmXRulesDebBdDataM.getIncome());
            ps.setString(6, prmXRulesDebBdDataM.getUpdateBy());
            ps.setBigDecimal(7,prmXRulesDebBdDataM.getBurdenAdjust());
            ps.setBigDecimal(8,prmXRulesDebBdDataM.getDebtBurdentScoreAdjust());
            ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesDebtDdException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:",e);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesDebtBdDAO#deleteModelXRulesDebBdM(com.eaf.xrules.shared.model.XRulesDebBdDataM)
     */
    public void deleteModelXRulesDebBdM(XRulesDebtBdDataM prmXRulesDebBdDataM)
            throws XRulesDebtDdException {
        try {
            deleteTableXRULES_DEBT_BD(prmXRulesDebBdDataM);
        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesDebtDdException(e.getMessage());
        }

    }

    /**
     * @param XRulesDebBdDataM
     */
    private void deleteTableXRULES_DEBT_BD(XRulesDebtBdDataM XRulesDebBdDataM)
            throws XRulesDebtDdException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_DEBT_BD ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, XRulesDebBdDataM.getApplicationRecordId());
            ps.setString(2, XRulesDebBdDataM.getCmpCode());
            ps.setString(3, XRulesDebBdDataM.getIdNo());*/
            ps.setString(1, XRulesDebBdDataM.getApplicationRecordId());
            ps.setString(2, XRulesDebBdDataM.getPersonalID());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesDebtDdException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:",e);
                throw new XRulesDebtDdException(e.getMessage());
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesDebtBdDAO#loadModelXRulesDebBdM(java.lang.String)
     */
    /*
     * public Vector loadModelXRulesDebBdM(String applicationRecordId) throws
     * XRulesDebtDdException { try { Vector vResult =
     * selectTableXRULES_DEBT_BD(applicationRecordId); return vResult; } catch
     * (Exception e) { log.fatal("Error:",e); throw new
     * XRulesDebtDdException(e.getMessage()); } }
     */

    public XRulesDebtBdDataM loadModelXRulesDebBdM(String personalID)
            throws XRulesDebtDdException {
        try {
            XRulesDebtBdDataM result = selectTableXRULES_DEBT_BD(personalID);
            return result;
        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesDebtDdException(e.getMessage());
        }
    }

    /**
     * @param applicationRecordId
     * @return
     */
    private XRulesDebtBdDataM selectTableXRULES_DEBT_BD(String personalID) throws XRulesDebtDdException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("SELECT  CMPCDE,IDNO,USE_FLAG,UPDATE_DATE,UPDATE_BY ");
            sql.append(" ,BURDENT,DEBT_BURDENT_SCORE,INCOME,BURDENT_ADJUST,DEBT_BURDENT_SCORE_ADJUST  ");
            sql.append(" FROM XRULES_DEBT_BD WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO =? ");*/
            sql.append("SELECT  PERSONAL_ID,USE_FLAG,UPDATE_DATE,UPDATE_BY ");
            sql.append(" ,BURDENT,DEBT_BURDENT_SCORE,INCOME,BURDENT_ADJUST,DEBT_BURDENT_SCORE_ADJUST  ");
            sql.append(" FROM XRULES_DEBT_BD WHERE PERSONAL_ID=? ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            rs = null;
            ps.setString(1, personalID);
            rs = ps.executeQuery();            
            XRulesDebtBdDataM prmXRulesDebtBdDataM = null;
            if (rs.next()) {
                prmXRulesDebtBdDataM = new XRulesDebtBdDataM();
                prmXRulesDebtBdDataM.setPersonalID(rs.getString(1));
                prmXRulesDebtBdDataM.setUseFlag(rs.getString(2));
                prmXRulesDebtBdDataM.setUpdateDate(rs.getTimestamp(3));
                prmXRulesDebtBdDataM.setUpdateBy(rs.getString(4));
                prmXRulesDebtBdDataM.setBurden(rs.getBigDecimal(5));
                prmXRulesDebtBdDataM.setDebtBurdentScore(rs.getBigDecimal(6));
                prmXRulesDebtBdDataM.setIncome(rs.getBigDecimal(7));
                prmXRulesDebtBdDataM.setBurdenAdjust(rs.getBigDecimal(8));
                prmXRulesDebtBdDataM.setDebtBurdentScoreAdjust(rs.getBigDecimal(9));
            }
            return prmXRulesDebtBdDataM;
        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesDebtDdException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, rs, ps);
            } catch (Exception e) {
                log.fatal("Error:",e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesDebtBdDAO#saveUpdateModelXRulesDebBdM(com.eaf.xrules.shared.model.XRulesDebBdDataM)
     */
    public void saveUpdateModelXRulesDebBdM(
            XRulesDebtBdDataM prmXRulesDebBdDataM) throws XRulesDebtDdException {
        double returnRows = 0;
        returnRows = updateTableXRULES_DEBT_BD(prmXRulesDebBdDataM);
        if (returnRows == 0) {
            log
                    .debug("New record then can't update record in table XRULES_DEBT_BD then call Insert method");
            createTableXRULES_DEBT_BD(prmXRulesDebBdDataM);
        }

    }

    /**
     * @param XRulesDebBdDataM
     * @return
     */
    private double updateTableXRULES_DEBT_BD(
            XRulesDebtBdDataM prmXRulesDebBdDataM) throws XRulesDebtDdException {
        double returnRows = 0;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("UPDATE XRULES_DEBT_BD ");
            sql.append(" SET  USE_FLAG=?,BURDENT=?,DEBT_BURDENT_SCORE=?,INCOME=?,UPDATE_DATE=SYSDATE ,UPDATE_BY=? ");
            sql.append(" ,BURDENT_ADJUST=?,DEBT_BURDENT_SCORE_ADJUST=? ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=?  ");*/
            sql.append("UPDATE XRULES_DEBT_BD ");
            sql.append(" SET  USE_FLAG=?,BURDENT=?,DEBT_BURDENT_SCORE=?,INCOME=?,UPDATE_DATE=SYSDATE ,UPDATE_BY=? ");
            sql.append(" ,BURDENT_ADJUST=?,DEBT_BURDENT_SCORE_ADJUST=? ");
            sql.append(" WHERE PERSONAL_ID=?  ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prmXRulesDebBdDataM.getUseFlag());
            ps.setBigDecimal(2, prmXRulesDebBdDataM.getBurden());
            ps.setBigDecimal(3, prmXRulesDebBdDataM.getDebtBurdentScore());
            ps.setBigDecimal(4, prmXRulesDebBdDataM.getIncome());
            ps.setString(5, prmXRulesDebBdDataM.getUpdateBy());
            ps.setBigDecimal(6,prmXRulesDebBdDataM.getBurdenAdjust());
            ps.setBigDecimal(7,prmXRulesDebBdDataM.getDebtBurdentScoreAdjust());
            ps.setString(8, prmXRulesDebBdDataM.getPersonalID());       
            returnRows = ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:",e);
            e.printStackTrace();   
            throw new XRulesDebtDdException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:",e);
            }
        }
        return returnRows;
    }

    

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesDebtBdDAO#deleteModelXRulesDebBdM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesDebBdM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesDebtDdException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			if (idNoVects == null) {
				log
						.debug("XRulesDebtBdDAOImpl-->deleteModelXRulesDebBdM  idno Vects=null");
				return;
			}
			if (idNoVects.size() < 1) {
				log
						.debug("XRulesDebtBdDAOImpl-->deleteModelXRulesDebBdM  Idno size =0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_DEBT_BD ");
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
			log.fatal("Error:",e);
			throw new XRulesDebtDdException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesDebtDdException(e.getMessage());
			}
		}

    }
}
