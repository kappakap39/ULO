package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;


import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;

public class ValidateFollowDocEmailWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(ValidateFollowDocEmailWebAction.class);
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {		
		try{
			PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
			PLApplicationDataM applicationM = formHandler.getAppForm();
			String saleBranchCode = getRequest().getParameter("seller_branch_code");
			String saleId = getRequest().getParameter("saleId");
			String refId = getRequest().getParameter("refId");
			logger.debug("@@@@@ saleBranchCode:" + saleBranchCode);
			logger.debug("@@@@@ saleId:" + saleId);
			logger.debug("@@@@@ refId:" + refId);
			String pass = "not_pass";
			if(applicationM != null){
				PLSaleInfoDataM saleM = applicationM.getSaleInfo();
				if(saleM == null){
					saleM = new PLSaleInfoDataM();
					applicationM.setSaleInfo(saleM);
				}
				if(saleId != null && !"".equals(saleId)){
					saleM.setSalesName(saleId);
				}
				if(refId != null && !"".equals(refId)){
					saleM.setRefName(refId);
				}
				saleM.setSalesBranchCode(saleBranchCode);
				if(saleBranchCode != null && !"".equals(saleBranchCode) && hasFollowDocList(applicationM)){
					pass = "pass";
				}
			}
			this.getResponse().getWriter().write(pass);
		}catch (Exception e) {
			logger.fatal("Error ",e);
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		/***SepTemWi Not Forward , This Call by Used Ajax*/
		return FrontController.NOTFORWARD;
	}
	
	public boolean hasFollowDocList(PLApplicationDataM app){
		if(app != null && app.getDocCheckListVect() != null && app.getDocCheckListVect().size() > 0){
			Vector<PLDocumentCheckListDataM> docVt = app.getDocCheckListVect();
			for(int i=0;i<docVt.size();i++){
				PLDocumentCheckListDataM docM = (PLDocumentCheckListDataM)docVt.get(i);
				if(docM != null && OrigConstant.DocumentStatus.TrackDoc.equals(docM.getReceive())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
