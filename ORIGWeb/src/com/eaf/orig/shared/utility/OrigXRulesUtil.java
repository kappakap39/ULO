/*
 * Created on Oct 12, 2007
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
package com.eaf.orig.shared.utility;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.ncb.model.output.HSRespM;
import com.eaf.ncb.model.output.TLRespM;
import com.eaf.orig.cache.properties.CarBrandProperties;
import com.eaf.orig.cache.properties.FICOErrorCodePropeties;
import com.eaf.orig.cache.properties.FICOScoreProperties;
import com.eaf.orig.cache.properties.LoanTypeProperties;
import com.eaf.orig.cache.properties.NCBAccountStatusProperties;
import com.eaf.orig.cache.properties.NCBAccountTypeProperties;
import com.eaf.orig.cache.properties.NCBOverDueStatusProperties;
import com.eaf.orig.cache.properties.NCBParamMasterProperties;
import com.eaf.orig.cache.properties.ParameterDetailCodeProperties;
import com.eaf.orig.cache.properties.PolicyRulesCacheProperties;
import com.eaf.orig.cache.properties.UsrNameCacheProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PreScoreDataM;
import com.eaf.orig.shared.model.UserExceptionDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.wf.shared.ORIGWFConstant;
//import com.eaf.xrules.moduleservice.blacklist.model.BlackListModuleServiceOutputDataM;
//import com.eaf.xrules.moduleservice.blacklistvehicle.model.BlacklistVehicleModuleServiceOutputDataM;
//import com.eaf.xrules.moduleservice.debtburden.model.DebtBurdenModuleServiceOutputDataM;
//import com.eaf.xrules.moduleservice.dedup.model.DeDupModuleServiceOutputDataM;
//import com.eaf.xrules.moduleservice.dupvehicle.model.DupVehicleModuleServiceOutputDataM;
//import com.eaf.xrules.moduleservice.existingcustomer.model.ExistingCustomerModuleServiceOutputDataM;
//import com.eaf.xrules.moduleservice.fico.FICOModuleServiceOutputDataM;
//import com.eaf.xrules.moduleservice.model.ModuleServiceOutputDataM;
//import com.eaf.xrules.moduleservice.ncb.NCBModuleServiceOutputDataM;
//import com.eaf.xrules.moduleservice.policy.model.PolicyRulesModuleserviceOutuptDataM;
import com.eaf.xrules.service.manager.XRulesServiceManager;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesBlacklistDataM;
import com.eaf.xrules.shared.model.XRulesBlacklistVehicleDataM;
import com.eaf.xrules.shared.model.XRulesDataM;
import com.eaf.xrules.shared.model.XRulesDebtBdDataM;
import com.eaf.xrules.shared.model.XRulesDedupDataM;
import com.eaf.xrules.shared.model.XRulesDedupVehicleDataM;
import com.eaf.xrules.shared.model.XRulesExistcustDataM;
import com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM;
import com.eaf.xrules.shared.model.XRulesFICODataM;
import com.eaf.xrules.shared.model.XRulesNCBAdjustDataM;
import com.eaf.xrules.shared.model.XRulesNCBDataM;
import com.eaf.xrules.shared.model.XRulesPolicyRulesDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;
//import com.eaf.xrules.shared.utility.XRulesUtil;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigXRulesUtil
 */
public class OrigXRulesUtil {
    static Logger log = Logger.getLogger(OrigXRulesUtil.class);

    static private OrigXRulesUtil me;

    static private HashMap ncbMasterData = null;

    /**
     *  
     */
    public OrigXRulesUtil() {
        super();
    }

    public static OrigXRulesUtil getInstance() {
        if (me == null) {
            me = new OrigXRulesUtil();
        }
        return me;
    }

    /**
     * @author
     *  
     */

    public XRulesDataM mapXRulesModel(ApplicationDataM app, UserDetailM user, String personType, int personalID) {
        XRulesDataM xRulesDataM = new XRulesDataM();
        OrigApplicationUtil appUtil = OrigApplicationUtil.getInstance();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xRulesDataM;
    }

