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
package com.eaf.orig.ulo.app.dao;

import com.eaf.orig.ulo.app.inc.dao.OrigBankStatementDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigBankStatementDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigBankStatementDetailDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigBankStatementDetailDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigClosedEndFundDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigClosedEndFundDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigFinInstrumentDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigFinInstrumentDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigFixedAccountDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigFixedAccountDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigFixedAccountDetailDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigFixedAccountDetailDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigFixedGuaranteeDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigFixedGuaranteeDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigIncomeCategoryDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigIncomeCategoryDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigIncomeDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigIncomeDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigKVIIncomeDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigKVIIncomeDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigMonthlyTawiDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigMonthlyTawiDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigMonthlyTawiDetailDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigMonthlyTawiDetailDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigOpenEndFundDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigOpenEndFundDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigOpenEndFundDetailDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigOpenEndFundDetailDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigOtherIncomeDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigOtherIncomeDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigPayrollDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigPayrollDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigPayrollFileDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigPayrollFileDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigPayslipDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigPayslipDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigPayslipMonthlyDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigPayslipMonthlyDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigPayslipMonthlyDetailDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigPayslipMonthlyDetailDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigPreviousIncomeDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigPreviousIncomeDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigSalaryCertDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigSalaryCertDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigSavingAccountDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigSavingAccountDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigSavingAccountDetailDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigSavingAccountDetailDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigTaweesapDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigTaweesapDAOImpl;
import com.eaf.orig.ulo.app.inc.dao.OrigYearlyTawiDAO;
import com.eaf.orig.ulo.app.inc.dao.OrigYearlyTawiDAOImpl;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBAccountDAO;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBAccountDAOImpl;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBAccountReportDAO;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBAccountReportDAOImpl;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBAccountReportRuleDAO;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBAccountReportRuleDAOImpl;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBAddressDAO;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBAddressDAOImpl;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBIdDAO;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBIdDAOImpl;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBInfoDAO;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBInfoDAOImpl;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBNameDAO;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBNameDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigCardMaintenanceDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigCardMaintenanceDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigDocVerificationDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigDocVerificationDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigGuidelineDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigGuidelineDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigHRVerificationDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigHRVerificationDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigIdentifyQuestionDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigIdentifyQuestionDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigIdentifyQuestionSetDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigIdentifyQuestionSetDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigORPolicyRulesDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigORPolicyRulesDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigORPolicyRulesDetailDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigORPolicyRulesDetailDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPhoneVerificationDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPhoneVerificationDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPolicyRulesConditionDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPolicyRulesConditionDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPolicyRulesDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPolicyRulesDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProductCCADAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProductCCADAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProductInsuranceDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProductInsuranceDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProductSavingDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProductSavingDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeDocDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeDocDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeExceptionDocDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeExceptionDocDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeKassetDocDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeKassetDocDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeMGMDocDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeMGMDocDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeTradingDocDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeTransferDocDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeProjectCodeTransferDocDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeRecommProjCodeDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigPrivilegeRecommProjCodeDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigRequiredDocDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigRequiredDocDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigRequiredDocDetailDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigRequiredDocDetailDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigVerificationResultDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigVerificationResultDAOImpl;
import com.eaf.orig.ulo.app.xrules.dao.OrigWebVerificationDAO;
import com.eaf.orig.ulo.app.xrules.dao.OrigWebVerificationDAOImpl;
import com.eaf.orig.ulo.search.dao.SearchApplicantDAO;
import com.eaf.orig.ulo.search.dao.SearchApplicantDAOImpl;
import com.eaf.orig.ulo.app.dao.LoanSetupDAOImpl;

/**
 * @author Anu
 * 
 * Type: ORIGDAOFactory
 */
public class ORIGDAOFactory {
	
	public static OrigApplicationGroupDAO getApplicationGroupDAO(){
		return new OrigApplicationGroupDAOImpl();
	}
	
	public static OrigApplicationDAO getApplicationDAO(){
		return new OrigApplicationDAOImpl();
	}
	
	public static OrigApplicationInceaseDAO getApplicationInceaseDAO(){
		return new OrigApplicationInceaseDAOImpl();
	}
	
	public static OrigApplicationImageDAO getApplicationImageDAO(){
		return new OrigApplicationImageDAOImpl();
	}
	
	public static OrigApplicationImageSplitDAO getApplicationImageSplitDAO(){
		return new OrigApplicationImageSplitDAOImpl();
	}
	
	public static OrigAdditionalServiceDAO getAdditionalServiceDAO(){
		return new OrigAdditionalServiceDAOImpl();
	}
	
	public static OrigAdditionalServiceMapDAO getAdditionalServiceMapDAO(){
		return new OrigAdditionalServiceMapDAOImpl();
	}
	
