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

import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.xrules.dao.exception.XRulesVerificationResultException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesBlacklistDataM;
import com.eaf.xrules.shared.model.XRulesBlacklistVehicleDataM;
import com.eaf.xrules.shared.model.XRulesDebtBdDataM;
import com.eaf.xrules.shared.model.XRulesDedupDataM;
import com.eaf.xrules.shared.model.XRulesDedupVehicleDataM;
import com.eaf.xrules.shared.model.XRulesExistcustDataM;
import com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM;
import com.eaf.xrules.shared.model.XRulesFICODataM;
import com.eaf.xrules.shared.model.XRulesLPMDataM;
import com.eaf.xrules.shared.model.XRulesNCBAdjustDataM;
import com.eaf.xrules.shared.model.XRulesNCBDataM;
import com.eaf.xrules.shared.model.XRulesPhoneVerificationDataM;
import com.eaf.xrules.shared.model.XRulesPolicyRulesDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesVerificationResultDAOImpl
 */
public class XRulesVerificationResultDAOImpl extends OrigObjectDAO implements XRulesVerificationResultDAO {
    private static Logger log = Logger.getLogger(XRulesVerificationResultDAOImpl.class);

    /**
     *  
     */
    public XRulesVerificationResultDAOImpl() {
        super();
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAO#createModelXRulesVerificationResultM(com.eaf.xrules.shared.model.XRulesVerificationResultDataM)
     */
    public void createModelXRulesVerificationResultM(XRulesVerificationResultDataM prmXRulesVerificationResultDataM) throws XRulesVerificationResultException {
        try {
            createTableXRULES_VERIFICATION_RESULT(prmXRulesVerificationResultDataM);
            //create other xrules result
            this.setVerificationKey(prmXRulesVerificationResultDataM);
            //==============Blacklist============================
            Vector vXRulesBlacklist = prmXRulesVerificationResultDataM.getVXRulesBlacklistDataM();
            if (vXRulesBlacklist != null) {
                XRulesBlacklistDAO xRulesBlacklistDAO = ORIGDAOFactory.getXRulesBlacklistDAO();
                for (int i = 0; i < vXRulesBlacklist.size(); i++)
                    xRulesBlacklistDAO.createModelXRulesBlacklistM((XRulesBlacklistDataM) vXRulesBlacklist.get(i));
            }
            //==================BlacklistVehicle========================
            Vector vXRulesBlacklistVehicle = prmXRulesVerificationResultDataM.getVXRulesBlacklistVehicleDataM();
            if (vXRulesBlacklistVehicle != null) {
                XRulesBlacklistVehicleDAO xRulesBlacklistVehicleDAO = ORIGDAOFactory.getXRulesBlacklistVehicleDAO();
                for (int i = 0; i < vXRulesBlacklistVehicle.size(); i++)
                    xRulesBlacklistVehicleDAO.createModelXRulesBlacklistVehicleM((XRulesBlacklistVehicleDataM) vXRulesBlacklistVehicle.get(i));
            }
            //==============Debtbd============================
            XRulesDebtBdDataM prmXRulesDebtBd = prmXRulesVerificationResultDataM.getXRulesDebtBdDataM();
            if (prmXRulesDebtBd != null) {
                XRulesDebtBdDAO xRulesDebtBdDAO = ORIGDAOFactory.getXRulesDebtBdDAO();
                //for (int i = 0; i < vXRulesDebtBdlist.size(); i++)
                xRulesDebtBdDAO.createModelXRulesDebBdM(prmXRulesDebtBd);
            }
            //================dedup==========================
            Vector vXRulesDedup = prmXRulesVerificationResultDataM.getVXRulesDedupDataM();
            if (vXRulesDedup != null) {
                XRulesDedupDAO xRulesDedupDAO = ORIGDAOFactory.getXRulesDedupDAO();
                for (int i = 0; i < vXRulesDedup.size(); i++)
                    xRulesDedupDAO.createModelXRulesDedupM((XRulesDedupDataM) vXRulesDedup.get(i));
            }
            //================Dedup Vehicle==========================
            Vector vXRulesDedupVehicle = prmXRulesVerificationResultDataM.getVXRulesDedupVehicleDataM();
            if (vXRulesDedupVehicle != null) {
                XRulesDedupVehicleDAO xRulesDedupVehicleDAO = ORIGDAOFactory.getXRulesDedupVehicleDAO();
                for (int i = 0; i < vXRulesDedupVehicle.size(); i++)
                    xRulesDedupVehicleDAO.createModelXRulesDedupVenhicleM((XRulesDedupVehicleDataM) vXRulesDedupVehicle.get(i));
            }
            //====================================
            //================Existcust==========================
            Vector vXRulesExistcust = prmXRulesVerificationResultDataM.getVXRulesExistcustDataM();
            if (vXRulesExistcust != null) {
                XRulesExistcustDAO xRulesExistcustDAO = ORIGDAOFactory.getXRulesExistcustDAO();
                for (int i = 0; i < vXRulesExistcust.size(); i++)
                    xRulesExistcustDAO.createModelXRulesExistcustM((XRulesExistcustDataM) vXRulesExistcust.get(i));
            }
            Vector vXRulesExistcustInprogres = prmXRulesVerificationResultDataM.getVXRulesExistcustInprogressDataM();
            if (vXRulesExistcustInprogres != null) {
                XRulesExistcustInprogressDAO xRulesExistcustInprogressDAO = ORIGDAOFactory.getXRulesExistcustInprogressDAO();
                for (int i = 0; i < vXRulesExistcustInprogres.size(); i++)
                    xRulesExistcustInprogressDAO.createModelXRulesExistcustInprogressM((XRulesExistcustInprogressDataM) vXRulesExistcustInprogres.get(i));
            }
            //====================================
            //			================LPM==========================
            Vector vXRulesLPM = prmXRulesVerificationResultDataM.getVXRulesLMPDataM();
            if (vXRulesLPM != null) {
                XRulesLPMDAO xRulesLPMDAO = ORIGDAOFactory.getXRulesLPMDAO();
                for (int i = 0; i < vXRulesLPM.size(); i++)
                    xRulesLPMDAO.createModelXRulesLPMM((XRulesLPMDataM) vXRulesLPM.get(i));
            }
            //====================================
            //			================NCB==========================
            Vector vXRulesNCB = prmXRulesVerificationResultDataM.getVXRulesNCBDataM();
            if (vXRulesNCB != null) {
                XRulesNCBDAO xRulesNCBDAO = ORIGDAOFactory.getXRulesNCBDAO();
                for (int i = 0; i < vXRulesNCB.size(); i++)
                    xRulesNCBDAO.createModelXRulesNCBM((XRulesNCBDataM) vXRulesNCB.get(i));
            }
            //====================================
            //			================Phone Verification==========================
            Vector vXRulesPhoneVerification = prmXRulesVerificationResultDataM.getVXRulesPhoneVerificationDataM();
            if (vXRulesPhoneVerification != null) {
                XRulesPhoneVerificationDAO xRulesPhoneVerificationAO = ORIGDAOFactory.getXRulesPhoneVerificationDAO();
                for (int i = 0; i < vXRulesPhoneVerification.size(); i++)
                    xRulesPhoneVerificationAO.createModelXRulesPhoneVerificationM((XRulesPhoneVerificationDataM) vXRulesPhoneVerification.get(i));
            }
            //====================================
            //			================Policy rule==========================
            Vector vXRulesPolicyRules = prmXRulesVerificationResultDataM.getVXRulesPolicyRulesDataM();

            if (vXRulesPolicyRules != null) {
                XRulesPolicyRulesDAO xRulesPolicyRulesDAO = ORIGDAOFactory.getXRulesPolicyRulesDAO();
                for (int i = 0; i < vXRulesPolicyRules.size(); i++)
                    xRulesPolicyRulesDAO.createModelXRulesPolicyRulesM((XRulesPolicyRulesDataM) vXRulesPolicyRules.get(i));
            }
            //====================================
            //			==============FICO============================
            XRulesFICODataM prmXRulesFico = prmXRulesVerificationResultDataM.getXrulesFICODataM();
            if (prmXRulesFico != null) {
                XRulesFICODAO xRulesFICODAO = ORIGDAOFactory.getXRulesFICODAO();
                //for (int i = 0; i < vXRulesDebtBdlist.size(); i++)
                xRulesFICODAO.createModelXRulesFICODataM(prmXRulesFico);
            }
            
            //======================NCB Adjust===================================
            Vector vNCBAdjust=prmXRulesVerificationResultDataM.getVNCBAdjust();
            if(vNCBAdjust!=null){
                XRulesNCBAdjustDAO xRulesNCBAdjustDAO=ORIGDAOFactory.getXRulesNCBAdjustDAO();
                for (int i = 0; i < vNCBAdjust.size(); i++) {                    
                    XRulesNCBAdjustDataM  prmXRulesNCBAdjustDataM=(XRulesNCBAdjustDataM)vNCBAdjust.get(i);
                    xRulesNCBAdjustDAO.createModelXRulesNCBAdjustM(prmXRulesNCBAdjustDataM); 
                }
            
            }
            
          //================Existcust Surname==========================
            Vector vXRulesExistcustSurname = prmXRulesVerificationResultDataM.getVXRulesExistcustSurnameDataM();
            if (vXRulesExistcustSurname != null) {
                XRulesExistcustSurnameDAO xRulesExistcustSurnameDAO = ORIGDAOFactory.getXRulesExistcustSurnameDAO();
                for (int i = 0; i < vXRulesExistcustSurname.size(); i++)
                    xRulesExistcustSurnameDAO.createModelXRulesExistcustM((XRulesExistcustDataM) vXRulesExistcustSurname.get(i));
            }
            Vector vXRulesExistcustInprogresSurname = prmXRulesVerificationResultDataM.getVXRulesExistcustInprogressSurnameDataM();
            if (vXRulesExistcustInprogresSurname != null) {
                XRulesExistcustInprogressSurnameDAO xRulesExistcustInprogressSurnameDAO = ORIGDAOFactory.getXRulesExistcustInprogressSurnameDAO();
                for (int i = 0; i < vXRulesExistcustInprogresSurname.size(); i++)
                    xRulesExistcustInprogressSurnameDAO.createModelXRulesExistcustInprogressM((XRulesExistcustInprogressDataM) vXRulesExistcustInprogresSurname.get(i));
            }
            //====================================
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesVerificationResultException(e.getMessage());
        }

    }

    /**
     * @param prmXRulesVerificationResultDataM
     */
    private void createTableXRULES_VERIFICATION_RESULT(XRulesVerificationResultDataM prmXRulesVerificationResultDataM) throws XRulesVerificationResultException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("INSERT INTO XRULES_VERIFICATION_RESULT ");
            //sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,BL_RESULT ,BL_VEHICLE_RESULT  ");
            sql.append("( PERSONAL_ID,BL_RESULT ,BL_VEHICLE_RESULT  ");           
            sql.append("  ,EXISTCUST_RESULT ,DEDUP_RESULT ,DEDUP_VEHICLE_RESULT  ,LPM_RESULT ,POLICY_RULES_RESULT ");
            sql.append("  ,DEBT_BD_RESULT ,DEBT_BD_PARAM,KHONTHAI_RESULT,PHONEVER_RESULT,YELLOW_PAGES_RESULT   ");
            sql.append("  ,NCB_RESULT,NCB_COLOR, KHONTHAI_COMMENT,YELLOW_PAGES_COMMENT,LPM_COMMENT ");
            sql.append("   ,DEBT_BD_SCORE,FINAL_POLICY_RULES_RESULT,PHONEBOOK_RESULT ,PHONEBOOK_COMMENT,NCB_TRACKING_CODE");
            sql.append("       ,FICO_RESULT  ");
            sql.append(" ,BLACKLIST_UPDATE_DATE,BLACKLIST_UPDATE_BY,BLACKLIST_VEHICLE_UPDATE_DATE,BLACKLIST_VEHICLE_UPDATE_BY,DEBT_BD_UPDATE_DATE ");
            sql.append(" ,DEBT_BD_UPDATE_BY,DEDUP_UPDATE_DATE,DEDUP_UPDATE_BY ,EXISTCUST_UPDATE_DATE ,EXISTCUST_UPDATE_BY");
            sql.append(" ,FICO_UPDATE_DATE ,FICO_UPDATE_BY , LPM_UPDATE_DATE,LPM_UPDATE_BY , NCB_UPDATE_DATE  ");
            sql.append(" , NCB_UPDATE_BY,PHONE_VER_UPDATE_DATE, PHONE_VER_UPDATE_BY,DEDUP_VEHICLE_UPDATE_DATE,DEDUP_VEHICLE_UPDATE_BY ");
            sql.append(" ,POLICY_RULES_UPDATE_DATE ,POLICY_RULES_UPDATE_BY,DEBT_AMT_RESULT,DEBT_AMT_OD_INT_FLAG,THAIREGISTRATION_RESULT");
            sql.append(" , THAIREGISTRATION_COMMENT, BOL_RESULT, BOL_COMMENT ,DEBT_AMT_UPDATE_DATE, DEBT_AMT_UPDATE_BY");
            sql.append(" ,THAIREGISTRATION_UPDATE_DATE,  THAIREGISTRATION_UPDATE_BY ,  BOL_UPDATE_DATE  ,  BOL_UPDATE_BY  ,KHONTHAI_UPDATE_DATE ");
            sql.append(" ,KHONTHAI_UPDATE_BY ,YELLOWPAGES_UPDATE_DATE ,YELLOWPAGES_UPDATE_BY ,PHONEBOOK_UPDATE_DATE , PHONEBOOK_UPDATE_BY ");
            sql.append(" ,BLACKLIST_CUST_UPDATE_ROLE , BLACKLIST_VEHICLE_UPDATE_ROLE , EXISTINGCUST_UPDATE_ROLE , DEDUP_APP_UPDATE_ROLE , DEDUP_VEHICLE_UPDATE_ROLE");
            sql.append(" ,LPM_UPDATE_ROLE , POLICY_RULES_UPDATE_ROLE  ,  DEBT_AMT_UPDATE_ROLE ,  DEBTBD_UPDATE_ROLE , FICO_UPDATE_ROLE   ");
            sql.append(" ,PHONE_VER_UPDATE_ROLE , PHONE_BOOK_UPDATE_ROLE , YELLOWS_PAGES_UPDATE_ROLE  , KHONTHAI_UPDATE_ROLE , BOL_UPDATE_ROLE");
            sql.append(" ,THAIREGISTRATION_UPDATE_ROLE,DEBT_AMT_ADJUST_RESULT,EILIGIBILITY_RESULT,LTV_RESULT  ");
            sql.append(" ) ");
            sql.append(" VALUES(?,?,?  ,?,?,?,?,?   ,?,?,?,?,? ,?,?,?,?,?");
            sql.append("        ,?,?,?,?,?  ,? ");
            sql.append("        ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
            sql.append("        ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
            sql.append("        ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,? )");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, prmXRulesVerificationResultDataM.getApplicationRecordId());
            ps.setString(2, prmXRulesVerificationResultDataM.getCmpCode());
            ps.setString(3, prmXRulesVerificationResultDataM.getIdNo());
            ps.setString(4, prmXRulesVerificationResultDataM.getBLResult());
            ps.setString(5, prmXRulesVerificationResultDataM.getBLVehicleResult());
            ps.setString(6, prmXRulesVerificationResultDataM.getExistCustResult());
            ps.setString(7, prmXRulesVerificationResultDataM.getDedupResult());
            ps.setString(8, prmXRulesVerificationResultDataM.getDedupVehicleResult());
            ps.setString(9, prmXRulesVerificationResultDataM.getLPMResult());
            ps.setString(10, prmXRulesVerificationResultDataM.getPolicyRulesResult());
            ps.setString(11, prmXRulesVerificationResultDataM.getDEBT_BDResult());
            ps.setBigDecimal(12, prmXRulesVerificationResultDataM.getDEBT_BD_PARAM());
            ps.setString(13, prmXRulesVerificationResultDataM.getKhonThaiResult());
            ps.setString(14, prmXRulesVerificationResultDataM.getPhoneVerResult());
            ps.setString(15, prmXRulesVerificationResultDataM.getYellowPageResult());
            ps.setString(16, prmXRulesVerificationResultDataM.getNCBResult());
            ps.setString(17, prmXRulesVerificationResultDataM.getNCBColor());
            ps.setString(18, prmXRulesVerificationResultDataM.getKhonThaiComment());
            ps.setString(19, prmXRulesVerificationResultDataM.getYellowPageComment());
            ps.setString(20, prmXRulesVerificationResultDataM.getLPMcomment());
            ps.setBigDecimal(21, prmXRulesVerificationResultDataM.getDEBT_BDScore());
            ps.setString(22, prmXRulesVerificationResultDataM.getFinalpolicyRulesResult());
            ps.setString(23, prmXRulesVerificationResultDataM.getPhoneBookResult());
            ps.setString(24, prmXRulesVerificationResultDataM.getPhoneBookComment());
            ps.setString(25, prmXRulesVerificationResultDataM.getNCBTrackingCode());
            ps.setString(26, prmXRulesVerificationResultDataM.getFicoResult());

            ps.setTimestamp(27, prmXRulesVerificationResultDataM.getBlacklistUpdateDate());
            ps.setString(28, prmXRulesVerificationResultDataM.getBlacklistUpdateBy());
            ps.setTimestamp(29, prmXRulesVerificationResultDataM.getBlacklistVehcieUpdateDate());
            ps.setString(30, prmXRulesVerificationResultDataM.getBlacklistVehicleUpdateBy());
            ps.setTimestamp(31, prmXRulesVerificationResultDataM.getDebtBdUpdateDate());
            ps.setString(32, prmXRulesVerificationResultDataM.getDebtBdUpdateBy());
            ps.setTimestamp(33, prmXRulesVerificationResultDataM.getDedupUpdateDate());
            ps.setString(34, prmXRulesVerificationResultDataM.getDedupUpdateBy());
            ps.setTimestamp(35, prmXRulesVerificationResultDataM.getExistingCustUpdateDate());
            ps.setString(36, prmXRulesVerificationResultDataM.getExistCustUpdateBy());
            ps.setTimestamp(37, prmXRulesVerificationResultDataM.getFicoUpdateDate());
            ps.setString(38, prmXRulesVerificationResultDataM.getFicoUpdateBy());
            ps.setTimestamp(39, prmXRulesVerificationResultDataM.getLpmUpdateDate());
            ps.setString(40, prmXRulesVerificationResultDataM.getLpmUpdateBy());
            ps.setTimestamp(41, prmXRulesVerificationResultDataM.getNcbUpdateDate());
            ps.setString(42, prmXRulesVerificationResultDataM.getNcbUpdateBy());
            ps.setTimestamp(43, prmXRulesVerificationResultDataM.getPhoneVerUpdateDate());
            ps.setString(44, prmXRulesVerificationResultDataM.getPhoneVerUpdateBy());
            ps.setTimestamp(45, prmXRulesVerificationResultDataM.getDupVehicleUpdateDate());
            ps.setString(46, prmXRulesVerificationResultDataM.getDupVehicleUpdateBy());
            ps.setTimestamp(47, prmXRulesVerificationResultDataM.getPolicyRulesUpdateDate());
            ps.setString(48, prmXRulesVerificationResultDataM.getPolicyRulesUpdateBy());
            ps.setBigDecimal(49, prmXRulesVerificationResultDataM.getDebtAmountResult());
            ps.setString(50, prmXRulesVerificationResultDataM.getDebtAmountODInterestFlag());
            ps.setString(51,prmXRulesVerificationResultDataM.getThaiRegistrationResult());
            ps.setString(52,prmXRulesVerificationResultDataM.getThaiRegistrationComment());
            ps.setString(53,prmXRulesVerificationResultDataM.getBOLResult());
            ps.setString(54,prmXRulesVerificationResultDataM.getBOLComment());
            ps.setTimestamp(55,prmXRulesVerificationResultDataM.getDebtAmountUpdateDate());
            ps.setString(56,prmXRulesVerificationResultDataM.getDebtAmountUpdateBy());
            ps.setTimestamp(57,prmXRulesVerificationResultDataM.getThaiRegistrationUpdateDate());
            ps.setString(58,prmXRulesVerificationResultDataM.getThaiRegistrationUpdateBy());
            ps.setTimestamp(59,prmXRulesVerificationResultDataM.getBolUpdateDate());
            ps.setString(60,prmXRulesVerificationResultDataM.getBolUpdateBy());
            ps.setTimestamp(61,prmXRulesVerificationResultDataM.getKhonthaiUpdateDate());
            ps.setString(62,prmXRulesVerificationResultDataM.getKhonthaiUpdateBy());
            ps.setTimestamp(63,prmXRulesVerificationResultDataM.getYellowsPagesUpdateDate());
            ps.setString(64,prmXRulesVerificationResultDataM.getYellowsPagesUpdateBy()); 
            ps.setTimestamp(65,prmXRulesVerificationResultDataM.getPhonebookUpdateDate());
            ps.setString(66,prmXRulesVerificationResultDataM.getPhonebookUpdateBy());
            
            ps.setString(67,prmXRulesVerificationResultDataM.getBlacklistCustomerUpdateRole());
            ps.setString(68,prmXRulesVerificationResultDataM.getBlacklistVehicleUpdateRole());
            ps.setString(69,prmXRulesVerificationResultDataM.getExistingCustomerUpdateRole());
            ps.setString(70,prmXRulesVerificationResultDataM.getDedupUpdateRole());
            ps.setString(71,prmXRulesVerificationResultDataM.getDedupVehicleUpdateRole());             
            ps.setString(72,prmXRulesVerificationResultDataM.getLpmUpdateRole());
            ps.setString(73,prmXRulesVerificationResultDataM.getPolicyRulesUpdateRole());
            ps.setString(74,prmXRulesVerificationResultDataM.getDebtAmtUpdateRole());
            ps.setString(75,prmXRulesVerificationResultDataM.getDebtbdUpdateRole());
            ps.setString(76,prmXRulesVerificationResultDataM.getFicoUpdateRole());
            ps.setString(77,prmXRulesVerificationResultDataM.getPhoneVerUpdateRole());
            ps.setString(78,prmXRulesVerificationResultDataM.getPhoneBookUpdateRole());
            ps.setString(79,prmXRulesVerificationResultDataM.getYellowsPageUpdateRole());
            ps.setString(80,prmXRulesVerificationResultDataM.getKhonthaiUpdteRole());
            ps.setString(81,prmXRulesVerificationResultDataM.getBolUpdateRole());
            ps.setString(82,prmXRulesVerificationResultDataM.getThaiRegistrationUpdateRole());      
            ps.setBigDecimal(83,prmXRulesVerificationResultDataM.getDebtAmountAdjustResult());
            ps.setString(84,prmXRulesVerificationResultDataM.getEligibilityResult());
            ps.setString(85,prmXRulesVerificationResultDataM.getLtvResult());*/      

            ps.setString(1, prmXRulesVerificationResultDataM.getPersonalID());
            ps.setString(2, prmXRulesVerificationResultDataM.getBLResult());
            ps.setString(3, prmXRulesVerificationResultDataM.getBLVehicleResult());
            
            ps.setString(4, prmXRulesVerificationResultDataM.getExistCustResult());
            ps.setString(5, prmXRulesVerificationResultDataM.getDedupResult());
            ps.setString(6, prmXRulesVerificationResultDataM.getDedupVehicleResult());
            ps.setString(7, prmXRulesVerificationResultDataM.getLPMResult());
            ps.setString(8, prmXRulesVerificationResultDataM.getPolicyRulesResult());
            
            ps.setString(9, prmXRulesVerificationResultDataM.getDEBT_BDResult());
            ps.setBigDecimal(10, prmXRulesVerificationResultDataM.getDEBT_BD_PARAM());
            ps.setString(11, prmXRulesVerificationResultDataM.getKhonThaiResult());
            ps.setString(12, prmXRulesVerificationResultDataM.getPhoneVerResult());
            ps.setString(13, prmXRulesVerificationResultDataM.getYellowPageResult());

            ps.setString(14, prmXRulesVerificationResultDataM.getNCBResult());
            ps.setString(15, prmXRulesVerificationResultDataM.getNCBColor());
            ps.setString(16, prmXRulesVerificationResultDataM.getKhonThaiComment());
            ps.setString(17, prmXRulesVerificationResultDataM.getYellowPageComment());
            ps.setString(18, prmXRulesVerificationResultDataM.getLPMcomment());
            
            ps.setBigDecimal(19, prmXRulesVerificationResultDataM.getDEBT_BDScore());
            ps.setString(20, prmXRulesVerificationResultDataM.getFinalpolicyRulesResult());
            ps.setString(21, prmXRulesVerificationResultDataM.getPhoneBookResult());
            ps.setString(22, prmXRulesVerificationResultDataM.getPhoneBookComment());
            ps.setString(23, prmXRulesVerificationResultDataM.getNCBTrackingCode());
            
            ps.setString(24, prmXRulesVerificationResultDataM.getFicoResult());

            ps.setTimestamp(25, prmXRulesVerificationResultDataM.getBlacklistUpdateDate());
            ps.setString(26, prmXRulesVerificationResultDataM.getBlacklistUpdateBy());
            ps.setTimestamp(27, prmXRulesVerificationResultDataM.getBlacklistVehcieUpdateDate());
            ps.setString(28, prmXRulesVerificationResultDataM.getBlacklistVehicleUpdateBy());
            ps.setTimestamp(29, prmXRulesVerificationResultDataM.getDebtBdUpdateDate());
            
            ps.setString(30, prmXRulesVerificationResultDataM.getDebtBdUpdateBy());
            ps.setTimestamp(31, prmXRulesVerificationResultDataM.getDedupUpdateDate());
            ps.setString(32, prmXRulesVerificationResultDataM.getDedupUpdateBy());
            ps.setTimestamp(33, prmXRulesVerificationResultDataM.getExistingCustUpdateDate());
            ps.setString(34, prmXRulesVerificationResultDataM.getExistCustUpdateBy());
            
            ps.setTimestamp(35, prmXRulesVerificationResultDataM.getFicoUpdateDate());
            ps.setString(36, prmXRulesVerificationResultDataM.getFicoUpdateBy());
            ps.setTimestamp(37, prmXRulesVerificationResultDataM.getLpmUpdateDate());
            ps.setString(38, prmXRulesVerificationResultDataM.getLpmUpdateBy());
            ps.setTimestamp(39, prmXRulesVerificationResultDataM.getNcbUpdateDate());
            
            ps.setString(40, prmXRulesVerificationResultDataM.getNcbUpdateBy());
            ps.setTimestamp(41, prmXRulesVerificationResultDataM.getPhoneVerUpdateDate());
            ps.setString(42, prmXRulesVerificationResultDataM.getPhoneVerUpdateBy());
            ps.setTimestamp(43, prmXRulesVerificationResultDataM.getDupVehicleUpdateDate());
            ps.setString(44, prmXRulesVerificationResultDataM.getDupVehicleUpdateBy());
            
            ps.setTimestamp(45, prmXRulesVerificationResultDataM.getPolicyRulesUpdateDate());
            ps.setString(46, prmXRulesVerificationResultDataM.getPolicyRulesUpdateBy());
            ps.setBigDecimal(47, prmXRulesVerificationResultDataM.getDebtAmountResult());
            ps.setString(48, prmXRulesVerificationResultDataM.getDebtAmountODInterestFlag());
            ps.setString(49,prmXRulesVerificationResultDataM.getThaiRegistrationResult());
            
            ps.setString(50,prmXRulesVerificationResultDataM.getThaiRegistrationComment());
            ps.setString(51,prmXRulesVerificationResultDataM.getBOLResult());
            ps.setString(52,prmXRulesVerificationResultDataM.getBOLComment());
            ps.setTimestamp(53,prmXRulesVerificationResultDataM.getDebtAmountUpdateDate());
            ps.setString(54,prmXRulesVerificationResultDataM.getDebtAmountUpdateBy());
            
            ps.setTimestamp(55,prmXRulesVerificationResultDataM.getThaiRegistrationUpdateDate());
            ps.setString(56,prmXRulesVerificationResultDataM.getThaiRegistrationUpdateBy());
            ps.setTimestamp(57,prmXRulesVerificationResultDataM.getBolUpdateDate());
            ps.setString(58,prmXRulesVerificationResultDataM.getBolUpdateBy());
            ps.setTimestamp(59,prmXRulesVerificationResultDataM.getKhonthaiUpdateDate());
            
            ps.setString(60,prmXRulesVerificationResultDataM.getKhonthaiUpdateBy());
            ps.setTimestamp(61,prmXRulesVerificationResultDataM.getYellowsPagesUpdateDate());
            ps.setString(62,prmXRulesVerificationResultDataM.getYellowsPagesUpdateBy()); 
            ps.setTimestamp(63,prmXRulesVerificationResultDataM.getPhonebookUpdateDate());
            ps.setString(64,prmXRulesVerificationResultDataM.getPhonebookUpdateBy());
            
            ps.setString(65,prmXRulesVerificationResultDataM.getBlacklistCustomerUpdateRole());
            ps.setString(66,prmXRulesVerificationResultDataM.getBlacklistVehicleUpdateRole());
            ps.setString(67,prmXRulesVerificationResultDataM.getExistingCustomerUpdateRole());
            ps.setString(68,prmXRulesVerificationResultDataM.getDedupUpdateRole());
            ps.setString(69,prmXRulesVerificationResultDataM.getDedupVehicleUpdateRole());             
            
            ps.setString(70,prmXRulesVerificationResultDataM.getLpmUpdateRole());
            ps.setString(71,prmXRulesVerificationResultDataM.getPolicyRulesUpdateRole());
            ps.setString(72,prmXRulesVerificationResultDataM.getDebtAmtUpdateRole());
            ps.setString(73,prmXRulesVerificationResultDataM.getDebtbdUpdateRole());
            ps.setString(74,prmXRulesVerificationResultDataM.getFicoUpdateRole());
            
            ps.setString(75,prmXRulesVerificationResultDataM.getPhoneVerUpdateRole());
            ps.setString(76,prmXRulesVerificationResultDataM.getPhoneBookUpdateRole());
            ps.setString(77,prmXRulesVerificationResultDataM.getYellowsPageUpdateRole());
            ps.setString(78,prmXRulesVerificationResultDataM.getKhonthaiUpdteRole());
            ps.setString(79,prmXRulesVerificationResultDataM.getBolUpdateRole());
            
            ps.setString(80,prmXRulesVerificationResultDataM.getThaiRegistrationUpdateRole());      
            ps.setBigDecimal(81,prmXRulesVerificationResultDataM.getDebtAmountAdjustResult());
            ps.setString(82,prmXRulesVerificationResultDataM.getEligibilityResult());
            ps.setString(83,prmXRulesVerificationResultDataM.getLtvResult());
            ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesVerificationResultException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAO#deleteModelXRulesVerificationResultM(com.eaf.xrules.shared.model.XRulesVerificationResultDataM)
     */
    public void deleteModelXRulesVerificationResultM(XRulesVerificationResultDataM prmXRulesVerificationResultDataM) throws XRulesVerificationResultException {
        try {
            //delete other xrules result
            //==============Blacklist============================
            Vector vXRulesBlacklist = prmXRulesVerificationResultDataM.getVXRulesBlacklistDataM();
            if (vXRulesBlacklist != null) {
                XRulesBlacklistDAO xRulesBlacklistDAO = ORIGDAOFactory.getXRulesBlacklistDAO();
                for (int i = 0; i < vXRulesBlacklist.size(); i++) {
                    xRulesBlacklistDAO.deleteModelXRulesBlacklistM((XRulesBlacklistDataM) vXRulesBlacklist.get(i));
                }
            }
            //==================BlacklistVehicle========================
            Vector vXRulesBlacklistVehicle = prmXRulesVerificationResultDataM.getVXRulesBlacklistVehicleDataM();
            if (vXRulesBlacklistVehicle != null) {
                XRulesBlacklistVehicleDAO xRulesBlacklistVehicleDAO = ORIGDAOFactory.getXRulesBlacklistVehicleDAO();
                for (int i = 0; i < vXRulesBlacklistVehicle.size(); i++) {
                    xRulesBlacklistVehicleDAO.deleteModelXRulesBlacklistVehicleM((XRulesBlacklistVehicleDataM) vXRulesBlacklistVehicle.get(i));
                }
            }
            //==============Debtbd============================
            XRulesDebtBdDataM xRulesDebtBdDataM = prmXRulesVerificationResultDataM.getXRulesDebtBdDataM();
            if (xRulesDebtBdDataM != null) {
                XRulesDebtBdDAO xRulesDebtBdDAO = ORIGDAOFactory.getXRulesDebtBdDAO();
                //for (int i = 0; i < vXRulesDebtBdlist.size(); i++)
                xRulesDebtBdDAO.deleteModelXRulesDebBdM(xRulesDebtBdDataM);
            }
            //================dedup==========================
            Vector vXRulesDedup = prmXRulesVerificationResultDataM.getVXRulesDedupDataM();
            if (vXRulesDedup != null) {
                XRulesDedupDAO xRulesDedupDAO = ORIGDAOFactory.getXRulesDedupDAO();
                for (int i = 0; i < vXRulesDedup.size(); i++) {
                    xRulesDedupDAO.deleteModelXRulesDedupM((XRulesDedupDataM) vXRulesDedup.get(i));
                }
            }
            //================Dedup Vehicle==========================
            Vector vXRulesDedupVehicle = prmXRulesVerificationResultDataM.getVXRulesDedupVehicleDataM();
            if (vXRulesDedupVehicle != null) {
                XRulesDedupVehicleDAO xRulesDedupVehicleDAO = ORIGDAOFactory.getXRulesDedupVehicleDAO();
                for (int i = 0; i < vXRulesDedupVehicle.size(); i++) {
                    xRulesDedupVehicleDAO.deleteModelXRulesDedupVenhicleM((XRulesDedupVehicleDataM) vXRulesDedupVehicle.get(i));
                }
            }
            //====================================
            //================Existcust==========================
            Vector vXRulesExistcust = prmXRulesVerificationResultDataM.getVXRulesExistcustDataM();
            if (vXRulesExistcust != null) {
                XRulesExistcustDAO xRulesExistcustDAO = ORIGDAOFactory.getXRulesExistcustDAO();
                for (int i = 0; i < vXRulesExistcust.size(); i++) {
                    xRulesExistcustDAO.deleteModelXRulesExistcustM((XRulesExistcustDataM) vXRulesExistcust.get(i));
                }
            }
            //================Existcust Inprogress==========================
            Vector vXRulesExistcustInprogress = prmXRulesVerificationResultDataM.getVXRulesExistcustInprogressDataM();
            if (vXRulesExistcustInprogress != null) {
                XRulesExistcustInprogressDAO xRulesExistcustInprogressDAO = ORIGDAOFactory.getXRulesExistcustInprogressDAO();
                for (int i = 0; i < vXRulesExistcustInprogress.size(); i++) {
                    xRulesExistcustInprogressDAO.deleteModelXRulesExistcustInprogressM((XRulesExistcustInprogressDataM) vXRulesExistcustInprogress.get(i));
                }
            }
                        
            
            //====================================
            //			================LPM==========================
            Vector vXRulesLPM = prmXRulesVerificationResultDataM.getVXRulesLMPDataM();
            if (vXRulesLPM != null) {
                XRulesLPMDAO xRulesLPMDAO = ORIGDAOFactory.getXRulesLPMDAO();
                for (int i = 0; i < vXRulesLPM.size(); i++) {
                    xRulesLPMDAO.deleteModelXRulesLPMM((XRulesLPMDataM) vXRulesLPM.get(i));
                }
            }
            //====================================
            //			================NCB==========================
            Vector vXRulesNCB = prmXRulesVerificationResultDataM.getVXRulesNCBDataM();
            if (vXRulesNCB != null) {
                XRulesNCBDAO xRulesNCBDAO = ORIGDAOFactory.getXRulesNCBDAO();
                for (int i = 0; i < vXRulesNCB.size(); i++) {
                    xRulesNCBDAO.deleteModelXRulesNCBM((XRulesNCBDataM) vXRulesNCB.get(i));
                }
            }
            //====================================
            //			================Phone Verification==========================
            Vector vXRulesPhoneVerification = prmXRulesVerificationResultDataM.getVXRulesPhoneVerificationDataM();
            if (vXRulesPhoneVerification != null) {
                XRulesPhoneVerificationDAO xRulesPhoneVerificationAO = ORIGDAOFactory.getXRulesPhoneVerificationDAO();
                for (int i = 0; i < vXRulesPhoneVerification.size(); i++) {
                    xRulesPhoneVerificationAO.deleteModelXRulesPhoneVerificationM((XRulesPhoneVerificationDataM) vXRulesPhoneVerification.get(i));
                }
            }
            //====================================
            //			================Policy rule==========================
            Vector vXRulesPolicyRules = prmXRulesVerificationResultDataM.getVXRulesPolicyRulesDataM();

            if (vXRulesPolicyRules != null) {
                XRulesPolicyRulesDAO xRulesPolicyRulesDAO = ORIGDAOFactory.getXRulesPolicyRulesDAO();
                for (int i = 0; i < vXRulesPolicyRules.size(); i++) {
                    xRulesPolicyRulesDAO.deleteModelXRulesPolicyRulesM((XRulesPolicyRulesDataM) vXRulesPolicyRules.get(i));
                }
            }
            //====================================
            //======================NCB Adjust===================================
            Vector vNCBAdjust=prmXRulesVerificationResultDataM.getVNCBAdjust();
            if(vNCBAdjust!=null){
                XRulesNCBAdjustDAO xRulesNCBAdjustDAO=ORIGDAOFactory.getXRulesNCBAdjustDAO();
                for (int i = 0; i < vNCBAdjust.size(); i++) {                    
                    XRulesNCBAdjustDataM  prmXRulesNCBAdjustDataM=(XRulesNCBAdjustDataM)vNCBAdjust.get(i);
                    xRulesNCBAdjustDAO.deleteModelXRulesNCBAdjustM(prmXRulesNCBAdjustDataM); 
                }
            
            }
            //========================================================================
            //================Existcust Surname==========================
            Vector vXRulesExistcustSurname = prmXRulesVerificationResultDataM.getVXRulesExistcustSurnameDataM();
            if (vXRulesExistcustSurname != null) {
                XRulesExistcustSurnameDAO xRulesExistcustSurnameDAO = ORIGDAOFactory.getXRulesExistcustSurnameDAO();
                for (int i = 0; i < vXRulesExistcustSurname.size(); i++) {
                    xRulesExistcustSurnameDAO.deleteModelXRulesExistcustM((XRulesExistcustDataM) vXRulesExistcustSurname.get(i));
                }
            }
            //================Existcust Inprogress Surname==========================
            Vector vXRulesExistcustInprogressSurname = prmXRulesVerificationResultDataM.getVXRulesExistcustInprogressSurnameDataM();
            if (vXRulesExistcustInprogressSurname != null) {
                XRulesExistcustInprogressSurnameDAO xRulesExistcustInprogressSurnameDAO = ORIGDAOFactory.getXRulesExistcustInprogressSurnameDAO();
                for (int i = 0; i < vXRulesExistcustInprogressSurname.size(); i++) {
                    xRulesExistcustInprogressSurnameDAO.deleteModelXRulesExistcustInprogressM((XRulesExistcustInprogressDataM) vXRulesExistcustInprogressSurname.get(i));
                }
            }                                    
            //====================================
            deleteTableXRULES_VERIFICATION_RESULT(prmXRulesVerificationResultDataM);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesVerificationResultException(e.getMessage());
        }

    }

    /**
     * @param XRulesDebBdDataM
     */
    private void deleteTableXRULES_VERIFICATION_RESULT(XRulesVerificationResultDataM XRulesDebBdDataM) throws XRulesVerificationResultException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_VERIFICATION_RESULT ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, XRulesDebBdDataM.getApplicationRecordId());
            ps.setString(2, XRulesDebBdDataM.getCmpCode());
            ps.setString(3, XRulesDebBdDataM.getIdNo());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesVerificationResultException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesVerificationResultException(e.getMessage());
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAO#loadModelXRulesVerificationResultM(java.lang.String)
     */
    public XRulesVerificationResultDataM loadModelXRulesVerificationResultM(String personalID)
            throws XRulesVerificationResultException {
        try {
            log.debug("loadModelXRulesVerificationResultM  personalID " + personalID);
            if (personalID == null) {
            	personalID = "";
            }
            XRulesVerificationResultDataM xRulesVerResult = selectTableXRULES_VERIFICATION_RESULT(personalID);
            //xRulesVerResult
            if (xRulesVerResult != null) {
                xRulesVerResult.setVXRulesBlacklistDataM(ORIGDAOFactory.getXRulesBlacklistDAO().loadModelXRulesBlacklistM(personalID));
                xRulesVerResult.setVXRulesBlacklistVehicleDataM(ORIGDAOFactory.getXRulesBlacklistVehicleDAO().loadModelXRulesBlacklistVehicleM(personalID));

                xRulesVerResult.setXRulesDebtBdDataM(ORIGDAOFactory.getXRulesDebtBdDAO().loadModelXRulesDebBdM(personalID));

                xRulesVerResult.setVXRulesDedupDataM(ORIGDAOFactory.getXRulesDedupDAO().loadModelXRulesDedupM(personalID));
                xRulesVerResult.setVXRulesDedupVehicleDataM(ORIGDAOFactory.getXRulesDedupVehicleDAO().loadModelXRulesDedupVenhicleM(personalID));
                xRulesVerResult.setVXRulesExistcustDataM(ORIGDAOFactory.getXRulesExistcustDAO().loadModelXRulesExistcustM(personalID));
                xRulesVerResult.setVXRulesExistcustInprogressDataM(ORIGDAOFactory.getXRulesExistcustInprogressDAO().loadModelXRulesExistcustInprogressM(personalID));
                xRulesVerResult.setVXRulesLMPDataM(ORIGDAOFactory.getXRulesLPMDAO().loadModelXRulesLPMM(personalID));
                xRulesVerResult.setVXRulesNCBDataM(ORIGDAOFactory.getXRulesNCBDAO().loadModelXRulesNCBM(personalID));
                xRulesVerResult.setVXRulesPhoneVerificationDataM(ORIGDAOFactory.getXRulesPhoneVerificationDAO().loadModelXRulesPhoneVerificationM(personalID));
                xRulesVerResult.setVXRulesPolicyRulesDataM(ORIGDAOFactory.getXRulesPolicyRulesDAO().loadModelXRulesPolicyRulesM(personalID));
                xRulesVerResult.setXrulesFICODataM(ORIGDAOFactory.getXRulesFICODAO().loadModelXRulesFICODataM(personalID));
                xRulesVerResult.setVNCBAdjust(ORIGDAOFactory.getXRulesNCBAdjustDAO().loadModelXRulesNCBAdjustM(personalID, xRulesVerResult.getNCBTrackingCode()));
                xRulesVerResult.setVXRulesExistcustSurnameDataM(ORIGDAOFactory.getXRulesExistcustSurnameDAO().loadModelXRulesExistcustM(personalID));
                xRulesVerResult.setVXRulesExistcustInprogressSurnameDataM(ORIGDAOFactory.getXRulesExistcustInprogressSurnameDAO().loadModelXRulesExistcustInprogressM(personalID));
            }
            return xRulesVerResult;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesVerificationResultException(e.getMessage());
        }
    }

    /**
     * @param applicationRecordId
     * @return
     */
    private XRulesVerificationResultDataM selectTableXRULES_VERIFICATION_RESULT(String personalID)
            throws XRulesVerificationResultException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            //sql.append("SELECT CMPCDE,IDNO,BL_RESULT ,BL_VEHICLE_RESULT ,EXISTCUST_RESULT  ");
            sql.append("SELECT PERSONAL_ID,BL_RESULT ,BL_VEHICLE_RESULT ,EXISTCUST_RESULT  ");
            sql.append("  ,DEDUP_RESULT ,DEDUP_VEHICLE_RESULT  ,LPM_RESULT ,POLICY_RULES_RESULT,DEBT_BD_RESULT ");
            sql.append("   ,DEBT_BD_PARAM,KHONTHAI_RESULT,PHONEVER_RESULT,YELLOW_PAGES_RESULT,NCB_RESULT");
            sql.append("  ,NCB_COLOR, KHONTHAI_COMMENT,YELLOW_PAGES_COMMENT ,LPM_COMMENT,DEBT_BD_SCORE ");
            sql.append("  ,FINAL_POLICY_RULES_RESULT,PHONEBOOK_RESULT ,PHONEBOOK_COMMENT,NCB_TRACKING_CODE,FICO_RESULT    ");
            sql.append(" ,BLACKLIST_UPDATE_DATE,BLACKLIST_UPDATE_BY,BLACKLIST_VEHICLE_UPDATE_DATE,BLACKLIST_VEHICLE_UPDATE_BY,DEBT_BD_UPDATE_DATE ");
            sql.append(" ,DEBT_BD_UPDATE_BY,DEDUP_UPDATE_DATE,DEDUP_UPDATE_BY ,EXISTCUST_UPDATE_DATE ,EXISTCUST_UPDATE_BY");
            sql.append(" ,FICO_UPDATE_DATE ,FICO_UPDATE_BY , LPM_UPDATE_DATE,LPM_UPDATE_BY , NCB_UPDATE_DATE  ");
            sql.append(" , NCB_UPDATE_BY,PHONE_VER_UPDATE_DATE, PHONE_VER_UPDATE_BY,DEDUP_VEHICLE_UPDATE_DATE,DEDUP_VEHICLE_UPDATE_BY ");
            sql.append(" ,POLICY_RULES_UPDATE_DATE ,POLICY_RULES_UPDATE_BY ,DEBT_AMT_RESULT,DEBT_AMT_OD_INT_FLAG,THAIREGISTRATION_RESULT  ");
            sql.append(" ,THAIREGISTRATION_COMMENT, BOL_RESULT, BOL_COMMENT,  DEBT_AMT_UPDATE_DATE, DEBT_AMT_UPDATE_BY ");
            sql.append(" ,THAIREGISTRATION_UPDATE_DATE,  THAIREGISTRATION_UPDATE_BY ,  BOL_UPDATE_DATE  ,  BOL_UPDATE_BY  ,KHONTHAI_UPDATE_DATE  ");
            sql.append(" ,  KHONTHAI_UPDATE_BY ,YELLOWPAGES_UPDATE_DATE ,YELLOWPAGES_UPDATE_BY ,PHONEBOOK_UPDATE_DATE , PHONEBOOK_UPDATE_BY ");
            sql.append(" ,BLACKLIST_CUST_UPDATE_ROLE , BLACKLIST_VEHICLE_UPDATE_ROLE , EXISTINGCUST_UPDATE_ROLE , DEDUP_APP_UPDATE_ROLE , DEDUP_VEHICLE_UPDATE_ROLE");
            sql.append(" ,LPM_UPDATE_ROLE , POLICY_RULES_UPDATE_ROLE  ,  DEBT_AMT_UPDATE_ROLE ,  DEBTBD_UPDATE_ROLE , FICO_UPDATE_ROLE   ");
            sql.append(" ,PHONE_VER_UPDATE_ROLE , PHONE_BOOK_UPDATE_ROLE , YELLOWS_PAGES_UPDATE_ROLE  , KHONTHAI_UPDATE_ROLE , BOL_UPDATE_ROLE");
            sql.append(" ,THAIREGISTRATION_UPDATE_ROLE,DEBT_AMT_ADJUST_RESULT,EILIGIBILITY_RESULT,LTV_RESULT  ");                        
            sql.append(" FROM XRULES_VERIFICATION_RESULT WHERE PERSONAL_ID = ? ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            rs = null;
            ps.setString(1, personalID);
            /*ps.setString(2, cmpcde);
            ps.setString(3, idno);*/
            rs = ps.executeQuery();
            XRulesVerificationResultDataM prmXRulesVerificationResultDataM = null;
            if (rs.next()) {
                prmXRulesVerificationResultDataM = new XRulesVerificationResultDataM();
                /*prmXRulesVerificationResultDataM.setApplicationRecordId(applicationRecordId);
                prmXRulesVerificationResultDataM.setCmpCode(rs.getString(1));
                prmXRulesVerificationResultDataM.setIdNo(rs.getString(2));
                prmXRulesVerificationResultDataM.setBLResult(rs.getString(3));
                prmXRulesVerificationResultDataM.setBLVehicleResult(rs.getString(4));
                log.debug("loadModelXRulesVerificationResultM  rs.getString(5) " + rs.getString(5));
                prmXRulesVerificationResultDataM.setExistCustResult(rs.getString(5));
                prmXRulesVerificationResultDataM.setDedupResult(rs.getString(6));
                prmXRulesVerificationResultDataM.setDedupVehicleResult(rs.getString(7));
                prmXRulesVerificationResultDataM.setLPMResult(rs.getString(8));
                prmXRulesVerificationResultDataM.setPolicyRulesResult(rs.getString(9));
                prmXRulesVerificationResultDataM.setDEBT_BDResult(rs.getString(10));
                prmXRulesVerificationResultDataM.setDEBT_BD_PARAM(rs.getBigDecimal(11));
                prmXRulesVerificationResultDataM.setKhonThaiResult(rs.getString(12));
                prmXRulesVerificationResultDataM.setPhoneVerResult(rs.getString(13));
                prmXRulesVerificationResultDataM.setYellowPageResult(rs.getString(14));
                prmXRulesVerificationResultDataM.setNCBResult(rs.getString(15));
                prmXRulesVerificationResultDataM.setNCBColor(rs.getString(16));
                prmXRulesVerificationResultDataM.setKhonThaiComment(rs.getString(17));
                prmXRulesVerificationResultDataM.setYellowPageComment(rs.getString(18));
                // prmXRulesVerificationResultDataM.setDEPT_BDScore(rs
                //       .getBigDecimal(19));
                prmXRulesVerificationResultDataM.setLPMcomment(rs.getString(19));
                prmXRulesVerificationResultDataM.setDEBT_BDScore(rs.getBigDecimal(20));
                prmXRulesVerificationResultDataM.setFinalpolicyRulesResult(rs.getString(21));
                prmXRulesVerificationResultDataM.setPhoneBookResult(rs.getString(22));
                prmXRulesVerificationResultDataM.setPhoneBookComment(rs.getString(23));
                prmXRulesVerificationResultDataM.setNCBTrackingCode(rs.getString(24));
                prmXRulesVerificationResultDataM.setFicoResult(rs.getString(25));

                prmXRulesVerificationResultDataM.setBlacklistUpdateDate(rs.getTimestamp(26));
                prmXRulesVerificationResultDataM.setBlacklistUpdateBy(rs.getString(27));
                prmXRulesVerificationResultDataM.setBlacklistVehcieUpdateDate(rs.getTimestamp(28));
                prmXRulesVerificationResultDataM.setBlacklistVehicleUpdateBy(rs.getString(29));
                prmXRulesVerificationResultDataM.setDebtBdUpdateDate(rs.getTimestamp(30));
                prmXRulesVerificationResultDataM.setDebtBdUpdateBy(rs.getString(31));
                prmXRulesVerificationResultDataM.setDedupUpdateDate(rs.getTimestamp(32));
                prmXRulesVerificationResultDataM.setDedupUpdateBy(rs.getString(33));
                prmXRulesVerificationResultDataM.setExistingCustUpdateDate(rs.getTimestamp(34));
                prmXRulesVerificationResultDataM.setExistCustUpdateBy(rs.getString(35));
                prmXRulesVerificationResultDataM.setFicoUpdateDate(rs.getTimestamp(36));
                prmXRulesVerificationResultDataM.setFicoUpdateBy(rs.getString(37));
                prmXRulesVerificationResultDataM.setLpmUpdateDate(rs.getTimestamp(38));
                prmXRulesVerificationResultDataM.setLpmUpdateBy(rs.getString(39));
                prmXRulesVerificationResultDataM.setNcbUpdateDate(rs.getTimestamp(40));
                prmXRulesVerificationResultDataM.setNcbUpdateBy(rs.getString(41));
                prmXRulesVerificationResultDataM.setPhoneVerUpdateDate(rs.getTimestamp(42));
                prmXRulesVerificationResultDataM.setPhoneVerUpdateBy(rs.getString(43));
                prmXRulesVerificationResultDataM.setDupVehicleUpdateDate(rs.getTimestamp(44));
                prmXRulesVerificationResultDataM.setDupVehicleUpdateBy(rs.getString(45));
                prmXRulesVerificationResultDataM.setPolicyRulesUpdateDate(rs.getTimestamp(46));
                prmXRulesVerificationResultDataM.setPolicyRulesUpdateBy(rs.getString(47));
                prmXRulesVerificationResultDataM.setDebtAmountResult(rs.getBigDecimal(48));
                prmXRulesVerificationResultDataM.setDebtAmountODInterestFlag(rs.getString(49));
                prmXRulesVerificationResultDataM.setThaiRegistrationResult(rs.getString(50));
                prmXRulesVerificationResultDataM.setThaiRegistrationComment(rs.getString(51));
                prmXRulesVerificationResultDataM.setBOLResult(rs.getString(52));
                prmXRulesVerificationResultDataM.setBOLComment(rs.getString(53));
                prmXRulesVerificationResultDataM.setDebtAmountUpdateDate(rs.getTimestamp(54));
                prmXRulesVerificationResultDataM.setDebtAmountUpdateBy(rs.getString(55));
                prmXRulesVerificationResultDataM.setThaiRegistrationUpdateDate(rs.getTimestamp(56));
                prmXRulesVerificationResultDataM.setThaiRegistrationUpdateBy(rs.getString(57));
                prmXRulesVerificationResultDataM.setBolUpdateDate(rs.getTimestamp(58));
                prmXRulesVerificationResultDataM.setBolUpdateBy(rs.getString(59));
                prmXRulesVerificationResultDataM.setKhonthaiUpdateDate(rs.getTimestamp(60));
                prmXRulesVerificationResultDataM.setKhonthaiUpdateBy(rs.getString(61));
                prmXRulesVerificationResultDataM.setYellowsPagesUpdateDate(rs.getTimestamp(62));
                prmXRulesVerificationResultDataM.setYellowsPagesUpdateBy(rs.getString(63));
                prmXRulesVerificationResultDataM.setPhonebookUpdateDate(rs.getTimestamp(64));
                prmXRulesVerificationResultDataM.setPhonebookUpdateBy(rs.getString(65));
                prmXRulesVerificationResultDataM.setBlacklistCustomerUpdateRole(rs.getString(66));
                prmXRulesVerificationResultDataM.setBlacklistVehicleUpdateRole(rs.getString(67));
                prmXRulesVerificationResultDataM.setExistingCustomerUpdateRole(rs.getString(68));
                prmXRulesVerificationResultDataM.setDedupUpdateRole(rs.getString(69));
                prmXRulesVerificationResultDataM.setDedupVehicleUpdateRole(rs.getString(70));             
                prmXRulesVerificationResultDataM.setLpmUpdateRole(rs.getString(71));
                prmXRulesVerificationResultDataM.setPolicyRulesUpdateRole(rs.getString(72));
                prmXRulesVerificationResultDataM.setDebtAmtUpdateRole(rs.getString(73));
                prmXRulesVerificationResultDataM.setDebtbdUpdateRole(rs.getString(74));
                prmXRulesVerificationResultDataM.setFicoUpdateRole(rs.getString(75));
                prmXRulesVerificationResultDataM.setPhoneVerUpdateRole(rs.getString(76));
                prmXRulesVerificationResultDataM.setPhoneBookUpdateRole( rs.getString(77));
                prmXRulesVerificationResultDataM.setYellowsPageUpdateRole(rs.getString(78));
                prmXRulesVerificationResultDataM.setKhonthaiUpdteRole(rs.getString(79));
                prmXRulesVerificationResultDataM.setBolUpdateRole(rs.getString(80));
                prmXRulesVerificationResultDataM.setThaiRegistrationUpdateRole(rs.getString(81));  
                prmXRulesVerificationResultDataM.setDebtAmountAdjustResult(rs.getBigDecimal(82));
                prmXRulesVerificationResultDataM.setEligibilityResult(rs.getString(83));
                prmXRulesVerificationResultDataM.setLtvResult(rs.getString(84)); */
                prmXRulesVerificationResultDataM.setPersonalID(rs.getString(1));
                prmXRulesVerificationResultDataM.setBLResult(rs.getString(2));
                prmXRulesVerificationResultDataM.setBLVehicleResult(rs.getString(3));
                log.debug("loadModelXRulesVerificationResultM  rs.getString(5) " + rs.getString(4));
                prmXRulesVerificationResultDataM.setExistCustResult(rs.getString(4));
                prmXRulesVerificationResultDataM.setDedupResult(rs.getString(5));
                prmXRulesVerificationResultDataM.setDedupVehicleResult(rs.getString(6));
                prmXRulesVerificationResultDataM.setLPMResult(rs.getString(7));
                prmXRulesVerificationResultDataM.setPolicyRulesResult(rs.getString(8));
                prmXRulesVerificationResultDataM.setDEBT_BDResult(rs.getString(9));
                prmXRulesVerificationResultDataM.setDEBT_BD_PARAM(rs.getBigDecimal(10));
                prmXRulesVerificationResultDataM.setKhonThaiResult(rs.getString(11));
                prmXRulesVerificationResultDataM.setPhoneVerResult(rs.getString(12));
                prmXRulesVerificationResultDataM.setYellowPageResult(rs.getString(13));
                prmXRulesVerificationResultDataM.setNCBResult(rs.getString(14));
                prmXRulesVerificationResultDataM.setNCBColor(rs.getString(15));
                prmXRulesVerificationResultDataM.setKhonThaiComment(rs.getString(16));
                prmXRulesVerificationResultDataM.setYellowPageComment(rs.getString(17));
                // prmXRulesVerificationResultDataM.setDEPT_BDScore(rs
                //       .getBigDecimal(19));
                prmXRulesVerificationResultDataM.setLPMcomment(rs.getString(18));
                prmXRulesVerificationResultDataM.setDEBT_BDScore(rs.getBigDecimal(19));
                prmXRulesVerificationResultDataM.setFinalpolicyRulesResult(rs.getString(20));
                prmXRulesVerificationResultDataM.setPhoneBookResult(rs.getString(21));
                prmXRulesVerificationResultDataM.setPhoneBookComment(rs.getString(22));
                prmXRulesVerificationResultDataM.setNCBTrackingCode(rs.getString(23));
                prmXRulesVerificationResultDataM.setFicoResult(rs.getString(24));

                prmXRulesVerificationResultDataM.setBlacklistUpdateDate(rs.getTimestamp(25));
                prmXRulesVerificationResultDataM.setBlacklistUpdateBy(rs.getString(26));
                prmXRulesVerificationResultDataM.setBlacklistVehcieUpdateDate(rs.getTimestamp(27));
                prmXRulesVerificationResultDataM.setBlacklistVehicleUpdateBy(rs.getString(28));
                prmXRulesVerificationResultDataM.setDebtBdUpdateDate(rs.getTimestamp(29));
                prmXRulesVerificationResultDataM.setDebtBdUpdateBy(rs.getString(30));
                prmXRulesVerificationResultDataM.setDedupUpdateDate(rs.getTimestamp(31));
                prmXRulesVerificationResultDataM.setDedupUpdateBy(rs.getString(32));
                prmXRulesVerificationResultDataM.setExistingCustUpdateDate(rs.getTimestamp(33));
                prmXRulesVerificationResultDataM.setExistCustUpdateBy(rs.getString(34));
                prmXRulesVerificationResultDataM.setFicoUpdateDate(rs.getTimestamp(35));
                prmXRulesVerificationResultDataM.setFicoUpdateBy(rs.getString(36));
                prmXRulesVerificationResultDataM.setLpmUpdateDate(rs.getTimestamp(37));
                prmXRulesVerificationResultDataM.setLpmUpdateBy(rs.getString(38));
                prmXRulesVerificationResultDataM.setNcbUpdateDate(rs.getTimestamp(39));
                prmXRulesVerificationResultDataM.setNcbUpdateBy(rs.getString(40));
                prmXRulesVerificationResultDataM.setPhoneVerUpdateDate(rs.getTimestamp(41));
                prmXRulesVerificationResultDataM.setPhoneVerUpdateBy(rs.getString(42));
                prmXRulesVerificationResultDataM.setDupVehicleUpdateDate(rs.getTimestamp(43));
                prmXRulesVerificationResultDataM.setDupVehicleUpdateBy(rs.getString(44));
                prmXRulesVerificationResultDataM.setPolicyRulesUpdateDate(rs.getTimestamp(45));
                prmXRulesVerificationResultDataM.setPolicyRulesUpdateBy(rs.getString(46));
                prmXRulesVerificationResultDataM.setDebtAmountResult(rs.getBigDecimal(47));
                prmXRulesVerificationResultDataM.setDebtAmountODInterestFlag(rs.getString(48));
                prmXRulesVerificationResultDataM.setThaiRegistrationResult(rs.getString(49));
                prmXRulesVerificationResultDataM.setThaiRegistrationComment(rs.getString(50));
                prmXRulesVerificationResultDataM.setBOLResult(rs.getString(51));
                prmXRulesVerificationResultDataM.setBOLComment(rs.getString(52));
                prmXRulesVerificationResultDataM.setDebtAmountUpdateDate(rs.getTimestamp(53));
                prmXRulesVerificationResultDataM.setDebtAmountUpdateBy(rs.getString(54));
                prmXRulesVerificationResultDataM.setThaiRegistrationUpdateDate(rs.getTimestamp(55));
                prmXRulesVerificationResultDataM.setThaiRegistrationUpdateBy(rs.getString(56));
                prmXRulesVerificationResultDataM.setBolUpdateDate(rs.getTimestamp(57));
                prmXRulesVerificationResultDataM.setBolUpdateBy(rs.getString(58));
                prmXRulesVerificationResultDataM.setKhonthaiUpdateDate(rs.getTimestamp(59));
                prmXRulesVerificationResultDataM.setKhonthaiUpdateBy(rs.getString(60));
                prmXRulesVerificationResultDataM.setYellowsPagesUpdateDate(rs.getTimestamp(61));
                prmXRulesVerificationResultDataM.setYellowsPagesUpdateBy(rs.getString(62));
                prmXRulesVerificationResultDataM.setPhonebookUpdateDate(rs.getTimestamp(63));
                prmXRulesVerificationResultDataM.setPhonebookUpdateBy(rs.getString(64));
                prmXRulesVerificationResultDataM.setBlacklistCustomerUpdateRole(rs.getString(65));
                prmXRulesVerificationResultDataM.setBlacklistVehicleUpdateRole(rs.getString(66));
                prmXRulesVerificationResultDataM.setExistingCustomerUpdateRole(rs.getString(67));
                prmXRulesVerificationResultDataM.setDedupUpdateRole(rs.getString(68));
                prmXRulesVerificationResultDataM.setDedupVehicleUpdateRole(rs.getString(69));             
                prmXRulesVerificationResultDataM.setLpmUpdateRole(rs.getString(70));
                prmXRulesVerificationResultDataM.setPolicyRulesUpdateRole(rs.getString(71));
                prmXRulesVerificationResultDataM.setDebtAmtUpdateRole(rs.getString(72));
                prmXRulesVerificationResultDataM.setDebtbdUpdateRole(rs.getString(73));
                prmXRulesVerificationResultDataM.setFicoUpdateRole(rs.getString(74));
                prmXRulesVerificationResultDataM.setPhoneVerUpdateRole(rs.getString(75));
                prmXRulesVerificationResultDataM.setPhoneBookUpdateRole( rs.getString(76));
                prmXRulesVerificationResultDataM.setYellowsPageUpdateRole(rs.getString(77));
                prmXRulesVerificationResultDataM.setKhonthaiUpdteRole(rs.getString(78));
                prmXRulesVerificationResultDataM.setBolUpdateRole(rs.getString(79));
                prmXRulesVerificationResultDataM.setThaiRegistrationUpdateRole(rs.getString(80));  
                prmXRulesVerificationResultDataM.setDebtAmountAdjustResult(rs.getBigDecimal(81));
                prmXRulesVerificationResultDataM.setEligibilityResult(rs.getString(82));
                prmXRulesVerificationResultDataM.setLtvResult(rs.getString(83));
            }
            return prmXRulesVerificationResultDataM;
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesVerificationResultException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAO#saveUpdateModelXRulesVerificationResultM(com.eaf.xrules.shared.model.XRulesVerificationResultDataM)
     */
    public void saveUpdateModelXRulesVerificationResultM(XRulesVerificationResultDataM prmXRulesVerificationResultDataM)
            throws XRulesVerificationResultException {
        double returnRows = 0;log.debug(">>>>>>>>>>>>>>>>>>>..saveUpdateModelXRulesVerificationResultM" + prmXRulesVerificationResultDataM.getPersonalID());
        returnRows = updateTableXRULES_VERIFICATION_RESULT(prmXRulesVerificationResultDataM);
        if (returnRows == 0) {
            log.debug("New record then can't update record in table XRULES_VERIFICATION_RESULT then call Insert method");
            createTableXRULES_VERIFICATION_RESULT(prmXRulesVerificationResultDataM);
        }
        //update other xrule
        this.setVerificationKey(prmXRulesVerificationResultDataM);
        try {
            if (prmXRulesVerificationResultDataM.getVXRulesBlacklistDataM() != null) {
                ORIGDAOFactory.getXRulesBlacklistDAO().saveUpdateModelXRulesBlacklistM(prmXRulesVerificationResultDataM.getVXRulesBlacklistDataM());
            }
            if (prmXRulesVerificationResultDataM.getVXRulesBlacklistVehicleDataM() != null) {
                ORIGDAOFactory.getXRulesBlacklistVehicleDAO().saveUpdateModelXRulesBlacklistVehicleM(prmXRulesVerificationResultDataM.getVXRulesBlacklistVehicleDataM());
            }
            if (prmXRulesVerificationResultDataM.getXRulesDebtBdDataM() != null) {
                ORIGDAOFactory.getXRulesDebtBdDAO().saveUpdateModelXRulesDebBdM(prmXRulesVerificationResultDataM.getXRulesDebtBdDataM());
            }
            if (prmXRulesVerificationResultDataM.getVXRulesDedupDataM() != null) {
                ORIGDAOFactory.getXRulesDedupDAO().saveUpdateModelXRulesDedupM(prmXRulesVerificationResultDataM.getVXRulesDedupDataM());
            }
            if (prmXRulesVerificationResultDataM.getVXRulesDedupVehicleDataM() != null) {
                ORIGDAOFactory.getXRulesDedupVehicleDAO().saveUpdateModelXRulesDedupVenhicleM(prmXRulesVerificationResultDataM.getVXRulesDedupVehicleDataM());
            }
            if (prmXRulesVerificationResultDataM.getVXRulesExistcustDataM() != null) {
                ORIGDAOFactory.getXRulesExistcustDAO().saveUpdateModelXRulesExistcustM(prmXRulesVerificationResultDataM.getVXRulesExistcustDataM());
            }
            Vector vXRulesExistcustInprogres = prmXRulesVerificationResultDataM.getVXRulesExistcustInprogressDataM();
            if (vXRulesExistcustInprogres != null) {
                XRulesExistcustInprogressDAO xRulesExistcustInprogressDAO = ORIGDAOFactory.getXRulesExistcustInprogressDAO();
                for (int i = 0; i < vXRulesExistcustInprogres.size(); i++)
                    xRulesExistcustInprogressDAO.saveUpdateModelXRulesExistcustInprogressM((XRulesExistcustInprogressDataM) vXRulesExistcustInprogres.get(i));
            }
            if (prmXRulesVerificationResultDataM.getVXRulesLMPDataM() != null) {
                ORIGDAOFactory.getXRulesLPMDAO().saveUpdateModelXRulesLPMM(prmXRulesVerificationResultDataM.getVXRulesLMPDataM());
            }
            if (prmXRulesVerificationResultDataM.getVXRulesNCBDataM() != null) {
                ORIGDAOFactory.getXRulesNCBDAO().saveUpdateModelXRulesNCBM(prmXRulesVerificationResultDataM.getVXRulesNCBDataM());
            }
            if (prmXRulesVerificationResultDataM.getVXRulesPhoneVerificationDataM() != null) {
                ORIGDAOFactory.getXRulesPhoneVerificationDAO().saveUpdateModelXRulesPhoneVerificationM(
                        prmXRulesVerificationResultDataM.getVXRulesPhoneVerificationDataM());
            }

            Vector vXRulesPolicyRule = prmXRulesVerificationResultDataM.getVXRulesPolicyRulesDataM();
            if (vXRulesPolicyRule != null) {
                XRulesPolicyRulesDAO xRulesPolicyRulesDAO = ORIGDAOFactory.getXRulesPolicyRulesDAO();
                for (int i = 0; i < vXRulesPolicyRule.size(); i++) {
                    xRulesPolicyRulesDAO.saveUpdateModelXRulesPolicyRulesM((XRulesPolicyRulesDataM) vXRulesPolicyRule.get(i));
                }
            }
            if (prmXRulesVerificationResultDataM.getXrulesFICODataM() != null) {
                ORIGDAOFactory.getXRulesFICODAO().saveUpdateModelXRulesFICODataM(prmXRulesVerificationResultDataM.getXrulesFICODataM());
            }
            Vector vNCBAdjust= prmXRulesVerificationResultDataM.getVNCBAdjust();
            //Delete old data
            XRulesNCBAdjustDAO xrulesNcbAdjsutDAO=ORIGDAOFactory.getXRulesNCBAdjustDAO();
            xrulesNcbAdjsutDAO.deleteModelXRulesNCBAdjustM(prmXRulesVerificationResultDataM.getPersonalID());
            if(vNCBAdjust!=null){
                xrulesNcbAdjsutDAO.saveUpdateModelXRulesNCBAdjustM(vNCBAdjust);            
            }
            //Existing customer Surname
            if (prmXRulesVerificationResultDataM.getVXRulesExistcustSurnameDataM() != null) {
                ORIGDAOFactory.getXRulesExistcustSurnameDAO().saveUpdateModelXRulesExistcustM(prmXRulesVerificationResultDataM.getVXRulesExistcustSurnameDataM());
            }
            Vector vXRulesExistcustInprogresSurname = prmXRulesVerificationResultDataM.getVXRulesExistcustInprogressSurnameDataM();
            if (vXRulesExistcustInprogresSurname != null) {
                XRulesExistcustInprogressSurnameDAO xRulesExistcustInprogressSurnameDAO = ORIGDAOFactory.getXRulesExistcustInprogressSurnameDAO();
                for (int i = 0; i < vXRulesExistcustInprogresSurname.size(); i++)
                    xRulesExistcustInprogressSurnameDAO.saveUpdateModelXRulesExistcustInprogressM((XRulesExistcustInprogressDataM) vXRulesExistcustInprogresSurname.get(i));
            }                                  
                
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesVerificationResultException(e.getLocalizedMessage());
        }
    }

    /**
     * @param prmXRulesDebBdDataM
     * @return
     */
    private double updateTableXRULES_VERIFICATION_RESULT(XRulesVerificationResultDataM prmXRulesVerificationResultDataM)
            throws XRulesVerificationResultException {
        double returnRows = 0;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("UPDATE XRULES_VERIFICATION_RESULT ");
            sql.append(" SET BL_RESULT=?,BL_VEHICLE_RESULT=? ,EXISTCUST_RESULT=? ,DEDUP_RESULT=? ,DEDUP_VEHICLE_RESULT=?   ");
            sql.append("  ,LPM_RESULT=? ,POLICY_RULES_RESULT=?,DEBT_BD_RESULT=? ,DEBT_BD_PARAM=?,KHONTHAI_RESULT=? ");
            sql.append("  ,PHONEVER_RESULT=?,YELLOW_PAGES_RESULT=?,NCB_RESULT=?,NCB_COLOR=?, KHONTHAI_COMMENT=?  ");
            sql.append("  ,YELLOW_PAGES_COMMENT=?,DEBT_BD_SCORE=?,LPM_COMMENT=?,FINAL_POLICY_RULES_RESULT=?,PHONEBOOK_RESULT=?");
            sql.append("   ,PHONEBOOK_COMMENT=?,NCB_TRACKING_CODE=?,FICO_RESULT=? ");
            sql.append(" ,BLACKLIST_UPDATE_DATE=?,BLACKLIST_UPDATE_BY=?,BLACKLIST_VEHICLE_UPDATE_DATE=?,BLACKLIST_VEHICLE_UPDATE_BY=?,DEBT_BD_UPDATE_DATE=? ");
            sql.append(" ,DEBT_BD_UPDATE_BY=?,DEDUP_UPDATE_DATE=?,DEDUP_UPDATE_BY =?,EXISTCUST_UPDATE_DATE=? ,EXISTCUST_UPDATE_BY=?");
            sql.append(" ,FICO_UPDATE_DATE=? ,FICO_UPDATE_BY=? , LPM_UPDATE_DATE=?,LPM_UPDATE_BY=? , NCB_UPDATE_DATE =? ");
            sql.append(" , NCB_UPDATE_BY=?,PHONE_VER_UPDATE_DATE=?, PHONE_VER_UPDATE_BY=?,DEDUP_VEHICLE_UPDATE_DATE=?,DEDUP_VEHICLE_UPDATE_BY=? ");
            sql.append(" ,POLICY_RULES_UPDATE_DATE=? ,POLICY_RULES_UPDATE_BY =?,DEBT_AMT_RESULT=?,DEBT_AMT_OD_INT_FLAG=? ,THAIREGISTRATION_RESULT=?  ");
            sql.append(" ,THAIREGISTRATION_COMMENT=?, BOL_RESULT=?, BOL_COMMENT=? ,DEBT_AMT_UPDATE_DATE=?, DEBT_AMT_UPDATE_BY=? ");
            sql.append(" ,THAIREGISTRATION_UPDATE_DATE=?,  THAIREGISTRATION_UPDATE_BY=? ,  BOL_UPDATE_DATE =? ,  BOL_UPDATE_BY =? ,KHONTHAI_UPDATE_DATE=? ");
            sql.append(" ,KHONTHAI_UPDATE_BY =?,YELLOWPAGES_UPDATE_DATE =?,YELLOWPAGES_UPDATE_BY =?,PHONEBOOK_UPDATE_DATE =?, PHONEBOOK_UPDATE_BY=?");
            sql.append(" ,BLACKLIST_CUST_UPDATE_ROLE=? , BLACKLIST_VEHICLE_UPDATE_ROLE =?, EXISTINGCUST_UPDATE_ROLE =?, DEDUP_APP_UPDATE_ROLE=? , DEDUP_VEHICLE_UPDATE_ROLE =?");
            sql.append(" ,LPM_UPDATE_ROLE =?, POLICY_RULES_UPDATE_ROLE=?  ,  DEBT_AMT_UPDATE_ROLE=? ,  DEBTBD_UPDATE_ROLE =?, FICO_UPDATE_ROLE=?   ");
            sql.append(" ,PHONE_VER_UPDATE_ROLE=? , PHONE_BOOK_UPDATE_ROLE=? , YELLOWS_PAGES_UPDATE_ROLE =? , KHONTHAI_UPDATE_ROLE=? , BOL_UPDATE_ROLE=?");
            sql.append(" ,THAIREGISTRATION_UPDATE_ROLE =?,DEBT_AMT_ADJUST_RESULT=?,EILIGIBILITY_RESULT =?,LTV_RESULT=? ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
            sql.append(" WHERE PERSONAL_ID=? ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prmXRulesVerificationResultDataM.getBLResult());
            ps.setString(2, prmXRulesVerificationResultDataM.getBLVehicleResult());
            ps.setString(3, prmXRulesVerificationResultDataM.getExistCustResult());
            ps.setString(4, prmXRulesVerificationResultDataM.getDedupResult());
            ps.setString(5, prmXRulesVerificationResultDataM.getDedupVehicleResult());
            ps.setString(6, prmXRulesVerificationResultDataM.getLPMResult());
            ps.setString(7, prmXRulesVerificationResultDataM.getPolicyRulesResult());
            ps.setString(8, prmXRulesVerificationResultDataM.getDEBT_BDResult());
            ps.setBigDecimal(9, prmXRulesVerificationResultDataM.getDEBT_BD_PARAM());
            ps.setString(10, prmXRulesVerificationResultDataM.getKhonThaiResult());
            ps.setString(11, prmXRulesVerificationResultDataM.getPhoneVerResult());
            ps.setString(12, prmXRulesVerificationResultDataM.getYellowPageResult());
            ps.setString(13, prmXRulesVerificationResultDataM.getNCBResult());
            ps.setString(14, prmXRulesVerificationResultDataM.getNCBColor());
            ps.setString(15, prmXRulesVerificationResultDataM.getKhonThaiComment());
            ps.setString(16, prmXRulesVerificationResultDataM.getYellowPageComment());
            ps.setBigDecimal(17, prmXRulesVerificationResultDataM.getDEBT_BDScore());
            ps.setString(18, prmXRulesVerificationResultDataM.getLPMcomment());
            ps.setString(19, prmXRulesVerificationResultDataM.getFinalpolicyRulesResult());
            ps.setString(20, prmXRulesVerificationResultDataM.getPhoneBookResult());
            ps.setString(21, prmXRulesVerificationResultDataM.getPhoneBookComment());
            ps.setString(22, prmXRulesVerificationResultDataM.getNCBTrackingCode());
            ps.setString(23, prmXRulesVerificationResultDataM.getFicoResult());

            ps.setTimestamp(24, prmXRulesVerificationResultDataM.getBlacklistUpdateDate());
            ps.setString(25, prmXRulesVerificationResultDataM.getBlacklistUpdateBy());
            ps.setTimestamp(26, prmXRulesVerificationResultDataM.getBlacklistVehcieUpdateDate());
            ps.setString(27, prmXRulesVerificationResultDataM.getBlacklistVehicleUpdateBy());
            ps.setTimestamp(28, prmXRulesVerificationResultDataM.getDebtBdUpdateDate());
            ps.setString(29, prmXRulesVerificationResultDataM.getDebtBdUpdateBy());
            ps.setTimestamp(30, prmXRulesVerificationResultDataM.getDedupUpdateDate());
            ps.setString(31, prmXRulesVerificationResultDataM.getDedupUpdateBy());
            ps.setTimestamp(32, prmXRulesVerificationResultDataM.getExistingCustUpdateDate());
            ps.setString(33, prmXRulesVerificationResultDataM.getExistCustUpdateBy());
            ps.setTimestamp(34, prmXRulesVerificationResultDataM.getFicoUpdateDate());
            ps.setString(35, prmXRulesVerificationResultDataM.getFicoUpdateBy());
            ps.setTimestamp(36, prmXRulesVerificationResultDataM.getLpmUpdateDate());
            ps.setString(37, prmXRulesVerificationResultDataM.getLpmUpdateBy());
            ps.setTimestamp(38, prmXRulesVerificationResultDataM.getNcbUpdateDate());
            ps.setString(39, prmXRulesVerificationResultDataM.getNcbUpdateBy());
            ps.setTimestamp(40, prmXRulesVerificationResultDataM.getPhoneVerUpdateDate());
            ps.setString(41, prmXRulesVerificationResultDataM.getPhoneVerUpdateBy());
            ps.setTimestamp(42, prmXRulesVerificationResultDataM.getDupVehicleUpdateDate());
            ps.setString(43, prmXRulesVerificationResultDataM.getDupVehicleUpdateBy());
            ps.setTimestamp(44, prmXRulesVerificationResultDataM.getPolicyRulesUpdateDate());
            ps.setString(45, prmXRulesVerificationResultDataM.getPolicyRulesUpdateBy());
            ps.setBigDecimal(46, prmXRulesVerificationResultDataM.getDebtAmountResult());
            ps.setString(47, prmXRulesVerificationResultDataM.getDebtAmountODInterestFlag());
            ps.setString(48,prmXRulesVerificationResultDataM.getThaiRegistrationResult());
            ps.setString(49,prmXRulesVerificationResultDataM.getThaiRegistrationComment());
            ps.setString(50,prmXRulesVerificationResultDataM.getBOLResult());
            ps.setString(51,prmXRulesVerificationResultDataM.getBOLComment());
            ps.setTimestamp(52,prmXRulesVerificationResultDataM.getDebtAmountUpdateDate());
            ps.setString(53,prmXRulesVerificationResultDataM.getDebtAmountUpdateBy());
            ps.setTimestamp(54,prmXRulesVerificationResultDataM.getThaiRegistrationUpdateDate());
            ps.setString(55,prmXRulesVerificationResultDataM.getThaiRegistrationUpdateBy());
            ps.setTimestamp(56,prmXRulesVerificationResultDataM.getBolUpdateDate());
            ps.setString(57,prmXRulesVerificationResultDataM.getBolUpdateBy());
            ps.setTimestamp(58,prmXRulesVerificationResultDataM.getKhonthaiUpdateDate());
            ps.setString(59,prmXRulesVerificationResultDataM.getKhonthaiUpdateBy());
            ps.setTimestamp(60,prmXRulesVerificationResultDataM.getYellowsPagesUpdateDate());
            ps.setString(61,prmXRulesVerificationResultDataM.getYellowsPagesUpdateBy()); 
            ps.setTimestamp(62,prmXRulesVerificationResultDataM.getPhonebookUpdateDate());
            ps.setString(63,prmXRulesVerificationResultDataM.getPhonebookUpdateBy());
            ps.setString(64,prmXRulesVerificationResultDataM.getBlacklistCustomerUpdateRole());
            ps.setString(65,prmXRulesVerificationResultDataM.getBlacklistVehicleUpdateRole());
            ps.setString(66,prmXRulesVerificationResultDataM.getExistingCustomerUpdateRole());
            ps.setString(67,prmXRulesVerificationResultDataM.getDedupUpdateRole());
            ps.setString(68,prmXRulesVerificationResultDataM.getDedupVehicleUpdateRole());             
            ps.setString(69,prmXRulesVerificationResultDataM.getLpmUpdateRole());
            ps.setString(70,prmXRulesVerificationResultDataM.getPolicyRulesUpdateRole());
            ps.setString(71,prmXRulesVerificationResultDataM.getDebtAmtUpdateRole());
            ps.setString(72,prmXRulesVerificationResultDataM.getDebtbdUpdateRole());
            ps.setString(73,prmXRulesVerificationResultDataM.getFicoUpdateRole());
            ps.setString(74,prmXRulesVerificationResultDataM.getPhoneVerUpdateRole());
            ps.setString(75,prmXRulesVerificationResultDataM.getPhoneBookUpdateRole());
            ps.setString(76,prmXRulesVerificationResultDataM.getYellowsPageUpdateRole());
            ps.setString(77,prmXRulesVerificationResultDataM.getKhonthaiUpdteRole());
            ps.setString(78,prmXRulesVerificationResultDataM.getBolUpdateRole());
            ps.setString(79,prmXRulesVerificationResultDataM.getThaiRegistrationUpdateRole());
            ps.setBigDecimal(80,prmXRulesVerificationResultDataM.getDebtAmountAdjustResult());
            ps.setString(81,prmXRulesVerificationResultDataM.getEligibilityResult());
            ps.setString(82,prmXRulesVerificationResultDataM.getLtvResult());
                        
            //ps.setString(83, prmXRulesVerificationResultDataM.getApplicationRecordId());
            /*ps.setString(84, prmXRulesVerificationResultDataM.getCmpCode());
            ps.setString(85, prmXRulesVerificationResultDataM.getIdNo());*/
            ps.setString(83, prmXRulesVerificationResultDataM.getPersonalID());
            returnRows = ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesVerificationResultException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesDedupDAO#saveUpdateModelXRulesDedupM(java.util.Vector)
     */
    private void setVerificationKey(XRulesVerificationResultDataM prmXRulesVerificationResultDataM) {
        if (prmXRulesVerificationResultDataM != null) {
            /*String prmCmpCde = prmXRulesVerificationResultDataM.getCmpCode();
            String prmIdNo = prmXRulesVerificationResultDataM.getIdNo();
            String prmAppRecId = prmXRulesVerificationResultDataM.getApplicationRecordId();*/
        	String personalID = prmXRulesVerificationResultDataM.getPersonalID();
            // String
            // prmUserName=prmXRulesVerificationResultDataM.getUpdateBy();
            //     ==============Blacklist============================
            Vector vXRulesBlacklist = prmXRulesVerificationResultDataM.getVXRulesBlacklistDataM();
            if (vXRulesBlacklist != null) {
                for (int i = 0; i < vXRulesBlacklist.size(); i++) {
                    XRulesBlacklistDataM prmXRulesBlacklist = (XRulesBlacklistDataM) vXRulesBlacklist.get(i);
                    /*prmXRulesBlacklist.setCmpCde(prmCmpCde);
                    prmXRulesBlacklist.setIdNo(prmIdNo);
                    prmXRulesBlacklist.setApplicationRecordId(prmAppRecId);*/
                    prmXRulesBlacklist.setPersonalID(personalID);
                    //prmXRulesBlacklist.setUpdateBy(prmUserName);
                }
            }
            //==================BlacklistVehicle========================
            Vector vXRulesBlacklistVehicle = prmXRulesVerificationResultDataM.getVXRulesBlacklistVehicleDataM();
            if (vXRulesBlacklistVehicle != null) {
                for (int i = 0; i < vXRulesBlacklistVehicle.size(); i++) {
                    XRulesBlacklistVehicleDataM prmXRulesBlacklistVehicleDataM = (XRulesBlacklistVehicleDataM) vXRulesBlacklistVehicle.get(i);
                    /*prmXRulesBlacklistVehicleDataM.setCmpCde(prmCmpCde);
                    prmXRulesBlacklistVehicleDataM.setIdNo(prmIdNo);
                    prmXRulesBlacklistVehicleDataM.setApplicationRecordId(prmAppRecId);*/
                    prmXRulesBlacklistVehicleDataM.setPersonalID(personalID);
                    // prmXRulesBlacklistVehicleDataM.setUpdateBy(prmUserName);
                }
            }
            //==============Debtbd============================
            XRulesDebtBdDataM prmXRulesDebtBd = prmXRulesVerificationResultDataM.getXRulesDebtBdDataM();
            if (prmXRulesDebtBd != null) {
                /*prmXRulesDebtBd.setIdNo(prmIdNo);
                prmXRulesDebtBd.setCmpCode(prmCmpCde);
                prmXRulesDebtBd.setApplicationRecordId(prmAppRecId);*/
            	prmXRulesDebtBd.setPersonalID(personalID);
                // prmXRulesDebtBd.setUpdateBy(prmUserName);
            }
            //================dedup==========================
            Vector vXRulesDedup = prmXRulesVerificationResultDataM.getVXRulesDedupDataM();
            if (vXRulesDedup != null) {
                for (int i = 0; i < vXRulesDedup.size(); i++) {
                    XRulesDedupDataM prmXrulesDedupDataM = (XRulesDedupDataM) vXRulesDedup.get(i);
                    /*prmXrulesDedupDataM.setIdNo(prmIdNo);
                    prmXrulesDedupDataM.setApplicationRecordId(prmAppRecId);
                    prmXrulesDedupDataM.setCmpCde(prmCmpCde);*/
                    prmXrulesDedupDataM.setPersonalID(personalID);
                    //prmXrulesDedupDataM.setUpdateBy(prmUserName);
                }
            }
            //================Dedup Vehicle==========================
            Vector vXRulesDedupVehicle = prmXRulesVerificationResultDataM.getVXRulesDedupVehicleDataM();
            if (vXRulesDedupVehicle != null) {

                for (int i = 0; i < vXRulesDedupVehicle.size(); i++) {
                    XRulesDedupVehicleDataM prmXRulesDedupVehicleDataM = (XRulesDedupVehicleDataM) vXRulesDedupVehicle.get(i);
                    /*prmXRulesDedupVehicleDataM.setCmpCde(prmCmpCde);
                    prmXRulesDedupVehicleDataM.setIdNo(prmIdNo);
                    prmXRulesDedupVehicleDataM.setApplicationRecordId(prmAppRecId);*/
                    prmXRulesDedupVehicleDataM.setPersonalID(personalID);
                    // prmXRulesDedupVehicleDataM.setUpdateBy(prmUserName);
                }
            }
            //====================================
            //================Existcust==========================
            Vector vXRulesExistcust = prmXRulesVerificationResultDataM.getVXRulesExistcustDataM();
            if (vXRulesExistcust != null) {
                for (int i = 0; i < vXRulesExistcust.size(); i++) {
                    XRulesExistcustDataM prmXRulesExistcustDataM = (XRulesExistcustDataM) vXRulesExistcust.get(i);
                    /*prmXRulesExistcustDataM.setApplicationRecordId(prmAppRecId);
                    prmXRulesExistcustDataM.setCmpCde(prmCmpCde);
                    prmXRulesExistcustDataM.setIdNo(prmIdNo);*/
                    prmXRulesExistcustDataM.setPersonalID(personalID);
                    //  prmXRulesExistcustDataM.setUpdateBy(prmUserName);
                }
            }
            Vector vXRulesExistcustInprogress = prmXRulesVerificationResultDataM.getVXRulesExistcustInprogressDataM();
            if (vXRulesExistcustInprogress != null) {               
                for (int i = 0; i < vXRulesExistcustInprogress.size(); i++) {
                    XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM = (XRulesExistcustInprogressDataM) vXRulesExistcustInprogress.get(i);
                    /*prmXRulesExistcustInprogressDataM.setApplicationRecordId(prmAppRecId);
                    prmXRulesExistcustInprogressDataM.setCmpCde(prmCmpCde);
                    prmXRulesExistcustInprogressDataM.setIdNo(prmIdNo);*/
                    prmXRulesExistcustInprogressDataM.setPersonalID(personalID);
                }
            }
            //====================================
            //			================LPM==========================
            Vector vXRulesLPM = prmXRulesVerificationResultDataM.getVXRulesLMPDataM();
            if (vXRulesLPM != null) {
                for (int i = 0; i < vXRulesLPM.size(); i++) {
                    XRulesLPMDataM prmXRulesLPMDataM = (XRulesLPMDataM) vXRulesLPM.get(i);
                    /*prmXRulesLPMDataM.setApplicationRecordId(prmAppRecId);
                    prmXRulesLPMDataM.setCmpCode(prmCmpCde);
                    prmXRulesLPMDataM.setIdNo(prmIdNo);*/
                    prmXRulesLPMDataM.setPersonalID(personalID);
                    // prmXRulesLPMDataM.setUpdateBy(prmUserName);
                }
            }
            //====================================
            //			================NCB==========================
            Vector vXRulesNCB = prmXRulesVerificationResultDataM.getVXRulesNCBDataM();
            if (vXRulesNCB != null) {
                for (int i = 0; i < vXRulesNCB.size(); i++) {
                    XRulesNCBDataM prmXRulesNCBDataM = (XRulesNCBDataM) vXRulesNCB.get(i);
                    /*prmXRulesNCBDataM.setApplicationRecordId(prmAppRecId);
                    prmXRulesNCBDataM.setIdNo(prmIdNo);
                    prmXRulesNCBDataM.setCmpCode(prmCmpCde);*/
                    prmXRulesNCBDataM.setPersonalID(personalID);
                    //  prmXRulesNCBDataM.setUpdateBy(prmUserName);
                }

            }
            //====================================
            //			================Phone Verification==========================
            Vector vXRulesPhoneVerification = prmXRulesVerificationResultDataM.getVXRulesPhoneVerificationDataM();
            if (vXRulesPhoneVerification != null) {
                for (int i = 0; i < vXRulesPhoneVerification.size(); i++) {
                    XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM = (XRulesPhoneVerificationDataM) vXRulesPhoneVerification.get(i);
                    /*prmXRulesPhoneVerificationDataM.setApplicationRecordId(prmAppRecId);
                    prmXRulesPhoneVerificationDataM.setIdNo(prmIdNo);
                    prmXRulesPhoneVerificationDataM.setCmpCode(prmCmpCde);*/
                    prmXRulesPhoneVerificationDataM.setPersonalID(personalID);
                    prmXRulesPhoneVerificationDataM.setSeq(i);
                    //  prmXRulesPhoneVerificationDataM.setUpdateBy(prmUserName);
                }
            }
            //====================================
            //			================Policy rule==========================
            Vector vXRulesPolicyRules = prmXRulesVerificationResultDataM.getVXRulesPolicyRulesDataM();

            if (vXRulesPolicyRules != null) {
                for (int i = 0; i < vXRulesPolicyRules.size(); i++) {
                    XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM = (XRulesPolicyRulesDataM) vXRulesPolicyRules.get(i);
                    /*prmXRulesPolicyRulesDataM.setApplicationRecordId(prmAppRecId);
                    prmXRulesPolicyRulesDataM.setIdNo(prmIdNo);
                    prmXRulesPolicyRulesDataM.setCmpCode(prmCmpCde);*/
                    prmXRulesPolicyRulesDataM.setPersonalID(personalID);
                    // prmXRulesPolicyRulesDataM.setUpdateBy(prmUserName);
                }

            }
            //====================================
            //=====================FICO ===============
            XRulesFICODataM prmXRulesFico = prmXRulesVerificationResultDataM.getXrulesFICODataM();
            if (prmXRulesFico != null) {
                /*prmXRulesFico.setCmpCde(prmCmpCde);
                prmXRulesFico.setIdNo(prmIdNo);
                prmXRulesFico.setApplicationRecordId(prmAppRecId);*/
            	prmXRulesFico.setPersonalID(personalID);
                // prmXRulesFico.setUpdateBy(prmUserName);
            }
            //====================================
           //====================NCB Adjust
            Vector  vNCBAdjust=prmXRulesVerificationResultDataM.getVNCBAdjust();
            if(vNCBAdjust!=null){
                for (int i = 0; i < vNCBAdjust.size(); i++) {
                    XRulesNCBAdjustDataM  prmXRulesNCBAdjustDataM=(XRulesNCBAdjustDataM)vNCBAdjust.get(i);
                    /*prmXRulesNCBAdjustDataM.setApplicationRecordId(prmAppRecId);
                    prmXRulesNCBAdjustDataM.setIdNo(prmIdNo);
                    prmXRulesNCBAdjustDataM.setCmpCode(prmCmpCde);*/
                    prmXRulesNCBAdjustDataM.setPersonalID(personalID);
                }
            
            }
            //================Existcust Surname==========================
            Vector vXRulesExistcustSurname = prmXRulesVerificationResultDataM.getVXRulesExistcustSurnameDataM();
            if (vXRulesExistcustSurname != null) {
                for (int i = 0; i < vXRulesExistcustSurname.size(); i++) {
                    XRulesExistcustDataM prmXRulesExistcustDataM = (XRulesExistcustDataM) vXRulesExistcustSurname.get(i);
                    /*prmXRulesExistcustDataM.setApplicationRecordId(prmAppRecId);
                    prmXRulesExistcustDataM.setCmpCde(prmCmpCde);
                    prmXRulesExistcustDataM.setIdNo(prmIdNo);*/
                    prmXRulesExistcustDataM.setPersonalID(personalID);
                    //  prmXRulesExistcustDataM.setUpdateBy(prmUserName);
                }
            }
            Vector vXRulesExistcustInprogressSurname = prmXRulesVerificationResultDataM.getVXRulesExistcustInprogressSurnameDataM();
            if (vXRulesExistcustInprogressSurname != null) {
                
                for (int i = 0; i < vXRulesExistcustInprogressSurname.size(); i++) {
                    XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM = (XRulesExistcustInprogressDataM) vXRulesExistcustInprogressSurname.get(i);
                    /*prmXRulesExistcustInprogressDataM.setApplicationRecordId(prmAppRecId);
                    prmXRulesExistcustInprogressDataM.setCmpCde(prmCmpCde);
                    prmXRulesExistcustInprogressDataM.setIdNo(prmIdNo);*/
                    prmXRulesExistcustInprogressDataM.setPersonalID(personalID);
                }
            }
            //====================================
        }
    }
    
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAO#deleteModelXRulesVerificationResultM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesVerificationResultM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesVerificationResultException {
        try {
            //delete other xrules result
            //==============Blacklist============================ 
              XRulesBlacklistDAO xRulesBlacklistDAO = ORIGDAOFactory.getXRulesBlacklistDAO(); 
             xRulesBlacklistDAO.deleteModelXRulesBlacklistM( applicationRecordId,cmpCde,idNoVects);                
            //==================BlacklistVehicle========================
              XRulesBlacklistVehicleDAO xRulesBlacklistVehicleDAO = ORIGDAOFactory.getXRulesBlacklistVehicleDAO();
              xRulesBlacklistVehicleDAO.deleteModelXRulesBlacklistVehicleM(applicationRecordId,cmpCde,idNoVects);               
            //==============Debtbd============================            
              XRulesDebtBdDAO xRulesDebtBdDAO = ORIGDAOFactory.getXRulesDebtBdDAO();          
             xRulesDebtBdDAO.deleteModelXRulesDebBdM(applicationRecordId,cmpCde,idNoVects);        
            //================dedup==========================             
             XRulesDedupDAO xRulesDedupDAO = ORIGDAOFactory.getXRulesDedupDAO();              
              xRulesDedupDAO.deleteModelXRulesDedupM(applicationRecordId,cmpCde,idNoVects);               
            //================Dedup Vehicle==========================            
              XRulesDedupVehicleDAO xRulesDedupVehicleDAO = ORIGDAOFactory.getXRulesDedupVehicleDAO();
              xRulesDedupVehicleDAO.deleteModelXRulesDedupVenhicleM(applicationRecordId,cmpCde,idNoVects);              
            //====================================
            //================Existcust==========================             
             XRulesExistcustDAO xRulesExistcustDAO = ORIGDAOFactory.getXRulesExistcustDAO();              
             xRulesExistcustDAO.deleteModelXRulesExistcustM(applicationRecordId,cmpCde,idNoVects);
//           ================Existcust Inprogress==========================             
             XRulesExistcustInprogressDAO xRulesExistcustInprogressDAO = ORIGDAOFactory.getXRulesExistcustInprogressDAO();              
             xRulesExistcustInprogressDAO.deleteModelXRulesExistcustInprogressM(applicationRecordId,cmpCde,idNoVects);
            //====================================
            //================LPM==========================                        
             XRulesLPMDAO xRulesLPMDAO = ORIGDAOFactory.getXRulesLPMDAO();                
             xRulesLPMDAO.deleteModelXRulesLPMM(applicationRecordId,cmpCde,idNoVects );             
            //====================================
            //================NCB==========================             
             XRulesNCBDAO xRulesNCBDAO = ORIGDAOFactory.getXRulesNCBDAO();               
             xRulesNCBDAO.deleteModelXRulesNCBM( applicationRecordId,cmpCde,idNoVects);               
            //====================================
            //			================Phone Verification==========================         
             XRulesPhoneVerificationDAO xRulesPhoneVerificationAO = ORIGDAOFactory.getXRulesPhoneVerificationDAO();                
             xRulesPhoneVerificationAO.deleteModelXRulesPhoneVerificationM(applicationRecordId,cmpCde,idNoVects);                 
            //====================================
            //================Policy rule==========================            
             XRulesPolicyRulesDAO xRulesPolicyRulesDAO = ORIGDAOFactory.getXRulesPolicyRulesDAO();               
             xRulesPolicyRulesDAO.deleteModelXRulesPolicyRulesM(applicationRecordId,cmpCde,idNoVects);              
            //====================================
             //=====================NCB Adjust===============
             XRulesNCBAdjustDAO prmXrulesNCBAdjustDAO=ORIGDAOFactory.getXRulesNCBAdjustDAO();
             prmXrulesNCBAdjustDAO.deleteModelXRulesNCBAdjustM(applicationRecordId,cmpCde,idNoVects);
//           ================Existcust Surname==========================             
             XRulesExistcustSurnameDAO xRulesExistcustSurnameDAO = ORIGDAOFactory.getXRulesExistcustSurnameDAO();              
             xRulesExistcustSurnameDAO.deleteModelXRulesExistcustM(applicationRecordId,cmpCde,idNoVects);
//           ================Existcust Surname Inprogress==========================             
             XRulesExistcustInprogressSurnameDAO xRulesExistcustInprogressSurnameDAO = ORIGDAOFactory.getXRulesExistcustInprogressSurnameDAO();              
             xRulesExistcustInprogressSurnameDAO.deleteModelXRulesExistcustInprogressM(applicationRecordId,cmpCde,idNoVects);
            //====================================
             deleteTableXRulesVerificationResultM(applicationRecordId,cmpCde,idNoVects);
        } catch (Exception e) {
            log.fatal("Error:", e);
            throw new XRulesVerificationResultException(e.getMessage());
        }
        
        
        
        
    }
    public void deleteTableXRulesVerificationResultM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesVerificationResultException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            if (idNoVects == null) {
                log.debug("XRulesVerificationResultDAOImpl-->deleteModelTableXRulesVerificationResultM  idno Vects=null");
                return;
            }
            if (idNoVects.size() < 1) {
                log.debug("XRulesVerificationResultDAOImpl-->deleteModelTableXRulesVerificationResultM  Idno size =0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_VERIFICATION_RESULT ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=?   ");
            sql.append(" AND IDNO not in('");
            sql.append((String) idNoVects.get(0));
            sql.append("'");
            for (int i = 1; i < idNoVects.size() && i < XRulesConstant.limitDeleteSQLParam; i++) {
                String idno = (String) idNoVects.get(i);
                sql.append("," + "'" +idno+"'");
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
            throw new XRulesVerificationResultException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesVerificationResultException(e.getMessage());
            }
        }
    }
}
