package com.eaf.orig.ulo.pl.util.ejb;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;
import javax.ejb.Local;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.cache.properties.IntSchemeCacheProperties;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.master.shared.model.FicoM;
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.master.shared.model.ReportParam;
import com.eaf.orig.master.shared.model.ScoreCharacterM;
import com.eaf.orig.master.shared.model.ScoreM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.AttachmentHistoryDataM;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.model.SearchAddressDataM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.search.SearchM;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.EnquiryLogDataM;
import com.eaf.orig.ulo.pl.model.app.ORIGWorkflowDataM;
import com.eaf.orig.ulo.pl.model.app.OverrideCapportDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountLogDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.model.app.PLAuditTrailDataM;
import com.eaf.orig.ulo.pl.model.app.PLImportSpecialPointDataM;
import com.eaf.orig.ulo.pl.model.app.PLInboxUnBlockDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLTrackingDataM;
import com.eaf.orig.ulo.pl.model.app.RulesDetailsDataM;
import com.eaf.orig.ulo.pl.model.app.TrackingDataM;
import com.eaf.orig.ulo.pl.model.app.UserPointDataM;
import com.eaf.orig.ulo.pl.model.app.search.PLSearchHistoryActionLogDataM;
import com.eaf.xrules.shared.model.XRulesScoringLogDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFUVerificationDataM;

