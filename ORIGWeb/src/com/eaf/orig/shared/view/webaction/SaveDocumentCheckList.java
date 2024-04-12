/*
 * Created on Oct 15, 2007
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
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SaveDocumentCheckList
 */
public class SaveDocumentCheckList extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(SaveDocumentCheckList.class);
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
        PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
        Vector documentVect = utility.getDocumentListByCustomerType(personalInfoDataM.getCustomerType());
        /*if(formHandler.getAppForm().getDocumentCheckListDataM() == null){
        	formHandler.getAppForm().setDocumentCheckListDataM(documentVect);
        }*/
        Vector docCheckList = formHandler.getAppForm().getDocumentCheckListDataM();
        Vector newDocCheckList = new Vector();
        
        DocumentCheckListDataM docCheckListDataM = null;
        //DocumentCheckListDataM docCheckListDataMTmp = null;
        //String[] checked = getRequest().getParameterValues("docListChk");
        String[] remark = getRequest().getParameterValues("remark");
        
        logger.debug(">>>>>>>>>>>>>>Size of Remark = "+remark.length);
  
        String docID = "";
        String[] docIDs;
        String checkData;
        String checkType;
        boolean flag = true;
        int documentID;
        //newDocCheckList = formHandler.getAppForm().getDocumentCheckListDataM();

        //if ((checked != null) && (checked.length > 0)) {
        	logger.debug("documentVect.size() = "+documentVect.size());
	        for (int i = 0; i < documentVect.size(); i++) {
		        flag = true;
		        docCheckListDataM = (DocumentCheckListDataM) documentVect.elementAt(i);
		        logger.debug("docCheckList ="+docCheckList);
		        logger.debug("docCheckListDataM ="+docCheckListDataM);
		        documentID = utility.getDocIdByDocTypeId(docCheckList, docCheckListDataM.getDocTypeId());
		        //for (int j = 0; j < checked.length; j++) {
		        docID = getRequest().getParameter("docListChk"+i);
		        if(docID != null){
			        docIDs = docID.split(",");
			        checkData = docIDs[0];
			        checkType = docIDs[1];
			        
			        logger.debug("docID>>> ="+docID);
			        logger.debug("checkData>>> ="+checkData);
			        logger.debug("checkType>>> ="+checkType);
			        logger.debug("docCheckListDataM.getDocTypeId()>>> ="+docCheckListDataM.getDocTypeId());
			   //     logger.debug("SaveDocumentList>>>DocTypeId ="+docCheckListDataM.getDocTypeId());
				        if (checkData.equals(String.valueOf(docCheckListDataM.getDocTypeId()))) {
				        	if(OrigConstant.RECEIVE_DOC.equals(checkType)){
				        		docCheckListDataM.setReceive("Y");
				        		docCheckListDataM.setWaive("N");
				        	}else if(OrigConstant.WAIVE_DOC.equals(checkType)){
				        		docCheckListDataM.setWaive("Y");
				        		docCheckListDataM.setReceive("N");
				        	}else if("N".equals(checkType)){
				        		docCheckListDataM.setWaive("");
				        		docCheckListDataM.setReceive("");
				        	}
					        //docCheckListDataM.setRemark(remark[j]);
				        	docCheckListDataM.setDocId(documentID);
					        newDocCheckList.addElement(docCheckListDataM);
					        flag = false;
					
					        //break;
				        }
		        }
		        //}
	
		        if (flag) {
	        		docCheckListDataM.setReceive("N");
	        		docCheckListDataM.setWaive("N");
	        		docCheckListDataM.setDocId(documentID);
			        newDocCheckList.addElement(docCheckListDataM);
		        }
	        }
        //}
	        logger.debug("newDocCheckList = "+newDocCheckList.size());    
        for (int j = 0; j < remark.length; j++) {
        	logger.debug("j = "+j); 
        	docCheckListDataM = (DocumentCheckListDataM) newDocCheckList.get(j);
        	docCheckListDataM.setRemark(remark[j]);
        }
        formHandler.getAppForm().setDocumentCheckListDataM(newDocCheckList);
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
