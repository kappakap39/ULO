package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.DateValidateUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class APPLY_DATEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(APPLY_DATEProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		logger.debug("APPLY_DATEProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		String APPLY_DATE_ERROR = MessageErrorUtil.getText(request,"APPLY_DATE_ERROR");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		
		if(Util.empty(applicationGroup.getApplyDate())){
			formError.error("APPLY_DATE",PREFIX_ERROR+LabelUtil.getText(request,"APPLY_DATE"));
		}else{
			if(!Util.empty(applicationGroup.getApplicationDate())){
				if(DateValidateUtil.validateDate(applicationGroup.getApplyDate(), DateValidateUtil.LIMIT_YEAR_DATE_VALIDATE,DateValidateUtil.TH)){
					formError.error("APPLY_DATE",LabelUtil.getText(request,"APPLY_DATE")+MessageErrorUtil.getText(request, "ERROR_LIMITE_YEAR_FORMAT"));
				}else{
					if(applicationGroup.getApplyDate().getTime() > applicationGroup.getApplicationDate().getTime()){
						formError.error("APPLY_DATE",APPLY_DATE_ERROR);
					}
					if(DateValidateUtil.validateDate(applicationGroup.getApplyDate(), DateValidateUtil.FORMAT_DATE_VALIDATE,DateValidateUtil.TH)){
						formError.error("APPLY_DATE",LabelUtil.getText(request,"APPLY_DATE")+MessageErrorUtil.getText(request, "ERROR_DATE_FORMAT"));
					}
				}
			}
		}
		return formError.getFormError();
	}
}