	public static OrigApplicationLogDAO getApplicationLogDAO(){
		return new OrigApplicationLogDAOImpl();
	}
	
	public static OrigReprocessLogDAO getReprocessLogDAO(){
		return new OrigReprocessLogDAOImpl();
	}
	
	public static OrigApplicationPointDAO getApplicationPointDAO(){
		return new OrigApplicationPointDAOImpl();
	}
	
	public static OrigAttachmentHistoryDAO getAttachmentHistoryDAO(){
		return new OrigAttachmentHistoryDAOImpl();
	}
	
	public static OrigAuditTrailDAO getAuditTrailDAO(){
		return new OrigAuditTrailDAOImpl();
	}
	
	public static OrigBundleHLDAO getBundleHLDAO(){
		return new OrigBundleHLDAOImpl();
	}
	
	public static OrigBundleKLDAO getBundleKLDAO(){
		return new OrigBundleKLDAOImpl();
	}
	
	public static OrigBundleSMEDAO getBundleSMEDAO(){
		return new OrigBundleSMEDAOImpl();
	}
	
	public static CallCenterDAO getCallCenterDAO(){
		return new CallCenterDAOImpl();
	}
	
	public static OrigCardDAO getCardDAO(){
		return new OrigCardDAOImpl();
	}
	
	public static OrigCardLinkCardDAO getCardLinkCardDAO(){
		return new OrigCardLinkCardDAOImp();
	}
	
	public static OrigCardLinkCustomerDAO getCardLinkCustomerDAO(){
		return new OrigCardLinkCustomerDAOImpl();
	}
	
	public static OrigCashTransferDAO getCashTransferDAO(){
		return new OrigCashTransferDAOImpl();
	}
	
	public static OrigContactLogDAO getContactLogDAO(){
		return new OrigContactLogDAOImpl();
	}
	
	public static OrigDebtInfoDAO getDebtInfoDAO(){
		return new OrigDebtInfoDAOImpl();
	}
	
	public static OrigDocumentCheckListDAO getDocumentCheckListDAO(){
		return new OrigDocumentCheckListDAOImpl();
	}
	
	public static OrigDocumentCommentDAO getDocumentCommentDAO(){
		return new OrigDocumentCommentDAOImpl();
	}
	
	public static OrigDocumentCheckListReasonDAO getDocumentCheckListReasonDAO(){
		return new OrigDocumentCheckListReasonDAOImpl();
	}
	
	public static OrigDocumentRelationDAO getDocumentRelationDAO(){
		return new OrigDocumentRelationDAOImpl();
	}
	
	public static OrigEnquiryLogDAO getEnquiryLogDAO(){
		return new OrigEnquiryLogDAOImpl();
	}
	
	public static OrigHistoryDataDAO getHistoryDataDAO(){
		return new OrigHistoryDataDAOImpl();
	}
	
	public static OrigIncomeSourceDAO getIncomeSourceDAO(){
		return new OrigIncomeSourceDAOImpl();
	}
	
	public static OrigKYCDAO getKYCDAO(){
		return new OrigKYCDAOImpl();
	}
	
	public static OrigLoanDAO getLoanDAO(){
		return new OrigLoanDAOImpl();
	}
	
	public static OrigLoanTierDAO getLoanTierDAO(){
		return new OrigLoanTierDAOImpl();
	}
	
	public static OrigLoanFeeDAO getLoanFeeDAO(){
		return new OrigLoanFeeDAOImpl();
	}
	
	public static OrigLoanPricingDAO getLoanPricingDAO(){
		return new OrigLoanPricingDAOImpl();
	}
	
	public static OrigNCBDocumentDAO getNCBDocumentDAO(){
		return new OrigNCBDocumentDAOImpl();
	}
	
	public static OrigNCBDocumentHistoryDAO getNCBDocumentHistoryDAO(){
		return new OrigNCBDocumentHistoryDAOImpl();
	}
	
	public static OrigNotepadDAO getNotepadDAO(){
		return new OrigNotepadDAOImpl();
	}
	
	public static OrigPaymentMethodDAO getPaymentMethodDAO(){
		return new OrigPaymentMethodDAOImpl();
	}
	
	public static OrigPersonalAddressDAO getPersonalAddressDAO(){
		return new OrigPersonalAddressDAOImpl();
	}
	
	public static OrigPersonalInfoDAO getPersonalInfoDAO(){
		return new OrigPersonalInfoDAOImpl();
	}
	
	public static OrigPersonalRelationDAO getPersonalRelationDAO(){
		return new OrigPersonalRelationDAOImpl();
	}
	
	public static OrigProjectCodeDAO getProjectCodeDAO(){
		return new OrigProjectCodeDAOImpl();
	}
	
	public static OrigReasonDAO getReasonDAO(){
		return new OrigReasonDAOImpl();
	}
	
