package com.eaf.orig.ulo.pl.app.ejb;

import java.util.Vector;

import javax.ejb.Remote;

import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageDataM;

@Remote
public interface PLORIGRemoteApplicationManager{	
	public PLApplicationDataM loadPLApplicationDataM(String appRecId);
	public void savePLApplicationDataM(PLApplicationDataM applicationM,	UserDetailM userM);
	public PLApplicationDataM loadAppliationAppNo(String appNo);
	public void SaveDeplicateApplication(PLApplicationDataM applicationM,UserDetailM userM);
	public WorkflowResponse claimApplication(PLApplicationDataM plApplicationM,UserDetailM userM);
	public WorkflowResponse ClaimAppWithOutRole(PLApplicationDataM appDataM, UserDetailM userM);
	public void SaveFollowDocument(PLApplicationImageDataM appImgM ,String appRecID);
	public void updateAppClaimCompleteForNCB(PLApplicationDataM appM, UserDetailM userDetailM, String consentRefNo,String TrackingCode);
	public Vector getPolicyRejectReasonVt(PLApplicationDataM applicationM, UserDetailM userM);
}
