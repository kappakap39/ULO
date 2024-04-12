/*
 * Created on Nov 18, 2007
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
package com.eaf.orig.master.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.MandatoryM;
import com.eaf.orig.shared.control.event.MandFieldEvent;

/**
 * @author Administrator
 *
 * Type: ShowMandFieldWebAction
 */
public class ShowMandFieldWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(ShowMandFieldWebAction.class);
	MandatoryM mandatoryM = new MandatoryM();

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		MandFieldEvent mandFieldEvent = new MandFieldEvent();
		
		mandFieldEvent.setEventType(MandFieldEvent.MAND_FIELD_SELECT);
		
		log.debug("MandFieldEvent.MAND_FIELD_SELECT=" + MandFieldEvent.MAND_FIELD_SELECT);
		
		mandFieldEvent.setObject(mandatoryM);
		
		log.debug("mandatoryM = " + mandatoryM);
		log.debug("mandatoryM.getFormNameId() = " + mandatoryM.getFormNameId());
		log.debug("mandatoryM.getFieldName() = " + mandatoryM.getFieldName());
		log.debug("mandatoryM.getCusType() = " + mandatoryM.getCusType());
		log.debug("mandFieldEvent=" + mandFieldEvent);
		
		return mandFieldEvent;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {

		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse arg0) {

		return defaultProcessResponse(arg0);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		
		// show edit form
		log.debug("///ShowFieldIDWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equals(shwAddFrm)){
			String frmNameIDEdit = getRequest().getParameter("frmNameIDEdit");
			String frmNameEdit = getRequest().getParameter("frmNameEdit");
			String cusTypeEdit = getRequest().getParameter("cusTypeEdit");
						
			mandatoryM.setFormNameId(frmNameIDEdit);
			mandatoryM.setFieldName(frmNameEdit);
			mandatoryM.setCusType(cusTypeEdit);
			
			return true;
		}
		// just show add form & clear session
//		if(!"cancelEdit".equalsIgnoreCase(shwAddFrm) && !"update".equalsIgnoreCase(shwAddFrm)){
//			getRequest().getSession().removeAttribute("FIRST_SEARCH_formNameId");
//			getRequest().getSession().removeAttribute("FIRST_SEARCH_fieldName");
//		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return 0;
	}
	protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
	}
	protected void doSuccess(EventResponse arg0) {
		log.debug("//from Action//mandatoryM = " + (MandatoryM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_MandatoryM", (MandatoryM)arg0.getEncapData());
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
