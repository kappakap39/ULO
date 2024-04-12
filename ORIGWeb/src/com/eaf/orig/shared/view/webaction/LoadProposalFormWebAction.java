package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.ProposalDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LoadProposalFormWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(LoadProposalFormWebAction.class);
	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		return null;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
	    log.debug("++++++++++++++ Start LoadProposalFormWebAction  ++++++++++++++++++++");

	    try{
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		log.debug("[userM.getRoles()] = " + userM.getRoles());
		if(null==userM)	userM=new UserDetailM();		
						
		ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		if(ORIGForm == null){
				ORIGForm = new ORIGFormHandler();
		}
		ORIGUtility utility = new ORIGUtility();	
		
		String customerType = (String) getRequest().getParameter("customerType");
		String loanType = (String) getRequest().getParameter("loanType");
		ApplicationDataM applicationDataM = null;
		

		if(applicationDataM == null){
			applicationDataM = new ApplicationDataM();
			applicationDataM.setLoanType(loanType);
			applicationDataM.setProposalDataM(new ProposalDataM());
			applicationDataM.setJobState(ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE);
		}
		if(ORIGUtility.isEmptyString(applicationDataM.getAppRecordID())){
			//Get Application Record ID
			//String prmApplicationRecordID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.APPLICATION_ID); 
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			String prmApplicationRecordID = generatorManager.generateUniqueIDByName(EJBConstant.APPLICATION_ID);
			applicationDataM.setAppRecordID(prmApplicationRecordID);
		}
		
		//Set null date
		applicationDataM.setNullDate(utility.getNulldate(userM.getDefaultOfficeCode()));
		applicationDataM.setSysNulldate(utility.getSysNulldate(userM.getDefaultOfficeCode()));
		
		ORIGForm.setAppForm(applicationDataM);
		
		PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
		if(personalInfoDataM == null){
		    personalInfoDataM = new PersonalInfoDataM();
		    personalInfoDataM.setCustomerType(customerType);
		    personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
		    personalInfoDataM.setCmpCode(utility.getGeneralParamByCode(OrigConstant.ORIG_CMPCDE));
		    personalInfoDataM.setAppRecordID(applicationDataM.getAppRecordID());
		    ORIGForm.getAppForm().getPersonalInfoVect().add(personalInfoDataM);
		}
		personalInfoDataM.setAddressIndex((utility.getMaxAddressSeq(personalInfoDataM.getAddressVect()))+1);
		personalInfoDataM.setFinanceIndex((utility.getMaxFinanceSeq(personalInfoDataM.getFinanceVect()))+1);
		personalInfoDataM.setChangeNameIndex((utility.getMaxChangeNameSeq(personalInfoDataM.getChangeNameVect()))+1);
		ORIGForm.getAppForm().setMainCustomerType(personalInfoDataM.getCustomerType());
		//*****************************************
		Vector userRoles = userM.getRoles();
		String formID = getRequest().getParameter("formID");
		String currentTab = getRequest().getParameter("currentTab");
		//****************************************
		ORIGForm.getSubForms().clear();
		ORIGForm.setIsLoadedSubForms(false);
		//****************************************
		ORIGForm.loadSubForms(userRoles, formID);
		ORIGForm.setCurrentTab(currentTab);
		ORIGForm.setFormID(formID);
		log.debug("form id is"+formID);
		log.debug("current tab is"+currentTab);
		//*****************************************	
		getRequest().getSession(true).setAttribute("ORIGForm",ORIGForm);
		getRequest().getSession(true).setAttribute("PersonalType",OrigConstant.PERSONAL_TYPE_APPLICANT);
		getRequest().getSession(true).setAttribute("PROPOSAL_MENU","Y");
	
	
		log.debug("++++++++++++++ End LoadProposalFormWebAction  ++++++++++++++++++++");
	    }catch (Exception e) {
	        log.error("Exception:",e);
        }
		return true;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		return FrontController.ACTION;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return "action=loadMenuAppForm";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
