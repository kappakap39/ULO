/*
 * Created on Nov 14, 2007
 * Created by Administrator
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
package com.eaf.orig.shared.dao.utility;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.cache.properties.IntSchemeCacheProperties;
import com.eaf.orig.shared.dao.EjbUtilException;
import com.eaf.orig.shared.model.CampaignDataM;
import com.eaf.orig.shared.model.SearchAddressDataM;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDedupDataM;
import com.orig.bpm.ulo.model.WorkflowDataM;

/**
 * @author Administrator
 *
 * Type: UtilityDAO
 */
public interface UtilityDAO {
	public String getDealerDescription(String code) throws EjbUtilException;
	public String getZipCodeDescription(String code) throws EjbUtilException;
	public String getSellerDescription(String code) throws EjbUtilException;
	public String getCarModelDescription(String code) throws EjbUtilException;
	public String getBranchDescription(String code) throws EjbUtilException;
	public String getBranchDescription(String code, String bankCode) throws EjbUtilException;
	public Vector getSLAForUW(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException;
	public Vector getSLAForXCMR(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException;
	public Vector getSLAForCMR(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException;
	public Vector getSLAForUWCMR(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException;
	public String getMainApplicatinRecordIdByApplicaionNo(String applicationNo) throws EjbUtilException;
	public String getModelDescription(String code, String carBrand) throws EjbUtilException;
	public Vector getCMRUser(String userName) throws EjbUtilException;
	public String getAppRecordForCreateDrawDown(String personalID) throws EjbUtilException;
	public String getPersonIdAndAppRecordIDForCreateDrawDown(String personalID) throws EjbUtilException;
	public String getTitleDescription(String code) throws EjbUtilException;
	public String getAreaDescription(String code, String userId) throws EjbUtilException;
	public Vector<CacheDataM> getAreaCodesLike(String code, String userId) throws EjbUtilException;
	public Vector<CacheDataM> getClientGroupsLike(String code) throws EjbUtilException;
	public Vector<CacheDataM> getMarketingCodesLike(String code) throws EjbUtilException;
	public Vector<CacheDataM> getCarCategorysLike(String code) throws EjbUtilException;
	public Vector<CacheDataM> getCarModelsLike(String code, String brand) throws EjbUtilException;
	public Vector<CacheDataM> getCarBrandsLike(String code) throws EjbUtilException;
	public Vector<CacheDataM> getCarColorsLike(String code) throws EjbUtilException;
	public Vector<CacheDataM> getCarLicenseTypesLike(String code) throws EjbUtilException;
	public Vector<CacheDataM> getCarProvincesLike(String code) throws EjbUtilException;
	public IntSchemeCacheProperties getIntSchemeForCode(String schemeCode) throws EjbUtilException;
	public Vector getImageAttachedFiles(  String requestId)throws EjbUtilException;
	public CampaignDataM getCampaign(String campaignCode) throws EjbUtilException;
	public Vector getEscarateGroup(String policyVersion,String customerType,String carType,String loanType,String scoringResult,BigDecimal financeAmount, String appType,
            BigDecimal installmentTerm, BigDecimal downPayment) throws EjbUtilException;
	public Vector getSLAForXUW(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException;
	public Vector getAllUserNameXUW() throws EjbUtilException ;
	public void beforeCallReportCAApp(Date fromDate,Date toDate)throws EjbUtilException ;
	public void beforeCallReportCADecision(String fromOfficer,String toOfficer,String fromCACode,String toCACode,Date fromDate,Date toDate)throws EjbUtilException ;
	public void beforeCallReportCAOveride(String fromOfficer,String toOfficer,String fromCACode,String toCACode,Date fromDate,Date toDate)throws EjbUtilException ;
	public Vector getPolicyGroup(String policyVersion)throws EjbUtilException ;
	public BigDecimal getLendingLimit(String username, String busID) throws EjbUtilException;
	public Vector getRekeyFields() throws EjbUtilException;
	public Vector getAppNumberByGroupID(String groupID,String appRecordID) throws EjbUtilException;
	public String getProvinceDescription(String code) throws EjbUtilException;
	public String getAmphurDescription(String code) throws EjbUtilException;
	public String getTambolDescription(String code) throws EjbUtilException;
	public HashMap<String, String> getTambol()throws EjbUtilException;
	public HashMap<String, String> getAmphur()throws EjbUtilException;
	public HashMap<String, String> getProvince()throws EjbUtilException;
	/*Sale Information Subform*/
	public String getRefBranchName(String code) throws EjbUtilException;
	public String getSellerName(String code) throws EjbUtilException;
	String getFileCategoryDesc(String code) throws EjbUtilException;
	String getWFDLAGroup(String jobType, String escalate, String allocationGroup)throws EjbUtilException;	
	public String GetChoiceNOFieldIDByDesc(String fieldID, String desc)throws EjbUtilException;
	public SearchAddressDataM SearchZipCode(String zipcode, String province,String amphur, String tambol);
	public SearchAddressDataM SearchTambol(String province, String amphur,String tambol);
	public SearchAddressDataM SearchAmphur(String province, String amphur);
	public SearchAddressDataM SearchProvince(String province);
	public SearchAddressDataM MandatoryAddress(String zipcode, String province,String amphur, String tambol);
	public ArrayList<String> LoadORIGApplication(String jobState ,int size);
	public int GetTotalJobCentralQueue(String tdID, String queueType) throws EjbUtilException;
	public String LoadUserName(String userName,String role) throws EjbUtilException;
	public String LoadUserID(String userName,String role) throws EjbUtilException;
	public String getApplicationNo(String appRecID);
	public Vector<PLXRulesDedupDataM> LoadDataDupApplication(ArrayList<String> list);
	public String getSellerName(String branch_code,String sale_code,String channel_group,String channel);
	public String getSellerNameByBranch(String branch_code,String sale_code,String branchGroup);
	public HashMap<String,String> getWorkflowInfo(String appRecID);
	public String getReasonDesc(String appRecID);
	public String getJobState(String appRecID)throws Exception;
	public ArrayList<String> getParamByUserID(String paramCode,String userID);
	public Vector<String> loadReasonCS(String appRecID, String jobState,String finalDecision);
	public Vector<String> loadTrackingDoclistName(String appRecID);
	public void getWfLastData(String appRecID,WorkflowDataM workflowM) throws EjbUtilException;
	public String getAppRecordID(String applicationNo);
	public String getNextWfSendBack(String appRecID,String role) throws EjbUtilException;
	public String getICDCFLAG(String appRecID);
	public HashMap<String, String> getVcInboxData(String appRecID);
	public Vector<PLApplicationImageSplitDataM>  getApplicationImageM(Vector<PLNCBDocDataM> ncbDocVect)throws EjbUtilException;
	
	public void deleteOldSubmitIATimestamp(String appGroupNo, String idNo, int seconds) throws ApplicationException;
	public void deleteErrorSubmitIATimestamp(String idNo) throws ApplicationException;
	public void insertSubmitIATimestamp(String appGroupNo, String idNo) throws Exception;
	public boolean isAppWaitForApprove(String idNo, String appGroupNo, ArrayList<String> jobStates, ArrayList<String> products, String cjdSource) throws Exception;
}