	public static OrigReasonLogDAO getReasonLogDAO(){
		return new OrigReasonLogDAOImpl();
	}
	
	public static OrigReferencePersonDAO getReferencePersonDAO(){
		return new OrigReferencePersonDAOImpl();
	}
	
	public static OrigSaleInfoDAO getSaleInfoDAO(){
		return new OrigSaleInfoDAOImpl();
	}
	
	public static OrigSMSLogDAO getSMSLogDAO(){
		return new OrigSMSLogDAOImpl();
	}
	
	public static OrigWisdomDAO getWisdomDAO(){
		return new OrigWisdomDAOImpl();
	}
	
	// INC DAOs
	public static OrigIncomeCategoryDAO getIncomeCategoryDAO(){
		return new OrigIncomeCategoryDAOImpl();
	}
	
	public static OrigBankStatementDAO getBankStatementDAO(){
		return new OrigBankStatementDAOImpl();
	}
	
	public static OrigBankStatementDetailDAO getBankStatementDetailDAO(){
		return new OrigBankStatementDetailDAOImpl();
	}
	
	public static OrigClosedEndFundDAO getClosedEndFundDAO(){
		return new OrigClosedEndFundDAOImpl();
	}
	
	public static OrigFinInstrumentDAO getFinInstrumentDAO(){
		return new OrigFinInstrumentDAOImpl();
	}
	
	public static OrigFixedAccountDAO getFixedAccountDAO(){
		return new OrigFixedAccountDAOImpl();
	}
	
	public static OrigFixedAccountDetailDAO getFixedAccountDetailDAO(){
		return new OrigFixedAccountDetailDAOImpl();
	}
	
	public static OrigFixedGuaranteeDAO getFixedGuaranteeDAO(){
		return new OrigFixedGuaranteeDAOImpl();
	}
	
	public static OrigIncomeDAO getIncomeDAO(){
		return new OrigIncomeDAOImpl();
	}
	
	public static OrigKVIIncomeDAO getKVIIncomeDAO(){
		return new OrigKVIIncomeDAOImpl();
	}
	
	public static OrigMonthlyTawiDAO getMonthlyTawiDAO(){
		return new OrigMonthlyTawiDAOImpl();
	}
	
	public static OrigMonthlyTawiDetailDAO getMonthlyTawiDetailDAO(){
		return new OrigMonthlyTawiDetailDAOImpl();
	}
	
	public static OrigOpenEndFundDAO getOpenEndFundDAO(){
		return new OrigOpenEndFundDAOImpl();
	}
	
	public static OrigOpenEndFundDetailDAO getOpenEndFundDetailDAO(){
		return new OrigOpenEndFundDetailDAOImpl();
	}
	
	public static OrigOtherIncomeDAO getOtherIncomeDAO(){
		return new OrigOtherIncomeDAOImpl();
	}
	
	public static OrigPayrollDAO getPayrollDAO(){
		return new OrigPayrollDAOImpl();
	}
	
	public static OrigPayrollFileDAO getPayrollFileDAO(){
		return new OrigPayrollFileDAOImpl();
	}
	
	public static OrigPayslipDAO getPayslipDAO(){
		return new OrigPayslipDAOImpl();
	}
	
	public static OrigPayslipMonthlyDAO getPayslipMonthlyDAO(){
		return new OrigPayslipMonthlyDAOImpl();
	}
	
	public static OrigPayslipMonthlyDetailDAO getPayslipMonthlyDetailDAO(){
		return new OrigPayslipMonthlyDetailDAOImpl();
	}
	
	public static OrigPreviousIncomeDAO getPreviousIncomeDAO(){
		return new OrigPreviousIncomeDAOImpl();
	}
	
	public static OrigSalaryCertDAO getSalaryCertDAO(){
		return new OrigSalaryCertDAOImpl();
	}
	
	public static OrigSavingAccountDAO getSavingAccountDAO(){
		return new OrigSavingAccountDAOImpl();
	}
	
	public static OrigSavingAccountDetailDAO getSavingAccountDetailDAO(){
		return new OrigSavingAccountDetailDAOImpl();
	}
	
	public static OrigTaweesapDAO getTaweesapDAO(){
		return new OrigTaweesapDAOImpl();
	}
	
	public static OrigYearlyTawiDAO getYearlyTawiDAO(){
		return new OrigYearlyTawiDAOImpl();
	}
	
