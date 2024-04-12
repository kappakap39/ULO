package com.eaf.orig.ulo.app.view.util.loadform;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class IALoadForm extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(IALoadForm.class);
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	String DEFAULT_IA_APPLICATION_FORM = SystemConstant.getConstant("DEFAULT_IA_APPLICATION_FORM");
	@Override
	public Object processAction() {		
		HashMap origApplicationForm = (HashMap)objectForm;
		if(null==origApplicationForm){
			origApplicationForm = new HashMap();
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
			if(null!=applicationGroup){
				origApplicationForm.put("APPLICATION_TEMPLATE", applicationGroup.getApplicationTemplate());
			}
		}
		String applicationTemplate = (String)origApplicationForm.get("APPLICATION_TEMPLATE");
		logger.debug("applicationTemplate >> "+applicationTemplate);
		String formId = CacheControl.getName(CACHE_TEMPLATE,"TEMPLATE_ID",applicationTemplate,"FORM_NAME");
		logger.debug("formId >> "+formId);
		if(Util.empty(formId)){
			formId = DEFAULT_IA_APPLICATION_FORM;
		}
		return formId;
	}
}
