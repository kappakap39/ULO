package com.eaf.orig.ulo.pl.app.utility;

import java.sql.Connection;
import org.apache.log4j.Logger;
import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.model.WorkflowDataM;
import com.orig.bpm.ulo.proxy.BpmWorkflow;

/***
 * Util For WebAction Manage
 * @author septemwi
 */
public class WebActionUtil {
	
	static Logger log = Logger.getLogger(WebActionUtil.class) ;
	
	/**
	 * Function getForwordPageSummaryRole For Get Page Summary ForwordFormSummit #SeptemWi 
	 */
	public static String getForwordPageSummaryRole(String role){
		/**Set Current DE_PL_SUMMARY_SCREEN For Page Not Have File Summary Screen!*/
		if(OrigConstant.ROLE_DE.equals(role))return "DE_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_DE_SUP.equals(role))return "DE_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_CA.equals(role))return "CA_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_CA_SUP.equals(role))return "CA_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_DC.equals(role))return "DC_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_DC_SUP.equals(role))return "DC_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_DF.equals(role))return "DE_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_DF_SUP.equals(role))return "DE_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_FU.equals(role))return "FU_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_FU_SUP.equals(role))return "FU_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_VC.equals(role))return "DE_PL_SUMMARY_SCREEN";
		else if(OrigConstant.ROLE_VC_SUP.equals(role))return "DE_PL_SUMMARY_SCREEN";		
		return "DE_PL_SUMMARY_SCREEN";
	}
	
	public static int getEvenAction(String role){	
		if(OrigConstant.ROLE_DE.equals(role))return PLApplicationEvent.DE_SUBMIT;
		else if(OrigConstant.ROLE_DE_SUP.equals(role))return PLApplicationEvent.DE_SUBMIT;
		else if(OrigConstant.ROLE_CA.equals(role))return PLApplicationEvent.CA_SUBMIT;
		else if(OrigConstant.ROLE_CA_SUP.equals(role))return PLApplicationEvent.CA_SUBMIT;
		else if(OrigConstant.ROLE_DC.equals(role))return PLApplicationEvent.DC_SUBMIT;
		else if(OrigConstant.ROLE_DC_SUP.equals(role))return PLApplicationEvent.DC_SUBMIT;
		else if(OrigConstant.ROLE_DF.equals(role))return PLApplicationEvent.DF_SUBMIT;
		else if(OrigConstant.ROLE_DF_SUP.equals(role))return PLApplicationEvent.DF_SUBMIT;
		else if(OrigConstant.ROLE_FU.equals(role))return PLApplicationEvent.FU_SUBMIT;
		else if(OrigConstant.ROLE_FU_SUP.equals(role))return PLApplicationEvent.FU_SUBMIT;
		else if(OrigConstant.ROLE_VC.equals(role))return  PLApplicationEvent.VC_SUBMIT;
		else if(OrigConstant.ROLE_VC_SUP.equals(role))return PLApplicationEvent.VC_SUBMIT;	
		return PLApplicationEvent.DE_SUBMIT;
	}
	
	public static String getJobState (String role){
		if(OrigConstant.ROLE_CA.equals(role) || OrigConstant.ROLE_I_CA.equals(role))return OrigConstant.generalParam_JobState.CA;
		else if(OrigConstant.ROLE_CA_SUP.equals(role) || OrigConstant.ROLE_I_SUP_CA.equals(role))return OrigConstant.generalParam_JobState.CA_SUP;
		else if(OrigConstant.ROLE_I_SUP_CA2.equals(role) || OrigConstant.ROLE_I_SUP_CA1.equals(role))return OrigConstant.generalParam_JobState.CA;
		else if(OrigConstant.ROLE_CB.equals(role))return OrigConstant.generalParam_JobState.CB;
		else if(OrigConstant.ROLE_CB_SUP.equals(role))return OrigConstant.generalParam_JobState.CB_SUP;
		else if(OrigConstant.ROLE_DE.equals(role))return OrigConstant.generalParam_JobState.DE;
		else if(OrigConstant.ROLE_DE_SUP.equals(role))return OrigConstant.generalParam_JobState.DE_SUP;
		else if(OrigConstant.ROLE_DC.equals(role) || OrigConstant.ROLE_I_DC.equals(role))return OrigConstant.generalParam_JobState.DC;
		else if(OrigConstant.ROLE_DC_SUP.equals(role) || OrigConstant.ROLE_I_SUP_DC.equals(role))return OrigConstant.generalParam_JobState.DC_SUP;
		else if(OrigConstant.ROLE_DF.equals(role))return OrigConstant.generalParam_JobState.DF;
		else if(OrigConstant.ROLE_DF_SUP.equals(role))return OrigConstant.generalParam_JobState.DF_SUP;
		else if(OrigConstant.ROLE_FU.equals(role) || OrigConstant.ROLE_I_FU.equals(role))return OrigConstant.generalParam_JobState.FU;
		else if(OrigConstant.ROLE_FU_SUP.equals(role) || OrigConstant.ROLE_I_SUP_FU.equals(role))return OrigConstant.generalParam_JobState.FU_SUP;
		else if(OrigConstant.ROLE_VC.equals(role))return OrigConstant.generalParam_JobState.VC;
		else if(OrigConstant.ROLE_VC_SUP.equals(role))return OrigConstant.generalParam_JobState.VC_SUP;
		return null;
	}
	
	public static String getUnderRole(String role){
		if(OrigConstant.ROLE_CA_SUP.equals(role))return OrigConstant.ROLE_CA;
		else if(OrigConstant.ROLE_CB_SUP.equals(role))return OrigConstant.ROLE_CB;
		else if(OrigConstant.ROLE_DE_SUP.equals(role))return OrigConstant.ROLE_DE;
		else if(OrigConstant.ROLE_DC_SUP.equals(role))return OrigConstant.ROLE_DC;
		else if(OrigConstant.ROLE_DF_SUP.equals(role))return OrigConstant.ROLE_DF;
		else if(OrigConstant.ROLE_FU_SUP.equals(role))return OrigConstant.ROLE_FU;
		else if(OrigConstant.ROLE_VC_SUP.equals(role))return OrigConstant.ROLE_VC;
		else if(OrigConstant.ROLE_I_SUP_CA.equals(role))return OrigConstant.ROLE_I_CA;
		else if(OrigConstant.ROLE_I_SUP_CA1.equals(role))return OrigConstant.ROLE_I_CA;
		else if(OrigConstant.ROLE_I_SUP_CA2.equals(role))return OrigConstant.ROLE_I_CA;
		else if(OrigConstant.ROLE_I_SUP_DC.equals(role))return OrigConstant.ROLE_I_DC;
		else if(OrigConstant.ROLE_I_SUP_FU.equals(role))return OrigConstant.ROLE_I_FU;
		return null;
	}
	
	public static String getUnderRoleReassign(String role){
		if(OrigConstant.ROLE_CA_SUP.equals(role))return OrigConstant.UnderRoleReAssign.CA_SUP;
		if(OrigConstant.ROLE_CB_SUP.equals(role))return OrigConstant.UnderRoleReAssign.CB_SUP;
		if(OrigConstant.ROLE_DE_SUP.equals(role))return OrigConstant.UnderRoleReAssign.DE_SUP;
		if(OrigConstant.ROLE_DC_SUP.equals(role))return OrigConstant.UnderRoleReAssign.DC_SUP;
		if(OrigConstant.ROLE_DF_SUP.equals(role))return OrigConstant.UnderRoleReAssign.DF_SUP;
		if(OrigConstant.ROLE_FU_SUP.equals(role))return OrigConstant.UnderRoleReAssign.FU_SUP;
		if(OrigConstant.ROLE_VC_SUP.equals(role))return OrigConstant.UnderRoleReAssign.VC_SUP;
		if(OrigConstant.ROLE_I_SUP_CA.equals(role))return OrigConstant.UnderRoleReAssign.I_SUP_CA;
		if(OrigConstant.ROLE_I_SUP_CA1.equals(role))return OrigConstant.UnderRoleReAssign.I_SUP_CA;
		if(OrigConstant.ROLE_I_SUP_CA2.equals(role))return OrigConstant.UnderRoleReAssign.I_SUP_CA;
		if(OrigConstant.ROLE_I_SUP_DC.equals(role))return OrigConstant.UnderRoleReAssign.I_SUP_DC;
		if(OrigConstant.ROLE_I_SUP_FU.equals(role))return OrigConstant.UnderRoleReAssign.I_SUP_FU;
		return null;
	}
	
	public static String getUnderRoleWf(String role){
		if(OrigConstant.ROLE_CA_SUP.equals(role))return OrigConstant.ROLE_CA;
		else if(OrigConstant.ROLE_CB_SUP.equals(role))return OrigConstant.ROLE_CB;
		else if(OrigConstant.ROLE_DE_SUP.equals(role))return OrigConstant.ROLE_DE;
		else if(OrigConstant.ROLE_DC_SUP.equals(role))return OrigConstant.ROLE_DC;
		else if(OrigConstant.ROLE_DF_SUP.equals(role))return OrigConstant.ROLE_DF;
		else if(OrigConstant.ROLE_FU_SUP.equals(role))return OrigConstant.ROLE_FU;
		else if(OrigConstant.ROLE_VC_SUP.equals(role))return OrigConstant.ROLE_VC;
		else if(OrigConstant.ROLE_I_SUP_CA.equals(role))return OrigConstant.ROLE_CA;
		else if(OrigConstant.ROLE_I_SUP_CA1.equals(role))return OrigConstant.ROLE_CA;
		else if(OrigConstant.ROLE_I_SUP_CA2.equals(role))return OrigConstant.ROLE_CA;
		else if(OrigConstant.ROLE_I_SUP_DC.equals(role))return OrigConstant.ROLE_DC;
		else if(OrigConstant.ROLE_I_SUP_FU.equals(role))return OrigConstant.ROLE_FU;
		return null;
	}
	public static String getRoleWf(String role){
		return ORIGLogic.getRoleWf(role);
	}
	public static String getTemplateRole(String role){
		if(OrigConstant.ROLE_I_SUP_CA.equals(role))return WorkflowConstant.ProcessTemplate.KLOP002;
		else if(OrigConstant.ROLE_I_SUP_CA1.equals(role))return WorkflowConstant.ProcessTemplate.KLOP002;
		else if(OrigConstant.ROLE_I_SUP_CA2.equals(role))return WorkflowConstant.ProcessTemplate.KLOP002;
		else if(OrigConstant.ROLE_I_SUP_DC.equals(role))return WorkflowConstant.ProcessTemplate.KLOP002;
		else if(OrigConstant.ROLE_I_SUP_FU.equals(role))return WorkflowConstant.ProcessTemplate.KLOP002;
		return WorkflowConstant.ProcessTemplate.KLOP001+","+WorkflowConstant.ProcessTemplate.KLOP003;
	}
	
	public void getAction(PLApplicationDataM applicationM, UserDetailM userM, String submitType, String SearchType){		
		try{
			if(!OrigUtil.isEmptyObject(applicationM) && !OrigUtil.isEmptyString(applicationM.getAppRecordID())){
				
				//load block flag
				ORIGDAOUtilLocal daoBean = PLORIGEJBService.getORIGDAOUtilLocal();
					applicationM.setBlockFlag(daoBean.loadBlockFlag(applicationM.getAppRecordID()));
				
				//load Block Application
				this.LoadBlockCancle(applicationM);
				
				log.debug("Application Decision ... "+applicationM.getAppDecision());
				
				if(applicationM.getEventType() != PLApplicationEvent.BLOCK_CANCEL){
					
					if(OrigConstant.SEARCH_TYPE_PL_CONFIRM_REJECT.equals(SearchType)
							&& OrigConstant.wfProcessState.SEND.equals(applicationM.getAppDecision())){
						applicationM.setAppDecision(OrigConstant.wfProcessState.SENDX);
					}
					
					//change action confirmReject to reopenConfirmReject when reopenFlag = Y
					if(OrigConstant.FLAG_Y.equals(applicationM.getReopenFlag()) 
							&& OrigConstant.Action.CONFIRM_REJECT.equals(applicationM.getAppDecision()) ){
//						#septemwi comment
//						applicationM.setAppDecision(OrigConstant.Action.REOPEN_CONFIRM_REJECT);
						applicationM.setAppDecision(OrigConstant.Action.REJECT_SKIP_DF);
					}
					
					//connection
					OrigObjectDAO origObjDAO = new OrigObjectDAO();
					Connection conn = origObjDAO.getConnection(OrigServiceLocator.WORKFLOW_DB);
					
					BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();						
					WorkflowDataM workFlowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);    	    	
					WorkflowResponse wfResp = bpmWorkflow.GetOfficialAction(workFlowM, conn);
					
					applicationM.setAppDecision(wfResp.getAction());
						
				}
				
				//set final decsion
				if(OrigConstant.SUBMIT_TYPE_SEND.equals(submitType)){
					OrigApplicationUtil.getInstance().setFinalAppDecision(applicationM, userM);
				}				
				//set CA Decision
				setRoleDecision(applicationM, userM);
				
				LogicDecision(applicationM);
				
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
		}
	}
	
	public void LogicDecision(PLApplicationDataM applicationM){
		if(OrigConstant.Action.REQUEST_FU.equals(applicationM.getAppDecision())
				|| OrigConstant.Action.REQUEST_FU_BLOCK.equals(applicationM.getAppDecision())){
			applicationM.setSmsFollowUp(null);
			applicationM.setSmsFollowUpDate(null);
		}
	}
	
	public void LoadBlockCancle(PLApplicationDataM applicationM) throws Exception{		
		if (OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision())
					&& OrigConstant.FLAG_B.equals(applicationM.getBlockFlag())){
			ORIGDAOUtilLocal origDaoBean = PLORIGEJBService.getORIGDAOUtilLocal();
			applicationM.setBlockAppRecId(origDaoBean.loadBlockApplication(applicationM.getAppRecordID()));
			applicationM.setBlockFlag(OrigConstant.FLAG_C);
			applicationM.setEventType(PLApplicationEvent.BLOCK_CANCEL);			
		}
		
	}
	
	public void setRoleDecision(PLApplicationDataM applicationM, UserDetailM userM){		
		if(!OrigUtil.isEmptyString(userM.getCurrentRole())){
			if(OrigConstant.ROLE_CA.equals(userM.getCurrentRole()) && OrigUtil.isEmptyString(applicationM.getCaDecision())){
				applicationM.setCaDecision(applicationM.getAppDecision());
			}
		}		
	}
	
}
