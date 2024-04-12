package com.eaf.orig.ulo.pl.app.utility;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class LogicMandatory {
	public static final int IGNORE_DECREASE = 1;
	public static final int CASH_DAY5 = 2;
	
	public static boolean doLogic(MandatoryDataM mandatoryM,HttpServletRequest request){
		switch (mandatoryM.getLogic()) {
			case IGNORE_DECREASE:
				return IGNORE_DECREASE(mandatoryM, request);
			case CASH_DAY5:
				return CASH_DAY5(mandatoryM, request);
			default:
				break;
		}
		return false;
	}
	public static boolean CASH_DAY5(MandatoryDataM mandatoryM,HttpServletRequest request){
		String searchType = (String) request.getSession().getAttribute("searchType");   
		if(OrigConstant.SEARCH_TYPE_CASH_DAY5.equals(searchType)){
			return true;
		}
		return false;
	}
	public static boolean IGNORE_DECREASE(MandatoryDataM mandatoryM,HttpServletRequest request){
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String role = userM.getCurrentRole();
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);		
		PLApplicationDataM applicationM = formHandler.getAppForm();
	 	  if(!OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId())){
	 	  		if(OrigConstant.ROLE_DC.equals(role) || OrigConstant.ROLE_DC_SUP.equals(role) 
	 	  				|| OrigConstant.ROLE_VC.equals(role) || OrigConstant.ROLE_VC_SUP.equals(role)){
					return true;
				}
	 	  }
		return false;
	}
	
}
