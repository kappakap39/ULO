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
import com.eaf.orig.shared.xrules.dao.exception.XRulesNCBAdjustException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesNCBAdjustDataM;
 

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesNCBDAOImpl
 */
public class XRulesNCBAdjustDAOImpl extends OrigObjectDAO implements XRulesNCBAdjustDAO {
    private static Logger log = Logger.getLogger(XRulesNCBAdjustDAOImpl.class);

    /**
     *  
     */
    public XRulesNCBAdjustDAOImpl() {
        super();
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBAdjustDAO#createModelXRulesNCBAdjustM(com.eaf.xrules.shared.model.XRulesNCBAdjustDataM)
     */
    public void createModelXRulesNCBAdjustM(XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM) throws XRulesNCBAdjustException {
      
        try {
            createTableXRULES_NCB_ADJUST(prmXRulesNCBAdjustDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        }
    }

    /**
     * @param prmXRulesNCBAdjustDataM
     */
    private void createTableXRULES_NCB_ADJUST(XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM) throws XRulesNCBAdjustException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("INSERT INTO XRULES_NCB_ADJUST ");
            /*sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,TRACKING_CODE,SEGMENT_VALUE  ");
            sql.append("  ,INSTALLMENT_ADJ_AMT,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,GROUP_SEQ)  ");*/
            sql.append("( PERSONAL_ID,TRACKING_CODE,SEGMENT_VALUE  ");
            sql.append("  ,INSTALLMENT_ADJ_AMT,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,GROUP_SEQ)  ");
            sql.append(" VALUES(?,?,?  ,?,SYSDATE,?,SYSDATE,? ,? ) ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);

            /*ps.setString(1, prmXRulesNCBAdjustDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesNCBAdjustDataM.getCmpCode());
            ps.setString(3, prmXRulesNCBAdjustDataM.getIdNo());
            ps.setString(4, prmXRulesNCBAdjustDataM.getNCBTracingCode());
            ps.setString(5, prmXRulesNCBAdjustDataM.getNCBSegmentValue());
            ps.setBigDecimal(6, prmXRulesNCBAdjustDataM.getNcbInstallmentAdjustAmount());
            ps.setString(7,prmXRulesNCBAdjustDataM.getCreateBy());
            ps.setString(8,prmXRulesNCBAdjustDataM.getUpdateBy());
            ps.setInt(9,prmXRulesNCBAdjustDataM.getGroupSeq());*/
            ps.setString(1, prmXRulesNCBAdjustDataM.getPersonalID());
            ps.setString(2, prmXRulesNCBAdjustDataM.getNCBTracingCode());
            ps.setString(3, prmXRulesNCBAdjustDataM.getNCBSegmentValue());
            ps.setBigDecimal(4, prmXRulesNCBAdjustDataM.getNcbInstallmentAdjustAmount());
            ps.setString(5, prmXRulesNCBAdjustDataM.getCreateBy());
            ps.setString(6, prmXRulesNCBAdjustDataM.getUpdateBy());
            ps.setInt(7, prmXRulesNCBAdjustDataM.getGroupSeq());
            ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
            }
        }
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBAdjustDAO#deleteModelXRulesNCBAdjustM(com.eaf.xrules.shared.model.XRulesNCBAdjustDataM)
     */
    public void deleteModelXRulesNCBAdjustM(XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM) throws XRulesNCBAdjustException {
        try {
            deleteTableXRULES_NCB_ADJUST(prmXRulesNCBAdjustDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        }

        
    }

    /**
     * @param prmXRulesNCBAdjustDataM
     */
    private void deleteTableXRULES_NCB_ADJUST(XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM) throws XRulesNCBAdjustException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_NCB_ADJUST ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND TRACKING_CODE=? AND SEGMENT_VALUE=?  AND GROUP_SEQ=?");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? AND TRACKING_CODE=? AND SEGMENT_VALUE=?  AND GROUP_SEQ=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prmXRulesNCBAdjustDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesNCBAdjustDataM.getPersonalID());
            ps.setString(3, prmXRulesNCBAdjustDataM.getNCBTracingCode());
            ps.setString(4, prmXRulesNCBAdjustDataM.getNCBSegmentValue());
            ps.setInt(5,prmXRulesNCBAdjustDataM.getGroupSeq());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesNCBAdjustException(e.getMessage());
            }
        }
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBAdjustDAO#loadModelXRulesNCBAdjustM(java.lang.String, java.lang.String, java.lang.String)
     */
    public Vector loadModelXRulesNCBAdjustM(String personalID, String trackingCode) throws XRulesNCBAdjustException {
        try {
            Vector vResult = selectTableXRULES_NCB_ADJUST(personalID, trackingCode);
            return vResult;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        }
      
    }

