/*
 * Created on Nov 27, 2007
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

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.MandatoryM;
import com.eaf.orig.shared.control.event.MandFieldEvent;

/**
 * @author Administrator
 *
 * Type: UpdateMandFieldWebAction
 */
public class UpdateMandFieldWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(UpdateMandFieldWebAction.class);
	MandatoryM mandatoryM = new MandatoryM();

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		MandFieldEvent mandFieldEvent = new MandFieldEvent();
		mandFieldEvent.setEventType(MandFieldEvent.MAND_FIELD_UPDATE);
		
		log.debug("MandFieldEvent.MAND_FIELD_UPDATE=" + MandFieldEvent.MAND_FIELD_UPDATE);
		
		mandFieldEvent.setObject(mandatoryM);
		
		log.debug("mandatoryM=" + mandatoryM);
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
		String frmNameID = getRequest().getParameter("frmNameID");
		String fldName = getRequest().getParameter("fldName");
		String cmrflag = getRequest().getParameter("cmrflag");
		String uwflag = getRequest().getParameter("uwflag");
		String deflag = getRequest().getParameter("deflag");
		String xcmrflag = getRequest().getParameter("xcmrflag");
		String pdflag = getRequest().getParameter("pdflag");
		String customerType = getRequest().getParameter("customerType");
		
		 log.debug("frmNameID ="+frmNameID);
		 log.debug("fldName ="+fldName);
		 log.debug("cmrflag ="+cmrflag);
		 log.debug("uwflag ="+uwflag);
		 log.debug("deflag ="+deflag);
		 log.debug("xcmrflag ="+xcmrflag);
		 log.debug("pdflag ="+pdflag);
		 log.debug("customerType ="+customerType);
		 		 
		 mandatoryM = new MandatoryM(); 
		 
		 mandatoryM.setFormNameId(frmNameID);
		 mandatoryM.setFieldName(fldName);
		 mandatoryM.setCmrFlag(cmrflag);
		 mandatoryM.setUwFlag(uwflag);
		 mandatoryM.setDeFlag(deflag);
		 mandatoryM.setXcmrFlag(xcmrflag);
		 mandatoryM.setPdFlag(pdflag);
		 mandatoryM.setCusType(customerType);
		
		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
			origMasterForm.setFormErrors(new Vector());
			if (mandatoryM.getCmrFlag()==null || "".equalsIgnoreCase(mandatoryM.getCmrFlag())){
				origMasterForm.getFormErrors().add("CMR Flag is required");
			}
			if (mandatoryM.getUwFlag()==null || "".equalsIgnoreCase(mandatoryM.getUwFlag())){
				origMasterForm.getFormErrors().add("UW Flag is required");
			}
			if (mandatoryM.getDeFlag()==null || "".equalsIgnoreCase(mandatoryM.getDeFlag())){
				origMasterForm.getFormErrors().add("DE Flag is required");
			}
			if (mandatoryM.getXcmrFlag()==null || "".equalsIgnoreCase(mandatoryM.getXcmrFlag())){
				origMasterForm.getFormErrors().add("XCMR Flag is required");
			}
			if (mandatoryM.getPdFlag()==null || "".equalsIgnoreCase(mandatoryM.getPdFlag())){
				origMasterForm.getFormErrors().add("PD Flag is required");
			}
			if (mandatoryM.getCusType()==null || "".equalsIgnoreCase(mandatoryM.getCusType())){
				origMasterForm.getFormErrors().add("Customer Type is required");
			}
			
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				getRequest().getSession().setAttribute("EDIT_MandatoryM",mandatoryM);
				return false;
			}else {
				return true;
			}
	}

	public int getNextActivityType() {

		return FrontController.ACTION;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return "action=SearchMandField";
    }
    
    protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
//		***Refresh Cache
		log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("MandatoryField");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
   

}
