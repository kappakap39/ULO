package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class PERCENT_LIMIT_CCProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(PERCENT_LIMITProperty.class);
	String PERCENT_LIMIT_MAINCARD_CC_IDENTIFY =SystemConstant.getConstant("PERCENT_LIMIT_MAINCARD_MANUAL");
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("PERCENT_LIMITProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");		
		FormErrorUtil formError = new FormErrorUtil();
		ApplicationDataM  applicationItem = (ApplicationDataM)objectForm;
		if(Util.empty(applicationItem)){
			applicationItem = new ApplicationDataM();
		}
		String finalAppDecision = applicationItem.getFinalAppDecision();
		CardDataM cardM = applicationItem.getCard();
		if(Util.empty(cardM)){
			cardM  = new CardDataM();
		}
		LoanDataM loan = applicationItem.getLoan();
		if(Util.empty(loan)){
			loan = new LoanDataM();
		}
		
		if(PERCENT_LIMIT_MAINCARD_CC_IDENTIFY.equals(cardM.getPercentLimitMaincard()) && Util.empty(finalAppDecision) ){
			if(Util.empty(loan.getRequestLoanAmt())){
				formError.error("PERCENT_LIMIT",PREFIX_ERROR+LabelUtil.getText(request,"PERCENT_LIMIT"));
			}else{
				if(loan.getRequestLoanAmt().intValue() <= 0){
					formError.error("PERCENT_LIMIT",MessageErrorUtil.getText(request,"ERROR_PERCENT_LIMIT"));
				}
			}
		}
		return formError.getFormError();
	}
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm){
		String VALIDATE_FLAG = ValidateFormInf.VALIDATE_NO;
		ApplicationDataM  applicationItem = (ApplicationDataM)masterObjectForm;
		if(Util.empty(applicationItem)){
			applicationItem = new ApplicationDataM();
		}
		String finalAppDecision = applicationItem.getFinalAppDecision();
		
		CardDataM cardM = applicationItem.getCard();
		if(Util.empty(cardM)){
			cardM  = new CardDataM();
		}
		LoanDataM loan = applicationItem.getLoan();
		if(Util.empty(loan)){
			loan = new LoanDataM();
		}
		
		if(ValidateFormInf.VALIDATE_SUBMIT.equals(mandatoryConfig)&&PERCENT_LIMIT_MAINCARD_CC_IDENTIFY.equals(cardM.getPercentLimitMaincard())
			&& Util.empty(finalAppDecision)){
			VALIDATE_FLAG= ValidateFormInf.VALIDATE_SUBMIT;
		}
		logger.debug("validateFlag>>>"+VALIDATE_FLAG);
		return VALIDATE_FLAG;
	}
}