    public Vector getRequiredModuleServices(ApplicationDataM applicationDataM, UserDetailM user, PersonalInfoDataM personalInfoDataM) {
        Vector requiredModuleServices = null;
        try {
            XRulesDataM xRulesDataM = this.mapXRulesModel(applicationDataM, user, personalInfoDataM);
            Vector personalInfoVect = applicationDataM.getPersonalInfoVect();
            ORIGUtility utility = new ORIGUtility();
            PersonalInfoDataM applicantPersonalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
            if (OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(applicationDataM.getDrawDownFlag())) {
                if (applicantPersonalInfoDataM.getCustomerTypeTmp() != null) {
                    xRulesDataM.setCustomerType(applicantPersonalInfoDataM.getCustomerTypeTmp());
                } else {
                    xRulesDataM.setCustomerType(applicantPersonalInfoDataM.getCustomerType());
                }
            }
            xRulesDataM.setModuleServiceOutputs(new Vector());
            XRulesServiceManager xRuleService = ORIGEJBService.getXRulesServiceManager();
            log.debug("[OrigXRulesUtil]-->getRequiredModuleServices " + "getRecommendedModuleService");
            log.debug("[OrigXRulesUtil]--> get Business Class " + xRulesDataM.getBusinessClass());
            log.debug("[OrigXRulesUtil]--> get CustomerType " + xRulesDataM.getCustomerType());
            if(xRulesDataM.getBusinessClass()==null||"".equals(xRulesDataM.getBusinessClass())){
                xRulesDataM.setBusinessClass("AL_01_SA");
                log.debug("[OrigXRulesUtil]--> get Business Class case from Image " + xRulesDataM.getBusinessClass());
            }
            requiredModuleServices = xRuleService.getRecommendedModuleService(xRulesDataM);
            log.debug("[OrigXRulesUtil]-->getRequiredModuleServices" + "  requiredModuleServices = " + requiredModuleServices);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requiredModuleServices;
    }

    public XRulesDataM getXRulesDecision(ApplicationDataM applicationDataM, UserDetailM user, int serviceID, PersonalInfoDataM personalInfoDataM) {
        XRulesDataM XRulesOutput = null;
//        try {
//            XRulesDataM xRulesM = mapXRulesModel(applicationDataM, user, personalInfoDataM);
//            xRulesM.setSpecifiedModuleService(serviceID);
//            if (XRulesConstant.ServiceID.DEBT_AMOUNT != serviceID) {
//                XRulesServiceManager xRuleService = ORIGEJBService.getXRulesServiceManager();
//                XRulesOutput = xRuleService.getXRulesDecision(xRulesM);
//            }
//
//            XRulesVerificationResultDataM xRulesVerification = personalInfoDataM.getXrulesVerification();
//            if (xRulesVerification == null) {
//                xRulesVerification = new XRulesVerificationResultDataM();
//                personalInfoDataM.setXrulesVerification(xRulesVerification);
//            }
//            Timestamp updateTime = new Timestamp((new Date()).getTime());
//            switch (serviceID) {
//            case XRulesConstant.ServiceID.NCB: {
//                NCBModuleServiceOutputDataM ncbModuleServiceOutputDataM = (NCBModuleServiceOutputDataM) this.getMouduleServiceOutput(XRulesOutput, serviceID);
//                Vector vNcbRecord = ncbModuleServiceOutputDataM.getVNCBRecords();
//                if (vNcbRecord != null) {
//                    for (int i = 0; i < vNcbRecord.size(); i++) {
//                        XRulesNCBDataM recXRulesNCBDataM = (XRulesNCBDataM) vNcbRecord.get(i);
//                        recXRulesNCBDataM.setUpdateBy(user.getUserName());
//                        recXRulesNCBDataM.setUpdateDate(updateTime);
//                    }
//                } else {
//                    vNcbRecord = new Vector();
//                }
//                xRulesVerification.setVXRulesNCBDataM(vNcbRecord);
//                xRulesVerification.setNcbUpdateDate(updateTime);
//                xRulesVerification.setNcbUpdateBy(user.getUserName());
//                log.debug("getXRulesDecision ncbModuleServiceOutputDataM.getExecutionResultCode() " + ncbModuleServiceOutputDataM.getExecutionResultCode());
//                if (!XRulesConstant.ExecutionResultCode.CODE_F.equalsIgnoreCase(ncbModuleServiceOutputDataM.getExecutionResultCode())) {
//
//                    log.debug(" NCB Subject Returncode" + ncbModuleServiceOutputDataM.getSubjectReturnCode());
//                    if ("1".equalsIgnoreCase(ncbModuleServiceOutputDataM.getSubjectReturnCode())) {
//
//                        xRulesVerification.setNCBResult(cutLenghtResult(ncbModuleServiceOutputDataM.getExecutionResultString()));
//                        xRulesVerification.setNCBColor(cutLenghtResult(ncbModuleServiceOutputDataM.getNcbColor()));
//                        xRulesVerification.setNCBTrackingCode(ncbModuleServiceOutputDataM.getTrackingCode());
//                    } else if ("0".equalsIgnoreCase(ncbModuleServiceOutputDataM.getSubjectReturnCode())) {
//                        xRulesVerification.setNCBResult(cutLenghtResult(ncbModuleServiceOutputDataM.getExecutionResultString()));
//                        xRulesVerification.setNCBColor(OrigConstant.NCBcolor.BLACK);
//                        xRulesVerification.setNCBTrackingCode(ncbModuleServiceOutputDataM.getTrackingCode());
//                    } else if ("3".equalsIgnoreCase(ncbModuleServiceOutputDataM.getSubjectReturnCode())) {
//                        xRulesVerification.setNCBResult("Consent Canceled");
//                        xRulesVerification.setNCBColor("");
//                        xRulesVerification.setNCBTrackingCode(ncbModuleServiceOutputDataM.getTrackingCode());
//                    } else {
//                        xRulesVerification.setNCBResult("NCB CONNECTION ERROR");
//                        xRulesVerification.setNCBColor("");
//                        xRulesVerification.setNCBTrackingCode("");
//                    }
//                    xRulesVerification.setDebtAmountODInterestFlag(ncbModuleServiceOutputDataM.getDebAmountODInterestFlag());
//                    log.debug("getXRulesDecision NCB Color " + xRulesVerification.getNCBColor());
//                    log.debug("getXRulesDecision OD Interest Flag " + xRulesVerification.getDebtAmountODInterestFlag());
//                    xRulesVerification.setDebtAmountResult(null);
//                } else {
//                    xRulesVerification.setNCBResult(cutLenghtResult("NCB CONNECTION ERROR:" + ncbModuleServiceOutputDataM.getExecutionError()));
//                    xRulesVerification.setNCBColor("");
//                    xRulesVerification.setNCBTrackingCode("");
//                }
//                applicationDataM.setIsReExcuteAppScoreFlag(true);
//                applicationDataM.setScorringResult(null);
//                break;
//            }
//            case XRulesConstant.ServiceID.BLACKLIST: {
//                BlackListModuleServiceOutputDataM blacklistModuleServiceOutputDataM = (BlackListModuleServiceOutputDataM) this.getMouduleServiceOutput(
//                        XRulesOutput, serviceID);
//                Vector vBlacklistRecords = blacklistModuleServiceOutputDataM.getVMatchedBlacklist();
//                if (vBlacklistRecords != null) {
//                    for (int i = 0; i < vBlacklistRecords.size(); i++) {
//                        XRulesBlacklistDataM recXRulesBlacklistDataM = (XRulesBlacklistDataM) vBlacklistRecords.get(i);
//                        recXRulesBlacklistDataM.setUpdateBy(user.getUserName());
//                        recXRulesBlacklistDataM.setUpdateDate(updateTime);
//                    }
//                }
//                xRulesVerification.setBlacklistUpdateDate(updateTime);
//                xRulesVerification.setBlacklistUpdateBy(user.getUserName());
//                xRulesVerification.setBlacklistVehicleUpdateRole(xRulesM.getUpdateRole());
//                xRulesVerification.setVXRulesBlacklistDataM(vBlacklistRecords);
//                if (!XRulesConstant.ExecutionResultCode.CODE_F.equalsIgnoreCase(blacklistModuleServiceOutputDataM.getExecutionResultCode())) {
//                    xRulesVerification.setBLResult(cutLenghtResult(blacklistModuleServiceOutputDataM.getExecutionResultString()));
//                } else {
//                    xRulesVerification.setBLResult(cutLenghtResult(blacklistModuleServiceOutputDataM.getExecutionError()));
//                }
//                break;
//            }
//            case XRulesConstant.ServiceID.BLACKLIST_VEHICLE: {
//                BlacklistVehicleModuleServiceOutputDataM blacklistVehicleModuleServiceOutputDataM = (BlacklistVehicleModuleServiceOutputDataM) this
//                        .getMouduleServiceOutput(XRulesOutput, serviceID);
//                Vector vBlacklistVehicleRecords = blacklistVehicleModuleServiceOutputDataM.getVMatchedBlacklistVehicle();
//                if (vBlacklistVehicleRecords != null) {
//                    for (int i = 0; i < vBlacklistVehicleRecords.size(); i++) {
//                        XRulesBlacklistVehicleDataM recXRulesBlacklistVehicleDataM = (XRulesBlacklistVehicleDataM) vBlacklistVehicleRecords.get(i);
//                        recXRulesBlacklistVehicleDataM.setUpdateBy(user.getUserName());
//                        recXRulesBlacklistVehicleDataM.setUpdateDate(updateTime);
//                    }
//                }
//                xRulesVerification.setBlacklistVehcieUpdateDate(updateTime);
//                xRulesVerification.setBlacklistVehicleUpdateBy(user.getUserName());
//                xRulesVerification.setBlacklistVehicleUpdateRole(xRulesM.getUpdateRole());
//                xRulesVerification.setVXRulesBlacklistVehicleDataM(vBlacklistVehicleRecords);
//                if (!XRulesConstant.ExecutionResultCode.CODE_F.equalsIgnoreCase(blacklistVehicleModuleServiceOutputDataM.getExecutionResultCode())) {
//                    xRulesVerification.setBLVehicleResult(cutLenghtResult(blacklistVehicleModuleServiceOutputDataM.getExecutionResultString()));
//                } else {
//                    xRulesVerification.setBLVehicleResult(cutLenghtResult(blacklistVehicleModuleServiceOutputDataM.getExecutionError()));
//                }
//                break;
//            }
//            case XRulesConstant.ServiceID.EXIST_CUSTOMER: {
//                ExistingCustomerModuleServiceOutputDataM existCustModuleServiceOutputDataM = (ExistingCustomerModuleServiceOutputDataM) this
//                        .getMouduleServiceOutput(XRulesOutput, serviceID);
//             
//                
//                Vector vExistingCustomerInprogressRecords = existCustModuleServiceOutputDataM.getVExistingCustomerInprogressDataMs();
//                if (vExistingCustomerInprogressRecords != null) {
//                    for (int i = 0; i < vExistingCustomerInprogressRecords.size(); i++) {
//                        XRulesExistcustInprogressDataM recXRulesExistcustInprogressDataM = (XRulesExistcustInprogressDataM) vExistingCustomerInprogressRecords
//                                .get(i);
//                        recXRulesExistcustInprogressDataM.setUpdateBy(user.getUserName());
//                        recXRulesExistcustInprogressDataM.setUpdateDate(updateTime);
//                        recXRulesExistcustInprogressDataM.setSeq(i);
//                    }
//                }
//                Vector vExistingCustomerBookedRecords = existCustModuleServiceOutputDataM.getVExistingCustomerBookedDataMs();
//                if (vExistingCustomerBookedRecords != null) {
//                    for (int i = 0; i < vExistingCustomerBookedRecords.size(); i++) {
//                        XRulesExistcustDataM recXRulesExistcusBookedtDataM = (XRulesExistcustDataM) vExistingCustomerBookedRecords.get(i);
//                        recXRulesExistcusBookedtDataM.setUpdateBy(user.getUserName());
//                        recXRulesExistcusBookedtDataM.setUpdateDate(updateTime);
//                        recXRulesExistcusBookedtDataM.setSeq(i);
//                    }
//                }
//                xRulesVerification.setExistingCustUpdateDate(updateTime);
//                xRulesVerification.setExistCustUpdateBy(user.getUserName());
//                xRulesVerification.setExistingCustomerUpdateRole(xRulesM.getUpdateRole());
//                xRulesVerification.setVXRulesExistcustDataM(vExistingCustomerBookedRecords);
//                xRulesVerification.setVXRulesExistcustInprogressDataM(vExistingCustomerInprogressRecords);
//                if (!XRulesConstant.ExecutionResultCode.CODE_F.equalsIgnoreCase(existCustModuleServiceOutputDataM.getExecutionResultCode())) {
//                    xRulesVerification.setExistCustResult(cutLenghtResult(existCustModuleServiceOutputDataM.getExecutionResultString()));
//                } else {
//                    xRulesVerification.setExistCustResult(cutLenghtResult(existCustModuleServiceOutputDataM.getExecutionError()));
//                }
//                
//                Vector vExistingCustomerInprogressSurnameRecords = existCustModuleServiceOutputDataM.getVExistingCustomerInprogressSurnameDataMs();
//                if (vExistingCustomerInprogressSurnameRecords != null) {
//                    for (int i = 0; i < vExistingCustomerInprogressSurnameRecords.size(); i++) {
//                        XRulesExistcustInprogressDataM recXRulesExistcustInprogressDataM = (XRulesExistcustInprogressDataM) vExistingCustomerInprogressSurnameRecords
//                                .get(i);
//                        recXRulesExistcustInprogressDataM.setUpdateBy(user.getUserName());
//                        recXRulesExistcustInprogressDataM.setUpdateDate(updateTime);
//                        recXRulesExistcustInprogressDataM.setSeq(i);
//                    }
//                }
//                Vector vExistingCustomerBookedSurnameRecords = existCustModuleServiceOutputDataM.getVExistingCustomerBookedSurnameDataMs();
//                if (vExistingCustomerBookedRecords != null) {
//                    for (int i = 0; i < vExistingCustomerBookedSurnameRecords.size(); i++) {
//                        XRulesExistcustDataM recXRulesExistcusBookedtDataM = (XRulesExistcustDataM) vExistingCustomerBookedSurnameRecords.get(i);
//                        recXRulesExistcusBookedtDataM.setUpdateBy(user.getUserName());
//                        recXRulesExistcusBookedtDataM.setUpdateDate(updateTime);
//                        recXRulesExistcusBookedtDataM.setSeq(i);
//                    }
//                }
//                xRulesVerification.setVXRulesExistcustSurnameDataM(vExistingCustomerBookedSurnameRecords);
//                xRulesVerification.setVXRulesExistcustInprogressSurnameDataM(vExistingCustomerInprogressSurnameRecords);                                                                
//                applicationDataM.setIsReExcuteAppScoreFlag(true);
//                applicationDataM.setScorringResult(null);
//                xRulesVerification.setDebtAmountResult(null);                                                
//                break;
//            }
//            case XRulesConstant.ServiceID.DEDUP: {
//                DeDupModuleServiceOutputDataM deDupModuleServiceOutputDataM = (DeDupModuleServiceOutputDataM) this.getMouduleServiceOutput(XRulesOutput,
//                        serviceID);
//                Vector vDedupRecords = deDupModuleServiceOutputDataM.getMatchedApplication();
//                if (vDedupRecords != null) {
//                    for (int i = 0; i < vDedupRecords.size(); i++) {
//                        XRulesDedupDataM recXRulesDedupDataM = (XRulesDedupDataM) vDedupRecords.get(i);
//                        recXRulesDedupDataM.setUpdateBy(user.getUserName());
//                        recXRulesDedupDataM.setUpdateDate(updateTime);
//                    }
//                }
//                xRulesVerification.setDedupUpdateDate(updateTime);
//                xRulesVerification.setDedupUpdateBy(user.getUserName());
//                xRulesVerification.setDedupUpdateRole(xRulesM.getUpdateRole());
//                xRulesVerification.setVXRulesDedupDataM(vDedupRecords);
//                if (!XRulesConstant.ExecutionResultCode.CODE_F.equalsIgnoreCase(deDupModuleServiceOutputDataM.getExecutionResultCode())) {
//                    xRulesVerification.setDedupResult(cutLenghtResult(deDupModuleServiceOutputDataM.getExecutionResultString()));
//                } else {
//                    xRulesVerification.setDedupResult(cutLenghtResult(deDupModuleServiceOutputDataM.getExecutionError()));
//                }
//                break;
//            }
//            case XRulesConstant.ServiceID.DUP_VEHICLE: {
//                DupVehicleModuleServiceOutputDataM dupVehicleModuleServiceOutputDataM = (DupVehicleModuleServiceOutputDataM) this.getMouduleServiceOutput(
//                        XRulesOutput, serviceID);
//                Vector vDedupVehicleRecords = dupVehicleModuleServiceOutputDataM.getDupVehicleDataVt();
//
//                if (vDedupVehicleRecords != null) {
//                    for (int i = 0; i < vDedupVehicleRecords.size(); i++) {
//                        XRulesDedupVehicleDataM recXRulesDedupVehicleDataM = (XRulesDedupVehicleDataM) vDedupVehicleRecords.get(i);
//                        recXRulesDedupVehicleDataM.setUpdateBy(user.getUserName());
//                        recXRulesDedupVehicleDataM.setUpdateDate(updateTime);
//                    }
//                }
//                xRulesVerification.setDupVehicleUpdateDate(updateTime);
//                xRulesVerification.setDupVehicleUpdateBy(user.getUserName());
//                xRulesVerification.setDedupVehicleUpdateRole(xRulesM.getUpdateRole());
//                xRulesVerification.setVXRulesDedupVehicleDataM(vDedupVehicleRecords);
//                if (!XRulesConstant.ExecutionResultCode.CODE_F.equalsIgnoreCase(dupVehicleModuleServiceOutputDataM.getExecutionResultCode())) {
//                    xRulesVerification.setDedupVehicleResult(cutLenghtResult(dupVehicleModuleServiceOutputDataM.getExecutionResultString()));
//                } else {
//                    xRulesVerification.setDedupVehicleResult(cutLenghtResult(dupVehicleModuleServiceOutputDataM.getExecutionError()));
//                }
//                break;
//            }
//            case XRulesConstant.ServiceID.LPM: {
//                xRulesVerification.setLpmUpdateDate(updateTime);
//                xRulesVerification.setLpmUpdateBy(user.getUserName());
//                xRulesVerification.setLpmUpdateRole(xRulesM.getUpdateRole());
//                break;
//            }
//            case XRulesConstant.ServiceID.POLICYRULES: {
//                //Clear Flag
//                //applicationDataM.setXuwDecision(null);
//                //applicationDataM.setXuwOverrideBy(null);
//                PolicyRulesModuleserviceOutuptDataM policyRulesModuleServiceOutputDataM = (PolicyRulesModuleserviceOutuptDataM) this.getMouduleServiceOutput(
//                        XRulesOutput, serviceID);
//
//                Vector vPolicyRulesRecords = policyRulesModuleServiceOutputDataM.getVAutoExcutePolicyRules();
//                Vector vOldPolicyRules = xRulesVerification.getVXRulesPolicyRulesDataM();
//                if (vOldPolicyRules == null) {
//                    vOldPolicyRules = new Vector();
//                }
//                boolean exceptionFlag = false;
//                if (vPolicyRulesRecords != null) {
//                    for (int i = 0; i < vPolicyRulesRecords.size(); i++) {
//                        XRulesPolicyRulesDataM recXRulesPolicyRulesDataM = (XRulesPolicyRulesDataM) vPolicyRulesRecords.get(i);
//                        recXRulesPolicyRulesDataM.setUpdateBy(user.getUserName());
//                        recXRulesPolicyRulesDataM.setUpdateDate(updateTime);                         
//                        if (!exceptionFlag) {
//                            for (int j = 0; j < vOldPolicyRules.size(); j++) {
//                                XRulesPolicyRulesDataM oldXRulesPolicyRulesDataM = (XRulesPolicyRulesDataM) vOldPolicyRules.get(j);
//                                if (recXRulesPolicyRulesDataM.getPolicyCode().equals(oldXRulesPolicyRulesDataM.getPolicyCode())) {
//                                    if (!recXRulesPolicyRulesDataM.getResult().equals(oldXRulesPolicyRulesDataM.getResult())) {
//                                        exceptionFlag=true;
//                                        applicationDataM.setXuwDecision(null);
//                                        applicationDataM.setXuwOverrideBy(null);
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                xRulesVerification.setPolicyRulesUpdateDate(updateTime);
//                xRulesVerification.setPolicyRulesUpdateBy(user.getUserName());
//                xRulesVerification.setPolicyRulesUpdateRole(xRulesM.getUpdateRole());
//                xRulesVerification.setVXRulesPolicyRulesDataM(this.mergePolicyRulesResult(xRulesVerification, vPolicyRulesRecords));
//                if (!XRulesConstant.ExecutionResultCode.CODE_F.equalsIgnoreCase(policyRulesModuleServiceOutputDataM.getExecutionResultCode())) {
//                    xRulesVerification.setPolicyRulesResult(policyRulesModuleServiceOutputDataM.getExecutionResultString());
//                } else {
//                    xRulesVerification.setPolicyRulesResult(policyRulesModuleServiceOutputDataM.getExecutionError());
//                }
//                break;
//            }
//            case XRulesConstant.ServiceID.DEBTBURDEN: {
//                DebtBurdenModuleServiceOutputDataM debtBurdenModuleServiceOutputDataM = (DebtBurdenModuleServiceOutputDataM) this.getMouduleServiceOutput(
//                        XRulesOutput, serviceID);
//                XRulesDebtBdDataM prmXRulesDebtBdDataM = debtBurdenModuleServiceOutputDataM.getXrulesDebtDdDtaM();
//                prmXRulesDebtBdDataM.setUpdateBy(user.getUserName());
//                prmXRulesDebtBdDataM.setUpdateDate(updateTime);
//                xRulesVerification.setDebtBdUpdateDate(updateTime);
//                xRulesVerification.setDebtBdUpdateBy(user.getUserName());
//                xRulesVerification.setDebtbdUpdateRole(xRulesM.getUpdateRole());
//                xRulesVerification.setXRulesDebtBdDataM(prmXRulesDebtBdDataM);
//                xRulesVerification.setDEBT_BDScore(prmXRulesDebtBdDataM.getDebtBurdentScore());
//                if (!XRulesConstant.ExecutionResultCode.CODE_F.equalsIgnoreCase(debtBurdenModuleServiceOutputDataM.getExecutionResultCode())) {
//                    xRulesVerification.setDEBT_BDResult(cutLenghtResult(debtBurdenModuleServiceOutputDataM.getExecutionResultString()));
//                } else {
//                    xRulesVerification.setDEBT_BDResult(cutLenghtResult(debtBurdenModuleServiceOutputDataM.getExecutionError()));
//                }
//                if (OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalInfoDataM.getPersonalType())) {
//                    prmXRulesDebtBdDataM.setUseFlag(OrigConstant.ORIG_FLAG_Y);
//                } else {
//                    prmXRulesDebtBdDataM.setUseFlag(OrigConstant.ORIG_FLAG_N);
//                }
//                //xRulesVerification
//                //       .setDEBT_BDScore(debtBurdenModuleServiceOutputDataM
//                //                .getXrulesDebtDdDtaM().getDebtBurdentScore());
//                //log.debug("getXRulesDecision DebdScore
//                // "+xRulesVerification.getDEBT_BDScore());
//                break;
//            }
//            case XRulesConstant.ServiceID.FICO: {
//                FICOModuleServiceOutputDataM ficoModuleServiceOutputDataM = (FICOModuleServiceOutputDataM) this
//                        .getMouduleServiceOutput(XRulesOutput, serviceID);
//                XRulesFICODataM xrulesFicoDataM = ficoModuleServiceOutputDataM.getXrulesFICODataM();
//                xrulesFicoDataM.setUpdateBy(user.getUserName());
//                xrulesFicoDataM.setUpdateDate(updateTime);
//                xRulesVerification.setFicoUpdateDate(updateTime);
//                xRulesVerification.setFicoUpdateBy(user.getUserName());
//                xRulesVerification.setFicoUpdateRole(xRulesM.getUpdateRole());
//                xRulesVerification.setXrulesFICODataM(xrulesFicoDataM);
//                String ficoResult = "";
//                log.debug("Error Message= " + xrulesFicoDataM.getErrorMessage());
//                if (xrulesFicoDataM.getErrorCode() == null || "".equals(xrulesFicoDataM.getErrorCode().trim())) {
//                    if (!(OrigConstant.NCBcolor.BLACK.equalsIgnoreCase(xRulesVerification.getNCBColor()) || "".equalsIgnoreCase(xRulesVerification
//                            .getNCBColor()))) {
//                        ficoResult = this.getFicoDecision(xrulesFicoDataM.getScore());
//                    } else {
//                        ficoResult = XRulesConstant.RESULT_NA;
//                    }
//                } else {
//                    ficoResult = ficoResult = XRulesConstant.RESULT_NA;
//                }
//                xRulesVerification.setFicoResult(cutLenghtResult(ficoResult));
//                break;
//            }
//            case XRulesConstant.ServiceID.KHONTHAI: {
//                break;
//            }
//            case XRulesConstant.ServiceID.THAIREGITRATION: {
//                break;
//            }
//            case XRulesConstant.ServiceID.BOL: {
//                break;
//            }
//            case XRulesConstant.ServiceID.YELLOWPAGE: {
//                break;
//            }
//            case XRulesConstant.ServiceID.PHONEBOOK: {
//                break;
//            }
//            case XRulesConstant.ServiceID.DEBT_AMOUNT: {
//                BigDecimal debtAmt;
//                BigDecimal debtAmtAdjust;
//                if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
//                    debtAmt = XRulesUtil.getDebtAmountCMR(personalInfoDataM, personalInfoDataM.getPreScoreDataM(), applicationDataM.getLoanVect());
//                    debtAmtAdjust = debtAmt;
//                } else {
//                    log.debug("Debt Amount");
//                    debtAmt = XRulesUtil.getDebtAmount(personalInfoDataM, personalInfoDataM.getPreScoreDataM(), applicationDataM.getLoanVect());
//                    log.debug("Debt Amount NCB Adjust");
//                    debtAmtAdjust = XRulesUtil.getDebtAmountNCBAdjust(personalInfoDataM, personalInfoDataM.getPreScoreDataM(), applicationDataM.getLoanVect());
//
//                }
//
//                if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())) {
//                    ORIGUtility utility = new ORIGUtility();
//                    //Get Guarantor
//                    BigDecimal guarantorDebt = new BigDecimal(0);
//                    BigDecimal guarantorDebtAdjust = new BigDecimal(0);
//                    Vector guarantorVect = utility.getVectorPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_GUARANTOR);
//                    if (guarantorVect != null) {
//                        for (int i = 0; i < guarantorVect.size(); i++) {
//                            PersonalInfoDataM gurantoPersonalInfoDataM = (PersonalInfoDataM) guarantorVect.get(i);
//                            if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(gurantoPersonalInfoDataM.getCoborrowerFlag())) {
//                                XRulesVerificationResultDataM guarantorVerification = gurantoPersonalInfoDataM.getXrulesVerification();
//                                log.debug("Debt id " + personalInfoDataM.getIdNo() + " Include " + guarantorVerification.getDebtAmountResult());
//                                log.debug("Debt id " + personalInfoDataM.getIdNo() + " Include " + guarantorVerification.getDebtAmountAdjustResult());
//                                if (guarantorVerification.getDebtAmountResult() != null) {
//                                    guarantorDebt = guarantorDebt.add(guarantorVerification.getDebtAmountResult());
//                                }
//                                if (guarantorVerification.getDebtAmountAdjustResult() != null) {
//                                    guarantorDebtAdjust = guarantorDebtAdjust.add(guarantorVerification.getDebtAmountAdjustResult());
//                                }
//                            } else if ((gurantoPersonalInfoDataM.getCoborrowerFlag() == null || OrigConstant.COBORROWER_FLAG_NO.equals(gurantoPersonalInfoDataM
//                                    .getCoborrowerFlag())
//                                    && OrigConstant.ORIG_FLAG_Y.equals(gurantoPersonalInfoDataM.getDebtIncludeFlag()))) {
//                                XRulesVerificationResultDataM guarantorVerification = gurantoPersonalInfoDataM.getXrulesVerification();
//                                log.debug("Debt id " + personalInfoDataM.getIdNo() + " Include " + guarantorVerification.getDebtAmountResult());
//                                log.debug("Debt id " + personalInfoDataM.getIdNo() + " Include " + guarantorVerification.getDebtAmountAdjustResult());
//                                if (guarantorVerification.getDebtAmountResult() != null) {
//                                    guarantorDebt = guarantorDebt.add(guarantorVerification.getDebtAmountResult());
//                                }
//                                if (guarantorVerification.getDebtAmountAdjustResult() != null) {
//                                    guarantorDebtAdjust = guarantorDebtAdjust.add(guarantorVerification.getDebtAmountAdjustResult());
//                                }
//                            }
//                        }
//                    }
//                    log.debug("Guatantor Debt" + guarantorDebt);
//                    log.debug("Guatantor DebtAdjust" + guarantorDebtAdjust);
//                    debtAmt = debtAmt.add(guarantorDebt);
//                    debtAmtAdjust = debtAmtAdjust.add(guarantorDebtAdjust);
//                    applicationDataM.setIsReExcuteDebtAmtFlag(false);
//                }
//                xRulesVerification.setDebtAmountResult(debtAmt);
//                xRulesVerification.setDebtAmountUpdateDate(updateTime);
//                xRulesVerification.setDebtAmountUpdateBy(user.getUserName());
//                xRulesVerification.setDebtAmtUpdateRole(xRulesM.getUpdateRole());
//                xRulesVerification.setDebtAmountAdjustResult(debtAmtAdjust);
//                xRulesVerification.setDEBT_BDResult(null);
//                break;
//            }
//            default:
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.debug(e.getMessage());
//        }
        return XRulesOutput;
    }

    /**
     * @param applicationDataM
     * @param user
     * @param personalInfo
     * @return
     */

    private XRulesDataM mapXRulesModel(ApplicationDataM applicationDataM, UserDetailM user, PersonalInfoDataM personalInfoDataM) {
        XRulesDataM xRulesDataM = new XRulesDataM();
        try {
            ORIGUtility utility = new ORIGUtility();
            xRulesDataM.setBusinessClass(applicationDataM.getBusinessClassId());
            xRulesDataM.setCardType("B");
            xRulesDataM.setJobState(applicationDataM.getJobState());
            log.debug("-->mapXRulesModel " + "Business Class " + xRulesDataM.getBusinessClass());
            log.debug("-->mapXRulesModel " + "Card Type " + xRulesDataM.getCardType());
            log.debug("-->mapXRulesModel " + "Job State " + xRulesDataM.getJobState());
            //test id No
            //Vector personal = appDataM.getPersonalInfoVect();
            VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
            if (vehicleDataM == null) {
                vehicleDataM = new VehicleDataM();
            }
            xRulesDataM.setChassisNo(vehicleDataM.getChassisNo());
            xRulesDataM.setEngineNo(vehicleDataM.getEngineNo());
            xRulesDataM.setRegistationNo(vehicleDataM.getRegisterNo());
            xRulesDataM.setApplicationRecordID(applicationDataM.getAppRecordID());
            xRulesDataM.setVehicleFlag(vehicleDataM.getCar());
            // PersonalInfoDataM persanalInfoDataM;
            if (personalInfoDataM != null) {
                xRulesDataM.setIdNo(personalInfoDataM.getIdNo());
                String customerType = "";
                customerType = personalInfoDataM.getCustomerType();
                log.debug("-->mapXRulesModel " + "Customer Type " + customerType);
                xRulesDataM.setCustomerType(customerType);
                xRulesDataM.setThaiFname(personalInfoDataM.getThaiFirstName());
                log.debug("thai FirstName -->" + personalInfoDataM.getThaiFirstName());
                xRulesDataM.setThaiLname(personalInfoDataM.getThaiLastName());
                log.debug("thai lastName -->" + personalInfoDataM.getThaiLastName());
                xRulesDataM.setBirthDate(personalInfoDataM.getBirthDate());

                log.debug("PersoalType -->" + personalInfoDataM.getPersonalType());
                xRulesDataM.setPersoanlType(personalInfoDataM.getPersonalType());
                personalInfoDataM.getXrulesVerification();
                if (personalInfoDataM.getXrulesVerification() != null) {
                    Vector vXruleNCBDataMs = personalInfoDataM.getXrulesVerification().getVXRulesNCBDataM();
                    if (vXruleNCBDataMs != null && vXruleNCBDataMs.size() > 0) {
                        XRulesNCBDataM xrulesNCBDataM = (XRulesNCBDataM) vXruleNCBDataMs.get(0);
                        xRulesDataM.setNcbTrackingCode(xrulesNCBDataM.getTrackingCode());
                    }

                    //xRulesDataM.setInerestRate(personalInfoDataM
                    //       .getXrulesVerification().getDEBT_BD_PARAM());
                    // log.debug("Interest Rate -->"
                    //        + xRulesDataM.getInerestRate());
                    //Policy rules
                    XRulesVerificationResultDataM perXrulesVerificationDataM = personalInfoDataM.getXrulesVerification();
                    xRulesDataM.setPolicyBlacklitHit(perXrulesVerificationDataM.getBLResult());
                    xRulesDataM.setPolicyNCBColor(perXrulesVerificationDataM.getNCBColor());
                    xRulesDataM.setNcbTrackingCode(perXrulesVerificationDataM.getNCBTrackingCode());
                    xRulesDataM.setBurden(perXrulesVerificationDataM.getDebtAmountResult());
                    xRulesDataM.setBurdentAdjust(perXrulesVerificationDataM.getDebtAmountAdjustResult());
                    //set policy debt Burdent
                    XRulesDebtBdDataM xRulesDebtBurden = perXrulesVerificationDataM.getXRulesDebtBdDataM();
                    if (xRulesDebtBurden != null) {
                        xRulesDataM.setPolicyRulesPercentDebtBurden(xRulesDebtBurden.getDebtBurdentScore());
                    }
                }
                //total income
                BigDecimal totalIncome = new BigDecimal(0);
                if (!ORIGWFConstant.JobState.CMR_DRAFT_STATE.equals(applicationDataM.getJobState())) {
                    //					total finance amt
                    Vector loanVect = applicationDataM.getLoanVect();
                    BigDecimal financeAmount = BigDecimal.valueOf(0);
                    System.out.println("LoanVect-->" + loanVect);
                    if (loanVect != null) {
                        for (int i = 0; i < loanVect.size(); i++) {
                            LoanDataM loan = (LoanDataM) loanVect.get(i);
                            log.debug("Finance Amount" + i + "  " + loan.getTotalOfFinancialAmt());
                            if (loan.getTotalOfFinancialAmt() != null) {
                                financeAmount = financeAmount.add(loan.getTotalOfFinancialAmt());
                            }
                        }
                    }

                    xRulesDataM.setTotalfinanceAmount(financeAmount);
                    log.debug("Total Finance Amount = " + xRulesDataM.getTotalfinanceAmount());
                    if (personalInfoDataM.getSalary() == null) {
                        personalInfoDataM.setSalary(new BigDecimal(0));
                    }
                    if (personalInfoDataM.getOtherIncome() == null) {
                        personalInfoDataM.setOtherIncome(new BigDecimal(0));
                    }

                    totalIncome = (totalIncome.add(personalInfoDataM.getSalary())).add(personalInfoDataM.getOtherIncome());
                    log.debug("Salary " + personalInfoDataM.getSalary() + "  Other Income  " + personalInfoDataM.getOtherIncome());
                    //get Coboower Income
                    if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())) {
                        log.debug("Personal appicatin  Plus income Coborrower");
                        Vector guarantorVect = utility.getVectorPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_GUARANTOR);
                        for (int i = 0; i < guarantorVect.size(); i++) {
                            PersonalInfoDataM guaPersanalInfoDataM = (PersonalInfoDataM) guarantorVect.get(i);
                            if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(guaPersanalInfoDataM.getCoborrowerFlag())) {
                                log.debug("Salary Co " + guaPersanalInfoDataM.getSalary() + "  Other Income Co " + guaPersanalInfoDataM.getOtherIncome());
                                BigDecimal coBorowerIncome = new BigDecimal(0);
                                if (guaPersanalInfoDataM.getSalary() != null) {
                                    coBorowerIncome = coBorowerIncome.add(guaPersanalInfoDataM.getSalary());
                                }
                                if (guaPersanalInfoDataM.getOtherIncome() != null) {
                                    coBorowerIncome = coBorowerIncome.add(guaPersanalInfoDataM.getOtherIncome());
                                }
                                totalIncome = totalIncome.add(coBorowerIncome);
                            }
                        }

                    }
                    //Co Borroewer Income
                    AddressDataM addrDoc = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_DOC);

