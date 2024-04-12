/*
 * Created on Oct 3, 2007
 * Created by weeraya
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
package com.eaf.orig.shared.dao;

import com.eaf.orig.logon.dao.OrigLogOnDAO;
import com.eaf.orig.logon.dao.OrigLogOnDAOImpl;
import com.eaf.orig.logs.dao.LogDAO;
import com.eaf.orig.logs.dao.LogDAOImpl;
import com.eaf.orig.menu.DAO.E_MenuDAOImpl;
import com.eaf.orig.menu.DAO.MenuMDAO;
import com.eaf.orig.profile.DAO.UserProfileDAO;
import com.eaf.orig.profile.DAO.UserProfileDAOImpl;
import com.eaf.orig.scheduler.dao.Scheduler.SchedulerDAO;
import com.eaf.orig.scheduler.dao.Scheduler.SchedulerDAOImpl;
import com.eaf.orig.shared.dao.utility.BusinessClassByUserDAO;
import com.eaf.orig.shared.dao.utility.BusinessClassByUserDAOImpl;
import com.eaf.orig.shared.dao.utility.ReportParamDAO;
import com.eaf.orig.shared.dao.utility.ReportParamDAOImpl;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.xrules.dao.ExtBlacklistDAO;
import com.eaf.orig.shared.xrules.dao.ExtBlacklistDAOImpl;
import com.eaf.orig.shared.xrules.dao.ExtExistingCustomerDAO;
import com.eaf.orig.shared.xrules.dao.ExtExistingCustomerDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesBlacklistDAO;
import com.eaf.orig.shared.xrules.dao.XRulesBlacklistDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesBlacklistVehicleDAO;
import com.eaf.orig.shared.xrules.dao.XRulesBlacklistVehicleDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesDebtBdDAO;
import com.eaf.orig.shared.xrules.dao.XRulesDebtBdDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesDedupDAO;
import com.eaf.orig.shared.xrules.dao.XRulesDedupDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesDedupVehicleDAO;
import com.eaf.orig.shared.xrules.dao.XRulesDedupVehicleDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesExistcustDAO;
import com.eaf.orig.shared.xrules.dao.XRulesExistcustDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressDAO;
import com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressSurnameDAO;
import com.eaf.orig.shared.xrules.dao.XRulesExistcustInprogressSurnameDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesExistcustSurnameDAO;
import com.eaf.orig.shared.xrules.dao.XRulesExistcustSurnameDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesFICODAO;
import com.eaf.orig.shared.xrules.dao.XRulesFICODAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesLPMDAO;
import com.eaf.orig.shared.xrules.dao.XRulesLPMDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesNCBAdjustDAO;
import com.eaf.orig.shared.xrules.dao.XRulesNCBAdjustDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesNCBDAO;
import com.eaf.orig.shared.xrules.dao.XRulesNCBDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesPhoneVerificationDAO;
import com.eaf.orig.shared.xrules.dao.XRulesPhoneVerificationDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesPolicyRulesDAO;
import com.eaf.orig.shared.xrules.dao.XRulesPolicyRulesDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesScoringLogDAO;
import com.eaf.orig.shared.xrules.dao.XRulesScoringLogDAOImpl;
import com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAO;
import com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.ORIGWorkflowDAO;
import com.eaf.orig.ulo.pl.app.dao.ORIGWorkflowDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.OrigEnquiryLogDAO;
import com.eaf.orig.ulo.pl.app.dao.OrigEnquiryLogDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAccountCardDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAccountCardDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAccountDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAccountDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationImageDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationImageDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationLogDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationLogDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationSplitedImageDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationSplitedImageDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAttachmentHistoryMDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAttachmentHistoryMDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAuditTrailDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAuditTrailDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigCapportDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigCapportDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigCardInformationDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigCardInformationDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigCashTransferDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigCashTransferDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigDocumentCheckListDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigDocumentCheckListDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigDocumentCheckListReasonDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigDocumentCheckListReasonDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigFinancialInfoDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigFinancialInfoDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigListBoxMasterUtilityDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigListBoxmasterUtilityDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigNCBDocumentDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigNCBDocumentDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigPaymentMethodDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigPaymentMethodDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigPersonalAddressDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigPersonalAddressDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigPersonalInfoDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigPersonalInfoDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigReasonDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigReasonDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigReasonLogDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigReasonLogDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigReferencePersonDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigReferencePersonDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigRuleDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigRuleDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigSalesInfoDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigSalesInfoDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigTrackingDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigTrackingDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigZipcodeDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigZipcodeDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigUserDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigUserDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLProjectCodeDAO;
import com.eaf.orig.ulo.pl.app.dao.PLProjectCodeDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLWFLoadCentralJobAmountDAO;
import com.eaf.orig.ulo.pl.app.dao.PLWFLoadCentralJobAmountDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.UpdateApplicationStatusDAO;
import com.eaf.orig.ulo.pl.app.dao.UpdateApplicationStatusDAOImpl;
import com.eaf.orig.ulo.pl.key.dao.PLUniqueIDGeneratorDAO;
import com.eaf.orig.ulo.pl.key.dao.PLUniqueIDGeneratorDAOImpl;


/**
 * @author weeraya
 * 
 * Type: ORIGDAOFactory
 */
