package com.eaf.inf.batch.ulo.notification.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.model.InfExportDataM;
import com.eaf.inf.batch.ulo.card.notification.model.CardNotificationDataM;
import com.eaf.inf.batch.ulo.card.notification.model.CardParamDataM;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.config.model.ReasonApplication;
import com.eaf.inf.batch.ulo.notification.model.DMNotificationDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailRejectNCBPersonalInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailTemplateDataM;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationEODRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.OrigContactLogDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.SMSTemplateDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;
import com.eaf.orig.master.shared.model.RunningParamM;
import com.eaf.orig.ulo.email.model.EmailRequest;

public interface NotificationDAO {
	public HashMap<String,SMSTemplateDataM> getSMSTemplateInfo(ArrayList<String> templateIds) throws InfBatchException;
	public HashMap<String,EmailTemplateDataM> getEmailTemplateInfo(ArrayList<String> templateId) throws InfBatchException;
	public EmailTemplateDataM getDMEmailTemplateInfo(String templateId) throws InfBatchException;
	public NotificationEODRequestDataM getNotificationEodRequest() throws InfBatchException;
	public NotificationEODRequestDataM getNotificationEodBranchSummaryRequest() throws InfBatchException;
	public NotificationEODRequestDataM getNotificationEodRejectNCBRequest() throws InfBatchException;
	public HashMap<String,ArrayList<NotificationEODDataM>> findNotificationEod() throws InfBatchException;
	public boolean isContainsKPLProDuctApprove(String applicationGroupId)throws InfBatchException;
	public boolean isKECAndKCCApprove(String applicationGroupId)throws InfBatchException;
	public boolean isCashDay1Approve(String applicationGroupId)throws InfBatchException;
	public NotificationInfoDataM getNotificationInfo(NotificationInfoRequestDataM requestNotifiInfo) throws InfBatchException;
	public VCEmpInfoDataM selectVCEmpManagerInfo(String SaleId) throws InfBatchException;
	public ArrayList<VCEmpInfoDataM> selectVCEmpList(ArrayList<String> SaleIds)throws InfBatchException;
	public ArrayList<VCEmpInfoDataM> findEmployeeInfos(ArrayList<String> empIds)throws InfBatchException;
	public VCEmpInfoDataM  selectVCEmpInfo(String SaleId)throws InfBatchException;
	public HashMap<String,ArrayList<VCEmpInfoDataM>> selectVCEmpInfoJobCode(String saleEmpId,ArrayList<JobCodeDataM> jobList) throws InfBatchException;
	public ArrayList<DMNotificationDataM> selectNotificationDM(String dmNotificationType,String groupCode)throws InfBatchException;
	public ArrayList<DMNotificationDataM> selectReturnNotificationDM(String dmNotificationType,String groupCode)throws InfBatchException;
	public String getDMGeneralParam(String paramCode) throws InfBatchException;
	public String getGeneralParamValue(String paramCode) throws InfBatchException;
	public HashMap<String, String> getGeneralParamMap(ArrayList<String> codeList) throws InfBatchException;
	public Date getApplicationDate() throws InfBatchException;
	public HashMap<String,ArrayList<JobCodeDataM>>  selectDMJobCodes(String scanChannel,String groupCode) throws InfBatchException;
	public void insertDMCorrespondLog(DMNotificationDataM dmNotification,EmailRequest emailRequest,Connection conn) throws InfBatchException;
	public HashMap<String, ArrayList<JobCodeDataM>>selectJobCodeChannelMapping(ArrayList<String> empIdList) throws InfBatchException;
	public boolean isContainsSanctioListRejectCodeCondition(String applicationGroup,String[] rejectCodes)throws InfBatchException;
	public boolean isContainRejected(String applicationGroupId)throws InfBatchException;
	public boolean isContainApproved(String applicationGroupId)throws InfBatchException;
	public void insertTableContactLog(OrigContactLogDataM contactLog)throws InfBatchException;
	public ArrayList<CardNotificationDataM> selectCardNotification() throws InfBatchException;
	public ArrayList<RecipientInfoDataM> loadRecipient(NotificationInfoDataM notificationInfo) throws InfBatchException;
	public ArrayList<RecipientInfoDataM> loadRecipientSmsNextDay(NotificationInfoDataM notificationInfo) throws InfBatchException;
	public ArrayList<RecipientInfoDataM> loadRecipientCash1Hour(NotificationInfoDataM notificationInfo) throws InfBatchException;
	public ArrayList<EmailRejectNCBPersonalInfoDataM> findPersonalRejectNcb(String applicationGroupId) throws InfBatchException;
	
	public  boolean isExceptionReasonCodes(String applicationGroupId) throws InfBatchException;
	public HashMap<String,String> getSellerMobileNo(ArrayList<String> saleIds,String saleType,String applicationGroupId) throws InfBatchException;
	public HashMap<String,ArrayList<String>> loadSMSTemplateNotification(NotificationInfoDataM notificationInfoDataM,RecipientInfoDataM reipientInfos )throws InfBatchException;
	public HashMap<String,ArrayList<String>> getSMSTemplateNotification(NotificationInfoDataM notificationInfoDataM,RecipientInfoDataM reipientInfos,String reasonCode)throws InfBatchException;
	public HashMap<String,ArrayList<String>> getSellerSMSTemplateNotification(NotificationInfoDataM notificationInfoDataM,RecipientInfoDataM reipientInfos )throws InfBatchException;
	public HashMap<String,ArrayList<String>>  getEmailTemplateNotification(NotificationInfoDataM notificationInfoDataM)throws InfBatchException;
//	public ArrayList<SMSRecipientDataM> selectPersonalDetailSMS(String applicationGroupId) throws InfBatchException;
//	public boolean getSpacialCard(String applicationGroupId)throws InfBatchException;
	public void insertInfExportKmobile(String contentMessage,String MODULE_ID)throws InfBatchException;
	public void insertInfExportKmobile(InfExportDataM infExport)throws InfBatchException;
	public String getKmobileRoleNameFromAppLog(String applicationGroupId,int lifeCycle)throws InfBatchException;
	
	public ArrayList<CardParamDataM> getCardParams()throws InfBatchException;
	public HashMap<String,RunningParamM>getRunningParam()throws InfBatchException;
	public String  getTemplateCode(String templateId) throws InfBatchException;
	public String  getCardLinkCustFlag(String applicationGroupId) throws InfBatchException;
	
	//SMS NEXT DAY
	public   HashMap<String,NotificationInfoDataM>  getNotificationInfoNextDay() throws InfBatchException;
	public ReasonApplication findReasonApplication(String applicationGroupId) throws InfBatchException;
	public List<ReasonApplication> findReasonApplications(String applicationGroupId) throws InfBatchException;
	public HashMap<String, ArrayList<String>> loadSMSTemplateNotificationNoDecision(
			NotificationInfoDataM notificationInfo,
			RecipientInfoDataM reipientInfo) throws InfBatchException;
	public HashMap<String, ArrayList<String>> getSMSTemplateNotificationNoDecision(
			NotificationInfoDataM notificationInfo,
			RecipientInfoDataM reipientInfo, String reasonCode)
			throws InfBatchException;
	public ArrayList<RecipientInfoDataM> loadRecipientDV(
			NotificationInfoDataM notificationInfo) throws InfBatchException;
}