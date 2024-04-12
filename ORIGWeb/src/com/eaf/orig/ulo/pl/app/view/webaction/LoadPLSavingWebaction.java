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
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class LoadPLSavingWebaction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preModelRequest() {
		//Load data to session
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);  
		PLApplicationDataM plapplicationDataM = formHandler.getAppForm();
			
		PLPersonalInfoDataM plPersonalInfoDataM = plapplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		Vector vFinanceDetails = plPersonalInfoDataM.getFinancialInfoVect();
		logger.debug("vFinanceDetails.size()"+vFinanceDetails.size());
		Vector vPayment=new Vector();
		vPayment.addAll(vFinanceDetails);
		getRequest().getSession().setAttribute("PL_SAVING_DETAIL", vPayment);		
		return true;
	}

	@Override
	public int getNextActivityType() {
		// TODO Auto-generated method stub
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
