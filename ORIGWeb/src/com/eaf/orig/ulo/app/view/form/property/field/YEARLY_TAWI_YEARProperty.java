package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public class YEARLY_TAWI_YEARProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(YEARLY_TAWI_YEARProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("YEARLY_TAWI_YEARProperty.validateForm");
/*		String BASE_TH_YEAR = SystemConstant.getConstant("BASE_TH_YEAR");
		int baseYear = Integer.parseInt(BASE_TH_YEAR);
		
		YearlyTawi50DataM yearlyTawiM = (YearlyTawi50DataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		int defaultTawiYear = IncomeTypeUtility.getDefaultThaiYearForTawi();
		
		if(yearlyTawiM.getYear() == 0 || yearlyTawiM.getYear() > defaultTawiYear|| yearlyTawiM.getYear() < baseYear){
			formError.clearElement("YEAR_"+yearlyTawiM.getSeq());
			formError.error("YEAR_"+yearlyTawiM.getSeq(),MessageErrorUtil.getText(request,"ERROR_ID_YEARLY_TAWI_YEAR"));
		}*/
		
		YearlyTawi50DataM yearlyTawiM = (YearlyTawi50DataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		int defaultTawiYear = IncomeTypeUtility.getDefaultThaiYearForTawi();
		if(defaultTawiYear !=yearlyTawiM.getYear()){
			formError.clearElement("YEAR_"+yearlyTawiM.getSeq());
			formError.error("YEAR_"+yearlyTawiM.getSeq(),MessageErrorUtil.getText(request,"ERROR_ID_YEARLY_TAWI_YEAR"));
		}
		
		return formError.getFormError();
	}
}