public class ORIGDAOFactory {
	public static ValueListDAO getValueListDAO() {
		return new ValueListDAOImpl();
	}
	public static ValueListDAO getValueListDAO(int dbType) {
		return new ValueListDAOImpl(dbType);
	}
	public static OrigApplicationApprvGroupMDAO getOrigApplicationApprvGroupMDAO() {
		return new OrigApplicationApprvGroupMDAOImpl();
	}
	public static OrigApplicationCustomerMDAO getOrigApplicationCustomerMDAO() {
		return new OrigApplicationCustomerMDAOImpl();
	}
	public static OrigApplicationLogMDAO getOrigApplicationLogMDAO() {
		return new OrigApplicationLogMDAOImpl();
	} 	
	public static OrigApplicationMDAO getOrigApplicationMDAO() {
		return new OrigApplicationMDAOImpl();
	}	
	public static OrigAttachmentHistoryMDAO getOrigAttachmentHistoryMDAO() {
		return new OrigAttachmentHistoryMDAOImpl();
	}
	public static OrigCustomerAddressMDAO getOrigCustomerAddressMDAO() {
		return new OrigCustomerAddressMDAOImpl();
	}
	public static OrigCustomerChangeNameMDAO getOrigCustomerChangeNameMDAO() {
		return new OrigCustomerChangeNameMDAOImpl();		
	}
	public static OrigCustomerFinanceMDAO getOrigCustomerFinanceMDAO() {
		return new OrigCustomerFinanceMDAOImpl();		
	}
	public static OrigDocumentCheckListMDAO getOrigDocumentCheckListMDAO() {
		return new OrigDocumentCheckListMDAOImpl();		
	}
	public static OrigInsuranceMDAO getOrigInsuranceMDAO() {
		return new OrigInsuranceMDAOImpl();		
	}
	public static OrigLoanMDAO getOrigLoanMDAO() {
		return new OrigLoanMDAOImpl();		
	}
	public static OrigNotePadDataMDAO getOrigNotePadDataMDAO() {
		return new OrigNotePadDataMDAOImpl();		
	}
	public static OrigReasonLogMDAO getOrigReasonLogMDAO() {
		return new OrigReasonLogMDAOImpl();		
	}
	public static OrigReasonMDAO getOrigReasonMDAO() {
		return new OrigReasonMDAOImpl();		
	}
	public static OrigVehicleMDAO getOrigVehicleMDAO() {
		return new OrigVehicleMDAOImpl();	
	}
//	public static UniqueIDGeneratorDAO getUniqueIDGeneratorDAO(){
//		return new UniqueIDGeneratorDAOImpl();
//	} 
	public static XRulesBlacklistDAO getXRulesBlacklistDAO(){
		return new XRulesBlacklistDAOImpl();
	}
	public static XRulesBlacklistVehicleDAO getXRulesBlacklistVehicleDAO(){
		return new XRulesBlacklistVehicleDAOImpl();
	}
	public static XRulesDebtBdDAO getXRulesDebtBdDAO(){
		return new XRulesDebtBdDAOImpl();
	}
	public static XRulesDedupDAO getXRulesDedupDAO(){
		return new XRulesDedupDAOImpl();
	}
	public static XRulesDedupVehicleDAO getXRulesDedupVehicleDAO(){
		return new XRulesDedupVehicleDAOImpl();
	}
	public static XRulesExistcustDAO getXRulesExistcustDAO(){
		return new XRulesExistcustDAOImpl();
	}
	public static XRulesLPMDAO getXRulesLPMDAO(){
		return new XRulesLPMDAOImpl();
	}
	public static XRulesNCBDAO getXRulesNCBDAO(){
		return new  XRulesNCBDAOImpl();
	}
	public static XRulesPhoneVerificationDAO getXRulesPhoneVerificationDAO(){
		return new  XRulesPhoneVerificationDAOImpl();
	}
	public static XRulesPolicyRulesDAO getXRulesPolicyRulesDAO(){
		return new  XRulesPolicyRulesDAOImpl();
	}
	public static XRulesVerificationResultDAO getXRulesVerificationResultDAO(){
		return new  XRulesVerificationResultDAOImpl();
	}
	public static XRulesFICODAO getXRulesFICODAO(){
		return new  XRulesFICODAOImpl();
	}
	public static UserProfileDAO getUserProfileDAO(){
		return new UserProfileDAOImpl();
	} 
	public static BusinessClassByUserDAO getBusinessClassByUserDAO() {
		return new BusinessClassByUserDAOImpl();
	}
	public static SearchApplicationUtilDAO getSearchApplicationUtilDAO() {
		return new SearchApplicationUtilDAOImpl();
	}
	public static MenuMDAO getMenuMDAO() {
		return new E_MenuDAOImpl();
	}
	public static OrigOtherNameMDAO getOrigOtherNameMDAO() {
		return new OrigOtherNameMDAOImpl();
	} 
	public static OrigLogOnDAO getOrigLogOnDAO() {
		return new OrigLogOnDAOImpl();
	}
	public static OrigCorperatedMDAO getOrigCorperatedMDAO() {
		return new OrigCorperatedMDAOImpl();
	}
	public static OrigProposalMDAO getOrigProposalDAO() {
		return new OrigProposalMDAOImpl();
	}
	public static OrigImageDAO getOrigImageDAO() {
		return new OrigImageDAOImpl();
	}
	public static UtilityDAO getUtilityDAO() {
		return new UtilityDAOImpl();
	}
	public static XRulesScoringLogDAO getXRulesScoringLogDAO(){
	    return new XRulesScoringLogDAOImpl();
	}
	public static SchedulerDAO getSchedulerDAO(){
	    return new SchedulerDAOImpl();
	}
	public static XRulesExistcustInprogressDAO getXRulesExistcustInprogressDAO(){
		return new XRulesExistcustInprogressDAOImpl();
	}
	public static ExtBlacklistDAO getExtBlacklistDAO() {
		return new ExtBlacklistDAOImpl();
	}
	public static ExtExistingCustomerDAO getExtExistingCustomerDAO() {
		return new ExtExistingCustomerDAOImpl();
	}
	public static OrigPreScoreMDAO getPreScoreDataMDAO(){
		return new OrigPreScoreMDAOImpl();
	}
	public static OrigSmsLogDAO getOrigSmsLogDAO(){
		return new OrigSmsLogDAOImpl();
	}
	public static OrigInstallmentMDAO getOrigInstallmentMDAO(){
		return new OrigInstallmentMDAOImpl();
	}
	public static XRulesNCBAdjustDAO getXRulesNCBAdjustDAO(){
	    return new XRulesNCBAdjustDAOImpl();
	}
	public static OrigPolicyVersionMDAO getOrigPolicyVersionMDAO(){
	    return new OrigPolicyVersionMDAOImpl();
	}
	public static OrigPolicyVersionLevelMDAO getOrigPolicyVersionLevelMDAO(){
	    return new OrigPolicyVersionLevelMDAOImpl();
	}
	public static OrigPolicyLevelGroupMDAO getOrigPolicyLevelGroupMDAO(){
	    return new OrigPolicyLevelGroupMDAOImpl();
	}
	public static OrigPolicyLevelMapMDAO getOrigPolicyLevelMapMDAO(){
	    return new OrigPolicyLevelMapMDAOImpl();
	}
	public static OrigPolicyRulesExceptionDAO getOrigPolicyRulesExceptionDAO(){
	    return new OrigPolicyRulesExceptionDAOImpl();
	}
	public static  OrigPolicyLevelGroupTotalMDAO getOrigPolicyLevelGroupTotalMDAO(){
	    return new  OrigPolicyLevelGroupTotalMDAOImpl();
	}
	public static XRulesExistcustInprogressSurnameDAO getXRulesExistcustInprogressSurnameDAO(){
		return new XRulesExistcustInprogressSurnameDAOImpl();
	}
	public static XRulesExistcustSurnameDAO getXRulesExistcustSurnameDAO(){
		return new XRulesExistcustSurnameDAOImpl();
	}
	public static OrigApplicationCardInformationMDAO getOrigApplicationCardInformationMDAO() {
		return new OrigApplicationCardInformationMDAOImpl();
	}
	public static LogDAO getLogDao(){
	    return new LogDAOImpl();
	}
	public static OrigMGCollateralMDAO getOrigMGCollateralMDAO(){
	    return new OrigMGCollateralMDAOImpl();
	}
	public static OrigMGCollateralAddressDAO getOrigMGCollateralAddressDAO(){
	    return new OrigMGCollatateralAddressDAOImpl();
	}
	public static OrigMGCollateralFeeInfoDAO getOrigMGCollateralFeeInfoDAO(){
	    return new OrigMGCollateralFeeInfoDAOImpl();
	}
	public static OrigMGAppraisalDAO getOrigMGAppraisalDAO(){
	    return new OrigMGAppraisalDAOImpl();   
	}
	public static com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAO getUniqueIDGeneratorDAO2(){
		return new com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAOImpl();
	} 
	/**
	 * Update Application form Workflow
	 * */
	public static UpdateApplicationStatusDAO getUpdateApplicationStatusDAO(){
		return new UpdateApplicationStatusDAOImpl();		
	}
	
