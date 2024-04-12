package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class ValidateAddSupCardInfo extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateAddSupCardInfo.class);
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}
	
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();		
		String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		String PRODUCT_CRADIT_CARD_LIMIT = SystemConstant.getConstant("PRODUCT_CRADIT_CARD_LIMIT");
		logger.debug("validateForm ValidateAddSupPersonal ");
		String uniqueId = request.getParameter("uniqueId");
		String CARD_TYPE = request.getParameter("CARD_TYPE");
		String CARD_LEVEL = request.getParameter("CARD_LEVEL");
		logger.debug("uniqueId >>> "+uniqueId);
		logger.debug("CARD_TYPE >>> "+CARD_TYPE);
		logger.debug("CARD_LEVEL >>> "+CARD_LEVEL);
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		
		ArrayList<ApplicationDataM> allApplicationCC=  applicationGroup.filterApplicationLifeCycle(PRODUCT_CRADIT_CARD);
		if(allApplicationCC.size()>=FormatUtil.getInt(PRODUCT_CRADIT_CARD_LIMIT)){
			formError.error(MessageUtil.getText(request, "MSG_MAX_CARD"));
		}
		
		ArrayList<ApplicationDataM> supplemantaryApplications = applicationGroup.filterMaincardRecordIdCardTypeLifeCycle(uniqueId,APPLICATION_CARD_TYPE_SUPPLEMENTARY);
		logger.debug("supplemantaryApplications >>> "+supplemantaryApplications.size());
		if(supplemantaryApplications.size()>0){
			formError.error(MessageUtil.getText(request, "MSG_MAX_CARD"));
		}
		formError.mandatoryElement("CARD_TYPE", "CARD_TYPE_SUP", CARD_TYPE, request);
		formError.mandatoryElement("CARD_LEVEL", "CARD_LEVEL_SUP", CARD_LEVEL, request);
		return formError.getFormError();
	}

}
