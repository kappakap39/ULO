package com.eaf.orig.ulo.pl.app.dao;

import java.math.BigDecimal;
import java.util.Vector;
import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.ApplicationDuplicateM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public interface PLOrigApplicationDAO {	
	public void updateSaveOrigApplication (PLApplicationDataM applicationM, UserDetailM userM) throws PLOrigApplicationException;
	public PLApplicationDataM loadOrigApplication (String appId) throws PLOrigApplicationException;
	public PLApplicationDataM loadOrigApplicationAppNo (String appNo) throws PLOrigApplicationException;
	public int updatePLApplicationStatus (WorkflowResponse response ,UserDetailM userM) throws PLOrigApplicationException;
	public void updatePLApplicationStatus (PLApplicationDataM applicationM ,UserDetailM userM) throws PLOrigApplicationException;
	public int updatePriority(PLApplicationDataM appDataM) throws PLOrigApplicationException;
	public int selectLifeCycle(String appRecId) throws PLOrigApplicationException;
	public Vector<ApplicationDuplicateM> GetDuplicateApplication(String idNo ,String appRecordID,String gProduct ,String gJobstate)throws PLOrigApplicationException;
	public void updateBlockFlag (PLApplicationDataM applicationM ,UserDetailM userM) throws PLOrigApplicationException;
	public Vector<String[]> loadCBConsent (String dateFrom, String dateTo) throws PLOrigApplicationException;
	public void updateRoleOrigApplication(PLApplicationDataM appM, UserDetailM userM, String role) throws PLOrigApplicationException;
	public void updateReOpenFlag(PLApplicationDataM appM) throws PLOrigApplicationException;
	public String loadApplicationStatus (String appRecId) throws PLOrigApplicationException;
	public PLApplicationDataM loadAppInFo (String appRecID) throws PLOrigApplicationException;
	public void LoadAppInfo(PLApplicationDataM applicationM,String appRecID) throws PLOrigApplicationException;
	public Vector<String[]> loadAuditTrailField (String SubFormID) throws PLOrigApplicationException;
	public String loadBlockFlag (String appRecID) throws PLOrigApplicationException;
	public String loadJobID (String appRecId) throws PLOrigApplicationException;
	public BigDecimal loadCreditLine (String projectCode) throws PLOrigApplicationException;
	public Vector<String> loadBlockApplication (String appRecID) throws PLOrigApplicationException;
	public PLApplicationDataM selectOrig_Appplication (String appRecId) throws PLOrigApplicationException;
	public void updateFinalAppDecision (PLApplicationDataM appM) throws PLOrigApplicationException;
	public void UpdateRetrieveNewImage(String appRecID) throws PLOrigApplicationException;
	public void updateAppStatusNotJoinWF (PLApplicationDataM applicationM, UserDetailM userM) throws PLOrigApplicationException;
	public void UpdateFollowApplicationStatus(String appRecID ,String appStatus) throws PLOrigApplicationException;
	public void updateRoleDecision(PLApplicationDataM appM) throws PLOrigApplicationException;
	public void LoadRoleDecision(PLApplicationDataM applicationM) throws PLOrigApplicationException;
	public PLApplicationDataM loadOrigApplicationForNCB (String appId) throws PLOrigApplicationException;
	public void saveAppMForNCB (PLApplicationDataM applicationM, UserDetailM userM) throws PLOrigApplicationException;
	public PLApplicationDataM LoadNCBImageDataM(String appRecID) throws PLOrigApplicationException;
	public void updateSMSFollowDocFLAG(PLApplicationDataM applicationM) throws PLOrigApplicationException;
}