	public static OrigApplicationImageMDAO getApplicationImageMDAO(){
		return new OrigApplicationImageMDAOImpl();
	}
	
	//**************** ULO **************** use PLORIGDAOFactory instead
	@Deprecated
	public static PLOrigApplicationDAO getPLOrigApplicationDAO(){
		return new PLOrigApplicationDAOImpl();
	}
	@Deprecated
	public static PLOrigPaymentMethodDAO getPLOrigPaymentMethodDAO(){
		return new PLOrigPaymentMethodDAOImpl();
	}
	@Deprecated
	public static PLOrigReferencePersonDAO getPLOrigReferencePersonDAO(){
		return new PLOrigReferencePersonDAOImpl();
	}
	@Deprecated
	public static PLOrigCashTransferDAO getPLOrigCashTransferDAO(){
		return new PLOrigCashTransferDAOImpl();
	}
	@Deprecated
	public static PLOrigSalesInfoDAO getPLOrigSalesInfoDAO(){
		return new PLOrigSalesInfoDAOImpl();
	}
	@Deprecated
	public static PLOrigDocumentCheckListDAO getPLOrigDocumentCheckListDAO(){
		return new PLOrigDocumentCheckListDAOImpl();
	}
	@Deprecated
	public static PLOrigApplicationLogDAO getPLOrigApplicationLogDAO(){
		return new PLOrigApplicationLogDAOImpl();
	}
	@Deprecated
	public static PLOrigDocumentCheckListReasonDAO getPLOrigDocumentCheckListReasonDAO(){
		return new PLOrigDocumentCheckListReasonDAOImpl();
	}
	@Deprecated
	public static PLOrigReasonLogDAO getPLOrigReasonLogDAO(){
		return new PLOrigReasonLogDAOImpl();
	}
	@Deprecated
	public static PLOrigAuditTrailDAO getPLOrigAuditTrailDAO(){
		return new PLOrigAuditTrailDAOImpl();
	}
	@Deprecated
	public static PLOrigApplicationImageDAO getPLOrigApplicationImageDAO(){
		return new PLOrigApplicationImageDAOImpl();
	}
	@Deprecated
	public static PLOrigPersonalInfoDAO getPLOrigPersonalInfoDAO(){
		return new PLOrigPersonalInfoDAOImpl();
	}
	@Deprecated
	public static PLOrigApplicationSplitedImageDAO getPLOrigApplicationSplitedImageDAO(){
		return new PLOrigApplicationSplitedImageDAOImpl();
	}
	@Deprecated
	public static PLOrigReasonDAO getPLOrigReasonDAO(){
		return new PLOrigReasonDAOImpl();
	}
	@Deprecated
	public static PLOrigAccountDAO getPLOrigAccountDAO(){
		return new PLOrigAccountDAOImpl();
	}
	@Deprecated
	public static PLOrigAccountCardDAO getPLOrigAccountCardDAO(){
		return new PLOrigAccountCardDAOImpl();
	}
	
