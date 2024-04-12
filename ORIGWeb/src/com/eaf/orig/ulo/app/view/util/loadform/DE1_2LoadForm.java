package com.eaf.orig.ulo.app.view.util.loadform;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DE1_2LoadForm extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(DE1_2LoadForm.class);
	String CACHE_REQUIRE_VERIFY_FORM = SystemConstant.getConstant("CACHE_REQUIRE_VERIFY_FORM");
	@Override
	public Object processAction() {		
		String formId = "";
		String applicationType = "";
		String jobState = "";
		if(objectForm instanceof HashMap){
			HashMap origApplicationForm = (HashMap)objectForm;
			logger.debug("origApplicationForm >> "+origApplicationForm);
			applicationType = SQLQueryEngine.display(origApplicationForm,"APPLICATION_TYPE");
			jobState = SQLQueryEngine.display(origApplicationForm,"JOB_STATE");
		}else{
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			applicationType = applicationGroup.getApplicationType();
			jobState = applicationGroup.getJobState();			
		}		
		logger.debug("jobState >> "+jobState);
		logger.debug("applicationType >> "+applicationType);
		if(SystemConstant.lookup("JOBSTATE_REQUIRE_VERIFY_INCOME",jobState)){
			formId = CacheControl.getName(CACHE_REQUIRE_VERIFY_FORM,"APPLICATION_TYPE",applicationType,"FORM_ID");
		}else{
			logger.debug("applicationType >> "+applicationType);
			formId = FormControl.getFormIdByApplicationType(applicationType);
		}
		logger.debug("formId >> "+formId);
		return formId;
	}
}