	//Xrules DAO
	public static OrigDocVerificationDAO getDocVerificationDAO() {
		return new OrigDocVerificationDAOImpl();
	}
	public static OrigHRVerificationDAO getHRVerificationDAO() {
		return new OrigHRVerificationDAOImpl();
	}
	public static OrigGuidelineDAO getGuidelineDAO() {
		return new OrigGuidelineDAOImpl();
	}
	public static OrigIdentifyQuestionDAO getIdentifyQuestionDAO() {
		return new OrigIdentifyQuestionDAOImpl();
	}
	public static OrigIdentifyQuestionSetDAO getIdentifyQuestionSetDAO() {
		return new OrigIdentifyQuestionSetDAOImpl();
	}
	public static OrigORPolicyRulesDAO getORPolicyRulesDAO() {
		return new OrigORPolicyRulesDAOImpl();
	}
	public static OrigORPolicyRulesDetailDAO getORPolicyRulesDetailDAO() {
		return new OrigORPolicyRulesDetailDAOImpl();
	}
	public static OrigPhoneVerificationDAO getPhoneVerificationDAO() {
		return new OrigPhoneVerificationDAOImpl();
	}
	public static OrigPolicyRulesDAO getPolicyRulesDAO() {
		return new OrigPolicyRulesDAOImpl();
	}
	public static OrigPrivilegeProjectCodeDAO getPrivilegeProjectCodeDAO() {
		return new OrigPrivilegeProjectCodeDAOImpl();
	}
	public static OrigPrivilegeProjectCodeDocDAO getPrivilegeProjectCodeDocDAO() {
		return new OrigPrivilegeProjectCodeDocDAOImpl();
	}
	public static OrigPrivilegeProjectCodeKassetDocDAO getPrivilegeProjectCodeKassetDocDAO() {
		return new OrigPrivilegeProjectCodeKassetDocDAOImpl();
	}
	public static OrigPrivilegeProjectCodeTradingDocDAOImpl getPrivilegeProjectCodeTradingDocDAO() {
		return new OrigPrivilegeProjectCodeTradingDocDAOImpl();
	}
	public static OrigPrivilegeProjectCodeMGMDocDAO getPrivilegeProjectCodeMGMDocDAO() {
		return new OrigPrivilegeProjectCodeMGMDocDAOImpl();
	}
	public static OrigPrivilegeProjectCodeExceptionDocDAO getPrivilegeProjectCodeExceptionDocDAO() {
		return new OrigPrivilegeProjectCodeExceptionDocDAOImpl();
	}
	public static OrigPrivilegeProjectCodeTransferDocDAO getPrivilegeProjectCodeTransferDocDAO() {
		return new OrigPrivilegeProjectCodeTransferDocDAOImpl();
	}
	public static OrigPrivilegeProductInsuranceDAO getPrivilegeProductInsuranceDAO() {
		return new OrigPrivilegeProductInsuranceDAOImpl();
	}
	public static OrigPrivilegeProductCCADAO getPrivilegeProductCCADAO() {
		return new OrigPrivilegeProductCCADAOImpl();
	}
	public static OrigPrivilegeRecommProjCodeDAO getPrivilegeRecommProjCodeDAO() {
		return new OrigPrivilegeRecommProjCodeDAOImpl();
	}
	public static OrigPrivilegeProductSavingDAO getPrivilegeProductSavingDAO() {
		return new OrigPrivilegeProductSavingDAOImpl();
	}
	public static OrigRequiredDocDAO getRequiredDocDAO() {
		return new OrigRequiredDocDAOImpl();
	}
	public static OrigRequiredDocDetailDAO getRequiredDocDetailDAO() {
		return new OrigRequiredDocDetailDAOImpl();
	}
	public static OrigVerificationResultDAO getVerificationResultDAO() {
		return new OrigVerificationResultDAOImpl();
	}
	public static OrigWebVerificationDAO getWebVerificationDAO() {
		return new OrigWebVerificationDAOImpl();
	}
	//NCB DAO
	public static OrigNCBInfoDAO getNCBInfoDAO() {
		return new OrigNCBInfoDAOImpl();
	}
	public static OrigNCBAccountDAO getNCBAccountDAO() {
		return new OrigNCBAccountDAOImpl();
	}
	public static OrigNCBAccountReportDAO getNCBAccountReportDAO() {
		return new OrigNCBAccountReportDAOImpl();
	}
	public static OrigNCBAccountReportRuleDAO getNCBAccountReportRuleDAO() {
		return new OrigNCBAccountReportRuleDAOImpl();
	}
	public static OrigNCBAddressDAO getNCBAddressDAO() {
		return new OrigNCBAddressDAOImpl();
	}
	public static OrigNCBIdDAO getNCBIdDAO() {
		return new OrigNCBIdDAOImpl();
	}
	public static OrigNCBNameDAO getNCBNameDAO() {
		return new OrigNCBNameDAOImpl();
	}
	
