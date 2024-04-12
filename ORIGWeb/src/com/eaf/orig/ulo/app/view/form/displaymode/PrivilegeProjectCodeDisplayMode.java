package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeRccmdPrjCdeDataM;

public class PrivilegeProjectCodeDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(PrivilegeProjectCodeDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
		if(Util.empty(privilegeProjectCode)){
			 privilegeProjectCode = new PrivilegeProjectCodeDataM();
		}	
		ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>  projectRccList = privilegeProjectCode.getPrivilegeProjectCodePrjCdes(); 
		String displayMode = HtmlUtil.VIEW;
		if(Util.empty(projectRccList)){
			displayMode =  HtmlUtil.EDIT;
		} 
		return displayMode;
	}
}
