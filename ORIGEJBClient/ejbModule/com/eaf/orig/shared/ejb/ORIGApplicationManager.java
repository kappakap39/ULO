package com.eaf.orig.shared.ejb;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.ejb.Local;

import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.ias.shared.model.UserM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
/**
 * Remote interface for Enterprise Bean: ORIGApplicationManager
 */
@Local
public interface ORIGApplicationManager {
	public boolean deSaveApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT, 
		String providerUrlIMG, 
		String jndiIMG);
	public boolean deSubmitApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF, 
		String customerType,
		String appType,
		String providerUrlEXT,
		String jndiEXT, 
		String providerUrlIMG, 
		String jndiIMG, 
		ServiceDataM serviceDataM);
	public ApplicationDataM loadApplicationDataM(
		ApplicationDataM applicationDataM,
		String appRecordID,
		String providerUrlEXT,
		String jndiEXT);
 
	public String cmrSaveApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT);
	public String cmrSubmitApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT);
	public boolean uwSaveApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT, 
		String customerType, 
		String appType);
	public boolean uwSubmitApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT, 
		String customerType, 
		String appType, 
		ServiceDataM serviceDataM);
	public boolean pdSaveApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT);
	public boolean pdSubmitApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT);
	public Vector loadCarDetail(
		String coClientID);
	public boolean saveCarDetail(
		Vector carDetailVt,String idNo);
	public boolean proposalSaveApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT);
	public boolean proposalSubmitApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT);
	public ApplicationDataM loadCarDetailDataM(
			ApplicationDataM applicationDataM,
			String vehicleID);
	public boolean uwReopenApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndiWF,
		String providerUrlEXT,
		String jndiEXT);
	public boolean xcmrSaveApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndi,
		String providerUrlEXT,
		String jndiEXT);
	public boolean xcmrSubmitApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndi,
		String providerUrlEXT,
		String jndiEXT);
	public int uwSetPriority(
		String appRecordID, 
		String priority,
		String userName);
	public boolean uwReAssignApplication(
		String appRecordID, 
		String appStatus, 
		String jobState, 
		UserDetailM userM, 
		String providerUrlWF, 
		String jndi);
	public String selectApplicationNo(String appRecordID);
	public void autoCancelApplication(
		String appRecordID,
		String jobstate,
		String appStatus);
	public void deleteOldSchedulerTask(String schedName);
    public String generateApplicationNo(ApplicationDataM prmApplicationDataM);
	public ApplicationDataM loadApplicationDataMForDrawDown(
		ApplicationDataM applicationDataM,
		String appRecordID);
	public String getAppRecordForCreateDrawDown(String personalID);
	public PersonalInfoDataM getVerificationForCreateDrawDown(
		PersonalInfoDataM prmPersonalInfoDataM);
	public boolean sendSMSAndEmail(
		ApplicationDataM applicationDataM,
		ServiceDataM serviceDataM,
		UserDetailM userLogon);
	public boolean saveUserDetail(UserM userM);
	public ApplicationDataM loadApplicationDataMForNCBFirst(
		ApplicationDataM applicationDataM,
		String appRecordID,
		String providerUrlEXT,
		String jndiEXT);
    public ApplicationDataM ncbSaveApplication(
        ApplicationDataM applicationDataM,
        UserDetailM userM,
        String providerUrlWF,
        String jndiWF,
        String providerUrlEXT,
        String jndiEXT,
        String providerUrlIMG,
        String jndiIMG);
    public ApplicationDataM preScoreSaveApplication(
        ApplicationDataM applicationDataM,
        UserDetailM userM,
        String providerUrlWF,
        String jndiWF,
        String providerUrlEXT,
        String jndiEXT,
        String providerUrlIMG,
        String jndiIMG);
	public ApplicationDataM pdReverseApplication(
		ApplicationDataM applicationDataM,
		UserDetailM userM,
		String providerUrlWF,
		String jndi,
		String providerUrlEXT,
		String jndiEXT);
    public boolean manualCancelApplication(
        ApplicationDataM applicationDataM,
        UserDetailM userM,
        String providerUrlWF,
        String jndi,
        String providerUrlEXT,
        String jndiEXT,
        String customerType,
        String appType,
        ServiceDataM serviceDataM);
    public boolean xuwSaveApplication(
        ApplicationDataM applicationDataM,
        UserDetailM userM,
        String providerUrlWF,
        String jndi,
        String providerUrlEXT,
        String jndiEXT);
    public boolean xuwSubmitApplication(
        ApplicationDataM applicationDataM,
        UserDetailM userM,
        String providerUrlWF,
        String jndi,
        String providerUrlEXT,
        String jndiEXT);
    //	add method for personal info
    public PersonalInfoDataM loadModelPersonal(PersonalInfoDataM prmPersonalInfoDataM);    
    // Claim Application
    public WorkflowResponse claimApplication(String appRecordID, String appStatus, String jobState, UserDetailM userM, String decision,
            String providerUrlWF, String jndi);
    public WorkflowResponse cancleclaim(ApplicationDataM applicationM, String providerUrlWF, String jndi);
    public WorkflowResponse cancleclaimByUserId(String userId);
	public boolean logonOrigApp(String username, String ipAddress ,String clientName);
	public String LogonOrigApplication(String username);
	public void LogonUserDetail(String username);
	public boolean CancelLogonOrig(String username,String ipAddress , String clientName);
	public void setUserLogonFlag(String userName ,String flag);
	ApplicationGroupDataM startBPMFlow(String userName) throws EJBException;
}
