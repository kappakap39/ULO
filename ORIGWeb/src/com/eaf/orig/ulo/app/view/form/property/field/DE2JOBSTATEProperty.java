package com.eaf.orig.ulo.app.view.form.property.field;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DE2JOBSTATEProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(DE2JOBSTATEProperty.class);
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		String VALIDATE_FLAG = ValidateFormInf.VALIDATE_NO;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		logger.debug("applicationGroup.getJobState() >> "+applicationGroup.getJobState());
		logger.debug("mandatoryConfig >>"+mandatoryConfig);
		if(mandatoryConfig.equals(ValidateFormInf.VALIDATE_SUBMIT) && !SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			VALIDATE_FLAG= ValidateFormInf.VALIDATE_YES;
		}
		logger.debug("validateFlag>>>"+VALIDATE_FLAG);
		return VALIDATE_FLAG;
	}
	
}
