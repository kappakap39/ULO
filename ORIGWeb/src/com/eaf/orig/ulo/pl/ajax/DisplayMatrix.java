package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

//import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
//import com.eaf.orig.ulo.pl.app.rule.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;

public class DisplayMatrix implements AjaxDisplayGenerateInf{   
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		logger.debug("[DisplayMatrix]");
			PLOrigFormHandler origForm = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");			
//			UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");		
			String formID = origForm.getFormID();
			String searchType = (String) request.getSession().getAttribute("searchType");		
			
//			ProcessMenuM currentMenuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");			
//			if(null == currentMenuM) currentMenuM = new ProcessMenuM();
			
			PLApplicationDataM applicationM = origForm.getAppForm();		
				if(applicationM == null)  applicationM = new PLApplicationDataM();
			
			logger.debug("SearchType >> "+searchType);	
			
//			logger.debug("MenuJobstate >> "+currentMenuM.getMenuJobstate());
//			ORIGXRulesTool xrulesTool = new ORIGXRulesTool();
//			XrulesRequestDataM requestM = xrulesTool.MapDisplayXrulesRequestDataM(applicationM,null,userM,searchType ,currentMenuM.getMenuJobstate());		
			
			DisplayMatrixTool tool = new DisplayMatrixTool();			
			String obj = tool.DisplayMatrix(applicationM ,request ,searchType ,formID );
		return obj;
	}

}
