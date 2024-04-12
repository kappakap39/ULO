/*
 * Created on Oct 17, 2007
 * Created by Prawit Limwattanachai
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
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.OtherNameDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SaveOtherNameWebAction
 */
public class SaveOtherNameWebAction extends WebActionHelper implements
		WebAction {
	Logger logger = Logger.getLogger(SaveChangeNameWebAction.class);
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
        
        try{
        	//utility.stringToBigDecimal();
	        String citizen_id = getRequest().getParameter("citizen_id");
	        String title_name = getRequest().getParameter("title_thai");
	        String name = getRequest().getParameter("name");
	        String lastname = getRequest().getParameter("last_name");
	        String occupation = getRequest().getParameter("occupation");
	        String position = getRequest().getParameter("position");
	        String seq = getRequest().getParameter("seq");
	        String description = getRequest().getParameter("description");	        
	        	        
	        logger.debug("othername seq = "+seq);
	//        logger.debug("Personal Type = "+personalType);
	        
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        Vector otherNameVect = applicationDataM.getOtherNameDataM(); 
	        
	       
	        OtherNameDataM otherNameDataM;
	     
	        if(Integer.parseInt(seq) == 0){// Add new change name
	        	otherNameDataM = new OtherNameDataM();
	        	otherNameDataM.setSeq(otherNameVect.size()+1);
	        	logger.debug("otherNameDataM.getSeq()  = "+otherNameDataM.getSeq());
	            otherNameVect.add(otherNameDataM);
	        }else{//Edit change name
	        	otherNameDataM = utility.getOtherNameBySeq(applicationDataM, Integer.parseInt(seq));
	        }
	        
	        otherNameDataM.setCitizenId(citizen_id);
	        otherNameDataM.setTitleName(title_name);
	        otherNameDataM.setName(name);
	        otherNameDataM.setLastName(lastname);
	        otherNameDataM.setOccupation(occupation);
	        otherNameDataM.setPosition(position);
	        otherNameDataM.setDescription(description);
	        otherNameDataM.setOrigOwner("Y");
	        
	        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	        if(ORIGUtility.isEmptyString(otherNameDataM.getCreateBy())){
	        	otherNameDataM.setCreateBy(userM.getUserName());
	        }else{
	        	otherNameDataM.setUpdateBy(userM.getUserName());
	        }
	        
	        OtherNameDataM otherNameDataM2;
	        for(int i=0; i<otherNameVect.size(); i++){
	        	otherNameDataM2 = (OtherNameDataM) otherNameVect.get(i);
	        	otherNameDataM2.setSeq(i+1);
	        }
	        
	        //Rewrite
	        String tableData = utility.getOtherNameTable(otherNameVect, getRequest());
	        
	        PopulatePopupM populatePopupM = new PopulatePopupM("OtherName",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData.toString()));
			Vector popMs = new Vector();
			popMs.add(populatePopupM);
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
        }catch(Exception e){
            logger.error("Error in SaveOtherNameWebAction.preModelRequest() : ", e);
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