                    if (addrDoc != null) {
                        xRulesDataM.setHouseId(addrDoc.getHouseID());
                    }

                } else { //total income cmr
                    PreScoreDataM preScoreDataM = personalInfoDataM.getPreScoreDataM();
                    if (preScoreDataM != null) {
                        totalIncome = totalIncome.add(preScoreDataM.getSalary());
                        totalIncome = totalIncome.add(preScoreDataM.getOtherIncome());
                        log.debug("Salary Prescore " + preScoreDataM.getSalary() + "  Other Income Prescore " + preScoreDataM.getOtherIncome());
                        xRulesDataM.setHouseId(preScoreDataM.getHouseIdno());
                        xRulesDataM.setTotalfinanceAmount(preScoreDataM.getFinanceAmountVAT());
                        log.debug("Total Finance Amount = " + xRulesDataM.getTotalfinanceAmount());
                    }

                }
                System.out.println("Total Income  " + totalIncome);
                xRulesDataM.setTotalIncome(totalIncome);
                xRulesDataM.setVPolicyRulesAddress(personalInfoDataM.getAddressVect());
                xRulesDataM.setPolicyWorkingYear(personalInfoDataM.getYearOfWork());
                xRulesDataM.setPolicyWorkingMonth(personalInfoDataM.getMonthOfWork());
                //get age
                OrigXRulesUtil origXrulesUtil = OrigXRulesUtil.getInstance();
                xRulesDataM.setPolicyCustomerAge(origXrulesUtil.getAge(personalInfoDataM.getBirthDate()));
                if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())) {
                    Vector guarantorVect = utility.getVectorPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_GUARANTOR);
                    if (guarantorVect != null) {
                        xRulesDataM.setVGuarantor(guarantorVect);
                    }
                }

            }
            //xRulesDataM
            //get Main Garuntor
            //Vector personalInfoVect = applicationDataM.getPersonalInfoVect();
            //  ORIGUtility utility = new ORIGUtility();
            //PersonalInfoDataM guarantorPersonalInfoDataM =

            //private int policyScoreingResult; N/a
            xRulesDataM.setPolicyScoreingResult(applicationDataM.getScorringResult());
            //get Change Name;
            xRulesDataM.setChangeNameVect(personalInfoDataM.getChangeNameVect());
            //get Other Name;
            xRulesDataM.setOtherNameVect(applicationDataM.getOtherNameDataM());
            xRulesDataM.setApplicationNo(applicationDataM.getApplicationNo());
            xRulesDataM.setUserName(user.getUserName());
            if (user.getRoles() != null && user.getRoles().size() > 0) {
                xRulesDataM.setUpdateRole((String) user.getRoles().get(0));
            }
            xRulesDataM.setOfficeCode(applicationDataM.getOfficeCode());
            log.debug("-->mapXRulesModel Application No" + xRulesDataM.getApplicationNo());
            log.debug("-->mapXRulesModel UserName " + xRulesDataM.getUserName());
            log.debug("-->mapXRulesModel NCBTracingCode " + xRulesDataM.getNcbTrackingCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return xRulesDataM;
    }

    public Vector getMergeRequireModule(ApplicationDataM ApplicationDataM) {
        return null;
    }