	public static OrigApplicationGroupDAO getApplicationGroupDAO(String userId){
		return new OrigApplicationGroupDAOImpl(userId);
	}	
	public static OrigApplicationDAO getApplicationDAO(String userId){
		return new OrigApplicationDAOImpl(userId);
	}	
	public static OrigApplicationInceaseDAO getApplicationIncreaseDAO(String userId){
		return new OrigApplicationInceaseDAOImpl(userId);
	}
	public static OrigApplicationImageDAO getApplicationImageDAO(String userId){
		return new OrigApplicationImageDAOImpl(userId);
	}	
	public static OrigApplicationImageDAO getApplicationImageDAO(String userId,String transactionId){
		return new OrigApplicationImageDAOImpl(userId);
	}
	public static OrigApplicationImageSplitDAO getApplicationImageSplitDAO(String userId){
		return new OrigApplicationImageSplitDAOImpl(userId);
	}
	public static OrigAdditionalServiceDAO getAdditionalServiceDAO(String userId){
		return new OrigAdditionalServiceDAOImpl(userId);
	}
	
	public static OrigAdditionalServiceMapDAO getAdditionalServiceMapDAO(String userId){
		return new OrigAdditionalServiceMapDAOImpl(userId);
	}
	
	public static OrigApplicationLogDAO getApplicationLogDAO(String userId){
		return new OrigApplicationLogDAOImpl(userId);
	}
	
	public static OrigReprocessLogDAO getOrigReprocessLogDAO(String userId){
		return new OrigReprocessLogDAOImpl(userId);
	}
	
	public static OrigApplicationPointDAO getApplicationPointDAO(String userId){
		return new OrigApplicationPointDAOImpl(userId);
	}
	
	public static OrigAttachmentHistoryDAO getAttachmentHistoryDAO(String userId){
		return new OrigAttachmentHistoryDAOImpl(userId);
	}
	
	public static OrigAuditTrailDAO getAuditTrailDAO(String userId){
		return new OrigAuditTrailDAOImpl(userId);
	}
	
	public static OrigBundleHLDAO getBundleHLDAO(String userId){
		return new OrigBundleHLDAOImpl(userId);
	}
	
	public static OrigBundleKLDAO getBundleKLDAO(String userId){
		return new OrigBundleKLDAOImpl(userId);
	}
	
	public static OrigBundleSMEDAO getBundleSMEDAO(String userId){
		return new OrigBundleSMEDAOImpl(userId);
	}
	
	public static CallCenterDAO getCallCenterDAO(String userId){
		return new CallCenterDAOImpl(userId);
	}
	
	public static OrigCardDAO getCardDAO(String userId){
		return new OrigCardDAOImpl(userId);
	}
	
	public static OrigCardLinkCardDAO getCardLinkCardDAO(String userId){
		return new OrigCardLinkCardDAOImp(userId);
	}
	
	public static OrigCardLinkCustomerDAO getCardLinkCustomerDAO(String userId){
		return new OrigCardLinkCustomerDAOImpl(userId);
	}
	
	public static OrigCashTransferDAO getCashTransferDAO(String userId){
		return new OrigCashTransferDAOImpl(userId);
	}
	
	public static OrigContactLogDAO getContactLogDAO(String userId){
		return new OrigContactLogDAOImpl(userId);
	}
	
	public static OrigDebtInfoDAO getDebtInfoDAO(String userId){
		return new OrigDebtInfoDAOImpl(userId);
	}
		
	public static OrigDocumentCheckListDAO getDocumentCheckListDAO(String userId){
		return new OrigDocumentCheckListDAOImpl(userId);
	}
	
	public static OrigDocumentCommentDAO getDocumentCommentDAO(String userId){
		return new OrigDocumentCommentDAOImpl(userId);
	}
	
	public static OrigDocumentCheckListReasonDAO getDocumentCheckListReasonDAO(String userId){
		return new OrigDocumentCheckListReasonDAOImpl(userId);
	}
	
	public static OrigDocumentRelationDAO getDocumentRelationDAO(String userId){
		return new OrigDocumentRelationDAOImpl(userId);
	}
	
	public static OrigEnquiryLogDAO getEnquiryLogDAO(String userId){
		return new OrigEnquiryLogDAOImpl();
	}
	
	public static OrigHistoryDataDAO getHistoryDataDAO(String userId){
		return new OrigHistoryDataDAOImpl(userId);
	}
	
	public static OrigIncomeSourceDAO getIncomeSourceDAO(String userId){
		return new OrigIncomeSourceDAOImpl(userId);
	}
	
	public static OrigKYCDAO getKYCDAO(String userId){
		return new OrigKYCDAOImpl(userId);
	}
	
	public static OrigLoanDAO getLoanDAO(String userId){
		return new OrigLoanDAOImpl(userId);
	}
	
	public static OrigLoanTierDAO getLoanTierDAO(String userId){
		return new OrigLoanTierDAOImpl(userId);
	}
	
