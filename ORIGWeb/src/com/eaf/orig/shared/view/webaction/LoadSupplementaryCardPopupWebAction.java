/*
 * Created on Dec 12, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadSupplementaryCardPopupWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(LoadSupplementaryCardPopupWebAction.class); 

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		// TODO Auto-generated method stub
        logger.debug("+++++LoadSupplementaryCardPopupWebAction+++++");
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null==userM)	userM=new UserDetailM();		

		ORIGUtility utility = new ORIGUtility();
		ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		ORIGFormHandler popupForm = (ORIGFormHandler) ORIGForm.getPopupForm();
		if(popupForm == null){
		    popupForm = new ORIGFormHandler();
		    ORIGForm.setPopupForm(popupForm);
		}
		String customerType = (String) getRequest().getParameter("type");
		if (customerType == null || "".equalsIgnoreCase(customerType)){
			PersonalInfoDataM temp = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
			if (temp!=null){
				customerType = temp.getCustomerType();
			}
		}
		Vector userRoles = userM.getRoles();
		String formID = "SUPCARD_FORM";
		String currentTab = "MAIN_TAB";
		//****************************************
		popupForm.getSubForms().clear();
		popupForm.setIsLoadedSubForms(false);
		//****************************************
		popupForm.loadSubForms(userRoles, formID);
		popupForm.setCurrentTab(currentTab);
		popupForm.setFormID(formID);
		logger.debug("customerType = "+customerType);
		popupForm.setSubForms(ORIGUtility.filterSubformByMainCusType(popupForm.getSubForms(), customerType));
		//*****************************************
		
		//Get Personal
		PersonalInfoDataM personalInfoDataM;
		String seqStr = (String) getRequest().getParameter("seq");
        int seq = 0;
        if(seqStr != null && !("").equals(seqStr)){
            seq = Integer.parseInt(seqStr);
        }
        logger.debug("seq "+seq);
       // if( OrigConstant.COBORROWER_FLAG_ACTIVE.equals(coborrwerFlag)){
		    //get Persoanl applicaint
		//    PersonalInfoDataM  applicaintPersonalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
		//   customerType=applicaintPersonalInfoDataM.getCustomerType();    
		//} 
		if(seq == 0){
		    personalInfoDataM = new PersonalInfoDataM();
		    personalInfoDataM.setCustomerType(customerType);
		    personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_SUP_CARD);
		    personalInfoDataM.setCmpCode(utility.getGeneralParamByCode(OrigConstant.ORIG_CMPCDE));
		    personalInfoDataM.setPersonalSeq(seq);
		    if("Y".equals(ORIGForm.getAppForm().getDrawDownFlag())){
		    	PersonalInfoDataM personalInfoDataM2 = (PersonalInfoDataM)getRequest().getSession().getAttribute("CUSTOMER_DRAW_DOWN");
		    	logger.debug("Load data first when application is drawdown .... personal drowdown = "+personalInfoDataM2);
		    	if(personalInfoDataM2 != null){
		    		personalInfoDataM.setXrulesVerification((XRulesVerificationResultDataM)SerializeUtil.clone(personalInfoDataM2.getXrulesVerification()));
		    	}
		    }
		    
		}else{
		    personalInfoDataM = utility.getPersonalInfoByTypeAndSeq(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_SUP_CARD, seq);
		}
		personalInfoDataM.setAddressIndex(personalInfoDataM.getAddressVect().size()+1);
		personalInfoDataM.setFinanceIndex(personalInfoDataM.getFinanceVect().size()+1);
		personalInfoDataM.setChangeNameIndex(personalInfoDataM.getChangeNameVect().size()+1);				     		
		getRequest().getSession().setAttribute("ORIGForm",ORIGForm);
		getRequest().getSession().setAttribute("PersonalType",OrigConstant.PERSONAL_TYPE_SUP_CARD);
		getRequest().getSession().setAttribute("SUPCARD_POPUP_DATA", personalInfoDataM);
        return true;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