//    public ModuleServiceOutputDataM getMouduleServiceOutput(XRulesDataM xrulesDataM, long serviceId){
//        Vector vMouduleServiceOutputs = xrulesDataM.getModuleServiceOutputs();
//        if (vMouduleServiceOutputs != null) {
//            for (int i = 0; i < vMouduleServiceOutputs.size(); i++) {
//                ModuleServiceOutputDataM moduleServiceOutputDataM = (ModuleServiceOutputDataM) vMouduleServiceOutputs.get(i);              
//                if (moduleServiceOutputDataM.getServiceID() == serviceId) {
//                    
//                    return moduleServiceOutputDataM;
//                }
//            }
//        }
//
//        return null;
//    }

    public String getXRulesVerificationRusult(XRulesVerificationResultDataM xRulesVerification, int serviceID) {
        String result = "";
        if (xRulesVerification == null) {
            return "";
        }
        switch (serviceID) {
        case XRulesConstant.ServiceID.NCB: {
            result = xRulesVerification.getNCBResult();
            break;
        }
        case XRulesConstant.ServiceID.BLACKLIST: {
            result = xRulesVerification.getBLResult();
            break;
        }
        case XRulesConstant.ServiceID.BLACKLIST_VEHICLE: {
            result = xRulesVerification.getBLVehicleResult();
            break;
        }
        case XRulesConstant.ServiceID.EXIST_CUSTOMER: {
            result = xRulesVerification.getExistCustResult();
            break;
        }
        case XRulesConstant.ServiceID.DEDUP: {
            result = xRulesVerification.getDedupResult();
            break;
        }
        case XRulesConstant.ServiceID.DUP_VEHICLE: {
            result = xRulesVerification.getDedupVehicleResult();
            break;
        }
        case XRulesConstant.ServiceID.LPM: {
            result = xRulesVerification.getLPMResult();
            break;
        }
        case XRulesConstant.ServiceID.POLICYRULES: {
            result = xRulesVerification.getPolicyRulesResult();
            break;
        }
        case XRulesConstant.ServiceID.DEBTBURDEN: {
            result = xRulesVerification.getDEBT_BDResult();
            break;
        }
        case XRulesConstant.ServiceID.FICO: {
            result = xRulesVerification.getFicoResult();
            break;
        }
        case XRulesConstant.ServiceID.KHONTHAI: {
            result = xRulesVerification.getKhonThaiResult();
            break;
        }
        case XRulesConstant.ServiceID.THAIREGITRATION: {
            result = xRulesVerification.getThaiRegistrationResult();
            break;
        }
        case XRulesConstant.ServiceID.BOL: {
            result = xRulesVerification.getBOLResult();
            break;
        }
        case XRulesConstant.ServiceID.YELLOWPAGE: {
            result = xRulesVerification.getYellowPageResult();
            break;
        }
        case XRulesConstant.ServiceID.PHONEBOOK: {
            result = xRulesVerification.getPhoneBookResult();
            break;
        }
        case XRulesConstant.ServiceID.PHONE_VER: {
            result = xRulesVerification.getPhoneVerResult();
            break;
        }
        case XRulesConstant.ServiceID.DEBT_AMOUNT: {
            if (xRulesVerification.getDebtAmountResult() != null) {
                try {
                    result = ORIGDisplayFormatUtil.displayCommaNumber(xRulesVerification.getDebtAmountResult());
                } catch (Exception e) {
                }
            } else {
                result = "";
            }
            break;
        }
        case XRulesConstant.ServiceID.ELIGIBILITY: {
            result = xRulesVerification.getEligibilityResult();
            break;
        }
        case XRulesConstant.ServiceID.LTV: {
            result = xRulesVerification.getLtvResult();
            break;
        }
        default:
        }
        return result;
    }

    public String convertPaymentHistoryDisplay(String paymentHistory1, String paymentHistory2) {
        if (paymentHistory1 == null) {
            paymentHistory1 = "";
        }
        if (paymentHistory2 == null) {
            paymentHistory2 = "";
        }
        paymentHistory1 = paymentHistory1 + paymentHistory2;
        StringBuffer paymentHistoryBuff = new StringBuffer(paymentHistory1);
        //if (paymentHistoryBuff.length() > 36) {
        //   paymentHistoryBuff = new StringBuffer(paymentHistoryBuff
        //           .substring(paymentHistoryBuff.length() - 36));
        // }
        StringBuffer paymentConvert = new StringBuffer("");
        for (int i = 0; i < paymentHistoryBuff.length(); i = i + 3) {
            String monthPaymentCode = paymentHistoryBuff.substring(i, (i + 3));
            paymentConvert.append(this.getOverDueShotCode(monthPaymentCode.trim()));
            /*
             * if ("999".equals(monthPaymentCode)) { paymentConvert.append("-"); }
             * else { paymentConvert.append(monthPaymentCode.substring(2)); }
             */
        }
        return paymentConvert.toString();
    }

    public Vector getPolicyRules(String policyType) {
        if (policyType == null) {
            return null;
        }
        HashMap h = TableLookupCache.getCacheStructure();
        Vector vPolicyRulesCacheProperties = (Vector) (h.get("PoliclyRules"));
        //log.debug("OrigXRulesUtil
        // getPolicyRules-->vPolicyRulesCacheProperties Seize
        // "+vPolicyRulesCacheProperties.size());
        //log.debug("OrigXRulesUtil getPolicyRules-->policy type "+policyType);
        Vector vResult = new Vector();
        for (int i = 0; i < vPolicyRulesCacheProperties.size(); i++) {
            PolicyRulesCacheProperties policyRules = (PolicyRulesCacheProperties) vPolicyRulesCacheProperties.get(i);
            //log.debug("OrigXRulesUtil getPolicyRules-->policyRules policy
            // type"+policyRules.getPolicyType());
            if (policyType.equals(policyRules.getPolicyType())) {
                XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM = new XRulesPolicyRulesDataM();
                prmXRulesPolicyRulesDataM.setPolicyCode(policyRules.getPolicyCode());
                prmXRulesPolicyRulesDataM.setPolicyType(policyRules.getPolicyType());
                vResult.add(prmXRulesPolicyRulesDataM);
            }
        }
        return vResult;
    }

    private Vector mergePolicyRulesResult(XRulesVerificationResultDataM prmXRulesVerificationResultDataM, Vector policyRulesAutoResult) {
        Vector mergeResult = new Vector();
        Vector vXRulesPolicyRules = prmXRulesVerificationResultDataM.getVXRulesPolicyRulesDataM();
        if (vXRulesPolicyRules == null) {
            vXRulesPolicyRules = new Vector();
        }
        if (policyRulesAutoResult != null) {
            mergeResult.addAll(policyRulesAutoResult);
            //add Manual Result
            for (int i = 0; i < vXRulesPolicyRules.size(); i++) {
                XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM = (XRulesPolicyRulesDataM) vXRulesPolicyRules.get(i);
                if (XRulesConstant.PolicyRulesType.MANUAL.equalsIgnoreCase(prmXRulesPolicyRulesDataM.getPolicyType())) {
                    mergeResult.add(prmXRulesPolicyRulesDataM);
                }
            }
        }
        return mergeResult;
    }

    public String getPolicyDescription(String policycode) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector vPolicyRulesCacheProperties = (Vector) (h.get("PoliclyRules"));
        for (int i = 0; i < vPolicyRulesCacheProperties.size(); i++) {
            PolicyRulesCacheProperties policyRules = (PolicyRulesCacheProperties) vPolicyRulesCacheProperties.get(i);
            //log.debug("OrigXRulesUtil getPolicyRules-->policyRules policy
            // type"+policyRules.getPolicyType());
            if (policycode.equals(policyRules.getPolicyCode())) {
                return policyRules.getPolicyDesc();
            }
        }
        return "";
    }

    public String getDebtResult(String customerType, BigDecimal debtBdScore, BigDecimal debtBdIncome) {
        /////////////////////////////
        String result = XRulesConstant.ExecutionResultString.RESULT_PASS;
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(customerType)) {

            long income = Math.round(debtBdIncome.doubleValue());
            long debtScoreLong = Math.round(debtBdScore.doubleValue());
            log.debug("Income " + debtBdIncome);
            log.debug("Debt Score " + debtBdScore);
            if (debtScoreLong > 40l && (income >= 10000l && income <= 30000l)) {
                result = XRulesConstant.ExecutionResultString.RESULT_FAIL;
            } else if (debtScoreLong > 50l && (income >= 30001l && income <= 50000l)) {
                result = XRulesConstant.ExecutionResultString.RESULT_FAIL;
            } else if (debtScoreLong > 55l && (income >= 50001l && income <= 100000l)) {
                result = XRulesConstant.ExecutionResultString.RESULT_FAIL;
            } else if (debtScoreLong > 65l && (income > 100000l)) {
                result = XRulesConstant.ExecutionResultString.RESULT_FAIL;
            }
        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customerType) || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(customerType)) {
            if (debtBdScore.doubleValue() < XRulesConstant.debtComprateCorp) {
                result = XRulesConstant.ExecutionResultString.RESULT_FAIL;
            }
        }

        return result;
    }

    public String getFicoDecision(long score) {
        String decision = "Can't display Decision  score" + score;
        HashMap hCache = TableLookupCache.getCacheStructure();
        Vector vFicoScore = (Vector) (hCache.get("FICOScore"));
        if (vFicoScore != null) {
            if (vFicoScore.size() > 0) {
                FICOScoreProperties ficoScoreProp = (FICOScoreProperties) vFicoScore.get(0);
                if (score >= ficoScoreProp.getAcceptScore()) {
                    decision = OrigConstant.Scoring.SCORING_ACCEPT;
                } else if (score >= ficoScoreProp.getRejectScore()) {
                    decision = OrigConstant.Scoring.SCORING_REFER;
                } else {
                    decision = OrigConstant.Scoring.SCORING_REJECT;
                }
            }
        }
        return decision;
    }

    public String getFicoErrorDesciption(String reason) {
        String desc = "";
        HashMap hCache = TableLookupCache.getCacheStructure();
        if (hCache != null) {

            Vector vFicoError = (Vector) (hCache.get("FICOErrorCode"));
            if (vFicoError != null) {
                for (int i = 0; i < vFicoError.size(); i++) {
                    FICOErrorCodePropeties ficoErrorProp = (FICOErrorCodePropeties) vFicoError.get(i);
                    if (reason != null && reason.equalsIgnoreCase(ficoErrorProp.getReason())) {
                        desc = ficoErrorProp.getDescription();
                        break;
                    }
                }
            }
        }
        return desc;
    }

    public String getUserPosition(String userName) {
        String position = "";

        HashMap hCache = TableLookupCache.getCacheStructure();
        if (hCache != null) {

            Vector vUsrName = (Vector) (hCache.get("UsrName"));
            if (vUsrName != null) {
                for (int i = 0; i < vUsrName.size(); i++) {
                    UsrNameCacheProperties usrCacheProp = (UsrNameCacheProperties) vUsrName.get(i);
                    if (userName != null && userName.equalsIgnoreCase(usrCacheProp.getUserName())) {
                        position = usrCacheProp.getPostion();
                        break;
                    }
                }
            }
        }
        return position;
    }

    public String getLoanTypeDesc(String loanType) {
        String loanTypeDesc = "";

        HashMap hCache = TableLookupCache.getCacheStructure();
        if (hCache != null) {

            Vector vLoanType = (Vector) (hCache.get("LoanType"));
            if (vLoanType != null) {
                for (int i = 0; i < vLoanType.size(); i++) {
                    LoanTypeProperties loanTypeProp = (LoanTypeProperties) vLoanType.get(i);
                    if (loanType != null && loanType.equalsIgnoreCase(loanTypeProp.getLOANTYP())) {
                        loanTypeDesc = loanTypeProp.getTHDESC();
                        break;
                    }
                }
            }
        }
        return loanTypeDesc;
    }

    public String getCarBrandDesc(String carBrandCode) {
        String loanTypeDesc = "";

        HashMap hCache = TableLookupCache.getCacheStructure();
        if (hCache != null) {

            Vector vCarBrand = (Vector) (hCache.get("CarBrand"));
            if (vCarBrand != null) {
                for (int i = 0; i < vCarBrand.size(); i++) {
                    CarBrandProperties carbrandProp = (CarBrandProperties) vCarBrand.get(i);
                    if (carBrandCode != null && carBrandCode.equalsIgnoreCase(carbrandProp.getBRAND())) {
                        loanTypeDesc = carbrandProp.getTHDESC();
                        break;
                    }
                }
            }
        }
        return loanTypeDesc;
    }

    public String getCarModel(String carModelCode, String carBrand) {
        String carmodelDesc = "";
//        carmodelDesc = ORIGDAOFactory.getOrigVehicleMDAO().loandCarModelDesc(carModelCode, carBrand);
        	carmodelDesc = PLORIGEJBService.getORIGDAOUtilLocal().loandCarModelDesc(carModelCode, carBrand);
        if (carmodelDesc == null) {
            carmodelDesc = "";
        }
        return carmodelDesc;
    }

    public String getNCBAccountStatusDescription(String accountStatusCode) {
        String ncbAccountStatusDesc = "";
        HashMap hCache = TableLookupCache.getCacheStructure();
        if (hCache != null) {
            Vector vNCBAccountStatus = (Vector) (hCache.get("NCBAccountStatus"));
            if (vNCBAccountStatus != null) {
                for (int i = 0; i < vNCBAccountStatus.size(); i++) {
                    NCBAccountStatusProperties ncbAcStatusProp = (NCBAccountStatusProperties) vNCBAccountStatus.get(i);
                    if (accountStatusCode != null && accountStatusCode.equalsIgnoreCase(ncbAcStatusProp.getAcccountStatusCode())) {
                        ncbAccountStatusDesc = ncbAcStatusProp.getThAccountDescription();
                        break;
                    }
                }
            }
        }
        return ncbAccountStatusDesc;
    }

    public String getNCBAccountTypeDescription(String accountTypeCode) {
        String ncbAccountTypeDesc = "";
        HashMap hCache = TableLookupCache.getCacheStructure();
        if (hCache != null) {
            Vector vNCBAccountType = (Vector) (hCache.get("NCBAccountType"));
            if (vNCBAccountType != null) {
                for (int i = 0; i < vNCBAccountType.size(); i++) {
                    NCBAccountTypeProperties ncbAcTypeProp = (NCBAccountTypeProperties) vNCBAccountType.get(i);
                    if (accountTypeCode != null && accountTypeCode.equalsIgnoreCase(ncbAcTypeProp.getAccountCode())

                    ) {
                        ncbAccountTypeDesc = ncbAcTypeProp.getAccountDescription();
                        break;
                    }
                }
            }
        }
        return ncbAccountTypeDesc;
    }

    public String getOverDueShotCode(String overDueCode) {
        String overDueShotCode = "";
        HashMap hCache = TableLookupCache.getCacheStructure();
        if (hCache != null) {
            Vector vOverDueCode = (Vector) (hCache.get("NCBOverDueStatus"));
            if (vOverDueCode != null) {
                for (int i = 0; i < vOverDueCode.size(); i++) {
                    NCBOverDueStatusProperties ncbOverDueStatusProp = (NCBOverDueStatusProperties) vOverDueCode.get(i);
                    if (overDueCode != null && overDueCode.equalsIgnoreCase(ncbOverDueStatusProp.getCode())

                    ) {
                        overDueShotCode = ncbOverDueStatusProp.getShotCode();
                        break;
                    }
                }
            }
        }
        return overDueShotCode;

    }

    public String getOverDueDescription(String overDueCode) {
        String overDueDescription = "";
        HashMap hCache = TableLookupCache.getCacheStructure();
        if (hCache != null) {
            Vector vOverDueCode = (Vector) (hCache.get("NCBOverDueStatus"));
            if (vOverDueCode != null) {
                for (int i = 0; i < vOverDueCode.size(); i++) {
                    NCBOverDueStatusProperties ncbOverDueStatusProp = (NCBOverDueStatusProperties) vOverDueCode.get(i);
                    if (overDueCode != null && overDueCode.equalsIgnoreCase(ncbOverDueStatusProp.getCode())

                    ) {
                        overDueDescription = ncbOverDueStatusProp.getDescription();
                        break;
                    }
                }
            }
        }
        return overDueDescription;

    }

    private String cutLenghtResult(String strMas) {
        String result = null;
        if (strMas != null && strMas.length() >= 80) {
            result = strMas.substring(0, 80);
        } else {
            return strMas;
        }
        return result;
    }

    public String getButtonStatus(String personalType, int serviceID, String customertype, String jobstate, UserDetailM userM) {
        return getButtonStatus(personalType, serviceID, customertype, jobstate, userM, "");
    }

    public String getButtonStatus(String personalType, int serviceID, String customertype, String jobstate, UserDetailM userM, String coborrowerFlag) {
        String result = "";
        if (OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalType)) {
            switch (serviceID) {
            case XRulesConstant.ServiceID.NCB: {
                if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customertype) || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(customertype)) {
                    result = OrigConstant.XRULES_BUTTON_DISABLE;
                }
                if (userM.getUserExceptionVect() != null && userM.getUserExceptionVect().size() > 0) {
                    Vector userExceptions = userM.getUserExceptionVect();
                    for (int i = 0; i < userExceptions.size(); i++) {
                        UserExceptionDataM userExceptionDataM = (UserExceptionDataM) userExceptions.get(i);
                        if (OrigConstant.NCB_EXCEPTION_ID.equalsIgnoreCase(userExceptionDataM.getExceptionId())) {
                            result = OrigConstant.XRULES_BUTTON_DISABLE;
                            break;
                        }
                    }
                }
                break;
            }
            case XRulesConstant.ServiceID.BLACKLIST: {
                break;
            }
            case XRulesConstant.ServiceID.BLACKLIST_VEHICLE: {
                if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equalsIgnoreCase(jobstate)
                        || ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equalsIgnoreCase(jobstate)) {
                    result = OrigConstant.XRULES_BUTTON_HIDE;
                }
                break;
            }
            case XRulesConstant.ServiceID.EXIST_CUSTOMER: {

                break;
            }
            case XRulesConstant.ServiceID.DEDUP: {

                break;
            }
            case XRulesConstant.ServiceID.DUP_VEHICLE: {
                if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equalsIgnoreCase(jobstate)
                        || ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equalsIgnoreCase(jobstate)) {
                    result = OrigConstant.XRULES_BUTTON_HIDE;
                }
                break;
            }
            case XRulesConstant.ServiceID.LPM: {
                break;
            }
            case XRulesConstant.ServiceID.POLICYRULES: {
                //if
                // (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customertype)
                // ||
                // ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equals(jobstate)
                if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equals(jobstate) || ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equals(jobstate)) {
                    result = OrigConstant.XRULES_BUTTON_DISABLE;
                }
                break;
            }
            case XRulesConstant.ServiceID.DEBTBURDEN: {
                if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equals(jobstate) || ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equals(jobstate)) {
                    result = OrigConstant.XRULES_BUTTON_DISABLE;
                }
                break;
            }
            case XRulesConstant.ServiceID.FICO: {
                if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customertype) || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(customertype)) {
                    result = OrigConstant.XRULES_BUTTON_DISABLE;
                }
                break;
            }
            case XRulesConstant.ServiceID.DEBT_AMOUNT: {
                if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equals(jobstate) || ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equals(jobstate)) {
                    result = OrigConstant.XRULES_BUTTON_DISABLE;
                }
            }
            default:
            }
        } else if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equalsIgnoreCase(personalType)||OrigConstant.PERSONAL_TYPE_SUP_CARD.equalsIgnoreCase(personalType)) {
            switch (serviceID) {
            case XRulesConstant.ServiceID.NCB: {
                if (OrigConstant.COBORROWER_FLAG_NO.equals(coborrowerFlag) || coborrowerFlag == null) {
                    result = OrigConstant.XRULES_BUTTON_DISABLE;
                } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customertype)
                        || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(customertype)) {
                    result = OrigConstant.XRULES_BUTTON_DISABLE;
                }
                if (userM.getUserExceptionVect() != null && userM.getUserExceptionVect().size() > 0) {
                    Vector userExceptions = userM.getUserExceptionVect();
                    for (int i = 0; i < userExceptions.size(); i++) {
                        UserExceptionDataM userExceptionDataM = (UserExceptionDataM) userExceptions.get(i);
                        if (OrigConstant.NCB_EXCEPTION_ID.equalsIgnoreCase(userExceptionDataM.getExceptionId())) {
                            result = OrigConstant.XRULES_BUTTON_DISABLE;
                            break;
                        }
                    }
                }
                break;
            }
            case XRulesConstant.ServiceID.BLACKLIST: {

                break;
            }
            case XRulesConstant.ServiceID.BLACKLIST_VEHICLE: {
                result = OrigConstant.XRULES_BUTTON_HIDE;
                break;
            }
            case XRulesConstant.ServiceID.EXIST_CUSTOMER: {

                break;
            }
            case XRulesConstant.ServiceID.DEDUP: {

                break;
            }
            case XRulesConstant.ServiceID.DUP_VEHICLE: {
                result = OrigConstant.XRULES_BUTTON_HIDE;
                break;
            }
            case XRulesConstant.ServiceID.LPM: {
                break;
            }
            case XRulesConstant.ServiceID.POLICYRULES: {
                if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(customertype) || ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equals(jobstate)
                        || ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equals(jobstate)) {
                    result = OrigConstant.XRULES_BUTTON_DISABLE;
                }
                break;
            }
            case XRulesConstant.ServiceID.DEBTBURDEN: {
                // result = OrigConstant.XRULES_BUTTON_DISABLE;
                break;
            }
            case XRulesConstant.ServiceID.FICO: {
                result = OrigConstant.XRULES_BUTTON_DISABLE;
                break;
            }
            case XRulesConstant.ServiceID.KHONTHAI: {

                break;
            }
            case XRulesConstant.ServiceID.THAIREGITRATION: {
                break;
            }
            case XRulesConstant.ServiceID.BOL: {

                break;
            }
            case XRulesConstant.ServiceID.YELLOWPAGE: {
                break;
            }
            case XRulesConstant.ServiceID.PHONEBOOK: {
                break;
            }
            case XRulesConstant.ServiceID.DEBT_AMOUNT: {
                //result = OrigConstant.XRULES_BUTTON_DISABLE;
                if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equals(jobstate) || ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equals(jobstate)) {
                    result = OrigConstant.XRULES_BUTTON_DISABLE;
                }
                break;
            }
            case XRulesConstant.ServiceID.PHONE_VER: {
                result = OrigConstant.XRULES_BUTTON_HIDE;
            }
            default:
            }
        }
        return result;
    }

    public double getPercentOfRevolving(Vector vAccountDetail) {
        BigDecimal totalCreditUse = new BigDecimal(0);
        BigDecimal totalCreditLimit = new BigDecimal(0);
        for (int j = 0; j < vAccountDetail.size(); j++) {
            TLRespM account = (TLRespM) vAccountDetail.get(j);
            String accountStatusFlag = (String) XRulesConstant.hAccountStatusDebtAmt.get(account.getAccountStatus());

            if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountStatusFlag)) {
                totalCreditUse = totalCreditUse.add(new BigDecimal(account.getAmtOwed()));
                totalCreditLimit = totalCreditLimit.add(new BigDecimal(account.getCreditLimOriLoanAmt()));
            }
        }
        BigDecimal percentOfRevolving = (totalCreditUse.divide(totalCreditLimit, XRulesConstant.MAX_SCALE, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(
                100));
        return percentOfRevolving.doubleValue();
    }

    public int getAge(Date birthDate) {
        if (birthDate != null) {
            Calendar calBirthDate = Calendar.getInstance();
            calBirthDate.setTime(birthDate);
            Calendar now = Calendar.getInstance();
            int years = now.get(Calendar.YEAR) - calBirthDate.get(Calendar.YEAR);
            if (now.get(Calendar.MONTH) < calBirthDate.get(Calendar.MONTH))
                years--;
            else if (now.get(Calendar.MONTH) == calBirthDate.get(Calendar.MONTH) && now.get(Calendar.DATE) < calBirthDate.get(Calendar.DATE))
                years--;
            return years;
        } else {
            return -1;
        }
    }

    public Vector getNCBAccountDetail(Vector NCBAccounts, String accountType) {
        Vector result = new Vector();
        for (int i = 0; i < NCBAccounts.size(); i++) {
            TLRespM account = (TLRespM) NCBAccounts.get(i);
            if (accountType != null && accountType.equalsIgnoreCase(account.getAccountType())) {
                result.add(account);
            }
        }
        return result;
    }

    public TLRespM getNCBAccount(Vector NCBAccounts, String accountType, String segmentValue, int groupSeq) {
        TLRespM result = new TLRespM();
        for (int i = 0; i < NCBAccounts.size(); i++) {
            TLRespM account = (TLRespM) NCBAccounts.get(i);
            if (accountType != null && accountType.equalsIgnoreCase(account.getAccountType()) && segmentValue != null
                    && segmentValue.equalsIgnoreCase(account.getSegmentValue()) && account.getGroupSeq() == groupSeq) {
                result = account;
            }
        }
        return result;
    }

    public String displayNCBDate(String strNcbDate) {
        String result = "";
        if (strNcbDate != null && strNcbDate.length() == 6) {
            result = strNcbDate.substring(0, 2) + ":" + strNcbDate.substring(2, 4) + ":" + strNcbDate.substring(4, 6);
        }
        return result;
    }

    public String getNCBParamEngDescription(String segmentName, String fields, String code) {
        String result = "";
        if (code == null) {
            code = "";
        }
        if (ncbMasterData == null) {
            log.debug("Map NCB Master Data");
            HashMap hashLookup = TableLookupCache.getCacheStructure();
            Vector vNCBParamMasterProperties = (Vector) hashLookup.get("NCBParamMaster");
            if (vNCBParamMasterProperties != null) {
                ncbMasterData = new HashMap();
                for (int i = 0; i < vNCBParamMasterProperties.size(); i++) {
                    NCBParamMasterProperties ncbMasterParamProp = (NCBParamMasterProperties) vNCBParamMasterProperties.get(i);
                    HashMap segmentHash = (HashMap) ncbMasterData.get(ncbMasterParamProp.getSegment());
                    if (segmentHash == null) {
                        segmentHash = new HashMap();
                        ncbMasterData.put(ncbMasterParamProp.getSegment(), segmentHash);
                    }
                    HashMap fieldsHash = (HashMap) segmentHash.get(ncbMasterParamProp.getField());
                    if (fieldsHash == null) {
                        fieldsHash = new HashMap();
                        segmentHash.put(ncbMasterParamProp.getField(), fieldsHash);
                    }
                    fieldsHash.put(ncbMasterParamProp.getCode(), ncbMasterParamProp);
                }
            }
        }
        HashMap hSegment = (HashMap) ncbMasterData.get(segmentName);
        if (hSegment != null) {
            HashMap hField = (HashMap) hSegment.get(fields);
            if (hField != null) {
                NCBParamMasterProperties ncbParamMaster = (NCBParamMasterProperties) hField.get(code);
                if (ncbParamMaster != null) {
                    result = ncbParamMaster.getEngDesc();
                }
            }
        }

        /*
         * 
         * if(data.getSegment()!=null){ HashMap
         * segmentHash=(HashMap)hashNCBParam.get(data.getSegment());
         * if(segmentHash==null){ segmentHash=new HashMap();
         * hashNCBParam.put(data.getSegment(),segmentHash); } // HashMap
         * fieldsHash=(HashMap)segmentHash.get(data.getField());
         * if(fieldsHash==null){ fieldsHash=new HashMap();
         * segmentHash.put(data.getField(),fieldsHash); }
         * fieldsHash.put(data.getField(),data); }
         */

        //  if(hashLookup!=null){
        //   HashMap hashLookup.get("NCBParamMaster");
        // }
        return result;
    }

    /**
     * @return Returns the ncbMasterData.
     */
    public static HashMap getNcbMasterData() {
        return ncbMasterData;
    }

    /**
     * @param ncbMasterData
     *            The ncbMasterData to set.
     */
    public static void setNcbMasterData(HashMap ncbMasterData) {
        OrigXRulesUtil.ncbMasterData = ncbMasterData;
    }

    public String getExistingApplicaitonInprogressStatus(String appStatus) {
        String result = "";
        if (appStatus != null) {
            if (appStatus.length() > 1) {
                result = appStatus;
            } else {
                //get status form master
                Vector vAppstatus = (Vector) ORIGUtility.getInstance().getMasterDataFormCache("APPSTATUS");
                if (vAppstatus != null) {
                    for (int i = 0; i < vAppstatus.size(); i++) {
                        ParameterDetailCodeProperties parameterDetail = (ParameterDetailCodeProperties) vAppstatus.get(i);
                        if (appStatus.equalsIgnoreCase(parameterDetail.getPRMFLAG())) {
                            result = parameterDetail.getENDESC();
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    public Vector getNCBAccountStatus() {
        Vector vNCBAccountStatus = null;
        HashMap hCache = TableLookupCache.getCacheStructure();
        if (hCache != null) {
            vNCBAccountStatus = (Vector) (hCache.get("NCBAccountStatus"));
        }
        return vNCBAccountStatus;
    }

    public XRulesNCBAdjustDataM getNCBAdjust(Vector vNCBAdjust, TLRespM account) {
        if (vNCBAdjust == null) {
            return null;
        }
        XRulesNCBAdjustDataM result = null;
        for (int i = 0; i < vNCBAdjust.size(); i++) {
            XRulesNCBAdjustDataM xrulesNCbAdjust = (XRulesNCBAdjustDataM) vNCBAdjust.get(i);
            if (account.getSegmentValue().equals(xrulesNCbAdjust.getNCBSegmentValue()) && account.getGroupSeq() == xrulesNCbAdjust.getGroupSeq()) {
                result = xrulesNCbAdjust;
            }
        }
        return result;
    }

    public String getVerificationAdjustResult(XRulesVerificationResultDataM xRulesVerification, int serviceID, PersonalInfoDataM personalInfoDataM) {
        String result = "";
        if (xRulesVerification == null) {
            return "";
        }
        switch (serviceID) {

        case XRulesConstant.ServiceID.DEBT_AMOUNT: {
            if (xRulesVerification.getDebtAmountAdjustResult() != null) {
                try {
                    result = ORIGDisplayFormatUtil.displayCommaNumber(xRulesVerification.getDebtAmountAdjustResult());
                } catch (Exception e) {
                }
            } else {
                result = "";
            }
            break;
        }
        case XRulesConstant.ServiceID.DEBTBURDEN: {
            try {
                XRulesDebtBdDataM prmXrulesDebtBDDataM = xRulesVerification.getXRulesDebtBdDataM();
                // result =ORIGDisplayFormatUtil.displayCommaNumber(
                // prmXrulesDebtBDDataM.getDebtBurdentScoreAdjust());
                if (prmXrulesDebtBDDataM != null && prmXrulesDebtBDDataM.getDebtBurdentScoreAdjust() != null) {
                    if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())
                            || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {

                        try {
                            result = ORIGDisplayFormatUtil.formatNumber("###,###,###,###,###", prmXrulesDebtBDDataM.getDebtBurdentScoreAdjust()) + "%";
                        } catch (Exception e) {
                            log.fatal("error", e);
                        }

                    } else {
                        try {
                            result = ORIGDisplayFormatUtil.displayCommaNumber(prmXrulesDebtBDDataM.getDebtBurdentScoreAdjust());
                        } catch (Exception e) {
                            log.fatal("error", e);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            break;
        }
        default:
        }
        return result;
    }

    public BigDecimal getTotalNCBBurdent(Vector vTlRespMs, BigDecimal odInterest, Vector vHsRespMs) {
        BigDecimal result = new BigDecimal(0);
        BigDecimal sumOfInstallmentLoan = new BigDecimal(0);
        BigDecimal sumOfNonInstallmentLoan = new BigDecimal(0);
        Vector creditCardAccount = new Vector();
        Vector creditCardAmexAccount = new Vector();
        Vector revolvingAccount = new Vector();
        Vector overdraftAccount = new Vector();
        Vector overdraftInterestAccount = new Vector();
        ORIGUtility utiliy = ORIGUtility.getInstance();
        //BigDecimal interestRateCreditCard =
        // utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("CREDITCARD_INTEREST_DEFAULT"));
        //if (interestRateCreditCard == null) {
        //   interestRateCreditCard = OrigConstant.interestRateCreditCard;
        //}
        BigDecimal interestRateCreditCard = utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("CREDITCARD_INTEREST_DEFAULT"));
        BigDecimal mrrRate = utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("MRR_INTEREST_RATE"));
        BigDecimal odPlusRate = utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("OD_VER_1000K_PLUS_RATE"));
        BigDecimal errorPlusRate = utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("ERROR_PLUS_INTEREST_RATE"));
        BigDecimal errorRate = new BigDecimal(24.5);
        BigDecimal odRate = new BigDecimal(12.5);
        if (mrrRate != null && errorPlusRate != null) {
            errorRate = (mrrRate.add(errorPlusRate)).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
        }
        if (odInterest != null && odPlusRate != null) {
            odRate = (odInterest.add(odPlusRate)).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
        }
        log.debug("Error Rate" + errorPlusRate);
        if (vTlRespMs != null) {
            for (int i = 0; i < vTlRespMs.size(); i++) {
                TLRespM tlRespM = (TLRespM) vTlRespMs.get(i);
                log.debug("=======================================");
                log.debug("Account Status=" + tlRespM.getAccountStatus());
                String accountFlag = (String) XRulesConstant.hAccountStatusDebtAmt.get(tlRespM.getAccountStatus());
                log.debug("Account Falg " + accountFlag);
                log.debug(" Member Code " + tlRespM.getMemberCode());
                if (!(OrigConstant.MEBERCODE.KL.equalsIgnoreCase(tlRespM.getMemberCode()))) {//check
                    // membercode
                    //check account type
                    if (OrigConstant.AccountType.CreditCard.equalsIgnoreCase(tlRespM.getAccountType())) {
                        if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)) {
                            log.debug("Non Installment Loan Credit card");
                            log.debug("Credit limit " + tlRespM.getCreditLimOriLoanAmt());
                            if (XRulesConstant.AMEX_CREDIT_LIMIT.compareTo(new BigDecimal(tlRespM.getCreditLimOriLoanAmt())) == 0) {
                                log.debug("Credit Card AMEX");
                                creditCardAmexAccount.add(tlRespM);
                            } else {
                                log.debug("Credit Card not AMEX");
                                if (Double.compare(tlRespM.getCreditLimOriLoanAmt(), 40000d) >= 0) {
                                    log.debug("Credit limit >40000");
                                    creditCardAccount.add(tlRespM);
                                }
                            }
                        }
                    } else if (OrigConstant.AccountType.OverDraft.equalsIgnoreCase(tlRespM.getAccountType())) {
                        if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)) {
                            log.debug("Non Installment Loan OD");
                            log.debug("Credit limit " + tlRespM.getCreditLimOriLoanAmt());
                            if (Double.compare(tlRespM.getCreditLimOriLoanAmt(), 100001d) >= 0) {
                                overdraftInterestAccount.add(tlRespM);
                            } else if (Double.compare(tlRespM.getCreditLimOriLoanAmt(), 40000d) > 0) {
                                log.debug("Credit limit >40000");
                                overdraftAccount.add(tlRespM);
                            }
                        }
                    } else if (OrigConstant.AccountType.Revolving.equalsIgnoreCase(tlRespM.getAccountType())) {
                        if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)) {
                            log.debug("Non Installment Loan Revolving");
                            log.debug("Credit limit " + tlRespM.getCreditLimOriLoanAmt());
                            if (Double.compare(tlRespM.getCreditLimOriLoanAmt(), 40000d) > 0) {
                                log.debug("Credit limit >40000");
                                revolvingAccount.add(tlRespM);
                            }
                        }
                    } else {//installment loan
                        if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag) || XRulesConstant.ACCOUNT_FLAG_C.equalsIgnoreCase(accountFlag)) {
                            log.debug("Installment Loan");
                            log.debug("Loan Amount " + tlRespM.getCreditLimOriLoanAmt());
                            // if (tlRespM.getCreditLimOriLoanAmt() > 40000d) {
                            //     log.debug("Loan Amount>40000");
                            //check Os Balance and
                            if (Double.compare(tlRespM.getInstallAmt(), 0d) == 0 && Double.compare(tlRespM.getAmtOwed(), 0) > 0) { //[ERROR
                                // Case:
                                // Data
                                // from
                                // NCB
                                // is
                                // abnormally
                                // identify
                                // by
                                // "Outstanding
                                // > 0
                                // but
                                // installment
                                // = 0
                                // or
                                // blank"
                                BigDecimal installment = getLast3MonthPaymentAverage(vHsRespMs, tlRespM.getSegmentValue(), tlRespM.getGroupSeq(), errorRate,
                                        tlRespM.getAmtOwed());
                                sumOfInstallmentLoan = sumOfInstallmentLoan.add(installment);
                                log.debug("Case Error  Installment Argv 3 monht" + installment);
                            } else { //normal Case
                                sumOfInstallmentLoan = sumOfInstallmentLoan.add(new BigDecimal(tlRespM.getInstallAmt()));
                            }
                            // }
                        }
                    }

                }

            }
            // sum non insallment
            //OD

            BigDecimal overDrafResult = new BigDecimal(0);
            if (overdraftAccount.size() > 0) {
                BigDecimal sumOverdraftCreditUse = new BigDecimal(0);
                for (int i = 0; i < overdraftAccount.size(); i++) {
                    TLRespM account = (TLRespM) overdraftAccount.get(i);
                    if (account != null) {
                        sumOverdraftCreditUse = sumOverdraftCreditUse.add(getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(), account
                                .getGroupSeq(), interestRateCreditCard, account.getAmtOwed()));
                    }
                }
                overDrafResult = sumOverdraftCreditUse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            log.debug(" OverDraft >40000 and <=100000 Result =" + overDrafResult);

            BigDecimal overDrafInterestResult = new BigDecimal(0);
            if (overdraftInterestAccount.size() > 0) {
                BigDecimal sumOverdraftInterestCreditUse = new BigDecimal(0);
                for (int i = 0; i < overdraftInterestAccount.size(); i++) {
                    TLRespM account = (TLRespM) overdraftInterestAccount.get(i);
                    if (account != null) {
                        sumOverdraftInterestCreditUse = sumOverdraftInterestCreditUse.add(getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(),
                                account.getGroupSeq(), odRate, account.getAmtOwed()));
                    }
                }
                overDrafInterestResult = sumOverdraftInterestCreditUse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            log.debug(" OverDraft >10000 Result =" + overDrafInterestResult);

            //Revolving
            BigDecimal revolvingResult = new BigDecimal(0);
            if (revolvingAccount.size() > 0) {

                BigDecimal sumRevolingCredituse = new BigDecimal(0);
                for (int i = 0; i < revolvingAccount.size(); i++) {
                    TLRespM account = (TLRespM) revolvingAccount.get(i);
                    if (account != null) {
                        sumRevolingCredituse = sumRevolingCredituse.add(getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(),
                                account.getGroupSeq(), interestRateCreditCard, account.getAmtOwed()));
                    }
                }
                revolvingResult = sumRevolingCredituse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            log.debug(" Revolving Result =" + revolvingResult);
            //AMEX credit card
            BigDecimal creditAmexResult = new BigDecimal(0);
            if (creditCardAmexAccount.size() > 0) {

                BigDecimal sumCreditAmexCreditUse = new BigDecimal(0);
                for (int i = 0; i < creditCardAmexAccount.size(); i++) {
                    TLRespM account = (TLRespM) creditCardAmexAccount.get(i);
                    if (account != null) {
                        sumCreditAmexCreditUse = sumCreditAmexCreditUse.add(getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(), account
                                .getGroupSeq(), interestRateCreditCard, account.getAmtOwed()));
                    }
                }
                creditAmexResult = sumCreditAmexCreditUse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            log.debug(" Amex Creditcard Result =" + creditAmexResult);
            //Credit Card result
            BigDecimal creditCardResult = new BigDecimal(0);
            if (creditCardAccount.size() > 0) {
                BigDecimal sumCreditCardCreditUse = new BigDecimal(0);
                for (int i = 0; i < creditCardAccount.size(); i++) {
                    TLRespM account = (TLRespM) creditCardAccount.get(i);
                    if (account != null) {
                        sumCreditCardCreditUse = sumCreditCardCreditUse.add(getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(), account
                                .getGroupSeq(), interestRateCreditCard, account.getAmtOwed()));
                    }
                }
                creditCardResult = sumCreditCardCreditUse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            log.debug(" Creditcard Result =" + creditCardResult);
            log.debug("=======================================");
            sumOfNonInstallmentLoan = creditCardResult.add(creditAmexResult).add(overDrafResult).add(revolvingResult).add(overDrafInterestResult);
            log.debug("sumOfNonInstallmentLoan " + sumOfNonInstallmentLoan);
            log.debug("sumOfInstallmentLoan " + sumOfInstallmentLoan);
        }
        result = sumOfInstallmentLoan.add(sumOfNonInstallmentLoan);
        log.debug("total NCB " + result);
        log.debug("=======================================");
        return result;
    }

    public static BigDecimal getLast3MonthPaymentAverage(Vector paymentHistory, String segmentValue, int groupSeq, BigDecimal interest,
            double currentMonthPayment) {
        Vector AccountPaymentHistory = new Vector();
        BigDecimal result = new BigDecimal(0);
        if (segmentValue != null || !"".equals(segmentValue)) {
            for (int i = 0; i < paymentHistory.size(); i++) {
                HSRespM payHistoryRespM = (HSRespM) paymentHistory.get(i);
                if (segmentValue.equalsIgnoreCase(payHistoryRespM.getSegmentValue()) && groupSeq == payHistoryRespM.getGroupSeq()) {
                    AccountPaymentHistory.add(payHistoryRespM);
                }
            }
            while (AccountPaymentHistory.size() > 2) {
                AccountPaymentHistory.remove(0);
            }
            BigDecimal sumAccountPayment = new BigDecimal(0);
            for (int j = 0; j < AccountPaymentHistory.size(); j++) {
                HSRespM payHistoryRespM = (HSRespM) AccountPaymentHistory.get(j);
                sumAccountPayment = sumAccountPayment.add(new BigDecimal(payHistoryRespM.getAmtOwed()));
            }
            sumAccountPayment = sumAccountPayment.add(new BigDecimal(currentMonthPayment));
            log.debug("sumAccountPayment " + sumAccountPayment);
            log.debug("interest " + interest);
            if (interest != null) {
                result = (interest.multiply(sumAccountPayment.divide(new BigDecimal(1 + AccountPaymentHistory.size()), XRulesConstant.MAX_SCALE,
                        BigDecimal.ROUND_HALF_UP))).divide(new BigDecimal(100), XRulesConstant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
            }
        }
        result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.debug("getLast3MonthPaymentAverage result=" + result);
        return result;
    }

    public BigDecimal getTotalExposure(XRulesVerificationResultDataM xrulesVer, LoanDataM loanDataM) {

        Vector xRuesExistCustomer = xrulesVer.getVXRulesExistcustDataM();
        if (xRuesExistCustomer == null) {
            xRuesExistCustomer = new Vector();
        }
        Vector xRuesExistCustomerInprogress = xrulesVer.getVXRulesExistcustInprogressDataM();
        if (xRuesExistCustomerInprogress == null) {
            xRuesExistCustomerInprogress = new Vector();
        }
        BigDecimal totalFinanceAmt = new BigDecimal(0);
        BigDecimal totalNetFinanceAmt = new BigDecimal(0);
        BigDecimal totalExposureExclude = new BigDecimal(0);
        BigDecimal totalExposure = new BigDecimal(0);
        BigDecimal totalOriginalFinanceAmt = new BigDecimal(0);
        BigDecimal totalInstallment = BigDecimal.valueOf(0);
        for (int i = 0; i < xRuesExistCustomerInprogress.size(); i++) {
            XRulesExistcustInprogressDataM xRulesExistingCustomerInprogress = (XRulesExistcustInprogressDataM) xRuesExistCustomerInprogress.get(i);
            if (xRulesExistingCustomerInprogress.getFinanceAmt() != null) {
                totalFinanceAmt = totalFinanceAmt.add(xRulesExistingCustomerInprogress.getFinanceAmt());
            }
        }

        for (int i = 0; i < xRuesExistCustomer.size(); i++) {
            XRulesExistcustDataM xRulesExistingCustomerDataM = (XRulesExistcustDataM) xRuesExistCustomer.get(i);
            if ("A".equalsIgnoreCase(xRulesExistingCustomerDataM.getContractStatus())) {
                totalNetFinanceAmt = totalNetFinanceAmt.add(xRulesExistingCustomerDataM.getNetFinanceAmount());
                totalOriginalFinanceAmt = totalOriginalFinanceAmt.add(xRulesExistingCustomerDataM.getOriginalFinaceAmount());
                totalInstallment = totalInstallment.add(xRulesExistingCustomerDataM.getInstallment());
            }
        }
        totalExposureExclude = totalFinanceAmt.add(totalNetFinanceAmt);
        // ORIGCacheUtil origCacheUtil = ORIGCacheUtil.getInstance();
        //LoanDataM loanDataM=null;
        // if(appForm.getLoanVect()!=null&&appForm.getLoanVect().size()>0){
        //  loanDataM=(LoanDataM)appForm.getLoanVect().get(0);
        if (loanDataM != null) {
            //Patt do for KBank demo //Start
            //totalExposure = totalExposureExclude.add(loanDataM.getCostOfFinancialAmt());
            totalExposure = totalExposureExclude.add(new BigDecimal(500000));
            //Patt End
        } else {
            totalExposure = totalExposureExclude;
        }
        return totalExposure;
        //  }

    }

    public BigDecimal getTotalNCBBurdentAdjust(Vector vTlRespMs, BigDecimal odInterest, Vector vHsRespMs, Vector vNCBAdjust, HashMap hNCBDetailAdjust) {
        BigDecimal result = new BigDecimal(0);
        BigDecimal sumOfInstallmentLoan = new BigDecimal(0);
        BigDecimal sumOfNonInstallmentLoan = new BigDecimal(0);
        BigDecimal sumInstallmnetAdjust = new BigDecimal(0);
        Vector creditCardAccount = new Vector();
        Vector creditCardAmexAccount = new Vector();
        Vector revolvingAccount = new Vector();
        Vector overdraftAccount = new Vector();
        Vector overdraftInterestAccount = new Vector();
        ORIGUtility utiliy = ORIGUtility.getInstance();
        //BigDecimal interestRateCreditCard =
        // utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("CREDITCARD_INTEREST_DEFAULT"));
        //if (interestRateCreditCard == null) {
        //   interestRateCreditCard = OrigConstant.interestRateCreditCard;
        // }
        BigDecimal interestRateCreditCard = utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("CREDITCARD_INTEREST_DEFAULT"));
        BigDecimal mrrRate = utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("MRR_INTEREST_RATE"));
        BigDecimal odPlusRate = utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("OD_VER_1000K_PLUS_RATE"));
        BigDecimal errorPlusRate = utiliy.stringToBigDecimal(utiliy.getGeneralParamByCode("ERROR_PLUS_INTEREST_RATE"));
        BigDecimal errorRate = new BigDecimal(24.5);
        BigDecimal odRate = new BigDecimal(12.5);
        if (mrrRate != null && errorPlusRate != null) {
            errorRate = (mrrRate.add(errorPlusRate)).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
        }
        if (odInterest != null && odPlusRate != null) {
            odRate = (odInterest.add(odPlusRate)).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
        }
        log.debug("Error Rate" + errorPlusRate);
        if (vNCBAdjust == null) {
            vNCBAdjust = new Vector();
        }
        if (vTlRespMs != null) {
            for (int i = 0; i < vTlRespMs.size(); i++) {
                TLRespM tlRespM = (TLRespM) vTlRespMs.get(i);
                log.debug("=======================================");
                log.debug("Account Status=" + tlRespM.getAccountStatus());
                String accountFlag = (String) XRulesConstant.hAccountStatusDebtAmt.get(tlRespM.getAccountStatus());
                log.debug("Account Falg " + accountFlag);
                log.debug(" Member Code " + tlRespM.getMemberCode());
                if (!(OrigConstant.MEBERCODE.KL.equalsIgnoreCase(tlRespM.getMemberCode()))) {//check
                    // membercode
                    //check account type
                    if (OrigConstant.AccountType.CreditCard.equalsIgnoreCase(tlRespM.getAccountType())) {
                        if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)) {
                            log.debug("Non Installment Loan Credit card");
                            log.debug("Credit limit " + tlRespM.getCreditLimOriLoanAmt());
                            XRulesNCBAdjustDataM acccountInstallmentAdjust = getNCBAdjust(vNCBAdjust, tlRespM);
                            if (acccountInstallmentAdjust != null) {
                                log.debug("Installment adjust");
                                sumInstallmnetAdjust = sumInstallmnetAdjust.add(acccountInstallmentAdjust.getNcbInstallmentAdjustAmount());
                                hNCBDetailAdjust.put(tlRespM.getSegmentValue() + "_" + String.valueOf(tlRespM.getGroupSeq()), acccountInstallmentAdjust
                                        .getNcbInstallmentAdjustAmount());
                            } else {
                                if (XRulesConstant.AMEX_CREDIT_LIMIT.compareTo(new BigDecimal(tlRespM.getCreditLimOriLoanAmt())) == 0) {
                                    log.debug("Credit Card AMEX");
                                    creditCardAmexAccount.add(tlRespM);
                                } else {
                                    log.debug("Credit Card not AMEX");
                                    if (Double.compare(tlRespM.getCreditLimOriLoanAmt(), 40000d) >= 0) {
                                        log.debug("Credit limit >40000");
                                        creditCardAccount.add(tlRespM);
                                    }
                                }
                            }
                        }
                    } else if (OrigConstant.AccountType.OverDraft.equalsIgnoreCase(tlRespM.getAccountType())) {
                        if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)) {
                            log.debug("Non Installment Loan OD");
                            log.debug("Credit limit " + tlRespM.getCreditLimOriLoanAmt());
                            XRulesNCBAdjustDataM acccountInstallmentAdjust = getNCBAdjust(vNCBAdjust, tlRespM);
                            if (acccountInstallmentAdjust != null) {
                                log.debug("Installment adjust");
                                sumInstallmnetAdjust = sumInstallmnetAdjust.add(acccountInstallmentAdjust.getNcbInstallmentAdjustAmount());
                                hNCBDetailAdjust.put(tlRespM.getSegmentValue() + "_" + String.valueOf(tlRespM.getGroupSeq()), acccountInstallmentAdjust
                                        .getNcbInstallmentAdjustAmount());
                            } else {
                                if (Double.compare(tlRespM.getCreditLimOriLoanAmt(), 100001d) >= 0) {
                                    overdraftInterestAccount.add(tlRespM);
                                } else if (Double.compare(tlRespM.getCreditLimOriLoanAmt(), 40000d) > 0) {
                                    log.debug("Credit limit >40000");
                                    overdraftAccount.add(tlRespM);
                                }
                            }
                        }
                    } else if (OrigConstant.AccountType.Revolving.equalsIgnoreCase(tlRespM.getAccountType())) {
                        if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)) {
                            log.debug("Non Installment Loan Revolving");
                            log.debug("Credit limit " + tlRespM.getCreditLimOriLoanAmt());
                            XRulesNCBAdjustDataM acccountInstallmentAdjust = getNCBAdjust(vNCBAdjust, tlRespM);
                            if (acccountInstallmentAdjust != null) {
                                log.debug("Installment adjust");
                                sumInstallmnetAdjust = sumInstallmnetAdjust.add(acccountInstallmentAdjust.getNcbInstallmentAdjustAmount());
                                hNCBDetailAdjust.put(tlRespM.getSegmentValue() + "_" + String.valueOf(tlRespM.getGroupSeq()), acccountInstallmentAdjust
                                        .getNcbInstallmentAdjustAmount());
                            } else {
                                if (Double.compare(tlRespM.getCreditLimOriLoanAmt(), 40000d) > 0) {
                                    log.debug("Credit limit >40000");
                                    revolvingAccount.add(tlRespM);
                                }
                            }
                        }
                    } else {//installment loan
                        if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag) || XRulesConstant.ACCOUNT_FLAG_C.equalsIgnoreCase(accountFlag)) {
                            log.debug("Installment Loan");
                            log.debug("Loan Amount " + tlRespM.getCreditLimOriLoanAmt());
                            // if (tlRespM.getCreditLimOriLoanAmt() > 40000d) {
                            //     log.debug("Loan Amount>40000");
                            //check Os Balance and
                            XRulesNCBAdjustDataM acccountInstallmentAdjust = getNCBAdjust(vNCBAdjust, tlRespM);
                            if (acccountInstallmentAdjust != null) {
                                log.debug("Installment adjust");
                                sumInstallmnetAdjust = sumInstallmnetAdjust.add(acccountInstallmentAdjust.getNcbInstallmentAdjustAmount());
                                hNCBDetailAdjust.put(tlRespM.getSegmentValue() + "_" + String.valueOf(tlRespM.getGroupSeq()), acccountInstallmentAdjust
                                        .getNcbInstallmentAdjustAmount());
                            } else {//installment loan
                                if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag) || XRulesConstant.ACCOUNT_FLAG_C.equalsIgnoreCase(accountFlag)) {
                                    log.debug("Installment Loan");
                                    log.debug("Loan Amount " + tlRespM.getCreditLimOriLoanAmt());
                                    // if (tlRespM.getCreditLimOriLoanAmt() >
                                    // 40000d) {
                                    //     log.debug("Loan Amount>40000");
                                    //check Os Balance and
                                    if (Double.compare(tlRespM.getInstallAmt(), 0d) == 0 && Double.compare(tlRespM.getAmtOwed(), 0) > 0) { //[ERROR
                                        // Case:
                                        // Data
                                        // from
                                        // NCB
                                        // is
                                        // abnormally
                                        // identify
                                        // by
                                        // "Outstanding
                                        // > 0
                                        // but
                                        // installment
                                        // = 0
                                        // or
                                        // blank"
                                        BigDecimal installment = getLast3MonthPaymentAverage(vHsRespMs, tlRespM.getSegmentValue(), tlRespM.getGroupSeq(),
                                                errorRate, tlRespM.getAmtOwed());
                                        sumOfInstallmentLoan = sumOfInstallmentLoan.add(installment);
                                        log.debug("Case Error  Installment Argv 3 monht" + installment);
                                        hNCBDetailAdjust.put(tlRespM.getSegmentValue() + "_" + String.valueOf(tlRespM.getGroupSeq()), installment);
                                    } else { //normal Case
                                        sumOfInstallmentLoan = sumOfInstallmentLoan.add(new BigDecimal(tlRespM.getInstallAmt()));
                                        hNCBDetailAdjust.put(tlRespM.getSegmentValue() + "_" + String.valueOf(tlRespM.getGroupSeq()), new BigDecimal(tlRespM
                                                .getInstallAmt()));
                                    }
                                    // }
                                }
                            }
                            // }
                        }
                    }

                }

            }
            // sum non insallment
            //OD

            BigDecimal overDrafResult = new BigDecimal(0);
            if (overdraftAccount.size() > 0) {
                BigDecimal sumOverdraftCreditUse = new BigDecimal(0);
                for (int i = 0; i < overdraftAccount.size(); i++) {
                    TLRespM account = (TLRespM) overdraftAccount.get(i);
                    if (account != null) {
                        BigDecimal installment = getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(), account.getGroupSeq(),
                                interestRateCreditCard, account.getAmtOwed());
                        sumOverdraftCreditUse = sumOverdraftCreditUse.add(installment);

                        hNCBDetailAdjust.put(account.getSegmentValue() + "_" + String.valueOf(account.getGroupSeq()), installment);
                    }
                }
                overDrafResult = sumOverdraftCreditUse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            log.debug(" OverDraft >40000 and <=100000 Result =" + overDrafResult);

            BigDecimal overDrafInterestResult = new BigDecimal(0);
            if (overdraftInterestAccount.size() > 0) {
                BigDecimal sumOverdraftInterestCreditUse = new BigDecimal(0);
                for (int i = 0; i < overdraftInterestAccount.size(); i++) {
                    TLRespM account = (TLRespM) overdraftInterestAccount.get(i);
                    if (account != null) {
                        BigDecimal installment = getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(), account.getGroupSeq(), odRate, account
                                .getAmtOwed());
                        sumOverdraftInterestCreditUse = sumOverdraftInterestCreditUse.add(installment);
                        hNCBDetailAdjust.put(account.getSegmentValue() + "_" + String.valueOf(account.getGroupSeq()), installment);
                    }
                }
                overDrafInterestResult = sumOverdraftInterestCreditUse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            log.debug(" OverDraft >10000 Result =" + overDrafInterestResult);

            //Revolving
            BigDecimal revolvingResult = new BigDecimal(0);
            if (revolvingAccount.size() > 0) {

                BigDecimal sumRevolingCredituse = new BigDecimal(0);
                for (int i = 0; i < revolvingAccount.size(); i++) {
                    TLRespM account = (TLRespM) revolvingAccount.get(i);
                    if (account != null) {
                        BigDecimal installment = getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(), account.getGroupSeq(),
                                interestRateCreditCard, account.getAmtOwed());
                        sumRevolingCredituse = sumRevolingCredituse.add(installment);
                        hNCBDetailAdjust.put(account.getSegmentValue() + "_" + String.valueOf(account.getGroupSeq()), installment);
                    }
                }
                revolvingResult = sumRevolingCredituse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            log.debug(" Revolving Result =" + revolvingResult);
            //AMEX credit card
            BigDecimal creditAmexResult = new BigDecimal(0);
            if (creditCardAmexAccount.size() > 0) {

                BigDecimal sumCreditAmexCreditUse = new BigDecimal(0);
                for (int i = 0; i < creditCardAmexAccount.size(); i++) {
                    TLRespM account = (TLRespM) creditCardAmexAccount.get(i);
                    if (account != null) {
                        BigDecimal installment = getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(), account.getGroupSeq(),
                                interestRateCreditCard, account.getAmtOwed());
                        sumCreditAmexCreditUse = sumCreditAmexCreditUse.add(installment);
                        hNCBDetailAdjust.put(account.getSegmentValue() + "_" + String.valueOf(account.getGroupSeq()), installment);
                    }
                }
                creditAmexResult = sumCreditAmexCreditUse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            log.debug(" Amex Creditcard Result =" + creditAmexResult);
            //Credit Card result
            BigDecimal creditCardResult = new BigDecimal(0);
            if (creditCardAccount.size() > 0) {
                BigDecimal sumCreditCardCreditUse = new BigDecimal(0);
                for (int i = 0; i < creditCardAccount.size(); i++) {
                    TLRespM account = (TLRespM) creditCardAccount.get(i);
                    if (account != null) {
                        BigDecimal installment = getLast3MonthPaymentAverage(vHsRespMs, account.getSegmentValue(), account.getGroupSeq(),
                                interestRateCreditCard, account.getAmtOwed());
                        sumCreditCardCreditUse = sumCreditCardCreditUse.add(installment);
                        hNCBDetailAdjust.put(account.getSegmentValue() + "_" + String.valueOf(account.getGroupSeq()), installment);
                    }
                }
                creditCardResult = sumCreditCardCreditUse.setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            log.debug(" Creditcard Result =" + creditCardResult);
            log.debug("=======================================");
            sumOfNonInstallmentLoan = creditCardResult.add(creditAmexResult).add(overDrafResult).add(revolvingResult).add(overDrafInterestResult);
            log.debug("sumOfNonInstallmentLoan " + sumOfNonInstallmentLoan);
            log.debug("sumOfInstallmentLoan " + sumOfInstallmentLoan);
            log.debug("hNCBDetailADjust size " + hNCBDetailAdjust.size());
            log.debug("VNCBAdjsut size " + vNCBAdjust.size());
        }
        result = sumOfInstallmentLoan.add(sumOfNonInstallmentLoan).add(sumInstallmnetAdjust);
        log.debug("total NCB " + result);
        log.debug("=======================================");
        return result;
    }
}