	public static OrigLoanFeeDAO getLoanFeeDAO(String userId){
		return new OrigLoanFeeDAOImpl(userId);
	}
	
	public static OrigLoanPricingDAO getLoanPricingDAO(String userId){
		return new OrigLoanPricingDAOImpl(userId);
	}
	
	public static OrigNCBDocumentDAO getNCBDocumentDAO(String userId){
		return new OrigNCBDocumentDAOImpl(userId);
	}
	
	public static OrigNCBDocumentHistoryDAO getNCBDocumentHistoryDAO(String userId){
		return new OrigNCBDocumentHistoryDAOImpl(userId);
	}
	
	public static OrigNotepadDAO getNotepadDAO(String userId){
		return new OrigNotepadDAOImpl(userId);
	}
	
	public static OrigPaymentMethodDAO getPaymentMethodDAO(String userId){
		return new OrigPaymentMethodDAOImpl(userId);
	}
	
	public static OrigPersonalAddressDAO getPersonalAddressDAO(String userId){
		return new OrigPersonalAddressDAOImpl(userId);
	}
	
	public static OrigPersonalInfoDAO getPersonalInfoDAO(String userId){
		return new OrigPersonalInfoDAOImpl(userId);
	}
	public static OrigPersonalInfoDAO getPersonalInfoDAO(String userId,String transactionId){
		return new OrigPersonalInfoDAOImpl(userId);
	}
	public static OrigPersonalRelationDAO getPersonalRelationDAO(String userId){
		return new OrigPersonalRelationDAOImpl(userId);
	}
	
	public static OrigProjectCodeDAO getProjectCodeDAO(String userId){
		return new OrigProjectCodeDAOImpl(userId);
	}
	
	public static OrigReasonDAO getReasonDAO(String userId){
		return new OrigReasonDAOImpl(userId);
	}
	
	public static OrigReasonLogDAO getReasonLogDAO(String userId){
		return new OrigReasonLogDAOImpl(userId);
	}
	
	public static OrigReferencePersonDAO getReferencePersonDAO(String userId){
		return new OrigReferencePersonDAOImpl(userId);
	}
	
	public static OrigSaleInfoDAO getSaleInfoDAO(String userId){
		return new OrigSaleInfoDAOImpl(userId);
	}
	
	public static OrigSMSLogDAO getSMSLogDAO(String userId){
		return new OrigSMSLogDAOImpl(userId);
	}
	
	public static OrigWisdomDAO getWisdomDAO(String userId){
		return new OrigWisdomDAOImpl(userId);
	}
	
	// INC DAOs
	public static OrigIncomeCategoryDAO getIncomeCategoryDAO(String userId){
		return new OrigIncomeCategoryDAOImpl(userId);
	}
	
	public static OrigBankStatementDAO getBankStatementDAO(String userId){
		return new OrigBankStatementDAOImpl(userId);
	}
	
	public static OrigBankStatementDetailDAO getBankStatementDetailDAO(String userId){
		return new OrigBankStatementDetailDAOImpl(userId);
	}
	
	public static OrigClosedEndFundDAO getClosedEndFundDAO(String userId){
		return new OrigClosedEndFundDAOImpl(userId);
	}
	
	public static OrigFinInstrumentDAO getFinInstrumentDAO(String userId){
		return new OrigFinInstrumentDAOImpl(userId);
	}
	
	public static OrigFixedAccountDAO getFixedAccountDAO(String userId){
		return new OrigFixedAccountDAOImpl(userId);
	}
	
	public static OrigFixedAccountDetailDAO getFixedAccountDetailDAO(String userId){
		return new OrigFixedAccountDetailDAOImpl(userId);
	}
	
	public static OrigFixedGuaranteeDAO getFixedGuaranteeDAO(String userId){
		return new OrigFixedGuaranteeDAOImpl(userId);
	}
	
	public static OrigIncomeDAO getIncomeDAO(String userId){
		return new OrigIncomeDAOImpl(userId);
	}
	
	public static OrigKVIIncomeDAO getKVIIncomeDAO(String userId){
		return new OrigKVIIncomeDAOImpl(userId);
	}
	
	public static OrigMonthlyTawiDAO getMonthlyTawiDAO(String userId){
		return new OrigMonthlyTawiDAOImpl(userId);
	}
	
	public static OrigMonthlyTawiDetailDAO getMonthlyTawiDetailDAO(String userId){
		return new OrigMonthlyTawiDetailDAOImpl(userId);
	}
	
	public static OrigOpenEndFundDAO getOpenEndFundDAO(String userId){
		return new OrigOpenEndFundDAOImpl(userId);
	}
	
	public static OrigOpenEndFundDetailDAO getOpenEndFundDetailDAO(String userId){
		return new OrigOpenEndFundDetailDAOImpl(userId);
	}
	
