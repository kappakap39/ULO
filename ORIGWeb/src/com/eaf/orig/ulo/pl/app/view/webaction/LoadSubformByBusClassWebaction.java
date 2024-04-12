package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigBusinessClassUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.ajax.ClearDataInformation;
import com.eaf.orig.ulo.pl.app.utility.BusinessClassManager;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.MapDefaultDataM;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class LoadSubformByBusClassWebaction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(LoadSubformByBusClassWebaction.class);
	
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
		
		logger.debug("LoadSubformByBusClass >> ");
		
		PLOrigFormHandler origForm = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		PLApplicationDataM applicationM = origForm.getAppForm();
		
		String saleType = getRequest().getParameter("saleType");
		String changeSaletype = getRequest().getParameter("change-saletype");
		
		if(!OrigUtil.isEmptyString(changeSaletype)){
			saleType = changeSaletype;
		}
		
		logger.debug("saleType >> "+saleType);
		
		BusinessClassManager buinessClass = new BusinessClassManager();
		
		Vector userRoles = userM.getRoles();
		
		String currentTab = "MAIN_TAB";
		String drawDownFlag = null;		
		String pDomain = applicationM.getProductDomain();
		String pGroup = applicationM.getProductGroup();
		String pFamily = applicationM.getProductFamily();
		String product = applicationM.getProduct();
		
		String busClass = buinessClass.findBus(pDomain, pGroup, pFamily, product, saleType);
		
		logger.debug("BusClassID >> "+busClass);
		
		if(!ORIGUtility.isEmptyString(busClass)){
			applicationM.setSaleType(saleType);
			applicationM.setBusinessClassId(busClass);
		}
		
		ClearApplicationData(applicationM,userM);
		
		String formID = ORIGFormUtil.getFormIDByBus(applicationM.getBusinessClassId());
		
		String searchType = (String) getRequest().getSession().getAttribute("searchType");			
		ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");			
		if(null == currentMenuM) currentMenuM = new ProcessMenuM();
		
		DisplayMatrixTool matrix = new DisplayMatrixTool();			
		applicationM.setMatrixServiceID(matrix.getMatrixServiceID(userM, applicationM, currentMenuM, searchType));
		MapDefaultDataM.map(applicationM);
		
		origForm.setAppForm(applicationM);
//		origForm.getSubForms().clear();
		origForm.setLoadedSubForms(false);
		
		origForm.loadSubFormsForDrawDown(userRoles, formID, drawDownFlag);
		origForm.setCurrentTab(currentTab);
		origForm.setFormID(formID);

		getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, origForm);

		PLApplicationDataM sensitiveappM = (PLApplicationDataM)SerializeUtil.clone(origForm.getAppForm());
		getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGSensitive,sensitiveappM);
		
		return true;
	}

	private void ClearApplicationData(PLApplicationDataM appM ,UserDetailM userM) {
		//Praisan Khunkaew for not clear project code in case of in/decrease
		if(!OrigBusinessClassUtil.getInstance().isContainsBusGroup(appM, OrigConstant.BusGroup.KEC_ICDC_FLOW)){
			appM.setProjectCode(null);
		}
		JsonObjectUtil jsonObj = new JsonObjectUtil();
		ClearDataInformation clearInfo = new ClearDataInformation();
			clearInfo.ClearPLXRulesVerificationResultDataM(jsonObj, appM, userM);	
	}

	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}

	@Override
	public boolean getCSRFToken() {
		return false;
	}

}
