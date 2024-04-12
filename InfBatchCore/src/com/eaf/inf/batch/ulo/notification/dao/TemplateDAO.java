package com.eaf.inf.batch.ulo.notification.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.inf.batch.ulo.notification.model.DMNotificationDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailBranchSummaryDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailEODRejectNCBDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.FollowDocHistoryDataM;

public interface TemplateDAO {
	/*####SMS#####*/
	public HashMap<String, Object> getSMSnonCashDay1ApproveValues(ArrayList<RecipientInfoDataM> receipientInfos ) throws InfBatchException;
	public HashMap<String, Object> getSMSInCreaseApproveValues(ArrayList<RecipientInfoDataM> receipientInfos) throws InfBatchException;
	public HashMap<String, Object> getSMSRejectValues(ArrayList<RecipientInfoDataM> receipientInfos ) throws InfBatchException;

	public HashMap<String, Object> getSMSTemplateCCandKECNonIncrease(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException; 
	public HashMap<String, Object> getSMSTemplateCCIncrease(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException; 
	public HashMap<String, Object> getSMSTemplateKECIncrease(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException; 
	public HashMap<String, Object> getSMSTemplateRejectDocumentList(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException;
	public HashMap<String, Object> getSMSTemplateKECCashTranfer(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException;
	
	//SELECT DATA_TEMPLATE_FOR SELLER
	public HashMap<String, Object> getSMSTemplateCCChannel(ArrayList<RecipientInfoDataM> receipientInfos) throws InfBatchException; 
	//SELECT DATA_TEMPLATE_FOR SELLER
	public HashMap<String, Object> getSMSTemplateKECandKPLReject(ArrayList<RecipientInfoDataM> receipientInfos) throws InfBatchException;
	
	/*####EMAIL#####*/
	public HashMap<String, Object> getEmailBranchApprovedValues(String applicationGroupId,  int maxLifeCycle) throws InfBatchException;
	public HashMap<String, Object>  getEmailAccountApprovedValues(String applicationGroupId,  int maxLifeCycle) throws InfBatchException;
	public HashMap<String, Object> getBranchRejectedValues(String applicationGroupId) throws InfBatchException;
	public HashMap<String, Object> getCustomerRejectedValues(String applicationGroupId) throws InfBatchException;
	public HashMap<String, Object> getSanctionListRejectedvalues(String applicationGroupId, int maxLifeCycle) throws InfBatchException;
	public HashMap<String, Object> findRejectedVariable(String applicationGroupId, int maxLifeCycle) throws InfBatchException;
	public ArrayList<EmailBranchSummaryDataM> getBranchSummaryData(ArrayList<String> applicationGroupIds) throws InfBatchException;
	public ArrayList<EmailEODRejectNCBDataM> getEodEmailRejectNcb(String applicationGroupId) throws InfBatchException;
	
	/*####EMAIL WAREHOUSE#####*/
	public HashMap<String, Object> getDMNotReceivePhysicalDocumentValues (ArrayList<DMNotificationDataM> dmNotifications) throws InfBatchException;
	public HashMap<String, Object> getDMIncompletePhysicalDocumentValues (ArrayList<DMNotificationDataM>  dmNotifications) throws InfBatchException;
	public HashMap<String, Object> getDMReturnningPhysicalDocumentValues (DMNotificationDataM  dmNotification) throws InfBatchException;
	
	public ArrayList<FollowDocHistoryDataM> loadFollowDocHistorys(ApplicationGroupDataM applicationGroup) throws InfBatchException;
	public HashMap<String, Object> getSMSTemplateKPLApprove(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException;
	
}
