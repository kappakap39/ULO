package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeRccmdPrjCdeDataM;

public class PrivilegeProjectCodeDisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(PrivilegeProjectCodeDisplayObject.class);

	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
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