@Local
public interface ORIGDAOUtilLocal {
	public GeneralParamM loadModelOrigMasterGenParamM(String genParamCde,String busID);
	public TreeMap getProcessMenuByID(Vector allmenuID);
	public int getLastScoring(String applicationRecordId);
	public Vector getImageAttachedFiles(String requestId);
	public ValueListM getResult(ValueListM valueListM);
	public ValueListM getResult2(ValueListM valueListM);
	public Vector getAppNumberByGroupID(String groupID,String appRecordID);
	public String loadWorkingTime(String queueTimeOutID);
	public Vector getRekeyFields();
	@Deprecated
	public Vector<ORIGCacheDataM> loadByChoice_No(String Field_ID);
	public int LoadCentralJob (String ATID, String ATID2);
	public Vector<PLTrackingDataM> queryTracking(String owner);
	public int countUser(String role, String firstName, String lastName, String logOn, String onJob);
	public int countLogOnStatus(String role, String firstName, String lastName, String logOn, String onJob);
	public int countOnjobStatus(String role, String firstName, String lastName, String logOn, String onJob);
	public int countPreviousJob(String role, String firstName, String lastName, String logOn, String onJob);
	public int countPreviousJobEdit(String role, String firstName, String lastName, String logOn, String onJob);
	public int countNewJob(String role, String firstName, String lastName, String logOn, String onJob);
	public int countSubmitJob(String role, String firstName, String lastName, String logOn, String onJob);
	public int countSubmitEditJob(String role, String firstName, String lastName, String logOn, String onJob);
	public int countRemainJob(String role, String firstName, String lastName, String logOn, String onJob);
	public int countAutoQueue(String role ,String roleWf);
	public int countAutoQueue(TrackingDataM trackM);
	public int countInboxAutoQueueSup(String menuId, String role ,String roleWf);
	public Vector<PLTrackingDataM> trackingAction (String owner, String role);
	public Vector<String[]> renderOnjobFlag(String user);
	public String Zipcode(String Tambol);
	public PLProjectCodeDataM Loadtable(String projectcode);
	public PLProjectCodeDataM Loadtable(String projectcode ,String productfeature,String busClassID,String exception_project);
	public int countJobComplete(String userName);
	public PLAccountCardDataM loadAccountCardByCardNo(String cardNo);
	public PLAccountDataM loadAccountNoCardData(String accountId);
	public boolean sendEmailWithEmailTemplate(String emailTemplate, PLApplicationDataM appM);
	public boolean sendEmailFollowDocToBranch(String emailTemplate, PLApplicationDataM applicationM, String ccTo);
	public boolean sendSMSWithSMSCode(String smsCode, PLApplicationDataM applicationM);
	public void SendSMSStartFollow(PLApplicationDataM applicationM);
	public void SendSMSFollowDoc(PLApplicationDataM applicationM);
	public void saveFollowDetail(PLApplicationDataM applicationM,PLXRulesFUVerificationDataM xrulesFuVerM);
	public String getAppRecordIdByCardNo(String cardNo);
//	public PLApplicationDataM loadApplication_IncreaseDecrease (String appRecId, PLApplicationDataM currentAppM);
	public Vector<PLInboxUnBlockDataM>loadSubTableUnBlock (String IDNO, String role, String userName);
	public Vector<String[]> loadCBConsent (String dateFrom, String dateTo);
	public Vector<PLImportSpecialPointDataM> saveTableOrig_Special_Point(Vector<PLImportSpecialPointDataM> importSpePointVect, Date dataDate);
	public Vector<String> loadDisplayReasonLog (String appLogId);
	public Vector<String> loadReasonEnquiry (String appRecId);
	public Vector<String> loadReasonCallCenterTracking(String appRecId, String jobState);
	public String loadStatusOnJob (String userName, String tdid);
	public PLApplicationDataM loadAppInFo (String appRecID) ;
	public PLApplicationDataM loadOrigApplication (String appId);
	public PLAccountDataM loadAccount (String accId);
	public void saveUpdateModelOrigAttachmentHistoryM(PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM);
	public PLPersonalInfoDataM LoadPersonalInfoModel(String idNo, String customerType);
	public PLAttachmentHistoryDataM loadModelOrigAttachmentHistoryMFromAttachId(String attachmentId);
	public void deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(Vector<PLAttachmentHistoryDataM> attachVect, String appRecordID);
	public void deleteTableORIG_ATTACHMENT_HISTORY_ByAttachId(String attachId);
	public Vector<PLSearchHistoryActionLogDataM> loadHistoryActionLog (String appRecId);
	public String getPreviousAppStatus(String appId);
	public String getDLA(BigDecimal creditLimit,String jobType) throws Exception;
	public UserPointDataM getUserPointDetails(String userName, ProcessMenuM menuM);
	public Vector<PLApplicationImageSplitDataM> loadImageSplitFromAppRecId (String appRecId);
	public CapportGroupDataM getCapportGroupDetails(String capportNo);
	public OverrideCapportDataM getOverrideCapport(String appRecordID);
	public BigDecimal getLendingLimit(String username, String busID);
	public ValueListM getResult_master(ValueListM valueListM, String dataValue);
	public ValueListM getResult_master2(ValueListM valueListM, String dataValue);
	public SearchM getResultSearchM(SearchM searchM);
	public Vector<PLAuditTrailDataM> loadOrigAuditTrailByRoleAppRecId (String appRecId);
	public String loandCarModelDesc(String carModel,String carBrand);
	public int getMaxScoringSequence(XRulesScoringLogDataM prmXRulesScoringLogDataM);
	public void createXRulesScoringLogM(XRulesScoringLogDataM prmXRulesScoringLogDataM);
	public BigDecimal getUserLendingLimit(String userName, String busClass);
	public Vector<RulesDetailsDataM> getRulesDetailsConfig(String busClass);
	public Date getDateExtendWorkingDay(Date startCalDate, int extendDay);
	public String getDefaultCycleCut(String businessClass);
	public ArrayList getCostFee(String origType);
	public void saveUpdateModelOrigAttachmentHistoryM(AttachmentHistoryDataM prmAttachmentHistoryDataM);
	public IntSchemeCacheProperties getIntSchemeForCode(String schemeCode);
	public Vector<CacheDataM> getAutoCompleteCacheDataM(String lookUp , String codeParam ,String username ,String brand);
	public Vector loadModelOrigPolicyRulesExceptionDataM(String policyVersion);
	public String getMainApplicatinRecordIdByApplicaionNo(String applicationNo);
	public void beforeCallReportCAApp(java.sql.Date fromDate,java.sql.Date toDate);
	public void beforeCallReportCADecision(String fromOfficer,String toOfficer,String fromCACode,String toCACode,java.sql.Date fromDate,java.sql.Date toDate);
	public void beforeCallReportCAOveride(String fromOfficer,String toOfficer,String fromCACode,String toCACode,java.sql.Date fromDate,java.sql.Date toDate);
	public Vector loadVehicleDetailByCustomerId(String idNo);
	public Vector loadMenus(Vector vtMenuID, Vector vtRows) ;
	public Vector getAllUserNameCMR();
	public Vector getCharTypeSpecificVect(String scoreCode, String charCode);
	public Vector SearchAllBranch();
	public Vector SearchAllGroupname();
	public Vector SearchAllExcept();
	public Vector SearchAllBusinessClass();
	public Vector getScoreFacterType(String cusType);
	public String getCusThaiDesc(String cusType);
	public HashMap getOriginalScoreTypeHash(String cusType);
	public ScoreM getOriginalScoreM(String cusType);
	public HashMap getOriginalCharTypeHash(Vector scoreCodeVect);
	public Vector selectCharM(String scoreCode,String charCode);
	public Vector SearchGroupnameByDesc(String groupName);
	public Vector SearchExceptByDesc(String exceptDesc);
	public Vector SearchBusinessClassByDesc(String busClassDesc);
	public Vector getScoreTypeVect(String cusType);
	public HashMap getCharTypeHashMapByScoreCode(Vector scoreTypeVect);
	public int getMaxScoreSeq(String custype);
	public FicoM getFicoLastInfo();
	public Vector getCharTypeVect(String scoreCode);
	public ScoreCharacterM getNewSpecDesc(ScoreCharacterM characterM);
	public OrigPolicyVersionDataM loadModelOrigPolicyVersionDataM(String policyVersion);
	public Vector loadPolicyRules();
	public UserDetailM getUserProfile(String userName);
	public Vector getSearchImage(String searchType,String requestId,String dealer,String channelCode,String userName,String faxInDateFrom,String faxInDateTo,String nric,String firstName,String lastName,String sortBy, String appNo, String mainCustomerType, String dealerFaxNo);
	public String getUniqByName(String systemType);
	public String getORIGCacheDisplayNameFormDB(String code, String type, String param1);
	public String getORIGCacheDisplayNameFormDB(String code, String type);
	public Vector SearchBranchByDesc(String branchDesc);
	public String getUserTimeRemain(String role, String appRecId);
	public String getSystemTimeRemain(String bussClass, String appRecId);
	public Vector<PLPersonalInfoDataM> loadOrigPersonalInfo(String appRecId);	
	public String generateApplicationNo(PLApplicationDataM appM);
	public Vector<String[]> loadAuditTrailField (String SubFormID);
	public String getAddressDesc(String type, String numberVal, String optionalCase);
	public Vector<String> loadTrackingDoclistName(String appRecId);
	public int GetTotalJobDoneCurrentDate(String userName ,String roleId, String tdid);
	public PLAccountCardDataM loadAccountCardByAppNo(String appNo);
	public String loadBlockFlag(String appRecID);
	public HashMap<String, String> getBusGroupByBusClass(String busClass);
	public HashMap<String, String> getBusClassByBusGroup(String busGroup);
	public String getJobTypeByPolicy(String policyRules,BigDecimal creditLimit);
	public String getJobID(String appRecId);
	public GeneralParamM getGeneralParamvalueByBussClass(String paramCode, String bussClass);
	public GeneralParamM getGeneralParamvalue(String paramCode);
	public String getBranchGroupByBranchNo(String BranchNo);
	public String getChannelGroupByChannel(String ChannelNo);
	public String getTargetPointCurrentFromUser(String User);
	public String getTargetPointFinishFromRole(String User, String Role);
	public String getTargetPointFinishFromRole(String User, String Role, String tdid);
	public Vector<ReportParam> getReportParamVect();
	public Vector<ReportParam> getReportParamVect(String paramType);
	public ReportParam getReportParam(String paramType, String paramCode);	
	public ArrayList<String> getRejectLetterTemplate();
	public void prepareRejectLetter(String reportDate);
	public int getMaxFileOfTemplate(String template);
	public void insertInterfaceLog(String moduleId, String logType, String logCode, String message, String createBy, String refId);
	public PLSaleInfoDataM loadPLOrigSaleInfoDataM (String appRecId);	
	public String loadCitizenNoByCardNo(String cardNo);
	public boolean isCitizenRelateCardNo(String citizenId, String cardNo);
	public String loadallNameInformationByRole(String username);
	public void saveApplication(PLApplicationDataM appM, UserDetailM userM);
	public Vector<String> loadBlockApplication (String appRecID);
	public Vector<NotePadDataM> loadNotePad (String appRecId);
	public void saveNotePad(Vector<NotePadDataM> noteVect, String appRecId);
	public Vector<PLAccountLogDataM> loadAccLogSortAsc (String accId);
	public String[] loadAccLogFirstRow (String accId);
	public String deCodeCardNo (String cardNo);
	public void InactiveAccount(String accId, UserDetailM userM);
	public void InactiveAccountCard(String accId, UserDetailM userM);
	public void setFinalAppDecision(PLApplicationDataM appM);
	public int getCardLinkStatus (String accId);
	public void updateAppStatusNotJoinWF (PLApplicationDataM appM, UserDetailM userM);
//	#septemwi comment not used
//	public void saveReasonM(Vector<PLReasonDataM> reasonVect, String appRecId);
	public String reasonFromRejectCancel(String appRecId);
	public String getAddressID(String type, String descVal, String optionalID);	
	public String GetGeneralParam(String paramCode, String busClassID);	
	public String GetChoiceNOFieldIDByDesc(String fieldID,String desc);
	public void saveNcbDochistVect(Vector<PLNCBDocDataM> ncbDocVect, UserDetailM userM, String consentRefNo);	
	public SearchAddressDataM SearchZipCode(String zipcode ,String province ,String amphur ,String tambol);
	public SearchAddressDataM SearchTambol(String province ,String amphur ,String tambol);
	public SearchAddressDataM SearchAmphur(String province ,String amphur);
	public SearchAddressDataM SearchProvince(String province);
	public SearchAddressDataM MandatoryAddress(String zipcode ,String province ,String amphur ,String tambol);	
	public void saveEnquiryLog (EnquiryLogDataM enqLogM);
	public PLApplicationDataM loadOrigApplicationForNCB(String appRecId);
	public ArrayList<String> LoadORIGApplication(String jobState ,int size);	
	public int GetTotalJobCentralQueue(String tdID ,String queueType);
	public String LoadUserName(String userName,String role);
	public Vector<ORIGWorkflowDataM> getQueueByOwner(String owner ,String tdID);
	public String isDataFinalCompleteApprove(String appRecordId);
	
	public int countUser(TrackingDataM trackM);
	public String countLogOn(TrackingDataM trackM);
	public String countOnJob(TrackingDataM trackM);
	public HashMap<String,Integer> countWfJob(TrackingDataM trackM);
	public HashMap<String, String> getServiceLOG();	
	public String getTotolJobWorking(TrackingDataM trackM);
	public Vector<PLReasonDataM> getConfirmRejectReasons(String appRecordID, UserDetailM userM);
}
