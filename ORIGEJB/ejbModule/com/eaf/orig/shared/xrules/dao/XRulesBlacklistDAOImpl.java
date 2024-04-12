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
import com.eaf.orig.shared.xrules.dao.exception.XRulesBlacklistException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesBlacklistDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesBlacklistDAOImpl
 */
public class XRulesBlacklistDAOImpl extends OrigObjectDAO implements XRulesBlacklistDAO {
    private static Logger log = Logger.getLogger(XRulesBlacklistDAOImpl.class);

    /**
     *  
     */
    public XRulesBlacklistDAOImpl() {
        super();
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistDAO#createModelXRulesBlacklistM(com.eaf.xrules.shared.model.XRulesBlacklistDataM)
     */
    public void createModelXRulesBlacklistM(XRulesBlacklistDataM prmXRulesBlacklistDataM) throws XRulesBlacklistException {
        try {
            createTableXRULES_BLACKLIST(prmXRulesBlacklistDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesBlacklistException(e.getMessage());
        }

    }

    /**
     * @param prmXRulesBlacklistDataM
     */
    private void createTableXRULES_BLACKLIST(XRulesBlacklistDataM prmXRulesBlacklistDataM) throws XRulesBlacklistException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("INSERT INTO XRULES_BLACKLIST ");
            /*sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,BL_FLAG,HL_FLAG  ");
            sql.append("  ,UPDATE_DATE ,UPDATE_BY,THTITLE, THNAME, THLASTNAME");
            sql.append("  ,ENTITLE,ENNAME,ENLASTNAME, BIRTHDATE, BL_SOURCE");
            sql.append("  ,BL_REASON ,BL_DETAILS, SEQ,HOUSEIDNO,CIDNUM");
            sql.append("  ,BL_IDNO   )");
            sql.append(" VALUES(?,?,?,?,?  ,SYSDATE,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
            sql.append("   ,?) ");*/
            sql.append("(  PERSONAL_ID,BL_FLAG,HL_FLAG  ");
            sql.append("  ,UPDATE_DATE ,UPDATE_BY,THTITLE, THNAME, THLASTNAME");
            sql.append("  ,ENTITLE,ENNAME,ENLASTNAME, BIRTHDATE, BL_SOURCE");
            sql.append("  ,BL_REASON ,BL_DETAILS, SEQ,HOUSEIDNO,CIDNUM");
            sql.append("  ,BL_IDNO   )");
            sql.append(" VALUES(?,?,?,?  ,SYSDATE,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
            sql.append("   ,?) ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, prmXRulesBlacklistDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesBlacklistDataM.getCmpCde());
            ps.setString(3, prmXRulesBlacklistDataM.getIdNo());
            ps.setString(4, prmXRulesBlacklistDataM.getBLFlag());
            ps.setString(5, prmXRulesBlacklistDataM.getHLFlag());
            ps.setString(6, prmXRulesBlacklistDataM.getUpdateBy());
            ps.setString(7, prmXRulesBlacklistDataM.getThaiTitleName());
            ps.setString(8, prmXRulesBlacklistDataM.getThaiFirstName());
            ps.setString(9, prmXRulesBlacklistDataM.getThaiLastName());

            ps.setString(10, prmXRulesBlacklistDataM.getEngTitleName());
            ps.setString(11, prmXRulesBlacklistDataM.getEngFirstName());
            ps.setString(12, prmXRulesBlacklistDataM.getEngLastName());
            ps.setDate(13, this.parseDate(prmXRulesBlacklistDataM.getBirthDate()));
            ps.setString(14, prmXRulesBlacklistDataM.getBLSource());
            ps.setString(15, prmXRulesBlacklistDataM.getBLReason());
            ps.setString(16, prmXRulesBlacklistDataM.getBLDetails());
            ps.setInt(17, prmXRulesBlacklistDataM.getSeq());
            ps.setString(18, prmXRulesBlacklistDataM.getHouseIdNo());
            ps.setString(19, prmXRulesBlacklistDataM.getCidnum());
            ps.setString(20, prmXRulesBlacklistDataM.getBlacklistIdNo());*/
            ps.setString(1, prmXRulesBlacklistDataM.getPersonalID());
            ps.setString(2, prmXRulesBlacklistDataM.getBLFlag());
            ps.setString(3, prmXRulesBlacklistDataM.getHLFlag());
            
            ps.setString(4, prmXRulesBlacklistDataM.getUpdateBy());
            ps.setString(5, prmXRulesBlacklistDataM.getThaiTitleName());
            ps.setString(6, prmXRulesBlacklistDataM.getThaiFirstName());
            ps.setString(7, prmXRulesBlacklistDataM.getThaiLastName());
            
            ps.setString(8, prmXRulesBlacklistDataM.getEngTitleName());
            ps.setString(9, prmXRulesBlacklistDataM.getEngFirstName());
            ps.setString(10, prmXRulesBlacklistDataM.getEngLastName());
            ps.setDate(11, this.parseDate(prmXRulesBlacklistDataM.getBirthDate()));
            ps.setString(12, prmXRulesBlacklistDataM.getBLSource());
            
            ps.setString(13, prmXRulesBlacklistDataM.getBLReason());
            ps.setString(14, prmXRulesBlacklistDataM.getBLDetails());
            ps.setInt(15, prmXRulesBlacklistDataM.getSeq());
            ps.setString(16, prmXRulesBlacklistDataM.getHouseIdNo());
            ps.setString(17, prmXRulesBlacklistDataM.getCidnum());
            
            ps.setString(18, prmXRulesBlacklistDataM.getBlacklistIdNo());
            ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesBlacklistException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistDAO#deleteModelXRulesBlacklistM(com.eaf.xrules.shared.model.XRulesBlacklistDataM)
     */
    public void deleteModelXRulesBlacklistM(XRulesBlacklistDataM prmXRulesBlacklistDataM) throws XRulesBlacklistException {
        try {
            deleteTableXRULES_BLACKLIST(prmXRulesBlacklistDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesBlacklistException(e.getMessage());
        }

    }

    /**
     * @param prmXRulesBlacklistDataM
     */
    private void deleteTableXRULES_BLACKLIST(XRulesBlacklistDataM prmXRulesBlacklistDataM) throws XRulesBlacklistException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("DELETE XRULES_BLACKLIST ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");*/
            sql.append("DELETE XRULES_BLACKLIST ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? AND SEQ=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prmXRulesBlacklistDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesBlacklistDataM.getPersonalID());
            ps.setInt(3, prmXRulesBlacklistDataM.getSeq());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesBlacklistException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesBlacklistException(e.getMessage());
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistDAO#loadModelXRulesBlacklistM(java.lang.String)
     */
    public Vector loadModelXRulesBlacklistM(String personalID) throws XRulesBlacklistException {
        try {
            Vector vResult = selectTableXRULES_BLACKLIST(personalID);
            return vResult;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesBlacklistException(e.getMessage());
        }
    }

    /**
     * @param applicationRecordId
     * @return
     */
    private Vector selectTableXRULES_BLACKLIST(String personalID) throws XRulesBlacklistException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("SELECT  CMPCDE,IDNO,BL_FLAG,HL_FLAG,UPDATE_DATE ");
            sql.append(" ,UPDATE_BY,THTITLE, THNAME, THLASTNAME ,ENTITLE");
            sql.append(" ,ENNAME,ENLASTNAME, BIRTHDATE, BL_SOURCE ,BL_REASON ");
            sql.append(" ,BL_DETAILS, SEQ ,HOUSEIDNO ,CIDNUM,BL_IDNO");
            sql.append(" FROM XRULES_BLACKLIST WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO =?");*/
            sql.append("SELECT  BL_FLAG,HL_FLAG,UPDATE_DATE ");
            sql.append(" ,UPDATE_BY,THTITLE, THNAME, THLASTNAME ,ENTITLE");
            sql.append(" ,ENNAME,ENLASTNAME, BIRTHDATE, BL_SOURCE ,BL_REASON ");
            sql.append(" ,BL_DETAILS, SEQ ,HOUSEIDNO ,CIDNUM,BL_IDNO");
            sql.append(" FROM XRULES_BLACKLIST WHERE PERSONAL_ID=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            rs = null;
            /*ps.setString(1, applicationRecordId);
            ps.setString(2, cmpcde);
            ps.setString(3, idno);*/
            ps.setString(1, personalID);
            rs = ps.executeQuery();
            Vector vt = new Vector();
            while (rs.next()) {
                XRulesBlacklistDataM prmXRulesBlacklistDataM = new XRulesBlacklistDataM();
                prmXRulesBlacklistDataM.setPersonalID(personalID);
                prmXRulesBlacklistDataM.setBLFlag(rs.getString(1));
                prmXRulesBlacklistDataM.setHLFlag(rs.getString(2));
                prmXRulesBlacklistDataM.setUpdateDate(rs.getTimestamp(3));
                prmXRulesBlacklistDataM.setUpdateBy(rs.getString(4));
                prmXRulesBlacklistDataM.setThaiTitleName(rs.getString(5));
                prmXRulesBlacklistDataM.setThaiFirstName(rs.getString(6));
                prmXRulesBlacklistDataM.setThaiLastName(rs.getString(7));
                prmXRulesBlacklistDataM.setEngTitleName(rs.getString(8));
                prmXRulesBlacklistDataM.setEngFirstName(rs.getString(9));
                prmXRulesBlacklistDataM.setEngLastName(rs.getString(10));
                prmXRulesBlacklistDataM.setBirthDate(rs.getDate(11));
                prmXRulesBlacklistDataM.setBLSource(rs.getString(12));
                prmXRulesBlacklistDataM.setBLReason(rs.getString(13));
                prmXRulesBlacklistDataM.setBLDetails(rs.getString(14));
                prmXRulesBlacklistDataM.setSeq(rs.getInt(15));
                prmXRulesBlacklistDataM.setHouseIdNo(rs.getString(16));
                prmXRulesBlacklistDataM.setCidnum(rs.getString(17));
                prmXRulesBlacklistDataM.setBlacklistIdNo(rs.getString(18));
                vt.add(prmXRulesBlacklistDataM);
            }
            return vt;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesBlacklistException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistDAO#saveUpdateModelXRulesBlacklistM(com.eaf.xrules.shared.model.XRulesBlacklistDataM)
     */
    public void saveUpdateModelXRulesBlacklistM(Vector vXRulesBlacklistDataM) throws XRulesBlacklistException {

        try {
            //update data
            if (vXRulesBlacklistDataM != null) {
                for (int i = 0; i < vXRulesBlacklistDataM.size(); i++) {
                    this.saveUpdateModelXRulesBlacklistM((XRulesBlacklistDataM) vXRulesBlacklistDataM.get(i));
                }
            }
            //delete table
            deleteSomeTableXRULES_BLACKLIST(vXRulesBlacklistDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesBlacklistException(e.getMessage());
        }

    }

    /**
     * @param rulesBlacklistDataM
     */
    private void deleteSomeTableXRULES_BLACKLIST(Vector vXRulesBlacklistDataM) throws XRulesBlacklistException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            if (vXRulesBlacklistDataM == null) {
                log.debug("XRulesBlacklistDAOImpl-->deleteSomeTableXRULES_BLACKLIST vXRulesBlacklistDataM=null");
                return;
            }
            if (vXRulesBlacklistDataM.size() < 1) {
                log.debug("XRulesBlacklistDAOImpl-->deleteSomeTableXRULES_BLACKLIST vXRulesBlacklistDataM=0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_BLACKLIST ");
           // sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
            sql.append(" WHERE PERSONAL_ID = ?");
            sql.append(" AND SEQ not in(");
            XRulesBlacklistDataM prmXRuleBlackListDataM;
            prmXRuleBlackListDataM = (XRulesBlacklistDataM) vXRulesBlacklistDataM.get(0);
            sql.append(prmXRuleBlackListDataM.getSeq());
            for (int i = 1; i < vXRulesBlacklistDataM.size() && i < XRulesConstant.limitDeleteSQLParam; i++) {
                prmXRuleBlackListDataM = (XRulesBlacklistDataM) vXRulesBlacklistDataM.get(i);
                sql.append("," + prmXRuleBlackListDataM.getSeq());
            }
            sql.append(" )");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, prmXRuleBlackListDataM.getApplicationRecordId());
            ps.setString(2, prmXRuleBlackListDataM.getCmpCde());
            ps.setString(3, prmXRuleBlackListDataM.getIdNo());*/
            
            ps.setString(1, prmXRuleBlackListDataM.getPersonalID());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesBlacklistException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesBlacklistException(e.getMessage());
            }
        }

    }

    public void saveUpdateModelXRulesBlacklistM(XRulesBlacklistDataM prmXRulesBlacklistDataM) throws XRulesBlacklistException {
        double returnRows = 0;
        returnRows = updateTableXRULES_BLACKLIST(prmXRulesBlacklistDataM);
        if (returnRows == 0) {
            log.debug("New record then can't update record in table XRULES_BLACKLIST then call Insert method");
            createTableXRULES_BLACKLIST(prmXRulesBlacklistDataM);
        }

    }

    /**
     * @param prmXRulesBlacklistDataM
     * @return
     */
    private double updateTableXRULES_BLACKLIST(XRulesBlacklistDataM prmXRulesBlacklistDataM) throws XRulesBlacklistException {
        double returnRows = 0;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("UPDATE XRULES_BLACKLIST ");
            sql.append(" SET  BL_FLAG=?,HL_FLAG=?,UPDATE_DATE=SYSDATE ,UPDATE_BY=?,THTITLE=?");
            sql.append("  ,THNAME=?, THLASTNAME=?,ENTITLE=?,ENNAME=?,ENLASTNAME=? ");
            sql.append(", BIRTHDATE=?, BL_SOURCE=?,BL_REASON=? ,BL_DETAILS=?,HOUSEIDNO=? ,CIDNUM=? ,BL_IDNO=?  ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
            sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prmXRulesBlacklistDataM.getBLFlag());
            ps.setString(2, prmXRulesBlacklistDataM.getHLFlag());
            ps.setString(3, prmXRulesBlacklistDataM.getUpdateBy());
            ps.setString(4, prmXRulesBlacklistDataM.getThaiTitleName());
            ps.setString(5, prmXRulesBlacklistDataM.getThaiFirstName());
            ps.setString(6, prmXRulesBlacklistDataM.getThaiLastName());

            ps.setString(7, prmXRulesBlacklistDataM.getEngTitleName());
            ps.setString(8, prmXRulesBlacklistDataM.getEngFirstName());
            ps.setString(9, prmXRulesBlacklistDataM.getEngLastName());
            ps.setDate(10, this.parseDate(prmXRulesBlacklistDataM.getBirthDate()));
            ps.setString(11, prmXRulesBlacklistDataM.getBLSource());
            ps.setString(12, prmXRulesBlacklistDataM.getBLReason());
            ps.setString(13, prmXRulesBlacklistDataM.getBLDetails());
            ps.setString(14, prmXRulesBlacklistDataM.getHouseIdNo());
            ps.setString(15, prmXRulesBlacklistDataM.getCidnum());
            ps.setString(16,prmXRulesBlacklistDataM.getBlacklistIdNo());
            //ps.setString(17, prmXRulesBlacklistDataM.getApplicationRecordId());
            //ps.setString(18, prmXRulesBlacklistDataM.getCmpCde());            
            //ps.setString(19, prmXRulesBlacklistDataM.getIdNo()); 
            ps.setString(17, prmXRulesBlacklistDataM.getPersonalID());
            ps.setInt(18, prmXRulesBlacklistDataM.getSeq());
            returnRows = ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesBlacklistException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistDAO#deleteModelXRulesBlacklistM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesBlacklistM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesBlacklistException {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
             
            if (idNoVects == null) {
                log.debug("XRulesBlacklistDAOImpl-->deleteModelXRulesBlacklistM  idNOVects is null ");
                return;
            }
            if (idNoVects.size() < 1) {
                log.debug("XRulesBlacklistDAOImpl-->deleteModelXRulesBlacklistM  IdNO vects Size =0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_BLACKLIST ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=?  ");
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
            throw new XRulesBlacklistException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesBlacklistException(e.getMessage());
            }
        }
    }
}