	// load project code

	@Deprecated
	public static PLProjectCodeDAO getPLProjectCodeDAO(){
		return new PLProjectCodeDAOImpl();
	}
	@Deprecated
	public static PLOrigPersonalAddressDAO getPLOrigPersonalAddressDAO(){
		return new PLOrigPersonalAddressDAOImpl();
	}
	@Deprecated
	public static PLOrigNCBDocumentDAO getPLOrigNCBDocumentDAO(){
		return new PLOrigNCBDocumentDAOImpl();
	}
	@Deprecated
	public static PLOrigFinancialInfoDAO getPLOrigFinancialInfoDAO(){
		return new PLOrigFinancialInfoDAOImpl();
	}
	@Deprecated
	public static PLOrigCardInformationDAO getPLOrigCardInformationDAO(){
		return new PLOrigCardInformationDAOImpl();
	}
	@Deprecated
	public static PLOrigAttachmentHistoryMDAO getPLOrigAttachmentHistoryMDAO(){
		return new PLOrigAttachmentHistoryMDAOImpl();
	}
	@Deprecated
	public static PLUniqueIDGeneratorDAO getPLUniqueIDGeneratorDAO(){
		return new PLUniqueIDGeneratorDAOImpl();
	}	
	//load tracking
	@Deprecated
	public static PLOrigTrackingDAO getPLOrigTrackingDAO(){
		return new PLOrigTrackingDAOImpl();
	}
	@Deprecated
	public static PLOrigZipcodeDAO getPLOrigZipcodeDAO(){
		return new PLOrigZipcodeDAOImpl();
	}
	@Deprecated
	public static PLOrigUserDAO getPLOrigUserDAO(){
		return new PLOrigUserDAOImpl();
	}
	@Deprecated
	public static PLOrigCapportDAO getPLOrigCapportDAO(){
		return new PLOrigCapportDAOImpl();
	}	
	//Utility
	@Deprecated
	public static PLOrigListBoxMasterUtilityDAO getPLOrigListBoxMasterUtilityDAO(){
		return new PLOrigListBoxmasterUtilityDAOImpl();
	}
	@Deprecated
	public static PLWFLoadCentralJobAmountDAO getPLWFLoadCentralJobAmountDAO(){
		return new PLWFLoadCentralJobAmountDAOImpl();
	}
	@Deprecated
	public static PLOrigRuleDAO getPLOrigRuleDAO(){
		return new PLOrigRuleDAOImpl();
	}
	public static OrigEnquiryLogDAO getOrigEnquiryLogDAO(){
		return new OrigEnquiryLogDAOImpl();
	}
	public static ORIGWorkflowDAO getORIGWorkflowDAO(){
		return new ORIGWorkflowDAOImpl();
	}
	public static ReportParamDAO getReportParamDAO(){
		return new ReportParamDAOImpl();
	}
}