    /**
     * @param applicationRecordId
     * @param cmpcde
     * @param idno
     * @return
     */
    private Vector selectTableXRULES_NCB_ADJUST(String personalID,String trackingCode) throws XRulesNCBAdjustException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT  PERSONAL_ID,TRACKING_CODE,SEGMENT_VALUE,INSTALLMENT_ADJ_AMT ");            
            sql.append(" ,CREATE_DATE,CREATE_BY ,UPDATE_DATE,UPDATE_BY,GROUP_SEQ     ");            
            sql.append(" FROM XRULES_NCB_ADJUST WHERE PERSONAL_ID=? AND TRACKING_CODE =? ORDER BY SEGMENT_VALUE ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            rs = null;
            ps.setString(1, personalID);
            ps.setString(2,trackingCode);
            rs = ps.executeQuery();
            Vector vt = new Vector();
            while (rs.next()) {
                XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM = new XRulesNCBAdjustDataM();
                prmXRulesNCBAdjustDataM.setPersonalID(rs.getString(1));                
                prmXRulesNCBAdjustDataM.setNCBTracingCode(rs.getString(2));
                prmXRulesNCBAdjustDataM.setNCBSegmentValue(rs.getString(3));
                prmXRulesNCBAdjustDataM.setNcbInstallmentAdjustAmount(rs.getBigDecimal(4));
                prmXRulesNCBAdjustDataM.setGroupSeq(rs.getInt(5));
                vt.add(prmXRulesNCBAdjustDataM);
            }
            return vt;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, rs, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBAdjustDAO#saveUpdateModelXRulesNCBAdjustM(com.eaf.xrules.shared.model.XRulesNCBAdjustDataM)
     */
    public void saveUpdateModelXRulesNCBAdjustM(XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM) throws XRulesNCBAdjustException {
        double returnRows = 0;
        returnRows = updateTableXRULES_NCB_ADJUST(prmXRulesNCBAdjustDataM);
        if (returnRows == 0) {
            log.debug("New record then can't update record in table XRULES_NCB_ADJUST then call Insert method");
            createTableXRULES_NCB_ADJUST(prmXRulesNCBAdjustDataM);
        }
        
    }

    /**
     * @param prmXRulesNCBAdjustDataM
     * @return
     */
    private double updateTableXRULES_NCB_ADJUST(XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM) throws XRulesNCBAdjustException{
        double returnRows = 0;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("UPDATE XRULES_NCB_ADJUST ");
            sql.append(" SET INSTALLMENT_ADJ_AMT =?,UPDATE_DATE=SYSDATE,UPDATE_BY=?");             
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND TRACKING_CODE=? AND SEGMENT_VALUE=? ");
            sql.append(" WHERE PERSONAL_ID=? AND TRACKING_CODE=? AND SEGMENT_VALUE=? ");
            sql.append(" AND GROUP_SEQ=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setBigDecimal(1,prmXRulesNCBAdjustDataM.getNcbInstallmentAdjustAmount());
            ps.setString(2, prmXRulesNCBAdjustDataM.getUpdateBy());                   
            ps.setString(3, prmXRulesNCBAdjustDataM.getApplicationRecordId());
            ps.setString(4, prmXRulesNCBAdjustDataM.getCmpCode());
            ps.setString(5, prmXRulesNCBAdjustDataM.getIdNo());
            ps.setString(6, prmXRulesNCBAdjustDataM.getNCBTracingCode());
            ps.setString(7, prmXRulesNCBAdjustDataM.getNCBSegmentValue());
            ps.setInt(8,prmXRulesNCBAdjustDataM.getGroupSeq());*/
            ps.setBigDecimal(1,prmXRulesNCBAdjustDataM.getNcbInstallmentAdjustAmount());
            ps.setString(2, prmXRulesNCBAdjustDataM.getUpdateBy());                   
            ps.setString(3, prmXRulesNCBAdjustDataM.getPersonalID());
            ps.setString(4, prmXRulesNCBAdjustDataM.getNCBTracingCode());
            ps.setString(5, prmXRulesNCBAdjustDataM.getNCBSegmentValue());
            ps.setInt(6,prmXRulesNCBAdjustDataM.getGroupSeq());
            returnRows = ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
            }
        }
        return returnRows;
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBAdjustDAO#saveUpdateModelXRulesNCBAdjustM(java.util.Vector)
     */
    public void saveUpdateModelXRulesNCBAdjustM(Vector vXRulesNCBAdjustDataM) throws XRulesNCBAdjustException {
        try {
            //update data             
            if (vXRulesNCBAdjustDataM != null) {
                for (int i = 0; i < vXRulesNCBAdjustDataM.size(); i++) {
                    this.saveUpdateModelXRulesNCBAdjustM((XRulesNCBAdjustDataM) vXRulesNCBAdjustDataM.get(i));
                }
            }           
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        }
    }

    /**
     * @param rulesNCBAdjustDataM
     */
    private void deleteSomeTableXRULES_NCB_ADJUST(Vector vXRulesNCBAdjustDataM) throws XRulesNCBAdjustException{
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            if (vXRulesNCBAdjustDataM == null) {
                log.debug("XRulesNCBDAOImpl-->deleteSomeTableXRULES_NCB vXRulesNCBDataM=null");
                return;
            }
            if (vXRulesNCBAdjustDataM.size() < 1) {
                log.debug("XRulesBlacklistDAOImpl-->deleteSomeTableXRULES_NCB vXRulesNCBDataM=0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_NCB_ADJUST ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=?  ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=?  ");
            sql.append(" AND (SEGMENT_VALUE not in('");
            XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM;
            prmXRulesNCBAdjustDataM = (XRulesNCBAdjustDataM) vXRulesNCBAdjustDataM.get(0);
            sql.append(prmXRulesNCBAdjustDataM.getNCBSegmentValue());
            for (int i = 1; i < vXRulesNCBAdjustDataM.size() && i < XRulesConstant.limitDeleteSQLParam; i++) {
                prmXRulesNCBAdjustDataM = (XRulesNCBAdjustDataM) vXRulesNCBAdjustDataM.get(i);
                sql.append("','" + prmXRulesNCBAdjustDataM.getNCBSegmentValue());
            }
            sql.append("' )");
            sql.append(" AND  TRACKING_CODE <> ? )");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, prmXRulesNCBAdjustDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesNCBAdjustDataM.getCmpCode());
            ps.setString(3, prmXRulesNCBAdjustDataM.getIdNo());
            ps.setString(4, prmXRulesNCBAdjustDataM.getNCBTracingCode());*/
            ps.setString(1, prmXRulesNCBAdjustDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesNCBAdjustDataM.getPersonalID());
            ps.setString(3, prmXRulesNCBAdjustDataM.getNCBTracingCode());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesNCBAdjustException(e.getMessage());
            }
        }

        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBAdjustDAO#deleteModelXRulesNCBAdjustM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesNCBAdjustM(String personalID) throws XRulesNCBAdjustException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            //if (vSegmentValue == null) {
             //   log.debug("XRulesNCBDAOImpl-->deleteModelXRulesNCBMAdjust  idno Vects=null");
            //    return;
            //}
            //if (vSegmentValue.size() < 1) {
                log.debug("XRulesNCBDAOImpl-->deleteModelXRulesNCBMAdjust  Idno size =0");
            //    return;
           // }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_NCB_ADJUST ");
            sql.append(" WHERE PERSONAL_ID = ? ");
            /*sql.append(" WHERE PERSONAL_ID = ? AND CMPCDE=?   ");
            sql.append(" AND  IDNO=?");*/                  
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, personalID);
            /*ps.setString(1, applicationRecordId);
            ps.setString(2, cmpCde);
            ps.setString(3, idno);*/
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesNCBAdjustException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesNCBAdjustException(e.getMessage());
            }
        }
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesNCBAdjustDAO#deleteModelXRulesNCBAdjustM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesNCBAdjustM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesNCBAdjustException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            if (idNoVects == null) {
                log.debug("XRulesNCBAdjustDAOImpl-->deleteModelXRulesNCBAdjustM  idno Vects=null");
                return;
            }
            if (idNoVects.size() < 1) {
                log.debug("XRulesNCBAdjustDAOImpl-->deleteModelXRulesNCBAdjustM  Idno size =0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_NCB_ADJUST ");
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
            throw new XRulesNCBAdjustException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesNCBAdjustException(e.getMessage());
            }
        }
        
    }

   
}
