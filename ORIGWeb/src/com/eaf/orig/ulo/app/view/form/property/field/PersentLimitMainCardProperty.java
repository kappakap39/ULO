package com.eaf.orig.ulo.app.view.form.property.field;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;

public class PersentLimitMainCardProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(PersentLimitMainCardProperty.class);
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		String VALIDATE_FLAG = ValidateFormInf.VALIDATE_NO;
		ApplicationDataM  applicationItem = (ApplicationDataM)masterObjectForm;
		if(Util.empty(applicationItem)){
			applicationItem = new ApplicationDataM();
		}
		String finalAppDecision = applicationItem.getFinalAppDecision();
		logger.debug("finalAppDecision >> "+finalAppDecision);
		logger.debug("mandatoryConfig >>"+mandatoryConfig);
		
		if(ValidateFormInf.VALIDATE_SUBMIT.equals(mandatoryConfig) && Util.empty(finalAppDecision)){
			VALIDATE_FLAG= ValidateFormInf.VALIDATE_YES;
		}
		logger.debug("validateFlag>>>"+VALIDATE_FLAG);
		return VALIDATE_FLAG;
	}
	
}
