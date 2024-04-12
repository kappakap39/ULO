package com.eaf.orig.ulo.app.view.util.loadform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class CALoadForm extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(CALoadForm.class);
	String CACHE_NAME_USER = SystemConstant.getConstant("CACHE_NAME_USER");
	ArrayList<String> caRoleGroup =  new ArrayList<String>(Arrays.asList(SystemConstant.getArrayConstant("CA_ROLE_GROUP")));
	ArrayList<String> caDlaHighLevel =  new ArrayList<String>(Arrays.asList(SystemConstant.getArrayConstant("CA_DLA_HIGH_LEVEL")));
	String CACHE_CA_DLA_FORM = SystemConstant.getConstant("CACHE_CA_DLA_FORM");
	@Override
	public Object processAction() {	
		String formId = "";
		String functionId = request.getParameter("functionId");
		logger.debug("functionId >> "+functionId);
//		if(Util.empty(functionId)){
			String applicationType = "";
			if(objectForm instanceof HashMap){
				HashMap origApplicationForm = (HashMap)objectForm;
				logger.debug("origApplicationForm >> "+origApplicationForm);
				applicationType = SQLQueryEngine.display(origApplicationForm,"APPLICATION_TYPE");
			}else{
				ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
				applicationType = applicationGroup.getApplicationType();
			}
			logger.debug("applicationType >> "+applicationType);
			UserDetailM userM =	(UserDetailM)request.getSession().getAttribute("ORIGUser");
			String userName = userM.getUserName();
			String roleId = FormControl.getFormRoleId(request);
			if(groupCaHighLevel(roleId, userName) && Util.empty(functionId)){
					formId = CacheControl.getName(CACHE_CA_DLA_FORM,"APPLICATION_TYPE",applicationType,"FORM_ID");
			} else{
				logger.debug("applicationType >> "+applicationType);
				formId = FormControl.getFormIdByApplicationType(applicationType);
			}
//		}
		logger.debug("formId >> "+formId);
		return formId;
	}
	public boolean groupCaHighLevel(String roleId,String userName){
		String DLA_ID = CacheControl.getName(CACHE_NAME_USER,"USER_NAME",userName,"DLA_ID");
		return caRoleGroup.contains(roleId) && caDlaHighLevel.contains(DLA_ID);
	}
}
