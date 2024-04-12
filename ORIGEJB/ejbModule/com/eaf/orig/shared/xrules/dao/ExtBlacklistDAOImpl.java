/*
 * Created on Oct 19, 2007
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

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.xrules.dao.exception.ExtBlacklistException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesBlacklistDataM;
import com.eaf.xrules.shared.model.XRulesDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: ExtBlacklistDAOImpl
 */
public class ExtBlacklistDAOImpl extends OrigObjectDAO implements ExtBlacklistDAO {
    private static Logger log = Logger.getLogger(ExtBlacklistDAOImpl.class);

    /**
     *  
     */
    public ExtBlacklistDAOImpl() {
        super();         
    }

    public Vector getBlacklist(XRulesDataM xRulesDataM) throws ExtBlacklistException {
        Vector blacklistedApp = new Vector();
        Vector vBlacklistName = new Vector();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String cmpCde = xRulesDataM.getCmpCde();
        String idNo = xRulesDataM.getIdNo();
        String firstName = xRulesDataM.getThaiFname();
        String lastName = xRulesDataM.getThaiLname();
        String houseId = xRulesDataM.getHouseId();
        log.debug("ExtBlacklistDAOImpl-->get Blacklist ");
        log.debug("cmpcde= " + cmpCde);
        log.debug("idNo " + idNo);
        log.debug("firstName " + firstName);
        log.debug("lastName " + lastName);
        if (!(idNo == null || "".equals(idNo)) || !(firstName == null || "".equals(firstName)) || !(lastName == null || "".equals(lastName))) {

        } else {
            log.debug("id  firstname lastname is null");
        }

        log.debug("blacklist ChangeName ->" + xRulesDataM.getChangeNameVect());

        Vector vChaneName = xRulesDataM.getChangeNameVect();
        Vector vOtherName = xRulesDataM.getOtherNameVect();
        vBlacklistName = this.getBlacklist(cmpCde, idNo, firstName, lastName, houseId, xRulesDataM.getCustomerType());
        blacklistedApp.addAll(vBlacklistName);
        return blacklistedApp;
    }

