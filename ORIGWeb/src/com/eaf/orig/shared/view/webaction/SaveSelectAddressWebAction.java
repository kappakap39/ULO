/*
 * Created on Oct 27, 2007
 * Created by weeraya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
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
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;

/**
 * @author weeraya
 *
 * Type: SaveAddressWebAction
 */
public class SaveSelectAddressWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SaveSelectAddressWebAction.class);
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
        ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
        
        try{
	        String[] addressChecked = getRequest().getParameterValues("select");
	        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
	        
	        logger.debug("Personal Type = "+personalType);
	        logger.debug("addressChecked = "+addressChecked);
	        
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
	    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
	    	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	    	}else{
	    		personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	    	}
	    	Vector addressVect = personalInfoDataM.getAddressVect();
	    	Vector addressCloneVect = (Vector) SerializeUtil.clone(personalInfoDataM.getAddressVect());
	    	Vector addressTmpVect = personalInfoDataM.getAddressTmpVect();
	    	Vector addressTypeSelect = new Vector();
	    	
	    	AddressDataM addressDataM;
	    	AddressDataM addressDataMTmp;
	    	int seq;
	    	if(addressChecked != null){
		        for(int i=0; i<addressChecked.length; i++){
		        	seq = Integer.parseInt(addressChecked[i]);
		        	logger.debug("addressChecked["+i+"] "+addressChecked[i]);
		        	addressDataMTmp = utility.getAddressBySeq(addressTmpVect, seq);
		        	addressDataMTmp.setOrigOwner("N");
		        	String addressType;
		        	//for(int j=0; j<addressTypeSelect.size(); j++){
		        		//addressType = (String) addressTypeSelect.get(j);
		        		if(addressTypeSelect.contains(addressDataMTmp.getAddressType())){
		        			Vector errorVect = formHandler.getFormErrors();
		        			if(errorVect == null){
		        				errorVect = new Vector();
		        				formHandler.setFormErrors(errorVect);
		        			}
		        			errorVect.add("Please select only one address for each address type");
		        			personalInfoDataM.setAddressVect(addressCloneVect);
		        			return false;
		        		}
		        	//}
		        	addressTypeSelect.add(addressDataMTmp.getAddressType());
		        	
		        	UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			        if(ORIGUtility.isEmptyString(addressDataMTmp.getCreateBy())){
			        	addressDataMTmp.setCreateBy(userM.getUserName());
			        }else{
			        	addressDataMTmp.setUpdateBy(userM.getUserName());
			        }
			        
		        	addressVect.add(addressDataMTmp);
		        }
	    	}
	        addressVect = personalInfoDataM.getAddressVect();
			/*for(int i = 0; i < addressVect.size(); i++){
				addressDataM = (AddressDataM)addressVect.get(i);
				addressDataM.setAddressSeq(i + 1);
			}*/
	        
	        //Rewrite
	        String tableData = utility.getAddressTable(addressVect, getRequest());
	        
	        PopulatePopupM populatePopupM = new PopulatePopupM("Address",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData));
			Vector popMs = new Vector();
			popMs.add(populatePopupM);
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
			getRequest().getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
			getRequest().getSession().setAttribute("SUPCARD_POPUP_DATA", personalInfoDataM);
        }catch(Exception e){
            logger.error("Error in SaveSelectAddressWebAction.preModelRequest() >> ", e);
        }
        
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.FORWARD;
    }
    /* (non-Javadoc)
	 * @see com.bara.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return "orig/appform/filterMainScreen.jsp";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
