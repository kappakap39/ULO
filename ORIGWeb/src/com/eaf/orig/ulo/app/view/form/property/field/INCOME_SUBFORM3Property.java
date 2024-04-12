package com.eaf.orig.ulo.app.view.form.property.field;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class INCOME_SUBFORM3Property extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(ADDRESS_NOProperty.class);
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String mandatoryFlag = ValidateFormInf.VALIDATE_NO;
		if(mandatoryConfig.equals(ValidateFormInf.VALIDATE_SUBMIT)){
			if(!SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
				mandatoryFlag = ValidateFormInf.VALIDATE_SUBMIT;
			}
		}	
		return mandatoryFlag;
	}
}