    public Vector getBlacklist(String cmpCde, String idNo, String thaiFirstName, String thaiLastName, String houseId, String customerType)
            throws ExtBlacklistException {

        if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customerType)) {
            if (thaiFirstName == null || "".equals(thaiFirstName))

            {
                log.error("Corporate Firstname no is null");
                return new Vector();
            }
        } else {
            if ((idNo == null || "".equals(idNo))

            ) {
                log.error("id no is null");
                return new Vector();
            }
        }
        Vector blacklistedApp = new Vector();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            int claseCount = 0;
            conn = getConnection(OrigServiceLocator.ORIG_DB);
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT CMPCDE,IDNO,TITCDE,THNAME,THSURN");
            sql.append(" ,ETITCDE,ENNAME,ENSURN,BIRDTE,BLSOURCE");
            sql.append(" ,BLREASON,BLNOTE,HOUSEIDNO,CIDNUM ");
            sql.append(" FROM HPMSBL00 ");
            sql.append("WHERE CMPCDE=? and ( ");
            if (idNo != null && !("".equals(idNo))) {
                sql.append(" (CIDNUM  =?) ");
                claseCount++;
            }
            if (thaiFirstName == null) {
                thaiFirstName = "";
            }
            if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customerType)) {
                if (claseCount > 0) {
                    sql.append(" or ");
                }
                if (!"".equals(thaiFirstName)) {
                    sql.append(" ( THNAME=? )");
                }
            } else {
                if (thaiLastName != null && !("".equals(thaiLastName))) {
                    if (claseCount > 0) {
                        sql.append(" or ");
                    }
                    sql.append(" ( THNAME=?  ");
                    sql.append(" and  THSURN=? )");

                    sql.append("   or ( THSURN like ? )  ");
                }
            }
            if ( houseId != null && !("".equals(houseId))) {
                if (claseCount > 0) {
                    sql.append(" or ");
                }
                sql.append(" (  HOUSEIDNO=? ) ");
            }
            sql.append(" ) ");
             
            String dSql = sql.toString();
            log.debug("getBlacklist ... sql= " + sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, cmpCde);
            int param = 2;
            if (idNo != null && !("".equals(idNo))) {
                ps.setString(param++, idNo);
            }
            if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customerType)) {
                if (!"".equals(thaiFirstName)) {
                    ps.setString(param++, thaiFirstName);
                }
            } else {
                if (thaiLastName != null && !("".equals(thaiLastName))) {
                    ps.setString(param++, thaiFirstName);
                    ps.setString(param++, thaiLastName);
                    ps.setString(param++, "%" + thaiLastName + "%");
                }
            }
            if ( houseId != null && !("".equals(houseId))) {
                ps.setString(param++, houseId);
            }
            rs = ps.executeQuery();
            String appID = "";
            int seq = 1;

            int limitResult = XRulesConstant.limitResult;
            while (rs.next()) {
                if (seq > limitResult) {
                    break;
                }
                String cmpCDE = rs.getString(1);
                String dupIdNo = rs.getString(2);
                XRulesBlacklistDataM xRulesBlacklistDataM = new XRulesBlacklistDataM();
                //BLFlag
                //HLFlag
                xRulesBlacklistDataM.setCmpCde(cmpCDE);
                //xRulesBlacklistDataM.setIdNo();
                xRulesBlacklistDataM.setBLFlag("");
                xRulesBlacklistDataM.setThaiTitleName(rs.getString(3));
                xRulesBlacklistDataM.setThaiFirstName(rs.getString(4));
                xRulesBlacklistDataM.setThaiLastName(rs.getString(5));
                xRulesBlacklistDataM.setEngTitleName(rs.getString(6));
                xRulesBlacklistDataM.setEngFirstName(rs.getString(7));
                xRulesBlacklistDataM.setEngLastName(rs.getString(8));
                xRulesBlacklistDataM.setBirthDate(rs.getDate(9));
                xRulesBlacklistDataM.setBLSource(rs.getString(10));
                xRulesBlacklistDataM.setBLReason(rs.getString(11));
                xRulesBlacklistDataM.setBLDetails(rs.getString(12));
                xRulesBlacklistDataM.setHouseIdNo(rs.getString(13));
                xRulesBlacklistDataM.setCidnum(rs.getString(14));
                xRulesBlacklistDataM.setBlacklistIdNo( dupIdNo);
                //set Hl flag
                if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customerType)) {
                    if (idNo.equalsIgnoreCase(xRulesBlacklistDataM.getBlacklistIdNo())) {
                        xRulesBlacklistDataM.setHLFlag(OrigConstant.ORIG_FLAG_Y);
                    } else {
                        xRulesBlacklistDataM.setHLFlag(OrigConstant.ORIG_FLAG_N);
                    }
                } else {
                    if (  (thaiFirstName != null && thaiFirstName.trim().equals(xRulesBlacklistDataM.getThaiFirstName().trim()))
                            && (thaiLastName != null && thaiLastName.trim().equals(xRulesBlacklistDataM.getThaiLastName().trim()))) {
                        xRulesBlacklistDataM.setHLFlag(OrigConstant.ORIG_FLAG_Y);
                    } else {
                        xRulesBlacklistDataM.setHLFlag(OrigConstant.ORIG_FLAG_N);
                    }
                }
                xRulesBlacklistDataM.setSeq(seq++);
                log.debug("Blacklist Hit[" + seq + "]" + dupIdNo);
                blacklistedApp.add(xRulesBlacklistDataM);
            }
            return blacklistedApp;
        } catch (Exception e) {
            log.error("Error:", e);
            throw new ExtBlacklistException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, rs, ps);
            } catch (Exception e) {
                log.error("Error:", e);
                throw new ExtBlacklistException(e.getMessage());
            }
        }
    }
}
