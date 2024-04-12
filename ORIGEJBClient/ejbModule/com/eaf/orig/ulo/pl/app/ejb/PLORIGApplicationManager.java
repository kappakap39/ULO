package com.eaf.orig.ulo.pl.app.ejb;

import java.sql.Timestamp;
import java.util.Vector;
import javax.ejb.Local;
import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.orig.inf.log.model.HistoryDataM;
import com.eaf.orig.profile.model.LogonDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.model.app.PLImportCreditLineDataM;
import com.eaf.orig.ulo.pl.model.app.PLResponseImportDataM;
import com.orig.bpm.ulo.model.WorkflowDataM;

@Local
public interface PLORIGApplicationManager {
	public PLApplicationDataM loadPLApplicationDataM(String appRecId);
	public PLApplicationDataM LoadNCBImageDataM(String appRecID);
	public void savePLApplicationDataM(PLApplicationDataM applicationM, UserDetailM userM);
	public String createApplicationICDC(PLApplicationDataM applicationM, UserDetailM userM);
	public void SaveApplication(PLApplicationDataM applicationM, UserDetailM userM);
	public String updateSetPriority(PLApplicationDataM appDataM, UserDetailM userDetailM);
	public void confirmReject(Vector<PLApplicationDataM> appDataVect, UserDetailM userDetailM);
	public void unblockApplication(Vector<PLApplicationDataM> appDataVect, UserDetailM userDetailM);
	public String reassingOrReallocateOrSendbackApplication(PLApplicationDataM appM, UserDetailM userM, String action, String reassignTo, String reassignType);
	public WorkflowResponse claimApplication(PLApplicationDataM plApplicationM,UserDetailM userM);
	public void reIssueCardNo (PLAccountCardDataM accCardM, UserDetailM userM);
	public void reIssueCustNo (PLAccountDataM accM, UserDetailM userM);
	public void claimAndCompleteApplication(PLApplicationDataM appDataM, UserDetailM userM);
	public void claimAndCompleteApplicationWithOutRole(PLApplicationDataM appDataM, UserDetailM userM);
	public String SetButtonStatus(WorkflowDataM workflowM, UserDetailM userM);
	public void PullWorkflowJob(WorkflowDataM workflowDataM, UserDetailM userM);
	public void reOpenFlow(PLApplicationDataM applicationM, UserDetailM userM);
	public void cancelClaimWithoutSaveLog(PLApplicationDataM plApplicationM, UserDetailM userM);
	public WorkflowResponse claimApplication(String appRecId, String role,	String userName);
	public String reWorkApplication(PLApplicationDataM appM, UserDetailM userDetailM);
	public void claimAndCompleteApplicationVect(Vector<PLApplicationDataM> appDataVect, UserDetailM userDetailM);
	public void updateAppClaimCompleteForNCB(PLApplicationDataM applicationM, UserDetailM userDetailM, String consentRefNo,String TrackingCode);
	public PLResponseImportDataM importCreditLineData(String sessionId, PLAttachmentHistoryDataM attachmentM, Vector<PLImportCreditLineDataM> importCreditLineVect, UserDetailM userM);
	public boolean importCreditLineDataOnly(String sessionId, PLAttachmentHistoryDataM attachmentM, Vector<PLImportCreditLineDataM> importCreditLineVect, UserDetailM userM);
	public void sendEmailSMS(PLApplicationDataM applicationM, String role);
	public void SaveDeplicateApplication(PLApplicationDataM applicationM,UserDetailM userM);
	public PLApplicationDataM loadAppliationAppNo(String appNo);
	public void saveCashDay5PLApplicationDataM(PLApplicationDataM applicationM,UserDetailM userM);
	void pullJobBundling(PLApplicationDataM applicationM, UserDetailM userM);
	public void claimCompleteAndSaveLogApplication(PLApplicationDataM appDataM, UserDetailM userM);
	void createAppBundling(PLApplicationDataM applicationM, UserDetailM userM);
	public void completeApplication(PLApplicationDataM plApplicationM, UserDetailM userM);
	public void completeApplicationWithOutSaveLog(PLApplicationDataM plApplicationM, UserDetailM userM);
	public void claimSaveAndCompleteApplication(PLApplicationDataM applicationM, UserDetailM userM);
	public void saveBlockCancelApplication(PLApplicationDataM applicationM, UserDetailM userM);
	public void SaveDeplicatePOApplication(PLApplicationDataM applicationM,UserDetailM userM);
	public void SaveRetrieveOldNcbData(PLApplicationDataM appM, UserDetailM userDetailM, String consentRefNo, String Result, String TrackingCode);
	public void CancelApplication(PLApplicationDataM applicationM,UserDetailM userM);
	public void SaveCancelPOApplication(PLApplicationDataM applicationM, UserDetailM userM);
	public void createApplicationManual(PLApplicationDataM applicationM, UserDetailM userM);
	boolean LogOrigLogon(LogonDataM logonM,String action, String description, Timestamp time);
	public WorkflowResponse ClaimWorkQueueApplication(String appRecID ,UserDetailM userM);
	public void AllocateApplication(PLApplicationDataM applicationM , UserDetailM userM);
	public void saveHistoryDataM(HistoryDataM historyM);
	public PLApplicationDataM getAppInfo(String appRecID);
	public void ReAssignApplication(PLApplicationDataM applicationM , UserDetailM userM,String reAssignTo);

	public Vector getPolicyRejectReasonVt(PLApplicationDataM applicationM, UserDetailM userM);
	public void saveFuApplication(PLApplicationDataM applicationM, UserDetailM userM);
	
}