	public static OrigOtherIncomeDAO getOtherIncomeDAO(String userId){
		return new OrigOtherIncomeDAOImpl(userId);
	}
	
	public static OrigPayrollDAO getPayrollDAO(String userId){
		return new OrigPayrollDAOImpl(userId);
	}
	
	public static OrigPayrollFileDAO getPayrollFileDAO(String userId){
		return new OrigPayrollFileDAOImpl(userId);
	}
	
	public static OrigPayslipDAO getPayslipDAO(String userId){
		return new OrigPayslipDAOImpl(userId);
	}
	
	public static OrigPayslipMonthlyDAO getPayslipMonthlyDAO(String userId){
		return new OrigPayslipMonthlyDAOImpl(userId);
	}
	
	public static OrigPayslipMonthlyDetailDAO getPayslipMonthlyDetailDAO(String userId){
		return new OrigPayslipMonthlyDetailDAOImpl(userId);
	}
	
	public static OrigPreviousIncomeDAO getPreviousIncomeDAO(String userId){
		return new OrigPreviousIncomeDAOImpl(userId);
	}
	
	public static OrigSalaryCertDAO getSalaryCertDAO(String userId){
		return new OrigSalaryCertDAOImpl(userId);
	}
	
	public static OrigSavingAccountDAO getSavingAccountDAO(String userId){
		return new OrigSavingAccountDAOImpl(userId);
	}
	
	public static OrigSavingAccountDetailDAO getSavingAccountDetailDAO(String userId){
		return new OrigSavingAccountDetailDAOImpl(userId);
	}
	
	public static OrigTaweesapDAO getTaweesapDAO(String userId){
		return new OrigTaweesapDAOImpl(userId);
	}
	
	public static OrigYearlyTawiDAO getYearlyTawiDAO(String userId){
		return new OrigYearlyTawiDAOImpl(userId);
	}
	
	//Xrules DAO
	public static OrigDocVerificationDAO getDocVerificationDAO(String userId) {
		return new OrigDocVerificationDAOImpl(userId);
	}
	public static OrigHRVerificationDAO getHRVerificationDAO(String userId) {
		return new OrigHRVerificationDAOImpl(userId);
	}
	public static OrigGuidelineDAO getGuidelineDAO(String userId) {
		return new OrigGuidelineDAOImpl(userId);
	}
	public static OrigIdentifyQuestionDAO getIdentifyQuestionDAO(String userId) {
		return new OrigIdentifyQuestionDAOImpl(userId);
	}
	public static OrigIdentifyQuestionSetDAO getIdentifyQuestionSetDAO(String userId) {
		return new OrigIdentifyQuestionSetDAOImpl(userId);
	}
	public static OrigORPolicyRulesDAO getORPolicyRulesDAO(String userId) {
		return new OrigORPolicyRulesDAOImpl(userId);
	}
	public static OrigORPolicyRulesDetailDAO getORPolicyRulesDetailDAO(String userId) {
		return new OrigORPolicyRulesDetailDAOImpl(userId);
	}
	public static OrigPhoneVerificationDAO getPhoneVerificationDAO(String userId) {
		return new OrigPhoneVerificationDAOImpl(userId);
	}
	public static OrigPolicyRulesDAO getPolicyRulesDAO(String userId) {
		return new OrigPolicyRulesDAOImpl(userId);
	}
	public static OrigPrivilegeProjectCodeDAO getPrivilegeProjectCodeDAO(String userId) {
		return new OrigPrivilegeProjectCodeDAOImpl(userId);
	}
	public static OrigPrivilegeProjectCodeDocDAO getPrivilegeProjectCodeDocDAO(String userId) {
		return new OrigPrivilegeProjectCodeDocDAOImpl(userId);
	}
	public static OrigPrivilegeProjectCodeKassetDocDAO getPrivilegeProjectCodeKassetDocDAO(String userId) {
		return new OrigPrivilegeProjectCodeKassetDocDAOImpl(userId);
	}
	public static OrigPrivilegeProjectCodeTradingDocDAOImpl getPrivilegeProjectCodeTradingDocDAO(String userId) {
		return new OrigPrivilegeProjectCodeTradingDocDAOImpl(userId);
	}
	public static OrigPrivilegeProjectCodeMGMDocDAO getPrivilegeProjectCodeMGMDocDAO(String userId) {
		return new OrigPrivilegeProjectCodeMGMDocDAOImpl(userId);
	}
	public static OrigPrivilegeProjectCodeExceptionDocDAO getPrivilegeProjectCodeExceptionDocDAO(String userId) {
		return new OrigPrivilegeProjectCodeExceptionDocDAOImpl(userId);
	}
	public static OrigPrivilegeProjectCodeTransferDocDAO getPrivilegeProjectCodeTransferDocDAO(String userId) {
		return new OrigPrivilegeProjectCodeTransferDocDAOImpl(userId);
	}
	public static OrigPrivilegeProductInsuranceDAO getPrivilegeProductInsuranceDAO(String userId) {
		return new OrigPrivilegeProductInsuranceDAOImpl(userId);
	}
	public static OrigPrivilegeProductCCADAO getPrivilegeProductCCADAO(String userId) {
		return new OrigPrivilegeProductCCADAOImpl(userId);
	}
	public static OrigPrivilegeRecommProjCodeDAO getPrivilegeRecommProjCodeDAO(String userId) {
		return new OrigPrivilegeRecommProjCodeDAOImpl(userId);
	}
	public static OrigPrivilegeProductSavingDAO getPrivilegeProductSavingDAO(String userId) {
		return new OrigPrivilegeProductSavingDAOImpl(userId);
	}
	public static OrigRequiredDocDAO getRequiredDocDAO(String userId) {
		return new OrigRequiredDocDAOImpl(userId);
	}
	public static OrigRequiredDocDetailDAO getRequiredDocDetailDAO(String userId) {
		return new OrigRequiredDocDetailDAOImpl(userId);
	}
	public static OrigVerificationResultDAO getVerificationResultDAO(String userId) {
		return new OrigVerificationResultDAOImpl(userId);
	}
	public static OrigWebVerificationDAO getWebVerificationDAO(String userId) {
		return new OrigWebVerificationDAOImpl(userId);
	}
	//NCB DAO
	public static OrigNCBInfoDAO getNCBInfoDAO(String userId) {
		return new OrigNCBInfoDAOImpl(userId);
	}
	public static OrigNCBAccountDAO getNCBAccountDAO(String userId) {
		return new OrigNCBAccountDAOImpl(userId);
	}
	public static OrigNCBAccountReportDAO getNCBAccountReportDAO(String userId) {
		return new OrigNCBAccountReportDAOImpl(userId);
	}
	public static OrigNCBAccountReportRuleDAO getNCBAccountReportRuleDAO(String userId) {
		return new OrigNCBAccountReportRuleDAOImpl(userId);
	}
	public static OrigNCBAddressDAO getNCBAddressDAO(String userId) {
		return new OrigNCBAddressDAOImpl(userId);
	}
	public static OrigNCBIdDAO getNCBIdDAO(String userId) {
		return new OrigNCBIdDAOImpl(userId);
	}
	public static OrigNCBNameDAO getNCBNameDAO(String userId) {
		return new OrigNCBNameDAOImpl(userId);
	}
	public static OrigFollowDocHistoryDAO getFollowDocHistoryDAO () {
		return new OrigFollowDocHistoryDAOImpl();
	}
	
