/*
 * Created on Dec 26, 2008
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
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.CollateralDataM;
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
public class LoadMortgageLoanPopupWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(LoadMortgageLoanPopupWebAction.class); 
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
         
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        logger.debug("+++++LoadMortgageLoanPopupWebAction+++++");
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null==userM)	userM=new UserDetailM();		
						
		ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		ORIGFormHandler popupForm = (ORIGFormHandler) ORIGForm.getPopupForm();
		if(popupForm == null){
		    popupForm = new ORIGFormHandler();
		    ORIGForm.setPopupForm(popupForm);
		}
		String customerType = (String) getRequest().getParameter("type");
		String coborrwerFlag = (String) getRequest().getParameter("coborrowerFlag");
		Vector userRoles = userM.getRoles();
		String formID = "MORTGAGE_POPUP_FORM";
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
		ORIGUtility utility = new ORIGUtility();
		PersonalInfoDataM personalInfoDataM;
		String seqStr = (String) getRequest().getParameter("seq");
        int seq = 0;
        if(seqStr != null && !("").equals(seqStr)){
            seq = Integer.parseInt(seqStr);
        }
        logger.debug("seq "+seq);
        logger.debug("coborrowerFlag="+coborrwerFlag);
       // if( OrigConstant.COBORROWER_FLAG_ACTIVE.equals(coborrwerFlag)){
		    //get Persoanl applicaint
		//    PersonalInfoDataM  applicaintPersonalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
		//    customerType=applicaintPersonalInfoDataM.getCustomerType();    
		//} 
		AddressDataM  addressDataM;
        CollateralDataM collateralDataM;
        Vector collateralVect = ORIGForm.getAppForm().getCollateralVect();
        
		if(seq == 0){
            collateralDataM = new CollateralDataM();
            collateralDataM.setSeq(0);
            
            personalInfoDataM = new PersonalInfoDataM();
		    personalInfoDataM.setCustomerType(customerType);
		    personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_GUARANTOR);
		    personalInfoDataM.setCmpCode(utility.getGeneralParamByCode(OrigConstant.ORIG_CMPCDE));
		    personalInfoDataM.setPersonalSeq(seq);
		    personalInfoDataM.setCoborrowerFlag(coborrwerFlag);
		    if("Y".equals(ORIGForm.getAppForm().getDrawDownFlag())){
		    	PersonalInfoDataM personalInfoDataM2 = (PersonalInfoDataM)getRequest().getSession().getAttribute("CUSTOMER_DRAW_DOWN");
		    	logger.debug("Load data first when application is drawdown .... personal drowdown = "+personalInfoDataM2);
		    	if(personalInfoDataM2 != null){
		    		personalInfoDataM.setXrulesVerification((XRulesVerificationResultDataM)SerializeUtil.clone(personalInfoDataM2.getXrulesVerification()));
		    	}
		    }

            addressDataM = new AddressDataM();
            addressDataM.setAddressSeq(0);
		}else{
		    logger.debug("collateralVect.size()="+collateralVect.size());
		    collateralDataM = (CollateralDataM)collateralVect.get(seq-1);
		    addressDataM = collateralDataM.getAddress();
		}			     		
		getRequest().getSession().setAttribute("COLLATERAL_POPUP_DATA", collateralDataM);
		getRequest().getSession().setAttribute("ADDRESS_POPUP_DATA", addressDataM);
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
         
        return 0;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