	public static CVRSValidationResultDAO getCVRSValidationResultDAO(){
		return new CVRSValidationResultDAOImpl();
	}
	
	public static OrigPolicyRulesConditionDAO getPolicyRulesConditionDAO(){
		return new OrigPolicyRulesConditionDAOImpl();
	}
	public static OrigPolicyRulesConditionDAO getPolicyRulesConditionDAO(String userId){
		return new OrigPolicyRulesConditionDAOImpl(userId);
	}
	public static OrigCardMaintenanceDAO getCardMaintenanceDAO(){
		return new OrigCardMaintenanceDAOImpl();
	}
	public static SearchApplicantDAO getSearchApplicantDAO(){
		return new SearchApplicantDAOImpl();
	}
	public static OrigApplicationGroupDAO getApplicationGroupDAO(String userId,String transactionId){
		return new OrigApplicationGroupDAOImpl(userId,transactionId);
	}
	public static OrigBScoreDAO getBScoreDAO(String userId){
		return new OrigBScoreDAOImpl(userId);
	}
	public static OrigPersonalAccountDAO getAccountDAO(String userId){
		return new OrigPersonalAccountDAOImpl(userId);
	}
	public static OrigBScoreDAO getBScoreDAO(){
		return new OrigBScoreDAOImpl();
	}
	public static OrigPersonalAccountDAO getAccountDAO(){
		return new OrigPersonalAccountDAOImpl();
	}
	public static LoanSetupDAO getLoanSetupDAO() {
		return new LoanSetupDAOImpl();
	}
	public static LoanSetupDAO getLoanSetupDAO(String userId) {
		return new LoanSetupDAOImpl(userId);
	}
	public static LoanSetupStampDutyDAO getLoanSetupStampDutyDAO() {
		return new LoanSetupStampDutyDAOImpl();
	}
	public static LoanSetupStampDutyDAO getLoanSetupStampDutyDAO(String userId) {
		return new LoanSetupStampDutyDAOImpl(userId);
	}
	public static CapportTransactionDAO getCapportTransactionDAO(String userId) {
		return new CapportTransactionDAOImpl(userId);
	}
}